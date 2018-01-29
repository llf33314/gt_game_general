<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item  :to="{ path:'/christmasGift/index' }">圣诞大礼包</el-breadcrumb-item>  
      <el-breadcrumb-item>编辑活动</el-breadcrumb-item>   
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
                <el-form-item label="活动名称：" prop="treeName">
                    <el-input class="w_demo"  placeholder="请输入活动名称"  v-model="ruleForm1.treeName"></el-input>
                </el-form-item>
                 <el-form-item label="活动时间：" prop="date">
                    <el-date-picker class="w_demo" v-model="ruleForm1.date" :editable="false"   type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>   
                
                <el-form-item label="参与人员：">
                    <el-radio-group v-model="ruleForm1.treeEggPartaker">
                    <el-radio :label="1">所有粉丝</el-radio>
                    <el-radio :label="2">仅会员(持有会员卡的粉丝)</el-radio>
                    </el-radio-group>
                </el-form-item>
                <div class="pd20 bw pt10 bb ml150 mb20" v-if="ruleForm1.treeEggPartaker==2">
                    <el-form-item label="参与方式：" label-width="100px">
                          <el-radio-group v-model="ruleForm1.treePway" class="mg-t10">
                            <el-radio :label="1">所有会员不需要积分</el-radio><br><br>
                            <el-radio :label="2">会员卡积分满<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.treePway!="2"' v-model="ruleForm1.treeMan"></el-input>即可参加（抽奖不扣除积分）</el-radio><br><br>
                            <el-radio :label="3">每次抽奖扣除<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.treePway!="3"' v-model="ruleForm1.treeKou"></el-input>积分</el-radio><br><br>
                            <el-radio :label="4">会员卡积分满<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.treePway!="4"' v-model="ruleForm1.treeMan"></el-input>分，
                                                 每次抽奖扣除<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.treePway!="4"' v-model="ruleForm1.treeKou"></el-input>分</el-radio> 
                          </el-radio-group> 
                    </el-form-item>
                </div> 

                <el-form-item label="活动说明：" prop="treeDescribe">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.treeDescribe" :rows="3" placeholder="描述活动详情，能让粉丝了解此次活动" :maxlength="100"></el-input>
                    <!-- <span class="el-upload__tip grey" >
                        描述活动详情，能让粉丝了解此次活动
                    </span> -->
                </el-form-item> 

                 <el-form-item label="活动未开始提示：" prop="treeBeforeTxt">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.treeBeforeTxt" :rows="3" placeholder="请控制字数在100以内，如：活动尚未开始，敬请期待!" :maxlength="100"></el-input>
                    <!-- <span class="el-upload__tip grey" >
                        活动未开始提示限制在100个字数以内
                    </span> -->
                </el-form-item>  

                 <el-form-item label="背景音乐：">
                    <div class="pd20 bb bw bgMusic">
                        <gt-material class="va-m" :prop="''" :isMusic="true" btnContent="点击上传"  v-on:getChangeUrl="getMusic" width="72" height="72"></gt-material>
                        <span class="el-upload__tip c333 ml20">{{ruleForm1.treeBgmName}}</span> 
                        <div class="el-upload__tip grey" style="line-height:25px">
                            音频文件的格式为mp3、wma、wav,大小不超过3M
                        </div>
                    </div>
                </el-form-item>
            </el-form> 
             <div class="btnRow">
                            <el-button type="primary" @click="Save('ruleForm1')" :loading="loading">保存</el-button>
                            <el-button   @click="backUrl">返回</el-button>
                    </div>
        </div>
        <!-- 规则设置 -->
        <div v-show="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="120px" class="mt40 demo-ruleForm">  
                    <el-form-item label="抽奖次数：" prop="treeCountOfDay">
                        <el-input class="w_demo mr10" v-model.number="ruleForm2.treeCountOfDay" placeholder="请输入每人抽奖总次数"></el-input> 次/人
                    </el-form-item>
                    <el-form-item label="抽奖总数：" prop="treeCountOfAll">
                        <el-input class="w_demo mr10" v-model.number="ruleForm2.treeCountOfAll" placeholder="请输入每人/每天抽奖总数"></el-input>次/人
                    </el-form-item>   
            </el-form> 
             <div class="btnRow">
                            <el-button type="primary" @click="Save('ruleForm2')" :loading="loading">保存</el-button>
                            <el-button   @click="backUrl">返回</el-button>
                    </div>
        </div> 
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖期限：" prop="treeCashDay">
                    <el-input class="w_demo mr10" type="number" v-model.number="ruleForm3.treeCashDay" placeholder="请输入兑奖期限"></el-input>天
                    <span class="el-upload__tip grey">
                        从活动结束后开始计算
                    </span>
                </el-form-item> 
                <el-form-item label="兑奖地址：" prop="treeAddress">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm3.treeAddress" :rows="5" placeholder="请输入兑奖地址"></el-input>
                </el-form-item> 

                <el-form-item label="兑奖方式：">
                    <el-select v-model="ruleForm3.eggCashWay" placeholder="请选择"> 
                      <el-option label="自动发放"   :value="1"></el-option>
                      <el-option label="手动兑奖"   :value="2"></el-option> 
                    </el-select> 
                </el-form-item>  

                <el-form-item label="兑奖提示：">
                    <el-input class="w_demo" :maxlength="100" type="textarea" v-model="ruleForm3.treeWinningTxt" :rows="5" 
                    placeholder="兑奖提示限制在100个字以内"></el-input>
                </el-form-item>  
                 <el-form-item label="中奖须知：">
                    <el-input class="w_demo" :maxlength="100" type="textarea" v-model="ruleForm3.eggWinningNotice" :rows="13" 
                    placeholder="1、如果是实物的奖品，要填写中奖人的手机号码，如不是现场兑奖的还要填写速递地址。2、如中奖是流量的则要填写手机号码，流量将在12小时内到充值中奖人的手机号码上同时运营商会有流量到帐短信通知。3、如中奖是粉币或积分、优惠券的， 则中奖数额会即时自动累计到会员中心对应的类目上， 中奖人可到会员中心查看粉币或积分的增加数量。4、如果是转赠的，则要输入受赠人的手机号，同时受赠人要关注我们的微信公众号。5、中奖人须在规定的时间内完成兑奖，逾期则奖品自动作废。"></el-input>
                </el-form-item>    
            </el-form> 
             <div class="btnRow">
                            <el-button type="primary" @click="Save('ruleForm3')" :loading="loading">保存</el-button>
                            <el-button   @click="backUrl">返回</el-button>
                    </div>
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
                <el-button   @click="addForm4()" :disabled='ruleForm4.length>4'  type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">最多设置五个奖项</span> 
            </div> 
             <el-table ref="multipleTable" :data="ruleForm4" tooltip-effect="dark">
                <el-table-column label="奖品类型" :width="200">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.type" placeholder="请选择" class="w160">
                            <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value" v-if="item.value != 4">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="奖品数额" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w150" type="number" v-model="scope.row.prizeUnit" placeholder="数值应大于0"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖项名称" :width="240">
                  <template slot-scope="scope">
                      <el-input class="w20_demo"  v-model="scope.row.prizeName" placeholder="请输入奖项名称"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖项数量" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w150"  type="number"  v-model.number="scope.row.num" placeholder="数值应大于0"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="中奖概率(%)" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w160"  type="number"  v-model="scope.row.probabiliy" placeholder="0-100且保留两位小数"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="中奖人" min-width="120"> 
                     <template slot-scope="scope">
                      {{scope.row.nickname}}
                    </template>
                </el-table-column>
                <el-table-column label="操作" min-width="250">
                  <template slot-scope="scope">
                      <el-button class="gt-button-normal blue" @click="assign(scope)">指定中奖人</el-button>
                      <el-button class="gt-button-normal"  v-if="scope.$index!=0"  @click="delForm4(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>  
             <div class="btnRow">
                            <el-button type="primary" @click="lastStep" :loading="loading" >保存</el-button>
                            <el-button   @click="backUrl">返回</el-button>
                    </div>
        </div>       
       
        <!-- 按钮 -->
        <div class="h80"></div>

        <gt-Fans-detail :visible.sync="dialogFans" :peopleNums="peopleNums" v-on:getFansData="getFansData"></gt-Fans-detail>  
    </div>   
</div>
</section>
</template>
<script>
import api from "./../api/api";
export default {
  data() {
    return {
      loading: false,
      active: 0,
      peopleNums: 1,
      ruleForm1: {
        treeName: "", // 活动名称
        date: [],
        activityBeginTime: "", // 活动开始时间
        activityEndTime: "", // 活动结束时间
        treeEggPartaker: 1, // 1.所有粉丝 2.仅会员(持有会员卡的粉丝)
        treePway: 0, // 参与方式
        treeMan: "", // 可参加抽奖的会员积分
        treeKou: "", // 每次抽奖扣除积分
        treeDescribe: "", // 活动说明/描述
        treeBeforeTxt: "", // 活动未开始提示
        treeBgmName: "", // 背景音乐名称
        treeBgm: "" // 背景音乐链接
      },
      rules1: {
        treeName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" }
        ],
        date: [
          {
            required: true,
            type: "array",
            message: "开始时间不能为空",
            trigger: "blur"
          }
        ]
      },
      ruleForm2: {
        treeCountOfDay: "",
        treeCountOfAll: ""
      },
      rules2: {
        treeCountOfDay: [
          {
            required: true,
            type: "number",
            validator: this.$valid.elemIsInteger,
            trigger: "blur"
          }
        ],
        treeCountOfAll: [
          {
            required: true,
            type: "number",
            validator: this.$valid.elemIsInteger,
            trigger: "blur"
          }
        ]
      },
      ruleForm3: {
        treeCashDay: "",
        treeAddress: "",
        treeWinningTxt: "",
        eggCashWay: 2
      },
      rules3: {
        treeCashDay: [
          { type:'number', required: true, message: "兑奖期限不能为空", trigger: "blur" }
        ],
        treeAddress: [
          { required: true, message: "兑奖地址不能为空", trigger: "blur" }
        ]
      },
      ruleForm4: [
        {
          type: "",
          prizeUnit: "",
          prizeName: "",
          num: "",
          probabiliy: "",
          nickname: ""
        }
      ],
      options: [],
      dialogFans: false,
      assignObj: {}
    };
  },
  methods: {
    assign(scope) {
      if (!scope.row.num) {
        this.$message.info('请先输入奖项数量')
        return
      }
      this.dialogFans = true;
      this.assignObj = scope.row;
      this.peopleNums = scope.row.num;
    },
    getFansData(e) {
      if (e.length) {
        let nickname = [];
        let openid = []
        e.forEach((item, index, arr) => {
          nickname.push(item.nickname) 
          openid.push(item.openid)
        });
        this.assignObj.nickname = nickname.join(",")
        this.assignObj.openid = openid.join(",")
      this.$set(this.ruleForm4, this.assignObj.$index, this.assignObj)
      }
    },
    getMusic(e) {
      this.ruleForm1.treeBgmName = e.music.name;
      this.ruleForm1.treeBgm = e.music.url;
    },
    test() {
      this.dialogFans = true;
    },
    delForm4(index) {
      this.ruleForm4.splice(index, 1);
    },
    addForm4() {
      this.ruleForm4.push({
        type: "",
        prizeUnit: "",
        prizeName: "",
        num: "",
        probabiliy: "",
        nickname: ""
      });
    },
    upStep() {
      this.active--;
    },

    Save(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.submit()
        } else {
          return false;
        }
      });
    },
    lastStep() {
      let percentage = 0
      for (let i = 0; i < this.ruleForm4.length; i++) {
        var regu = /^[1-9]\d*$/;
        if (
          !this.ruleForm4[i].type ||
          !this.ruleForm4[i].prizeUnit ||
          !this.ruleForm4[i].prizeName ||
          !this.ruleForm4[i].num ||
          !this.ruleForm4[i].probabiliy
        ) {
          this.$message.error("表单不能留空，请填写完整~");
          return false;
        } else if (!regu.test(this.ruleForm4[i].prizeUnit)) {
          this.$message.error("奖品单位填写有误，请重新填写~");
          return false;
        } else if (!regu.test(this.ruleForm4[i].num)) {
          this.$message.error("奖项数量填写有误，请重新填写~");
          return false;
        } else if (
          this.ruleForm4[i].type == 4 &&
          this.ruleForm4[i].name5.length == 0
        ) {
          this.$message.error("当奖品为实物时，请上传实物图片~");
          return false;
        }
        percentage += Number(this.ruleForm4[i].probabiliy)
      }
      if (percentage != 100) {
         this.$message.error("中奖概率之和加起来应为100%");
         return false;
      }
      this.submit();
    },
    //表单提交--------------------------------------star
    submit() {
        // this.ruleForm4.forEach((item, idnex, arr) => {
        //     item.eggPrizeType = Number(item.eggPrizeType);
        // });
        const data = {
            id: this.$route.query.id,
            //基础设置
            treeName: this.ruleForm1.treeName, // 活动名称
            activityBeginTime: this.ruleForm1.date[0], // 活动开始时间
            activityEndTime: this.ruleForm1.date[1], // 活动结束时间
            treeEggPartaker: this.ruleForm1.treeEggPartaker, // 1.所有粉丝 2.仅会员(持有会员卡的粉丝)
            treePway: this.ruleForm1.treePway, // 参与方式
            treeMan: this.ruleForm1.treeMan, // 可参加抽奖的会员积分
            treeKou: this.ruleForm1.treeKou, // 每次抽奖扣除积分
            treeDescribe: this.ruleForm1.treeDescribe, // 活动说明/描述
            treeBeforeTxt: this.ruleForm1.treeBeforeTxt, // 活动未开始提示
            treeBgmName: this.ruleForm1.treeBgmName, // 背景音乐名称
            treeBgm: this.ruleForm1.treeBgm, // 背景音乐链接
            //规则设置
            treeCountOfDay: this.ruleForm2.treeCountOfDay, // 抽奖次数
            treeCountOfAll: this.ruleForm2.treeCountOfAll, // 抽奖总数
            //兑奖设置
            treeCashDay: this.ruleForm3.treeCashDay, // 兑奖期限
            treeAddress: this.ruleForm3.treeAddress, // 兑奖地址
            eggCashWay: this.ruleForm3.eggCashWay, // 兑奖方式
            treeWinningTxt: this.ruleForm3.treeWinningTxt, // 兑奖提示
            eggWinningNotice: this.ruleForm3.eggWinningNotice, // 中奖须知
            //奖项设置
            prizeSetList: this.ruleForm4
        };
        this.loading = true
        api
            .modfiyActivity(data)
            .then(data => {
              this.isSubmit = true;
              if (data.code == 100) {
                  this.$message.success('保存成功');
              } else {
                  this.isSubmit = false;
                  this.$message.error(data.msg || '保存失败');
              }
              this.loading = false
            })
    },
    backUrl() {
      window.history.go(-1);
    }
  },
  created() {
    Promise.all([
        // 获取详情信息
        api.getActivityById({ id: this.$route.query.id }),
        // 获取奖品类型
        this.$api.getPrizeTypeThree()
      ]).then(res => {
        if (res[0].code == 100) {
           // 步骤一详情 基础设置
            for(let key in this.ruleForm1) {
              this.ruleForm1[key] = res[0].data[key] || ''
            }
            this.ruleForm1.date = [ res[0].data.activityBeginTime,  res[0].data.activityEndTime ]

            // 步骤二详情 规则设置
            for(let key in this.ruleForm2) {
              this.ruleForm2[key] = res[0].data[key] || ''
            }

            // 步骤三详情 兑奖设置
            for(let key in this.ruleForm3) {
              this.ruleForm3[key] = res[0].data[key] || ''
            }

            // 步骤四详情 奖项设置
             this.ruleForm4 = res[0].data.prizeSetList
        } else {
            this.$message.error("获取编辑数据失败");
        }
        if (res[1].code == 100) {
          this.options = res[1].data;
        } else {
          this.$message.error("获取奖品类型失败");  
        }
      });
  },
  filters: {
    prizeStatus(val) {
      if (val == 0) {
        val = "一等奖";
      } else if (val == 1) {
        val = "二等奖";
      } else if (val == 2) {
        val = "三等奖";
      } else if (val == 3) {
        val = "四等奖";
      } else if (val == 4) {
        val = "五等奖";
      } else if (val == 5) {
        val = "六等奖";
      }
      return val;
    }
  }
};
</script>
