package kr.ymtech.ojt.controller.model;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import open.commons.json.annotation.JSONField;

public class MemberGrade
 {

	public static final int UNKNOWN_USER = -2;
	public static final int INVALID_UNKNOWN_AND_ERROR = -1;

	/** 관리자 */
	public static final int ADMIN = 0;
	/** 일반사용자 */
	public static final int USER = 1;

	private static final HashMap<Integer, String> USER_GRADES = new HashMap<>();

	static {
		USER_GRADES.put(UNKNOWN_USER, "UNKNOWN_USER");
		USER_GRADES.put(INVALID_UNKNOWN_AND_ERROR, "ERROR");

		USER_GRADES.put(ADMIN, "ROLE_ADMIN");
		USER_GRADES.put(USER, "ROLE_USER");
	}

	@JSONField(name = "descr")
	private String descr;

	@JSONField(name = "id")
	private Integer id = new Integer(0);

	@JSONField(name = "name")
	private String name;

	public MemberGrade() {
	}

	private MemberGrade(int grade) {
		this.id = grade;
	}

	/**
	 *
	 * @return descr
	 *
	 * @since 2016. 10. 31
	 */
	public String getDescr() {
		return this.descr;
	}

	/**
	 *
	 * @return id
	 *
	 * @since 2016. 10. 31
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 *
	 * @return name
	 *
	 * @since 2016. 10. 31
	 */
	public String getName() {
		return this.name;
	}

	/**
	 *
	 * @param descr
	 *            descr to set.
	 *
	 * @since 2016. 10. 31
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 *
	 * @param id
	 *            id to set.
	 *
	 * @since 2016. 10. 31
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *
	 * @param name
	 *            name to set.
	 *
	 * @since 2016. 10. 31
	 */
	public void setName(String name) {
		this.name = name;
	}

	public static boolean available(int grade) {
		switch (grade) {
			case MemberGrade.ADMIN:
			case MemberGrade.USER:
				return true;
			default:
				return false;
		}
	}

	public static MemberGrade getUserGrade(int id, String name, String desc) {

		int _id_ = INVALID_UNKNOWN_AND_ERROR;

		if (USER_GRADES.containsKey(id)) {
			_id_ = id;
			name = USER_GRADES.get(id);
		}

		MemberGrade grade = new MemberGrade(_id_);

		grade.setName(name);
		grade.setDescr(desc);

		return grade;
	}

}