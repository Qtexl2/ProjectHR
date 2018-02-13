package by.epam.hr.model;


public class Vacancy extends Entity {
    private Long vacancyId;
    private String vacancyTitle;
    private String vacancyDescription;
    private Boolean vacancyStatus;
    private String location;
    private String company;
    private Long employerId;
    public Vacancy() {
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Vacancy(String vacancyTitle, String vacancyDescription, String location, String company) {
        this.vacancyTitle = vacancyTitle;
        this.vacancyDescription = vacancyDescription;
        this.location = location;
        this.company = company;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getVacancyTitle() {
        return vacancyTitle;
    }

    public void setVacancyTitle(String vacancyTitle) {
        this.vacancyTitle = vacancyTitle;
    }

    public String getVacancyDescription() {
        return vacancyDescription;
    }

    public void setVacancyDescription(String vacancyDescription) {
        this.vacancyDescription = vacancyDescription;
    }

    public Boolean getVacancyStatus() {
        return vacancyStatus;
    }

    public void setVacancyStatus(Boolean vacancyStatus) {
        this.vacancyStatus = vacancyStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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
