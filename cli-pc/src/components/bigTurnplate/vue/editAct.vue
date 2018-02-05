<style lang="less">

</style>
<template>

  <section>
    <div class="hd-common turnPlate">
      <el-breadcrumb separator="/" class="gt-crumbs">
        <el-breadcrumb-item @click.native="$util.ClickApply">互动游戏</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/bigTurnplate/index' }">大转盘</el-breadcrumb-item>
        <el-breadcrumb-item>编辑</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="gt-content">
        <el-tabs v-model="active" type="card">
          <el-tab-pane label="基础设置" name="0"></el-tab-pane>
          <el-tab-pane label="规则设置" name="1"></el-tab-pane>
          <el-tab-pane label="兑奖设置" name="2"></el-tab-pane>
          <el-tab-pane label="奖项设置" name="3"></el-tab-pane>
        </el-tabs>
        <!-- 基础设置 -->
        <div v-show="this.active==0" class="mt40">
          <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="145px" class="demo-ruleForm">
            <el-form-item label="活动名称：" prop="actName">
              <el-input class="w_demo" placeholder="请输入活动名称" v-model="ruleForm1.actName"></el-input>
            </el-form-item>
            <el-form-item label="开始时间：" prop="actBeginTime">
              <el-date-picker class="w_demo" v-model="ruleForm1.actBeginTime" type="date" placeholder="请选择开始时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间：" prop="actEndTime">
              <el-date-picker class="w_demo" v-model="ruleForm1.actEndTime" type="date" placeholder="请选择结束时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束说明：" prop="actOverdescribe">
              <el-input class="w_demo" type="textarea" v-model="ruleForm1.actOverdescribe" :rows="3" placeholder="请输入活动结束说明"></el-input>
              <p class="el-upload__tip grey" style="width: 422px;line-height: 1.8;">
                可详细填写活动结束说明，让粉丝了解此次活动已结束。如：亲爱的粉丝们， 此次活动已结束，感谢你们的关注与参与，敬请期待下次活动的开展。
              </p>
            </el-form-item>
            <el-form-item label="参与人员：">
              <el-radio-group v-model="ruleForm1.actPartaker">
                <el-radio :label="1">所有粉丝</el-radio>
                <el-radio :label="2">仅会员(持有会员卡的粉丝)</el-radio>
              </el-radio-group>
            </el-form-item>
            <div class="pd20 bw pt10 bb ml150 mb20" v-if="ruleForm1.actPartaker==2">
              <el-form-item label="参与方式：" label-width="100px">
                <el-radio-group v-model="ruleForm1.actPway" class="mg-t10">
                  <el-radio :label="1">所有会员不需要积分</el-radio><br><br>
                  <el-radio :label="2">会员卡积分满
                    <el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.actPway!="2"' v-model="ruleForm1.actMan"></el-input>即可参加（抽奖不扣除积分）</el-radio><br><br>
                  <el-radio :label="3">每次抽奖扣除
                    <el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.actPway!="3"' v-model="ruleForm1.actKou"></el-input>积分</el-radio><br><br>
                  <el-radio :label="4">会员卡积分满
                    <el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.actPway!="4"' v-model="ruleForm1.actMan"></el-input>分， 每次抽奖扣除
                    <el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.actPway!="4"' v-model="ruleForm1.actKou"></el-input>分</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
            <el-form-item label="背景音乐：">
              <div class="pd20 bb bw bgMusic">
                <gt-material class="va-m" :prop="''" :isMusic="true" btnContent="点击上传" v-on:getChangeUrl="getMusic" width="72" height="72"></gt-material>
                <span class="el-upload__tip grey ml20">{{ruleForm1.actBgmName}}</span>
                <div class="el-upload__tip grey" style="line-height:25px">
                  音频文件的格式为mp3、wma、wav,大小不超过3M
                </div>
              </div>
            </el-form-item>
          </el-form>
        </div>
        <!-- 规则设置 -->
        <div v-show="this.active==1" class="mt40">
          <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="120px" class="mt40 demo-ruleForm">
            <el-form-item label="抽奖次数：" prop="actCountOfDay">
              <el-input class="w_demo" type="number" v-model="ruleForm2.actCountOfDay" placeholder="请输入每人/每天抽奖次数"></el-input>
            </el-form-item>
            <el-form-item label="抽奖总数：" prop="actTotalOfAct">
              <el-input class="w_demo" type="number" v-model="ruleForm2.actTotalOfAct" placeholder="请输入每人抽奖总数"></el-input>
            </el-form-item>
            <el-form-item label="活动规则：">
              <el-input class="w_demo" type="textarea" v-model="ruleForm2.actDescribe" :rows="5" placeholder="请输入活动规则"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
          <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
            <el-form-item label="兑奖期限：" prop="actCashday">
              <el-input class="w_demo mr10" type="number" v-model="ruleForm3.actCashday" placeholder="请输入兑奖期限"></el-input>天
              <span class="el-upload__tip grey">
                从活动结束后开始计算
              </span>
            </el-form-item>
            <el-form-item label="兑奖方式：">
              <el-select v-model="ruleForm3.actCashWay" placeholder="请选择">
                <el-option label="自动发放" :value="1"></el-option>
                <el-option label="手动兑奖" :value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="兑奖地址：" prop="actAddress">
              <el-input class="w_demo" type="textarea" v-model="ruleForm3.actAddress" :rows="5" placeholder="请输入兑奖地址"></el-input>
            </el-form-item>
            <el-form-item label="兑奖提示：">
              <el-input class="w_demo" type="textarea" v-model="ruleForm3.actCashtext" :rows="5" placeholder="如：兑换奖品请阅读活动说明或联系相关工作人员确认兑换！兑奖请联系我们，电话0752-2366888"></el-input>
            </el-form-item>
            <el-form-item label="中奖须知：">
              <el-input class="w_demo" type="textarea" v-model="ruleForm3.actWinningNotice" :rows="5" placeholder="如：1、如果是实物的奖品，要填写中奖人的手机号码，如不是现场兑奖的还要填写速递地址。"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <!-- 奖项设置 -->
        <div v-show="this.active==3" class="mt40">
          <div class="gt-gray-region" style="color:#666;line-height:20px">
            <p>奖品数额：奖品的数量或内容；奖项数量：该奖品的可领取次数；中奖概率：每种奖项在转盘中的中奖概率</p>
            <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p>
            <p>此次游戏活动设置了六个奖项：其中一个奖项为粉币(奖项名称)，该奖项出现3次(中奖数量)，中了该奖项会得到2个(奖品数额)粉币（奖品类型）。</p>
            <p>当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px</p>
          </div>
          <div class="mt20 mb20">
            奖项数量：
            <el-select v-model="awardKey" placeholder="请选择" @change="setting()">
              <el-option label="六个奖项" :value="0"></el-option>
              <el-option label="八个奖项" :value="1"></el-option>
              <el-option label="十个奖项" :value="2"></el-option>
            </el-select>
            <span class="el-upload__tip grey">
              奖项个数对应着转盘上的奖项区域数
            </span>
          </div>
          <el-table ref="multipleTable" :data="ruleForm4" tooltip-effect="dark">
            <el-table-column label="奖品类型" :width="200">
              <template slot-scope="scope">
                <el-select v-model="scope.row.turPrizeType" placeholder="请选择" class="w160">
                  <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value">
                    <span>{{item.name}}</span>
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="奖品数额" :width="200">
              <template slot-scope="scope">
                <el-input class="w150" type="number" v-model="scope.row.turPrizeLimit" placeholder="数值应大于0"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="奖项名称" :width="240">
              <template slot-scope="scope">
                <el-input class="w20_demo" v-model="scope.row.turPrizeName" placeholder="请输入奖项名称"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="奖项数量" :width="200">
              <template slot-scope="scope">
                <el-input class="w150" type="number" v-model="scope.row.turPrizeNums" placeholder="数值应大于0"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="中奖概率(%)" :width="200">
              <template slot-scope="scope">
                <el-input class="w160" v-model="scope.row.turPrizeChance" placeholder="0-100且保留两位小数"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="中奖人">
              <template slot-scope="scope">
                {{scope.row.nickname}}
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button class="gt-button-normal blue" @click="assign(scope)">指定中奖人</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 按钮 -->
        <div class="h80"></div>
        <div class="btnRow" v-if="this.active!=5">
          <el-button @click="backUrl()">返回</el-button>
          <el-button type="primary" @click="next()" v-if="this.active==0||this.active==1">保存</el-button>
          <el-button type="primary" @click="next()" v-if="this.active==2">保存</el-button>
          <el-button type="primary" @click="lastStep()" v-if="this.active==3">保存</el-button>
        </div>
        <gt-Fans-detail :visible.sync="dialogFans" :peopleNums="peopleNums" v-on:getFansData="getFansData"></gt-Fans-detail>
      </div>
    </div>
  </section>
</template>
<script>
import { getPrizeType, modfiyScratch, getActivityById } from "./../api/api";
export default {
  data() {
    let timeCode = (rule, value, callback) => {
      if (!value) {
        callback(new Error("兑奖期限不能为空"));
      }
      if (!/^[1-9]\d*$/.test(value)) {
        //如果输入正确做什么处理，这里根据实际情况可修改
        callback(new Error('请输入正整数'));
      } else {
        callback();
      }
    };
    return {
      loading: false,
      active: 0,
      peopleNums: 1,

      //第一步-----------------------------------
      ruleForm1: {
        actName: "", // 活动名称
        actBeginTime: "", // 活动开始时间
        actEndTime: "", // 活动结束时间
        actPartaker: 1, // 1.所有粉丝 2.仅会员(持有会员卡的粉丝)
        actPway: 0, // 参与方式
        actMan: "", // 可参加抽奖的会员积分
        actKou: "", // 每次抽奖扣除积分
        actOverdescribe: "", // 结束说明/描述
        actBgmName: "暂无上传音乐", // 背景音乐名称
        actBgm: "" // 背景音乐链接
      },
      rules1: {
        actName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" }
        ],
        actBeginTime: [
          { type: 'date', required: true, message: '请输入活动开始时间', trigger: 'change' }
        ],
        actEndTime: [
          { type: 'date', required: true, message: '请输入活动结束时间', trigger: 'change' }
        ],
      },
      //第2步-----------------------------------
      ruleForm2: {
        actCountOfDay: "",  // 抽奖次数
        actTotalOfAct: "", // 抽奖总数
        actDescribe: "",//活动规则
      },
      rules2: {
        actCountOfDay: [
          {
            required: true,
            type: "number",
            validator: this.$valid.elemIsInteger,
            trigger: "blur"
          }
        ],
        actTotalOfAct: [
          {
            required: true,
            type: "number",
            validator: this.$valid.elemIsInteger,
            trigger: "blur"
          }
        ]
      },
      //第3步-----------------------------------
      ruleForm3: {
        actCashday: "",   // 兑奖期限
        actAddress: "",   // 兑奖地址
        actCashWay: 2,      // 兑奖方式
        actCashtext: "", // 兑奖提示
        actWinningNotice: "" // 中奖须知
      },
      rules3: {
        actCashday: [
          { required: true, validator: timeCode, trigger: "blur" }
        ],
        actAddress: [
          { required: true, message: "兑奖地址不能为空", trigger: "blur" }
        ]
      },
      //第4步-----------------------------------
      awardKey: 0,
      ruleForm4: [],
      ruleForm46: [
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        }
      ],
      ruleForm48: [
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        }
      ],
      ruleForm41: [
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        },
        {
          turPrizeType: "",  // 奖品类型
          turPrizeLimit: "", // 奖品数额
          turPrizeName: "",  // 奖项名称
          turPrizeNums: "",  // 奖项数量
          turPrizeChance: "",// 中奖概率
          nickname: ""       // 中奖人
        }
      ],
      options: [],//奖品类型
      dialogFans: false,//中奖人弹窗
      assignObj: {},
    };
  },
  methods: {
    //指定中奖人
    assign(scope) {
      if (!scope.row.turPrizeNums) {
        this.$message.info('请先输入奖项数量')
        return
      }
      this.dialogFans = true;
      this.assignObj = scope.row;
      this.peopleNums = Number(scope.row.turPrizeNums);
    },
    //中奖人弹窗
    getFansData(e) {
      if (e.length) {
        let nickname = [];
        e.forEach((item, index, arr) => {
          nickname.push(item.nickname)
        });
        this.assignObj.nickname = nickname.join(",")
        this.$set(this.ruleForm4, this.assignObj.$index, this.assignObj)
      }
    },
    //上传音乐======================================
    getMusic(e) {
      this.ruleForm1.actBgmName = e.music.name;
      this.ruleForm1.actBgm = e.music.url;
    },
    //奖项设置===============================================
    test() {
      this.dialogFans = true;
    },
    //步骤条-==-========================================
    upStep() {
      this.active--;
    },
    //下一步=====================================
    next(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.active++;
        } else {
          return false;
        }
      });
    },
    //保存验证奖项设置-------------------------------------------
    lastStep() {
      let percentage = 0
      for (let i = 0; i < this.ruleForm4.length; i++) {
        var regu = /^[1-9]\d*$/;
        if (
          !this.ruleForm4[i].turPrizeType ||
          !this.ruleForm4[i].turPrizeLimit ||
          !this.ruleForm4[i].turPrizeName ||
          !this.ruleForm4[i].turPrizeNums ||
          !this.ruleForm4[i].turPrizeChance
        ) {
          this.$message.error("表单不能留空，请填写完整~");
          return false;
        } else if (!regu.test(this.ruleForm4[i].turPrizeLimit)) {
          this.$message.error("奖品数额填写有误，请填写大于0的正整数");
          return false;
        } else if (!regu.test(this.ruleForm4[i].turPrizeNums)) {
          this.$message.error("奖项数量填写有误，请填写大于0的正整数");
          return false;
        } else if (!/(^[1-9]{1}[0-9]*$)|(^[0-9]*\.[0-9]{2}$)/.test(this.ruleForm4[i].turPrizeChance)) {
          this.$message.error("中奖概率填写有误，请输入大于0的整数或者保留两位小数");
          return false;
        } else if (
          this.ruleForm4[i].turPrizeType == 4
        )
          console.log(this.ruleForm4[i])
        percentage += parseFloat(this.ruleForm4[i].turPrizeChance)
      }
      console.log(percentage)
      if (percentage != 100) {
        this.$message.error("中奖概率之和加起来应为100%");
        return false;
      }
      this.submit();

    },
    //表单提交--------------------------------------star
    submit() {
      const data = {
        id: this.$router.history.current.query.id,
        //基础设置
        actName: this.ruleForm1.actName, // 活动名称
        actBeginTime: this.ruleForm1.actBeginTime, // 活动开始时间
        actEndTime: this.ruleForm1.actEndTime, // 活动结束时间
        actPartaker: this.ruleForm1.actPartaker, // 1.所有粉丝 2.仅会员(持有会员卡的粉丝)
        actPway: this.ruleForm1.actPway, // 参与方式
        actMan: this.ruleForm1.actMan, // 可参加抽奖的会员积分
        actKou: this.ruleForm1.actKou, // 每次抽奖扣除积分
        actOverdescribe: this.ruleForm1.actOverdescribe, // 活动说明/描述
        actBgmName: this.ruleForm1.actBgmName, // 背景音乐名称
        actBgm: this.ruleForm1.actBgm, // 背景音乐链接
        //规则设置
        actCountOfDay: this.ruleForm2.actCountOfDay, // 抽奖次数
        actTotalOfAct: this.ruleForm2.actTotalOfAct, // 抽奖总数
        actDescribe: this.ruleForm2.actDescribe, // 抽奖总数        
        //兑奖设置
        actCashday: this.ruleForm3.actCashday, // 兑奖期限
        actAddress: this.ruleForm3.actAddress, // 兑奖地址
        actCashWay: this.ruleForm3.actCashWay, // 兑奖方式
        actCashtext: this.ruleForm3.actCashtext, // 兑奖提示
        actWinningNotice: this.ruleForm3.actWinningNotice, // 中奖须知
        //奖项设置
        prizeSetList: this.ruleForm4
      };
      console.log(data, 5555555)
      // this.loading = true;
      modfiyScratch(data).then(res => {
        console.log(data, 66666666)
        if (res.code == 100) {
          console.log(res, 7777777)
          this.$message({ message: "保存成功", type: "success" });
        } else {
          this.$message.error(res.msg);
        }
      }).catch(() => {
        this.$message({ type: "info", message: "网络问题，请刷新重试~" });
      });
    },
    //返回=====================
    backUrl() {
      window.history.go(-1);
    },
    //初始化获取-------------------
    getActData() {
      var id = this.$router.history.current.query.id
      getActivityById({ id }).then(data => {
        if (data.code == 100) {
          console.log(data, 88552223333)
          //基础设置
          this.ruleForm1.actName = data.data.actName // 活动名称
          this.ruleForm1.actBeginTime = data.data.actBeginTime // 活动开始时间
          this.ruleForm1.actEndTime = data.data.actEndTime  // 活动结束时间
          this.ruleForm1.actPartaker = data.data.actPartaker // 1.所有粉丝 2.仅会员(持有会员卡的粉丝)
          this.ruleForm1.actPway = data.data.actPway // 参与方式
          this.ruleForm1.actMan = data.data.actMan// 可参加抽奖的会员积分
          this.ruleForm1.actKou = data.data.actKou // 每次抽奖扣除积分
          this.ruleForm1.actOverdescribe = data.data.actOverdescribe // 活动说明/描述
          this.ruleForm1.actBgmName = data.data.actBgmName// 背景音乐名称
          this.ruleForm1.actBgm = data.data.actBgm // 背景音乐链接
          //规则设置
          this.ruleForm2.actCountOfDay = data.data.actCountOfDay // 抽奖次数
          this.ruleForm2.actTotalOfAct = data.data.actTotalOfAct // 抽奖总数
          this.ruleForm2.actDescribe = data.data.actDescribe // 抽奖总数        
          //兑奖设置
          this.ruleForm3.actCashday = data.data.actCashday // 兑奖期限
          this.ruleForm3.actAddress = data.data.actAddress // 兑奖地址
          this.ruleForm3.actCashWay = data.data.actCashWay // 兑奖方式
          this.ruleForm3.actCashtext = data.data.actCashtext // 兑奖提示
          this.ruleForm3.actWinningNotice = data.data.actWinningNotice // 中奖须知
          //奖项设置
          this.ruleForm4 = data.data.prizeSetList
        } else {
          this.$message.error(data.msg);
        }
      }).catch(() => {
        this.$message({ type: "info", message: "网络问题，请刷新重试~" });
      });
    },
    //奖项数量切换----------------------------------star
    setting() {
      if (this.awardKey == 0) {
        this.ruleForm4 = this.ruleForm46
      } else if (this.awardKey == 1) {
        this.ruleForm4 = this.ruleForm48
      } else {
        this.ruleForm4 = this.ruleForm41
      }
    },
    //获取奖品类型-----------star=====================================
    getPrizeTypeData() {
      getPrizeType().then(data => {
        if (data.code == 100) {
          this.options = data.data
        } else {
          this.$message.error("获取奖品类型失败");
        }
      }).catch(() => {
        this.$message({ type: "info", message: "网络问题，请刷新重试~" });
      });
    },
  },

  // created() {
  //   this.ruleForm4 = this.ruleForm46;
  //   // 获取奖品类型
  //   getPrizeType().then(res => {
  //     if (res.code == 100) {
  //       this.options = res.data;
  //       console.log(this.options, "获取奖品类型");
  //     } else {
  //       this.$message.error("获取奖品类型失败");
  //     }
  //   });
  // },
  mounted() {
    this.getPrizeTypeData()
    this.getActData()
  },
};
</script>