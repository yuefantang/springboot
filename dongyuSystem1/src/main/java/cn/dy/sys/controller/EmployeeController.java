package cn.dy.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.dy.sys.constants.Constants;
import cn.dy.sys.dto.EmployeeDTO;
import cn.dy.sys.dto.common.ResultDTO;
import cn.dy.sys.dto.common.Searchable;
import cn.dy.sys.dto.convertor.EmployeeConvertor;
import cn.dy.sys.model.employee.Employee;
import cn.dy.sys.repository.EmployeeRepository;
import cn.dy.sys.service.EmployeeService;

@RestController
@RequestMapping("/m/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeConvertor employeeConvertor;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 检索员工
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO<?> list(final Searchable searchable,
            final Pageable pageable) {
        final Page<Employee> modelPage =
                this.employeeRepository.findAll(searchable, pageable);

        final ResultDTO<List<EmployeeDTO>> resultDTO = this.employeeConvertor.toResultDTO(modelPage);
        return resultDTO;
    }

    /**
     * 新增员工
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO<?> create(@RequestBody final EmployeeDTO dto) {
        final Employee model = this.employeeConvertor.toModel(dto);
        final Employee save = this.employeeRepository.save(model);
        return this.employeeConvertor.toResultDTO(save);
    }

    /**
     * 修改员工
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResultDTO<?> update(@RequestBody final EmployeeDTO dto, @PathVariable final Long id) {
        final Employee model = this.employeeRepository.findOne(id);
        final Employee update = this.employeeConvertor.update(dto, model);
        final Employee save = this.employeeRepository.save(update);
        return this.employeeConvertor.toResultDTO(save);
    }

    /**
     * 删除员工
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResultDTO<?> delete(@PathVariable final Long id) {
        final Employee model = this.employeeRepository.findOne(id);
        model.setIsDeleted(Constants.IS_DELETE_YES);
        final Employee save = this.employeeRepository.save(model);
        return this.employeeConvertor.toResultDTO(save);
    }

    /**
     * 员工详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultDTO<?> get(@PathVariable final Long id) {
        final Employee model = this.employeeRepository.findOne(id);
        return this.employeeConvertor.toResultDTO(model);
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/excel/download.do", method = RequestMethod.GET)
    public ModelAndView generateExcel(@RequestParam("s_job") final String job,
            @RequestParam("s_member") final String member,
            @RequestParam("s_idcard") final String idcard,
            final Searchable searchable,
            final HttpServletResponse getResponse)
            throws Exception {
        System.out.println(System.currentTimeMillis());

        final List<Employee> employees =
                this.employeeService.findAll(searchable, job, member, idcard);

        final ModelMap modelMap = new ModelMap();
        final String strFileName = "员工查询" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
        modelMap.addAttribute("filename", strFileName);
        modelMap.addAttribute("data", employees);
        return new ModelAndView("employeeExcelView", modelMap);

    }

    /**
     * 导入
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultDTO<?> upload(@RequestPart("file") final MultipartFile file) {

        //        final LeadsImportResultDTO leadsImportResultDTO = this.leadsService.importLeads(file);
        //
        //        return ResultDTO.success(leadsImportResultDTO);
        return null;
    }

    /**
     * 模板下载
     */
    @RequestMapping(value = "/template/download.do", method = RequestMethod.GET)
    public ModelAndView generateTemplate() {

        final ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("filename", "员工管理导入模板.xls");
        modelMap.addAttribute("data", new ArrayList<>());

        return new ModelAndView("employeeImportExcelView", modelMap);
    }
}
