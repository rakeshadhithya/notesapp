import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//NOTE:
//create table in notesappdb: 
//create table users(email varchar(50), name varchar(50), password varchar(50), notes_list json)


public class UserDao {
    //singleton pattern
    private static UserDao userDao;
    private static Connection connection;
    //initialising the connection while creation of object itself
    private UserDao() throws ClassNotFoundException, SQLException{
        if(connection == null){
            //protocol and address of database 
            String url = "jdbc:mysql://localhost:3306/notesappdb";
            //load driver class from a location
            Class.forName("com.mysql.cj.jdbc.Driver");
            //get the useless connection: with drivermanager. usefull connection: with datasource
            connection = DriverManager.getConnection(url, "root", "root");
        }
        //statement and result set is different for every method and must be closed in that method only
    }
    public static UserDao getUserDao() throws ClassNotFoundException, SQLException{
        if(userDao==null){
            userDao = new UserDao();
        }
        return userDao;
    }


    //to be used in session close & contextDestroyed
    public static void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
        }
    }

    //to be used in catch block
    private static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        if(preparedStatement != null){
            preparedStatement.close();
        }
    }

    //to be used in catch block
    private static void closeResultSet(ResultSet resultSet) throws SQLException{
        if(resultSet != null){
            resultSet.close();
        }
    }





/*
 statement, null answer-connection-preparedstate-resultset, 
 one try(connection, preparestatement & bind, submit&process)-catch-finally

 */


    //working e.g.: insert into users values('rak@gmail.com', 'rakesh', 'rak123', '["hi", "hello"]')
    public Integer addUser(String email, String name, String password) {
        String statement = "insert into users values( ?, ?, ?, ?)";
        int rowsEffected = -1;
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            //bind empty list for 4th parameter
            preparedStatement.setString(4, "[]");
            //submit the query & process result
            rowsEffected = preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            //close the prepared statement
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsEffected;
    }
    






    //working e.g.: select 1 from users where email='rak@gmail.com'
    public Boolean findIfEmailExists(String email){
        String statement = "select 1 from users where email=?";
        Boolean booleanAnswer = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            //submit the query & process result
            resultSet = preparedStatement.executeQuery();
            //move from no data area to data area in the resultset
            if(resultSet.next()){
                int returnValue = resultSet.getInt(1);
                if(returnValue==1){
                    booleanAnswer = true;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return booleanAnswer;

    }






    //working e.g.: select name from users where email='rak@gmail.com'
    public String getUserNameByEmail(String email){
        String statement = "select name from users where email=?";
        String name = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            //submit the query & process result
            resultSet = preparedStatement.executeQuery();
            //move from no data area to data area in the resultset
            if(resultSet.next()){
                name = resultSet.getString(1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return name;

    }






    //working e.g.: select password from users where email='rak@gmail.com'
    public String getPasswordByEmail(String email){
        String statement = "select password from users where email=?";

        String password = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            //submit the query & process result
            resultSet = preparedStatement.executeQuery();
            //move from no data area to data area in the resultset
            if(resultSet.next()){
                password = resultSet.getString(1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return password;

    }




    //working(as string): select notes_list from users where email='rak@gmail.com'
    public List<String> getNotesListByEmail(String email){
        String statement = "select notes_list from users where email=?";
        String notesListAsString = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            //submit the query & process result
            resultSet = preparedStatement.executeQuery();
            //move from no data area to data area in the resultset
            if(resultSet.next()){
                notesListAsString = resultSet.getString(1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //parse notesListAsString to ArrayListOfString
        // this is the string version e.g. "["hi", "hello", "hibro"]"
        String notesListAsStringUpdated = notesListAsString.replace("\"", "")
                                                            .replace("[","")
                                                            .replace("]","");

        //split the string at ',' now
        List<String> notesList = Arrays.asList(notesListAsStringUpdated.split(","));

        return notesList;

    }



    //working e.g.: update users set password='raki123' where email='rak@gmail.com';
    public Integer updatePasswordByEmail(String email, String password) {
        String statement = "update users set password=? where email=?";
        int rowsEffected = -1;
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            //submit the query & process result
            rowsEffected = preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsEffected;

        
    }






    //working e.g.: update users set notes_list=json_array_append(notes_list,'$','hibro')
    public Integer postNoteByEmailAndNote(String email, String note) {
        String statement =  "update users set notes_list=json_array_append(notes_list, '$', ?) where email=?";
        int rowsEffected = -1;
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, email);
            //submit the query & process result
            rowsEffected = preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsEffected;

    }





    //working e.g.: update users set notes_list=json_remove(notes_list, '$[3]');
    public Integer deleteNoteByEmailAndNotedId(String email, String noteid) {
        String statement = "update users set notes_list=json_remove(notes_list, '$["+noteid+"]') where email=?";
        int rowsEffected = -1;
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            //submit the query & process result
            rowsEffected = preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsEffected;

    }






    //working e.g.: update users set notes_list = json_set(notes_list, '$[3]', 'adhithya')
    public Integer updateNoteByEmailAndNoteIdAndNote(String email, String noteid, String note) {
        String statement = "update users set notes_list = json_set(notes_list, '$["+noteid+"]', ?) where email=?";
        int rowsEffected = -1;
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, email);
            //submit the query & process result
            rowsEffected = preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsEffected;

    }


    //search feature
    //working e.g.: select note from users, json_table(notes_list, '$[*]' columns (note varchar(255) path '$')) 
    //               as notes where note like '%to%' and email='rak@gmail.com';
    public List<String> searchByText(String email, String text) {
        String statement = "select note from users, json_table(notes_list, '$[*]' columns (note varchar(255) path '$'))" +
                            "as notes where note like '%"+ text + "%' and email=?";
        List<String> matchedList = new ArrayList<String>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(statement);
            //bind parameters
            preparedStatement.setString(1, email);
            //submit the query & process result
            resultSet = preparedStatement.executeQuery();
            //while result set has next elements
            while(resultSet.next()){
                matchedList.add(resultSet.getString(1));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return matchedList;
        
    }


}
