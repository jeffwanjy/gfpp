package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.util.UnicodeUtil;
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
 * 配置表控制器
 *
 * @author fengshuonan
 * @Date 2019-04-22 16:50:16
 */
@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController {

    private String PREFIX = "/system/config/";

    @Autowired
    private IConfigService configService;

    /**
     * 跳转到配置表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "config.html";
    }

    /**
     * 跳转到添加配置表
     */
    @RequestMapping("/config_add")
    public String configAdd() {
        return PREFIX + "config_add.html";
    }

    /**
     * 跳转到修改配置表
     */
    @RequestMapping("/config_update/{configId}")
    public String configUpdate(@PathVariable Integer configId, Model model) {
        Config config = configService.selectById(configId);
        config.setOperations(UnicodeUtil.decode(config.getOperations()));
        model.addAttribute("item",config);
        LogObjectHolder.me().set(config);
        return PREFIX + "config_edit.html";
    }

    /**
     * 获取配置表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return configService.selectList(null);
    }

    /**
     * 新增配置表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Config config) throws Exception {
        config.setOperations(UnicodeUtil.encode(config.getOperations()));
        configService.insert(config);
        return SUCCESS_TIP;
    }

    /**
     * 删除配置表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer configId) {
        configService.deleteById(configId);
        return SUCCESS_TIP;
    }

    /**
     * 修改配置表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Config config) {
        configService.updateById(config);
        return SUCCESS_TIP;
    }

    /**
     * 配置表详情
     */
    @RequestMapping(value = "/detail/{configId}")
    @ResponseBody
    public Object detail(@PathVariable("configId") Integer configId) {
        return configService.selectById(configId);
    }
}
