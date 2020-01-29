package top.jasonkayzk.ezshare.common.service.impl;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.jasonkayzk.ezshare.common.entity.RedisInfo;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.common.function.JedisExecutor;
import top.jasonkayzk.ezshare.common.service.RedisService;

import java.util.*;

/**
 * Redis 工具类，封装了几个常用的 redis 命令
 *
 * @author zk
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    private static String separator = System.getProperty("line.separator");

    final JedisPool jedisPool;

    public RedisServiceImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 处理 jedis请求
     *
     * @param j 处理逻辑，通过 lambda行为参数化
     * @return 处理结果
     */
    private <T> T executeByJedis(JedisExecutor<Jedis, T> j) throws CacheException {
        try (Jedis jedis = jedisPool.getResource()) {
            return j.execute(jedis);
        } catch (Exception e) {
            throw new CacheException(e.getMessage());
        }
    }

    @Override
    public List<RedisInfo> getRedisInfo() throws CacheException {
        String info = this.executeByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        List<RedisInfo> infoList = new ArrayList<>();
        String[] strs = Objects.requireNonNull(info).split(separator);
        RedisInfo redisInfo;
        if (strs.length > 0) {
            for (String str1 : strs) {
                redisInfo = new RedisInfo();
                String[] str = str1.split(":");
                if (str.length > 1) {
                    String key = str[0];
                    String value = str[1];
                    redisInfo.setKey(key);
                    redisInfo.setValue(value);
                    infoList.add(redisInfo);
                }
            }
        }
        return infoList;
    }

    @Override
    public Map<String, Object> getKeysSize() throws CacheException {
        Long dbSize = this.executeByJedis(
                j -> {
                    Client client = j.getClient();
                    client.dbSize();
                    return client.getIntegerReply();
                }
        );
        Map<String, Object> map = new HashMap<>(8);
        map.put("create_time", System.currentTimeMillis());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String, Object> getMemoryInfo() throws CacheException {
        String info = this.executeByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        String[] strs = Objects.requireNonNull(info).split(separator);
        Map<String, Object> map = null;
        for (String s : strs) {
            String[] detail = s.split(":");
            if ("used_memory".equals(detail[0])) {
                map = new HashMap<>();
                map.put("used_memory", detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", System.currentTimeMillis());
                break;
            }
        }
        return map;
    }

    @Override
    public Set<String> getKeys(String pattern) throws CacheException {
        return this.executeByJedis(j -> j.keys(pattern));
    }

    @Override
    public String get(String key) throws CacheException {
        return this.executeByJedis(j -> j.get(key.toLowerCase()));
    }

    @Override
    public String set(String key, String value) throws CacheException {
        return this.executeByJedis(j -> j.set(key.toLowerCase(), value));
    }

    @Override
    public String set(String key, String value, Long milliseconds) throws CacheException {
        String result = this.set(key.toLowerCase(), value);
        this.pexpire(key, milliseconds);
        return result;
    }

    @Override
    public Long del(String... key) throws CacheException {
        return this.executeByJedis(j -> j.del(key));
    }

    @Override
    public Boolean exists(String key) throws CacheException {
        return this.executeByJedis(j -> j.exists(key));
    }

    @Override
    public Long pttl(String key) throws CacheException {
        return this.executeByJedis(j -> j.pttl(key));
    }

    @Override
    public Long pexpire(String key, Long milliseconds) throws CacheException {
        return this.executeByJedis(j -> j.pexpire(key, milliseconds));
    }

    @Override
    public Long zadd(String key, Double score, String member) throws CacheException {
        return this.executeByJedis(j -> j.zadd(key, score, member));
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) throws CacheException {
        return this.executeByJedis(j -> j.zrangeByScore(key, min, max));
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) throws CacheException {
        return this.executeByJedis(j -> j.zremrangeByScore(key, start, end));
    }

    @Override
    public Long zrem(String key, String... members) throws CacheException {
        return this.executeByJedis(j -> j.zrem(key, members));
    }

}
