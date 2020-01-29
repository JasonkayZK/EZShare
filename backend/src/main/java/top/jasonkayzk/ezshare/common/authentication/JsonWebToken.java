package top.jasonkayzk.ezshare.common.authentication;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JSON Web Token实体类
 *
 * @author zk
 */
@Data
public class JsonWebToken implements AuthenticationToken {

    private static final long serialVersionUID = 5813653837647128539L;

    private String token;

    private String expireAt;

    public JsonWebToken(String token) {
        this.token = token;
    }

    public JsonWebToken(String token, String expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
