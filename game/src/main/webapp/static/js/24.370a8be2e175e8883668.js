webpackJsonp([24],{"+4lh":function(t,e){},rdZU:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("//Fk"),i=a.n(n),s=a("ssrk"),c={data:function(){return{initCurrentPage:1,currentPage:1,initPageSize:10,pageSize:10,totalNums:0,keyWord:"",activeName:"-1",countNum:{count1:"0",count2:"0",count3:"0",count4:"0",count5:"0"},tableData:[{name:"活动名称",status:1,activityBeginTime:15130079e5,activityEndTime:151447679e4}],copeData:{url:"",shortUrl:"",copyUrlVisible:!1}}},methods:{searchFuc:function(){this.currentPage=this.initCurrentPage,this.fetchData()},delEmployee:function(t){this.dialogTip=!1},askPreview:function(t){var e=this,a={mainId:t};s.a.getMobileUrl(a).then(function(t){100==t.code?(e.copeData.url=t.data.mobileUrl,e.$util.getShortUrl(t.data.mobileUrl).then(function(t){e.copeData.shortUrl=t}),e.copeData.copyUrlVisible=!0):e.$message.error("获取链接失败")})},delOne:function(t){var e=this;this.$confirm("此操作将永久删除该活动, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){s.a.delActivity({id:idnex}).then(function(t){t.code&&(e.$message.success("删除成功!"),e.currentPage=e.initCurrentPage,e.fetchData())})}).catch(function(){e.$message({type:"info",message:"已取消删除"})})},startBtn:function(t){var e=this;this.$confirm("确定要开启该活动吗？",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){s.a.startOrStopActivity({id:t.id,status:1}).then(function(t){100==t.code?(e.$message({type:"success",message:"开始成功"}),e.fetchData()):e.$message.error(t.msg||"开始失败")})}).catch(function(){e.$message({type:"info",message:"已取消"})})},stopBtn:function(t){var e=this;this.$confirm("确定要暂停该活动吗？",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){s.a.startOrStopActivity({id:t.id,status:2}).then(function(t){100==t.code?(e.$message({type:"success",message:"暂停成功"}),e.fetchData()):e.$message.error(t.msg||"暂停失败")})}).catch(function(){e.$message({type:"info",message:"已取消"})})},handleClick:function(){this.currentPage=this.initCurrentPage,this.fetchData()},addActive:function(){this.$router.push("/eggSmash/addAct")},edit:function(t){this.$router.push({path:"/eggSmash/editAct",query:{id:t}})},record:function(t){this.$router.push({path:"/eggSmash/prizeRecord",query:{id:t}})},impower:function(t){this.$router.push({path:"/eggSmash/cancelOut",query:{id:t}})},handleCurrentChange:function(t){this.currentPage=t,this.fetchData()},fetchData:function(t){var e=this;this.initRequest=!!t;var a={current:this.currentPage,size:this.pageSize,name:this.keyWord,status:parseFloat(this.activeName)};i.a.all([s.a.getActivityList(a),s.a.countActivity({name:this.keyWord})]).then(function(t){100==t[0].code&&(e.tableData=t[0].data,e.totalNums=t[0].page.totalNums),100==t[1].code&&(e.countNum=t[1].data)})}},created:function(){this.fetchData(!0)},filters:{activityStatus:function(t){switch(t){case 0:return"未开始";case 1:return"进行中";case 2:return"已结束";case 3:return"已暂停"}}}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("section",[a("div",{staticClass:"hd-common"},[a("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",[t._v("互动游戏")]),t._v(" "),a("el-breadcrumb-item",[t._v("砸金蛋")])],1),t._v(" "),a("div",{staticClass:"gt-gray-region mb20"},[a("span",{staticClass:"padding-left-md ml30 mb10"},[a("el-input",{staticStyle:{width:"250px"},attrs:{placeholder:"输入标题关键字查询",icon:"search","on-icon-click":t.searchFuc,maxlength:25},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.searchFuc(e)}},model:{value:t.keyWord,callback:function(e){t.keyWord=e},expression:"keyWord"}})],1),t._v(" "),a("gt-video-btn",{staticClass:"gt-video-btn mr70",attrs:{videoId:"22"}}),t._v(" "),a("div",{staticClass:"h10"}),t._v(" "),a("el-button",{staticClass:"ml30",attrs:{type:"primary"},on:{click:t.addActive}},[t._v("新建活动")])],1),t._v(" "),a("div",{staticClass:"gt-content"},[a("el-tabs",{attrs:{type:"card"},on:{"tab-click":t.handleClick},model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{attrs:{label:"全部  ("+t.countNum.count1+")",name:"-1"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"未开始("+t.countNum.count2+")",name:"0"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"进行中("+t.countNum.count3+")",name:"1"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"已暂停("+t.countNum.count5+")",name:"3"}}),t._v(" "),a("el-tab-pane",{attrs:{label:"已结束("+t.countNum.count4+")",name:"2"}})],1),t._v(" "),t.initRequest&&t.tableData.length<1?a("gt-null-data",[t._v("还没有创建相关活动，\r\n          "),a("span",{on:{click:t.addActive}},[t._v("点击这里")]),t._v("创建活动吧\r\n        ")]):a("el-table",{attrs:{data:t.tableData}},[a("el-table-column",{attrs:{prop:"name",label:"活动名称","min-width":"160","show-overflow-tooltip":""}}),t._v(" "),a("el-table-column",{attrs:{prop:"activityBeginTime",label:"活动开始时间","min-width":"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("DateFormat")(e.row.activityBeginTime,"yyyy-MM-dd hh:mm"))+"\r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"activityEndTime",label:"活动结束时间","min-width":"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("DateFormat")(e.row.activityEndTime,"yyyy-MM-dd hh:mm:ss"))+"\r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"活动状态","min-width":"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("activityStatus")(e.row.status))+"\r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"order_option",width:"450",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.askPreview(e.row.id)}}},[t._v("预览链接")]),t._v(" "),a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.record(e.row.id)}}},[t._v("中奖纪录")]),t._v(" "),1==e.row.status?a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.stopBtn(e.row)}}},[t._v("暂停活动")]):t._e(),t._v(" "),3==e.row.status?a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.startBtn(e.row)}}},[t._v("开始活动")]):t._e(),t._v(" "),0==e.row.status?a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.edit(e.row.id)}}},[t._v("编辑")]):t._e(),t._v(" "),0==e.row.status||2==e.row.status?a("el-button",{staticClass:"gt-button-normal",on:{click:function(a){t.delOne(e.row.id)}}},[t._v("删除")]):t._e()]}}])})],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:t.tableData.length,expression:"tableData.length"}],staticClass:"public-page-fr"},[a("el-pagination",{attrs:{"current-page":t.currentPage,"page-size":t.pageSize,layout:"prev, pager, next, jumper",total:t.totalNums},on:{"current-change":t.handleCurrentChange,"update:currentPage":function(e){t.currentPage=e}}})],1)],1),t._v(" "),a("gt-copy-url",{attrs:{copeData:t.copeData}})],1)])},staticRenderFns:[]};var o=a("VU/8")(c,r,!1,function(t){a("+4lh")},null,null);e.default=o.exports}});