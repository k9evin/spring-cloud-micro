package com.example.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.user.domain.Privilege;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 * @description 针对表【privilege_tbl】的数据库操作Mapper
 * @createDate 2023-06-25 14:28:57
 * @Entity generator.domain.Privilege
 */
public interface PrivilegeMapper extends BaseMapper<Privilege> {

    @Select("select role_id from privilege_tbl where privilege_url = #{url}")
    Long getRoleIdByUrl(String url);
}




