package cn.edu.nwsuaf.bean;

import java.util.Date;

/**
 * Pindex entity. @author MyEclipse Persistence Tools
 */

public class Pindex implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String proteinName;
	private String fileName;
	private String path;
	private String title;
	private String author;
	private String keyWord;
	private String summary;
	private Date dateTime;

	// Constructors

	/** default constructor */
	public Pindex() {
	}

	/** minimal constructor */
	public Pindex(String proteinName) {
		this.proteinName = proteinName;
	}

	/** full constructor */
	public Pindex(String proteinName, String fileName, String path,
			String title, String author, String keyWord, String summary,
			Date dateTime) {
		this.proteinName = proteinName;
		this.fileName = fileName;
		this.path = path;
		this.title = title;
		this.author = author;
		this.keyWord = keyWord;
		this.summary = summary;
		this.dateTime = dateTime;
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

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}