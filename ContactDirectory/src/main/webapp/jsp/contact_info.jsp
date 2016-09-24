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
<form method="post" onsubmit="return validateContactInfo();" enctype="multipart/form-data">
Photo: <br />
<input type="file" name="photo" id="uploadPhoto" accept="image/*">
<a href="${contact.photo.path}" download=""><button type="button">Download</button></a>
<button type="button" id="deletePhoto">Delete</button>
<input type="hidden" id="defaultPhoto" value="${defaultPhoto}">
<input type="hidden" id="deletePhotoWithPath" name="deletePhotoWithPath" value="" autocomplete="off">
	<br />
<jstl:if test="${not empty contact.photo.path}">
	<img id="photoPreview" src="${contact.photo.path}" />
</jstl:if>
<jstl:if test="${empty contact.photo.path}">
	<img id="photoPreview" src="${defaultPhoto}" />
</jstl:if>
 <br />
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
<button type="button" id="deleteCheckedPhones" >Delete</button>
<button type="button" id="createNewPhone" >Create</button>
<table id="phoneTable">
	<tr>
		<th></th>
		<th>Number</th>
		<th>Type</th>
		<th>Comment</th>
		<th></th>
	</tr>
	<jstl:forEach items="${contact.phones}" var="phone">
	<tr>
		<td><input type="checkbox" name="phoneChecked"></td>
		<td>${phone.countryCode} (${phone.operatorCode}) ${phone.phoneNumber}</td>
		<td>${phone.type.toString()}</td>
		<td>${phone.comment}</td>
		<td>
			<input type="hidden" name="updatePhone" value="" autocomplete="off">
			<input type="hidden" name="phoneId" value="${phone.id}">
			<button type="button" class="editPhone">Edit</button><button type="button" class="deletePhone">Delete</button>
		</td>
	</tr>
	</jstl:forEach>
</table>
<div id="phoneChanges"></div>
Attachments: <br />
<button type="button" id="deleteCheckedAtts" >Delete</button>
<button type="button" id="createNewAtt" >Create</button>
<table id="attTable">
	<tr>
		<th></th>
		<th>Name</th>
		<th>Upload date</th>
		<th>Comment</th>
		<th></th>
	</tr>
	<jstl:forEach items="${contact.attachments}" var="attachment">
	<tr>
		<td><input type="checkbox" name="attChecked"></td>
		<td>${attachment.name}</td>
		<td>${dateFormat.format(attachment.downloadDate.getTime())}</td>
		<td>${attachment.comment}</td>
		<td>
			<input type="hidden" name="updateAtt" value="" autocomplete="off">
			<input type="hidden" name="attId" value="${attachment.id}">
			<input type="hidden" name="attPath" value="${attachment.path}">
			<input type="file" name="attFile${attachment.id}">
			<button type="button" class="editAtt">Edit</button><button type="button" class="deleteAtt">Delete</button>
		</td>
	</tr>
	</jstl:forEach>
</table>
<div id="attChanges"></div>
<button id formaction="${action}">Save</button>
</form>
<div id="phoneFormDiv">
	<form id="phoneForm">
		<h4 id="phoneFormErrorMes"></h4>
		Country code: <br />
		<select name="countryCode">
			<option value=""> Choose country code </option>
			<jstl:forEach items="${codes}" var="code">
				<option value="${code}"> ${code} </option>
			</jstl:forEach>
		</select> <br />
		Operator code: <br />
		<input type="text" name="operatorCode" value="" autocomplete="off"> <br />
		Phone number: <br />
		<input type="text" name="phoneNumber" value="" autocomplete="off"> <br />
		Type:
		<jstl:forEach items="${types}" var="type">
			<input type="radio" name="phoneType" id="r${type.toString()}" value="${type.toString()}"/><label for="r${type.toString()}"> ${type.toString()} </label>
		</jstl:forEach> <br />
		Comment: <br />
		<textarea cols="30" rows="3" name="phoneComment"></textarea> <br />
		<button type="button" id="cancelPhoneForm">Cancel</button>
		<button type="button" id="savePhone">Save</button>
	</form>
</div>
<div id="attFormDiv">
	<form id="attForm">
		<h4 id="attFormErrorMes"></h4>
		Name: <br />
		<input type="text" name="attName" value="" autocomplete="off"> <br />
		<a id="downloadAttFile" href="" download=""><button type="button">Download</button> </a> <br />
		<button type="button" id="deleteAttFile">Delete</button> <br />
		Comment: <br />
		<textarea cols="30" rows="3" name="attComment"></textarea> <br />
		<button type="button" id="cancelAttForm">Cancel</button>
		<button type="button" id="saveAtt">Save</button>
	</form>
</div>
<script src="js/validation.js"></script>
<script src="js/contact_info_validate.js"></script>
<script src="js/photo_manipul.js"></script>
<script src="js/phone_manipul.js"></script>
<script src="js/att_man3.js"></script>
</body>
</html>