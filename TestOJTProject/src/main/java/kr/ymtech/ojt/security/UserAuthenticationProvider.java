package kr.ymtech.ojt.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.dao.model.MemberModel;
import kr.ymtech.ojt.service.impl.MemberServiceImpl;

public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier(MemberServiceImpl.BEAN_QUALIFIER)
	private MemberServiceImpl memberService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Object principal = authentication.getPrincipal();
		Object password = authentication.getCredentials();

		GrantedAuthorityDetail grant;
		Collection<GrantedAuthorityDetail> authorities = new ArrayList<GrantedAuthorityDetail>();

		// ID / PW가 입력되지 않은 경우
		if (principal == null || password == null || (principal.toString().trim().isEmpty() && password.toString().trim().isEmpty())) {
			grant = new GrantedAuthorityDetail(MemberGrade.getUserGrade(MemberGrade.INVALID_UNKNOWN_AND_ERROR, null, "모든 정보를 입력해 주시기 바랍니다."));
			authorities.add(grant);
			return new UsernamePasswordAuthenticationToken(principal, password, authorities);
		}

		// ID / PW 로 사용자 정보 존재하는지 확인
		boolean resultUser = memberService.checkLogin(principal.toString(), password.toString());
		if (!resultUser) { // ID/PW에 해당하는 사용자가 없을 경우
			grant = new GrantedAuthorityDetail(MemberGrade.getUserGrade(MemberGrade.UNKNOWN_USER, null, "해당 사용자가 존재하지 않습니다."));
			authorities.add(grant);
			return new UsernamePasswordAuthenticationToken(principal, password, authorities);
		}

		// 사용자 정보 불러오기
		MemberModel memberInfo = memberService.getMemberInfo(principal.toString());
		if (memberInfo == null) { // 사용자 정보를 가져오지 못했을때
			grant = new GrantedAuthorityDetail(MemberGrade.getUserGrade(MemberGrade.UNKNOWN_USER, null, "해당 사용자가 존재하지 않습니다."));
			authorities.add(grant);
			return new UsernamePasswordAuthenticationToken(principal, password, authorities);
		}

		// 사용자 권한정보 추가
		grant = new GrantedAuthorityDetail(memberInfo.getMemberGradeObj());
		grant.setMember(memberInfo);
		authorities.add(grant);

		return new UsernamePasswordAuthenticationToken(principal, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return Authentication.class.isAssignableFrom(authentication);
	}

}
