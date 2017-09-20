package kr.ymtech.ojt.dao;


import java.util.List;

import kr.ymtech.ojt.dao.model.CommentModel;
import open.commons.Result;

public interface ICommentDao {
	
	/**
	 * 댓글 등록
	 * @param commentModel
	 * @return
	 */
	public Result<Integer> insertComment(CommentModel commentModel);
	
	/**
	 * 댓글 조회
	 * @param board_no
	 * @return
	 */
	public Result<List<CommentModel>> selectComment(String board_no);
	
	/**
	 * 댓글 삭제
	 * @param board_no
	 * @return
	 */
	public Result<Integer> deleteComment(String board_no);

}
