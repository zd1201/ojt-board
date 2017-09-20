package kr.ymtech.ojt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.ymtech.ojt.dao.ICommentDao;
import kr.ymtech.ojt.dao.impl.CommentDaoImpl;
import kr.ymtech.ojt.dao.model.CommentModel;
import kr.ymtech.ojt.service.ICommentService;
import open.commons.Result;

@Service
@Qualifier(CommentServiceImpl.BEAN_QUALIFIER)
public class CommentServiceImpl extends GenericService implements ICommentService{
	
	public static final String BEAN_QUALIFIER = "kr.ymtech.ojt.service.impl.CommentServiceImpl";
	
	@Autowired
	@Qualifier(CommentDaoImpl.BEAN_QUALIFIER)
	private ICommentDao commentDao;
	
	public CommentServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean registComment(CommentModel commentModel) {
		return commentDao.insertComment(commentModel).getResult();
	}

	@Override
	public Result<List<CommentModel>> getComment(String board_no) {
		return commentDao.selectComment(board_no);
	}

	@Override
	public boolean removeComment(String board_no) {
		// TODO Auto-generated method stub
		return commentDao.deleteComment(board_no).getResult();
	}

}
