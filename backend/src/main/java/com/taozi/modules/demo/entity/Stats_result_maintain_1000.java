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
 * @Description: 百米养护建议
 * @Author: jeecg-boot
 * @Date:   2022-04-08
 * @Version: V1.0
 */
@Data
@TableName("stats_result_maintain_1000")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="stats_result_maintain_1000对象", description="百米养护建议")
public class Stats_result_maintain_1000 {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private Integer id;
	/**projectId*/
	@Excel(name = "projectId", width = 15)
    @ApiModelProperty(value = "projectId")
	private String projectId;
	/**养管单位*/
	@Excel(name = "养管单位", width = 15)
    @ApiModelProperty(value = "养管单位")
	private String maintain;
	/**路线编码*/
	@Excel(name = "路线编码", width = 15)
    @ApiModelProperty(value = "路线编码")
	private String roadCode;
	/**路线名称*/
	@Excel(name = "路线名称", width = 15)
    @ApiModelProperty(value = "路线名称")
	private String roadName;
	/**上下行*/
	@Excel(name = "上下行", width = 15)
    @ApiModelProperty(value = "上下行")
	private String lineDir;
	/**起点*/
	@Excel(name = "起点", width = 15)
    @ApiModelProperty(value = "起点")
	private Float startStake;
	/**终点*/
	@Excel(name = "终点", width = 15)
    @ApiModelProperty(value = "终点")
	private Float endStake;
	/**里程km*/
	@Excel(name = "里程km", width = 15)
    @ApiModelProperty(value = "里程km")
	private Float length;
	/**养护性质*/
	@Excel(name = "养护性质", width = 15)
    @ApiModelProperty(value = "养护性质")
	private String maintainType;
}
