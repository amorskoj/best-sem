package com.perfect.commons.quartz;

/**
 * Created on 2015-09-29.
 * <p>定时任务类
 *
 * @author dolphineor
 */
public class ScheduledJob {

    /**
     * 任务id
     */
    private final String jobId;

    /**
     * 任务名称
     */
    private final String jobName;

    /**
     * 任务分组
     */
    private final String jobGroup;

    /**
     * 任务类型
     *
     * @see com.perfect.commons.constants.MaterialsJobEnum
     */
    private final int jobType;

    /**
     * 任务状态
     *
     * @see com.perfect.commons.constants.MaterialsJobEnum
     */
    private final int jobStatus;

    /**
     * 任务运行时间表达式
     */
    private final String cronExpression;


    private ScheduledJob(String jobId, String jobName, String jobGroup, int jobType, int jobStatus, String cronExpression) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobType = jobType;
        this.jobStatus = jobStatus;
        this.cronExpression = cronExpression;
    }

    public static class Builder {
        private String jobId;
        private String jobName;
        private String jobGroup;
        private int jobType;
        private int jobStatus;
        private String cronExpression;

        public Builder jobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public Builder jobName(String jobName) {
            this.jobName = jobName;
            return this;
        }

        public Builder jobGroup(String jobGroup) {
            this.jobGroup = jobGroup;
            return this;
        }

        public Builder jobType(int jobType) {
            this.jobType = jobType;
            return this;
        }

        public Builder jobStatus(int jobStatus) {
            this.jobStatus = jobStatus;
            return this;
        }

        public Builder cronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
            return this;
        }

        public ScheduledJob build() {
            return new ScheduledJob(jobId, jobName, jobGroup, jobType, jobStatus, cronExpression);
        }
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public int getJobType() {
        return jobType;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }
}
