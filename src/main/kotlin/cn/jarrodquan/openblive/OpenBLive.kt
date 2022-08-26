package cn.jarrodquan.openblive

import cn.jarrodquan.openblive.client.DefaultOpenBLiveClient
import cn.jarrodquan.openblive.client.OpenBLiveClient
import cn.jarrodquan.openblive.client.OpenBLiveClientConfiguration
import java.io.InputStream
import java.util.*

object OpenBLive {
    private var openBLiveClient: OpenBLiveClient? = null

    fun client(): OpenBLiveClient {
        return openBLiveClient!!
    }

    fun initialize() {
        initialize("openblive.properties")
    }

    fun initialize(configFile: String) {
        synchronized(OpenBLive::class.java) {
            if (openBLiveClient == null) {
                val configuration = loadConfiguration(configFile)
                openBLiveClient = DefaultOpenBLiveClient(configuration)
            }
        }
    }

    fun initialize(inputStream: InputStream) {
        synchronized(OpenBLive::class.java) {
            if (openBLiveClient == null) {
                val configuration = loadConfiguration(inputStream)
                openBLiveClient = DefaultOpenBLiveClient(configuration)
            }
        }
    }

    private fun loadConfiguration(configFile: String = "openblive.properties"): OpenBLiveClientConfiguration {
        val config = Properties()
        config.load(this.javaClass.classLoader.getResourceAsStream(configFile))

        return readProperties(config)
    }

    private fun loadConfiguration(inputStream: InputStream): OpenBLiveClientConfiguration {
        val config = Properties()
        config.load(inputStream)

        return readProperties(config)
    }

    private fun readProperties(config: Properties): OpenBLiveClientConfiguration {
        val appId = config.getProperty("openblive.app.id", null)
        if (appId == null || appId.isBlank()) {
            throw IllegalArgumentException("项目ID缺失")
        }

        val accessKeyId = config.getProperty("openblive.access.key.id", null)
        if (accessKeyId == null || accessKeyId.isBlank()) {
            throw IllegalArgumentException("密钥ID缺失")
        }

        val accessKeySecret = config.getProperty("openblive.access.key.secret", null)
        if (accessKeySecret == null || accessKeySecret.isBlank()) {
            throw IllegalArgumentException("密钥缺失")
        }

        return OpenBLiveClientConfiguration(
            protocol = config.getProperty("openblive.protocol", "https"),
            domain = config.getProperty("openblive.domain", "live-open.biliapi.com"),
            appId = appId.toLong(),
            accessKeyId = accessKeyId,
            accessKeySecret = accessKeySecret
        )
    }
}
