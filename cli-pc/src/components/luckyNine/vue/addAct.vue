<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item @click.native="$util.ClickApply">互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/luckyNine/index' }">幸运九宫格</el-breadcrumb-item>   
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
        <div v-if="this.active==0" class="mt40">
          <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
                <el-form-item label="活动名称：" prop="name">
                    <el-input class="w_demo"  placeholder="请输入活动名称" v-model="ruleForm1.name" :maxlength="25"></el-input>
                </el-form-item> 
                <el-form-item label="活动时间：" prop="date">
                    <el-date-picker class="w_demo" v-model="ruleForm1.date" :editable="false"  type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>    
        </el-form> 
        </div>
        <!-- 规则设置 -->
        <div v-if="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" class="mt40 demo-ruleForm">
                <el-form-item label="关注二维码：" prop="followQrCode">
                  <gt-material prop="url" :url="ruleForm2.followQrCode" v-on:getChangeUrl="getChangeUrl2" width="72" height="72"></gt-material>
                  <span class="el-upload__tip grey ml10">上传1:1二维码，将会在活动规则中显示商家二维码</span>  
                </el-form-item>  
                <el-form-item label="游戏总数：" prop="manTotalChance">
                    <el-input class="w25_demo mr10" type="number" placeholder="请输入游戏总数"  v-model="ruleForm2.manTotalChance" :maxlength="10" @keydown.native="$util.KeydownNumber"></el-input>次/人
                </el-form-item> 
                <el-form-item label="每天次数：" prop="manDayChance">
                    <el-input class="w25_demo mr10" type="number" placeholder="请输入每天游戏次数）" v-model="ruleForm2.manDayChance" :maxlength="10" @keydown.native="$util.KeydownNumber"></el-input>次/人
                </el-form-item>    
                <el-form-item label="活动规则：" prop="actRule">
                    <el-input class="w_demo" :maxlength="300"  type="textarea" v-model="ruleForm2.actRule" :rows="3" placeholder="请填写活动规则"></el-input>
                    <span class="el-upload__tip grey ml10">300个字以内</span>   
                </el-form-item>   
            </el-form> 
        </div> 
        <!-- 兑奖设置 -->
        <div v-if="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖时间：" prop="PrizeDate">
                    <el-date-picker class="w_demo" v-model="ruleForm3.PrizeDate" type="daterange" placeholder="选择日期范围">
                    </el-date-picker> 
                </el-form-item>  
                <el-form-item label="兑奖方式：" prop="receiveTypeList">
                    <el-checkbox-group v-model="ruleForm3.receiveTypeList">
                        <el-checkbox label="1" name="1">到店领取</el-checkbox>
                        <el-checkbox label="2" name="2">邮寄</el-checkbox> 
                    </el-checkbox-group>
                </el-form-item> 
                <div   class="bb pt20 pb20 ml120 bw mb10" v-if="this.ruleForm3.receiveTypeList[0]==1||this.ruleForm3.receiveTypeList[1]==1" >
                    <span style="margin-left:30px;color: #333;" ><span style="color:#ff4949;margin-right:3px">*</span>兑奖地址：</span>
                    <el-button  class="mb10"  type="primary" @click="addList()">添加</el-button>  
                    <el-form-item v-for="(item,index) in ruleForm3.addressList" :key="index"  :prop="'addressList.' + index + '.list'" :rules="{required:true,validator:addrPass,trigger: 'blur'}">
                        <el-input class="w_demo mr10"  v-model="item.list" placeholder="请输入到店领取地址" :maxlength="100"></el-input> 
                        <span class="blueee"  @click="delList(index)" v-if="index!=0" >删除</span> 
                    </el-form-item>
                </div>  
                <el-form-item label="联系电话：" prop="phone">
                      <el-input  class="w_demo" v-model="ruleForm3.phone" placeholder="请输入联系电话(固话用-分割)" :maxlength="20" @keydown.native="$util.KeydownNumber"></el-input>    
                </el-form-item>  

                <el-form-item label="兑奖说明：" prop="cashPrizeInstruction">
                    <el-input class="bw" type="textarea" v-model="ruleForm3.cashPrizeInstruction" :rows="5" placeholder="请输入兑奖说明" :maxlength="200"></el-input> 
                </el-form-item>   
            </el-form> 
        </div>
        <!-- 奖项设置 -->
        <div v-if="this.active==3" class="mt40">
            <div class="gt-gray-region mt20" style="color:#666;line-height:20px">
                <p>奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数</p>
                <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p> 
                 <p>当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px</p> 
            </div> 
            <div class="mt20 mb20">
                <el-button   @click="addForm4()"  type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">下列奖品根据排名由上至下顺序分配</span> 
            </div> 
            <el-tooltip placement="top" effect="light">
                <div slot="content">
                    当奖品为实物时，请上传实物图片
                </div>
                <span class="el-icon-warning" style="font-size:18px; margin-left:67%;z-index:11;position: absolute;margin-top: 12px; color:#ccc"></span> 
            </el-tooltip> 
             <el-table ref="multipleTable" :data="ruleForm4" tooltip-effect="dark">
                <el-table-column label="奖品类型" :width="220">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.type" placeholder="请选择"> 
                                <el-option v-for="item in options" :key="item.value" :label="item.name"  :value="item.value">
                                </el-option>
                        </el-select>
                    </template>
                </el-table-column> 
                <el-table-column label="奖品单位"  :width="240">
                    <template slot-scope="scope">
                        <el-input class="w20_demo" type="number" v-model="scope.row.prizeUnit" placeholder="数值应大于0" :maxlength="10" @keydown.native="$util.KeydownNumber"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="奖品名称" :width="240">
                    <template slot-scope="scope">
                       <el-input class="w20_demo" v-if="scope.row.type != 7"  v-model="scope.row.prizeName" placeholder="请输入奖品名称" :maxlength="50"></el-input>
                       
                       <el-select v-model="scope.row.youhui" placeholder="请选择" v-else>
                            <el-option
                            v-for="item in options1"
                            :key="item.id"
                            :label="item.cardsName"
                            :value="item.id">
                            </el-option>
                        </el-select>
                     </template>
                </el-table-column>
                
                <el-table-column label="奖项数量" :width="240">
                    <template slot-scope="scope">
                        <el-input class="w20_demo"  type="number"  v-model="scope.row.num" placeholder="数值应大于0" :maxlength="10" @keydown.native="$util.KeydownNumber"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="中奖概率(%)" :width="200">
                  <template slot-scope="scope">
                      <el-input class="w160" type="number"  v-model="scope.row.probabiliy" placeholder="0-100且保留两位小数"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="奖品图片" :width="160">
                    <template slot-scope="scope"  v-if="scope.row.type==4">  
                        <gt-material v-for="(item,index) in scope.row.imgUrl " :key="index" :prop="scope" :sonIndex="index" selectType="radio" :url="item" @getChangeUrl="getAwardImgList" width="50" height="50" class="mr10"></gt-material>
                        <gt-material :prop="scope" selectType="select"  @getChangeUrl="addAwardImg" width="50" height="50" class="uploadBtn"></gt-material>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button class="gt-button-normal" v-if="scope.$index!=0" @click="delForm4(scope.$index)">删除</el-button>
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
            <el-button type="primary" @click="next('ruleForm1')" v-if="this.active==0">下一步1</el-button> 
            <el-button type="primary" @click="next('ruleForm2')" v-if="this.active==1">下一步2</el-button>
            <el-button type="primary" @click="next('ruleForm3')" v-if="this.active==2">下一步3</el-button>   
            <el-button type="primary" @click="lastStep()"        v-if="this.active==3">保存</el-button>   
        </div> 
    </div>      
</div>
</section>
</template>
<script>
import api from './../api/api'
export default {
  data() {
    let iiPass = (rule, value, callback) => {
      if (!this.ruleForm3.phone) {
        return callback(new Error("联系电话不能为空"));
      }else if(!(/^1[34578]\d{9}$/.test(value)) && !/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(value)){ 
        return callback("联系电话有误，请重填");  
        return false; 
      }  else {
        callback();
      }
    }; 
    let timePass = (rule, value, callback) => {
       const time = window.parseInt(value) 
       if (!time) {
        callback(new Error("游戏时长不能为空"));
       } else if (time<10 || time>30) {
        callback(new Error("游戏时长有误，请重填"));
      } else {
        callback();
      }
    }; 
   
    return {
      active: 0,
      ruleForm1: {
        name: "",
        date: ""
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        date: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }] 
      },
      ruleForm2: {
        followQrCode: "",
        manTotalChance:"",
        manDayChance:"", 
        actRule: ""
      },
      rules2: {
        manTotalChance: [
          { required: true,  message: "请填写每人免费游戏次数", trigger: "blur" } 
        ], 
        manDayChance: [
          { required: true,  message: "请填写每人每天免费游戏次数", trigger: "blur" } 
        ], 
        actRule: [
          { required: true,  message: "请填写活动规则", trigger: "blur" } 
        ],  
        
      },
      ruleForm3: {
        PrizeDate:"",
        receiveTypeList: [], 
        addressList:[{list:""}],
        phone:"",
        cashPrizeInstruction:""
      },
      rules3: {
        list: [{ required: true }],
        PrizeDate: [{ required: true,type: 'array', message: "兑奖时间不能为空" }],
        receiveTypeList: [{ required: true,type: 'array', message: "兑奖方式不能为空", trigger: "blur" }], 
        phone:[{ required: true,type: 'text', validator:iiPass,trigger: "blur" }], 
        cashPrizeInstruction: [{ required: true,message: "兑奖说明不能为空", trigger: "blur" }], 
      },
       options: [],
       options1: [],
      ruleForm4: [{ 
          type: "",  // 奖品类型
          prizeUnit: "",
          prizeName: "",
          num: "",
          probabiliy: "",
          imgUrl :[],
          youhui: ''
        },
        { 
          type: "",
          prizeUnit: "",
          prizeName: "",
          num: "",
          probabiliy: "",
          imgUrl : [],
          youhui: ''
        }],  
    };
  },
  methods: { 
    addrPass(rule, value, callback) {
      if (!value) {
       callback(new Error("到店领取地址不能为空"));
      }else {
        callback();
      }
    },
    phoneCheck(rule, value, callback) {
      if (!value) {
        callback(new Error("联系电话不能为空"));
      } else if (!value.match(/^1[3|4|5|6|7|8][0-9][0-9]{8}$/)) {
        callback(new Error("请输入正确的手机号码"));
      } else {
        callback();
      }
    },
    addList(){
        this.ruleForm3.addressList.push({list:""});
    },
    delList(val){
        this.ruleForm3.addressList.splice(val, 1);
    },
    upStep() {
      this.active--;
    },
    getChangeUrl2(e) { 
      this.ruleForm2.followQrCode = e.url
    },  
    // 添加实物图 
    addAwardImg(val) {
         JSON.parse(val.url).forEach(function (item, index, arr) {
               this.ruleForm4[val.prop.$index].imgUrl .push(item.url)
         }, this)
    },
    getAwardImgList(val) {
      // 删除图片
      if(!val.url) {
        this.ruleForm4[val.prop.$index].imgUrl .splice(val.sonIndex, 1)
        return
      }
    }, 
    addForm4(){ 
        this.ruleForm4.push({ type: "", prizeUnit: "", prizeName: "", num: "", imgUrl : []},)
    },
    delForm4(val){
        this.ruleForm4.splice(val, 1); 
    },
    next(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) { 
          this.active++;
        } else {
         console.log("error submit!!");
        }
      });
    }, 
    lastStep() {
      let percentage = 0
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if( !this.ruleForm4[i].type ||
            !this.ruleForm4[i].prizeUnit ||
            !this.ruleForm4[i].prizeName|| 
            !this.ruleForm4[i].num ||
            !this.ruleForm4[i].probabiliy
        ){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].prizeUnit)) {
            this.$message.error("奖品单位填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].num)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        }else if (this.ruleForm4[i].type==4&&this.ruleForm4[i].imgUrl .length==0) { 
            this.$message.error("当奖品为实物时，请上传实物图片~");
            return false 
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
    submit(){
       //兑奖地址
        var AddressReqs=[];
        if(this.ruleForm3.addressList){ 
            for(let i =0;i< this.ruleForm3.addressList.length;i++){ 
                AddressReqs.push(this.ruleForm3.addressList[i].list)
            }    
        } 
        const data = {
            id :0,
            //基础设置 
            name  : this.ruleForm1.name, 
            activityBeginTime: this.ruleForm1.date[0], 
            activityEndTime  : this.ruleForm1.date[1], 
            //规则设置
            followQrCode  : this.ruleForm2.followQrCode, 
            manTotalChance: Number(this.ruleForm2.manTotalChance), 
            manDayChance  :  Number(this.ruleForm2.manDayChance), 
            actRule  : this.ruleForm2.actRule,  
            //兑奖设置 
            cashPrizeBeginTime: this.ruleForm3.PrizeDate[0], 
            cashPrizeEndTime : this.ruleForm3.PrizeDate[1], 
            receiveTypeList : this.ruleForm3.receiveTypeList, // 兑奖方式
            phone  : this.ruleForm3.phone,  
            cashPrizeInstruction : this.ruleForm3.cashPrizeInstruction,  
            addressList : AddressReqs,
            //奖项设置 
            prizeSetList:  this.ruleForm4
        };
        api.addActivity(data).then(data=>{
          this.isSubmit=true
          if (data.code == 100) { 
              this.active=5
          } else {
              this.isSubmit=false
              this.$message.error(data.msg + "错误码：[" + data.code + "]");
          }
        }).catch(() => {
            this.isSubmit=false
            this.$message({type: "info", message: "网络问题，请刷新重试~" });
        }); 
    },  
    backUrl(){
         window.history.go(-1);
    }
  },
  mounted() {
    // 获取奖品类型
    this.$api.getPrizeTypeOne().then(res => {
        if (res.code == 100) {
            res.data.push({
                value: 5,
                name: "谢谢参与"
            })
            this.options = res.data
        } else {
            this.$message.error( res.msg || '获取奖品类型失败');
        }
    })

    // 获取优惠券
    this.$api.getCardReceviceList().then(res => {
         if (res.code == 100) {
            this.options1 = res.data
         } else {
            this.$message.error( res.msg || '获取优惠券列表失败');
         }
    })
  }
};
</script>
