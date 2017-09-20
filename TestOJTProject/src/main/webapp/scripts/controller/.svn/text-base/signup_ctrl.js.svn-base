var myApp = angular.module("signupApp", []);

var signupCtrl = function($scope, $http) {
	$scope.id = "";
	$scope.pwd = "";
	$scope.tel = "";
	$scope.email = "";
	$scope.id_validation=false;
	
	// id, 비밀번호 안내 메시지
	$scope.id_msg = "(아이디는 영문자 또는 숫자로 5~15자 이내로 입력해주세요.)";
	$scope.pwd_msg = "(비밀번호는 8 ~ 16자 이내로 입력해 주세요. 최소 1개의 숫자와 특수 문자를 포함해야 합니다.)";

	// 입력 값 패턴
	$scope.id_re = /^[A-za-z0-9]{5,15}$/;
	$scope.email_re = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	//$scope.pwd_re = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,16}$/;
	$scope.pwd_re = /^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	$scope.tel_re = /^\d{3}-\d{3,4}-\d{4}$/;
	
	// 아이디 중복 확인
	$scope.checkId = function() {

		var req = {
			method : "GET",
			url : ctx + "/member/checkId",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			params : {
				id : $scope.id,
			}
		};

		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			$scope.userData = response.data;
			console.log(response.data);
			if ($scope.userData["code"] == 0) {
				$scope.info_id_color = "blue";
				$scope.checkId_validation = true;
			} else {
				$scope.info_id_color = "red";
				$scope.checkId_validation = false;
				$scope.id_validation=false;
			}
			$scope.id_msg = response.data["msg"];

		}, function myError(response) {
			$scope.myWelcome = response.statusText;
		});
	};

	// 아이디 미입력, 재입력, 잘못 입력 상태 시 안내메시지 출력
	$scope.idChange = function() {
			$scope.info_id_color = "black";
			$scope.id_msg = "(아이디는 영문자 또는 숫자로 5~15자 이내로 입력해주세요.)";
			$scope.id_validation = $scope.id_re.test($scope.id);
			
			console.log("$scope.id_validation",$scope.id_validation);
			console.log("$scope.id",$scope.id);
			
			$scope.checkId_validation = false;
	};

	// 비밀번호 입력 안내메시지 출력
	$scope.pwdChange = function() {
		if (!$scope.pwd_re.test($scope.pwd)) {
			$scope.info_pwd_color = "black";
			$scope.pwd_msg = "(비밀번호는 8 ~ 16자 이내로 입력해 주세요. 최소 1개의 숫자와 특수 문자를 포함해야 합니다.)";
			$scope.pwd_validation = false;
		} else {
			if ($scope.pwd == $scope.pwd_check) {
				$scope.info_pwd_color = "blue";
				$scope.pwd_msg = "(비밀번호가 확인 되었습니다)";
				$scope.pwd_validation = true;
			} else {
				$scope.info_pwd_color = "red";
				$scope.pwd_msg = "(비밀번호가 일치하지 않습니다.)";
				$scope.pwd_validation = false;
			}
		}
	};

	// 사용자 정보 추가
	$scope.signup = function() {

		if (!$scope.id_validation) {
			$scope.focus_id = true;
			alert("아이디 형식이 올바르지 않습니다.");
			return;
		}
		if (!$scope.checkId_validation) {
			alert("중복확인을 눌러주세요");
			return;
		}
		if (!$scope.pwd_validation) {
			$scope.focus_pwd = true;
			alert("비밀번호 형식이 올바르지 않습니다.");
			return;
		}
		if(""!=$scope.tel){ //연락처 입력 값이 있을 때 확인
			$scope.tel_validation=$scope.tel_re.test($scope.tel);
		 	if(!$scope.tel_validation){
		 		alert("연락처 형식이 올바르지 않습니다.");
		 		return;
		 	}
		 }
		if(""==$scope.email){ // 이메일 필수 입력 값
			alert("이메일을 입력해주세요");
			return;
		 }
		if (""!=$scope.email) { // 이메일 입력 값이 있을 때 확인
			$scope.email_validation = $scope.email_re.test($scope.email);
			if (!$scope.email_validation) {
				alert("이메일 형식이 올바르지 않습니다.");
				return;
			}
		}

		var user = {
			id : $scope.id,
			pwd : $scope.pwd,
			pwdCheck : $scope.pwd_check,
			name : $scope.name,
			age : $scope.age,
			tel : $scope.tel,
			email : $scope.email,
			addr : $scope.addr
		};

		var req = {
			method : "POST",
			url : ctx + "/member/signup",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			data : user
		};
		
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			$scope.signupResult = response.data;
			alert($scope.signupResult.msg);
			if ($scope.signupResult.code == 0) {
				// 로그인 페이지 이동
				location.href = "/";
				
			}
		}, function myError(response) {
			$scope.errMsg = response.data.msg;
			alert($scope.errMsg);
		});
	};
}

myApp.controller("signupCtrl", [ "$scope", "$http", signupCtrl ]);