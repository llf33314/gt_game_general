webpackJsonp([84],{"4geu":function(module,__webpack_exports__,__webpack_require__){"use strict";var __WEBPACK_IMPORTED_MODULE_0__api_api__=__webpack_require__("dDDG");__webpack_exports__.a={data:function(){var e=this;return{active:0,ruleForm1:{name:"",name1:"",musicUrl:"",music:"暂无上传音乐",links:[{url:"",imgUrl:""},{url:"",imgUrl:""}]},rules1:{name:[{required:!0,message:"活动名称不能为空",trigger:"blur"}],name1:[{required:!0,type:"array",message:"游戏时间不能为空",trigger:"blur"}]},ruleForm2:{code:"",time:"",duihuan:"",manTotalChance:"",manDayChance:"",fenbi:"",jifen:"",desc:"",msgModel:"",msg:1},rules2:{time:[{required:!0,validator:function(e,r,t){var a=window.parseInt(r);a?a<60||a>100?t(new Error("游戏时长有误，请重填")):t():t(new Error("游戏时长不能为空"))},trigger:"blur,change"}],duihuan:[{required:!0,message:"请填写元宝兑换金币比例",trigger:"blur"}],manTotalChance:[{required:!0,message:"请填写每人免费游戏次数",trigger:"blur"}],manDayChance:[{required:!0,message:"请填写每人每天免费游戏次数",trigger:"blur"}],fenbi:[{required:!0,message:"请填写每天游戏小号的粉币或积分",trigger:"blur"}],msg:[{required:!0}],desc:[{required:!0,message:"请填写活动规则",trigger:"blur"}],msgModel:[{required:!0,type:"string",message:"请选择消息模块",trigger:"blur"}]},ruleForm3:{date:"",type:[],addrRow:[{list:""},{list:""}],phone:"",desc:""},rules3:{list:[{required:!0}],date:[{required:!0,type:"array",message:"兑奖时间不能为空"}],type:[{required:!0,type:"array",message:"兑奖方式不能为空",trigger:"blur"}],phone:[{required:!0,type:"text",validator:function(r,t,a){return e.ruleForm3.phone?/^1[34578]\d{9}$/.test(t)||/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(t)?void a():a("联系电话有误，请重填"):a(new Error("联系电话不能为空"))},trigger:"blur"}],desc:[{required:!0,message:"兑奖说明不能为空",trigger:"blur"}]},options:[],ruleForm4:[{name0:"",name1:"",name2:"",name3:"",name4:"",name5:[]},{name0:"",name1:"",name2:"",name3:"",name4:"",name5:[]}],pickerOptions:{disabledDate:function(e){return e.getTime()<Date.now()-864e5}}}},methods:{addrPass:function(e,r,t){r?t():t(new Error("到店领取地址不能为空"))},phoneCheck:function(e,r,t){r?r.match(/^1[3|4|5|6|7|8][0-9][0-9]{8}$/)?t():t(new Error("请输入正确的手机号码")):t(new Error("联系电话不能为空"))},addList:function(){this.ruleForm3.addrRow.push({list:""})},delList:function(e){this.ruleForm3.addrRow.splice(e,1)},upStep:function(){this.active--},addlinks:function(){this.ruleForm1.links.push({url:"",imgUrl:""})},delLinks:function(e){this.ruleForm1.links.splice(e,1)},getChangeUrl:function(e,r){this.ruleForm1.links[e].imgUrl=r.url},getChangeUrl2:function(e){this.ruleForm2.code=e.url},getMusic:function(e){console.log(e),this.ruleForm1.music=e.music.name,this.ruleForm1.musicUrl=e.music.url},addAwardImg:function(e){JSON.parse(e.url).forEach(function(r,t,a){this.ruleForm4[e.prop.$index].name5.push(r.url)},this)},getAwardImgList:function(e){e.url||this.ruleForm4[e.prop.$index].name5.splice(e.sonIndex,1)},getPrizeTypeData:function(){var e=this;Object(__WEBPACK_IMPORTED_MODULE_0__api_api__.h)().then(function(r){100==r.code?(console.log(r,1233),e.options=r.data,console.log(e.options,444)):e.$message.error(r.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},addForm4:function(){this.ruleForm4.push({name0:"",name1:"",name2:"",name3:"",name4:"",name5:[]})},delForm4:function(e){this.ruleForm4.splice(e,1)},next:function(e){var r=this;this.$refs[e].validate(function(e){e?r.submit():console.log("error submit!!")})},checkGL:function checkGL(){for(var arr1=[],i=0;i<this.ruleForm4.length;i++){var arr2={id:parseFloat(this.ruleForm4[i].name4).toFixed(2)};arr1.push(arr2.id)}var getSum=function getSum(ar){var arr=ar,s=eval(arr.join("+"));return s},sum=getSum(arr1);console.log(sum,998),100!=sum?this.$message.error("中奖概率之和必须等于100%"):this.submit()},lastStep:function(){console.log(this.ruleForm4,1243);for(var e=0;e<this.ruleForm4.length;e++){var r=/^[1-9]\d*$/;if(!(this.ruleForm4[e].name1&&this.ruleForm4[e].name2&&this.ruleForm4[e].name3&&this.ruleForm4[e].name4))return this.$message.error("表单不能留空，请填写完整~"),!1;if(!r.test(this.ruleForm4[e].name1))return this.$message.error("奖品单位填写有误，请重新填写~"),!1;if(!r.test(this.ruleForm4[e].name3))return this.$message.error("奖项数量填写有误，请重新填写~"),!1;if(4==this.ruleForm4[e].name0&&0==this.ruleForm4[e].name5.length)return this.$message.error("当奖品为实物时，请上传实物图片~"),!1;if(4==this.ruleForm4[e].name0&&this.ruleForm4[e].name5.length>3)return this.$message.error("实物图片最多上传3张~"),!1;this.ruleForm4[e].name4=parseFloat(this.ruleForm4[e].name4).toFixed(2)}this.checkGL()},submit:function(){var e=this;console.log(this.ruleForm3,123);for(var r=[],t=0;t<this.ruleForm1.links.length;t++){var a={hrefUrl:this.ruleForm1.links[t].url,url:this.ruleForm1.links[t].imgUrl};r.push(a)}var l=[];if(this.ruleForm3.addrRow)for(var s=0;s<this.ruleForm3.addrRow.length;s++){var o={address:this.ruleForm3.addrRow[s].list};l.push(o)}var i=[];if(this.ruleForm4)for(var n=0;n<this.ruleForm4.length;n++){var m={imgInstruction:"",type:this.ruleForm4[n].name0,prizeUnit:Number(this.ruleForm4[n].name1),prizeName:this.ruleForm4[n].name2,num:Number(this.ruleForm4[n].name3),probabiliy:this.ruleForm4[n].name4,loveArrowPrizeImgReqs:[]};if(4==m.type)for(var u=0;u<this.ruleForm4[n].name5.length;u++){var c={imgUrl:this.ruleForm4[n].name5[u]};m.loveArrowPrizeImgReqs.push(c)}i.push(m)}var d={id:this.$router.history.current.query.id,name:this.ruleForm1.name,activityBeginTime:this.ruleForm1.name1[0],activityEndTime:this.ruleForm1.name1[1],musicUrl:this.ruleForm1.musicUrl,loveArrowAdReqs:r,followQrCode:this.ruleForm2.code,manTotalChance:Number(this.ruleForm2.manTotalChance),manDayChance:Number(this.ruleForm2.manDayChance),actRule:this.ruleForm2.desc,cashPrizeBeginTime:this.ruleForm3.date[0],cashPrizeEndTime:this.ruleForm3.date[1],receiveType:this.ruleForm3.type.toString(),phone:this.ruleForm3.phone,cashPrizeInstruction:this.ruleForm3.desc,loveArrowAddressReqs:l,loveArrowPrizeReqs:i};console.log(d,123),Object(__WEBPACK_IMPORTED_MODULE_0__api_api__.m)(d).then(function(r){100==r.code?e.$message({message:"操作成功",type:"success"}):e.$message.error(r.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},backUrl:function(){window.history.go(-1)},test:function(){console.log(1122)},getActData:function(){var e=this,r=this.$router.history.current.query.id;Object(__WEBPACK_IMPORTED_MODULE_0__api_api__.c)(r).then(function(r){if(100==r.code){console.log(r,88552223333),e.ruleForm1.name=r.data.name,e.ruleForm1.name1=[r.data.activityBeginTime,r.data.activityEndTime],e.ruleForm1.musicUrl=r.data.musicUrl,r.data.musicUrl&&(e.ruleForm1.music=r.data.musicUrl.split("/")[r.data.musicUrl.split("/").length-1]);var t=[];if(0!=r.data.loveArrowAdReqs.length)for(var a=0;a<r.data.loveArrowAdReqs.length;a++){var l={url:r.data.loveArrowAdReqs[a].hrefUrl,imgUrl:window.IMAGEURL+r.data.loveArrowAdReqs[a].url};t.push(l)}e.ruleForm1.links=t,r.data.followQrCode&&(e.ruleForm2.code=window.IMAGEURL1+r.data.followQrCode),e.ruleForm2.manTotalChance=String(r.data.manTotalChance),e.ruleForm2.manDayChance=String(r.data.manDayChance),e.ruleForm2.desc=r.data.actRule,e.ruleForm3.date=[r.data.cashPrizeBeginTime,r.data.cashPrizeEndTime],e.ruleForm3.type=r.data.receiveType.split(","),e.ruleForm3.phone=r.data.phone,e.ruleForm3.desc=r.data.cashPrizeInstruction;var s=[];for(a=0;a<r.data.loveArrowAddressReqs.length;a++){l={list:r.data.loveArrowAddressReqs[a].address};s.push(l)}e.ruleForm3.addrRow=s;var o=[];for(a=0;a<r.data.loveArrowPrizeReqs.length;a++){if(4==(l={name0:r.data.loveArrowPrizeReqs[a].type,name1:String(r.data.loveArrowPrizeReqs[a].prizeUnit),name2:r.data.loveArrowPrizeReqs[a].prizeName,name3:String(r.data.loveArrowPrizeReqs[a].num),name4:String(r.data.loveArrowPrizeReqs[a].probabiliy),name5:[]}).name0)for(var i=0;i<r.data.loveArrowPrizeReqs[a].loveArrowPrizeImgReqs.length;i++){var n={url:window.IMAGEURL+r.data.loveArrowPrizeReqs[a].loveArrowPrizeImgReqs[i].imgUrl};l.name5.push(n.url)}o.push(l)}e.ruleForm4=o}else e.$message.error(r.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})}},mounted:function(){this.getPrizeTypeData(),this.getActData()}}},JfyD:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var a=t("4geu"),l={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("section",[t("div",{staticClass:"hd-common"},[t("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[t("el-breadcrumb-item",[e._v("互动游戏")]),e._v(" "),t("el-breadcrumb-item",{attrs:{to:{path:"/throughHeart/index"}}},[e._v("一箭穿心")]),e._v(" "),t("el-breadcrumb-item",[e._v("编辑活动")])],1),e._v(" "),t("div",{staticClass:"gt-content"},[t("el-tabs",{attrs:{type:"card"},model:{value:e.active,callback:function(r){e.active=r},expression:"active"}},[t("el-tab-pane",{attrs:{label:"基础设置",name:"0"}}),e._v(" "),t("el-tab-pane",{attrs:{label:"规则设置",name:"1"}}),e._v(" "),t("el-tab-pane",{attrs:{label:"兑奖设置",name:"2"}}),e._v(" "),t("el-tab-pane",{attrs:{label:"奖项设置",name:"3"}})],1),e._v(" "),t("div",{directives:[{name:"show",rawName:"v-show",value:0==this.active,expression:"this.active==0"}],staticClass:"mt40"},[t("el-form",{ref:"ruleForm1",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"120px"}},[t("el-form-item",{attrs:{label:"活动名称：",prop:"name"}},[t("el-input",{staticClass:"w_demo",model:{value:e.ruleForm1.name,callback:function(r){e.$set(e.ruleForm1,"name",r)},expression:"ruleForm1.name"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"游戏时间：",prop:"name1"}},[t("el-date-picker",{staticClass:"w_demo",attrs:{type:"datetimerange","picker-options":e.pickerOptions,placeholder:"请选择游戏时间范围"},model:{value:e.ruleForm1.name1,callback:function(r){e.$set(e.ruleForm1,"name1",r)},expression:"ruleForm1.name1"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"背景音乐："}},[t("div",{staticClass:"pd20 bb bw bgMusic"},[t("gt-material",{attrs:{prop:"",isMusic:!0,index:2,btnContent:"点击上传",width:"72",height:"72"},on:{getChangeUrl:e.getMusic}}),e._v(" "),t("span",{staticClass:"el-upload__tip grey ml20"},[e._v(e._s(e.ruleForm1.music))]),e._v(" "),t("div",{staticClass:"el-upload__tip grey",staticStyle:{"line-height":"25px"}},[e._v("\r\n                            音频文件的格式为mp3、wma、wav,大小不超过3M\r\n                        ")])],1)]),e._v(" "),t("h1",{staticClass:"mt30 mb20 pb10 bbtom"},[e._v("广告设置")]),e._v(" "),t("el-button",{staticClass:"mb20",attrs:{type:"primary"},on:{click:function(r){e.addlinks()}}},[e._v("新增")]),e._v(" "),t("span",{staticClass:"ml10 el-upload__tip grey"},[e._v("1.仅支持多粉与翼粉开头的链接    2.广告图格式：1000*300px")]),e._v(" "),t("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm1.links,"tooltip-effect":"dark"}},[t("el-table-column",{attrs:{label:"广告链接"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-input",{model:{value:r.row.url,callback:function(t){e.$set(r.row,"url",t)},expression:"scope.row.url"}},[t("template",{slot:"prepend"},[e._v("Http://")])],2)]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"选择图片"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("gt-material",{attrs:{prop:"url",url:r.row.imgUrl,width:"60",height:"60"},on:{getChangeUrl:function(t){e.getChangeUrl(r.$index,t)}}})]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-button",{staticClass:"gt-button-normal",on:{click:function(t){e.delLinks(r.$index)}}},[e._v("删除")])]}}])})],1)],1)],1),e._v(" "),t("div",{directives:[{name:"show",rawName:"v-show",value:1==this.active,expression:"this.active==1"}],staticClass:"mt40"},[t("el-form",{ref:"ruleForm2",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"150px"}},[t("el-form-item",{attrs:{label:"关注二维码：",prop:"code"}},[t("gt-material",{attrs:{prop:"url",url:e.ruleForm2.code,width:"72",height:"72"},on:{getChangeUrl:e.getChangeUrl2}}),e._v(" "),t("span",{staticClass:"el-upload__tip grey ml10"},[e._v("上传1:1二维码，将会在活动规则中显示商家二维码")])],1),e._v(" "),t("el-form-item",{attrs:{label:"游戏总数：",prop:"manTotalChance"}},[t("el-input",{staticClass:"w25_demo mr10",attrs:{type:"number",placeholder:"请输入游戏总数"},model:{value:e.ruleForm2.manTotalChance,callback:function(r){e.$set(e.ruleForm2,"manTotalChance",r)},expression:"ruleForm2.manTotalChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),t("el-form-item",{attrs:{label:"每天次数：",prop:"manDayChance"}},[t("el-input",{staticClass:"w25_demo mr10",attrs:{type:"number",placeholder:"请输入每天游戏次数"},model:{value:e.ruleForm2.manDayChance,callback:function(r){e.$set(e.ruleForm2,"manDayChance",r)},expression:"ruleForm2.manDayChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),t("el-form-item",{attrs:{label:"活动规则：",prop:"desc"}},[t("el-input",{staticClass:"w_demo",attrs:{maxlength:300,type:"textarea",rows:3,placeholder:"请填写活动规则"},model:{value:e.ruleForm2.desc,callback:function(r){e.$set(e.ruleForm2,"desc",r)},expression:"ruleForm2.desc"}}),e._v(" "),t("span",{staticClass:"el-upload__tip grey ml10"},[e._v("300个字以内")])],1)],1)],1),e._v(" "),t("div",{directives:[{name:"show",rawName:"v-show",value:2==this.active,expression:"this.active==2"}],staticClass:"mt40"},[t("el-form",{ref:"ruleForm3",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"120px"}},[t("el-form-item",{attrs:{label:"兑奖时间：",prop:"date"}},[t("el-date-picker",{staticClass:"w_demo",attrs:{"picker-options":e.pickerOptions,type:"daterange",placeholder:"选择日期范围"},model:{value:e.ruleForm3.date,callback:function(r){e.$set(e.ruleForm3,"date",r)},expression:"ruleForm3.date"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"兑奖方式：",prop:"type"}},[t("el-checkbox-group",{model:{value:e.ruleForm3.type,callback:function(r){e.$set(e.ruleForm3,"type",r)},expression:"ruleForm3.type"}},[t("el-checkbox",{attrs:{label:"1",name:"1"}},[e._v("到店领取")]),e._v(" "),t("el-checkbox",{attrs:{label:"2",name:"2"}},[e._v("邮寄")])],1)],1),e._v(" "),1==this.ruleForm3.type[0]||1==this.ruleForm3.type[1]?t("div",{staticClass:"bb pt20 pb20 ml120 bw mb10"},[t("span",{staticStyle:{"margin-left":"30px",color:"#333"}},[t("span",{staticStyle:{color:"#ff4949","margin-right":"3px"}},[e._v("*")]),e._v("兑奖地址：")]),e._v(" "),t("el-button",{staticClass:"mb10",attrs:{type:"primary"},on:{click:function(r){e.addList()}}},[e._v("添加")]),e._v(" "),e._l(e.ruleForm3.addrRow,function(r,a){return t("el-form-item",{key:r.key,attrs:{prop:"addrRow."+a+".list",rules:{required:!0,validator:e.addrPass,trigger:"blur"}}},[t("el-input",{staticClass:"w_demo mr10",attrs:{prop:"list",placeholder:"请输入到店领取地址"},model:{value:r.list,callback:function(t){e.$set(r,"list",t)},expression:"item.list"}}),e._v(" "),0!=a?t("span",{staticClass:"blueee",on:{click:function(r){e.delList(a)}}},[e._v("删除")]):e._e()],1)})],2):e._e(),e._v(" "),t("el-form-item",{attrs:{label:"联系电话：",prop:"phone"}},[t("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入联系电话(固话用-分割)"},model:{value:e.ruleForm3.phone,callback:function(r){e.$set(e.ruleForm3,"phone",r)},expression:"ruleForm3.phone"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"兑奖说明：",prop:"desc"}},[t("el-input",{staticClass:"bw",attrs:{type:"textarea",rows:5,placeholder:"请输入兑奖说明"},model:{value:e.ruleForm3.desc,callback:function(r){e.$set(e.ruleForm3,"desc",r)},expression:"ruleForm3.desc"}})],1)],1)],1),e._v(" "),t("div",{directives:[{name:"show",rawName:"v-show",value:3==this.active,expression:"this.active==3"}],staticClass:"mt40"},[e._m(0),e._v(" "),t("div",{staticClass:"mt20 mb20"},[t("el-button",{attrs:{disabled:e.ruleForm4.length>8,type:"primary"},on:{click:function(r){e.addForm4()}}},[e._v("新增奖品")]),e._v(" "),t("span",{staticClass:"el-upload__tip grey ml10"},[e._v("最多设置9个奖项")])],1),e._v(" "),t("el-tooltip",{attrs:{placement:"top",effect:"light"}},[t("div",{attrs:{slot:"content"},slot:"content"},[e._v("\r\n                    当奖品为实物时，请上传实物图片\r\n                ")]),e._v(" "),t("span",{staticClass:"el-icon-warning",staticStyle:{"font-size":"18px","margin-left":"71%","z-index":"11",position:"absolute","margin-top":"12px",color:"#ccc"}})]),e._v(" "),t("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm4,"tooltip-effect":"dark"}},[t("el-table-column",{attrs:{label:"奖品类型"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-select",{attrs:{placeholder:"请选择"},model:{value:r.row.name0,callback:function(t){e.$set(r.row,"name0",t)},expression:"scope.row.name0"}},e._l(e.options,function(e){return t("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}))]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"奖品单位"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:r.row.name1,callback:function(t){e.$set(r.row,"name1",t)},expression:"scope.row.name1"}})]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"奖品名称"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-input",{staticClass:"w20_demo",attrs:{placeholder:"请输入奖品名称"},model:{value:r.row.name2,callback:function(t){e.$set(r.row,"name2",t)},expression:"scope.row.name2"}})]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"奖项数量"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:r.row.name3,callback:function(t){e.$set(r.row,"name3",t)},expression:"scope.row.name3"}})]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"中奖概率(%)"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("el-input",{staticClass:"w20_demo",attrs:{placeholder:"0-100且保留两位小数"},model:{value:r.row.name4,callback:function(t){e.$set(r.row,"name4",t)},expression:"scope.row.name4"}})]}}])}),e._v(" "),t("el-table-column",{attrs:{label:"奖品图片"},scopedSlots:e._u([{key:"default",fn:function(r){return 4==r.row.name0||"实体物品"==r.row.name0?[e._l(r.row.name5,function(a,l){return t("gt-material",{key:l,staticClass:"mr10",attrs:{prop:r,sonIndex:l,selectType:"radio",url:a,width:"50",height:"50"},on:{getChangeUrl:e.getAwardImgList}})}),e._v(" "),t("gt-material",{staticClass:"uploadBtn",attrs:{prop:r,selectType:"select",width:"50",height:"50"},on:{getChangeUrl:e.addAwardImg}})]:void 0}}])}),e._v(" "),t("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(r){return[0!=r.$index?t("el-button",{staticClass:"gt-button-normal",on:{click:function(t){e.delForm4(r.$index)}}},[e._v("删除")]):e._e()]}}])})],1)],1),e._v(" "),t("div",{staticClass:"h80"}),e._v(" "),5!=this.active?t("div",{staticClass:"btnRow"},[t("el-button",{on:{click:function(r){e.backUrl()}}},[e._v("返回")]),e._v(" "),0==this.active||1==this.active?t("el-button",{attrs:{type:"primary"},on:{click:function(r){e.submit()}}},[e._v("保存")]):e._e(),e._v(" "),2==this.active?t("el-button",{attrs:{type:"primary"},on:{click:function(r){e.next("ruleForm3")}}},[e._v("保存")]):e._e(),e._v(" "),3==this.active?t("el-button",{attrs:{type:"primary"},on:{click:function(r){e.lastStep()}}},[e._v("保存")]):e._e()],1):e._e()],1)],1)])},staticRenderFns:[function(){var e=this.$createElement,r=this._self._c||e;return r("div",{staticClass:"gt-gray-region mt20",staticStyle:{color:"#666","line-height":"20px"}},[r("p",[this._v("奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数")]),this._v(" "),r("p",[this._v("如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12")]),this._v(" "),r("p",[this._v("当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px")])])}]},s=t("VU/8")(a.a,l,!1,null,null,null);r.default=s.exports}});