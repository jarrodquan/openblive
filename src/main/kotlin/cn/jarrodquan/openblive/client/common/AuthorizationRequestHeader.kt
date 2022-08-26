package cn.jarrodquan.openblive.client.common

import cn.jarrodquan.openblive.OpenBLive
import java.time.Instant
import java.util.*

data class AuthorizationRequestHeader(
    val accept: String = "application/json",
    val contentType: String = "application/json",
    val contentMd5: String,
    val timestamp: String = Instant.now().epochSecond.toString(),
    val signatureMethod: String = "HMAC-SHA256",
    val signatureNonce: String = UUID.randomUUID().toString(),
    val accessKeyId: String = OpenBLive.client().configuration.accessKeyId,
    val signatureVersion: String = "1.0",
    val authorization: String = ""
)
