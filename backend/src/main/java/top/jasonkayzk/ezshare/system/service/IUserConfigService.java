package top.jasonkayzk.ezshare.system.service;

import top.jasonkayzk.ezshare.system.entity.UserConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jasonkay
 */
public interface IUserConfigService extends IService<UserConfig> {

    /**
     * 通过用户 ID 获取前端系统个性化配置
     *
     * @param userId 用户 ID
     * @return 前端系统个性化配置
     */
    UserConfig findByUserId(String userId);

    /**
     * 生成用户默认个性化配置
     *
     * @param userId 用户 ID
     */
    void initDefaultUserConfig(String userId);

    /**
     * 通过用户 ID 删除个性化配置
     *
     * @param userIds 用户 ID 数组
     */
    void deleteByUserId(String... userIds);

    /**
     * 更新用户个性化配置
     *
     * @param  userConfig 用户个性化配置
     *
     * @throws Exception exception
     */
    void update(UserConfig userConfig) throws Exception;

}
