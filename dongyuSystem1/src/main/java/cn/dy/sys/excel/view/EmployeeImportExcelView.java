package cn.dy.sys.excel.view;

import org.springframework.stereotype.Component;

import cn.dy.sys.util.excel.BaseExcelView;

@Component
public class EmployeeImportExcelView extends BaseExcelView {

    public EmployeeImportExcelView() {
        super.setUrl("classpath:/excel/employeeImport");
        super.setChildExcelViewType("employeeImportExcelView");
    }

}
