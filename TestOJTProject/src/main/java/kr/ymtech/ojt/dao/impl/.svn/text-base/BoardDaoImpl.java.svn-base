package kr.ymtech.ojt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.IBoardDao;
import kr.ymtech.ojt.dao.IRowMapperSetter;
import kr.ymtech.ojt.dao.model.BoardModel;
import open.commons.Result;

@Repository(BoardDaoImpl.BEAN_QUALIFIER)
public class BoardDaoImpl extends GenericDaoImpl implements IBoardDao {

	public static final String BEAN_QUALIFIER = "kr.ymtech.ojt.dao.impl.BoardDaoImpl";

	public BoardDaoImpl() {

	}

	public PaginationResult<BoardModel> getBoardInfo(int pageNum, int itemCountPerPage, String searchId,
			String searchTitle, String searchDate1, String searchDate2) {
		// TODO Auto-generated method stub
		String sql = getQuery("board.select.all.paging");
		String query = "";
		ArrayList<Object> li = new ArrayList<>();

		// 작성자 아이디 검색 조건
		if (!"".equals(searchId) && searchId != null) {
			query += getQuery("board.select.id");
			query += " ";
			li.add("%" + searchId + "%");
		}

		// 제목 검색 조건
		if (!"".equals(searchTitle) && searchTitle != null) {
			query += getQuery("board.select.title");
			query += " ";
			li.add("%" + searchTitle + "%");
		}

		// 날짜 검색 조건
		query += getQuery("board.select.regdate");
		query += " ";
		li.add(searchDate1);
		li.add(searchDate2);

		li.add((pageNum - 1) * itemCountPerPage);
		li.add(itemCountPerPage);

		sql = sql.replace("##FIELD_FILTERING##", query);
		
		// 완성된 쿼리문
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		// 작성자 아이디 검색 조건
		if (!"".equals(searchId) && searchId != null) {
			li.add("%" + searchId + "%");
		}

		// 제목 검색 조건
		if (!"".equals(searchTitle) && searchTitle != null) {
			li.add("%" + searchTitle + "%");
		}

		li.add(searchDate1);
		li.add(searchDate2);
		
		// 쿼리 파라미터
		if (logger.isDebugEnabled()) {
			logger.debug(li);
		}
		
		PaginationResult<BoardModel> pResult = new PaginationResult<>();

		pResult.setItemCountPerPage(itemCountPerPage);
		pResult.setPageNum(pageNum);

		final int[] totalCount = new int[1];

		Result<List<BoardModel>> result = getObjectList(sql, li.toArray(), new IRowMapperSetter<BoardModel>() {

			public BoardModel set(ResultSet rs, int rowNum) throws SQLException {

				BoardModel board = new BoardModel();

				board.setBoard_no(rs.getInt("board_no"));
				board.setContent(rs.getString("content"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getString("regdate"));
				board.setView(rs.getInt("view"));
				totalCount[0] = rs.getInt("count");

				return board;
			}
		});

		pResult.setTotalCount(totalCount[0]);
		pResult.setItems(result.getData());

		return pResult;
	}

	public Result<Integer> registBoard(BoardModel boardModel) {
		String query = getQuery("board.insert");

		return execute(query, new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, boardModel.getId());
				pstmt.setString(2, boardModel.getTitle());
				pstmt.setString(3, boardModel.getContent());
				pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(boardModel.getRegdate()));
				pstmt.setString(5, boardModel.getFilename());
				pstmt.setLong(6, boardModel.getFilesize());
				pstmt.setBlob(7, boardModel.getFiledata());
			}
		});
	}

	public Result<Integer> deleteBoard(String board_no) {
		String query = getQuery("board.delete");

		return execute(query, new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, board_no);
			}
		});
	}

	@Override
	public Result<Integer> updateBoard(BoardModel boardModel) {
		String query = getQuery("board.update");
		PreparedStatementSetter pss = null;

		if (null != boardModel.getFiledata()) {

			query = query.replace("##FIELD_FILTERING##", getQuery("board.update.file"));

			pss = new PreparedStatementSetter() {
				public void setValues(PreparedStatement pstmt) throws SQLException {
					pstmt.setString(1, boardModel.getTitle());
					pstmt.setString(2, boardModel.getContent());
					pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(boardModel.getRegdate()));
					pstmt.setString(4, boardModel.getFilename());
					pstmt.setLong(5, boardModel.getFilesize());
					pstmt.setBlob(6, boardModel.getFiledata());
					pstmt.setInt(7, boardModel.getBoard_no());
				}
			};

		} else {
			query = query.replace("##FIELD_FILTERING##", "");

			pss = new PreparedStatementSetter() {
				public void setValues(PreparedStatement pstmt) throws SQLException {
					pstmt.setString(1, boardModel.getTitle());
					pstmt.setString(2, boardModel.getContent());
					pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(boardModel.getRegdate()));
					pstmt.setInt(4, boardModel.getBoard_no());
				}
			};
		}

		if (logger.isDebugEnabled()) {
			logger.debug(query);
		}

		return this.execute(query, pss);
	}

	public Result<BoardModel> getBoard(String boardNo) {

		String query = getQuery("board.select.by.board_no");

		String[] param = { boardNo };

		return getObject(query, param, new IRowMapperSetter<BoardModel>() {
			@Override
			public BoardModel set(ResultSet rs, int rowNum) throws SQLException {
				BoardModel board = new BoardModel();

				board.setBoard_no(rs.getInt("board_no"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setFilename(rs.getString("filename"));
				board.setFilesize(rs.getLong("filesize"));
				// board.setFiledata(rs.getBinaryStream("filedata"));
				return board;
			}
		});
	}

	public Result<Integer> updateView(BoardModel boardModel) {
		String query = getQuery("board.update.view");

		return execute(query, new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, boardModel.getView());
				pstmt.setInt(2, boardModel.getBoard_no());
			}
		});
	}

	@Override
	public Result<BoardModel> download(int board_no) {
		String query = getQuery("board.select.by.board_no");

		Integer[] param = { board_no };

		return getObject(query, param, new IRowMapperSetter<BoardModel>() {
			@Override
			public BoardModel set(ResultSet rs, int rowNum) throws SQLException {
				BoardModel board = new BoardModel();

				board.setBoard_no(rs.getInt("board_no"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setFilename(rs.getString("filename"));
				board.setFilesize(rs.getLong("filesize"));
				board.setFiledata(rs.getBinaryStream("filedata"));
				return board;
			}
		});
	}
}
