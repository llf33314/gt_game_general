<style lang="less"> 
</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/standStill/index' }">一站到底</el-breadcrumb-item>  
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
          <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
                <el-form-item label="活动名称：" prop="name">
                    <el-input class="w_demo" v-model="ruleForm1.name"></el-input>
                </el-form-item> 
                <el-form-item label="活动时间：" prop="name1">
                    <el-date-picker class="w_demo" v-model="ruleForm1.name1" :picker-options="pickerOptions"  type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>  
                 <el-form-item label="题库选择：" prop="library">
                    <el-select v-model="ruleForm1.library" placeholder="请选择"> 
                            <el-option v-for="item in Quesbank" :key="item.id" :label="item.bankName"  :value="item.id">
                            </el-option>
                    </el-select>
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
          </el-form> 
        </div>
        <!-- 规则设置 -->
        <div v-show="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" class="mt40 demo-ruleForm">
                <el-form-item label="游戏总数:" prop="manTotalChance">
                    <el-input class="w25_demo mr10"  type="number" v-model="ruleForm2.manTotalChance"></el-input>次/人
                </el-form-item> 
                <el-form-item label="每天次数："  type="number" prop="manDayChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manDayChance"></el-input>次/人
                </el-form-item>
                <el-form-item label="答题时间：" prop="gametime">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.gametime" type="number" placeholder="请输入答题时间"></el-input>秒
                </el-form-item>  
                <el-form-item label="奖励积分：" prop="jifen">
                    <el-input class="w25_demo mr10" placeholder="请输入每道题奖励的积分" v-model="ruleForm2.jifen"></el-input>分 
                </el-form-item>   
                <el-form-item label="活动规则：" prop="desc">
                    <el-input class="w_demo" :maxlength="300"  type="textarea" v-model="ruleForm2.desc" :rows="3" placeholder="请填写活动规则"></el-input>
                    <span class="el-upload__tip grey ml10">300个字以内</span>   
                </el-form-item>   
            </el-form> 
        </div>  
        <!-- 兑奖设置 -->
        <div v-show="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖期限：" prop="days">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm3.date" placeholder="请输入兑奖期限"></el-input>天
                    <span class="el-upload__tip grey">
                        从活动结束后开始计算
                    </span>
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
                    <el-form-item v-for="(item,index) in ruleForm3.addrRow" :key="item.key"  :prop="'addrRow.' + index + '.list'" :rules="{required:true,validator:addrPass,trigger: 'blur'}">
                        <el-input class="w_demo mr10" prop="list" v-model="item.list" placeholder="请输入到店领取地址"></el-input> 
                        <span class="blueee"  @click="delList(index)" v-if="index!=0" >删除</span> 
                    </el-form-item>
                </div>  
                <el-form-item label="联系电话：" prop="phone">
                      <el-input  class="w_demo" v-model="ruleForm3.phone" placeholder="请输入联系电话(固话用-分割)"></el-input>    
                </el-form-item>  

                <el-form-item label="兑奖说明：" prop="desc">
                    <el-input class="bw" type="textarea" v-model="ruleForm3.desc" :rows="5" placeholder="请输入兑奖说明"></el-input> 
                </el-form-item>   
            </el-form> 
        </div>
        <!-- 奖项设置 -->
        <div v-show="this.active==3" class="mt40"> 
            <div class="gt-gray-region mt20" style="color:#666;line-height:20px">
                <p>奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数</p>
                <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p> 
                <p>当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px</p> 
                <p>下列奖品根据排名由上至下顺序</p> 
            </div> 
            <div class="mt20 mb20">
                <el-button   @click="addForm4()" :disabled='ruleForm4.length>4'  type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">最多设置5个奖项</span> 
            </div> 
               <el-tooltip placement="top" effect="light">
                <div slot="content">
                    当奖品为实物时，请上传实物图片
                </div>
                <span class="el-icon-warning" style="font-size:18px; margin-left:67%;z-index:11;position: absolute;margin-top: 12px; color:#ccc"></span> 
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
                        <el-select v-model="scope.row.name2" v-if="scope.row.name0==7"   placeholder="请选择" @change="optionsData(scope.$index)"> 
                            <el-option v-for="item in memberOptions" :key="item.id"  :label="item.cardsName"  :value="item.id">
                            </el-option>
                        </el-select>  
                        <el-input v-else class="w20_demo"   v-model="scope.row.name2"></el-input> 
                    </template>
                </el-table-column>
                <el-table-column label="奖项数量">
                <template slot-scope="scope">
                    <el-input class="w20_demo"  type="number"  v-model="scope.row.name3" placeholder="数值应大于0"></el-input>
                </template>
                </el-table-column>
                <el-table-column label="奖品图片">
                    <template slot-scope="scope"   v-if="scope.row.name0==4||scope.row.name0=='实体物品'">  
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
            <el-button   @click="backUrl()">返回</el-button>
            <el-button type="primary" @click="submit()"          v-if="this.active==0||this.active==1">保存</el-button>   
            <el-button type="primary" @click="next('ruleForm3')" v-if="this.active==2">保存</el-button>   
            <el-button type="primary" @click="lastStep()"        v-if="this.active==3">保存</el-button>   
        </div> 
    </div>   
</div>
</section>
</template>
<script>
import { 
 saveAct,getQuesbank,getPrizeType,getAct,getMemberType}from './../api/api'
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
    return {
      active:0,  
      Quesbank:[],
      options:[],
      ruleForm1: {
        name: "",
        name1: "",
        library:"",
        musicUrl:"",//音乐链接
        music: "暂无上传音乐",         
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        name1: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }], 
        library: [{required: true, message: "请选择消息模块", }] 
      },
      ruleForm2: { 
        manTotalChance:"",
        manDayChance:"", 
        gametime:"", 
        jifen:"",
        desc: "",  
      },
      rules2: {          
        manTotalChance: [
          { required: true,  message: "请填写每人免费游戏次数", trigger: "blur" } 
        ], 
        manDayChance: [
          { required: true,  message: "请填写每人每天免费游戏次数", trigger: "blur" } 
        ],
        gametime: [ 
          { required: true, message: "请填写答题时间",trigger: "blur" }
        ], 
        jifen: [
          { required: true,  message: "请输入每道题奖励的积分", trigger: "blur" } 
        ], 
        desc: [
          { required: true,  message: "请填写活动规则", trigger: "blur" } 
        ] 
      },
      ruleForm3: {
        date:"",
        type: [], 
        addrRow:[{list:""},{list:""}],
        phone:"",
        desc:""
      }, 
      rules3: {
        list: [{ required: true }],
        date: [{ required: true,type: 'array', message: "兑奖时间不能为空" }],
        type: [{ required: true,type: 'array', message: "兑奖方式不能为空", trigger: "blur" }], 
        phone:[{ required: true,type: 'text', validator:iiPass,trigger: "blur" }], 
        desc: [{ required: true,message: "兑奖说明不能为空", trigger: "blur" }], 
      },
      // 时间的筛选
      pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;
          }
      }, 
      memberOptions:[],
      ruleForm4: [{ 
          name0: "",
          name1: "",
          name2: "",
          name3: "",
          name5:[] 
        },
        { 
          name0: "",
          name1: "",
          name2: "",
          name3: "",
          name5:[]
        }],    
    };
  },
  methods: {   
    getMusic(e) {
        console.log(e);
        this.ruleForm1.music = e.music.name
        this.ruleForm1.musicUrl = e.music.url
    },  
    //实物图
    getContentUrl(x,e) {
      if (e.url === "go_back") { return; } 
      console.log(e.url);
      let arr = JSON.parse(e.url); 
      var g =[];
      for(let i = 0; i < arr.length; i++) {
        arr[i].url = arr[i].url;
        g.push(arr[i])
      } 
      this.ruleForm4[x].name5=this.ruleForm4[x].name5.concat(g)
    }, 
    // 删除实物图
    handleRemove(file, fileList) {
         this.ruleForm4.name5 = fileList
    }, 
    addrPass(rule, value, callback) {
      if (!value) {
       callback(new Error("到店领取地址不能为空")); 
      }else {
        callback();
      }
    }, 
    addList(){
        this.ruleForm3.addrRow.push({list:""});
    },
    delList(val){
        this.ruleForm3.addrRow.splice(val, 1);
    },
    upStep() {
      this.active--;
    },
    //广告设置的新增&删除
    addlinks(){
      this.linksId++
      this.ruleForm1.links.push({id:this.linksId,url:"",img:""},)
    },
    delLinks(val) { 
          this.ruleForm1.links.splice(val, 1); 
    },
    getChangeUrl(i,e) { 
      //console.log(i)
      this.ruleForm1.links[i].img=e.url
    }, 
    getChangeUrl2(e) { 
      this.ruleForm2.code=e.url
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
    getChangeUrl4(i,e) {   
      this.ruleForm4[i].name5=e.url
    }, 
    addForm4(){ 
        this.ruleForm4.push({ name0:"", name1: "", name2: "", name3: "", name5: []},)
    },
    delForm4(val){
        this.ruleForm4.splice(val, 1); 
    },
    next(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) { 
          this.submit();
        } else {
         console.log("error submit!!");
        }
      });
    },
    lastStep() {
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if(!this.ruleForm4[i].name0||!this.ruleForm4[i].name1||!this.ruleForm4[i].name2||!this.ruleForm4[i].name3){
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
        }else if(this.ruleForm4[i].name0==4&&this.ruleForm4[i].name5.length>5){
                this.$message.error("实物图片最多上传5张~");
                return false
        }   
      }
      this.submit();
    }, 
    optionsData(val){
        for(var i=0;i<this.memberOptions.length;i++){
            if(this.memberOptions[i].id==this.ruleForm4[val].name2||this.ruleForm4[val].name2==this.memberOptions[i].id){
                this.ruleForm4[val].name6=this.memberOptions[i].cardsName
            } 
        } 
      }  ,
    //表单提交--------------------------------------star
    submit(){  
        //兑奖地址
        var newAddr=[];
        if(this.ruleForm3.addrRow){ 
            for(let i =0;i< this.ruleForm3.addrRow.length;i++){ 
                var arraddr={
                    address:this.ruleForm3.addrRow[i].list,  
                } 
                newAddr.push(arraddr)
            }    
        }
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
                    cardReceiveId:"",
                    standPrizeImgReqs:[]//图片
                }
                if(arr4.type==7){
                    arr4.prizeName=this.ruleForm4[i].name6//id
                    arr4.cardReceiveId=this.ruleForm4[i].name2//name
                }
                if(arr4.type==4){
                    for(var j=0;j<this.ruleForm4[i].name5.length;j++){
                        var imgarr={
                            imgUrl:this.ruleForm4[i].name5[j]
                        }
                    arr4.standPrizeImgReqs.push(imgarr)
                    } 
                } 
                newPrize.push(arr4)
            }  
        } 
        const data = {
            id:this.$router.history.current.query.id,
            //基础设置  
            actName           :this.ruleForm1.name, 
            activityBegintime :this.ruleForm1.name1[0],
            activityEndtime   :this.ruleForm1.name1[1],
            bankId            :this.ruleForm1.library, 
            musicUrl          :this.ruleForm1.musicUrl, 
            //规则设置
            manDayTotalQuesChance:Number(this.ruleForm2.manTotalChance), 
            manDayChance         :Number(this.ruleForm2.manDayChance), 
            answerTime           :Number(this.ruleForm2.gametime), 
            rightCount           :Number(this.ruleForm2.jifen), 
            actRule              :this.ruleForm2.desc,  
            //兑奖设置  
            standCashDay        :Number(this.ruleForm3.date),  
            receiveType         :this.ruleForm3.type.toString(), //兑奖方式
            phone               :this.ruleForm3.phone,  
            cashPrizeInstruction:this.ruleForm3.desc,  
            standAddressReqs    :newAddr, 
            //奖项设置  
            standPrizeReqs:newPrize,   
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
    //获取奖品类型-----------star
    getPrizeTypeData(){
        getPrizeType().then(data=>{
          if (data.code == 100) {
            console.log(data,1233);
            this.options=data.data 
          } else {
              this.$message.error(data.msg);
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
    },
    //获取奖品名称-----------star
    getMemberTypeData(){
        getMemberType().then(data=>{
          if (data.code == 100) { 
            this.memberOptions=data.data 
          } else {
              this.$message.error(data.msg);
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
    }, 
    //获取题库-----------star
    getQuesbankData(){
        getQuesbank().then(data=>{
          if (data.code == 100) {
            console.log(data,1233);
            this.Quesbank=data.data 
          } else {
              this.$message.error(data.msg);
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
    },
     //初始化-------------------
    getActData(){
        var id=this.$router.history.current.query.id
        getAct(id).then(data=>{
          if (data.code == 100) {
            //基础设置
            this.ruleForm1.name=data.data.actName
            this.ruleForm1.name1=[data.data.activityBegintime,data.data.activityEndtime]  
            this.ruleForm1.musicUrl=data.data.musicUrl  
            this.ruleForm1.library=Number(data.data.bankId) 
            if(data.data.musicUrl){
                this.ruleForm1.music = data.data.musicUrl.split("/")[data.data.musicUrl.split("/").length-1]
            }              
            //规则设置 
            this.ruleForm2.manTotalChance=String(data.data.manDayTotalQuesChance)
            this.ruleForm2.manDayChance  =String(data.data.manDayChance) 
            this.ruleForm2.gametime      =String(data.data.answerTime) 
            this.ruleForm2.jifen         =String(data.data.rightCount)
            this.ruleForm2.desc         =data.data.actRule
            //兑奖设置 
            this.ruleForm3.date=data.data.standCashDay
            this.ruleForm3.type=data.data.receiveType.split(',')
            this.ruleForm3.phone=data.data.phone
            this.ruleForm3.desc=data.data.cashPrizeInstruction 
            //兑奖地址  
            var newaddr = [];
            for (var i = 0; i < data.data.standAddressReqs.length; i++) {
                var newabc1 = {
                list  : data.data.standAddressReqs[i].address,  
                };
                newaddr.push(newabc1);  
            } 
            this.ruleForm3.addrRow= newaddr
            //奖项设置  
            var newPraise = [];//兑奖 
            for (var i = 0; i < data.data.standPrizeReqs.length; i++) {
                var newabc1 = {
                    name0  : data.data.standPrizeReqs[i].type, 
                    name1  : String(data.data.standPrizeReqs[i].prizeUnit), 
                    name2  : data.data.standPrizeReqs[i].prizeName, 
                    name3  : String(data.data.standPrizeReqs[i].num),  
                    name5  :[] ,
                    cardReceiveId  : data.data.standPrizeReqs[i].cardReceiveId,
                }; 
                if(newabc1.name0==4){
                    for(var j = 0; j < data.data.standPrizeReqs[i].loveArrowPrizeImgReqs.length; j++){
                        var imgarr={
                             url:window.IMAGEURL+data.data.standPrizeReqs[i].loveArrowPrizeImgReqs[j].imgUrl
                        }
                        newabc1.name5.push(imgarr.url)
                    }
                }
               newPraise.push(newabc1);  
            }  
            this.ruleForm4=newPraise 
            this.explain=data.data.cashPrizeInstruction  
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
    this.getQuesbankData()
    this.getActData()
    this.getMemberTypeData()
  }
};
</script>
