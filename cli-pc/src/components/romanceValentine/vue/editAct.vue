<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/romanceValentine/index' }">浪漫七夕</el-breadcrumb-item>  
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
        <div v-if="this.active==0" class="mt40">
          <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
                <el-form-item label="活动名称：" prop="name">
                    <el-input class="w_demo" v-model="ruleForm1.name"></el-input>
                </el-form-item>
                <el-form-item label="游戏时间：" prop="name1">
                    <el-date-picker class="w_demo" v-model="ruleForm1.name1"   type="datetimerange" :picker-options="pickerOptions"  placeholder="请选择游戏时间范围">
                    </el-date-picker>
                </el-form-item>   
            <h1 class="mt30 mb20 pb10 bbtom">广告设置</h1> 
            <el-button type="primary" class="mb20" @click="addlinks()">新增</el-button>  
            <span class="ml10 el-upload__tip grey">1.仅支持多粉与翼粉开头的链接    2.广告图格式：1000*300px</span>
            <el-table ref="multipleTable" :data="ruleForm1.links" tooltip-effect="dark">
                <el-table-column label="广告链接">
                  <template slot-scope="scope" >
                        <el-input v-model="scope.row.url">
                          <template slot="prepend">Http://</template>
                        </el-input>
                  </template>
                </el-table-column> 
                <el-table-column label="选择图片">
                  <template slot-scope="scope">
                      <gt-material prop="url" :url="scope.row.imgUrl" v-on:getChangeUrl="getChangeUrl(scope.$index, $event)" width="60" height="60"></gt-material>
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
                <el-form-item label="关注二维码：" prop="code">
                  <gt-material prop="url" :url="ruleForm2.code" v-on:getChangeUrl="getChangeUrl2" width="72" height="72"></gt-material>
                  <span class="el-upload__tip grey ml10">上传1:1二维码，将会在活动规则中显示商家二维码</span>  
                </el-form-item> 
                <el-form-item label="游戏总数：" prop="manTotalChance">
                    <el-input class="w25_demo mr10" type="number" placeholder="请输入游戏总数" v-model="ruleForm2.manTotalChance"></el-input>次/人
                </el-form-item> 
                <el-form-item label="每天次数：" prop="manDayChance">
                    <el-input class="w25_demo mr10" type="number"   placeholder="请输入每天游戏次数" v-model="ruleForm2.manDayChance"></el-input>次/人
                </el-form-item>  
                <el-form-item label="活动规则：" prop="desc">
                    <el-input class="w_demo" :maxlength="300"  type="textarea" v-model="ruleForm2.desc" :rows="3" placeholder="请填写活动规则"></el-input>
                    <span class="el-upload__tip grey ml10">300个字以内</span>   
                </el-form-item>   
            </el-form> 
        </div> 
        <!-- 兑奖设置 -->
        <div v-if="this.active==2" class="mt40">
            <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="120px" class="mt40 demo-ruleForm">
                <el-form-item label="兑奖时间：" prop="date">
                    <el-date-picker class="w_demo" v-model="ruleForm3.date"  :picker-options="pickerOptions" type="daterange" placeholder="选择日期范围">
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
            <el-button   @click="backUrl()">返回</el-button>
            <el-button type="primary" @click="submit()" v-if="this.active==0">保存</el-button> 
            <el-button type="primary" @click="submit()" v-if="this.active==1">保存</el-button>
            <el-button type="primary" @click="next('ruleForm3')" v-if="this.active==2">保存</el-button>   
            <el-button type="primary" @click="lastStep()"        v-if="this.active==3">保存</el-button> 
        </div> 
    </div>   
</div>
</section>
</template>
<script>
import { 
 saveAct,getPrizeType,getAct
}from './../api/api'
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
       } else if (time<60 || time>100) {
        callback(new Error("游戏时长有误，请重填"));
      } else {
        callback();
      }
    }; 
    return {
      active: 0,
      ruleForm1: {
        name: "",
        name1: "", 
        links:[
          {url:"",imgUrl:""},
          {url:"",imgUrl:""}
        ]
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        name1: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }] 
      },
      ruleForm2: {
        code: "",
        time: "", 
        manTotalChance:"",
        manDayChance:"",  
        desc: "",  
      },
      rules2: {
        time: [ 
          { required: true,validator: timePass,  trigger: "blur,change" }
        ],  
        manTotalChance: [
          { required: true,  message: "请填写每人免费游戏次数", trigger: "blur" } 
        ], 
        manDayChance: [
          { required: true,  message: "请填写每人每天免费游戏次数", trigger: "blur" } 
        ], 
        desc: [
          { required: true,  message: "请填写活动规则", trigger: "blur" } 
        ], 
      },
      ruleForm3: {
        date:"",
        type: [], 
        addrRow:[{list:""},{list:""}],
        phone:"",
        desc:""
      },
            // 时间的筛选
      pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;
          }
      },
      rules3: {
        list: [{ required: true }],
        date: [{ required: true,type: 'array', message: "兑奖时间不能为空" }],
        type: [{ required: true,type: 'array', message: "兑奖方式不能为空", trigger: "blur" }], 
        phone:[{ required: true,type: 'text', validator:iiPass,trigger: "blur" }], 
        desc: [{ required: true,message: "兑奖说明不能为空", trigger: "blur" }], 
      },
      options: [],
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
          name5: []
        }],
              // 时间的筛选
      pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;
          }
      }, 
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
      this.ruleForm1.links.push({url:"",imgUrl:""},)
    },
    delLinks(val) { 
          this.ruleForm1.links.splice(val, 1); 
    },
    getChangeUrl(i,e) { 
      //console.log(i)
      this.ruleForm1.links[i].imgUrl=e.url
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
    getAwardImgList(val) {
      // 删除图片
      if(!val.url) {
        this.ruleForm4[val.prop.$index].name5.splice(val.sonIndex, 1)
        return
      }
    }, 
    addForm4(){ 
        this.ruleForm4.push({name0: "", name1: "", name2: "", name3: "", name5: []},)
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
        console.log(this.ruleForm4,1243)
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
        }else{
            this.ruleForm4[i].name4 = parseFloat(this.ruleForm4[i].name4).toFixed(2);  
        }  
      }
      this.submit(); 
    }, 
    //表单提交--------------------------------------star
    submit(){ 
        //广告
        var newadv=[];
        for(let i =0;i< this.ruleForm1.links.length;i++){ 
            var arr={
                hrefUrl:this.ruleForm1.links[i].url, 
                url:this.ruleForm1.links[i].imgUrl, 
            } 
            newadv.push(arr)
        } 
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
                    prizeName :this.ruleForm4[i].name1,//名称
                    num :Number(this.ruleForm4[i].name3),//数量
                    // probabiliy :this.ruleForm4[i].name4,  //概率
                    qixiPrizeImgReqs :[]//图片
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
                    arr4.qixiPrizeImgReqs .push(imgarr)
                    } 
                } 
                newPrize.push(arr4)
            } 
        } 
        const data = {
            id:this.$router.history.current.query.id,
            //基础设置 
            name             : this.ruleForm1.name, 
            activityBeginTime: this.ruleForm1.name1[0],
            activityEndTime  : this.ruleForm1.name1[1], 
            qixiAdReqs       : newadv, 
            //规则设置 
            followQrCode     : this.ruleForm2.code, 
            manTotalChance   : Number(this.ruleForm2.manTotalChance), 
            manDayChance     : Number(this.ruleForm2.manDayChance), 
            actRule          : this.ruleForm2.desc,   
            //兑奖设置  
            cashPrizeBeginTime:this.ruleForm3.date[0], 
            cashPrizeEndTime  :this.ruleForm3.date[1], 
            receiveType       :this.ruleForm3.type.toString(), //兑奖方式
            phone             :this.ruleForm3.phone,  
            cashPrizeInstruction :this.ruleForm3.desc,  
            qixiAddressReqs      :newAddr, 
            //奖项设置  
            qixiPrizeReqs        :newPrize,   
        };
        console.log(data,123); 
        saveAct(data).then(data=>{ 
          if (data.code == 100) {  
             this.$message({ message: "操作成功", type: "success"}); 
          } else { 
              this.$message.error(data.msg + "错误码：[" + data.code + "]");
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
    geteditData(){
        var id=this.$router.history.current.query.id
        getAct(id).then(data=>{
            if (data.code == 100) {                
            console.log(data,1233); 
            //基础设置
            this.ruleForm1.name=data.data.name
            this.ruleForm1.name1=[data.data.activityBeginTime,data.data.activityEndTime] 
            //广告设置 
            var newadv = [];//兑奖地址 
            if(data.data.qixiAdReqs.length!=0){  
                for (var i = 0; i < data.data.qixiAdReqs.length; i++) {
                    var newabc1 = {
                        url  : data.data.qixiAdReqs[i].hrefUrl,  
                        imgUrl  : window.IMAGEURL+data.data.qixiAdReqs[i].url,  
                    };
                    newadv.push(newabc1);  
                }  
            }
            this.ruleForm1.links= newadv 
            //规则设置 
            this.ruleForm2.code=window.IMAGEURL+data.data.followQrCode
            this.ruleForm2.manTotalChance=String(data.data.manTotalChance)
            this.ruleForm2.manDayChance=String(data.data.manDayChance)
            this.ruleForm2.desc=data.data.actRule  
            //兑奖设置 
            this.ruleForm3.date=[data.data.cashPrizeBeginTime,data.data.cashPrizeEndTime]
            this.ruleForm3.type=data.data.receiveType.split(',')
            this.ruleForm3.phone=data.data.phone
            this.ruleForm3.desc=data.data.cashPrizeInstruction  
            //兑奖地址  
            var newaddr = [];
            for (var i = 0; i < data.data.qixiAddressReqs.length; i++) {
                var newabc1 = {
                list  : data.data.qixiAddressReqs[i].address,  
                };
                newaddr.push(newabc1);  
            } 
            this.ruleForm3.addrRow= newaddr
            //奖项设置 
            var newPraise = [];//兑奖地址
            for (var i = 0; i < data.data.qixiPrizeReqs.length; i++) {
                var newabc1 = {
                    name0  : data.data.qixiPrizeReqs[i].type, 
                    name1  : data.data.qixiPrizeReqs[i].prizeUnit, 
                    name2  : data.data.qixiPrizeReqs[i].prizeName, 
                    name3  : String(data.data.qixiPrizeReqs[i].num),  
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
                    for(var j = 0; j < data.data.qixiPrizeReqs[i].qixiPrizeImgReqs.length; j++){
                        var imgarr={
                             url:window.IMAGEURL+data.data.qixiPrizeReqs[i].qixiPrizeImgReqs[j].imgUrl
                        }
                        newabc1.name5.push(imgarr.url)
                    }
                } 
               newPraise.push(newabc1);  
            } 
            this.ruleForm4=newPraise
            } else {
                this.$message.error(data.msg + "错误码：[" + data.code + "]");
            }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
    },
    //获取奖品类型-----------star
    getPrizeTypeData(){
        getPrizeType().then(data=>{
            if (data.code == 100) { 
                this.options=data.data
                console.log(this.options,'获取奖品类型');
            } else {
                this.$message.error(data.msg + "错误码：[" + data.code + "]");
            }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
    }, 
  },
  mounted() {
    this.getPrizeTypeData()
    this.geteditData()
  }
};
</script>
