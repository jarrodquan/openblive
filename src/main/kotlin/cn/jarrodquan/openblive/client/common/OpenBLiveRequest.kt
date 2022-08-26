package cn.jarrodquan.openblive.client.common

import okhttp3.Request

abstract class OpenBLiveRequest<T : OpenBLiveRequestBody> {
    abstract fun build(body: T): Request
}
