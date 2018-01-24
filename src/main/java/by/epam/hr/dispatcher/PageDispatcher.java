package by.epam.hr.dispatcher;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PageDispatcher {

    private static PageDispatcher instance;
    private static final String BUNDLE_NAME = "dispatcher";
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
    public static final String VACANCIES_PAGE_PATH = "VACANCIES_PAGE_PATH";
    public static final String VACANCY_PAGE_PATH = "VACANCY_PAGE_PATH";
    public static final String REGISTRATION_PAGE_PATH = "REGISTRATION_PAGE_PATH";
    public static final String PAGE_404_PATH = "PAGE_404_PATH";
    private ResourceBundle resourceBundle;
    private static AtomicBoolean dispatcherCreated = new AtomicBoolean(false);
    private static Lock lockInstance = new ReentrantLock();


    public static PageDispatcher getInstance(){
        if (!dispatcherCreated.get()){
            lockInstance.lock();
            if(instance == null){
                try {
                    instance = new PageDispatcher();
                    instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
                    dispatcherCreated.set(true);
                }
                finally {
                    lockInstance.unlock();
                }
            }
        }
        return instance ;
    }



    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }

}
