<style lang="less">

</style>
<template>
  <section>
    <div class="hd-common turnPlate">
      <el-breadcrumb separator="/" class="gt-crumbs">
        <el-breadcrumb-item>互动游戏</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/scrapeHappy/index' }">刮刮乐</el-breadcrumb-item>
        <el-breadcrumb-item>中奖纪录</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="gt-gray-region mb20">
        <span class="padding-left-md ml30 mb10">
          <el-select v-model="prizeType" @change="getData()">
            <el-option label="全部" value="-1"></el-option>
            <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value">
            </el-option>
          </el-select>
        </span>
        <span class="padding-left-md ml10 mb10">
          <el-select v-model="prizeState" @change="getData()">
            <el-option label="全部" value="-1"></el-option>
            <el-option label="已提交" value="3"></el-option>
            <el-option label="未兑奖" value="1"></el-option>
            <el-option label="已兑奖" value="2"></el-option>
          </el-select>
        </span>
        <span class="padding-left-md ml10 mb10">
          <el-input placeholder="请输入兑奖码" icon="search" v-model="codeWord" style="width:250px" :on-icon-click="getData" @keyup.enter.native="getData()" @blur="getData($event)">
          </el-input>
        </span>
        <div class="h10"></div>
        <el-button type="primary" @click="toExport()" class="ml30">导出</el-button>
      </div>
      <div class="gt-content">
        <gt-null-data v-if="this.tableData.data.length==0">还没有相关中奖纪录</gt-null-data>
        <el-table ref="multipleTable" :data="tableData.data" v-if="this.tableData.data.length!=0" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="prizeName" label="奖项名称">
          </el-table-column>
          <el-table-column prop="type" label="奖品类型">
            <template slot-scope="scope">
              {{scope.row.type |prizeType(scope.row.type)}}
            </template>
          </el-table-column>
          <el-table-column prop="cashTime" label="中奖时间">
            <template slot-scope="scope">
              {{scope.row.cashTime|parseTime('{y}-{m}-{d} {h}:{i}')}}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态">
            <template slot-scope="scope">
              {{scope.row.status |prizeStatus(scope.row.status)}}
            </template>
          </el-table-column>
          <el-table-column prop="snCode" label="兑奖码">
          </el-table-column>
          <el-table-column prop="order_option" label="操作">
            <template slot-scope="scope">
              <el-button class="gt-button-normal blue" @click="showDetailBtn(scope.row)">详情</el-button>
              <el-button class="gt-button-normal blue" v-if="scope.row.status==3" @click="handOut(scope.row.id)">发放奖品</el-button>
              <el-button class="gt-button-normal blue" v-if="scope.row.status==1" :disabled="true" @click="handOut(scope.row.id)">发放奖品</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 详情 -->
        <el-dialog title="详情" :visible.sync="showDetail" class="detail-dialog">
          <div>
            <p>
              <span class="w20_demo">中奖人</span>
              <b> : </b> {{showDetailData.nickname}}</p>
            <p>
              <span class="w20_demo">兑奖人</span>
              <b> : </b> -</p>
            <p>
              <span class="w20_demo">兑奖人联系方式</span>
              <b> : </b> {{showDetailData.memberPhone }}</p>
            <p v-if="showDetailData.status==1">
              <span class="w20_demo">兑奖时间</span>
              <b> : </b> -</p>
            <p v-if="showDetailData.status!=1">
              <span class="w20_demo">领取方式</span>
              <b> : </b> {{showDetailData.receiveType|receiveTypeStatus(showDetailData.receiveType)}}</p>
            <p v-if="showDetailData.receiveType==1">
              <span class="w20_demo">到店领取地址</span>
              <b> : </b> {{showDetailData.addressName}}</p>
            <p v-if="showDetailData.receiveType==2">
              <span class="w20_demo">收货人姓名</span>
              <b> : </b> {{showDetailData.addressName}}</p>
            <p v-if="showDetailData.receiveType==2">
              <span class="w20_demo">收货地址</span>
              <b> : </b> {{showDetailData.address}}</p>
          </div>
        </el-dialog>
        <div class="mt20" v-if="this.tableData.data.length!=0">
          <div class="pull-left">
            <el-button class="ticket-button-small" @click="toggleSelection()">取消选择</el-button>
            <el-button class="ticket-button-small" @click="select_delbtn()">批量删除</el-button>
          </div>
          <div class="pull-right">
            <el-pagination @current-change="handleCurrentChange" :page-size="10" layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import { getWinningList, getPrizeType, editDragonboatApply, delScratchWinning } from './../api/api'
export default {
  data() {
    return {
      prizeType: "-1",
      prizeState: "-1",
      codeWord: "",
      current: 1,
      tableData: {
        data: [],
        page: {}
      },
      options: [],
      showDetail: false,
      showDetailData: [],
      multipleSelection: [],
    };
  },
  methods: {
    showDetailBtn(val) {
      this.showDetail = true
      this.showDetailData = val
      console.log(this.showDetailData, 852222);

    },
    //中奖列表---------------------------star
    getData() {
      var params = {};
      params.actId = this.$router.history.current.query.id;
      params.current = this.current;
      params.size = 10;
      params.snCode = this.codeWord;
      params.status = this.prizeState;
      params.type = this.prizeType;
      console.log(params, 77)
      getWinningList(params).then(data => {
        if (data.code == 100) {
          this.tableData = data
          console.log(data, '中奖列表');
        } else {
          this.$message.error(data.msg);
        }
      }).catch(() => {
        this.$message({ type: "info", message: "网络问题，请刷新重试网络问题，请刷新重试~" });
      });
    },
    //获取奖品类型-----------star
    getPrizeTypeData() {
      getPrizeType().then(data => {
        if (data.code == 100) {
          console.log(data, 1233);
          this.options = data.data
          console.log(this.options, 444);
        } else {
          this.$message.error(data.msg);
        }
      }).catch(() => {
        this.$message({ type: "info", message: "网络问题，请刷新重试~" });
      });
    },
    //导出---------------------------star
    toExport() {
      var params = {};
      params.actId = this.$router.history.current.query.id;
      params.status = this.prizeState;
      params.type = this.prizeType;
      params.snCode = this.codeWord;
      window.open(window.BASEDOMAIN + '/app/scratch/exportScratch?actId=' + params.actId + '&status=' + params.status + '&type=' + params.type + '&snCode=' + params.snCode);
    },
    //发放奖品  
    handOut(id) {
      editDragonboatApply({ id }).then(data => {
        if (data.code == 100) {
          this.$message({ message: "发放成功", type: "success" });
          this.getData();
        } else {
          this.$message.error(data.msg);
        }
      }).catch(() => {
        this.$message({ type: "info", message: "网络问题，请刷新重试~" });
      });
    },
    //批量删除=======================
    select_delbtn() {
      if (this.multipleSelection.length > 0) {
        var params = {}
        var id = [];
        for (var i = 0; i < this.multipleSelection.length; i++) {
          var arr = {
            id: this.multipleSelection[i].id
          };
          id.push(arr.id);
        }
        params.actId = Number(this.$router.history.current.query.id);
        params.id = id;
        this.$confirm('确定删除已选中的中奖纪录？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delScratchWinning(params).then(data => {
            if (data.code == 100) {
              this.$message({ message: "操作成功", type: "success" });
              this.getData();
            } else {
              this.$message.error(data.msg);
            }
          });
        }).catch(() => {
          this.$message({ type: "info", message: "已取消删除" });
        });
      } else {
        this.$message({
          type: 'info',
          message: '请选择需删除的中奖纪录'
        });
      }
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
    handleCurrentChange(val) {
      console.log(val)
    }
  },
  mounted() {
    this.getData();
    this.getPrizeTypeData();

  },
  //转换状态===========================================
  filters: {
    prizeStatus(val) {
      if (val == 1) {
        val = "未兑奖";
      } else if (val == 2) {
        val = "已兑奖";
      } else if (val == 3) {
        val = "已提交";
      }
      return val;
    },
    receiveTypeStatus(val) {
      if (val == 1) {
        val = "到店领取";
      } else if (val == 2) {
        val = "邮寄";
      } else if (val == 3) {
        val = "直接兑奖";
      }
      return val;
    },
    prizeType(val) {
      if (val == 1) {
        val = "粉币";
      } else if (val == 2) {
        val = "手机流量";
      } else if (val == 3) {
        val = "手机话费";
      } else if (val == 4) {
        val = "实体物品";
      } else if (val == 6) {
        val = "积分";
      } else if (val == 7) {
        val = "优惠券";
      }
      return val;
    },
  }
}
</script>
