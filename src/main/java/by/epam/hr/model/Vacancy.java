package by.epam.hr.model;


public class Vacancy extends Entity {
    private Long vacancyID;
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

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
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
    public String toString() {
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", vacancyTitle='" + vacancyTitle + '\'' +
                ", vacancyDescription='" + vacancyDescription + '\'' +
                ", vacancyStatus=" + vacancyStatus +
                ", location='" + location + '\'' +
                '}';
    }
}
