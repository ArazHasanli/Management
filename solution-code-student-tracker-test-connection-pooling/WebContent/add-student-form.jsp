<%--
  Created by IntelliJ IDEA.
  User: Araz
  Date: 2019-11-04
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Student</title>
    <link type="text/css" rel="stylesheet" href="add-student-form.jsp">
</head>
<body>
<div id="container">
    <h3>Add Student</h3>
    <form action="StudentControllerServlet" method="get">
        <input type="hidden" name="command" value="ADD">
        <table>
            <tbody>
            <tr>
                <td> <label>First Name:</label> </td>
                <td><input type="text" name="name" /></td>
            </tr>
            <tr>
                <td> <label>Last Name:</label> </td>
                <td><input type="text" name="surname" /></td>
            </tr>
            <tr>
                <td> <label>email:</label> </td>
                <td><input type="text" name="email" /></td>
            </tr>
            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save" /></td>
            </tr>
            </tbody>
        </table>
    </form>

    <div style="clear: both;"></div>
    <p>
        <a href="StudentControllerServlet">Back to List</a>
    </p>
</div>
</body>
</html>
