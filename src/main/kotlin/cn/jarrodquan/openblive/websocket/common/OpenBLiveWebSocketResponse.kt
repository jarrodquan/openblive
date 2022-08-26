package cn.jarrodquan.openblive.websocket.common

data class OpenBLiveWebSocketResponse<T>(
    val cmd: OpenBLiveCMD,
    val data: T?
)
