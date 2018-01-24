<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item  :to="{ path:'/eggSmash/index' }">砸金蛋</el-breadcrumb-item>  
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
                <el-form-item label="活动名称：" prop="eggName">
                    <el-input class="w_demo"  placeholder="请输入活动名称"  v-model="ruleForm1.eggName"></el-input>
                </el-form-item>
                 <el-form-item label="活动时间：" prop="date">
                    <el-date-picker class="w_demo" v-model="ruleForm1.date" :editable="false"   type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>   
                
                <el-form-item label="参与人员：">
                    <el-radio-group v-model="ruleForm1.eggEggPartaker">
                    <el-radio :label="1">所有粉丝</el-radio>
                    <el-radio :label="2">仅会员(持有会员卡的粉丝)</el-radio>
                    </el-radio-group>
                </el-form-item>
                <div class="pd20 bw pt10 bb ml150 mb20" v-if="ruleForm1.eggEggPartaker==2">
                    <el-form-item label="参与方式：" label-width="100px">
                          <el-radio-group v-model="ruleForm1.eggPway" class="mg-t10">
                            <el-radio :label="1">所有会员不需要积分</el-radio><br><br>
                            <el-radio :label="2">会员卡积分满<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.eggPway!="2"' v-model="ruleForm1.eggMan"></el-input>即可参加（抽奖不扣除积分）</el-radio><br><br>
                            <el-radio :label="3">每次抽奖扣除<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.eggPway!="3"' v-model="ruleForm1.eggKou"></el-input>积分</el-radio><br><br>
                            <el-radio :label="4">会员卡积分满<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.eggPway!="4"' v-model="ruleForm1.eggMan"></el-input>分，
                                                 每次抽奖扣除<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.eggPway!="4"' v-model="ruleForm1.eggKou"></el-input>分</el-radio> 
                          </el-radio-group> 
                    </el-form-item>
                </div> 

                <el-form-item label="活动说明：" prop="eggDescribe">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.eggDescribe" :rows="3" placeholder="请输入活动说明"></el-input>
                    <span class="el-upload__tip grey" >
                        描述活动详情，能让粉丝了解此次活动
                    </span>
                </el-form-item> 

                 <el-form-item label="活动未开始提示：" prop="eggBeforeTxt">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.eggBeforeTxt" :rows="3" placeholder="如：活动尚未开始，敬请期待!"></el-input>
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
                <el-form-item>
                    <div class="btnRow">
                            <el-button type="primary" @click="Save('ruleForm1')" >保存</el-button>
                            <el-button   @click="backUrl">返回</el-button>
                    </div>
                </el-form-item>
            </el-form> 
        </div>
       <!-- 规则设置 -->
        <div v-show="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="120px" class="mt40 demo-ruleForm">  
                    <el-form-item label="抽奖次数：" prop="eggCountOfDay">
                        <el-input class="w_demo mr10" v-model.number="ruleForm2.eggCountOfDay" placeholder="请输入每人抽奖总次数"></el-input> 次/人
                    </el-form-item>
                    <el-form-item label="抽奖总数：" prop="eggCountOfAll">
                        <el-input class="w_demo mr10" v-model.number="ruleForm2.eggCountOfAll" placeholder="请输入每人/每天抽奖总数"></el-input>次/人
                    </el-form-item>   
                     <el-form-item>
                        <div class="btnRow">
                                <el-button type="primary" @click="Save('ruleForm2')" >保存</el-button>
                                <el-button   @click="backUrl">返回</el-button>
                        </div>
                    </el-form-item>
            </el-form> 
        </div>  
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖期限：" prop="eggCashDay">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm3.eggCashDay" placeholder="请输入兑奖期限"></el-input>天
                    <span class="el-upload__tip grey">
                        从活动结束后开始计算
                    </span>
                </el-form-item> 
                <el-form-item label="兑奖地址：" prop="eggAddress">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm3.eggAddress" :rows="5" placeholder="请输入兑奖地址"></el-input>
                </el-form-item> 

                <el-form-item label="兑奖方式：">
                    <el-select v-model="ruleForm3.eggCashWay" placeholder="请选择"> 
                      <el-option label="自动发放"   :value="1"></el-option>
                      <el-option label="手动兑奖"   :value="2"></el-option> 
                    </el-select> 
                </el-form-item>  

                <el-form-item label="兑奖提示：">
                    <el-input class="w_demo" :maxlength="100" type="textarea" v-model="ruleForm3.eggWinningTxt" :rows="5" 
                    placeholder="兑奖提示限制在100个字以内"></el-input>
                </el-form-item>  
                 <el-form-item label="中奖须知：">
                    <el-input class="w_demo" :maxlength="100" type="textarea" v-model="ruleForm3.eggWinningNotice" :rows="13" 
                    placeholder="1、如果是实物的奖品，要填写中奖人的手机号码，如不是现场兑奖的还要填写速递地址。2、如中奖是流量的则要填写手机号码，流量将在12小时内到充值中奖人的手机号码上同时运营商会有流量到帐短信通知。3、如中奖是粉币或积分、优惠券的， 则中奖数额会即时自动累计到会员中心对应的类目上， 中奖人可到会员中心查看粉币或积分的增加数量。4、如果是转赠的，则要输入受赠人的手机号，同时受赠人要关注我们的微信公众号。5、中奖人须在规定的时间内完成兑奖，逾期则奖品自动作废。"></el-input>
                </el-form-item>
                <el-form-item>
                        <div class="btnRow">
                                <el-button type="primary" @click="Save('ruleForm3')" >保存</el-button>
                                <el-button   @click="backUrl">返回</el-button>
                        </div>
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
                <el-button   @click="addForm4()" :disabled='ruleForm4.length>4'  type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">最多设置五个奖项</span> 
            </div> 
             <el-table ref="multipleTable" :data="ruleForm4" tooltip-effect="dark">
                <el-table-column label="奖品类型" :width="200">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.eggPrizeType" placeholder="请选择" class="w160">
                            <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value" v-if="item.value != 4">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="奖品数额" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w150" type="number" v-model="scope.row.eggPrizeLimit" placeholder="数值应大于0"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖项名称" :width="240">
                  <template slot-scope="scope">
                      <el-input class="w20_demo"  v-model="scope.row.eggPrizeName" placeholder="请输入奖项名称"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖项数量" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w150"  type="number"  v-model="scope.row.eggPrizeNums" placeholder="数值应大于0"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="中奖概率(%)" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w160"  v-model="scope.row.eggPrizeChance" placeholder="0-100且保留两位小数"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="中奖人"> 
                     <template slot-scope="scope">
                      {{scope.row.nickname}}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                      <el-button class="gt-button-normal blue" @click="test(scope.row.id)">指定中奖人</el-button>
                      <el-button class="gt-button-normal"  v-if="scope.$index!=0"  @click="delForm4(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>  
            <div class="btnRow">
                <el-button type="primary" @click="lastStep" >保存</el-button>
                <el-button   @click="backUrl">返回</el-button>
            </div>
        </div> 
        <div class="h80"></div> 
    </div>   
</div>
</section>
</template>
<script>
import api from './../api/api'
export default {
  data() {
    return {
      active: "0",
      ruleForm1: {
        eggName: '',  // 活动名称
        date: [],               
        eggBeginTime: '', // 活动开始时间
        eggEndTime: '',  // 活动结束时间
        eggEggPartaker: 1,  // 1.所有粉丝 2.仅会员(持有会员卡的粉丝) 
        eggPway: 0, // 参与方式
        eggMan: '', // 可参加抽奖的会员积分
        eggKou: '', // 每次抽奖扣除积分
        eggDescribe: '',  // 活动说明/描述
        eggBeforeTxt : '',   // 活动未开始提示
        bgmSp: '',       // 背景音乐名称    
        musicUrl: '',   // 背景音乐链接
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        eggPrizeLimit: [
          { required: true, type: "array", message: "开始时间不能为空", trigger: "blur" }
        ],
      },
      ruleForm2: { 
        eggCountOfDay: "",
        eggCountOfAll: "" ,
      },
      rules2: { 
        eggCountOfDay: [{ required: true, type:'number', validator: this.$valid.elemIsInteger, trigger: "blur" }],
        eggCountOfAll: [{ required: true, type:'number', validator: this.$valid.elemIsInteger, trigger: "blur" }]
      },
       ruleForm3: {
        eggCashDay:"",
        eggAddress: "",
        eggWinningTxt: "",
        eggCashWay:2,
      }, 
      rules3: {
        eggCashDay: [{ required: true,  message: "兑奖期限不能为空", trigger: "blur" }],
        eggAddress: [{ required: true, message: "兑奖地址不能为空", trigger: "blur" }]
      },  
     ruleForm4:[
        { 
          eggPrizeType: "",
          eggPrizeLimit: "",
          eggPrizeName: "",
          eggPrizeNums: "",
          eggPrizeChance: "",
          nickname:"",
        }
      ],
      options: []
    };
  },
  methods: {  
    test() {
      console.log(123); 
      this.active=5
    },
    delForm4(index){
      this.ruleForm4.splice(index, 1);
    },
    addForm4(){  
      this.ruleForm4.push({ eggPrizeType: "", eggPrizeLimit: "", eggPrizeName: "", eggPrizeNums: "", eggPrizeChance: "", nickname: "" })
    },
    getMusic(e) {
      this.ruleForm1.bgmSp = e.music.name
      this.ruleForm1.musicUrl = e.music.url
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
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if(!this.ruleForm4[i].eggPrizeLimit||!this.ruleForm4[i].eggPrizeName||!this.ruleForm4[i].eggPrizeNums||!this.ruleForm4[i].eggPrizeChance){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].eggPrizeLimit)) {
            this.$message.error("奖品数额填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].eggPrizeNums)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        }else{
            this.ruleForm4[i].eggPrizeChance = parseFloat(this.ruleForm4[i].eggPrizeChance).toFixed(2); 
            this.submit();
        }  
      }
    },
    //表单提交--------------------------------------star
    submit(){
        this.ruleForm4.forEach((item, idnex, arr)=>{
            item.eggPrizeType = Number(item.eggPrizeType)
        });
        const data = {
            id: this.$route.query.id,
            //基础设置 
            eggName: this.ruleForm1.eggName,  // 活动名称               
            eggBeginTime: this.ruleForm1.date[0], // 活动开始时间
            eggEndTime: this.ruleForm1.date[1],  // 活动结束时间
            eggEggPartaker: this.ruleForm1.eggEggPartaker,  // 1.所有粉丝 2.仅会员(持有会员卡的粉丝) 
            eggPway: this.ruleForm1.eggPway, // 参与方式
            eggMan: this.ruleForm1.eggMan, // 可参加抽奖的会员积分
            eggKou: this.ruleForm1.eggKou, // 每次抽奖扣除积分
            eggDescribe: this.ruleForm1.eggDescribe,  // 活动说明/描述
            eggBeforeTxt : this.ruleForm1.eggBeforeTxt,   // 活动未开始提示
            bgmSp: this.ruleForm1.bgmSp,       // 背景音乐名称    
            musicUrl: this.ruleForm1.musicUrl,   // 背景音乐链接
            //规则设置
            eggCountOfDay: this.ruleForm2.eggCountOfDay,   // 抽奖次数
            eggCountOfAll: this.ruleForm2.eggCountOfAll, // 抽奖总数
            //兑奖设置
            eggCashDay: this.ruleForm3.eggCashDay,      // 兑奖期限
            eggAddress: this.ruleForm3.eggAddress,     // 兑奖地址
            // eggPrizeLimit1: this.ruleForm3.eggWinningTxt,     // 兑奖时间
            eggCashWay: this.ruleForm3.eggCashWay,  // 兑奖方式
            eggWinningTxt: this.ruleForm3.eggWinningTxt, // 兑奖提示
            eggWinningNotice: this.ruleForm3.eggWinningNotice,// 中奖须知
            //奖项设置 
            prizeSetList: this.ruleForm4
        };
        api.modfiyActivity(data).then(data=>{
          if (data.code == 100) { 
              this.$message.success(data.msg || '保存成功');
          } else {
              this.$message.error(data.msg || '保存失败');
          }
        })
    },    
    backUrl(){
         window.history.go(-1);
    },
  },
  created() {
      api.getActivityById({ id: this.$route.query.id }).then(res => {
          
      if (res.code == 100) {
       
        //基础设置 
        this.ruleForm1.eggName = res.data.eggName,  // 活动名称               
        this.ruleForm1.date = [ res.data.eggBeginTime, res.data.eggEndTime ] // 活动时间
        this.ruleForm1.eggEggPartaker = res.data.eggEggPartaker  // 1.所有粉丝 2.仅会员(持有会员卡的粉丝) 
        this.ruleForm1.eggPway = res.data.eggPway // 参与方式
        this.ruleForm1.eggMan = res.data.eggMan // 可参加抽奖的会员积分
        this.ruleForm1.eggKou = res.data.eggKou // 每次抽奖扣除积分
        this.ruleForm1.eggDescribe = res.data.eggDescribe  // 活动说明/描述
        this.ruleForm1.eggBeforeTxt = res.data.eggBeforeTxt   // 活动未开始提示
        this.ruleForm1.bgmSp = res.data.bgmSp  // 背景音乐名称
        this.ruleForm1.bgmSp = res.data.musicUrl  // 背景音乐链接
      
        //规则设置
            this.ruleForm2.eggCountOfDay = res.data.eggCountOfDay,   // 抽奖次数
            this.ruleForm2.eggCountOfAll = res.data.eggCountOfAll, // 抽奖总数

        //兑奖设置
            this.ruleForm3.eggCashDay = res.data.eggCashDay      // 兑奖期限
            this.ruleForm3.eggAddress = res.data.eggAddress     // 兑奖地址
            this.ruleForm3.eggCashWay = res.data.eggCashWay  // 兑奖方式
            this.ruleForm3.eggWinningTxt = res.data.eggWinningTxt // 兑奖提示
            this.ruleForm3.eggWinningNotice = res.data.eggWinningNotice // 中奖须知

        //奖项设置
        res.data.prizeSetList.forEach((item, idnex, arr)=>{
            item.eggPrizeType = item.eggPrizeType + ''
        });
         this.ruleForm4 = res.data.prizeSetList
      } else {
        this.$message.error("获取编辑数据失败");
      }
    });
    api.getPrizeType().then(data => {
          if (data.code == 100) {
            this.options = data.data;
          } else {
            this.$message.error('获取奖品类型失败');
          }
    })
  },
  filters: {
    prizeStatus(val) {
        if (val == 0) {
          val = "一等奖";
        }else if(val == 1){
          val = "二等奖";
        }else if(val == 2){
          val = "三等奖";
        }else if(val == 3){
          val = "四等奖";
        }else if(val == 4){
          val = "五等奖";
        }else if(val == 5){
          val = "六等奖";
        }  
        return val;
    },
     

  }
};
</script>
