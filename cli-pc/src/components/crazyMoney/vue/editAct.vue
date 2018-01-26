

<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item  :to="{ path:'/crazyMoney/index' }">疯狂数钱</el-breadcrumb-item>  
      <el-breadcrumb-item>编辑活动</el-breadcrumb-item>   
    </el-breadcrumb>  
    <div class="gt-content">
         <el-tabs v-model="active" type="card">
            <el-tab-pane label="基础设置" name="0"></el-tab-pane>
            <el-tab-pane label="规则设置" name="1"></el-tab-pane>
            <el-tab-pane label="兑奖设置" name="2"></el-tab-pane>
            <el-tab-pane label="奖项设置" name="3" v-if="ruleForm1.actType == 1"></el-tab-pane>
        </el-tabs>
        <!-- 基础设置 -->
        <div v-show="this.active==0" class="mt40">
            <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="145px" class="demo-ruleForm">
                 <el-form-item label="游戏模式：">
                    <el-radio-group v-model="ruleForm1.actType">
                        <el-radio :label="1">排名中奖模式</el-radio>
                        <el-radio :label="2">数钱折算模式</el-radio>
                    </el-radio-group>
                </el-form-item>
               
                <el-form-item label="活动名称：" prop="actName">
                    <el-input class="w_demo"  placeholder="请输入活动名称"  v-model="ruleForm1.actName"></el-input>
                </el-form-item>
                 <el-form-item label="活动时间：" prop="date">
                    <el-date-picker class="w_demo" v-model="ruleForm1.date"   type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>     
                <el-form-item label="活动说明：" prop="actDescribe">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.actDescribe" :rows="3" placeholder="请输入活动说明"></el-input>
                    <span class="el-upload__tip grey" >
                        描述活动详情，能让粉丝了解此次活动
                    </span>
                </el-form-item> 

                 <el-form-item label="活动未开始提示：" prop="actNotStartedTips">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.actNotStartedTips" :rows="3" placeholder="如：活动尚未开始，敬请期待!"></el-input>
                    <span class="el-upload__tip grey" >
                        活动未开始提示限制在100个字数以内
                    </span>
                </el-form-item>  

                 <el-form-item label="背景音乐：">
                    <div class="pd20 bb bw bgMusic">
                        <gt-material class="va-m" :prop="''" :isMusic="true" btnContent="点击上传"  v-on:getChangeUrl="getMusic" width="72" height="72"></gt-material>
                        <span class="el-upload__tip c333 ml20">{{ruleForm1.bgmSp}}</span> 
                        <div class="el-upload__tip grey" style="line-height:25px">
                            音频文件的格式为mp3、wma、wav,大小不超过3M
                        </div>
                    </div>
                </el-form-item>
            </el-form> 
                    <div class="btnRow">
                                <el-button type="primary" @click="Save('ruleForm1')" >保存</el-button>
                                <el-button   @click="backUrl">返回</el-button>
                        </div>
        </div>
        <!-- 规则设置 -->
        <div v-show="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="游戏时间" prop="gameTime">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm2.gameTime" placeholder="请输入游戏持续时间"></el-input> 秒
                </el-form-item>
                <el-form-item label="抽奖次数：" prop="cishu">
                    <el-input class="w_demo mr10"  type="number" v-model="ruleForm2.cishu" placeholder="请输入每人抽奖总次数"></el-input> 次/人
                </el-form-item>
                <el-form-item label="抽奖总数：" prop="zongshu">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm2.zongshu" placeholder="请输入每人/每天抽奖总数"></el-input>次/人
                </el-form-item>

                <el-form-item label="概率设置：" class="mt10" prop="">
                    <div class=" grey" >请至少设置一种概率 </div>
                    <el-tooltip placement="top" effect="light">
                        <div slot="content">
                             已勾选面额的概率之和必须等于100%
                        </div>
                        <span class="el-icon-warning  ml10" style="position: absolute;  z-index: 1;margin-left: 435px;margin-top: 12px;color: #ccc;font-size: 17px;"></span>
                    </el-tooltip>

                     <el-tooltip placement="top" effect="light">
                        <div slot="content">
                            勾选后该面额的钞票会在数钱过程中出现 
                        </div>
                        <span class="el-icon-warning  ml10" style="position: absolute;  z-index: 1;margin-left: 145px;margin-top: 12px;color: #ccc;font-size: 17px;"></span>
                    </el-tooltip>

                    <el-table ref="multipleTable" :data="ruleForm2.countmoneyProbabilitysetList" tooltip-effect="dark" width="560" class="bw lh1">
                        <el-table-column label="面额" align="center" width="260"  header-align="center" label-class-name="miane">
                        <template slot-scope="scope" >
                            <el-checkbox  v-model="scope.row.checked"></el-checkbox>
                            <span class="mg-l8 va-m">{{scope.row.fenbiType}}元</span>
                        </template> 
                        </el-table-column>  
                        <el-table-column label="出现概率"  align="center"  header-align="center" label-class-name="chuxiangailv">
                        <template slot-scope="scope">
                            <el-input  class="w100_demo mr10" v-model.number="scope.row.fenbiChance"> 
                            </el-input>%
                        </template>
                        </el-table-column>   
                    </el-table> 
                     
                </el-form-item>    
            </el-form> 
            <div class="btnRow">
                                <el-button type="primary" @click="Save('ruleForm2')" >保存</el-button>
                                <el-button   @click="backUrl">返回</el-button>
                        </div>
        </div> 
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖期限：" prop="actAwardingTime">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm3.actAwardingTime" placeholder="请输入兑奖期限"></el-input>天
                    <span class="el-upload__tip grey">
                        从活动结束后开始计算
                    </span>
                </el-form-item> 
                <el-form-item label="兑奖地址：" prop="actAwardingAddress">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm3.actAwardingAddress" :rows="5" placeholder="请输入活动规则"></el-input>
                </el-form-item> 

                <el-form-item label="兑奖提示：">
                    <el-input class="w_demo" :maxlength="100" type="textarea" v-model="ruleForm3.aactAwardingTips" :rows="5" 
                    placeholder="兑奖提示限制在100个字以内"></el-input>
                </el-form-item>    
            </el-form> 
                    <div class="btnRow">
                                <el-button type="primary" @click="Save('ruleForm3')" >保存</el-button>
                                <el-button   @click="backUrl">返回</el-button>
                        </div>
        </div>
        <!-- 奖项设置 -->
        <div v-if="this.active==3 && ruleForm1.actType == 1" class="mt40">
            <div class="gt-gray-region" style="color:#666;line-height:20px">
                <p>奖品数额：奖品的数量或内容；奖项数量：该奖品的可领取次数；中奖概率：每种奖项在转盘中的中奖概率</p>
                <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p>
                <p>此次游戏活动设置了六个奖项：其中一个奖项为粉币(奖项名称)，该奖项出现3次(中奖数量)，中了该奖项会得到2个(奖品数额)粉币（奖品类型）。</p>
                 <p>当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px</p> 
            </div> 
            <div class="mt20 mb20">
                <span class="mr20 ">奖品数量：</span> 
                <el-radio-group v-model="actIsShowNums">
                    <el-radio :label="1">显示</el-radio>
                    <el-radio :label="2">不显示</el-radio>
                </el-radio-group> 
            </div> 
            <div class="mt20 mb20">
                <el-button   @click="addForm4()" :disabled='ruleForm4.length>4'  type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">最多设置五个奖项</span> 
            </div> 
            <el-table ref="multipleTable" :data="ruleForm4" tooltip-effect="dark">
                <el-table-column label="奖品类型">
                  <template slot-scope="scope">  
                      {{scope.$index |prizeStatus(scope.$index)}}
                  </template>
                </el-table-column> 
                <el-table-column label="奖品类型" :width="200">
                  <template slot-scope="scope">
                      <el-select class="w150"  v-model="scope.row.turPrizeType" placeholder="请选择"> 
                      <el-option label="粉币"      :value="1"></el-option>
                      <el-option label="手机流量"   :value="2"></el-option>
                      <el-option label="手机话费"   :value="3"></el-option>
                      <el-option label="实体物品"   :value="4"></el-option>
                      <el-option label="谢谢参与"   :value="5"></el-option> 
                      <el-option label="积分"       :value="6"></el-option> 
                      </el-select>
                  </template>
                </el-table-column> 
                <el-table-column label="奖品数额" :width="200">
                    <template slot-scope="scope">
                        <el-input class="w150"   type="number" v-model="scope.row.turPrizeUnit" placeholder="数值应大于0"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="奖项数量" :width="200">
                    <template slot-scope="scope">
                        <el-input class="w150"   type="number"  v-model="scope.row.turPrizeNums" placeholder="数值应大于0"></el-input>
                    </template>
                </el-table-column> 
                <el-table-column label="操作">
                    <template slot-scope="scope"> 
                        <el-button class="gt-button-normal" v-if="scope.row.id!=0" @click="delForm4(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>  
            <div class="btnRow">
                <el-button type="primary" @click="lastStep" >保存</el-button>
                <el-button   @click="backUrl">返回</el-button>
            </div>
        </div>       
        <!-- 新建完成 -->
        <div v-if="active==5" class="gt-content complete"> 
            <div class="addOk"> 
                <div class="el-icon-circle-check green" style="font-size:40px"></div>
                <div class="complete-info">活动添加成功</div>
                <el-button class="mt80" type="primary" @click="backUrl()">返回活动列表</el-button>  
            </div> 
        </div>
        <!-- 按钮 -->
        <div class="h80"></div>
    </div>   
</div>
</section>
</template>
<script>
import api from "../api/api";
export default {
  data() {
    return {
      active: "0",
      ruleForm1: {
        actType: 1,
        actName: "",
        date: "",
        actDescribe: "",
        actNotStartedTips: "",
        bgmSp: "",
        musicUrl: ""
      },
      rules1: {
        actName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" }
        ],
        date: [
          {
            required: true,
            type: "array",
            message: "游戏时间不能为空",
            trigger: "blur"
          }
        ]
      },
      ruleForm2: {
        gameTime: "", // 游戏时间
        actCountOfDay: "", // 抽奖次数
        actTotalOfAct: "", // 抽奖总数
        countmoneyProbabilitysetList: [
          {
            checked: false,
            fenbiType: 1,
            fenbiChance: ""
          },
          {
            checked: false,
            fenbiType: 5,
            fenbiChance: ""
          },
          {
            checked: false,
            fenbiType: 10,
            fenbiChance: ""
          },
          {
            checked: false,
            fenbiType: 20,
            fenbiChance: ""
          },
          {
            checked: false,
            fenbiType: 50,
            fenbiChance: ""
          },
          {
            checked: false,
            fenbiType: 100,
            fenbiChance: ""
          }
        ]
      },
      rules2: {
        gameTime: [
          { required: true, message: "游戏时间不能为空", trigger: "blur" }
        ],
        cishu: [
          { required: true, message: "抽奖次数不能为空", trigger: "blur" }
        ],
        zongshu: [
          { required: true, message: "抽奖总数不能为空", trigger: "blur" }
        ]
      },
      ruleForm3: {
        actAwardingTime: "",
        actAwardingAddress: "",
        aactAwardingTips: ""
      },
      rules3: {
        actAwardingTime: [
          { required: true, message: "兑奖期限不能为空", trigger: "blur" }
        ],
        actAwardingAddress: [
          { required: true, message: "兑奖地址不能为空", trigger: "blur" }
        ]
      },
      actIsShowNums: 1,
      ruleForm4: [
        {
          turPrizeType: "",
          turPrizeUnit: "",
          turPrizeNums: ""
        }
      ]
    };
  },
  methods: {
    getMusic(e) {
      this.ruleForm1.bgmSp = e.music.name;
      this.ruleForm1.musicUrl = e.music.url;
    },
    delForm4(index) {
      this.ruleForm4.splice(index, 1);
    },
    addForm4() {
      this.ruleForm4.push({
        turPrizeType: "",
        turPrizeUnit: "",
        turPrizeNums: ""
      });
    },
    Save(formName) {
      this.$refs[formName].validate(valid => {
        // 判断概率设置 之和是否100
        if (this.active == 1) {
              let probability = 0
              let checked = false
              this.ruleForm2.countmoneyProbabilitysetList.forEach((item, index, arr)=> {
                 if (item.checked) {
                     checked = true
                     probability += Number(item.fenbiChance) 
                 }
              })
              if(checked && probability !== 100) {
                valid = false
                this.$message.error('已勾选面额的概率之和必须等于100%')
              }
        }
        if (valid) {
          this.submit()
        } else {
          return false
        }
      });
    },
    lastStep() {
      for (let i = 0; i < this.ruleForm4.length; i++) {
        var regu = /^[1-9]\d*$/;
        if (
          !this.ruleForm4[i].turPrizeUnit ||
          !this.ruleForm4[i].turPrizeNums
        ) {
          this.$message.error("表单不能留空，请填写完整~");
          return false;
        } else if (!regu.test(this.ruleForm4[i].turPrizeUnit)) {
          this.$message.error("奖品数额填写有误，请重新填写~");
          return false;
        } else if (!regu.test(this.ruleForm4[i].turPrizeNums)) {
          this.$message.error("奖项数量填写有误，请重新填写~");
          return false;
        } else {
          this.submit();
        }
      }
    },
    //表单提交--------------------------------------star
    submit() {
      // 概率数据处理
      let Arr = [];
      this.ruleForm2.countmoneyProbabilitysetList.forEach(
        (item, index, arr) => {
          if (item.checked == true) {
            Arr.push({
              fenbiType: item.fenbiType,
              fenbiChance: item.fenbiChance
            });
          }
        }
      );
      const data = {
        id: this.$route.query.id, 
        // 基础设置
        actType: this.ruleForm1.actType, // 游戏模式(1-排名中奖；2-数钱折算),
        actName: this.ruleForm1.actName, // 活动名称
        actBeginTime: this.ruleForm1.date[0], // 活动开始时间
        actEndTime: this.ruleForm1.date[1], // 活动结束时间
        actDescribe: this.ruleForm1.actDescribe, // 活动说明
        actNotStartedTips: this.ruleForm1.actNotStartedTips, // 活动尚未开始提示
        bgmSp: this.ruleForm1.bgmSp, // 音乐名称
        musicUrl: this.ruleForm1.musicUrl, // 音乐地址
        //规则设置
        actGameTime: this.ruleForm2.actGameTime, // 游戏时间
        actCountOfDay: this.ruleForm2.actCountOfDay, // 抽奖次数
        actTotalOfAct: this.ruleForm2.actTotalOfAct, // 抽奖总数
        countmoneyProbabilitysetList: Arr, // 概率设置
        //兑奖设置
        actAwardingTime: this.ruleForm3.actAwardingTime, // 兑奖期限
        actAwardingAddress: this.ruleForm3.actAwardingAddress, // 兑奖地址
        actAwardingTips: this.ruleForm3.actAwardingTips, // 兑奖提示
        //奖项设置
        actIsShowNums: this.actIsShowNums, // 是否显示奖品数量
        prizeSetList: this.ruleForm4
      };
      api.modfiyActivity(data).then(data => {
        this.isSubmit = true;
        if (data.code == 100) {
          this.$message.success('保存成功');
        } else {
          this.isSubmit = false;
          this.$message.error(data.msg || "保存失败");
        }
      });
    },
    backUrl() {
      window.history.go(-1);
    }
  },
  mounted() {},
  created() {
    api.getActivityById({ id: this.$route.query.id }).then(res => {
      if (res.code == 100) {
        //基础设置
        (this.ruleForm1.actType = res.data.actType), // 游戏模式(1-排名中奖；2-数钱折算),
          (this.ruleForm1.actName = res.data.actName), // 活动名称
          (this.ruleForm1.date = [res.data.actBeginTime, res.data.actEndTime]); // 活动时间
        (this.ruleForm1.actDescribe = res.data.actDescribe), // 活动说明
          (this.ruleForm1.actNotStartedTips = res.data.actNotStartedTips), // 活动尚未开始提示
          (this.ruleForm1.bgmSp = res.data.bgmSp), // 音乐名称
          (this.ruleForm1.musicUrl = res.data.musicUrl), // 音乐地址
          //规则设置
          (this.ruleForm2.actGameTime = res.data.actGameTime), // 游戏时间
          (this.ruleForm2.actCountOfDay = res.data.actCountOfDay), // 抽奖次数
          (this.ruleForm2.actTotalOfAct = res.data.actTotalOfAct); // 抽奖总数
        // 概率设置
        res.data.countmoneyProbabilitysetList.forEach((item, idnex, arr) => {
          for (
            let i = 0;
            i < this.ruleForm2.countmoneyProbabilitysetList.length;
            i++
          ) {
            if (
              this.ruleForm2.countmoneyProbabilitysetList[i].fenbiType ==
              item.fenbiType
            ) {
              this.ruleForm2.countmoneyProbabilitysetList[i].checked = true;
              this.ruleForm2.countmoneyProbabilitysetList[i].fenbiChance =
                item.fenbiChance;
              continue;
            }
          }
        });
        //兑奖设置
        (this.ruleForm3.actAwardingTime = res.data.actAwardingTime), // 兑奖期限
          (this.ruleForm3.actAwardingAddress = res.data.actAwardingAddress), // 兑奖地址
          (this.ruleForm3.actAwardingTips = res.data.actAwardingTips); // 兑奖提示

        //奖项设置
        this.actactIsShowNumsNums = res.data.actactIsShowNumsNums, // 是否显示奖品数量
        res.data.prizeSetList.forEach((item, idnex, arr) => {
          item.eggPrizeType = item.eggPrizeType + "";
        });
        this.ruleForm4 = res.data.prizeSetList;
      } else {
        this.$message.error("获取编辑数据失败");
      }
    });
    api.getPrizeType().then(data => {
      if (data.code == 100) {
        this.options = data.data;
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
<style lang="less">
  .miane {
    padding-left: 90px;
  }

  .chuxiangailv {
    padding-left: 85px;
  }

</style>