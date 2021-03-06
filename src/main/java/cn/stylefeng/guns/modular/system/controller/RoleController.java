/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.cache.Cache;
import cn.stylefeng.guns.core.common.constant.dictmap.RoleDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.util.CacheUtil;
import cn.stylefeng.guns.modular.system.model.Dict;
import cn.stylefeng.guns.modular.system.model.Menu;
import cn.stylefeng.guns.modular.system.model.Role;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.res.RoleRes;
import cn.stylefeng.guns.modular.system.service.IRoleService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.system.warpper.RoleWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static String PREFIX = "/system/role";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/role.html";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd() {
        return PREFIX + "/role_add.html";
    }

    /**
     * 跳转到修改角色
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.selectById(roleId);
        model.addAttribute(role);
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_edit.html";
    }

    /**
     * 跳转到角色分配
     */
    @Permission
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return PREFIX + "/role_assign.html";
    }

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String roleName) {
        List<Map<String, Object>> roles = this.roleService.selectRoles(super.getPara("roleName"));
       // select count(1) from  `sys_user` where status = 1 and roleid = 1


        for (int i = 0; i <roles.size() ; i++) {

           String II =  roles.get(i).get("id").toString();

            RoleRes res = this.userService.selectCountRoleIdById(roles.get(i).get("id").toString());
            if (res==null || StringUtils.isBlank(res.getRoleid())){
                roles.get(i).put("version",0);
            }else{
                roles.get(i).put("version",res.getVersion());
            }
        }

        return super.warpObject(new RoleWarpper(roles));
    }

    /**
     * 角色新增
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "添加角色", key = "name", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        Role temp = new Role();
        temp.setName(role.getName());
        Role pMenu = this.roleService.selectOne(new EntityWrapper<>(temp));

        if (pMenu != null) {
            throw new ServiceException(BizExceptionEnum.ROLE_EXISTED);
        } else {
        role.setId(null);
        role.setPid(0);
        role.setDeptid(24);
        this.roleService.insert(role);
        return SUCCESS_TIP;
        }
    }

    /**
     * 角色修改
     */
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改角色", key = "name", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData edit(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.updateById(role);

        //删除缓存
        CacheUtil.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }




    /**
     * 删除角色
     */
    @RequestMapping(value = "/removeBatch")
    @BussinessLog(value = "批量删除角色", key = "roleId", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData removeBatch(@RequestParam Integer roleId) {

        if (ToolUtil.isEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        this.roleService.delRoleById(roleId);

        //删除缓存
        CacheUtil.removeAll(Cache.CONSTANT);

        return SUCCESS_TIP;
    }







    /**
     * 查看角色
     */
    @RequestMapping(value = "/view/{roleId}")
    @ResponseBody
    public ResponseData view(@PathVariable Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.selectById(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = this.roleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable String userId) {
      //  User theUser = this.userService.selectById(userId);
      //  String roleid = theUser.getRoleid();
        String  roleid = null;
        if (ToolUtil.isEmpty(roleid)) {
            return this.roleService.roleTreeList();
        } else {
            String[] strArray = roleid.split(",");
            return this.roleService.roleTreeListByRoleId(strArray);
        }
    }

}
