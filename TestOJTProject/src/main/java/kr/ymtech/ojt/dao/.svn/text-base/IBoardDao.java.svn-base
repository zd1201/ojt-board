package kr.ymtech.ojt.dao;

import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.model.BoardModel;
import open.commons.Result;

public interface IBoardDao {
	
	/**
	 * 전체 게시판 리스트 조회
	 * @param pageNum TODO
	 * @param itemCountPerPage TODO
	 * @return
	 */
	public PaginationResult<BoardModel> getBoardInfo(int pageNum, int itemCountPerPage, String searchId, String searchTitle, String searchDate1, String searchDate2);
	
	/**
	 * 게시글 등록
	 * @param boardrModel
	 * @return
	 */
	public Result<Integer> registBoard(BoardModel boardModel);

	/**
	 * 게시글 삭제
	 * @param board_no
	 * @return
	 */
	public Result<Integer> deleteBoard(String board_no);
	
	/**
	 * 게시글 수정
	 * @param boardModel
	 * @return
	 */
	public Result<Integer> updateBoard(BoardModel boardModel);
	
	/**
	 * 게시글 상세조회
	 * @param boardNo
	 * @return
	 */
	public Result<BoardModel> getBoard(String board_no); 
	
	/**
	 * 조회수 업데이트
	 * @param boardModel
	 * @return
	 */
	public Result<Integer> updateView(BoardModel boardModel);

	/**
	 * 파일 다운로드
	 * @param board_no
	 * @return
	 */
	public Result<BoardModel> download(int board_no);
	
}
