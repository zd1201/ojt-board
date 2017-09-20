ojtBoard.controller("BoardCtrl", function($scope, $http, $location, $uibModal, $log, $window, Upload) {
	
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
	
	//조회 옵션 초기화
	var initialize = function(){
		$scope.search_id = null;
		$scope.search_title = null;
		$scope.search_date1 = new Date();
		$scope.search_date2 = new Date();
	}
	
	// 게시글 리스트 조회
	var getBoardInfo = function() {
		var req = {
			method : "GET",
			url : ctx + "/board/",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			params : {
				pageNum : $scope.pagination.pageNum,
				itemCountPerPage : $scope.pagination.itemCountPerPage,
				searchId : $scope.search_id != null ?  $scope.search_id : "",
				searchTitle : $scope.search_title != null ? $scope.search_title : "",
				searchDate1 : $scope.search_date1 != null ?  $scope.search_date1 : new Date(),
				searchDate2 : $scope.search_date2 != null ? $scope.search_date2 : new Date()
			}
		};
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			if(response.data.code == 0){
				$scope.boardData =  response.data.value.items;
				$scope.pagination.itemTotalCount = response.data.value.totalCount;
			}
			else{
				$scope.boardData = null;
				$scope.pagination.itemTotalCount = 0;
			}
		},function myError(response) {
			forwardErrorPages(response.status);
		});
	}
	
	// 페이지 바뀔 때 검색 호출
	$scope.retrieve =function(){
		getBoardInfo();
	};
	
	// 글쓰기 버튼 클릭
	$scope.write = function(){
		$location.url("/board/write");
	}
	
	// 미리보기 파일 선택
    $scope.onFileSelect = function(files) {
		$scope.selectedFile = files[0];
	};
	
	// 파일 사이즈 체크
    var maxSize  = 3145728;    //3MB
    
    function getTimeStamp() {
    	  var d = new Date();

    	  var s =
    	    leadingZeros(d.getFullYear(), 4) + '-' +
    	    leadingZeros(d.getMonth() + 1, 2) + '-' +
    	    leadingZeros(d.getDate(), 2) + ' ' +

    	    leadingZeros(d.getHours(), 2) + ':' +
    	    leadingZeros(d.getMinutes(), 2) + ':' +
    	    leadingZeros(d.getSeconds(), 2) + ".0";

    	  return s;
    	}



    	function leadingZeros(n, digits) {
    	  var zero = '';
    	  n = n.toString();

    	  if (n.length < digits) {
    	    for (i = 0; i < digits - n.length; i++)
    	      zero += '0';
    	  }
    	  return zero + n;
    	}

    	
	// 글쓰기 등록
	$scope.register = function(){
		
		if ($scope.selectedFile != null) {
			var size = $scope.selectedFile.size;
			
		     if(size > maxSize){
		         alert("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
		          return false;
	      }
		}	
		
		var board = {
				regdate : getTimeStamp(),
				title : $scope.title != null ?  $scope.title : "",
				content : $scope.content != null ?  $scope.content : "",
//				filedata : $scope.projectInfo.files.length < 1 ? null : $scope.projectInfo.files[0]
				filedata : $scope.selectedFile
			};
		
    	Upload.upload({
			url :  ctx + "/board/",
			method : "POST",
			params : {
				title : board.title,
				content : board.content,
				regdate : board.regdate
				
			},
			file : board.filedata 
		}).progress(function (evt) {
	        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	    }).success(function (resultData, status, headers, config) {
			alert(resultData.msg);
			if (resultData.code == 0) {
				// 게시판 페이지 이동
				location.href = "/";
			}
	    }).error(function(response){
			$scope.errMsg = response.msg;
			alert($scope.errMsg);
		});
	}

	
	// 게시판 검색 조건 조회
	$scope.search = function(){
		getBoardInfo();
	}
	
	// 게시판 검색 초기화
	$scope.init = function(){
		initialize();
		getBoardInfo();
	}
	
	// 게시글 등록 취소
	$scope.cancel = function(){
		history.go(-1);
	}
	
	// 게시글 삭제
	var removeBoard = function(board_no) {
		var req = {
			method : "DELETE",
			url : ctx + "/board/",
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			data : board_no
		};
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			alert(response.data.msg);
			getBoardInfo();
		}, function myError(response) {
			forwardErrorPages(response.status);
		});
	};
	
	//조회수 증가
	var countView = function(board_no, view){
		var board = {
				board_no : board_no,
				view : view
		}
		
		var req = {
				method : "PUT",
				url : ctx + "/board/countView",
				headers : {
					"Content-Type" : "application/json; charset=UTF-8"
				},
				data : board
			};
			$http(req) // 요청 파라미터
			.then(function mySuccess(response) {
				console.log(response.data.msg);
			}, function myError(response) {
				forwardErrorPages(response.status);
			});
	}
	
	// 게시글 상세보기 이동
	$scope.boardDetail = function(board_no, view){
		view = view + 1;
		countView(board_no, view);
		
//		var parentElem = parentSelector ? 
//				angular.element($document[0].querySelector(".modal-demo " + parentSelector)) : undefined;
		var uibModalInstance = $uibModal.open({
			ariaLabelledBy : "modal-title",
			ariaDescribedBy : "modal-body",
			templateUrl : ctx + "/templates/board/tpl_content_board_view.html",
			controller : "BoardViewCtrl",
			size : "lg",
//			appendTo: parentElem,
			keyboard: false,
			resolve : {
				board_no: function() {
					return board_no;
					}
				}
			});

			uibModalInstance.result.then(
			function(board_no) {
				removeBoard(board_no);
			},
			function() {
				$log.info("Modal dismissed at: " + new Date());
				getBoardInfo();
			});
	}
	
	// 날짜 검색

	  $scope.dateOptions1 = {
	    formatYear: 'yy',
	    maxDate: new Date(),
	    startingDay: 1
	  };
	  
	  $scope.dateOptions2 = {
		formatYear: 'yy',
		maxDate: new Date(),
		minDate: $scope.search_date1,
		startingDay: 1
	  };

	  $scope.open1 = function() {
	    $scope.popup1.opened = true;
	  };

	  $scope.open2 = function() {
	    $scope.popup2.opened = true;
	  };

	  $scope.popup1 = {
	    opened: false
	  };

	  $scope.popup2 = {
	    opened: false
	  };

	initialize();
	getBoardInfo();
});

ojtBoard.config(['$compileProvider', function ($compileProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(|blob|):/);
}]);

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

//ojtBoard.controller('appController', function ($scope, $window) {
//    var data = 'some data here...',
//        blob = new Blob([data], { type: 'text/plain' }),
//        url = $window.URL || $window.webkitURL;
//    $scope.fileUrl = url.createObjectURL(blob);
//});
