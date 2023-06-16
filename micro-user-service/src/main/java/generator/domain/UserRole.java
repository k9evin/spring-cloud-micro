package generator.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user_role_tbl
 */
@TableName(value = "user_role_tbl")
@Data
public class UserRole implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long id;
    /**
     * 权限id
     */
    private Long roleId;

}