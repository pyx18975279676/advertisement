var string2Obj = function(str){
    return eval("(" + str + ")");
}

function fnLogin() {

    var oUpass = document.getElementById("upass");
    var oError = document.getElementById("error_box");
    var isError = true;
    oError.innerHTML="";
    if (oUpass.value.length > 20 || oUpass.value.length < 4) {
    oError.innerHTML = "密码请输入4-20位字符"
    isError = false;
    return;
    }
    var fd = new FormData();
    fd.append("password", oUpass.value);

    var oAjax;
    if(window.XMLHttpRequest)
    {
        oAjax = new XMLHttpRequest();
    }
    else
    {
        oAjax = new ActiveXObject("Microsoft.XMLHTTP");//IE6
    }

    oAjax.open("POST","login",true);

    oAjax.onreadystatechange=function()
    {
        if(oAjax.readyState==4)
        {
            if(oAjax.status==200)
            {
                var result = string2Obj(oAjax.responseText);
                var code = result["code"];
                if (code==0){
                   window.location.href = "index.html";
                } else {
                   oError.innerHTML="密码错误";
                }
            }
            else
            {
                oError.innerHTML="请求失败";
            }
        }
    };

    oAjax.send(fd);
}

function fnQuery() {

    var queryResult = document.getElementById("query_result");
    var oAjax;
    if(window.XMLHttpRequest)
    {
        oAjax = new XMLHttpRequest();
    }
    else
    {
        oAjax = new ActiveXObject("Microsoft.XMLHTTP");//IE6
    }

    oAjax.open("POST","query",true);

    oAjax.onreadystatechange=function()
    {
        if(oAjax.readyState==4)
        {
            if(oAjax.status==200)
            {
                var result = string2Obj(oAjax.responseText);
                var code = result["code"];
                if (code==0){
		   var tableStr = "<table border=\"1\">";
		   var objResult = result["result"];
		   for(key in objResult) {
			tableStr += "<tr><td>商品ID:" + key;
			var adFileList = objResult[key];
			for(var i in adFileList) {
		       	    tableStr += "<td>广告名称:" + adFileList[i].name + "<br/>文件路径:" + adFileList[i].path + "<br/>播放时长:" + adFileList[i].time
                       	    + "<br/><button onclick=fnDelete(\"" + adFileList[i].id + "\")" + ">删除</button></td>"
			}
			tableStr += "</tr><br/>";
		   }
		   tableStr += "</table>";
                   queryResult.innerHTML=tableStr;
                } else if(code=3) {
		   window.location.href = "login.html";
		}
            }
            else
            {
                queryResult.innerHTML="请求失败";
            }
        }
    };

    oAjax.send();
}

function fnDelete(id) {

    var fd = new FormData();
    fd.append("id", id);

    var oAjax;
    if(window.XMLHttpRequest)
    {
        oAjax = new XMLHttpRequest();
    }
    else
    {
        oAjax = new ActiveXObject("Microsoft.XMLHTTP");//IE6
    }

    oAjax.open("POST","delete",true);

    oAjax.onreadystatechange=function()
    {
        if(oAjax.readyState==4)
        {
            if(oAjax.status==200)
            {
                var result = string2Obj(oAjax.responseText);
                var code = result["code"];
                if (code==0){
                   window.location.reload();
                } else {
                   alert("错误码" + code);
                }
            }
            else
            {
                oError.innerHTML="请求失败";
            }
        }
    };

    oAjax.send(fd);
}
