<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" href="${contextPath}/resources/css/slider/ion.rangeSlider.skinFlat.css">
<link rel="stylesheet" href="${contextPath}/resources/css/slider/ion.rangeSlider.css" />
<script src="${contextPath}/resources/js/ion.rangeSlider.js"></script>

<c:set var="baseUrl" value="${contextPath}/tours" />

<br />

<!-- START PAGE NAME AND SORTING -->
<div class="row">
	<h4>
		<span class="col s6 light">Tours</span> 
		<span class="col s6 right-align"> 
			<c:if test="${not empty gridItems}">
				<a class="btn-small dropdown-trigger waves-effect waves-light cyan lighten-1 roundborder hvr-icon-hang" data-target="sort"><mytaglib:i18n key="sortBy" /><i class="material-icons right hvr-icon">arrow_drop_down</i></a>
			</c:if>
		</span>
	</h4>
</div>

<ul id="sort" class="dropdown-content roundborder">
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="id">№ &nbsp; &nbsp; &nbsp;</mytaglib:sort-link></li>
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></li>
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="name"><mytaglib:i18n key="name" /></mytaglib:sort-link></li>
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="city"><mytaglib:i18n key="city" /></mytaglib:sort-link></li>
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="country"><mytaglib:i18n key="country" /></mytaglib:sort-link></li>
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="price"><mytaglib:i18n key="price" /></mytaglib:sort-link></li>
	<li><mytaglib:sort-link pageUrl="${baseUrl}" column="nights"><mytaglib:i18n key="nights" /></mytaglib:sort-link></li>
</ul>
<!-- END PAGE NAME AND SORTING -->

<!-- START TOURS FLOATING ADD BUTTON -->
<c:if test="${empty gridItems}">
	<sec:authorize access="hasAnyRole('admin','moderator')">
		<a class="btn-floating btn-large waves-effect waves-light cyan lighten-1 pulse centerOnPage hvr-icon-pulse-grow" href="${baseUrl}/add"><i class="material-icons hvr-icon">add</i></a>
	</sec:authorize>
</c:if>

<c:if test="${not empty gridItems}">
	<sec:authorize access="hasAnyRole('admin','moderator')">
		<div class="fixed-action-btn" style="z-index: 10000">
			<a class="btn-floating btn-large waves-effect waves-light cyan lighten-1 hvr-icon-pulse-grow" href="${baseUrl}/add"><i class="material-icons hvr-icon">add</i></a>
		</div>
	</sec:authorize>
</c:if>
<!-- END TOURS FLOATING ADD BUTTON -->

<br/>

<!-- START TOURS PAGE CONTENT -->
<div class="row" style="margin-bottom: 0">
	<div class="col s12 l4">
		
		<!-- START TOURS SEARCH FORM -->
		<form:form method="POST" action="${baseUrl}" modelAttribute="searchForm" style="margin-bottom: 0">
			<div class="card roundborder hoverable" style="margin: 0">
				<div class="card-content black-text">
					<span class="card-title center"><mytaglib:i18n key="findTour" /></span>
					
					<div class="input-field col s12">
						<i class="material-icons prefix">public</i>
						<form:select path="countryId">
							<option value="" selected><mytaglib:i18n key="allCountries" /></option>
							<form:options items="${countryChoice}" />
						</form:select>
						<form:errors path="countryId" cssClass="red-text" />
					</div>
				
					<div class="input-field col s12">
						<i class="material-icons prefix">location_city</i>
						<form:select path="cityId">
							<option value="" selected><mytaglib:i18n key="allCities" /></option>
							<form:options items="${cityChoice}" />
						</form:select>
						<form:errors path="cityId" cssClass="red-text" />
					</div>
					
					<div class="input-field col s12">
						<i class="material-icons prefix">landscape</i>
						<form:select path="tourTypeId">
							<option value="" selected><mytaglib:i18n key="allTourTypes" /></option>
							<form:options items="${tourTypeChoice}" />
						</form:select>
						<form:errors path="tourTypeId"/>
					</div>
		
					<div class="input-field col s12">
						<h6 id="nightsText" class="center"><mytaglib:i18n key="nights" />:</h6>
						<form:input path="nights" type="text" class="range"/>
					</div>
					<script>
						var n = document.getElementById('nightsText').innerHTML;
						if (n.includes('Nights')){
							var nights = 'Nights: ';
						} else {
							var nights = 'Ночи: ';
						}
						initRange('${maxNights}', '${fromNights}', '${toNights}', nights);
					</script>
		
					<div class="input-field col s12">
						<h6 class="center"><mytaglib:i18n key="price" />:</h6>
						<form:input path="price" type="text" class="range"/>
					</div>
					<script>
						initRange('${maxPrice}', '${fromPrice}', '${toPrice}', '$');
					</script>
		
					<div class="center">
						<button class="btn waves-effect waves-light gradient-45deg-cyan-light-green roundborder" type="submit"><mytaglib:i18n key="search" /><i class="material-icons left">search</i></button>
					</div>
						
					<script src="${pageContext.request.contextPath}/resources/js/init-combos.js"></script>
					<script>
						initCountry('${baseUrl}');
					</script>
				</div>
			</div>
			<br />
		</form:form>
		<!-- END TOURS SEARCH FORM -->
		
		<!-- START CURRENCY CONVERTER -->
		<div class="card roundborder hoverable" style="margin: 0 0 1rem 0">
			<div class="row" style="margin-left: 0; margin-right: 0">
				<div id="convertor" class="card-content" style="padding-bottom: 0">
					<span class="card-title center" style="font-size: 20px; margin-bottom: 0"><mytaglib:i18n key="currencyConverter" /></span>
					<span id="dateby" class="card-title center" style="font-size: 16px"></span>
				</div>
				<div id="convertor" class="card-content col s12 m6 offset-m3 l12" style="padding-top: 0; padding-bottom: 32px; overflow: hidden;">
					<span id="bodyby"></span>
				</div>
			</div>
			<script type="text/javascript" src="https://kurs.blr.cc/js/calculator.js"></script>
			<script>
				var date = $("#dateby").html();
				if ('${locale}' !== 'ru') {
					$("#dateby").html(date.replace("Ha","On"));
				}
				$('table tr:last').remove();
			</script>
		</div>
		<!-- END CURRENCY CONVERTER -->

	</div>
	<div class="col s12 l8">
	
		<!-- START TOUR CARDS -->
		<c:forEach var="tour" items="${gridItems}" varStatus="loopCounter">
		
			<!-- START CARDS FOR MED/LARGE SCREEN -->
			<div class="row hide-on-small-only" style="margin-left: 0; margin-right: 0">
				<div class="card hoverable hvr-grow-my horizontal roundborder tour-${tour.id}" style="margin: 0; overflow: hidden">
				
					<c:if test="${loopCounter.index % 2 != 0}">
						<div class="card-image waves-effect waves-light tourImgBackground" style="background-image: url(${tour.image}); z-index: 1; border-radius: 2.5vh !important; border-width: 1vh; border-style: solid; border-color: white">
							<a href="${baseUrl}/${tour.id}"> 
								<img src="${tour.image}" style="opacity: 0; border-radius: 2.5vh"> 
								<span class="card-title" style="z-index: 999; padding: 0 24px; top: 24px">
									<span class="new badge roundborder z-depth-1" data-badge-caption="" style="margin-left: 0; margin-right: 1rem; background-color: #e16151; padding: 0 8px 0 6px">
										<i class="material-icons left pt10" style="line-height: 24px; margin-right: 0.5rem">favorite_border</i><span class="favoriteCount-${tour.id}">${tour.favoriteCount}</span>
									</span>
									<c:if test="${tour.isNew == true}">
										<span class="new badge roundborder z-depth-1" data-badge-caption="<mytaglib:i18n key="new" />" style="margin-left: 0; margin-right: 1rem; background-color: #26c6da"></span>
									</c:if>	
								</span>
								<span class="card-title center" style="background-color: #e16151f0; width: 100%; padding: 1vh">
									<span class="light">${tour.name}</span>
								</span>
							</a>
						</div>
					</c:if>	
					
					<div class="card-stacked">
						
						<div class="card-content light flexCenterOnPage">
							<div class="pt20 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">public</i>${tour.countryName}</div>
							<div class="pt20 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">location_city</i>${tour.cityName}</div>
							<br/>
							<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">local_atm</i>
								<c:if test="${customerBonus == 0}">
									${tour.price}
								</c:if>
								<c:if test="${customerBonus > 0}">
									<strike><span style="color: #ff5722">${tour.price}</span></strike>
									&nbsp;
									<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
								</c:if>
							</div>
							<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">landscape</i>${tour.tourTypeName}</div>	
							<div class="pt16 valign-wrapper-center center-div"><i class="material-icons" style="margin-right: 1vh">hotel</i>${tour.nights}</div>								
						</div>
							
						<!-- START BUTTONS -->
						<sec:authorize access="hasAnyRole('admin','moderator')">
								<div class="fixed-action-btn toolbar toolbarEnabled" style="position: absolute">
									<a class="btn-floating btn-small waves-effect waves-light cyan lighten-1"><i class="material-icons">menu</i></a>
									<ul>
										<li class="waves-effect waves-light hvr-icon-rotate"><a href="${baseUrl}/edit/${tour.id}"><i class="material-icons hvr-icon">edit</i></a></li>
										<c:if test="${ tour.tourStatus eq 'active'}">
											<li class="waves-effect waves-light deleteoractivate-${tour.id} hvr-icon-shrink"><a><i class="material-icons hvr-icon">delete</i></a></li>
										</c:if>
										<c:if test="${ tour.tourStatus eq 'inactive'}">
											<li class="waves-effect waves-light deleteoractivate-${tour.id} hvr-icon-shrink"><a><i class="material-icons hvr-icon">restore_from_trash</i></a></li>
										</c:if>
									</ul>
								</div>
						</sec:authorize>
							
						<sec:authorize access="hasRole('user')">
							<div class="fixed-action-btn hvr-bounce-in" style="position: absolute">
								<c:if test="${tour.favorite == true}">
									<a class="btn-floating waves-effect waves-light addToFavorite-${tour.id} gradient-45deg-red-red btn-small" onclick="addToFavorite('${contextPath}', '${tour.id}');"><i class="material-icons">favorite</i></a>
								</c:if>
								<c:if test="${tour.favorite == false}">
									<a class="btn-floating waves-effect waves-light addToFavorite-${tour.id} cyan lighten-1 btn-small" onclick="addToFavorite('${contextPath}', '${tour.id}');"><i class="material-icons">favorite_border</i></a>
								</c:if>
							</div>
						</sec:authorize>
						<!-- END BUTTONS -->
							
					</div>
						
					<c:if test="${loopCounter.index % 2 == 0}">
						<div class="card-image waves-effect waves-light tourImgBackground" style="background-image: url(${tour.image}); z-index: 1; border-radius: 2.5vh !important; border-width: 1vh; border-style: solid; border-color: white">
							<a href="${baseUrl}/${tour.id}"> 
								<img src="${tour.image}" style="opacity: 0; border-radius: 2.5vh"> 
								<span class="card-title" style="z-index: 999; padding: 0 24px; top: 24px">
									<span class="new badge roundborder z-depth-1" data-badge-caption="" style="margin-left: 0; margin-right: 1rem; background-color: #26c6da; padding: 0 8px 0 6px">
										<i class="material-icons left pt10" style="line-height: 24px; margin-right: 0.5rem">favorite_border</i><span class="favoriteCount-${tour.id}">${tour.favoriteCount}</span>
									</span>
									<c:if test="${tour.isNew == true}">
										<span class="new badge roundborder z-depth-1" data-badge-caption="<mytaglib:i18n key="new" />" style="margin-left: 0; margin-right: 1rem; background-color: #e16151"></span>
									</c:if>	
								</span>
								<span class="card-title center" style="background-color: #26c6daf0; width: 100%; padding: 1vh">
									<span class="light">${tour.name}</span>
								</span>
							</a>
						</div>
					</c:if>
						
				</div>
			</div>
			<!-- END CARDS FOR MED/LARGE SCREEN -->
			
			<!-- START CARDS FOR SMALL SCREEN -->
			<div class="row hide-on-med-and-up">
				<div class="col s12" style="margin-bottom: 1.5vh">
				
					<div class="card hoverable hvr-grow-my roundborder" style="margin: 0; overflow: hidden">
					
						<div class="card-image waves-effect waves-light roundtopborder" style="border-width: 1vh; border-style: solid; border-color: white">
							<a href="${baseUrl}/${tour.id}"> 
								<img src="${tour.image}" style="border-radius: 1.2vh"> 
								<span class="card-title" style="z-index: 999; padding: 0 12px; bottom: 12px">
									<span class="new badge roundborder z-depth-1" data-badge-caption="" style="margin-left: 0; margin-right: 1rem; background-color: #26c6da; padding: 0 8px 0 6px">
										<i class="material-icons left pt10" style="line-height: 24px; margin-right: 0.5rem">favorite_border</i><span class="favoriteCount-${tour.id}">${tour.favoriteCount}</span>
									</span>
									<c:if test="${tour.isNew == true}">
										<span class="new badge roundborder z-depth-1" data-badge-caption="<mytaglib:i18n key="new" />" style="margin-left: 0; margin-right: 1rem; background-color: #e16151"></span>
									</c:if>	
								</span>
							</a>
						</div>
							
						<div class="card-stacked tour-${tour.id}">
						
							<div class="card-content center" style="padding: 0 2vh 1vh 2vh">
								<a href="${contextPath}/tours/${tour.id}" style="color: black">
									<span class="light pt14">${tour.name}</span>
								</a>
							</div>
						
							<div class="card-content flexCenterOnPage" style="padding: 1vh 2vh 2vh 2vh">
								<table class="myTable light">
									<tr>
										<td>
											<div class="pt14 valign-wrapper-center center-div"><i class="material-icons pt16" style="margin-right: 1vh">public</i>${tour.countryName}</div>
											<div class="pt14 valign-wrapper-center center-div"><i class="material-icons pt16" style="margin-right: 1vh">location_city</i>${tour.cityName}</div>
										</td>
										<td>
											<div class="pt14 valign-wrapper-center center-div"><i class="material-icons pt16" style="margin-right: 1vh">local_atm</i>
												<c:if test="${customerBonus == 0}">
													${tour.price}
												</c:if>
												<c:if test="${customerBonus > 0}">
													<strike><span style="color: #ff5722">${tour.price}</span></strike>
													&nbsp;
													<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
												</c:if>
											</div>
											<div class="pt14 valign-wrapper-center center-div"><i class="material-icons pt16" style="margin-right: 1vh">landscape</i>${tour.tourTypeName}</div>	
											<div class="pt14 valign-wrapper-center center-div"><i class="material-icons pt16" style="margin-right: 1vh">hotel</i>${tour.nights}</div>								
										</td>
									</tr>
								</table>
							</div>
							
							<!-- START BUTTONS -->
							<sec:authorize access="hasAnyRole('admin','moderator')">
									<div class="fixed-action-btn toolbar toolbarEnabled" style="position: absolute">
										<a class="btn-floating btn-small waves-effect waves-light cyan lighten-1"><i class="material-icons">menu</i></a>
										<ul>
											<li class="waves-effect waves-light"><a href="${baseUrl}/edit/${tour.id} hvr-icon-rotate"><i class="material-icons hvr-icon">edit</i></a></li>
											<c:if test="${ tour.tourStatus eq 'active'}">
												<li class="waves-effect waves-light deleteoractivate-${tour.id} hvr-icon-shrink"><a><i class="material-icons hvr-icon">delete</i></a></li>
											</c:if>
											<c:if test="${ tour.tourStatus eq 'inactive'}">
												<li class="waves-effect waves-light deleteoractivate-${tour.id} hvr-icon-shrink"><a><i class="material-icons hvr-icon">restore_from_trash</i></a></li>
											</c:if>
										</ul>
									</div>
							</sec:authorize>
							<script>
								deleteOrActivate('${contextPath}', '${tour.id}', '${tour.name}', '${tour.tourStatus}');
							</script>
								
							<sec:authorize access="hasRole('user')">
								<div class="fixed-action-btn hvr-bounce-in" style="position: absolute">
									<c:if test="${tour.favorite == true}">
										<a class="btn-floating waves-effect waves-light addToFavorite-${tour.id} gradient-45deg-red-red btn-small" onclick="addToFavorite('${contextPath}', '${tour.id}');"><i class="material-icons ">favorite</i></a>
									</c:if>
									<c:if test="${tour.favorite == false}">
										<a class="btn-floating waves-effect waves-light addToFavorite-${tour.id} cyan lighten-1 btn-small btn-small" onclick="addToFavorite('${contextPath}', '${tour.id}');"><i class="material-icons ">favorite_border</i></a>
									</c:if>
								</div>
							</sec:authorize>
							<!-- END BUTTONS -->
								
						</div>
						
				</div>
				</div>
			</div>
			<!-- END CARDS FOR SMALL SCREEN -->
		
		</c:forEach>
		<!-- END TOUR CARDS -->
		
	</div>
</div>
<!-- END TOURS PAGE CONTENT -->

<!-- EXPORT PAGE PAGING -->
<jspFragments:paging />
<br />