<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="${contextPath}/resources/css/customer.css">

<c:set var="baseUrl" value="${contextPath}/customer" />

<!-- START USER ACCOUNT HEADER -->
<div class="row">
	<br /> <br />
	<div class="col s12">
		<div class="card roundborder" style="border-style: solid; border-color: white; border-width: 0.5vh">
		
			<div class="card-image waves-effect waves-light centerInDiv darken roundtopborder" style="max-height: 200px">
				<div class="mask">
					<img src="${contextPath}/resources/img/travel.png">
				</div>
				<div class="card-title">
					<img src="${contextPath}/resources/img/user.png" style="width: 76px; margin-bottom: 1vh">
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
						<li class="tab col s3"><a class="white-text light" href="#booked"><mytaglib:i18n key="customer.booked" /></a></li>
						<li class="tab col s3"><a class="white-text light" href="#favorite"><mytaglib:i18n key="customer.favorite" /></a></li>
						<li class="tab col s3"><a class="white-text light" href="#review"><mytaglib:i18n key="customer.review" /></a></li>
						<li class="tab col s3"><a class="white-text light" href="#account"><mytaglib:i18n key="customer.account" /></a></li>
					</ul>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- END USER ACCOUNT HEADER -->

<!-- START USER ACCOUNT BOOKED TOURS -->
<div id="booked">
	<div class="row" style="margin-bottom: 4vh">
		<c:if test="${not empty booked}">
			<div class="col s12 l6" style="margin: 0.25vh 0 0.25vh 0">
						
				<!-- START EVEN CARDS -->
				<ul class="collapsible roundborder" style="margin: 0">
					<c:forEach var="booked" items="${booked}" varStatus="loopCounter">
						<c:if test="${loopCounter.index % 2 == 0 }">
							<li>
								<div class="collapsible-header booked-${booked.id}" style="padding: 0">
									<ul class="collection roundborder" style="width: 100%; margin: 0">
										<li class="collection-item avatar" style="padding-left: 92px">
											<a href="${contextPath}/tours/${booked.tourId}"><img src="${booked.tourImage}" class="circle" style="width: 64px; height: 64px"></a>
											<span class="title valign-wrapper-center">
												<i class="material-icons pt14" style="margin-right: 1vh">account_balance</i>${booked.tourName}
												<c:if test="${booked.processed == true }">
													<i class="material-icons" style="margin-left: 1vh; color: #4caf50">done</i>
												</c:if>
											</span>
											<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh">calendar_today</i><fmt:formatDate pattern="dd.M.y" value="${booked.tourDateStart}" /> &ndash; <fmt:formatDate pattern="dd.M.y" value="${booked.tourDateEnd}" /></span>
											<span class="valign-wrapper-center">
												<i class="material-icons pt14" style="margin-right: 1vh">wc</i>${booked.numPerson}
												<i class="material-icons pt14" style="margin-right: 1vh; margin-left: 1vh">hotel</i>${booked.tourNights}
												<i class="material-icons pt14" style="margin-right: 1vh; margin-left: 1vh">local_atm</i>${booked.price}
											</span>
																						
											<c:if test="${booked.processed != true }">
												<div class="fixed-action-btn secondary-content" style="position: absolute">
													<a class="btn-floating btn-small waves-effect waves-light gradient-45deg-red-red hvr-shrink" onclick="deleteBookedTour('${contextPath}', '${booked.id}');"><i class="material-icons pt14" style="margin-right: 0">delete</i></a>
												</div>
												</c:if>
											<c:if test="${booked.processed == true }">
												<div class="fixed-action-btn secondary-content" style="position: absolute">
													<a class="btn-floating btn-small waves-effect waves-light cyan lighten-1 hvr-shrink" onclick="deleteBookedTour('${contextPath}', '${booked.id}');"><i class="material-icons pt14" style="margin-right: 0">delete</i></a>
												</div>
											</c:if>
										</li>
									</ul>
								</div>
																				
								<div class="collapsible-body booked-${booked.id}">
									<c:if test="${empty booked.message}">
										<pre class="inner-pre light"><mytaglib:i18n key="customer.nomessage" /></pre>
									</c:if>
									<c:if test="${not empty booked.message}">
										<pre class="inner-pre light">${booked.message}</pre>
									</c:if>
								</div>
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<!-- END EVEN CARDS -->
							
			</div>
			<div class="col s12 l6" style="margin: 0.25vh 0 0.25vh 0">
				
				<!-- START ODD CARDS -->
				<c:if test="${fn:length(booked) gt 1}">
					<ul class="collapsible roundborder" style="margin: 0">
						<c:forEach var="booked" items="${booked}" varStatus="loopCounter">
							<c:if test="${loopCounter.index % 2 != 0 }">
								<li>
									<div class="collapsible-header booked-${booked.id}" style="padding: 0">
										<ul class="collection roundborder" style="width: 100%; margin: 0">
											<li class="collection-item avatar" style="padding-left: 92px">
												<a href="${contextPath}/tours/${booked.tourId}"><img src="${booked.tourImage}" class="circle" style="width: 64px; height: 64px"></a>
												<span class="title valign-wrapper-center">
													<i class="material-icons pt14" style="margin-right: 1vh">account_balance</i>${booked.tourName}
													<c:if test="${booked.processed == true }">
														<i class="material-icons" style="margin-left: 1vh; color: #4caf50">done</i>
													</c:if>
												</span>
												<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh">calendar_today</i><fmt:formatDate pattern="dd.M.y" value="${booked.tourDateStart}" /> &ndash; <fmt:formatDate pattern="dd.M.y" value="${booked.tourDateEnd}" /></span>
												<span class="valign-wrapper-center">
													<i class="material-icons pt14" style="margin-right: 1vh">wc</i>${booked.numPerson}
													<i class="material-icons pt14" style="margin-right: 1vh; margin-left: 1vh">hotel</i>${booked.tourNights}
													<i class="material-icons pt14" style="margin-right: 1vh; margin-left: 1vh">local_atm</i>${booked.price}
												</span>
																							
												<c:if test="${booked.processed != true }">
													<div class="fixed-action-btn secondary-content" style="position: absolute">
														<a class="btn-floating btn-small waves-effect waves-light  gradient-45deg-red-red hvr-shrink" onclick="deleteBookedTour('${contextPath}', '${booked.id}');"><i class="material-icons pt14" style="margin-right: 0">delete</i></a>
													</div>
													</c:if>
												<c:if test="${booked.processed == true }">
													<div class="fixed-action-btn secondary-content" style="position: absolute">
														<a class="btn-floating btn-small waves-effect waves-light cyan lighten-1 hvr-shrink" onclick="deleteBookedTour('${contextPath}', '${booked.id}');"><i class="material-icons pt14" style="margin-right: 0">delete</i></a>
													</div>
												</c:if>
											</li>
										</ul>
									</div>
																			
									<div class="collapsible-body booked-${booked.id}">
										<c:if test="${empty booked.message}">
											<pre class="inner-pre light"><mytaglib:i18n key="customer.nomessage" /></pre>
										</c:if>
										<c:if test="${not empty booked.message}">
											<pre class="inner-pre light">${booked.message}</pre>
										</c:if>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
				<!-- END ODD CARDS -->
							
			</div>
		</c:if>
	</div>
</div>
<!-- END USER ACCOUNT BOOKED TOURS -->
	
<!-- START USER ACCOUNT FAVORITED TOURS -->
<div id="favorite">
	<div class="row" style="margin-bottom: 4vh">
		<c:if test="${not empty favorites}">
			<div class="col s12 l6" style="margin: 0.25vh 0 0 0">
						
				<!-- START EVEN CARDS -->
				<c:forEach var="favorite" items="${favorites}" varStatus="loopCounter">
					<c:if test="${loopCounter.index % 2 == 0 }">
						<ul class="collection roundborder z-depth-1 favorite-${favorite.tourId}" style="width: 100%; margin: 0 0 0.5vh 0">
							<li class="collection-item avatar" style="padding-left: 92px">
								<a href="${contextPath}/tours/${favorite.tourId}"><img src="${favorite.tourImage}" class="circle" style="width: 64px; height: 64px"></a>
								<span class="title valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh; margin-left: 0.4vh">account_balance</i>${favorite.tourName}</span>
								<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh; margin-left: 0.4vh">local_atm</i>
									<c:if test="${customer.bonus == 0 || empty customer.bonus}">
										${favorite.tourPrice}
									</c:if>
									<c:if test="${customer.bonus > 0}">
										<strike><span style="color: #ff5722">${favorite.tourPrice}</span></strike>
										&nbsp;
										<span style="color: #4caf50">${favorite.tourPrice - favorite.tourPrice*customer.bonus/100}</span>
									</c:if>
								</span>
								<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh; margin-left: 0.4vh">hotel</i>${favorite.tourNights}</span>
								<div class="fixed-action-btn secondary-content" style="position: absolute">
									<a class="btn-floating btn-small waves-effect waves-light addToFavorite-${favorite.tourId} gradient-45deg-red-red hvr-shrink" onclick="addToFavorite('${contextPath}', '${favorite.tourId}');"><i class="material-icons pt14">delete</i></a>
								</div>
							</li>
						</ul>
					</c:if>
				</c:forEach>
				<!-- END EVEN CARDS -->
							
			</div>
				
			<div class="col s12 l6" style="margin: 0.25vh 0 0 0">
						
				<!-- START ODD CARDS -->
				<c:forEach var="favorite" items="${favorites}" varStatus="loopCounter">
					<c:if test="${loopCounter.index % 2 != 0 }">
						<ul class="collection roundborder z-depth-1 favorite-${favorite.tourId}" style="width: 100%; margin: 0 0 0.5vh 0">
							<li class="collection-item avatar" style="padding-left: 92px">
								<a href="${contextPath}/tours/${favorite.tourId}"><img src="${favorite.tourImage}" class="circle" style="width: 64px; height: 64px"></a>
								<span class="title valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh; margin-left: 0.4vh">account_balance</i>${favorite.tourName}</span>
								<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh; margin-left: 0.4vh">local_atm</i>
									<c:if test="${customer.bonus == 0 || empty customer.bonus}">
										${favorite.tourPrice}
									</c:if>
									<c:if test="${customer.bonus > 0}">
										<strike><span style="color: #ff5722">${favorite.tourPrice}</span></strike>
										&nbsp;
										<span style="color: #4caf50">${favorite.tourPrice - favorite.tourPrice*customer.bonus/100}</span>
									</c:if>
								</span>
								<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh; margin-left: 0.4vh">hotel</i>${favorite.tourNights}</span>
								<div class="fixed-action-btn secondary-content" style="position: absolute">
									<a class="btn-floating btn-small waves-effect waves-light addToFavorite-${favorite.tourId} gradient-45deg-red-red hvr-shrink" onclick="addToFavorite('${contextPath}', '${favorite.tourId}');"><i class="material-icons pt14">delete</i></a>
								</div>
							</li>
						</ul>
					</c:if>
				</c:forEach>
				<!-- END ODD CARDS -->
							
			</div>
		</c:if>
	</div>
</div>	
<!-- END USER ACCOUNT FAVORITED TOURS -->	

<!-- START USER ACCOUNT REVIEWS -->
<div id="review">
	<div class="row" style="margin-bottom: 4vh">
		<c:if test="${not empty review}">
			<div class="col s12" style="margin: 0.25vh 0 0.25vh 0">
						
				<!-- START CARDS -->
				<ul class="collapsible roundborder" style="margin: 0">
					<c:forEach var="review" items="${review}" varStatus="loopCounter">
						<li>
							<div class="collapsible-header review-${review.id}" style="padding: 0">
								<ul class="collection roundborder" style="width: 100%; margin: 0">
									<li class="collection-item avatar" style="padding-left: 92px">
										<a href="${contextPath}/tours/${review.tourId}"><img src="${review.tourImage}" class="circle" style="width: 64px; height: 64px"></a>
										<span class="title valign-wrapper-center">
											<i class="material-icons pt14" style="margin-right: 1vh">account_balance</i>${review.tourName}
										</span>
										<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh">calendar_today</i><fmt:formatDate pattern="dd.M.y" value="${review.tourDateStart}" /> &ndash; <fmt:formatDate pattern="dd.M.y" value="${review.tourDateEnd}" /></span>
													
										<span class="valign-wrapper-center"><i class="material-icons pt14" style="margin-right: 1vh">stars</i>
											<span class="chip" style="margin: 0; padding: 0 0.7vh; height: 2.5vh; line-height: 2.5vh">
												<c:forEach var = "i" begin = "1" end = "${review.rating}" >
													<i class="material-icons pt11" style="line-height: inherit; margin: 0; width: 1rem">star</i>
												</c:forEach>
											</span>
										</span>
										<div class="fixed-action-btn secondary-content" style="position: absolute">
											<a class="btn-floating btn-small waves-effect waves-light gradient-45deg-red-red hvr-shrink" onclick="deleteReview('${contextPath}', '${review.id}');"><i class="material-icons pt14" style="margin-right: 0">delete</i></a>
										</div>
									</li>
								</ul>
							</div>
																			
							<div class="collapsible-body review-${review.id}">
								<pre class="inner-pre light">${review.review}</pre>
							</div>
						</li>
					</c:forEach>
				</ul>
				<!-- END CARDS -->
							
			</div>
		</c:if>
	</div>
</div>	
<!-- END USER ACCOUNT REVIEWS -->

<!-- START MANAGE USER ACCOUNT -->
<div id="account" class="row" style="margin-bottom: 2vh">
	<div class="col s12" style="margin: 0">
	 
		<div class="card roundborder" style="margin: 0.25vh 0 1rem 0; margin-bottom: 3.5vh">
			<div class="card-content">
				<span class="card-title center"><mytaglib:i18n key="manageAcc" /></span>
				<br/>
				<div class="row">
				
					<div class="col s12 m6">
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

						<form:form id="first" modelAttribute="customer">
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
						</form:form>
					</div>
					
					<div class="col s12 m6">	
						<form:form id="second" modelAttribute="customer">
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
					</div>
					
					<div class="input-field col s12 center-align">
						<button class="btn waves-effect waves-light roundborder gradient-45deg-cyan-light-green" onclick="updateCustomer('${contextPath}', '${customer.id}');">
							<mytaglib:i18n key="update" /><i class="material-icons left">check</i>
						</button>
					</div>
					<div class="col s12 center-align">
						<a class="btn-flat btn-small waves-effect roundborder" onclick="deactivateCustomer('${contextPath}', '${customer.id}');"><mytaglib:i18n key="deleteAcc" /></a>
					</div>
					
				</div>
			</div>	
		</div>
		
		<!-- START CHANGE PASSWORD -->
		<div class="row center-align">
			<div class="col s12 l6 offset-l3">
				<ul class="collapsible roundborder" style="margin-bottom: 0">
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
									<button class="btn waves-effect waves-light roundborder gradient-45deg-cyan-light-green" onclick="updatePassword('${contextPath}', '${customer.id}');">
										<mytaglib:i18n key="update" /><i class="material-icons left">check</i>
									</button>
								</div>
							</div>			
						</div>
					</li>
				</ul>
			</div>
		</div>
		<!-- END CHANGE PASSWORD -->
		
	</div>
</div>
<!-- END MANAGE USER ACCOUNT -->

<script>
	$('.tabs').tabs({
		swipeable : false
	});
</script>
