package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.ReportOne;
import cn.stylefeng.guns.modular.system.service.IReportOneService;

/**
 * 报表一控制器
 *
 * @author fengshuonan
 * @Date 2019-04-17 18:13:05
 */
@Controller
@RequestMapping("/reportOne")
public class ReportOneController extends BaseController {

    private String PREFIX = "/system/reportOne/";

    @Autowired
    private IReportOneService reportOneService;

    /**
     * 跳转到报表一首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "reportOne.html";
    }

    /**
     * 跳转到添加报表一
     */
    @RequestMapping("/reportOne_add")
    public String reportOneAdd() {
        return PREFIX + "reportOne_add.html";
    }

    /**
     * 跳转到修改报表一
     */
    @RequestMapping("/reportOne_update/{reportOneId}")
    public String reportOneUpdate(@PathVariable Integer reportOneId, Model model) {
        ReportOne reportOne = reportOneService.selectById(reportOneId);
        model.addAttribute("item",reportOne);
        LogObjectHolder.me().set(reportOne);
        return PREFIX + "reportOne_edit.html";
    }

    /**
     * 获取报表一列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return reportOneService.selectList(null);
    }

    /**
     * 新增报表一
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ReportOne reportOne) {
        reportOneService.insert(reportOne);
        return SUCCESS_TIP;
    }

    /**
     * 删除报表一
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer reportOneId) {
        reportOneService.deleteById(reportOneId);
        return SUCCESS_TIP;
    }

    /**
     * 修改报表一
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ReportOne reportOne) {
        reportOneService.updateById(reportOne);
        return SUCCESS_TIP;
    }

    /**
     * 报表一详情
     */
    @RequestMapping(value = "/detail/{reportOneId}")
    @ResponseBody
    public Object detail(@PathVariable("reportOneId") Integer reportOneId) {
        return reportOneService.selectById(reportOneId);
    }
}
