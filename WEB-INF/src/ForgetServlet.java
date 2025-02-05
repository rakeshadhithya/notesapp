import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgetServlet extends HttpServlet{

    UserDao userDao;

    public void init(ServletConfig config) throws ServletException{
        //make sure super class implementation is execcuted
        super.init(config);
        //get the dao from context. default return type is Object
        userDao = (UserDao) getServletContext().getAttribute("userDao");

    }

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //get email and password from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");



        //check if email exists or not
        Boolean emailExists = userDao.findIfEmailExists(email);

        
        //if email do not exist
        if( !emailExists ){
            //redirect to page that says email do not exist
            response.sendRedirect("./emaildonotexist.html");
        }
        //else if email exist
        else{
            //update the password in database
            Integer rowsEffected = userDao.updatePasswordByEmail(email, password);


            //return to page to login
            response.sendRedirect("./passwordresetsuccess.html");
        }
    }
}
