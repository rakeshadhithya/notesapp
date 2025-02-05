import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //get the session without creating
        HttpSession session = request.getSession(false);
        //if session is present invalidate it
        if(session!=null){
            session.invalidate();
        }
        //return the session expired page
        response.sendRedirect("./sessionexpired.html");
    }
    
}
