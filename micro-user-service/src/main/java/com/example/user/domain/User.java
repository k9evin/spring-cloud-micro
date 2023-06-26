package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName user_tbl
 */
@TableName(value = "user_tbl")
@Data
@NoArgsConstructor
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Object isDeleted;
}