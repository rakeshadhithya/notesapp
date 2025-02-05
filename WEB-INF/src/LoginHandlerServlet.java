import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //get the session without creating a new one
        HttpSession session = request.getSession(false);
        //if session present forward to userpage
        if(session != null){
            response.sendRedirect("./userpage.jsp");
        }
        //else forward to login page
        else{
            response.sendRedirect("./loginpage.html");
        }


    }
}
