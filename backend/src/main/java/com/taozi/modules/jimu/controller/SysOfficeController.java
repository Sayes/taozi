package com.taozi.modules.jimu.controller;

import com.taozi.common.api.vo.Result;
import com.taozi.modules.jimu.service.SysOfficeService;
import com.taozi.modules.jimu.vo.SysOfficeDropdownVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 积木报表-机构数据
 * @Date: 2022-04-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "")
@RestController
@RequestMapping("/v1/jimu/sys_office")
public class SysOfficeController {

    @Autowired
    private SysOfficeService sysOfficeService;

    @GetMapping("tree")
    public List<SysOfficeDropdownVO> tree(@RequestParam(name = "pid", required = false) String pid) {
        List<SysOfficeDropdownVO> list = sysOfficeService.queryDropdownByPid(pid);
        return list;
    }
}
