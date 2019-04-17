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
import cn.stylefeng.guns.modular.system.model.Config;
import cn.stylefeng.guns.modular.system.service.IConfigService;

/**
 * 上传管理控制器
 *
 * @author fengshuonan
 * @Date 2019-04-17 17:10:59
 */
@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController {

    private String PREFIX = "/system/config/";

    @Autowired
    private IConfigService configService;

    /**
     * 跳转到上传管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "config.html";
    }

    /**
     * 跳转到添加上传管理
     */
    @RequestMapping("/config_add")
    public String configAdd() {
        return PREFIX + "config_add.html";
    }

    /**
     * 跳转到修改上传管理
     */
    @RequestMapping("/config_update/{configId}")
    public String configUpdate(@PathVariable Integer configId, Model model) {
        Config config = configService.selectById(configId);
        model.addAttribute("item",config);
        LogObjectHolder.me().set(config);
        return PREFIX + "config_edit.html";
    }

    /**
     * 获取上传管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return configService.selectList(null);
    }

    /**
     * 新增上传管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Config config) {
        configService.insert(config);
        return SUCCESS_TIP;
    }

    /**
     * 删除上传管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer configId) {
        configService.deleteById(configId);
        return SUCCESS_TIP;
    }

    /**
     * 修改上传管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Config config) {
        configService.updateById(config);
        return SUCCESS_TIP;
    }

    /**
     * 上传管理详情
     */
    @RequestMapping(value = "/detail/{configId}")
    @ResponseBody
    public Object detail(@PathVariable("configId") Integer configId) {
        return configService.selectById(configId);
    }
}
