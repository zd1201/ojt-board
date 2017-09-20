package kr.ymtech.ojt.service;

import java.util.List;

import kr.ymtech.ojt.dao.model.CommentModel;
import open.commons.Result;

public interface ICommentService {
	/**
	 * 
	 * 댓글을 생성한다.
	 * @param boardModel
	 * @return true 이면 게시글 등록, false 이면 등록 실패
	 */
	public boolean registComment(CommentModel commentModel);
	
	/**
	 * 댓글 정보를 가져온다.
	 * @param board_no
	 * @return
	 */
	public Result<List<CommentModel>> getComment(String board_no);
	
	/**
	 * 댓글을 삭제한다.
	 * @param board_no
	 * @return
	 */
	public boolean removeComment(String board_no);
}