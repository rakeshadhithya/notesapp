import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchByTextServlet extends HttpServlet {

    UserDao userDao;

    public void init(ServletConfig config) throws ServletException{
        //make sure super class implementation is execcuted
        super.init(config);
        //get the dao from context. default return type is Object
        userDao = (UserDao) getServletContext().getAttribute("userDao");

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //get the session without creating
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/sessionexpired.html");
        }

        //get the email from session
        String email = (String) session.getAttribute("email");
        //get attributes from request
        String text = (String) request.getParameter("text");

        //send data to dao and get the matched list
        List<String> notesMatched = userDao.searchByText(email, text);

        //save notes list to the session scope
        session.setAttribute("notesList", notesMatched);
        //redirect to the userpage
        response.sendRedirect("./userpage.jsp");
    }
    
}
