package cn.edu.nwsuaf.util;

import java.awt.TextField;
import java.io.*;

import org.apache.lucene.LucenePackage;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.xml.sax.ContentHandler;

public class PDFBoxPDFHandler {
    
    public static void getDocument(String fileName) {
    	  
        try{
        	 FileInputStream in = new FileInputStream(new File(fileName));   
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
            System.out.println("PDFText:"+docText);
        	/*COSDocument cosDoc= parseDocument.load(in);//读InputStream对象
        	String docText="";
        	//提取文档内容
            PDFTextStripper stripper=new PDFTextStripper();
            docText=stripper.getText(new PDDocument(cosDoc));*/
            Document doc=new Document();
           
            if (docText!=null){
              doc.add(new StringField("body",docText.toString(),Store.YES));//保证域内容
            }
          //PDDocument pdDoc=null;
         //提取文档元数据
             PDDocumentInformation docInfo=pdfdocument.getDocumentInformation();
             String author=docInfo.getAuthor();
             String title=docInfo.getTitle();
             String keywords=docInfo.getKeywords();
             String summary=docInfo.getSubject();
             if ((author!=null)&&!author.equals("")){
            	 System.out.println("author:"+author);
                   doc.add(new StringField("author",author,Store.YES));
             }
             if ((title!=null)&&!title.equals("")){
            	 System.out.println("title:"+title);
                   doc.add(new StringField("title",title,Store.YES));
             }  
             if ((keywords!=null)&&!keywords.equals("")){
            	 System.out.println("keyword:"+keywords);
                   doc.add(new StringField("keyword",keywords,Store.YES));
             }      
             if ((summary!=null)&&!summary.equals("")){
            	 System.out.println("summary:"+summary);
                   doc.add(new StringField("summary",summary,Store.YES));
             }  
             //closeCOSDocument(cosDoc);
             closePDDocument(pdfdocument);
            
           } 
           catch (Exception e){
                e.printStackTrace();
                System.err.println("cannot gret PDF document meta-data:"+e.getMessage());
           }
        
           
    }
    /*public static COSDocument parseDocument(InputStream is) throws Exception{
        PDFParser parser=new PDFParser(is);
        parser.parse();
        return paresr.getDocument();
    }*/
    public static void closeCOSDocument(COSDocument cosDoc){
        if (cosDoc!=null){
           try{
               cosDoc.close();
            }
            catch (IOException e){
               //
            }
        }
    }
    public static   void closePDDocument(PDDocument pdDoc){
        if (pdDoc!=null){
            try{
                 pdDoc.close();
            }
            catch (IOException e){
               //
            }
        }
    }
    public static void main(String[] args) {
         
         String fileName = "D:\\Lucene Text\\1\\ner.pdf";
         getDocument(fileName);
        // System.out.println(doc);
    }
}