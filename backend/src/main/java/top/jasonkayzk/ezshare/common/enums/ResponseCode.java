package top.jasonkayzk.ezshare.common.enums;

import top.jasonkayzk.ezshare.common.response.ApplicationResponse;

import static top.jasonkayzk.ezshare.common.response.ApplicationResponse.ResponseParam.buildParam;

/**
 * 相应Code
 *
 * @author zk
 */

public enum ResponseCode {

    SUCCESS(buildParam(0, "SUCCESS"));

    public final ApplicationResponse.ResponseParam PARAM;

    ResponseCode(ApplicationResponse.ResponseParam param) {
        this.PARAM = param;
    }

    public int getCode() {
        return this.PARAM.getCode();
    }

    public String getMsg() {
        return this.PARAM.getMsg();
    }

}
