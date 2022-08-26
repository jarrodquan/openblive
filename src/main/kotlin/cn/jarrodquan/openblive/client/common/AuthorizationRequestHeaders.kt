package cn.jarrodquan.openblive.client.common

class AuthorizationRequestHeaders {
    companion object {
        const val ACCEPT = "Accept"
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_MD5 = "x-bili-content-md5"
        const val TIMESTAMP = "x-bili-timestamp"
        const val SIGNATURE_METHOD = "x-bili-signature-method"
        const val SIGNATURE_NONCE = "x-bili-signature-nonce"
        const val ACCESS_KEY_ID = "x-bili-accesskeyid"
        const val SIGNATURE_VERSION = "x-bili-signature-version"
        const val AUTHORIZATION = "Authorization"
    }
}
