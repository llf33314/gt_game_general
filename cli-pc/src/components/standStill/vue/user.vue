<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/standStill/index' }">一站到底</el-breadcrumb-item>  
      <el-breadcrumb-item>用户信息</el-breadcrumb-item>   
    </el-breadcrumb>  
    <div class="gt-content"> 
        <h1 class="pb10 mb20 bbtom">参与答题的用户列表</h1>
        <gt-null-data v-show="tableData.data.length==0">还没有相关纪录</gt-null-data>   
        <el-table :data="tableData.data" v-show="tableData.data.length!=0">
          <el-table-column prop="memberName" label="微信名称">              
          </el-table-column>
          <el-table-column prop="totalScore" label="答题总积分">             
          </el-table-column>
          <el-table-column prop="createtime" label="完成时间"> 
            <template slot-scope="scope">
              {{scope.row.createtime |parseTime('{y}-{m}-{d} {h}:{i}')}}
            </template>     
          </el-table-column>
          <el-table-column prop="order_option"  label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" @click="answer(scope.row.id)">答题记录</el-button>  
              <el-button class="gt-button-normal "     @click="delBtn(scope.row.id)">删除</el-button> 
            </template>
          </el-table-column>
        </el-table>  

<!-- 答题记录 -->
<el-dialog title="订单详情" :visible.sync="answerDetail" class="detail-dialog">
   <el-table :data="detailData.data">
          <el-table-column prop="quesTitle" label="题号">              
          </el-table-column>
          <el-table-column prop="chooseAnswer" label="选择答案">             
          </el-table-column>
          <el-table-column prop="score" label="获得积分">  
          </el-table-column> 
           <el-table-column prop="createTime" label="答题时间"> 
            <template slot-scope="scope">
              {{scope.row.createTime |parseTime('{y}-{m}-{d} {h}:{i}')}}
            </template>     
          </el-table-column>  
        </el-table>    
</el-dialog>
    </div>
</div>
</section>
</template>
<script>
import {  
getUserList,getUserDetail
}from './../api/api'
  export default{
    data() {
      return { 
        prizeType:"",
        prizeState:"",
        keyWord:"",
        tableData:{
          data:[] 
        },  
        detailData:{
          data:[] 
        },  
        answerDetail:false
      };
    },
    methods: {  
       getData(){
        var params    ={}; 
        params.id  =this.$router.history.current.query.id; 
        getUserList(params).then(data=>{
          if (data.code == 100) {
            this.tableData=data
            console.log(data,'中奖列表');
          } else {
              this.$message.error(data.msg + "错误码：[" + data.code + "]");
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
      test(){
        console.log(123)
      },
      //详情---------------------Star
      answer(val){ 
        this.answerDetail=true 
       var id  =val; 
        getUserDetail({id}).then(data=>{
          if (data.code == 100) {
            this.detailData=data
            console.log(data,11)
          } else {
              this.$message.error(data.msg + "错误码：[" + data.code + "]");
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
      delBtn(val){
        console.log(val)
      },
      handleCurrentChange(val){
        console.log(val)
      }
    },
    mounted() {
      this.getData()
    },
    filters: { 
    }
  }
</script>
