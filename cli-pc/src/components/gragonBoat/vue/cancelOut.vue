<style lang="less">

</style>
<template>
    <section>
        <div class="hd-common">
            <el-breadcrumb separator="/" class="gt-crumbs">
                <el-breadcrumb-item @click.native="$util.ClickApply">互动游戏</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path:'/gragonBoat/index' }">端午赛龙舟</el-breadcrumb-item>
                <el-breadcrumb-item>核销授权</el-breadcrumb-item>
            </el-breadcrumb>
            <div class="gt-gray-region mb20">
                <gt-video-btn videoId="22" class="gt-video-btn mr70"></gt-video-btn>
                <el-button type="primary" @click="add()" class="ml30">新增授权</el-button>
            </div>
            <div class="gt-content">
                <el-table ref="multipleTable" :data="tableData.data" v-if="this.tableData.data.length!=0" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column label="核销员" prop="memberName">
                    </el-table-column>
                    <el-table-column prop="createtime" label="创建时间">
                        <template slot-scope="scope">
                            {{scope.row.createtime|parseTime('{y}-{m}-{d} {h}:{i}')}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="address" label="操作" show-overflow-tooltip>
                        <template slot-scope="scope">
                            <el-button class="gt-button-normal" @click="delbtn(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <gt-null-data v-if="this.tableData.data.length==0">还没有相关数据，
                    <span @click="add()">点击这里</span>新增核销员吧
                </gt-null-data>
                <!-- 页码 -->
                <div class="mt20" v-if="this.tableData.data.length!=0">
                    <div class="pull-left">
                        <el-button class="ticket-button-small" @click="toggleSelection()">取消选择</el-button>
                        <el-button class="ticket-button-small" @click="select_delbtn()">批量删除</el-button>
                    </div>
                    <div class="pull-right">
                        <el-pagination @current-change="handleCurrentChange" :current-page.sync="current" :page-size="10" layout="prev, pager, next, jumper" :total="tableData.page.totalNums">
                        </el-pagination>
                    </div>
                </div>
                <!-- 新增授权弹窗 -->
                <el-dialog title="新增授权" :visible.sync="cancelCode" class="detail-dialog">
                    <div class="grid-content" style="width:100%;text-align:center;">
                        <div>
                            <img :src="$baseURL + '/app/link/buildQrcode?url='+ Code" style="width:180px;height:180px;margin:15px 0">
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
import { getDragonboatAuthorityList, getAuthorityUrl, delDragonboatAuthority } from './../api/api'
export default {
    data() {
        return {
            Code: "",
            cancelCode: false,
            tableData: { data: [], page: {} },
            current: 1,
            showSuccee: false,
            multipleSelection: [],
        };
    },
    methods: {
        //获取核销员---------------------------star
        getData() {
            var params = {};
            params.actId = this.$router.history.current.query.id;
            params.size = 10;
            params.current = this.current;
            getDragonboatAuthorityList(params).then(data => {
                if (data.code == 100) {
                    this.tableData = data
                    console.log(data, '获取核销员');
                } else {
                    this.$message.error(data.msg);
                }
            }).catch(() => {
                this.$message({ type: "info", message: "网络问题，请刷新重试~" });
            });
        },
        //新增授权---------------------------star
        add() {
            var mainId = this.$router.history.current.query.id;
            getAuthorityUrl({ mainId }).then(data => {
                if (data.code == 100) {
                    this.Code = data.data.mobileUrl
                    this.cancelCode = true
                    console.log(data.data.mobileUrl, '新增授权');
                } else {
                    this.$message.error(data.msg);
                }
            }).catch(() => {
                this.$message({ type: "info", message: "网络问题，请刷新重试~" });
            });
        },
        //删除---------------------------star
        delbtn(val) {
            var params = {}
            var id = [val];
            params.actId = Number(this.$router.history.current.query.id);
            params.id = id;
            console.log(params, 963);
            this.$confirm("确定要永久删除该核销员吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"

            }).then(() => {
                delDragonboatAuthority(params).then(data => {
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
                this.$confirm('确定删除已选中的核销员？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delDragonboatAuthority(params).then(data => {
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
                    message: '请选择需删除的核销员'
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
        test() {
            console.log(123)
        },
        handleCurrentChange(val) {
            this.getData();
        }
    },
    mounted() {
        this.getData();

    },
    filters: {

    }
}
</script>
