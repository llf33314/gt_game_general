<style lang="less">

</style>
<template>
<section>
<div class="hd-common">
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item>互动游戏</el-breadcrumb-item> 
      <el-breadcrumb-item :to="{ path:'/romanceValentine/index' }">浪漫七夕</el-breadcrumb-item>  
      <el-breadcrumb-item>核销授权</el-breadcrumb-item>   
    </el-breadcrumb> 
    <div class="gt-gray-region mb20">  
       <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn>  
      <el-button type="primary" @click="add()" class="ml30">新增授权</el-button> 
    </div> 
    <div class="gt-content"> 
        <gt-null-data>还没有相关数据，
          <span  @click="add()">点击这里</span>新增核销员吧
        </gt-null-data>  

        <el-table ref="multipleTable" :data="tableData.data" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="核销员">
                <template slot-scope="scope">{{scope.row.name0 }}</template>
            </el-table-column>
            <el-table-column prop="name" label="创建时间">
                <template slot-scope="scope">
                    {{scope.row.name1|parseTime('{y}-{m}-{d} {h}:{i}')}}
                </template>
            </el-table-column>
            <el-table-column prop="address" label="操作" show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-button class="gt-button-normal" @click="test(scope.row.id)">删除</el-button>    
                </template>
            </el-table-column>
        </el-table> 

        <!-- 页码 -->
        <div class="mt20">
            <div class="pull-left">
                <!-- <el-button class="ticket-button-small" @click="toggleSelection()">取消选择</el-button> -->
                <el-button class="ticket-button-small" @click="test()">批量删除</el-button>
            </div> 
            <div class="pull-right">
                <el-pagination @current-change="handleCurrentChange"  :page-size="10" 
                layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
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
  export default{
    data() {
      return { 
        Code:"",
        cancelCode:false,
        tableData:{
          multipleSelection: [], 
          data:[
            {
                id:0,
              name0:"熊小猫",
              name1:"1514252903" 
            },
            {
                id:12, 
              name0:"王小虎",
              name1:"1514252903" 
            },
            {
                id:0,
              name0:"熊小猫",
              name1:"1514252903" 
            },
            {
                id:12, 
              name0:"王小虎",
              name1:"1514252903" 
            }
          ], 
          page:{
            totalNums:31,
            totalPages:4
          }
        },  
        showSuccee:false,
      };
    },
    methods: {
        //新增授权
        add(){
            this.cancelCode=true
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
