package cn.jarrodquan.openblive.websocket

import cn.jarrodquan.openblive.ApplicationContext
import cn.jarrodquan.openblive.websocket.common.OpenBLiveCMD
import cn.jarrodquan.openblive.websocket.common.OpenBLiveOperation
import cn.jarrodquan.openblive.websocket.common.OpenBLivePacketEnums
import cn.jarrodquan.openblive.websocket.common.OpenBLiveWebSocketResponse
import okio.ByteString
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.nio.ByteOrder
import java.util.zip.Inflater

object PacketDecoder {
    private val LOGGER = LoggerFactory.getLogger(PacketDecoder::class.java)
    private const val NOT_SUPPORT_COMPRESSED_BODY = "不支持压缩的body解析"

    fun decode(data: ByteString): OpenBLiveWebSocketResponse<*>? {
        val byteBuffer = data.asByteBuffer().order(ByteOrder.BIG_ENDIAN)

        val packetLength = byteBuffer.getInt(0)
        if (packetLength < 0 || packetLength > OpenBLivePacketEnums.BODY_MAX_LENGTH) {
            throw IllegalArgumentException("包体长度不对：${packetLength}/${OpenBLivePacketEnums.BODY_MAX_LENGTH}")
        }

        val headerLength = byteBuffer.getShort(4)
        if (headerLength != OpenBLivePacketEnums.HEADER_LENGTH) {
            throw IllegalArgumentException("包头长度不对：${headerLength}/${OpenBLivePacketEnums.HEADER_LENGTH}")
        }

        val version = byteBuffer.getShort(6)
        val bodyCompressed = version == OpenBLivePacketEnums.VERSION_COMPRESSED

        val operation = byteBuffer.getInt(8)
        val operationEnum = OpenBLiveOperation.match(operation)
        //val sequenceId = byteBuffer.getInt(12)

        LOGGER.debug(
            """
                
                包体长度：${packetLength} bytes
                包头长度：${headerLength} bytes
                版本：${version}
                操作：${operation} （${operationEnum?.name} - ${operationEnum?.description}）
            """.trimIndent()
        )

        val bodyLength = packetLength - OpenBLivePacketEnums.HEADER_LENGTH
        val bodyByteArray = ByteArray(bodyLength)

        byteBuffer.position(OpenBLivePacketEnums.HEADER_LENGTH.toInt())
        byteBuffer.get(bodyByteArray)

        LOGGER.debug("内容: {}", bodyByteArray.decodeToString())

        return when (operationEnum) {
            OpenBLiveOperation.OP_AUTH_REPLY -> {
                return null
            }

            OpenBLiveOperation.OP_HEARTBEAT_REPLY -> {
                return null
            }

            OpenBLiveOperation.OP_SEND_SMS_REPLY -> {
                handleSendSmsReply(bodyByteArray, bodyCompressed)
            }

            else -> throw IllegalArgumentException("不支持的Operation")
        }
    }

    private fun handleSendSmsReply(body: ByteArray, compressed: Boolean): OpenBLiveWebSocketResponse<*> {
        if (compressed) {
            //val inflatedBodyByteArray = inflateBody(body)
            throw RuntimeException(NOT_SUPPORT_COMPRESSED_BODY)
        } else {
            val jsonNode = ApplicationContext.OBJECT_MAPPER.readTree(body)
            val cmd = jsonNode.at("/cmd").toPrettyString().replace("\"", "")
            val cmdEnum = OpenBLiveCMD.match(cmd)
            if (cmdEnum != null) {
                val data = jsonNode.at("/data").toPrettyString()
                val dataObject = ApplicationContext.OBJECT_MAPPER.readValue(data, cmdEnum.dataClass)

                return OpenBLiveWebSocketResponse(cmdEnum, dataObject)
            } else {
                throw RuntimeException("不支持的CMD")
            }
        }
    }

    private fun inflateBody(body: ByteArray): ByteArray {
        val zipInflater = Inflater()
        zipInflater.reset()
        zipInflater.setInput(body)

        val byteArrayOutputStream = ByteArrayOutputStream(body.size)

        val tempBuffer = ByteArray(1024)
        while (!zipInflater.finished()) {
            val sizeOfUncompressedBytes = zipInflater.inflate(tempBuffer)
            byteArrayOutputStream.write(tempBuffer, 0, sizeOfUncompressedBytes)
        }

        return byteArrayOutputStream.toByteArray()
    }
}
