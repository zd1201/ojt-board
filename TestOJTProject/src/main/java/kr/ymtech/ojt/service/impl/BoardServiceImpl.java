package kr.ymtech.ojt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.dao.IBoardDao;
import kr.ymtech.ojt.dao.impl.BoardDaoImpl;
import kr.ymtech.ojt.dao.model.BoardModel;
import kr.ymtech.ojt.dao.model.BoardModel;
import kr.ymtech.ojt.service.IBoardService;
import open.commons.Result;

@Service
@Qualifier(BoardServiceImpl.BEAN_QUALIFIER)
public class BoardServiceImpl extends GenericService implements IBoardService {
	
	public static final String BEAN_QUALIFIER = "kr.ymtech.ojt.service.impl.BoardServiceImpl";

	
	@Autowired
	@Qualifier(BoardDaoImpl.BEAN_QUALIFIER)
	private IBoardDao boardDao;
	
	
	public PaginationResult<BoardModel> getAllBoardInfo(int pageNum, int itemCountPerPage, String searchId, String searchTitle, String searchDate1, String searchDate2) {
		PaginationResult<BoardModel> result = boardDao.getBoardInfo(pageNum, itemCountPerPage, searchId, searchTitle, searchDate1, searchDate2);
		
		if (result == null) {
			result = null;
			logger.warn("게시글 정보가 없습니다.");
		}
		
		return result;
	}


	@Override
	public boolean registBoard(BoardModel boardModel) {
		return boardDao.registBoard(boardModel).getResult();
	}


	public boolean removeBoard(String board_no) {
		return boardDao.deleteBoard(board_no).getResult();
	}


	public boolean updateBoard(BoardModel boardModel) {
		return boardDao.updateBoard(boardModel).getResult();
	}


	public BoardModel getBoardInfo(String board_no) {
		Result<BoardModel> resultBoard = boardDao.getBoard(board_no);
		
		if (!resultBoard.getResult()) {
			logger.warn("게시글(" + board_no + ")가 존재하지 않습니다. " + resultBoard.getMessage());
		}
		
		return resultBoard.getData();
	}


	@Override
	public boolean countView(BoardModel boardModel) {
		return boardDao.updateView(boardModel).getResult();
	}


	@Override
	public BoardModel downloadFile(int board_no) {
		Result<BoardModel> resultBoard = boardDao.download(board_no);
		if (!resultBoard.getResult()) {
			logger.warn("게시글(" + board_no + ")가 존재하지 않습니다. " + resultBoard.getMessage());
		}
		return resultBoard.getData();
	}
}
