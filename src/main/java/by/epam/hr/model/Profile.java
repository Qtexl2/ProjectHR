package by.epam.hr.model;


/**
 * The Class Profile.
 */
public class Profile extends Entity{
    /** The profile id. */
    private Long profileId;

    /** The email. */
    private String email;

    /** The password. */
    private String password;

    /** The role. */
    private Role role;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The phone. */
    private String phone;

    /** The english level. */
    private EnglishLevel englishLevel;

    /** The age. */
    private Integer age;

    /** The gender. */
    private Gender gender;

    /** The current position. */
    private String currentPosition;

    /** The describe. */
    private String describe;

    /** The resume. */
    private String resume;

    /** The photo. */
    private String photo;

    /** The pre interview. */
    private String preInterview;

    /** The technical interview. */
    private String technicalInterview;

    /** The status interview. */
    private Boolean statusInterview;

    /** The company. */
    private String company;

    /**
     * Instantiates a new profile.
     *
     * @param email the email
     * @param password the password
     * @param role the role
     */
    public Profile(String email, String password, Role role) {
        this.email = email;

        this.password = password;
        this.role = role;
    }

    /**
     * Instantiates a new profile.
     */
    public Profile() {
    }

    /**
     * Gets the profile id.
     *
     * @return the profile id
     */
    public Long getProfileId() {
        return profileId;
    }

    /**
     * Sets the profile id.
     *
     * @param profileId the new profile id
     */
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
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
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone the new phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the english level.
     *
     * @return the english level
     */
    public EnglishLevel getEnglishLevel() {
        return englishLevel;
    }

    /**
     * Sets the english level.
     *
     * @param englishLevel the new english level
     */
    public void setEnglishLevel(EnglishLevel englishLevel) {
        this.englishLevel = englishLevel;
    }

    /**
     * Gets the age.
     *
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age.
     *
     * @param age the new age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the current position.
     *
     * @return the current position
     */
    public String getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the current position.
     *
     * @param currentPosition the new current position
     */
    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Gets the describe.
     *
     * @return the describe
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * Sets the describe.
     *
     * @param describe the new describe
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * Gets the resume.
     *
     * @return the resume
     */
    public String getResume() {
        return resume;
    }

    /**
     * Sets the resume.
     *
     * @param resume the new resume
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * Gets the photo.
     *
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets the photo.
     *
     * @param photo the new photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Gets the pre interview.
     *
     * @return the pre interview
     */
    public String getPreInterview() {
        return preInterview;
    }

    /**
     * Sets the pre interview.
     *
     * @param preInterview the new pre interview
     */
    public void setPreInterview(String preInterview) {
        this.preInterview = preInterview;
    }

    /**
     * Gets the technical interview.
     *
     * @return the technical interview
     */
    public String getTechnicalInterview() {
        return technicalInterview;
    }

    /**
     * Sets the technical interview.
     *
     * @param technicalInterview the new technical interview
     */
    public void setTechnicalInterview(String technicalInterview) {
        this.technicalInterview = technicalInterview;
    }

    /**
     * Gets the status interview.
     *
     * @return the status interview
     */
    public Boolean getStatusInterview() {
        return statusInterview;
    }

    /**
     * Sets the status interview.
     *
     * @param statusInterview the new status interview
     */
    public void setStatusInterview(Boolean statusInterview) {
        this.statusInterview = statusInterview;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /**
     * @see java.lang.Object#hashCode()
     */
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

    /**
     * @see java.lang.Object#toString()
     */
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
