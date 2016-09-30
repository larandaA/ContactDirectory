<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contacts</title>
	<link rel="stylesheet" href="css/wrap.css">
	<link rel="stylesheet" href="css/grid.css">
	<link rel="stylesheet" href="css/checkbox.css">
</head>
<body>
	<header>
		<div class="header-title">
			<span class="app-title"><a href="StartPage">Contact directory</a></span>
			<span class="section-title">
					<span class="title-separator">-</span>
					Contact list
			</span>
		</div>
	</header>
	<div id="content">
		<div class="purple-block">
			<h1>Contact list</h1>
			<p class="home-subtitle">Look through your contacts.
				<br /> Edit existing, create new, delete old, search needed.
			</p>
		</div>
		<div class="white-small-block">
			<div class="cont-row">
				<div class="cont-cell-3"></div>
				<div class="cont-cell-6">
					<div id="error-message">${errorMessage}</div>
				</div>
				<div class="cont-cell-3"></div>
			</div>
			<div class="cont-row">
				<div class="cont-cell-1"></div>
				<div class="cont-cell-5">
					<form method="post" id="contactListForm">
						<button formaction="MailEdit" class="btn">Email</button>
						<button formaction="DeleteContactList" class="btn">Delete</button>
					</form>
					<form><button formaction="SearchEdit" class="btn">Search</button></form>
				</div>
				<div class="cont-cell-4"></div>
				<div class="cont-cell-1">
					<form><button formaction="CreateContact" class="btn btn-create">+</button></form>
				</div>
				<div class="cont-cell-1"></div>
			</div>
		</div>
		<div class="cont-row">
			<table>
				<tr>
					<th></th>
					<th>Name</th>
					<th>Birth date</th>
					<th>Address</th>
					<th>Company</th>
					<th></th>
				</tr>
				<jstl:forEach items="${contacts}" var="contact">
					<tr>
						<jstl:url value="EditContact" var="editUrl">
							<jstl:param name="id" value="${contact.id}"/>
						</jstl:url>
						<td>
							<input type="checkbox" form="contactListForm" name="checked" value="${contact.id}">
						</td>
						<td>
							<a href="${editUrl}">${contact.firstName} ${contact.lastName}</a>
						</td>
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
								<button formaction="EditContact" class="btn list-btn">Edit</button>
								<button formaction="DeleteContact" class="btn list-btn">Delete</button>
							</form>
						</td>
					</tr>
				</jstl:forEach>
			</table>
		</div>
		<div class="purple-small-block">
			<div class="cont-row">
				<div class="cont-cell-4"></div>
				<div class="cont-cell-4">
					<div class="cont-cell-4">
						<form>
							<input type="hidden" name="page" value="${previousPage}">
							<button class="btn" formaction="ContactList"
									<jstl:if test="${not availablePrevious}">
										disabled
									</jstl:if>
							>Prev</button>
						</form>
					</div>
					<div class="cont-cell-4">
						${currentPage} of ${pageAmount}
					</div>
					<div class="cont-cell-4">
						<form>
							<input type="hidden" name="page" value="${nextPage}">
							<button class="btn" formaction="ContactList"
									<jstl:if test="${not availableNext}">
										disabled
									</jstl:if>
							>Next</button>
						</form>
					</div>
				</div>
				<div class="cont-cell-4"></div>
			</div>
		</div>
	</div>
	<footer>
		<div class="footer-info">
			by Alexandra Ryzhevich
		</div>
	</footer>
<script src="js/validate_contact_list.js"></script>
</body>
</html>