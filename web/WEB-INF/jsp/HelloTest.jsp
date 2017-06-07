<%--
  Created by IntelliJ IDEA.
  User: Esong
  Date: 2017/5/31
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
    <p id="test">${message}</p>
</body>
<script>
    var xmlHttp;
    //获得xmlHttp对象
    function createXMLHttp() {
        //对于大多数浏览器适用
        var xmlHttp;
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        }
        //考虑浏览器的兼容性
        if (window.ActiveXObject) {
            xmlHttp = new ActiveXOject("Microsoft.XMLHTTP");
            if (!xmlHttp) {
                xmlHttp = new ActiveXOject("Msxml2.XMLHTTP");
            }
        }
        return xmlHttp;
    }
    $(document).ready(function () {
        var temp = document.getElementById("test").innerHTML;
        alert(temp);
        //向服务器发送内容，用到XmlHttp对象
        xmlHttp = createXMLHttp();
        //给服务器发送数据，escape()不加中文会有问题
        var url = "http://app.cuit.edu.cn/v1/oauth/token";
        //true表示js的脚本会在send()方法之后继续执行而不会等待来自服务器的响应
        xmlHttp.open("POST",url,true);
        //xmlHttp绑定回调方法，这个方法会在xmlHttp状态改变的时候调用,xmlHttp状态有0-4，
        //我们只关心4，4表示完成
        xmlHttp.onreadystatechange=Callback;
        //定义传输的文件HTTP头信息
        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        var postStr = "grant_type=authorization_code&client_id=cuitevaluate&client_secret=evaluate2017&code="+temp;
        xmlHttp.send(postStr);
    });
    function Callback() {
        alert("xmlHttp.readyState="+xmlHttp.readyState+",xmlHttp.status="+xmlHttp.status);
        //4代表完成
        if(xmlHttp.readyState == 4) {
            //200代表服务器响应成功，404代表资源未找到，500服务器内部错误
            if (xmlHttp.status == 200) {
                //交互成功获得响应的数据，是文本格式,并解析
                // if (xmlHttp.responseText == "") {
                //     alert("没有查询到信息");
                //     //onload();
                //     return 0;
                // }
                //数据处理
                alert(xmlHttp.responseText);
            }
        }
    }
</script>
</html>
