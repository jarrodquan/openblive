package cn.jarrodquan.openblive.client

import cn.jarrodquan.openblive.ApplicationContext
import cn.jarrodquan.openblive.client.data.AppBatchHeartbeatData
import cn.jarrodquan.openblive.client.data.AppStartData
import cn.jarrodquan.openblive.client.interceptor.OpenBLiveRequestHeaderInterceptor
import cn.jarrodquan.openblive.client.interceptor.OpenBLiveResponseInterceptor
import cn.jarrodquan.openblive.client.request.AppBatchHeartbeatRequest
import cn.jarrodquan.openblive.client.request.AppEndRequest
import cn.jarrodquan.openblive.client.request.AppHeartbeatRequest
import cn.jarrodquan.openblive.client.request.AppStartRequest
import okhttp3.OkHttpClient
import okhttp3.Response
import org.slf4j.LoggerFactory

class DefaultOpenBLiveClient(
    override val configuration: OpenBLiveClientConfiguration
) : OpenBLiveClient() {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(DefaultOpenBLiveClient::class.java)
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(OpenBLiveRequestHeaderInterceptor())
        .addInterceptor(OpenBLiveResponseInterceptor())
        .build()

    override fun startApp(code: String): AppStartData? {
        val request = AppStartRequest().build(AppStartRequest.Body(code, configuration.appId))
        val response = httpClient.newCall(request).execute()
        return parseResponse(response, AppStartData::class.java)
    }

    override fun endApp(appId: Long, gameId: String): Any? {
        val request = AppEndRequest().build(AppEndRequest.Body(appId, gameId))
        val response = httpClient.newCall(request).execute()
        return parseResponse(response, Any::class.java)
    }

    override fun heartbeat(gameId: String): Any? {
        val request = AppHeartbeatRequest().build(AppHeartbeatRequest.Body(gameId))
        val response = httpClient.newCall(request).execute()
        return parseResponse(response, Any::class.java)
    }

    override fun batchHeartbeat(gameIds: List<String>): AppBatchHeartbeatData? {
        val request = AppBatchHeartbeatRequest().build(AppBatchHeartbeatRequest.Body(gameIds))
        val response = httpClient.newCall(request).execute()
        return parseResponse(response, AppBatchHeartbeatData::class.java)
    }

    private fun <T> parseResponse(response: Response, dataClass: Class<*>): T? {
        val responseBody = response.body!!.string()
        val jsonNode = ApplicationContext.OBJECT_MAPPER.readTree(responseBody)
        val dataJsonString = jsonNode.at("/data").toPrettyString()

        return ApplicationContext.OBJECT_MAPPER.readValue(dataJsonString, dataClass) as T
    }
}
