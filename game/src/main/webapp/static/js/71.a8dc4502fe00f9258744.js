webpackJsonp([71],{YUj0:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("y7Z1"),s={data:function(){return{Code:"",cancelCode:!1,tableData:{data:[],page:{}},current:1,showSuccee:!1,multipleSelection:[]}},methods:{getData:function(){var t=this,e={};e.actId=this.$router.history.current.query.id,e.size=10,e.current=this.current,Object(n.p)(e).then(function(e){100==e.code?(t.tableData=e,console.log(e,"获取核销员")):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})},add:function(){var t=this,e=this.$router.history.current.query.id;Object(n.q)({mainId:e}).then(function(e){100==e.code?(t.Code=e.data.mobileUrl,t.cancelCode=!0,console.log(e.data.mobileUrl,"新增授权")):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})},delbtn:function(t){var e=this,a={},s=[t];a.actId=Number(this.$router.history.current.query.id),a.ids=s,console.log(a,963),this.$confirm("确定要永久删除该核销员吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(n.c)(a).then(function(t){100==t.code?(e.$message({message:"操作成功",type:"success"}),e.getData()):e.$message.error(t.msg)})}).catch(function(){e.$message({type:"info",message:"已取消删除"})})},select_delbtn:function(){var t=this;if(this.multipleSelection.length>0){for(var e={},a=[],s=0;s<this.multipleSelection.length;s++){var i={id:this.multipleSelection[s].id};a.push(i.id)}e.actId=Number(this.$router.history.current.query.id),e.ids=a,this.$confirm("确定删除已选中的核销员？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(n.c)(e).then(function(e){100==e.code?(t.$message({message:"操作成功",type:"success"}),t.getData()):t.$message.error(e.msg)})}).catch(function(){t.$message({type:"info",message:"已取消删除"})})}else this.$message({type:"info",message:"请选择需删除的核销员"})},toggleSelection:function(t){var e=this;t?t.forEach(function(t){e.$refs.multipleTable.toggleRowSelection(t)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(t){this.multipleSelection=t},test:function(){console.log(123)},handleCurrentChange:function(t){this.getData()}},mounted:function(){this.getData()},filters:{}},i={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("section",[a("div",{staticClass:"hd-common"},[a("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",[t._v("互动游戏")]),t._v(" "),a("el-breadcrumb-item",{attrs:{to:{path:"/standStill/index"}}},[t._v("一站到底")]),t._v(" "),a("el-breadcrumb-item",[t._v("核销授权")])],1),t._v(" "),a("div",{staticClass:"gt-gray-region mb20"},[a("gt-video-btn",{staticClass:"gt-video-btn mr70",attrs:{videoId:"22"}}),t._v(" "),a("el-button",{staticClass:"ml30",attrs:{type:"primary"},on:{click:function(e){t.add()}}},[t._v("新增授权")])],1),t._v(" "),a("div",{staticClass:"gt-content"},[0==this.tableData.data.length?a("gt-null-data",[t._v("还没有相关数据，\r\n          "),a("span",{on:{click:function(e){t.add()}}},[t._v("点击这里")]),t._v("新增核销员吧\r\n        ")]):t._e(),t._v(" "),0!=this.tableData.data.length?a("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:t.tableData.data,"tooltip-effect":"dark"},on:{"selection-change":t.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),t._v(" "),a("el-table-column",{attrs:{label:"核销员",prop:"name"}}),t._v(" "),a("el-table-column",{attrs:{prop:"createTime",label:"创建时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n                    "+t._s(t._f("parseTime")(e.row.createTime,"{y}-{m}-{d} {h}:{i}"))+"\r\n                ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"address",label:"操作","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{staticClass:"gt-button-normal",on:{click:function(a){t.delbtn(e.row.id)}}},[t._v("删除")])]}}])})],1):t._e(),t._v(" "),0!=this.tableData.data.length?a("div",{staticClass:"mt20"},[a("div",{staticClass:"pull-left"},[a("el-button",{staticClass:"ticket-button-small",on:{click:function(e){t.select_delbtn()}}},[t._v("批量删除")])],1),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-pagination",{attrs:{"current-page":t.current,"page-size":10,layout:"prev, pager, next, jumper",total:t.tableData.page.totalNums},on:{"current-change":t.handleCurrentChange,"update:currentPage":function(e){t.current=e}}})],1)]):t._e(),t._v(" "),a("el-dialog",{staticClass:"detail-dialog",attrs:{title:"新增授权",visible:t.cancelCode},on:{"update:visible":function(e){t.cancelCode=e}}},[a("div",{staticClass:"grid-content",staticStyle:{width:"100%","text-align":"center"}},[a("div",[a("img",{staticStyle:{width:"180px",height:"180px",margin:"15px 0"},attrs:{src:t.$baseURL+"/app/link/buildQrcode?url="+t.Code}})]),t._v(" "),a("div",[t._v("扫描二维码成为核销员")])])]),t._v(" "),a("el-dialog",{staticClass:"gt-del-tip",attrs:{title:"提示",visible:t.showSuccee},on:{"update:visible":function(e){t.showSuccee=e}}},[a("div",{staticClass:"el-message-box__status el-icon-circle-check"}),t._v(" "),a("div",{staticClass:"mg-l50"},[t._t("default",[a("p",{staticClass:"c333"},[t._v("授权成功")]),t._v(" "),a("p",{staticClass:"c666 mt10"},[t._v('3秒后返回"授权管理列表"...')])])],2),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.test()}}},[t._v("继续新增")]),t._v(" "),a("el-button",{on:{click:function(e){t.showSuccee=!1}}},[t._v("取 消")])],1)])],1)],1)])},staticRenderFns:[]};var l=a("VU/8")(s,i,!1,function(t){a("pzXY")},null,null);e.default=l.exports},pzXY:function(t,e){}});