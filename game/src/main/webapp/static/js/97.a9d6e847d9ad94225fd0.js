webpackJsonp([97],{sKzt:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=r("hDEg"),l={data:function(){var e=this;return{active:0,isSubmit:!1,ruleForm1:{name:"",name1:"",links:[{url:"",imgUrl:""},{url:"",imgUrl:""}]},rules1:{name:[{required:!0,message:"活动名称不能为空",trigger:"blur"}],name1:[{required:!0,type:"array",message:"游戏时间不能为空",trigger:"blur"}]},ruleForm2:{code:"",manTotalChance:"",manDayChance:"",desc:""},rules2:{manTotalChance:[{required:!0,message:"请填写每人免费游戏次数",trigger:"blur"}],manDayChance:[{required:!0,message:"请填写每人每天免费游戏次数",trigger:"blur"}],desc:[{required:!0,message:"请填写活动规则",trigger:"blur"}]},ruleForm3:{date:"",type:[],addrRow:[{list:""},{list:""}],phone:"",desc:""},rules3:{list:[{required:!0}],date:[{required:!0,type:"array",message:"兑奖时间不能为空"}],type:[{required:!0,type:"array",message:"兑奖方式不能为空",trigger:"blur"}],phone:[{required:!0,type:"text",validator:function(t,r,a){return e.ruleForm3.phone?/^1[34578]\d{9}$/.test(r)||/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(r)?void a():a("联系电话有误，请重填"):a(new Error("联系电话不能为空"))},trigger:"blur"}],desc:[{required:!0,message:"兑奖说明不能为空",trigger:"blur"}]},options:[],memberOptions:[],ruleForm4:[{name0:"",name1:"",name2:"",name3:"",name4:"",name5:[],cardsName:""},{name0:"",name1:"",name2:"",name3:"",name4:"",name5:[],cardsName:""}],pickerOptions:{disabledDate:function(e){return e.getTime()<Date.now()-864e5}}}},methods:{optionsData:function(e){for(var t=0;t<this.memberOptions.length;t++)this.memberOptions[t].id==this.ruleForm4[e].name2&&(this.ruleForm4[e].cardsName=this.memberOptions[t].cardsName)},addrPass:function(e,t,r){t?r():r(new Error("到店领取地址不能为空"))},phoneCheck:function(e,t,r){t?t.match(/^1[3|4|5|6|7|8][0-9][0-9]{8}$/)?r():r(new Error("请输入正确的手机号码")):r(new Error("联系电话不能为空"))},addList:function(){this.ruleForm3.addrRow.push({list:""})},delList:function(e){this.ruleForm3.addrRow.splice(e,1)},upStep:function(){this.active--},addlinks:function(){this.ruleForm1.links.push({url:"",imgUrl:""})},delLinks:function(e){this.ruleForm1.links.splice(e,1)},getChangeUrl:function(e,t){this.ruleForm1.links[e].imgUrl=t.url},getChangeUrl2:function(e){this.ruleForm2.code=e.url},addAwardImg:function(e){JSON.parse(e.url).forEach(function(t,r,a){this.ruleForm4[e.prop.$index].name5.push(t.url)},this)},getAwardImgList:function(e){e.url||this.ruleForm4[e.prop.$index].name5.splice(e.sonIndex,1)},addForm4:function(){this.ruleForm4.push({name0:"",name1:"",name2:"",name3:"",name5:[],cardsName:""})},delForm4:function(e){this.ruleForm4.splice(e,1)},next:function(e){var t=this;this.$refs[e].validate(function(e){e?t.active++:console.log("error submit!!")})},lastStep:function(){console.log(this.ruleForm4,1243);for(var e=0;e<this.ruleForm4.length;e++){var t=/^[1-9]\d*$/;if(!(this.ruleForm4[e].name0&&this.ruleForm4[e].name1&&this.ruleForm4[e].name2&&this.ruleForm4[e].name3))return this.$message.error("表单不能留空，请填写完整~"),!1;if(!t.test(this.ruleForm4[e].name1))return this.$message.error("奖品单位填写有误，请重新填写~"),!1;if(!t.test(this.ruleForm4[e].name3))return this.$message.error("奖项数量填写有误，请重新填写~"),!1;if(4==this.ruleForm4[e].name0&&0==this.ruleForm4[e].name5.length)return this.$message.error("当奖品为实物时，请上传实物图片~"),!1;if(4==this.ruleForm4[e].name0&&this.ruleForm4[e].name5.length>5)return this.$message.error("实物图片最多上传5张~"),!1;this.ruleForm4[e].name4=parseFloat(this.ruleForm4[e].name4).toFixed(2)}this.submit()},submit:function(){for(var e=this,t=[],r=0;r<this.ruleForm1.links.length;r++){var l={hrefUrl:this.ruleForm1.links[r].url,url:this.ruleForm1.links[r].imgUrl};t.push(l)}var s=[];if(this.ruleForm3.addrRow)for(var i=0;i<this.ruleForm3.addrRow.length;i++){var o={address:this.ruleForm3.addrRow[i].list};s.push(o)}var n=[];if(this.ruleForm4)for(var m=0;m<this.ruleForm4.length;m++){var c={imgInstruction:"",type:this.ruleForm4[m].name0,prizeUnit:Number(this.ruleForm4[m].name1),prizeName:this.ruleForm4[m].name2,num:Number(this.ruleForm4[m].name3),cardReceiveId:"",qixiPrizeImgReqs:[]};if(7==c.type&&(c.cardReceiveId=this.ruleForm4[m].name2,c.prizeName=this.ruleForm4[m].cardsName),4==c.type)for(var u=0;u<this.ruleForm4[m].name5.length;u++){var p={imgUrl:this.ruleForm4[m].name5[u]};c.qixiPrizeImgReqs.push(p)}n.push(c)}var d={id:0,name:this.ruleForm1.name,activityBeginTime:this.ruleForm1.name1[0],activityEndTime:this.ruleForm1.name1[1],qixiAdReqs:t,followQrCode:this.ruleForm2.code,manTotalChance:Number(this.ruleForm2.manTotalChance),manDayChance:Number(this.ruleForm2.manDayChance),actRule:this.ruleForm2.desc,cashPrizeBeginTime:this.ruleForm3.date[0],cashPrizeEndTime:this.ruleForm3.date[1],receiveType:this.ruleForm3.type.toString(),phone:this.ruleForm3.phone,cashPrizeInstruction:this.ruleForm3.desc,qixiAddressReqs:s,qixiPrizeReqs:n};console.log(d,123),console.log(d.qixiPrizeReqs,66666),Object(a.n)(d).then(function(t){e.isSubmit=!0,100==t.code?e.active=5:(e.isSubmit=!1,e.$message.error(t.msg))}).catch(function(){e.isSubmit=!1,e.$message({type:"info",message:"网络问题，请刷新重试~"})})},backUrl:function(){window.history.go(-1)},test:function(){console.log(1122)},getPrizeTypeData:function(){var e=this;Object(a.i)().then(function(t){100==t.code?(console.log(t,1233),e.options=t.data,console.log(e.options,"获取奖品类型")):e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})},getMemberTypeData:function(){var e=this;Object(a.f)().then(function(t){100==t.code?e.memberOptions=t.data:e.$message.error(t.msg)}).catch(function(){e.$message({type:"info",message:"网络问题，请刷新重试~"})})}},mounted:function(){this.getPrizeTypeData(),this.getMemberTypeData()}},s={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("section",[r("div",{staticClass:"hd-common"},[r("el-breadcrumb",{staticClass:"gt-crumbs",attrs:{separator:"/"}},[r("el-breadcrumb-item",{nativeOn:{click:function(t){e.$util.ClickApply(t)}}},[e._v("互动游戏")]),e._v(" "),r("el-breadcrumb-item",{attrs:{to:{path:"/romanceValentine/index"}}},[e._v("浪漫七夕")]),e._v(" "),r("el-breadcrumb-item",[e._v("创建活动")])],1),e._v(" "),r("div",{staticClass:"gt-content"},[r("el-steps",{staticClass:"bbtom pb20",attrs:{active:e.active,center:!0,"align-center":!0}},[r("el-step",{attrs:{title:"基础设置"}}),e._v(" "),r("el-step",{attrs:{title:"规则设置"}}),e._v(" "),r("el-step",{attrs:{title:"兑奖设置"}}),e._v(" "),r("el-step",{attrs:{title:"奖项设置"}}),e._v(" "),r("el-step",{attrs:{title:"新建完成"}})],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:0==this.active,expression:"this.active==0"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm1",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"活动名称：",prop:"name"}},[r("el-input",{staticClass:"w_demo",model:{value:e.ruleForm1.name,callback:function(t){e.$set(e.ruleForm1,"name",t)},expression:"ruleForm1.name"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏时间：",prop:"name1"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{"picker-options":e.pickerOptions,type:"datetimerange",placeholder:"请选择游戏时间范围"},model:{value:e.ruleForm1.name1,callback:function(t){e.$set(e.ruleForm1,"name1",t)},expression:"ruleForm1.name1"}})],1),e._v(" "),r("h1",{staticClass:"mt30 mb20 pb10 bbtom"},[e._v("广告设置")]),e._v(" "),r("el-button",{staticClass:"mb20",attrs:{type:"primary"},on:{click:function(t){e.addlinks()}}},[e._v("新增")]),e._v(" "),r("span",{staticClass:"ml10 el-upload__tip grey"},[e._v("1.仅支持多粉与翼粉开头的链接    2.广告图格式：1000*300px")]),e._v(" "),r("el-table",{ref:"multipleTable",staticStyle:{"max-width":"1000px"},attrs:{data:e.ruleForm1.links,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"广告链接"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{model:{value:t.row.url,callback:function(r){e.$set(t.row,"url",r)},expression:"scope.row.url"}},[r("template",{slot:"prepend"},[e._v("Http://")])],2)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"选择图片"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("gt-material",{attrs:{prop:"url",url:t.row.imgUrl,width:"60",height:"60"},on:{getChangeUrl:function(r){e.getChangeUrl(t.$index,r)}}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delLinks(t.$index)}}},[e._v("删除")])]}}])})],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:1==this.active,expression:"this.active==1"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm2",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"150px"}},[r("el-form-item",{attrs:{label:"关注二维码：",prop:"code"}},[r("gt-material",{attrs:{prop:"url",url:e.ruleForm2.code,width:"72",height:"72"},on:{getChangeUrl:e.getChangeUrl2}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("上传1:1二维码，将会在活动规则中显示商家二维码")])],1),e._v(" "),r("el-form-item",{attrs:{label:"游戏总数：",prop:"manTotalChance"}},[r("el-input",{staticClass:"w25_demo mr10",attrs:{type:"number",placeholder:"请输入游戏总数"},model:{value:e.ruleForm2.manTotalChance,callback:function(t){e.$set(e.ruleForm2,"manTotalChance",t)},expression:"ruleForm2.manTotalChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"每天次数：",prop:"manDayChance"}},[r("el-input",{staticClass:"w25_demo mr10",attrs:{type:"number",placeholder:"请输入每天游戏次数"},model:{value:e.ruleForm2.manDayChance,callback:function(t){e.$set(e.ruleForm2,"manDayChance",t)},expression:"ruleForm2.manDayChance"}}),e._v("次/人\r\n                ")],1),e._v(" "),r("el-form-item",{attrs:{label:"活动规则：",prop:"desc"}},[r("el-input",{staticClass:"w_demo",attrs:{maxlength:300,type:"textarea",rows:3,placeholder:"请填写活动规则"},model:{value:e.ruleForm2.desc,callback:function(t){e.$set(e.ruleForm2,"desc",t)},expression:"ruleForm2.desc"}}),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("300个字以内")])],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:2==this.active,expression:"this.active==2"}],staticClass:"mt40"},[r("el-form",{ref:"ruleForm3",staticClass:"mt40 demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"兑奖时间：",prop:"date"}},[r("el-date-picker",{staticClass:"w_demo",attrs:{"picker-options":e.pickerOptions,type:"daterange",placeholder:"选择日期范围"},model:{value:e.ruleForm3.date,callback:function(t){e.$set(e.ruleForm3,"date",t)},expression:"ruleForm3.date"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖方式：",prop:"type"}},[r("el-checkbox-group",{model:{value:e.ruleForm3.type,callback:function(t){e.$set(e.ruleForm3,"type",t)},expression:"ruleForm3.type"}},[r("el-checkbox",{attrs:{label:"1",name:"1"}},[e._v("到店领取")]),e._v(" "),r("el-checkbox",{attrs:{label:"2",name:"2"}},[e._v("邮寄")])],1)],1),e._v(" "),1==this.ruleForm3.type[0]||1==this.ruleForm3.type[1]?r("div",{staticClass:"bb pt20 pb20 ml120 bw mb10"},[r("span",{staticStyle:{"margin-left":"30px",color:"#333"}},[r("span",{staticStyle:{color:"#ff4949","margin-right":"3px"}},[e._v("*")]),e._v("兑奖地址：")]),e._v(" "),r("el-button",{staticClass:"mb10",attrs:{type:"primary"},on:{click:function(t){e.addList()}}},[e._v("添加")]),e._v(" "),e._l(e.ruleForm3.addrRow,function(t,a){return r("el-form-item",{key:t.key,attrs:{prop:"addrRow."+a+".list",rules:{required:!0,validator:e.addrPass,trigger:"blur"}}},[r("el-input",{staticClass:"w_demo mr10",attrs:{prop:"list",placeholder:"请输入到店领取地址"},model:{value:t.list,callback:function(r){e.$set(t,"list",r)},expression:"item.list"}}),e._v(" "),0!=a?r("span",{staticClass:"blueee",on:{click:function(t){e.delList(a)}}},[e._v("删除")]):e._e()],1)})],2):e._e(),e._v(" "),r("el-form-item",{attrs:{label:"联系电话：",prop:"phone"}},[r("el-input",{staticClass:"w_demo",attrs:{placeholder:"请输入联系电话(固话用-分割)"},model:{value:e.ruleForm3.phone,callback:function(t){e.$set(e.ruleForm3,"phone",t)},expression:"ruleForm3.phone"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"兑奖说明：",prop:"desc"}},[r("el-input",{staticClass:"bw",attrs:{type:"textarea",rows:5,placeholder:"请输入兑奖说明"},model:{value:e.ruleForm3.desc,callback:function(t){e.$set(e.ruleForm3,"desc",t)},expression:"ruleForm3.desc"}})],1)],1)],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:3==this.active,expression:"this.active==3"}],staticClass:"mt40"},[e._m(0),e._v(" "),r("div",{staticClass:"mt20 mb20"},[r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addForm4()}}},[e._v("新增奖品")]),e._v(" "),r("span",{staticClass:"el-upload__tip grey ml10"},[e._v("下列奖品根据排名由上至下顺序分配")])],1),e._v(" "),r("el-tooltip",{attrs:{placement:"top",effect:"light"}},[r("div",{attrs:{slot:"content"},slot:"content"},[e._v("\r\n                    当奖品为实物时，请上传实物图片\r\n                ")]),e._v(" "),r("span",{staticClass:"el-icon-warning",staticStyle:{"font-size":"18px","margin-left":"67%","z-index":"11",position:"absolute","margin-top":"12px",color:"#ccc"}})]),e._v(" "),r("el-table",{ref:"multipleTable",attrs:{data:e.ruleForm4,"tooltip-effect":"dark"}},[r("el-table-column",{attrs:{label:"奖品类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-select",{attrs:{placeholder:"请选择"},model:{value:t.row.name0,callback:function(r){e.$set(t.row,"name0",r)},expression:"scope.row.name0"}},e._l(e.options,function(e){return r("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})}))]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品单位"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.name1,callback:function(r){e.$set(t.row,"name1",r)},expression:"scope.row.name1"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品名称"},scopedSlots:e._u([{key:"default",fn:function(t){return[7==t.row.name0?r("el-select",{attrs:{placeholder:"请选择"},on:{change:function(r){e.optionsData(t.$index)}},model:{value:t.row.name2,callback:function(r){e.$set(t.row,"name2",r)},expression:"scope.row.name2"}},e._l(e.memberOptions,function(e){return r("el-option",{key:e.id,attrs:{label:e.cardsName,value:e.id}})})):r("el-input",{staticClass:"w20_demo",model:{value:t.row.name2,callback:function(r){e.$set(t.row,"name2",r)},expression:"scope.row.name2"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖项数量"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-input",{staticClass:"w20_demo",attrs:{type:"number",placeholder:"数值应大于0"},model:{value:t.row.name3,callback:function(r){e.$set(t.row,"name3",r)},expression:"scope.row.name3"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"奖品图片"},scopedSlots:e._u([{key:"default",fn:function(t){return 4==t.row.name0?[e._l(t.row.name5,function(a,l){return r("gt-material",{key:l,staticClass:"mr10",attrs:{prop:t,sonIndex:l,selectType:"radio",url:a,width:"50",height:"50"},on:{getChangeUrl:e.getAwardImgList}})}),e._v(" "),r("gt-material",{staticClass:"uploadBtn",attrs:{prop:t,selectType:"select",width:"50",height:"50"},on:{getChangeUrl:e.addAwardImg}})]:void 0}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[0!=t.$index?r("el-button",{staticClass:"gt-button-normal",on:{click:function(r){e.delForm4(t.$index)}}},[e._v("删除")]):e._e()]}}])})],1)],1),e._v(" "),5==e.active?r("div",{staticClass:"gt-content complete"},[r("div",{staticClass:"addOk"},[r("div",{staticClass:"el-icon-circle-check green",staticStyle:{"font-size":"40px"}}),e._v(" "),r("div",{staticClass:"complete-info"},[e._v("活动添加成功")]),e._v(" "),r("el-button",{staticClass:"mt80",attrs:{type:"primary"},on:{click:function(t){e.backUrl()}}},[e._v("返回活动列表")])],1)]):e._e(),e._v(" "),r("div",{staticClass:"h80"}),e._v(" "),5!=this.active?r("div",{staticClass:"btnRow"},[0!=this.active?r("el-button",{on:{click:function(t){e.upStep()}}},[e._v("上一步")]):e._e(),e._v(" "),0==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm1")}}},[e._v("下一步")]):e._e(),e._v(" "),1==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm2")}}},[e._v("下一步")]):e._e(),e._v(" "),2==this.active?r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.next("ruleForm3")}}},[e._v("下一步")]):e._e(),e._v(" "),3==this.active?r("el-button",{attrs:{type:"primary",disabled:e.isSubmit},on:{click:function(t){e.lastStep()}}},[e._v("保存")]):e._e(),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:function(t){e.submit()}}},[e._v("打印")])],1):e._e()],1)],1)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"gt-gray-region mt20",staticStyle:{color:"#666","line-height":"20px"}},[t("p",[this._v("奖品类型：奖品的内容;奖品单位：奖品的数量货内容；奖项数量:该奖品的可领取次数")]),this._v(" "),t("p",[this._v("如：奖品类型：粉币；奖品数额：2；奖项名称：粉币；奖项数量：3；中奖概率：12")]),this._v(" "),t("p",[this._v("当奖品为实物时，请上传实物图片，实物图片建议尺寸1160px*64px")])])}]},i=r("VU/8")(l,s,!1,null,null,null);t.default=i.exports}});