package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role_tbl
 */
@TableName(value = "role_tbl")
@Data
public class Role implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 权限id
     */
    @TableId(value = "role_id")
    private Long roleId;
    /**
     * 权限名
     */
    @TableField(value = "role_name")
    private String roleName;
    /**
     * 权限
     */
    @TableField(value = "role")
    private String role;
}