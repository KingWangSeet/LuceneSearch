package cn.edu.nwsuaf.Service.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;



import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.struts2.ServletActionContext;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;
import org.textmining.text.extraction.WordExtractor;



import abner.Tagger;


import cn.edu.nwsuaf.Model.DisplayData;
import cn.edu.nwsuaf.Model.SearchMode;
import cn.edu.nwsuaf.bean.Pindex;
import cn.edu.nwsuaf.bean.Protein;
import cn.edu.nwsuaf.dao.Impl.QueryUtilDaoImpl;
import cn.edu.nwsuaf.util.JAVAtoPDF;

/***
 * @date 2016-5-4
 * @author 王勇
 * 
 */
public class IndexService extends BaseServiceImpl<Pindex> {
	//private static String indexPath = "D:\\luceneData"; // 索引文件存放路径
	//private static final String indexPath  = "D:\\Myeclipse 8.6-32\\LuceneSerach\\indexData";
	//private static final String pdfSaveDir = "D:\\Myeclipse 8.6-32\\LuceneSerach\\WebRoot\\PDF";//txt 转换成PDF存储目录
	//private static final String proteinTxt = "D:\\Myeclipse 8.6-32\\LuceneSerach\\WebRoot\\SearchProteins\\protein.txt";//txt
	
	private static final String indexPath  = "C:\\Users\\Administrator.PC-20130714JITF\\Desktop\\LuceneSerach\\indexData";
	private static final String pdfSaveDir = "C:\\Users\\Administrator.PC-20130714JITF\\Desktop\\LuceneSerach\\WebRoot\\PDF";//txt 转换成PDF存储目录
	private static final String proteinTxt = "C:\\Users\\Administrator.PC-20130714JITF\\Desktop\\LuceneSerach\\WebRoot\\SearchProteins\\protein.txt";//txt
	
    private static Logger logger = Logger.getLogger(IndexService.class);  
	private ArrayList <File>  AllFiles = new ArrayList <File> ();// 遍历后得到的文件夹路径保存在此ArrayList<File>中
	
	/***
	 * 初始化索引列表时获取索引项信息，从数据库读取信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Pindex>findIndexListFirst(int page, int rows){
		String hql = "from Pindex order by id ASC";
		List<Pindex> list = (List<Pindex>) QueryUtilDaoImpl.executeQueryByPage(hql, null, page, rows);
			System.out.println("list.size()"+list.size());			
		return list;
	}
	/***
	 * 初始化索引列表 查询结果条数，从数据库读取信息
	 * @return
	 */
	public int countFirst() {
		int count=0;	
		String hql="select count(*) from Pindex";
		count =  QueryUtilDaoImpl.getResultCountForHql(hql, null);
		return count;
	}
	/***
	 * 提供给后台索引维护人员维护索引信息，查看索引列表
	 * @param smode
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Pindex> findIndexList(SearchMode smode,
			int page, int rows) {
		String hql = "from Pindex as a  where a.proteinName like :name ";
		Map mapParam = new HashMap();
		mapParam.put("name", "%" + smode.getKeyWords() + "%");
		List<Pindex> list = (List<Pindex>) QueryUtilDaoImpl.executeQueryByPage(hql, null, mapParam, page, rows);
		return list;
	}
	/**
	 *  提供给后台索引维护人员维护索引信息，查看索引列表
	 *  查询结果条数
	 *  @param smode
	 */
	@SuppressWarnings("unchecked")
	public int countFindIndex(SearchMode smode) {
		int count = 0;
		String hql = "select count(*) from Pindex as a  where a.proteinName like :name ";
		Map mapParam = new HashMap();
		mapParam.put("name", "%" + smode.getKeyWords() + "%");
		count = QueryUtilDaoImpl.getResultCountForHql(hql, null, mapParam);
		//System.out.println("count=========" + count);
		return count;

	}
	
	
	/**
	 * 返回模型蛋白质信息列表
	 * @param smode
	 * @param page
	 * @param rows
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public List<Index> findProteinindexsList(SearchMode smode,
			int page, int rows) {
		String hql = "from Index as a  where a.proteinName like :name ";
		Map mapParam = new HashMap();
		mapParam.put("name", "%" + smode.getKeyWords() + "%");
		List<Index> list = (List<Index>) QueryUtilDaoImpl.executeQueryByPage(hql, null, mapParam, page, rows);
		return list;

	}

	*//**
	 *  查询结果条数
	 *  @param smode
	 *//*
	@SuppressWarnings("unchecked")
	public int countFindProteinindexs(SearchMode smode) {
		int count = 0;
		String hql = "from Index as a  where a.proteinName like :name ";
		Map mapParam = new HashMap();
		mapParam.put("name", "%" + smode.getKeyWords() + "%");
		count = QueryUtilDaoImpl.getResultCountForHql(hql, null, mapParam);
		//System.out.println("count=========" + count);
		return count;

	}*/
	/** 
     * 检索蛋白质是否存在
     *  
     * @return int
     */  
	public int isExist(String name) {
		int count = 0;
		String hql = "select count(*) from Pindex as d where "
				+ "d.proteinName = '" + name + "'";

		count = QueryUtilDaoImpl.getResultCountForHql(hql, null);
		return count;
	}
	
	
	/** 
     * 依据关键字和页数进行蛋白质信息的检索
     * 采用Lucence 工具检索
     * @param seaMode:检索模型，里面是关键字属性
     * @param curpage：当前页
     * @param pageSize: 页面容量
     * @return 返回检索出的包含蛋白质信息的文件的URL的List
     */ 
	
	public List<DisplayData> searchByIndex(SearchMode seaMode,int curpage, int pageSize) throws Exception{
			System.out.println("name：-->"+seaMode.getKeyWords());
			
			 
			long startTime = new Date().getTime();
			//String indexDataPath = indexFilePath;
			Directory dir = FSDirectory.open(new File(indexPath));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			// 在索引器中使用IKSimilarity相似度评估器  
            //searcher.setSimilarity(new IKSimilarity());  
			
			//final String[]fieldList={"content","contentFull"};
			//Term term = new Term("contentFull", seaMode.getKeyWords());
			//TermQuery query1 = new TermQuery(term);//单词精确查找
			QueryParser queryParser=new QueryParser(Version.LUCENE_47,"proteinName" ,new CJKAnalyzer(Version.LUCENE_47));
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
			int start = (curpage - 1) * pageSize;
			int hm = start + pageSize;
			TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
			searcher.search(query, res);

			int rowCount = res.getTotalHits();
			List<String> findURLList = new ArrayList<String>();
			List<DisplayData>displayDataList=new ArrayList<DisplayData>();
			/*System.out.println("计算后总页面：" + this.pages + "每页数目：" + this.pageSize
					+ "当前页面" + this.currentPage);*/

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
				//System.out.println("path:"+path);
				String fileName=document.get("fileName");
				//System.out.println("fileName:"+fileName);
				String absolutePath=ServletActionContext.getServletContext().getRealPath("/");
				String paths=path.substring(absolutePath.length());
				//System.out.println("Path:"+paths);
				if(paths.endsWith(".txt")){
					String pddfPath=ServletActionContext.getServletContext().getRealPath("/PDF");
					System.out.println("pddfPath:"+pddfPath);
					JAVAtoPDF.makePDF(pdfSaveDir,path,fileName,seaMode.getKeyWords());
					//pdf 文件路径
					paths=pddfPath.substring(absolutePath.length())+File.separator+fileName.substring(0,fileName.indexOf("."))+".pdf";
					//transFormTxtToPDF(dataPath,searchMode.getKeyWords());
					System.out.println("paths:"+paths);
				}
				findURLList.add(paths);
				String content = document.get("contentFull");
				//System.out.println("as:" + content);
				/* Begin:开始关键字高亮 */
				//if(document.get("path").endsWith(".html")||document.get("path").endsWith(".xml")){
				if(!(document.get("path").endsWith(".txt")||document.get("path").endsWith(".pdf")
						||document.get("path").endsWith(".word"))){
					SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
							"<b><font color='red'>", "</font></b>");
					Highlighter highlighter = new Highlighter(formatter,
							new QueryScorer(query));
					highlighter.setTextFragmenter(new SimpleFragmenter(1024*1024));
					Analyzer luceneAnalyzer = new CJKAnalyzer(Version.LUCENE_47);
					if (content != null) {
						TokenStream tokenstream = luceneAnalyzer.tokenStream(
								seaMode.getKeyWords(), new StringReader(content));
							content = highlighter.getBestFragment(tokenstream,content);
							//System.out.println("文件写入内容:" + content);
							writeFile(path, content);
					}
				}
				String author=document.get("author");
				//String keywords=document.get("keyword");
				String title=document.get("title");
				String summary=document.get("summary");
				/* End:结束关键字高亮 */
				//System.out.println("文件内容:" + content);
				
				//把检索出的信息存入展示类中
				DisplayData searchData=new DisplayData();
				searchData.setAuthor(author);
				searchData.setFileName(fileName);
				searchData.setPath(paths);
				searchData.setTitle(title);
				searchData.setSummary(summary);
				searchData.setScore(scoredocs[i].score);
				displayDataList.add(searchData);
				
				System.out.println("匹配相关度：" + scoredocs[i].score);
				long endTime = new Date().getTime();
				@SuppressWarnings("unused")
				long costSearchTime=endTime-startTime;
			}
			reader.close();
			return displayDataList;
	}
	
	/**
	 * 计算检索到蛋白质信息的条数
	 * 采用Lucence 工具检索
	 * 这是前端用户进行蛋白质实体信息搜索时用到的函数
	 * @param seaMode
	 * @param curpage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public int  searchByIndexCount(SearchMode seaMode,int curpage, int pageSize) throws Exception {
			Directory dir = FSDirectory.open(new File(indexPath));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			QueryParser queryParser=new QueryParser(Version.LUCENE_47,"proteinName" ,new CJKAnalyzer(Version.LUCENE_47));
			Query query = queryParser.parse(seaMode.getKeyWords());
			// 检索出匹配相关指数最高的30条记录
			if (curpage <= 0) {
				curpage = 1;
			}
			if (pageSize <= 0) {
				pageSize = 10;
			}
			int start = (curpage - 1) * pageSize;
			int hm = start + pageSize;
			TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
			searcher.search(query, res);
			int rowCount = res.getTotalHits();
			return rowCount;
	}
	
	
	
	
	

	/***
	 * 依据文件所在路径搜索文件并进行索引的创建
	 * 返回蛋白质信息列表
	 * @param dataPath
	 * @return List
	 */
	public List<Protein> createIndex(String dataPath)  {
		List <Protein>proteinList=null;
	
		try{
			System.out.println("dataPath:"+dataPath);
			System.out.println("indexPath:"+indexPath);
			File dataDir = new File(dataPath);
			File indexDir = new File(indexPath);
			Analyzer luceneAnalyzer = new CJKAnalyzer(Version.LUCENE_47);
			// Analyzer luceneAnalyzer =new ChineseAnalyzer();
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47,luceneAnalyzer);
			//优化索引
			conf.setMergePolicy(optimizeIndex());
			
			long startTime = new Date().getTime();
			ArrayList<File> GetAllFiles = getAllFilesOfDirectory(dataDir);
			//File proteinsFile=new File(ServletActionContext.getRequest().getRealPath("/")+"\\SearchProteins\\protein.txt");
			System.out.println("size:="+ GetAllFiles.size());
			// 根据所得到的文件夹的路径，变量此文件夹下的所有文件
			if(GetAllFiles.size()==0){
				conf.setOpenMode(OpenMode.CREATE);//覆盖形式索引
			}else{
				//conf.setOpenMode(OpenMode.CREATE);
				conf.setOpenMode(OpenMode.APPEND);//追加形式索引
			}
			conf.setOpenMode(OpenMode.CREATE);//覆盖形式索引
			IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), conf);
			
			
			for (int index = 0; index < GetAllFiles.size(); index++) {
				File[] dataFiles = GetAllFiles.get(index).listFiles();
				for (int i = 0; i < dataFiles.length; i++) {
					Document doc = new Document();
					if (dataFiles[i].isFile()) {
						//System.out.println("Indexing File:"+ dataFiles[i].getCanonicalPath());
						//解析PDF文档
						if(dataFiles[i].getName().endsWith(".pdf")){
							doc =getPDFDocument(new File(dataFiles[i].getAbsolutePath()));
							indexWriter.addDocument(doc);
						}
						//解析HTML文档 XML文档,Lucene会自动解析,再说这个解析器时断时续正常
						else if(dataFiles[i].getName().endsWith(".html")||dataFiles[i].getName().endsWith(".xml")){
							doc =getHTMLDocument(new File(dataFiles[i].getCanonicalPath()));
							indexWriter.addDocument(doc);
						}
						//解析Word文档
						else if(dataFiles[i].getName().endsWith(".doc")){
							doc =getWordDocument(new File(dataFiles[i].getAbsolutePath()));
							indexWriter.addDocument(doc);
						}
						else{
							StringBuffer reader = readFile(dataFiles[i].getCanonicalPath());
							
							doc.add(new StringField("path", dataFiles[i].getAbsolutePath(), Store.YES));
							doc.add(new StringField("fileName", dataFiles[i].getName(), Store.YES));//文件名字
							doc.add(new TextField("contentFull", reader.toString(),Store.YES));
							//ABNER NER Protein
				        	doc=ABNERExtractProtein(reader.toString(),dataFiles[i].getName(),dataFiles[i].getAbsolutePath(),null,null,null,null,null,doc);
				        	
							indexWriter.addDocument(doc);
						}
						
						
					}
				}
			}
			//提取数据库蛋白质到TXT
			proteinList=saveProteinToTxt();
			logger.debug("索引结束,共有索引{}个" + indexWriter.numDocs());
			indexWriter.commit();
			indexWriter.close();
			long endTime = new Date().getTime();
			System.out.println("It takes:"+ (endTime - startTime)+ "milliseconds to create index for the files in directory:"+ dataDir.getPath());
			return proteinList;
		}catch (Exception e) {
			e.printStackTrace();
			//return "error";
		}
		return proteinList;
	}
	/** 
     * 优化索引，返回优化策略 
     *  
     * @return mergePolicy
     */  
    private static LogMergePolicy optimizeIndex() {  
        LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();  
  
        // 设置segment添加文档(Document)时的合并频率  
        // 值较小,建立索引的速度就较慢  
        // 值较大,建立索引的速度就较快,>10适合批量建立索引  
        // 达到50个文件时就和合并  
        mergePolicy.setMergeFactor(50);  
  
        // 设置segment最大合并文档(Document)数  
        // 值较小有利于追加索引的速度  
        // 值较大,适合批量建立索引和更快的搜索  
        mergePolicy.setMaxMergeDocs(5000);  
  
        // 启用复合式索引文件格式,合并多个segment  
        //mergePolicy.setUseCompoundFile(true);  
        return mergePolicy;  
    } 
    /***
     * 利用ABNER 工具提取蛋白质实体信息，并存入数据库
     * @param file
     * @return Document 添加蛋白质实体信息的索引
     */
    public Document  ABNERExtractProtein(String fulltext,String fileName,String filePath,String title,String author,String keyWord,String summary,Date dateTime,
    		Document document){
    	try{
    	
    	String[][] entities;
    	//String proteinString="";
    	int n=0;
    	//ABNER 解析包 加载 biocreative.crf
    	Tagger tagger = new Tagger(1);
    	//原始文本用ABNER内置标记
		fulltext = tagger.tokenize(fulltext);
		//转换成二维特征向量
		tagger.getWords(fulltext);
		//获取Protein实体
		entities  =  tagger.getEntities( fulltext );
		//System.out.println("------------------------------------------------------------------------");
		if (entities[0].length == 2 || entities[0].length == 3) {
			n += entities[0].length;
		}
		
		Pindex index=new Pindex();
		for (int j = 0; j < entities[0].length; j++) {
			index.setProteinName(entities[0][j]);
			index.setFileName(fileName);
			index.setPath(filePath);
			index.setTitle(title);
			index.setAuthor(author);
			index.setKeyWord(keyWord);
			index.setSummary(summary);
			index.setDateTime(dateTime);
			//存储检索到的蛋白质信息到数据库
			this.save(index);
			//proteinString+=entities[0][j];
			//蛋白质名字存入索引表
			document.add(new TextField("proteinName", entities[0][j],Store.YES));
		}
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	return document;
    }
    
	
	  /** 
     * 把数据库ANER识别出的蛋白质信息取出来存入文件proteinTxt
     *  并存入蛋白质信息列表，为后续存入数据库 2016-5-5  后期发现没有去重复
     *  @return List
     */ 
    
	@SuppressWarnings("unchecked")
	public List<Protein> saveProteinToTxt(){
		String hql=" from Pindex ";
		
		List<Pindex>proteinlIst=(List<Pindex>) QueryUtilDaoImpl.executeQuery(hql, null);
		java.util.Iterator<Pindex>iterator=proteinlIst.iterator();
		//蛋白质信息列表
		List<Protein>proteinList=new ArrayList<Protein>();
		HashSet<String> proteinNameSet=new HashSet();
		while(iterator.hasNext()){
			String protein=iterator.next().getProteinName();
			//System.out.println("protein:"+protein);
			proteinNameSet.add(protein);
			Protein newProtein=new Protein();
			newProtein.setProteinName(protein);
			proteinList.add(newProtein);
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(proteinTxt), "UTF-8"));
			java.util.Iterator<String>setiIterator=proteinNameSet.iterator();
			while(setiIterator.hasNext()){
				String protein=setiIterator.next();
				//System.out.println("protein:"+protein);
				writer.write(protein);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proteinList;
	}
	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Protein>getProteinListFromIndex(){
		String hql=" from Pindex ";
		
		List<Pindex>proteinlIst=(List<Pindex>) QueryUtilDaoImpl.executeQuery(hql, null);
		java.util.Iterator<Pindex>iterator=proteinlIst.iterator();
		//蛋白质信息列表
		List<Protein>proteinList=new ArrayList<Protein>();
		HashSet<String> proteinNameSet=new HashSet();
		while(iterator.hasNext()){
			String protein=iterator.next().getProteinName();
			//System.out.println("protein:"+protein);
			proteinNameSet.add(protein);
			Protein newProtein=new Protein();
			newProtein.setProteinName(protein);
			proteinList.add(newProtein);
		}
		return proteinList;
	}
	
	/***
	 * 写入文件
	 * @param fileName 文件名称信息
	 * @param content  文件内容
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
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
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
	 * @param RootFile： 待遍历的目录
	 * @return 目录列表
	 */
	public ArrayList<File> getAllFilesOfDirectory(File RootFile) {
		 System.out.println("进入文件获取");
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
	
	/** 
     * Txt 转换成PDF
     * @param dataPath：txt文件路径
     * @param colorWords：查询关键字
     * 这个是在检索时才把相应的Txt文档存储在PDF文件夹下
     */  
	public void transFormTxtToPDF(String dataPath,String colorWords){
		//处理目录下的所有txt文件使之生成PDF文件
		JAVAtoPDF.generatePDF(pdfSaveDir,dataPath,colorWords);
	}
	/***
	 * 解析PDF文档
	 * @param file
	 * @return Document
	 */
	public Document  getPDFDocument(File pdfFile) {
		 Document doc=new Document();
        try{
        	 FileInputStream in = new FileInputStream(pdfFile);   
        	 // 新建一个PDF解析器对象
        	PDFParser parser=new PDFParser(in);
        	// 对PDF文件进行解析   
            parser.parse();
            // 获取解析后得到的PDF文档对象   
            PDDocument pdfdocument = parser.getPDDocument(); 
            //COSDocument cosDocument=parser.getDocument();
            // 新建一个PDF文本剥离器   
            PDFTextStripper stripper = new PDFTextStripper();   
            // 从PDF文档对象中剥离文本   
            String docText = stripper.getText(pdfdocument);   
            //System.out.println("PDFText:"+docText);
            //System.out.println("path:"+pdfFile.getAbsolutePath());
            //System.out.println("fileName:"+pdfFile.getName());
           
        	
            doc.add(new StringField("path",pdfFile.getAbsolutePath(), Store.YES));
			doc.add(new StringField("fileName", pdfFile.getName(), Store.YES));//文件名字
			//doc.add(new TextField("contentFull", reader.toString(),Store.YES));
           
           
          //PDDocument pdDoc=null;
         //提取文档元数据
             PDDocumentInformation docInfo=pdfdocument.getDocumentInformation();
             String author=docInfo.getAuthor();
             String title=docInfo.getTitle();
             String keywords=docInfo.getKeywords();
             String summary=docInfo.getSubject();
             Date dateTime=docInfo.getModificationDate().getTime();
             if ((author!=null)&&!author.equals("")){
            	// System.out.println("author:"+author);
                   doc.add(new StringField("author",author,Store.YES));
             }
             if ((title!=null)&&!title.equals("")){
            	 //System.out.println("title:"+title);
                   doc.add(new StringField("title",title,Store.YES));
             }  
             if ((keywords!=null)&&!keywords.equals("")){
            	// System.out.println("keyword:"+keywords);
                   doc.add(new StringField("keyword",keywords,Store.YES));
             }      
             if ((summary!=null)&&!summary.equals("")){
            	 //System.out.println("summary:"+summary);
                   doc.add(new StringField("summary",summary,Store.YES));
             }  
             if (docText!=null){
             	//ABNER NER Protein
             	doc=ABNERExtractProtein(docText,pdfFile.getName(),pdfFile.getAbsolutePath(),title,author,keywords,summary,dateTime,doc);
             	doc.add(new TextField("contentFull",docText.toString(),Store.YES));//保证域内容
             }
            closePDDocument(pdfdocument);
           
            in.close();
            
            
           } 
           catch (Exception e){
                e.printStackTrace();
                System.err.println("cannot gret PDF document meta-data:"+e.getMessage());
           }
        
           return doc;
    }
	/***
	 * HTML解析器
	 * @param htmlFile
	 * @return
	 */
	public  Document  getHTMLDocument(File htmlFile) {
		 Document doc=new Document();
		
       try{
       		String htmlPath=htmlFile.getAbsolutePath();   
       		System.out.println("HTMLPath:_------->"+htmlPath);
       	 	// 新建一个HTML解析器对象
       		Parser parser=new Parser(htmlPath);
       		// 对HTML文件进行解析   
       		parser.setEncoding("UTF-8");
       		HtmlPage visitor=new HtmlPage(parser);
       		visitor.beginParsing();
       		parser.visitAllNodesWith(visitor);
       		NodeList nodes  =  (NodeList) visitor.getBody();
       		String text="";
            int  size  =  nodes.size();
            System.out.println("HTMLSize:_------->"+size);
            for ( int  i = 0 ;i < size;i ++ ) {
               Node node  =  (Node) nodes.elementAt(i);
               text  +=  node.toPlainTextString();
           } 
            //ABNER NER Protein
        	doc=ABNERExtractProtein(text,htmlFile.getName(),htmlFile.getAbsolutePath(),null,null,null,null,null,doc);
        	String title  =  visitor.getTitle();
        	
        	System.out.println("HTMLTitle:_------->"+title);
        	System.out.println("HTMLText:_------->"+text);
            
            doc.add( new  StringField( "title", title, Store.YES));
            doc.add( new  TextField( "contentFull" , text,Store.YES));
            doc.add( new  StringField( "path" , htmlPath, Store.YES));
            doc.add( new  StringField("fileName", htmlFile.getName(), Store.YES));//文件名字
             
            visitor.finishedParsing();
            return  doc;
           
          } 
          catch (Exception e){
               e.printStackTrace();
               System.err.println("cannot gret HTML document meta-data:"+e.getMessage());
          }
       
          return doc;
   }
	/***
	 * 解析Word文件
	 * @param wordFile
	 * @return
	 */
	public Document  getWordDocument(File wordFile) {
		 Document doc=new Document();
      try{
      		String wordPath=wordFile.getAbsolutePath();
      		String fileName=wordFile.getName();
      		InputStream in=new FileInputStream(wordFile);
      	 	// 新建一个Word解析器对象
      		WordExtractor extractor =  new WordExtractor();
      		String text= extractor.extractText(in);
      		 
      		//ABNER NER Protein
        	doc=ABNERExtractProtein(text,wordFile.getName(),wordFile.getAbsolutePath(),null,null,null,null,null,doc);
        	
           doc.add( new  TextField( "contentFull" , text,Store.YES));
           doc.add( new  StringField( "path" , wordPath, Store.YES));
           doc.add(new StringField("fileName", fileName, Store.YES));//文件名字
          
           in.close();
         } 
         catch (Exception e){
              e.printStackTrace();
              System.err.println("cannot get Word document meta-data:"+e.getMessage());
         }
      
         return doc;
  }
	/***
	 * 关闭PDDocument
	 * @param pdDoc
	 */
    public    void closePDDocument(PDDocument pdDoc){
        if (pdDoc!=null){
            try{
                 pdDoc.close();
            }
            catch (IOException e){
               //
            }
        }
    }
	
	
	public void setAllFiles(ArrayList<File> allFiles) {
		AllFiles = allFiles;
	}


	public ArrayList<File> getAllFiles() {
		return AllFiles;
	}
	
	public static String getPdfSaveDir() {
		return pdfSaveDir;
	}
	
	public static String getProteinTxt() {
		return proteinTxt;
	}


}
