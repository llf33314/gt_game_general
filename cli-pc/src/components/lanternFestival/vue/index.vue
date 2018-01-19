<style lang="less">


</style>
<template>
  <section>
    <div class="hd-common">
      <el-breadcrumb separator="/" class="gt-crumbs">
        <el-breadcrumb-item>互动游戏</el-breadcrumb-item>
        <el-breadcrumb-item>元宵点灯</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="gt-gray-region mb20">
        <span class="padding-left-md ml30 mb10">
          <el-input placeholder="输入标题关键字查询" icon="search" v-model="keyWord" style="width:250px" @keyup.native.enter="searchFuc"  :on-icon-click="searchFuc" :maxlength="25">
          </el-input>
        </span>
        <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn>
        <div class="h10"></div>
        <el-button type="primary" @click="addActive" class="ml30">新建活动</el-button>
      </div>
      <div class="gt-content">
        
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane :label="'全部  ('+countNum.first+')'" name="-1"></el-tab-pane>
          <el-tab-pane :label="'未开始('+countNum.second+')'" name="0"></el-tab-pane>
          <el-tab-pane :label="'进行中('+countNum.third+')'" name="1"></el-tab-pane>
          <el-tab-pane :label="'已结束('+countNum.fifth+')'" name="2"></el-tab-pane>
        </el-tabs>
        <gt-null-data v-if="initRequest && tableData.length < 1">还没有创建相关活动，
          <span @click="addActive">点击这里</span>创建活动吧
        </gt-null-data>
        <el-table :data="tableData" v-else>
          <el-table-column prop="name" label="活动名称"></el-table-column>
          <el-table-column prop="activityBeginTime" label="活动开始时间">
            <template slot-scope="scope">
              {{scope.row.activityBeginTime|parseTime('{y}-{m}-{d} {h}:{i}')}}
            </template>
          </el-table-column>
          <el-table-column prop="activityEndTime" label="活动结束时间">
            <template slot-scope="scope">
              {{scope.row.activityEndTime|parseTime('{y}-{m}-{d} {h}:{i}')}}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="活动状态">
            <template slot-scope="scope">
              {{scope.row.status|actStatus(scope.row.status)}}
            </template>
          </el-table-column>
          <el-table-column prop="order_option" width="450" label="操作">
            <template slot-scope="scope">
              <el-button class="gt-button-normal blue" @click="edit(scope.row.id)">编辑</el-button>
              <el-button class="gt-button-normal blue" @click="askPreview(scope.row.id)">预览链接</el-button>
              <el-button class="gt-button-normal blue" @click="impower(scope.row.id)">核销授权</el-button>
              <el-button class="gt-button-normal blue" @click="record(scope.row.id)">中奖纪录</el-button>
              <el-button class="gt-button-normal" @click="delBtn(scope.row.id)">删除</el-button>
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
  import _ from 'lodash';  //引入lodash
  import axios from 'axios' //引入axios
  //请求canceltoken列表
  let sources = [];

  import api from '../api/api'
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
        activeName: '-1',
        countNum: {
          first: "10",
          second: "20",
          third: "30",
          fourth: "40",
          fifth: "2"
        },
        tableData: [{
            name: "活动名称",
            status: 1,
            activityBeginTime: 1513008000000,
            activityEndTime: 1514476800000,
          }],
        //预览连接
        copeData: {
          url: "",
          shortUrl: "",
          copyUrlVisible: false,
        },
      };
    },
    methods: {
      search: _.debounce(
        function () {
          let that = this;
          //删除已经结束的请求
          _.remove(sources, function (n) {
            return n.source === null;
          });
          //取消还未结束的请求
          sources.forEach(function (item) {
            if (item !== null && item.source !== null && item.status === 1) {
              item.status = 0;
              item.source.cancel('取消上一个')
            }
          });
        
          //创建新的请求cancelToken,并设置状态请求中
          var sc = {
            source: axios.CancelToken.source(),
            status: 1 //状态1：请求中，0:取消中
          };
          //这个对象加入数组中
          sources.push(sc);
　　　　　　//开始搜索数据，yourhttp替换成你自己的请求路径
          let params = {
            current: that.currentPage,
            size: that.pageSize,
            name: that.keyWord,
            status: parseFloat(that.activeName)
          }
          axios.post(window.BASEDOMAIN + '/app/lantern/getLanternList',  params, {
            cancelToken: sc.source.token
          }).then(function (res) {
            // 请求成功 网络层
            sc.source = null; //置空请求canceltoken
            // 后台服务器层
            if (res.data.code == 100) {
                 that.tableData = res.data.data
            }
          }).catch(function (thrown) {
            //请求失败
            sc.source = null; //置空请求canceltoken
          });
        },
        500 //空闲时间间隔设置500ms
      ),

      searchFuc() {
        this.currentPage = this.initCurrentPage
        this.fetchData()
      },
      delEmployee(hide) {
        this.dialogTip = false
      },
      askPreview(id) {
        let param = { mainId: id }
        api.getMobileUrl(param).then(res => {
          if (res.code == 100) {
            // 页面链接
            this.copeData.url = res.data.mobileUrl
            // 短信链接
            this.$util.getShortUrl(res.data.mobileUrl).then(res => {
              this.copeData.shortUrl = res
            })
            this.copeData.copyUrlVisible = true;
          } else {
            this.$message.error('获取链接失败')
          }
        })

      },
      //删除------------------------------------------------------------star
      delBtn(id) {
        this.$confirm('此操作将永久删除该活动, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          api.delLantern({
            id: idnex
          }).then(res => {
            if (res.code) {
              this.$message.success('删除成功!');
              this.currentPage = this.initCurrentPage
              this.fetchData()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //开始------------------------------------------------------------star 
      actBtn(val) {
        this.$confirm('确定要开启该活动吗？', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      },
      //暂停------------------------------------------------------------star
      stopBtn(val) {
        this.$confirm('确定要暂停该活动吗？', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      },
      //切换------------------------------------------------------------star
      handleClick() {
        console.log(this.activeName, 11);
        this.currentPage = this.initCurrentPage
        this.fetchData()
      },
      handleCurrentChange(val) {
        console.log(val)
      },
      addActive() {
        this.$router.push('/lanternFestival/addAct')
      },
      edit(id) {
        this.$router.push({ path: '/lanternFestival/editAct', query: { id: id }})
      },
      //中奖记录
      record(val){
         this.$router.push({path: '/lanternFestival/prizeRecord', query: {id: val}});
      },
      //核销授权
      impower(val){
         this.$router.push({path: '/lanternFestival/cancelOut', query: {id: val}});
      },
      fetchData(initRequest) {
        initRequest ? (this.initRequest = true) : (this.initRequest = false);
        let params = {
          current: this.currentPage,
          size: this.pageSize,
          name: this.keyWord,
          status: parseFloat(this.activeName)
        }
        api.getLanternList(params).then(res => {
          if (res.code == 100) {
            this.tableData = res.data
            this.totalNums = res.page.totalNums
          } 
        })
      }
    },
    created() {
      this.fetchData()
    },
    mounted() {

    },
    filters: {
      actStatus(val) {
        if (val == 0) {
          val = "未开始";
        } else if (val == 1) {
          val = "进行中";
        } else if (val == 2) {
          val = "已暂停";
        } else if (val == 3) {
          val = "已结束";
        }
        return val;
      },
    }
  }

</script>
