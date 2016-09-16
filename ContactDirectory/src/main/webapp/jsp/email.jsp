<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Email</title>
</head>
<body>
<h3 id="errorMessage"></h3>
<form method="post" onsubmit="return validateEmailForm();">
	Emails: <br />
	<input type="text" value="${emails}" name="emails" autocomplete="off" placeholder="Email" required readonly> <br />
	Topic: <br />
	<input type="text" value="" name="topic" autocomplete="off" placeholder="Topic" required> <br />
	Template <br />
	Message: <br />
	<textarea cols="30" rows="10" required name="text" placeholder="Text"></textarea> <br />
	<button formaction="SendMail">Send</button>
</form>
<form>
	<button formaction="ContactList">Back</button>
</form>
<script src="js/validation.js"></script>
<script src="js/email_validate.js"></script>
</body>
</html>