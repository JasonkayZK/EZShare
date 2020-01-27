package top.jasonkayzk.ezshare.job.enums;

/**
 * 任务计划状态
 *
 * @author zk
 */
public enum ScheduleStatusEnum {

    /**
     * 正常
     */
    NORMAL("0"),
    /**
     * 暂停
     */
    PAUSE("1");

    private String value;

    ScheduleStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
