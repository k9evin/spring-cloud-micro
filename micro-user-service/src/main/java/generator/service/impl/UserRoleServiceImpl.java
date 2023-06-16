package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserRole;
import generator.service.UserRoleService;
import generator.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_role_tbl】的数据库操作Service实现
* @createDate 2023-06-16 16:44:38
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




