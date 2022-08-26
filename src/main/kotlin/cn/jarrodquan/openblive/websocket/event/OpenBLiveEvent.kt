package cn.jarrodquan.openblive.websocket.event

enum class OpenBLiveEvent(
    val description: String
) {
    ON_DANMAKU("收到弹幕"),
    ON_GIFT("收到礼物"),
    ON_SUPER_CHAT("收到付费留言"),
    ON_SUPER_CHAT_DEL("付费留言下线通知"),
    ON_GUARD("收到大航海")
}
