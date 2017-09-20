package kr.ymtech.ojt.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.dao.model.MemberModel;

public class GrantedAuthorityDetail implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final MemberGrade memberGrade;

	private MemberModel member;

	public GrantedAuthorityDetail(MemberGrade memberGrade) {
		Assert.hasText(memberGrade.getName(), "A granted authority textual representation is required");
		this.memberGrade = memberGrade;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GrantedAuthorityDetail) {
			return memberGrade.equals(((GrantedAuthorityDetail) obj).memberGrade);
		}

		return false;
	}

	public String getAuthority() {
		return memberGrade.getName();
	}

	public MemberGrade getMemberGrade() {
		return this.memberGrade;
	}

	public int hashCode() {
		return this.memberGrade.hashCode();
	}

	public String toString() {
		return memberGrade.toString();
	}

	/**
	 * @return the member
	 */
	public MemberModel getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(MemberModel member) {
		this.member = member;
	}

}
