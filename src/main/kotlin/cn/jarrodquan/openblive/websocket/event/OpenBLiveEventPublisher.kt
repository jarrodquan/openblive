package cn.jarrodquan.openblive.websocket.event

import cn.jarrodquan.openblive.websocket.data.OpenBLiveData
import java.util.*

class OpenBLiveEventPublisher<T : OpenBLiveData> {
    private val eventListeners: EnumMap<OpenBLiveEvent, ArrayList<OpenBLiveEventListener<T>>> =
        EnumMap(OpenBLiveEvent::class.java)

    fun subscribe(event: OpenBLiveEvent, eventListener: OpenBLiveEventListener<T>) {
        var targetListeners = eventListeners[event]
        if (targetListeners == null) {
            targetListeners = ArrayList()
            eventListeners[event] = targetListeners
        }

        targetListeners.add(eventListener)
    }

    fun publish(event: OpenBLiveEvent, data: T) {
        val targetListeners = eventListeners[event]
        if (targetListeners != null) {
            for (eventListener in targetListeners) {
                eventListener.handle(data)
            }
        }
    }
}
