package by.epam.hr.command;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHelper {

    public static final String SEARCH_JOB_PARAM = "searchJob";
    public static final String VACANCY = "vacancy";
    public static final String REGISTRATION = "reg";
    public static final String REGISTRATION_PAGE = "regPage";
    public static final String AUTHORIZATION_PAGE = "authPage";
    public static final String AUTHORIZATION = "auth";


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
