package cn.jarrodquan.openblive.websocket.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 弹幕信息
 */
data class DmData(
    /**
     * 用户昵称
     */
    val uname: String,
    /**
     * 用户UID
     */
    val uid: Long,
    /**
     * 用户头像
     */
    val uface: String,
    /**
     * 弹幕发送时间秒级时间戳
     */
    val timestamp: Long,
    /**
     * 弹幕接收的直播间
     */
    @JsonProperty("room_id")
    val roomId: Long,
    /**
     * 弹幕内容
     */
    val msg: String,
    /**
     * 消息唯一id
     */
    @JsonProperty("msg_id")
    val msgId: String,
    /**
     * 对应房间大航海等级
     */
    @JsonProperty("guard_level")
    val guardLevel: Long,
    /**
     * 该房间粉丝勋章佩戴情况
     */
    @JsonProperty("fans_medal_wearing_status")
    val fansMedalWearingStatus: Boolean,
    /**
     * 粉丝勋章名
     */
    @JsonProperty("fans_medal_name")
    val fansMedalName: String,
    /**
     * 对应房间勋章信息
     */
    @JsonProperty("fans_medal_level")
    val fansMedalLevel: Long
) : OpenBLiveData()
