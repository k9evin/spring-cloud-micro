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
    @TableId
    private Long id;
    /**
     * 权限名
     */
    private String roleName;
    /**
     * 权限
     */
    private String role;

}