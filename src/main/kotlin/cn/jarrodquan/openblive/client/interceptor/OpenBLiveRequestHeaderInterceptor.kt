package cn.jarrodquan.openblive.client.interceptor

import cn.jarrodquan.openblive.client.common.AuthorizationRequestHeader
import cn.jarrodquan.openblive.client.common.AuthorizationRequestHeaders
import cn.jarrodquan.openblive.client.common.OpenBLiveRequestBody
import okhttp3.Interceptor
import okhttp3.Response
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory

class OpenBLiveRequestHeaderInterceptor : Interceptor {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(OpenBLiveRequestHeaderInterceptor::class.java)
    }

    private val signatureBuilder = object : SignatureBuildable {}

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBody = request.body
        if (requestBody !is OpenBLiveRequestBody) {
            return chain.proceed(request)
        }

        val content = requestBody.jsonString()
        LOGGER.debug("请求主体：{}", content)

        val contentMd5 = DigestUtils.md5Hex(content)

        val requestHeader = AuthorizationRequestHeader(contentMd5 = contentMd5)
        val signature = signatureBuilder.build(requestHeader)

        val finalRequestHeader = requestHeader.copy(authorization = signature)
        LOGGER.debug("请求头: {}", finalRequestHeader)

        val finalRequest = chain.request().newBuilder()
            .header(AuthorizationRequestHeaders.ACCEPT, finalRequestHeader.accept)
            .header(AuthorizationRequestHeaders.CONTENT_TYPE, finalRequestHeader.contentType)
            .header(AuthorizationRequestHeaders.CONTENT_MD5, finalRequestHeader.contentMd5)
            .header(AuthorizationRequestHeaders.TIMESTAMP, finalRequestHeader.timestamp)
            .header(AuthorizationRequestHeaders.SIGNATURE_METHOD, finalRequestHeader.signatureMethod)
            .header(AuthorizationRequestHeaders.SIGNATURE_NONCE, finalRequestHeader.signatureNonce)
            .header(AuthorizationRequestHeaders.ACCESS_KEY_ID, finalRequestHeader.accessKeyId)
            .header(AuthorizationRequestHeaders.SIGNATURE_VERSION, finalRequestHeader.signatureVersion)
            .header(AuthorizationRequestHeaders.AUTHORIZATION, finalRequestHeader.authorization)
            .build()
        return chain.proceed(finalRequest)
    }
}
