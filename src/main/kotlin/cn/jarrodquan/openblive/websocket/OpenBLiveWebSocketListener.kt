package cn.jarrodquan.openblive.websocket

import cn.jarrodquan.openblive.client.data.AppStartData
import cn.jarrodquan.openblive.util.OpenBLiveHeartbeatTimer
import cn.jarrodquan.openblive.websocket.common.OpenBLiveCMD.*
import cn.jarrodquan.openblive.websocket.data.*
import cn.jarrodquan.openblive.websocket.event.OpenBLiveEvent
import cn.jarrodquan.openblive.websocket.event.OpenBLiveEventPublisher
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import okio.ByteString.Companion.toByteString
import org.slf4j.LoggerFactory
import java.util.concurrent.ScheduledFuture

class OpenBLiveWebSocketListener(
    private val appStartData: AppStartData,
    private val eventPublisher: OpenBLiveEventPublisher<OpenBLiveData>
) : WebSocketListener() {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(OpenBLiveWebSocketListener::class.java)
    }

    private lateinit var heartbeatScheduleFuture: ScheduledFuture<*>

    override fun onOpen(webSocket: WebSocket, response: Response) {
        //发送auth_body
        LOGGER.debug("已建立连接")
        logResponse(response)

        val authPacket = PacketEncoder.buildAuthPacket(appStartData.websocketInfo.authBody)
        LOGGER.debug("authPacket: {}", authPacket)
        webSocket.send(authPacket.toByteString())
        LOGGER.debug("已发送AUTH包")

        heartbeatScheduleFuture = OpenBLiveHeartbeatTimer.schedule(30) {
            val heartbeatPacket = PacketEncoder.buildHeartbeatPacket()
            webSocket.send(heartbeatPacket.toByteString())

            LOGGER.debug("WebSocket心跳已发送")
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        LOGGER.error("拦截到未处理的异常", t)
        response?.let { logResponse(it) }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        LOGGER.debug("连接正在关闭：{} - {}", code, reason)

        //停止心跳
        heartbeatScheduleFuture.cancel(true)
        LOGGER.debug("已触发停止心跳")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        LOGGER.debug("连接已关闭：{} - {}", code, reason)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        LOGGER.debug("字节新消息：{}", bytes.utf8())

        try {
            val response = PacketDecoder.decode(bytes)
            response?.let {
                when (it.cmd) {
                    LIVE_OPEN_PLATFORM_DM -> eventPublisher.publish(OpenBLiveEvent.ON_DANMAKU, it.data as DmData)
                    LIVE_OPEN_PLATFORM_SEND_GIFT -> eventPublisher.publish(
                        OpenBLiveEvent.ON_GIFT,
                        it.data as SendGiftData
                    )

                    LIVE_OPEN_PLATFORM_SUPER_CHAT -> eventPublisher.publish(
                        OpenBLiveEvent.ON_SUPER_CHAT,
                        it.data as SuperChatData
                    )

                    LIVE_OPEN_PLATFORM_SUPER_CHAT_DEL -> eventPublisher.publish(
                        OpenBLiveEvent.ON_SUPER_CHAT_DEL,
                        it.data as SuperChatDelData
                    )

                    LIVE_OPEN_PLATFORM_GUARD -> eventPublisher.publish(OpenBLiveEvent.ON_GUARD, it.data as GuardData)
                }
            }
        } catch (exception: Exception) {
            LOGGER.error("处理新消息时发生异常", exception)
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        LOGGER.debug("新消息：{}", text)
    }

    private fun logResponse(response: Response) {
        LOGGER.debug(
            """
                
                状态码：${response.code}
                请求头：${response.headers}
                内容：${response.body?.string()}
                ${response.isSuccessful}
            """.trimIndent()
        )
    }
}
