webpackJsonp([45],{"7iWF":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("PTM0"),n={data:function(){return{prizeType:"-1",prizeState:"-1",codeWord:"",current:1,tableData:{data:[],page:{}},options:[],showDetail:!1,showDetailData:[]}},methods:{showDetailBtn:function(t){this.showDetail=!0,this.showDetailData=t,console.log(this.showDetailData,852222)},getData:function(){var t=this,e={};e.actId=this.$router.history.current.query.id,e.current=this.current,e.size=10,e.snCode=this.codeWord,e.status=this.prizeState,e.type=this.prizeType,console.log(e,77),Object(s.g)(e).then(function(e){100==e.code?(t.tableData=e,console.log(e,"中奖列表")):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试网络问题，请刷新重试~"})})},getPrizeTypeData:function(){var t=this;Object(s.h)().then(function(e){100==e.code?(console.log(e,1233),t.options=e.data,console.log(t.options,444)):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})},toExport:function(){var t={};t.actId=this.$router.history.current.query.id,t.status=this.prizeState,t.type=this.prizeType,t.snCode=this.codeWord,window.open(window.BASEDOMAIN+"/app/goldtree/exports?actId="+t.actId+"&status="+t.status+"&type="+t.type+"&snCode="+t.snCode)},handOut:function(t){var e=this;Object(s.m)({id:t}).then(function(t){100==t.code?(e.$message({message:"发放成功",type:"success"}),e.getData()):e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},test:function(){console.log(123)},handleCurrentChange:function(t){console.log(t)}},mounted:function(){this.getData(),this.getPrizeTypeData()},filters:{prizeStatus:function(t){return 1==t?t="未兑奖":2==t?t="已兑奖":3==t&&(t="已提交"),t},receiveTypeStatus:function(t){return 1==t?t="到店领取":2==t?t="邮寄":3==t&&(t="直接兑奖"),t},prizeType:function(t){return 1==t?t="粉币":2==t?t="手机流量":3==t?t="手机话费":4==t?t="实体物品":6==t?t="积分":7==t&&(t="优惠券"),t}}},o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("section",[a("div",{staticClass:"hd-common turnPlate"},[a("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",{nativeOn:{click:function(e){t.$util.ClickApply(e)}}},[t._v("互动游戏")]),t._v(" "),a("el-breadcrumb-item",{attrs:{to:{path:"/cashTree/index"}}},[t._v("摇钱树")]),t._v(" "),a("el-breadcrumb-item",[t._v("中奖纪录")])],1),t._v(" "),a("div",{staticClass:"gt-gray-region mb20"},[a("span",{staticClass:"padding-left-md ml30 mb10"},[a("el-select",{on:{change:function(e){t.getData()}},model:{value:t.prizeType,callback:function(e){t.prizeType=e},expression:"prizeType"}},[a("el-option",{attrs:{label:"全部",value:"-1"}}),t._v(" "),t._l(t.options,function(t){return a("el-option",{key:t.value,attrs:{label:t.name,value:t.value}})})],2)],1),t._v(" "),a("span",{staticClass:"padding-left-md ml10 mb10"},[a("el-select",{on:{change:function(e){t.getData()}},model:{value:t.prizeState,callback:function(e){t.prizeState=e},expression:"prizeState"}},[a("el-option",{attrs:{label:"全部",value:"-1"}}),t._v(" "),a("el-option",{attrs:{label:"已提交",value:"3"}}),t._v(" "),a("el-option",{attrs:{label:"未兑奖",value:"1"}}),t._v(" "),a("el-option",{attrs:{label:"已兑奖",value:"2"}})],1)],1),t._v(" "),a("span",{staticClass:"padding-left-md ml10 mb10"},[a("el-input",{staticStyle:{width:"250px"},attrs:{placeholder:"请输入兑奖码",icon:"search","on-icon-click":t.getData},on:{blur:function(e){t.getData(e)}},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.getData()}},model:{value:t.codeWord,callback:function(e){t.codeWord=e},expression:"codeWord"}})],1),t._v(" "),a("div",{staticClass:"h10"}),t._v(" "),a("el-button",{staticClass:"ml30",attrs:{type:"primary"},on:{click:function(e){t.toExport()}}},[t._v("导出")])],1),t._v(" "),a("div",{staticClass:"gt-content"},[0==this.tableData.data.length?a("gt-null-data",[t._v("还没有相关中奖纪录")]):t._e(),t._v(" "),0!=this.tableData.data.length?a("el-table",{attrs:{data:t.tableData.data}},[a("el-table-column",{attrs:{prop:"prizeName",label:"奖项名称"}}),t._v(" "),a("el-table-column",{attrs:{prop:"type",label:"奖品类型"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("prizeType")(e.row.type,e.row.type))+" \r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"cashTime",label:"兑奖时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("parseTime")(e.row.cashTime,"{y}-{m}-{d} {h}:{i}"))+"\r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\r\n              "+t._s(t._f("prizeStatus")(e.row.status,e.row.status))+" \r\n            ")]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"snCode",label:"兑奖码"}}),t._v(" "),a("el-table-column",{attrs:{prop:"order_option",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.showDetailBtn(e.row)}}},[t._v("详情")]),t._v(" "),3==e.row.status?a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){t.handOut(e.row.id)}}},[t._v("发放奖品")]):t._e(),t._v(" "),1==e.row.status?a("el-button",{staticClass:"gt-button-normal blue",attrs:{disabled:!0},on:{click:function(a){t.handOut(e.row.id)}}},[t._v("发放奖品")]):t._e()]}}])})],1):t._e(),t._v(" "),a("el-dialog",{staticClass:"detail-dialog",attrs:{title:"详情",visible:t.showDetail},on:{"update:visible":function(e){t.showDetail=e}}},[a("div",[a("p",[a("span",{staticClass:"w20_demo"},[t._v("中奖人")]),a("b",[t._v(" : ")]),t._v(" "+t._s(t.showDetailData.nickname))]),t._v(" "),a("p",[a("span",{staticClass:"w20_demo"},[t._v("兑奖人")]),a("b",[t._v(" : ")]),t._v(" -")]),t._v(" "),a("p",[a("span",{staticClass:"w20_demo"},[t._v("兑奖人联系方式")]),a("b",[t._v(" : ")]),t._v(" "+t._s(t.showDetailData.memberPhone))]),t._v(" "),1==t.showDetailData.status?a("p",[a("span",{staticClass:"w20_demo"},[t._v("兑奖时间")]),a("b",[t._v(" : ")]),t._v(" -")]):t._e(),t._v(" "),1!=t.showDetailData.status?a("p",[a("span",{staticClass:"w20_demo"},[t._v("领取方式")]),a("b",[t._v(" : ")]),t._v(" "+t._s(t._f("receiveTypeStatus")(t.showDetailData.receiveType,t.showDetailData.receiveType)))]):t._e(),t._v(" "),1==t.showDetailData.receiveType?a("p",[a("span",{staticClass:"w20_demo"},[t._v("到店领取地址")]),a("b",[t._v(" : ")]),t._v(" "+t._s(t.showDetailData.addressName))]):t._e(),t._v(" "),2==t.showDetailData.receiveType?a("p",[a("span",{staticClass:"w20_demo"},[t._v("收货人姓名")]),a("b",[t._v(" : ")]),t._v(" "+t._s(t.showDetailData.addressName))]):t._e(),t._v(" "),2==t.showDetailData.receiveType?a("p",[a("span",{staticClass:"w20_demo"},[t._v("收货地址")]),a("b",[t._v(" : ")]),t._v(" "+t._s(t.showDetailData.address))]):t._e()])]),t._v(" "),0!=this.tableData.data.length?a("div",{staticClass:"public-page-fr"},[a("el-pagination",{attrs:{"page-size":10,layout:"prev, pager, next, jumper",total:t.tableData.page.totalNums},on:{"current-change":t.handleCurrentChange}})],1):t._e()],1)],1)])},staticRenderFns:[]};var l=a("VU/8")(n,o,!1,function(t){a("LkNH")},null,null);e.default=l.exports},LkNH:function(t,e){}});