/*
  Highlight.js 10.2.0 (da7d149b)
  License: BSD-3-Clause
  Copyright (c) 2006-2020, Ivan Sagalaev
*/
var hljs=function(){"use strict";function e(n){Object.freeze(n);var t="function"==typeof n;return Object.getOwnPropertyNames(n).forEach((function(r){!Object.hasOwnProperty.call(n,r)||null===n[r]||"object"!=typeof n[r]&&"function"!=typeof n[r]||t&&("caller"===r||"callee"===r||"arguments"===r)||Object.isFrozen(n[r])||e(n[r])})),n}class n{constructor(e){void 0===e.data&&(e.data={}),this.data=e.data}ignoreMatch(){this.ignore=!0}}function t(e){return e.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/"/g,"&quot;").replace(/'/g,"&#x27;")}function r(e,...n){var t={};for(const n in e)t[n]=e[n];return n.forEach((function(e){for(const n in e)t[n]=e[n]})),t}function a(e){return e.nodeName.toLowerCase()}var i=Object.freeze({__proto__:null,escapeHTML:t,inherit:r,nodeStream:function(e){var n=[];return function e(t,r){for(var i=t.firstChild;i;i=i.nextSibling)3===i.nodeType?r+=i.nodeValue.length:1===i.nodeType&&(n.push({event:"start",offset:r,node:i}),r=e(i,r),a(i).match(/br|hr|img|input/)||n.push({event:"stop",offset:r,node:i}));return r}(e,0),n},mergeStreams:function(e,n,r){var i=0,s="",o=[];function l(){return e.length&&n.length?e[0].offset!==n[0].offset?e[0].offset<n[0].offset?e:n:"start"===n[0].event?e:n:e.length?e:n}function c(e){s+="<"+a(e)+[].map.call(e.attributes,(function(e){return" "+e.nodeName+'="'+t(e.value)+'"'})).join("")+">"}function u(e){s+="</"+a(e)+">"}function g(e){("start"===e.event?c:u)(e.node)}for(;e.length||n.length;){var d=l();if(s+=t(r.substring(i,d[0].offset)),i=d[0].offset,d===e){o.reverse().forEach(u);do{g(d.splice(0,1)[0]),d=l()}while(d===e&&d.length&&d[0].offset===i);o.reverse().forEach(c)}else"start"===d[0].event?o.push(d[0].node):o.pop(),g(d.splice(0,1)[0])}return s+t(r.substr(i))}});const s="</span>",o=e=>!!e.kind;class l{constructor(e,n){this.buffer="",this.classPrefix=n.classPrefix,e.walk(this)}addText(e){this.buffer+=t(e)}openNode(e){if(!o(e))return;let n=e.kind;e.sublanguage||(n=`${this.classPrefix}${n}`),this.span(n)}closeNode(e){o(e)&&(this.buffer+=s)}value(){return this.buffer}span(e){this.buffer+=`<span class="${e}">`}}class c{constructor(){this.rootNode={children:[]},this.stack=[this.rootNode]}get top(){return this.stack[this.stack.length-1]}get root(){return this.rootNode}add(e){this.top.children.push(e)}openNode(e){const n={kind:e,children:[]};this.add(n),this.stack.push(n)}closeNode(){if(this.stack.length>1)return this.stack.pop()}closeAllNodes(){for(;this.closeNode(););}toJSON(){return JSON.stringify(this.rootNode,null,4)}walk(e){return this.constructor._walk(e,this.rootNode)}static _walk(e,n){return"string"==typeof n?e.addText(n):n.children&&(e.openNode(n),n.children.forEach(n=>this._walk(e,n)),e.closeNode(n)),e}static _collapse(e){"string"!=typeof e&&e.children&&(e.children.every(e=>"string"==typeof e)?e.children=[e.children.join("")]:e.children.forEach(e=>{c._collapse(e)}))}}class u extends c{constructor(e){super(),this.options=e}addKeyword(e,n){""!==e&&(this.openNode(n),this.addText(e),this.closeNode())}addText(e){""!==e&&this.add(e)}addSublanguage(e,n){const t=e.root;t.kind=n,t.sublanguage=!0,this.add(t)}toHTML(){return new l(this,this.options).value()}finalize(){return!0}}function g(e){return e?"string"==typeof e?e:e.source:null}const d="(-?)(\\b0[xX][a-fA-F0-9]+|(\\b\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)",h={begin:"\\\\[\\s\\S]",relevance:0},f={className:"string",begin:"'",end:"'",illegal:"\\n",contains:[h]},p={className:"string",begin:'"',end:'"',illegal:"\\n",contains:[h]},m={begin:/\b(a|an|the|are|I'm|isn't|don't|doesn't|won't|but|just|should|pretty|simply|enough|gonna|going|wtf|so|such|will|you|your|they|like|more)\b/},b=function(e,n,t={}){var a=r({className:"comment",begin:e,end:n,contains:[]},t);return a.contains.push(m),a.contains.push({className:"doctag",begin:"(?:TODO|FIXME|NOTE|BUG|OPTIMIZE|HACK|XXX):",relevance:0}),a},v=b("//","$"),x=b("/\\*","\\*/"),E=b("#","$");var _=Object.freeze({__proto__:null,IDENT_RE:"[a-zA-Z]\\w*",UNDERSCORE_IDENT_RE:"[a-zA-Z_]\\w*",NUMBER_RE:"\\b\\d+(\\.\\d+)?",C_NUMBER_RE:d,BINARY_NUMBER_RE:"\\b(0b[01]+)",RE_STARTERS_RE:"!|!=|!==|%|%=|&|&&|&=|\\*|\\*=|\\+|\\+=|,|-|-=|/=|/|:|;|<<|<<=|<=|<|===|==|=|>>>=|>>=|>=|>>>|>>|>|\\?|\\[|\\{|\\(|\\^|\\^=|\\||\\|=|\\|\\||~",SHEBANG:(e={})=>{const n=/^#![ ]*\//;return e.binary&&(e.begin=function(...e){return e.map(e=>g(e)).join("")}(n,/.*\b/,e.binary,/\b.*/)),r({className:"meta",begin:n,end:/$/,relevance:0,"on:begin":(e,n)=>{0!==e.index&&n.ignoreMatch()}},e)},BACKSLASH_ESCAPE:h,APOS_STRING_MODE:f,QUOTE_STRING_MODE:p,PHRASAL_WORDS_MODE:m,COMMENT:b,C_LINE_COMMENT_MODE:v,C_BLOCK_COMMENT_MODE:x,HASH_COMMENT_MODE:E,NUMBER_MODE:{className:"number",begin:"\\b\\d+(\\.\\d+)?",relevance:0},C_NUMBER_MODE:{className:"number",begin:d,relevance:0},BINARY_NUMBER_MODE:{className:"number",begin:"\\b(0b[01]+)",relevance:0},CSS_NUMBER_MODE:{className:"number",begin:"\\b\\d+(\\.\\d+)?(%|em|ex|ch|rem|vw|vh|vmin|vmax|cm|mm|in|pt|pc|px|deg|grad|rad|turn|s|ms|Hz|kHz|dpi|dpcm|dppx)?",relevance:0},REGEXP_MODE:{begin:/(?=\/[^/\n]*\/)/,contains:[{className:"regexp",begin:/\//,end:/\/[gimuy]*/,illegal:/\n/,contains:[h,{begin:/\[/,end:/\]/,relevance:0,contains:[h]}]}]},TITLE_MODE:{className:"title",begin:"[a-zA-Z]\\w*",relevance:0},UNDERSCORE_TITLE_MODE:{className:"title",begin:"[a-zA-Z_]\\w*",relevance:0},METHOD_GUARD:{begin:"\\.\\s*[a-zA-Z_]\\w*",relevance:0},END_SAME_AS_BEGIN:function(e){return Object.assign(e,{"on:begin":(e,n)=>{n.data._beginMatch=e[1]},"on:end":(e,n)=>{n.data._beginMatch!==e[1]&&n.ignoreMatch()}})}}),w="of and for in not or if then".split(" ");function N(e,n){return n?+n:function(e){return w.includes(e.toLowerCase())}(e)?0:1}const y={props:["language","code","autodetect"],data:function(){return{detectedLanguage:"",unknownLanguage:!1}},computed:{className(){return this.unknownLanguage?"":"hljs "+this.detectedLanguage},highlighted(){if(!this.autoDetect&&!hljs.getLanguage(this.language))return console.warn(`The language "${this.language}" you specified could not be found.`),this.unknownLanguage=!0,t(this.code);let e;return this.autoDetect?(e=hljs.highlightAuto(this.code),this.detectedLanguage=e.language):(e=hljs.highlight(this.language,this.code,this.ignoreIllegals),this.detectectLanguage=this.language),e.value},autoDetect(){return!(this.language&&(e=this.autodetect,!e&&""!==e));var e},ignoreIllegals:()=>!0},render(e){return e("pre",{},[e("code",{class:this.className,domProps:{innerHTML:this.highlighted}})])}},R={install(e){e.component("highlightjs",y)}},k=t,O=r,{nodeStream:M,mergeStreams:L}=i,T=Symbol("nomatch");return function(t){var a=[],i=Object.create(null),s=Object.create(null),o=[],l=!0,c=/(^(<[^>]+>|\t|)+|\n)/gm,d="Could not find the language '{}', did you forget to load/include a language module?";const h={disableAutodetect:!0,name:"Plain text",contains:[]};var f={noHighlightRe:/^(no-?highlight)$/i,languageDetectRe:/\blang(?:uage)?-([\w-]+)\b/i,classPrefix:"hljs-",tabReplace:null,useBR:!1,languages:null,__emitter:u};function p(e){return f.noHighlightRe.test(e)}function m(e,n,t,r){var a={code:n,language:e};S("before:highlight",a);var i=a.result?a.result:b(a.language,a.code,t,r);return i.code=a.code,S("after:highlight",i),i}function b(e,t,a,s){var o=t;function c(e,n){var t=E.case_insensitive?n[0].toLowerCase():n[0];return Object.prototype.hasOwnProperty.call(e.keywords,t)&&e.keywords[t]}function u(){null!=R.subLanguage?function(){if(""!==L){var e=null;if("string"==typeof R.subLanguage){if(!i[R.subLanguage])return void M.addText(L);e=b(R.subLanguage,L,!0,O[R.subLanguage]),O[R.subLanguage]=e.top}else e=v(L,R.subLanguage.length?R.subLanguage:null);R.relevance>0&&(j+=e.relevance),M.addSublanguage(e.emitter,e.language)}}():function(){if(!R.keywords)return void M.addText(L);let e=0;R.keywordPatternRe.lastIndex=0;let n=R.keywordPatternRe.exec(L),t="";for(;n;){t+=L.substring(e,n.index);const r=c(R,n);if(r){const[e,a]=r;M.addText(t),t="",j+=a,M.addKeyword(n[0],e)}else t+=n[0];e=R.keywordPatternRe.lastIndex,n=R.keywordPatternRe.exec(L)}t+=L.substr(e),M.addText(t)}(),L=""}function h(e){return e.className&&M.openNode(e.className),R=Object.create(e,{parent:{value:R}})}function p(e){return 0===R.matcher.regexIndex?(L+=e[0],1):(I=!0,0)}var m={};function x(t,r){var i=r&&r[0];if(L+=t,null==i)return u(),0;if("begin"===m.type&&"end"===r.type&&m.index===r.index&&""===i){if(L+=o.slice(r.index,r.index+1),!l){const n=Error("0 width match regex");throw n.languageName=e,n.badRule=m.rule,n}return 1}if(m=r,"begin"===r.type)return function(e){var t=e[0],r=e.rule;const a=new n(r),i=[r.__beforeBegin,r["on:begin"]];for(const n of i)if(n&&(n(e,a),a.ignore))return p(t);return r&&r.endSameAsBegin&&(r.endRe=RegExp(t.replace(/[-/\\^$*+?.()|[\]{}]/g,"\\$&"),"m")),r.skip?L+=t:(r.excludeBegin&&(L+=t),u(),r.returnBegin||r.excludeBegin||(L=t)),h(r),r.returnBegin?0:t.length}(r);if("illegal"===r.type&&!a){const e=Error('Illegal lexeme "'+i+'" for mode "'+(R.className||"<unnamed>")+'"');throw e.mode=R,e}if("end"===r.type){var s=function(e){var t=e[0],r=o.substr(e.index),a=function e(t,r,a){let i=function(e,n){var t=e&&e.exec(n);return t&&0===t.index}(t.endRe,a);if(i){if(t["on:end"]){const e=new n(t);t["on:end"](r,e),e.ignore&&(i=!1)}if(i){for(;t.endsParent&&t.parent;)t=t.parent;return t}}if(t.endsWithParent)return e(t.parent,r,a)}(R,e,r);if(!a)return T;var i=R;i.skip?L+=t:(i.returnEnd||i.excludeEnd||(L+=t),u(),i.excludeEnd&&(L=t));do{R.className&&M.closeNode(),R.skip||R.subLanguage||(j+=R.relevance),R=R.parent}while(R!==a.parent);return a.starts&&(a.endSameAsBegin&&(a.starts.endRe=a.endRe),h(a.starts)),i.returnEnd?0:t.length}(r);if(s!==T)return s}if("illegal"===r.type&&""===i)return 1;if(S>1e5&&S>3*r.index)throw Error("potential infinite loop, way more iterations than matches");return L+=i,i.length}var E=y(e);if(!E)throw console.error(d.replace("{}",e)),Error('Unknown language: "'+e+'"');var _=function(e){function n(n,t){return RegExp(g(n),"m"+(e.case_insensitive?"i":"")+(t?"g":""))}class t{constructor(){this.matchIndexes={},this.regexes=[],this.matchAt=1,this.position=0}addRule(e,n){n.position=this.position++,this.matchIndexes[this.matchAt]=n,this.regexes.push([n,e]),this.matchAt+=function(e){return RegExp(e.toString()+"|").exec("").length-1}(e)+1}compile(){0===this.regexes.length&&(this.exec=()=>null);const e=this.regexes.map(e=>e[1]);this.matcherRe=n(function(e,n="|"){for(var t=/\[(?:[^\\\]]|\\.)*\]|\(\??|\\([1-9][0-9]*)|\\./,r=0,a="",i=0;i<e.length;i++){var s=r+=1,o=g(e[i]);for(i>0&&(a+=n),a+="(";o.length>0;){var l=t.exec(o);if(null==l){a+=o;break}a+=o.substring(0,l.index),o=o.substring(l.index+l[0].length),"\\"===l[0][0]&&l[1]?a+="\\"+(+l[1]+s):(a+=l[0],"("===l[0]&&r++)}a+=")"}return a}(e),!0),this.lastIndex=0}exec(e){this.matcherRe.lastIndex=this.lastIndex;const n=this.matcherRe.exec(e);if(!n)return null;const t=n.findIndex((e,n)=>n>0&&void 0!==e),r=this.matchIndexes[t];return n.splice(0,t),Object.assign(n,r)}}class a{constructor(){this.rules=[],this.multiRegexes=[],this.count=0,this.lastIndex=0,this.regexIndex=0}getMatcher(e){if(this.multiRegexes[e])return this.multiRegexes[e];const n=new t;return this.rules.slice(e).forEach(([e,t])=>n.addRule(e,t)),n.compile(),this.multiRegexes[e]=n,n}resumingScanAtSamePosition(){return 0!=this.regexIndex}considerAll(){this.regexIndex=0}addRule(e,n){this.rules.push([e,n]),"begin"===n.type&&this.count++}exec(e){const n=this.getMatcher(this.regexIndex);n.lastIndex=this.lastIndex;const t=n.exec(e);return t&&(this.regexIndex+=t.position+1,this.regexIndex===this.count&&(this.regexIndex=0)),t}}function i(e,n){const t=e.input[e.index-1],r=e.input[e.index+e[0].length];"."!==t&&"."!==r||n.ignoreMatch()}if(e.contains&&e.contains.includes("self"))throw Error("ERR: contains `self` is not supported at the top-level of a language.  See documentation.");return function t(s,o){const l=s;if(s.compiled)return l;s.compiled=!0,s.__beforeBegin=null,s.keywords=s.keywords||s.beginKeywords;let c=null;if("object"==typeof s.keywords&&(c=s.keywords.$pattern,delete s.keywords.$pattern),s.keywords&&(s.keywords=function(e,n){var t={};return"string"==typeof e?r("keyword",e):Object.keys(e).forEach((function(n){r(n,e[n])})),t;function r(e,r){n&&(r=r.toLowerCase()),r.split(" ").forEach((function(n){var r=n.split("|");t[r[0]]=[e,N(r[0],r[1])]}))}}(s.keywords,e.case_insensitive)),s.lexemes&&c)throw Error("ERR: Prefer `keywords.$pattern` to `mode.lexemes`, BOTH are not allowed. (see mode reference) ");return l.keywordPatternRe=n(s.lexemes||c||/\w+/,!0),o&&(s.beginKeywords&&(s.begin="\\b("+s.beginKeywords.split(" ").join("|")+")(?=\\b|\\s)",s.__beforeBegin=i),s.begin||(s.begin=/\B|\b/),l.beginRe=n(s.begin),s.endSameAsBegin&&(s.end=s.begin),s.end||s.endsWithParent||(s.end=/\B|\b/),s.end&&(l.endRe=n(s.end)),l.terminator_end=g(s.end)||"",s.endsWithParent&&o.terminator_end&&(l.terminator_end+=(s.end?"|":"")+o.terminator_end)),s.illegal&&(l.illegalRe=n(s.illegal)),void 0===s.relevance&&(s.relevance=1),s.contains||(s.contains=[]),s.contains=[].concat(...s.contains.map((function(e){return function(e){return e.variants&&!e.cached_variants&&(e.cached_variants=e.variants.map((function(n){return r(e,{variants:null},n)}))),e.cached_variants?e.cached_variants:function e(n){return!!n&&(n.endsWithParent||e(n.starts))}(e)?r(e,{starts:e.starts?r(e.starts):null}):Object.isFrozen(e)?r(e):e}("self"===e?s:e)}))),s.contains.forEach((function(e){t(e,l)})),s.starts&&t(s.starts,o),l.matcher=function(e){const n=new a;return e.contains.forEach(e=>n.addRule(e.begin,{rule:e,type:"begin"})),e.terminator_end&&n.addRule(e.terminator_end,{type:"end"}),e.illegal&&n.addRule(e.illegal,{type:"illegal"}),n}(l),l}(e)}(E),w="",R=s||_,O={},M=new f.__emitter(f);!function(){for(var e=[],n=R;n!==E;n=n.parent)n.className&&e.unshift(n.className);e.forEach(e=>M.openNode(e))}();var L="",j=0,A=0,S=0,I=!1;try{for(R.matcher.considerAll();;){S++,I?I=!1:(R.matcher.lastIndex=A,R.matcher.considerAll());const e=R.matcher.exec(o);if(!e&&R.matcher.resumingScanAtSamePosition()){L+=o[A],A+=1;continue}if(!e)break;const n=x(o.substring(A,e.index),e);A=e.index+n}return x(o.substr(A)),M.closeAllNodes(),M.finalize(),w=M.toHTML(),{relevance:j,value:w,language:e,illegal:!1,emitter:M,top:R}}catch(n){if(n.message&&n.message.includes("Illegal"))return{illegal:!0,illegalBy:{msg:n.message,context:o.slice(A-100,A+100),mode:n.mode},sofar:w,relevance:0,value:k(o),emitter:M};if(l)return{illegal:!1,relevance:0,value:k(o),emitter:M,language:e,top:R,errorRaised:n};throw n}}function v(e,n){n=n||f.languages||Object.keys(i);var t=function(e){const n={relevance:0,emitter:new f.__emitter(f),value:k(e),illegal:!1,top:h};return n.emitter.addText(e),n}(e),r=t;return n.filter(y).filter(A).forEach((function(n){var a=b(n,e,!1);a.language=n,a.relevance>r.relevance&&(r=a),a.relevance>t.relevance&&(r=t,t=a)})),r.language&&(t.second_best=r),t}function x(e){return f.tabReplace||f.useBR?e.replace(c,e=>"\n"===e?f.useBR?"<br>":e:f.tabReplace?e.replace(/\t/g,f.tabReplace):e):e}function E(e){let n=null;const t=function(e){var n=e.className+" ";n+=e.parentNode?e.parentNode.className:"";const t=f.languageDetectRe.exec(n);if(t){var r=y(t[1]);return r||(console.warn(d.replace("{}",t[1])),console.warn("Falling back to no-highlight mode for this block.",e)),r?t[1]:"no-highlight"}return n.split(/\s+/).find(e=>p(e)||y(e))}(e);if(p(t))return;S("before:highlightBlock",{block:e,language:t}),f.useBR?(n=document.createElement("div")).innerHTML=e.innerHTML.replace(/\n/g,"").replace(/<br[ /]*>/g,"\n"):n=e;const r=n.textContent,a=t?m(t,r,!0):v(r),i=M(n);if(i.length){const e=document.createElement("div");e.innerHTML=a.value,a.value=L(i,M(e),r)}a.value=x(a.value),S("after:highlightBlock",{block:e,result:a}),e.innerHTML=a.value,e.className=function(e,n,t){var r=n?s[n]:t,a=[e.trim()];return e.match(/\bhljs\b/)||a.push("hljs"),e.includes(r)||a.push(r),a.join(" ").trim()}(e.className,t,a.language),e.result={language:a.language,re:a.relevance,relavance:a.relevance},a.second_best&&(e.second_best={language:a.second_best.language,re:a.second_best.relevance,relavance:a.second_best.relevance})}const w=()=>{if(!w.called){w.called=!0;var e=document.querySelectorAll("pre code");a.forEach.call(e,E)}};function y(e){return e=(e||"").toLowerCase(),i[e]||i[s[e]]}function j(e,{languageName:n}){"string"==typeof e&&(e=[e]),e.forEach(e=>{s[e]=n})}function A(e){var n=y(e);return n&&!n.disableAutodetect}function S(e,n){var t=e;o.forEach((function(e){e[t]&&e[t](n)}))}Object.assign(t,{highlight:m,highlightAuto:v,fixMarkup:function(e){return console.warn("fixMarkup is deprecated and will be removed entirely in v11.0"),console.warn("Please see https://github.com/highlightjs/highlight.js/issues/2534"),x(e)},highlightBlock:E,configure:function(e){f=O(f,e)},initHighlighting:w,initHighlightingOnLoad:function(){window.addEventListener("DOMContentLoaded",w,!1)},registerLanguage:function(e,n){var r=null;try{r=n(t)}catch(n){if(console.error("Language definition for '{}' could not be registered.".replace("{}",e)),!l)throw n;console.error(n),r=h}r.name||(r.name=e),i[e]=r,r.rawDefinition=n.bind(null,t),r.aliases&&j(r.aliases,{languageName:e})},listLanguages:function(){return Object.keys(i)},getLanguage:y,registerAliases:j,requireLanguage:function(e){var n=y(e);if(n)return n;throw Error("The '{}' language is required, but not loaded.".replace("{}",e))},autoDetection:A,inherit:O,addPlugin:function(e){o.push(e)},vuePlugin:R}),t.debugMode=function(){l=!1},t.safeMode=function(){l=!0},t.versionString="10.2.0";for(const n in _)"object"==typeof _[n]&&e(_[n]);return Object.assign(t,_),t}({})}();"object"==typeof exports&&"undefined"!=typeof module&&(module.exports=hljs);hljs.registerLanguage("xml",function(){"use strict";return function(e){var n={className:"symbol",begin:"&[a-z]+;|&#[0-9]+;|&#x[a-f0-9]+;"},a={begin:"\\s",contains:[{className:"meta-keyword",begin:"#?[a-z_][a-z1-9_-]+",illegal:"\\n"}]},s=e.inherit(a,{begin:"\\(",end:"\\)"}),t=e.inherit(e.APOS_STRING_MODE,{className:"meta-string"}),i=e.inherit(e.QUOTE_STRING_MODE,{className:"meta-string"}),c={endsWithParent:!0,illegal:/</,relevance:0,contains:[{className:"attr",begin:"[A-Za-z0-9\\._:-]+",relevance:0},{begin:/=\s*/,relevance:0,contains:[{className:"string",endsParent:!0,variants:[{begin:/"/,end:/"/,contains:[n]},{begin:/'/,end:/'/,contains:[n]},{begin:/[^\s"'=<>`]+/}]}]}]};return{name:"HTML, XML",aliases:["html","xhtml","rss","atom","xjb","xsd","xsl","plist","wsf","svg"],case_insensitive:!0,contains:[{className:"meta",begin:"<![a-z]",end:">",relevance:10,contains:[a,i,t,s,{begin:"\\[",end:"\\]",contains:[{className:"meta",begin:"<![a-z]",end:">",contains:[a,s,i,t]}]}]},e.COMMENT("\x3c!--","--\x3e",{relevance:10}),{begin:"<\\!\\[CDATA\\[",end:"\\]\\]>",relevance:10},n,{className:"meta",begin:/<\?xml/,end:/\?>/,relevance:10},{className:"tag",begin:"<style(?=\\s|>)",end:">",keywords:{name:"style"},contains:[c],starts:{end:"</style>",returnEnd:!0,subLanguage:["css","xml"]}},{className:"tag",begin:"<script(?=\\s|>)",end:">",keywords:{name:"script"},contains:[c],starts:{end:"<\/script>",returnEnd:!0,subLanguage:["javascript","handlebars","xml"]}},{className:"tag",begin:"</?",end:"/?>",contains:[{className:"name",begin:/[^\/><\s]+/,relevance:0},c]}]}}}());hljs.registerLanguage("json",function(){"use strict";return function(n){var e={literal:"true false null"},i=[n.C_LINE_COMMENT_MODE,n.C_BLOCK_COMMENT_MODE],t=[n.QUOTE_STRING_MODE,n.C_NUMBER_MODE],a={end:",",endsWithParent:!0,excludeEnd:!0,contains:t,keywords:e},l={begin:"{",end:"}",contains:[{className:"attr",begin:/"/,end:/"/,contains:[n.BACKSLASH_ESCAPE],illegal:"\\n"},n.inherit(a,{begin:/:/})].concat(i),illegal:"\\S"},s={begin:"\\[",end:"\\]",contains:[n.inherit(a)],illegal:"\\S"};return t.push(l,s),i.forEach((function(n){t.push(n)})),{name:"JSON",contains:t,keywords:e,illegal:"\\S"}}}());hljs.registerLanguage("plaintext",function(){"use strict";return function(t){return{name:"Plain text",aliases:["text","txt"],disableAutodetect:!0}}}());hljs.registerLanguage("css",function(){"use strict";return function(e){var n={begin:/(?:[A-Z\_\.\-]+|--[a-zA-Z0-9_-]+)\s*:/,returnBegin:!0,end:";",endsWithParent:!0,contains:[{className:"attribute",begin:/\S/,end:":",excludeEnd:!0,starts:{endsWithParent:!0,excludeEnd:!0,contains:[{begin:/[\w-]+\(/,returnBegin:!0,contains:[{className:"built_in",begin:/[\w-]+/},{begin:/\(/,end:/\)/,contains:[e.APOS_STRING_MODE,e.QUOTE_STRING_MODE,e.CSS_NUMBER_MODE]}]},e.CSS_NUMBER_MODE,e.QUOTE_STRING_MODE,e.APOS_STRING_MODE,e.C_BLOCK_COMMENT_MODE,{className:"number",begin:"#[0-9A-Fa-f]+"},{className:"meta",begin:"!important"}]}}]};return{name:"CSS",case_insensitive:!0,illegal:/[=\/|'\$]/,contains:[e.C_BLOCK_COMMENT_MODE,{className:"selector-id",begin:/#[A-Za-z0-9_-]+/},{className:"selector-class",begin:/\.[A-Za-z0-9_-]+/},{className:"selector-attr",begin:/\[/,end:/\]/,illegal:"$",contains:[e.APOS_STRING_MODE,e.QUOTE_STRING_MODE]},{className:"selector-pseudo",begin:/:(:)?[a-zA-Z0-9\_\-\+\(\)"'.]+/},{begin:"@(page|font-face)",lexemes:"@[a-z-]+",keywords:"@page @font-face"},{begin:"@",end:"[{;]",illegal:/:/,returnBegin:!0,contains:[{className:"keyword",begin:/@\-?\w[\w]*(\-\w+)*/},{begin:/\s/,endsWithParent:!0,excludeEnd:!0,relevance:0,keywords:"and or not only",contains:[{begin:/[a-z-]+:/,className:"attribute"},e.APOS_STRING_MODE,e.QUOTE_STRING_MODE,e.CSS_NUMBER_MODE]}]},{className:"selector-tag",begin:"[a-zA-Z-][a-zA-Z0-9_-]*",relevance:0},{begin:"{",end:"}",illegal:/\S/,contains:[e.C_BLOCK_COMMENT_MODE,n]}]}}}());hljs.registerLanguage("properties",function(){"use strict";return function(e){var n="[ \\t\\f]*",t="("+n+"[:=]"+n+"|[ \\t\\f]+)",a="([^\\\\:= \\t\\f\\n]|\\\\.)+",s={end:t,relevance:0,starts:{className:"string",end:/$/,relevance:0,contains:[{begin:"\\\\\\n"}]}};return{name:".properties",case_insensitive:!0,illegal:/\S/,contains:[e.COMMENT("^\\s*[!#]","$"),{begin:"([^\\\\\\W:= \\t\\f\\n]|\\\\.)+"+t,returnBegin:!0,contains:[{className:"attr",begin:"([^\\\\\\W:= \\t\\f\\n]|\\\\.)+",endsParent:!0,relevance:0}],starts:s},{begin:a+t,returnBegin:!0,relevance:0,contains:[{className:"meta",begin:a,endsParent:!0,relevance:0}],starts:s},{className:"attr",relevance:0,begin:a+n+"$"}]}}}());hljs.registerLanguage("java",function(){"use strict";function e(e){return e?"string"==typeof e?e:e.source:null}function n(e){return a("(",e,")?")}function a(...n){return n.map(n=>e(n)).join("")}function s(...n){return"("+n.map(n=>e(n)).join("|")+")"}return function(e){var t="false synchronized int abstract float private char boolean var static null if const for true while long strictfp finally protected import native final void enum else break transient catch instanceof byte super volatile case assert short package default double public try this switch continue throws protected public private module requires exports do",i={className:"meta",begin:"@[À-ʸa-zA-Z_$][À-ʸa-zA-Z_$0-9]*",contains:[{begin:/\(/,end:/\)/,contains:["self"]}]},r=e=>a("[",e,"]+([",e,"_]*[",e,"]+)?"),c={className:"number",variants:[{begin:`\\b(0[bB]${r("01")})[lL]?`},{begin:`\\b(0${r("0-7")})[dDfFlL]?`},{begin:a(/\b0[xX]/,s(a(r("a-fA-F0-9"),/\./,r("a-fA-F0-9")),a(r("a-fA-F0-9"),/\.?/),a(/\./,r("a-fA-F0-9"))),/([pP][+-]?(\d+))?/,/[fFdDlL]?/)},{begin:a(/\b/,s(a(/\d*\./,r("\\d")),r("\\d")),/[eE][+-]?[\d]+[dDfF]?/)},{begin:a(/\b/,r(/\d/),n(/\.?/),n(r(/\d/)),/[dDfFlL]?/)}],relevance:0};return{name:"Java",aliases:["jsp"],keywords:t,illegal:/<\/|#/,contains:[e.COMMENT("/\\*\\*","\\*/",{relevance:0,contains:[{begin:/\w+@/,relevance:0},{className:"doctag",begin:"@[A-Za-z]+"}]}),e.C_LINE_COMMENT_MODE,e.C_BLOCK_COMMENT_MODE,e.APOS_STRING_MODE,e.QUOTE_STRING_MODE,{className:"class",beginKeywords:"class interface enum",end:/[{;=]/,excludeEnd:!0,keywords:"class interface enum",illegal:/[:"\[\]]/,contains:[{beginKeywords:"extends implements"},e.UNDERSCORE_TITLE_MODE]},{beginKeywords:"new throw return else",relevance:0},{className:"function",begin:"([À-ʸa-zA-Z_$][À-ʸa-zA-Z_$0-9]*(<[À-ʸa-zA-Z_$][À-ʸa-zA-Z_$0-9]*(\\s*,\\s*[À-ʸa-zA-Z_$][À-ʸa-zA-Z_$0-9]*)*>)?\\s+)+"+e.UNDERSCORE_IDENT_RE+"\\s*\\(",returnBegin:!0,end:/[{;=]/,excludeEnd:!0,keywords:t,contains:[{begin:e.UNDERSCORE_IDENT_RE+"\\s*\\(",returnBegin:!0,relevance:0,contains:[e.UNDERSCORE_TITLE_MODE]},{className:"params",begin:/\(/,end:/\)/,keywords:t,relevance:0,contains:[i,e.APOS_STRING_MODE,e.QUOTE_STRING_MODE,e.C_NUMBER_MODE,e.C_BLOCK_COMMENT_MODE]},e.C_LINE_COMMENT_MODE,e.C_BLOCK_COMMENT_MODE]},c,i]}}}());hljs.registerLanguage("sql",function(){"use strict";return function(e){var t=e.COMMENT("--","$");return{name:"SQL",case_insensitive:!0,illegal:/[<>{}*]/,contains:[{beginKeywords:"begin end start commit rollback savepoint lock alter create drop rename call delete do handler insert load replace select truncate update set show pragma grant merge describe use explain help declare prepare execute deallocate release unlock purge reset change stop analyze cache flush optimize repair kill install uninstall checksum restore check backup revoke comment values with",end:/;/,endsWithParent:!0,keywords:{$pattern:/[\w\.]+/,keyword:"as abort abs absolute acc acce accep accept access accessed accessible account acos action activate add addtime admin administer advanced advise aes_decrypt aes_encrypt after agent aggregate ali alia alias all allocate allow alter always analyze ancillary and anti any anydata anydataset anyschema anytype apply archive archived archivelog are as asc ascii asin assembly assertion associate asynchronous at atan atn2 attr attri attrib attribu attribut attribute attributes audit authenticated authentication authid authors auto autoallocate autodblink autoextend automatic availability avg backup badfile basicfile before begin beginning benchmark between bfile bfile_base big bigfile bin binary_double binary_float binlog bit_and bit_count bit_length bit_or bit_xor bitmap blob_base block blocksize body both bound bucket buffer_cache buffer_pool build bulk by byte byteordermark bytes cache caching call calling cancel capacity cascade cascaded case cast catalog category ceil ceiling chain change changed char_base char_length character_length characters characterset charindex charset charsetform charsetid check checksum checksum_agg child choose chr chunk class cleanup clear client clob clob_base clone close cluster_id cluster_probability cluster_set clustering coalesce coercibility col collate collation collect colu colum column column_value columns columns_updated comment commit compact compatibility compiled complete composite_limit compound compress compute concat concat_ws concurrent confirm conn connec connect connect_by_iscycle connect_by_isleaf connect_by_root connect_time connection consider consistent constant constraint constraints constructor container content contents context contributors controlfile conv convert convert_tz corr corr_k corr_s corresponding corruption cos cost count count_big counted covar_pop covar_samp cpu_per_call cpu_per_session crc32 create creation critical cross cube cume_dist curdate current current_date current_time current_timestamp current_user cursor curtime customdatum cycle data database databases datafile datafiles datalength date_add date_cache date_format date_sub dateadd datediff datefromparts datename datepart datetime2fromparts day day_to_second dayname dayofmonth dayofweek dayofyear days db_role_change dbtimezone ddl deallocate declare decode decompose decrement decrypt deduplicate def defa defau defaul default defaults deferred defi defin define degrees delayed delegate delete delete_all delimited demand dense_rank depth dequeue des_decrypt des_encrypt des_key_file desc descr descri describ describe descriptor deterministic diagnostics difference dimension direct_load directory disable disable_all disallow disassociate discardfile disconnect diskgroup distinct distinctrow distribute distributed div do document domain dotnet double downgrade drop dumpfile duplicate duration each edition editionable editions element ellipsis else elsif elt empty enable enable_all enclosed encode encoding encrypt end end-exec endian enforced engine engines enqueue enterprise entityescaping eomonth error errors escaped evalname evaluate event eventdata events except exception exceptions exchange exclude excluding execu execut execute exempt exists exit exp expire explain explode export export_set extended extent external external_1 external_2 externally extract failed failed_login_attempts failover failure far fast feature_set feature_value fetch field fields file file_name_convert filesystem_like_logging final finish first first_value fixed flash_cache flashback floor flush following follows for forall force foreign form forma format found found_rows freelist freelists freepools fresh from from_base64 from_days ftp full function general generated get get_format get_lock getdate getutcdate global global_name globally go goto grant grants greatest group group_concat group_id grouping grouping_id groups gtid_subtract guarantee guard handler hash hashkeys having hea head headi headin heading heap help hex hierarchy high high_priority hosts hour hours http id ident_current ident_incr ident_seed identified identity idle_time if ifnull ignore iif ilike ilm immediate import in include including increment index indexes indexing indextype indicator indices inet6_aton inet6_ntoa inet_aton inet_ntoa infile initial initialized initially initrans inmemory inner innodb input insert install instance instantiable instr interface interleaved intersect into invalidate invisible is is_free_lock is_ipv4 is_ipv4_compat is_not is_not_null is_used_lock isdate isnull isolation iterate java join json json_exists keep keep_duplicates key keys kill language large last last_day last_insert_id last_value lateral lax lcase lead leading least leaves left len lenght length less level levels library like like2 like4 likec limit lines link list listagg little ln load load_file lob lobs local localtime localtimestamp locate locator lock locked log log10 log2 logfile logfiles logging logical logical_reads_per_call logoff logon logs long loop low low_priority lower lpad lrtrim ltrim main make_set makedate maketime managed management manual map mapping mask master master_pos_wait match matched materialized max maxextents maximize maxinstances maxlen maxlogfiles maxloghistory maxlogmembers maxsize maxtrans md5 measures median medium member memcompress memory merge microsecond mid migration min minextents minimum mining minus minute minutes minvalue missing mod mode model modification modify module monitoring month months mount move movement multiset mutex name name_const names nan national native natural nav nchar nclob nested never new newline next nextval no no_write_to_binlog noarchivelog noaudit nobadfile nocheck nocompress nocopy nocycle nodelay nodiscardfile noentityescaping noguarantee nokeep nologfile nomapping nomaxvalue nominimize nominvalue nomonitoring none noneditionable nonschema noorder nopr nopro noprom nopromp noprompt norely noresetlogs noreverse normal norowdependencies noschemacheck noswitch not nothing notice notnull notrim novalidate now nowait nth_value nullif nulls num numb numbe nvarchar nvarchar2 object ocicoll ocidate ocidatetime ociduration ociinterval ociloblocator ocinumber ociref ocirefcursor ocirowid ocistring ocitype oct octet_length of off offline offset oid oidindex old on online only opaque open operations operator optimal optimize option optionally or oracle oracle_date oradata ord ordaudio orddicom orddoc order ordimage ordinality ordvideo organization orlany orlvary out outer outfile outline output over overflow overriding package pad parallel parallel_enable parameters parent parse partial partition partitions pascal passing password password_grace_time password_lock_time password_reuse_max password_reuse_time password_verify_function patch path patindex pctincrease pctthreshold pctused pctversion percent percent_rank percentile_cont percentile_disc performance period period_add period_diff permanent physical pi pipe pipelined pivot pluggable plugin policy position post_transaction pow power pragma prebuilt precedes preceding precision prediction prediction_cost prediction_details prediction_probability prediction_set prepare present preserve prior priority private private_sga privileges procedural procedure procedure_analyze processlist profiles project prompt protection public publishingservername purge quarter query quick quiesce quota quotename radians raise rand range rank raw read reads readsize rebuild record records recover recovery recursive recycle redo reduced ref reference referenced references referencing refresh regexp_like register regr_avgx regr_avgy regr_count regr_intercept regr_r2 regr_slope regr_sxx regr_sxy reject rekey relational relative relaylog release release_lock relies_on relocate rely rem remainder rename repair repeat replace replicate replication required reset resetlogs resize resource respect restore restricted result result_cache resumable resume retention return returning returns reuse reverse revoke right rlike role roles rollback rolling rollup round row row_count rowdependencies rowid rownum rows rtrim rules safe salt sample save savepoint sb1 sb2 sb4 scan schema schemacheck scn scope scroll sdo_georaster sdo_topo_geometry search sec_to_time second seconds section securefile security seed segment select self semi sequence sequential serializable server servererror session session_user sessions_per_user set sets settings sha sha1 sha2 share shared shared_pool short show shrink shutdown si_averagecolor si_colorhistogram si_featurelist si_positionalcolor si_stillimage si_texture siblings sid sign sin size size_t sizes skip slave sleep smalldatetimefromparts smallfile snapshot some soname sort soundex source space sparse spfile split sql sql_big_result sql_buffer_result sql_cache sql_calc_found_rows sql_small_result sql_variant_property sqlcode sqldata sqlerror sqlname sqlstate sqrt square standalone standby start starting startup statement static statistics stats_binomial_test stats_crosstab stats_ks_test stats_mode stats_mw_test stats_one_way_anova stats_t_test_ stats_t_test_indep stats_t_test_one stats_t_test_paired stats_wsr_test status std stddev stddev_pop stddev_samp stdev stop storage store stored str str_to_date straight_join strcmp strict string struct stuff style subdate subpartition subpartitions substitutable substr substring subtime subtring_index subtype success sum suspend switch switchoffset switchover sync synchronous synonym sys sys_xmlagg sysasm sysaux sysdate sysdatetimeoffset sysdba sysoper system system_user sysutcdatetime table tables tablespace tablesample tan tdo template temporary terminated tertiary_weights test than then thread through tier ties time time_format time_zone timediff timefromparts timeout timestamp timestampadd timestampdiff timezone_abbr timezone_minute timezone_region to to_base64 to_date to_days to_seconds todatetimeoffset trace tracking transaction transactional translate translation treat trigger trigger_nestlevel triggers trim truncate try_cast try_convert try_parse type ub1 ub2 ub4 ucase unarchived unbounded uncompress under undo unhex unicode uniform uninstall union unique unix_timestamp unknown unlimited unlock unnest unpivot unrecoverable unsafe unsigned until untrusted unusable unused update updated upgrade upped upper upsert url urowid usable usage use use_stored_outlines user user_data user_resources users using utc_date utc_timestamp uuid uuid_short validate validate_password_strength validation valist value values var var_samp varcharc vari varia variab variabl variable variables variance varp varraw varrawc varray verify version versions view virtual visible void wait wallet warning warnings week weekday weekofyear wellformed when whene whenev wheneve whenever where while whitespace window with within without work wrapped xdb xml xmlagg xmlattributes xmlcast xmlcolattval xmlelement xmlexists xmlforest xmlindex xmlnamespaces xmlpi xmlquery xmlroot xmlschema xmlserialize xmltable xmltype xor year year_to_month years yearweek",literal:"true false null unknown",built_in:"array bigint binary bit blob bool boolean char character date dec decimal float int int8 integer interval number numeric real record serial serial8 smallint text time timestamp tinyint varchar varchar2 varying void"},contains:[{className:"string",begin:"'",end:"'",contains:[{begin:"''"}]},{className:"string",begin:'"',end:'"',contains:[{begin:'""'}]},{className:"string",begin:"`",end:"`"},e.C_NUMBER_MODE,e.C_BLOCK_COMMENT_MODE,t,e.HASH_COMMENT_MODE]},e.C_BLOCK_COMMENT_MODE,t,e.HASH_COMMENT_MODE]}}}());