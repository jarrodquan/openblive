package cn.jarrodquan.openblive.websocket.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 付费留言
 */
data class SuperChatData(
    /**
     * 直播间id
     */
    @JsonProperty("room_id")
    val roomId: Long,
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
    /**
     * 留言id(风控场景下撤回留言需要)
     */
    @JsonProperty("message_id")
    val messageId: Long,
    /**
     * 留言内容
     */
    val message: String,
    /**
     * 支付金额(元)
     */
    val rmb: Long,
    /**
     * 赠送时间秒级
     */
    val timestamp: Long,
    /**
     * 生效开始时间
     */
    @JsonProperty("start_time")
    val startTime: Long,
    /**
     * 生效结束时间
     */
    @JsonProperty("end_time")
    val endTime: Long,
    /**
     * 对应房间大航海等级
     */
    @JsonProperty("guard_level")
    val guardLevel: Long,
    /**
     * 实际送礼人的勋章信息
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
     * 消息唯一id
     */
    @JsonProperty("msg_id")
    val msgId: String
) : OpenBLiveData()
