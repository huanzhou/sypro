<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc/meta.jsp"></jsp:include>
<jsp:include page="inc/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(function() {
		$.ajax({
			url : 'repairController.do?repair',
			dataType : 'json',
			cache : false,
			success : function(r) {
				if (r.success) {
					location.replace('index.jsp');
				}
			},
			type : "POST",
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.responseText);
			}
		});
	});
</script>
</head>
<body>init database...
</body>
</html>
