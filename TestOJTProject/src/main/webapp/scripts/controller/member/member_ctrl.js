ojtBoard.controller("MemberCtrl", function($scope, $http, $filter, $location	//
		 , $route, $uibModal, $document, $log) {
	
	// Pagination
	$scope.itemCountPerPage = [
			5, 10, 15, 20
	];
	
	//페이지네이션 초기 값
	$scope.pagination = {
		pageNum : 1, // 현재 선택된 페이지 번호
		pageMaxSize : 10, // 한번에 보여질 페이지 최대 개수
		itemCountPerPage : $scope.itemCountPerPage[1], // 하나의 페이지에 보여지는 데이터 개수
		itemTotalCount : 0,
	};
	
	//페이지 보기 옵션
	$scope.pageOptions = [
			      {value: 5, name: "5개씩 보기"},
			      {value: 10, name: "10개씩 보기"},
			      {value: 15, name: "15개씩 보기"},
			      {value: 20, name: "20개씩 보기"}
	];
	
	//권한 조회 옵션
	$scope.gradeOptions = [
	              {value: 0, name: "관리자"},
	    		  {value: 1, name: "일반사용자"}
	];
	
	//조회 옵션 초기화
	var initialize = function(){
		$scope.search_grade = null;
		$scope.search_name = null;
		$scope.search_age = null;
		$scope.search_tel = null;
		$scope.search_email = null;
		$scope.search_addr = null;
	}
	// 회원 리스트 조회
	var getMemberInfo = function() {
		var req = {
			method : "GET",
			url : ctx + "/member/",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			params : {
				pageNum : $scope.pagination.pageNum,
				itemCountPerPage : $scope.pagination.itemCountPerPage,
				searchName : $scope.search_name != null ?  $scope.search_name : "",
				searchAge : $scope.search_age != null && $scope.search_age!="" ? $scope.search_age : -1,
				searchTel : $scope.search_tel != null ?  $scope.search_tel : "",
				searchGrade : $scope.search_grade != null ? $scope.search_grade : 2,
				searchEmail : $scope.search_email != null ?  $scope.search_email : "",
				searchAddr : $scope.search_addr !=null ?  $scope.search_addr : ""
			}
		};
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			
			$scope.memberData =  response.data.value.items;
			$scope.pagination.itemTotalCount = response.data.value.totalCount;
			
		}, function myError(response) {
			forwardErrorPages(response.status);
		});
	}
	
	// 페이지 바뀔 때 검색 호출
	$scope.retrieve =function(){
		getMemberInfo();
	};
	
	// 회원 정보 수정
	var updateMember = function(member) {

		var req = {
			method : "PUT",
			url : ctx + "/member/",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			data : member
		};

		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
//			$scope.responseDataCode = response.data.code;
//			$scope.responseDataMsg = response.data.msg;
			alert(response.data.msg);
			window.location.reload(true);
		}, function myError(response) {
			forwardErrorPages(response.status);
		});
	};
	
	// 회원정보 상세보기
	$scope.showMemberInfo = function(id, parentSelector, size) {
		
	    var parentElem = parentSelector ? 
	    	      angular.element($document[0].querySelector(".modal-demo " + parentSelector)) : undefined;
		
		var uibModalInstance = $uibModal.open({
			ariaLabelledBy : "modal-title",
			ariaDescribedBy : "modal-body",
			templateUrl : ctx + "/templates/member/tpl_content_member_modify.html",
			controller : "MemberViewCtrl",
			size : size,
		    appendTo: parentElem,
		    keyboard: false,
			resolve : {
				userID: function() {
					return id;
				}
			}
		});

		uibModalInstance.result.then(
		function(memberInfo) {
			// 회원정보 수정
//			if( memberViewData.type == 0 ) {
				updateMember(memberInfo);
//			}
		},
		function() {
			$log.info("Modal dismissed at: " + new Date());
		});
	}
	
	// 회원 검색 조건 조회
	$scope.search = function(){
		
		// 나이 입력 시 숫자 확인
			if($scope.search_age != null && $scope.search_age != ""){
				if(isNaN($scope.search_age)|| ($scope.search_age < 0)){
					alert("나이 입력을 확인해주세요.");
					return;
				}
			}
			getMemberInfo();
	}
	
	// 회원 검색 초기화
	$scope.init = function(){
		initialize();
		getMemberInfo();
	}
	
	initialize();
	getMemberInfo();
});