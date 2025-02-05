# notesapp

A simple notes app similar to google keep. Fully implemented using Servlets, JSP, JDBC, HTML/CSS and no other frameworks. more in README file

# Features

- User specific homepage
- Login/Logout functionality
- User registration
- Password reset
- Adding, Updating, Deleting notes
- Search functionality

# Skills

- Singleton pattern is used for dao class to make sure only one connection is used throughout application rather than keep requesting for connection and disconnection to the databse server.
- Session tracking is used to manage user specific requests for a session between login and logout
  Note: The focus of this project is to implement the servlets, jsp, jdbc concepts. So authentication of email is not implemented, currently accepts any email id without verifying

# Installation Guide

- make sure you have JDK and Tomcat installed. else you can find many youtube videos to install them. When installing tomcat just remember where you are installing and the port you are giving while installation.
- my versions: JDK:17, Tomcat:9.0.98
- base folder of tomcat is C:\Program Files\Apache Software Foundation\Tomcat 9.0
- download this project zip, extract and copy the project folder in webapps folder in Tomcat 9.0

# Usage

- Start the tomcat server
  go to ther bin folder, in my case C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\, in this double click on Tomcat9.exe file (not the Tomcat9w.exe)
- go to http://localhost/port/notesapp [in place of port, give the port number you have given while tomcat installation] this the homepage of our application.
- from there you can simply register, login, search notes, create note, update note, deletenote, logout etc
