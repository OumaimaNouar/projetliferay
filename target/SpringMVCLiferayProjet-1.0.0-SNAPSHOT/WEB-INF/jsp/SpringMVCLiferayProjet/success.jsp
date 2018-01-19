<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n/resources/ApplicationResources"/>
<portlet:defineObjects />
<portlet:renderURL var="renderURL">
	<portlet:param name="act" value="showForm2"/>
</portlet:renderURL>
<a href="<%=renderURL.toString()%>" style="text-align:center;font-family:'flexothin',Verdana,sans-serif;color:#45316f;"><u>Revenir au formulaire</u></a>
<iframe src="https://demo.yousign.fr/public/cosignature/${successModel.token}" style="height:450px; border: none;width: 100%;overflow:hidden;"></iframe>
	