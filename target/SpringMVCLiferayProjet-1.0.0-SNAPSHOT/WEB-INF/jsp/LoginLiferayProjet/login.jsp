<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@page import="javax.portlet.PortletSession" %>
<portlet:defineObjects />



<portlet:actionURL name="login" var="login">

<portlet:param name="action" value="loginSubmit"></portlet:param>

</portlet:actionURL>




<form action="${login}" method="POST">

Name: <input type="text" name = "username" required><br>

Password:<input type="password" name="password" required><br>

<input type="submit" value="SUBMIT">

</form>