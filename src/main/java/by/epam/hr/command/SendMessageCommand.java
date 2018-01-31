package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.MessageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String RECEIVER = "receiver";
    private static final String TEXT = "text";
    private MessageService messageService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MessageService messageService;
        String result = null;
        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        String receiverId = request.getParameter(RECEIVER);
        String text = request.getParameter(TEXT);
        if(profile != null && receiverId !=null && text != null){
            Long senderId = profile.getProfileID();
            try {
                messageService = new MessageService();
                if(messageService.insertMessage(senderId,Long.parseLong(receiverId),text)){
                    request.setAttribute(ATTR_PAGE,JSON);
                    return SUCCESS;
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"DialogCommand can not return a list of profiles");
            }
        }
        else{
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            result = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return result;
    }
}
