<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/lanternFestival/index' }">元宵点灯</el-breadcrumb-item>  
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
            <h1 class="mt30 mb20 pb10 bbtom" style="width:80%">广告设置</h1> 
            <el-button type="primary" class="mb20" @click="addlinks">新增</el-button>  
            <span class="ml10 el-upload__tip grey">1.仅支持多粉与一点揩油的链接    2.广告图格式：1000*300px</span>
            <el-table ref="multipleTable" :data="ruleForm1.advertisingPictureList" tooltip-effect="dark" style="width:80%">
                <el-table-column label="广告链接">
                  <template slot-scope="scope" >
                        <el-input v-model="scope.row.hrefUrl" :maxlength="100">
                          <template slot="prepend">Http://</template>
                        </el-input>
                  </template>
                </el-table-column> 
                <el-table-column label="选择图片">
                  <template slot-scope="scope">
                      <gt-material prop="url" :url="scope.row.url" v-on:getChangeUrl="getChangeUrl(scope.$index, $event)" width="60" height="60"></gt-material>
                  </template>
                </el-table-column> 
                <el-table-column label="操作">
                  <template slot-scope="scope">
                        <el-button class="gt-button-normal" @click="delLinks(scope.$index)">删除</el-button>
                  </template>
                </el-table-column> 
             </el-table>  
        </el-form> 
        </div>
        <!-- 规则设置 -->
        <div v-if="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" class="mt40 demo-ruleForm">
                <el-form-item label="关注二维码：" prop="followQrCode">
                  <gt-material prop="url" :url="ruleForm2.followQrCode" v-on:getChangeUrl="getChangeUrl2" width="72" height="72"></gt-material>
                  <span class="el-upload__tip grey ml10">上传1:1二维码，将会在活动规则中显示商家二维码</span>  
                </el-form-item>  
                <el-form-item label="背景音乐：">
                    <div class="pd20 bb bw bgMusic">
                        <gt-material class="va-m" :prop="''" :isMusic="true" btnContent="点击上传"  v-on:getChangeUrl="getMusic" width="72" height="72"></gt-material>
                        <span class="el-upload__tip c333 ml20">{{ruleForm2.bgmSp}}</span> 
                        <div class="el-upload__tip grey" style="line-height:25px">
                            音频文件的格式为mp3、wma、wav,大小不超过3M
                        </div>
                    </div>
                </el-form-item> 
                <el-form-item label="游戏总数：" prop="manTotalChance">
                    <el-input class="w25_demo mr10" type="number" placeholder="请输入游戏总数"  v-model="ruleForm2.manTotalChance" :maxlength="10" @keydown.native="$util.KeydownNumber"></el-input>次/人
                </el-form-item> 
                <el-form-item label="每天次数：" prop="manDayChance">
                    <el-input class="w25_demo mr10" type="number" placeholder="请输入每天游戏次数）" v-model="ruleForm2.manDayChance" :maxlength="10" @keydown.native="$util.KeydownNumber"></el-input>次/人
                </el-form-item>    
                <el-form-item label="游戏时长：" prop="gameTime">
                    <el-input class="w25_demo mr10" type="number" placeholder="请输入时长（60~100秒）" v-model.number="ruleForm2.gameTime" :maxlength="3" @keydown.native="$util.KeydownNumber"></el-input>秒
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
                <el-form-item label="兑奖时间：" prop="prizeDate">
                    <el-date-picker class="w_demo" v-model="ruleForm3.prizeDate" type="daterange" placeholder="选择日期范围">
                    </el-date-picker> 
                </el-form-item>  
                <el-form-item label="兑奖方式：" prop="receiveTypeList">
                    <el-checkbox-group v-model="ruleForm3.receiveTypeList">
                        <el-checkbox label="1" name="1">到店领取</el-checkbox>
                        <el-checkbox label="2" name="2">邮寄</el-checkbox> 
                    </el-checkbox-group>
                </el-form-item> 
                <div class="bb pt20 pb20 ml120 bw mb10" v-if="this.ruleForm3.receiveTypeList[0]==1||this.ruleForm3.receiveTypeList[1]==1" >
                    <span style="margin-left:30px;color: #333;" ><span style="color:#ff4949;margin-right:3px">*</span>兑奖地址：</span>
                    <el-button  class="mb10"  type="primary" @click="addList()">添加</el-button>  
                    <el-form-item v-for="(item,index) in ruleForm3.addressList" :key="item.key"  :prop="'addressList.' + index + '.list'" :rules="{required:true,validator:addrPass,trigger: 'blur'}">
                        <el-input class="w_demo mr10" prop="list" v-model="item.list" placeholder="请输入到店领取地址" :maxlength="100"></el-input> 
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
            <div>
                <span style="color: #333; position:absolute;margin-top:0px;" >奖品说明：</span>
                <el-input type="textarea" class="bw ml120"  :maxlength="300"  :rows="3" placeholder="请输入兑奖说明" v-model="prizeDescription">
                </el-input>
                <span class="el-upload__tip grey ml10">300字以内</span>
            </div> 
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
                <el-table-column label="奖品图片" :width="160">
                    <template slot-scope="scope"  v-if="scope.row.type==4">  
                        <gt-material v-for="(item,index) in scope.row.imgUrl" :key="index" :prop="scope" :sonIndex="index" selectType="radio" :url="item" @getChangeUrl="getAwardImgList" width="50" height="50" class="mr10"></gt-material>
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
    let Gametime = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入时长（60~100秒）"));
      } else if ( Number(value) && Number(value)< 60 || Number(value) && Number(value) > 100 ) {
        callback(new Error("请输入时长（60~100秒）"));
      } else {
        callback();
      }
    };
    return {
      active: 0,
      ruleForm1: {
        name: "",
        date: "", 
        advertisingPictureList:[
          { hrefUrl:"", url:"" },
          { hrefUrl:"", url:"" }
        ]
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        date: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }] 
      },
      ruleForm2: {
        followQrCode: "",
        bgmSp: '',
        musicUrl: '',
        manTotalChance:"",
        manDayChance:"", 
        gameTime: '',
        actRule: ""
      },
      rules2: {
        manTotalChance: [{ required: true,  message: "请填写每人免费游戏次数", trigger: "blur" }], 
        manDayChance: [{ required: true,  message: "请填写每人每天免费游戏次数", trigger: "blur" }], 
        gameTime: [{ type: 'number', required: true,  validator: Gametime, trigger: "blur" }],
        actRule: [{ required: true,  message: "请填写活动规则", trigger: "blur" }]
      },
      ruleForm3: {
        prizeDate:"",
        receiveTypeList: [], 
        addressList:[{list:""},{list:""}],
        phone:"",
        cashPrizeInstruction:""
      },
      rules3: {
        list: [{ required: true }],
        prizeDate: [{ required: true,type: 'array', message: "兑奖时间不能为空" }],
        receiveTypeList: [{ required: true,type: 'array', message: "兑奖方式不能为空", trigger: "blur" }], 
        phone:[{ required: true,type: 'text', validator:iiPass,trigger: "blur" }], 
        cashPrizeInstruction: [{ required: true,message: "兑奖说明不能为空", trigger: "blur" }]
      },
      prizeDescription: "",
      options1: [],
       options: [],
      ruleForm4: [{ 
          type: "",
          prizeUnit: "",
          prizeName: "",
          num: "",
          imgUrl:[]
        },
        { 
          type: "",
          prizeUnit: "",
          prizeName: "",
          num: "",
          imgUrl: []
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
    addList(){
        this.ruleForm3.addressList.push({list:""});
    },
    delList(val){
        this.ruleForm3.addressList.splice(val, 1);
    },
    upStep() {
      this.active--;
    },
    //广告设置的新增&删除
    addlinks(){
      this.ruleForm1.advertisingPictureList.push({ hrefUrl:"", url:"" })
    },
    delLinks(val) { 
          this.ruleForm1.advertisingPictureList.splice(val, 1); 
    },
    getChangeUrl(i,e) {  
      this.ruleForm1.advertisingPictureList[i].url = e.url
    }, 
    getChangeUrl2(e) { 
      this.ruleForm2.followQrCode=e.url
    },  
    // 添加实物图 
    addAwardImg(val) {
         JSON.parse(val.url).forEach(function (item, index, arr) {
               this.ruleForm4[val.prop.$index].imgUrl.push(item.url)
         }, this)
    },
    getAwardImgList(val) {
      // 删除图片
      if(!val.url) {
        this.ruleForm4[val.prop.$index].imgUrl.splice(val.sonIndex, 1)
        return
      }
    }, 
    addForm4(){ 
        this.ruleForm4.push({ type: "", prizeUnit: "", prizeName: "", num: "", imgUrl: [] })
    },
    delForm4(val){
        this.ruleForm4.splice(val, 1); 
    },
    next(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) { 
          this.active++;
        } else {
            return false
        }
      });
    }, 
    lastStep() {
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if(!this.ruleForm4[i].type||!this.ruleForm4[i].prizeUnit||!this.ruleForm4[i].prizeName||!this.ruleForm4[i].num){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].prizeUnit)) {
            this.$message.error("奖品单位填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].num)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        }else if (this.ruleForm4[i].type==4&&this.ruleForm4[i].imgUrl.length==0) { 
            this.$message.error("当奖品为实物时，请上传实物图片~");
            return false 
        }
      }
       this.submit();
    }, 
    getMusic(e) {
      this.ruleForm2.bgmSp = e.music.name
      this.ruleForm2.musicUrl = e.music.url
    },
    //表单提交--------------------------------------star
    submit(){
       //兑奖地址
        var newYearAddressReqs=[];
        if(this.ruleForm3.addressList){ 
            for(let i =0;i< this.ruleForm3.addressList.length;i++){ 
                newYearAddressReqs.push(this.ruleForm3.addressList[i].list)
            }    
        } 
        const data = {
            id :0,
            //基础设置 
            name  : this.ruleForm1.name, 
            activityBeginTime: this.ruleForm1.date[0], 
            activityEndTime  : this.ruleForm1.date[1], 
            advertisingPictureList: this.ruleForm1.advertisingPictureList, 
            //规则设置
            followQrCode  : this.ruleForm2.followQrCode, 
            bgmSp: this.ruleForm2.bgmSp, // 背景音乐名
            musicUrl: this.ruleForm2.musicUrl, // 背景音乐地址 
            manTotalChance: Number(this.ruleForm2.manTotalChance), 
            manDayChance  :  Number(this.ruleForm2.manDayChance), 
            gameTime: this.ruleForm2.gameTime, 
            actRule  : this.ruleForm2.actRule,  
            //兑奖设置 
            cashPrizeBeginTime: this.ruleForm3.prizeDate[0], 
            cashPrizeEndTime : this.ruleForm3.prizeDate[1], 
            receiveTypeList : this.ruleForm3.receiveTypeList, // 兑奖方式
            phone  : this.ruleForm3.phone,  
            cashPrizeInstruction : this.ruleForm3.cashPrizeInstruction,  
            addressList: newYearAddressReqs,
            //奖项设置 
            prizeDescription: this.prizeDescription,
            prizeSetList:  this.ruleForm4
        };
        api.addActivity(data).then(data=>{
          if (data.code == 100) { 
              this.active=5
          } else {
              this.$message.error(data.msg);
          }
        })
    },  
    backUrl(){
         window.history.go(-1);
    }
  },
  mounted() {
    // 获取奖品类型
    this.$api.getPrizeTypeOne().then(res => {
        if (res.code == 100) {
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
