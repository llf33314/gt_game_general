<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item>一站到底</el-breadcrumb-item>  
    </el-breadcrumb>
    <div class="gt-gray-region mb20"> 
      <span class="padding-left-md ml30 mb10">
        <el-input placeholder="输入标题关键字查询" icon="search" v-model="keyWord" style="width:250px" @change="getdata()"> 
        </el-input>
      </span> 
       <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn> 
      <div class="h10"></div>
      <el-button type="primary" @click="addActive()" class="ml30">新建活动</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data>还没有创建相关活动，
          <span  @click="addActive()">点击这里</span>创建活动吧
        </gt-null-data>
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane :label="'全部  ('+countNum.first+')'"   name="0"></el-tab-pane>
          <el-tab-pane :label="'未开始('+countNum.third+')'"   name="2"></el-tab-pane> 
          <el-tab-pane :label="'进行中('+countNum.second+')'"  name="1"></el-tab-pane> 
          <el-tab-pane :label="'已结束('+countNum.fifth+')'"   name="4"></el-tab-pane>
        </el-tabs>
        <el-table :data="tableData.data">
          <el-table-column prop="actName" label="活动名称"></el-table-column>
          <el-table-column prop="startTime" label="活动开始时间">
            <template slot-scope="scope">
              {{scope.row.startTime|parseTime('{y}-{m}-{d}')}}
            </template>
          </el-table-column>
          <el-table-column prop="endTime" label="活动结束时间">
            <template slot-scope="scope">
              {{scope.row.endTime|parseTime('{y}-{m}-{d}')}}
            </template>
          </el-table-column>
          <el-table-column prop="state" label="活动状态">
            <template slot-scope="scope">
              {{scope.row.state|actStatus(scope.row.state)}} 
            </template> 
          </el-table-column>
          <el-table-column prop="order_option" width="400" label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">编辑</el-button>
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">中奖纪录</el-button>
              <el-button class="gt-button-normal blue" @click="askPreview(scope.row.id)">预览链接</el-button>
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">核销授权</el-button>
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">开始活动</el-button>
              <el-button class="gt-button-normal"      @click="dialogTip=true">删除</el-button> 
            </template>
          </el-table-column>
        </el-table>
        <div class="public-page-fr">
            <el-pagination @current-change="handleCurrentChange"  :page-size="10" 
            layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
            </el-pagination>
        </div> 
    </div>
    <!-- 删除 -->
    <gt-del-tip :showTip.sync="dialogTip" :confirmFuc="delEmployee">
      <p>确定删除该活动吗</p> 
    </gt-del-tip>
    <!-- 链接 -->
    <gt-copy-url :copeData="copeData"></gt-copy-url>
    
</div>
</section>
</template>
<script>
  export default{
    data() {
      return { 
        keyWord:"",
        activeName:0,
        dialogTip:false, 
        countNum:{first:"10",second:"20",third:"30",fourth:"40",fifth:"2"},
        tableData:{
          data:[
            {
              actName:"活动名称",
              state:1,
              startTime:1513008000000,
              endTime:1514476800000,
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
      getdata(){
        console.log(3);
      },
      delEmployee(hide) {
        console.log("触发事件");
        this.dialogTip=false
      },
      askPreview(){
        this.copeData.copyUrlVisible = true
      },
      //切换------------------------------------------------------------star
      handleClick() { 
        this.current=1;
        this.getdata();
      },
      handleCurrentChange(val){
        console.log(val)
      },
      addActive(){
        this.$router.push('/standStill/addAct')
      },
      test(){
        console.log(123)
      }
    },
    mounted() {
    
    },
    filters: {
      actStatus(val) {
        if (val == 0) {
          val = "未开始";
        }else if(val == 1){
          val = "进行中";
        }else if(val == 2){
          val = "已暂停";
        }else if(val == 3){
          val = "已结束";
        }  
        return val;
      },
    }
  }
</script>
