package cn.dy.sys.util.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class BaseExcelViewOld extends AbstractExcelView {
	
	private String childExcelViewType;

	

    public String getChildExcelViewType() {
		return childExcelViewType;
	}

	public void setChildExcelViewType(String childExcelViewType) {
		this.childExcelViewType = childExcelViewType;
	}

	@Override
    protected final void buildExcelDocument(final Map<String, Object> model, final HSSFWorkbook workbook,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final String filename = (String) model.get("filename");
        if (filename != null) {
            response.setHeader("Content-Disposition", "attachment; " + this.encodeFilename(request, filename));
        }

        this.buildExcelDocumentContents(model, workbook, request, response);
    }

    protected void buildExcelDocumentContents(final Map<String, Object> model, final HSSFWorkbook workbook,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {

    }
    

    /**
     * 构建Excel表体的默认单元格格式
     *
     * @param workbook
     * @return
     */
    protected HSSFCellStyle buildDefaultCellStyle(final HSSFWorkbook workbook) {
        final HSSFCellStyle defaultCellStyle = workbook.createCellStyle();
        defaultCellStyle.setWrapText(true);
        defaultCellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        defaultCellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        defaultCellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        defaultCellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
        defaultCellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
        defaultCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        final HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        defaultCellStyle.setFont(font);

        return defaultCellStyle;
    }

    /**
     * 构建Excel表头的默认单元格格式
     *
     * @param workbook
     * @return
     */
    protected HSSFCellStyle buildDefaultHeaderCellStyle(final HSSFWorkbook workbook) {
        final HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(false);
        cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        final HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("宋体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 合并单元格
     *
     * @param sheet Sheet
     * @param rowStart 起始行（从0开始）
     * @param colStart 结束列（从0开始）
     * @param cellStyle 单元格格式
     * @return 合并后的单元格
     */
    protected Cell buildMergedRowCell(final HSSFSheet sheet, final int rowStart, final int colStart,
            final HSSFCellStyle cellStyle) {
        return this.buildMergedRowCell(sheet, rowStart, rowStart, colStart, colStart, cellStyle);
    }

    /**
     * 合并单元格
     *
     * @param sheet Sheet
     * @param rowStart 起始行（从0开始）
     * @param rowEnd 结束行（从0开始）
     * @param colStart 起始列（从0开始）
     * @param colEnd 结束列（从0开始）
     * @param cellStyle 单元格格式
     * @return 合并后的单元格
     */
    protected Cell buildMergedRowCell(final HSSFSheet sheet, final int rowStart, final int rowEnd,
            final int colStart, final int colEnd,
            final HSSFCellStyle cellStyle) {
        final CellRangeAddress ca = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);

        sheet.addMergedRegion(ca);
        HSSFRow row = sheet.getRow(rowStart);
        if (row == null) {
            row = sheet.createRow(rowStart);
        }
        final Cell cell = row.createCell(colStart);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 构建单元格
     *
     * @param sheet
     * @param rowNumber
     * @param colNumber
     * @param cellStyle
     * @return
     */
    protected Cell buildCell(final HSSFSheet sheet, final int rowNumber, final int colNumber,
            final HSSFCellStyle cellStyle) {
        HSSFRow row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }
        final Cell cell = row.createCell(colNumber);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    protected String encodeFilename(final HttpServletRequest request, final String filename) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            userAgent = "";
        }
        try {
            userAgent = userAgent.toLowerCase();
            if ((userAgent.indexOf("msie") != -1)
                    || (userAgent.toLowerCase().indexOf("like gecko") != -1)) {
                // IE浏览器，只能采用URLEncoder编码
                return "filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"";
            } else if (userAgent.indexOf("opera") != -1) {
                // Opera浏览器只能采用filename*
                return "filename*=UTF-8''" + filename;
            } else if (userAgent.indexOf("safari") != -1) {
                // Safari浏览器，只能采用ISO编码的中文输出
                return "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
            } else if (userAgent.indexOf("applewebkit") != -1) {
                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                final String newFilename = MimeUtility.encodeText(filename, "UTF8", "B");
                return "filename=\"" + newFilename + "\"";
            } else if (userAgent.indexOf("mozilla") != -1) {
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                return "filename*=UTF-8''" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "";
            } else {
                return "filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"";
            }
        } catch (final UnsupportedEncodingException e) {
            return "filename=\"" + filename + "\"";
        }
    }

    protected Object getData(final Map<String, Object> model) {
        return model.get("data");
    }
    
    
    
    public static File zip(ArrayList<HSSFWorkbook> wblist,String zipFileName,String filename) throws IOException{
    	File target =  new File(zipFileName);

	    FileOutputStream fos = null;
	    ZipOutputStream zos = null;
	    
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
    	
        
	    try {
	        //先生成zip文件
	    	fos = new FileOutputStream(target);
	        zos = new ZipOutputStream(new BufferedOutputStream(fos));
	        
	    	int i=1;
	    	String tempFileName;
	        for(HSSFWorkbook wb:wblist){
	    		//生成多个excel文件,名字后面加1位数字
	        	tempFileName = filename.replace(".xls", "_"+i+".xls");
	        	
		        File f = new File(tempFileName);
		    	FileOutputStream wbfos = new FileOutputStream(f); 
		    	wb.write(wbfos); 
		    	
		    	//读入excel文件
		        byte[] buffer = new byte[1024 * 10];
		        fis = new FileInputStream(f);
		        bis = new BufferedInputStream(fis, buffer.length);
		        
		        //将源文件压缩到zip文件中
		        int read = 0;
		        zos.putNextEntry(new ZipEntry(tempFileName));
		        while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
		            zos.write(buffer, 0, read);
		        }
		        zos.closeEntry();
				i++;
			}
 	    } finally {
	    	bis.close();
	    	fis.close();
	    	zos.close();
	    	fos.close();
	    }
	
	    return target;
    }
    
    
	
	

}
