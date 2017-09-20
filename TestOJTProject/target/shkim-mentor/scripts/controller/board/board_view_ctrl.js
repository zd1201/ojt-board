ojtBoard.controller("BoardViewCtrl", function($scope, $http, $uibModalInstance, $uibModal, board_no, $sce, Upload) {
	
	$scope.user = user;
	
	
	// 댓글 보기
	var getComment = function(){
		var req = {
				method : "GET",
				url : ctx + "/comment/" + board_no,
				headers : {
					"Content-Type" : "application/json; charset=UTF-8"
				},
			};
			$http(req) // 요청 파라미터
			.then(function mySuccess(response) {
				if(response.data.code==0){
					$scope.commentInfo = response.data.value.data;
				}
			}, function myError(response) {
				forwardErrorPages(response.status);
			});
	}
	
	// 댓글 등록
	$scope.commentRegit = function(comment,comment_no){
		// comment
//		if(null == comment_no){
//			alert("comment_no에 null 들어옴");
//		}
//		if( undefined == comment_no){
//			comment_no = comment_no != undefined ? comment_no : null;
//			alert("답글이 아닙니다.");
//		}
		if(""==comment || null == comment){
			alert("내용을 입력해주세요");
			return;
		}
		
		var comment = {
				board_no : board_no,
				content : comment,
				parent : comment_no
		}
		
		var req = {
				method : "POST",
				url : ctx + "/comment/",
				headers : {
					"Content-Type" : "application/json; charset=UTF-8"
				},
				data : comment
			};
			$http(req) // 요청 파라미터
			.then(function mySuccess(response) {
				alert(response.data.msg);
				getComment();
			}, function myError(response) {
				forwardErrorPages(response.status);
			});
	}
	
//	// 답글 클릭시 답글 등록 폼 생성
//	$scope.showReplyForm = function(click_id){
//
//		$scope.commentForm = "<div ng-if='!updateForm'> \
//		<table width='100%' cellpadding='1' cellspacing='0'> \
//		<tbody>\
//			<tr style='padding-bottom: 20px;'>\
//				<td>\
//					<div style='padding: 10px;'>\
//						<textarea rows='10'  style='overflow:hidden; width: 100%; height: 64px; resize: none;' ng-model='reply'></textarea>\
//					</div>\
//				</td>\
//				<td style='text-align: center;'>\
//					<a style='cursor:pointer;' ng-click='replyRegit(reply)'>등록</a>\
//				</td>\
//			</tr>\
//		</tbody>\
//	</table>\
//</div>";
//		 $scope.replyHTML = function(reply_id){
//			 if(click_id == reply_id){
//				 return $sce.trustAsHtml($scope.commentForm);
//			 }
//		 }
//	}
	
	
	// 게시글 상세보기
	var getBoardDetail = function(){
		var req = {
			method : "GET",
			url : ctx + "/board/" + board_no,
			headers : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
		};
		$http(req) // 요청 파라미터
		.then(function mySuccess(response) {
			$scope.boardInfo = response.data.value;
			getComment();
		}, function myError(response) {
			forwardErrorPages(response.status);
		});
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

  	
	
	// 게시글 정보 수정
	var updateBoard = function(board) {
		
		if ($scope.selectedFile != null) {
			var size = $scope.selectedFile.size;
			
		     if(size > maxSize){
		         alert("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
		          return false;
	      }
		}	
		
//		var board = {
//				title : board.title != null ?  board.title : "",
//				content : board.content != null ?  board.content : "",
//				board_no : board.board_no,
//				//filedata : $scope.projectInfo.files.length < 1 ? null : $scope.projectInfo.files[0]
//				file : $scope.selectedFile
//			};
		
	 	Upload.upload({
			url :  ctx + "/board/update",
			method : "POST",
			params : {
				regdate: getTimeStamp(),
				title : board.title != null ?  board.title : "",
				content : board.content != null ?  board.content : "",
				board_no : board.board_no != null ?  board.board_no : ""
			},
			file : $scope.selectedFile
		}).progress(function (evt) {
	        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	    }).success(function (resultData, status, headers, config) {
			$scope.responseDataCode = resultData.code;
			$scope.responseDataMsg = resultData.msg;
			alert(responseDataMsg);
	    }).error(function(response){
	    	forwardErrorPages(response.status);
		});
	 	
//		var req = {
//			method : "PUT",
//			url : ctx + "/board/",
//			headers : {
//				"Content-Type" : "application/json; charset=UTF-8"
//			},
//			data : board
//		};
//
//		$http(req) // 요청 파라미터
//		.then(function mySuccess(response) {
////			$scope.responseDataCode = response.data.code;
////			$scope.responseDataMsg = response.data.msg;
//			alert(response.data.msg);
//		}, function myError(response) {
//			forwardErrorPages(response.status);
//		});
	};
	
	//삭제 버튼 클릭 
	$scope.remove = function() {
//		 $uibModalInstance.close(board_no);
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
							$uibModalInstance.close(board_no);
						}
					}, function myError(response) {
						alert(response.statusText);
					});
					
//				result = {"type": 1, data: password};	
//				$uibModalInstance.close(result);
		},
		//비밀번호 재입력 취소
		function() {
			$log.info("Modal dismissed at: " + new Date());
		});
	};
	
	$scope.updateForm= false;
	// 수정 버튼 클릭
	$scope.update = function() {
		$scope.updateForm = true;
	};
	
	// 닫기 버튼 클릭
	$scope.cancel = function() {
		$uibModalInstance.dismiss("cancel");
	};
	
	// 수정 취소 버튼 클릭
	$scope.updateCancel = function(){
		$scope.updateForm = false;
	}
	
	// 수정 확인 버튼 클릭
	$scope.updateOk = function(){
		updateBoard($scope.boardInfo);
		$scope.updateForm = false;
		getBoardDetail();

	}
	
	
	// 첨부파일 다운로드
	$scope.download = function(filename){
		var req = {
				url : ctx + "/board/download/" + board_no,
			    method: "GET",
			    headers: {
			       "Content-type": "application/json"
			    },
			    responseType: "arraybuffer"
		};
		
		$http(req) // 요청 파라미터
		.then(function success (response, status, headers, config) {
			console.log(response);
//			$scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
//		    var file = new Blob([response.data], { type : "text/plain" });
		    var file = new Blob([response.data]);
		    var fileURL = URL.createObjectURL(file);
//		    window.open(fileURL);
			var link=document.createElement("a");
			link.href=fileURL;
			link.download=filename;
			link.click();
//			window.open(objectUrl);
		},function myError(response){
		    alert(response.data.msg);
		});
	}
	
	// 답글 등록 폼 보여주기
	$scope.showReplyForm = function(comment){
		if(comment.reply){
			comment.reply=false;
		}else{
			comment.reply=true;
		}
	}	
	
	// 답글 등록
//	$scope.replyRegit = function(comment_no, comment){
//		alert("comment_no"+comment_no);
//		alert("/comment"+ comment);
//			
//	}
	
	getBoardDetail();
});

ojtBoard.config(["$compileProvider",
            function ($compileProvider) {
                $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
}]);
//ojtBoard.directive("compile", ['$compile', function ($compile) {
//    return function(scope, element, attrs) {
//      scope.$watch(
//        function(scope) {
//          // watch the 'compile' expression for changes
//          return scope.$eval(attrs.compile);
//        },
//        function(value) {
//          // when the 'compile' expression changes
//          // assign it into the current DOM
//          element.html(value);
//
//          // compile the new DOM and link it to the current
//          // scope.
//          // NOTE: we only compile .childNodes so that
//          // we don't get into infinite loop compiling ourselves
//          $compile(element.contents())(scope);
//        }
//    );
//  };
//}]);