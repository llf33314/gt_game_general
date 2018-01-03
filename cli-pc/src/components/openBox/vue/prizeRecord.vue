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
            <el-select v-model="prizeType"  @change="test()"> 
                <el-option label="请选择奖品类型"       value=""></el-option>
                <el-option label="粉币"       :value="1"></el-option>
                <el-option label="手机流量"   :value="2"></el-option>
                <el-option label="手机话费"   :value="3"></el-option>
                <el-option label="实体物品"   :value="4"></el-option>
                <el-option label="谢谢参与"   :value="5"></el-option> 
                <el-option label="积分"       :value="6"></el-option> 
            </el-select>
        </span> 
        <span class="padding-left-md ml10 mb10">
                <el-select v-model="prizeState"  @change="test()"> 
                    <el-option label="请选择状态"       value=""></el-option>
                    <el-option label="已提交"   :value="1"></el-option>
                    <el-option label="未兑奖"   :value="2"></el-option>
                    <el-option label="已兑奖"   :value="3"></el-option> 
                </el-select>
        </span> 
        <span class="padding-left-md ml10 mb10">
            <el-input placeholder="请输入兑奖码" icon="search" v-model="keyWord" style="width:250px"  :on-icon-click="test" @blur="test($event)">
            </el-input>
        </span> 
        <div class="h10"></div>
        <el-button type="primary" @click="test()" class="ml30">导出</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data>还没有相关中奖纪录</gt-null-data>  
        <el-table :data="tableData.data">
          <el-table-column prop="actName" label="奖项名称">              
          </el-table-column>
          <el-table-column prop="startTime" label="奖品类型">             
          </el-table-column>
          <el-table-column prop="endTime" label="兑奖时间">            
          </el-table-column>
          <el-table-column prop="state" label="状态">             
          </el-table-column>
          <el-table-column prop="state" label="兑奖码">             
          </el-table-column>
          <el-table-column prop="order_option"  label="操作">
            <template slot-scope="scope"> 
              <el-button class="gt-button-normal blue" @click="test(scope.row.id)">详情</el-button>  
              <el-button class="gt-button-normal blue"      @click="test(scope.row.id)">发放奖品</el-button> 
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
        prizeType:"",
        prizeState:"",
        keyWord:"",
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
