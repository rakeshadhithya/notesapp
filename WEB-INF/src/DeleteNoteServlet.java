import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteNoteServlet extends HttpServlet {

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


        //get the email from session
        String email = (String) session.getAttribute("email");
        //get the note id from request
        String noteid = request.getParameter("noteid");
        //delete the note using service
        Integer rowsEffected = userDao.deleteNoteByEmailAndNotedId(email, noteid);


        //get the updated notes list for the email
        List<String> notesList = userDao.getNotesListByEmail(email);

        
        //add it to the session
        session.setAttribute("notesList", notesList);
        //return to userpage
        response.sendRedirect("./userpage.jsp");


    }
}
