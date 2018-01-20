webpackJsonp([4],{"Bj/7":function(t,e,n){var o=n("iDEd"),r=n("ZE5A");t.exports=function(t,e,n){if(!t&&!e&&!n)throw new Error("Missing required arguments");if(!o.string(e))throw new TypeError("Second argument must be a String");if(!o.fn(n))throw new TypeError("Third argument must be a Function");if(o.node(t))return d=e,p=n,(f=t).addEventListener(d,p),{destroy:function(){f.removeEventListener(d,p)}};if(o.nodeList(t))return c=t,s=e,u=n,Array.prototype.forEach.call(c,function(t){t.addEventListener(s,u)}),{destroy:function(){Array.prototype.forEach.call(c,function(t){t.removeEventListener(s,u)})}};if(o.string(t))return i=t,a=e,l=n,r(document.body,i,a,l);throw new TypeError("First argument must be a String, HTMLElement, HTMLCollection, or NodeList");var i,a,l,c,s,u,f,d,p}},Jssu:function(t,e){var n=9;if("undefined"!=typeof Element&&!Element.prototype.matches){var o=Element.prototype;o.matches=o.matchesSelector||o.mozMatchesSelector||o.msMatchesSelector||o.oMatchesSelector||o.webkitMatchesSelector}t.exports=function(t,e){for(;t&&t.nodeType!==n;){if("function"==typeof t.matches&&t.matches(e))return t;t=t.parentNode}}},"LF/X":function(t,e,n){var o,r,i,a;a=function(t,e){"use strict";var n,o=(n=e)&&n.__esModule?n:{default:n};var r="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t};var i=function(){function t(t,e){for(var n=0;n<e.length;n++){var o=e[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(t,o.key,o)}}return function(e,n,o){return n&&t(e.prototype,n),o&&t(e,o),e}}(),a=function(){function t(e){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,t),this.resolveOptions(e),this.initSelection()}return i(t,[{key:"resolveOptions",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.action=t.action,this.container=t.container,this.emitter=t.emitter,this.target=t.target,this.text=t.text,this.trigger=t.trigger,this.selectedText=""}},{key:"initSelection",value:function(){this.text?this.selectFake():this.target&&this.selectTarget()}},{key:"selectFake",value:function(){var t=this,e="rtl"==document.documentElement.getAttribute("dir");this.removeFake(),this.fakeHandlerCallback=function(){return t.removeFake()},this.fakeHandler=this.container.addEventListener("click",this.fakeHandlerCallback)||!0,this.fakeElem=document.createElement("textarea"),this.fakeElem.style.fontSize="12pt",this.fakeElem.style.border="0",this.fakeElem.style.padding="0",this.fakeElem.style.margin="0",this.fakeElem.style.position="absolute",this.fakeElem.style[e?"right":"left"]="-9999px";var n=window.pageYOffset||document.documentElement.scrollTop;this.fakeElem.style.top=n+"px",this.fakeElem.setAttribute("readonly",""),this.fakeElem.value=this.text,this.container.appendChild(this.fakeElem),this.selectedText=(0,o.default)(this.fakeElem),this.copyText()}},{key:"removeFake",value:function(){this.fakeHandler&&(this.container.removeEventListener("click",this.fakeHandlerCallback),this.fakeHandler=null,this.fakeHandlerCallback=null),this.fakeElem&&(this.container.removeChild(this.fakeElem),this.fakeElem=null)}},{key:"selectTarget",value:function(){this.selectedText=(0,o.default)(this.target),this.copyText()}},{key:"copyText",value:function(){var t=void 0;try{t=document.execCommand(this.action)}catch(e){t=!1}this.handleResult(t)}},{key:"handleResult",value:function(t){this.emitter.emit(t?"success":"error",{action:this.action,text:this.selectedText,trigger:this.trigger,clearSelection:this.clearSelection.bind(this)})}},{key:"clearSelection",value:function(){this.trigger&&this.trigger.focus(),window.getSelection().removeAllRanges()}},{key:"destroy",value:function(){this.removeFake()}},{key:"action",set:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"copy";if(this._action=t,"copy"!==this._action&&"cut"!==this._action)throw new Error('Invalid "action" value, use either "copy" or "cut"')},get:function(){return this._action}},{key:"target",set:function(t){if(void 0!==t){if(!t||"object"!==(void 0===t?"undefined":r(t))||1!==t.nodeType)throw new Error('Invalid "target" value, use a valid Element');if("copy"===this.action&&t.hasAttribute("disabled"))throw new Error('Invalid "target" attribute. Please use "readonly" instead of "disabled" attribute');if("cut"===this.action&&(t.hasAttribute("readonly")||t.hasAttribute("disabled")))throw new Error('Invalid "target" attribute. You can\'t cut text from elements with "readonly" or "disabled" attributes');this._target=t}},get:function(){return this._target}}]),t}();t.exports=a},r=[t,n("SPM9")],void 0===(i="function"==typeof(o=a)?o.apply(e,r):o)||(t.exports=i)},SPM9:function(t,e){t.exports=function(t){var e;if("SELECT"===t.nodeName)t.focus(),e=t.value;else if("INPUT"===t.nodeName||"TEXTAREA"===t.nodeName){var n=t.hasAttribute("readonly");n||t.setAttribute("readonly",""),t.select(),t.setSelectionRange(0,t.value.length),n||t.removeAttribute("readonly"),e=t.value}else{t.hasAttribute("contenteditable")&&t.focus();var o=window.getSelection(),r=document.createRange();r.selectNodeContents(t),o.removeAllRanges(),o.addRange(r),e=o.toString()}return e}},V33R:function(t,e,n){var o,r,i,a;a=function(t,e,n,o){"use strict";var r=l(e),i=l(n),a=l(o);function l(t){return t&&t.__esModule?t:{default:t}}var c="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t};var s=function(){function t(t,e){for(var n=0;n<e.length;n++){var o=e[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(t,o.key,o)}}return function(e,n,o){return n&&t(e.prototype,n),o&&t(e,o),e}}();var u=function(t){function e(t,n){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,e);var o=function(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}(this,(e.__proto__||Object.getPrototypeOf(e)).call(this));return o.resolveOptions(n),o.listenClick(t),o}return function(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}(e,i.default),s(e,[{key:"resolveOptions",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.action="function"==typeof t.action?t.action:this.defaultAction,this.target="function"==typeof t.target?t.target:this.defaultTarget,this.text="function"==typeof t.text?t.text:this.defaultText,this.container="object"===c(t.container)?t.container:document.body}},{key:"listenClick",value:function(t){var e=this;this.listener=(0,a.default)(t,"click",function(t){return e.onClick(t)})}},{key:"onClick",value:function(t){var e=t.delegateTarget||t.currentTarget;this.clipboardAction&&(this.clipboardAction=null),this.clipboardAction=new r.default({action:this.action(e),target:this.target(e),text:this.text(e),container:this.container,trigger:e,emitter:this})}},{key:"defaultAction",value:function(t){return f("action",t)}},{key:"defaultTarget",value:function(t){var e=f("target",t);if(e)return document.querySelector(e)}},{key:"defaultText",value:function(t){return f("text",t)}},{key:"destroy",value:function(){this.listener.destroy(),this.clipboardAction&&(this.clipboardAction.destroy(),this.clipboardAction=null)}}],[{key:"isSupported",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:["copy","cut"],e="string"==typeof t?[t]:t,n=!!document.queryCommandSupported;return e.forEach(function(t){n=n&&!!document.queryCommandSupported(t)}),n}}]),e}();function f(t,e){var n="data-clipboard-"+t;if(e.hasAttribute(n))return e.getAttribute(n)}t.exports=u},r=[t,n("LF/X"),n("WreF"),n("Bj/7")],void 0===(i="function"==typeof(o=a)?o.apply(e,r):o)||(t.exports=i)},WreF:function(t,e){function n(){}n.prototype={on:function(t,e,n){var o=this.e||(this.e={});return(o[t]||(o[t]=[])).push({fn:e,ctx:n}),this},once:function(t,e,n){var o=this;function r(){o.off(t,r),e.apply(n,arguments)}return r._=e,this.on(t,r,n)},emit:function(t){for(var e=[].slice.call(arguments,1),n=((this.e||(this.e={}))[t]||[]).slice(),o=0,r=n.length;o<r;o++)n[o].fn.apply(n[o].ctx,e);return this},off:function(t,e){var n=this.e||(this.e={}),o=n[t],r=[];if(o&&e)for(var i=0,a=o.length;i<a;i++)o[i].fn!==e&&o[i].fn._!==e&&r.push(o[i]);return r.length?n[t]=r:delete n[t],this}},t.exports=n},ZE5A:function(t,e,n){var o=n("Jssu");function r(t,e,n,r,i){var a=function(t,e,n,r){return function(n){n.delegateTarget=o(n.target,e),n.delegateTarget&&r.call(t,n)}}.apply(this,arguments);return t.addEventListener(n,a,i),{destroy:function(){t.removeEventListener(n,a,i)}}}t.exports=function(t,e,n,o,i){return"function"==typeof t.addEventListener?r.apply(null,arguments):"function"==typeof n?r.bind(null,document).apply(null,arguments):("string"==typeof t&&(t=document.querySelectorAll(t)),Array.prototype.map.call(t,function(t){return r(t,e,n,o,i)}))}},"fI2+":function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n("V33R"),r=n.n(o),i={props:{copeDatad:{url:{type:String,default:""},shortUrl:{type:String,default:""},copyUrlVisibled:{type:Boolean,default:!1}}},methods:{handleClick:function(t){this.$emit("click",t)},down:function(){window.location.href=this.$baseURL+"/app/link/downQrcode?url="+this.copeDatad.url},copy:function(t){var e=this;if(0===t)(n=new r.a("#gtLongUrlCopy")).on("success",function(t){e.$message({message:t.trigger.getAttribute("aria-label"),type:"success"}),n.destroy()});else if(1===t){var n;(n=new r.a("#gtShortUrlCopy")).on("success",function(t){e.$message({message:t.trigger.getAttribute("aria-label"),type:"success"}),n.destroy()})}},closeDialog:function(){console.log("close",999999999)}}},a={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("section",[n("el-dialog",{attrs:{title:"预览",visible:t.copeDatad.copyUrlVisibled,size:"cope-link"},on:{"update:visible":function(e){t.$set(t.copeDatad,"copyUrlVisibled",e)},close:t.$util.closeParentMask,open:t.$util.openParentMask}},[n("el-form",{attrs:{"label-width":"120px"}},[n("el-form-item",{attrs:{label:"核销二维码："}},[n("img",{staticClass:"erwema",attrs:{src:t.$baseURL+"/app/link/buildQrcode?url="+t.copeDatad.url}})]),t._v(" "),n("div",{staticStyle:{color:"#333","font-size":"12px","margin-left":"125px","margin-top":"-15px"}},[t._v("成为核销员后，通过以下链接，刻进去手机核销操作页面")]),t._v(" "),n("br"),t._v(" "),""!=t.copeDatad.url?n("el-form-item",{attrs:{label:"核销链接:"}},[n("input",{staticClass:"el-input__inner",staticStyle:{width:"320px","margin-right":"17px"},attrs:{type:"text",id:"gtLongUrl"},domProps:{value:t.copeDatad.url}}),t._v(" "),n("el-button",{attrs:{type:"primary","data-clipboard-target":"#gtLongUrl","aria-label":"复制成功！",id:"gtLongUrlCopy"},on:{click:function(e){t.copy(0)}}},[t._v("复制")])],1):t._e(),t._v(" "),""!=t.copeDatad.shortUrl?n("el-form-item",{attrs:{label:"短信链接:"}},[n("input",{staticClass:"el-input__inner",staticStyle:{width:"320px","margin-right":"17px"},attrs:{type:"text",id:"gtShortUrl"},domProps:{value:t.copeDatad.shortUrl}}),t._v(" "),n("el-button",{attrs:{type:"primary","data-clipboard-target":"#gtShortUrl","aria-label":"复制成功！",id:"gtShortUrlCopy"},on:{click:function(e){t.copy(1)}}},[t._v("复制")])],1):t._e()],1)],1)],1)},staticRenderFns:[]};var l=n("VU/8")(i,a,!1,function(t){n("jzIS")},"data-v-130d7dbd",null);e.default=l.exports},iDEd:function(t,e){e.node=function(t){return void 0!==t&&t instanceof HTMLElement&&1===t.nodeType},e.nodeList=function(t){var n=Object.prototype.toString.call(t);return void 0!==t&&("[object NodeList]"===n||"[object HTMLCollection]"===n)&&"length"in t&&(0===t.length||e.node(t[0]))},e.string=function(t){return"string"==typeof t||t instanceof String},e.fn=function(t){return"[object Function]"===Object.prototype.toString.call(t)}},jzIS:function(t,e){}});