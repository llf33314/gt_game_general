<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item>圣诞大礼包</el-breadcrumb-item>  
    </el-breadcrumb>
    <div class="gt-gray-region mb20"> 
      <span class="padding-left-md ml30 mb10">
        <el-input placeholder="输入标题关键字查询" icon="search" v-model="keyWord" style="width:250px" @change="getdata()"> 
        </el-input>
      </span> 
       <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn> 
      <div class="h10"></div>
      <el-button type="primary" @click="addActive" class="ml30">新建活动</el-button> 
    </div> 
    <div class="gt-content"> 
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane :label="'全部  ('+countNum.count1+')'"   name="0"></el-tab-pane>
          <el-tab-pane :label="'未开始('+countNum.count2+')'"  name="2"></el-tab-pane> 
          <el-tab-pane :label="'进行中('+countNum.count3+')'"   name="3"></el-tab-pane> 
          <el-tab-pane :label="'已暂停('+countNum.count4+')'"  name="4"></el-tab-pane>
          <el-tab-pane :label="'已结束('+countNum.count5+')'"   name="5"></el-tab-pane>
        </el-tabs>
        <gt-null-data v-if="initRequest && tableData.length < 1">还没有创建相关活动，
          <span @click="addActive">点击这里</span>创建活动吧
        </gt-null-data>
        <el-table :data="tableData" v-else>
          <el-table-column prop="name" label="活动名称" min-width="160" show-overflow-tooltip></el-table-column>
          <el-table-column prop="activityBeginTime" label="活动开始时间" min-width="200">
            <template slot-scope="scope">
              {{ scope.row.activityBeginTime | DateFormat('yyyy-MM-dd hh:mm:ss') }}
            </template>
          </el-table-column>
          <el-table-column prop="activityEndTime" label="活动结束时间" min-width="200">
            <template slot-scope="scope">
              {{ scope.row.activityEndTime | DateFormat('yyyy-MM-dd hh:mm:ss') }}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="活动状态" min-width="100">
            <template slot-scope="scope">
              {{scope.row.status | activityStatus }}
            </template>
          </el-table-column>
          <el-table-column prop="order_option" width="450" label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">编辑</el-button>
              <el-button class="gt-button-normal blue" @click="askPreview(scope.row.id)">预览链接</el-button>              
              <el-button class="gt-button-normal blue" @click="impower(scope.row.id)">核销授权</el-button>
              <el-button class="gt-button-normal blue" @click="record(scope.row.id)">中奖纪录</el-button>  
              <el-button class="gt-button-normal blue" @click="stopBtn(scope.row.id)">暂停活动</el-button>    
              <el-button class="gt-button-normal blue" @click="actBtn(scope.row.id)">开始活动</el-button> 
              <el-button class="gt-button-normal"      @click="delOne(scope.row.id)">删除</el-button> 
            </template>
          </el-table-column>
        </el-table>
        <div class="public-page-fr" v-show="tableData.length">
          <el-pagination @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-size="pageSize" layout="prev, pager, next, jumper" :total="totalNums">
          </el-pagination>
        </div>
    </div>
    <!-- 删除 -->
    <!-- <gt-del-tip :showTip.sync="dialogTip" :confirmFuc="delEmployee">
      <p>确定删除该活动吗</p> 
      <p class="grey mt10">点击确定后，将不可以回复哦~</p> 
    </gt-del-tip> -->
    <!-- 链接 -->
    <gt-copy-url :copeData="copeData"></gt-copy-url>    
</div>
</section>
</template>

<script>
import api from "../api/api";
export default {
  data() {
    return {
      // 分页
      initCurrentPage: 1,
      currentPage: 1,
      initPageSize: 10,
      pageSize: 10,
      totalNums: 0,

      keyWord: "",
      activeName: "-1",
      countNum: {
        count1: 0,
        count2: 0,
        count3: 0,
        count4: 0,
        count5: 0
      },
      tableData: [
        {
          name: "活动名称",
          status: 1,
          activityBeginTime: 1513008000000,
          activityEndTime: 1514476800000
        }
      ],
      //预览连接
      copeData: {
        url: "",
        shortUrl: "",
        copyUrlVisible: false
      }
    };
  },
  methods: {
    searchFuc() {
      this.currentPage = this.initCurrentPage;
      this.fetchData();
    },
    delEmployee(hide) {
      this.dialogTip = false;
    },
    askPreview(id) {
      let param = { mainId: id };
      api.getMobileUrl(param).then(res => {
        if (res.code == 100) {
          // 页面链接
          this.copeData.url = res.data.mobileUrl;
          // 短信链接
          this.$util.getShortUrl(res.data.mobileUrl).then(res => {
            this.copeData.shortUrl = res;
          });
          this.copeData.copyUrlVisible = true;
        } else {
          this.$message.error("获取链接失败");
        }
      });
    },
    //删除------------------------------------------------------------star
    delOne(id) {
      this.$confirm("此操作将永久删除该活动, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          api
            .delActivity({
              id: id
            })
            .then(res => {
              if (res.code) {
                this.$message.success("删除成功!");
                this.currentPage = this.initCurrentPage;
                this.fetchData();
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //开始------------------------------------------------------------star
    actBtn(val) {
      this.$confirm("确定要开启该活动吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    //暂停------------------------------------------------------------star
    stopBtn(val) {
      this.$confirm("确定要暂停该活动吗？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    //切换------------------------------------------------------------star
    handleClick() {
      this.fetchData();
    },
    addActive() {
      this.$router.push("/christmasGift/index");
    },
    edit(id) {
      this.$router.push({
        path: "/lanternFestival/editAct",
        query: { id: id }
      });
    },
    //中奖记录
    record(id) {
      this.$router.push({
        path: "/christmasGift/prizeRecord",
        query: { id: id }
      });
    },
    //核销授权
    impower(val) {
      this.$router.push({
        path: "/christmasGift/cancelOut",
        query: { id: val }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchData();
    },
    fetchData(initRequest) {
      initRequest ? (this.initRequest = true) : (this.initRequest = false);
      let params = {
        current: this.currentPage,
        size: this.pageSize,
        name: this.keyWord,
        status: parseFloat(this.activeName)
      };
      Promise.all([ api.getActivityList(params), api.countActivity({ name: this.keyWord }) ]).then(res => {
        if (res[0].code == 100) {
          this.tableData = res[0].data;
          this.totalNums = res[0].page.totalNums;
        }
        if (res[1].code == 100) {
          this.countNum = res[1].data;
        }
      })
    }
  },
  created() {
    this.fetchData(true);
  }
};
</script>

