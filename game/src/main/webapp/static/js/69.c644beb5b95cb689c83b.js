webpackJsonp([69],{RAIP:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=r("//Fk"),l=r.n(a),s=r("ssrk"),i={data:function(){return{loading:!1,active:0,peopleNums:1,ruleForm1:{eggName:"",date:[],eggBeginTime:"",eggEndTime:"",eggEggPartaker:1,eggPway:0,eggMan:"",eggKou:"",eggDescribe:"",eggBeforeTxt:"",eggBgmName:"",eggBgm:""},rules1:{eggName:[{required:!0,message:"活动名称不能为空",trigger:"blur"}],date:[{required:!0,type:"array",message:"开始时间不能为空",trigger:"blur"}]},ruleForm2:{eggCountOfDay:"",eggCountOfAll:""},rules2:{eggCountOfDay:[{required:!0,type:"number",validator:this.$valid.elemIsInteger,trigger:"blur"}],eggCountOfAll:[{required:!0,type:"number",validator:this.$valid.elemIsInteger,trigger:"blur"}]},ruleForm3:{eggCashDay:"",eggAddress:"",eggWinningTxt:"",eggCashWay:2},rules3:{eggCashDay:[{type:"number",required:!0,message:"兑奖期限不能为空",trigger:"blur"}],eggAddress:[{required:!0,message:"兑奖地址不能为空",trigger:"blur"}]},ruleForm4:[{eggPrizeType:5,eggPrizeLimit:"",eggPrizeName:"",eggPrizeNums:"",eggPrizeChance:"",nickname:"",openid:""},{eggPrizeType:"",eggPrizeLimit:"",eggPrizeName:"",eggPrizeNums:"",eggPrizeChance:"",nickname:"",openid:""}],options:[],dialogFans:!1,assignObj:{}}},methods:{assign:function(e){e.row.eggPrizeNums?(this.dialogFans=!0,this.assignObj=e.row,this.peopleNums=e.row.eggPrizeNums):this.$message.info("请先输入奖项数量")},getFansData:function(e){if(e.length){var t=[],r=[];e.forEach(function(e,a,l){t.push(e.nickname),r.push(e.openid)}),this.assignObj.nickname=t.join(","),this.assignObj.openid=r.join(","),this.$set(this.ruleForm4,this.assignObj.$index,this.assignObj)}},delForm4:function(e){this.ruleForm4.splice(e,1)},addForm4:function(){this.ruleForm4.push({eggPrizeType:"",eggPrizeLimit:"",eggPrizeName:"",eggPrizeNums:"",eggPrizeChance:"",nickname:"",openid:""})},getMusic:function(e){this.ruleForm1.eggBgmName=e.music.name,this.ruleForm1.eggBgm=e.music.url},Save:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.submit()})},lastStep:function(){for(var e=0,t=0;t<this.ruleForm4.length;t++){var r=/^[1-9]\d*$/;if(!(this.ruleForm4[t].eggPrizeType&&this.ruleForm4[t].eggPrizeLimit&&this.ruleForm4[t].eggPrizeName&&this.ruleForm4[t].eggPrizeNums&&this.ruleForm4[t].eggPrizeChance))return this.$message.error("表单不能留空，请填写完整~"),!1;if(!r.test(this.ruleForm4[t].eggPrizeLimit))return this.$message.error("奖品单位填写有误，请重新填写~"),!1;if(!r.test(this.ruleForm4[t].eggPrizeNums))return this.$message.error("奖项数量填写有误，请重新填写~"),!1;if(4==this.ruleForm4[t].eggPrizeType&&0==this.ruleForm4[t].name5.length)return this.$message.error("当奖品为实物时，请上传实物图片~"),!1;e+=Number(this.ruleForm4[t].eggPrizeChance)}if(100!=e)return this.$message.error("中奖概率之和加起来应为100%"),!1;this.submit()},submit:function(){var e=this,t={id:this.$route.query.id,eggName:this.ruleForm1.eggName,eggBeginTime:Number(this.ruleForm1.date[0])?this.ruleForm1.date[0]:new Date(this.ruleForm1.date[0]),eggEndTime:Number(this.ruleForm1.date[1])?this.ruleForm1.date[1]:new Date(this.ruleForm1.date[1]),eggEggPartaker:this.ruleForm1.eggEggPartaker,eggPway:this.ruleForm1.eggPway,eggMan:this.ruleForm1.eggMan,eggKou:this.ruleForm1.eggKou,eggDescribe:this.ruleForm1.eggDescribe,eggBeforeTxt:this.ruleForm1.eggBeforeTxt,eggBgmName:this.ruleForm1.eggBgmName,eggBgm:this.ruleForm1.eggBgm,eggCountOfDay:this.ruleForm2.eggCountOfDay,eggCountOfAll:this.ruleForm2.eggCountOfAll,eggCashDay:this.ruleForm3.eggCashDay,eggAddress:this.ruleForm3.eggAddress,eggCashWay:this.ruleForm3.eggCashWay,eggWinningTxt:this.ruleForm3.eggWinningTxt,eggWinningNotice:this.ruleForm3.eggWinningNotice,prizeSetList:this.ruleForm4};this.loading=!0,s.a.modfiyActivity(t).then(function(t){100==t.code?e.$message.success(t.msg||"保存成功"):e.$message.error(t.msg||"保存失败"),e.loading=!1})},backUrl:function(){window.history.go(-1)}},created:function(){var e=this;l.a.all([s.a.getActivityById({id:this.$route.query.id}),this.$api.getPrizeTypeThree()]).then(function(t){if(100==t[0].code){for(var r in e.ruleForm1)e.ruleForm1[r]=t[0].data[r]||"";e.ruleForm1.date=[t[0].data.eggBeginTime,t[0].data.eggEndTime];for(var a in e.ruleForm2)e.ruleForm2[a]=t[0].data[a]||"";for(var l in e.ruleForm3)e.ruleForm3[l]=t[0].data[l]||"";e.ruleForm4=t[0].data.prizeSetList}else e.$message.error("获取编辑数据失败");100==t[1].code?e.options=t[1].data:e.$message.error("获取奖品类型失败")})},filters:{prizeStatus:function(e){return 0==e?e="一等奖":1==e?e="二等奖":2==e?e="三等奖":3==e?e="四等奖":4==e?e="五等奖":5==e&&(e="六等奖"),e}}},o={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("section",[r("div",{staticClass:"hd-common turnPlate"},[r("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[r("el-breadcrumb-item",[e._v("互动游戏")]),e._v(" "),r("el-breadcrumb-item",{attrs:{to:{path:"/eggSmash/index"}}},[e._v("砸金蛋")]),e._v(" "),r("el-breadcrumb-item",[e._v("编辑活动")])],1),e._v(" "),r("div",{staticClass:"gt-content"},[r("el-tabs",{attrs:{type:"card"},model:{value:e.active,callback:function(t){e.active=t},expression:"active"}},[r("el-tab-pane",{attrs:{label:"基础设置",name:"0"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"规则设置",name:"1"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"兑奖设置",name:"2"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"奖项设置",name:"3"}})],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:0==this.active,expression:"this.active==0"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm1",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"145px"}},[r("el-form-item",{attrs:{label:"活动名称：",prop:"eggName"}},[r("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入活动名称"},model:{value:e.ruleForm1.eggName,callback:function(t){e.$set(e.ruleForm1,"eggName",t)},expression:"ruleForm1.eggName"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"活动时间：",prop:"date"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{editable:!1,type:"datetimerange",placeholder:"选择时间范围"},model:{value:e.ruleForm1.date,callback:function(t){e.$set(e.ruleForm1,"date",t)},expression:"ruleForm1.date"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"参与人员："}},[r("el-radio-group",{model:{value:e.ruleForm1.eggEggPartaker,callback:function(t){e.$set(e.ruleForm1,"eggEggPartaker",t)},expression:"ruleForm1.eggEggPartaker"}},[r("el-radio",{attrs:{label:1}},[e._v("所有粉丝")]),e._v(" "),r("el-radio",{attrs:{label:2}},[e._v("仅会员(持有会员卡的粉丝)")])],1)],1),e._v(" "),2==e.ruleForm1.eggEggPartaker?r("div",{staticClass:"pd20 bw pt10 bb ml150 mb20"},[r("el-form-item",{attrs:{label:"参与方式：","label-width":"100px"}},[r("el-radio-group",{staticClass:"mg-t10",model:{value:e.ruleForm1.eggPway,callback:function(t){e.$set(e.ruleForm1,"eggPway",t)},expression:"ruleForm1.eggPway"}},[r("el-radio",{attrs:{label:1}},[e._v("所有会员不需要积分")]),r("br"),r("br"),e._v(" "),r("el-radio",{attrs:{label:2}},[e._v("会员卡积分满"),r("el-input",{staticClass:"w100_demo ml10 mt20 mr10",attrs:{disabled:"2"!=e.ruleForm1.eggPway},model:{value:e.ruleForm1.eggMan,callback:function(t){e.$set(e.ruleForm1,"eggMan",t)},expression:"ruleForm1.eggMan"}}),e._v("即可参加（抽奖不扣除积分）")],1),r("br"),r("br"),e._v(" "),r("el-radio",{attrs:{label:3}},[e._v("每次抽奖扣除"),r("el-input",{staticClass:"w100_demo ml10 mt20 mr10",attrs:{disabled:"3"!=e.ruleForm1.eggPway},model:{value:e.ruleForm1.eggKou,callback:function(t){e.$set(e.ruleForm1,"eggKou",t)},expression:"ruleForm1.eggKou"}}),e._v("积分")],1),r("br"),r("br"),e._v(" "),r("el-radio",{attrs:{label:4}},[e._v("会员卡积分满"),r("el-input",{staticClass:"w100_demo ml10 mt20 mr10",attrs:{disabled:"4"!=e.ruleForm1.eggPway},model:{value:e.ruleForm1.eggMan,callback:function(t){e.$set(e.ruleForm1,"eggMan",t)},expression:"ruleForm1.eggMan"}}),e._v("分，\r\n                                                 每次抽奖扣除"),r("el-input",{staticClass:"w100_demo ml10 mt20 mr10",attrs:{disabled:"4"!=e.ruleForm1.eggPway},model:{value:e.ruleForm1.eggKou,callback:function(t){e.$set(e.ruleForm1,"eggKou",t)},expression:"ruleForm1.eggKou"}}),e._v("分")],1)],1)],1)],1):e._e(),e._v(" "),r("el-form-item",{attrs:{label:"活动说明：",prop:"eggDescribe"}},[r("el-input",{staticClass:"w_demo",attrs:{type:"textarea",rows:3,placeholder:"描述活动详情，能让粉丝了解此次活动",maxlength:100},model:{value:e.ruleForm1.eggDescribe,callback:function(t){e.$set(e.ruleForm1,"eggDescribe",t)},expression:"ruleForm1.eggDescribe"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"活动未开始提示：",prop:"eggBeforeTxt"}},[r("el-input",{staticClass:"w_demo",attrs:{type:"textarea",rows:3,placeholder:"请控制字数在100以内，如：活动尚未开始，敬请期待!",maxlength:100},model:{value:e.ruleForm1.eggBeforeTxt,callback:function(t){e.$set(e.ruleForm1,"eggBeforeTxt",t)},expression:"ruleForm1.eggBeforeTxt"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"背景音乐："}},[r("div",{staticClass:"pd20 bb bw bgMusic"},[r("gt-material",{staticClass:"va-m",attrs:{prop:"",isMusic:!0,btnContent:"点击上传",width:"72",height:"72"},on:{getChangeUrl:e.getMusic}}),e._v(" "),r("span",{staticClass:"el-upload__tip c333 ml20"},[e._v(e._s(e.ruleForm1.eggBgmName))]),e._v(" "),r("div",{staticClass:"el-upload__tip grey",staticStyle:{"line-height":"25px"}},[e._v("\r\n                            音频文件的格式为mp3、wma、wav,大小不超过3M\r\n                        ")])],1)]),e._v(" "),r("el-form-item",[r("div",{staticClass:"btnRow"},[r("el-button",{attrs:{type:"primary",loading:e.loading},on:{click:function(t){e.Save("ruleForm1")}}},[e._v("保存")]),e._v(" "),r("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)])],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:1==this.active,expression:"this.active==1"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm2",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"抽奖次数：",prop:"eggCountOfDay"}},[r("el-input",{staticClass:"w_demo mr10",attrs:{placeholder:"请输入每人抽奖总次数"},model:{value:e.ruleForm2.eggCountOfDay,callback:function(t){e.$set(e.ruleForm2,"eggCountOfDay",e._n(t))},expression:"ruleForm2.eggCountOfDay"}}),e._v(" 次/人\r\n                    ")],1),e._v(" "),r("el-form-item",{attrs:{label:"抽奖总数：",prop:"eggCountOfAll"}},[r("el-input",{staticClass:"w_demo mr10",attrs:{placeholder:"请输入每人/每天抽奖总数"},model:{value:e.ruleForm2.eggCountOfAll,callback:function(t){e.$set(e.ruleForm2,"eggCountOfAll",e._n(t))},expression:"ruleForm2.eggCountOfAll"}}),e._v("次/人\r\n                    ")],1),e._v(" "),r("el-form-item",[r("div",{staticClass:"btnRow"},[r("el-button",{attrs:{type:"primary",loading:e.loading},on:{click:function(t){e.Save("ruleForm2")}}},[e._v("保存")]),e._v(" "),r("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)])],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:2==this.active,expression:"this.active==2"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm3",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"兑奖期限：",prop:"eggCashDay"}},[r("el-input",{staticClass:"w_demo mr10",attrs:{type:"number",placeholder:"请输入兑奖期限"},model:{value:e.ruleForm3.eggCashDay,callback:function(t){e.$set(e.ruleForm3,"eggCashDay",e._n(t))},expression:"ruleForm3.eggCashDay"}}),e._v("天\r\n                    "),r("span",{staticClass:"el-upload__tip grey"},[e._v("\r\n                        从活动结束后开始计算\r\n                    ")])],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖地址：",prop:"eggAddress"}},[r("el-input",{staticClass:"w_demo",attrs:{type:"textarea",rows:5,placeholder:"请输入兑奖地址"},model:{value:e.ruleForm3.eggAddress,callback:function(t){e.$set(e.ruleForm3,"eggAddress",t)},expression:"ruleForm3.eggAddress"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖方式："}},[r("el-select",{attrs:{placeholder:"请选择"},model:{value:e.ruleForm3.eggCashWay,callback:function(t){e.$set(e.ruleForm3,"eggCashWay",t)},expression:"ruleForm3.eggCashWay"}},[r("el-option",{attrs:{label:"自动发放",value:1}}),e._v(" "),r("el-option",{attrs:{label:"手动兑奖",value:2}})],1)],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖提示："}},[r("el-input",{staticClass:"w_demo",attrs:{maxlength:100,type:"textarea",rows:5,placeholder:"兑奖提示限制在100个字以内"},model:{value:e.ruleForm3.eggWinningTxt,callback:function(t){e.$set(e.ruleForm3,"eggWinningTxt",t)},expression:"ruleForm3.eggWinningTxt"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"中奖须知："}},[r("el-input",{staticClass:"w_demo",attrs:{maxlength:100,type:"textarea",rows:13,placeholder:"1、如果是实物的奖品，要填写中奖人的手机号码，如不是现场兑奖的还要填写速递地址。2、如中奖是流量的则要填写手机号码，流量将在12小时内到充值中奖人的手机号码上同时运营商会有流量到帐短信通知。3、如中奖是粉币或积分、优惠券的， 则中奖数额会即时自动累计到会员中心对应的类目上， 中奖人可到会员中心查看粉币或积分的增加数量。4、如果是转赠的，则要输入受赠人的手机号，同时受赠人要关注我们的微信公众号。5、中奖人须在规定的时间内完成兑奖，逾期则奖品自动作废。"},model:{value:e.ruleForm3.eggWinningNotice,callback:function(t){e.$set(e.ruleForm3,"eggWinningNotice",t)},expression:"ruleForm3.eggWinningNotice"}})],1),e._v(" "),r("el-form-item",[r("div",{staticClass:"btnRow"},[r("el-button",{attrs:{type:"primary",loading:e.loading},on:{click:function(t){e.Save("ruleForm3")}}},[e._v("保存")]),e._v(" "),r("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)])],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:3==this.active,expression:"this.active==3"}],staticClass:"mt40"},[e._m(0),e._v(" "),r("div",{staticClass:"mt20 mb20"},[r("el-button",{attrs:{disabled:e.ruleForm4.length>4,type:"primary"},on:{click:function(t){e.addForm4()}}},[e._v("新增奖品")]),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("最多设置五个奖项")])],1),e._v(" "),r("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm4,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"奖品类型",width:200},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-select",{staticClass:"w160",attrs:{placeholder:"请选择"},model:{value:t.row.eggPrizeType,callback:function(r){e.$set(t.row,"eggPrizeType",r)},expression:"scope.row.eggPrizeType"}},e._l(e.options,function(t){return 4!=t.value?r("el-option",{key:t.value,attrs:{label:t.name,value:t.value}}):e._e()}))]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品数额",width:200},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w150",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.eggPrizeLimit,callback:function(r){e.$set(t.row,"eggPrizeLimit",r)},expression:"scope.row.eggPrizeLimit"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖项名称",width:240},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{placeholder:"请输入奖项名称"},model:{value:t.row.eggPrizeName,callback:function(r){e.$set(t.row,"eggPrizeName",r)},expression:"scope.row.eggPrizeName"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖项数量",width:200},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w150",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.eggPrizeNums,callback:function(r){e.$set(t.row,"eggPrizeNums",e._n(r))},expression:"scope.row.eggPrizeNums"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"中奖概率(%)",width:200},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w160",attrs:{type:"number",placeholder:"0-100且保留两位小数"},model:{value:t.row.eggPrizeChance,callback:function(r){e.$set(t.row,"eggPrizeChance",r)},expression:"scope.row.eggPrizeChance"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"中奖人","min-width":"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\r\n                      "+e._s(t.row.nickname)+"\r\n                    ")]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作","min-width":"250"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{staticClass:"gt-button-normal blue",on:{click:function(r){e.assign(t)}}},[e._v("指定中奖人")]),e._v(" "),0!=t.$index?r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delForm4(t.$index)}}},[e._v("删除")]):e._e()]}}])})],1),e._v(" "),r("div",{staticClass:"btnRow"},[r("el-button",{attrs:{type:"primary",loading:e.loading},on:{click:e.lastStep}},[e._v("保存")]),e._v(" "),r("el-button",{on:{click:e.backUrl}},[e._v("返回")])],1)],1),e._v(" "),r("div",{staticClass:"h80"}),e._v(" "),r("gt-Fans-detail",{attrs:{visible:e.dialogFans,peopleNums:e.peopleNums},on:{"update:visible":function(t){e.dialogFans=t},getFansData:e.getFansData}})],1)],1)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"gt-gray-region",staticStyle:{color:"#666","line-height":"20px"}},[t("p",[this._v("奖品数额：奖品的数量或内容；奖项数量：该奖品的可领取次数；中奖概率：每种奖项在转盘中的中奖概率")]),this._v(" "),t("p",[this._v("如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12")]),this._v(" "),t("p",[this._v("此次游戏活动设置了六个奖项：其中一个奖项为粉币(奖项名称)，该奖项出现3次(中奖数量)，中了该奖项会得到2个(奖品数额)粉币（奖品类型）。")]),this._v(" "),t("p",[this._v("当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px")])])}]};var g=r("VU/8")(i,o,!1,function(e){r("py6i")},null,null);t.default=g.exports},py6i:function(e,t){}});