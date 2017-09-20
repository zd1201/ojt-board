ojtBoard.controller("MyPageCtrl", function($scope, $http, $uibModal, $window, $rootScope) {
	
	$scope.user = user;
	$scope.pwd_msg = '(비밀번호는 8 ~ 16자 이내로 입력해 주세요. 최소 1개의 숫자와 특수 문자를 포함해야 합니다.)';
	$scope.pwd_re = /^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	$scope.modifyPwd = "";
	$scope.modifyPwdCheck = "";
	$scope.responseDataMsg = "";
	$scope.responseDataCode = 1;
	
	
	// 비밀번호 입력 안내메시지 출력
	$scope.pwdChange = function() {
		if (!$scope.pwd_re.test($scope.modifyPwd)) {
			$scope.info_pwd_color = 'black';
			$scope.pwd_msg = '(비밀번호는 8 ~ 16자 이내로 입력해 주세요. 최소 1개의 숫자와 특수 문자를 포함해야 합니다.)';
			$scope.pwd_validation = false;
		} else {
			if ($scope.modifyPwd == $scope.modifyPwdCheck) {
				$scope.info_pwd_color = 'blue';
				$scope.pwd_msg = '(비밀번호가 확인 되었습니다)';
				$scope.pwd_validation = true;
			} else {
				$scope.info_pwd_color = 'red';
				$scope.pwd_msg = '(비밀번호가 일치하지 않습니다.)';
				$scope.pwd_validation = false;
			}
		}
	};
	
	
	// 내 정보 조회
	var getMyInfo = function() {
		var req = {
			method : "GET",
			url : ctx + "/mypage/",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
		};
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			$scope.myInfo = response.data.value;
		}, function myError(response) {
			forwardErrorPages(response.status);
		});
	}
	
	// 탈퇴
	$scope.withdrawal = function(){
		//삭제 시 비밀번호 재입력 modal
		var uibModalInstance = $uibModal.open({
			ariaLabelledBy : "modal-title",
			ariaDescribedBy : "modal-body",
			templateUrl : ctx + "/templates/member/password_enter.html",
			controller : "passwordEnterCtrl",
			size : "sm",
		    keyboard: false,
		});
		
		uibModalInstance.result.then(
		function(password){
			//비밀번호 미입력
			if(password==""){
				alert("비밀번호를 입력해주세요.");
			}else{
				var req = {
						method : "POST",
						url : ctx + "/member/checkPwd",
						headers : {
							"Content-Type" : "application/json; charset=UTF-8"
						},
						data : password
					};

					$http(req) // 요청 파라미터
					.then(function mySuccess(response) {
						if(response.data.code==1){
							alert(response.data.msg);
						}
						// 비밀번호 일치 시 계정 삭제
						else{
							removeMember();
							window.location = "/j_spring_security_logout";
						}
					}, function myError(response) {
						alert(response.statusText);
					});
					
			}
	
		},
		//비밀번호 재입력 취소
		function() {
			$log.info("Modal dismissed at: " + new Date());
		});
	}
	
	// 회원 정보 삭제
	var removeMember = function() {
		var req = {
			method : "DELETE",
			url : ctx + "/mypage/",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
//			data : id
		};
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			alert(response.data.msg);
			window.location.reload(true);
		}, function myError(response) {
			forwardErrorPages(response.status);
		});
	};
	
	// 마이페이지 수정화면 이동
	
	$scope.goMyUpdate = function(){
		window.location = "#/mypage/modify";
	}
	
	// 내 정보 수정
	$scope.myInfoUpdate = function(){
		var myInfo = {
			id : $scope.myInfo.id,
			name : $scope.myInfo.name,
			age : $scope.myInfo.age,
			email : $scope.myInfo.email,
			pwd : $scope.modifyPwd,
			pwdCheck : $scope.modifyPwdCheck,
			tel : $scope.myInfo.tel,
			addr : $scope.myInfo.addr
		}
		
		var req = {
				method : "PUT",
				url : ctx + "/mypage/",
				headers : {
					"Content-Type" : "application/json; charset=UTF-8"
				},
				data : myInfo
			};

			$http(req) // 요청 파라미터
			.then(function mySuccess(response) {
//				$scope.responseDataCode = response.data.code;
//				$scope.responseDataMsg = response.data.msg;
				alert(response.data.msg);
				window.location = "#/mypage";
			}, function myError(response) {
				if(response.status == 500){
					alert(response.data.msg);
				}else{
					forwardErrorPages(response.status);
				}
			});
	}
	
	getMyInfo();
	
});