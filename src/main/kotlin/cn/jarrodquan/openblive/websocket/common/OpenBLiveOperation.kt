package cn.jarrodquan.openblive.websocket.common

enum class OpenBLiveOperation(
    val value: Int,
    val description: String
) {
    OP_HEARTBEAT(2, "客户端发送的心跳包(30秒发送一次)"),
    OP_HEARTBEAT_REPLY(3, "服务器收到心跳包的回复"),
    OP_SEND_SMS_REPLY(5, "服务器推送的弹幕消息包"),
    OP_AUTH(7, "客户端发送的鉴权包(客户端发送的第一个包)"),
    OP_AUTH_REPLY(8, "服务器收到鉴权包后的回复");

    companion object {
        fun match(value: Int): OpenBLiveOperation? {
            return OpenBLiveOperation.values().find {
                it.value == value
            }
        }
    }
}
