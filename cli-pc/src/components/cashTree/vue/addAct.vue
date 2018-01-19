
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/cashTree/index' }">摇钱树</el-breadcrumb-item>  
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
          <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
                <el-form-item label="活动名称：" prop="name">
                    <el-input class="w_demo" v-model="ruleForm1.name"></el-input>
                </el-form-item> 
                <el-form-item label="游戏时间：" prop="gtTime">
                    <el-date-picker class="w_demo" v-model="ruleForm1.gtTime"   type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>  
            <h1 class="mt30 mb20 pb10 bbtom">广告设置</h1> 
            <el-button type="primary" class="mb20" @click="addlinks()" :disabled="this.ruleForm1.links.length>2">新增</el-button>  
            <span class="ml10 el-upload__tip grey mr10">1.仅支持多粉与翼店开头的链接</span>
            <span class="ml10 el-upload__tip grey mr10">2.广告图格式：1000*300px</span>
            <span class="ml10 el-upload__tip grey mr10">3.最多添加3个广告</span>
            <el-table ref="multipleTable" :data="ruleForm1.links" tooltip-effect="dark">
                <el-table-column label="广告链接">
                    <template slot-scope="scope" >
                        <el-input v-model="scope.row.url" :class="{ 'bd1px-red': checked && !scope.row.url}">
                            <template slot="prepend">Http://</template>
                        </el-input>
                    </template>
                </el-table-column> 
                <el-table-column label="选择图片">
                    <template slot-scope="scope">
                        <gt-material prop="url" :url="scope.row.imgUrl" v-on:getChangeUrl="getChangeUrl(scope.$index, $event)" width="60" height="60" :class="{ 'bd1px-red': checked && !scope.row.imgUrl}"></gt-material>
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
                <el-form-item label="游戏时长：" prop="time">
                    <el-input class="w_demo" v-model="ruleForm2.time" type="number" placeholder="请输入游戏时长"></el-input>
                    <span class="el-upload__tip grey ml10">设置时间为10~30秒</span>  
                </el-form-item> 
                <el-form-item label="一元宝兑换：" prop="duihuan">
                    <el-input class="w_demo" v-model="ruleForm2.duihuan" placeholder="请输入元宝兑换金币比例"></el-input>
                </el-form-item>
                <el-form-item label="免费游戏：" prop="manTotalChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manTotalChance"></el-input>次/人
                </el-form-item> 
                <el-form-item label="免费次数：" prop="manDayChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manDayChance"></el-input>次/人
                </el-form-item>
                <el-form-item label="游戏消耗：" prop="fenbi">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.fenbi"></el-input>粉币
                    <span class="ml20 mr20"> 或</span>
                    <el-input class="w25_demo ml10 mr10" v-model="ruleForm2.jifen"></el-input>积分
                </el-form-item>  
                 
                <el-form-item label="中奖消息：" prop="isMsgTemplate">
                  <el-switch on-text="开启" :on-value="1" off-text="关闭" :off-value="0" v-model="ruleForm2.isMsgTemplate"></el-switch>
                  <span class="el-upload__tip grey">
                        <el-tooltip placement="right" effect="light">
                        <div slot="content">
                            1.开通消息模块功能;2.公众号平台添加消
                            <br/>息模块;3.同步模块到多粉平台;4.选取相
                            <br/>应的消息模块；如有问题,请联系客服！
                        </div>
                        <span class="el-icon-warning  ml10" style="font-size:16px;position: absolute;top: 11px;"></span> 
                        </el-tooltip> 
                    </span> 
                </el-form-item>
                <div class="bb bw pt20 mb20 ml150" v-if="ruleForm2.isMsgTemplate==1">
                    <el-form-item class="Prompt" prop="msgTemplateId" label-width="10px" >                       
                            <el-select v-model="ruleForm2.msgTemplateId" placeholder="请选择模块"  >
                                <el-option v-for="(item, index) in textArray"  :key="index" :label="item.title" :value="item.id"></el-option>
                            </el-select>
                    </el-form-item>
                </div>  
                <el-form-item label="活动规则：" prop="desc">
                    <el-input class="w_demo" :maxlength="300"  type="textarea" v-model="ruleForm2.desc" :rows="3" placeholder="请填写活动规则"></el-input>
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
            <div>
                <span style="color: #333; position:absolute;margin-top:0px;" >设置奖品说明：</span>
                <el-input type="textarea" class="bw ml120"  :maxlength="300"  :rows="3" placeholder="请输入兑奖说明" v-model="explain">
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
                    <template slot-scope="scope"  v-if="scope.row.name0==4">  
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
            <el-button   @click="upStep()" v-if="this.active!=0">上一步</el-button>
            <el-button type="primary" @click="next('ruleForm1')" v-if="this.active==0">下一步</el-button> 
            <el-button type="primary" @click="next('ruleForm2')" v-if="this.active==1">下一步</el-button>
            <el-button type="primary" @click="next('ruleForm3')" v-if="this.active==2">下一步</el-button>   
            <el-button type="primary" @click="lastStep()"        v-if="this.active==3">保存</el-button>   
        </div> 
    </div>   
</div>
</section>
</template>
<script>
import { saveAct,getTemplateList,getPrizeType}from './../api/api'
export default {
  data() {
    //联系电话===============================================
        let iiPass = (rule, value, callback) => {
        if (!this.ruleForm3.phone) {
            return callback(new Error("联系电话不能为空"));
        } else if (
            !/^1[34578]\d{9}$/.test(value) &&
            !/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(value)
        ) {
            return callback("联系电话有误，请重填");
            return false;
        } else {
            callback();
        }
        };
    //游戏时长===================================================
        let timePass = (rule, value, callback) => {
        const time = window.parseInt(value);
        if (!time) {
            callback(new Error("游戏时长不能为空"));
        } else if (time < 10 || time > 30) {
            callback(new Error("游戏时长有误，请重填"));
        } else {
            callback();
        }
        };
    return {
    active: 0,
    //第一步-------------------------------------
        ruleForm1: {
            name: "",
            gtTime: "",
            name4: "",
            links:[
                {url:"",imgUrl:""},
                {url:"",imgUrl:""}
            ]
        },
        rules1: {
            name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
            gtTime: [{ required: true, type: "array", message: "游戏时间不能为空", trigger: "blur" }]
        },
        checked: false, // 是否检验table的内容
    //第二步-------------------------------------
        ruleForm2: {
            followQrCode: "",
            time: "",
            duihuan: "",
            manTotalChance: "",
            manDayChance: "",
            fenbi: "",
            jifen: "",
            desc: "",
            msgTemplateId: "",
            isMsgTemplate: 0
        },
        rules2: {
            time: [{ required: true, validator: timePass, trigger: "blur,change" }],
            duihuan: [{ required: true, message: "请填写元宝兑换金币比例", trigger: "blur" }],
            manTotalChance: [
                { required: true, message: "请填写每人免费游戏次数", trigger: "blur" }
            ],
            manDayChance: [
                { required: true, message: "请填写每人每天免费游戏次数", trigger: "blur" }
            ],
            fenbi: [
                { required: true, message: "请填写每天游戏小号的粉币或积分", trigger: "blur" }
            ],
            isMsgTemplate: [{ required: true }],
            desc: [{ required: true, message: "请填写活动规则", trigger: "blur" }],
            msgTemplateId: [
                {required: true,message: "请选择消息模块"}
            ]
        },
        textArray:[],//中奖数组
    //第三步-------------------------------------
        ruleForm3: {
            date: "",
            type: [],
            addrRow: [{ list: "" }, { list: "" }],
            phone: "",
            desc: ""
        },
        rules3: {
            list: [{ required: true }],
            date: [{ required: true, type: "array", message: "兑奖时间不能为空" }],
            type: [
            {
                required: true,
                type: "array",
                message: "兑奖方式不能为空",
                trigger: "blur"
            }
            ],
            phone: [
            { required: true, type: "text", validator: iiPass, trigger: "blur" }
            ],
            desc: [{ required: true, message: "兑奖说明不能为空", trigger: "blur" }]
        },
        // 时间的筛选
        pickerOptions: {
            disabledDate(time) {
                return time.getTime() < Date.now() - 8.64e7;
            }
        },
    //第四部======================================
        explain: "",
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
    };
  },
  methods: {
    //兑奖地址===============================================
        addrPass(rule, value, callback) {
        if (!value) {
            callback(new Error("到店领取地址不能为空"));
        } else {
            callback();
        }
        },

    //兑奖地址添加===============================================
        addList() {
        this.ruleForm3.addrRow.push({ list: "" });
        },
    //兑奖地址删除===============================================
        delList(val) {
        this.ruleForm3.addrRow.splice(val, 1);
        },
    //步骤条
        upStep() {
        this.active--;
        },
    //第一步广告设置的新增======================================
        addlinks(){ 
            this.ruleForm1.links.push({url:"",imgUrl:""},)
        },
    //第一步广告设置删除======================================
        delLinks(val) { 
            this.ruleForm1.links.splice(val, 1); 
        },
    //基础设置--选择图片================================
        getChangeUrl(i,e) { 
            this.ruleForm1.links[i].imgUrl=e.url
        }, 
    //获取公众号消息模板列表==================================
      fetchData() {
        getTemplateList().then(result => {
            if (result.code == 100) {
            this.textArray = this.$util.isArray(result.data);
          } else {
            this.$message.error("获取评论列表失败");
          }
        })
      },
    //规则设置--关注二维码================================
        getChangeUrl2(e) {
        this.ruleForm2.followQrCode = e.url;
        },
    //奖项设置--奖品图片==============================================
        getChangeUrl4(i, e) {
        this.ruleForm4[i].name4 = e.url;
        },
    // 添加实物图 
        addAwardImg(val) {
            JSON.parse(val.url).forEach(function (item, index, arr) {
                this.ruleForm4[val.prop.$index].name5.push(item.url)
            }, this)
        },
    // 删除图片
        getAwardImgList(val) {
        if(!val.url) {
            this.ruleForm4[val.prop.$index].name5.splice(val.sonIndex, 1)
            return
        }
        }, 
    //奖项设置--新增奖品==============================================
        addForm4() {
        this.ruleForm4.push({
            name0: "",
            name1: "",
            name2: "",
            name3: "",
            name4: ""
        });
        },
    //奖项设置--删除奖品==============================================
        delForm4(val) {
        this.ruleForm4.splice(val, 1);
        },
    //获取奖品类型-----------star=====================================
        getPrizeTypeData(){
            getPrizeType().then(data=>{
                if (data.code == 100) {
                console.log(data,1233);
                this.options=data.data
                    console.log(this.options,'获取奖品类型');
                } else {
                    this.$message.error(data.msg);
                }
            }).catch(() => {
                this.$message({ type: "info", message: "网络问题，请刷新重试~" });
            }); 
        },
    //步骤条--下一步------------------------------------------------
        next(formName) {
            this.checked=true;
            console.log(this.ruleForm1.links,8787848548)
            this.$refs[formName].validate(valid => {
            if (valid) {  
                if(formName=='ruleForm1'){
                    console.log(this)
                    for(let i =0;i< this.ruleForm1.links.length;i++){      
                        if(!this.ruleForm1.links[i].imgUrl){
                            this.$message.error('图片不能为空 ');
                            return false;
                        }
                        if(!this.ruleForm1.links[i].url){
                            this.$message.error('广告链接不能为空');
                            return false;
                        }
                    }
                }    
            this.active++;
            } else {
            console.log("error submit!!");
            }
        });
        },
    //最后保存=====================================================
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
        submit() {
            //广告设置
                var newadv=[];
                for(let i =0;i< this.ruleForm1.links.length;i++){ 
                    var arr={
                        hrefUrl:this.ruleForm1.links[i].url, 
                        url:this.ruleForm1.links[i].imgUrl, 
                    } 
                    newadv.push(arr)
                } 
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
            //奖项设置--新增奖品==============================================
                var newPrize=[];
                if(this.ruleForm4){
                    for(let i =0;i< this.ruleForm4.length;i++){
                        var arr4={
                            imgInstruction :"",
                            type :this.ruleForm4[i].name0,//类型
                            prizeUnit :Number(this.ruleForm4[i].name1),//单位
                            prizeName :this.ruleForm4[i].name2,//名称
                            num :Number(this.ruleForm4[i].name3),//数量
                            // probabiliy :this.ruleForm4[i].name4,  //概率
                            qixiPrizeImgReqs :[]//图片
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
                id:0,
            //基础设置
                name              : this.ruleForm1.name,
                activityBeginTime : this.ruleForm1.gtTime[0],
                activityEndTime   : this.ruleForm1.gtTime[1],
                goldtreeAdReqs    : newadv,
            //规则设置
                followQrCode      : this.ruleForm2.followQrCode,
                gameTime          : this.ruleForm2.time,
                ingotMoney        : this.ruleForm2.duihuan,
                freeTimes         : this.ruleForm2.manTotalChance,
                dayFreeTimes      : this.ruleForm2.manDayChance,
                gameFb            : this.ruleForm2.fenbi,
                gameJf            : this.ruleForm2.jifen,
                actRule           : this.ruleForm2.desc,
                msgTemplateId     : this.ruleForm2.msgTemplateId,
                isMsgTemplate     : this.ruleForm2.isMsgTemplate,
            //兑奖设置
                cashPrizeBeginTime      :this.ruleForm3.date[0], 
                cashPrizeEndTime        :this.ruleForm3.date[1], 
                receiveType             :this.ruleForm3.type.toString(), //兑奖方式
                phone                   :this.ruleForm3.phone,  
                cashPrizeInstruction    :this.ruleForm3.desc,  
                qixiAddressReqs         :newAddr, 
            //奖项设置
                prizeSetInstruction     :this.explain,  
                qixiPrizeReqs           :newPrize, 
            };
            console.log(data, 123);
            saveAct(data).then(data => {
                //添加预约管理接口
                if (data.code == 100) {
                this.active = 5;
                } else {
                this.$message.error(data.msg + "[错误码：" + data.code + "]");
                }
            })
        },
    //返回活动列表=============================================
        backUrl() {
            window.history.go(-1);
        },
  },
  mounted() {
      this.fetchData()//获取公众号消息模板列表
      this.getPrizeTypeData()//获取奖品类型
  }
};
</script>
<style lang="less">
.bd1px-red{
      .plus-box,textarea{
        border-color: #FF4949 !important;
      }
      .el-input__inner{
          border-color: #FF4949 !important;
      }
    }
</style>