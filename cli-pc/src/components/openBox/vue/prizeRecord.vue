<style lang="less">

</style>
<template>
<section>
<div class="hd-common turnPlate">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/openBox/index' }">拆礼盒</el-breadcrumb-item>  
      <el-breadcrumb-item>中奖纪录</el-breadcrumb-item>   
    </el-breadcrumb> 
    <div class="gt-gray-region mb20">  
        <span class="padding-left-md ml30 mb10">
            <el-select v-model="prizeType"  @change="getData()"> 
                <el-option  label="全部"          value="-1"></el-option>
                <el-option  label="粉币"          value="1"></el-option>
                <el-option  label="手机流量"      value="2"></el-option>
                <el-option  label="实体物品"      value="4"></el-option>
                <el-option  label="积分"          value="6"></el-option> 
                <el-option  label="优惠券"        value="7"></el-option> 
            </el-select>
        </span> 
        <span class="padding-left-md ml10 mb10">
            <el-select v-model="prizeState"  @change="getData()"> 
                <el-option label="全部"     value="-1"></el-option>
                <el-option label="已提交"   value="3"></el-option>
                <el-option label="未兑奖"   value="1"></el-option>
                <el-option label="已兑奖"   value="2"></el-option> 
            </el-select>
        </span> 
        <span class="padding-left-md ml10 mb10">
            <el-input placeholder="请输入兑奖码" icon="search" v-model="codeWord" style="width:250px"  :on-icon-click="test" @blur="test($event)">
            </el-input>
        </span> 
        <div class="h10"></div>
        <el-button type="primary" @click="toExport()" class="ml30">导出</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data v-if="this.tableData.data.length==0">还没有相关中奖纪录</gt-null-data>  
        <el-table :data="tableData.data"  v-if="this.tableData.data.length!=0">
          <el-table-column prop="prizeName" label="奖项名称">              
          </el-table-column>
          <el-table-column prop="type" label="奖品类型">  
            <template slot-scope="scope">
              {{scope.row.type |prizeType(scope.row.type)}} 
            </template>                 
          </el-table-column>
          <el-table-column prop="cashTime" label="兑奖时间"> 
            <template slot-scope="scope">
              {{scope.row.cashTime|parseTime('{y}-{m}-{d}')}}
            </template>           
          </el-table-column>
          <el-table-column prop="status" label="状态">  
            <template slot-scope="scope">
              {{scope.row.status |prizeStatus(scope.row.status)}} 
            </template>            
          </el-table-column>
          <el-table-column prop="snCode" label="兑奖码">             
          </el-table-column>
          <el-table-column prop="order_option"  label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue"      @click="test(scope.row.id)">详情</el-button>  
              <el-button class="gt-button-normal blue"  v-if="scope.row.status==3"       @click="handOut(scope.row.id)">发放奖品</el-button> 
              <el-button class="gt-button-normal blue"  v-if="scope.row.status==1"  :disabled="true"   @click="handOut(scope.row.id)">发放奖品</el-button>  
            </template>
          </el-table-column>
        </el-table> 
        <div class="public-page-fr" v-if="this.tableData.data.length!=0">
              <el-pagination @current-change="handleCurrentChange"  :page-size="10" 
              layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
              </el-pagination>
        </div> 
    </div>
</div>
</section>
</template>
<script>
import {  
getDemolitionApplyList,editDemolitionApply
}from './../api/api'
  export default{
    data() {
      return { 
        prizeType:"-1",
        prizeState:"-1",
        codeWord:"",
        current:1,
        tableData:{
          data:[ ], 
          page:{ }
        },  
      };
    },
    methods: {
      getData(){
        var params    ={}; 
          params.actId  =this.$router.history.current.query.id; 
          params.current=this.current;
          params.size   =10;
          params.snCode =this.codeWord;
          params.status =this.prizeState;
          params.type   =this.prizeType;
        console.log(params,77)
        getDemolitionApplyList(params).then(data=>{
          if (data.code == 100) {
            this.tableData=data
            console.log(data,33);
          } else {
              this.$message.errorthis.$message.error(data.msg);;
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
      //发放奖品  
      handOut(id){
        editDemolitionApply({id}).then(data=>{
          if (data.code == 100) {
                this.$message({ message: "发放成功", type: "success" });
                this.getData();  
          } else {
              this.$message.errorthis.$message.error(data.msg);;
          }
        }).catch(() => {
            this.$message({ type: "info", message: "网络问题，请刷新重试~" });
        }); 
      },
    //导出---------------------------star
    toExport(){
      var params         = {}; 
      params.actId  = this.$router.history.current.query.id;  
      params.status = this.prizeState;  
      params.type   = this.prizeType;  
      params.snCode   = this.codeWord;  
      window.open(window.BASEDOMAIN+'/app/demolition/exports?actId='+params.actId+'&status='+params.status+'&type='+params.type+'&snCode='+params.snCode);   
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
    filters: {
      prizeStatus(val) {
        if (val == 1) {
          val = "未兑奖";
        }else if(val == 2){
          val = "已兑奖"; 
        }else if(val == 3){
          val = "已提交";
        }  
        return val;
      },
      prizeType(val) {
        if (val == 1) {
          val = "粉币";
        }else if(val == 2){
          val = "手机流量"; 
        }else if(val == 4){
          val = "实体物品";
        }  else if(val == 6){
          val = "积分";
        } else if(val == 7){
          val = "优惠券";
        } 
        return val;
      },
    }
  }
</script>
