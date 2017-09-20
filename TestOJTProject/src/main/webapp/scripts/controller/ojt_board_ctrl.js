/*
 * This file is generated under this project, "TestOJTProject". 
 *
 * @author yskim
 * @copyright: 
 * @package: 
 * @license: 
 * @url: 
 * @require: 
 * @since: 2017. 8. 23. 오후 4:06:43
 */

routeMenu = function(display, url, grade, selected) {
	this.display = display;
	this.url = url;
	this.grade = grade;
	this.selected = selected;
};

routeMenu.prototype.getUrl = function() {
	return "#/" + this.url;
};


var forwardErrorPages = function(httpStatus) {
//	window.location = window.location.protocol + "://" + window.location.host + ctx + "/errorpages/" + httpStatus;
	window.location = ctx + "/errorpages/" + httpStatus;
}


ojtBoard.controller("OjtBoardCtrl", function($scope, $route, $routeParams, $location) {

	$scope.routeMenus = [ 
	                     new routeMenu("게시판", "board", 1, true), 
	                     new routeMenu("회원관리", "member", 0, null),
	                     new routeMenu("마이페이지", "mypage", 1, null), 
                     ]

	var currMenu = $location.url().split("/");
	console.log(currMenu[1]);
	if(undefined == currMenu[1]){
		$scope.routeMenus.forEach(function(menu, index){
			menu.selected = false;
			if(menu.url == "board"){
				menu.selected = true;
			}
		});
	}
	else{
		$scope.routeMenus.forEach(function(menu, index){
			menu.selected = false;
			if(menu.url == currMenu[1]){
				menu.selected = true;
			}
		});
	}
	
	$scope.checkMemus = function(grade){
		if(grade){
			return false;
		}
	}
	
	$scope.updateMenu = function(menu) {
		if (!menu) {
			return;
		}
		
		$scope.routeMenus.forEach(function(e) {
			e.selected = (e.url == menu.url);
		});
		
		console.log("$scope.routeMenus", $scope.routeMenus);
	}
	
	// 로그 클릭시 게시판 화면으로 이동
	$scope.goBoard = function(){
		$scope.routeMenus.forEach(function(menu, index){
			menu.selected = false;
			if(menu.url == "board"){
				menu.selected = true;
			}
		});
		window.location = "#/board"
	}
});

// 상단 메뉴 Route 설정
ojtBoard.config(function($routeProvider, $locationProvider) {

	$routeProvider
	.when('/', {
		templateUrl : ctx + "/templates/board/tpl_content_board.html",
	})	//
	.when('/board', {
		templateUrl : ctx + "/templates/board/tpl_content_board.html",
	})	//
	.when('/board/detail', {
		templateUrl : ctx + "/templates/board/tpl_content_board_view.html",
	})	//
	.when('/board/write', {
		templateUrl : ctx + "/templates/board/tpl_content_board_add.html",
	})	//
	.when('/member', {
		templateUrl : ctx + "/templates/member/tpl_content_member.html",
	})	//
	.when('/mypage', {
		templateUrl : ctx + "/templates/mypage/tpl_content_mypage_view.html",
	})	//
	.when('/mypage/modify', {
		templateUrl : ctx + "/templates/mypage/tpl_content_mypage_modify.html",
	})
	.when('/error', {
		templateUrl : ctx + "/templates/member/error.html",
	});
});