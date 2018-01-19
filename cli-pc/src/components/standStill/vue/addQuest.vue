<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/standStill/index' }">一站到底</el-breadcrumb-item>  
      <el-breadcrumb-item :to="{ path:'/standStill/question' }">题库管理</el-breadcrumb-item>   
      <el-breadcrumb-item>题库管理</el-breadcrumb-item>   
    </el-breadcrumb> 
    
    <div class="gt-content">  
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
            <el-form-item label="题库名称：" prop="name">
              <el-input v-model="ruleForm.name" class="w20_demo"></el-input>
            </el-form-item>  
            
            <el-form-item label="题库管理：">
               <el-button type="primary" @click="addTest('ruleForm')" class="mr10">添加</el-button> 
               <el-button type="primary" @click="test()" >导入题库</el-button>
               <a target="_blank" class="el-upload__tip grey ml10 blueee" style="cursor:pointer" href="https://deeptel.com.cn//stand/downMould.do">下载导入示例</a> 
            </el-form-item>   

             <el-form-item>
                <el-table :data="tableData"  v-if="this.tableData.length">
                  <el-table-column  label="题库序号"> 
                    <template slot-scope="scope"> 
                      {{scope.$index+1}}
                    </template>         
                  </el-table-column>
                  <el-table-column prop="quesTitle" label="题目名称">   
                  </el-table-column>
                  <el-table-column prop="createTime" label="选项"> 
                    <template slot-scope="scope">
                     <p>A：{{scope.row.optionA}}<span v-show="scope.row.rightAnswer==1">(正确)</span></p>
                     <p>B：{{scope.row.optionB}}<span v-show="scope.row.rightAnswer==2">(正确)</span></p>
                     <p>C：{{scope.row.optionC}}<span v-show="scope.row.rightAnswer==3">(正确)</span></p>
                     <p>D：{{scope.row.optionD}}<span v-show="scope.row.rightAnswer==4">(正确)</span></p>
                    </template>           
                  </el-table-column> 
                  <el-table-column prop="order_option"  label="操作">
                    <template slot-scope="scope"> 
                      <el-button class="gt-button-normal blue"      @click="showDetailBtn(scope.row)">编辑</el-button>  
                      <el-button class="gt-button-normal "          @click="handOut(scope.row.id)">删除</el-button>  
                    </template>
                  </el-table-column>
                </el-table> 
            </el-form-item>  
            <!-- 弹窗 -->
            <el-dialog title="添加" :visible.sync="showAdd" class="detail-dialog"> 
                <el-form :label-position="labelPosition" label-width="160px" :model="formLabelAlign" :rules="rulesAlign" ref="formLabelAlign">
                  <el-form-item label="问题：" prop="name">
                    <el-input v-model="formLabelAlign.name" class="w_demo"></el-input>
                  </el-form-item>
                  <el-radio-group v-model="formLabelAlign.select" prop="select">  
                      <el-form-item label="选项A：" prop="name1">
                        <el-input v-model="formLabelAlign.name1" class="w_demo mr10"></el-input>
                        <el-radio :label="1">设为答案</el-radio> 
                      </el-form-item>
                      <el-form-item label="选项B：" prop="name2">
                        <el-input v-model="formLabelAlign.name2" class="w_demo mr10"></el-input> 
                        <el-radio :label="2">设为答案</el-radio> 
                      </el-form-item>
                      <el-form-item label="选项C：" prop="name3">
                        <el-input v-model="formLabelAlign.name3" class="w_demo mr10"></el-input>
                        <el-radio :label="3">设为答案</el-radio> 
                      </el-form-item>
                      <el-form-item label="选项D：" prop="name4">
                        <el-input v-model="formLabelAlign.name4" class="w_demo mr10"></el-input>
                        <el-radio :label="4">设为答案</el-radio> 
                      </el-form-item>
                  </el-radio-group>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="saveBtn('formLabelAlign')">确 定</el-button> 
                  <el-button @click="showAdd = false">取 消</el-button>
                </div>
            </el-dialog>  
        </el-form> 
      
        <div class="h80"></div> 
        <div class="btnRow">   
            <el-button   @click="backUrl()" >返回</el-button> 
            <el-button type="primary" @click="submit('ruleForm')" >保存</el-button>   
        </div>   
    </div>
</div>
</section>
</template>
<script>
import {  
saveQuesbank,saveStandQuestion,removeStandQuestion,getStandQuesbank
}from './../api/api'
  export default{
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('不能为空'));
        } else { 
          callback();
        }
      };
      return { 
        ruleForm:{
          name:"", 
          id:0
        }, 
        tableData:{ 
        },  
        rules:{ 
          name: [ 
            { required: true, message: "题库名称不能为空", trigger: "blur" }
          ],  
        }, 
        showAdd:false,
        labelPosition: 'right',
        formLabelAlign: {
          id:0,
          name: '',
          select:'',
          name1: '',
          name2: '',
          name3: '',
          name4: ''
        },
        rulesAlign:{
          name: [ 
            { required: true, validator: validatePass, trigger: 'blur' }
          ],  
          name1: [
            { validator: validatePass, trigger: 'blur' }
          ],
           name2: [
            { validator: validatePass, trigger: 'blur' }
          ],
           name3: [
            { validator: validatePass, trigger: 'blur' }
          ],
           name4: [
            { validator: validatePass, trigger: 'blur' }
          ],
          
        },
      };
    },
    methods: { 
      //添加 按钮---------------------------star
      addTest(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.ruleForm.id!=0){
              this.showAdd=true
              this.formLabelAlign.name=''
              this.formLabelAlign.select=''
              this.formLabelAlign.name1=''
              this.formLabelAlign.name2=''
              this.formLabelAlign.name3=''
              this.formLabelAlign.name4='' 
            }else{
             var params={};
             params.bankName=this.ruleForm.name
             params.id=this.ruleForm.id
             saveQuesbank(params).then(data=>{
              if (data.code == 100) { 
                console.log(data,1234);
                   this.showAdd=true
                   this.ruleForm.id=data.data.id
                   //this.getData() 
                } else {
                  this.$message.error(data.msg);;
                }
              }).catch(() => {
                  this.$message({ type: "info", message: "网络问题，请刷新重试~" });
              });
            } 
          } else {
            console.log('error submit!!');
            return false;
          }
        }); 
      },
      //弹窗保存---------------------------star
      saveBtn(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.formLabelAlign.select==''){
                this.$message.error("请设定正确答案");;
            }else{
              var params={}; 
              params.bankId =this.ruleForm.id
              params.id     =this.formLabelAlign.id
              params.optionA=this.formLabelAlign.name1
              params.optionB=this.formLabelAlign.name2
              params.optionC=this.formLabelAlign.name3
              params.optionD=this.formLabelAlign.name4
              params.quesTitle=this.formLabelAlign.name
              params.rightAnswer=this.formLabelAlign.select 
              console.log(params,7788);
              saveStandQuestion(params).then(data=>{
                if (data.code == 100) { 
                    this.showAdd=false
                    this.$message({ message: "操作成功", type: "success"}); 
                    this.getData();
                  } else {
                    this.$message.error(data.msg);;
                  }
                }).catch(() => {
                    this.$message({ type: "info", message: "网络问题，请刷新重试~" });
                }); 
            } 
          } else {
            console.log('error submit!!');
            return false;
          }
        }); 
      }, 
      //获取题库列表---------------------------star
      getData(){ 
        var id=this.ruleForm.id
        console.log(id,'id');
        getStandQuesbank({id}).then(data=>{
          if (data.code == 100) {
            console.log(data,55);
            this.tableData=data.data.standQuestionResList
            console.log(data,'题库列表');
          } else {
              this.$message.error(data.msg);;
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
      //底部保存---------------------------star
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) { 
              var params={};
              params.bankName=this.ruleForm.name
              params.id=this.ruleForm.id
              saveQuesbank(params).then(data=>{
              if (data.code == 100) {
                console.log(data,123);
                    this.ruleForm.id=data.data.id  
                    this.$message({ message: "操作成功", type: "success"}); 
                   // this.$router.push({path:'/standStill/question'});
                } else {
                  this.$message.error(data.msg);;
                }
              }).catch(() => {
                  this.$message({ type: "info", message: "网络问题，请刷新重试~" });
              });
          } else {
            console.log('error submit!!');
            return false;
          }
        }); 
      },  
      //删除题目---------------------------star
      handOut(val){
        this.$confirm('此操作将永久删除该题目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        removeStandQuestion({id:val}).then(data=>{
            if (data.code == 100) {
                this.$message({ message: "操作成功", type: "success"}); 
                this.getData();
            } else {
                this.$message.error(data.msg);
            }
          }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
          }); 
        }).catch(() => {
          this.$message({type: 'info',message: '已取消删除'});          
        });   
      },
      //题目编辑---------------------------star
      showDetailBtn(val){
        this.showAdd=true
        this.formLabelAlign.name=val.quesTitle 
        this.formLabelAlign.name1=val.optionA
        this.formLabelAlign.name2=val.optionB
        this.formLabelAlign.name3=val.optionC
        this.formLabelAlign.name4=val.optionD
        this.formLabelAlign.select=Number(val.rightAnswer) 
        this.formLabelAlign.id=val.id
      }, 
      test(){
        console.log(123)
      },
      backUrl(){
         window.history.go(-1);
      },
      handleCurrentChange(val){
        console.log(val)
      }
    },   
  }
</script>
