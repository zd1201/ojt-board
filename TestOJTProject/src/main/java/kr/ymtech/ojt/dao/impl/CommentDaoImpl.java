package kr.ymtech.ojt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import kr.ymtech.ojt.dao.ICommentDao;
import kr.ymtech.ojt.dao.IRowMapperSetter;
import kr.ymtech.ojt.dao.model.CommentModel;
import open.commons.Result;

@Repository(CommentDaoImpl.BEAN_QUALIFIER)
public class CommentDaoImpl extends GenericDaoImpl implements ICommentDao {

	public static final String BEAN_QUALIFIER = "kr.ymtech.ojt.dao.impl.CommentDaoImpl";

	public Result<Integer> deleteComment(String board_no){
		String query = getQuery("comment.delete");
		
		return this.execute(query,new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, board_no);
			}
		});
	}
	
	public Result<Integer> insertComment(CommentModel commentModel) {

		String query = getQuery("comment.insert");

		return execute(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, commentModel.getBoard_no());
				ps.setString(2, commentModel.getId());
				ps.setString(3, commentModel.getContent());
				ps.setTimestamp(4, commentModel.getRegdate());
				ps.setInt(5, commentModel.getParent());
			}
		});
	}

	@Override
	public Result<List<CommentModel>> selectComment(String board_no) {
		String query = getQuery("comment.select");

		String[] param = { board_no };
		
		return getObjectList(query, param, new IRowMapperSetter<CommentModel>() {

			public CommentModel set(ResultSet rs, int rowNum) throws SQLException {

				CommentModel comment = new CommentModel();
				
				comment.setComment_no(rs.getInt("comment_no"));
				comment.setBoard_no(rs.getInt("board_no"));
				comment.setId(rs.getString("id"));
				comment.setContent(rs.getString("content"));
				comment.setRegdate(rs.getTimestamp("regdate"));
				comment.setParent(rs.getInt("parent"));
				
				return comment;
			}
		});
//		return result;
	}

	// String query = getQuery("member.select.by.id");
	//
	// String[] param = { id };
	//
	// return getObject(query, param, new IRowMapperSetter<MemberModel>() {
	// @Override
	// public MemberModel set(ResultSet rs, int rowNum) throws SQLException {
	// MemberModel member = new MemberModel();
	//
	// member.setId(rs.getString("id"));
	// member.setName(rs.getString("name"));
	// member.setPwd(rs.getString("pwd"));
	// member.setTel(rs.getString("tel"));
	// member.setAge(rs.getInt("age"));
	// member.setAddr(rs.getString("addr"));
	// member.setEmail(rs.getString("email"));
	// member.setMemberGrade(rs.getInt("grade"));
	//
	// return member;
	// }
	// });
}
