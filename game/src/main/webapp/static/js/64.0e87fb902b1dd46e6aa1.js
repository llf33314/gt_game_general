webpackJsonp([64],{Tvot:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("y7Z1"),o={data:function(){var e=function(e,t,a){""===t?a(new Error("不能为空")):a()};return{ruleForm:{name:"",id:0},tableData:{},rules:{name:[{required:!0,message:"题库名称不能为空",trigger:"blur"}]},showAdd:!1,labelPosition:"right",formLabelAlign:{id:0,name:"",select:"",name1:"",name2:"",name3:"",name4:""},rulesAlign:{name:[{required:!0,validator:e,trigger:"blur"}],name1:[{validator:e,trigger:"blur"}],name2:[{validator:e,trigger:"blur"}],name3:[{validator:e,trigger:"blur"}],name4:[{validator:e,trigger:"blur"}]}}},methods:{addTest:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return console.log("error submit!!"),!1;if(0!=t.ruleForm.id)t.showAdd=!0,t.formLabelAlign.name="",t.formLabelAlign.select="",t.formLabelAlign.name1="",t.formLabelAlign.name2="",t.formLabelAlign.name3="",t.formLabelAlign.name4="";else{var a={};a.bankName=t.ruleForm.name,a.id=t.ruleForm.id,Object(l.v)(a).then(function(e){100==e.code?(console.log(e,1234),t.showAdd=!0,t.ruleForm.id=e.data.id):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})}})},saveBtn:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return console.log("error submit!!"),!1;if(""==t.formLabelAlign.select)t.$message.error("请设定正确答案");else{var a={};a.bankId=t.ruleForm.id,a.id=t.formLabelAlign.id,a.optionA=t.formLabelAlign.name1,a.optionB=t.formLabelAlign.name2,a.optionC=t.formLabelAlign.name3,a.optionD=t.formLabelAlign.name4,a.quesTitle=t.formLabelAlign.name,a.rightAnswer=t.formLabelAlign.select,console.log(a,7788),Object(l.w)(a).then(function(e){100==e.code?(t.showAdd=!1,t.$message({message:"操作成功",type:"success"}),t.getData()):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})}})},getData:function(){var e=this,t=this.ruleForm.id;console.log(t,"id"),Object(l.m)({id:t}).then(function(t){100==t.code?(console.log(t,55),e.tableData=t.data.standQuestionResList,e.ruleForm.name=t.data.bankName,console.log(t,"题库列表")):e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},submit:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return console.log("error submit!!"),!1;var a={};a.bankName=t.ruleForm.name,a.id=t.ruleForm.id,Object(l.v)(a).then(function(e){100==e.code?(console.log(e,123),t.ruleForm.id=e.data.id,t.$message({message:"操作成功",type:"success"})):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})})},handOut:function(e){var t=this;this.$confirm("此操作将永久删除该题目, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(l.t)({id:e}).then(function(e){100==e.code?(t.$message({message:"操作成功",type:"success"}),t.getData()):t.$message.error(e.msg)}).catch(function(){t.$message({type:"info",message:"网络问题，请刷新重试~"})})}).catch(function(){t.$message({type:"info",message:"已取消删除"})})},showDetailBtn:function(e){this.showAdd=!0,this.formLabelAlign.name=e.quesTitle,this.formLabelAlign.name1=e.optionA,this.formLabelAlign.name2=e.optionB,this.formLabelAlign.name3=e.optionC,this.formLabelAlign.name4=e.optionD,this.formLabelAlign.select=Number(e.rightAnswer),this.formLabelAlign.id=e.id},test:function(){console.log(123)},backUrl:function(){window.history.go(-1)},handleCurrentChange:function(e){console.log(e)}},mounted:function(){this.ruleForm.id=this.$router.history.current.query.id,this.getData()}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("section",[a("div",{staticClass:"hd-common turnPlate"},[a("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",{nativeOn:{click:function(t){e.$util.ClickApply(t)}}},[e._v("互动游戏")]),e._v(" "),a("el-breadcrumb-item",{attrs:{to:{path:"/standStill/index"}}},[e._v("一站到底")]),e._v(" "),a("el-breadcrumb-item",{attrs:{to:{path:"/standStill/question"}}},[e._v("题库管理")]),e._v(" "),a("el-breadcrumb-item",[e._v("题库管理")])],1),e._v(" "),a("div",{staticClass:"gt-content"},[a("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-width":"130px"}},[a("el-form-item",{attrs:{label:"题库名称：",prop:"name"}},[a("el-input",{staticClass:"w20_demo",model:{value:e.ruleForm.name,callback:function(t){e.$set(e.ruleForm,"name",t)},expression:"ruleForm.name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"题库管理："}},[a("el-button",{staticClass:"mr10",attrs:{type:"primary"},on:{click:function(t){e.addTest("ruleForm")}}},[e._v("添加")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.test()}}},[e._v("导入题库")]),e._v(" "),a("a",{staticClass:"el-upload__tip grey ml10 blueee",staticStyle:{cursor:"pointer"},attrs:{target:"_blank",href:"https://deeptel.com.cn//stand/downMould.do"}},[e._v("下载导入示例")])],1),e._v(" "),a("el-form-item",[this.tableData.length?a("el-table",{attrs:{data:e.tableData}},[a("el-table-column",{attrs:{label:"题库序号"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" \r\n                      "+e._s(t.$index+1)+"\r\n                    ")]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"quesTitle",label:"题目名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime",label:"选项"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("p",[e._v("A："+e._s(t.row.optionA)),a("span",{directives:[{name:"show",rawName:"v-show",value:1==t.row.rightAnswer,expression:"scope.row.rightAnswer==1"}]},[e._v("(正确)")])]),e._v(" "),a("p",[e._v("B："+e._s(t.row.optionB)),a("span",{directives:[{name:"show",rawName:"v-show",value:2==t.row.rightAnswer,expression:"scope.row.rightAnswer==2"}]},[e._v("(正确)")])]),e._v(" "),a("p",[e._v("C："+e._s(t.row.optionC)),a("span",{directives:[{name:"show",rawName:"v-show",value:3==t.row.rightAnswer,expression:"scope.row.rightAnswer==3"}]},[e._v("(正确)")])]),e._v(" "),a("p",[e._v("D："+e._s(t.row.optionD)),a("span",{directives:[{name:"show",rawName:"v-show",value:4==t.row.rightAnswer,expression:"scope.row.rightAnswer==4"}]},[e._v("(正确)")])])]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"order_option",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{staticClass:"gt-button-normal blue",on:{click:function(a){e.showDetailBtn(t.row)}}},[e._v("编辑")]),e._v(" "),a("el-button",{staticClass:"gt-button-normal ",on:{click:function(a){e.handOut(t.row.id)}}},[e._v("删除")])]}}])})],1):e._e()],1),e._v(" "),a("el-dialog",{staticClass:"detail-dialog",attrs:{title:"添加",visible:e.showAdd},on:{"update:visible":function(t){e.showAdd=t}}},[a("el-form",{ref:"formLabelAlign",attrs:{"label-position":e.labelPosition,"label-width":"160px",model:e.formLabelAlign,rules:e.rulesAlign}},[a("el-form-item",{attrs:{label:"问题：",prop:"name"}},[a("el-input",{staticClass:"w_demo",model:{value:e.formLabelAlign.name,callback:function(t){e.$set(e.formLabelAlign,"name",t)},expression:"formLabelAlign.name"}})],1),e._v(" "),a("el-radio-group",{attrs:{prop:"select"},model:{value:e.formLabelAlign.select,callback:function(t){e.$set(e.formLabelAlign,"select",t)},expression:"formLabelAlign.select"}},[a("el-form-item",{attrs:{label:"选项A：",prop:"name1"}},[a("el-input",{staticClass:"w_demo mr10",model:{value:e.formLabelAlign.name1,callback:function(t){e.$set(e.formLabelAlign,"name1",t)},expression:"formLabelAlign.name1"}}),e._v(" "),a("el-radio",{attrs:{label:1}},[e._v("设为答案")])],1),e._v(" "),a("el-form-item",{attrs:{label:"选项B：",prop:"name2"}},[a("el-input",{staticClass:"w_demo mr10",model:{value:e.formLabelAlign.name2,callback:function(t){e.$set(e.formLabelAlign,"name2",t)},expression:"formLabelAlign.name2"}}),e._v(" "),a("el-radio",{attrs:{label:2}},[e._v("设为答案")])],1),e._v(" "),a("el-form-item",{attrs:{label:"选项C：",prop:"name3"}},[a("el-input",{staticClass:"w_demo mr10",model:{value:e.formLabelAlign.name3,callback:function(t){e.$set(e.formLabelAlign,"name3",t)},expression:"formLabelAlign.name3"}}),e._v(" "),a("el-radio",{attrs:{label:3}},[e._v("设为答案")])],1),e._v(" "),a("el-form-item",{attrs:{label:"选项D：",prop:"name4"}},[a("el-input",{staticClass:"w_demo mr10",model:{value:e.formLabelAlign.name4,callback:function(t){e.$set(e.formLabelAlign,"name4",t)},expression:"formLabelAlign.name4"}}),e._v(" "),a("el-radio",{attrs:{label:4}},[e._v("设为答案")])],1)],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.saveBtn("formLabelAlign")}}},[e._v("确 定")]),e._v(" "),a("el-button",{on:{click:function(t){e.showAdd=!1}}},[e._v("取 消")])],1)],1)],1),e._v(" "),this.tableData.length?e._e():a("gt-null-data",[e._v("没有相关数据")]),e._v(" "),a("div",{staticClass:"h80"}),e._v(" "),a("div",{staticClass:"btnRow"},[a("el-button",{on:{click:function(t){e.backUrl()}}},[e._v("返回")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.submit("ruleForm")}}},[e._v("保存")])],1)],1)],1)])},staticRenderFns:[]};var r=a("VU/8")(o,n,!1,function(e){a("t2Ml")},null,null);t.default=r.exports},t2Ml:function(e,t){}});