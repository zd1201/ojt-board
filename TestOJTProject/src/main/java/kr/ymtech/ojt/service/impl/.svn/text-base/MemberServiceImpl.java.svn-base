package kr.ymtech.ojt.service.impl;

import org.hibernate.type.NullableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.IMemberDao;
import kr.ymtech.ojt.dao.impl.MemberDaoImpl;
import kr.ymtech.ojt.dao.model.MemberModel;
import kr.ymtech.ojt.service.IMemberService;
import open.commons.Result;

@Service
@Qualifier(MemberServiceImpl.BEAN_QUALIFIER)
public class MemberServiceImpl extends GenericService implements IMemberService {

	public static final String BEAN_QUALIFIER = "kr.ymtech.ojt.service.impl.MemberServiceImpl";

	@Autowired
	@Qualifier(MemberDaoImpl.BEAN_QUALIFIER)
	private IMemberDao memberDao;

	public MemberServiceImpl() {
	}

	@Override
	public boolean registMember(MemberModel memberModel) {

		// 존재하는 id 확인
		int existionCheck = memberDao.existIdCheck(memberModel.getId());

		// 이미 존재하는 id일 경우
		if (existionCheck > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("존재하는 id 입니다.");
			}
		}
		return memberDao.createMember(memberModel).getResult();
	}

	@Override
	public boolean checkId(String id) {

		// 존재하는 id 확인
		int existionCheck = memberDao.existIdCheck(id);

		return !(existionCheck > 0);
	}

	@Override
	public boolean checkLogin(String id, String pwd) {

		return memberDao.loginCheck(id, pwd) > 0;
	}

	/**
	 * 
	 * @param id
	 * @return {@link NullableType}
	 */
	@Override
	public MemberModel getMemberInfo(String id) {

		MemberModel member = null;
		Result<MemberModel> resultMember = memberDao.getMember(id);
		if (!resultMember.getResult()) {
			logger.warn("사용자(" + id + ")가 존재하지 않습니다. " + resultMember.getMessage());
			return member;
		}
		member = resultMember.getData();

		Result<MemberGrade> resultGrade = memberDao.getMemberGrade(member.getMemberGrade());
		if (!resultGrade.getResult()) {
			logger.warn("ID(" + member.getMemberGrade() + ")에 해당하는 사용자 권한이 존재하지 않습니다. " + resultGrade.getMessage());
			return member;
		}
		MemberGrade grade = resultGrade.getData();

		// MEMBER 정보에 UserGrade 정보 추가
		member.setMemberGradeObj(grade);

		return member;

	}

	@Override
	public PaginationResult<MemberModel> getAllMemberInfo(int pageNum, int itemCountPerPage, String searchName, int searchAge, String searchEmail, String searchAddr, String searchTel, int searchGrade) {
		
		PaginationResult<MemberModel> result = memberDao.getMemberInfo(pageNum, itemCountPerPage, searchName, searchAge, searchEmail, searchAddr, searchTel, searchGrade);
		
		if (result == null) {
			result = null;
			logger.warn("회원 정보가 없습니다.");
		}
		
		return result;
	}

	@Override
	public boolean updateMember(MemberModel memberModel) {
		
		return memberDao.updateMember(memberModel).getResult();
	}

//	@Override
//	public boolean updateMemberPwd(MemberModel memberModel) {
//		
//		return memberDao.updateMemberPwd(memberModel).getResult();
//	}

	@Override
	public boolean removeMember(String id) {
		
		return memberDao.deleteMember(id).getResult();
	}
}
