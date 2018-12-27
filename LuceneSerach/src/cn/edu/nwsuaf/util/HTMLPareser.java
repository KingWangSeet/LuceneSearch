package cn.edu.nwsuaf.util;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class HTMLPareser {

	/**
	 * @param args
	 */
	/***
	 * HTML解析器
	 * @param htmlFile
	 * @return
	 */
	public static  void  getHTMLDocument(File htmlFile) {
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
            
        	String title  =  visitor.getTitle();
        	
        	System.out.println("HTMLTitle:_------->"+title);
        	System.out.println("HTMLText:_------->"+text);
            
            doc.add( new  StringField( "title", title, Store.YES));
            doc.add( new  TextField( "contentFull" , text,Store.YES));
            doc.add( new  StringField( "path" , htmlPath, Store.YES));
            doc.add( new  StringField("fileName", htmlFile.getName(), Store.YES));//文件名字
             
            visitor.finishedParsing();
            
           
          } 
          catch (Exception e){
               e.printStackTrace();
               System.err.println("cannot gret HTML document meta-data:"+e.getMessage());
          }
       
        
   }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      File file=new File("D:\\Lucene Text\\1\\ner.html");
      getHTMLDocument(file);
	}

}
