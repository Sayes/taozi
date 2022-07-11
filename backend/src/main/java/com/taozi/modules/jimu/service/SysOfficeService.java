package com.taozi.modules.jimu.service;

import com.taozi.modules.jimu.entity.SysOffice;
import com.taozi.modules.jimu.vo.SysOfficeDropdownVO;

import java.util.List;

public interface SysOfficeService {

    List<SysOfficeDropdownVO> queryDropdownByPid(String pid);
}
