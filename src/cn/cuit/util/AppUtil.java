package cn.cuit.util;

import cn.cuit.bo.impl.CourseService;
import cn.cuit.bo.impl.TaskService;
import cn.cuit.bo.impl.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Esong on 2017/4/9.
 */
public class AppUtil {
    private static ApplicationContext context = null;
    public static ApplicationContext getAppContext() {
        if (context==null){
            context = new ClassPathXmlApplicationContext("spring/config/BeanLocation.xml");
        }
        return context;
    }
    public static UserService getUserService(){
        return (UserService)getAppContext().getBean("userServiceImpl");
    }
    public static CourseService getCourseService(){
        return (CourseService)getAppContext().getBean("courseService");
    }
    public static TaskService getTaskService(){
        return (TaskService)getAppContext().getBean("taskService");
    }
}
