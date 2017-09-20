package kr.ymtech.ojt.service;

import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.model.MemberModel;

public interface IMemberService {

	/**
	 * 
	 * 회원 계정을 생성한다.
	 * 
	 * @param memberModel
	 * @return true 이면 회원생성, false 이면 생성 실패
	 */
	public boolean registMember(MemberModel memberModel);
	
	/**
	 * @param id
	 * @return true 계정 삭제 성공, false 계정 삭제 실패
	 */
	public boolean removeMember(String id);

	/**
	 * 아이디 존재하는지 확인한다.
	 * 
	 * @param id
	 * @return true 중복된 id 없음, false 중복된 id 있음
	 */
	public boolean checkId(String id);

	/**
	 * 로그인 아이디와 비밀번호가 맞는지 확인한다.
	 * 
	 * @param id
	 * @param pwd
	 * @return true 로그인 성공, false 로그인 실패
	 */
	public boolean checkLogin(String id, String pwd);

	/**
	 * 회원 ID로 회원 정보를 가져온다.
	 * 
	 * @param id
	 * @return 해당 id의 회원 정보
	 */
	public MemberModel getMemberInfo(String id);
	
	/**
	 * 회원 정보 전체 조회한다.
	 * @param pageNum TODO
	 * @param itemCountPerPage TODO
	 * @return 페지이네이션한 회원 정보
	 */
	public PaginationResult<MemberModel> getAllMemberInfo(int pageNum, int itemCountPerPage, String searchName, int searchAge, String searchEmail, String searchAddr, String searchTel, int searchGrade);
	
	/**
	 * 회원 정보를 업데이트한다.
	 * @return true 회원 정보 업데이트 성공, false 회원 정보 업데이트 실패
	 */
	public boolean updateMember(MemberModel memberModel);
	

//	/**
//	 * 회원 정보와 비밀번호 업데이트
//	 * @param memberModel
//	 * @return true 회원 정보와 비밀번호 업데이트 성공, false 회원 정보와 비밀번호 업데이트 실패
//	 */
//	public boolean updateMemberPwd(MemberModel memberModel);
}
