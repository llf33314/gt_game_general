webpackJsonp([17],{Y5jO:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("FdRB"),l={data:function(){return{active:"0",ruleForm1:{actType:1,actName:"",date:"",actDescribe:"",actNotStartedTips:"",bgmSp:"",musicUrl:""},rules1:{actName:[{required:!0,message:"活动名称不能为空",trigger:"blur"}],date:[{required:!0,type:"array",message:"游戏时间不能为空",trigger:"blur"}]},ruleForm2:{gameTime:"",actCountOfDay:"",actTotalOfAct:"",countmoneyProbabilitysetList:[{checked:!1,fenbiType:1,fenbiChance:""},{checked:!1,fenbiType:5,fenbiChance:""},{checked:!1,fenbiType:10,fenbiChance:""},{checked:!1,fenbiType:20,fenbiChance:""},{checked:!1,fenbiType:50,fenbiChance:""},{checked:!1,fenbiType:100,fenbiChance:""}]},rules2:{gameTime:[{required:!0,message:"游戏时间不能为空",trigger:"blur"}],cishu:[{required:!0,message:"抽奖次数不能为空",trigger:"blur"}],zongshu:[{required:!0,message:"抽奖总数不能为空",trigger:"blur"}]},ruleForm3:{actAwardingTime:"",actAwardingAddress:"",aactAwardingTips:""},rules3:{actAwardingTime:[{required:!0,message:"兑奖期限不能为空",trigger:"blur"}],actAwardingAddress:[{required:!0,message:"兑奖地址不能为空",trigger:"blur"}]},actIsShowNums:1,ruleForm4:[{turPrizeType:"",turPrizeUnit:"",turPrizeNums:""}]}},methods:{getMusic:function(e){this.ruleForm1.bgmSp=e.music.name,this.ruleForm1.musicUrl=e.music.url},delForm4:function(e){this.ruleForm4.splice(e,1)},addForm4:function(){this.ruleForm4.push({turPrizeType:"",turPrizeUnit:"",turPrizeNums:""})},Save:function(e){var t=this;this.$refs[e].validate(function(e){if(1==t.active){var a=0,r=!1;t.ruleForm2.countmoneyProbabilitysetList.forEach(function(e,t,l){e.checked&&(r=!0,a+=Number(e.fenbiChance))}),r&&100!==a&&(e=!1,t.$message.error("已勾选面额的概率之和必须等于100%"))}if(!e)return!1;t.submit()})},lastStep:function(){for(var e=0;e<this.ruleForm4.length;e++){var t=/^[1-9]\d*$/;if(!this.ruleForm4[e].turPrizeUnit||!this.ruleForm4[e].turPrizeNums)return this.$message.error("表单不能留空，请填写完整~"),!1;if(!t.test(this.ruleForm4[e].turPrizeUnit))return this.$message.error("奖品数额填写有误，请重新填写~"),!1;if(!t.test(this.ruleForm4[e].turPrizeNums))return this.$message.error("奖项数量填写有误，请重新填写~"),!1;this.submit()}},submit:function(){var e=this,t=[];this.ruleForm2.countmoneyProbabilitysetList.forEach(function(e,a,r){1==e.checked&&t.push({fenbiType:e.fenbiType,fenbiChance:e.fenbiChance})});var a={id:this.$route.query.id,actType:this.ruleForm1.actType,actName:this.ruleForm1.actName,actBeginTime:this.ruleForm1.date[0],actEndTime:this.ruleForm1.date[1],actDescribe:this.ruleForm1.actDescribe,actNotStartedTips:this.ruleForm1.actNotStartedTips,bgmSp:this.ruleForm1.bgmSp,musicUrl:this.ruleForm1.musicUrl,actGameTime:this.ruleForm2.actGameTime,actCountOfDay:this.ruleForm2.actCountOfDay,actTotalOfAct:this.ruleForm2.actTotalOfAct,countmoneyProbabilitysetList:t,actAwardingTime:this.ruleForm3.actAwardingTime,actAwardingAddress:this.ruleForm3.actAwardingAddress,actAwardingTips:this.ruleForm3.actAwardingTips,actIsShowNums:this.actIsShowNums,prizeSetList:this.ruleForm4};r.a.modfiyActivity(a).then(function(t){e.isSubmit=!0,100==t.code?e.$message.success("保存成功"):(e.isSubmit=!1,e.$message.error(t.msg||"保存失败"))})},backUrl:function(){window.history.go(-1)}},mounted:function(){},created:function(){var e=this;r.a.getActivityById({id:this.$route.query.id}).then(function(t){100==t.code?(e.ruleForm1.actType=t.data.actType,e.ruleForm1.actName=t.data.actName,e.ruleForm1.date=[t.data.actBeginTime,t.data.actEndTime],e.ruleForm1.actDescribe=t.data.actDescribe,e.ruleForm1.actNotStartedTips=t.data.actNotStartedTips,e.ruleForm1.bgmSp=t.data.bgmSp,e.ruleForm1.musicUrl=t.data.musicUrl,e.ruleForm2.actGameTime=t.data.actGameTime,e.ruleForm2.actCountOfDay=t.data.actCountOfDay,e.ruleForm2.actTotalOfAct=t.data.actTotalOfAct,t.data.countmoneyProbabilitysetList.forEach(function(t,a,r){for(var l=0;l<e.ruleForm2.countmoneyProbabilitysetList.length;l++)e.ruleForm2.countmoneyProbabilitysetList[l].fenbiType!=t.fenbiType||(e.ruleForm2.countmoneyProbabilitysetList[l].checked=!0,e.ruleForm2.countmoneyProbabilitysetList[l].fenbiChance=t.fenbiChance)}),e.ruleForm3.actAwardingTime=t.data.actAwardingTime,e.ruleForm3.actAwardingAddress=t.data.actAwardingAddress,e.ruleForm3.actAwardingTips=t.data.actAwardingTips,e.actactIsShowNumsNums=t.data.actactIsShowNumsNums,t.data.prizeSetList.forEach(function(e,t,a){e.eggPrizeType=e.eggPrizeType+""}),e.ruleForm4=t.data.prizeSetList):e.$message.error("获取编辑数据失败")}),r.a.getPrizeType().then(function(t){100==t.code?e.options=t.data:e.$message.error("获取奖品类型失败")})},filters:{prizeStatus:function(e){return 0==e?e="一等奖":1==e?e="二等奖":2==e?e="三等奖":3==e?e="四等奖":4==e?e="五等奖":5==e&&(e="六等奖"),e}}},i={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("section",[a("div",{staticClass:"hd-common turnPlate"},[a("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",{nativeOn:{click:function(t){e.$util.ClickApply(t)}}},[e._v("互动游戏")]),e._v(" "),a("el-breadcrumb-item",{attrs:{to:{path:"/crazyMoney/index"}}},[e._v("疯狂数钱")]),e._v(" "),a("el-breadcrumb-item",[e._v("编辑活动")])],1),e._v(" "),a("div",{staticClass:"gt-content"},[a("el-tabs",{attrs:{type:"card"},model:{value:e.active,callback:function(t){e.active=t},expression:"active"}},[a("el-tab-pane",{attrs:{label:"基础设置",name:"0"}}),e._v(" "),a("el-tab-pane",{attrs:{label:"规则设置",name:"1"}}),e._v(" "),a("el-tab-pane",{attrs:{label:"兑奖设置",name:"2"}}),e._v(" "),1==e.ruleForm1.actType?a("el-tab-pane",{attrs:{label:"奖项设置",name:"3"}}):e._e()],1),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:0==this.active,expression:"this.active==0"}],staticClass:"mt40"},[a("el-form",{ref:"ruleForm1",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"145px"}},[a("el-form-item",{attrs:{label:"游戏模式："}},[a("el-radio-group",{model:{value:e.ruleForm1.actType,callback:function(t){e.$set(e.ruleForm1,"actType",t)},expression:"ruleForm1.actType"}},[a("el-radio",{attrs:{label:1}},[e._v("排名中奖模式")]),e._v(" "),a("el-radio",{attrs:{label:2}},[e._v("数钱折算模式")])],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"活动名称：",prop:"actName"}},[a("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入活动名称"},model:{value:e.ruleForm1.actName,callback:function(t){e.$set(e.ruleForm1,"actName",t)},expression:"ruleForm1.actName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"活动时间：",prop:"date"}},[a("el-date-picker",{staticClass:"w_demo",attrs:{type:"datetimerange",placeholder:"选择时间范围"},model:{value:e.ruleForm1.date,callback:function(t){e.$set(e.ruleForm1,"date",t)},expression:"ruleForm1.date"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"活动说明：",prop:"actDescribe"}},[a("el-input",{staticClass:"w_demo",attrs:{type:"textarea",rows:3,placeholder:"请输入活动说明"},model:{value:e.ruleForm1.actDescribe,callback:function(t){e.$set(e.ruleForm1,"actDescribe",t)},expression:"ruleForm1.actDescribe"}}),e._v(" "),a("span",{staticClass:"el-upload__tip grey"},[e._v("\r\n                        描述活动详情，能让粉丝了解此次活动\r\n                    ")])],1),e._v(" "),a("el-form-item",{attrs:{label:"活动未开始提示：",prop:"actNotStartedTips"}},[a("el-input",{staticClass:"w_demo",attrs:{type:"textarea",rows:3,placeholder:"请控制字数在100以内，如：活动尚未开始，敬请期待!"},model:{value:e.ruleForm1.actNotStartedTips,callback:function(t){e.$set(e.ruleForm1,"actNotStartedTips",t)},expression:"ruleForm1.actNotStartedTips"}}),e._v(" "),a("span",{staticClass:"el-upload__tip grey"},[e._v("\r\n                        活动未开始提示限制在100个字数以内\r\n                    ")])],1),e._v(" "),a("el-form-item",{attrs:{label:"背景音乐："}},[a("div",{staticClass:"pd20 bb bw bgMusic"},[a("gt-material",{staticClass:"va-m",attrs:{prop:"",isMusic:!0,btnContent:"点击上传",width:"72",height:"72"},on:{getChangeUrl:e.getMusic}}),e._v(" "),a("span",{staticClass:"el-upload__tip c333 ml20"},[e._v(e._s(e.ruleForm1.bgmSp))]),e._v(" "),a("div",{staticClass:"el-upload__tip grey",staticStyle:{"line-height":"25px"}},[e._v("\r\n                            音频文件的格式为mp3、wma、wav,大小不超过3M\r\n                        ")])],1)])],1),e._v(" "),a("div",{staticClass:"btnRow"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.Save("ruleForm1")}}},[e._v("保存")]),e._v(" "),a("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)],1),e._v(" "),1==this.active?a("div",{staticClass:"mt40"},[a("el-form",{ref:"ruleForm2",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"游戏时间",prop:"actGameTime"}},[a("el-input",{staticClass:"w_demo mr10",attrs:{type:"number",placeholder:"请输入游戏持续时间"},model:{value:e.ruleForm2.actGameTime,callback:function(t){e.$set(e.ruleForm2,"actGameTime",t)},expression:"ruleForm2.actGameTime"}}),e._v(" 秒\r\n                ")],1),e._v(" "),a("el-form-item",{attrs:{label:"抽奖次数：",prop:"actCountOfDay"}},[a("el-input",{staticClass:"w_demo mr10",attrs:{type:"number",placeholder:"请输入每人抽奖总次数"},model:{value:e.ruleForm2.actCountOfDay,callback:function(t){e.$set(e.ruleForm2,"actCountOfDay",t)},expression:"ruleForm2.actCountOfDay"}}),e._v(" 次/人\r\n                ")],1),e._v(" "),a("el-form-item",{attrs:{label:"抽奖总数：",prop:"actTotalOfAct"}},[a("el-input",{staticClass:"w_demo mr10",attrs:{type:"number",placeholder:"请输入每人/每天抽奖总数"},model:{value:e.ruleForm2.actTotalOfAct,callback:function(t){e.$set(e.ruleForm2,"actTotalOfAct",t)},expression:"ruleForm2.actTotalOfAct"}}),e._v("次/人\r\n                ")],1),e._v(" "),a("el-form-item",{staticClass:"mt10",attrs:{label:"概率设置：",prop:""}},[a("div",{staticClass:" grey"},[e._v("请至少设置一种概率 ")]),e._v(" "),a("el-tooltip",{attrs:{placement:"top",effect:"light"}},[a("div",{attrs:{slot:"content"},slot:"content"},[e._v("\r\n                             已勾选面额的概率之和必须等于100%\r\n                        ")]),e._v(" "),a("span",{staticClass:"el-icon-warning  ml10",staticStyle:{position:"absolute","z-index":"1","margin-left":"435px","margin-top":"12px",color:"#ccc","font-size":"17px"}})]),e._v(" "),a("el-tooltip",{attrs:{placement:"top",effect:"light"}},[a("div",{attrs:{slot:"content"},slot:"content"},[e._v("\r\n                            勾选后该面额的钞票会在数钱过程中出现 \r\n                        ")]),e._v(" "),a("span",{staticClass:"el-icon-warning  ml10",staticStyle:{position:"absolute","z-index":"1","margin-left":"145px","margin-top":"12px",color:"#ccc","font-size":"17px"}})]),e._v(" "),a("el-table",{ref:"multipleTable",staticClass:"bw lh1",attrs:{data:e.ruleForm2.countmoneyProbabilitysetList,"tooltip-effect":"dark",width:"560"}},[a("el-table-column",{attrs:{label:"面额",align:"center",width:"260","header-align":"center","label-class-name":"miane"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-checkbox",{model:{value:t.row.checked,callback:function(a){e.$set(t.row,"checked",a)},expression:"scope.row.checked"}}),e._v(" "),a("span",{staticClass:"mg-l8 va-m"},[e._v(e._s(t.row.fenbiType)+"元")])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"出现概率",align:"center","header-align":"center","label-class-name":"chuxiangailv"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-input",{staticClass:"w100_demo mr10",model:{value:t.row.fenbiChance,callback:function(a){e.$set(t.row,"fenbiChance",e._n(a))},expression:"scope.row.fenbiChance"}}),e._v("%\r\n                        ")]}}])})],1)],1)],1),e._v(" "),a("div",{staticClass:"btnRow"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.Save("ruleForm2")}}},[e._v("保存")]),e._v(" "),a("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)],1):e._e(),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:2==this.active,expression:"this.active==2"}],staticClass:"mt40"},[a("el-form",{ref:"ruleForm3",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"兑奖期限：",prop:"actAwardingTime"}},[a("el-input",{staticClass:"w_demo mr10",attrs:{type:"number",placeholder:"请输入兑奖期限"},model:{value:e.ruleForm3.actAwardingTime,callback:function(t){e.$set(e.ruleForm3,"actAwardingTime",t)},expression:"ruleForm3.actAwardingTime"}}),e._v("天\r\n                    "),a("span",{staticClass:"el-upload__tip grey"},[e._v("\r\n                        从活动结束后开始计算\r\n                    ")])],1),e._v(" "),a("el-form-item",{attrs:{label:"兑奖地址：",prop:"actAwardingAddress"}},[a("el-input",{staticClass:"w_demo",attrs:{type:"textarea",rows:5,placeholder:"请输入活动规则"},model:{value:e.ruleForm3.actAwardingAddress,callback:function(t){e.$set(e.ruleForm3,"actAwardingAddress",t)},expression:"ruleForm3.actAwardingAddress"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"兑奖提示："}},[a("el-input",{staticClass:"w_demo",attrs:{maxlength:100,type:"textarea",rows:5,placeholder:"兑奖提示限制在100个字以内"},model:{value:e.ruleForm3.aactAwardingTips,callback:function(t){e.$set(e.ruleForm3,"aactAwardingTips",t)},expression:"ruleForm3.aactAwardingTips"}})],1)],1),e._v(" "),a("div",{staticClass:"btnRow"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.Save("ruleForm3")}}},[e._v("保存")]),e._v(" "),a("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)],1),e._v(" "),3==this.active&&1==e.ruleForm1.actType?a("div",{staticClass:"mt40"},[e._m(0),e._v(" "),a("div",{staticClass:"mt20 mb20"},[a("span",{staticClass:"mr20 "},[e._v("奖品数量：")]),e._v(" "),a("el-radio-group",{model:{value:e.actIsShowNums,callback:function(t){e.actIsShowNums=t},expression:"actIsShowNums"}},[a("el-radio",{attrs:{label:1}},[e._v("显示")]),e._v(" "),a("el-radio",{attrs:{label:2}},[e._v("不显示")])],1)],1),e._v(" "),a("div",{staticClass:"mt20 mb20"},[a("el-button",{attrs:{disabled:e.ruleForm4.length>4,type:"primary"},on:{click:function(t){e.addForm4()}}},[e._v("新增奖品")]),e._v(" "),a("span",{staticClass:"el-upload__tip grey ml10"},[e._v("最多设置五个奖项")])],1),e._v(" "),a("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm4,"tooltip-effect":"dark"}},[a("el-table-column",{attrs:{label:"奖品类型",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("  \r\n                      "+e._s(e._f("prizeStatus")(t.$index,t.$index))+"\r\n                  ")]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"奖品类型",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-select",{staticClass:"w150",attrs:{placeholder:"请选择"},model:{value:t.row.turPrizeType,callback:function(a){e.$set(t.row,"turPrizeType",a)},expression:"scope.row.turPrizeType"}},e._l(e.options,function(t){return 4!=t.value?a("el-option",{key:t.value,attrs:{label:t.name,value:t.value}}):e._e()}))]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"奖品数额",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-input",{staticClass:"w150",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.turPrizeUnit,callback:function(a){e.$set(t.row,"turPrizeUnit",a)},expression:"scope.row.turPrizeUnit"}})]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"奖项数量",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-input",{staticClass:"w150",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.turPrizeNums,callback:function(a){e.$set(t.row,"turPrizeNums",a)},expression:"scope.row.turPrizeNums"}})]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[0!=t.row.id?a("el-button",{staticClass:"gt-button-normal",on:{click:function(a){e.delForm4(t.$index)}}},[e._v("删除")]):e._e()]}}])})],1),e._v(" "),a("div",{staticClass:"btnRow"},[a("el-button",{attrs:{type:"primary"},on:{click:e.lastStep}},[e._v("保存")]),e._v(" "),a("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)],1):e._e(),e._v(" "),5==e.active?a("div",{staticClass:"gt-content complete"},[a("div",{staticClass:"addOk"},[a("div",{staticClass:"el-icon-circle-check green",staticStyle:{"font-size":"40px"}}),e._v(" "),a("div",{staticClass:"complete-info"},[e._v("活动添加成功")]),e._v(" "),a("el-button",{staticClass:"mt80",attrs:{type:"primary"},on:{click:function(t){e.backUrl()}}},[e._v("返回活动列表")])],1)]):e._e(),e._v(" "),a("div",{staticClass:"h80"})],1)],1)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"gt-gray-region",staticStyle:{color:"#666","line-height":"20px"}},[t("p",[this._v("奖品数额：奖品的数量或内容；奖项数量：该奖品的可领取次数；中奖概率：每种奖项在转盘中的中奖概率")]),this._v(" "),t("p",[this._v("如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12")]),this._v(" "),t("p",[this._v("此次游戏活动设置了六个奖项：其中一个奖项为粉币(奖项名称)，该奖项出现3次(中奖数量)，中了该奖项会得到2个(奖品数额)粉币（奖品类型）。")]),this._v(" "),t("p",[this._v("当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px")])])}]};var s=a("VU/8")(l,i,!1,function(e){a("p6zB")},null,null);t.default=s.exports},p6zB:function(e,t){}});