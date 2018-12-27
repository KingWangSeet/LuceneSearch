package cn.edu.nwsuaf.bean;

/**
 * Protein entity. @author MyEclipse Persistence Tools
 */

public class Protein implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String proteinName;

	// Constructors

	/** default constructor */
	public Protein() {
	}

	/** full constructor */
	public Protein(String proteinName) {
		this.proteinName = proteinName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProteinName() {
		return this.proteinName;
	}

	public void setProteinName(String proteinName) {
		this.proteinName = proteinName;
	}

}