import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateNoteServlet extends HttpServlet {

    UserDao userDao;

    public void init(ServletConfig config) throws ServletException{
        //make sure super class implementation is execcuted
        super.init(config);
        //get the dao from context. default return type is Object
        userDao = (UserDao) getServletContext().getAttribute("userDao");

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //get the session without creating a new one
        HttpSession session = request.getSession(false);
        //if no session found
        if(session==null){
            //return to page that handle session expired
            response.sendRedirect("./sessionexpired.html");
        }



        //get the email from session
        String email = (String) session.getAttribute("email");
        //get the note from request
        String note = request.getParameter("note");
        //post the note in database
        Integer rowsEffected = userDao.postNoteByEmailAndNote(email, note);


        //get the updated notes 
        List<String> notesList = userDao.getNotesListByEmail(email);


        //store it in the session
        session.setAttribute("notesList", notesList);
        //return the userpage
        response.sendRedirect("./userpage.jsp");


    }
}
