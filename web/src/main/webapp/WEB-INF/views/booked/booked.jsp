<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request" />

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"></link>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"></link>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
	
	<link rel="shortcut icon" type="image/png" href="${contextPath}/resources/img/logo-tittle.png" /></link>
	<link rel="stylesheet" href="${contextPath}/resources/css/custom.css"></link>
	
	<title><mytaglib:i18n key="booked" /> – ${booked.id} – ${booked.tourName}</title>

</head>

<body onload="window.print()">
	<page size="A4">
		
		<div class="row valign-wrapper center-align">
			<div class="col s12"><a class="brand-logo"><img height=3% src="${contextPath}/resources/img/logo-2.png" /></a></div>
		</div>
		
		<div class="row valign-wrapper roundborder" style="border: 1px; border-style: solid; border-color: #cccccc">
			<div class="col s7 center-align">
				<c:forEach var="description" items="${description}" varStatus="loopCounter">
					<pre><span class="inner-pre pt10 light">${description.description}</span></pre>
				</c:forEach>
			</div>
			<div class="col s5 right-align">
				<c:forEach var="description" items="${description}" varStatus="loopCounter">
					<pre><span class="inner-pre light" style="font-size: 9pt">${description.contacts}</span></pre>
					<pre><span class="inner-pre light" style="font-size: 9pt">${description.address}</span></pre>
				</c:forEach>
			</div>
		</div>

		<ul class="collapsible z-depth-0 roundborder" style="border: 1px; border-style: solid; border-color: #cccccc; margin-bottom: 20px">
			<li class="active">
				<div class="collapsible-header roundborder" style="border-bottom: 1px; border-style: solid; border-color: #cccccc; padding: 0">
					<div class="row valign-wrapper roundborder" style="width: 100%; margin: 0">
						<div class="col s3 left-align">
							<pre><span class="inner-pre" style="font-size: 14pt; margin-left: 1rem"><b><mytaglib:i18n key="booked" /> №${booked.id}</b></span></pre>
						</div>
						<div class="col s6 center-align">
							<pre><span class="inner-pre" style="font-size: 16pt"><b>${booked.tourName}</b></span></pre>
						</div>
						<div class="col s3 right-align">
							<pre><span class="inner-pre" style="font-size: 14pt; margin-right: 1rem"><b><fmt:formatDate pattern="dd.M.y" value="${booked.created}" /></b></span></pre>
						</div>
					</div>
				</div>
				<div class="collapsible-body" style="padding: 0.5rem">
					<div class="row" style="margin: 0">
						<div class="col s6 left-align">
							<pre class="center-align"><span style="font-size: 12pt; margin-left: 1rem"><b><mytaglib:i18n key="booked.description" />:</b></span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="country" />:</b> ${tour.countryName}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="city" />:</b> ${tour.cityName}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="address" />:</b> ${tour.address}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="type" />:</b> ${tour.tourTypeName}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="peoplecount" />:</b> ${booked.numPerson}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="price" />:</b> $${booked.price}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="nights" />:</b> ${booked.tourNights}</span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="startDate" />:</b> <fmt:formatDate pattern="dd.M.y" value="${booked.tourDateStart}" /></span></pre>
							<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="endDate" />:</b> <fmt:formatDate pattern="dd.M.y" value="${booked.tourDateEnd}" /></span></pre>
							<c:if test="${not empty booked.message}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="message" />:</b> <blockquote>${booked.message}</blockquote></span></pre>
							</c:if>
						</div>
						<div class="col s6 left-align">
							<pre class="center-align"><span class="inner-pre" style="font-size: 12pt; margin-left: 1rem"><b><mytaglib:i18n key="customerdata" />:</b></span></pre>
							<b>
								<c:if test="${not empty user.lastName && not empty user.firstName && not empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${user.lastName} ${user.firstName} ${customer.middleName}</span></pre>
								</c:if>
								<c:if test="${not empty user.lastName && not empty user.firstName && empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${user.lastName} ${user.firstName}</span></pre>
								</c:if>
								<c:if test="${not empty user.lastName && empty user.firstName && empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${user.lastName}</span></pre>
								</c:if>
								<c:if test="${empty user.lastName && not empty user.firstName && not empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${user.firstName} ${customer.middleName}</span></pre>
								</c:if>
								<c:if test="${empty user.lastName && not empty user.firstName && empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${user.firstName}</span></pre>
								</c:if>
								<c:if test="${not empty user.lastName && empty user.firstName && not empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${user.lastName} ${customer.middleName}</span></pre>
								</c:if>
								<c:if test="${empty user.lastName && empty user.firstName && not empty customer.middleName}">
									<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${customer.middleName}</span></pre>
								</c:if>
							</b>
							<c:if test="${not empty customer.birthday}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="birthday" />:</b> ${customer.birthday}</span></pre>
							</c:if>
							<c:if test="${not empty customer.passportNumber}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="passportNumber" />:</b> ${customer.passportNumber}</span></pre>
							</c:if>
							<c:if test="${not empty customer.passportStart}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="passportStart" />:</b> ${customer.passportStart}</span></pre>
							</c:if>
							<c:if test="${not empty customer.passportEnd}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="passportEnd" />:</b> ${customer.passportEnd}</span></pre>
							</c:if>
							<c:if test="${not empty customer.phoneNumber}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="phone" />:</b> ${customer.phoneNumber}</span></pre>
							</c:if>
							<c:if test="${not empty customer.country}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="country" />:</b> ${customer.country}</span></pre>
							</c:if>
							<c:if test="${not empty customer.city}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="city" />:</b> ${customer.city}</span></pre>
							</c:if>
							<c:if test="${not empty customer.street}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="address" />:</b> ${customer.street}</span></pre>
							</c:if>
							<c:if test="${not empty customer.bonus}">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem"><b><mytaglib:i18n key="booked.bonus" />:</b> ${customer.bonus}%</span></pre>
							</c:if>
						</div>
					</div>
				</div>
			</li>
		</ul>
	
		<ul class="collapsible z-depth-0 roundborder" style="border: 1px; border-style: solid; border-color: #cccccc; margin-bottom: 20px">
				<li class="active">
					<div class="collapsible-header roundborder" style="border-bottom: 1px; border-style: solid; border-color: #cccccc; padding: 0">
						<div class="row valign-wrapper roundborder" style="width: 100%; margin: 0">
							<div class="col s12 center-align">
								<pre><span class="inner-pre" style="font-size: 14pt; margin-left: 1rem"><b><mytaglib:i18n key="tour.description" /></b></span></pre>
							</div>
						</div>
					</div>
					<div class="collapsible-body" style="padding: 0.5rem">
						<div class="row" style="margin: 0">
							<div class="col s12 left-align">
								<pre><span class="inner-pre" style="font-size: 10pt; margin-left: 1rem">${tour.description}</span></pre>
							</div>
						</div>
					</div>
				</li>
			</ul>
			
	</page>
</body>

</html>

<script>
$(document).ready(function() {
    $('.collapsible').collapsible();
});
</script>

<style>
body {
	background: rgb(90, 90, 90) !important;
	font-weight: 300;
}

page {
	background: white;
	display: block;
	margin: 0 auto;
	margin-bottom: 0.25cm;
	margin-top: 0.25cm;
}

page[size="A4"] {
	width: 21cm;
	/* height: 29.7cm; */
	height: inherit;
}

pre {
	margin-top: 0.5rem;
	margin-bottom: 0.5rem;
	white-space: inherit;
}

blockquote {
	display: block;
	margin-block-start: 1em;
	margin-block-end: 1em;
	margin-inline-start: 40px;
	margin-inline-end: 40px;
}

h5 {
	font-size: 1.2rem;
	font-weight: 400;
	margin: 0.5rem 0 0.5rem 0;
}

h4 {
	font-size: 1.5rem;
	font-weight: 400;
	margin: 0.5rem 0 0.5rem 0;
}
</style>
