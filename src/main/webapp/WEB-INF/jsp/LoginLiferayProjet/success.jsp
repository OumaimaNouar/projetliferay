<%@page import="javax.portlet.PortletSession"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:renderURL var="renderURL">
<portlet:param name="action" value="showForm"/>
</portlet:renderURL>
<portlet:defineObjects/>

<h1>Bienvenue
<%

PortletSession ps = renderRequest.getPortletSession();
String qString = (String)ps.getAttribute("userName");

%>
<%=qString %></h1><br>
 </h2>
<a href="<%=renderURL.toString()%>" style="text-align:center;">Revenir à la page d'accueil</a>