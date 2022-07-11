package com.taozi.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 结果分析评定汇总
 * @Author: jeecg-boot
 * @Date:   2022-04-12
 * @Version: V1.0
 */
@Data
@TableName("stats_result_summary_1000")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="stats_result_summary_1000对象", description="结果分析评定汇总")
public class Stats_result_summary_1000 {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**项目编码*/
	@Excel(name = "项目编码", width = 15)
    @ApiModelProperty(value = "项目编码")
	private String projectId;
	/**区域*/
	@Excel(name = "区域", width = 15)
    @ApiModelProperty(value = "区域")
	private String district;
	/**路线编码*/
	@Excel(name = "路线编码", width = 15)
    @ApiModelProperty(value = "路线编码")
	private String roadCode;
	/**路线类型*/
	@Excel(name = "路线类型", width = 15)
    @ApiModelProperty(value = "路线类型")
	private String lineDir;
	/**指标名称*/
	@Excel(name = "指标名称", width = 15)
    @ApiModelProperty(value = "指标名称")
	private String metricsName;
	/**养管单位*/
	@Excel(name = "养管单位", width = 15)
    @ApiModelProperty(value = "养管单位")
	private String maintain;
	/**总里程*/
	@Excel(name = "总里程", width = 15)
    @ApiModelProperty(value = "总里程")
	private java.math.BigDecimal lengthTotal;
	/**平均值*/
	@Excel(name = "平均值", width = 15)
    @ApiModelProperty(value = "平均值")
	private java.math.BigDecimal average;
	/**里程*/
	@Excel(name = "里程", width = 15)
    @ApiModelProperty(value = "里程")
	private java.math.BigDecimal lengthExcellent;
	/**占比*/
	@Excel(name = "占比", width = 15)
    @ApiModelProperty(value = "占比")
	private java.math.BigDecimal percentExcellent;
	/**里程*/
	@Excel(name = "里程", width = 15)
    @ApiModelProperty(value = "里程")
	private java.math.BigDecimal lengthGood;
	/**占比*/
	@Excel(name = "占比", width = 15)
    @ApiModelProperty(value = "占比")
	private java.math.BigDecimal percentGood;
	/**里程*/
	@Excel(name = "里程", width = 15)
    @ApiModelProperty(value = "里程")
	private java.math.BigDecimal lengthGeneral;
	/**占比*/
	@Excel(name = "占比", width = 15)
    @ApiModelProperty(value = "占比")
	private java.math.BigDecimal percentGeneral;
	/**里程*/
	@Excel(name = "里程", width = 15)
    @ApiModelProperty(value = "里程")
	private java.math.BigDecimal lengthFair;
	/**占比*/
	@Excel(name = "占比", width = 15)
    @ApiModelProperty(value = "占比")
	private java.math.BigDecimal percentFair;
	/**里程*/
	@Excel(name = "里程", width = 15)
    @ApiModelProperty(value = "里程")
	private java.math.BigDecimal lengthBad;
	/**管养单位*/
	@Excel(name = "管养单位", width = 15)
    @ApiModelProperty(value = "管养单位")
	private java.math.BigDecimal percentBad;
}
