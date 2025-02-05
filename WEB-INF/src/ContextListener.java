import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    UserDao userDao;

    public void contextInitialized(ServletContextEvent servletContextEvent){
        //get the user dao 
        try {
            userDao = UserDao.getUserDao();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //get the servlet context from event
        ServletContext context = servletContextEvent.getServletContext();
        //save the userdao object in context scope
        context.setAttribute("userDao", userDao);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent){
        //close the connection
        try {
            UserDao.closeConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
