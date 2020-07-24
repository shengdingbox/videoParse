javascript:var dur=document.getElementsByClassName("x-time-duration")[0].innerText;
var ti=document.getElementById("vinfobox").getElementsByTagName("h2")[0].innerText;
var dfn=document.getElementsByClassName("x-resolution-btn")[0].innerText;
var content="#EXTM3U\n";
_player.p2pkernel.dispatchUrlArr.forEach(function(item,index){var url=item["0"];
$.ajaxSettings.async=false;
$.get(url,function(data,status){content+="#EXTINF:0\n"+data["servers"][0]["url"]+"\n"});$.ajaxSettings.async=true});content+="#EXT-X-ENDLIST";var blob=new Blob([content],{type:"text/plain"});var url=URL.createObjectURL(blob);var aLink=document.createElement("a");aLink.href=url;aLink.download=ti+"_"+dfn+"_"+dur.replace(/:/,".")+".m3u8";/*nilaoda*/aLink.style.display="none";var event;if(window.MouseEvent){event=new MouseEvent("click")}else{event=document.createEvent("MouseEvents");event.initMouseEvent("click",true,false,window,0,0,0,0,0,false,false,false,false,0,null)}aLink.dispatchEvent(event)
