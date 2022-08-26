package cn.jarrodquan.openblive.client

data class OpenBLiveClientConfiguration(
    /**
     * 请求协议头
     */
    val protocol: String = "https",
    /**
     * 正式环境域名
     */
    val domain: String = "live-open.biliapi.com",
    /**
     * 项目ID
     */
    val appId: Long,
    /**
     * 密钥ID
     */
    val accessKeyId: String,
    /**
     * 密钥
     */
    val accessKeySecret: String
) {
    val url: String
        get() = "${this.protocol}://${this.domain}"
}
