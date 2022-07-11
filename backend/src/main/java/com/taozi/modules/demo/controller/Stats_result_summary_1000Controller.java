package com.taozi.modules.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taozi.common.system.base.entity.TaoziSearchOpt01;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.taozi.common.api.vo.Result;
//import com.taozi.common.aspect.annotation.AutoLog;
import com.taozi.common.system.base.controller.TaoziController;
import com.taozi.common.system.query.QueryGenerator;
import com.taozi.modules.demo.entity.Stats_result_summary_1000;
import com.taozi.modules.demo.service.IStats_result_summary_1000Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 结果分析评定汇总
* @Author: jeecg-boot
* @Date:   2022-04-12
* @Version: V1.0
*/
@Slf4j
@Api(tags="结果分析评定汇总")
@RestController
@RequestMapping("/v1/stats_result_summary_1000")
public class Stats_result_summary_1000Controller extends TaoziController<Stats_result_summary_1000, IStats_result_summary_1000Service> {
   @Autowired
   private IStats_result_summary_1000Service stats_result_summary_1000Service;

   /**
    * 分页列表查询
    *
    * @param stats_result_summary_1000
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "结果分析评定汇总-分页列表查询")
   @ApiOperation(value="结果分析评定汇总-分页列表查询", notes="结果分析评定汇总-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(Stats_result_summary_1000 stats_result_summary_1000,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="20") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<Stats_result_summary_1000> queryWrapper = QueryGenerator.initQueryWrapper(stats_result_summary_1000, req.getParameterMap());
       Page<Stats_result_summary_1000> page = new Page<Stats_result_summary_1000>(pageNo, pageSize);
       IPage<Stats_result_summary_1000> pageList = stats_result_summary_1000Service.page(page, queryWrapper);
       return Result.OK(pageList);
   }

    /**
     * 查询项目id, 管养单位, 检测方向
     * @param projectName
     * @param maintain
     * @param metrics
     * @param lineDir
     */
    @ApiOperation(value="结果分析评定汇总-搜索菜单项查询", notes="结果分析评定汇总-搜索菜单项查询")
    @GetMapping(value = "/search_opt")
    public Result<?> querySearchOpt(
            @RequestParam(name="projectName", defaultValue = "") String projectName,
            @RequestParam(name="maintain", defaultValue = "") String maintain,
            @RequestParam(name="metrics", defaultValue = "") String metrics,
            @RequestParam(name="lineDir", defaultValue = "") String lineDir,
            HttpServletRequest req) throws Exception {

        StringBuffer sql = new StringBuffer();// 拼接sql
        sql.append("select p.project_id as project_id, p.project_name as project_name, r.maintain as maintain, r.metrics_name as metrics, r.line_dir as line_dir from stats_result_summary_1000 as r, project as p where r.project_id = p.project_id ");

        if (!projectName.isEmpty()) {
            sql.append(" and p.project_name = '");
            sql.append(projectName);
            sql.append("' ");
        }
        if (!maintain.isEmpty()) {
            sql.append(" and r.maintain = '");
            sql.append(maintain);
            sql.append("' ");
        }
        if (!lineDir.isEmpty()) {
            sql.append(" and r.metrics_name = '");
            sql.append(metrics);
            sql.append("' ");
        }
        if (!lineDir.isEmpty()) {
            sql.append(" and r.line_dir = '");
            sql.append(lineDir);
            sql.append("' ");
        }
        sql.append(" group by r.project_id, r.maintain, r.metrics_name, r.line_dir;");
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
                } else if (j == 2) {
                    searchOpt01.addMaintain(row.get(j).toString());
                } else if (j == 3) {
                    searchOpt01.addCustom01(row.get(j).toString());
                } else if (j == 4) {
                    searchOpt01.addLineDir(row.get(j).toString());
                }
            }
            //System.out.println();
        }

        return Result.OK(searchOpt01);
    }

    /**
    * 添加
    *
    * @param stats_result_summary_1000
    * @return
    */
   //@AutoLog(value = "结果分析评定汇总-添加")
   @ApiOperation(value="结果分析评定汇总-添加", notes="结果分析评定汇总-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody Stats_result_summary_1000 stats_result_summary_1000) {
       stats_result_summary_1000Service.save(stats_result_summary_1000);
       return Result.OK("添加成功！");
   }

   /**
    * 编辑
    *
    * @param stats_result_summary_1000
    * @return
    */
   //@AutoLog(value = "结果分析评定汇总-编辑")
   @ApiOperation(value="结果分析评定汇总-编辑", notes="结果分析评定汇总-编辑")
   @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
   public Result<?> edit(@RequestBody Stats_result_summary_1000 stats_result_summary_1000) {
       stats_result_summary_1000Service.updateById(stats_result_summary_1000);
       return Result.OK("编辑成功!");
   }

   /**
    * 通过id删除
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "结果分析评定汇总-通过id删除")
   @ApiOperation(value="结果分析评定汇总-通过id删除", notes="结果分析评定汇总-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       stats_result_summary_1000Service.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    * 批量删除
    *
    * @param ids
    * @return
    */
   //@AutoLog(value = "结果分析评定汇总-批量删除")
   @ApiOperation(value="结果分析评定汇总-批量删除", notes="结果分析评定汇总-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.stats_result_summary_1000Service.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功！");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "结果分析评定汇总-通过id查询")
   @ApiOperation(value="结果分析评定汇总-通过id查询", notes="结果分析评定汇总-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       Stats_result_summary_1000 stats_result_summary_1000 = stats_result_summary_1000Service.getById(id);
       return Result.OK(stats_result_summary_1000);
   }

 /**
  * 导出excel
  *
  * @param request
  * @param stats_result_summary_1000
  */
 @RequestMapping(value = "/exportXls")
 public ModelAndView exportXls(HttpServletRequest request, Stats_result_summary_1000 stats_result_summary_1000) {
     return super.exportXls(request, stats_result_summary_1000, Stats_result_summary_1000.class, "结果分析评定汇总");
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
     return super.importExcel(request, response, Stats_result_summary_1000.class);
 }

}
