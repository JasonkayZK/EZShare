package top.jasonkayzk.ezshare.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.system.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import top.jasonkayzk.ezshare.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author Jasonkay
 */
public interface ILoginLogService extends IService<LoginLog> {

    /**
     * 根据Id查询登录记录
     *
     * @param id 登录记录Id
     *
     * @return 登录记录
     */
    LoginLog getLoginLogById(Long id);

    /**
     * 分页查询登录日志
     *
     * @param queryRequest 分页参数
     *
     * @param loginLog 其他登录日志查询参数
     *
     * @return LoginLog 分页列表
     */
    IPage<LoginLog> getLoginLogList(QueryRequest queryRequest, LoginLog loginLog);

    /**
     * 保存登录记录
     *
     * @param loginLog 登录记录
     */
    void saveLoginLog (LoginLog loginLog);

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount();

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问Ip数
     *
     * @return Long
     */
    Long findTodayIp();

    /**
     * 获取系统近七天来的访问记录
     *
     * @param user 用户
     * @return 系统近七天来的访问记录
     */
    List<Map<String, Object>> findLastSevenDaysVisitCount(User user);

}
