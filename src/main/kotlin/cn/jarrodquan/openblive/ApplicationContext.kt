package cn.jarrodquan.openblive

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ApplicationContext {
    val OBJECT_MAPPER = ObjectMapper()

    init {
        OBJECT_MAPPER.registerModule(KotlinModule.Builder().build())
        OBJECT_MAPPER.registerModule(Jdk8Module())
        OBJECT_MAPPER.registerModule(JavaTimeModule())
    }
}
