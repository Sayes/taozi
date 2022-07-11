package com.taozi.modules.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taozi.common.system.base.entity.TaoziAppMenu;
import com.taozi.common.system.base.entity.TaoziOneItemPage;
import com.taozi.common.system.base.entity.TaoziSearchOpt01;
import com.taozi.common.system.base.service.impl.TaoziServiceImpl;
import com.taozi.modules.demo.entity.Project;
import com.taozi.modules.demo.mapper.Stats_result_1000Mapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.taozi.common.api.vo.Result;
//import com.taozi.common.aspect.annotation.AutoLog;
import com.taozi.common.system.base.controller.TaoziController;
import com.taozi.common.system.query.QueryGenerator;
import com.taozi.modules.demo.entity.Stats_result_1000;
import com.taozi.modules.demo.service.IStats_result_1000Service;
import net.sf.json.JSONNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Description: 评定结果明细
 * @Author: jeecg-boot
 * @Date:   2022-04-08
 * @Version: V1.0
 */
@Slf4j
@Api(tags="评定结果明细")
@RestController
@RequestMapping("/v1/stats_result_1000")
public class Stats_result_1000Controller extends TaoziController<Stats_result_1000, IStats_result_1000Service> {
    @Autowired
    private IStats_result_1000Service stats_result_1000Service;

    @Autowired
    TaoziAppMenu appMenu;

    /**
     * 分页列表查询
     *
     * @param stats_result_1000
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "评定结果明细-分页列表查询")
    @ApiOperation(value="评定结果明细-分页列表查询", notes="评定结果明细-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(Stats_result_1000 stats_result_1000,
                     @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                     @RequestParam(name="pageSize", defaultValue="20") Integer pageSize,
                     HttpServletRequest req) {

        if (!appMenu.allowAccess(req, "/stats_result_1000", "list")) {
            return Result.error(501, "没有权限");
        }

        QueryWrapper<Stats_result_1000> queryWrapper = QueryGenerator.initQueryWrapper(stats_result_1000, req.getParameterMap());
        Page<Stats_result_1000> page = new Page<Stats_result_1000>(pageNo, pageSize);
        IPage<Stats_result_1000> pageList = stats_result_1000Service.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 复杂查询
     * @param projectId
     * @param roadCode
     * @param maintain
     * @param isRqiAll
     * @param lineDir
     * @param startStake
     * @param endStake
     * @param pavementType
     * @param roadGrade
     * @param metricsName
     * @param metricsCompar
     * @param metricsValue
     */
    @ApiOperation(value="评定结果明细-搜索菜单项查询", notes="评定结果明细-搜索菜单项查询")
    @GetMapping(value = "/search")
    public Result<?> queryPageListMore(
            @RequestParam(name="projectId", defaultValue = "") String projectId,
            @RequestParam(name="roadCode", defaultValue = "") String roadCode,
            @RequestParam(name="maintain", defaultValue = "") String maintain,
            @RequestParam(name="isRqiAll", defaultValue = "") String isRqiAll,
            @RequestParam(name="lineDir", defaultValue = "") String lineDir,
            @RequestParam(name="startStake", defaultValue = "") String startStake,
            @RequestParam(name="endStake", defaultValue = "") String endStake,
            @RequestParam(name="pavementType", defaultValue = "") String pavementType,
            @RequestParam(name="roadGrade", defaultValue = "") String roadGrade,
            @RequestParam(name="metricsName", defaultValue = "") String metricsName,
            @RequestParam(name="metricsCompar", defaultValue = "") String metricsCompar,
            @RequestParam(name="metricsValue", defaultValue = "") String metricsValue,
            HttpServletRequest req) throws Exception {

        if (!appMenu.allowAccess(req, "/stats_result_1000", "search")) {
            return Result.error(501, "没有权限");
        }

        StringBuffer sql = new StringBuffer();// 拼接sql
        sql.append("select * from stats_result_1000 where id > 0 ");

        if (!projectId.isEmpty()) {
            sql.append(" and project_id = '");
            sql.append(projectId);
            sql.append("' ");
        }
        if (!roadCode.isEmpty()) {
            sql.append(" and road_code = '");
            sql.append(roadCode);
            sql.append("' ");
        }
        if (!maintain.isEmpty()) {
            sql.append(" and maintain = '");
            sql.append(maintain);
            sql.append("' ");
        }
        if (isRqiAll.isEmpty() || isRqiAll.equals("")) {
            sql.append(" and rqi > 0 ");
        }
        if (!lineDir.isEmpty()) {
            List<String> lineDirLst = Arrays.asList(lineDir.split(","));
            String sql_lineDir = "";
            for (int i = 0; i < lineDirLst.size(); i++) {
                sql_lineDir += "'" + lineDirLst.get(i) + "',";
            }
            if (!sql_lineDir.isEmpty()) {
                sql_lineDir = sql_lineDir.substring(0, sql_lineDir.length()-1);
                sql.append(" and line_dir in (");
                sql.append(sql_lineDir);
                sql.append(") ");
            }
        }
        if (!startStake.isEmpty()) {
            sql.append(" and start_stake > ");
            sql.append(startStake);
        }
        if (!endStake.isEmpty()) {
            sql.append(" and start_stake < ");
            sql.append(endStake);
        }
        if (!pavementType.isEmpty()) {
            List<String> pavementTypeLst = Arrays.asList(pavementType.split(","));
            String sql_pavementType = "";
            for (int i = 0; i < pavementTypeLst.size(); i++) {
                sql_pavementType += "'" + pavementTypeLst.get(i) + "',";
            }
            if (!sql_pavementType.isEmpty()) {
                sql_pavementType = sql_pavementType.substring(0, sql_pavementType.length()-1);
                sql.append(" and pavementType in (");
                sql.append(sql_pavementType);
                sql.append(") ");
            }
        }
        if (!roadGrade.isEmpty()) {
            List<String> roadGradeLst = Arrays.asList(roadGrade.split(","));
            String sql_roadGrade = "";
            for (int i = 0; i < roadGradeLst.size(); i++) {
                sql_roadGrade += "'" + roadGradeLst.get(i) + "',";
            }
            if (!sql_roadGrade.isEmpty()) {
                sql_roadGrade = sql_roadGrade.substring(0, sql_roadGrade.length()-1);
                sql.append(" and road_grade in (");
                sql.append(sql_roadGrade);
                sql.append(") ");
            }
        }
        if (!metricsName.isEmpty() && !metricsCompar.isEmpty() && !metricsValue.isEmpty()) {
            List<String> metricsNameLst = Arrays.asList(metricsName.split(","));
            String sql_metrics = "";
            for (int i = 0; i < metricsNameLst.size(); i++) {
                sql_metrics += " and " + metricsNameLst.get(i) + metricsCompar + metricsValue + " ";
            }
            if (!sql_metrics.isEmpty()) {
                sql.append(sql_metrics);
            }
        }
        sql.append(" ;");
        //System.out.println(sql.toString());
        JSONObject tmpdata = executeQuerySql(sql.toString());
        JSONArray tableTitle = tmpdata.getJSONArray("tableTitle");
        JSONArray tableBody = tmpdata.getJSONArray("tableBody");
        for (int j = 0; j < tableTitle.size(); j++)
        {
            JSONObject tableTitle_Th = (JSONObject) tableTitle.get(j);
            //System.out.print(tableTitle_Th.get("columnname") + "\t");
        }
        //System.out.println("\n----------------------------------------");

        TaoziOneItemPage<Stats_result_1000> page = new TaoziOneItemPage<>();

        for (int i = 0; i < tableBody.size(); i++)
        {
            JSONArray row = (JSONArray) tableBody.get(i);
            Stats_result_1000 item = new Stats_result_1000();
            //System.out.print((i + 1) + "\t");
            for (int j = 0; j < row.size(); j++)
            {
                JSONObject tableTitle_Th = (JSONObject) tableTitle.get(j);
                String columnName = tableTitle_Th.get("columnname").toString();
                String item_str = row.get(j) instanceof JSONNull ? "" : row.get(j).toString();

                if (columnName.equals("id")) {
                    item.setId(Integer.parseInt(item_str));
                    continue;
                } else if (columnName.equals("project_id")) {
                    item.setProjectId(item_str);
                    continue;
                } else if (columnName.equals("road_code")) {
                    item.setRoadCode(item_str);
                    continue;
                } else if (columnName.equals("road_name")) {
                    item.setRoadName(item_str);
                    continue;
                } else if (columnName.equals("start_stake")) {
                    item.setStartStake(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("end_stake")) {
                    item.setEndStake(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("length")) {
                    item.setLength(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("line_dir")) {
                    item.setLineDir(item_str);
                    continue;
                } else if (columnName.equals("district")) {
                    item.setDistrict(item_str);
                    continue;
                } else if (columnName.equals("maintain")) {
                    item.setMaintain(item_str);
                    continue;
                } else if (columnName.equals("road_grade")) {
                    item.setRoadGrade(item_str);
                    continue;
                } else if (columnName.equals("pavement_type")) {
                    item.setPavementType(item_str);
                    continue;
                } else if (columnName.equals("pavement_width")) {
                    item.setPavementWidth(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("mqi")) {
                    item.setMqi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("mqi_level")) {
                    item.setMqiLevel(item_str);
                    continue;
                } else if (columnName.equals("pqi")) {
                    item.setPqi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pqi_level")) {
                    item.setPqiLevel(item_str);
                    continue;
                } else if (columnName.equals("pci")) {
                    item.setPci(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pci_level")) {
                    item.setPciLevel(item_str);
                    continue;
                } else if (columnName.equals("rqi")) {
                    item.setRqi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("rqi_level")) {
                    item.setRqiLevel(item_str);
                    continue;
                } else if (columnName.equals("rdi")) {
                    item.setRdi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("rdi_level")) {
                    item.setRdiLevel(item_str);
                    continue;
                } else if (columnName.equals("pbi")) {
                    item.setPbi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pbi_level")) {
                    item.setPbiLevel(item_str);
                    continue;
                } else if (columnName.equals("pwi")) {
                    item.setPwi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pwi_level")) {
                    item.setPwiLevel(item_str);
                    continue;
                } else if (columnName.equals("sri")) {
                    item.setSri(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("sri_level")) {
                    item.setSriLevel(item_str);
                    continue;
                } else if (columnName.equals("sci")) {
                    item.setSci(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("sci_level")) {
                    item.setSciLevel(item_str);
                    continue;
                } else if (columnName.equals("bci")) {
                    item.setBci(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("bci_level")) {
                    item.setBciLevel(item_str);
                    continue;
                } else if (columnName.equals("tci")) {
                    item.setTci(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("tci_level")) {
                    item.setTciLevel(item_str);
                    continue;
                } else if (columnName.equals("pssi")) {
                    item.setPssi(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pssi_level")) {
                    item.setPssiLevel(item_str);
                    continue;
                } else if (columnName.equals("dr")) {
                    item.setDr(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("iri")) {
                    item.setIri(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("rd")) {
                    item.setRd(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("sfc")) {
                    item.setSfc(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pb")) {
                    item.setPb(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pb_l")) {
                    item.setPbL(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pb_m")) {
                    item.setPbM(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("pb_h")) {
                    item.setPbH(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                } else if (columnName.equals("wr")) {
                    item.setWr(item_str.equals("") ? 0f : Float.parseFloat(item_str));
                    continue;
                }
            }
            page.Add(item);
        }
        return Result.OK(page);
    }

    /**
     * 查询项目id, 路线编码, 管养单位, 检测方向
     * @param projectName
     * @param roadCode
     * @param maintain
     */
    @ApiOperation(value="评定结果明细-搜索菜单项查询", notes="评定结果明细-搜索菜单项查询")
    @GetMapping(value = "/search_opt")
    public Result<?> querySearchOpt(
                    @RequestParam(name="projectName", defaultValue = "") String projectName,
                    @RequestParam(name="roadCode", defaultValue = "") String roadCode,
                    @RequestParam(name="maintain", defaultValue = "") String maintain,
                    HttpServletRequest req) throws Exception {

        StringBuffer sql = new StringBuffer();// 拼接sql
        sql.append("select p.project_id as project_id, p.project_name as project_name, r.road_code as road_code, r.maintain as maintain, r.line_dir as line_dir from stats_result_1000 as r, project as p where r.project_id = p.project_id ");

        if (!projectName.isEmpty()) {
            sql.append(" and p.project_name = '");
            sql.append(projectName);
            sql.append("' ");
        }
        if (!roadCode.isEmpty()) {
            sql.append(" and r.road_code = '");
            sql.append(roadCode);
            sql.append("' ");
        }
        if (!maintain.isEmpty()) {
            sql.append(" and r.maintain = '");
            sql.append(maintain);
            sql.append("' ");
        }
        sql.append(" group by r.project_id, r.maintain, r.road_code, r.line_dir;");
        //System.out.println(sql.toString());
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
                    searchOpt01.addRoadCode(row.get(j).toString());
                } else if (j == 3) {
                    searchOpt01.addMaintain(row.get(j).toString());
                }
            }
            //System.out.println();
        }
        return Result.OK(searchOpt01);
    }

    /**
     * 添加
     *
     * @param stats_result_1000
     * @return
     */
    //@AutoLog(value = "评定结果明细-添加")
    @ApiOperation(value="评定结果明细-添加", notes="评定结果明细-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Stats_result_1000 stats_result_1000, HttpServletRequest req) {
        if (!appMenu.allowAccess(req, "/stats_result_1000", "add")) {
            return Result.error(501, "没有权限");
        }
        stats_result_1000Service.save(stats_result_1000);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param stats_result_1000
     * @return
     */
    //@AutoLog(value = "评定结果明细-编辑")
    @ApiOperation(value="评定结果明细-编辑", notes="评定结果明细-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<?> edit(@RequestBody Stats_result_1000 stats_result_1000, HttpServletRequest req) {
        if (!appMenu.allowAccess(req, "/stats_result_1000", "edit")) {
            return Result.error(501, "没有权限");
        }
        stats_result_1000Service.updateById(stats_result_1000);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "评定结果明细-通过id删除")
    @ApiOperation(value="评定结果明细-通过id删除", notes="评定结果明细-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id, HttpServletRequest req) {
        if (!appMenu.allowAccess(req, "/stats_result_1000", "delete")) {
            return Result.error(501, "没有权限");
        }
        stats_result_1000Service.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    //@AutoLog(value = "评定结果明细-批量删除")
    @ApiOperation(value="评定结果明细-批量删除", notes="评定结果明细-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids, HttpServletRequest req) {
        if (!appMenu.allowAccess(req, "/stats_result_1000", "deleteBatch")) {
            return Result.error(501, "没有权限");
        }
        this.stats_result_1000Service.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "评定结果明细-通过id查询")
    @ApiOperation(value="评定结果明细-通过id查询", notes="评定结果明细-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id, HttpServletRequest req) {
        if (!appMenu.allowAccess(req, "/stats_result_1000", "queryById")) {
            return Result.error(501, "没有权限");
        }
        Stats_result_1000 stats_result_1000 = stats_result_1000Service.getById(id);
        return Result.OK(stats_result_1000);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param stats_result_1000
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Stats_result_1000 stats_result_1000) {
        if (!appMenu.allowAccess(request, "/stats_result_1000", "exportXls")) {
            return null;
        }
        return super.exportXls(request, stats_result_1000, Stats_result_1000.class, "评定结果明细");
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
        if (!appMenu.allowAccess(request, "/stats_result_1000", "importExcel")) {
            return Result.error(501, "没有权限");
        }
        return super.importExcel(request, response, Stats_result_1000.class);
    }

    /**
     * 刷新菜单缓存
     *
     */
    //@AutoLog(value = "")
    @ApiOperation(value="刷新菜单缓存", notes="刷新菜单缓存")
    @GetMapping(value = "/flushMenu")
    public Result<?> flushMenu(HttpServletRequest req) {
        appMenu.flushMenu();
        return Result.OK();
    }


}
