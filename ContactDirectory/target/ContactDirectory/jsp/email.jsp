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

<form method="post">
	Emails: <br />
	<input type="text" value="${emails}" name="emails" autocomplete="off" placeholder="Email" required> <br />
	Topic: <br />
	<input type="text" value="" name="topic" autocomplete="off" placeholder="Topic"> <br />
	Template <br />
	Message: <br />
	<input type="text" value="" name="text" autocomplete="off" placeholder="Text" required> <br />
	<button formaction="SendMail">Send</button>
</form>
<form>
	<button formaction="ContactList">Back</button>
</form>

</body>
</html>