package com.taozi.modules.demo.service.impl;

import com.taozi.modules.demo.entity.Project;
import com.taozi.modules.demo.mapper.ProjectMapper;
import com.taozi.modules.demo.service.IProjectService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 年度项目信息数据
 * @Date:   2022-03-23
 * @Version: V1.0
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

}
