package by.epam.hr.command;

import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.VacancyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacancyRespondCommand implements Command{


    VacancyService vacancyService;

    public VacancyRespondCommand() {
        vacancyService = new VacancyService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute("profile");
        String message;
        if (profile != null) {
            String id = request.getParameter("idVac");
            if (id!=null){
                try {
                    vacancyService.insertVacandyAndProfile(profile.getProfileID(),Long.parseLong(id));
                } catch (ServiceException e) {

                }

            }
        }
        else {
            //редирект
        }
        return "";
    }
}
