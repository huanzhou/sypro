<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc/meta.jsp"></jsp:include>
<jsp:include page="../inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding: 5px;">
		<div>■测试用户：test，密码：test</div>
		<div>■可以自己注册用户登录，但无权限</div>
		<div>■管理员用户：admin，密码：admin</div>
		<div>■如果数据库损坏，或admin用户密码错误，或其他数据错误</div>
		<div>
			请执行<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/init.jsp"%></div>
		<div>进行数据修复</div>
	</div>
</body>
</html>