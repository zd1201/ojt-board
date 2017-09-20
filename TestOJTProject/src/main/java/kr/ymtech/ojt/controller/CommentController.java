package kr.ymtech.ojt.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.controller.model.ResponseData;
import kr.ymtech.ojt.dao.model.CommentModel;
import kr.ymtech.ojt.dao.model.MemberModel;
import kr.ymtech.ojt.service.impl.CommentServiceImpl;
import open.commons.Result;

@RequestMapping(value = "/comment")
@RestController
public class CommentController {

	@Autowired
	@Qualifier(CommentServiceImpl.BEAN_QUALIFIER)
	private CommentServiceImpl commentService;

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	MemberController mc = new MemberController();

	/**
	 * 댓글 등록을 처리합니다. (POST)
	 * @param commentModel
	 * @return responseData
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> registComment(@RequestBody CommentModel commentModel) {

		ResponseData responseData = new ResponseData();
		MemberGrade memberGrade = mc.getMemberGrade();
		MemberModel memberModel = mc.getCurrentMember();
	

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("CommentController - RegistComment(댓글 생성을 처리합니다.) BEGIN -----------");
		}

		// 사용자 정보 모두 입력하였는지 확인
		
		if("".equals(commentModel.getContent())){
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("댓글을 입력하세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

		try {
			
			Timestamp time = new Timestamp(System.currentTimeMillis());

			commentModel.setRegdate(time);
			commentModel.setId(memberModel.getId());
			
			if (logger.isDebugEnabled()) {
				logger.debug("Request Model: " + commentModel);
			}

			if (!commentService.registComment(commentModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("댓글 생성에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("댓글 등록에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("댓글 등록에 성공하였습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("CommentController - RegistComment(댓글 생성을 처리합니다.) END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}
	
	/**
	 * 댓글 정보를 가져옵니다. (GET)
	 * @param board_no
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/{board_no}", method = RequestMethod.GET) 
	@ResponseBody
	public ResponseEntity<Object> getComment(
	@PathVariable("board_no") String board_no,
	HttpServletRequest request, HttpServletResponse response) {
	
		MemberGrade memberGrade = mc.getMemberGrade();
		ResponseData responseData = new ResponseData();
		
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}
		if (logger.isInfoEnabled()) {
			logger.info("CommentController - getComment(댓글 조회를 처리합니다.) BEGIN -----------");
		}

		
		Result<List<CommentModel>> commentLi = new Result<List<CommentModel>>() ;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request board_no: " + board_no);
			}
			commentLi = commentService.getComment(board_no);
			
			if(commentLi.getData().size() == 0){
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("해당 게시글의 댓글이 존재하지 않습니다.");
			}
			else{
				responseData.setCode(ResponseData.SUCCESS_CODE);
				responseData.setMsg("댓글 조회에 성공하였습니다.");
				responseData.setValue(commentLi);
				if(logger.isDebugEnabled()){
					logger.debug(commentLi.getData().toString());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("CommentController -  getComment(댓글 조회를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
			
	}
}
