package com.perfect.admin.controllers;

import com.perfect.admin.utils.JsonViews;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.dto.sys.UserModuleMenuDTO;
import com.perfect.service.SystemUserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户模块控制器
 * <p>
 * 包括模块的新增以及菜单的维护,另外维护模块对应的账号信息
 * <p>
 * Created by yousheng on 15/12/15.
 */
@RestController
public class UserModuleController {

    @Resource
    private SystemUserService systemUserService;

    @RequestMapping(value = "/users/{userid}/menus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateUserModules(@PathVariable("userid") String userid, @RequestBody UserModuleMenuDTO userModuleMenuDTO) {
        if (userModuleMenuDTO == null) {
            return JsonViews.generate(-1, "无菜单信息.");
        }

        boolean success = systemUserService.updateUserModuleMenus(userid, userModuleMenuDTO);

        if (success) {
            return JsonViews.generateSuccessNoData();
        } else {
            return JsonViews.generateFailedNoData();
        }
    }

    @RequestMapping(value = "/users/{userid}/modules", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addModule(@PathVariable("userid") String userid, @RequestParam("moduleid") String moduleId) {

        boolean success = systemUserService.addModule(userid, moduleId);
        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generateFailedNoData();

    }

    @RequestMapping(value = "/users/{userid}/modules/{usermoduleid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteModule(@PathVariable("userid") String userid, @PathVariable("usermoduleid") String usermoduleid) {

        boolean success = systemUserService.deleteModule(userid, usermoduleid);
        if (success) {
            return JsonViews.generateSuccessNoData();
        }
        return JsonViews.generateFailedNoData();
    }


    @RequestMapping(value = "/users/{userid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView userModules(@PathVariable("userid") String userid) {

        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserService.getUserModules(userid);

        if (systemUserModuleDTOs == null || systemUserModuleDTOs.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }
        return JsonViews.generate(JsonResultMaps.successMap(systemUserModuleDTOs));

    }

    @RequestMapping(value = "/users/{userid}/modules/{usermoduleid}/submenus", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView updateUserModuleSubMenus(@PathVariable("userid") String id, @PathVariable("usermoduleid") String
            usermoduleid, @RequestBody List<SystemMenuDTO> menus) {

        if (menus == null) {
            return JsonViews.generateFailedNoData();
        }

        boolean success = systemUserService.updateUserModuleMenus(id, usermoduleid, menus);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }

    @RequestMapping(value = "/users/{userid}/modules/{moduleid}/submenus/{submenuid}", method = RequestMethod.DELETE, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView deleteUserModuleSubMenus(@PathVariable("userid") String id, @PathVariable("moduleid") String
            modulename, @PathVariable("submenuid") String submenuid, @RequestParam("menus") String menus) {

        boolean success = systemUserService.updateUserModuleMenus(id, modulename, null);

        if (success) {
            return JsonViews.generateSuccessNoData();
        }

        return JsonViews.generate(-1);
    }

    @RequestMapping(value = "/users/{userid}/modules/{moduleid}/submenus", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ModelAndView getUserModuleSubMenus(@PathVariable("userid") String id, @PathVariable("moduleid") String
            moduleid) {


        List<SystemMenuDTO> systemMenuDTOs = systemUserService.getUserSubMenu(id, moduleid);

        if (systemMenuDTOs == null) {
            return JsonViews.generateFailedNoData();
        }

        return JsonViews.generate(JsonResultMaps.successMap(systemMenuDTOs));
    }


    @RequestMapping(value = "/users/{userid}/modules/{moduleid}/accounts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView createModuleAccount(@PathVariable("userid") String id, @PathVariable("moduleid") String
            moduleid, @RequestBody ModuleAccountInfoDTO moduleAccountInfoDTO) {


        boolean created = systemUserService.addModuleAccount(id, moduleid, moduleAccountInfoDTO);

        if (created) {
            return JsonViews.generateSuccessNoData();
        } else {
            return JsonViews.generateFailedNoData();
        }
    }
}