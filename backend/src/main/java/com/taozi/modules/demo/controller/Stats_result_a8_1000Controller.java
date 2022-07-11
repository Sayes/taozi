package com.taozi.modules.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taozi.common.excel.ExcelInterceptor;
import com.taozi.common.system.base.entity.TaoziAppMenu;
import com.taozi.common.system.base.entity.TaoziSearchOpt01;
import com.taozi.common.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.taozi.common.api.vo.Result;
//import com.taozi.common.aspect.annotation.AutoLog;
import com.taozi.common.system.base.controller.TaoziController;
import com.taozi.common.system.query.QueryGenerator;
import com.taozi.modules.demo.entity.Stats_result_a8_1000;
import com.taozi.modules.demo.service.IStats_result_a8_1000Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 路面技术状况评定汇总表A-8
 * @Author: jeecg-boot
 * @Date:   2022-04-08
 * @Version: V1.0
 */
@Slf4j
@Api(tags="路面技术状况评定汇总表A-8")
@RestController
@RequestMapping("/v1/stats_result_a8_1000")
public class Stats_result_a8_1000Controller extends TaoziController<Stats_result_a8_1000, IStats_result_a8_1000Service> {
	@Autowired
	private IStats_result_a8_1000Service stats_result_a8_1000Service;

	@Autowired
	TaoziAppMenu appMenu;

	/**
	 * 分页列表查询
	 *
	 * @param stats_result_a8_1000
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "路面技术状况评定汇总表A-8-分页列表查询")
	@ApiOperation(value="路面技术状况评定汇总表A-8-分页列表查询", notes="路面技术状况评定汇总表A-8-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Stats_result_a8_1000 stats_result_a8_1000,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="20") Integer pageSize,
								   HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/stats_result_a8_1000", "list")) {
			return Result.error(501, "没有权限");
		}
		QueryWrapper<Stats_result_a8_1000> queryWrapper = QueryGenerator.initQueryWrapper(stats_result_a8_1000, req.getParameterMap());
		Page<Stats_result_a8_1000> page = new Page<Stats_result_a8_1000>(pageNo, pageSize);
		IPage<Stats_result_a8_1000> pageList = stats_result_a8_1000Service.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 查询项目id, 路线编码, 管养单位, 检测方向
	  * @param projectName
	  * @param roadCode
	  * @param maintain
	  */
	 @ApiOperation(value="路面技术状况评定汇总表A-8-搜索菜单项查询", notes="路面技术状况评定汇总表A-8-搜索菜单项查询")
	 @GetMapping(value = "/search_opt")
	 public Result<?> querySearchOpt(
			 @RequestParam(name="projectName", defaultValue = "") String projectName,
			 @RequestParam(name="roadCode", defaultValue = "") String roadCode,
			 @RequestParam(name="maintain", defaultValue = "") String maintain,
			 HttpServletRequest req) throws Exception {

		 StringBuffer sql = new StringBuffer();// 拼接sql
		 sql.append("select p.project_id as project_id, p.project_name as project_name from stats_result_a8_1000 as r, project as p where r.project_id = p.project_id ");

		 if (!projectName.isEmpty()) {
			 sql.append(" and p.project_name = '");
			 sql.append(projectName);
			 sql.append("' ");
		 }
		 sql.append(" group by r.project_id;");
		 System.out.println(sql.toString());
		 JSONObject tmpdata = executeQuerySql(sql.toString());
		 JSONArray tableTitle = tmpdata.getJSONArray("tableTitle");
		 JSONArray tableBody = tmpdata.getJSONArray("tableBody");
		 for (int j = 0; j < tableTitle.size(); j++)
		 {
			 JSONObject tableTitle_Th = (JSONObject) tableTitle.get(j);
			 //System.out.print(tableTitle_Th.get("columnname") + "\t");
		 }
		 //System.out.println("\n----------------------------------------");

		 TaoziSearchOpt01 searchOpt01 = new TaoziSearchOpt01();

		 for (int i = 0; i < tableBody.size(); i++)
		 {
			 JSONArray row = (JSONArray) tableBody.get(i);
			 //System.out.print((i + 1) + "\t");
			 for (int j = 0; j < row.size(); j++)
			 {
				 //System.out.print(row.get(j) + "\t");
				 if (j == 0) {
					 searchOpt01.addProjectId(row.get(j).toString());
				 } else if (j == 1) {
					 searchOpt01.addProjectName(row.get(j).toString());
				 }
			 }
			 //System.out.println();
		 }

		 return Result.OK(searchOpt01);
	 }

	 /**
	 * 添加
	 *
	 * @param stats_result_a8_1000
	 * @return
	 */
	//@AutoLog(value = "路面技术状况评定汇总表A-8-添加")
	@ApiOperation(value="路面技术状况评定汇总表A-8-添加", notes="路面技术状况评定汇总表A-8-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Stats_result_a8_1000 stats_result_a8_1000, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/stats_result_a8_1000", "add")) {
			return Result.error(501, "没有权限");
		}
		stats_result_a8_1000Service.save(stats_result_a8_1000);
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param stats_result_a8_1000
	 * @return
	 */
	//@AutoLog(value = "路面技术状况评定汇总表A-8-编辑")
	@ApiOperation(value="路面技术状况评定汇总表A-8-编辑", notes="路面技术状况评定汇总表A-8-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody Stats_result_a8_1000 stats_result_a8_1000, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/stats_result_a8_1000", "edit")) {
			return Result.error(501, "没有权限");
		}
		stats_result_a8_1000Service.updateById(stats_result_a8_1000);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "路面技术状况评定汇总表A-8-通过id删除")
	@ApiOperation(value="路面技术状况评定汇总表A-8-通过id删除", notes="路面技术状况评定汇总表A-8-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/stats_result_a8_1000", "delete")) {
			return Result.error(501, "没有权限");
		}
		stats_result_a8_1000Service.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	//@AutoLog(value = "路面技术状况评定汇总表A-8-批量删除")
	@ApiOperation(value="路面技术状况评定汇总表A-8-批量删除", notes="路面技术状况评定汇总表A-8-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/stats_result_a8_1000", "deleteBatch")) {
			return Result.error(501, "没有权限");
		}
		this.stats_result_a8_1000Service.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "路面技术状况评定汇总表A-8-通过id查询")
	@ApiOperation(value="路面技术状况评定汇总表A-8-通过id查询", notes="路面技术状况评定汇总表A-8-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id, HttpServletRequest req) {
		if (!appMenu.allowAccess(req, "/stats_result_a8_1000", "queryById")) {
			return Result.error(501, "没有权限");
		}
		Stats_result_a8_1000 stats_result_a8_1000 = stats_result_a8_1000Service.getById(id);
		return Result.OK(stats_result_a8_1000);
	}

    /**
     * 导出excel
     *
     * @param request
     * @param stats_result_a8_1000
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Stats_result_a8_1000 stats_result_a8_1000) {
		if (!appMenu.allowAccess(request, "/stats_result_a8_1000", "exportXls")) {
			return null;
		}
  	    Workbook template;
        try {
            // 这个模板文件放表头
            template = ExcelUtil.readFromResource("public/excel_templates/Stats_result_a8_1000.xls");
        } catch (FileNotFoundException e) {
            return new ModelAndView("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // 往model放入拦截器，让ModelAndView输出内容到HttpResponse前执行拦截器内容
        Map<String, Object> model = new HashMap<>();
        model.put(ExcelInterceptor.FLAG, (ExcelInterceptor) wb -> {
            Sheet firstSheet = wb.getSheetAt(0);
            ExcelUtil.deleteRows(firstSheet, 0, 3);
            int firstSheetRowCount = firstSheet.getPhysicalNumberOfRows();
            // 模板sheet
            Sheet templateFirstSheet = template.getSheetAt(0);
            // 加入表头（此时表头在excel表格的最后）
            int appendRows = ExcelUtil.appendSheetToAnotherSheet(templateFirstSheet, firstSheet);
            // 把表头移到最上面
            ExcelUtil.copyRows(firstSheet, firstSheetRowCount, appendRows, 0);
            ExcelUtil.deleteRows(firstSheet, firstSheetRowCount + appendRows, appendRows);
            // 合并单元格
            Iterator<Row> ri = firstSheet.rowIterator();
            int mergeIndex = 0, mergeSize = 0;
            String lastRoadCode = "";
            while (ri.hasNext()) {
                Row r = ri.next();
                Cell c0 = r.getCell(0);
                if (c0 == null || c0.getCellType() != CellType.STRING) {
                    if (mergeIndex > 0 && mergeSize > 0) {
                        // 需要合并前4列
                        for (int i = 0; i < 4; i++) {
                            firstSheet.addMergedRegion(new CellRangeAddress(mergeIndex, mergeIndex + mergeSize, i, i));
                        }
                    }
                    mergeIndex = 0;
                    mergeSize = 0;
                    lastRoadCode = "";
                    continue;
                }
                String cellText = c0.getStringCellValue();
                if (lastRoadCode.equals(cellText)) {
                    // 累加
                    mergeSize++;
                } else {
                    // 中断累计
                    // 进行合并
                    if (mergeIndex > 0 && mergeSize > 0) {
                        // 需要合并前4列
                        for (int i = 0; i < 4; i++) {
                            firstSheet.addMergedRegion(new CellRangeAddress(mergeIndex, mergeIndex + mergeSize, i, i));
                        }
                    }
                    lastRoadCode = cellText;
                    mergeIndex = c0.getRow().getRowNum();
                    mergeSize = 0;
                }
            }
            if (mergeIndex > 0 && mergeSize > 0) {
                // 需要合并前4列
                for (int i = 0; i < 4; i++) {
                    firstSheet.addMergedRegion(new CellRangeAddress(mergeIndex, mergeIndex + mergeSize, i, i));
                }
            }
        });
        return super.exportXls(request, stats_result_a8_1000, Stats_result_a8_1000.class, "路面技术状况评定汇总表A-8", model);
    }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
	  if (!appMenu.allowAccess(request, "/stats_result_a8_1000", "importExcel")) {
		  return Result.error(501, "没有权限");
	  }
      return super.importExcel(request, response, Stats_result_a8_1000.class);
  }

}
