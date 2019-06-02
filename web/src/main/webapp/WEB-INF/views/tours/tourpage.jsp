<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<link rel="stylesheet" href="${contextPath}/resources/css/pignose.calendar.min.css"></link>
<script src="${contextPath}/resources/js/pignose.calendar.full.min.js"></script>

<c:set var="baseUrl" value="${contextPath}/tours" scope="request"/>

<br />
<br />

<div class="row">
	
	<div class="col s12 l7">
	
		<!-- START TOUR IMAGE -->
		<div class="card hoverable roundborder" style="background-color: #26c6daf0">
			<div class="card-image" style="padding: 1vh">
				<img class="materialboxed" src="${formModel.image}" style="border-radius: 1.2vh"> 
				<c:if test="${formModel.isNew == true}">
					<span class="card-title" style="z-index: 999; padding: 0 24px; top: 24px">
						<span class="new badge red" data-badge-caption="NEW"></span>
					</span>
				</c:if>
			</div>
			<div class="card-content roundbottomborder" style="padding: 1vh 2vh 2vh 2vh">
				<span class="card-title center roundbottomborder" style="margin-bottom: 0">
					<span class="white-text light">${formModel.name}</span>
				</span>
			</div>
		</div>
		<!-- END TOUR IMAGE -->
		
		<!-- START TOUR DESCRIPTION -->
		<div class="hide-on-med-and-down">
			<jspFragments:tourDescription />
		</div>
		<!-- END TOUR DESCRIPTION -->
		
	</div>
	
	<div class="col s12 l5">
		
		<!-- START TOUR OPTIONS -->
		<div class="card hoverable roundborder" style="overflow: hidden">
			<div class="card-content light flexCenterOnPage tour-${formModel.id}">
			
				<div class="hide-on-small-only">
					<div class="pt20 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">public</i><mytaglib:i18n key="country" />: ${formModel.countryName}</div>
					<div class="pt20 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">location_city</i><mytaglib:i18n key="city" />: ${formModel.cityName}</div>
					<br/>
					<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">local_atm</i>
						<c:if test="${customerBonus == 0}">
							${formModel.price}
						</c:if>
						<c:if test="${customerBonus > 0}">
							<mytaglib:i18n key="price" />:&nbsp;
							<strike><span style="color: #ff5722">${formModel.price}</span></strike>
							&nbsp;
							<span style="color: #4caf50">${formModel.price - formModel.price*customerBonus/100}</span>
						</c:if>
					</div>
					<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">landscape</i><mytaglib:i18n key="type" />: ${formModel.tourTypeName}</div>	
					<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">hotel</i><mytaglib:i18n key="nights" />: ${formModel.nights}</div>	
				</div>
				
				<div class="hide-on-med-and-up">
					<table class="myTable light">
						<tr>
							<td>
								<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">public</i>${formModel.countryName}</div>
								<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">location_city</i>${formModel.cityName}</div>
							</td>
							<td>
								<div class="pt14 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">local_atm</i>
									<c:if test="${customerBonus == 0}">
										${formModel.price}
									</c:if>
									<c:if test="${customerBonus > 0}">
										<strike><span style="color: #ff5722">${formModel.price}</span></strike>
										&nbsp;
										<span style="color: #4caf50">${formModel.price - formModel.price*customerBonus/100}</span>
									</c:if>
								</div>
								<div class="pt14 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">landscape</i>${formModel.tourTypeName}</div>	
								<div class="pt14 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">hotel</i>${formModel.nights}</div>									
							</td>
						</tr>
					</table>
				</div>
				
				<!-- START BUTTONS -->
				<sec:authorize access="hasAnyRole('admin','moderator')">
					<div class="fixed-action-btn toolbar toolbarEnabled" style="position: absolute">
						<a class="btn-floating btn-small waves-effect waves-light cyan lighten-1"><i class="material-icons">menu</i></a>
						<ul>
							<li class="waves-effect waves-light hvr-icon-rotate"><a href="${baseUrl}/edit/${formModel.id}"><i class="material-icons hvr-icon">edit</i></a></li>
							<c:if test="${ formModel.tourStatus eq 'active'}">
								<li class="waves-effect waves-light deleteoractivate-${formModel.id} hvr-icon-shrink"><a><i class="material-icons hvr-icon">delete</i></a></li>
							</c:if>
							<c:if test="${ formModel.tourStatus eq 'inactive'}">
								<li class="waves-effect waves-light deleteoractivate-${formModel.id} hvr-icon-shrink"><a><i class="material-icons hvr-icon">restore_from_trash</i></a></li>
							</c:if>
						</ul>
					</div>
				</sec:authorize>
				<script>
					deleteOrActivate('${contextPath}', '${formModel.id}', '${formModel.name}', '${formModel.tourStatus}');
				</script>
								
				<sec:authorize access="hasRole('user')">
					<div class="fixed-action-btn hvr-bounce-in" style="position: absolute">
						<c:if test="${formModel.favorite == true}">
							<a class="btn-floating waves-effect waves-light addToFavorite-${formModel.id} gradient-45deg-red-red btn-small" onclick="addToFavorite('${contextPath}', '${formModel.id}');"><i class="material-icons">favorite</i></a>
						</c:if>
						<c:if test="${formModel.favorite == false}">
							<a class="btn-floating waves-effect waves-light addToFavorite-${formModel.id} cyan lighten-1 btn-small btn-small" onclick="addToFavorite('${contextPath}', '${formModel.id}');"><i class="material-icons">favorite_border</i></a>
						</c:if>
					</div>
				</sec:authorize>
				<!-- END BUTTONS -->	
												
			</div>
		</div>
		<!-- END TOUR OPTIONS -->
		
		<!-- START TOUR DATES -->
		<div class="card hoverable roundborder" style="overflow: hidden">
			<div class="card-content light flexCenterOnPage tour-${formModel.id}">
				<div class="hide-on-small-only">
					<div class="pt16 valign-wrapper-center center-div"  style="margin-bottom: 1.5vh"><i class="material-icons" style="margin-right: 1vh">calendar_today</i><mytaglib:i18n key="dates" />:</div>
					<div id="dates" class="pt14 center-div center-align">
						<sec:authorize access="hasAnyRole('admin','moderator')">
							<c:forEach var="date" items="${tourDates}" varStatus="loopCounter">
								<button class="btn btn-small roundborder gradient-45deg-red-red deleteTourDate-${date.id} hvr-shrink" onclick="deleteTourDate('${contextPath}', '${date.id}');" style="margin: 0.5vh; padding: 0 1vh">
									${date.dateStartString}<c:if test="${not empty date.dateStartString}"> – </c:if>${date.dateEndString}
								</button>
							</c:forEach>
						</sec:authorize>
						
						<c:if test="${userRole ne 'Admin' && userRole ne 'Moderator'}">
							<c:forEach var="date" items="${tourDates}" varStatus="loopCounter">
								<c:if test="${loopCounter.index !=0 && not empty date.dateEndString}">, </c:if>
								${date.dateStartString}<c:if test="${not empty date.dateStartString}"> – </c:if>${date.dateEndString}
							</c:forEach>
						</c:if>
						
						<c:if test="${empty tourDates}">
							<mytaglib:i18n key="noDatesFound" />
						</c:if>
					</div>
				</div>
				
				<div class="hide-on-med-and-up">
					<div class="pt14 valign-wrapper-center center-div" style="margin-bottom: 1.5vh"><i class="material-icons" style="margin-right: 1vh">calendar_today</i><mytaglib:i18n key="dates" />:</div>
					<div class="pt12 center-div center-align">
						<sec:authorize access="hasAnyRole('admin','moderator')">
							<c:forEach var="date" items="${tourDates}" varStatus="loopCounter">
								<button class="btn btn-small roundborder roundborder gradient-45deg-red-red deleteTourDate-${date.id}" onclick="deleteTourDate('${contextPath}', '${date.id}');" style="margin: 0.5vh; padding: 0 0.5vh">
									${date.dateStartString}<c:if test="${not empty date.dateStartString}"> – </c:if>${date.dateEndString}
								</button>
							</c:forEach>
						</sec:authorize>
						
						<c:if test="${userRole ne 'Admin' && userRole ne 'Moderator'}">
							<c:forEach var="date" items="${tourDates}" varStatus="loopCounter">
								<c:if test="${loopCounter.index !=0 && not empty date.dateEndString}">, </c:if>
								${date.dateStartString}<c:if test="${not empty date.dateStartString}"> – </c:if>${date.dateEndString}
							</c:forEach>
						</c:if>
						<c:if test="${empty tourDates}">
							<mytaglib:i18n key="noDatesFound" />
						</c:if>
					</div>
				</div>
			</div>
			<div class="card-image roundbottomborder">
			
				<!-- START TOUR DATES ADD BLOCK -->
				<sec:authorize access="hasAnyRole('admin','moderator')">
					<ul class="collapsible light incard z-depth-0 tour-${formModel.id}">
						<li>
							<div class="collapsible-header hide-on-small-only gradient-45deg-light-blue-teal">
		      					<span class="valign-wrapper-center center-div pt16"><i class="material-icons" style="margin-right: 0.5vh">calendar_today</i><mytaglib:i18n key="addDates" /></span>
		      				</div>
					      	<div class="collapsible-header hide-on-med-and-up gradient-45deg-light-blue-teal">
		      					<span class="valign-wrapper-center center-div pt14"><i class="material-icons" style="margin-right: 0.5vh">calendar_today</i><mytaglib:i18n key="addDates" /></span>
					      	</div>
		      				<div class="collapsible-body tour-${formModel.id}">
		      					<div class="row" style="margin-bottom: 0">
		      						<div class="input-field col s12 l6">
		      							<i class="material-icons prefix">calendar_today</i>
		      							<input id="startDate" type="text" class="datepicker">
		      							<label for="startDate"><mytaglib:i18n key="dateStart" /></label>
		      						</div>
		      						<div class="input-field col s12 l6">
		      							<i class="material-icons prefix">calendar_today</i>
		      							<input id="endDate" type="text" class="datepicker">
		      							<label for="endDate"><mytaglib:i18n key="dateEnd" /></label>
		      						</div>
		      						<div class="input-field col s12 l6">
		      							<i class="material-icons prefix">wc</i>
		      							<input id="numPerson" type="text">
		      							<label for="numPerson"><mytaglib:i18n key="peoplecount" /></label>
		      						</div>
		      						<div class="input-field col s12 l6 center-align">
		      							<button class="btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green" onclick="addTourDates('${contextPath}', '${formModel.id}');"><mytaglib:i18n key="add" /><i class="material-icons left">add_circle</i></button>
		      						</div>
		      					</div>
		      				</div>
						</li>
					</ul>
				</sec:authorize>
				<!-- END TOUR DATES ADD BLOCK -->
				
				<!-- START TOUR RESERVE -->
				<jspFragments:bookedTour />
				<!-- END TOUR RESERVE -->
				
			</div>
		</div>
		<!-- END TOUR DATES -->
		
		<!-- START CALENDAR -->
		<script>
			ymaps.ready(initMap.bind(null, '${formModel.address}', '16'));
		</script>
		<ul class="collapsible light hoverable roundborder" style="overflow: hidden">
			<li class="active">
		    	<div class="collapsible-header roundtopborder hide-on-small-only tour-${formModel.id}">
		      		<span class="valign-wrapper-center center-div pt16"><i class="material-icons" style="margin-right: 0.5">today</i><mytaglib:i18n key="calendar" /></span>
		      	</div>
		      	<div class="collapsible-header roundtopborder hide-on-med-and-up tour-${formModel.id}">
		      		<span class="valign-wrapper-center center-div pt14"><i class="material-icons" style="margin-right: 0.5">today</i><mytaglib:i18n key="calendar" /></span>
		      	</div>
				<div class="collapsible-body roundborder noPadding">
			    	<div class="card z-depth-0 roundborder" style="margin: 0">
			    		<div class="calendar roundborder"></div>
					</div>
		    	</div>
		    </li>
		  </ul>
		<!-- END CALENDAR -->
		
		<!-- START MAPS -->
		<script>
			ymaps.ready(initMap.bind(null, '${formModel.address}', '16'));
		</script>
		<ul class="collapsible light hoverable roundborder" style="overflow: hidden">
			<li>
		    	<div class="collapsible-header roundtopborder hide-on-small-only tour-${formModel.id}">
		      		<span class="valign-wrapper-center center-div pt16"><i class="material-icons" style="margin-right: 0.5">map</i><mytaglib:i18n key="hotelOnMap" /></span>
		      	</div>
		      	<div class="collapsible-header roundtopborder hide-on-med-and-up tour-${formModel.id}">
		      		<span class="valign-wrapper-center center-div pt14"><i class="material-icons" style="margin-right: 0.5">map</i><mytaglib:i18n key="hotelOnMap" /></span>
		      	</div>
				<div class="collapsible-body noPadding">
			    	<div class="card z-depth-0" style="margin: 0">
						<div class="card small z-depth-0" style="margin: 0">
							<div class="card-content">
								<div id="map" class="dmap"></div>
							</div>
						</div>
						<div class="card-action center tour-${formModel.id}" style="padding: 0.5vh; border-top: 0; background-color: transparent">
							<span class="pt12 ">${formModel.address}</span>
						</div>
					</div>
		    	</div>
		    </li>
		  </ul>
		<!-- END MAPS -->
		
	</div>
	
	<div class="col s12">
	
		<!-- START TOUR DESCRIPTION -->
		<div class="hide-on-large-only">
			<jspFragments:tourDescription />
		</div>
		<script>
			rateTourDate('${contextPath}');	
		</script>
		<!-- END TOUR DESCRIPTION -->
		
	</div>
	
</div>
<br/>

<!-- START NOTIFICATION -->
<c:if test="${formModel.tourStatus eq 'inactive'}">
	<script>
		swal({
			title: "Tour has status 'inactive'.",
			text: "Do you want to see this tour?",
			icon: "warning",
			buttons: true,
		}).then((yes) => {
				if (yes) {
					swal({
						title: "You can view or edit this tour.",
						icon: "success",
			    		timer: 3000,
					});
				} else {
					swal({
						title: "You'll be redirectet to tours page.",
						text: "Do you want to be redirected?",
						icon: "warning",
						buttons: true,
					}).then((yes) => {
							if (yes) {
								window.location.href = "${baseUrl}";
						 	 }
						});
				}
			});
	</script>
</c:if>
<!-- END NOTIFICATION -->

<script>
$(function() {
	if (['ko', 'fr', 'ch', 'de', 'jp', 'pt', 'da', 'pl', 'es', 'fa', 'it', 'cs', 'uk', 'ru'].indexOf('${locale}') >= 0) {
		$('.calendar').pignoseCalendar({
	    	lang: '${locale}',
	    	week: 1
		});
	} else {
		$('.calendar').pignoseCalendar({
	    	lang: 'en'
		});
	}
});
</script>

<style>
h4 {
	font-size: 2rem;
	line-height: 110%;
	margin: 1rem 0 0 0;
}

h5 {
	font-size: 1.5rem;
	line-height: 110%;
	margin: 1rem 0 0.5rem 0;
}
.card .card-action {
    border-top: 0;
	background-color: white !important;
}
</style>
