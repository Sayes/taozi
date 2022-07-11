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
 * @Description: 公路技术状况明细表A-6
 * @Author: jeecg-boot
 * @Date:   2022-04-08
 * @Version: V1.0
 */
@Data
@TableName("stats_result_a6_1000")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="stats_result_a6_1000对象", description="公路技术状况明细表A-6")
public class Stats_result_a6_1000 {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**projectId*/
	@Excel(name = "projectId", width = 15)
    @ApiModelProperty(value = "projectId")
	private String projectId;
	/**线路编码*/
	@Excel(name = "线路编码", width = 15)
    @ApiModelProperty(value = "线路编码")
	private String roadCode;
	/**线路名称*/
	@Excel(name = "线路名称", width = 15)
    @ApiModelProperty(value = "线路名称")
	private String roadName;
	/**检测方向*/
	@Excel(name = "检测方向", width = 15)
    @ApiModelProperty(value = "检测方向")
	private String lineDir;
	/**MQI*/
	@Excel(name = "MQI", width = 15)
    @ApiModelProperty(value = "MQI")
	private Float mqi;
	/**SCI*/
	@Excel(name = "SCI", width = 15)
    @ApiModelProperty(value = "SCI")
	private Float sci;
	/**PQI*/
	@Excel(name = "PQI", width = 15)
    @ApiModelProperty(value = "PQI")
	private Float pqi;
	/**PCI*/
	@Excel(name = "PCI", width = 15)
    @ApiModelProperty(value = "PCI")
	private Float pci;
	/**RQI*/
	@Excel(name = "RQI", width = 15)
    @ApiModelProperty(value = "RQI")
	private Float rqi;
	/**RDI*/
	@Excel(name = "RDI", width = 15)
    @ApiModelProperty(value = "RDI")
	private Float rdi;
	/**PBI*/
	@Excel(name = "PBI", width = 15)
    @ApiModelProperty(value = "PBI")
	private Float pbi;
	/**PWI*/
	@Excel(name = "PWI", width = 15)
    @ApiModelProperty(value = "PWI")
	private Float pwi;
	/**SRI*/
	@Excel(name = "SRI", width = 15)
    @ApiModelProperty(value = "SRI")
	private Float sri;
	/**PSSI*/
	@Excel(name = "PSSI", width = 15)
    @ApiModelProperty(value = "PSSI")
	private Float pssi;
	/**BCI*/
	@Excel(name = "BCI", width = 15)
    @ApiModelProperty(value = "BCI")
	private Float bci;
	/**TCI*/
	@Excel(name = "TCI", width = 15)
    @ApiModelProperty(value = "TCI")
	private Float tci;
	/**起点桩号*/
	@Excel(name = "起点桩号", width = 15)
    @ApiModelProperty(value = "起点桩号")
	private Float startStake;
	/**终点桩号*/
	@Excel(name = "终点桩号", width = 15)
    @ApiModelProperty(value = "终点桩号")
	private Float endStake;
	/**单元长度km*/
	@Excel(name = "单元长度km", width = 15)
    @ApiModelProperty(value = "单元长度km")
	private Float length;
	/**养管单位*/
	@Excel(name = "养管单位", width = 15)
    @ApiModelProperty(value = "养管单位")
	private String maintain;
	/**技术等级*/
	@Excel(name = "技术等级", width = 15)
    @ApiModelProperty(value = "技术等级")
	private String roadGrade;
	/**路面类型*/
	@Excel(name = "路面类型", width = 15)
    @ApiModelProperty(value = "路面类型")
	private String pavementType;
}
