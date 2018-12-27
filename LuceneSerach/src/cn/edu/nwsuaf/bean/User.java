package cn.edu.nwsuaf.bean;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class User implements java.io.Serializable {

	// Fields

	private String userNo;
	private Role role;
	private String userName;
	private String userPasswd;
	private String note;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userNo, Role role, String userName) {
		this.userNo = userNo;
		this.role = role;
		this.userName = userName;
	}

	/** full constructor */
	public User(String userNo, Role role, String userName, String userPasswd,
			String note) {
		this.userNo = userNo;
		this.role = role;
		this.userName = userName;
		this.userPasswd = userPasswd;
		this.note = note;
	}

	// Property accessors

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPasswd() {
		return this.userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "User [note=" + note +  ", userName="
				+ userName + ", userNo=" + userNo + ", userPasswd="
				+ userPasswd  + "]";
	}
	
}