package cn.edu.nwsuaf.bean;

// default package

/**
 * proteinindex entity. @author MyEclipse Persistence Tools
 */


public class Proteinindex implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String path;

	// Constructors

	/** default constructor */
	public Proteinindex() {
	}

	/** full constructor */
	public Proteinindex(String name, String path) {
		this.name = name;
		this.path = path;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}