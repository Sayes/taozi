package com.taozi.common.system.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taozi.common.excel.MyJeecgEntityExcelView;
import com.taozi.common.system.base.entity.TaoziAppMenu;
import com.taozi.common.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import com.taozi.common.api.vo.Result;
import com.taozi.common.system.query.QueryGenerator;
import com.taozi.common.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class TaoziController<T, S extends IService<T>> {
    @Autowired
    protected S service;

    @Value("${taozi.path.upload}")
    private String upLoadPath;

    /**
     * 导出excel
     *
     * @param request
     */
    protected ModelAndView exportXls(HttpServletRequest request, T object, Class<T> clazz, String title) {
        // Step.1 组装查询条件
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, request.getParameterMap());
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据
        List<T> pageList = service.list(queryWrapper);
        List<T> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, title); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams  exportParams=new ExportParams(title + "报表", "导出人:" + /*sysUser.getRealname()*/ "syz", title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导出excel2
     *
     * @param request
     */
    protected ModelAndView exportXls(HttpServletRequest request, T object, Class<T> clazz, String title, Map<String, ?> model) {
        // Step.1 组装查询条件
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, request.getParameterMap());
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据
        List<T> pageList = service.list(queryWrapper);
        List<T> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new MyJeecgEntityExcelView(), model);
        mv.addObject(NormalExcelConstants.FILE_NAME, title); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams  exportParams=new ExportParams(title + "报表", "导出人:" + /*sysUser.getRealname()*/ "syz", title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 根据每页sheet数量导出多sheet
     *
     * @param request
     * @param object 实体类
     * @param clazz 实体类class
     * @param title 标题
     * @param exportFields 导出字段自定义
     * @param pageNum 每个sheet的数据条数
     * @param request
     */
    protected ModelAndView exportXlsSheet(HttpServletRequest request, T object, Class<T> clazz, String title,String exportFields,Integer pageNum) {
        // Step.1 组装查询条件
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, request.getParameterMap());
        // SYZ TODO
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.2 计算分页sheet数据
        double total = service.count();
        int count = (int)Math.ceil(total/pageNum);
        // Step.3 多sheet处理
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (int i = 1; i <=count ; i++) {
            Page<T> page = new Page<T>(i, pageNum);
            IPage<T> pageList = service.page(page, queryWrapper);
            List<T> records = pageList.getRecords();
            List<T> exportList = null;
            // 过滤选中数据
            String selections = request.getParameter("selections");
            if (oConvertUtils.isNotEmpty(selections)) {
                List<String> selectionList = Arrays.asList(selections.split(","));
                exportList = records.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
            } else {
                exportList = records;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            ExportParams  exportParams=new ExportParams(title + "报表", "导出人:" + /*sysUser.getRealname()*/ "syz", title+i,upLoadPath);
            exportParams.setType(ExcelType.XSSF);
            //map.put("title",exportParams);//表格Title
            map.put(NormalExcelConstants.PARAMS,exportParams);//表格Title
            map.put(NormalExcelConstants.CLASS,clazz);//表格对应实体
            map.put(NormalExcelConstants.DATA_LIST, exportList);//数据集合
            listMap.add(map);
        }
        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, title); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.MAP_LIST, listMap);
        return mv;
    }

    /**
     * 根据权限导出excel，传入导出字段参数
     *
     * @param request
     */
    protected ModelAndView exportXls(HttpServletRequest request, T object, Class<T> clazz, String title,String exportFields) {
        ModelAndView mv = this.exportXls(request,object,clazz,title);
        mv.addObject(NormalExcelConstants.EXPORT_FIELDS,exportFields);
        return mv;
    }

    /**
     * 获取对象ID
     *
     * @return
     */
    private String getId(T item) {
        try {
            return PropertyUtils.getProperty(item, "id").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    protected Result<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<T> list = ExcelImportUtil.importExcel(file.getInputStream(), clazz, params);
                //update-begin-author:taoyan date:20190528 for:批量插入数据
                long start = System.currentTimeMillis();
                service.saveBatch(list);
                //400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
                //1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                //update-end-author:taoyan date:20190528 for:批量插入数据
                return Result.ok("文件导入成功！数据行数：" + list.size());
            } catch (Exception e) {
                //update-begin-author:taoyan date:20211124 for: 导入数据重复增加提示
                String msg = e.getMessage();
                log.error(msg, e);
                if(msg!=null && msg.indexOf("Duplicate entry")>=0){
                    return Result.error("文件导入失败:有重复数据！");
                }else{
                    return Result.error("文件导入失败:" + e.getMessage());
                }
                //update-end-author:taoyan date:20211124 for: 导入数据重复增加提示
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

    ///////////////////////////////////////////////
    private static String url = "jdbc:mysql://taozi-mysql:3306/taozi?characterEncoding=UTF-8&useUnicode=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String name = "com.mysql.cj.jdbc.Driver";
    private static final String username = "root";// 数据库登录用户名
    private static final String password = "tgTBesuBnL7SK0KL";// 数据库登录密码

    private static Connection connection = null;// 连接对象
    private static PreparedStatement pst = null;// 事务对象

    /**
     * 获取连接
     *
     * @throws Exception
     *
     * @auther zhuteng
     * @time 2019年7月24日
     */
    private static void getConnection() throws Exception
    {
        try {
            Class.forName(name);// 加载驱动
            String[] envs = {};
            ApplicationContext context = SpringContextUtils.getApplicationContext();
            envs = context.getEnvironment().getActiveProfiles();
            for (String profile : envs) {
                if ("dev".equals(profile)) {
                    url = "jdbc:mysql://192.168.1.100:3306/taozi?characterEncoding=UTF-8&useUnicode=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai";
                    break;
                }
            }
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 執行sql查詢
     *
     * @auther zhuteng
     * @time 2019年7月25日
     */
    public static JSONObject executeQuerySql(String sql) throws Exception
    {
        JSONObject table = new JSONObject();// 所有查詢的數據
        JSONArray tableTitle = new JSONArray();// 表格头
        JSONArray tableBody = new JSONArray();// 表格内容
        try {
            getConnection();// 获取连接
            pst = connection.prepareStatement(sql.toString());
            ResultSet result = pst.executeQuery();// 查询结果
            ResultSetMetaData rsmd = result.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                JSONObject tableTitle_Th = new JSONObject();// 表格头单元格
                tableTitle_Th.put("columnname", rsmd.getColumnName(i));// 字段名
                tableTitle_Th.put("tablename", rsmd.getTableName(i));// 表名
                tableTitle_Th.put("columnclassname", rsmd.getColumnClassName(i));// JAVA_数据类型
                tableTitle_Th.put("columntypename", rsmd.getColumnTypeName(i) + "(" + rsmd.getColumnDisplaySize(i) + ")");// DB_数据类型
                tableTitle.add(tableTitle_Th);// 保存到数组
            }
            while (result.next())
            {
                JSONArray tableRow = new JSONArray();// 表内容单元格
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    String classname = rsmd.getColumnClassName(i);// 数据类型
                    switch (classname)
                    {
                        case "java.math.BigDecimal":
                        {
                            tableRow.add(result.getBigDecimal(i));
                            break;
                        }
                        case "java.lang.Boolean":
                        {
                            tableRow.add(result.getBoolean(i));
                            break;
                        }
                        case "java.lang.Byte":
                        {
                            tableRow.add(result.getByte(i));
                            break;
                        }
                        case "java.util.Date":
                        {
                            java.util.Date date = result.getDate(i);
                            String time = "";
                            if (date != null)
                            {
                                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                            }
                            tableRow.add(time);
                            break;
                        }
                        case "java.sql.Date":
                        {
                            java.sql.Date date = result.getDate(i);
                            String time = "";
                            if (date != null)
                            {
                                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                            }
                            tableRow.add(time);
                            break;
                        }
                        case "java.lang.Double":
                        {
                            tableRow.add(result.getDouble(i));
                            break;
                        }
                        case "java.lang.Float":
                        {
                            tableRow.add(result.getFloat(i));
                            break;
                        }
                        case "java.lang.Integer":
                        {
                            tableRow.add(result.getInt(i));
                            break;
                        }
                        case "java.lang.Long":
                        {
                            tableRow.add(result.getLong(i));
                            break;
                        }
                        case "java.lang.String":
                        {
                            tableRow.add(result.getString(i));
                            break;
                        }
                    }
                }
                tableBody.add(tableRow);
            }
            connection.close();// 关闭
            table.put("tableTitle", tableTitle);
            table.put("tableBody", tableBody);
            return table;

        } catch (Exception e) {
            table.put("tableTitle", tableTitle);
            table.put("tableBody", tableBody);
        }
        return table;
    }

}
