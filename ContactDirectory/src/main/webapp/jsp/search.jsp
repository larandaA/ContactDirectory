<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search</title>
</head>
<body>
<h1>Search</h1>
<form>
First name: <input type="text" value="" name="firstName" placeholder="First Name" autocomplete="off" required> <br />
Last name: <input type="text" value="" name="lastName" placeholder="Last Name" autocomplete="off" required> <br />
Patronymic: <input type="text" value="" name="patronymic" placeholder="Patronymic" autocomplete="off"> <br />
Birthday bigger: <input type="date" value="" name="birthDateBigger" placeholder="YYYY-MM-DD" autocomplete="off"> 
less <input type="date" value="" name="birthDateLess" placeholder="YYYY-MM-DD" autocomplete="off"> <br />
Gender:
	<jstl:forEach items="${genders}" var="gend">
		<input type="radio" name="gender" id="r${gend.toString()}" value="${gend.toString()}" /><label for="r${gend.toString()}"> ${gend.toString()} </label>	
	</jstl:forEach> 
	<br />
Citizenship: 
	<select name="citizenship">
			<option value=""> Choose country </option>
		<jstl:forEach items="${countries}" var="country">
			<option value="${country}"> ${country} </option>	
		</jstl:forEach>
	</select>
  <br />
Marital status: 
	<jstl:forEach items="${marital}" var="status">
		<input type="radio" name="maritalStatus" id="r${status.toString()}" value="${status.toString()}" /><label for="r${status.toString()}"> ${status.toString()} </label>
	</jstl:forEach> 
	<br />
Address: <br />
Country: 
	<select name="country">
			<option value=""> Choose country </option>
		<jstl:forEach items="${countries}" var="country">
			<option value="${country}"> ${country} </option>
		</jstl:forEach>
	</select>
 City: <input type="text" name="city" value="" placeholder="City" autocomplete="off">
 Local address: <input type="text" name="localAddress" value="" placeholder="Local address" autocomplete="off">
 Index: <input type="text" name="index" value="" placeholder="Index" autocomplete="off"> <br />
<button formaction="SearchContacts">Search</button>
</form>

</body>
</html>