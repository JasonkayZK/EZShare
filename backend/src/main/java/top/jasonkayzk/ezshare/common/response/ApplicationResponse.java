package top.jasonkayzk.ezshare.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A general response class
 *
 * @author zk
 */
@Data
@ApiModel(description = "通用返回响应类型")
public class ApplicationResponse<T> {

    private static final int SUCCESS_CODE = 200;

    private static final String SUCCESS_MESSAGE = "Success";

    @ApiModelProperty(value = "响应码", name = "code", required = true, example = "" + SUCCESS_CODE)
    private int code;

    @ApiModelProperty(value = "响应消息", name = "msg", required = true, example = SUCCESS_MESSAGE)
    private String msg;

    @ApiModelProperty(value = "响应数据", name = "data")
    private T data;


    private ApplicationResponse() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    private ApplicationResponse(int code, String msg) {
        this(code, msg, null);
    }

    private ApplicationResponse(T data) {
        this(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    private ApplicationResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApplicationResponse<T> success() {
        return new ApplicationResponse<>();
    }

    public static <T> ApplicationResponse<T> successWithData(T data) {
        return new ApplicationResponse<>(data);
    }

    public static <T> ApplicationResponse<T> failWithCodeAndMsg(int code, String msg) {
        return new ApplicationResponse<>(code, msg, null);
    }

    public static <T> ApplicationResponse<T> buildWithParam(ResponseParam param) {
        return new ApplicationResponse<>(param.getCode(), param.getMsg(), null);
    }

    @Data
    public static class ResponseParam {

        private int code;

        private String msg;

        private ResponseParam(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public static ResponseParam buildParam(int code, String msg) {
            return new ResponseParam(code, msg);
        }
    }

}