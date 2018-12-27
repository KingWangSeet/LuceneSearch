package cn.edu.nwsuaf.util;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class pdfTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);  
        PdfWriter pdfwriter;  
        try {  
            pdfwriter = PdfWriter.getInstance(document, new FileOutputStream("D:\\Lucene Text\\1\\ner.pdf"));  
            document.open();  
            PdfContentByte pcb = pdfwriter.getDirectContent();  
            Graphics2D g;  
            String tagWords="interferon";
           // for (int i = 0; i < 9; i++) {  
                document.newPage();  
                g = pcb.createGraphicsShapes(700,700);  
                g.setColor(Color.red);  
                g.setFont(new Font("Tahoma", Font.ITALIC, 10));  
                g.drawString(tagWords, 10, tagWords.length()*10);  
                 
                g.dispose();  
           // }  
            document.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
