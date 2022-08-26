package cn.jarrodquan.openblive.util

import ch.qos.logback.core.util.ExecutorServiceUtil
import org.slf4j.LoggerFactory
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object OpenBLiveHeartbeatTimer {
    private val LOGGER = LoggerFactory.getLogger(OpenBLiveHeartbeatTimer::class.java)
    private val SCHEDULE_SERVICE = ExecutorServiceUtil.newScheduledExecutorService()

    /**
     * @param interval 间隔，单位：秒
     * @param runnable 要执行的操作
     */
    fun schedule(interval: Long, runnable: Runnable): ScheduledFuture<*> {
        return SCHEDULE_SERVICE.scheduleWithFixedDelay(
            {
                try {
                    runnable.run()
                } catch (exception: Exception) {
                    LOGGER.error("异常已丢弃", exception)
                }
            },
            0,
            interval,
            TimeUnit.SECONDS
        )
    }
}
