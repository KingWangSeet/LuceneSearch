package cn.edu.nwsuaf.util;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.cos.COSArray;

import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFOperator;
import org.apache.pdfbox.util.PDFTextStripper;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/**
 * author  此程序主要是将txt转为pdf
 * pdf的编辑
 * 
 */

public class JAVAtoPDF {
	public static ArrayList<File> getAllFilesOfDirectory(File RootFile) {
		ArrayList<File> allFiles = new ArrayList<File>();
		File[] files = RootFile.listFiles();
		// System.out.println("进入文件"+files.length);
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("========开始遍历文件夹======");
				getAllFilesOfDirectory(file);
				allFiles.add(file);
			}
			// AllFiles.add(file);
		}
		// System.out.println("进入文件"+AllFiles.size());
		return allFiles;
	}

	// 这个可以将目录下的所有txt->pdf
	public static void generatePDF(String pdfFileDir, String fileDir,
			String colorWords) {
		ArrayList<File> GetAllFiles = getAllFilesOfDirectory(new File(fileDir));
		for (int index = 0; index < GetAllFiles.size(); index++) {
			File[] dataFiles = GetAllFiles.get(index).listFiles();
			System.out.println(dataFiles.length);
			for (int i = 0; i < dataFiles.length; i++) {
				if (dataFiles[i].isFile()
						&& dataFiles[i].getName().endsWith(".txt")) {
					/*
					 * String string=dataFiles[i].getAbsolutePath();
					 * System.out.println("Path:"+string); String
					 * fileName=dataFiles[i].getName();
					 * System.out.println("Name:"+fileName);
					 * System.out.println("KeyWords:"+colorWords);
					 */
					// String colorWords="interferon";

					//makePDF(pdfFileDir, dataFiles[i], colorWords);
				}
			}
		}
	}
	/***
	 * 编辑Pdf对查询关键字进行标记,只是实现了替换查找功能，未能实现颜色标记，这个是个测试，用于获取页面文本和图片
	 * @param pdfFileDir
	 * @param file
	 * @param colorWords
	 */
	@SuppressWarnings("unchecked")
	public static void editPDF(File pdfFile, String colorWords,String imgSavePath,String newPdfFile) {
		try {
			
	        FileInputStream input = new FileInputStream(pdfFile);   
	        // 新建一个PDF解析器对象
	        PDFParser parser=new PDFParser(input);
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
	        System.out.println("path:"+pdfFile.getAbsolutePath());
	        System.out.println("fileName:"+pdfFile.getName());

	          //PDDocument pdDoc=null;
	         //提取文档元数据
	             PDDocumentInformation docInfo=pdfdocument.getDocumentInformation();
	            
	             String author=docInfo.getAuthor();
	             String title=docInfo.getTitle();
	             String keywords=docInfo.getKeywords();
	             String summary=docInfo.getSubject();
	             if ((author!=null)&&!author.equals("")){
	            	 System.out.println("author:"+author);


	             }
	             if ((title!=null)&&!title.equals("")){
	            	 System.out.println("title:"+title);
	                  
	             }  
	             if ((keywords!=null)&&!keywords.equals("")){
	            	 System.out.println("keyword:"+keywords);
	                  
	             }      
	             if ((summary!=null)&&!summary.equals("")){
	            	 System.out.println("summary:"+summary);
	                  
	             }  
	            
	            /** 文档页面信息 **/  
	            PDDocumentCatalog cata = pdfdocument.getDocumentCatalog();  
	            List pages = cata.getAllPages();  
	            int count = 1;  
	            for( int i = 0; i < pages.size(); i++ )  
	            {  
	                PDPage page = ( PDPage ) pages.get( i );  
	                if( null != page )  
	                {  
	                    PDResources res = page.findResources();  
	                      
	                    //获取页面图片信息  
	                    Map imgs = res.getImages();  
	                    if( null != imgs )  
	                    {  
	                        Set keySet = imgs.keySet();  
	                        Iterator it = keySet.iterator();  
	                        while( it.hasNext() )  
	                        {  
	                            Object obj =  it.next();  
	                            @SuppressWarnings("unused")
								PDXObjectImage img = ( PDXObjectImage ) imgs.get( obj );  
	                            //img.write2file( imgSavePath + count );  
	                            count++;  
	                        }  
	                    }  
	                }  
	            } 
	            
	            input.close();  
	            //pdfdocument.close();
	            
	            //开始写进PDF文件
	            BaseFont bfChinese = BaseFont.createFont(
						"C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
						BaseFont.EMBEDDED);
				Font keyFont = new Font(bfChinese, 15, Font.BOLD, Color.red);// 设置字体大小
		        Chunk chunk=new Chunk(colorWords, keyFont);
		        docText=docText.replaceAll(keywords, chunk.toString());
		        Document document = new Document(PageSize.A4, 50, 50, 50, 50);// 设置左右上边界距离
		        
	            PdfWriter pdfWriter=PdfWriter.getInstance(document, new FileOutputStream(new File(newPdfFile)));
	           
	            
	            document.open();
	            //document.addKeywords(colorWords);
	            //document.addAuthor(author);
	            //document.addTitle(title);
	            //document.addSubject(summary);
	            
	           // document.add(docText);
	            document.close();
				pdfWriter.flush();
				pdfWriter.close();
	              
		}catch( Exception e){  
	            e.printStackTrace();
	            
	    }

	}
	
	/****
	 * 实现PDF文本中关键字的查找，但是标记功能目前还未实现
	 * @param inifile
	 * @param colorWords
	 * @param newPdfFile
	 */
	@SuppressWarnings("unchecked")
	public static void editPDF(String inifile, String colorWords,String newPdfFile) {

		try {
			//BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
			//Font keyFont = new Font(bfChinese, 15, Font.BOLD, Color.red);// 设置字体大小
			//Chunk chunk = new Chunk(colorWords, keyFont);

			// pdfwithText 加载PDF文档
			PDDocument pdfDocument = PDDocument.load(new File(inifile));
			  //提取文档元数据
            PDDocumentInformation docInfo=pdfDocument.getDocumentInformation();
           
            String author=docInfo.getAuthor();
            String title=docInfo.getTitle();
            String keywords=docInfo.getKeywords();
            String summary=docInfo.getSubject();
            if ((author!=null)&&!author.equals("")){
           	 System.out.println("author:"+author);

            }
            if ((title!=null)&&!title.equals("")){
           	 System.out.println("title:"+title);
                 
            }  
            if ((keywords!=null)&&!keywords.equals("")){
           	 System.out.println("keyword:"+keywords);
                 
            }      
            if ((summary!=null)&&!summary.equals("")){
           	 System.out.println("summary:"+summary);
                 
            } 
            /** 文档页面信息 **/ 
			PDDocumentCatalog cata = pdfDocument.getDocumentCatalog();
			List pages = cata.getAllPages();
			for (int i = 0; i < pages.size(); i++) {
				PDPage page = (PDPage) pages.get(i); // 获取pdf中每页信息
				
				//pdPageContentStream.close();
				PDStream contents = page.getContents();
				//这将解析一个PDF字节流和提取操作数等。
				PDFStreamParser parser = new PDFStreamParser(contents
						.getStream());
				parser.parse();
				//获取流中所有分词和操作符
				List<Object> tokens = parser.getTokens();
				for (int j = 0; j < tokens.size(); j++) {
					Object next = tokens.get(j);
					if (next instanceof PDFOperator) {
						PDFOperator op = (PDFOperator) next;
						
						// Tj and TJ are the two operators that display strings
						// in a PDF
						
						if (op.getOperation().equals("Tj")) {
							// Tj takes one operator and that is the string
							// to display so lets update that operator
							COSString previous = (COSString) tokens.get(j - 1);
							
							
							String string = previous.getString();
							string = string.replaceAll(colorWords,colorWords.toLowerCase());
							
							// Word you want to change. Currently this code
							// changes word "Solr" to "Solr123"
							previous.reset();
							previous.append(string.getBytes("UTF-8"));
							
						} else if (op.getOperation().equals("TJ")) {
							COSArray previous = (COSArray) tokens.get(j - 1);
							for (int k = 0; k < previous.size(); k++) {
								Object arrElement = previous.getObject(k);
								if (arrElement instanceof COSString) {
									COSString cosString = (COSString) arrElement;
									String string = cosString.getString();
									string = string.replaceAll(colorWords,colorWords.toLowerCase());
											
									// Currently this code changes word "Solr"
									// to "Solr123"
									cosString.reset();
									
									cosString.append(string.getBytes("UTF-8"));
								}
							}
						}
					}
				}

				// now that the tokens are updated we will replace the page
				// content stream.
				PDStream updatedStream = new PDStream(pdfDocument);
				OutputStream out = updatedStream.createOutputStream();
				ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
				tokenWriter.writeTokens(tokens);
				page.setContents(updatedStream);
				
			}
			
			pdfDocument.save(newPdfFile); // Output file name
			pdfDocument.close();
			// PDFTextStripper textStripper = new PDFTextStripper();
			// System.out.println(textStripper.getText(helloDocument));
			// helloDocument.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/***
	 * 查询时Txt转换为Pdf
	 * @param pdfFileDir
	 * @param filePath
	 * @param fileName
	 * @param colorWords
	 */
	public static void makePDF(String pdfFileDir, String filePath,
			String fileName, String colorWords) {
		try {

			// 首先创建一个字体
			// BaseFont bfChinese = BaseFont.createFont("STSong-Light",
			// "UniGB-UCS2-H", BaseFont.EMBEDDED);//设置文本格式
			// BaseFont bfChinese = BaseFont.createFont("Courier-New",
			// "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			BaseFont bfChinese = BaseFont.createFont(
					"C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font keyFont = new Font(bfChinese, 15, Font.BOLD, Color.red);// 设置字体大小

			String line = null;
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);// 设置左右上边界距离

			BufferedReader in = new BufferedReader(new FileReader(new File(filePath)));
			System.out.println("txtFileName:"+ fileName.substring(0, fileName.lastIndexOf(".")));
			// String pdfSaveDir="D:\\Myeclipse 8.6-32\\LuceneSerach\\PDF";
			// File padFile=new
			// File(fileName.substring(0,fileName.indexOf("."))+".pdf");
			//String nowdate=DateUtil.getDateTime();
			//System.out.println("nowdate"+nowdate);
			String pdfFilePath = pdfFileDir + File.separator+ fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
			System.out.println("建立PDF文件路径:" + pdfFilePath);
			File padFile = new File(pdfFilePath);
			PdfWriter pdfWriter=PdfWriter.getInstance(document, new FileOutputStream(padFile));
			document.open();
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				// Paragraph pap=new Paragraph()
				sb.append(line);
			}
			in.close();
			
			String allText = sb.toString();
			// int allTextStart=0;
			Paragraph paragraph = new Paragraph();
			String afterKeyWords="";
			while (allText.length() > 0) {
				int locate = allText.toUpperCase().indexOf(
						colorWords.toUpperCase());
				if (locate > 0) {
					System.out.println("keyWords:" + colorWords + "-->>>"+ locate);
					String beforeString = allText.substring(0, locate - 1)+"  ";
					Chunk BeforekeyWords = new Chunk(beforeString);
					Chunk chunk = new Chunk(colorWords, keyFont);
					 afterKeyWords = allText.substring(locate
							+ colorWords.length(), allText.length());
					
					paragraph.add(BeforekeyWords);
					paragraph.add(chunk);
					allText = afterKeyWords;
				} else {
					break;
				}
			}
			Chunk AfterKeyWords = new Chunk(afterKeyWords);
			paragraph.add(AfterKeyWords);
			paragraph.setAlignment(Element.ALIGN_LEFT);
			
			document.add(paragraph);
			document.close();
			pdfWriter.flush();
			pdfWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	/*
	 * public static void editPDF(String padfFilePath,String tagWords) { try {
	 * // pdfwithText //Document document; // document = new
	 * Document(PageSize.A4, 50, 50, 50, 50);//设置左右上边界距离
	 * 
	 * // PdfWriter pdfwriter = PdfWriter.getInstance(document, new
	 * FileOutputStream(new File(padfFilePath))); // int pageCount =
	 * helloDocument.getNumberOfPages(); PDDocument helloDocument =
	 * PDDocument.load(new File(padfFilePath)); PDPage firstPage =
	 * (PDPage)helloDocument.getDocumentCatalog().getAllPages().get(0); //
	 * PDPageContentStream content = new PDPageContentStream(helloDocument,
	 * firstPage); PDStream contents = firstPage.getContents();
	 * 
	 * PDFStreamParser parser = new PDFStreamParser(contents.getStream());
	 * parser.parse(); java.util.List<Object> tokens = parser.getTokens(); for
	 * (int j = 0; j < tokens.size(); j++) { Object next = tokens.get(j); if
	 * (next instanceof PDFOperator) { PDFOperator op = (PDFOperator) next; //
	 * Tj and TJ are the two operators that display strings in a PDF if
	 * (op.getOperation().equals("Tj")) { // Tj takes one operator and that is
	 * the string // to display so lets update that operator COSString previous
	 * = (COSString) tokens.get(j - 1); String string = previous.getString();
	 * string = string.replaceFirst(tagWords, "Hello Wang");
	 * if(string.contentEquals(tagWords)){//找到匹配的关键词 PdfContentByte pcb =
	 * pdfwriter.getDirectContent(); Graphics2D g; document.open();
	 * 
	 * g = pcb.createGraphicsShapes(300, 200); g.setColor(Color.BLUE);
	 * g.setFont(new Font("Tahoma", Font.ITALIC, 10)); g.drawString(tagWords,
	 * 20, 50); g.dispose(); document.open(); } //Word you want to change.
	 * Currently this code changes word "Solr" to "Solr123" previous.reset();
	 * previous.append(string.getBytes("ISO-8859-1")); } else if
	 * (op.getOperation().equals("TJ")) { COSArray previous = (COSArray)
	 * tokens.get(j - 1); for (int k = 0; k < previous.size(); k++) { Object
	 * arrElement = previous.getObject(k); if (arrElement instanceof COSString)
	 * { COSString cosString = (COSString) arrElement; String string =
	 * cosString.getString(); string = string.replaceFirst(tagWords,
	 * "Hello Wang");
	 * 
	 * if(string.contentEquals(tagWords)){//找到匹配的关键词 PdfContentByte pcb =
	 * pdfwriter.getDirectContent(); Graphics2D g; document.open();
	 * 
	 * g = pcb.createGraphicsShapes(300, 200); g.setColor(Color.BLUE);
	 * g.setFont(new Font("Tahoma", Font.ITALIC, 10)); g.drawString(tagWords,
	 * 20, 50); g.dispose(); document.open(); } cosString.reset();
	 * cosString.append(string.getBytes("ISO-8859-1")); } } } } } // now that
	 * the tokens are updated we will replace the page content stream. PDStream
	 * updatedStream = new PDStream(helloDocument); OutputStream out =
	 * updatedStream.createOutputStream(); ContentStreamWriter tokenWriter = new
	 * ContentStreamWriter(out); tokenWriter.writeTokens(tokens);
	 * firstPage.setContents(updatedStream); helloDocument.save(padfFilePath);
	 * //Output file name helloDocument.close(); // PDFTextStripper textStripper
	 * = new PDFTextStripper(); //
	 * System.out.println(textStripper.getText(helloDocument)); //
	 * helloDocument.close(); } catch (Exception e) { e.printStackTrace(); } }
	 */
	public static void main(String[] args) throws IOException {
		/*
		 * String path="D:\\Lucene Text"; File dir = new File(path);
		 * System.out.print("path:---->" + path );
		 */
		// 判断是否是有此文件目录
		/*
		 * if (!dir.isDirectory()) { System.out.println("没有此目录"); return; }
		 */
		// editPDF("D:\\Lucene Text\\1\\ner.pdf","interferon");
		// generatePDF("D:\\Myeclipse 8.6-32\\LuceneSerach\\PDF","D:\\Myeclipse 8.6-32\\LuceneSerach\\WebRoot\\Lucene Text","interferon");
		editPDF("D:\\Lucene Text\\1\\Extended_Interferon-Alpha_Therapy_Accelerates.pdf", "Est","D:\\Lucene Text\\1\\test.pdf");
	  // editPDFTest("D:\\Lucene Text\\1\\Extended_Interferon-Alpha_Therapy_Accelerates.pdf","The","D:\\Lucene Text\\1\\test.pdf");//,
	}
}