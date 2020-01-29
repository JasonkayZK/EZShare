package top.jasonkayzk.ezshare.common.routine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.service.RedisService;
import top.jasonkayzk.ezshare.common.utils.DateUtil;

import java.time.LocalDateTime;

/**
 * 主要用于定时删除Redis中
 *
 * key为ezshare.user.active中已经过期的score
 *
 * @author zk
 */
@Slf4j
@Component
public class CacheRoutine {

    private final RedisService redisService;

    public CacheRoutine(RedisService redisService) {
        this.redisService = redisService;
    }

    @Scheduled(fixedRate = 3600000)
    public void run() {
        try {
            String now = DateUtil.formatFullTime(LocalDateTime.now());
            redisService.zremrangeByScore(ApplicationConstant.ACTIVE_USERS_ZSET_PREFIX, "-inf", now);
            log.info("delete expired user");
        } catch (Exception ignore) {
        }
    }

}
