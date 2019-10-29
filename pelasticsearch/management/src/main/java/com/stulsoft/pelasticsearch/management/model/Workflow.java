/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.pelasticsearch.management.model;

import java.util.Objects;

/**
 * @author Yuriy Stul
 */
public class Workflow {
    private String workflowType;
    private String scheduledTime;
    private Integer version;
    private String workflowId;
    private String startTime;
    private String status;
    private String input;
    private String output;
    private Long executionTime;
    private Integer priority;
    private Integer inputSize;
    private Integer outputSize;
    private String updateTime;
    private String endTime;

    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getInputSize() {
        return inputSize;
    }

    public void setInputSize(Integer inputSize) {
        this.inputSize = inputSize;
    }

    public Integer getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(Integer outputSize) {
        this.outputSize = outputSize;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workflow)) return false;
        Workflow workflow = (Workflow) o;
        return Objects.equals(getWorkflowType(), workflow.getWorkflowType()) &&
                Objects.equals(getScheduledTime(), workflow.getScheduledTime()) &&
                Objects.equals(getVersion(), workflow.getVersion()) &&
                Objects.equals(getWorkflowId(), workflow.getWorkflowId()) &&
                Objects.equals(getStartTime(), workflow.getStartTime()) &&
                Objects.equals(getStatus(), workflow.getStatus()) &&
                Objects.equals(getInput(), workflow.getInput()) &&
                Objects.equals(getOutput(), workflow.getOutput()) &&
                Objects.equals(getExecutionTime(), workflow.getExecutionTime()) &&
                Objects.equals(getPriority(), workflow.getPriority()) &&
                Objects.equals(getInputSize(), workflow.getInputSize()) &&
                Objects.equals(getOutputSize(), workflow.getOutputSize()) &&
                Objects.equals(getUpdateTime(), workflow.getUpdateTime()) &&
                Objects.equals(getEndTime(), workflow.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkflowType(), getScheduledTime(), getVersion(), getWorkflowId(), getStartTime(), getStatus(), getInput(), getOutput(), getExecutionTime(), getPriority(), getInputSize(), getOutputSize(), getUpdateTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "workflowType='" + workflowType + '\'' +
                ", scheduledTime='" + scheduledTime + '\'' +
                ", version=" + version +
                ", workflowId='" + workflowId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", status='" + status + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", executionTime=" + executionTime +
                ", priority=" + priority +
                ", inputSize=" + inputSize +
                ", outputSize=" + outputSize +
                ", updateTime='" + updateTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
