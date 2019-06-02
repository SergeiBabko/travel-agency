<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<link rel="stylesheet" href="${contextPath}/resources/css/owl.css">

<script>
	$(document).ready(function() {
		$('.tabs').tabs({
			swipeable : true
		});
	});
</script>

<br />
<br />

<div class="row">
	
	<!-- START OWL BODY-->
	<div class="owl	">
		<div class="hand"></div>
		<div class="hand hand-r"></div>
		<div class="arms">
			<div class="arm"></div>
			<div class="arm arm-r"></div>
		</div>
	</div>
	<!-- END OWL BODY-->
	
	<!-- START LOGIN/REGISTER CARD BODY-->
	<div class="card hoverable lr roundborder">
		<ul class="tabs roundtopborder gradient-45deg-light-blue-cyan">
			<li class="tab col s6"><a class="white-text" style="font-size: 15pt" href="#signin"><mytaglib:i18n key="logreg.login" /></a></li>
			<li class="tab col s6"><a class="white-text" style="font-size: 15pt" href="#register"><mytaglib:i18n key="logreg.reg" /></a></li>
		</ul>
		<div class="card-content">
			<div class="tabs-content carousel carousel-slider logreg">
			<br/>

				<!-- START LOGIN FORM -->
				<div id="signin">
					<form name='loginForm' action="<c:url value='login' />" method='POST' style="margin-bottom: 0">
						<div class="row">

							<div class="input-field col s12">
								<i class="material-icons prefix">mail_outline</i> 
								<input id="email" class="validate" name="username" type="email"> 
								<label for="email" data-error="Wrong Email" data-success="right"><mytaglib:i18n key="email" /></label>
							</div>

							<div class="input-field col s12">
								<i class="material-icons prefix">lock_outline</i> 
								<input id="pass" type="password" name="password"> 
								<label for="pass"><mytaglib:i18n key="password" /></label>
							</div>

						</div>
						<div class="row">

							<div class="col s12 center-align">
								<button class="btn waves-effect waves-light gradient-45deg-cyan-light-green hoverable roundborder" type="submit"><mytaglib:i18n key="logreg.login" /><i class="material-icons left" style="font-size: 150%">person</i></button>
								<a class="btn waves-effect waves-light gradient-45deg-red-red hoverable roundborder" href="${baseUrl}"><mytaglib:i18n key="menu.home" /><i class="material-icons left" style="font-size: 150%">arrow_back</i></a>
							</div>
							<div class="col s12 center-align">
								<a class="waves-effect waves-orange btn-flat modal-trigger roundborder" style="margin-top: 5px; font-size: 12px; height: 20px; line-height: 20px;" href="#restore"><mytaglib:i18n key="logreg.restorebutton" /></a>
							</div>

						</div>
					</form>
				</div>
				<!-- END LOGIN FORM -->
				
				<!-- START REGISTER FORM -->
				<div id="register">
					<form:form name='registerForm' action="${contextPath}/register" method='POST' modelAttribute="formModel" style="margin-bottom: 0">
						<div class="row">

							<div class="input-field col s12">
								<i class="material-icons prefix">mail_outline</i>
								<form:input id="regemail" path="email" type="email" class="validate"  />
								<form:errors path="email" cssClass="red-text" />
								<label for="regemail" data-error="Wrong Email" data-success="right"><mytaglib:i18n key="email" /></label>
							</div>

							<div class="input-field col s12">
								<i class="material-icons prefix">lock_outline</i>
								<form:input id="regpass" path="password" type="password" class="validate"  />
								<form:errors path="password" cssClass="red-text" />
								<label for="regpass"><mytaglib:i18n key="password" /></label>
							</div>

						</div>

						<div class="row">

							<div class="col s12 center-align">
								<a class="btn waves-effect waves-light gradient-45deg-cyan-light-green hoverable roundborder" onclick="register('${contextPath}');"><mytaglib:i18n key="logreg.reg" /><i class="material-icons left" style="font-size: 150%">person</i></a>
								<a class="btn waves-effect waves-light gradient-45deg-red-red hoverable roundborder" href="${baseUrl}"><mytaglib:i18n key="menu.home" /><i class="material-icons left" style="font-size: 150%">arrow_back</i></a>
							</div>

						</div>
					</form:form>
				</div>
				<!-- END REGISTER FORM -->
				
			</div>
		</div>
	</div>
	<!-- END LOGIN/REGISTER CARD BODY-->

	<!-- START RESTORE FORM -->
	<div id="restore" class="modal roundborder">
		<div class="modal-content">
			<h4 class="center-align"><mytaglib:i18n key="logreg.restoreMessage" /></h4>
			<br/>
			<div class="row">
				
				<div class="input-field col s12">
					<i class="material-icons prefix">mail_outline</i>
					<input id="restoredEmail" type="email" class="validate" />
					<label for="restoredEmail" data-error="Wrong Email" data-success="right"><mytaglib:i18n key="email" /></label>
				</div>
				
			</div>

			<div class="row">

				<div class="col s12 center-align">
					<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="accountRestore('${contextPath}');"><mytaglib:i18n key="restore" /><i class="material-icons left" style="font-size: 150%">person</i></a>
					<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable"><mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i></a>
				</div>

			</div>
		</div>
	</div>
	<!-- START RESTORE FORM -->

</div>

<br />
<br />

<!-- START LOGIN/LOGOUT MESSAGE -->
<c:if test="${not empty error}">
	<script>
	swal({
		  title: "${error}",
		  timer: 3000,
		});
	</script>
</c:if>

<c:if test="${not empty message}">
	<script>
	swal({
		  title: "${message}",
		  icon: "success",
		  timer: 3000
		});
	</script>
</c:if>
<!-- END LOGIN/LOGOUT MESSAGE -->
