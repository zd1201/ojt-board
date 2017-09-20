package kr.ymtech.ojt.dao.model;

import java.sql.Timestamp;

public class CommentModel {


	private int comment_no;
	
	private int board_no;
	
	private String id;
	
	private String content;
	
	private Timestamp regdate;
	
	private int parent;
	
	private boolean reply;
	
	public CommentModel(){
		this.reply = false;
	}

	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}

	public int getComment_no() {
		return comment_no;
	}

	public void setComment_no(int comment_no) {
		this.comment_no = comment_no;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}


	@Override
	public String toString() {
		return "CommentModel [comment_no=" + comment_no + ", board_no=" + board_no + ", id=" + id + ", content="
				+ content + ", regdate=" + regdate + ", parent=" + parent + "]";
	}
}
