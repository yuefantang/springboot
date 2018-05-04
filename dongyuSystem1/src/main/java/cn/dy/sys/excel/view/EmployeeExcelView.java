package cn.dy.sys.excel.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

import cn.dy.sys.model.employee.Employee;
import cn.dy.sys.util.excel.BaseExcelViewOld;

@Component
public class EmployeeExcelView extends BaseExcelViewOld {

    public EmployeeExcelView() {
        super.setUrl("classpath:/excel/employee");
        super.setChildExcelViewType("EmployeeExcelView");
    }

    @Override
    protected void buildExcelDocumentContents(final Map<String, Object> model, final HSSFWorkbook workbook,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        final Iterable<Employee> employees = (Iterable<Employee>) model.get("data");

        final HSSFCellStyle defaultHeaderCellStyle = this.buildDefaultHeaderCellStyle(workbook);
        final HSSFCellStyle defaultCellStyle = this.buildDefaultCellStyle(workbook);

        HSSFSheet sheet = workbook.getSheetAt(1);

        int rowNumber = -1;
        int colNumber = -1;

        Cell cell = null;

        int count = 1;
        int sheetNumber = 1;
        final int sheetCount = 10000; // sheet中最大条数
        final String sheetName = sheet.getSheetName();

        for (final Employee employee : employees) {

            if (count > sheetCount) {
                count = 1;
                sheetNumber++;
                workbook.setSheetName(1, sheetName + "(" + String.valueOf(1) + ")");
            }

            if (count == 1) {

                if (sheetNumber > 1) {
                    sheet = workbook.cloneSheet(0);
                    workbook.setSheetName(sheetNumber, sheetName + "(" + String.valueOf(sheetNumber) + ")");
                }
                rowNumber = 0;
                colNumber = 0;

                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("#");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("编号");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("姓名");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("性别");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("出生日期");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("籍贯");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("在职情况");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("学历");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("入职时间");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("身份证号");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("家庭住址");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("联系人信息");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("部门");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("岗位");
                cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultHeaderCellStyle);
                cell.setCellValue("备注");
            }

            rowNumber++;
            colNumber = 0;

            //行号
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            if (sheetNumber == 1) {
                cell.setCellValue(count++);
            } else {
                cell.setCellValue((sheetCount * (sheetNumber - 1)) + count++);
            }

            //编号
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeMember() != null ? employee.getEmployeeMember() : "");

            //姓名
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeName() != null ? employee.getEmployeeName() : "");

            //性别
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeGender() != null ? employee.getEmployeeGender() : "");

            //出生日期
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeBirthday() != null ? employee.getEmployeeBirthday() : "");

            //籍贯
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeBirthPlace() != null ? employee.getEmployeeBirthPlace() : "");

            //在职情况
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeJob() != null ? employee.getEmployeeJob() : "");

            //学历
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeEducation() != null ? employee.getEmployeeEducation() : "");

            //入职时间
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeEntryDate() != null ? employee.getEmployeeEntryDate() : "");

            //身份证号
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeIdcard() != null ? employee.getEmployeeIdcard() : "");

            //家庭住址
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeAddress() != null ? employee.getEmployeeAddress() : "");

            //联系人信息
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeContact() != null ? employee.getEmployeeContact() : "");

            //部门
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeDepartment() != null ? employee.getEmployeeDepartment() : "");

            //岗位
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeePost() != null ? employee.getEmployeePost() : "");

            //备注
            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            cell.setCellValue(employee.getEmployeeRemark() != null ? employee.getEmployeeRemark() : "");

            //            cell = this.buildMergedRowCell(sheet, rowNumber, colNumber++, defaultCellStyle);
            //            cell.setCellValue(leads.getValidDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(leads
            //                    .getValidDate()) : "");
        }
    }

}
