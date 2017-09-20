<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%@ include file="/WEB-INF/views/taglibs.jsp"%>
<%-- <%@ page session="false" %> --%>

<!-- request 객체에 저장된 contextpath를 참조하여 경로 명시 -->
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}" /> --%>

<html>
<head>
<link rel="StyleSheet" href="css/common.css" type="text/css">

<script src="${ctx}/scripts/angularjs/angular.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/login_ctrl.js" type="text/javascript"></script>
<script type="text/javascript">
	/* var ctx = "${ctx}"; */
	
	var msg = "${msg}";
	
	if( msg ) {
		alert (msg);
	}
</script>

<title>:: 게시판 회원 로그인 ::</title>
</head>

<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" class="login_bg">
	<!-- contents Area -->
	<div id="contents_login">
		<form action="${ctx}/j_security_check" method="POST" name="login" >
			<div id="login_frame" class="login_area">
				<ul>
					<li style="margin-bottom: 8px;"><span class="l_01">회원
					</span> <span class="l_02">로그인</span></li>
					<li><input class="input_id" type="text" id="id" name="j_username" type="text" autofocus placeholder="User ID" maxlength="16" /></li>
					<li><input class="input_pw" type="password" id="password" name="j_password" type="password" placeholder="Password" maxlength="16"></li>
					<li>
						<button id="login_button" type="submit" class="btn btn-primary login_btn">LOGIN</button>
					</li>
					<li class="login_check"><span style="float: right;"><a href="member/signup" id="signin_button">회원가입</a></span></li>
				</ul>
			</div>
		</form>
		<div class="l_copy_area02">Copyright ⓒ UBIQUITOUS MEDIA TECHNOLOGY. All Rights Reserved.</div>
	</div>
	<!--end contents Area -->
</body>
</html>
