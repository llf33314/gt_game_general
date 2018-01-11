<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/standStill/index' }">一站到底</el-breadcrumb-item>  
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
                    <el-date-picker class="w_demo" v-model="ruleForm1.name1" :picker-options="pickerOptions"  type="datetimerange"  placeholder="选择时间范围">
                    </el-date-picker>
                </el-form-item>  
                <el-form-item label="题库选择：" prop="library">
                        <el-select v-model="ruleForm1.library" placeholder="请选择">
                            <el-option label="题库一"  value="0"></el-option>
                            <el-option label="题库二"  value="1"></el-option>
                            <el-option label="题库三"  value="2"></el-option>
                        </el-select> 
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
          </el-form> 
        </div>
        <!-- 规则设置 -->
        <div v-if="this.active==1" class="mt40">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" class="mt40 demo-ruleForm">
                <el-form-item label="关注二维码：" prop="code">
                  <gt-material prop="url" :url="ruleForm2.code" v-on:getChangeUrl="getChangeUrl2" width="72" height="72"></gt-material>
                  <span class="el-upload__tip grey ml10">上传1:1二维码，将会在活动规则中显示商家二维码</span>  
                </el-form-item> 
               
                <el-form-item label="游戏总数:" prop="manTotalChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manTotalChance"></el-input>次/人
                </el-form-item> 
                <el-form-item label="免费次数：" prop="manDayChance">
                    <el-input class="w25_demo mr10" v-model="ruleForm2.manDayChance"></el-input>次/人
                </el-form-item>
                <el-form-item label="答题时间：" prop="time">
                    <el-input class="w_demo" v-model="ruleForm2.time" type="number" placeholder="请输入答题时间"></el-input>
                    <span class="el-upload__tip grey ml10">设置时间为10~30秒</span>  
                </el-form-item>  
                <el-form-item label="奖励积分：" prop="fenbi">
                    <el-input class="w25_demo mr10" placeholder="请输入每道题奖励的积分" v-model="ruleForm2.fenbi"></el-input>分 
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
        <div v-if="this.active==3" class="mt40">
            <div class="gt-gray-region mt20" style="color:#666;line-height:20px">
                <p>奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数</p>
                <p>如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12</p> 
                <p>下列奖品根据排名由上至下顺序</p>
            </div> 
            <div class="mt20 mb20">
                <el-button   @click="addForm4()" :disabled='ruleForm4.length>4' type="primary">新增奖品</el-button> 
                <span class="el-upload__tip grey ml10">分配最多设置5个奖项</span> 
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
                        <el-option label="实体物品"   :value="4"></el-option>
                        <el-option label="积分"       :value="6"></el-option> 
                        <el-option label="优惠券"       :value="7"></el-option> 
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
                    <template slot-scope="scope" v-if="scope.row.name0==4">  
                            <span  class="imgRow" >
                                <el-upload  action="''" list-type="picture-card" :file-list="scope.row.name5"  :on-remove="handleRemove">
                                </el-upload>
                            </span>
                            <span class="uploadBtn">
                                <gt-material :prop="''" selectType="select" btnContent="+" v-on:getChangeUrl="getContentUrl(scope.$index, $event)" width="50" height="50"></gt-material>
                            </span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
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
      active: 3,
      ruleForm1: {
        name: "",
        name1: "",
        library:"",
        music: "暂无上传音乐",         
      },
      rules1: {
        name: [{ required: true, message: "活动名称不能为空", trigger: "blur" }],
        name1: [{required: true, type: "array",message: "游戏时间不能为空", trigger: "blur" }], 
        library: [{required: true, type: 'string',  message: "请选择消息模块", trigger: "blur"}] 
      },
      ruleForm2: {
        code: "",
        time: "",
        duihuan:"",
        manTotalChance:"",
        manDayChance:"", 
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
            // 时间的筛选
      pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;
          }
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
          name5: []
        },
        { 
          name0: 1,
          name1: "",
          name2: "",
          name3: "",
          name5: []
        }],  // 时间的筛选
      pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;
          }
      },
    };
  },
  methods: {   
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
        }else if (this.ruleForm4[i].name0==4&&this.ruleForm4[i].name5.length==0) { 
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
                    address:this.ruleForm3.addrRow[i].list,  
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
            name9 : this.ruleForm2.manTotalChance, 
            name10: this.ruleForm2.manDayChance, 
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
