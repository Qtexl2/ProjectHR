package by.epam.hr.model;

import java.sql.Timestamp;

/**
 * The Class Interview.
 */
public class Interview extends Entity {

    /** The interview id. */
    private Long interviewId;

    /** The interview time. */
    private Timestamp interviewTime;

    /** The interview description. */
    private String interviewDescription;

    /** The interview type. */
    private InterviewType interviewType;

    /** The technical id. */
    private Long technicalId;

    /** The employer id. */
    private Long employerId;

    /** The candidate id. */
    private Long candidateId;

    /**
     * Instantiates a new interview.
     */
    public Interview() {
    }

    /**
     * Instantiates a new interview.
     *
     * @param interviewTime the interview time
     * @param interviewDescription the interview description
     * @param interviewType the interview type
     * @param technicalId the technical id
     * @param employerId the employer id
     * @param candidateId the candidate id
     */
    public Interview(Timestamp interviewTime, String interviewDescription, InterviewType interviewType, Long technicalId, Long employerId, Long candidateId) {
        this.interviewTime = interviewTime;
        this.interviewDescription = interviewDescription;
        this.interviewType = interviewType;
        this.technicalId = technicalId;
        this.employerId = employerId;
        this.candidateId = candidateId;
    }

    /**
     * Gets the interview id.
     *
     * @return the interview id
     */
    public Long getInterviewId() {
        return interviewId;
    }

    /**
     * Sets the interview id.
     *
     * @param interviewId the new interview id
     */
    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    /**
     * Gets the interview time.
     *
     * @return the interview time
     */
    public Timestamp getInterviewTime() {
        return interviewTime;
    }

    /**
     * Sets the interview time.
     *
     * @param interviewTime the new interview time
     */
    public void setInterviewTime(Timestamp interviewTime) {
        this.interviewTime = interviewTime;
    }

    /**
     * Gets the interview description.
     *
     * @return the interview description
     */
    public String getInterviewDescription() {
        return interviewDescription;
    }

    /**
     * Sets the interview description.
     *
     * @param interviewDescription the new interview description
     */
    public void setInterviewDescription(String interviewDescription) {
        this.interviewDescription = interviewDescription;
    }

    /**
     * Gets the interview type.
     *
     * @return the interview type
     */
    public InterviewType getInterviewType() {
        return interviewType;
    }

    /**
     * Sets the interview type.
     *
     * @param interviewType the new interview type
     */
    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    /**
     * Gets the technical id.
     *
     * @return the technical id
     */
    public Long getTechnicalId() {
        return technicalId;
    }

    /**
     * Sets the technical id.
     *
     * @param technicalId the new technical id
     */
    public void setTechnicalId(Long technicalId) {
        this.technicalId = technicalId;
    }

    /**
     * Gets the employer id.
     *
     * @return the employer id
     */
    public Long getEmployerId() {
        return employerId;
    }

    /**
     * Sets the employer id.
     *
     * @param employerId the new employer id
     */
    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    /**
     * Gets the candidate id.
     *
     * @return the candidate id
     */
    public Long getCandidateId() {
        return candidateId;
    }

    /**
     * Sets the candidate id.
     *
     * @param candidateId the new candidate id
     */
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /**
     * @see java.lang.Object#hashCode()
     */
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

    /**
     * @see java.lang.Object#toString()
     */
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
