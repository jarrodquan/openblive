package cn.jarrodquan.openblive.websocket.event

import cn.jarrodquan.openblive.websocket.data.OpenBLiveData

interface OpenBLiveEventListener<T : OpenBLiveData> {
    fun handle(data: T)
}
