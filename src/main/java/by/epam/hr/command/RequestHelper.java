package by.epam.hr.command;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHelper {

    public static final String SEARCH_JOB_PARAM = "searchJob";


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
        System.out.println(nameCommand);
        Command command = commands.get(nameCommand);
        System.out.println(command + " возвращен команд");
        if(command == null){
            System.out.println("ПРИСВОИЛ БЕЗ КОМАНДЫ");
            command = new NoCommand();
        }
        return command;
    }


}
