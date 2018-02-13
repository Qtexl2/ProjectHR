package by.epam.hr.model;


public class Profile extends Entity{
    private Long profileId;
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

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (profileId != null ? !profileId.equals(profile.profileId) : profile.profileId != null) return false;
        if (email != null ? !email.equals(profile.email) : profile.email != null) return false;
        if (password != null ? !password.equals(profile.password) : profile.password != null) return false;
        if (role != profile.role) return false;
        if (firstName != null ? !firstName.equals(profile.firstName) : profile.firstName != null) return false;
        if (lastName != null ? !lastName.equals(profile.lastName) : profile.lastName != null) return false;
        if (phone != null ? !phone.equals(profile.phone) : profile.phone != null) return false;
        if (englishLevel != profile.englishLevel) return false;
        if (age != null ? !age.equals(profile.age) : profile.age != null) return false;
        if (gender != profile.gender) return false;
        if (currentPosition != null ? !currentPosition.equals(profile.currentPosition) : profile.currentPosition != null)
            return false;
        if (describe != null ? !describe.equals(profile.describe) : profile.describe != null) return false;
        if (preInterview != null ? !preInterview.equals(profile.preInterview) : profile.preInterview != null)
            return false;
        if (technicalInterview != null ? !technicalInterview.equals(profile.technicalInterview) : profile.technicalInterview != null)
            return false;
        if (statusInterview != null ? !statusInterview.equals(profile.statusInterview) : profile.statusInterview != null)
            return false;
        return company != null ? company.equals(profile.company) : profile.company == null;
    }

    @Override
    public int hashCode() {
        int result = profileId != null ? profileId.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (englishLevel != null ? englishLevel.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (currentPosition != null ? currentPosition.hashCode() : 0);
        result = 31 * result + (describe != null ? describe.hashCode() : 0);
        result = 31 * result + (preInterview != null ? preInterview.hashCode() : 0);
        result = 31 * result + (technicalInterview != null ? technicalInterview.hashCode() : 0);
        result = 31 * result + (statusInterview != null ? statusInterview.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
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
