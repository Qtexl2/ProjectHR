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
    public static final String IMAGE_UPDATE = "imgUpd";
    public static final String ALL_DIALOG = "allDialog";
    public static final String CHAT = "chat";
    public static final String SEND_MESSAGE = "sendMessage";
    public static final String INTERVIEW = "interview";
    public static final String CREATE_INTERVIEW = "createInterview";
    public static final String SELECT_INTERVIEW = "selectInterview";
    public static final String UPDATE_INTERVIEW_PAGE = "updateInterviewPage";
    public static final String UPDATE_INTERVIEW = "updateInterview";
    public static final String DELETE_INTERVIEW = "deleteInterview";
    public static final String GET_USER_PAGE = "page";
    public static final String UPDATE_USER = "userUpdate";
    public static final String UPDATE_VACANCY_PAGE = "vacancyEdit";
    public static final String MY_VACANCIES = "myVacancies";
    public static final String CREATE_VACANCY_PAGE = "createVacancyPage";
    public static final String CREATE_VACANCY = "createVacancy";
    public static final String VACANCY_UPDATE = "editVacancy";
    public static final String VACANCY_DELETE = "deleteVacancy";
    public static final String USER_MANAGER = "userManager";


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
        commands.put(LOGOUT, new LogoutCommand());
        commands.put(PROFILE_PAGE, new RedirectToProfileCommand());
        commands.put(PROFILE_UPDATE, new ProfileUpdateCommand());
        commands.put(IMAGE_UPDATE, new UploadImageCommand());
        commands.put(VACANCY_RESPOND, new VacancyRespondCommand());
        commands.put(ALL_DIALOG, new DialogCommand());
        commands.put(CHAT, new ChatCommand());
        commands.put(SEND_MESSAGE, new SendMessageCommand());
        commands.put(INTERVIEW, new CreateInterviewPageCommand());
        commands.put(GET_USER_PAGE, new EditUserPageCommand());
        commands.put(UPDATE_USER, new EditUserCommand());
        commands.put(MY_VACANCIES, new EmployerVacancyCommand());
        commands.put(CREATE_VACANCY_PAGE, new CreateVacancyPageCommand());
        commands.put(CREATE_VACANCY, new CreateVacancyCommand());
        commands.put(UPDATE_VACANCY_PAGE, new EditVacancyPageCommand());
        commands.put(VACANCY_UPDATE, new EditVacancyCommand());
        commands.put(VACANCY_DELETE, new DeleteVacancyCommand());
        commands.put(CREATE_INTERVIEW, new CreateInterviewCommand());
        commands.put(SELECT_INTERVIEW, new GetInterviewPageCommand());
        commands.put(UPDATE_INTERVIEW_PAGE, new EditInterviewPageCommand());
        commands.put(UPDATE_INTERVIEW, new EditInterviewCommand());
        commands.put(DELETE_INTERVIEW, new DeleteInterviewCommand());
        commands.put(USER_MANAGER, new SelectUserCommand());
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
