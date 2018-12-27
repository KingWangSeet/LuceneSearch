package search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
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
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;

import cn.edu.nwsuaf.Model.SearchMode;

import com.mysql.jdbc.Connection;

import abner.Tagger;

import search.ProduceIndex;

public class SearchAction {
	//private static String dataPath = "D:\\Lucene Text"; // 文本文件存放路径
	//private static String dataPath="D:/Myeclipse 8.6-32/.metadata/.me_tcat/webapps/LuceneSerach/Lucene Text";
	@SuppressWarnings("deprecation")
	private static String dataPath=ServletActionContext.getRequest().getRealPath("/")+"\\Lucene Text";
	//private static String dataPath="/Lucene Text";
	private static String indexPath = "D:\\luceneData"; // 索引文件存放路径
	private String keyWords;// 存入搜索的关键字信息
	private SearchMode searchMode = new SearchMode();
	private ArrayList<File> AllFiles = new ArrayList<File>();// 遍历后得到的文件夹路径保存在此ArrayList<File>中
	private ProduceIndex produceIndex = new ProduceIndex();

	private int rowCount;// 记录数
	private int pages;// 总页数
	private int currentPage;// 当前页数
	private int pageSize; // 每页记录数
	
	private List<String> findURLList = new ArrayList<String>();
        
    /** 索引页面缓冲 */    
    private int maxBufferedDocs = 500;     
	
    private long costSearchTime;
	public String createIndex(){
		createIndex(indexPath,dataPath);
		return "success";
	}
	public String Search() {
		// String dataPath = "D:\\Lucene Text"; //文本文件存放路径
		// String indexPath ="D:\\luceneData"; //索引文件存放路径
		// ProduceIndex indexdao=new ProduceIndex();
		/* 为指定文件夹创建索引 */
		//createIndex(indexPath,dataPath);
		/* 根据索引全文检索 */
		try {
			searchMode.setKeyWords(java.net.URLDecoder.decode(searchMode
					.getKeyWords(), "UTF-8"));
			searchByIndex(indexPath, searchMode, currentPage, pageSize);
			if (rowCount % pageSize == 0) {
				pages = rowCount / pageSize;
			} else {
				pages = rowCount == 0 ? 1 : (rowCount / pageSize + 1);
			}
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}

	public void createIndex(String indexFilePath, String dataFilePath) {
		try {
			File dataDir = new File(dataFilePath);
			File indexDir = new File(indexFilePath);
			Analyzer luceneAnalyzer = new CJKAnalyzer(Version.LUCENE_47);
			// Analyzer luceneAnalyzer =new ChineseAnalyzer();
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47,
					luceneAnalyzer);
			conf.setOpenMode(OpenMode.CREATE);
			IndexWriter indexWriter = new IndexWriter(FSDirectory
					.open(indexDir), conf);

			long startTime = new Date().getTime();
			ArrayList<File> GetAllFiles = getAllFilesOfDirectory(dataDir);
			//File proteinsFile=new File(ServletActionContext.getRequest().getRealPath("/")+"\\SearchProteins\\protein.txt");
			// System.out.println("size:="+ GetAllFiles.get(0).listFiles());
			// 根据所得到的文件夹的路径，变量此文件夹下的所有文件
			String proteinString="";
			for (int index = 0; index < GetAllFiles.size(); index++) {
				File[] dataFiles = GetAllFiles.get(index).listFiles();
				for (int i = 0; i < dataFiles.length; i++) {
					String[][] entities;
					int n = 0;
					if (dataFiles[i].isFile()) {
						System.out.println("Indexing File:"+ dataFiles[i].getCanonicalPath());
						Document doc = new Document();
						StringBuffer reader = readFile(dataFiles[i]
								.getCanonicalPath());
						// System.out.println("conetednt:"+reader);
						// Reader reader=new FileReader(dataFiles[i]);
						doc.add(new StringField("path", dataFiles[i].getAbsolutePath(), Store.YES));
						doc.add(new TextField("contentFull", reader.toString(),Store.YES));
						//if (!dataFiles[i].getName().endsWith(".xml")&&!dataFiles[i].getName().endsWith(".html")){
						// 开始进行蛋白质实体信息的提取
						String fulltext = reader.toString();
						Tagger tagger = new Tagger(1);
						fulltext = tagger.tokenize(fulltext);
						// System.out.println(string);
						tagger.getWords(fulltext);
						fulltext = tagger.tokenize(fulltext);
						entities = tagger.getEntities(fulltext);
						//System.out.println("------------------------------------------------------------------------");
						if (entities[0].length == 2 || entities[0].length == 3) {
							n += entities[0].length;
						}
						System.out.println(entities[0].length);
						for (int j = 0; j < entities[0].length; j++) {
							//System.out.println(entities[0][j]);
							proteinString+=entities[0][j];
							//doc.add(new TextField("content", entities[0][j],Store.YES));
						}
						//}
						indexWriter.addDocument(doc);
					}
				}
			}
			//System.out.println("proteinString:"+proteinString);
			//FileWriter writer = new FileWriter(proteinsFile, true);
            //writer.write(proteinString);
           // writer.close();
			//writeFile(ServletActionContext.getRequest().getRealPath("/")+"\\SearchProteins\\protein.txt", proteinString);
			indexWriter.close();
			long endTime = new Date().getTime();
			System.out.println("It takes:"+ (endTime - startTime)+ "milliseconds to create index for the files in directory:"+ dataDir.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用已建立的索引检索指定的关键词，并高亮关键词后返回结果
	 * 
	 * @param indexFilePath
	 *            索引文件路径
	 * @param keyWord
	 *            检索关键词
	 */
	public void searchByIndex(String indexFilePath, SearchMode seaMode,
			int curpage, int pageSize) {
		try { System.out.println(ServletActionContext.getRequest().getContextPath()+"/Lucene Text");
			 System.out.println("name：-->"+seaMode.getKeyWords());
			 long startTime = new Date().getTime();
			String indexDataPath = indexFilePath;
			Directory dir = FSDirectory.open(new File(indexDataPath));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			//final String[]fieldList={"content","contentFull"};
			//Term term = new Term("contentFull", seaMode.getKeyWords());
			//TermQuery query1 = new TermQuery(term);//单词精确查找
			QueryParser queryParser=new QueryParser(Version.LUCENE_47,"contentFull" ,new CJKAnalyzer(Version.LUCENE_47));
			Query query = queryParser.parse(seaMode.getKeyWords());
			//Term term1 = new Term("content", seaMode.getKeyWords());
			//TermQuery query2 = new TermQuery(term1);//单词精确查找
			//BooleanQuery query=new BooleanQuery();
			//query.add(query1,BooleanClause.Occur.SHOULD);
			//query.add(query2,BooleanClause.Occur.SHOULD);
			//PhraseQuery query =new PhraseQuery(term);
			//System.out.println("Term:"+term.text());
			//FuzzyQuery query=new FuzzyQuery (term);//模糊查找
			//Query query = new WildcardQuery(term);
			//SpanQuery query=new SpanFirstQuery(term)
			//PrefixQuery query=new PrefixQuery(term);
			//MultiFields query=new MultiFields(fieldList, subSlices)
			// 检索出匹配相关指数最高的30条记录
			if (curpage <= 0) {
				curpage = 1;
			}
			if (pageSize <= 0) {
				pageSize = 10;
			}
			this.pageSize = pageSize;
			this.currentPage = curpage;
			int start = (curpage - 1) * pageSize;
			int hm = start + pageSize;
			TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
			searcher.search(query, res);

			this.rowCount = res.getTotalHits();
			this.pages = (rowCount - 1) / pageSize + 1;// 计算总页数
			System.out.println("计算后总页面：" + this.pages + "每页数目：" + this.pageSize
					+ "当前页面" + this.currentPage);

			TopDocs topdocs = res.topDocs(start, pageSize);
			ScoreDoc[] scoredocs = topdocs.scoreDocs;
			// count=topdocs.totalHits;
			System.out.println("查询结果总数:" + rowCount);
			System.out.println("最大的评分:" + topdocs.getMaxScore());
			for (int i = 0; i < scoredocs.length; i++) {
				int doc = scoredocs[i].doc;
				Document document = searcher.doc(doc);
				System.out.println("====================文件【" + (i + 1)
						+ "】=================");
				System.out.println("检索关键词：" + seaMode.getKeyWords());
				System.out.println("文件路径:" + document.get("path"));
				System.out.println("文件ID:" + scoredocs[i].doc);
				String path = document.get("path");
				String absolutePath=ServletActionContext.getRequest().getRealPath("/");
				//System.out.println("absoliPath:"+absolutePath);
				String paths=path.substring(absolutePath.length());
				//System.out.println("Path:"+paths);
				findURLList.add(paths);
				String content = document.get("contentFull");
				//System.out.println("as:" + content);
				/* Begin:开始关键字高亮 */
				if(document.get("path").endsWith("html")||document.get("path").endsWith("xml")){
					SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
							"<b><font color='red'>", "</font></b>");
					Highlighter highlighter = new Highlighter(formatter,
							new QueryScorer(query));
					highlighter.setTextFragmenter(new SimpleFragmenter(1024*1024));
					Analyzer luceneAnalyzer = new CJKAnalyzer(Version.LUCENE_47);
					if (content != null) {
						TokenStream tokenstream = luceneAnalyzer.tokenStream(
								seaMode.getKeyWords(), new StringReader(content));
						try {
							content = highlighter.getBestFragment(tokenstream,content);
							//System.out.println("文件写入内容:" + content);
							writeFile(path, content);
						} catch (InvalidTokenOffsetsException e) {
							e.printStackTrace();
						}
					}
				}
				/* End:结束关键字高亮 */
				//System.out.println("文件内容:" + content);

				System.out.println("匹配相关度：" + scoredocs[i].score);
				long endTime = new Date().getTime();
				costSearchTime=endTime-startTime;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取File文件的内容
	 * 
	 * @param file
	 *            File实体
	 * @return StringBuffer类型的文件内容
	 */
	public void writeFile(String fileName, String content) {
		try {
			// BufferedReader reader=new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8"));
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StringBuffer readFile(String file) {
		StringBuffer sb = new StringBuffer();
		try {
			// BufferedReader reader=new BufferedReader(new FileReader(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 根据指定的根文件夹，递归遍历此文件夹下的所有文件
	 * 
	 * @param RootFile
	 *            待遍历的目录
	 * @return 目录列表
	 */
	public ArrayList<File> getAllFilesOfDirectory(File RootFile) {
		// System.out.println("进入文件获取");
		File[] files = RootFile.listFiles();
		System.out.println("进入文件"+files.length);
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("========开始遍历文件夹======");
				getAllFilesOfDirectory(file);
				AllFiles.add(file);
			}
			// AllFiles.add(file);
		}
		// System.out.println("进入文件"+AllFiles.size());
		return AllFiles;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public ArrayList<File> getAllFiles() {
		return AllFiles;
	}

	public void setAllFiles(ArrayList<File> allFiles) {
		AllFiles = allFiles;
	}

	public ProduceIndex getProduceIndex() {
		return produceIndex;
	}

	public void setProduceIndex(ProduceIndex produceIndex) {
		this.produceIndex = produceIndex;
	}

	public List<String> getFindURLList() {
		return findURLList;
	}

	public void setFindURLList(List<String> findURLList) {
		this.findURLList = findURLList;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSearchMode(SearchMode searchMode) {
		this.searchMode = searchMode;
	}

	public SearchMode getSearchMode() {
		return searchMode;
	}
	public int getMaxBufferedDocs() {
		return maxBufferedDocs;
	}
	public void setMaxBufferedDocs(int maxBufferedDocs) {
		this.maxBufferedDocs = maxBufferedDocs;
	}
	public void setCostSearchTime(long costSearchTime) {
		this.costSearchTime = costSearchTime;
	}
	public long getCostSearchTime() {
		return costSearchTime;
	}
	
}
