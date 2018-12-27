package cn.edu.nwsuaf.Model;

import cn.edu.nwsuaf.bean.Proteinindex;

public class DisplayData extends Proteinindex {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id; //索引中获取
	private String fileName; // 文件名 
	private String path;// 文件地址
	private String title;// 文件标题
	private String author;// 作者
	private String summary;// 摘要
	private String contentFull;// 文件内容
	private String keyword;// 关键字
	private float score;// 文件得分

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContentFull() {
		return contentFull;
	}

	public void setContentFull(String contentFull) {
		this.contentFull = contentFull;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "DisplayDate [author=" + author + ", contentFull=" + contentFull
				+ ", fileName=" + fileName + ", id=" + id + ", keyword="
				+ keyword + ", path=" + path + ", score=" + score
				+ ", summary=" + summary + ", title=" + title + "]";
	}

}
