import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationServlet extends HttpServlet {

    UserDao userDao;

    public void init(ServletConfig config) throws ServletException{
        //make sure super class implementation is execcuted
        super.init(config);
        //get the dao from context. default return type is Object
        userDao = (UserDao) getServletContext().getAttribute("userDao");

    }

   
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //get user parameters from request
        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //check if email exists in database
        Boolean emailExists = userDao.findIfEmailExists(email);



        //if email exist in database
        if( emailExists ){
            //forward to jsp that handles email already exists page
            response.sendRedirect("./emailalreadyexists.html");
        }
        //else if email do not exist in database
        else{
            //add a user in the database
            Integer rowsEffected = userDao.addUser(email, name, password);


            //before sending redirect invalidate the existing session
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
            }
            //forwad to print registration success with a jsp
            response.sendRedirect("./registrationsuccess.html");
        }
    }
        
}

