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

    //拆礼盒响应
    DEMOLITION_HAS1(401,"该奖品已发放!"),
    DEMOLITION_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    DEMOLITION_HAS3(403,"未到兑奖时间!"),
    DEMOLITION_HAS4(404,"已过兑奖时间!"),
    DEMOLITION_HAS5(405,"活动为空!"),
    DEMOLITION_HAS6(406,"活动已经开始，不允许操作!"),
    DEMOLITION_HAS7(407,"粉币不足，保存失败"),
    DEMOLITION_HAS8(408,"冻结粉币失败，保存失败"),
    DEMOLITION_HAS9(409,"输入的粉币数量过少"),
    DEMOLITION_HAS10(410,"活动正在进行，不允许删除"),
    DEMOLITION_HAS11(411,"奖品未发放完毕，不允许操作"),
    DEMOLITION_HAS12(412,"未过活动兑换期限，不允许删除"),
    DEMOLITION_HAS13(413,"回滚粉币失败，删除失败"),

    //大海捞金响应
    SEAGOLD_HAS1(401,"该奖品已发放!"),
    SEAGOLD_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    SEAGOLD_HAS3(403,"未到兑奖时间!"),
    SEAGOLD_HAS4(404,"已过兑奖时间!"),
    SEAGOLD_HAS5(405,"活动为空!"),
    SEAGOLD_HAS6(406,"活动已经开始，不允许操作!"),
    SEAGOLD_HAS7(407,"粉币不足，保存失败"),
    SEAGOLD_HAS8(408,"冻结粉币失败，保存失败"),
    SEAGOLD_HAS9(409,"输入的粉币数量过少"),
    SEAGOLD_HAS10(410,"活动正在进行，不允许删除"),
    SEAGOLD_HAS11(411,"奖品未发放完毕，不允许操作"),
    SEAGOLD_HAS12(412,"未过活动兑换期限，不允许删除"),
    SEAGOLD_HAS13(413,"回滚粉币失败，删除失败"),

    //元旦跨年跳跃响应
    NEWYEAR_HAS1(401,"该奖品已发放!"),
    NEWYEAR_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    NEWYEAR_HAS3(403,"未到兑奖时间!"),
    NEWYEAR_HAS4(404,"已过兑奖时间!"),
    NEWYEAR_HAS5(405,"活动为空!"),
    NEWYEAR_HAS6(406,"活动已经开始，不允许操作!"),
    NEWYEAR_HAS7(407,"粉币不足，保存失败"),
    NEWYEAR_HAS8(408,"冻结粉币失败，保存失败"),
    NEWYEAR_HAS9(409,"输入的粉币数量过少"),
    NEWYEAR_HAS10(410,"活动正在进行，不允许删除"),
    NEWYEAR_HAS11(411,"奖品未发放完毕，不允许操作"),
    NEWYEAR_HAS12(412,"未过活动兑换期限，不允许删除"),
    NEWYEAR_HAS13(413,"回滚粉币失败，删除失败"),

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