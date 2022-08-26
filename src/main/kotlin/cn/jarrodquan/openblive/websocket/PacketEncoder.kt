package cn.jarrodquan.openblive.websocket

import cn.jarrodquan.openblive.websocket.common.OpenBLiveOperation
import cn.jarrodquan.openblive.websocket.common.OpenBLivePacketEnums
import java.nio.ByteBuffer
import java.nio.ByteOrder

object PacketEncoder {
    fun buildAuthPacket(authBody: String): ByteArray {
        val bodyPart = authBody.toByteArray()
        val packetLength = bodyPart.size + OpenBLivePacketEnums.HEADER_LENGTH

        return ByteBuffer.allocate(packetLength)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt(packetLength)
            .putShort(OpenBLivePacketEnums.HEADER_LENGTH)
            .putShort(OpenBLivePacketEnums.VERSION_UNCOMPRESSED)
            .putInt(OpenBLiveOperation.OP_AUTH.value)
            .putInt(OpenBLivePacketEnums.SEQUENCE_ID)
            .put(bodyPart)
            .array()
    }

    fun buildHeartbeatPacket(): ByteArray {
        return ByteBuffer.allocate(OpenBLivePacketEnums.HEADER_LENGTH.toInt())
            .order(ByteOrder.BIG_ENDIAN)
            .putInt(OpenBLivePacketEnums.HEADER_LENGTH.toInt())
            .putShort(OpenBLivePacketEnums.HEADER_LENGTH)
            .putShort(OpenBLivePacketEnums.VERSION_UNCOMPRESSED)
            .putInt(OpenBLiveOperation.OP_HEARTBEAT.value)
            .putInt(OpenBLivePacketEnums.SEQUENCE_ID)
            .array()
    }
}
