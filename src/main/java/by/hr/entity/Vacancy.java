package by.hr.entity;


public class Vacancy extends Entity {
    private Long vacancyID;
    private String vacancyTitle;
    private String vacancyDescription;
    private Boolean vacancyStatus;
    private String location;
    public Vacancy() {
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
