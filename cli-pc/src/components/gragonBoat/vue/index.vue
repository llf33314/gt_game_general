<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item>111</el-breadcrumb-item>  
    </el-breadcrumb>
    <div class="gt-gray-region mb20">  
       <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn> 
      <div class="h10"></div>
      <el-button type="primary" @click="test()" class="ml30">新建活动</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data>还没有创建相关活动，
          <span  @click="test()">点击这里</span>创建活动吧
        </gt-null-data> 

        <el-table :data="tableData.data">
          <el-table-column prop="actName" label="活动名称"></el-table-column>
          <el-table-column prop="startTime" label="活动开始时间">
             
          </el-table-column>
          <el-table-column prop="endTime" label="活动结束时间">
            
          </el-table-column>
          <el-table-column prop="state" label="活动状态">
             
          </el-table-column>
          <el-table-column prop="order_option" width="400" label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">编辑</el-button>  
              <el-button class="gt-button-normal"      @click="test(scope.row.id)">删除</el-button> 
            </template>
          </el-table-column>
        </el-table>

         <div class="public-page-fr">
              <el-pagination @current-change="handleCurrentChange"  :page-size="10" 
              layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
              </el-pagination>
          </div> 

    </div>
</div>
</section>
</template>
<script>
  export default{
    data() {
      return { 
        tableData:{
          data:[
            {
              
            }
          ], 
          page:{
            totalNums:31,
            totalPages:4
          }
        }, 
        //预览连接
        copeData: { 
          url: "",
          shortUrl: "",
          copyUrlVisible: false,
        }, 
      };
    },
    methods: {  
      test(){
        console.log(123)
      },
      handleCurrentChange(val){
        console.log(val)
      }
    },
    mounted() {
    
    },
    filters: { 
    }
  }
</script>
