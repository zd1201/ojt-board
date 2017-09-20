ojtBoard.controller("passwordEnterCtrl", function($scope, $uibModalInstance, $http) {

	$scope.pwdCheck = "";
	
	// 비밀번호 재입력 확인 버튼 클릭
	$scope.ok = function() {
		if($scope.pwdCheck==""){
			alert("비밀번호를 입력해주세요.");
		}
		else{
			$uibModalInstance.close($scope.pwdCheck);
		}
	};

	// 비밀번호 재입력 취소 버튼 클릭
	$scope.cancel = function() {
		$uibModalInstance.dismiss("cancel");
	};
	
    $scope.DoWork = function(){
    	$scope.ok();  
  };

});
ojtBoard.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });
 
                event.preventDefault();
            }
        });
    };
});
