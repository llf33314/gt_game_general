<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item @click.native="$util.ClickApply">互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/standStill/index' }">一站到底</el-breadcrumb-item>  
      <el-breadcrumb-item>题库管理</el-breadcrumb-item>   
    </el-breadcrumb> 
    <div class="gt-gray-region mb20">  
        <el-button type="primary" @click="addAskBtn()" class="ml30">新增题库</el-button> 
    </div> 
    <div class="gt-content">  
        <el-table :data="tableData.data"  v-if="this.tableData.data.length!=0">
          <el-table-column prop="quesAmount" label="题库序号">   
              <template slot-scope="scope"> 
                {{scope.$index+1}}
              </template>           
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
getQuesbank,removeStandQuesbank
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
      //删除题库---------------------------star
      handOut(val){ 
        this.$confirm('此操作将永久删除该题库, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeStandQuesbank({id:val}).then(data=>{
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
      addAskBtn(){
        this.$router.push({path:'/standStill/addQuest'});
      },    
      showDetailBtn(val){
           this.$router.push({path: '/standStill/editQuest', query: {id: val}}); 
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
