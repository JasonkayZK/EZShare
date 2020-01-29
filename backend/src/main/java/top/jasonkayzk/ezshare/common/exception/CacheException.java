package top.jasonkayzk.ezshare.common.exception;

/**
 * Redis 连接异常
 *
 * @author zk
 */
public class CacheException extends Exception {

    private static final long serialVersionUID = -1919683607002193871L;

    public CacheException(String message) {
        super(message);
    }
}
