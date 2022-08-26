package cn.jarrodquan.openblive.client.interceptor

import cn.jarrodquan.openblive.ApplicationContext
import cn.jarrodquan.openblive.client.common.ErrorCode
import cn.jarrodquan.openblive.client.common.OpenBLiveRequestBody
import cn.jarrodquan.openblive.client.common.OpenBLiveResponseBody
import cn.jarrodquan.openblive.client.exception.RequestException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.slf4j.LoggerFactory

class OpenBLiveResponseInterceptor : Interceptor {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(OpenBLiveResponseInterceptor::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBody = request.body
        if (requestBody !is OpenBLiveRequestBody) {
            return chain.proceed(request)
        }

        val response = chain.proceed(request)

        if (response.code == 200) {
            val responseBody = response.body!!.string()
            LOGGER.debug("响应主体：{}", responseBody)

            val openBLiveResponseBody = ApplicationContext.OBJECT_MAPPER.readValue(
                responseBody,
                OpenBLiveResponseBody::class.java
            )

            val matchedErrorCode = ErrorCode.match(openBLiveResponseBody.code)
            if (matchedErrorCode != null) {
                throw RequestException(matchedErrorCode)
            }

            return response.newBuilder()
                .body(responseBody.toResponseBody())
                .build()
        } else {
            LOGGER.error(
                """
                    
                    状态码：${response.code}
                    内容类型：${response.headers("Content-Type")}
                    内容长度：${response.headers("Content-Length")}
                    内容：${response.body?.string()}
                """.trimIndent()
            )
            throw RuntimeException("无法处理的HTTP状态码")
        }
    }
}
