package cn.jarrodquan.openblive

import cn.jarrodquan.openblive.websocket.OpenBLiveWebSocket
import cn.jarrodquan.openblive.websocket.data.DmData
import cn.jarrodquan.openblive.websocket.event.OpenBLiveEventListener
import org.junit.jupiter.api.Test

class OpenBLiveClientTests {
    companion object {
        private const val CODE = "******"
        private const val APP_ID = 0
    }

    @Test
    fun test() {
        OpenBLive.initialize()
        val openBLiveClient = OpenBLive.client()
        val appStartData = openBLiveClient.startApp(CODE)!!

        val openBLiveWebSocket = OpenBLiveWebSocket(appStartData)
        openBLiveWebSocket.onDanmaku(object : OpenBLiveEventListener<DmData> {
            override fun handle(data: DmData) {
                println("第一次处理：${data.uname} - ${data.msg}")
            }
        })
        openBLiveWebSocket.onDanmaku(object : OpenBLiveEventListener<DmData> {
            override fun handle(data: DmData) {
                println("第二次处理：${data.uname} - ${data.msg}")
            }
        })
        openBLiveWebSocket.connect()

        Thread.sleep(60000)

        openBLiveWebSocket.disconnect()

        Thread.sleep(3000)
    }
}
