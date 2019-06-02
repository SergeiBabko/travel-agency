<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:if test="${formModel.tourStatus eq 'active'}">
	<sec:authorize access="isAnonymous()">
		<ul class="collapsible light roundbottomborder incard z-depth-0">
			<li>
				<div class="collapsible-header borderless gradient-45deg-light-blue-teal">
					<span class="valign-wrapper-center center-div pt16 hide-on-small-only ">
						<a class="black-text valign-wrapper-center" href="../login" style="text-align: center"><i class="material-icons" style="margin-right: 0.5vh">drafts</i><mytaglib:i18n key="booked.register" /></a>
					</span>
					<span class="valign-wrapper-center center-div pt14 hide-on-med-and-up">
						<a class="black-text valign-wrapper-center" href="../login" style="text-align: center"><i class="material-icons" style="margin-right: 0.5vh">drafts</i><mytaglib:i18n key="booked.register" /></a>
					</span>
				</div>
			</li>
		</ul>
	</sec:authorize>
	<sec:authorize access="hasRole('user')">
		<ul class="collapsible light roundbottomborder incard z-depth-0">
			<li>
				<div class="collapsible-header borderless gradient-45deg-light-blue-teal">
					<span class="valign-wrapper-center center-div pt16 hide-on-small-only "><i class="material-icons" style="margin-right: 0.5vh">drafts</i><mytaglib:i18n key="reserveTour" /></span>
					<span class="valign-wrapper-center center-div pt14 hide-on-med-and-up"><i class="material-icons" style="margin-right: 0.5vh">drafts</i><mytaglib:i18n key="reserveTour" /></span>
				</div>
				<div class="collapsible-body borderless">
					<div class="row" style="margin-bottom: 0">
					
						<div class="input-field col s12">
							<i class="material-icons prefix">calendar_today</i> 
							<select id="tourDates"></select> 
							<label for="tourDates"><mytaglib:i18n key="dates" /></label>
						</div>
						
						<div class="input-field col s12">
							<i class="material-icons prefix">wc</i> 
							<select id="personCount"></select> 
							<label for="personCount"><mytaglib:i18n key="peoplecount" /></label>
						</div>

						<form:form modelAttribute="userAccount">
							<div class="input-field col s12">
								<i class="material-icons prefix">perm_identity</i>
								<form:input id="firstName" path="firstName" type="text" />
								<label for="firstName"><mytaglib:i18n key="firstname" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">person</i>
								<form:input id="lastName" path="lastName" type="text" />
								<label for="lastName"><mytaglib:i18n key="lastname" /></label>
							</div>
						</form:form>

						<form:form modelAttribute="customerAccount">
							<div class="input-field col s12">
								<i class="material-icons prefix">people</i>
								<form:input id="middleName" path="middleName" type="text" />
								<label for="middleName"><mytaglib:i18n key="middlename" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">calendar_today</i>
								<form:input id="birthday" path="birthday" type="text" class="datepicker"/>
								<label for="birthday"><mytaglib:i18n key="birthday" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">filter_9_plus</i>
								<form:input id="passportNumber" path="passportNumber" type="text" />
								<label for="passportNumber"><mytaglib:i18n key="passportNumber" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">event_available</i>
								<form:input id="passportStart" path="passportStart" type="text" class="datepicker"/>
								<label for="passportStart"><mytaglib:i18n key="passportStart" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">event_busy</i>
								<form:input id="passportEnd" path="passportEnd" type="text" class="datepicker"/>
								<label for="passportEnd"><mytaglib:i18n key="passportEnd" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">smartphone</i>
								<form:input id="phoneNumber" path="phoneNumber" type="text" />
								<label for="phoneNumber"><mytaglib:i18n key="phone" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">public</i>
								<form:input id="country" path="country" type="text" />
								<label for="country"><mytaglib:i18n key="country" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">location_city</i>
								<form:input id="city" path="city" type="text" />
								<label for="city"><mytaglib:i18n key="city" /></label>
							</div>
							
							<div class="input-field col s12">
								<i class="material-icons prefix">location_on</i>
								<form:input id="street" path="street" type="text" />
								<label for="street"><mytaglib:i18n key="address" /></label>
							</div>
						</form:form>

						<div class="input-field col s12">
							<i class="material-icons prefix">insert_comment</i>
							<textarea id="message" class="materialize-textarea"></textarea>
							<label for="message"><mytaglib:i18n key="message" /></label>
						</div>
						
						<div class="input-field col s12 center-align">
							<button id="bookedTour" class="btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green">
								<mytaglib:i18n key="send" /><i class="material-icons right">send</i>
							</button>
						</div>
						
					</div>
				</div>
			</li>
		</ul>
		<script src="${pageContext.request.contextPath}/resources/js/init-combos.js"></script>
		<script>
			initTourDates('${baseUrl}', '${formModel.id}');
			bookedTour('${contextPath}', '${formModel.id}');
		</script>
	</sec:authorize>
</c:if>