package com.taozi.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
// SYZ, TODO
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 年度项目信息数据
 * @Date:   2022-03-23
 * @Version: V1.0
 */
@Data
@TableName("project")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@ApiModel(value="project对象", description="年度项目信息数据")
public class Project {

	/**主键, 项目编码*/
	@TableId(type = IdType.ASSIGN_UUID)
	@Excel(name = "主键, 项目编码", width = 15)
    //@ApiModelProperty(value = "主键, 项目编码")
	private java.lang.String projectId;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    //@ApiModelProperty(value = "项目名称")
	private java.lang.String projectName;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    //@ApiModelProperty(value = "创建人")
	private java.lang.String creator;
	/**年度*/
	@Excel(name = "年度", width = 15)
    //@ApiModelProperty(value = "年度")
	private java.lang.String year;
	/**检测单位*/
	@Excel(name = "检测单位", width = 15)
    //@ApiModelProperty(value = "检测单位")
	private java.lang.String detector;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    //@ApiModelProperty(value = "联系电话")
	private java.lang.String cellphone;
	/**0:未锁定, 1:锁定*/
	@Excel(name = "0:未锁定, 1:锁定", width = 15)
    //@ApiModelProperty(value = "0:未锁定, 1:锁定")
	private java.lang.Integer state;
	/**备注*/
	@Excel(name = "备注", width = 15)
    //@ApiModelProperty(value = "备注")
	private java.lang.String remark;
}
