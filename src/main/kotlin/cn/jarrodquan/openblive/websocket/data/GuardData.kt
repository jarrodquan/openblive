package cn.jarrodquan.openblive.websocket.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 大航海
 */
data class GuardData(
    /**
     * 用户信息
     */
    @JsonProperty("user_info")
    val userInfo: UserInfo,
    /**
     * 大航海等级
     */
    @JsonProperty("guard_level")
    val guardLevel: Long,
    /**
     * 大航海数量
     */
    @JsonProperty("guard_num")
    val guardNum: Long,
    /**
     * 大航海单位
     */
    @JsonProperty("guard_unit")
    val guardUnit: String,
    /**
     * 粉丝勋章等级
     */
    @JsonProperty("fans_medal_level")
    val fansMedalLevel: Long,
    /**
     * 粉丝勋章名
     */
    @JsonProperty("fans_medal_name")
    val fansMedalName: String,
    /**
     * 该房间粉丝勋章佩戴情况
     */
    @JsonProperty("fans_medal_wearing_status")
    val fansMedalWearingStatus: Boolean,
    /**
     * 	房间号
     */
    @JsonProperty("room_id")
    val roomId: Long,
    /**
     * 消息唯一id
     */
    @JsonProperty("msg_id")
    val msgId: String,
    /**
     * 上舰时间秒级时间戳
     */
    val timestamp: Long,
) : OpenBLiveData() {
    data class UserInfo(
        /**
         * 购买用户UID
         */
        val uid: Long,
        /**
         * 购买的用户昵称
         */
        val uname: String,
        /**
         * 购买用户头像
         */
        val uface: String,
    )
}
