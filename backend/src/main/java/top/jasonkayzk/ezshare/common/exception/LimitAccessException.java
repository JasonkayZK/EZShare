package top.jasonkayzk.ezshare.common.exception;

/**
 * 限流异常
 *
 * @author zk
 */
public class LimitAccessException extends Exception {

    private static final long serialVersionUID = -6258122661932716465L;

    public LimitAccessException(String message) {
        super(message);
    }

}
