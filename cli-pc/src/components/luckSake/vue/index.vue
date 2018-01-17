<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item>摇手气</el-breadcrumb-item>  
    </el-breadcrumb>
    <div class="gt-gray-region mb20"> 
      <span class="padding-left-md ml30 mb10">
        <el-input placeholder="输入标题关键字查询" icon="search" v-model="keyWord" style="width:250px" :on-icon-click="getdata" @keyup.native.enter="getdata" @blur="getdata($event)"> 
        </el-input>
      </span> 
       <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn> 
      <div class="h10"></div>
      <el-button type="primary" @click="addActive()" class="ml30">新建活动</el-button> 
    </div> 
    <div class="gt-content">  
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick" >
          <el-tab-pane :label="'全部  ('+countNum.all+')'"   name="-1"></el-tab-pane>
          <el-tab-pane :label="'未开始('+countNum.nostar+')'"   name="0"></el-tab-pane> 
          <el-tab-pane :label="'进行中('+countNum.started+')'"  name="1"></el-tab-pane> 
          <el-tab-pane :label="'已结束('+countNum.over+')'"   name="2"></el-tab-pane>
        </el-tabs>
         <gt-null-data v-if="this.tableData.page.totalNums==0">还没有创建相关活动，
          <span  @click="addActive()">点击这里</span>创建活动吧
        </gt-null-data>
        <el-table :data="tableData.data"  v-if="this.tableData.page.totalNums!=0">
          <el-table-column prop="name" label="活动名称"></el-table-column>
          <el-table-column prop="activityBeginTime" label="活动开始时间">
            <template slot-scope="scope">
              {{scope.row.activityBeginTime|parseTime('{y}-{m}-{d}')}}
            </template>
          </el-table-column>
          <el-table-column prop="activityEndTime" label="活动结束时间">
            <template slot-scope="scope">
              {{scope.row.activityEndTime |parseTime('{y}-{m}-{d}')}}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="活动状态">
            <template slot-scope="scope">
              {{scope.row.status |actStatus(scope.row.status)}} 
            </template> 
          </el-table-column>
          <el-table-column  width="450" label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" v-if="scope.row.isEdit==1" @click="editActive(scope.row.id)">编辑</el-button>
              <el-button class="gt-button-normal blue"  v-if="scope.row.status!=0" @click="record(scope.row.id)">中奖纪录</el-button>
              <el-button class="gt-button-normal blue" @click="askPreview(scope.row.id)">预览链接</el-button>
              <el-button class="gt-button-normal blue" @click="impower(scope.row.id)">核销授权</el-button>
              <el-button class="gt-button-normal"       @click="delBtn(scope.row.id)">删除</el-button> 
            </template>
          </el-table-column>
        </el-table>
        <div class="public-page-fr">
            <el-pagination @current-change="handleCurrentChange"  :current-page.sync="current" :page-size="10"  v-if="this.tableData.page.totalNums!=0"
            layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
            </el-pagination>
        </div> 
    </div> 
    <!-- 链接 -->
    <gt-copy-url :copeData="copeData"></gt-copy-url> 
</div>
</section>
</template>
<script>
import { 
  getActList,getMobileUrl,getShortUrl,delAct,getActCount

}from './../api/api'
  export default{
    data() {
      return { 
        keyWord:"",
        activeName:"-1",
        dialogTip:false, 
        // countNum:{first:"10",second:"20",third:"30",fourth:"40",fifth:"2"},
        countNum:{all:"",nostar:"",started:"",stop:"",over:""},
        tableData:{data:[], page:{}}, 
        current:1,
        //预览连接
        copeData: { 
          url: "",
          shortUrl: "",
          copyUrlVisible: false,
        }, 
      };
    },
    methods: { 
      //初始化数据-----------------------------------star
      getdata(){
        var params    ={};
        params.status =Number(this.activeName);
        params.name   =this.keyWord;
        params.current=this.current;
        params.size   =10;
        console.log(params,77)
        getActList(params).then(data=>{
          if (data.code == 100) {
            this.tableData=data
            console.log(data,'获取首页');
          } else {
              this.$message.error(data.msg + "错误码：[" + data.code + "]");
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
      //获取数量--------------------------------------star
      getCount(){
        getActCount().then(data=>{
          if (data.code == 100) {
            console.log(data,'数量')
            this.countNum.all    =data.data.count1 
            this.countNum.nostar =data.data.count2 
            this.countNum.started=data.data.count3 
            this.countNum.over   =data.data.count4             
          } else {
              this.$message.error(data.msg + "错误码：[" + data.code + "]");
          }
        }).catch(() => {
            this.$message({type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
      //预览连接--------------------------------------------------------------star
      askPreview(mainId) { 
        getMobileUrl({mainId}).then(data => {
          if (data.code == 100) {  
             console.log(data,'连接')
            this.copeData.url = data.data.mobileUrl; 
            getShortUrl(data.data.mobileUrl).then(res => {
               console.log(res,'短链接')
              this.copeData.shortUrl = res;
            });
            this.copeData.copyUrlVisible = true;
          } else {
            this.$message.error(data.msg + "错误码：[" + data.code + "]");
          }
        });
      },
      //删除--------------------------------------star
      delBtn(val) {
            this.$confirm("确定要永久删除此活动吗?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning" 
            }).then(() => {  
            delAct({id:val}).then(data => {  
                if (data.code == 100) { 
                    this.$message({ message: "操作成功", type: "success"}); 
                    this.getdata();
                } else {
                this.$message.error(data.msg + "错误码：[" + data.code + "]");
                }
            });
            }).catch(() => {
                this.$message({ type: "info", message: "已取消删除" });
            });    
      },
      // askPreview(){
      //   this.copeData.copyUrlVisible = true
      // },
      //切换------------------------------------------------------------star
      handleClick() { 
        this.getdata();
      },
      handleCurrentChange(val){
        this.getdata();
      },
      //中奖记录
      record(val){
         this.$router.push({path: '/luckSake/prizeRecord', query: {id: val}});
      },
      //核销授权
      impower(val){
         this.$router.push({path: '/luckSake/cancelOut', query: {id: val}});
      },
      addActive(){
        this.$router.push('/luckSake/addAct')
      },
      editActive(val){
        this.$router.push({path:'/luckSake/editAct', query: {id: val}});
      },
      test(){
        console.log(123)
      }
    },
    mounted() {
      this.getdata();
      this.getCount();
    },
    filters: {
      actStatus(val) {
        if (val == 0) {
          val = "未开始";
        }else if(val == 1){
          val = "进行中"; 
        }else if(val == 2){
          val = "已结束";
        }  
        return val;
      },
    }
  }
</script>
