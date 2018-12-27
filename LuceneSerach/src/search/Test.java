package search;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 	String dataPath = "D:\\Lucene Text"; //文本文件存放路径
	        String indexPath  ="D:\\luceneData"; //索引文件存放路径
	        String keyWord="王勇";
	        ProduceIndex indexdao=new ProduceIndex();
	        /*为指定文件夹创建索引*/
	        indexdao.createIndex(indexPath,dataPath);
	        /*根据索引全文检索*/
	        indexdao.searchByIndex(indexPath,keyWord);
	}

}
