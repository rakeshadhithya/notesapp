import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{

    UserDao userDao;

    public void init(ServletConfig config) throws ServletException{
        //make sure super class implementation is execcuted
        super.init(config);
        //get the dao from context. default return type is Object
        userDao = (UserDao) getServletContext().getAttribute("userDao");

    }
    

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //get the email and password from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        //check if email exists in database
        Boolean emailExists = userDao.findIfEmailExists(email);


        //if email do not exist in database
        if( !emailExists){
            //forward to jsp that handles invalid email page
            response.sendRedirect("./invalidemail.html");
        }
        //else if email exist in database
        else{
            //get the password for that email
            String passwordInDatabase = userDao.getPasswordByEmail(email);


            
            //check if passwords do not match
            if( ! passwordInDatabase.equals(password)){
                //forward to jsp that handle invalid password page
                response.sendRedirect("./invalidpassword.html");
            }
            //else if passwords match
            else{
                //start a new http session
                HttpSession session = request.getSession();
                //get the user details by email id from database
                String username = userDao.getUserNameByEmail(email);


                List<String> notesList = userDao.getNotesListByEmail(email);



                //save user credentials to the session
                session.setAttribute("username", username);
                session.setAttribute("email", email);
                session.setAttribute("notesList", notesList);
                //forward to jsp that takes to user page
                response.sendRedirect("./userpage.jsp");
            }
        }


    }
}