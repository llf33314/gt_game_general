package com.gt.game.common.enums;

/**
 * 响应成功Code 类
 * Created by psr on 2017/8/24 0024.
 */
public enum ResponseEnums {

    SUCCESS(100, "请求成功"),
    // 200+针对请求失败
    ERROR(200, "请求失败"),
    LOGIN(201, "请先登录"),
    // 300+针对统一错误
    DIFF_USER(301, "无权操作的信息"),
    // 400+针对自己的错误
    DEMO_HAS(401, "信息已存在"), // 定义的demo特定返回参数
    DIFF_NUM(501, "已达到本版本最多新增个数，请升级版本"),
    DIFF_PayRefund(601, "退款失败"),

    //360全景响应
    PANORAMA_HAS(401,"没有找到订单!"),
    PANORAMA_HAS1(402,"没有找到活动!"),

    //推广海报响应
    POSTER_HAS(401,"没有找到对象!"),

    //微名片响应
    MICROCARD_HAS(401,"没有找到对象!"),

    //邀请函响应
    INVITE_HAS(401,"没有找到对象"),
    INVITE_HAS1(402,"调用接口失败"),
    INVITE_HAS2(403,"本版本没有背景音乐权限"),
    INVITE_HAS3(404,"本版本没有开启红包权限"),
    INVITE_HAS4(405,"入场编码已存在，请重新设置或由系统生成"),
    INVITE_HAS5(406,"活动已结束，无法核销"),
    INVITE_HAS6(407,"该入场码不存在，无法核销"),
    INVITE_HAS7(408,"该入场码已核销"),
    INVITE_HAS8(409,"该入场码已失效"),
    ;



    private final int code;
    private final String desc;

    ResponseEnums(int code, String desc ) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}