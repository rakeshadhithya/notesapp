import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateNoteServlet extends HttpServlet {

    UserDao userDao;

    public void init(ServletConfig config) throws ServletException{
        //make sure super class implementation is execcuted
        super.init(config);
        //get the dao from context. default return type is Object
        userDao = (UserDao) getServletContext().getAttribute("userDao");

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //get the session without creating, if null return to session expired
        HttpSession session = request.getSession(false);
        if(session==null){
            response.sendRedirect("./sessionexpired.html");
        }


        
        //get the noteid from request
        String noteid = request.getParameter("noteid");
        //get the note from request
        String note = request.getParameter("note");
        //get the email from session
        String email = (String) session.getAttribute("email");
        //update the note in database
        Integer rowsEffected = userDao.updateNoteByEmailAndNoteIdAndNote(email, noteid, note);


        //get the updated notesList from the database
        List<String> notesList = userDao.getNotesListByEmail(email);


        //set it in the session
        session.setAttribute("notesList", notesList);
        //return the userpage
        response.sendRedirect("./userpage.jsp");
    }
    
}
