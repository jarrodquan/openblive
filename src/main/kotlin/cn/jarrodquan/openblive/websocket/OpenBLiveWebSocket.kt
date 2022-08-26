package cn.jarrodquan.openblive.websocket

import cn.jarrodquan.openblive.client.data.AppStartData
import cn.jarrodquan.openblive.websocket.data.*
import cn.jarrodquan.openblive.websocket.event.OpenBLiveEvent
import cn.jarrodquan.openblive.websocket.event.OpenBLiveEventListener
import cn.jarrodquan.openblive.websocket.event.OpenBLiveEventPublisher
import okhttp3.*
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean

class OpenBLiveWebSocket(private val appStartData: AppStartData) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(OpenBLiveWebSocket::class.java)
    }

    private val httpClient = OkHttpClient.Builder().build()
    private val eventPublisher = OpenBLiveEventPublisher<OpenBLiveData>()

    private lateinit var webSocket: WebSocket

    fun connect() {
        val wssLink = appStartData.websocketInfo.wssLink
        for (i in wssLink.indices) {
            val connectSuccessfully = AtomicBoolean(false)

            try {
                synchronized(connectSuccessfully) {
                    webSocket = httpClient.newWebSocket(
                        Request.Builder()
                            .url(wssLink[i])
                            .build(),
                        object : WebSocketListener() {
                            override fun onOpen(webSocket: WebSocket, response: Response) {
                                synchronized(connectSuccessfully) {
                                    connectSuccessfully.set(true)
                                    connectSuccessfully.notifyAll()
                                }
                            }
                        }
                    )

                    connectSuccessfully.wait()
                }
            } catch (exception: Exception) {
                if (i < wssLink.size - 1) {
                    LOGGER.error("尝试连接[${wssLink[i]}]失败", exception)
                }
            }

            if (connectSuccessfully.get()) {
                webSocket.close(1000, "正常关闭")

                webSocket = httpClient.newWebSocket(
                    Request.Builder()
                        .url(wssLink[i])
                        .build(),
                    OpenBLiveWebSocketListener(appStartData, eventPublisher)
                )

                LOGGER.debug("已使用该WebSocket地址[{}]", wssLink[i])
                return
            }
        }

        throw RuntimeException("所有尝试均失败")
    }

    fun disconnect() {
        webSocket.close(1000, "正常关闭")
    }

    /**
     * 收到弹幕
     */
    fun onDanmaku(eventListener: OpenBLiveEventListener<DmData>) {
        eventPublisher.subscribe(OpenBLiveEvent.ON_DANMAKU, eventListener as OpenBLiveEventListener<OpenBLiveData>)
    }

    /**
     * 收到礼物
     */
    fun onGift(eventListener: OpenBLiveEventListener<SendGiftData>) {
        eventPublisher.subscribe(OpenBLiveEvent.ON_GIFT, eventListener as OpenBLiveEventListener<OpenBLiveData>)
    }

    /**
     * 付费留言
     */
    fun onSuperChat(eventListener: OpenBLiveEventListener<SuperChatData>) {
        eventPublisher.subscribe(OpenBLiveEvent.ON_SUPER_CHAT, eventListener as OpenBLiveEventListener<OpenBLiveData>)
    }

    /**
     * 付费留言下线
     */
    fun onSuperChatDel(eventListener: OpenBLiveEventListener<SuperChatDelData>) {
        eventPublisher.subscribe(
            OpenBLiveEvent.ON_SUPER_CHAT_DEL,
            eventListener as OpenBLiveEventListener<OpenBLiveData>
        )
    }

    /**
     * 收到大航海
     */
    fun onGuard(eventListener: OpenBLiveEventListener<GuardData>) {
        eventPublisher.subscribe(OpenBLiveEvent.ON_GUARD, eventListener as OpenBLiveEventListener<OpenBLiveData>)
    }
}
