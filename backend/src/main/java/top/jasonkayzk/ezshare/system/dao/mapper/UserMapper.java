package top.jasonkayzk.ezshare.system.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.jasonkayzk.ezshare.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Jasonkay
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询所有用户列表
     *
     * @param page 分页参数
     * @param user 用户
     *
     * @return 用户列表
     */
    IPage<User> findUserDetail(Page<User> page, @Param("user") User user);

    /**
     * 获取单个用户详情
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findDetail(String username);

}
