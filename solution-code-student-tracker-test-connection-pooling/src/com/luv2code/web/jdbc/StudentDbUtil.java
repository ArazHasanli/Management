package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;

public class StudentDbUtil {

    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents() throws Exception {

        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            myConn = dataSource.getConnection();

            // create sql statement
            String sql = "select * from student_information order by surname";

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String firstName = myRs.getString("name");
                String lastName = myRs.getString("surname");
                String email = myRs.getString("email");

                // create new student object
                Student tempStudent = new Student(id, firstName, lastName, email);

                // add it to the list of students
                students.add(tempStudent);
            }

            return students;
        }
        finally {
            // close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();   // doesn't really close it ... just puts back in connection pool
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void addStudents(Student theStudent) throws Exception{
        Connection connection=null;
        PreparedStatement preparedStatement=null;


        try {
            connection=dataSource.getConnection();
            String sql="insert into student_information" +
                    "(id,name,surname,email)" +
                    "values(nextval(?),?,?,?)";
            preparedStatement=connection.prepareStatement(sql);




            preparedStatement.setString(1,"id_seq");
            preparedStatement.setString(2,theStudent.getFirstName());

            preparedStatement.setString(3,theStudent.getLastName());

            preparedStatement.setString(4,theStudent.getEmail());

            preparedStatement.execute();
        }finally {

            close(connection,preparedStatement,null);

        }
    }

public Student getStudent(String theStudentId) throws Exception {

    Student theStudent = null;

    Connection myConn = null;
    PreparedStatement myStmt = null;
    ResultSet myRs = null;
    int studentId;

    try {
        // convert student id to int
        studentId = Integer.parseInt(theStudentId);

        // get connection to database
        myConn = dataSource.getConnection();

        // create sql to get selected student
        String sql = "select * from student_information where id=?";

        // create prepared statement
        myStmt = myConn.prepareStatement(sql);

        // set params
        myStmt.setInt(1, studentId);

        // execute statement
        myRs = myStmt.executeQuery();

        // retrieve data from result set row
        if (myRs.next()) {
            String firstName = myRs.getString("name");
            String lastName = myRs.getString("surname");
            String email = myRs.getString("email");

            // use the studentId during construction
            theStudent = new Student(studentId, firstName, lastName, email);
        }
        else {
            throw new Exception("Could not find student id: " + studentId);
        }

        return theStudent;
    }
    finally {
        // clean up JDBC objects
        close(myConn, myStmt, myRs);
    }
}

    public void updateStudent(Student theStudent) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            myConn = dataSource.getConnection();

            // create SQL update statement
            String sql = "update student_information "
                    + "set name =?, surname=?, email=? "
                    + "where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());
            myStmt.setInt(4, theStudent.getId());

            // execute SQL statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public void deleteStudent(String theStudentId) throws Exception{
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try{
            int studentId= Integer.parseInt(theStudentId);
            myConn=dataSource.getConnection();
            String sql= "delete from student_information where id=?";

            myStmt=myConn.prepareStatement(sql);

            myStmt.setInt(1,studentId);

            myStmt.execute();
        }finally {

            close(myConn,myStmt,null);
        }
    }
}