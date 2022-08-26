package cn.jarrodquan.openblive.client.request

import cn.jarrodquan.openblive.OpenBLive
import cn.jarrodquan.openblive.client.common.OpenBLiveRequest
import cn.jarrodquan.openblive.client.common.OpenBLiveRequestBody
import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.Request

class AppStartRequest : OpenBLiveRequest<AppStartRequest.Body>() {
    override fun build(body: Body): Request {
        return Request.Builder()
            .url("${OpenBLive.client().configuration.url}/v2/app/start")
            .post(body)
            .build()
    }

    data class Body(
        /**
         * 主播身份码
         */
        val code: String,
        /**
         * 项目ID
         */
        @JsonProperty("app_id")
        val appId: Long
    ) : OpenBLiveRequestBody()
}
