<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item  :to="{ path:'/luckTranslate/index' }">好运翻翻看</el-breadcrumb-item>  
      <el-breadcrumb-item>创建活动</el-breadcrumb-item>   
    </el-breadcrumb>  
    <div class="gt-content">
        <el-steps :active="active" :center="true" :align-center="true" class="bbtom pb20">
            <el-step title="基础设置"></el-step>
            <el-step title="规则设置"></el-step>
            <el-step title="兑奖设置"></el-step>
            <el-step title="奖项设置"></el-step>
            <el-step title="新建完成"></el-step>
        </el-steps>
        <!-- 基础设置 -->
        <div v-show="this.active==0" class="mt40">
            <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="160px" class="demo-ruleForm"> 
                <el-form-item label="活动名称：" prop="luckName">
                    <el-input class="w_demo" :maxlength="20"  placeholder="请输入活动名称"  v-model="ruleForm1.luckName"></el-input>
                    <span class="el-upload__tip grey" >
                       20个字数以内
                    </span>
                </el-form-item>
                 <el-form-item label="游戏时间：" prop="acttime">
                    <el-date-picker class="w_demo" v-model="ruleForm1.acttime"  :picker-options="pickerOptions" type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>   
                <el-form-item label="活动说明：" prop="luckBeforeTxt">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.luckBeforeTxt" :rows="3" placeholder="请输入活动说明"></el-input>
                    <span class="el-upload__tip grey" >
                        描述活动详情，能让粉丝了解此次活动
                    </span>
                </el-form-item> 

                 <el-form-item label="活动未开始提示：" prop="luckDescribe">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.luckDescribe" :rows="3" placeholder="如：活动尚未开始，敬请期待!"></el-input>
                    <span class="el-upload__tip grey" >
                        活动未开始提示限制在100个字数以内
                    </span>
                </el-form-item>  
                <el-form-item label="参与人员："  prop="luckLuckPartaker">
                    <el-radio-group v-model="ruleForm1.luckLuckPartaker">
                    <el-radio :label="1">所有粉丝</el-radio>
                    <el-radio :label="2">仅会员
                        <span class="el-upload__tip grey" >
                        (持有会员卡的粉丝)
                        </span>
                    </el-radio>
                    </el-radio-group>
                </el-form-item>
                <div class="pd20 bw pt10 bb ml150 mb20" v-show="ruleForm1.luckLuckPartaker==2">
                    <el-form-item label="参与方式：" label-width="100px" prop="luckPway">
                          <el-radio-group v-model="ruleForm1.luckPway">
                            <el-radio :label="1">所有会员不需要积分</el-radio><br><br>
                            <el-radio :label="2">会员卡积分满<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.luckPway!="2"' v-model="ruleForm1.name1"></el-input>即可参加（抽奖不扣除积分）</el-radio><br><br>
                            <el-radio :label="3">每次抽奖扣除<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.luckPway!="3"' v-model="ruleForm1.name2"></el-input>积分</el-radio><br><br>
                            <el-radio :label="4">会员卡积分满<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.luckPway!="4"' v-model="ruleForm1.name3"></el-input>分，
                                                 每次抽奖扣除<el-input class="w100_demo ml10 mt20 mr10" :disabled='ruleForm1.luckPway!="4"' v-model="ruleForm1.name4"></el-input>分</el-radio> 
                          </el-radio-group> 
                          
                    </el-form-item>
                </div> 
                <el-form-item label="背景音乐：">
                    <div class="pd20 bb bw bgMusic">
                        <gt-material :prop="''" :isMusic="true" :index="2" btnContent="点击上传"  v-on:getChangeUrl="getMusic" width="72" height="72"></gt-material>
                        <span class="el-upload__tip grey ml20">{{ruleForm1.music}}</span> 
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
                    <el-form-item label="游戏总数：" prop="luckCountOfDay">
                        <el-input class="w_demo mr10" type="number" v-model="ruleForm2.luckCountOfDay" placeholder="请输入每人抽奖总次数"></el-input> 次/人
                    </el-form-item>
                    <el-form-item label="每天次数：" prop="luckCountOfAll">
                        <el-input class="w_demo mr10" type="number"  v-model="ruleForm2.luckCountOfAll" placeholder="请输入每人/每天抽奖总数"></el-input>次/人
                    </el-form-item>   
            </el-form> 
        </div> 
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖期限：" prop="luckCashDay">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm3.luckCashDay" placeholder="请输入兑奖期限"></el-input>天
                    <span class="el-upload__tip grey">
                        从活动结束后开始计算
                    </span>
                </el-form-item>  
                
                <el-form-item label="兑奖地址：" prop="luckAddress">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm3.luckAddress" :rows="5" placeholder="请输入兑奖地址"></el-input>
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
                <el-table-column label="奖品类型">
                  <template slot-scope="scope">
                      <el-select v-model="scope.row.name0" placeholder="请选择"> 
                      <el-option label="粉币"      :value="1"></el-option>
                      <el-option label="手机流量"   :value="2"></el-option>
                      <el-option label="手机话费"   :value="3"></el-option>
                      <el-option label="实体物品"   :value="4"></el-option>
                      <el-option label="谢谢参与"   :value="5"></el-option> 
                      <el-option label="积分"       :value="6"></el-option> 
                      </el-select>
                  </template>
                </el-table-column> 
                <el-table-column label="奖品数额">
                  <template slot-scope="scope">
                      <el-input class="w20_demo" type="number" v-model="scope.row.name1" placeholder="数值应大于0"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖项名称">
                  <template slot-scope="scope">
                      <el-input class="w20_demo"  v-model="scope.row.name2" placeholder="请输入奖项名称"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖项数量">
                  <template slot-scope="scope">
                      <el-input class="w20_demo"  type="number"  v-model="scope.row.name3" placeholder="数值应大于0"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="中奖概率(%)">
                  <template slot-scope="scope">
                      <el-input class="w20_demo"  v-model="scope.row.name4" placeholder="0-100且保留两位小数"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="中奖人"> 
                     <template slot-scope="scope">
                      {{scope.row.nickname}}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                      <el-button class="gt-button-normal blue" @click="shoeDialogFans(scope.$index)">指定中奖人</el-button>
                      <el-button class="gt-button-normal"  v-if="scope.$index!=0"  @click="delForm4(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>  
            
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
        <div class="btnRow"  v-if="this.active!=5">
            <el-button   @click="upStep()" v-if="this.active!=0">上一步</el-button>
            <el-button type="primary" @click="next('ruleForm1')" v-if="this.active==0">下一步</el-button> 
            <el-button type="primary" @click="next('ruleForm2')" v-if="this.active==1">下一步</el-button>
            <el-button type="primary" @click="next('ruleForm3')" v-if="this.active==2">下一步</el-button>   
            <el-button type="primary" @click="lastStep()"     :disabled="this.isSubmit"    v-if="this.active==3">保存</el-button>   
            <!-- <el-button type="primary" @click="submit()">打印</el-button>    -->
        </div> 
        <!-- 选择粉丝弹窗 --> 
        <gt-Fans-detail  :visible.sync="dialogFans"   v-on:getFansData="getFansData"></gt-Fans-detail>  
</div>   
</div>
</section>
</template>
<script>
import { 
saveAct
}from './../api/api' 
export default {
  data() {
    let numPass = (rule, value, callback) => { 
        if(this.ruleForm1.luckPway==2&&this.ruleForm1.name1==''){
           callback(new Error("不能为空"));
        }else if(this.ruleForm1.luckPway==2&&this.ruleForm1.name1<0){
           callback(new Error("请输入大于0的整数")); 
        }else if(this.ruleForm1.luckPway==3&&this.ruleForm1.name2==''){
           callback(new Error("不能为空"));
        }else if(this.ruleForm1.luckPway==3&&this.ruleForm1.name2<0){
           callback(new Error("请输入大于0的整数")); 
        }else if((this.ruleForm1.luckPway==4&&this.ruleForm1.name3=='')||(this.ruleForm1.luckPway==4&&this.ruleForm1.name4=='')){
           callback(new Error("不能为空"));
        }else if(this.ruleForm1.luckPway==4&&this.ruleForm1.name3<0){
           callback(new Error("请输入大于0的整数")); 
        }else if(this.ruleForm1.luckPway==4&&this.ruleForm1.name4<0){
           callback(new Error("请输入大于0的整数")); 
        }else{
           callback(); 
        }          
    }; 
    let numberPass = (rule, value, callback) => { 
        if(!value){
            callback(new Error("每天抽奖次数不能为空")); 
        }else if(value<0){
           callback(new Error("请输入大于0的整数")); 
        }else{
           callback(); 
        }         
    }; 
    return {
      active:0,
      // 时间的筛选
      pickerOptions: {
        disabledDate(time) { return time.getTime() < Date.now() - 8.64e7; }
      },
      ruleForm1: {
        type:1,
        luckName: "",
        acttime: [], 
        luckLuckPartaker: 1,
        luckBeforeTxt: "",
        luckDescribe: "",
        music: "暂无上传音乐",
        musicUrl: "",
        luckPway:1,
        name1:"", 
        name2:"", 
        name3:"", 
        name4:""  
      },
      rules1: {
        luckName: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        luckBeforeTxt: [{ required: true, message: "活动说明不能为空", trigger: "blur" }],
        luckDescribe: [{ required: true, message: "活动未开始提示不能为空", trigger: "blur" }],
        luckLuckPartaker:[{ required: true, }],
        acttime: [{ required: true, type: "array", message: "开始时间不能为空", trigger: "blur" }] ,
        luckPway:[{validator:numPass, trigger: "blur" }]
      },
      ruleForm2: { 
        luckCountOfDay: "",
        luckCountOfAll: "" ,       
      },
      rules2: { 
        luckCountOfDay:[{required: true,validator:numberPass,trigger: "blur" }],
        luckCountOfAll:[{required: true,validator:numberPass,  trigger: "blur" }]
      },
      ruleForm3: {
        luckCashDay:"",
        luckAddress: "", 
      }, 
      rules3: {
        luckCashDay: [{ required: true,  message: "兑奖期限不能为空", trigger: "blur" }],
        luckAddress: [{ required: true, message: "兑奖地址不能为空", trigger: "blur" }]
      },  
      ruleForm4:[
        { 
          name0: "",
          name1: "",
          name2: "",
          name3: "",
          name4: "",
          nickname:"",
          openid:"",
        }
      ],
      dialogFans:false,
      key:0, 
      isSubmit:false,
    };
  },
  methods: { 
    shoeDialogFans(val){
        this.key=val
        this.dialogFans=true 
        console.log(this.key);
    }, 
    getFansData(e){ 
        console.log(e,'子组件的信息')
        this.dialogFans=false
        var k = this.key;
        var nickname = [];
        var openid = [];
        // console.log(k, 7788);
        for (var i = 0; i < e.length; i++) {
          var arr1 = {
            nickname:e[i].nickname,
            openid:e[i].openid
          };
          nickname.push(arr1.nickname);
          openid.push(arr1.openid);
        }
        this.ruleForm4[k].nickname = nickname + "";
        this.ruleForm4[k].openid = openid + ""; 
        // console.log(this.ruleForm4,852);
    }, 
    //保存活动
    submit(){
        if(this.isSubmit){
             this.$message({type: "info", message: "请不要重复提交~" });
        }else{ 
            //第一部分
            var luckMan="",luckKou="";
            if(this.ruleForm1.luckPway==2){
                luckMan = this.ruleForm1.name1
            }else if(this.ruleForm1.luckPway==3){
                luckKou = this.ruleForm1.name2 
            }else if(this.ruleForm1.luckPway==4){
                luckMan = this.ruleForm1.name3
                luckKou = this.ruleForm1.name4
            }
            //第四部分
            var newPrize=[];
            for(let i =0;i< this.ruleForm4.length;i++){
                var arr={
                    luckPrizeType   :this.ruleForm4[i].name0,
                    luckPrizeLimit  :Number(this.ruleForm4[i].name1),
                    luckPrizeName   :this.ruleForm4[i].name2, 
                    luckPrizeNums   :Number(this.ruleForm4[i].name3), 
                    luckPrizeChance :Number(this.ruleForm4[i].name4), 
                    nickname        :this.ruleForm4[i].nickname, 
                    openid          :this.ruleForm4[i].openid, 
                }
                newPrize.push(arr)
            } 
           console.log(newPrize,123)
           
            const data = {
                id:0,
                //基础设置 
                luckName     :this.ruleForm1.luckName, 
                luckBeginTime:this.ruleForm1.acttime[0], 
                luckEndTime  :this.ruleForm1.acttime[1], 
                luckBgm      :this.ruleForm1.musicUrl, 
                luckBgmName  :this.ruleForm1.music, 
                luckBeforeTxt:this.ruleForm1.luckBeforeTxt , 
                luckDescribe :this.ruleForm1.luckDescribe, //活动说明
                luckLuckPartaker:this.ruleForm1.luckLuckPartaker, 
                luckPway     :this.ruleForm1.luckPway , 
                luckKou      :luckKou, 
                luckMan      :luckMan, 
                //规则设置
                luckCountOfDay : this.ruleForm2.luckCountOfDay, 
                luckCountOfAll : this.ruleForm2.luckCountOfAll,
                //兑奖设置
                luckCashDay : Number(this.ruleForm3.luckCashDay), 
                luckAddress : this.ruleForm3.luckAddress, 
                //奖项设置 
                //name13:this.awardKey,
                luckDetailReqs:newPrize
            };
            console.log(data,123); 
            saveAct(data).then(data=>{
                this.isSubmit=true
                if (data.code == 100) {  
                    this.active=5
                } else {
                    this.isSubmit=false
                    this.$message.error(data.msg);
                }
            }).catch(() => {
                this.isSubmit=false
                this.$message({type: "info", message: "网络问题，请刷新重试~" });
            }); 
        }
    },  
    test() {
      console.log(123); 
      this.active=5
    },
    //背景音乐
    getMusic(e) {
        console.log(e);
        this.ruleForm1.music    = e.music.name
        this.ruleForm1.musicUrl = e.music.url
    }, 
    delForm4(index){
      this.ruleForm4.splice(index, 1);
    },
    addForm4(){  
      this.ruleForm4.push({name0: "", name1: "", name2: "", name3: "", name4: "",nickname:"",openid:"",})
    },
    upStep() {
      this.active--;
    }, 
    next(formName) { 
        this.$refs[formName].validate((valid) => { 
          if (valid) {
            this.active++; 
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    }, 
    lastStep() {
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if(!this.ruleForm4[i].name1||!this.ruleForm4[i].name2||!this.ruleForm4[i].name3||!this.ruleForm4[i].name4){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name1)) {
            this.$message.error("奖品数额填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name3)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        } 
      }
      this.checkGL(); 
    }, 
    //校验概率
    checkGL(){
        var arr1=[]; 
        for(let i=0;i<this.ruleForm4.length;i++){
           var  arr2={
                id: parseFloat(this.ruleForm4[i].name4).toFixed(2)
            }
            arr1.push(arr2.id) 
        } 
        var getSum=function(ar){
            var arr=ar;   
            var s=eval(arr.join("+")) 
                return  s; 
        };  
       var sum = getSum(arr1)
    //    console.log(sum,998);
       if(sum!=100){
            this.$message.error("中奖概率之和必须等于100%");
        }else{
            this.submit();
        }
    },  
    backUrl(){
         window.history.go(-1);
    },
  },
   
    filters: {
        sexStatus(val) {
            if (val == 2) {
            val = "女";
            }else if(val == 1){
            val = "男"; 
            }else if(val == 0){
                val = "未知"; 
            }
            return val;
        },
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
    }, 
};
</script>
