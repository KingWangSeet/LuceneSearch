package search;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class ProduceIndex {
    public ArrayList<File> AllFiles=new ArrayList<File>();//遍历后得到的文件夹路径保存在此ArrayList<File>中
	/**
     * 为指定的文件夹下的文件创建索引，并将索引文件保存在指定的文件夹下
     * @param indexFilePath 索引文件的保存路径
     * @param dataFilePath    文本文件的存放路径
     */
    public void createIndex(String indexFilePath,String dataFilePath){
        try {
            File   dataDir = new File(dataFilePath);
            File   indexDir  = new File(indexFilePath);
            Analyzer luceneAnalyzer=new CJKAnalyzer(Version.LUCENE_47);
            //Analyzer luceneAnalyzer =new ChineseAnalyzer();
            IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_47, luceneAnalyzer);
            IndexWriter indexWriter=new IndexWriter(FSDirectory.open(indexDir), iwc);
            //IndexWriter indexWriter=new IndexWriter(FSDirectory.open(indexDir), iwc,true);
            long startTime=new Date().getTime();
            ArrayList<File> GetAllFiles=getAllFilesOfDirectory(dataDir);
            //System.out.println("size:="+ GetAllFiles.get(0).listFiles());
            //根据所得到的文件夹的路径，变量此文件夹下的所有文件
            for(int index=0;index<GetAllFiles.size();index++){
                File[]  dataFiles=GetAllFiles.get(index).listFiles();
                for(int i=0;i<dataFiles.length;i++){
                    if(dataFiles[i].isFile()&&dataFiles[i].getName().endsWith(".txt")){
                        System.out.println("Indexing File:"+dataFiles[i].getCanonicalPath());
                        Document doc=new Document();
                        StringBuffer reader=readFile(dataFiles[i].getCanonicalPath());
                        System.out.println("conetednt:"+reader);
                        //Reader reader=new FileReader(dataFiles[i]);
                        doc.add(new StringField("path", dataFiles[i].getCanonicalPath(), Store.YES));
                        doc.add(new TextField("content", reader.toString(),Store.YES));
                        indexWriter.addDocument(doc);
                    }
                }
            }
            indexWriter.close();
            long endTime=new Date().getTime();
            System.out.println("It takes:"+(endTime-startTime)+"milliseconds to create index for the files in directory:"+dataDir.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 使用已建立的索引检索指定的关键词，并高亮关键词后返回结果
     * @param indexFilePath 索引文件路径
     * @param keyWord 检索关键词
     */
    public void searchByIndex(String indexFilePath,String keyWord){
        try {
            String indexDataPath=indexFilePath;
            Directory dir=FSDirectory.open(new File(indexDataPath));
            IndexReader reader=DirectoryReader.open(dir);
            IndexSearcher searcher=new IndexSearcher(reader);
            Term term=new Term("content",keyWord);
            TermQuery query=new TermQuery(term);
            //检索出匹配相关指数最高的30条记录
            
            TopDocs topdocs=searcher.search(query,1);
            ScoreDoc[] scoredocs=topdocs.scoreDocs;
            System.out.println("查询结果总数:" + topdocs.totalHits);
            System.out.println("最大的评分:"+topdocs.getMaxScore());
            for(int i=0;i<scoredocs.length;i++){
                int doc=scoredocs[i].doc;
                Document document=searcher.doc(doc);
                System.out.println("====================文件【"+(i+1)+"】=================");
                System.out.println("检索关键词："+term.toString());
                System.out.println("文件路径:"+document.get("path"));
                System.out.println("文件ID:"+scoredocs[i].doc);
                //String path=document.get("path");
                String content=document.get("content");
                /*Begin:开始关键字高亮*/
                SimpleHTMLFormatter formatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
                Highlighter highlighter=new Highlighter(formatter, new QueryScorer(query));
                highlighter.setTextFragmenter(new SimpleFragmenter(400));
                Analyzer luceneAnalyzer=new CJKAnalyzer(Version.LUCENE_47);
                if(content!=null){
                    TokenStream tokenstream=luceneAnalyzer.tokenStream(keyWord, new StringReader(content));
                    try {
                        content=highlighter.getBestFragment(tokenstream, content);
                       /* BufferedWriter bufferWritter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "GBK"));
                        bufferWritter.write(content);
                        bufferWritter.close();*/
                    } catch (InvalidTokenOffsetsException e) {
                        e.printStackTrace();
                    } 
                }
                /*End:结束关键字高亮*/
                System.out.println("文件内容:"+content);
                
                System.out.println("匹配相关度："+scoredocs[i].score);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 读取File文件的内容
     * @param file File实体
     * @return StringBuffer类型的文件内容
     */
    public StringBuffer readFile(String  file){
        StringBuffer sb=new StringBuffer();
        try {
            //BufferedReader reader=new BufferedReader(new FileReader(file));
            BufferedReader reader =   new  BufferedReader( new InputStreamReader(   
                    new  FileInputStream(file), "GBK"));  
            String str;
            while((str=reader.readLine())!=null){
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
     * @param RootFile 待遍历的目录
     * @return 目录列表
     */
    public ArrayList<File> getAllFilesOfDirectory(File RootFile){
    	//System.out.println("进入文件获取");
        File[] files = RootFile.listFiles();
      // System.out.println("进入文件"+files.length);
        for(File file:files){
            if(file.isDirectory()){
                System.out.println("========开始遍历文件夹======");
                getAllFilesOfDirectory(file);
                AllFiles.add(file);
            }
           // AllFiles.add(file);
        }
       // System.out.println("进入文件"+AllFiles.size());
        return AllFiles;
    }
}
