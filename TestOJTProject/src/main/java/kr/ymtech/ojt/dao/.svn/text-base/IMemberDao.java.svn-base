package kr.ymtech.ojt.dao;

import javax.servlet.http.HttpSession;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.model.MemberModel;
import open.commons.Result;

public interface IMemberDao {
	
	/**
	 * 존재하는 계정 체크
	 * @return
	 */
	public int existIdCheck(String id);
	

	/**
	 * 회원 로그인 체크
	 * @param memberModel
	 * @return
	 */
	public int loginCheck(String id, String pwd);
	
	/**
	 * 회원 정보 변경
	 * @param pwd
	 * @param id
	 * @return
	 */
	public Result<Integer> updateMember(MemberModel memberModel);
	
	/**
	 * 회원 정보 생성
	 * @return
	 */
	public Result<Integer> createMember(MemberModel memberModel);

	/**
	 * 회원 정보 삭제
	 * @param id
	 * @return
	 */
	public Result<Integer> deleteMember(String id);
	/**
	 * id로 회원 정보 조회
	 * @param id
	 * @return 
	 */
	public Result<MemberModel> getMember(String id); 

	/**
	 * 권한 정보 조회
	 * @param id
	 * @return
	 */
	public Result<MemberGrade> getMemberGrade(int id);
	
	/**
	 * 전체 회원 정보 조회
	 * @param pageNum TODO
	 * @param itemCountPerPage TODO
	 * @return
	 */
	public PaginationResult<MemberModel> getMemberInfo(int pageNum, int itemCountPerPage, String searchName, int searchAge, String searchEmail, String searchAddr, String searchTel, int searchGrade);
	
//	/**
//	 * 회원 정보와 비밀번호 업데이트
//	 * @param memberModel
//	 * @return
//	 */
//	public Result<Integer> updateMemberPwd(MemberModel memberModel);

}
