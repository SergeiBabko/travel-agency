<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.jqueryui.min.css">
<link rel="stylesheet" href="${contextPath}/resources/css/manager.css">
<link rel="stylesheet" href="${contextPath}/resources/css/customer.css">

<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/dataTables.jqueryui.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>


<!-- START MANAGER HEADER -->
<div class="row">
	<br /> <br />
	<div class="col s12">
		<div class="card roundborder" style="border-style: solid; border-color: white; border-width: 0.5vh">
		
			<div class="card-image waves-effect waves-light centerInDiv darken roundtopborder" style="max-height: 200px">
				<div class="mask">
					<img src="${contextPath}/resources/img/travel.png">
				</div>
				<div class="card-title">
					<img src="${contextPath}/resources/img/user2.png" style="width: 76px; margin-bottom: 1vh">
					<div class="name pt16">
						<mytaglib:user-name></mytaglib:user-name>
					</div>
					<div class="email pt14">
						<sec:authentication property="principal" />
					</div>
				</div>
			</div>
			
			<div class="card-tabs center" style="padding: 0">
				<div class="row" style="margin: 0">
					<ul class="tabs gradient-45deg-light-blue-cyan roundbottomborder">
						<li class="tab col s3"><a class="white-text light" href="#booked"><mytaglib:i18n key="manager.booked" /></a></li>
						<li class="tab col s3"><a class="white-text light" href="#tours"><mytaglib:i18n key="manager.tours" /></a></li>
						<li class="tab col s3"><a class="white-text light" href="#info"><mytaglib:i18n key="manager.info" /></a></li>
						<li class="tab col s3"><a class="white-text light" href="#accounts"><mytaglib:i18n key="manager.acc" /></a></li>
					</ul>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- END MANAGER HEADER -->


<!-- START BOOKED TOURS -->
<div id="booked" class="row" style="margin-bottom: 4vh">
	<div class="col s12" style="margin: 0">
	 
		<div class="card roundborder" style="margin: 0.25vh 0 1rem 0; margin-bottom: 3.5vh; display: grid">
			<div class="card-content" style="padding: 0.5vh">
				
				<!-- START MANAGER ACCOUNTS TABLE -->
				<span class="card-title center"><mytaglib:i18n key="manager.mBooked" /></span>
				<table class="dataTable display" style="width: 100%">
					<thead>
						<tr>
							<th>№</th>
							<th><mytaglib:i18n key="tour.name" /></th>
							<th><mytaglib:i18n key="price" /></th>
							<th><mytaglib:i18n key="peoplecount" /></th>
							<th><mytaglib:i18n key="dates" /></th>
							<th><mytaglib:i18n key="print" /></th>
							<th><mytaglib:i18n key="created" /></th>
							<th><mytaglib:i18n key="processed" /></th>
							<th><mytaglib:i18n key="delete" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="booked" items="${booked}" varStatus="loopCounter">
							<tr class="booked-${booked.id}">
								<td>${booked.id}</td>
								<td class="cl-effect-1 link-1"><a target="_blank" href="${contextPath}/tours/${booked.tourId}">${booked.tourName}</a></td>
								<td>${booked.price}</td>
								<td>${booked.numPerson}</td>
								<td><fmt:formatDate pattern="dd.M.y" value="${booked.tourDateStart}" /> &ndash; <fmt:formatDate pattern="dd.M.y" value="${booked.tourDateEnd}" /></td>
								<td>
									<a class="btn-floating waves-effect waves-light cyan lighten-1" style="width: 22px; height: 22px; line-height: 22px" href="${contextPath}/booked/${booked.id}" target="_blank"> 
										<i class="material-icons" style="font-size: 10pt; line-height: inherit">print</i>
									</a>
								</td>
								<td><fmt:formatDate pattern="dd.M.y" value="${booked.created}" /></td>
								<td>
									<c:if test="${booked.processed == false}">
										<div class="switch">
										    <label>
										      <input class="processBooked-${booked.id}" type="checkbox">
										      <span class="lever"></span>
										    </label>
										</div>
									</c:if>
									<c:if test="${booked.processed == true}">
										<div class="switch">
										    <label>
										      <input class="processBooked-${booked.id}" type="checkbox" disabled checked="checked">
										      <span class="lever"></span>
										    </label>
										</div>
									</c:if>
								</td>
								<td>
									<a id="deleteBooked-${booked.id}" class="btn-floating waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteBookedTour('${contextPath}', '${booked.id}');" style="width: 22px; height: 22px; line-height: 22px"> 
										<i class="material-icons" style="font-size: 10pt; line-height: inherit">delete</i>
									</a>
								</td>
							</tr>
							<script>
								processBooked('${contextPath}', '${booked.id}', '${booked.processed}');
							</script>
						</c:forEach>
					</tbody>
				</table>
				<!-- END MANAGER ACCOUNTS TABLE -->
				
			</div>
		</div>
		
		<ul class="collapsible roundborder" style="margin-bottom: 0">
			<li class="active">
				<div class="collapsible-header centerInDiv"><i class="material-icons left" style="margin-right: 0">show_chart</i><mytaglib:i18n key="charts" /></div>
				<div class="collapsible-body" style="padding: 2rem">
					<div class="row" style="margin-top: 20px">
						<canvas id="myChart" width="100%" style="width: 100% !important; height: 50% important"></canvas>
					</div>			
				</div>
			</li>
		</ul>
				
		<script>
			getBookedByMonth();
		</script>
		
	</div>
</div>
<!-- END BOOKED TOURS -->

<!-- START TOURS -->
<div id="tours" class="row" style="margin-bottom: 4vh">
	<div class="col s12" style="margin: 0">
	 
		<div class="card roundborder" style="margin: 0.25vh 0 1rem 0; margin-bottom: 3.5vh; display: grid">
			<div class="card-content" style="padding: 0.5vh">
				
				<!-- START MANAGER ACCOUNTS TABLE -->
				<span class="card-title center"><mytaglib:i18n key="allTours" /></span>
				<table class="dataTable display" style="width: 100%">
					<thead>
						<tr>
							<th>№</th>
							<th><mytaglib:i18n key="status" /></th>
							<th><mytaglib:i18n key="tour.name" /></th>
							<th><mytaglib:i18n key="price" /></th>
							<th><mytaglib:i18n key="nights" /></th>
							<th><mytaglib:i18n key="type" /></th>
							<th><mytaglib:i18n key="city" /></th>
							<th><mytaglib:i18n key="country" /></th>
							<th><mytaglib:i18n key="bookedCount" /></th>
							<th><mytaglib:i18n key="favoriteCount" /></th>
							<th><mytaglib:i18n key="created" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tour" items="${tours}" varStatus="loopCounter">
							<tr>
								<td>${tour.id}</td>
								<td>${tour.tourStatus}</td>
								<td class="cl-effect-1 link-1"><a target="_blank" href="${contextPath}/tours/${tour.id}">${tour.name}</a></td>
								<td>${tour.price}</td>
								<td>${tour.nights}</td>
								<td>${tour.tourTypeName}</td>
								<td>${tour.cityName}</td>
								<td>${tour.countryName}</td>
								<td>${tour.bookedCount}</td>
								<td>${tour.favoriteCount}</td>
								<td><fmt:formatDate pattern="dd.M.y" value="${tour.created}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- END MANAGER ACCOUNTS TABLE -->
				
			</div>
		</div>
	</div>
</div>
<!-- END TOURS -->

<!-- START INFO PAGE -->
<div id="info" class="row" style="margin-bottom: 2vh">
	<div class="col s12" style="margin: 0">
	 
		<div class="card roundborder" style="margin: 0.25vh 0 1rem 0; margin-bottom: 3.5vh; display: grid">
			<div class="card-content center" style="padding: 0.5vh">
				
				<!-- START COUNTRY -->
				<div class="col s12 l6" style="margin: 0">
					<!-- START COUNTRIES TABLE -->
					<span class="card-title center"><mytaglib:i18n key="manageCountries" /></span>
					<table class="dataTable display" style="width: 100%">
						<thead>
							<tr>
								<th>№</th>
								<th><mytaglib:i18n key="country" /></th>
								<th><mytaglib:i18n key="created" /></th>
								<th><mytaglib:i18n key="manage" /></th>
							</tr>
						</thead>
						<tbody id="country">
							<c:forEach var="country" items="${countries}" varStatus="loopCounter">
								<tr id="country-${country.id}">
									<td>${country.id}</td>
									<td id="defCountryName-${country.id}">${country.name}</td>
									<td><fmt:formatDate pattern="dd.M.y" value="${country.created}" /></td>
									<td>
										<a class="btn-floating waves-effect waves-light modal-trigger teal lighten-3 hvr-rotate" href="#editCountry-${country.id}" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">edit</i>
										</a>
										<a class="btn-floating waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteCountry('${contextPath}', '${country.id}');" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">delete</i>
										</a>
									</td>
								</tr>
								
								<!-- START EDIT COUNTRY -->
								<div id="editCountry-${country.id}" class="modal roundborder">
									<div class="modal-content">
										<h4 class="center-align"><mytaglib:i18n key="updateCountryName" /></h4>
										<br/>
										<div class="row">
											<div class="input-field col s12 l6 offset-l3">
												<i class="material-icons prefix">public</i>
												<input value="${country.name}" id="countryName-${country.id}" type="text" />
												<label id="countryLabel-${country.id}" class="active" for="countryName-${country.id}"><mytaglib:i18n key="country" /></label>
											</div>
										</div>
							
										<div class="row">
											<div class="col s12 center-align">
												<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="updateCountry('${contextPath}', '${country.id}', '${country.name}');">
													<mytaglib:i18n key="update" /><i class="material-icons left" style="font-size: 150%">check</i>
												</a>
												<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable">
													<mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i>
												</a>
											</div>
										</div>
									</div>
								</div>
								<!-- END EDIT COUNTRY -->
								
							</c:forEach>
						</tbody>
					</table>
					<div class="col s12 right-align" style="margin-top: 1vh">
						<a class="btn btn-small waves-effect waves-light roundborder modal-trigger gradient-45deg-cyan-light-green" href="#addNewCountry">
							<mytaglib:i18n key="addCountry" /><i class="material-icons left">add</i>
						</a>
					</div>
					<!-- END COUNTRIES TABLE -->
				</div>
				
				<!-- START ADD NEW COUNTRY -->
				<div id="addNewCountry" class="modal roundborder">
					<div class="modal-content">
						<h4 class="center-align"><mytaglib:i18n key="newCountry" /></h4>
						<br/>
						<div class="row">
							<div class="input-field col s12 l6 offset-l3">
								<i class="material-icons prefix">public</i>
								<input id="newCountryName" type="text" />
								<label for="newCountryName"><mytaglib:i18n key="country" /></label>
							</div>
						</div>
			
						<div class="row">
							<div class="col s12 center-align">
								<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="createNewCountry('${contextPath}');"><mytaglib:i18n key="create" /><i class="material-icons left" style="font-size: 150%">add</i></a>
								<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable"><mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i></a>
							</div>
						</div>
					</div>
				</div>
				<!-- END ADD NEW COUNTRY -->
				<!-- END COUNTRY -->
				
				<!-- START CITY -->
				<div class="col s12 l6" style="margin: 0">
					<!-- START CITIES TABLE -->
					<span class="card-title center"><mytaglib:i18n key="manageCities" /></span>
					<table class="dataTable display" style="width: 100%">
						<thead>
							<tr>
								<th>№</th>
								<th><mytaglib:i18n key="city" /></th>
								<th><mytaglib:i18n key="country" /></th>
								<th><mytaglib:i18n key="created" /></th>
								<th><mytaglib:i18n key="manage" /></th>
							</tr>
						</thead>
						<tbody id="city">
							<c:forEach var="city" items="${cities}" varStatus="loopCounter">
								<tr id="city-${city.id}">
									<td>${city.id}</td>
									<td id="defCityName-${city.id}">${city.name}</td>
									<td id="defCityCountryName-${city.id}">${city.countryName}</td>
									<td><fmt:formatDate pattern="dd.M.y" value="${city.created}" /></td>
									<td>
										<a class="btn-floating waves-effect waves-light modal-trigger teal lighten-3 hvr-rotate" href="#editCity-${city.id}" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">edit</i>
										</a>
										<a class="btn-floating waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteCity('${contextPath}', '${city.id}');" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">delete</i>
										</a>
									</td>
								</tr>
								
								<!-- START EDIT CITY -->
								<div id="editCity-${city.id}" class="modal roundborder" style="overflow: inherit">
									<div class="modal-content">
										<h4 class="center-align"><mytaglib:i18n key="updateCity" /></h4>
										<br/>
										<div class="row">
											
											<div class="input-field col s12 l6">
												<i class="material-icons prefix" style="left: 0">public</i>
												<select class="countryEditSelect" style="display: none">
													<option disabled><mytaglib:i18n key="selectCountry" /></option>
													<c:forEach var="country" items="${countries}" varStatus="loopCounter">
														<c:if test="${country.id == city.countryId}">
															<option value="${country.id}" selected >${country.name}</option>
														</c:if> 
														<c:if test="${country.id != city.countryId}">
															<option value="${country.id}" >${country.name}</option>
														</c:if> 
													</c:forEach>
												</select>
												<label for="countrySelect"><mytaglib:i18n key="country" /></label>
											</div>
										
											<div class="input-field col s12 l6">
												<i class="material-icons prefix">location_city</i>
												<input value="${city.name}" id="cityName-${city.id}" type="text" />
												<label id="cityLabel-${city.id}" class="active" for="cityyName-${city.id}"><mytaglib:i18n key="city" /></label>
											</div>
										</div>
							
										<div class="row">
											<div class="col s12 center-align">
												<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="updateCity('${contextPath}', '${city.id}', '${city.name}', '${city.countryId}');">
													<mytaglib:i18n key="update" /><i class="material-icons left" style="font-size: 150%">check</i>
												</a>
												<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable">
													<mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i>
												</a>
											</div>
										</div>
									</div>
								</div>
								<!-- END EDIT CITY -->
								
							</c:forEach>
						</tbody>
					</table>
					<div class="col s12 right-align" style="margin-top: 1vh">
						<a class="btn btn-small waves-effect waves-light roundborder modal-trigger gradient-45deg-cyan-light-green" href="#addNewCity">
							<mytaglib:i18n key="addCity" /><i class="material-icons left">add</i>
						</a>
					</div>
					<!-- END CITIES TABLE -->
				</div>
				
				<!-- START ADD NEW CITY -->
				<div id="addNewCity" class="modal roundborder" style="overflow: inherit">
					<div class="modal-content">
						<h4 class="center-align"><mytaglib:i18n key="addNewCity" /></h4>
						<br/>
						
						<div class="row">
						
							<div class="input-field col s12 l6">
								<i class="material-icons prefix" style="left: 0">public</i>
								<select class="countryAddSelect" style="display: none">
									<option disabled selected><mytaglib:i18n key="selectCountry" /></option>
									<c:forEach var="country" items="${countries}" varStatus="loopCounter">
										<option value="${country.id}" >${country.name}</option>
									</c:forEach>
								</select>
								<label for="countrySelect"><mytaglib:i18n key="country" /></label>
							</div>
							<script>
								getCountryId();
							</script>

							<div class="input-field col s12 l6">
								<i class="material-icons prefix">location_city</i>
								<input id="newCityName" type="text" />
								<label for="newCityName"><mytaglib:i18n key="city" /></label>
							</div>
							
						</div>
			
						<div class="row">
							<div class="col s12 center-align">
								<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="createNewCity('${contextPath}');"><mytaglib:i18n key="create" /><i class="material-icons left" style="font-size: 150%">add</i></a>
								<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable"><mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i></a>
							</div>
						</div>
					</div>
				</div>
				<!-- END ADD NEW CITY -->
				<!-- END CITY -->
				
				<!-- START TOURTYPES -->
				<div class="col s12 l6 offset-l3">
					<!-- START TOURTYPES TABLE -->
					<span class="card-title center"><mytaglib:i18n key="manageTourTypes" /></span>
					<table class="dataTable display" style="width: 100%">
						<thead>
							<tr>
								<th>№</th>
								<th><mytaglib:i18n key="type" /></th>
								<th><mytaglib:i18n key="created" /></th>
								<th><mytaglib:i18n key="manage" /></th>
							</tr>
						</thead>
						<tbody id="tourType">
							<c:forEach var="type" items="${tourTypes}" varStatus="loopCounter">
								<tr id="tourType-${type.id}">
									<td>${type.id}</td>
									<td id="defTourType-${type.id}">${type.type}</td>
									<td><fmt:formatDate pattern="dd.M.y" value="${type.created}" /></td>
									<td>
										<a class="btn-floating waves-effect waves-light modal-trigger teal lighten-3 hvr-rotate" href="#editTourType-${type.id}" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">edit</i>
										</a>
										<a class="btn-floating waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteTourType('${contextPath}', '${type.id}');" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">delete</i>
										</a>
									</td>
								</tr>
								
								<!-- START EDIT TOURTYPE -->
								<div id="editTourType-${type.id}" class="modal roundborder">
									<div class="modal-content">
										<h4 class="center-align"><mytaglib:i18n key="updateTourType" /></h4>
										<br/>
										<div class="row">
											<div class="input-field col s12 l6 offset-l3">
												<i class="material-icons prefix">public</i>
												<input value="${type.type}" id="tourTypeName-${type.id}" type="text" />
												<label id="tourTypeLabel-${type.id}" class="active" for="tourTypeName-${type.id}"><mytaglib:i18n key="type" /></label>
											</div>
										</div>
							
										<div class="row">
											<div class="col s12 center-align">
												<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="updateTourType('${contextPath}', '${type.id}', '${type.type}');">
													<mytaglib:i18n key="update" /><i class="material-icons left" style="font-size: 150%">check</i>
												</a>
												<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable">
													<mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i>
												</a>
											</div>
										</div>
									</div>
								</div>
								<!-- END EDIT TOURTYPE -->
								
							</c:forEach>
						</tbody>
					</table>
					<div class="col s12 right-align" style="margin-top: 1vh">
						<a class="btn btn-small waves-effect waves-light roundborder modal-trigger gradient-45deg-cyan-light-green" href="#addNewTourType">
							<mytaglib:i18n key="addTourType" /><i class="material-icons left">add</i>
						</a>
					</div>
					<!-- END TOURTYPES TABLE -->
				</div>
				
				<!-- START ADD NEW TOURTYPE -->
				<div id="addNewTourType" class="modal roundborder">
					<div class="modal-content">
						<h4 class="center-align"><mytaglib:i18n key="enterNewTourType" /></h4>
						<br/>
						<div class="row">
							<div class="input-field col s12 l6 offset-l3">
								<i class="material-icons prefix">public</i>
								<input id="newTourType" type="text" />
								<label for="newTourType"><mytaglib:i18n key="type" /></label>
							</div>
						</div>
			
						<div class="row">
							<div class="col s12 center-align">
								<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="createNewTourType('${contextPath}');"><mytaglib:i18n key="create" /><i class="material-icons left" style="font-size: 150%">add</i></a>
								<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable"><mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i></a>
							</div>
						</div>
					</div>
				</div>
				<!-- END ADD NEW TOURTYPE -->
				<!-- END TOURTYPES -->
				
			</div>
		</div>
	</div>
</div>
<!-- END INFO PAGE -->

<!-- START MANAGE ACCOUNTS -->
<div id="accounts" class="row" style="margin-bottom: 2vh">
	<div class="col s12" style="margin: 0">
	 
		<div class="card roundborder" style="margin: 0.25vh 0 1rem 0; margin-bottom: 3.5vh; display: grid">
			<div class="card-content" style="padding: 0.5vh">
				
				<sec:authorize access="hasRole('admin')">
					<!-- START MANAGER ACCOUNTS TABLE -->
					<span class="card-title center"><mytaglib:i18n key="managerAccount" /></span>
					<table class="dataTable display" style="width: 100%">
						<thead>
							<tr>
								<th>№</th>
								<th><mytaglib:i18n key="email" /></th>
								<th><mytaglib:i18n key="firstname" /></th>
								<th><mytaglib:i18n key="lastname" /></th>
								<th><mytaglib:i18n key="role" /></th>
								<th>Online</th>
								<th><mytaglib:i18n key="created" /></th>
								<th><mytaglib:i18n key="delete" /></th>
							</tr>
						</thead>
						<tbody id="managers">
							<c:forEach var="userAccount" items="${userAccounts}" varStatus="loopCounter">
								<tr id="manager-${userAccount.id}">
									<td>${userAccount.id}</td>
									<td>${userAccount.email}</td>
									<td>${userAccount.firstName}</td>
									<td>${userAccount.lastName}</td>
									<td>${userAccount.role}</td>
									<td id="isOnline-${userAccount.id}">upd...</td>
									<td><fmt:formatDate pattern="dd.M.y" value="${userAccount.created}" /></td>
									<td>
										<a class="btn-floating waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteManagerAccount('${contextPath}', '${userAccount.id}');" style="width: 22px; height: 22px; line-height: 22px"> 
											<i class="material-icons" style="font-size: 10pt; line-height: inherit">delete</i>
										</a>
									</td>
								</tr>
								<script>
									getOnlineStatus('${userAccount.id}');
								</script>
								
							</c:forEach>
						</tbody>
					</table>
					<div class="col s12 right-align" style="margin-top: 1vh">
						<a class="btn btn-small waves-effect waves-light roundborder modal-trigger gradient-45deg-cyan-light-green" href="#addNewManager">
							<mytaglib:i18n key="addNewManager" /><i class="material-icons left">add</i>
						</a>
					</div>
					<!-- END MANAGER ACCOUNTS TABLE -->
					
					<!-- START ADD NEW MANAGER ACCOUNT -->
					<div id="addNewManager" class="modal roundborder">
						<div class="modal-content">
							<h4 class="center-align"><mytaglib:i18n key="enterNewManager" /></h4>
							<br/>
							<div class="row">
								<div class="input-field col s12 l6">
									<i class="material-icons prefix">mail_outline</i>
									<input id="newManagerEmail" type="email" class="validate" />
									<label for="newManagerEmail"><mytaglib:i18n key="email" /></label>
								</div>
								<div class="input-field col s12 l6">
									<i class="material-icons prefix">lock_outline</i>
									<input id="newManagerPassword" type="password" />
									<label for="newManagerPassword"><mytaglib:i18n key="password" /></label>
								</div>
								<div class="input-field col s12 l6">
									<i class="material-icons prefix">perm_identity</i>
									<input id="newManagerName" type="text" />
									<label for="newManagerName"><mytaglib:i18n key="firstname" /></label>
								</div>
								<div class="input-field col s12 l6">
									<i class="material-icons prefix">person</i>
									<input id="newManagerSurname" type="text" />
									<label for="newManagerSurname"><mytaglib:i18n key="lastname" /></label>
								</div>
							</div>
				
							<div class="row">
								<div class="col s12 center-align">
									<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-cyan-light-green hoverable" onclick="createNewManager('${contextPath}');"><mytaglib:i18n key="create" /><i class="material-icons left" style="font-size: 150%">add</i></a>
									<a class="modal-close btn roundborder waves-effect waves-light gradient-45deg-red-red hoverable"><mytaglib:i18n key="close" /><i class="material-icons left" style="font-size: 150%">close</i></a>
								</div>
							</div>
						</div>
					</div>
					<!-- END ADD NEW MANAGER ACCOUNT -->
					<br/>
				</sec:authorize>
				
				<!-- START CUSTOMERS TABLE -->
				<span class="card-title center"><mytaglib:i18n key="customersAccounts" /></span>
				<table class="dataTable display" style="width: 100%">
					<thead>
						<tr>
							<th>№</th>
							<th><mytaglib:i18n key="email" /></th>
							<th><mytaglib:i18n key="firstname" /></th>
							<th><mytaglib:i18n key="lastname" /></th>
							<th><mytaglib:i18n key="phone" /></th>
							<th><mytaglib:i18n key="bonus" /></th>
							<th><mytaglib:i18n key="country" /></th>
							<th><mytaglib:i18n key="city" /></th>
							<th><mytaglib:i18n key="status" /></th>
							<th>Online</th>
							<th><mytaglib:i18n key="created" /></th>
							<th><mytaglib:i18n key="blocked" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="customer" items="${customers}" varStatus="loopCounter">
							<tr id="customer-${customer.id}">
								<td>${customer.id}</td>
								<td>${customer.email}</td>
								<td>${customer.firstName}</td>
								<td>${customer.lastName}</td>
								<td>${customer.phoneNumber}</td>
								<td>${customer.bonus}</td>
								<td>${customer.country}</td>
								<td>${customer.city}</td>
								<td id="status-${customer.id}">${customer.status}</td>
								<td id="isOnline-${customer.id}">upd...</td>
								<td><fmt:formatDate pattern="dd.M.y" value="${customer.created}" /></td>
								<td>
									<c:if test="${customer.status eq 'active'}">
										<div class="switch">
										    <label>
										      <input class="blockCustomer-${customer.id}" type="checkbox">
										      <span class="lever"></span>
										    </label>
										</div>
									</c:if>
									<c:if test="${customer.status eq 'blocked'}">
										<div class="switch">
										    <label>
										      <input class="blockCustomer-${customer.id}" type="checkbox" checked="checked">
										      <span class="lever"></span>
										    </label>
										</div>
									</c:if>
								</td>
							</tr>
							<script>
								blockCustomer('${contextPath}', '${customer.id}', '${customer.status}');
								getOnlineStatus('${customer.id}');
							</script>
						</c:forEach>
					</tbody>
				</table>
				<!-- END CUSTOMERS TABLE -->
	
			</div>	
		</div>
		
		<!-- START MANAGER ACCOUNT -->
		<div class="row center-align">
			<div class="col s12 l6 offset-l3">
				<ul class="collapsible roundborder" style="margin-bottom: 0">
				
					<!-- START MANAGE MANAGER ACCOUNT -->
					<li>
						<div class="collapsible-header centerInDiv"><i class="material-icons">assignment</i><mytaglib:i18n key="Manage" /> "<mytaglib:user-name></mytaglib:user-name>" <mytaglib:i18n key="account" /></div>
						<div class="collapsible-body">
							<div class="row" style="margin-top: 20px">
							
								<form:form modelAttribute="user">
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
								
								<div class="col s12 center-align">
									<button onclick="updateManager('${contextPath}', '${user.id}');" class="btn waves-effect waves-light roundborder gradient-45deg-cyan-light-green">
										<mytaglib:i18n key="update" /><i class="material-icons left">check</i>
									</button>
								</div>
								
							</div>			
						</div>
					</li>
					<!-- END MANAGE MANAGER ACCOUNT -->
					
					<!-- START CHANGE PASSWORD -->
					<li>
						<div class="collapsible-header centerInDiv"><i class="material-icons">lock_outline</i><mytaglib:i18n key="passUpd" /></div>
						<div class="collapsible-body">
							<div class="row" style="margin-top: 20px">
								<div class="input-field col s12 m6">
									<i class="material-icons prefix">lock_outline</i>
									<input id="oldPassword" type="password" />
									<label for="oldPassword"><mytaglib:i18n key="oldPassword" /></label>
								</div>	
								
								<div class="input-field col s12 m6">
									<i class="material-icons prefix">lock</i>
									<input id="newPassword" type="password" />
									<label for="newPassword"><mytaglib:i18n key="newPassword" /></label>
								</div>	
								
								<div class="col s12 center-align">
									<button class="btn waves-effect waves-light roundborder gradient-45deg-cyan-light-green" onclick="updatePassword('${contextPath}', '${user.id}');">
										<mytaglib:i18n key="update" /><i class="material-icons left">check</i>
									</button>
								</div>
							</div>			
						</div>
					</li>
					<!-- END CHANGE PASSWORD -->
					
				</ul>
			</div>
		</div>
		<!-- END MANAGER ACCOUNT -->
		
	</div>
</div>
<!-- END MANAGE ACCOUNTS -->

<script>
	$(document).ready(function() {
		$('.dataTable').DataTable();
	});
	$('.tabs').tabs({
		swipeable : false
	});
</script>
