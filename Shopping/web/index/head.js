window.Douban=window.Douban||{};var Do=function(){Do.actions.push([].slice.call(arguments))};Do.ready=function(){Do.actions.push([].slice.call(arguments))};Do.add=Do.define=function(a,b){Do.mods[a]=b};Do.global=function(){Do.global.mods=Array.prototype.concat(Do.global.mods,[].slice.call(arguments))};Do.global.mods=[];Do.mods={};Do.actions=[];function set_cookie(g,f,d,e){var b=new Date(),a,c;b.setTime(b.getTime()+((f||30)*24*60*60*1000));a="; expires="+b.toGMTString();for(c in g){document.cookie=c+"="+g[c]+a+"; domain="+(d||"douban.com")+"; path="+(e||"/")}}function get_cookie(b){var e=b+"=",a=document.cookie.split(";"),d,f;for(d=0;d<a.length;d++){f=a[d];while(f.charAt(0)==" "){f=f.substring(1,f.length)}if(f.indexOf(e)===0){return f.substring(e.length,f.length).replace(/\"/g,"")}}return null}Douban.init_show_login=function(a){Do("dialog",function(){var b="/j/misc/login_form";dui.Dialog({title:"登录",url:b,width:/device-mobile/i.test(document.documentElement.className)?document.documentElement.offsetWidth*0.9:350,cache:true,callback:function(c,d){d.node.addClass("dialog-login");d.node.find("h2").css("display","none");d.node.find(".hd h3").replaceWith(d.node.find(".bd h3"));d.node.find("form").css({border:"none",width:"auto",padding:"0"});d.update()}}).open()})};Do(function(){(function(){if("localStorage" in window){return}window.localStorage=function(){var e=document;if(!e.documentElement.addBehavior){throw"don't support localstorage or userdata.";return}var c="_localstorage_ie";var d=e.createElement("input");d.type="hidden";var b=function(f){return function(){e.body.appendChild(d);d.addBehavior("#default#userData");var h=new Date();h.setDate(h.getDate()+365);d.expires=h.toUTCString();d.load(c);var g=f.apply(d,arguments);e.body.removeChild(d);return g}};return{getItem:b(function(f){return this.getAttribute(f)}),setItem:b(function(f,g){this.setAttribute(f,g);this.save(c)}),removeItem:b(function(f){this.removeAttribute(f);this.save(c)}),clear:b(function(){var g=this.XMLDocument.documentElement.attributes;for(var h=0,f;f=g[h];h++){this.removeAttribute(f.name)}this.save(c)})}}()})();$(window).one("load",function(){var b=localStorage.getItem("report");if(!b){return}b=b.split("_moreurl_separator_");var c=function(d){if(d==""){c(b.shift());return}$.get(typeof _MOREURL_REQ=="undefined"?"/stat.html?"+d:_MOREURL_REQ+"?"+d,function(){if(b.length){c(b.shift());localStorage.setItem("report",b.join("_moreurl_separator_"));return}localStorage.removeItem("report")})};c(b.shift())});function a(b,e){var d=["ref="+encodeURIComponent(location.pathname)];for(var c in e){if(e.hasOwnProperty(c)){d.push(c+"="+e[c])}}if(window._SPLITTEST){d.push("splittest="+window._SPLITTEST)}localStorage.setItem("report",(localStorage.getItem("report")||"")+"_moreurl_separator_"+d.join("&"))}window.moreurl=a;$(document).click(function(c){var d=c.target,b=$(d).data("moreurl-dict");if(b){a(d,b)}});$.ajax_withck=function(b){if(b.type=="POST"){b.data=$.extend(b.data||{},{ck:get_cookie("ck")})}return $.ajax(b)};$.postJSON_withck=function(b,c,d){return $.post_withck(b,c,d,"json")};$.post_withck=function(b,d,e,c){if($.isFunction(d)){c=e;e=d;d={}}return $.ajax({type:"POST",url:b,data:$.extend(d,{ck:get_cookie("ck")}),success:e,dataType:c||"text"})};$("html").click(function(d){var b=$(d.target),c=b.attr("class");if(!c){return}$(c.match(/a_(\w+)/gi)).each($.proxy(function(e,g){var f=Douban[g.replace(/^a_/,"init_")];if(typeof f==="function"){d.preventDefault();f.call(this,d)}},b[0]))})});