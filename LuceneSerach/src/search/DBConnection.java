package search;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;

import cn.edu.nwsuaf.Model.SearchMode;

import com.mysql.jdbc.*;
import com.thoughtworks.qdox.Searcher;

public class DBConnection {
	private Connection connection;
	private Statement statement;
	private ResultSet  resultSet; 
	@SuppressWarnings("deprecation")
	private  String searchDir=ServletActionContext.getRequest().getRealPath("/")+"\\Lucene Text";
	private static File indexFile = new File("D:\\luceneData"); // 索引文件存放路径
	private static IndexSearcher searcher = null;     
    private static Analyzer analyzer = null;
    /** 索引页面缓冲 */    
    private int maxBufferedDocs = 500;
	/**   
	    * 获取数据库数据   
	    * @return ResultSet   
	    * @throws Exception   
	    */    
	    public List<Protein> getResult(String queryStr) throws Exception {     
	        List<Protein> result = null;     
	        connection = (Connection) JdbcUtil.getConnection();     
	        if(connection == null) {     
	            throw new Exception("数据库连接失败！");     
	        }     
	        String sql = "select id, name, path from proteinindex";     
	        try {     
	        	statement = (Statement) connection.createStatement();     
	        	resultSet = statement.executeQuery(sql);     
	            this.createIndex(resultSet);   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引     
	            //TopDocs topDocs = this.search(searchMode seaMode,int curpage, int pageSize);     
	            //ScoreDoc[] scoreDocs = topDocs.scoreDocs;     
	            //result = this.addHits2List(scoreDocs);     
	        } catch(Exception e) {     
	            e.printStackTrace();     
	            throw new Exception("数据库查询sql出错！ sql : " + sql);     
	        } finally {     
	            if(resultSet!= null) resultSet.close();     
	            if(statement != null) statement.close();     
	            if(connection != null) connection.close();     
	        }              
	        return result;     
	    } 
	    /**   
	    * 为数据库检索数据创建索引   
	    * @param rs   
	    * @throws Exception   
	    */    
	        private void createIndex(ResultSet rs) throws Exception {     
	            Directory directory = null;     
	            IndexWriter indexWriter = null;     
	            try {     
	                indexFile = new File(searchDir);     
	                if(!indexFile.exists()) {     
	                    indexFile.mkdir();     
	                }     
	                directory = FSDirectory.open(indexFile);     
	                analyzer =  new CJKAnalyzer(Version.LUCENE_47);
	                IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47,analyzer);
	                conf.setMaxBufferedDocs(maxBufferedDocs);
	                conf.setOpenMode(OpenMode.CREATE);//覆盖索引
	                indexWriter = new IndexWriter(directory, conf);     
	                Document doc = null;     
	                while(rs.next()) {     
	                    doc = new Document();     
	                    StringField id = new StringField("id", String.valueOf(rs.getInt("id")), Store.YES);     
	                    TextField name = new TextField("name", rs.getString("name") == null ? "" : rs.getString("username"), Store.YES);     
	                    StringField path=new StringField("path", String.valueOf(rs.getInt("path")), Store.YES);
	                    doc.add(id);     
	                    doc.add(name);   
	                    doc.add(path);
	                    indexWriter.addDocument(doc);     
	                }     
	                             
	                indexWriter.close();     
	            } catch(Exception e) {     
	                e.printStackTrace();     
	            }      
	        }     
	        /**   
	         * 搜索索引   
	         * @param queryStr   
	         * @return   
	         * @throws Exception   
	         */    
	         @SuppressWarnings("unused")
			private TopDocs search(SearchMode seaMode,int curpage, int pageSize) throws Exception {            
	             if(searcher == null) {     
	                 indexFile = new File(searchDir);     
	                 searcher =  new IndexSearcher(DirectoryReader.open(FSDirectory.open(indexFile)));   
	             }     
	            QueryParser queryParser=new QueryParser(Version.LUCENE_47,"contentFull" ,new StandardAnalyzer(Version.LUCENE_47));
	 			Query query = queryParser.parse(seaMode.getKeyWords());
	 			int start = (curpage - 1) * pageSize;
				int hm = start + pageSize;
	 			TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
	            searcher.search(query,res);  
	            TopDocs topdocs = res.topDocs(start, pageSize);
	             return topdocs;     
	         }     
	         /**   
	          * 返回结果并添加到List中   
	          * @param scoreDocs   
	          * @return   
	          * @throws Exception   
	          */    
	          private List<Protein> addHits2List(ScoreDoc[] scoreDocs ) throws Exception {     
	              List<Protein> listBean = new ArrayList<Protein>();     
	              Protein bean = null;     
	              for(int i=0 ; i<scoreDocs.length; i++) {     
	                  int docId = scoreDocs[i].doc;     
	                  Document doc = searcher.doc(docId);     
	                  bean = new Protein();     
	                  bean.setId(Integer.valueOf(doc.get("id")));     
	                  bean.setName(doc.get("name")); 
	                  bean.setPath(doc.get("path")); 
	                  listBean.add(bean);     
	              }     
	              return listBean;     
	          }  
	    
	public DBConnection() {
		super();
	}
	public DBConnection(Connection connection, Statement statement,
			ResultSet resultSet) {
		super();
		this.connection = connection;
		this.statement = statement;
		this.resultSet = resultSet;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Statement getStatement() {
		return statement;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	public ResultSet getResultSet() {
		return resultSet;
	}
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
}
