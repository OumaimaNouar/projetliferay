<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page session="true" %>
<portlet:defineObjects />
<portlet:actionURL var="submitFormURL" name="handleCustomer"/>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <div class="container">
<form:form  method="post" modelAttribute="customer" action="<%=submitFormURL.toString() %>" enctype="multipart/form-data" commandName="customer" style="height:450px; border: none;width: 100%;overflow:hidden;" >
  	<div class="form-group">
		<form:label path="firstName" style="color:#45316f;font-family:'flexothin',Verdana,sans-serif;">First Name</form:label>
		<form:input path="firstName" required="required" value="${portletSessionScope.firstname}" class="form-control" style="font-family:'flexothin',Verdana,sans-serif;border-color:#f07e26"/>
	</div>
	<div class="form-group">
		<form:label path="lastName" style="color:#45316f;font-family:'flexothin',Verdana,sans-serif;">Last Name</form:label>
		<form:input path="lastName" required="required" value="<%= portletSession.getAttribute("lastname") %>"class="form-control" style="font-family:'flexothin',Verdana,sans-serif;border-color:#f07e26"></form:input>
	</div>
	<div class="form-group">		
		<form:label path="email" style="color:#45316f;font-family:'flexothin',Verdana,sans-serif;">Email</form:label>
		<form:input path="email" type="email" required="required" value="<%= portletSession.getAttribute("emailadress") %>"class="form-control" style="font-family:'flexothin',Verdana,sans-serif;border-color:#f07e26"></form:input>
	</div>
	<div class="form-group">
		<form:label path="tel" style="color:#45316f;font-family:'flexothin',Verdana,sans-serif;">Tel</form:label>
		<form:input path="tel" pattern="[+][0-9]{11}" title="+33605xxxxxx" required="required" value="<%= portletSession.getAttribute("number") %>"class="form-control" style="font-family:'flexothin',Verdana,sans-serif;border-color:#f07e26"></form:input>
	</div>
			<!--  
			<tr>
				<td><form:label path="fileUpload">FileUpload</form:label></td>
				<td><form:input path="fileUpload" type="file" accept="application/pdf" name="fileUpload" required="required" ></form:input></td>

			</tr>
-->
	<div class="form-group">
	<button type="submit" class="btn btn-default btn-block" style="color:#45316f;font-family:'flexothin',Verdana,sans-serif;border-color:#f07e26">OK</button>
	</div>
	<div class="form-group">
	<a href="http://capfivm629.vlan42.capfigroup.domain.com:8080/user/<%= portletSession.getAttribute("screenname") %>/~/control_panel/manage?p_p_id=com_liferay_document_library_web_portlet_DLAdminPortlet&p_p_lifecycle=0&p_p_state=maximized" style="font-family:'flexothin',Verdana,sans-serif;color:#45316f"><u>Cliquez ici pour accéder à vos documents privés</u></a>
	</div>
</form:form>

</div>