package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Message;
import by.epam.hr.model.Profile;
import by.epam.hr.service.MessageService;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChatCommand implements Command{
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String MESSAGES = "messages";
    private static final String SENDER_ID = "sender";
    private MessageService messageService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result =null;
        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        String sender = request.getParameter(SENDER_ID);
        if(profile != null && sender != null) {
            try {
                messageService = new MessageService();
                List<Message> messages = messageService.selectDialogById(Long.parseLong(sender), profile.getProfileID());
                if(messages != null){
                    result = new Gson().toJson(messages);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"ChatCommand can not return a list of message");
            }
        }
        else{
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        request.setAttribute(ATTR_PAGE,JSON);
        return result;

    }

}
