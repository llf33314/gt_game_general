<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/crazyMoney/index' }">疯狂数钱</el-breadcrumb-item>   
      <el-breadcrumb-item>核销授权</el-breadcrumb-item>   
    </el-breadcrumb> 
    <div class="gt-gray-region mb20">  
       <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn>  
      <el-button type="primary" @click="add" class="ml30">新增授权</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data v-if="initRequest && tableData.length < 1">还没有相关数据，
          <span  @click="add">点击这里</span>新增核销员吧
        </gt-null-data>  

        <el-table v-else ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="memberName" label="核销员">
                <!-- <template slot-scope="scope">{{scope.row.name0 }}</template> -->
            </el-table-column>
            <el-table-column prop="createtime" label="创建时间">
                <template slot-scope="scope">
                    {{ scope.row.createtime | DateFormat('yyyy-MM-dd hh:mm:ss') }}
                </template>
            </el-table-column>
            <el-table-column prop="address" label="操作" show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-button class="gt-button-normal" @click="delOne(scope.row.id)">删除</el-button>    
                </template>
            </el-table-column>
        </el-table> 

        <!-- 页码 -->
        <div class="mt20" v-show="tableData.length">
            <div class="pull-left">
                <!-- <el-button class="ticket-button-small" @click="toggleSelection()">取消选择</el-button> -->
                <el-button class="ticket-button-small" @click="delAll">批量删除</el-button>
            </div> 
            <div class="pull-right">
                <el-pagination @current-change="handleCurrentChange"  :current-page.sync="currentPage" :page-size="pageSize" 
                layout="prev, pager, next, jumper" :total="totalNums">
                </el-pagination>
            </div> 
        </div>  
        <!-- 新增授权弹窗 -->
        <el-dialog title="新增授权" :visible.sync="cancelCode" class="detail-dialog">  
            <div class="grid-content" style="width:100%;text-align:center;"> 
                <div>
                    <img :src="$baseURL + '/app/link/buildQrcode?url='+ Code"  style="width:180px;height:180px;margin:15px 0">
                </div>
                <div>扫描二维码成为核销员</div>
            </div>  
        </el-dialog>
        <!-- 成功提示 -->
        <el-dialog title="提示" :visible.sync="showSuccee" class="gt-del-tip">
            <div class="el-message-box__status el-icon-circle-check"></div>
            <div class="mg-l50">
                <slot>
                    <p class="c333">授权成功</p>
                    <p class="c666 mt10">3秒后返回"授权管理列表"...</p>
                </slot>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="test()">继续新增</el-button>
                <el-button @click="showSuccee=false">取 消</el-button>
            </div>
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
      initRequest: false,
      // 分页
      initCurrentPage: 1,
      currentPage: 1,
      initPageSize: 10,
      pageSize: 10,
      totalNums: 0,

      Code: "",
      cancelCode: false,

      multipleSelection: [],
      tableData: [
        {
          id: 0,
          memberName: "熊小猫",
          createtime: "1514252903"
        },
        {
          id: 12,
          memberName: "王小虎",
          createtime: "1514252903"
        },
        {
          id: 0,
          memberName: "熊小猫",
          createtime: "1514252903"
        },
        {
          id: 12,
          memberName: "王小虎",
          createtime: "1514252903"
        }
      ],
      showSuccee: false
    };
  },
  methods: {
    //新增授权
    add() {
      this.cancelCode = true;
    },
    //全选
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
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

        api.delAuthority(params).then(res => {
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
        api.delAuthority(params).then(res => {
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
        size: this.pageSize
      };
      api.getAuthorityList(params).then(res => {
        if (res.code == 100) {
          this.tableData = res.data;
          this.totalNums = res.page.totalNums;
        }
      });
    }
  },
  created() {
    this.fetchData(true);
  }
};
</script>
