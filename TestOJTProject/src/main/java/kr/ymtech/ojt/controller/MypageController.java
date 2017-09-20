package kr.ymtech.ojt.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import kr.ymtech.ojt.dao.model.MemberModel;
import kr.ymtech.ojt.service.IMemberService;
import kr.ymtech.ojt.service.impl.MemberServiceImpl;

@RequestMapping(value = "/mypage") // 모든매핑은 /mypage/를 상속
@RestController
public class MypageController {
	@Autowired
	@Qualifier(MemberServiceImpl.BEAN_QUALIFIER)
	private MemberServiceImpl memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	Pattern emailPattern = Pattern
			.compile("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$");
	Pattern pwdPattern = Pattern.compile("^.*(?=^.{8,16}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");
	Pattern telPattern = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");

	Matcher emailMatcher;
	Matcher pwdMatcher;
	Matcher tellMatcher;
	
	
	MemberController mc = new MemberController();
	
	/**
	 * 마이페이지 조회를 처리합니다. (GET)
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return value가 회원정보인 responseData
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getMyInfo(HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		MemberModel memberModel = mc.getCurrentMember();
		MemberGrade memberGrade = mc.getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("MypageController - getMyInfo(마이페이지 조회를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request ID: " + memberModel.getId());
			}
			memberModel = memberService.getMemberInfo(memberModel.getId());

			if (memberModel == null) {
				logger.warn("회원 정보가 없습니다.");
				responseData.setMsg("회원 정보가 없습니다.");
				responseData.setCode(false);
			} else {
				responseData.setMsg("id로 조회한 회원 정보입니다.");
				responseData.setCode(true);
				responseData.setValue(memberModel);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("MypageController - getMyInfo(마이페이지 조회를 처리합니다.) END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}
	
	/**
	 * 회원정보 삭제를 처리합니다. (DELETE)
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteMyInfo(HttpServletRequest request,
			HttpServletResponse response) {

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
			logger.info("MemberController - deleteMember(삭제를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request ID: " + memberModel.getId());
			}

			if (!memberService.removeMember(memberModel.getId())) {
				if (logger.isDebugEnabled()) {
					logger.debug("계정 삭제에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("계정 삭제에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("계정삭제에 성공하였습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("TestController - deleteMember(삭제를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> updateMyInfo(@RequestBody MemberModel memberModel) {

		emailMatcher = emailPattern.matcher(memberModel.getEmail());
		pwdMatcher = pwdPattern.matcher(memberModel.getPwd());
		tellMatcher = telPattern.matcher(memberModel.getTel());

		if (logger.isInfoEnabled()) {
			logger.info("MyPageController - updateMyInfoTest(회원정보 변경을 처리합니다.) BEGIN -----------");
		}
		ResponseData responseData = new ResponseData();

		// 사용자 정보 모두 입력하였는지 확인
		if ("".equals(memberModel.getEmail()) || "".equals(memberModel.getPwd())) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("필수 입력 값을 입력하세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!emailMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("이메일 입력 형식이 올바르지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!memberModel.getPwdCheck().equalsIgnoreCase(memberModel.getPwd())) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("비밀번호가 일치하지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!pwdMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("비밀번호 형식이 올바르지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!"".equalsIgnoreCase(memberModel.getTel()) && !tellMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("연락처 형식이 올바르지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request Model: " + memberModel);
			}

//			if (!memberService.updateMemberPwd(memberModel)) {
				if (!memberService.updateMember(memberModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("회원정보 변경에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("회원정보 변경에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("회원정보를 수정하였습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("MyPageController - updateMyInfoTest(회원정보 변경을 처리합니다.)  END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}
	
}
