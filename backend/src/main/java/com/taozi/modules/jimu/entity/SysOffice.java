package com.taozi.modules.jimu.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 组织机构
 * @Date:   2022-04-15
 * @Version: V1.0
 */
@Data
@TableName("sys_office")
@EqualsAndHashCode(callSuper = false)
public class SysOffice {

    private String id;

    private String parentId;

    private String name;
}
