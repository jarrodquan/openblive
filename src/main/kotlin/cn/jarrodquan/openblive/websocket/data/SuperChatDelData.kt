package cn.jarrodquan.openblive.websocket.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 付费留言下线
 */
data class SuperChatDelData(
    /**
     * 直播间id
     */
    @JsonProperty("room_id")
    val roomId: Long,
    /**
     * 留言id
     */
    @JsonProperty("message_ids")
    val messageIds: List<Long>,
    /**
     * 消息唯一id
     */
    @JsonProperty("msgId")
    val msgId: String
) : OpenBLiveData()
