<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/luckyNine/index' }">幸运九宫格</el-breadcrumb-item>    
      <el-breadcrumb-item>中奖纪录</el-breadcrumb-item>   
    </el-breadcrumb> 
    <div class="gt-gray-region mb20">  
        <span class="padding-left-md ml30 mb10">
            <el-select v-model="type"  placeholder="请选择奖品类型"> 
                 <el-option  v-for="(item, index) in options.type"  :key="index"  :label="item.label"  :value="item.value"></el-option>
            </el-select>
        </span> 
        <span class="padding-left-md ml10 mb10">
                <el-select v-model="status" placeholder="请选择状态"> 
                  <el-option  v-for="(item, index) in options.status" :key="index" :label="item.label" :value="item.value"></el-option>
                </el-select>
        </span> 
        <span class="padding-left-md ml10 mb10">
            <el-input placeholder="请输入兑奖码" icon="search" v-model="snCode" style="width:250px"  @keyup.native.enter="searchFuc"  :on-icon-click="searchFuc" :maxlength="25"> 
            </el-input>
        </span> 
        <div class="h10"></div>
        <el-button type="primary" @click="exportFuc" class="ml30">导出</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data v-if="initRequest && tableData.length < 1">还没有相关中奖纪录</gt-null-data>  
        <el-table v-else :data="tableData">
          <el-table-column prop="prizeName" label="奖项名称">              
          </el-table-column>
          <el-table-column prop="type" label="奖品类型">   
            <template slot-scope="scope">
                 {{ scope.row.type | prizeType }}  
            </template>          
          </el-table-column>
          <el-table-column prop="cashTime" label="兑奖时间">  
            <template slot-scope="scope">
                 {{ scope.row.cashTime | DateFormat('yyyy-MM-dd hh:mm:ss') }}  
            </template>           
          </el-table-column>
          <el-table-column prop="status" label="状态">  
            <template slot-scope="scope">
              {{ scope.row.status | prizeStatus }}
            </template>

          </el-table-column>
          <el-table-column prop="snCode" label="兑奖码">             
          </el-table-column>
          <el-table-column prop="order_option" width="260" label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" @click="sendPrize(scope.row.id)" >发放奖品</el-button>
              <el-button class="gt-button-normal blue" @click="detail(scope.row)">详情</el-button>
              <el-button class="gt-button-normal" @click="delOne(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table> 
         <div class="public-page-fr" v-show="tableData.length">
          <el-pagination @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-size="pageSize" layout="prev, pager, next, jumper" :total="totalNums">
          </el-pagination>
        </div>

        <!-- 详情 -->
        <el-dialog title="详情" :visible.sync="DetailVisible" class="detail-dialog"> 
            <gt-prize-detail :data="DetailData"></gt-prize-detail>
        </el-dialog>
    </div>
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
      snCode: "",
      status: -1,
      type: -1,

      tableData: [
        {
          address: "string",
          addressName: "string",
          cashTime: "1516341837149",
          id: 0,
          memberId: 0,
          memberName: "string",
          memberPhone: "string",
          nickname: "string",
          prizeName: "string",
          prizeUnit: 0,
          receiveType: 2,
          score: 0,
          snCode: "string",
          status: 2,
          type: 1
        }
      ],
      options: {
        type: [
          { label: "全部", value: -1 },
          { label: "粉币", value: 1 },
          { label: "手机流量", value: 2 },
          { label: "手机话费", value: 3 },
          { label: "实体物品", value: 4 },
          { label: "谢谢参与", value: 5 },
          { label: "积分", value: 6 }
        ],
        status: [
          { label: "全部", value: -1 },
          { label: "已提交", value: 1 },
          { label: "未兑奖", value: 2 },
          { label: "已兑奖", value: 3 }
        ]
      },
      // 详情
      DetailVisible: false,
      DetailData: {}
    };
  },
  methods: {
    searchFuc() {
      this.currentPage = this.initCurrentPage;
      this.fetchData();
    },
    exportFuc() {
      let params = {
        actId: this.$route.actId,
        status: this.status,
        type: this.type,
        snCode: this.snCode
      };
      // location.href = api.exportLantern(params)
      window.open(api.exportPrizeRecord(params));
    },
    sendPrize(id) {
      this.$confirm("确定要发送奖品?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        api.sendWinning({ id: id }).then(res => {
          if (res.code == 100) {
            this.$message.success("发送奖品成功");
          } else {
            this.$message.error("发送奖品失败");
          }
        });
      });
    },
    detail(row) {
      this.DetailData = row;
      this.DetailVisible = true;
    },
    delOne(id) {
      this.$confirm("确定要删除选中的核销员?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        let params = {
          actId: this.$route.query.id,
          id: [id]
        };

        api.delWinning(params).then(res => {
          if (res.code == 100) {
            this.$message.success("删除成功");
            this.currentPage = this.initCurrentPage;
            this.fetchData();
          } else {
            this.$message.error("删除失败");
          }
        });
      });
    },
    delAll() {
      if (this.multipleSelection.length < 1) {
        this.$message.info("请勾选需要删除的核销员");
        return;
      }
      let array = [];
      this.$confirm("确定要删除选中的核销员?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.multipleSelection.forEach(function(item, index, arr) {
          array.push(item.id);
        }, this);
        let params = {
          actId: this.$route.query.id,
          id: array
        };
        api.delWinning(params).then(res => {
          if (res.code == 100) {
            this.$message.success("删除成功");
            this.currentPage = this.initCurrentPage;
            this.fetchData();
          } else {
            this.$message.error("删除失败");
          }
        });
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchData();
    },
    fetchData(initRequest) {
      initRequest ? (this.initRequest = true) : (this.initRequest = false);
      let params = {
        actId: this.$route.query.id,
        current: this.currentPage,
        size: this.pageSize,
        snCode: this.snCode,
        status: this.status,
        type: this.type
      };
      api.getWinningList(params).then(res => {
        if (res.code == 100) {
          this.tableData = res.data
          this.totalNums = res.page.totalNums
        }
      });
    }
  },
  created() {
    this.fetchData(true);
  },
  mounted() {},
};
</script>
