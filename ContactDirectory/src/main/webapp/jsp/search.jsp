<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search</title>
	<link rel="stylesheet" href="css/general.css">
	<link rel="stylesheet" href="css/grid.css">
	<link rel="stylesheet" href="css/inputs.css">
	<link rel="stylesheet" href="css/checkbox.css">
</head>
<body>
	<header>
		<div class="header-title">
			<span class="app-title"><a href="ContactList">Contact directory</a></span>
			<span class="section-title">
					<span class="title-separator">-</span>
					Searching
			</span>
		</div>
	</header>
	<div id="content">
		<div class="purple-block">
			<h1>Search</h1>
			<p class="home-subtitle">Search contacts with specified characteristics.
			</p>
		</div>
		<div class="white-block">
			<form method="post" onsubmit="return validateSearch();">
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<input type="text" value="" name="firstName" autocomplete="off">
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>First name</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<input type="text" value="" name="lastName" autocomplete="off">
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Last name</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<input type="text" value="" name="patronymic" autocomplete="off">
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Patronymic</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<input type="text" value="" name="birthDateBigger" autocomplete="off">
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Birth date lower bound (dd.mm.yyyy)</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-4"></div>
					<div class="cont-cell-8">
						<div class="igroup">
							<input type="text" value="" name="birthDateLess" autocomplete="off">
							<span class="highlight"></span>
							<span class="bar"></span>
							<label>Birth date upper bound (dd.mm.yyyy)</label>
						</div>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-1"></div>
					<div class="cont-cell-3">
						<div class="field-title">Gender:</div>
					</div>
					<div class="cont-cell-8">
						<jstl:forEach items="${genders}" var="gend">
							<input type="radio" name="gender" id="r${gend.toString()}" value="${gend.toString()}" />
							<label for="r${gend.toString()}"> ${gend.toString()} </label>
						</jstl:forEach>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-1"></div>
					<div class="cont-cell-3">
						<div class="field-title">Citizenship:</div>
					</div>
					<div class="cont-cell-3">
						<select name="citizenship">
							<option value=""> Choose country </option>
							<jstl:forEach items="${countries}" var="country">
								<option value="${country}"> ${country} </option>
							</jstl:forEach>
						</select>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-1"></div>
					<div class="cont-cell-3">
						<div class="field-title">Marital status:</div>
					</div>
					<div class="cont-cell-8">
						<jstl:forEach items="${marital}" var="status">
							<input type="radio" name="maritalStatus" id="r${status.toString()}" value="${status.toString()}" />
							<label for="r${status.toString()}"> ${status.toString()} </label>
						</jstl:forEach>
					</div>
				</div>
				<div class="cont-row">
					<div class="cont-cell-1"></div>
					<div class="cont-cell-3">
						<div class="field-title">Address:</div>
					</div>
					<div class="cont-cell-3">
						<div class="cont-row">
							<select name="country">
								<option value=""> Choose country </option>
								<jstl:forEach items="${countries}" var="country">
									<option value="${country}"> ${country} </option>
								</jstl:forEach>
							</select>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="city" value="" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>City</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="localAddress" value="" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Local address</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="index" value="" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Index</label>
							</div>
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
						<button formaction="SearchContacts" class="btn btn-start">Search</button>
					</div>
					<div class="cont-cell-5"></div>
				</div>
			</form>
		</div>
	</div>
	<footer>
		<div class="footer-info">
			by Alexandra Ryzhevich
		</div>
	</footer>
<script src="js/validation.js"></script>
<script src="js/search_validate.js"></script>
</body>
</html>