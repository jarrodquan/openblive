package cn.jarrodquan.openblive.websocket.common

class OpenBLivePacketEnums {
    companion object {
        /**
         * Header的长度，固定为16
         */
        const val HEADER_LENGTH: Short = 16

        /**
         * 未压缩版本
         */
        const val VERSION_UNCOMPRESSED: Short = 0

        /**
         * 已压缩版本，需要解压
         */
        const val VERSION_COMPRESSED: Short = 2

        /**
         * 保留字段，可以忽略
         */
        const val SEQUENCE_ID: Int = 0

        /**
         * Body最大长度
         */
        const val BODY_MAX_LENGTH: Int = 2048
    }
}
