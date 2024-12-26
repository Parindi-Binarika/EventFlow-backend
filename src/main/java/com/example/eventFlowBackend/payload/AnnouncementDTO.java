package com.example.eventFlowBackend.payload;

import lombok.Data;

public class AnnouncementDTO {
    private Integer aID;
    private String subject;
    private String massage;
    private Long createdBy;
    private boolean isActive;
    private boolean isSent;

    public Integer getaID() {
        return aID;
    }

    public void setaID(Integer aID) {
        this.aID = aID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
