<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contacts</title>
    <link rel="stylesheet" type="text/css" href="css/table.css" />
    <link rel="stylesheet" type="text/css" href="css/butt.css" />
</head>
<body>
<h1>Contacts</h1>
<p> Page ${currentPage} </p>
<h3 id="errorMessage"></h3>
<form>
<button formaction="SearchEdit" class="search">Search</button>
<button formaction="CreateContact" class="create">Create</button>
</form>

<form method="post" id="contactListForm" onsubmit="return validateContactList();">
<button formaction="MailEdit" class="mail">Email</button>
<button formaction="DeleteContactList" class="delete">Delete</button>
</form>

<table class="cd-table">
	<tr>
		<th></th>
		<th>Name</th>
		<th>Birthday</th>
		<th>Address</th>
		<th>Company</th>
		<th></th>
	</tr>
	<jstl:forEach items="${contacts}" var="contact">
	<tr>
		<jstl:url value="EditContact" var="editUrl">
			<jstl:param name="id" value="${contact.id}"/>
		</jstl:url>
		<td><input type="checkbox" form="contactListForm" name="checked" value="${contact.id}"></td>
		<td><a href="${editUrl}">${contact.firstName} ${contact.lastName}</a></td>
		<td>
		<jstl:if test="${not empty contact.birthDate}">
		 ${dateFormat.format(contact.birthDate.getTime())}
		</jstl:if>
		</td>
		<td>${contact.address.country} ${contact.address.city} ${contact.address.localAddress} ${contact.address.index}</td>
		<td>${contact.placeOfWork}</td>
		<td>
			<form method="post" class="singleContactForm">
				<input type="hidden" name="id" value="${contact.id}">
				<button formaction="EditContact" class="edit">Edit</button>
				<button formaction="DeleteContact" class="delete">Delete</button>
			</form>
		</td>
	</tr>
	</jstl:forEach>
</table>
<form>
<input type="hidden" name="page" value="${previousPage}">
<button formaction="ContactList" 
<jstl:if test="${not availablePrevious}">
	disabled
</jstl:if>
>Previous</button>
</form>
<form>
<input type="hidden" name="page" value="${nextPage}">
<button formaction="ContactList" 
<jstl:if test="${not availableNext}">
	disabled
</jstl:if>
>Next</button>
</form>
<script src="js/contact_list_validate.js"></script>
</body>
</html>