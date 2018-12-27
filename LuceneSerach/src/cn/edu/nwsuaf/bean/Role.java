package cn.edu.nwsuaf.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer roleNo;
	private String roleName;
	private String note;
	@SuppressWarnings("unchecked")
	private Set users = new HashSet(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleName) {
		this.roleName = roleName;
	}

	/** full constructor */
	@SuppressWarnings("unchecked")
	public Role(String roleName, String note, Set users) {
		this.roleName = roleName;
		this.note = note;
		this.users = users;
	}

	// Property accessors

	public Integer getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(Integer roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@SuppressWarnings("unchecked")
	public Set getUsers() {
		return this.users;
	}

	@SuppressWarnings("unchecked")
	public void setUsers(Set users) {
		this.users = users;
	}

}