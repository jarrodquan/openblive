package cn.jarrodquan.openblive.client.common

enum class ErrorCode(
    val code: Int,
    val description: String,
    val remark: String
) {
    _400(-400, "参数缺失", "请检查必填参数"),

    /**
     * 参数错误
     *
     * 请检查必填参数，参数大小限制
     */
    _4000(4000, "参数错误", "请检查必填参数，参数大小限制"),

    /**
     * 应用无效
     *
     * 请检查header的x-bili-accesskeyid是否为空，或者有效
     */
    _4001(4001, "应用无效", "请检查header的x-bili-accesskeyid是否为空，或者有效"),

    /**
     * 签名异常
     *
     * 请检查header的Authorization
     */
    _4002(4002, "签名异常", "请检查header的Authorization"),

    /**
     * 请求过期
     *
     * 请检查header的x-bili-timestamp
     */
    _4003(4003, "请求过期", "请检查header的x-bili-timestamp"),

    /**
     * 重复请求
     *
     * 请检查header的x-bili-nonce
     */
    _4004(4004, "重复请求", "请检查header的x-bili-nonce"),

    /**
     * 签名method异常
     *
     * 请检查header的x-bili-signature-method
     */
    _4005(4005, "签名method异常", "请检查header的x-bili-signature-method"),

    /**
     * 版本异常
     *
     * 请检查header的x-bili-version
     */
    _4006(4006, "版本异常", "请检查header的x-bili-version"),

    /**
     * IP白名单限制
     *
     * 请确认请求服务器是否在报备的白名单内
     */
    _4007(4007, "IP白名单限制", "请确认请求服务器是否在报备的白名单内"),

    /**
     * 权限异常
     *
     * 请确认接口权限
     */
    _4008(4008, "权限异常", "请确认接口权限"),

    /**
     * 接口访问限制
     *
     * 请确认接口权限及请求频率
     */
    _4009(4009, "接口访问限制", "请确认接口权限及请求频率"),

    /**
     * 接口不存在
     *
     * 请确认请求接口url
     */
    _4010(4010, "接口不存在", "请确认请求接口url"),

    /**
     * Content-Type不为application/json
     *
     * 请检查header的Content-Type
     */
    _4011(4011, "Content-Type不为application/json", "请检查header的Content-Type"),

    /**
     * MD5校验失败
     *
     * 请检查header的x-bili-content-md5
     */
    _4012(4012, "MD5校验失败", "请检查header的x-bili-content-md5"),

    /**
     * Accept不为application/json
     *
     * 请检查header的Accept
     */
    _4013(4013, "Accept不为application/json", "请检查header的Accept"),

    /**
     * 服务异常
     *
     * 请联系B站对接同学
     */
    _5000(5000, "服务异常", "请联系B站对接同学"),

    /**
     * 请求超时
     *
     * 请求超时
     */
    _5001(5001, "请求超时", "请求超时"),

    /**
     * 内部错误
     *
     * 请联系B站对接同学
     */
    _5002(5002, "内部错误", "请联系B站对接同学"),

    /**
     * 配置错误
     *
     * 请联系B站对接同学
     */
    _5003(5003, "配置错误", "请联系B站对接同学"),

    /**
     * 房间白名单限制
     *
     * 请联系B站对接同学
     */
    _5004(5004, "房间白名单限制", "请联系B站对接同学"),

    /**
     * 房间黑名单限制
     *
     * 请联系B站对接同学
     */
    _5005(5005, "房间黑名单限制", "请联系B站对接同学"),

    /**
     * 验证码错误
     *
     * 验证码校验失败
     */
    _6000(6000, "验证码错误", "验证码校验失败"),

    /**
     * 手机号码错误
     *
     * 检查手机号码
     */
    _6001(6001, "手机号码错误", "检查手机号码"),

    /**
     * 验证码已过期
     *
     * 验证码超过规定有效期
     */
    _6002(6002, "验证码已过期", "验证码超过规定有效期"),

    /**
     * 验证码频率限制
     *
     * 检查获取验证码的频率
     */
    _6003(6003, "验证码频率限制", "检查获取验证码的频率"),

    /**
     * 不在游戏内
     *
     * 当前房间未进行互动游戏
     */
    _7000(7000, "不在游戏内", "当前房间未进行互动游戏"),

    /**
     * 请求冷却期
     *
     * 上个游戏正在结算中，建议10秒后进行重试
     */
    _7001(7001, "请求冷却期", "上个游戏正在结算中，建议10秒后进行重试"),

    /**
     * 房间重复游戏
     *
     * 当前房间正在进行游戏,无法开启下一局互动游
     */
    _7002(7002, "房间重复游戏", "当前房间正在进行游戏,无法开启下一局互动游戏"),

    /**
     * 心跳过期
     *
     * 当前game_id错误或互动游戏已关闭
     */
    _7003(7003, "心跳过期", "当前game_id错误或互动游戏已关闭"),

    /**
     * 批量心跳超过最大值
     *
     * 批量心跳单次最大值为200
     */
    _7004(7004, "批量心跳超过最大值", "批量心跳单次最大值为200"),

    /**
     * 批量心跳ID重复
     *
     * 批量心跳game_id存在重复,请检查参数
     */
    _7005(7005, "批量心跳ID重复", "批量心跳game_id存在重复,请检查参数"),

    /**
     * 身份码错误
     *
     * 请检查身份码是否正确
     */
    _7007(7007, "身份码错误", "请检查身份码是否正确"),

    /**
     * 项目无权限访问
     *
     * 确认项目ID是否正确
     */
    _8002(8002, "项目无权限访问", "确认项目ID是否正确");

    companion object {
        fun match(code: Int): ErrorCode? {
            return ErrorCode.values().find {
                it.code == code
            }
        }
    }
}