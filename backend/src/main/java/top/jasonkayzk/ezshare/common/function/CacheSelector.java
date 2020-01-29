package top.jasonkayzk.ezshare.common.function;

/**
 * 缓存选择函数接口
 *
 * @author zk
 */
@FunctionalInterface
public interface CacheSelector<T> {

    /**
     * 选择缓存
     *
     * @return 命中的缓存内容
     *
     * @throws Exception 异常
     */
    T select() throws Exception;

}
