<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/standStill/index' }">一站到底</el-breadcrumb-item>  
      <el-breadcrumb-item>题库管理</el-breadcrumb-item>   
    </el-breadcrumb> 
    <div class="gt-gray-region mb20">  
        <el-button type="primary" @click="addAskBtn()" class="ml30">新增题库</el-button> 
    </div> 
    <div class="gt-content">  
        <el-table :data="tableData.data"  v-if="this.tableData.data.length!=0">
          <el-table-column prop="quesAmount" label="题库序号">              
          </el-table-column>
          <el-table-column prop="bankName" label="题库名称">   
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间"> 
            <template slot-scope="scope">
              {{scope.row.createTime|parseTime('{y}-{m}-{d} {h}:{i}')}}
            </template>           
          </el-table-column> 
          <el-table-column prop="order_option"  label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue"      @click="showDetailBtn(scope.row.id)">题库编辑</el-button>  
              <el-button class="gt-button-normal "    @click="handOut(scope.row.id)">删除</el-button>  
            </template>
          </el-table-column>
        </el-table> 
        <gt-null-data v-if="this.tableData.data.length==0">没有相关数据</gt-null-data>   
    </div>
</div>
</section>
</template>
<script>
import {  
getQuesbank
}from './../api/api'
  export default{
    data() {
      return {  
        tableData:{
          data:[ ], 
          page:{ }
        },  
        showDetail:false,
        showDetailData:[]
      };
    },
    methods: {
        showDetailBtn(val){
            this.showDetail=true
            this.showDetailData=val
            console.log(this.showDetailData,852222); 
        },
      //题库列表---------------------------star
      getData(){ 
        getQuesbank().then(data=>{
          if (data.code == 100) {
            this.tableData=data
            console.log(data,'题库列表');
          } else {
              this.$message.error(data.msg);
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },

      addAskBtn(){
        this.$router.push({path:'/standStill/testBase'});
      },      
      test(){
        console.log(123)
      },
      handleCurrentChange(val){
        console.log(val)
      }
    },
    mounted() {
      this.getData();
    
    },

  }
</script>
