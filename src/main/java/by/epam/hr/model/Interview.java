package by.epam.hr.model;

import java.sql.Timestamp;

public class Interview extends Entity {
    private Long interviewId;
    private Timestamp interviewTime;
    private String interviewDescription;
    private InterviewType interviewType;
    private Long technicalId;
    private Long employerId;
    private Long candidateId;

    public Interview() {
    }

    public Interview(Timestamp interviewTime, String interviewDescription, InterviewType interviewType, Long technicalId, Long employerId, Long candidateId) {
        this.interviewTime = interviewTime;
        this.interviewDescription = interviewDescription;
        this.interviewType = interviewType;
        this.technicalId = technicalId;
        this.employerId = employerId;
        this.candidateId = candidateId;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
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

    public Long getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Long technicalId) {
        this.technicalId = technicalId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interview interview = (Interview) o;

        if (interviewId != null ? !interviewId.equals(interview.interviewId) : interview.interviewId != null)
            return false;
        if (interviewDescription != null ? !interviewDescription.equals(interview.interviewDescription) : interview.interviewDescription != null)
            return false;
        if (interviewType != interview.interviewType) return false;
        if (technicalId != null ? !technicalId.equals(interview.technicalId) : interview.technicalId != null)
            return false;
        if (employerId != null ? !employerId.equals(interview.employerId) : interview.employerId != null) return false;
        return candidateId != null ? candidateId.equals(interview.candidateId) : interview.candidateId == null;
    }

    @Override
    public int hashCode() {
        int result = interviewId != null ? interviewId.hashCode() : 0;
        result = 31 * result + (interviewDescription != null ? interviewDescription.hashCode() : 0);
        result = 31 * result + (interviewType != null ? interviewType.hashCode() : 0);
        result = 31 * result + (technicalId != null ? technicalId.hashCode() : 0);
        result = 31 * result + (employerId != null ? employerId.hashCode() : 0);
        result = 31 * result + (candidateId != null ? candidateId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "interviewId=" + interviewId +
                ", interviewTime=" + interviewTime +
                ", interviewDescription='" + interviewDescription + '\'' +
                ", interviewType=" + interviewType +
                ", technicalId=" + technicalId +
                ", employerId=" + employerId +
                ", candidateId=" + candidateId +
                '}';
    }
}
