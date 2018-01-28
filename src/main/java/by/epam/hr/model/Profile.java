package by.epam.hr.model;


import java.sql.Blob;

public class Profile extends Entity{
    private Long profileID;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private String phone;
    private EnglishLevel englishLevel;
    private Integer age;
    private Gender gender;
    private String currentPosition;
    private String describe;
    private String resume;
    private String photo;
    private String preInterview;
    private String technicalInterview;
    private Boolean statusInterview;
    private String company;

    public Profile(String email, String password, Role role) {
        this.email = email;

        this.password = password;
        this.role = role;
    }

    public Profile() {
    }

    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EnglishLevel getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(EnglishLevel englishLevel) {
        this.englishLevel = englishLevel;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPreInterview() {
        return preInterview;
    }

    public void setPreInterview(String preInterview) {
        this.preInterview = preInterview;
    }

    public String getTechnicalInterview() {
        return technicalInterview;
    }

    public void setTechnicalInterview(String technicalInterview) {
        this.technicalInterview = technicalInterview;
    }

    public Boolean getStatusInterview() {
        return statusInterview;
    }

    public void setStatusInterview(Boolean statusInterview) {
        this.statusInterview = statusInterview;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileID=" + profileID +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", englishLevel=" + englishLevel +
                ", age=" + age +
                ", gender=" + gender +
                ", currentPosition='" + currentPosition + '\'' +
                ", describe='" + describe + '\'' +
                ", preInterview='" + preInterview + '\'' +
                ", technicalInterview='" + technicalInterview + '\'' +
                ", statusInterview=" + statusInterview +
                '}';
    }
}
