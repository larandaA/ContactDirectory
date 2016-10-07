<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact info</title>
	<link rel="stylesheet" href="css/wrap.css">
	<link rel="stylesheet" href="css/grid.css">
	<link rel="stylesheet" href="css/checkbox.css">
	<link rel="stylesheet" href="css/inputs.css">
	<link rel="stylesheet" href="css/popup.css">
</head>
<body>
	<header>
		<div class="header-title">
			<span class="app-title"><a href="ContactList">Contact directory</a></span>
			<span class="section-title">
					<span class="title-separator">-</span>
					Contact info
				</span>
		</div>
	</header>
	<div id="content">
		<div class="purple-block">
			<h1>Contact info</h1>
		</div>
		<div class="white-block">
			<form method="post" id="infoForm" enctype="multipart/form-data">
				<div class="cont-row">
					<div class="cont-cell-6">
						<div class="cont-row">
							<div class="cont-cell-1"></div>
							<div class="cont-cell-4">
								<div class="file-upload">
									<label>
										<input type="file" name="photo" id="uploadPhoto" accept="image/*">
										<span>Change photo</span>
									</label>
								</div>
							</div>
							<div class="cont-cell-7">
								<jstl:if test="${not empty contact.photo.path}">
									<jstl:url value="GetFile" var="photoHref">
										<jstl:param name="photoName" value="${contact.photo.path}"/>
									</jstl:url>
									<a href="${photoHref}" download=""><button type="button" class="btn">Download</button></a>
								</jstl:if>
								<button id="deletePhoto" type="button" class="btn">Delete</button>
								<input type="hidden" id="noPhoto" name="noPhoto" value="">
								<jstl:url value="GetFile" var="defPhotoHref">
									<jstl:param name="photoName" value="${defaultPhoto}"/>
								</jstl:url>
								<input type="hidden" id="defaultPhoto" value="${defPhotoHref}">
								<input type="hidden" id="deletePhotoWithPath" name="deletePhotoWithPath" value="" autocomplete="off">
							</div>
						</div>
						<div class="cont-row">
							<jstl:if test="${not empty contact.photo.path}">
								<jstl:url value="GetFile" var="photoHref">
									<jstl:param name="photoName" value="${contact.photo.path}"/>
								</jstl:url>
								<img id="photo-preview" src="${photoHref}" />
							</jstl:if>
							<jstl:if test="${empty contact.photo.path}">
								<jstl:url value="GetFile" var="photoHref">
									<jstl:param name="photoName" value="${defaultPhoto}"/>
								</jstl:url>
								<img id="photo-preview" src="${photoHref}" />
							</jstl:if>
						</div>
					</div>
					<div class="cont-cell-6">
						<div class="cont-row">
							<input type=hidden name="id" value="${contact.id}">
							<div class="igroup">
								<input type="text" value="${contact.firstName}" name="firstName" autocomplete="off" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>First name</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" value="${contact.lastName}" name="lastName" autocomplete="off" required>
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Last name</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" value="${contact.patronymic}" name="patronymic" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Patronymic</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text"
									<jstl:if test="${not empty contact.birthDate}">
									   value="${dateFormat.format(contact.birthDate.getTime())}"
									</jstl:if>
									   name="birthDate" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Birth date (dd.mm.yyyy)</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="email" value="${contact.email}" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Email</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="webSite" value="${contact.webSite}" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Web site</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="placeOfWork" value="${contact.placeOfWork}" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Company</label>
							</div>
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
							<jstl:if test="${gend eq contact.gender }">
								<input type="radio" name="gender" id="r${gend.toString()}" value="${gend.toString()}" checked/>
								<label for="r${gend.toString()}"> ${gend.toString()} </label>
							</jstl:if>
							<jstl:if test="${gend ne contact.gender }">
								<input type="radio" name="gender" id="r${gend.toString()}" value="${gend.toString()}" />
								<label for="r${gend.toString()}"> ${gend.toString()} </label>
							</jstl:if>
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
								<jstl:if test="${country eq contact.citizenship }">
									<option selected value="${country}"> ${country} </option>
								</jstl:if>
								<jstl:if test="${country ne contact.citizenship }">
									<option value="${country}"> ${country} </option>
								</jstl:if>
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
							<jstl:if test="${status eq contact.maritalStatus }">
								<input type="radio" name="maritalStatus" id="r${status.toString()}" value="${status.toString()}" checked/>
								<label for="r${status.toString()}"> ${status.toString()} </label>
							</jstl:if>
							<jstl:if test="${status ne contact.maritalStatus }">
								<input type="radio" name="maritalStatus" id="r${status.toString()}" value="${status.toString()}" />
								<label for="r${status.toString()}"> ${status.toString()} </label>
							</jstl:if>
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
									<jstl:if test="${country eq contact.address.country }">
										<option selected value="${country}"> ${country} </option>
									</jstl:if>
									<jstl:if test="${country ne contact.address.country }">
										<option value="${country}"> ${country} </option>
									</jstl:if>
								</jstl:forEach>
							</select>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="city" value="${contact.address.city}" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>City</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="localAddress" value="${contact.address.localAddress}" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Local address</label>
							</div>
						</div>
						<div class="cont-row">
							<div class="igroup">
								<input type="text" name="index" value="${contact.address.index}" autocomplete="off">
								<span class="highlight"></span>
								<span class="bar"></span>
								<label>Index</label>
							</div>
						</div>
					</div>
				</div>
				<div class="purple-small-block">
					<div class="cont-row">
						<div class="cont-cell-1"></div>
						<div class="cont-cell-3">
							<div class="field-title">Phones:</div>
						</div>
					</div>
				</div>
				<div class="white-small-block">
					<div class="cont-row">
						<div class="cont-cell-1"></div>
						<div class="cont-cell-2">
							<button type="button" id="deleteCheckedPhones" class="btn">Delete</button>
						</div>
						<div class="cont-cell-7"></div>
						<div class="cont-cell-1">
							<button type="button" id="createNewPhone" class="btn btn-create">+</button>
						</div>
						<div class="cont-cell-1"></div>
					</div>
				</div>
				<div class="cont-row">
					<table id="phoneTable">
						<tr>
							<th></th>
							<th>Phone number</th>
							<th>Type</th>
							<th>Comment</th>
							<th></th>
						</tr>
						<jstl:forEach items="${contact.phones}" var="phone">
							<tr>
								<td><input type="checkbox" name="phoneChecked"></td>
								<td><jstl:if test="${not empty phone.countryCode}">${phone.countryCode}</jstl:if> (<jstl:if test="${not empty phone.operatorCode}">${phone.operatorCode}</jstl:if>) ${phone.phoneNumber}</td>
								<td>${phone.type.toString()}</td>
								<td>${phone.comment}</td>
								<td>
									<input type="hidden" name="updatePhone" value="" autocomplete="off">
									<input type="hidden" name="phoneId" value="${phone.id}">
									<button type="button" class="btn list-btn editPhone">Edit</button><button type="button" class="btn list-btn deletePhone">Delete</button>
								</td>
							</tr>
						</jstl:forEach>
					</table>
					<div id="phoneChanges"></div>
				</div>
				<div class="purple-small-block">
					<div class="cont-row">
						<div class="cont-cell-1"></div>
						<div class="cont-cell-3">
							<div class="field-title">Attachments:</div>
						</div>
					</div>
				</div>
				<div class="white-small-block">
					<div class="cont-row">
						<div class="cont-cell-1"></div>
						<div class="cont-cell-2">
							<button type="button" id="deleteCheckedAtts" class="btn">Delete</button>
						</div>
						<div class="cont-cell-7"></div>
						<div class="cont-cell-1">
							<button type="button" id="createNewAtt" class="btn btn-create">+</button>
						</div>
						<div class="cont-cell-1"></div>
					</div>
				</div>
				<div class="cont-row">
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
								<jstl:url value="GetFile" var="downloadHref">
									<jstl:param name="fileName" value="${attachment.path}"/>
								</jstl:url>
								<td><input type="checkbox" name="attChecked"></td>
								<td>${attachment.name}</td>
								<td>${timeFormat.format(attachment.downloadDate.getTime())}</td>
								<td>${attachment.comment}</td>
								<td>
									<input type="hidden" name="updateAtt" value="" autocomplete="off">
									<input type="hidden" name="attId" value="${attachment.id}">
									<input type="hidden" name="attDownloadPath" value="${downloadHref}">
									<input type="hidden" name="attPath" value="${attachment.path}">
									<button type="button" class="btn list-btn editAtt">Edit</button><button type="button" class="btn list-btn deleteAtt">Delete</button>
								</td>
							</tr>
						</jstl:forEach>
					</table>
					<div id="attChanges"></div>
				</div>
				<div class="white-block">
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
							<button formaction="${action}" class="btn btn-start">Save</button>
						</div>
						<div class="cont-cell-5"></div>
					</div>
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
	<div class="modal-overlay" id="modalOverlay"></div>
	<div class="modal-window" id="phoneFormDiv">
		<div class="modal-header">
			<div class="modal-title">Phone info</div>
		</div>
		<form id="phoneForm">
			<div class="modal-row">
				<select name="countryCode">
					<option value=""> Choose country code </option>
					<jstl:forEach items="${codes}" var="code">
						<option value="${code}"> ${code} </option>
					</jstl:forEach>
				</select>
			</div>
			<div class="modal-row">
				<div class="igroup">
					<input type="text" name="operatorCode" value="" autocomplete="off">
					<span class="highlight"></span>
					<span class="bar"></span>
					<label>Operator code</label>
				</div>
			</div>
			<div class="modal-row">
				<div class="igroup">
					<input type="text" name="phoneNumber" value="" autocomplete="off" required>
					<span class="highlight"></span>
					<span class="bar"></span>
					<label>Phone number</label>
				</div>
			</div>
			<div class="modal-row">
				<jstl:forEach items="${types}" var="type">
					<input type="radio" name="phoneType" id="r${type.toString()}" value="${type.toString()}"/>
					<label for="r${type.toString()}"> ${type.toString()} </label>
				</jstl:forEach>
			</div>
			<div class="modal-row">
				<div class="igroup">
					<textarea cols="40" rows="3" name="phoneComment"></textarea>
					<span class="highlight"></span>
					<span class="bar"></span>
					<label>Comment</label>
				</div>
			</div>
			<div class="modal-row">
				<div class="cont-cell-12">
					<div class="error-message" id="phoneFormErrorMes"></div>
				</div>
			</div>
			<div class="modal-row">
				<div class="cont-cell-2"></div>
				<div class="cont-cell-10">
					<button type="button" id="cancelPhoneForm" class="btn">Cancel</button>
					<button type="button" id="savePhone" class="btn">Save</button>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-window" id="attFormDiv">
		<div class="modal-header">
			<div class="modal-title">Attachment info</div>
		</div>
		<form id="attForm">
			<div class="modal-row">
				<div class="igroup">
					<input type="text" name="attName" value="" autocomplete="off">
					<span class="highlight"></span>
					<span class="bar"></span>
					<label>Name</label>
				</div>
			</div>
			<div class="modal-row">
				<div class="cont-cell-2"></div>
				<div class="cont-cell-8">
					<a id="downloadAttFile" href="" download="">
						<button type="button" class="btn margin-auto-btn">Download</button>
					</a>
					<div class="file-upload" id="fileUploadDiv">
						<label id="fileUploadLabel">
							<span id="fileUploadSpan">Change file</span>
						</label>
					</div>
				</div>
				<div class="cont-cell-2"></div>
			</div>
			<div class="modal-row">
				<div class="igroup">
					<textarea cols="40" rows="5" name="attComment"></textarea>
					<span class="highlight"></span>
					<span class="bar"></span>
					<label>Comment</label>
				</div>
			</div>
			<div class="modal-row">
				<div class="cont-cell-12">
					<div class="error-message" id="attFormErrorMes"></div>
				</div>
			</div>
			<div class="modal-row">
				<div class="cont-cell-2"></div>
				<div class="cont-cell-10">
					<button type="button" id="cancelAttForm" class="btn">Cancel</button>
					<button type="button" id="saveAtt" class="btn">Save</button>
				</div>
			</div>
		</form>
	</div>
<script src="js/validation_functions.js"></script>
<script src="js/elem_create_functions.js"></script>
<script src="js/contact_info_validation.js"></script>
<script src="js/photo_manipulation.js"></script>
<script src="js/phone_manipul.js"></script>
<script src="js/att_manipul.js"></script>
</body>
</html>