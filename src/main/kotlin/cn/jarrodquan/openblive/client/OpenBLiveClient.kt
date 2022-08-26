package cn.jarrodquan.openblive.client

import cn.jarrodquan.openblive.client.data.AppBatchHeartbeatData
import cn.jarrodquan.openblive.client.data.AppStartData

abstract class OpenBLiveClient {
    /**
     * 客户端配置
     */
    abstract val configuration: OpenBLiveClientConfiguration

    /**
     * 开启应用
     * @param code 主播身份码
     */
    abstract fun startApp(code: String): AppStartData?

    /**
     * 关闭应用
     *
     * @param appId 项目ID
     * @param gameId 场次ID
     */
    abstract fun endApp(appId: Long, gameId: String): Any?

    /**
     * 发送心跳
     *
     * @param gameId 场次ID
     */
    abstract fun heartbeat(gameId: String): Any?

    /**
     * 批量发送心跳
     *
     * @param gameIds 场次ID集合
     */
    abstract fun batchHeartbeat(gameIds: List<String>): AppBatchHeartbeatData?
}
