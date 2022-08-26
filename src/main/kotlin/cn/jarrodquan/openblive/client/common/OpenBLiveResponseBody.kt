package cn.jarrodquan.openblive.client.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class OpenBLiveResponseBody(
    val code: Int,
    val message: String,
    @JsonProperty("request_id")
    val requestId: String,
    @JsonIgnore
    val data: Any?
)
