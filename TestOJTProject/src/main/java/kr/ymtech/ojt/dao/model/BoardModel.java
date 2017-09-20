package kr.ymtech.ojt.dao.model;

import java.io.InputStream;

public class BoardModel {

	public int board_no;
	
	private String id;
	
	private String title;
	
	private String content;
	
	private String regdate;
	
	private String filename;
	
	private long filesize;
	
	private InputStream filedata;
	
//	private MultipartFile file;
	
	private int view;

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long l) {
		this.filesize = l;
	}

	public InputStream getFiledata() {
		return filedata;
	}

	public void setFiledata(InputStream previewInputStream) {
		this.filedata = previewInputStream;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return "BoardModel [board_no=" + board_no + ", id=" + id + ", title=" + title + ", content=" + content
				+ ", regdate=" + regdate + ", filename=" + filename + ", filesize=" + filesize + ", filedata="
				+ filedata + ", view=" + view + "]";
	}
}
