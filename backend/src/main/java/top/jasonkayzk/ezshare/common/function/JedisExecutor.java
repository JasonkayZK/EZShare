package top.jasonkayzk.ezshare.common.function;

import top.jasonkayzk.ezshare.common.exception.CacheException;

/**
 * @author zk
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {

    /**
     * 执行redis命令
     *
     * @param t redis命令
     *
     * @return 相应
     *
     * @throws CacheException Redis错误
     */
    R execute(T t) throws CacheException;

}
