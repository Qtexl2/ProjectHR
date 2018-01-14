package by.hr.entity;

import java.sql.Timestamp;

public class Interview extends Entity {
    private Long interviewID;
    private Timestamp interviewTime;
    private String interviewDescription;
    private InterviewType interviewType;
    private Long technicalID;
    private Long employerID;
    private Long candidateID;

    public Interview() {
    }

    public Long getInterviewID() {
        return interviewID;
    }

    public void setInterviewID(Long interviewID) {
        this.interviewID = interviewID;
    }

    public Timestamp getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(Timestamp interviewTime) {
        this.interviewTime = interviewTime;
    }

    public String getInterviewDescription() {
        return interviewDescription;
    }

    public void setInterviewDescription(String interviewDescription) {
        this.interviewDescription = interviewDescription;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public Long getTechnicalID() {
        return technicalID;
    }

    public void setTechnicalID(Long technicalID) {
        this.technicalID = technicalID;
    }

    public Long getEmployerID() {
        return employerID;
    }

    public void setEmployerID(Long employerID) {
        this.employerID = employerID;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "interviewID=" + interviewID +
                ", interviewTime=" + interviewTime +
                ", interviewDescription='" + interviewDescription + '\'' +
                ", interviewType=" + interviewType +
                ", technicalID=" + technicalID +
                ", employerID=" + employerID +
                ", candidateID=" + candidateID +
                '}';
    }
}
