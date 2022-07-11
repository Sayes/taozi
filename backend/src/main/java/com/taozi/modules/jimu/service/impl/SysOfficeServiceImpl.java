package com.taozi.modules.jimu.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.taozi.modules.jimu.entity.SysOffice;
import com.taozi.modules.jimu.mapper.SysOfficeMapper;
import com.taozi.modules.jimu.service.SysOfficeService;
import com.taozi.modules.jimu.vo.SysOfficeDropdownVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class SysOfficeServiceImpl implements SysOfficeService {

    @Resource
    private SysOfficeMapper sysOfficeMapper;


    @DS("dadao") // 机构些数据在大道的库
    @Override
    public List<SysOfficeDropdownVO> queryDropdownByPid(String pid) {
        if (StringUtils.isBlank(pid)) {
            pid = "0";
        }
        List<SysOffice> list = sysOfficeMapper.selectList(new QueryWrapper<SysOffice>()
                .eq("parent_id", pid));

        List<SysOfficeDropdownVO> voList = new LinkedList<>();
        for (SysOffice sysOffice : list) {
            SysOfficeDropdownVO vo = new SysOfficeDropdownVO();
            vo.setId(sysOffice.getId());
            vo.setPid(sysOffice.getParentId());
            vo.setValue(sysOffice.getId());
            vo.setTitle(sysOffice.getName());
            voList.add(vo);
        }
        return voList;
    }
}
