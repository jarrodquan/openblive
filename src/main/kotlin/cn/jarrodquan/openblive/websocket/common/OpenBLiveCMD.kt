package cn.jarrodquan.openblive.websocket.common

import cn.jarrodquan.openblive.websocket.data.*

enum class OpenBLiveCMD(
    val description: String,
    val dataClass: Class<*>
) {
    LIVE_OPEN_PLATFORM_DM("获取弹幕信息", DmData::class.java),
    LIVE_OPEN_PLATFORM_SEND_GIFT("获取礼物信息", SendGiftData::class.java),
    LIVE_OPEN_PLATFORM_SUPER_CHAT("获取付费留言", SuperChatData::class.java),
    LIVE_OPEN_PLATFORM_SUPER_CHAT_DEL("付费留言下线", SuperChatDelData::class.java),
    LIVE_OPEN_PLATFORM_GUARD("付费大航海", GuardData::class.java);

    companion object {
        fun match(name: String): OpenBLiveCMD? {
            return OpenBLiveCMD.values().find {
                it.name == name
            }
        }
    }
}
