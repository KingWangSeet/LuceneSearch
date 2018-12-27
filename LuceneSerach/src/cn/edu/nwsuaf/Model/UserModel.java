package cn.edu.nwsuaf.Model;
/**
 * 这个主要用于封装用户的查询多条件查询，里面信息
 * @author wangyong
 *
 */
public class UserModel {
	private String userNo;
	private String userName;
	private String note;
	private Integer userType;
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	@Override
	public String toString() {
		return "UserModel [note=" + note + ", userName=" + userName
				+ ", userNo=" + userNo + ", userType=" + userType + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public UserModel(String userNo, String userName, String note,
			Integer userType) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.note = note;
		this.userType = userType;
	}
	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
