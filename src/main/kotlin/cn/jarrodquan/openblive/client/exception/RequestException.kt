package cn.jarrodquan.openblive.client.exception

import cn.jarrodquan.openblive.client.common.ErrorCode

class RequestException(errorCode: ErrorCode) : RuntimeException("${errorCode.description} - ${errorCode.remark}")
