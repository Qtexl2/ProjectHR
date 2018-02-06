package by.epam.hr.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeLanguageCommand implements Command {
    private static final String REGEXP_URI = "\\/controller\\?.+";
    private static final String LANG = "lang=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter("lang");
        String referer = request.getHeader("Referer");
        Pattern pattern = Pattern.compile(REGEXP_URI);
        Pattern patternLang = Pattern.compile(LANG);
        Matcher matcher = pattern.matcher(referer);
        String currentUrl = null;
        while (matcher.find()){
            currentUrl = matcher.group();
            Matcher matcherLang = patternLang.matcher(currentUrl);
            while (matcherLang.find()){
                currentUrl = currentUrl.substring(0,matcherLang.start()-1);
            }
        }
        if(currentUrl != null){
            currentUrl = currentUrl +'&' + LANG + lang;
        }
        else {
            currentUrl = request.getScheme()+ "://" +request.getServerName() + ":" +request.getServerPort() + "?" + LANG + lang;

        }
        request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
        return currentUrl;
    }
}
