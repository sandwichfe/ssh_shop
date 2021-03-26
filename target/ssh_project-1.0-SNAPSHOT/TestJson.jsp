<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2021/3/22
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<script>
    var url = "user!loadModel";
    var params = {
        "json": "json string",
        "user.username" : "zhang",
        "users[0].username" : "zhang1",
        "users[0].password" : "1",
        "users[1].username" : "zhang2",
        "users[1].password" : "2",
    };
    $.post(url, params, function callback(data) {
        console.log(data)
    });

</script>
</body>
</html>
