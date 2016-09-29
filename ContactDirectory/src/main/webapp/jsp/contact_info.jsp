<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact info</title>
	<link rel="stylesheet" href="css/general.css">
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
			<form method="post" onsubmit="return validateContactInfo();" enctype="multipart/form-data">
				<div class="cont-row">
					<div class="cont-cell-6">
						<div class="cont-row">
							<div class="cont-cell-5">
								<div class="file-upload">
									<label>
										<input type="file" name="photo" id="uploadPhoto" accept="image/*">
										<span>Change photo</span>
									</label>
								</div>
							</div>
							<div class="cont-cell-7">
								<a href="${contact.photo.path}" download=""><button type="button" class="btn">Download</button></a>
								<button id="deletePhoto" type="button" class="btn">Delete</button>
								<input type="hidden" id="defaultPhoto" value="${defaultPhoto}">
								<input type="hidden" id="deletePhotoWithPath" name="deletePhotoWithPath" value="" autocomplete="off">
							</div>
						</div>
						<div class="cont-row">
							<jstl:if test="${not empty contact.photo.path}">
								<img id="photoPreview photo-preview" src="${contact.photo.path}" />
							</jstl:if>
							<jstl:if test="${empty contact.photo.path}">
								<img id="photoPreview photo-preview" src="${defaultPhoto}" />
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
								<td>${phone.countryCode} (${phone.operatorCode}) ${phone.phoneNumber}</td>
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
								<td><input type="checkbox" name="attChecked"></td>
								<td>${attachment.name}</td>
								<td>${dateFormat.format(attachment.downloadDate.getTime())}</td>
								<td>${attachment.comment}</td>
								<td>
									<input type="hidden" name="updateAtt" value="" autocomplete="off">
									<input type="hidden" name="attId" value="${attachment.id}">
									<input type="hidden" name="attPath" value="${attachment.path}">
									<input type="file" name="attFile${attachment.id}" style="display:none;">
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
	<footer>
		<div class="footer-info">
			by Alexandra Ryzhevich
		</div>
	</footer>
	<div class="modal-overlay"></div>
	<div class="modal-window" id="phones">
		<div class="modal-header">
			<div class="modal-title">Phone info</div>
		</div>
		<div class="modal-row">
			<select>
				<option>Choose country code</option>
				<option>375</option>
				<option>44</option>
				<option>7</option>
			</select>
		</div>
		<div class="modal-row">
			<div class="igroup">
				<input type="text">
				<span class="highlight"></span>
				<span class="bar"></span>
				<label>Operator code</label>
			</div>
		</div>
		<div class="modal-row">
			<div class="igroup">
				<input type="text" required>
				<span class="highlight"></span>
				<span class="bar"></span>
				<label>Phone number</label>
			</div>
		</div>
		<div class="modal-row">
			<input name="type" type="radio" id="rhome" />
			<label for="rhome">Home</label>

			<input name="type" type="radio" id="rwork" />
			<label for="rwork">Work</label>

			<input name="type" type="radio" id="rmobile" />
			<label for="rmobile">Mobile</label>
		</div>
		<div class="modal-row">
			<div class="igroup">
				<textarea cols="40" rows="5"></textarea>
				<span class="highlight"></span>
				<span class="bar"></span>
				<label>Comment</label>
			</div>
		</div>
		<div class="modal-row">
			<div class="cont-cell-2"></div>
			<div class="cont-cell-10">
				<button type="button" class="btn">Cancel</button>
				<button type="button" class="btn">Save</button>
			</div>
		</div>
	</div>
	<div class="modal-window" id="atts">
		<div class="modal-header">
			<div class="modal-title">Attachment info</div>
		</div>
		<div class="modal-row">
			<div class="igroup">
				<input type="text">
				<span class="highlight"></span>
				<span class="bar"></span>
				<label>Name</label>
			</div>
		</div>
		<div class="modal-row">
			<div class="file-upload">
				<label>
					<input type="file" name="file">
					<span>Change file</span>
				</label>
			</div>
			<div>filename.txt</div>
		</div>

		<div class="modal-row">
			<div class="igroup">
				<textarea cols="40" rows="5"></textarea>
				<span class="highlight"></span>
				<span class="bar"></span>
				<label>Comment</label>
			</div>
		</div>
		<div class="modal-row">
			<div class="cont-cell-2"></div>
			<div class="cont-cell-10">
				<button type="button" class="btn">Cancel</button>
				<button type="button" class="btn">Save</button>
			</div>
		</div>
	</div>



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
<script src="js/att_man4.js"></script>
</body>
</html>