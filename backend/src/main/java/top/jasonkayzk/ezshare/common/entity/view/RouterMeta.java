package top.jasonkayzk.ezshare.common.entity.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Vue路由元数据
 *
 * @author zk
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta implements Serializable {

    private static final long serialVersionUID = 3761113871022347522L;

    private Boolean closeable;

    private Boolean isShow;

}
