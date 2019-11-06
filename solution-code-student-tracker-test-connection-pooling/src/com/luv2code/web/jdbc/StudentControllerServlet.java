package com.luv2code.web.jdbc;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/StudentControllerServlet")
//@WebServlet("/StudentControllerServlet")

public class StudentControllerServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;
    private StudentDbUtil studentDbUtil;

    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {


            studentDbUtil=new StudentDbUtil(dataSource);
        }catch (Exception e){
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {

            String theCommand= request.getParameter("command");
            if (theCommand==null){
                theCommand="LIST";
            }
            switch (theCommand){
                case "LIST":
                    listStudents(request,response);
                    break;
                case "ADD":
                    addStudent(request,response);
                    listStudents(request,response);
                    break;
                case "LOAD":
                    loadStudent(request,response);
                    break;
                case "UPDATE":
                    updateStudent(request,response);
                    break;
                case "DELETE":
                    deleteStudent(request,response);
                    break;
                default:
                    listStudents(request,response);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String theStdudentId= request.getParameter("studentId");
        studentDbUtil.deleteStudent(theStdudentId);

        listStudents(request,response);

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id =Integer.parseInt(request.getParameter("studentId"));
        String  name =request.getParameter("name");
        String surname =request.getParameter("surname");
        String email =request.getParameter("email");
        Student theStudent= new Student(id,name,surname,email);

        studentDbUtil.updateStudent(theStudent);

        listStudents(request,response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String theStdudentId= request.getParameter("studentId");

        Student theStudent =studentDbUtil.getStudent(theStdudentId);
        request.setAttribute("THE_STUDENT",theStudent);
        RequestDispatcher dispatcher =request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request,response);
            }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name  = request.getParameter("name");
        String surname=request.getParameter("surname");
        String email=request.getParameter("email");
        Student theStudent=new Student(name,surname,email);

        studentDbUtil.addStudents(theStudent);


        listStudents(request,response);

    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Student>students=studentDbUtil.getStudents();

        request.setAttribute("STUDENT_LIST",students);

        RequestDispatcher dispatcher=request.getRequestDispatcher("/list-student.jsp");
        dispatcher.forward(request,response);

    }
}
