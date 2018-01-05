<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/openBox/index' }">拆礼盒</el-breadcrumb-item>  
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
                    <el-input class="w_demo" v-model="ruleForm1.name"></el-input>
                </el-form-item> 
                <el-form-item label="游戏时间：" prop="name1">
                    <el-date-picker class="w_demo" v-model="ruleForm1.name1"   type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>  
                 <el-form-item label="背景音乐：">
                    <div class="pd20 bb bw"> 
                        <el-button size="small" type="primary">点击上传</el-button>
                        <span class="el-upload__tip grey ml20">{{ruleForm1.music}}</span> 
                        <div class="el-upload__tip grey" style="line-height:25px">
                            音频文件的格式为mp3、wma、wav,大小不超过3M
                        </div>
                    </div>
                </el-form-item>



            <h1 class="mt30 mb20 pb10 bbtom" style="width:80%">礼盒设置</h1> 
            <el-button type="primary" class="mb20" @click="addboxs()">新增</el-button>  
            <span class="ml10 el-upload__tip grey">至少添加3-5个礼盒，礼盒图片建议尺寸270*270px</span>
            <el-table ref="multipleTable" :data="ruleForm1.boxs" tooltip-effect="dark" style="width:80%">
                <el-table-column label="礼盒类型">
                    <template slot-scope="scope">
                        <el-select  class="w20_demo"  v-model="scope.row.name1" placeholder="请选择"> 
                            <el-option label="自定义"     :value="0"></el-option> 
                            <el-option label="默认"       :value="1"></el-option>                             
                        </el-select>
                    </template>
                </el-table-column> 
                <el-table-column label="礼盒名称">
                    <template slot-scope="scope"> 
                        <el-input class="w20_demo"    v-model="scope.row.name2" v-if="scope.row.name1==0"></el-input>
                        <el-select  class="w20_demo"  v-model="scope.row.name2" placeholder="请选择" v-if="scope.row.name1==1"> 
                            <el-option label="矮方盒"  :value="1"></el-option>      
                        </el-select>
                    </template>
                </el-table-column> 
                <el-table-column label="礼盒图片">
                  <template slot-scope="scope">
                      <gt-material prop="url" :url="scope.row.name3" v-on:getChangeUrl="getChangeUrlBox(scope.$index, $event)" width="60" height="60"></gt-material>
                  </template>
                </el-table-column> 
                 <el-table-column label="礼盒音乐">
                  <template slot-scope="scope">
                       <el-select  class="w100_demo"  v-model="scope.row.name4" placeholder="请选择"> 
                            <el-option label="音乐1"     :value="0"></el-option> 
                            <el-option label="音乐2"     :value="1"></el-option>                             
                        </el-select>
                        <el-button size="small" type="primary">播放</el-button>
                        <el-button size="small" type="primary">暂停</el-button>
                  </template>
                </el-table-column> 
                 <el-table-column label="放置礼品">
                  <template slot-scope="scope">
                    <el-switch v-model="scope.row.name5" on-text="开启" off-text="关闭" :on-value="0" :off-value="1">
                    </el-switch>
                  </template>
                </el-table-column> 
                <el-table-column label="操作">
                  <template slot-scope="scope">
                        <el-button class="gt-button-normal" v-show="scope.$index>2" @click="delboxs(scope.$index)">删除</el-button>
                  </template>
                </el-table-column> 
             </el-table> 

            <h1 class="mt30 mb20 pb10 bbtom" style="width:80%">广告设置</h1> 
            <el-button type="primary" class="mb20" @click="addlinks()">新增</el-button>  
            <span class="ml10 el-upload__tip grey">1.仅支持多粉与一点揩油的链接    2.广告图格式：1000*300px</span>
            <el-table ref="multipleTable" :data="ruleForm1.links" tooltip-effect="dark" style="width:80%">
                <el-table-column label="广告链接">
                  <template slot-scope="scope" >
                        <el-input v-model="scope.row.url">
                          <template slot="prepend">Http://</template>
                        </el-input>
                  </template>
                </el-table-column> 
                <el-table-column label="选择图片">
                  <template slot-scope="scope">
                      <gt-material prop="url" :url="scope.row.img" v-on:getChangeUrl="getChangeUrl(scope.$index, $event)" width="60" height="60"></gt-material>
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
                <el-form-item label="游戏总数：" prop="freePeople">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.freePeople"></el-input>次/人
                </el-form-item> 
                <el-form-item label="每天次数：" prop="freeNum">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.freeNum"></el-input>次/人
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
                    <el-date-picker class="w_demo" v-model="ruleForm3.date" type="daterange" placeholder="选择日期范围">
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
            <div>
                <span style="color: #333; position:absolute;margin-top:0px;" >奖品说明：</span>
                <el-input type="textarea" class="bw ml120"  :maxlength="300"  :rows="3" placeholder="请输入兑奖说明" v-model="explain">
                </el-input>
                <span class="el-upload__tip grey ml10">300字以内</span>
            </div> 
            <div class="gt-gray-region mt20" style="color:#666;line-height:20px">
                <p>奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数</p>
                <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p> 
                <p>当奖品为实物时，请上传实物图片</p> 
                <p>当奖品为实物时，请上传实物图片</p> 
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
                        <el-option label="粉币"       :value="1"></el-option>
                        <el-option label="手机流量"   :value="2"></el-option>
                        <el-option label="手机话费"   :value="3"></el-option>
                        <el-option label="实体物品"   :value="4"></el-option>
                        <el-option label="谢谢参与"   :value="5"></el-option> 
                        <el-option label="积分"       :value="6"></el-option> 
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
                <!-- <el-table-column label="奖品图片">
                    <template slot-scope="scope"> 
                        <div  v-if="scope.row.name0==4" >
                            <gt-material prop="url" :url="scope.row.name4" v-on:getChangeUrl="getChangeUrl4(scope.$index, $event)" width="72" height="72"></gt-material>
                        </div>
                    </template>
                </el-table-column> -->
                <el-table-column label="操作">
                    <template slot-scope="scope">
                         <el-button class="gt-button-normal blue" v-if="scope.row.name0==4"  @click="delForm4(scope.$index)">上传实物</el-button>
                        <el-button class="gt-button-normal" v-if="scope.$index!=0"  @click="delForm4(scope.$index)">删除</el-button>
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
            <el-button type="primary" @click="submit()">打印</el-button>   
        </div> 
    </div>   
</div>
</section>
</template>
<script>
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
        name1: "",
        name4:"",
         music: "暂无上传音乐",
        links:[
          {url:"",img:""},
          {url:"",img:""}
        ],
        boxs:[
            {
            name1:0,
            name2:"",
            name3:"",
            name4:"",
            name5:"",
            }, {
            name1:0,
            name2:"",
            name3:"",
            name4:"",
            name5:"",
            }, {
            name1:0,
            name2:"",
            name3:"",
            name4:"",
            name5:"",
            },
            
        ],
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        name1: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }] 
      },
      ruleForm2: {
        code: "",
        time: "",
        duihuan:"",
        freePeople:"",
        freeNum:"", 
        fenbi:"",
        jifen:"",
        desc: "", 
        msgModel:"",
        msg:1
      },
      rules2: {
        time: [ 
          { required: true,validator: timePass,  trigger: "blur,change" }
        ], 
        duihuan: [
          { required: true,  message: "请填写元宝兑换金币比例", trigger: "blur" } 
        ], 
        freePeople: [
          { required: true,  message: "请填写每人免费游戏次数", trigger: "blur" } 
        ], 
        freeNum: [
          { required: true,  message: "请填写每人每天免费游戏次数", trigger: "blur" } 
        ],
        fenbi: [
          { required: true,  message: "请填写每天游戏小号的粉币或积分", trigger: "blur" } 
        ],
        msg: [ 
          { required: true}
        ], 
        desc: [
          { required: true,  message: "请填写活动规则", trigger: "blur" } 
        ],
        msgModel: [
          { required: true, type: 'string',  message: "请选择消息模块", trigger: "blur" } 
        ],
         
        
      },
      ruleForm3: {
        date:"",
        type: [], 
        addrRow:[{list:""},{list:""}],
        phone:"",
        desc:""
      },
      rules3: {
        list: [{ required: true,message: "不能为空" }],
        date: [{ required: true,type: 'array', message: "兑奖时间不能为空" }],
        type: [{ required: true,type: 'array', message: "兑奖方式不能为空", trigger: "blur" }], 
        phone:[{ required: true,type: 'text', validator:iiPass,trigger: "blur" }], 
        desc: [{ required: true,message: "兑奖说明不能为空", trigger: "blur" }], 
      },
      explain: "",
      ruleForm4: [{ 
          name0: 1,
          name1: "",
          name2: "",
          name3: "",
          name4: ""
        },
        { 
          name0: 1,
          name1: "",
          name2: "",
          name3: "",
          name4: ""
        }],  
    };
  },
  methods: {    
    addrPass(rule, value, callback) {
      if (!value) {
        callback(new Error("不能为空"));
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
    //礼盒设置的新增&删除
    addboxs(){ 
      this.ruleForm1.boxs.push({ name1:0, name2:"", name3:"", name4:"", name5:"",})
    },
    delboxs(val) { 
          this.ruleForm1.boxs.splice(val, 1); 
    },
    //广告设置的新增&删除
    addlinks(){
      this.ruleForm1.links.push({url:"",img:""},)
    },
    delLinks(val) { 
          this.ruleForm1.links.splice(val, 1); 
    },
    getChangeUrlBox(i,e) { 
      this.ruleForm1.boxs[i].name3=e.url
    }, 
    getChangeUrl(i,e) { 
      //console.log(i)
      this.ruleForm1.links[i].img=e.url
    }, 
    getChangeUrl2(e) { 
      this.ruleForm2.code=e.url
    }, 
    getChangeUrl4(i,e) {   
      this.ruleForm4[i].name4=e.url
    }, 
    addForm4(){ 
        this.ruleForm4.push({ name0: 1, name1: "", name2: "", name3: "", name4: ""},)
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
      for (let i = 0; i < this.ruleForm4.length; i++) { 
        var regu =/^[1-9]\d*$/;
        if(!this.ruleForm4[i].name1||!this.ruleForm4[i].name2||!this.ruleForm4[i].name3){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name1)) {
            this.$message.error("奖品单位填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name3)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        }else if (this.ruleForm4[i].name0==4&&this.ruleForm4[i].name4.length==0) { 
            this.$message.error("当奖品为实物时，请上传实物图片~");
            return false 
        }else{
            this.submit();
        }  
      }
    }, 
    //表单提交--------------------------------------star
    submit(){
        console.log(this.ruleForm3,123); 
        var newarr=[];
        for(let i =0;i< this.ruleForm1.links.length;i++){ 
            var arr={
                name0:this.ruleForm1.links[i].url, 
                name1:this.ruleForm1.links[i].img, 
            } 
            newarr.push(arr)
        }  
         var newaddr=[];
        if(this.ruleForm3.addrRow){ 
            for(let i =0;i< this.ruleForm3.addrRow.length;i++){ 
                var arraddr={
                    name0:this.ruleForm3.addrRow[i].list,  
                } 
                newaddr.push(arraddr)
            }    
        } 
        var newarr4=[];
        if(this.ruleForm4){
            for(let i =0;i< this.ruleForm4.length;i++){
                var arr4={
                    name0:this.ruleForm4[i].name0,
                    name1:this.ruleForm4[i].name1,
                    name1:this.ruleForm4[i].name1,
                    name3:this.ruleForm4[i].name3,
                    name4:this.ruleForm4[i].name4  
                }
                newarr4.push(arr4)
            }
        } 
        const data = {
            //基础设置 
            name  : this.ruleForm1.name, 
            name1 : this.ruleForm1.name1, 
            name3 : newarr, 
            //规则设置
            name6 : this.ruleForm2.code, 
            name7 : this.ruleForm2.time, 
            name8 : this.ruleForm2.duihuan, 
            name9 : this.ruleForm2.freePeople, 
            name10: this.ruleForm2.freeNum, 
            name11: this.ruleForm2.fenbi, 
            name12: this.ruleForm2.jifen,  
            name4 : this.ruleForm2.desc,  
            name5 : this.ruleForm2.msgModel,  
            name13: this.ruleForm2.msg,  
            //兑奖设置 
            name14:this.ruleForm3.date, 
            name15:this.ruleForm3.type, 
            name16:newaddr, 
            name17:this.ruleForm3.phone, 
            name18:this.ruleForm3.desc,  
            //奖项设置 
            name19:this.explain, 
            name20:newarr4, 

           
        };
        console.log(data,123); 
    },  
    backUrl(){
         window.history.go(-1);
    },
    test(){
        console.log(1122);
    }
  },
  mounted() {
    
  }
};
</script>
