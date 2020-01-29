package top.jasonkayzk.ezshare.common.exception;

import javax.naming.AuthenticationException;

/**
 * Token过期异常
 *
 * @author zk
 */
public class TokenTimeoutException extends AuthenticationException {

    private static final long serialVersionUID = 3216364098688253717L;

    public TokenTimeoutException(String message) {
        super(message);
    }

}
