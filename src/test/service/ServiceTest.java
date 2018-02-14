package service;

import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Interview;
import by.epam.hr.model.InterviewType;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import by.epam.hr.service.InterviewService;
import by.epam.hr.service.ProfileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ServiceTest {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private ProfileService profileService;
    private InterviewService interviewService;

    private static long userId;
    private static long interviewId;
    @BeforeClass
    public void initProfileServiceTest() {
        try {
            interviewService = new InterviewService();
            profileService = new ProfileService();
            Assert.assertNotNull(profileService);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,"Error in init");
        }
    }
    @Test
    public void createProfileTest() throws ServiceException {
        String email = "testng@testng.testng";
        String pass = "!QAZ1qaz";
        Profile profile = new Profile();
        profile.setEmail(email);
        profile.setPassword(pass);
        profile.setRole(Role.ADMIN);
        int count = profileService.select().size();
        profileService.createNewProfile(profile);
        for (Profile user: profileService.select()) {
            if(user.getEmail().equals(email)){
                userId = user.getProfileId();
            }
        }
        int newCount = profileService.select().size();
        Assert.assertNotEquals(count, newCount, "createProfileTest faild");
    }
    @Test(dependsOnMethods = {"createProfileTest"})
    public void updateProfileTest() throws ServiceException {
        Role role = Role.EMPLOYER;
        String pass = "!QAZ1qaz";
        Profile profile = profileService.selectUserById(userId);
        profileService.updateUser(profile.getEmail(),pass,role);
        Assert.assertEquals(role.name(),profileService.selectUserById(userId).getRole().name(),"Update User Failed");
    }

    @Test(dependsOnMethods = {"updateProfileTest"})
    public void createInterviewTest() throws ServiceException {
        String text = "Hello";
        int count;
        Interview interview = new Interview(new Timestamp(new Date().getTime()),text, InterviewType.COMMON, userId, userId, userId);
        count = interviewService.selectInterviewByEmployerID(userId).size();
        interviewService.insertInterview(interview);
        List<Interview> list = interviewService.selectInterviewByEmployerID(userId);
        for (Interview inter: list) {
            if(inter.getInterviewDescription().equals(text)){
                interviewId = inter.getInterviewId();
            }
        }
        int newCount = list.size();
        Assert.assertNotEquals(count, newCount, "createInterviewTest failed");
    }


    @AfterClass
    public void destroyProfileServiceTest() {
        try {
            profileService.delete(userId);
            interviewService.deleteInterview(interviewId);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,"Error in destroy");
        }
        finally {
            ConnectionPool.getInstance().destroy();
        }
    }
}
