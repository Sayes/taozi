package com.taozi.modules.demo.controller;

import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.taozi.common.api.vo.Result;
import com.taozi.common.system.base.entity.TaoziAppMenu;
import com.taozi.common.system.query.QueryGenerator;
//import org.jeecg.common.aspect.annotation.AutoLog;
import com.taozi.modules.demo.entity.Project;
import com.taozi.modules.demo.service.IProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taozi.common.system.base.controller.TaoziController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.taozi.common.system.base.entity.TaoziOneItemPage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 年度项目信息数据
 * @Date:   2022-03-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="年度项目信息数据")
@RestController
@RequestMapping("/v1/project")
public class ProjectController extends TaoziController<Project, IProjectService> {
	@Autowired
	private IProjectService projectService;

	@Autowired
	TaoziAppMenu appMenu;

	 /**
	 * 分页列表查询
	 *
	 * @param project
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	// SYZ, TODO
	//@AutoLog(value = "年度项目信息数据-分页列表查询")
	@ApiOperation(value="年度项目信息数据-分页列表查询", notes="年度项目信息数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Project project,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		if (!appMenu.allowAccess(req, "/projectInfo/project", "list")) {
			return Result.error(501, "没有权限");
		}

		if (project != null) {
			if (project.getProjectName() != null) {
				project.setProjectName("*" + project.getProjectName() + "*");
			}
			if (project.getYear() != null) {
				project.setYear("*" + project.getYear() + "*");
			}
		}
		QueryWrapper<Project> queryWrapper = QueryGenerator.initQueryWrapper(project, req.getParameterMap());
		Page<Project> page = new Page<Project>(pageNo, pageSize);
		IPage<Project> pageList = projectService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param project
	 * @return
	 */
	//@AutoLog(value = "年度项目信息数据-添加")
	@ApiOperation(value="年度项目信息数据-添加", notes="年度项目信息数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Project project, HttpServletRequest req) {

		if (!appMenu.allowAccess(req, "/projectInfo/project", "add")) {
			return Result.error(501, "没有权限");
		}

		projectService.save(project);
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param project
	 * @return
	 */
	//@AutoLog(value = "年度项目信息数据-编辑")
	@ApiOperation(value="年度项目信息数据-编辑", notes="年度项目信息数据-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody Project project, HttpServletRequest req) {

		if (!appMenu.allowAccess(req, "/projectInfo/project", "edit")) {
			return Result.error(501, "没有权限");
		}

		projectService.updateById(project);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "年度项目信息数据-通过id删除")
	@ApiOperation(value="年度项目信息数据-通过id删除", notes="年度项目信息数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/projectInfo/project", "delete")) {
			return Result.error(501, "没有权限");
		}

		projectService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	//@AutoLog(value = "年度项目信息数据-批量删除")
	@ApiOperation(value="年度项目信息数据-批量删除", notes="年度项目信息数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/projectInfo/project", "deleteBatch")) {
			return Result.error(501, "没有权限");
		}
		this.projectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "年度项目信息数据-通过id查询")
	@ApiOperation(value="年度项目信息数据-通过id查询", notes="年度项目信息数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/projectInfo/project", "queryById")) {
			return Result.error(501, "没有权限");
		}
		Project project = projectService.getById(id);
		TaoziOneItemPage<Project> page = new TaoziOneItemPage<>(project);
		return Result.OK(page);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param project
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Project project, HttpServletRequest req) {
	  if (!appMenu.allowAccess(req, "/projectInfo/project", "exportXls")) {
		  return null;
	  }
      return super.exportXls(request, project, Project.class, "年度项目信息数据");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response, HttpServletRequest req) {
	  if (!appMenu.allowAccess(req, "/projectInfo/project", "importExcel")) {
		  return null;
	  }
      return super.importExcel(request, response, Project.class);
  }

}
