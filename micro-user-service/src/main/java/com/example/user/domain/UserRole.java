package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user_role_tbl
 */
@TableName(value = "public.user_role_tbl")
@Data
public class UserRole implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     * 权限id
     */
    private Long roleId;
}