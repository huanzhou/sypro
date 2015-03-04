<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>jQuery EasyUI Sypro2.0.5 springMvc+hibernate4+easyui</title>
<jsp:include page="inc/meta.jsp"></jsp:include>
<jsp:include page="inc/easyui.jsp"></jsp:include>
</head>
<body id="indexLayout" class="easyui-layout" fit="true">
	<div region="north" href="userController.do?north" style="height:60px;overflow: hidden;background: url('images/SyProLogo.png') repeat-y;"></div>
	<div region="west" href="userController.do?west" title="导航" split="false" style="width:200px;overflow: hidden;"></div>
	<div region="center" href="userController.do?center" title="欢迎使用SyPro系统v2.0" style="overflow: hidden;"></div>
	<div region="south" href="userController.do?south" style="height:20px;overflow: hidden;"></div>
	<jsp:include page="user/loginAndReg.jsp"></jsp:include>
</body>
</html>