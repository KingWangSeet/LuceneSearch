package cn.edu.nwsuaf.Model;

public class SearchMode {
  private int id;
  private String keyWords;
  private String others;
public void setKeyWords(String keyWords) {
	this.keyWords = keyWords;
}
public String getKeyWords() {
	return keyWords;
}
public void setOthers(String others) {
	this.others = others;
}
public String getOthers() {
	return others;
}
public SearchMode() {
	super();
	// TODO Auto-generated constructor stub
}
public void setId(int id) {
	this.id = id;
}
public int getId() {
	return id;
}
public SearchMode(int id, String keyWords, String others) {
	super();
	this.id = id;
	this.keyWords = keyWords;
	this.others = others;
}

}
