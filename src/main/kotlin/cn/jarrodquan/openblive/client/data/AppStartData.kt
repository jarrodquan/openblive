package cn.jarrodquan.openblive.client.data

import com.fasterxml.jackson.annotation.JsonProperty

data class AppStartData(
    /**
     * 场次信息
     */
    @JsonProperty("game_info")
    val gameInfo: GameInfo,
    /**
     * 长连信息
     */
    @JsonProperty("websocket_info")
    val websocketInfo: WebsocketInfo,
    /**
     * 长连信息
     */
    @JsonProperty("anchor_info")
    val anchorInfo: AnchorInfo
) {
    data class GameInfo(
        /**
         * 场次ID
         */
        @JsonProperty("game_id")
        val gameId: String
    )

    data class WebsocketInfo(
        /**
         * 长连使用的请求json体 第三方无需关注内容,建立长连时使用即可
         */
        @JsonProperty("auth_body")
        val authBody: String,
        /**
         * wss 长连地址
         */
        @JsonProperty("wss_link")
        val wssLink: List<String>
    )

    data class AnchorInfo(
        /**
         * 主播房间号
         */
        @JsonProperty("room_id")
        val roomId: Long,
        /**
         * 主播昵称
         */
        val uname: String,
        /**
         * 主播头像
         */
        val uface: String,
        /**
         * 主播uid
         */
        val uid: Long
    )
}
