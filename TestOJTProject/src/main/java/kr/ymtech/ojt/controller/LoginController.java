package kr.ymtech.ojt.controller;

import java.util.Iterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.security.GrantedAuthorityDetail;
import kr.ymtech.ojt.service.impl.MemberServiceImpl;

/**
 * 로그인 Handles requests for the application home page.
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	@Qualifier(MemberServiceImpl.BEAN_QUALIFIER)
	private MemberServiceImpl memberService;

	/**
	 * Simply selects the login view to render by returning its name.
	 */
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String login(Locale locale, Model model) {
	// logger.info("Welcome login! The client locale is {}.", locale);
	//
	// Date date = new Date();
	// DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
	// DateFormat.LONG, locale);
	//
	// String formattedDate = dateFormat.format(date);
	//
	// model.addAttribute("serverTime", formattedDate );
	//
	// return "login";
	// }

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		ModelAndView view = new ModelAndView();

		// Login Authentication 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Session에 로그인 정보 없는 사용자 처리
		if (auth == null || !UsernamePasswordAuthenticationToken.class.isAssignableFrom(auth.getClass())) {
			logger.warn("잘못된 사용자 접근: Spring Security 정보가 없습니다.");
			view.setViewName("login");
			return view;
		}

		// Session에 로그인 정보 있는 사용자 처리
		try {

			Iterator<? extends GrantedAuthority> itrAuthority = auth.getAuthorities().iterator();
			GrantedAuthorityDetail authDetail = null;

			while (itrAuthority.hasNext()) {
				authDetail = (GrantedAuthorityDetail) itrAuthority.next();
				MemberGrade memberGrade = authDetail.getMemberGrade();

				switch (memberGrade.getId()) {
				case MemberGrade.ADMIN:
				case MemberGrade.USER: // 정상 로그인된 사용자 처리
					view.setViewName("main");
					view.addObject("user", authDetail.getMember().getId());
					view.addObject("memberGrade",memberGrade);
					break;

				case MemberGrade.INVALID_UNKNOWN_AND_ERROR: // 잘못된 사용자 처리
				case MemberGrade.UNKNOWN_USER:
				default:
					view.setViewName("login");
					break;
				}
			}

		} catch (Exception e) {
			logger.warn("잘못된 사용자 접근: 로그인 시 문제가 발생하였습니다.", e);
			view.setViewName("login");
		}

		return view;
	}

//	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" })
//	public ModelAndView main(@RequestParam("id") String id, @RequestParam("password") String password, Locale locale) {
//
//		logger.info("Welcome main! The client locale is {}.", locale);
//		if (logger.isDebugEnabled()) {
//			logger.debug("login id: " + id + "login pwd:" + password);
//		}
//		ModelAndView view = null;
//		// 로그인 확인
//		if (!memberService.checkLogin(id, password)) {
//			if (logger.isDebugEnabled()) {
//				logger.debug("로그인에 실패하였습니다.");
//				view = new ModelAndView("login");
//				view.addObject("msg", "아이디와 비밀번호를 확인하세요.");
//				return view;
//			}
//		} else {
//			view = new ModelAndView("main");
//			return view;
//		}
//		return view;
//	}
}
