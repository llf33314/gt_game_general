<style lang="less">
.redpack{ 
  .prizeItem{ width:100%;height:40px; overflow: hidden}
  .prizeItem span{ cursor: pointer}
  .prizeName{max-width:60%;text-overflow:ellipsis;overflow:hidden;white-space:nowrap; margin-left:12px; float:left; margin-top:20px;}
}
</style>
<template>
<section>
<div class="hd-common redpack">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item  :to="{ path:'/redpachetFly/index' }">让红包飞</el-breadcrumb-item>  
      <el-breadcrumb-item>创建活动</el-breadcrumb-item>   
    </el-breadcrumb>  
    <div class="gt-content">
        <el-steps :active="active" :center="true" :align-center="true" class="bbtom pb20">
            <el-step title="基础设置"></el-step>
            <el-step title="规则设置"></el-step>
            <el-step title="新建完成"></el-step>
        </el-steps>
        <!-- 基础设置 -->
        <div v-show="this.active==0" class="mt40">
            <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
                <el-form-item label="商户名称：" prop="name">
                    <el-input class="w_demo" v-model="ruleForm1.name"></el-input>
                </el-form-item>
                <el-form-item label="活动名称：" prop="name1">
                    <el-date-picker class="w_demo" v-model="ruleForm1.name1"  placeholder="请输入活动名称">
                    </el-date-picker>
                </el-form-item>  
                 <el-form-item label="活动说明：" prop="desc">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm1.desc" :rows="3" placeholder="请输入活动说明"></el-input>
                    <span class="el-upload__tip grey" >
                        描述活动详情，能让粉丝了解此次活动
                    </span>
                </el-form-item>    
            </el-form> 
        </div>
        <!-- 规则设置 -->
        <div v-show="this.active==1" class="mt40">
            <div class="gt-gray-region" style="color:#666;line-height:20px">
                <p>1.每分钟发放红包数量不得超过1800个。</p>
                <p>2.北京时间0:00-8:00不可赠送红包。</p>
                <p>3.裂变组合钟的每个红包平均金额介于[1.00元至200.00元]之间。</p>
                <p>4.该功能必须要上传p12整数和支付秘钥（如果还没上传p12证书请先到<a  style="color:#20a0ff" target="_blank" href="http://weixin.qq.com/">weixin.qq.com</a>平台下载）。</p>
                <p>5.其中指定一位粉丝项的微信用户必须要绑定银行卡，否则会发送失败。</p>
                <p>6.发放总人数不能少于3人，不能多于20人。</p>
            </div>
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="140px" class="mt40 demo-ruleForm">
                <el-form-item label="指定粉丝：" prop="name21">
                    <el-input class="w_demo" v-model="ruleForm2.name21" placeholder="请选择粉丝"></el-input>
                    <span class="ml10"><el-button  @click="prizeBtn()"  type="primary">选择粉丝</el-button ></span>
                </el-form-item>
                <el-form-item label="发放总人数：" prop="name22">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm2.name22" placeholder="发放总人数设置在3-20人之间"></el-input>人
                </el-form-item>  
                <el-form-item label="红包类型：" prop="name23">
                    <el-select v-model="ruleForm2.name23" placeholder="请选择">
                        <el-option label="裂变红包"  value="0"></el-option> 
                    </el-select> 
                </el-form-item> 
                 <el-form-item label="红包总金额：" prop="name24">
                    <el-input class="w_demo mr10" type="number" v-model="ruleForm2.name24" placeholder="红包金额介于1-200之间"></el-input>元
                </el-form-item> 
                <el-form-item label="红包祝福语：" prop="name25">
                    <el-input class="w_demo"  type="textarea" v-model="ruleForm2.name25" :rows="3" placeholder="请输入红包祝福语"></el-input>
                    
                </el-form-item>      
            </el-form> 
        </div> 
        <!-- 选择粉丝弹窗 -->
<el-dialog title="粉丝列表"  :visible.sync="dialogFans">
    <div class="mb10"> 
      <el-input placeholder="请输入昵称" icon="search" v-model="keyWord" style="width:250px" @change="test()"> 
          </el-input>
    </div>
  <div style="width:100%;height:500px;">
    <div style="width:75%;float:left;">
      <el-table ref="multipleTable" :data="fansData.data"  tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="40">
          </el-table-column>
          <el-table-column label="头像" prop="photo">   
                <template slot-scope="scope">
                  <img :src="scope.row.photo" style="width:30px;height:30px"/>
                </template>
          </el-table-column>
          <el-table-column label="昵称" prop="name">
          </el-table-column>
          <el-table-column label="性别" prop="sex">
            <template slot-scope="scope">{{ scope.row.sex|sexStatus(scope.row.sex) }}</template>
          </el-table-column>
          <el-table-column label="所在城市" prop="city">
          </el-table-column>
          <el-table-column label="关注时间" prop="time">
            <template slot-scope="scope">{{scope.row.time|parseTime('{y}-{m}-{d}')}}</template>
          </el-table-column>
          <el-table-column label="组别" prop="group">
          </el-table-column>
      </el-table>
      <div class="public-page-fr">
          <el-pagination @current-change="handleCurrentChange"  :page-size="10" 
          layout="prev, pager, next, jumper" :total="fansData.page.totalNums">
          </el-pagination>
      </div>  
      <div class="h20"></div> 
    </div>

    <div style="width:24%;float:right;height:550px;background:#f2f2f2;border:1px solid #dfe6ec">
        <div style="height:40px;line-height:40px; background:#EEF1F6;padding-left:10px;border-bottom:1px solid #dfe6ec">
          已选择：{{this.prizeData.length}}
        </div> 
        <div v-for="(item,index) in  prizeData" class="prizeItem" >  
            <p style="width:100%;">
                <div class="prizeName">
                  {{item.name}} 
                </div>
                <span @click="delPrize(index)" class="blueee mr10 pull-right mt20" >删除</span>  
            </p>
        </div> 
    </div>
  </div>

  <div style="float:right">
    <div class="h20"></div> 
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogFans = false">取 消</el-button>
      <el-button type="primary" @click="prizeSubmit()">确 定</el-button>
    </span>
  </div> 
</el-dialog>
        <!-- 兑奖设置 --> 
        <!-- 奖项设置 -->    
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
    let peoplePass = (rule, value, callback) => {
       const people = window.parseInt(value) 
       if (!people) {
        callback(new Error("发放总人数不能为空"));
       } else if (people<3 || people>20) {
        callback(new Error("发放总人数设置在3-20人之间，请重填"));
      } else {
        callback();
      }
    };
    let moneyPass = (rule, value, callback) => {
       const money = window.parseInt(value) 
       if (!money) {
        callback(new Error("红包金额不能为空"));
       } else if (money<1 || money>200) {
        callback(new Error("红包金额介于1-200之间，请重填"));
      } else {
        callback();
      }
    }; 
    return {
      active: 1,
      dialogFans:true,
      keyWord:"",
      fansData:{
         data:[
           {
            photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"哈哈",
            sex:1,
            city:"珠海",
            time:1513008000000,
            group:"未分组"
          }, {
            photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"啊few",
            sex:0,
            city:"广州",
            time:1513008000000,
            group:"未分组"
          },{
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"各位范围",
            sex:1,
            city:"湛江",
            time:1513008000000,
            group:"未分组"
          }, {
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"热热热",
            sex:0,
            city:"广州",
            time:1513008000000,
            group:"未分组"
          }, {
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"好好好日",
            sex:0,
            city:"广州",
            time:1513008000000,
            group:"未分组"
          },{
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"呃呃呃",
            sex:1,
            city:"北京",
            time:1513008000000,
            group:"未分组"
          }, {
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"蒙多多",
            sex:0,
            city:"广州",
            time:1513008000000,
            group:"未分组"
          }, {
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"寄交接",
            sex:0,
            city:"广州",
            time:1513008000000,
            group:"未分组"
          },{
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"用用",
            sex:1,
            city:"珠海",
            time:1513008000000,
            group:"未分组"
          }, {
             photo:"http://maint.deeptel.com.cn/upload//image/3/gt123/3/20171208/939131F19C762122A62B663A81315559.jpg",
            name:"天天",
            sex:1,
            city:"广州",
            time:1513008000000,
            group:"未分组"
          }
        ], 
        page:{
            totalNums:31,
            totalPages:4
        }        
      },
      prizeData:[],

      ruleForm1: {
        name: "",
        name1: "",  
        desc: "",  
      },
      rules1: {
        name: [{ required: true, message: "商户名称不能为空", trigger: "blur" }],
        name1: [ { required: true, message: "活动名称不能为空" , trigger: "blur" } ] 
      },
      ruleForm2: {
        name21: "",
        name22: "",
        name23: "",
      },
      rules2: {
        name21: [{ required: true, type:'array', message: "请选择指定粉丝", trigger: "blur" }],
        name22: [{ required: true,type:'number', validator: peoplePass,  trigger: "blur,change" } ],
        name23: [{ required: true, message: "请选择红包类型", trigger: "blur" }],
        name24: [{ required: true,type:'number', validator: moneyPass,  trigger: "blur,change" } ],
        name25: [{ required: true, message: "红包祝福语不能为空", trigger: "blur" }]
      },       
    };
  },
  methods: {  
    delPrize(index){
      this.prizeData.splice(index, 1); 
    },
    prizeBtn(){
      this.dialogFans=true
    },
    //多选名单
    handleSelectionChange(val) {
      // console.log(val,111)
        // this.multipleSelection = val; 
        this.prizeData = val;  
        
    },
    prizeSubmit(){ 
      var arr=[];
      for(var i=0;i<this.prizeData.length;i++){
        var arr1={
          name:this.prizeData[i].name
        }
        arr.push(arr1.name);
      }
      //  console.log(this.ruleForm2.name21); 
      this.ruleForm2.name21=arr+''
      this.dialogFans=false
    },
   
    handleCurrentChange(){
      console.log(123);  
    },
    test() {
      console.log(123);  
    },
    upStep() {
      this.active--;
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
        if(!this.ruleForm4[i].name1|!this.ruleForm4[i].name2|!this.ruleForm4[i].name3|!this.ruleForm4[i].name4){
            this.$message.error("表单不能留空，请填写完整~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name1)) {
            this.$message.error("奖品数额填写有误，请重新填写~");
            return false
        }else if (!regu.test(this.ruleForm4[i].name3)) {
            this.$message.error("奖项数量填写有误，请重新填写~");
            return false
        }else{
            this.ruleForm4[i].name4 = parseFloat(this.ruleForm4[i].name4).toFixed(2); 
            this.submit();
        }  
      }
    }, 
    //表单提交--------------------------------------star
    submit(){
        var newarr=[];
        for(let i =0;i< this.ruleForm4.length;i++){
            var arr={
                name0:this.ruleForm4[i].name0,
                name1:this.ruleForm4[i].name1,
                name1:this.ruleForm4[i].name1,
                name3:this.ruleForm4[i].name3,
                name4:this.ruleForm4[i].name4  
            }
            newarr.push(arr)
        } 
        const data = {
            //基础设置 
            name1 : this.ruleForm1.name, 
            name2 : this.ruleForm1.name1, 
            name3 : this.ruleForm1.endTime, 
            name4 : this.ruleForm1.resource, 
            name5 : this.ruleForm1.desc, 
            name15: this.ruleForm1.music, 
            //规则设置
            name6 : this.ruleForm2.cishu, 
            name7 : this.ruleForm2.zongshu, 
            name8 : this.ruleForm2.desc, 
            //兑奖设置
            name9 : this.ruleForm3.days, 
            name10: this.ruleForm3.dizhi, 
            name11: this.ruleForm3.tishi, 
            name12: this.ruleForm3.xuzhi,  
            //奖项设置 
            name13:this.awardKey,
            name14:newarr
        };
        console.log(data,123); 
    },    
    backUrl(){
         window.history.go(-1);
    },
  },
  mounted() { 
  },
   filters: {
      sexStatus(val) {
        if (val == 0) {
          val = "女";
        }else if(val == 1){
          val = "男"; 
        }
        return val;
      },
    }
};
</script>
