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
 * @Description: 评定结果明细
 * @Author: jeecg-boot
 * @Date:   2022-04-08
 * @Version: V1.0
 */
@Data
@TableName("stats_result_1000")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="stats_result_1000对象", description="评定结果明细")
public class Stats_result_1000 {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**项目编码*/
	@Excel(name = "项目编码", width = 15)
    @ApiModelProperty(value = "项目编码")
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
	private Float startStake;
	/**终点桩号*/
	@Excel(name = "终点桩号", width = 15)
    @ApiModelProperty(value = "终点桩号")
	private Float endStake;
	/**路段长度*/
	@Excel(name = "路段长度", width = 15)
    @ApiModelProperty(value = "路段长度")
	private Float length;
	/**方向*/
	@Excel(name = "方向", width = 15)
    @ApiModelProperty(value = "方向")
	private String lineDir;
	/**政区名称*/
	@Excel(name = "政区名称", width = 15)
    @ApiModelProperty(value = "政区名称")
	private String district;
	/**maintain*/
	@Excel(name = "maintain", width = 15)
    @ApiModelProperty(value = "maintain")
	private String maintain;
	/**技术等级*/
	@Excel(name = "技术等级", width = 15)
    @ApiModelProperty(value = "技术等级")
	private String roadGrade;
	/**路面类型*/
	@Excel(name = "路面类型", width = 15)
    @ApiModelProperty(value = "路面类型")
	private String pavementType;
	/**有效路面宽度*/
	@Excel(name = "有效路面宽度", width = 15)
    @ApiModelProperty(value = "有效路面宽度")
	private Float pavementWidth;
	/**MQI*/
	@Excel(name = "MQI", width = 15)
    @ApiModelProperty(value = "MQI")
	private Float mqi;
	/**MQI分级*/
	@Excel(name = "MQI分级", width = 15)
    @ApiModelProperty(value = "MQI分级")
	private String mqiLevel;
	/**PQI*/
	@Excel(name = "PQI", width = 15)
    @ApiModelProperty(value = "PQI")
	private Float pqi;
	/**PQI分级*/
	@Excel(name = "PQI分级", width = 15)
    @ApiModelProperty(value = "PQI分级")
	private String pqiLevel;
	/**PCI*/
	@Excel(name = "PCI", width = 15)
    @ApiModelProperty(value = "PCI")
	private Float pci;
	/**PCI分级*/
	@Excel(name = "PCI分级", width = 15)
    @ApiModelProperty(value = "PCI分级")
	private String pciLevel;
	/**RQI*/
	@Excel(name = "RQI", width = 15)
    @ApiModelProperty(value = "RQI")
	private Float rqi;
	/**RQI分级*/
	@Excel(name = "RQI分级", width = 15)
    @ApiModelProperty(value = "RQI分级")
	private String rqiLevel;
	/**RDI*/
	@Excel(name = "RDI", width = 15)
    @ApiModelProperty(value = "RDI")
	private Float rdi;
	/**RDI分级*/
	@Excel(name = "RDI分级", width = 15)
    @ApiModelProperty(value = "RDI分级")
	private String rdiLevel;
	/**PBI*/
	@Excel(name = "PBI", width = 15)
    @ApiModelProperty(value = "PBI")
	private Float pbi;
	/**PBI分级*/
	@Excel(name = "PBI分级", width = 15)
    @ApiModelProperty(value = "PBI分级")
	private String pbiLevel;
	/**PWI*/
	@Excel(name = "PWI", width = 15)
    @ApiModelProperty(value = "PWI")
	private Float pwi;
	/**PWI分级*/
	@Excel(name = "PWI分级", width = 15)
    @ApiModelProperty(value = "PWI分级")
	private String pwiLevel;
	/**SRI*/
	@Excel(name = "SRI", width = 15)
    @ApiModelProperty(value = "SRI")
	private Float sri;
	/**SRI分级*/
	@Excel(name = "SRI分级", width = 15)
    @ApiModelProperty(value = "SRI分级")
	private String sriLevel;
	/**SCI*/
	@Excel(name = "SCI", width = 15)
    @ApiModelProperty(value = "SCI")
	private Float sci;
	/**SCI分级*/
	@Excel(name = "SCI分级", width = 15)
    @ApiModelProperty(value = "SCI分级")
	private String sciLevel;
	/**BCI*/
	@Excel(name = "BCI", width = 15)
    @ApiModelProperty(value = "BCI")
	private Float bci;
	/**BCI分级*/
	@Excel(name = "BCI分级", width = 15)
    @ApiModelProperty(value = "BCI分级")
	private String bciLevel;
	/**TCI*/
	@Excel(name = "TCI", width = 15)
    @ApiModelProperty(value = "TCI")
	private Float tci;
	/**TCI分级*/
	@Excel(name = "TCI分级", width = 15)
    @ApiModelProperty(value = "TCI分级")
	private String tciLevel;
	/**PSSI*/
	@Excel(name = "PSSI", width = 15)
    @ApiModelProperty(value = "PSSI")
	private Float pssi;
	/**PSSI分级*/
	@Excel(name = "PSSI分级", width = 15)
    @ApiModelProperty(value = "PSSI分级")
	private String pssiLevel;
	/**DR*/
	@Excel(name = "DR", width = 15)
    @ApiModelProperty(value = "DR")
	private Float dr;
	/**IRI*/
	@Excel(name = "IRI", width = 15)
    @ApiModelProperty(value = "IRI")
	private Float iri;
	/**RD*/
	@Excel(name = "RD", width = 15)
    @ApiModelProperty(value = "RD")
	private Float rd;
	/**SFC*/
	@Excel(name = "SFC", width = 15)
    @ApiModelProperty(value = "SFC")
	private Float sfc;
	/**PB*/
	@Excel(name = "PB", width = 15)
    @ApiModelProperty(value = "PB")
	private Float pb;
	/**PB_L*/
	@Excel(name = "PB_L", width = 15)
    @ApiModelProperty(value = "PB_L")
	private Float pbL;
	/**PB_M*/
	@Excel(name = "PB_M", width = 15)
    @ApiModelProperty(value = "PB_M")
	private Float pbM;
	/**PB_H*/
	@Excel(name = "PB_H", width = 15)
    @ApiModelProperty(value = "PB_H")
	private Float pbH;
	/**WR*/
	@Excel(name = "WR", width = 15)
    @ApiModelProperty(value = "WR")
	private Float wr;
}
