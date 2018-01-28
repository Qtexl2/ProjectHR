package by.epam.hr.command;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHelper {

    public static final String SEARCH_JOB_PARAM = "searchJob";
    public static final String VACANCY = "vacancy";
    public static final String PROFILE_PAGE = "profile";
    public static final String PROFILE_UPDATE = "profileUpdate";
    public static final String REGISTRATION = "reg";
    public static final String VACANCY_RESPOND = "vacancyRespond";
    public static final String REGISTRATION_PAGE = "regPage";
    public static final String AUTHORIZATION_PAGE = "authPage";
    public static final String LOGOUT = "logout";
    public static final String AUTHORIZATION = "auth";
//    public static final String IMAGE = "img";
    public static final String IMAGE_UPDATE = "imgUpd";


    private static AtomicBoolean requestHelperCreated = new AtomicBoolean(false);
    private static final String REQ_PARAM = "command";
    private static Lock lockInstance = new ReentrantLock();
    private static RequestHelper instance = null;
    private HashMap<String, Command> commands;
    private RequestHelper(){
        createCommands();
    }

    private void createCommands(){
        commands = new HashMap<>();
        commands.put(SEARCH_JOB_PARAM,new SearchJobCommand());
        commands.put(VACANCY, new VacancyCommand());
        commands.put(REGISTRATION, new RegisterCommand());
        commands.put(REGISTRATION_PAGE, new RedirectToRegistrationCommand());
        commands.put(AUTHORIZATION_PAGE, new RedirectToAuthCommand());
        commands.put(AUTHORIZATION, new AuthorizationCommand());
//        commands.put(IMAGE, new ImageCommand());
        commands.put(LOGOUT, new LogoutCommand());
        commands.put(PROFILE_PAGE, new RedirectToProfileCommand());
        commands.put(PROFILE_UPDATE, new ProfileUpdateCommand());
        commands.put(IMAGE_UPDATE, new UploadImageCommand());
        commands.put(VACANCY_RESPOND, new VacancyRespondCommand());
    }

    public static RequestHelper getInstance(){
        if (!requestHelperCreated.get()){
            lockInstance.lock();
            if(instance == null){
                try {
                    instance = new RequestHelper();
                    requestHelperCreated.set(true);
                }
                finally {
                    lockInstance.unlock();
                }
            }
        }
        return instance ;
    }

    public Command getCommand(HttpServletRequest request){
        String nameCommand = request.getParameter(REQ_PARAM);
        Command command = commands.get(nameCommand);
        if(command == null){
            command = new NoCommand();
        }
        return command;
    }


}
