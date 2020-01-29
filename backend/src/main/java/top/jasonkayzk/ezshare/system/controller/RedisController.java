package top.jasonkayzk.ezshare.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jasonkayzk.ezshare.common.entity.RedisInfo;
import top.jasonkayzk.ezshare.common.response.ApplicationResponse;
import top.jasonkayzk.ezshare.common.service.RedisService;

import java.util.List;
import java.util.Map;

/**
 * Redis监控数据控制器
 *
 * @author zk
 */
@RestController
@RequestMapping("/system/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("info")
    public ApplicationResponse<List<RedisInfo>> getRedisInfo() throws Exception {
        return ApplicationResponse.successWithData(this.redisService.getRedisInfo());
    }

    @GetMapping("keysSize")
    public ApplicationResponse<Map<String, Object>> getKeysSize() throws Exception {
        return ApplicationResponse.successWithData(redisService.getKeysSize());
    }

    @GetMapping("memoryInfo")
    public ApplicationResponse<Map<String, Object>> getMemoryInfo() throws Exception {
        return ApplicationResponse.successWithData(redisService.getMemoryInfo());
    }

}
