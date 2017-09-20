package kr.ymtech.ojt.controller;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.controller.model.ResponseData;
import kr.ymtech.ojt.dao.model.MemberModel;
import kr.ymtech.ojt.security.GrantedAuthorityDetail;
import kr.ymtech.ojt.service.impl.MemberServiceImpl;

@RequestMapping(value = "/member") // 모든매핑은 /member/를 상속
@RestController // 현재 클래스를 스프링에서 관리하는 컨트롤러 bean으로 생성
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	Pattern idPattern = Pattern.compile("^[A-za-z0-9]{5,15}$");
	Pattern emailPattern = Pattern
			.compile("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$");
	Pattern pwdPattern = Pattern.compile("^.*(?=^.{8,16}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");
	Pattern telPattern = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");

	Matcher idMatcher;
	Matcher emailMatcher;
	Matcher pwdMatcher;
	Matcher tellMatcher;

	@Autowired
	@Qualifier(MemberServiceImpl.BEAN_QUALIFIER)
	private MemberServiceImpl memberService;

	public MemberController() {
	}

	/**
	 * 회원가입 화면 요청을 처리합니다. (GET)
	 * 
	 * @return signup.jsp
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView signupView = new ModelAndView("/signup");
		return signupView;
	}

	/**
	 * 회원가입을 처리합니다. (POST)
	 * 
	 * @param memberModel
	 * @return responseData
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> registMember(@RequestBody MemberModel memberModel) {

		idMatcher = idPattern.matcher(memberModel.getId());
		emailMatcher = emailPattern.matcher(memberModel.getEmail());
		pwdMatcher = pwdPattern.matcher(memberModel.getPwd());
		tellMatcher = telPattern.matcher(memberModel.getTel());

		if (logger.isInfoEnabled()) {
			logger.info("MemberController - createMemberTest(계정 생성을 처리합니다.) BEGIN -----------");
		}
		ResponseData responseData = new ResponseData();

		// 사용자 정보 모두 입력하였는지 확인
		if ("".equals(memberModel.getId()) || "".equals(memberModel.getEmail()) || "".equals(memberModel.getPwd())) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("필수 입력 값을 입력하세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!idMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("아이디 입력 형식이 올바르지 않습니다.");
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

			if (!memberService.registMember(memberModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("계정 생성에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("계정 생성에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("회원가입에 성공하였습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("MemberController - createMemberTest(계정 생성을 처리합니다.) END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 중복 id 검사를 처리합니다. (GET)
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return responseData
	 */
	@RequestMapping(value = "/checkId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> checkId(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {

		if (logger.isInfoEnabled()) {
			logger.info("TestController - checkIdTest(id 조회를 처리합니다.) BEGIN -----------");
		}

		ResponseData responseData = new ResponseData();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request ID: " + id);
			}

			if (!memberService.checkId(id)) {
				responseData.setMsg("이미 사용중인 아이디입니다.");
				responseData.setCode(false);
			} else {
				responseData.setMsg("사용 가능한 아이디 입니다.");
				responseData.setCode(true);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("TestController - checkIdTest(id 조회를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 현재 로그인한 사용자가 재입력한 비밀번호를 확인합니다.
	 * 
	 * @param pwd
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkPwd", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> checkPwd(@RequestBody String password) {

		if (logger.isInfoEnabled()) {
			logger.info("TestController - getListTest(비밀번호 조회를 처리합니다.) BEGIN -----------");
		}

		ResponseData responseData = new ResponseData();
		MemberModel memberModel = getCurrentMember();
//		MemberGrade memberGrade = getMemberGrade();

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request password: " + password);
			}

			if (!memberService.checkLogin(memberModel.getId(), password)) {
				responseData.setMsg("비밀번호가 일치하지 않습니다.");
				responseData.setCode(false);
			} else {
				responseData.setMsg("비밀번호가 일치합니다.");
				responseData.setCode(true);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 
	 * 회원 목록 조회를 처리합니다. (GET)
	 * 
	 * @param request
	 * @param response
	 * @return value가 회원 목록 리스트인 responseData
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getMemberList(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("itemCountPerPage") int itemCountPerPage,
			@RequestParam("searchName") String searchName,
			@RequestParam("searchAge") int searchAge, 
			@RequestParam("searchEmail") String searchEmail,
			@RequestParam("searchAddr") String searchAddr,
			@RequestParam("searchTel") String searchTel,
			@RequestParam("searchGrade") int searchGrade, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ResponseData responseData = new ResponseData();
		MemberGrade memberGrade = getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.ADMIN != memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("memberController - getMemberList(회원 목록 조회를 처리합니다.) BEGIN -----------");
		}

		PaginationResult<MemberModel> memberLi = new PaginationResult<>();

		try {
			memberLi = memberService.getAllMemberInfo(pageNum, itemCountPerPage, searchName, searchAge, searchEmail, searchAddr, searchTel, searchGrade);
			responseData.setMsg("회원 목록 조회에 성공하였습니다.");
			responseData.setValue(memberLi);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.debug("memberLi.getTotalCount()"+memberLi.getTotalCount());
			logger.debug(memberLi.getItems().toString());
			logger.info("memberController - getMemberList(회원 목록 조회를 처리합니다.) END -----------");

		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 회원 상세 조회를 처리합니다. (GET) 조회 되는 모델의 ID를 받는다.
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return value가 회원정보인 responseData
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getMember(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		MemberModel memberModel = null;
		MemberGrade memberGrade = getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.ADMIN != memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("MemberController - getMember(회원 상세조회를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request ID: " + id);
			}
			memberModel = memberService.getMemberInfo(id);

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
			logger.info("MemberController - getMember(회원 상세조회를 처리합니다.) END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 회원정보 업데이트를 처리합니다. (PUT) 업데이트 되는 모델 업데이트 정보를 받는다.
	 * 
	 * @param id
	 * @param memberModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> updateMember(@RequestBody MemberModel memberModel, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		MemberGrade memberGrade = getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.ADMIN != memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("MemberController - updateMember(업데이트를 처리합니다.) BEGIN -----------");
		}

		idMatcher = idPattern.matcher(memberModel.getId());
		emailMatcher = emailPattern.matcher(memberModel.getEmail());
		tellMatcher = telPattern.matcher(memberModel.getTel());

		// 사용자 정보 모두 입력하였는지 확인
		if ("".equals(memberModel.getEmail())) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("이메일을 입력하세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!idMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("아이디 입력 형식이 올바르지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!emailMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("이메일 입력 형식이 올바르지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (!"".equalsIgnoreCase(memberModel.getTel()) && !tellMatcher.find()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("연락처 형식이 올바르지 않습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 비밀번호 입력 값이 있을 때만 패턴 검사
		if (!"".equals(memberModel.getPwd()) && null != memberModel.getPwd()) {
			System.out.println("memberModel.getPwdCheck()" + memberModel.getPwdCheck());
			pwdMatcher = pwdPattern.matcher(memberModel.getPwd());
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

//			// 비밀번호 수정
//			try {
//				if (logger.isDebugEnabled()) {
//					logger.debug("Request Model: " + memberModel);
//				}
////				if (!memberService.updateMemberPwd(memberModel)) {
//					if (!memberService.updateMember(memberModel)) {
//					if (logger.isDebugEnabled()) {
//						logger.debug("회원 정보 수정에 실패하였습니다.");
//					}
//					responseData.setCode(ResponseData.ERROR_CODE);
//					responseData.setMsg("회원 정보 수정에 실패하였습니다.");
//					return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//				responseData.setCode(ResponseData.SUCCESS_CODE);
//				responseData.setMsg("회원정보를 수정하였습니다.");
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//
//			if (logger.isInfoEnabled()) {
//				logger.info("MemberController - updateMember(업데이트를 처리합니다.) END -----------");
//			}
//
//			return new ResponseEntity<Object>(responseData, HttpStatus.OK);
		}
		// 비밀번호 입력 값이 없을 때 비밀번호 확인 입력 값만 입력한 경우
		else if(!"".equals(memberModel.getPwdCheck()) && memberModel.getPwdCheck() != null){
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("비밀번호를 입력해주세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request Model: " + memberModel);
			}
			if (!memberService.updateMember(memberModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("회원 정보 수정에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("회원 정보 수정에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("회원정보를 수정하였습니다.");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("MemberController - updateMember(업데이트를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 회원정보 삭제를 처리합니다. (DELETE) 삭제 되는 모델의 ID를 받는다.
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ResponseBody
	protected ResponseEntity<Object> removeMember(@RequestBody String id, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		MemberGrade memberGrade = getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.ADMIN != memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("MemberController - deleteMember(삭제를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request ID: " + id);
			}

			if (!memberService.removeMember(id)) {
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

	/**
	 * 회원 권한 가져오기
	 * 
	 * @return 현재 로그인한 회원의 권한 정보
	 */
	public MemberGrade getMemberGrade() {

		MemberGrade memberGrade = null;

		// Login Authentication 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Session에 로그인 정보 있는 사용자 처리
		try {

			Iterator<? extends GrantedAuthority> itrAuthority = auth.getAuthorities().iterator();
			GrantedAuthorityDetail authDetail = null;

			while (itrAuthority.hasNext()) {
				authDetail = (GrantedAuthorityDetail) itrAuthority.next();
				memberGrade = authDetail.getMemberGrade();
			}
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return memberGrade;
	}

	/**
	 * 현재 로그인한 회원 정보 가져오기
	 * 
	 * @return 현재 로그인한 회원 정보
	 */
	MemberModel getCurrentMember() {

		MemberModel memberModel = null;

		// Login Authentication 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Session에 로그인 정보 있는 사용자 처리
		try {

			Iterator<? extends GrantedAuthority> itrAuthority = auth.getAuthorities().iterator();
			GrantedAuthorityDetail authDetail = null;

			while (itrAuthority.hasNext()) {
				authDetail = (GrantedAuthorityDetail) itrAuthority.next();
				memberModel = authDetail.getMember();
			}
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return memberModel;
	}

	/**
	 * 회원 정보 입력 양식 체크
	 * 
	 * @param memberModel
	 * @return
	 */
	ResponseData checkPattern(MemberModel memberModel) {

		return null;
	}

}
