package kr.ymtech.ojt.service;

import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.model.BoardModel;
import kr.ymtech.ojt.dao.model.MemberModel;

public interface IBoardService {
	
	/**
	 * 전체 게시판 리스트 조회한다.
	 * @param pageNum
	 * @param itemCountPerPage
	 * @param searchId
	 * @param searchTitle
	 * @param searchDate1
	 * @param searchDate2
	 * @return 페이지네이션한 게시판 리스트
	 */
	public PaginationResult<BoardModel> getAllBoardInfo(int pageNum, int itemCountPerPage, String searchId, String searchTitle, String searchDate1, String searchDate2);
	
	/**
	 * 
	 * 게시글을 생성한다.
	 * @param boardModel
	 * @return true 이면 게시글 등록, false 이면 등록 실패
	 */
	public boolean registBoard(BoardModel boardModel);
	
	/**
	 * 게시글을 삭제한다.
	 * @param board_no
	 * @return true 게시글 삭제 성공, false 게시글 삭제 실패
	 */
	public boolean removeBoard(String board_no);
	
	/**
	 * 게시글을 수정한다.
	 * @param boardModel
	 * @return true 게시글 수정 성공, false 게시글 수정 실패
	 */
	public boolean updateBoard(BoardModel boardModel);
	
	/**
	 * 게시글 상세정보 조회한다.
	 * @param boardNo
	 * @return 해당 boardNo의 게시글 정보
	 */
	public BoardModel getBoardInfo(String board_no);
	
	/**
	 * 조회수 업데이트한다.
	 * @param boardModel
	 * @return
	 */
	public boolean countView(BoardModel boardModel);
	
	/**
	 * 파일을 다운로드한다.
	 * @param board_no
	 * @return
	 */
	public BoardModel downloadFile(int board_no);
}
