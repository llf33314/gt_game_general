<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/openBox/index' }">拆礼盒</el-breadcrumb-item>  
      <el-breadcrumb-item>创建活动</el-breadcrumb-item>   
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
          <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
                <el-form-item label="活动名称：" prop="name">
                    <el-input class="w_demo" v-model="ruleForm1.name"></el-input>
                </el-form-item> 
                <el-form-item label="游戏时间：" prop="name1">
                    <el-date-picker class="w_demo" v-model="ruleForm1.name1" :picker-options="pickerOptions"  type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>  
                <el-form-item label="背景音乐：">
                    <div class="pd20 bb bw bgMusic">
                        <gt-material :prop="''" :isMusic="true" :index="2" btnContent="点击上传"  v-on:getChangeUrl="getMusic" width="72" height="72"></gt-material>
                        <span class="el-upload__tip grey ml20">{{ruleForm1.music}}</span> 
                        <div class="el-upload__tip grey" style="line-height:25px">
                            音频文件的格式为mp3、wma、wav,大小不超过3M
                        </div>
                    </div>
                </el-form-item>  
            <h1 class="mt30 mb20 pb10 bbtom">礼盒设置</h1> 
            <el-button type="primary" class="mb20" :disabled="this.ruleForm1.demolitionGiftBoxReqs.length>4" @click="adddemolitionGiftBoxReqs()">新增</el-button>  
            <span class="ml10 el-upload__tip grey">至少添加3-5个礼盒，礼盒图片建议尺寸270*270px</span>
            <el-table ref="multipleTable" :data="ruleForm1.demolitionGiftBoxReqs" tooltip-effect="dark"  style="max-width:1200px">
                <el-table-column label="礼盒类型">
                    <template slot-scope="scope">
                        <el-select  class="w100_demo"  v-model="scope.row.name1" placeholder="请选择"> 
                            <el-option label="自定义"     :value="0"></el-option> 
                            <el-option label="默认"       :value="1"></el-option>                             
                        </el-select>
                    </template>
                </el-table-column> 
                <el-table-column label="礼盒名称">
                    <template slot-scope="scope"> 
                        <el-input class="w150_demo" placeholder="20字以内"  :maxlength="20"   v-model="scope.row.giftName" v-if="scope.row.name1==0"></el-input>
                        <el-select  class="w150_demo"  v-model="scope.row.giftName"  @change="boxStyle(scope.$index)" placeholder="请选择" v-if="scope.row.name1==1"> 
                            <el-option label="矮方盒"  value="矮方盒"></el-option>
                            <el-option label="大方盒"  value="大方盒"></el-option>
                            <el-option label="矮圆盒"  value="矮圆盒"></el-option>
                            <el-option label="高圆盒"  value="高圆盒"></el-option>
                            <el-option label="大圆盒"  value="大圆盒"></el-option>      
                        </el-select>
                    </template>
                </el-table-column> 
                <el-table-column label="礼盒图片">
                  <template slot-scope="scope">
                      <gt-material prop="url" :url="scope.row.giftImg" v-on:getChangeUrl="getChangeUrlBox(scope.$index, $event)" width="60" height="60"></gt-material>
                  </template>
                </el-table-column> 
                <el-table-column label="礼盒音乐">
                  <template slot-scope="scope">
                       <el-select  class="w100_demo"  v-model="scope.row.giftSound" placeholder="请选择"> 
                            <el-option label="音乐一"  value="音乐一"></el-option> 
                            <el-option label="音乐二"  value="音乐二"></el-option> 
                            <el-option label="音乐三"  value="音乐三"></el-option> 
                            <el-option label="音乐四"  value="音乐四"></el-option> 
                            <el-option label="音乐五"  value="音乐五"></el-option>                             
                        </el-select>
                        <!-- <el-button size="small" type="primary">播放</el-button>
                        <el-button size="small" type="primary">暂停</el-button> -->
                  </template>
                </el-table-column> 
                 <el-table-column label="放置礼品">
                  <template slot-scope="scope">
                    <el-switch v-model="scope.row.award" on-text="开启" off-text="关闭" :on-value="1" :off-value="0">
                    </el-switch>
                  </template>
                </el-table-column> 
                <el-table-column label="操作">
                  <template slot-scope="scope">
                        <el-button class="gt-button-normal" v-show="scope.$index>2" @click="deldemolitionGiftBoxReqs(scope.$index)">删除</el-button>
                  </template>
                </el-table-column> 
             </el-table> 

            <h1 class="mt30 mb20 pb10 bbtom">广告设置</h1> 
            <el-button type="primary" class="mb20" @click="adddemolitionAdReqs()">新增</el-button>  
            <span class="ml10 el-upload__tip grey">1.仅支持多粉与翼粉开头的链接    2.广告图格式：1000*300px</span>
            <el-table ref="multipleTable" :data="ruleForm1.demolitionAdReqs" tooltip-effect="dark" style="max-width:1000px">
                <el-table-column label="广告链接">
                  <template slot-scope="scope" >
                        <el-input v-model="scope.row.hrefUrl">
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
        <div v-show="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" class="mt40 demo-ruleForm">
                <el-form-item label="关注二维码：" prop="followQrCode">
                  <gt-material prop="url" :url="ruleForm2.followQrCode" v-on:getChangeUrl="getChangeUrl2" width="72" height="72"></gt-material>
                  <span class="el-upload__tip grey ml10">上传1:1二维码，将会在活动规则中显示商家二维码</span>  
                </el-form-item>  
                <el-form-item label="游戏总数：" prop="manTotalChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manTotalChance"></el-input>次/人
                </el-form-item> 
                <el-form-item label="每天次数：" prop="manDayChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manDayChance"></el-input>次/人
                </el-form-item>    
                <el-form-item label="活动规则：" prop="actRule">
                    <el-input class="w_demo" :maxlength="300"  type="textarea" v-model="ruleForm2.actRule" :rows="3" placeholder="请填写活动规则"></el-input>
                    <span class="el-upload__tip grey ml10">300个字以内</span>   
                </el-form-item>   
            </el-form> 
        </div> 
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖时间：" prop="date">
                    <el-date-picker class="w_demo" v-model="ruleForm3.date" :picker-options="pickerOptions" type="daterange" placeholder="选择日期范围">
                    </el-date-picker> 
                </el-form-item>  
                <el-form-item label="兑奖方式：" prop="type">
                    <el-checkbox-group v-model="ruleForm3.type">
                        <el-checkbox label="1" name="1">到店领取</el-checkbox>
                        <el-checkbox label="2" name="2">邮寄</el-checkbox> 
                    </el-checkbox-group>
                </el-form-item> 
                <div   class="bb pt20 pb20 ml120 bw mb10" v-if="this.ruleForm3.type[0]==1||this.ruleForm3.type[1]==1" >
                    <span style="margin-left:30px;color: #333;" ><span style="color:#ff4949;margin-right:3px">*</span>兑奖地址：</span>
                    <el-button  class="mb10"  type="primary" @click="addList()">添加</el-button>  
                    <el-form-item v-for="(item,index) in ruleForm3.demolitionAddressReqs" :key="item.key"  :prop="'demolitionAddressReqs.' + index + '.address'" :rules="{required:true,validator:addrPass,trigger: 'blur'}">
                        <el-input class="w_demo mr10" prop="address" v-model="item.address" placeholder="请输入到店领取地址"></el-input> 
                        <span class="blueee"  @click="delList(index)" v-if="index!=0" >删除</span> 
                    </el-form-item>
                </div>  
                <el-form-item label="联系电话：" prop="phone">
                      <el-input  class="w_demo" v-model="ruleForm3.phone" placeholder="请输入联系电话(固话用-分割)"></el-input>    
                </el-form-item>  

                <el-form-item label="兑奖说明：" prop="cashPrizeInstruction">
                    <el-input class="bw" type="textarea" v-model="ruleForm3.cashPrizeInstruction" :rows="5" placeholder="请输入兑奖说明"></el-input> 
                </el-form-item>   
            </el-form> 
        </div>   
        <!-- 奖项设置 -->
        <div v-show="this.active==3" class="mt40">
            <div>
                <span style="color: #333; position:absolute;margin-top:0px;" >奖品说明：</span>
                <el-input type="textarea" class="bw ml120"  :maxlength="300"  :rows="3" placeholder="请输入兑奖说明" v-model="prizeSetInstruction">
                </el-input>
                <span class="el-upload__tip grey ml10">300字以内</span>
            </div>
            <div class="gt-gray-region mt20" style="color:#666;line-height:20px">
                <p>奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数</p>
                <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p> 
                <p>当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px</p> 
            </div>  
            <div class="mt20 mb20">
                <el-button   @click="addForm4()" :disabled='ruleForm4.length>8'  type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">最多设置9个奖项</span> 
            </div> 
               <el-tooltip placement="top" effect="light">
                <div slot="content">
                    当奖品为实物时，请上传实物图片
                </div>
                <span class="el-icon-warning" style="font-size:18px; margin-left:71%;z-index:11;position: absolute;margin-top: 12px; color:#ccc"></span> 
            </el-tooltip>
            <el-table ref="multipleTable" :data="ruleForm4" tooltip-effect="dark">
                <el-table-column label="奖品类型">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.name0" placeholder="请选择"> 
                                <el-option v-for="item in options" :key="item.value" :label="item.name"  :value="item.value">
                                </el-option>
                        </el-select>
                    </template>
                </el-table-column> 
                <el-table-column label="奖品单位">
                <template slot-scope="scope">
                    <el-input class="w20_demo" type="number" v-model="scope.row.name1" placeholder="数值应大于0"></el-input>
                </template>
                </el-table-column>
                <el-table-column label="奖品名称">
                <template slot-scope="scope">
                    <el-input class="w20_demo"  v-model="scope.row.name2" placeholder="请输入奖品名称"></el-input>
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
                <el-table-column label="奖品图片">
                    <template slot-scope="scope"  v-if="scope.row.name0==4||scope.row.name0=='实体物品'">  
                        <gt-material v-for="(item,index) in scope.row.name5" :key="index" :prop="scope" :sonIndex="index" selectType="radio" :url="item" @getChangeUrl="getAwardImgList" width="50" height="50" class="mr10"></gt-material>
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
            <el-button   @click="backUrl()" >返回</el-button>
            <el-button type="primary" @click="next1('ruleForm1')" v-if="this.active==0">保存</el-button> 
            <el-button type="primary" @click="next('ruleForm2')" v-if="this.active==1">保存</el-button>
            <el-button type="primary" @click="next('ruleForm3')" v-if="this.active==2">保存</el-button>   
            <el-button type="primary" @click="lastStep()"        v-if="this.active==3">保存</el-button>   
            <!-- <el-button type="primary" @click="submit()">打印</el-button>    -->
        </div> 
    </div>   
</div>
</section>
</template>
<script>
import {saveAct,getPrizeType,getAct}from './../api/api'
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
      active:0,
      ruleForm1: {
        name: "",
        name1: "", 
        music: "暂无上传音乐",
        musicUrl:"",
        demolitionAdReqs:[
          {hrefUrl:"",url:""},
          {hrefUrl:"",url:""}
        ],
        demolitionGiftBoxReqs:[
            {
            name1:0,
            giftName :"",
            giftImg:"",
            giftSound:"",
            award:"",
            }, {
            name1:0,
            giftName :"",
            giftImg:"",
            giftSound:"",
            award:"",
            }, {
            name1:0,
            giftName :"",
            giftImg:"",
            giftSound:"",
            award:"",
            },
            
        ],
      },
      // 时间的筛选
      pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;
          }
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        name1: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }] 
      },
      ruleForm2: {
        followQrCode: "", 
        manTotalChance:"",
        manDayChance:"",  
        actRule: "",  
      },
      rules2: {
        time: [ 
          { required: true,validator: timePass,  trigger: "blur,change" }
        ], 
        duihuan: [
          { required: true,  message: "请填写元宝兑换金币比例", trigger: "blur" } 
        ], 
        manTotalChance: [
          { required: true,  message: "请填写每人免费游戏次数", trigger: "blur" } 
        ], 
        manDayChance: [
          { required: true,  message: "请填写每人每天免费游戏次数", trigger: "blur" } 
        ],
        fenbi: [
          { required: true,  message: "请填写每天游戏小号的粉币或积分", trigger: "blur" } 
        ],
        msg: [ 
          { required: true}
        ], 
        actRule: [
          { required: true,  message: "请填写活动规则", trigger: "blur" } 
        ],
        msgModel: [
          { required: true, type: 'string',  message: "请选择消息模块", trigger: "blur" } 
        ], 
        },
        ruleForm3: {
            date:"",
            type: [], 
            demolitionAddressReqs:[{address:""},{address:""}],
            phone:"",
            cashPrizeInstruction:""
        },
        rules3: {
            address: [{ required: true }],
            date: [{ required: true,type: 'array', message: "兑奖时间不能为空" }],
            type: [{ required: true,type: 'array', message: "兑奖方式不能为空", trigger: "blur" }], 
            phone:[{ required: true,type: 'text', validator:iiPass,trigger: "blur" }],  
            cashPrizeInstruction: [{ required: true,message: "兑奖说明不能为空", trigger: "blur" }], 
        }, 
        prizeSetInstruction: "",
        options: [],
        ruleForm4: [{ 
            name0: "",
            name1: "",
            name2: "",
            name3: "",
            name4: "",
            name5:[] 
            },{ 
            name0: "",
            name1: "",
            name2: "",
            name3: "",
            name4: "" ,
            name5:[]
            }], 
            // 时间的筛选
        pickerOptions: {
            disabledDate(time) {return time.getTime() < Date.now() - 8.64e7;}
        }, 
    };
  },
  methods: {
     boxStyle(val){
        var k=val
        var giftNameid= this.ruleForm1.demolitionGiftBoxReqs[k].giftName 
        console.log(val,giftNameid);
        if(giftNameid=="矮方盒"){ 
            this.ruleForm1.demolitionGiftBoxReqs[k].giftImg=gameGift+'game/openBox/images/gift1.png'
        }else if(giftNameid=="大方盒"){
            this.ruleForm1.demolitionGiftBoxReqs[k].giftImg=gameGift+'game/openBox/images/gift2.png'
        }else if(giftNameid=="矮圆盒"){
            this.ruleForm1.demolitionGiftBoxReqs[k].giftImg=gameGift+'game/openBox/images/gift3.png'
        }else if(giftNameid=="高圆盒"){
            this.ruleForm1.demolitionGiftBoxReqs[k].giftImg=gameGift+'game/openBox/images/gift4.png'
        }else if(giftNameid=="大圆盒"){
            this.ruleForm1.demolitionGiftBoxReqs[k].giftImg=gameGift+'game/openBox/images/gift5.png'
        }
     },
      
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
        this.ruleForm3.demolitionAddressReqs.push({address:""});
    },
    delList(val){
        this.ruleForm3.demolitionAddressReqs.splice(val, 1);
    },
    upStep() {
      this.active--;
    },
    //礼盒设置的新增&删除
    adddemolitionGiftBoxReqs(){ 
      this.ruleForm1.demolitionGiftBoxReqs.push({ name1:0, name2:"", name3:"", name4:"", name5:"",})
    },
    deldemolitionGiftBoxReqs(val) { 
          this.ruleForm1.demolitionGiftBoxReqs.splice(val, 1); 
    },
    //背景音乐
    getMusic(e) {
        console.log(e);
        this.ruleForm1.music = e.music.name
        this.ruleForm1.musicUrl  = e.music.url
    },
    //广告设置的新增&删除
    adddemolitionAdReqs(){
      this.ruleForm1.demolitionAdReqs.push({hrefUrl:"",url:""},)
    },
    delLinks(val) { 
          this.ruleForm1.demolitionAdReqs.splice(val, 1); 
    },
    getChangeUrlBox(i,e) { 
      this.ruleForm1.demolitionGiftBoxReqs[i].giftImg=e.url
    }, 
    getChangeUrl(i,e) {  
      this.ruleForm1.demolitionAdReqs[i].url=e.url
    }, 
    getChangeUrl2(e) { 
      this.ruleForm2.followQrCode=e.url
    }, 
    //获取奖品类型-----------star
    getPrizeTypeData(){
        getPrizeType().then(data=>{
          if (data.code == 100) {
            console.log(data,1233);
            this.options=data.data
             console.log(this.options,444);
          } else {
              this.$message.error(data.msg);
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
    }, 
    addForm4(){ 
        this.ruleForm4.push({ name0:"", name1: "", name2: "", name3: "", name4: "", name5: []},)
    },
    delForm4(val){
        this.ruleForm4.splice(val, 1); 
    },
    // 添加实物图 
    addAwardImg(val) {
         JSON.parse(val.url).forEach(function (item, index, arr) {
               this.ruleForm4[val.prop.$index].name5.push(item.url)
         }, this)
    },
    // 删除实物图 
    getAwardImgList(val) { 
      if(!val.url) {
        this.ruleForm4[val.prop.$index].name5.splice(val.sonIndex, 1)
        return
      }
    }, 
    next1(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) { 
           this.checkStep1()
        } else {
         console.log("error submit!!");
        }
      });
    }, 
    checkStep1(){
        for(var i=0;i<this.ruleForm1.demolitionGiftBoxReqs.length;i++){
            if(!this.ruleForm1.demolitionGiftBoxReqs[i].giftName||!this.ruleForm1.demolitionGiftBoxReqs[i].giftImg||!this.ruleForm1.demolitionGiftBoxReqs[i].giftSound){
                this.$message.error("礼盒设置不能留空，请填写完整~");
                return false
            }
        }
        this.active=1
        
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
       if(sum!=100){
            this.$message.error("中奖概率之和必须等于100%");
        }else{
            this.submit();
        }
    },
    lastStep() {
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if(!this.ruleForm4[i].name0||!this.ruleForm4[i].name1||!this.ruleForm4[i].name2||!this.ruleForm4[i].name3||!this.ruleForm4[i].name4){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name1)) {
            this.$message.error("奖品单位填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name3)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        }else if (this.ruleForm4[i].name0==4&&this.ruleForm4[i].name5.length==0) { 
                this.$message.error("当奖品为实物时，请上传实物图片~");
                return false 
        }else{
            this.ruleForm4[i].name4 = parseFloat(this.ruleForm4[i].name4).toFixed(2);  
        }  
      }
       this.checkGL(); 
    },  
    //表单提交--------------------------------------star
    submit(){   
        //奖品
        var newPrize=[];
        if(this.ruleForm4){
            for(let i =0;i< this.ruleForm4.length;i++){
                var arr4={
                    imgInstruction :"",
                    type :this.ruleForm4[i].name0,//类型
                    prizeUnit :Number(this.ruleForm4[i].name1),//单位
                    prizeName :this.ruleForm4[i].name2,//名称
                    num :Number(this.ruleForm4[i].name3),//数量
                    probabiliy :this.ruleForm4[i].name4,  //概率
                    demolitionPrizeImgReqs:[]//图片
                }
                if (arr4.type == "粉币"){
                    arr4.type =1
                }else if (arr4.type == "手机流量"){
                    arr4.type =2 
                }else if (arr4.type == "手机话费"){
                    arr4.type =3 
                }else if (arr4.type == "实体物品"){
                    arr4.type =4 
                }
                else if (arr4.type == "积分"){
                    arr4.type =6
                }
                else if (arr4.type == "优惠券"){
                    arr4.type =7 
                } 
                if(arr4.type==4){
                    for(var j=0;j<this.ruleForm4[i].name5.length;j++){
                        var imgarr={
                            imgUrl:this.ruleForm4[i].name5[j]
                        }
                    arr4.demolitionPrizeImgReqs.push(imgarr)
                    } 
                } 
                newPrize.push(arr4)
            } 
        } 
        const data = {
            id:this.$router.history.current.query.id,
            //基础设置 
            name  : this.ruleForm1.name, 
            activityBeginTime: this.ruleForm1.name1[0], 
            activityEndTime  : this.ruleForm1.name1[1], 
            musicUrl  : this.ruleForm1.musicUrl ,  
            demolitionAdReqs:this.ruleForm1.demolitionAdReqs,
            //规则设置
            followQrCode  : this.ruleForm2.followQrCode, 
            manTotalChance: this.ruleForm2.manTotalChance, 
            manDayChance  : this.ruleForm2.manDayChance, 
            actRule       : this.ruleForm2.actRule,  
            //兑奖设置  
            cashPrizeBeginTime:this.ruleForm3.date[0], 
            cashPrizeEndTime  :this.ruleForm3.date[1], 
            receiveType       :this.ruleForm3.type.toString(), //兑奖方式
            seagoldAddressReqs:this.ruleForm3.demolitionAddressReqs,//兑奖地址 
            phone             :this.ruleForm3.phone, 
            cashPrizeInstruction:this.ruleForm3.cashPrizeInstruction,  
            //奖项设置 
            prizeSetInstruction:this.prizeSetInstruction, 
            demolitionPrizeReqs:newPrize, 

           
        };
        console.log(data,123); 
        saveAct(data).then(data=>{ 
          if (data.code == 100) {  
              this.$message({ message: "操作成功", type: "success"}); 
          } else { 
              this.$message.error(data.msg);
          }
        }).catch(() => { 
            this.$message({type: "info", message: "网络问题，请刷新重试~" });
        }); 
    },  
    backUrl(){
         window.history.go(-1);
    },
    test(){
        console.log(1122);
    },
     //初始化-------------------
    getActData(){
        var id=this.$router.history.current.query.id
        getAct(id).then(data=>{
          if (data.code == 100) {
              console.log(data,123)
            //基础设置
            this.ruleForm1=data.data
            this.ruleForm1.name1=[data.data.activityBeginTime,data.data.activityEndTime]
            if(data.data.musicUrl){
                this.ruleForm1.music = data.data.musicUrl.split("/")[data.data.musicUrl.split("/").length-1]
            } 
            //礼盒
            for(var i=0;i<data.data.demolitionGiftBoxReqs.length;i++){
                if(data.data.demolitionGiftBoxReqs[i].giftName=="矮方盒"){
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=1
                }else if(data.data.demolitionGiftBoxReqs[i].giftName=="大方盒"){
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=1
                }else if(data.data.demolitionGiftBoxReqs[i].giftName=="矮圆盒"){
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=1
                }else if(data.data.demolitionGiftBoxReqs[i].giftName=="矮方盒"){
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=1
                }else if(data.data.demolitionGiftBoxReqs[i].giftName=="高圆盒"){
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=1
                }else if(data.data.demolitionGiftBoxReqs[i].giftName=="大圆盒"){
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=1
                }else{
                    this.ruleForm1.demolitionGiftBoxReqs[i].name1=0
                }
            }
            //广告
            if(data.data.demolitionAdReqs){
                for(var i=0;i<data.data.demolitionAdReqs.length;i++){
                    this.ruleForm1.demolitionAdReqs[i].url=IMAGEURL1+data.data.demolitionAdReqs[i].url 
                }
            } 
            //规则设置 
            this.ruleForm2=data.data
            if(data.data.followQrCode){
                this.ruleForm2.followQrCode=IMAGEURL1+data.data.followQrCode
            } 
            //兑奖设置 
            this.ruleForm3=data.data
            this.ruleForm1.date=[data.data.cashPrizeBeginTime,data.data.cashPrizeEndTime]
            this.ruleForm3.type=data.data.receiveType.split(',')  
            //奖项设置
            this.prizeSetInstruction=data.data.prizeSetInstruction 
            var newPraise = [];//兑奖
            for (var i = 0; i < data.data.demolitionPrizeReqs.length; i++) {
                var newabc1 = {
                    name0  : data.data.demolitionPrizeReqs[i].type, 
                    name1  : data.data.demolitionPrizeReqs[i].prizeUnit, 
                    name2  : data.data.demolitionPrizeReqs[i].prizeName, 
                    name3  : String(data.data.demolitionPrizeReqs[i].num), 
                    name4  : data.data.demolitionPrizeReqs[i].probabiliy, 
                    name5  :[] 
                }; 
                if (newabc1.name0 == 1) {
                newabc1.name0  = "粉币";
                }else if(newabc1.name0  == 2){
                newabc1.name0  = "手机流量"; 
                }else if(newabc1.name0  == 3){
                newabc1.name0  = "手机话费"; 
                }else if(newabc1.name0  == 4){
                newabc1.name0  = "实体物品";
                }  else if(newabc1.name0  == 6){
                newabc1.name0  = "积分";
                } else if(newabc1.name0  == 7){
                newabc1.name0  = "优惠券";
                } 
                if(newabc1.name0=="实体物品"){
                    for(var j = 0; j < data.data.demolitionPrizeReqs[i].demolitionPrizeImgReqs.length; j++){
                        var imgarr={
                             url:window.IMAGEURL+data.data.demolitionPrizeReqs[i].demolitionPrizeImgReqs[j].imgUrl
                        }
                        newabc1.name5.push(imgarr.url)
                    }
                }
               newPraise.push(newabc1);  
            } 
            this.ruleForm4=newPraise  
            
          } else {
              this.$message.error(data.msg);
          }
        }).catch(() => {
            this.$message({type: "info", message: "网络问题，请刷新重试~" });
        }); 
    },
   },
  mounted() {
    this.getPrizeTypeData()
    this.getActData()
  }
};
</script>
