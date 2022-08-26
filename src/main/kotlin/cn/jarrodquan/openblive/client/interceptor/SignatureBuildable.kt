package cn.jarrodquan.openblive.client.interceptor

import cn.jarrodquan.openblive.OpenBLive
import cn.jarrodquan.openblive.client.common.AuthorizationRequestHeader
import cn.jarrodquan.openblive.client.common.AuthorizationRequestHeaders
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.digest.HmacUtils

interface SignatureBuildable {
    fun build(requestHeader: AuthorizationRequestHeader): String {
        val signature = """${AuthorizationRequestHeaders.ACCESS_KEY_ID}:${requestHeader.accessKeyId}
${AuthorizationRequestHeaders.CONTENT_MD5}:${requestHeader.contentMd5}
${AuthorizationRequestHeaders.SIGNATURE_METHOD}:${requestHeader.signatureMethod}
${AuthorizationRequestHeaders.SIGNATURE_NONCE}:${requestHeader.signatureNonce}
${AuthorizationRequestHeaders.SIGNATURE_VERSION}:${requestHeader.signatureVersion}
${AuthorizationRequestHeaders.TIMESTAMP}:${requestHeader.timestamp}"""

        val accessKeySecret = OpenBLive.client().configuration.accessKeySecret
        val sha256Mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, accessKeySecret.toByteArray())
        val encodedSignature = sha256Mac.doFinal(signature.toByteArray())

        return Hex.encodeHexString(encodedSignature)
    }
}
