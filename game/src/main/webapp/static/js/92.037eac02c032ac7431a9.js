webpackJsonp([92],{"1SHM":function(module,__webpack_exports__,__webpack_require__){"use strict";var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty__=__webpack_require__("bOdI"),__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default=__webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty__),__WEBPACK_IMPORTED_MODULE_1__api_api__=__webpack_require__("xRBu");__webpack_exports__.a={data:function(){var e=this;return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()({active:0,isSubmit:!1,ruleForm1:{name:"",name1:"",busLogo:"",busName:"",links:[{url:"",imgUrl:""},{url:"",imgUrl:""}]},rules1:{name:[{required:!0,message:"活动名称不能为空",trigger:"blur"}],name1:[{required:!0,type:"array",message:"游戏时间不能为空",trigger:"blur"}]},ruleForm2:{code:"",manTotalChance:"",manDayChance:"",desc:""},pickerOptions:{disabledDate:function(e){return e.getTime()<Date.now()-864e5}},rules2:{manTotalChance:[{required:!0,message:"游戏总数不能为空",trigger:"blur"}],manDayChance:[{required:!0,message:"每天次数不能为空",trigger:"blur"}],desc:[{required:!0,message:"请填写活动规则",trigger:"blur"}]},ruleForm3:{date:"",type:[],addrRow:[{list:""},{list:""}],phone:"",desc:""},rules3:{list:[{required:!0}],date:[{required:!0,type:"array",message:"兑奖时间不能为空"}],type:[{required:!0,type:"array",message:"兑奖方式不能为空",trigger:"blur"}],phone:[{required:!0,type:"text",validator:function(t,r,a){return e.ruleForm3.phone?/^1[34578]\d{9}$/.test(r)||/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(r)?void a():a("联系电话有误，请重填"):a(new Error("联系电话不能为空"))},trigger:"blur"}],desc:[{required:!0,message:"兑奖说明不能为空",trigger:"blur"}]},options:[],memberOptions:[],ruleForm4:[{name0:"",name1:"",name2:"",name3:"",name4:"",name5:[],cardsName:""},{name0:"",name1:"",name2:"",name3:"",name4:"",name5:[],cardsName:""}]},"pickerOptions",{disabledDate:function(e){return e.getTime()<Date.now()-864e5}})},methods:{getMusic:function(e){console.log(e),this.ruleForm1.music=e.music.name,this.ruleForm1.musicUrl=e.music.url},getPrizeTypeData:function(){var e=this;Object(__WEBPACK_IMPORTED_MODULE_1__api_api__.i)().then(function(t){100==t.code?(console.log(t,1233),e.options=t.data,console.log(e.options,444)):e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},addrPass:function(e,t,r){t?r():r(new Error("到店领取地址不能为空"))},addlinks:function(){this.ruleForm1.links.push({url:"",imgUrl:""})},delLinks:function(e){this.ruleForm1.links.splice(e,1)},getChangeUrl:function(e,t){this.ruleForm1.links[e].imgUrl=t.url},phoneCheck:function(e,t,r){t?t.match(/^1[3|4|5|6|7|8][0-9][0-9]{8}$/)?r():r(new Error("请输入正确的手机号码")):r(new Error("联系电话不能为空"))},addList:function(){this.ruleForm3.addrRow.push({list:""})},delList:function(e){this.ruleForm3.addrRow.splice(e,1)},upStep:function(){this.active--},getChangeUrl2:function(e){this.ruleForm2.code=e.url},addAwardImg:function(e){JSON.parse(e.url).forEach(function(t,r,a){this.ruleForm4[e.prop.$index].name5.push(t.url)},this)},getAwardImgList:function(e){e.url||this.ruleForm4[e.prop.$index].name5.splice(e.sonIndex,1)},getChangeUrl4:function(e,t){this.ruleForm4[e].name5=t.url},getChangeUrl1:function(e){this.ruleForm1.busLogo=e.url},addForm4:function(){this.ruleForm4.push({name0:"",name1:"",name2:"",name3:"",name4:"",name5:[],cardsName:""})},delForm4:function(e){this.ruleForm4.splice(e,1)},next:function(e){var t=this;this.$refs[e].validate(function(e){e?t.submit():console.log("error submit!!")})},optionsData:function(e){for(var t=0;t<this.memberOptions.length;t++)this.memberOptions[t].id!=this.ruleForm4[e].name2&&this.ruleForm4[e].name2!=this.memberOptions[t].id||(this.ruleForm4[e].cardsName=this.memberOptions[t].cardsName)},checkGL:function checkGL(){for(var arr1=[],i=0;i<this.ruleForm4.length;i++){var arr2={id:parseFloat(this.ruleForm4[i].name4).toFixed(2)};arr1.push(arr2.id)}var getSum=function getSum(ar){var arr=ar,s=eval(arr.join("+"));return s},sum=getSum(arr1);100!=sum?this.$message.error("中奖概率之和必须等于100%"):this.submit()},lastStep:function(){for(var e=0;e<this.ruleForm4.length;e++){var t=/^[1-9]\d*$/;if(!(this.ruleForm4[e].name0&&this.ruleForm4[e].name1&&this.ruleForm4[e].name2&&this.ruleForm4[e].name3&&0!=this.ruleForm4[e].name4.length))return this.$message.error("表单不能留空，请填写完整~"),!1;if(!t.test(this.ruleForm4[e].name1))return this.$message.error("奖品单位填写有误，请重新填写~"),!1;if(!t.test(this.ruleForm4[e].name3))return this.$message.error("奖项数量填写有误，请重新填写~"),!1;if(4==this.ruleForm4[e].name0&&0==this.ruleForm4[e].name5.length)return this.$message.error("当奖品为实物时，请上传实物图片~"),!1;if(4==this.ruleForm4[e].name0&&this.ruleForm4[e].name5.length>3)return this.$message.error("实物图片最多上传3张~"),!1;this.ruleForm4[e].name4=parseFloat(this.ruleForm4[e].name4).toFixed(2)}this.checkGL()},submit:function(){for(var e=this,t=[],r=0;r<this.ruleForm1.links.length;r++){var a={hrefUrl:this.ruleForm1.links[r].url,url:this.ruleForm1.links[r].imgUrl};t.push(a)}var l=[];if(this.ruleForm3.addrRow)for(var s=0;s<this.ruleForm3.addrRow.length;s++){var o={address:this.ruleForm3.addrRow[s].list};l.push(o)}var i=[];if(this.ruleForm4)for(var n=0;n<this.ruleForm4.length;n++){var m={imgInstruction:"",type:this.ruleForm4[n].name0,prizeUnit:Number(this.ruleForm4[n].name1),prizeName:this.ruleForm4[n].name2,num:Number(this.ruleForm4[n].name3),probabiliy:this.ruleForm4[n].name4,shakeluckPrizeImgReqs:[],cardReceiveId:""};if(7==m.type&&(m.prizeName=this.ruleForm4[n].cardsName,m.cardReceiveId=this.ruleForm4[n].name2),4==m.type)for(var u=0;u<this.ruleForm4[n].name5.length;u++){var c={imgUrl:this.ruleForm4[n].name5[u]};m.shakeluckPrizeImgReqs.push(c)}i.push(m)}var d={id:this.$router.history.current.query.id,name:this.ruleForm1.name,activityBeginTime:this.ruleForm1.name1[0],activityEndTime:this.ruleForm1.name1[1],busLogo:this.ruleForm1.busLogo,busName:this.ruleForm1.busName,shakeluckAdReqs:t,followQrCode:this.ruleForm2.code,manTotalChance:Number(this.ruleForm2.manTotalChance),manDayChance:Number(this.ruleForm2.manDayChance),actRule:this.ruleForm2.desc,cashPrizeBeginTime:this.ruleForm3.date[0],cashPrizeEndTime:this.ruleForm3.date[1],receiveType:this.ruleForm3.type.toString(),shakeluckAddressReqs:l,phone:this.ruleForm3.phone,cashPrizeInstruction:this.ruleForm3.desc,shakeluckPrizeReqs:i};console.log(d,123),Object(__WEBPACK_IMPORTED_MODULE_1__api_api__.n)(d).then(function(t){100==t.code?e.$message({message:"操作成功",type:"success"}):e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},getMemberTypeData:function(){var e=this;Object(__WEBPACK_IMPORTED_MODULE_1__api_api__.f)().then(function(t){100==t.code?e.memberOptions=t.data:e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},backUrl:function(){window.history.go(-1)},test:function(){console.log(1122)},geteditData:function(){var e=this,t=this.$router.history.current.query.id;Object(__WEBPACK_IMPORTED_MODULE_1__api_api__.c)(t).then(function(t){if(100==t.code){console.log(t,1233),e.ruleForm1.name=t.data.name,e.ruleForm1.name1=[t.data.activityBeginTime,t.data.activityEndTime],e.ruleForm1.busLogo=t.data.busLogo,e.ruleForm1.busName=t.data.busName;var r=[];if(0!=t.data.shakeluckAdReqs.length)for(var a=0;a<t.data.shakeluckAdReqs.length;a++){var l={url:t.data.shakeluckAdReqs[a].hrefUrl,imgUrl:window.IMAGEURL+t.data.shakeluckAdReqs[a].url};r.push(l)}e.ruleForm1.links=r,t.data.followQrCode&&(e.ruleForm2.code=window.IMAGEURL+t.data.followQrCode),e.ruleForm2.manTotalChance=String(t.data.manTotalChance),e.ruleForm2.manDayChance=String(t.data.manDayChance),e.ruleForm2.desc=t.data.actRule,e.ruleForm3.date=[t.data.cashPrizeBeginTime,t.data.cashPrizeEndTime],e.ruleForm3.type=t.data.receiveType.split(","),e.ruleForm3.phone=t.data.phone,e.ruleForm3.desc=t.data.cashPrizeInstruction;var s=[];for(a=0;a<t.data.shakeluckAddressReqs.length;a++){l={list:t.data.shakeluckAddressReqs[a].address};s.push(l)}e.ruleForm3.addrRow=s;var o=[];for(a=0;a<t.data.shakeluckPrizeReqs.length;a++){if(7==(l={name0:t.data.shakeluckPrizeReqs[a].type,name1:t.data.shakeluckPrizeReqs[a].prizeUnit,name2:t.data.shakeluckPrizeReqs[a].prizeName,name3:String(t.data.shakeluckPrizeReqs[a].num),name4:String(t.data.shakeluckPrizeReqs[a].probabiliy),name5:[]}).name0&&(l.cardsName=t.data.shakeluckPrizeReqs[a].prizeName,l.name2=t.data.shakeluckPrizeReqs[a].cardReceiveId),4==l.name0)for(var i=0;i<t.data.shakeluckPrizeReqs[a].shakeluckPrizeImgReqs.length;i++){var n={url:window.IMAGEURL+t.data.shakeluckPrizeReqs[a].shakeluckPrizeImgReqs[i].imgUrl};l.name5.push(n.url)}o.push(l)}e.ruleForm4=o}else e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})}},mounted:function(){this.getPrizeTypeData(),this.geteditData(),this.getMemberTypeData()}}},mC7v:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=r("1SHM"),l={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("section",[r("div",{staticClass:"hd-common"},[r("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[r("el-breadcrumb-item",{nativeOn:{click:function(t){e.$util.ClickApply(t)}}},[e._v("互动游戏")]),e._v(" "),r("el-breadcrumb-item",{attrs:{to:{path:"/luckSake/index"}}},[e._v("摇手气")]),e._v(" "),r("el-breadcrumb-item",[e._v("创建活动")])],1),e._v(" "),r("div",{staticClass:"gt-content"},[r("el-tabs",{attrs:{type:"card"},model:{value:e.active,callback:function(t){e.active=t},expression:"active"}},[r("el-tab-pane",{attrs:{label:"基础设置",name:"0"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"规则设置",name:"1"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"兑奖设置",name:"2"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"奖项设置",name:"3"}})],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:0==this.active,expression:"this.active==0"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm1",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"活动名称：",prop:"name"}},[r("el-input",{staticClass:"w_demo",model:{value:e.ruleForm1.name,callback:function(t){e.$set(e.ruleForm1,"name",t)},expression:"ruleForm1.name"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏时间：",prop:"name1"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{"picker-options":e.pickerOptions,type:"datetimerange",placeholder:"选择时间范围"},model:{value:e.ruleForm1.name1,callback:function(t){e.$set(e.ruleForm1,"name1",t)},expression:"ruleForm1.name1"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"商家LOGO："}},[r("gt-material",{attrs:{prop:"url",url:e.ruleForm1.busLogo,width:"72",height:"72"},on:{getChangeUrl:e.getChangeUrl1}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("上传1:1的商家logo，将会在游戏首页显示商家logo")])],1),e._v(" "),r("el-form-item",{attrs:{label:"商家名称："}},[r("el-input",{staticClass:"w_demo",model:{value:e.ruleForm1.busName,callback:function(t){e.$set(e.ruleForm1,"busName",t)},expression:"ruleForm1.busName"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("有商家logo才需要填写商家名称")])],1),e._v(" "),r("h1",{staticClass:"mt30 mb20 pb10 bbtom"},[e._v("广告设置")]),e._v(" "),r("el-button",{staticClass:"mb20",attrs:{type:"primary"},on:{click:function(t){e.addlinks()}}},[e._v("新增")]),e._v(" "),r("span",{staticClass:"ml10 el-upload__tip grey"},[e._v("1.仅支持多粉与翼粉开头的链接    2.广告图格式：1000*300px")]),e._v(" "),r("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm1.links,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"广告链接"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{model:{value:t.row.url,callback:function(r){e.$set(t.row,"url",r)},expression:"scope.row.url"}},[r("template",{slot:"prepend"},[e._v("Http://")])],2)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"选择图片"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("gt-material",{attrs:{prop:"url",url:t.row.imgUrl,width:"60",height:"60"},on:{getChangeUrl:function(r){e.getChangeUrl(t.$index,r)}}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delLinks(t.$index)}}},[e._v("删除")])]}}])})],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:1==this.active,expression:"this.active==1"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm2",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"150px"}},[r("el-form-item",{attrs:{label:"关注二维码："}},[r("gt-material",{attrs:{url:e.ruleForm2.code,width:"72",height:"72"},on:{getChangeUrl:e.getChangeUrl2}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("上传1:1二维码，将会在活动规则中显示商家二维码")])],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏总数：",prop:"manTotalChance"}},[r("el-input",{staticClass:"w25_demo mr10",attrs:{placeholder:"请输入游戏总数"},model:{value:e.ruleForm2.manTotalChance,callback:function(t){e.$set(e.ruleForm2,"manTotalChance",t)},expression:"ruleForm2.manTotalChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"每天次数：",prop:"manDayChance"}},[r("el-input",{staticClass:"w25_demo mr10",attrs:{placeholder:"请输入每天次数"},model:{value:e.ruleForm2.manDayChance,callback:function(t){e.$set(e.ruleForm2,"manDayChance",t)},expression:"ruleForm2.manDayChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"活动规则：",prop:"desc"}},[r("el-input",{staticClass:"w_demo",attrs:{maxlength:300,type:"textarea",rows:3,placeholder:"请填写活动规则"},model:{value:e.ruleForm2.desc,callback:function(t){e.$set(e.ruleForm2,"desc",t)},expression:"ruleForm2.desc"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("300个字以内")])],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:2==this.active,expression:"this.active==2"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm3",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"兑奖时间：",prop:"date"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{"picker-options":e.pickerOptions,type:"daterange",placeholder:"选择日期范围"},model:{value:e.ruleForm3.date,callback:function(t){e.$set(e.ruleForm3,"date",t)},expression:"ruleForm3.date"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖方式：",prop:"type"}},[r("el-checkbox-group",{model:{value:e.ruleForm3.type,callback:function(t){e.$set(e.ruleForm3,"type",t)},expression:"ruleForm3.type"}},[r("el-checkbox",{attrs:{label:"1",name:"1"}},[e._v("到店领取")]),e._v(" "),r("el-checkbox",{attrs:{label:"2",name:"2"}},[e._v("邮寄")])],1)],1),e._v(" "),1==this.ruleForm3.type[0]||1==this.ruleForm3.type[1]?r("div",{staticClass:"bb pt20 pb20 ml120 bw mb10"},[r("span",{staticStyle:{"margin-left":"30px",color:"#333"}},[r("span",{staticStyle:{color:"#ff4949","margin-right":"3px"}},[e._v("*")]),e._v("兑奖地址：")]),e._v(" "),r("el-button",{staticClass:"mb10",attrs:{type:"primary"},on:{click:function(t){e.addList()}}},[e._v("添加")]),e._v(" "),e._l(e.ruleForm3.addrRow,function(t,a){return r("el-form-item",{key:t.key,attrs:{prop:"addrRow."+a+".list",rules:{required:!0,validator:e.addrPass,trigger:"blur"}}},[r("el-input",{staticClass:"w_demo mr10",attrs:{prop:"list",placeholder:"请输入到店领取地址"},model:{value:t.list,callback:function(r){e.$set(t,"list",r)},expression:"item.list"}}),e._v(" "),0!=a?r("span",{staticClass:"blueee",on:{click:function(t){e.delList(a)}}},[e._v("删除")]):e._e()],1)})],2):e._e(),e._v(" "),r("el-form-item",{attrs:{label:"联系电话：",prop:"phone"}},[r("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入联系电话(固话用-分割)"},model:{value:e.ruleForm3.phone,callback:function(t){e.$set(e.ruleForm3,"phone",t)},expression:"ruleForm3.phone"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖说明：",prop:"desc"}},[r("el-input",{staticClass:"bw",attrs:{type:"textarea",rows:5,placeholder:"请输入兑奖说明"},model:{value:e.ruleForm3.desc,callback:function(t){e.$set(e.ruleForm3,"desc",t)},expression:"ruleForm3.desc"}})],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:3==this.active,expression:"this.active==3"}],staticClass:"mt40"},[e._m(0),e._v(" "),r("div",{staticClass:"mt20 mb20"},[r("el-button",{attrs:{disabled:e.ruleForm4.length>8,type:"primary"},on:{click:function(t){e.addForm4()}}},[e._v("新增奖品")]),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("最多设置9个奖项")])],1),e._v(" "),r("el-tooltip",{attrs:{placement:"top",effect:"light"}},[r("div",{attrs:{slot:"content"},slot:"content"},[e._v("\r\n                    当奖品为实物时，请上传实物图片\r\n                ")]),e._v(" "),r("span",{staticClass:"el-icon-warning",staticStyle:{"font-size":"18px","margin-left":"71%","z-index":"11",position:"absolute","margin-top":"12px",color:"#ccc"}})]),e._v(" "),r("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm4,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"奖品类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-select",{attrs:{placeholder:"请选择"},model:{value:t.row.name0,callback:function(r){e.$set(t.row,"name0",r)},expression:"scope.row.name0"}},e._l(e.options,function(e){return r("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}))]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品单位"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.name1,callback:function(r){e.$set(t.row,"name1",r)},expression:"scope.row.name1"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品名称"},scopedSlots:e._u([{key:"default",fn:function(t){return[7==t.row.name0?r("el-select",{attrs:{placeholder:"请选择"},on:{change:function(r){e.optionsData(t.$index)}},model:{value:t.row.name2,callback:function(r){e.$set(t.row,"name2",r)},expression:"scope.row.name2"}},e._l(e.memberOptions,function(e){return r("el-option",{key:e.id,attrs:{label:e.cardsName,value:e.id}})})):r("el-input",{staticClass:"w20_demo",model:{value:t.row.name2,callback:function(r){e.$set(t.row,"name2",r)},expression:"scope.row.name2"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖项数量"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.name3,callback:function(r){e.$set(t.row,"name3",r)},expression:"scope.row.name3"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"中奖概率(%)"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{placeholder:"0-100且保留两位小数"},model:{value:t.row.name4,callback:function(r){e.$set(t.row,"name4",r)},expression:"scope.row.name4"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品图片"},scopedSlots:e._u([{key:"default",fn:function(t){return 4==t.row.name0||"实体物品"==t.row.name0?[e._l(t.row.name5,function(a,l){return r("gt-material",{key:l,staticClass:"mr10",attrs:{prop:t,sonIndex:l,selectType:"radio",url:a,width:"50",height:"50"},on:{getChangeUrl:e.getAwardImgList}})}),e._v(" "),r("gt-material",{staticClass:"uploadBtn",attrs:{prop:t,selectType:"select",width:"50",height:"50"},on:{getChangeUrl:e.addAwardImg}})]:void 0}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[0!=t.$index?r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delForm4(t.$index)}}},[e._v("删除")]):e._e()]}}])})],1)],1),e._v(" "),5==e.active?r("div",{staticClass:"gt-content complete"},[r("div",{staticClass:"addOk"},[r("div",{staticClass:"el-icon-circle-check green",staticStyle:{"font-size":"40px"}}),e._v(" "),r("div",{staticClass:"complete-info"},[e._v("活动添加成功")]),e._v(" "),r("el-button",{staticClass:"mt80",attrs:{type:"primary"},on:{click:function(t){e.backUrl()}}},[e._v("返回活动列表")])],1)]):e._e(),e._v(" "),r("div",{staticClass:"h80"}),e._v(" "),5!=this.active?r("div",{staticClass:"btnRow"},[r("el-button",{on:{click:function(t){e.backUrl()}}},[e._v("返回")]),e._v(" "),0==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm1")}}},[e._v("保存")]):e._e(),e._v(" "),1==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm2")}}},[e._v("保存")]):e._e(),e._v(" "),2==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm3")}}},[e._v("保存")]):e._e(),e._v(" "),3==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.lastStep()}}},[e._v("保存")]):e._e()],1):e._e()],1)],1)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"gt-gray-region mt20",staticStyle:{color:"#666","line-height":"20px"}},[t("p",[this._v("奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数")]),this._v(" "),t("p",[this._v("如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12")]),this._v(" "),t("p",[this._v("当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px")])])}]},s=r("VU/8")(a.a,l,!1,null,null,null);t.default=s.exports}});