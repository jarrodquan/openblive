package cn.jarrodquan.openblive.client.common

import cn.jarrodquan.openblive.ApplicationContext
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink

@JsonIgnoreProperties("oneShot", "duplex")
abstract class OpenBLiveRequestBody : RequestBody() {
    fun jsonString(): String {
        return ApplicationContext.OBJECT_MAPPER.writeValueAsString(this)
    }

    override fun contentType(): MediaType? {
        return "application/json".toMediaType()
    }

    override fun writeTo(sink: BufferedSink) {
        val jsonBytes = jsonString().toByteArray()
        sink.write(jsonBytes, 0, jsonBytes.size)
    }
}
