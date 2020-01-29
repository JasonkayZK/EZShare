package top.jasonkayzk.ezshare.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.utils.AddressUtil;
import top.jasonkayzk.ezshare.common.utils.HttpContextUtil;
import top.jasonkayzk.ezshare.common.utils.IPUtil;
import top.jasonkayzk.ezshare.common.utils.SortUtil;
import top.jasonkayzk.ezshare.system.entity.LoginLog;
import top.jasonkayzk.ezshare.system.dao.mapper.LoginLogMapper;
import top.jasonkayzk.ezshare.system.entity.User;
import top.jasonkayzk.ezshare.system.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Jasonkay
 */
@Service("loginLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Override
    public LoginLog getLoginLogById(Long id) {
        return this.getById(id);
    }

    @Override
    public IPage<LoginLog> getLoginLogList(QueryRequest queryRequest, LoginLog loginLog) {
        try {
            LambdaQueryWrapper<LoginLog> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(loginLog.getUsername())) {
                queryWrapper.eq(LoginLog::getUsername, loginLog.getUsername());
            }
            if (StringUtils.isNotBlank(loginLog.getIp())) {
                queryWrapper.eq(LoginLog::getIp, loginLog.getIp());
            }
            if (StringUtils.isNotBlank(loginLog.getLocation())) {
                queryWrapper.eq(LoginLog::getLocation, loginLog.getLocation());
            }
            if (StringUtils.isNotBlank(loginLog.getTimeFrom()) && StringUtils.isNotBlank(loginLog.getTimeTo())) {
                queryWrapper
                        .ge(LoginLog::getLoginTime, loginLog.getTimeFrom())
                        .le(LoginLog::getLoginTime, loginLog.getTimeTo());
            }

            Page<LoginLog> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize());
            SortUtil.handlePageSort(queryRequest, page, "loginTime", ApplicationConstant.ORDER_DESC, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取任务失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(LocalDateTime.now());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
        return this.baseMapper.findLastSevenDaysVisitCount(user);
    }

}
