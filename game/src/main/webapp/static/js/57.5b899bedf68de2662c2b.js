webpackJsonp([57],{"F5/7":function(t,e){},VqJ0:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("NCuU"),o={data:function(){return{keyWord:"",activeName:"-1",dialogTip:!1,countNum:{all:0,nostar:0,started:0,stop:0,over:0},tableData:{data:[],page:{}},current:1,copeData:{url:"",shortUrl:"",copyUrlVisible:!1}}},methods:{getdata:function(){var t=this,e={};e.status=this.activeName,e.name=this.keyWord,e.current=this.current,e.size=10,console.log(e,77),Object(n.e)(e).then(function(e){100==e.code?(t.tableData=e,console.log(e,123)):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})},getCount:function(){var t=this;Object(n.d)().then(function(e){100==e.code?(t.countNum.all=e.data.count1,t.countNum.nostar=e.data.count2,t.countNum.started=e.data.count3,t.countNum.over=e.data.count4):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})},askPreview:function(t){var e=this;Object(n.g)({mainId:t}).then(function(t){100==t.code?(e.copeData.url=t.data.mobileUrl,Object(n.j)(t.data.mobileUrl).then(function(t){e.copeData.shortUrl=t}),e.copeData.copyUrlVisible=!0):e.$message.error(t.msg)})},delBtn:function(t){var e=this;this.$confirm("确定要永久删除此活动吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(n.a)({id:t}).then(function(t){100==t.code?(e.$message({message:"操作成功",type:"success"}),e.getdata()):e.$message.error(t.msg)})}).catch(function(){e.$message({type:"info",message:"已取消删除"})})},handleClick:function(){this.getdata()},handleCurrentChange:function(t){this.getdata()},record:function(t){this.$router.push({path:"/openBox/prizeRecord",query:{id:t}})},impower:function(t){this.$router.push({path:"/openBox/cancelOut",query:{id:t}})},addActive:function(){this.$router.push("/openBox/addAct")},editAct:function(t){this.$router.push({path:"/openBox/editAct",query:{id:t}})},test:function(){console.log(123)}},mounted:function(){this.getdata(),this.getCount()},filters:{actStatus:function(t){return 0==t?t="未开始":1==t?t="进行中":2==t&&(t="已结束"),t}}},s={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("section",[a("div",{staticClass:"hd-common"},[a("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",[t._v("互动游戏")]),t._v(" "),a("el-breadcrumb-item",[t._v("拆礼盒")])],1),t._v(" "),a("div",{staticClass:"gt-gray-region mb20"},[a("span",{staticClass:"padding-left-md ml30 mb10"},[a("el-input",{staticStyle:{width:"250px"},attrs:{placeholder:"输入标题关键字查询",icon:"search"},on:{blur:function(e){t.getdata(e)}},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.getdata(e)}},model:{value:t.keyWord,callback:function(e){t.keyWord=e},expression:"keyWord"}})],1),t._v(" "),a("gt-video-btn",{staticClass:"gt-video-btn mr70",attrs:{videoId:"22"}}),t._v(" "),a("div",{staticClass:"h10"}),t._v(" "),a("el-button",{staticClass:"ml30",attrs:{type:"primary"},on:{click:function(e){t.addActive()}}},[t._v("新建活动")])],1),t._v(" "),a("div",{staticClass:"gt-content"},[a("el-tabs",{attrs:{type:"card"},on:{"tab-click":t.handleClick},model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{attrs:{label:"全部  ("+t.countNum.all+")",name:"-1"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"未开始("+t.countNum.nostar+")",name:"0"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"进行中("+t.countNum.started+")",name:"1"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"已结束("+t.countNum.over+")",name:"2"}})],1),t._v(" "),0==this.tableData.page.totalNums?a("gt-null-data",[t._v("还没有创建相关活动，\r\n          "),a("span",{on:{click:function(e){t.addActive()}}},[t._v("点击这里")]),t._v("创建活动吧\r\n        ")]):t._e(),t._v(" "),0!=this.tableData.page.totalNums?a("el-table",{attrs:{data:t.tableData.data}},[a("el-table-column",{attrs:{prop:"name",label:"活动名称"}}),t._v(" "),a("el-table-column",{attrs:{prop:"activityBeginTime",label:"活动开始时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("parseTime")(e.row.activityBeginTime,"{y}-{m}-{d}"))+"\r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"activityEndTime",label:"活动结束时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("parseTime")(e.row.activityEndTime,"{y}-{m}-{d}"))+"\r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"活动状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("actStatus")(e.row.status,e.row.status))+" \r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{width:"400",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[1==e.row.isEdit?a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.editAct(e.row.id)}}},[t._v("编辑")]):t._e(),t._v(" "),a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.record(e.row.id)}}},[t._v("中奖纪录")]),t._v(" "),a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.askPreview(e.row.id)}}},[t._v("预览链接")]),t._v(" "),a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.impower(e.row.id)}}},[t._v("核销授权")]),t._v(" "),a("el-button",{staticClass:"gt-button-normal",on:{click:function(a){t.delBtn(e.row.id)}}},[t._v("删除")])]}}])})],1):t._e(),t._v(" "),a("div",{staticClass:"public-page-fr"},[0!=this.tableData.page.totalNums?a("el-pagination",{attrs:{"page-size":10,layout:"prev, pager, next, jumper",total:t.tableData.page.totalNums},on:{"current-change":t.handleCurrentChange}}):t._e()],1)],1),t._v(" "),a("gt-copy-url",{attrs:{copeData:t.copeData}})],1)])},staticRenderFns:[]};var c=a("VU/8")(o,s,!1,function(t){a("F5/7")},null,null);e.default=c.exports}});