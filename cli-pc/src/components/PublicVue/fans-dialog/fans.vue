<template>
<section>
<!-- 选择粉丝弹窗 -->
<el-dialog title="粉丝列表"  :visible.sync="dialogFans"  >
    <div class="mb10"> 
      <el-input placeholder="请输入昵称" icon="search" v-model="memberName" style="width:250px"  @keyup.native.enter="getMembersData" :on-icon-click="getMembersData" @blur="getMembersData($event)"> 
          </el-input>
    </div>
    <div class="dialogFansAll">
        <div class="pull-left" style="width:75%">
        <el-table ref="multipleTable" :data="fansDetail.data"  tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40">
            </el-table-column>
            <el-table-column label="头像" prop="headimgurl">   
                    <template slot-scope="scope">
                    <img :src="scope.row.headimgurl" style="width:30px;height:30px"/>
                    </template>
            </el-table-column>
            <el-table-column label="昵称" prop="nickname">
            </el-table-column>
            <el-table-column label="性别" prop="sex">
                <template slot-scope="scope">{{ scope.row.sex|sexStatus(scope.row.sex) }}</template>
            </el-table-column>
            <el-table-column label="所在城市" prop="city">
            </el-table-column>
            <el-table-column label="关注时间" prop="jointime">
                <template slot-scope="scope">{{scope.row.jointime|parseTime('{y}-{m}-{d}')}}</template>
            </el-table-column>
            <el-table-column label="组别" prop="name">
            </el-table-column>
        </el-table>
        <div class="public-page-fr">
            <el-pagination @current-change="handleCurrentChange"  :current-page.sync="current" :page-size="8"
                layout="prev, pager, next, jumper" :total="fansDetail.page.totalNums">
                </el-pagination> 
        </div>  
        <div class="h20"></div> 
        </div>

        <div class="pull-right dialogFans_r">
            <div class="dialogFans_r_chose">
            已选择：{{this.multipleTable.length}}
            </div> 
            <div v-for="(item,index) in  multipleTable" class="prizeItem">  
                <div style="width:100%;">
                    <div class="prizeName">
                    {{item.nickname}} 
                    </div>
                    <span @click="delPrize(index)" class="blueee mr10 pull-right mt20" >删除</span>  
                </div>
            </div> 
        </div>
        <div  class="pull-right" > 
            <span slot="footer" class="dialog-footer">
                <div class="h20"></div>
            <el-button @click="dialogFans=false">取 消</el-button>
            <el-button type="primary" @click="prizeSubmit()">确 定</el-button>
            </span>
        </div> 
    </div> 
</el-dialog>
</section>
</template> 
<script>
import { getMembers } from "./api";
export default {
  props: { 
    // fansKey:{
    //   type: String,
    //   default: "" 
    // },
    visible: {
      type: Boolean,
      default: true 
    } 
  },
  data() {
    return {
        fansDetail:{ 
            data: [],
            page: {},
        },
        current: 1,
        memberName: "",
        multipleTable: [],
        dialogFans: this.visible, 
    };
  }, 
    methods: {  
        getMembersData() {
            var params = {};
            params.current = this.current;
            params.size = 8;
            params.memberName = this.memberName;
            getMembers(params).then(data => {
                if (data.code == 100) {
                this.fansDetail = data;
                } else {
                this.$message.error(data.msg);
                }
            })
            .catch(() => {
                this.$message({ type: "info", message: "网络问题，请刷新重试~" });
            });
        },
        //指定中奖人确定按钮
        prizeSubmit() {
            if(this.multipleTable.length==0){
                this.$message.error("指定中奖人个数不能为0，请重新选择");
            }else if(this.multipleTable.length > 4){
                this.$message.error("指定中奖人不能超过5个，请重新选择");
            }else{
                this.$emit("getFansData", this.multipleTable);
                this.dialogFans = false
            } 
        },
        //多选名单
        handleSelectionChange(val) { 
            this.multipleTable = val; 
        },
        //翻页
        handleCurrentChange(val) {
            this.getMembersData();
        }
    }, 
    created(){
        //console.log(this.fansKey,'fansKey')
        this.getMembersData();
        console.log(this.visible,123)        
    },
    filters: {
        sexStatus(val) {
            if (val == 2) {
            val = "女";
            }else if(val == 1){
            val = "男"; 
            }else if(val == 0){
                val = "未知"; 
            }
            return val;
        }, 
    },
    watch: {
        // fansKey: function(val, old) {
        //     console.log(val,11111111111111)
        //     console.log(old,22222222222222)
        //     // this.dialogFans = val
        //     // this.$refs.multipleTable && this.$refs.multipleTable.clearSelection()
        // },
        visible: function(val, old) {            
            this.dialogFans = val
            this.$refs.multipleTable && this.$refs.multipleTable.clearSelection()
        },
        dialogFans: function(val, old) {
            this.$emit('update:visible',val)
        }
    } 
};
</script> 