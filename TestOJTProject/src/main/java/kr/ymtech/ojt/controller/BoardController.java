package kr.ymtech.ojt.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.ymtech.ojt.controller.model.MemberGrade;
import kr.ymtech.ojt.controller.model.PaginationResult;
import kr.ymtech.ojt.controller.model.ResponseData;
import kr.ymtech.ojt.dao.model.BoardModel;
import kr.ymtech.ojt.dao.model.MemberModel;
import kr.ymtech.ojt.service.impl.BoardServiceImpl;
import kr.ymtech.ojt.service.impl.CommentServiceImpl;

@RequestMapping(value = "/board") // 모든매핑은 /board/를 상속
@RestController
public class BoardController {

	@Autowired
	@Qualifier(BoardServiceImpl.BEAN_QUALIFIER)
	private BoardServiceImpl boardService;

	@Autowired
	@Qualifier(CommentServiceImpl.BEAN_QUALIFIER)
	private CommentServiceImpl commentSerivce;

	private static final int FILEMAXSIZE = 3145728;

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	MemberController mc = new MemberController();

	/**
	 * 게시판 리스트를 조회한다 (GET)
	 * 
	 * @param pageNum
	 * @param itemCountPerPage
	 * @param searchId
	 * @param searchTitle
	 * @param searchDate1
	 * @param searchDate2
	 * @param request
	 * @param response
	 * @return0
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getBoardList(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("itemCountPerPage") int itemCountPerPage, 
			@RequestParam("searchId") String searchId,
			@RequestParam("searchTitle") String searchTitle, 
			@RequestParam("searchDate1") String searchDate1,
			@RequestParam("searchDate2") String searchDate2, 
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		MemberGrade memberGrade = mc.getMemberGrade();
		ResponseData responseData = new ResponseData();

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("boardController - getBoardList(게시판 목록 조회를 처리합니다.) BEGIN -----------");
			logger.debug("searchId: " + searchId + "searchTitle: " + searchTitle + "searchDate: "
					+ searchDate1.substring(0, 10) + "~" + searchDate2);
		}

		searchDate1 = searchDate1.substring(0, 10);
		searchDate2 = searchDate2.substring(0, 10);

		PaginationResult<BoardModel> boardLi = new PaginationResult<>();

		try {
			boardLi = boardService.getAllBoardInfo(pageNum, itemCountPerPage, searchId, searchTitle, searchDate1,
					searchDate2);

			if (boardLi.getItems().size() == 0) {
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("검색된 결과가 없습니다.");
				

			} else {
				responseData.setCode(ResponseData.SUCCESS_CODE);
				responseData.setMsg("게시판 목록 조회 성공하였습니다.");
				responseData.setValue(boardLi);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.debug(boardLi.getItems().toString());
			logger.info("boardController - getBoardList(게시판 목록 조회를 처리합니다.) END -----------");

		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 게시글을 생성합니다. (POST)
	 * 
	 * @param boardModel
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> registBoard(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("regdate") String regdate,
			@RequestPart(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request,HttpServletResponse response) {

		MemberModel memberModel = mc.getCurrentMember();
		ResponseData responseData = new ResponseData();

		// Timestamp time = new Timestamp(System.currentTimeMillis());
		MemberGrade memberGrade = mc.getMemberGrade();
		BoardModel boardModel = new BoardModel();

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if ("".equals(title)) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("제목을 입력하세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (logger.isInfoEnabled()) {
			logger.info("boardController - getBoardList(게시글을 등록 합니다.) BEGIN -----------");
			logger.debug("timestamp:" + regdate);
		}

		try {
			// InputStream previewInputStream = null;

			boardModel.setId(memberModel.getId());
			boardModel.setTitle(title);
			boardModel.setContent(content);
			boardModel.setRegdate(regdate);

			// file을 로딩한다.
			if (file != null) {

				if (file.getSize() > FILEMAXSIZE) {
					responseData.setCode(ResponseData.ERROR_CODE);
					responseData.setMsg("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
					return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
				}

				// previewInputStream = file.getInputStream();
				boardModel.setFiledata(file.getInputStream());

				String fileName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");

				boardModel.setFilename(fileName);
				boardModel.setFilesize(file.getSize());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Request Model: " + boardModel.toString());
			}

			if (!boardService.registBoard(boardModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("게시글 등록에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("게시글 등록에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("게시글 등록에 성공하였습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("boardController - getBoardList(게시글을 등록 합니다.) END -----------");

		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 게시글을 삭제합니다. (DELETE)
	 * 
	 * @param board_no
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> removeBoard(@RequestBody String board_no, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		MemberGrade memberGrade = mc.getMemberGrade();
		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoarderController - deleteBoarder(삭제를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request board_no: " + board_no);
			}

			// 게시글 삭제
			if (!boardService.removeBoard(board_no)) {
				if (logger.isDebugEnabled()) {
					logger.debug("게시글 삭제에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("게시글 삭제에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// 댓글 삭제
			if (!commentSerivce.removeComment(board_no)) {
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("댓글 삭제에 실패하였습니다.");
				// return new ResponseEntity<Object>(responseData,
				// HttpStatus.INTERNAL_SERVER_ERROR);
			}
			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("게시글 삭제에 성공하였습니다.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoarderController - deleteBoarder(삭제를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	/**
	 * 게시글을 수정합니다. (PUT)
	 * 
	 * @param boardModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> updateBoard(@RequestParam("regdate") String regdate,
			@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("board_no") int board_no, @RequestPart(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		// public ResponseEntity<Object> updateBoard(
		// @RequestBody BoardModel boardModel,
		// HttpServletRequest request, HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		// Timestamp time = new Timestamp(System.currentTimeMillis());
		MemberGrade memberGrade = mc.getMemberGrade();
		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		// 사용자 정보 모두 입력하였는지 확인
		if ("".equals(title)) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("제목을 입력하세요.");
			return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - updateBoard(업데이트를 처리합니다.) BEGIN -----------");
		}

		try {

			BoardModel boardModel = new BoardModel();

			// file을 로딩한다.
			if (file != null) {

				if (file.getSize() > FILEMAXSIZE) {
					responseData.setCode(ResponseData.ERROR_CODE);
					responseData.setMsg("첨부파일 사이즈는 3MB 이내로 등록 가능합니다.");
					return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
				}

				// 파일 이름 인코딩
				String fileName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");

				boardModel.setFilename(fileName);
				boardModel.setFiledata(file.getInputStream());
				boardModel.setFilesize(file.getSize());

			}

			boardModel.setRegdate(regdate);
			boardModel.setTitle(title);
			boardModel.setContent(content);
			boardModel.setBoard_no(board_no);

			if (logger.isDebugEnabled()) {
				logger.debug("Request Model: " + boardModel.toString());
			}
			if (!boardService.updateBoard(boardModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("게시글 수정에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("게시글 수정에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("게시글 수정하였습니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - updateBoard(업데이트를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	@RequestMapping(value = "/{board_no}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getBoard(@PathVariable("board_no") String board_no, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		BoardModel boardModel = null;
		MemberGrade memberGrade = mc.getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - getBoard(게시글 상세조회를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request boardNo: " + board_no);
			}
			boardModel = boardService.getBoardInfo(board_no);

			if (boardModel == null) {
				logger.warn("게시글 정보가 없습니다.");
				responseData.setMsg("게시글 정보가 없습니다.");
				responseData.setCode(false);
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseData.setMsg("boardNo로 조회한 게시글 상세정보입니다.");
				responseData.setCode(true);
				responseData.setValue(boardModel);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - getBoard(게시글 상세조회를 처리합니다.) END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	@RequestMapping(value = "/download/{board_no}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> download(@PathVariable("board_no") int board_no, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		BoardModel boardModel = null;
		MemberGrade memberGrade = mc.getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - download(다운로드를 처리합니다.) BEGIN -----------");
		}

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Request boardNo: " + board_no);
			}
			boardModel = boardService.downloadFile(board_no);

			if (boardModel == null) {
				logger.warn("게시글 정보가 없습니다.");
				responseData.setMsg("다운로드 실패하였습니다.");
				responseData.setCode(false);
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseData.setMsg("다운로드 성공하였습니다.");

				responseData.setCode(true);

				//responseData.setValue(boardModel);

				InputStream fileInputStream = boardModel.getFiledata();

				OutputStream output = response.getOutputStream();

				IOUtils.copyLarge(fileInputStream, output);
				
				if(response.getOutputStream() == null){
					logger.warn("게시글 정보가 없습니다.");
					responseData.setMsg("다운로드 실패하였습니다.");
					responseData.setCode(false);
					return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				output.flush();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - download(다운로드를 처리합니다.) END -----------");
		}
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	// /**
	// * 작성자 id 검사를 처리합니다. (GET)
	// *
	// * @param id
	// * @param request
	// * @param response
	// * @return responseData
	// */
	// @RequestMapping(value = "/checkId", method = RequestMethod.GET)
	// @ResponseBody
	// public ResponseEntity<Object> checkId(@RequestParam("id") String id,
	// HttpServletRequest request,
	// HttpServletResponse response) {
	//
	// MemberModel memberModel = mc.getCurrentMember();
	// MemberGrade memberGrade = mc.getMemberGrade();
	//
	// if (logger.isInfoEnabled()) {
	// logger.info("TestController - checkIdTest(id 조회를 처리합니다.) BEGIN
	// -----------");
	// }
	//
	// ResponseData responseData = new ResponseData();
	//
	// try {
	// if (logger.isDebugEnabled()) {
	// logger.debug("Request ID: " + id);
	// }
	//
	// if (!id.equals(memberModel.getId())) {
	// responseData.setCode(false);
	// } else {
	// responseData.setCode(true);
	// }
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	//
	// if (logger.isInfoEnabled()) {
	// logger.info("TestController - checkIdTest(id 조회를 처리합니다.) END
	// -----------");
	// }
	//
	// return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	// }

	/**
	 * 조회수 증가를 처리합니다. (POST)
	 * 
	 * @param boardModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/countView", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> countView(@RequestBody BoardModel boardModel, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseData responseData = new ResponseData();
		MemberGrade memberGrade = mc.getMemberGrade();

		// 접근권한 없음을 반환
		if (MemberGrade.UNKNOWN_USER == memberGrade.getId()
				|| MemberGrade.INVALID_UNKNOWN_AND_ERROR == memberGrade.getId()) {
			responseData.setCode(ResponseData.ERROR_CODE);
			responseData.setMsg("접근 권한이 없습니다.");
			return new ResponseEntity<Object>(responseData, HttpStatus.FORBIDDEN);
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - countView(업데이트를 처리합니다.) BEGIN -----------");
		}

		// 사용자 정보 모두 입력하였는지 확인
		try {

			if (logger.isDebugEnabled()) {
				logger.debug("Request Model: " + boardModel.toString());
			}
			if (!boardService.countView(boardModel)) {
				if (logger.isDebugEnabled()) {
					logger.debug("조회수 증가에 실패하였습니다.");
				}
				responseData.setCode(ResponseData.ERROR_CODE);
				responseData.setMsg("조회수 증가에 실패하였습니다.");
				return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			responseData.setCode(ResponseData.SUCCESS_CODE);
			responseData.setMsg("조회수 증가하였습니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (logger.isInfoEnabled()) {
			logger.info("BoardController - countView(업데이트를 처리합니다.) END -----------");
		}

		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}
}
