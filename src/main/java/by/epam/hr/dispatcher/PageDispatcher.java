package by.epam.hr.dispatcher;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Class PageDispatcher.
 */
public class PageDispatcher {

    /** The instance. */
    private static PageDispatcher instance;

    /** The Constant BUNDLE_NAME. */
    private static final String BUNDLE_NAME = "dispatcher";

    /** The Constant MAIN_PAGE_PATH. */
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";

    /** The Constant VACANCIES_PAGE_PATH. */
    public static final String VACANCIES_PAGE_PATH = "VACANCIES_PAGE_PATH";

    /** The Constant EMPLOYER_VACANCIES_PAGE_PATH. */
    public static final String EMPLOYER_VACANCIES_PAGE_PATH = "EMPLOYER_VACANCIES_PAGE_PATH";

    /** The Constant PROFILE_PAGE_PATH. */
    public static final String PROFILE_PAGE_PATH = "PROFILE_PAGE_PATH";

    /** The Constant VACANCY_PAGE_PATH. */
    public static final String VACANCY_PAGE_PATH = "VACANCY_PAGE_PATH";

    /** The Constant EDIT_INTERVIEW_PAGE_PATH. */
    public static final String EDIT_INTERVIEW_PAGE_PATH = "EDIT_INTERVIEW_PAGE_PATH";

    /** The Constant REGISTRATION_PAGE_PATH. */
    public static final String REGISTRATION_PAGE_PATH = "REGISTRATION_PAGE_PATH";

    /** The Constant AUTHORIZATION_PAGE_PATH. */
    public static final String AUTHORIZATION_PAGE_PATH = "AUTHORIZATION_PAGE_PATH";

    /** The Constant PAGE_404_PATH. */
    public static final String PAGE_404_PATH = "PAGE_404_PATH";

    /** The Constant PAGE_500_PATH. */
    public static final String PAGE_500_PATH = "PAGE_500_PATH";

    /** The Constant DIALOG_PAGE_PATH. */
    public static final String DIALOG_PAGE_PATH = "DIALOG_PAGE_PATH";

    /** The Constant ADMIN_USER_PAGE. */
    public static final String ADMIN_USER_PAGE = "ADMIN_USER_PAGE";

    /** The Constant ADMIN_USER_MANAGER_PAGE. */
    public static final String ADMIN_USER_MANAGER_PAGE = "ADMIN_USER_MANAGER_PAGE";

    /** The Constant EMPLOYER_USER_PAGE. */
    public static final String EMPLOYER_USER_PAGE = "EMPLOYER_USER_PAGE";

    /** The Constant CREATE_VACANCY_PAGE. */
    public static final String CREATE_VACANCY_PAGE = "CREATE_VACANCY_PAGE";

    /** The Constant UPDATE_VACANCY_PAGE. */
    public static final String UPDATE_VACANCY_PAGE = "UPDATE_VACANCY_PAGE";

    /** The Constant SELECT_INTERVIEW_PAGE. */
    public static final String SELECT_INTERVIEW_PAGE = "SELECT_INTERVIEW_PAGE";

    /** The Constant SELECT_EMPLOYER_INTERVIEW_PAGE. */
    public static final String SELECT_EMPLOYER_INTERVIEW_PAGE = "SELECT_EMPLOYER_INTERVIEW_PAGE";

    /** The Constant CREATE_INTERVIEW_PAGE_PATH. */
    public static final String CREATE_INTERVIEW_PAGE_PATH = "CREATE_INTERVIEW_PAGE_PATH";

    /** The resource bundle. */
    private ResourceBundle resourceBundle;

    /** The dispatcher created. */
    private static AtomicBoolean dispatcherCreated = new AtomicBoolean(false);

    /** The lock instance. */
    private static Lock lockInstance = new ReentrantLock();


    /**
     * Gets the single instance of PageDispatcher.
     *
     * @return single instance of PageDispatcher
     */
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

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }

}
