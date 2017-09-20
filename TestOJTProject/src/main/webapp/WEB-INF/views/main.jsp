<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="ojtBoard">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
// 	var ctx = "${ctx}";
	var APP_NAME = "ojtBoard";
	var ojtBoard = angular.module(APP_NAME, [ "ngRoute", "ui.bootstrap", "ngFileUpload", "ngSanitize"]);
// 	var ojtBoard = angular.module(APP_NAME, [ "ngRoute", "ui.bootstrap", 'angularFileUpload' ]);
	var user = "${user}";
</script>

<script src="${ctx}/scripts/controller/ojt_board_ctrl.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/board/board_ctrl.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/board/board_view_ctrl.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/member/member_ctrl.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/member/member_view_ctrl.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/mypage/mypage_ctrl.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/scripts/controller/password_enter_ctrl.js" type="text/javascript" charset="utf-8"></script>

<title>메인화면</title>
</head>
<body ng-controller="OjtBoardCtrl" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<!--header -->
	<div id="header">
		<div id="h_logo">
			<h1 id="Logo">
				<a ng-click="goBoard()" style="cursor:pointer"><span class="logo_p">OJT</span> 게시판 관리 시스템</a>
			</h1>
			<div class="tlogin">
<!-- 				<img src="images/icon_user.png" border="0">  -->
				${user}님 환영합니다. <span class="tlogo_u"><a href="${ctx}/j_spring_security_logout">로그아웃</a></span>
			</div>
		</div>

		<!-- MENU -->
		<div id="mNavi">
			<div id="mNaviG">
				<ul>
					<li ng-repeat="menu in routeMenus" ng-if="(${memberGrade.id} == menu.grade) || (${memberGrade.id} == 0)"><a href="{{menu.getUrl()}}" ng-click="updateMenu(menu)" ng-style="menu.selected && {'color':'#d61185'}">{{menu.display}}</a></li>
				</ul>
			</div>
			<!-- END MENU-->

		</div>
	</div>
	<!-- END header-->

	<div id="container">
		<!-- cont -->
		<div id="cont" ng-view></div>
		<!-- end cont-->
	</div>


	<!--footer-->
	<div id="footer">
		<div id="fGroup" class="ptp">Copyright ⓒ UBIQUITOUS MEDIA TECHNOLOGY. All Rights Reserved.</div>
	</div>
	<!--END footer-->


</body>
</html>
