webpackJsonp([72],{"S/sk":function(e,t){},Vt9O:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=r("PTM0"),l={data:function(){var e=this;return{active:0,ruleForm1:{name:"",gtTime:"",name4:"",links:[{url:"",imgUrl:""},{url:"",imgUrl:""}]},rules1:{name:[{required:!0,message:"活动名称不能为空",trigger:"blur"}],gtTime:[{required:!0,type:"array",message:"游戏时间不能为空",trigger:"blur"}]},checked:!1,ruleForm2:{followQrCode:"",time:"",duihuan:"",manTotalChance:"",manDayChance:"",fenbi:"",jifen:"",desc:"",msgTemplateId:"",isMsgTemplate:0},rules2:{time:[{required:!0,validator:function(e,t,r){var a=window.parseInt(t);a?a<10||a>30?r(new Error("游戏时长有误，请重填")):r():r(new Error("游戏时长不能为空"))},trigger:"blur,change"}],duihuan:[{required:!0,message:"请填写元宝兑换金币比例",trigger:"blur"}],manTotalChance:[{required:!0,message:"请填写每人免费游戏次数",trigger:"blur"}],manDayChance:[{required:!0,message:"请填写每人每天免费游戏次数",trigger:"blur"}],fenbi:[{required:!0,message:"请填写每天游戏小号的粉币或积分",trigger:"blur"}],isMsgTemplate:[{required:!0}],desc:[{required:!0,message:"请填写活动规则",trigger:"blur"}],msgTemplateId:[{required:!0,message:"请选择消息模块"}]},textArray:[],ruleForm3:{date:"",type:[],addrRow:[{list:""},{list:""}],phone:"",desc:""},rules3:{list:[{required:!0}],date:[{required:!0,type:"array",message:"兑奖时间不能为空"}],type:[{required:!0,type:"array",message:"兑奖方式不能为空",trigger:"blur"}],phone:[{required:!0,type:"text",validator:function(t,r,a){return e.ruleForm3.phone?/^1[34578]\d{9}$/.test(r)||/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(r)?void a():a("联系电话有误，请重填"):a(new Error("联系电话不能为空"))},trigger:"blur"}],desc:[{required:!0,message:"兑奖说明不能为空",trigger:"blur"}]},pickerOptions:{disabledDate:function(e){return e.getTime()<Date.now()-864e5}},explain:"",options:[],ruleForm4:[{name0:"",name1:"",name2:"",name3:"",name5:[]},{name0:"",name1:"",name2:"",name3:"",name5:[]}]}},methods:{addrPass:function(e,t,r){t?r():r(new Error("到店领取地址不能为空"))},addList:function(){this.ruleForm3.addrRow.push({list:""})},delList:function(e){this.ruleForm3.addrRow.splice(e,1)},addlinks:function(){this.ruleForm1.links.push({url:"",imgUrl:""})},delLinks:function(e){this.ruleForm1.links.splice(e,1)},getChangeUrl:function(e,t){this.ruleForm1.links[e].imgUrl=t.url},fetchData:function(){var e=this;Object(a.j)().then(function(t){100==t.code?e.textArray=e.$util.isArray(t.data):e.$message.error("获取评论列表失败")})},getChangeUrl2:function(e){this.ruleForm2.followQrCode=e.url},getChangeUrl4:function(e,t){this.ruleForm4[e].name4=t.url},addAwardImg:function(e){JSON.parse(e.url).forEach(function(t,r,a){this.ruleForm4[e.prop.$index].name5.push(t.url)},this)},getAwardImgList:function(e){e.url||this.ruleForm4[e.prop.$index].name5.splice(e.sonIndex,1)},addForm4:function(){this.ruleForm4.push({name0:"",name1:"",name2:"",name3:"",name5:[]})},delForm4:function(e){this.ruleForm4.splice(e,1)},getPrizeTypeData:function(){var e=this;Object(a.h)().then(function(t){100==t.code?(console.log(t,1233),e.options=t.data,console.log(e.options,"获取奖品类型")):e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},next:function(e){var t=this;this.checked=!0,this.$refs[e].validate(function(r){if(r){if("ruleForm1"==e){console.log(t);for(var a=0;a<t.ruleForm1.links.length;a++){if(!t.ruleForm1.links[a].imgUrl)return t.$message.error("图片不能为空 "),!1;if(!t.ruleForm1.links[a].url)return t.$message.error("广告链接不能为空"),!1}}t.submit()}else console.log("error submit!!")})},lastStep:function(){console.log(this.ruleForm4,1243);for(var e=0;e<this.ruleForm4.length;e++){var t=/^[1-9]\d*$/;if(!(this.ruleForm4[e].name0&&this.ruleForm4[e].name1&&this.ruleForm4[e].name2&&this.ruleForm4[e].name3))return this.$message.error("表单不能留空，请填写完整~"),!1;if(!t.test(this.ruleForm4[e].name1))return this.$message.error("奖品单位填写有误，请重新填写~"),!1;if(!t.test(this.ruleForm4[e].name3))return this.$message.error("奖项数量填写有误，请重新填写~"),!1;if(4==this.ruleForm4[e].name0&&0==this.ruleForm4[e].name5.length)return this.$message.error("当奖品为实物时，请上传实物图片~"),!1}this.submit()},submit:function(){for(var e=this,t=[],r=0;r<this.ruleForm1.links.length;r++){var l={hrefUrl:this.ruleForm1.links[r].url,url:this.ruleForm1.links[r].imgUrl};t.push(l)}var s=[];if(this.ruleForm3.addrRow)for(var o=0;o<this.ruleForm3.addrRow.length;o++){var i={address:this.ruleForm3.addrRow[o].list};s.push(i)}var n=[];if(this.ruleForm4)for(var m=0;m<this.ruleForm4.length;m++){var u={imgInstruction:"",type:this.ruleForm4[m].name0,prizeUnit:Number(this.ruleForm4[m].name1),prizeName:this.ruleForm4[m].name2,num:Number(this.ruleForm4[m].name3),goldtreePrizeImgReqs:[]};if("粉币"==u.type?u.type=1:"手机流量"==u.type?u.type=2:"手机话费"==u.type?u.type=3:"实体物品"==u.type?u.type=4:"积分"==u.type?u.type=6:"优惠券"==u.type&&(u.type=7),4==u.type)for(var c=0;c<this.ruleForm4[m].name5.length;c++){var d={imgUrl:this.ruleForm4[m].name5[c]};u.goldtreePrizeImgReqs.push(d)}n.push(u)}var p={id:this.$router.history.current.query.id,name:this.ruleForm1.name,activityBeginTime:this.ruleForm1.gtTime[0],activityEndTime:this.ruleForm1.gtTime[1],goldtreeAdReqs:t,followQrCode:this.ruleForm2.followQrCode,gameTime:this.ruleForm2.time,ingotMoney:this.ruleForm2.duihuan,freeTimes:this.ruleForm2.manTotalChance,dayFreeTimes:this.ruleForm2.manDayChance,gameFb:this.ruleForm2.fenbi,gameJf:this.ruleForm2.jifen,actRule:this.ruleForm2.desc,msgTemplateId:this.ruleForm2.msgTemplateId,isMsgTemplate:this.ruleForm2.isMsgTemplate,cashPrizeBeginTime:this.ruleForm3.date[0],cashPrizeEndTime:this.ruleForm3.date[1],receiveType:this.ruleForm3.type.toString(),phone:this.ruleForm3.phone,cashPrizeInstruction:this.ruleForm3.desc,goldtreeAddressReqs:s,prizeSetInstruction:this.explain,goldtreePrizeReqs:n};console.log(p,123),Object(a.n)(p).then(function(t){100==t.code?e.$message({message:"保存成功",type:"success"}):e.$message.error(t.msg)})},geteditData:function(){var e=this,t=this.$router.history.current.query.id;Object(a.c)(t).then(function(t){if(100==t.code){console.log(t,1233),e.ruleForm1.name=t.data.name,e.ruleForm1.gtTime=[t.data.activityBeginTime,t.data.activityEndTime];var r=[];if(0!=t.data.goldtreeAdReqs.length)for(var a=0;a<t.data.goldtreeAdReqs.length;a++){var l={url:t.data.goldtreeAdReqs[a].hrefUrl,imgUrl:window.IMAGEURL+t.data.goldtreeAdReqs[a].url};r.push(l)}e.ruleForm1.links=r,e.ruleForm2.followQrCode=window.IMAGEURL+t.data.followQrCode,e.ruleForm2.time=t.data.gameTime,e.ruleForm2.duihuan=t.data.ingotMoney,e.ruleForm2.manTotalChance=t.data.dayFreeTimes,e.ruleForm2.manDayChance=t.data.ingotMoney,e.ruleForm2.fenbi=t.data.gameFb,e.ruleForm2.jifen=t.data.gameJf,e.ruleForm2.desc=t.data.actRule,e.ruleForm2.msgTemplateId=t.data.msgTemplateId,e.ruleForm2.isMsgTemplate=t.data.isMsgTemplate,1==e.ruleForm2.isMsgTemplate&&(35==t.data.msgTemplateId?e.ruleForm2.msgTemplateId="中奖结果通知":36==t.data.msgTemplateId?e.ruleForm2.msgTemplateId="会员加入提醒":37==t.data.msgTemplateId?e.ruleForm2.msgTemplateId="积分变动提醒":38==t.data.msgTemplateId?e.ruleForm2.msgTemplateId="会员升级通知":39==t.data.msgTemplateId?e.ruleForm2.msgTemplateId="活动时间变更通知":40==t.data.msgTemplateId?e.ruleForm2.msgTemplateId="资金变动通知":41==t.data.msgTemplateId&&(e.ruleForm2.msgTemplateId="审核通过通知")),e.ruleForm3.date=[t.data.cashPrizeBeginTime,t.data.cashPrizeEndTime],e.ruleForm3.type=t.data.receiveType.split(","),e.ruleForm3.phone=t.data.phone,e.ruleForm3.desc=t.data.cashPrizeInstruction;var s=[];for(a=0;a<t.data.goldtreeAddressReqs.length;a++){l={list:t.data.goldtreeAddressReqs[a].address};s.push(l)}e.ruleForm3.addrRow=s,e.explain=t.data.prizeSetInstruction;var o=[];for(a=0;a<t.data.goldtreePrizeReqs.length;a++){if(1==(l={name0:t.data.goldtreePrizeReqs[a].type,name1:t.data.goldtreePrizeReqs[a].prizeUnit,name2:t.data.goldtreePrizeReqs[a].prizeName,name3:String(t.data.goldtreePrizeReqs[a].num),name5:[]}).name0?l.name0="粉币":2==l.name0?l.name0="手机流量":3==l.name0?l.name0="手机话费":4==l.name0?l.name0="实体物品":6==l.name0?l.name0="积分":7==l.name0&&(l.name0="优惠券"),"实体物品"==l.name0)for(var i=0;i<t.data.goldtreePrizeReqs[a].goldtreePrizeImgReqs.length;i++){var n={url:window.IMAGEURL+t.data.goldtreePrizeReqs[a].goldtreePrizeImgReqs[i].imgUrl};l.name5.push(n.url)}o.push(l)}e.ruleForm4=o,console.log(t.data.goldtreePrizeReqs[a].prizeUnit,9999)}else e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},backUrl:function(){window.history.go(-1)}},mounted:function(){this.fetchData(),this.getPrizeTypeData(),this.geteditData()}},s={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("section",[r("div",{staticClass:"hd-common"},[r("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[r("el-breadcrumb-item",[e._v("互动游戏")]),e._v(" "),r("el-breadcrumb-item",{attrs:{to:{path:"/cashTree/index"}}},[e._v("摇钱树")]),e._v(" "),r("el-breadcrumb-item",[e._v("编辑活动")])],1),e._v(" "),r("div",{staticClass:"gt-content"},[r("el-tabs",{attrs:{type:"card"},model:{value:e.active,callback:function(t){e.active=t},expression:"active"}},[r("el-tab-pane",{attrs:{label:"基础设置",name:"0"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"规则设置",name:"1"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"兑奖设置",name:"2"}}),e._v(" "),r("el-tab-pane",{attrs:{label:"奖项设置",name:"3"}})],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:0==this.active,expression:"this.active==0"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm1",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"活动名称：",prop:"name"}},[r("el-input",{staticClass:"w_demo",model:{value:e.ruleForm1.name,callback:function(t){e.$set(e.ruleForm1,"name",t)},expression:"ruleForm1.name"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏时间：",prop:"gtTime"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{type:"datetimerange",placeholder:"选择时间范围"},model:{value:e.ruleForm1.gtTime,callback:function(t){e.$set(e.ruleForm1,"gtTime",t)},expression:"ruleForm1.gtTime"}})],1),e._v(" "),r("h1",{staticClass:"mt30 mb20 pb10 bbtom"},[e._v("广告设置")]),e._v(" "),r("el-button",{staticClass:"mb20",attrs:{type:"primary",disabled:this.ruleForm1.links.length>2},on:{click:function(t){e.addlinks()}}},[e._v("新增")]),e._v(" "),r("span",{staticClass:"ml10 el-upload__tip grey mr10"},[e._v("1.仅支持多粉与翼店开头的链接")]),e._v(" "),r("span",{staticClass:"ml10 el-upload__tip grey mr10"},[e._v("2.广告图格式：1000*300px")]),e._v(" "),r("span",{staticClass:"ml10 el-upload__tip grey mr10"},[e._v("3.最多添加3个广告")]),e._v(" "),r("el-table",{ref:"multipleTable",staticStyle:{"max-width":"1000px"},attrs:{data:e.ruleForm1.links,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"广告链接"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{class:{"bd1px-red":e.checked&&!t.row.url},model:{value:t.row.url,callback:function(r){e.$set(t.row,"url",r)},expression:"scope.row.url"}},[r("template",{slot:"prepend"},[e._v("Http://")])],2)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"选择图片"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("gt-material",{class:{"bd1px-red":e.checked&&!t.row.imgUrl},attrs:{prop:"url",url:t.row.imgUrl,width:"60",height:"60"},on:{getChangeUrl:function(r){e.getChangeUrl(t.$index,r)}}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delLinks(t.$index)}}},[e._v("删除")])]}}])})],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:1==this.active,expression:"this.active==1"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm2",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"150px"}},[r("el-form-item",{attrs:{label:"关注二维码：",prop:"followQrCode"}},[r("gt-material",{attrs:{prop:"url",url:e.ruleForm2.followQrCode,width:"72",height:"72"},on:{getChangeUrl:e.getChangeUrl2}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("上传1:1二维码，将会在活动规则中显示商家二维码")])],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏时长：",prop:"time"}},[r("el-input",{staticClass:"w_demo",attrs:{type:"number",placeholder:"请输入游戏时长"},model:{value:e.ruleForm2.time,callback:function(t){e.$set(e.ruleForm2,"time",t)},expression:"ruleForm2.time"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("设置时间为10~30秒")])],1),e._v(" "),r("el-form-item",{attrs:{label:"一元宝兑换：",prop:"duihuan"}},[r("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入元宝兑换金币比例"},model:{value:e.ruleForm2.duihuan,callback:function(t){e.$set(e.ruleForm2,"duihuan",t)},expression:"ruleForm2.duihuan"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"免费游戏：",prop:"manTotalChance"}},[r("el-input",{staticClass:"w25_demo mr10",model:{value:e.ruleForm2.manTotalChance,callback:function(t){e.$set(e.ruleForm2,"manTotalChance",t)},expression:"ruleForm2.manTotalChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"免费次数：",prop:"manDayChance"}},[r("el-input",{staticClass:"w25_demo mr10",model:{value:e.ruleForm2.manDayChance,callback:function(t){e.$set(e.ruleForm2,"manDayChance",t)},expression:"ruleForm2.manDayChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏消耗：",prop:"fenbi"}},[r("el-input",{staticClass:"w25_demo mr10",model:{value:e.ruleForm2.fenbi,callback:function(t){e.$set(e.ruleForm2,"fenbi",t)},expression:"ruleForm2.fenbi"}}),e._v("粉币\r\n                    "),r("span",{staticClass:"ml20 mr20"},[e._v(" 或")]),e._v(" "),r("el-input",{staticClass:"w25_demo ml10 mr10",model:{value:e.ruleForm2.jifen,callback:function(t){e.$set(e.ruleForm2,"jifen",t)},expression:"ruleForm2.jifen"}}),e._v("积分\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"中奖消息：",prop:"isMsgTemplate"}},[r("el-switch",{attrs:{"on-text":"开启","on-value":1,"off-text":"关闭","off-value":0},model:{value:e.ruleForm2.isMsgTemplate,callback:function(t){e.$set(e.ruleForm2,"isMsgTemplate",t)},expression:"ruleForm2.isMsgTemplate"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey"},[r("el-tooltip",{attrs:{placement:"right",effect:"light"}},[r("div",{attrs:{slot:"content"},slot:"content"},[e._v("\r\n                            1.开通消息模块功能;2.公众号平台添加消\r\n                            "),r("br"),e._v("息模块;3.同步模块到多粉平台;4.选取相\r\n                            "),r("br"),e._v("应的消息模块；如有问题,请联系客服！\r\n                        ")]),e._v(" "),r("span",{staticClass:"el-icon-warning  ml10",staticStyle:{"font-size":"16px",position:"absolute",top:"11px"}})])],1)],1),e._v(" "),1==e.ruleForm2.isMsgTemplate?r("div",{staticClass:"bb bw pt20 mb20 ml150"},[r("el-form-item",{staticClass:"Prompt",attrs:{prop:"msgTemplateId","label-width":"10px"}},[r("el-select",{attrs:{placeholder:"请选择模块"},model:{value:e.ruleForm2.msgTemplateId,callback:function(t){e.$set(e.ruleForm2,"msgTemplateId",t)},expression:"ruleForm2.msgTemplateId"}},e._l(e.textArray,function(e,t){return r("el-option",{key:t,attrs:{label:e.title,value:e.id}})}))],1)],1):e._e(),e._v(" "),r("el-form-item",{attrs:{label:"活动规则：",prop:"desc"}},[r("el-input",{staticClass:"w_demo",attrs:{maxlength:300,type:"textarea",rows:3,placeholder:"请填写活动规则"},model:{value:e.ruleForm2.desc,callback:function(t){e.$set(e.ruleForm2,"desc",t)},expression:"ruleForm2.desc"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("300个字以内")])],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:2==this.active,expression:"this.active==2"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm3",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"兑奖时间：",prop:"date"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{"picker-options":e.pickerOptions,type:"daterange",placeholder:"选择日期范围"},model:{value:e.ruleForm3.date,callback:function(t){e.$set(e.ruleForm3,"date",t)},expression:"ruleForm3.date"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖方式：",prop:"type"}},[r("el-checkbox-group",{model:{value:e.ruleForm3.type,callback:function(t){e.$set(e.ruleForm3,"type",t)},expression:"ruleForm3.type"}},[r("el-checkbox",{attrs:{label:"1",name:"1"}},[e._v("到店领取")]),e._v(" "),r("el-checkbox",{attrs:{label:"2",name:"2"}},[e._v("邮寄")])],1)],1),e._v(" "),1==this.ruleForm3.type[0]||1==this.ruleForm3.type[1]?r("div",{staticClass:"bb pt20 pb20 ml120 bw mb10"},[r("span",{staticStyle:{"margin-left":"30px",color:"#333"}},[r("span",{staticStyle:{color:"#ff4949","margin-right":"3px"}},[e._v("*")]),e._v("兑奖地址：")]),e._v(" "),r("el-button",{staticClass:"mb10",attrs:{type:"primary"},on:{click:function(t){e.addList()}}},[e._v("添加")]),e._v(" "),e._l(e.ruleForm3.addrRow,function(t,a){return r("el-form-item",{key:t.key,attrs:{prop:"addrRow."+a+".list",rules:{required:!0,validator:e.addrPass,trigger:"blur"}}},[r("el-input",{staticClass:"w_demo mr10",attrs:{prop:"list",placeholder:"请输入到店领取地址"},model:{value:t.list,callback:function(r){e.$set(t,"list",r)},expression:"item.list"}}),e._v(" "),0!=a?r("span",{staticClass:"blueee",on:{click:function(t){e.delList(a)}}},[e._v("删除")]):e._e()],1)})],2):e._e(),e._v(" "),r("el-form-item",{attrs:{label:"联系电话：",prop:"phone"}},[r("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入联系电话(固话用-分割)"},model:{value:e.ruleForm3.phone,callback:function(t){e.$set(e.ruleForm3,"phone",t)},expression:"ruleForm3.phone"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖说明：",prop:"desc"}},[r("el-input",{staticClass:"bw",attrs:{type:"textarea",rows:5,placeholder:"请输入兑奖说明"},model:{value:e.ruleForm3.desc,callback:function(t){e.$set(e.ruleForm3,"desc",t)},expression:"ruleForm3.desc"}})],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:3==this.active,expression:"this.active==3"}],staticClass:"mt40"},[r("div",[r("span",{staticStyle:{color:"#333",position:"absolute","margin-top":"0px"}},[e._v("设置奖品说明：")]),e._v(" "),r("el-input",{staticClass:"bw ml120",attrs:{type:"textarea",maxlength:300,rows:3,placeholder:"请输入兑奖说明"},model:{value:e.explain,callback:function(t){e.explain=t},expression:"explain"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("300字以内")])],1),e._v(" "),e._m(0),e._v(" "),r("div",{staticClass:"mt20 mb20"},[r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addForm4()}}},[e._v("新增奖品")]),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("下列奖品根据排名由上至下顺序分配")])],1),e._v(" "),r("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm4,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"奖品类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-select",{attrs:{placeholder:"请选择"},model:{value:t.row.name0,callback:function(r){e.$set(t.row,"name0",r)},expression:"scope.row.name0"}},e._l(e.options,function(e){return r("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}))]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品单位"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.name1,callback:function(r){e.$set(t.row,"name1",r)},expression:"scope.row.name1"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品名称"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{placeholder:"请输入奖品名称"},model:{value:t.row.name2,callback:function(r){e.$set(t.row,"name2",r)},expression:"scope.row.name2"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖项数量"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.name3,callback:function(r){e.$set(t.row,"name3",r)},expression:"scope.row.name3"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品图片"},scopedSlots:e._u([{key:"default",fn:function(t){return 4==t.row.name0||"实体物品"==t.row.name0?[e._l(t.row.name5,function(a,l){return r("gt-material",{key:l,staticClass:"mr10",attrs:{prop:t,sonIndex:l,selectType:"radio",url:a,width:"50",height:"50"},on:{getChangeUrl:e.getAwardImgList}})}),e._v(" "),r("gt-material",{staticClass:"uploadBtn",attrs:{prop:t,selectType:"select",width:"50",height:"50"},on:{getChangeUrl:e.addAwardImg}})]:void 0}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[0!=t.$index?r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delForm4(t.$index)}}},[e._v("删除")]):e._e()]}}])})],1)],1),e._v(" "),r("div",{staticClass:"h80"}),e._v(" "),5!=this.active?r("div",{staticClass:"btnRow"},[r("el-button",{on:{click:function(t){e.backUrl()}}},[e._v("返回")]),e._v(" "),0==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.submit()}}},[e._v("保存")]):e._e(),e._v(" "),1==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.submit()}}},[e._v("保存")]):e._e(),e._v(" "),2==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm3")}}},[e._v("保存")]):e._e(),e._v(" "),3==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.lastStep()}}},[e._v("保存")]):e._e()],1):e._e()],1)],1)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"gt-gray-region mt20",staticStyle:{color:"#666","line-height":"20px"}},[t("p",[this._v("奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数")]),this._v(" "),t("p",[this._v("如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12")]),this._v(" "),t("p",[this._v("当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px")])])}]};var o=r("VU/8")(l,s,!1,function(e){r("S/sk")},null,null);t.default=o.exports}});