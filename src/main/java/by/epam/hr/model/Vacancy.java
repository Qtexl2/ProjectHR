package by.epam.hr.model;


/**
 * The Class Vacancy.
 */
public class Vacancy extends Entity {

    /** The vacancy id. */
    private Long vacancyId;

    /** The vacancy title. */
    private String vacancyTitle;

    /** The vacancy description. */
    private String vacancyDescription;

    /** The vacancy status. */
    private Boolean vacancyStatus;

    /** The location. */
    private String location;

    /** The company. */
    private String company;

    /** The employer id. */
    private Long employerId;

    /**
     * Instantiates a new vacancy.
     */
    public Vacancy() {
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
     * Gets the company.
     *
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company.
     *
     * @param company the new company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Instantiates a new vacancy.
     *
     * @param vacancyTitle the vacancy title
     * @param vacancyDescription the vacancy description
     * @param location the location
     * @param company the company
     */
    public Vacancy(String vacancyTitle, String vacancyDescription, String location, String company) {
        this.vacancyTitle = vacancyTitle;
        this.vacancyDescription = vacancyDescription;
        this.location = location;
        this.company = company;
    }

    /**
     * Gets the vacancy id.
     *
     * @return the vacancy id
     */
    public Long getVacancyId() {
        return vacancyId;
    }

    /**
     * Sets the vacancy id.
     *
     * @param vacancyId the new vacancy id
     */
    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    /**
     * Gets the vacancy title.
     *
     * @return the vacancy title
     */
    public String getVacancyTitle() {
        return vacancyTitle;
    }

    /**
     * Sets the vacancy title.
     *
     * @param vacancyTitle the new vacancy title
     */
    public void setVacancyTitle(String vacancyTitle) {
        this.vacancyTitle = vacancyTitle;
    }

    /**
     * Gets the vacancy description.
     *
     * @return the vacancy description
     */
    public String getVacancyDescription() {
        return vacancyDescription;
    }

    /**
     * Sets the vacancy description.
     *
     * @param vacancyDescription the new vacancy description
     */
    public void setVacancyDescription(String vacancyDescription) {
        this.vacancyDescription = vacancyDescription;
    }

    /**
     * Gets the vacancy status.
     *
     * @return the vacancy status
     */
    public Boolean getVacancyStatus() {
        return vacancyStatus;
    }

    /**
     * Sets the vacancy status.
     *
     * @param vacancyStatus the new vacancy status
     */
    public void setVacancyStatus(Boolean vacancyStatus) {
        this.vacancyStatus = vacancyStatus;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacancy vacancy = (Vacancy) o;

        if (vacancyId != null ? !vacancyId.equals(vacancy.vacancyId) : vacancy.vacancyId != null) return false;
        if (vacancyTitle != null ? !vacancyTitle.equals(vacancy.vacancyTitle) : vacancy.vacancyTitle != null)
            return false;
        if (vacancyDescription != null ? !vacancyDescription.equals(vacancy.vacancyDescription) : vacancy.vacancyDescription != null)
            return false;
        if (vacancyStatus != null ? !vacancyStatus.equals(vacancy.vacancyStatus) : vacancy.vacancyStatus != null)
            return false;
        if (location != null ? !location.equals(vacancy.location) : vacancy.location != null) return false;
        if (company != null ? !company.equals(vacancy.company) : vacancy.company != null) return false;
        return employerId != null ? employerId.equals(vacancy.employerId) : vacancy.employerId == null;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = vacancyId != null ? vacancyId.hashCode() : 0;
        result = 31 * result + (vacancyTitle != null ? vacancyTitle.hashCode() : 0);
        result = 31 * result + (vacancyDescription != null ? vacancyDescription.hashCode() : 0);
        result = 31 * result + (vacancyStatus != null ? vacancyStatus.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (employerId != null ? employerId.hashCode() : 0);
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", vacancyTitle='" + vacancyTitle + '\'' +
                ", vacancyDescription='" + vacancyDescription + '\'' +
                ", vacancyStatus=" + vacancyStatus +
                ", location='" + location + '\'' +
                '}';
    }
}
