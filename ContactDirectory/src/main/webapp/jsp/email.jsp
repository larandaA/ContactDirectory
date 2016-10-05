<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Email</title>
	<link rel="stylesheet" href="css/wrap.css">
	<link rel="stylesheet" href="css/grid.css">
	<link rel="stylesheet" href="css/inputs.css">
</head>
<body>
	<header>
		<div class="header-title">
			<span class="app-title"><a href="ContactList">Contact directory</a></span>
			<span class="section-title">
					<span class="title-separator">-</span>
					Email sending
				</span>
		</div>
	</header>
	<div id="content">
		<div class="purple-block">
			<h1>Email sending</h1>
			<p class="home-subtitle">Send emails to your contacts.
			</p>
		</div>
		<div class="white-block">
			<form method="post" id="emailForm">
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<textarea name="emails" autocomplete="off" cols="40" rows="3" readonly> ${emails} </textarea>
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Emails</label>
						</div>
						<jstl:forEach var="id" items="${ids}">
							<input type="hidden" name="id" value="${id}">
						</jstl:forEach>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<input type="text" value="" name="topic" autocomplete="off" required>
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Subject</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-3">
						<select name="template">
							<option value="" selected>Choose template</option>
							<jstl:forEach var="entry" items="${templates}">
								<option value="${entry.key}">${entry.key}</option>
							</jstl:forEach>
						</select>
						<jstl:forEach var="entry" items="${templates}">
							<input type="hidden" name="template${entry.key}" value="${entry.value}">
						</jstl:forEach>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<textarea cols="40" rows="5" id="templateText" readonly></textarea>
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Template text</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<textarea cols="40" rows="10" name="text"></textarea>
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Your message</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-3"></div>
					<div class="cont-cell-6">
						<div id="error-message"></div>
					</div>
					<div class="cont-cell-3"></div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-5"></div>
					<div class="cont-cell-2">
						<button formaction="SendMail" class="btn btn-start">Send</button>
					</div>
					<div class="cont-cell-5"></div>
				</div>
			</form>
		</div>
	</div>
	<div id="pre-footer"></div>
	<footer>
		<div class="footer-info">
			by Alexandra Ryzhevich
		</div>
	</footer>
<script src="js/validation_functions.js"></script>
<script src="js/email_form_validation.js"></script>
	<script src="js/template_email.js"></script>
</body>
</html>