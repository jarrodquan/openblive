package cn.jarrodquan.openblive.client.request

import cn.jarrodquan.openblive.OpenBLive
import cn.jarrodquan.openblive.client.common.OpenBLiveRequest
import cn.jarrodquan.openblive.client.common.OpenBLiveRequestBody
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.Request

class AppBatchHeartbeatRequest : OpenBLiveRequest<AppBatchHeartbeatRequest.Body>() {
    override fun build(body: Body): Request {
        return Request.Builder()
            .url("${OpenBLive.client().configuration.url}/v2/app/batchHeartbeat")
            .post(body)
            .build()
    }

    data class Body(
        /**
         * 场次ID
         */
        @JsonProperty("game_ids")
        val gameIds: List<String>
    ) : OpenBLiveRequestBody()
}
