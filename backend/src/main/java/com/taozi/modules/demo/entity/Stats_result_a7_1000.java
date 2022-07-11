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
 * @Description: 公路技术状况评定汇总表A-7
 * @Author: jeecg-boot
 * @Date:   2022-04-08
 * @Version: V1.0
 */
@Data
@TableName("stats_result_a7_1000")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="stats_result_a7_1000对象", description="公路技术状况评定汇总表A-7")
public class Stats_result_a7_1000 {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**projectId*/
	// @Excel(name = "projectId", width = 15)
    @ApiModelProperty(value = "projectId")
	private String projectId;
	/**路线编码*/
	@Excel(name = "路线编码", width = 15)
    @ApiModelProperty(value = "路线编码")
	private String roadCode;
	/**路线名称*/
	@Excel(name = "路线名称", width = 15)
    @ApiModelProperty(value = "路线名称")
	private String roadName;
	/**起点桩号*/
	@Excel(name = "起点桩号", width = 15)
    @ApiModelProperty(value = "起点桩号")
	private java.math.BigDecimal startStake;
	/**评定长度km*/
	@Excel(name = "评定长度km", width = 15)
    @ApiModelProperty(value = "评定长度km")
	private java.math.BigDecimal length;
	/**调查方向*/
	@Excel(name = "调查方向", width = 15)
    @ApiModelProperty(value = "调查方向")
	private String lineDir;
	/**技术等级*/
	@Excel(name = "技术等级", width = 15)
    @ApiModelProperty(value = "技术等级")
	private String roadGrade;
	/**路面类型*/
	@Excel(name = "路面类型", width = 15)
    @ApiModelProperty(value = "路面类型")
	private String pavementType;
	/**MQI*/
	@Excel(name = "MQI", width = 15)
    @ApiModelProperty(value = "MQI")
	private java.math.BigDecimal mqi;
	/**SCI*/
	@Excel(name = "SCI", width = 15)
    @ApiModelProperty(value = "SCI")
	private java.math.BigDecimal sci;
	/**PQI*/
	@Excel(name = "PQI", width = 15)
    @ApiModelProperty(value = "PQI")
	private java.math.BigDecimal pqi;
	/**BCI*/
	@Excel(name = "BCI", width = 15)
    @ApiModelProperty(value = "BCI")
	private java.math.BigDecimal bci;
	/**TCI*/
	@Excel(name = "TCI", width = 15)
    @ApiModelProperty(value = "TCI")
	private java.math.BigDecimal tci;
	/**优*/
	@Excel(name = "优", width = 15)
    @ApiModelProperty(value = "优")
	private java.math.BigDecimal excellent;
	/**良*/
	@Excel(name = "良", width = 15)
    @ApiModelProperty(value = "良")
	private java.math.BigDecimal good;
	/**中*/
	@Excel(name = "中", width = 15)
    @ApiModelProperty(value = "中")
	private java.math.BigDecimal general;
	/**次*/
	@Excel(name = "次", width = 15)
    @ApiModelProperty(value = "次")
	private java.math.BigDecimal fair;
	/**差*/
	@Excel(name = "差", width = 15)
    @ApiModelProperty(value = "差")
	private java.math.BigDecimal bad;
	/**优等路率*/
	@Excel(name = "优等路率", width = 15)
    @ApiModelProperty(value = "优等路率")
	private java.math.BigDecimal percentExcellent;
	/**优良路率*/
	@Excel(name = "优良路率", width = 15)
    @ApiModelProperty(value = "优良路率")
	private java.math.BigDecimal percentGood;
	/**次差路率*/
	@Excel(name = "次差路率", width = 15)
    @ApiModelProperty(value = "次差路率")
	private java.math.BigDecimal percentBad;
}
