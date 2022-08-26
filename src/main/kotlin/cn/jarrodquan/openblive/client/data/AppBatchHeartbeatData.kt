package cn.jarrodquan.openblive.client.data

import com.fasterxml.jackson.annotation.JsonProperty

data class AppBatchHeartbeatData(
    /**
     * 心跳失败的id
     */
    @JsonProperty("failed_game_ids")
    val failedGameIds: List<String>
)
