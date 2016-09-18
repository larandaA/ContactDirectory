<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact info</title>
</head>
<body>
<h3 id="errorMessage"></h3>
<form method="post" onsubmit="return validateContactInfo();">
Photo: <br />
<input type="file" name="photo" id="uploadPhoto" accept="image/*"> <br />
<img id="photoPreview" src="${contact.photo.path}" /> <br />
<input type=hidden name="id" value="${contact.id}">
First name: <input type="text" value="${contact.firstName}" name="firstName" placeholder="First Name" autocomplete="off" required> <br />
Last name: <input type="text" value="${contact.lastName}" name="lastName" placeholder="Last Name" autocomplete="off" required> <br />
Patronymic: <input type="text" value="${contact.patronymic}" name="patronymic" placeholder="Patronymic" autocomplete="off"> <br />
Birthday: <input type="text" 
	<jstl:if test="${not empty contact.birthDate}">
	value="${dateFormat.format(contact.birthDate.getTime())}" 
	</jstl:if>
	name="birthDate" placeholder="DD.MM.YYYY" autocomplete="off"> <br />
Gender:
	<jstl:forEach items="${genders}" var="gend">
	<jstl:if test="${gend eq contact.gender }">
		<input type="radio" name="gender" id="r${gend.toString()}" value="${gend.toString()}" checked/><label for="r${gend.toString()}"> ${gend.toString()} </label>
	</jstl:if>
	<jstl:if test="${gend ne contact.gender }">
		<input type="radio" name="gender" id="r${gend.toString()}" value="${gend.toString()}" /><label for="r${gend.toString()}"> ${gend.toString()} </label>
	</jstl:if>	
	</jstl:forEach> 
	<br />
Citizenship: 
	<select name="citizenship">
			<option value=""> Choose country </option>
		<jstl:forEach items="${countries}" var="country">
		<jstl:if test="${country eq contact.citizenship }">
			<option selected value="${country}"> ${country} </option>
		</jstl:if>
		<jstl:if test="${country ne contact.citizenship }">
			<option value="${country}"> ${country} </option>
		</jstl:if>	
		</jstl:forEach>
	</select>
  <br />
Marital status: 
	<jstl:forEach items="${marital}" var="status">
	<jstl:if test="${status eq contact.maritalStatus }">
		<input type="radio" name="maritalStatus" id="r${status.toString()}" value="${status.toString()}" checked/><label for="r${status.toString()}"> ${status.toString()} </label>
	</jstl:if>
	<jstl:if test="${status ne contact.maritalStatus }">
		<input type="radio" name="maritalStatus" id="r${status.toString()}" value="${status.toString()}" /><label for="r${status.toString()}"> ${status.toString()} </label>
	</jstl:if>	
	</jstl:forEach> 
	<br />
Email: <input type="text" name="email" value="${contact.email}" placeholder="Email" autocomplete="off"> <br />
Web site: <input type="text" name="webSite" value="${contact.webSite}" placeholder="Web site" autocomplete="off"> <br />
Company: <input type="text" name="placeOfWork" value="${contact.placeOfWork}" placeholder="Company" autocomplete="off"> <br />
Address: <br />
Country: 
	<select name="country">
			<option value=""> Choose country </option>
		<jstl:forEach items="${countries}" var="country">
		<jstl:if test="${country eq contact.address.country }">
			<option selected value="${country}"> ${country} </option>
		</jstl:if>
		<jstl:if test="${country ne contact.address.country }">
			<option value="${country}"> ${country} </option>
		</jstl:if>	
		</jstl:forEach>
	</select>
 City: <input type="text" name="city" value="${contact.address.city}" placeholder="City" autocomplete="off">
 Local address: <input type="text" name="localAddress" value="${contact.address.localAddress}" placeholder="Local address" autocomplete="off">
 Index: <input type="text" name="index" value="${contact.address.index}" placeholder="Index" autocomplete="off"> <br />
Phone numbers: <br />
<table>
	<tr>
		<th>-</th>
		<th>Number</th>
		<th>Type</th>
		<th>Comment</th>
	</tr>
	<jstl:forEach items="${contact.phones}" var="phone">
	<tr>
		<td>-</a></td>
		<td>${phone.countryCode} (${phone.operatorCode}) ${phone.phoneNumber}</td>
		<td>${phone.type}</td>
		<td>${phone.comment}</td>
	</tr>
	</jstl:forEach>
</table>
Attachments: <br />
<table>
	<tr>
		<th>-</th>
		<th>Name</th>
		<th>Upload date</th>
		<th>Comment</th>
	</tr>
	<jstl:forEach items="${contact.attachments}" var="attachment">
	<tr>
		<td>-</a></td>
		<td>${attachment.name}</td>
		<td>${dateFormat.format(attachment.downloadDate.getTime())}</td>
		<td>${attachment.comment}</td>
	</tr>
	</jstl:forEach>
</table>
<button formaction="${action}">Save</button>
</form>
<script src="js/validation.js"></script>
<script src="js/contact_info_validate.js"></script>
<script src="js/image_preview.js"></script>
</body>
</html>