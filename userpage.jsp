<%@ page import="java.util.List" %>

<html>
  <head>
    <title>Notes App</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <nav>
      <h1>Notes App</h1>
      Username: <%= session.getAttribute("username") %> <br />
      Email: <%=session.getAttribute("email") %> <br />
      <ul>
        <li><a href="./createnotepage.html">create note</a></li>
        <li><a href="./updatenotepage.html">update note</a></li>
        <li><a href="./deletenotepage.html">delete note</a></li>
      </ul>
      <input type="button" value="logout" onclick="location.href='./logout'">
    </nav>

    <form action="./searchbytext">
      <input type="text" name="text" placeholder="Search notes...">
      <input type="submit" value="Search">
      <a style="font-size: small" href="./getallnotes">All Notes</a>
    </form>

    <div class="notecontainer">
      <%  List<String> notesList = (List<String>) session.getAttribute("notesList");
          if(notesList != null && notesList.size()!=0){
            for(int i=0; i< notesList.size(); i++){
      %>
              <div class="note-box">
                <%= i+". " + notesList.get(i) %>
              </div>
      <%
            }
          }
      %>
    </div>
  </body>
</html>
