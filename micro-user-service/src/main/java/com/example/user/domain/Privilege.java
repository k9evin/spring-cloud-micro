package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName privilege_tbl
 */
@TableName(value = "privilege_tbl")
@Data
public class Privilege implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    @TableField(value = "id")
    private Long id;
    /**
     * 权限名称
     */
    @TableField(value = "privilege_name")
    private String privilege_name;
    /**
     * 资源访问路径
     */
    @TableField(value = "privilege_url")
    private String privilege_url;
}