<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="i18n/resources/ApplicationResources"/>
<portlet:actionURL var="action"/>
This is the <b>SpringMVCLiferayPortlet</b> portlet.<br />
<c:out value="${myText}" /> <br/>
<c:out escapeXml="true" value="${releaseInfo}" />.
<body>
<h1>E-Signature</h1>
<div>
<fmt:message key="hello.message">
<fmt:param>${name }</fmt:param>
</fmt:message>
</div>