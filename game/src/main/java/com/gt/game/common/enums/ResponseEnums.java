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
    SEAGOLD_HAS14(414,"请输入正确的粉币数量"),
    SEAGOLD_HAS15(415,"修改粉币数量失败"),

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

    //一站到底响应
    STAND_HAS1(401,"该奖品已发放!"),
    STAND_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    STAND_HAS3(403,"未到兑奖时间!"),
    STAND_HAS4(404,"已过兑奖时间!"),
    STAND_HAS5(405,"活动为空!"),
    STAND_HAS6(406,"活动已经开始，不允许操作!"),
    STAND_HAS7(407,"粉币不足，保存失败"),
    STAND_HAS8(408,"冻结粉币失败，保存失败"),
    STAND_HAS9(409,"输入的粉币数量过少"),
    STAND_HAS10(410,"活动正在进行，不允许删除"),
    STAND_HAS11(411,"奖品未发放完毕，不允许操作"),
    STAND_HAS12(412,"未过活动兑换期限，不允许删除"),
    STAND_HAS13(413,"回滚粉币失败，删除失败"),
    STAND_HAS14(414,"题库正在活动中使用，不允许删除或编辑"),
    STAND_HAS15(415,"格式错误！请上传Excel文件，文件后缀为xls或者xlsx"),
    STAND_HAS16(416,"读取文件失败"),
    STAND_HAS17(417,"导入失败,格式有误！存在空值！请修正后再尝试导入"),
    STAND_HAS18(418,"导入失败,格式有误"),

    //元宵点灯响应
    LANTERN_HAS1(401,"未到兑奖时间!"),
    LANTERN_HAS2(402,"已过兑奖时间!"),
    LANTERN_HAS3(403,"该奖品已发放!"),
    LANTERN_HAS4(404,"手动发放只能选择[已提交]状态的数据！!"),
    LANTERN_HAS5(405,"活动为空!"),
    LANTERN_HAS6(401,"删除活动失败！"),
    //一箭穿心响应
    LOVEARROW_HAS1(401,"该奖品已发放!"),
    LOVEARROW_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    LOVEARROW_HAS3(403,"未到兑奖时间!"),
    LOVEARROW_HAS4(404,"已过兑奖时间!"),
    LOVEARROW_HAS5(405,"活动为空!"),
    LOVEARROW_HAS6(406,"活动已经开始，不允许操作!"),
    LOVEARROW_HAS7(407,"粉币不足，保存失败"),
    LOVEARROW_HAS8(408,"冻结粉币失败，保存失败"),
    LOVEARROW_HAS9(409,"输入的粉币数量过少"),
    LOVEARROW_HAS10(410,"活动正在进行，不允许删除"),
    LOVEARROW_HAS11(411,"奖品未发放完毕，不允许操作"),
    LOVEARROW_HAS12(412,"未过活动兑换期限，不允许删除"),
    LOVEARROW_HAS13(413,"回滚粉币失败，删除失败"),
    LOVEARROW_HAS14(414,"请输入正确的粉币数量"),
    LOVEARROW_HAS15(415,"修改粉币数量失败"),

    //Common响应
    COMMON_HAS1(401,"该奖品已发放!"),
    COMMON_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    COMMON_HAS3(403,"未到兑奖时间!"),
    COMMON_HAS4(404,"已过兑奖时间!"),
    COMMON_HAS5(405,"活动为空!"),
    COMMON_AS6(406,"活动已经开始，不允许操作!"),
    COMMON_HAS7(407,"粉币不足，保存失败"),
    COMMON_HAS8(408,"冻结粉币失败，保存失败"),
    COMMON_HAS9(409,"输入的粉币数量过少"),
    COMMON_HAS10(410,"活动正在进行，不允许删除"),
    COMMON_HAS11(411,"奖品未发放完毕，不允许操作"),
    COMMON_HAS12(412,"未过活动兑换期限，不允许删除"),
    COMMON_HAS13(413,"回滚粉币失败，删除失败"),
    COMMON_HAS14(414,"请输入正确的粉币数量"),
    COMMON_HAS15(415,"修改粉币数量失败"),
    COMMON_HAS16(416,"兑奖开始时间需在活动开始之后"),
    COMMON_HAS17(417,"请选择活动开始时间"),
    //升国旗响应
    RAISEFLAG_HAS1(401,"该奖品已发放!"),
    RAISEFLAG_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    RAISEFLAG_HAS3(403,"未到兑奖时间!"),
    RAISEFLAG_HAS4(404,"已过兑奖时间!"),
    RAISEFLAG_HAS5(405,"活动为空!"),
    RAISEFLAG_HAS6(406,"活动已经开始，不允许操作!"),
    RAISEFLAG_HAS7(407,"粉币不足，保存失败"),
    RAISEFLAG_HAS8(408,"冻结粉币失败，保存失败"),
    RAISEFLAG_HAS9(409,"输入的粉币数量过少"),
    RAISEFLAG_HAS10(410,"活动正在进行，不允许删除"),
    RAISEFLAG_HAS11(411,"奖品未发放完毕，不允许操作"),
    RAISEFLAG_HAS12(412,"未过活动兑换期限，不允许删除"),
    RAISEFLAG_HAS13(413,"回滚粉币失败，删除失败"),
    RAISEFLAG_HAS14(414,"请输入正确的粉币数量"),
    RAISEFLAG_HAS15(415,"修改粉币数量失败"),

    //浪漫七夕响应
    QIXI_HAS1(401,"该奖品已发放!"),
    QIXI_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    QIXI_HAS3(403,"未到兑奖时间!"),
    QIXI_HAS4(404,"已过兑奖时间!"),
    QIXI_HAS5(405,"活动为空!"),
    QIXI_HAS6(406,"活动已经开始，不允许操作!"),
    QIXI_HAS7(407,"粉币不足，保存失败"),
    QIXI_HAS8(408,"冻结粉币失败，保存失败"),
    QIXI_HAS9(409,"输入的粉币数量过少"),
    QIXI_HAS10(410,"活动正在进行，不允许删除"),
    QIXI_HAS11(411,"奖品未发放完毕，不允许操作"),
    QIXI_HAS12(412,"未过活动兑换期限，不允许删除"),
    QIXI_HAS13(413,"回滚粉币失败，删除失败"),
    QIXI_HAS14(414,"请输入正确的粉币数量"),
    QIXI_HAS15(415,"修改粉币数量失败"),
    //欢乐抢元宝响应
    GOLDRUSH_HAS1(401,"该奖品已发放!"),
    GOLDRUSH_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    GOLDRUSH_HAS3(403,"未到兑奖时间!"),
    GOLDRUSH_HAS4(404,"已过兑奖时间!"),
    GOLDRUSH_HAS5(405,"活动为空!"),
    GOLDRUSH_HAS6(406,"活动已经开始，不允许操作!"),
    GOLDRUSH_HAS7(407,"粉币不足，保存失败"),
    GOLDRUSH_HAS8(408,"冻结粉币失败，保存失败"),
    GOLDRUSH_HAS9(409,"输入的粉币数量过少"),
    GOLDRUSH_HAS10(410,"活动正在进行，不允许删除"),
    GOLDRUSH_HAS11(411,"奖品未发放完毕，不允许操作"),
    GOLDRUSH_HAS12(412,"未过活动兑换期限，不允许删除"),
    GOLDRUSH_HAS13(413,"回滚粉币失败，删除失败"),
    GOLDRUSH_HAS14(414,"请输入正确的粉币数量"),
    GOLDRUSH_HAS15(415,"修改粉币数量失败"),

    //摇手气响应
    SHAKELUCK_HAS1(401,"该奖品已发放!"),
    SHAKELUCK_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    SHAKELUCK_HAS3(403,"未到兑奖时间!"),
    SHAKELUCK_HAS4(404,"已过兑奖时间!"),
    SHAKELUCK_HAS5(405,"活动为空!"),
    SHAKELUCK_HAS6(406,"活动已经开始，不允许操作!"),
    SHAKELUCK_HAS7(407,"粉币不足，保存失败"),
    SHAKELUCK_HAS8(408,"冻结粉币失败，保存失败"),
    SHAKELUCK_HAS9(409,"输入的粉币数量过少"),
    SHAKELUCK_HAS10(410,"活动正在进行，不允许删除"),
    SHAKELUCK_HAS11(411,"奖品未发放完毕，不允许操作"),
    SHAKELUCK_HAS12(412,"未过活动兑换期限，不允许删除"),
    SHAKELUCK_HAS13(413,"回滚粉币失败，删除失败"),
    SHAKELUCK_HAS14(414,"请输入正确的粉币数量"),
    SHAKELUCK_HAS15(415,"修改粉币数量失败"),

    //摇钱树响应
    GOLDTREE_HAS1(401,"该奖品已发放!"),
    GOLDTREE_HAS2(402,"手动发放只能选择[已提交]状态的数据！!"),
    GOLDTREE_HAS3(403,"未到兑奖时间!"),
    GOLDTREE_HAS4(404,"已过兑奖时间!"),
    GOLDTREE_HAS5(405,"活动为空!"),
    GOLDTREE_HAS6(406,"活动已经开始，不允许操作!"),
    GOLDTREE_HAS7(407,"粉币不足，保存失败"),
    GOLDTREE_HAS8(408,"冻结粉币失败，保存失败"),
    GOLDTREE_HAS9(409,"输入的粉币数量过少"),
    GOLDTREE_HAS10(410,"活动正在进行，不允许删除"),
    GOLDTREE_HAS11(411,"奖品未发放完毕，不允许操作"),
    GOLDTREE_HAS12(412,"未过活动兑换期限，不允许删除"),
    GOLDTREE_HAS13(413,"回滚粉币失败，删除失败"),
    GOLDTREE_HAS14(414,"请输入正确的粉币数量"),
    GOLDTREE_HAS15(415,"修改粉币数量失败"),

    //好运翻翻看响应
    LUCK_HAS1(401,"请选择游戏时间"),
    LUCK_HAS2(402,"活动为空"),
    LUCK_HAS3(403,"活动已经开始，不允许操作"),
    LUCK_HAS4(404,"请添加奖品"),
    LUCK_HAS5(405,"至少需要一个谢谢参与奖项"),
    LUCK_HAS6(406,"活动还未开始"),
    LUCK_HAS7(407,"活动已结束"),

    //端午赛龙舟响应
    DRAGONBOAT_HAS1(401,"删除活动失败！"),
    DRAGONBOAT_HAS2(402,"未到兑奖时间!"),
    DRAGONBOAT_HAS3(403,"已过兑奖时间!"),
    DRAGONBOAT_HAS4(403,"该奖品已发放!"),
    DRAGONBOAT_HAS5(404,"手动发放只能选择[已提交]状态的数据!"),
    DRAGONBOAT_HAS6(405,"活动为空!"),

    //幸运九宫格响应
    NINELATTICE_HAS1(401,"未到兑奖时间!"),
    NINELATTICE_HAS2(402,"已过兑奖时间!"),
    NINELATTICE_HAS3(403,"该奖品已发放!"),
    NINELATTICE_HAS4(404,"手动发放只能选择[已提交]状态的数据！!"),
    NINELATTICE_HAS5(405,"活动为空!"),
    NINELATTICE_HAS6(401,"删除活动失败！"),

    //圣诞大礼包响应
    TREE_HAS1(401,"未到兑奖时间!"),
    TREE_HAS2(402,"已过兑奖时间!"),
    TREE_HAS3(403,"该奖品已发放!"),
    TREE_HAS4(404,"手动发放只能选择[已提交]状态的数据！!"),

    //砸金蛋响应
    EGGS_HAS1(401,"未到兑奖时间!"),
    EGGS_HAS2(402,"已过兑奖时间!"),
    EGGS_HAS3(403,"该奖品已发放!"),
    EGGS_HAS4(404,"手动发放只能选择[已提交]状态的数据！!"),
    EGGS_HAS5(405,"活动为空!"),
    EGGS_HAS6(401,"删除活动失败！"),
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