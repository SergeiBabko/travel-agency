<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- START TOUR DESCRIPTION -->
<div class="card hoverable roundborder" style="overflow: hidden">

	<div class="card-header light gradient-45deg-light-blue-indigo">
		<div class="card-content" style="padding: 1rem">
			<div class="pt16 valign-wrapper-center center-div hide-on-small-only"><i class="material-icons" style="margin-right: 1vh">insert_comment</i><mytaglib:i18n key="tour.description" />:</div>
			<div class="pt14 valign-wrapper-center center-div hide-on-med-and-up"><i class="material-icons" style="margin-right: 1vh">insert_comment</i><mytaglib:i18n key="tour.description" />:</div>
		</div>
	</div>
	<div class="card-content light tour-${formModel.id}" style="padding: 1rem 24px 24px 24px">
		<pre style="margin: 0"><span class="inner-pre">${formModel.description}</span></pre>
	</div>

</div>
<!-- END TOUR DESCRIPTION -->

<!-- START ALL COMMENTS  -->
<ul class="collapsible light z-depth-0">
	<li>
		<div class="collapsible-header" style="background-color: transparent">
			<button class="btn light roundborder black-text valign-wrapper-center center-div pt12 hide-on-small-only" style="background-color: #d1c4e9"><i class="material-icons" style="margin-right: 0.5vh">forum</i><mytaglib:i18n key="reviews" /></button> 
			<button class="btn light roundborder black-text valign-wrapper-center center-div pt10 hide-on-med-and-up" style="background-color: #d1c4e9"><i class="material-icons" style="margin-right: 0.5vh">forum</i><mytaglib:i18n key="reviews" /></button> 
		</div>
		<div class="collapsible-body gradient-45deg-deep-purple-purple z-depth-1 comments roundborder">

			<!-- START COMMENT ADD FIELD  -->
			<sec:authorize access="hasRole('user')">
				<ul class="collapsible light z-depth-0" style="margin: 0 0 1.5rem 0">
					<li>
						<div class="collapsible-header" style="background-color: transparent">
							<button class="btn light roundborder black-text valign-wrapper-center center-div pt12 hide-on-small-only" style="background-color: #ebfffa"><i class="material-icons" style="margin-right: 0.5vh">rate_review</i><mytaglib:i18n key="leaveYourReview" /></button> 
							<button class="btn light roundborder black-text valign-wrapper-center center-div pt10 hide-on-med-and-up" style="background-color: #ebfffa"><i class="material-icons" style="margin-right: 0.5vh">rate_review</i><mytaglib:i18n key="leaveYourReview" /></button>
						</div>
						<div class="collapsible-body z-depth-1 hoverable comments roundborder" style="background-color: #ebfffa">
							<div class="row" style="margin-bottom: 0">
								<div class="input-field col s4 m2 offset-m2" style="margin-bottom: 0">
									<select id ="rating" class="rating"></select> 
									<label for="rating"><mytaglib:i18n key="rating" /></label>
								</div>
								<div class="input-field col s8" style="margin-bottom: 0">
									<select class="tourDateReview"></select> 
									<label for="tourDateReview"><mytaglib:i18n key="dates" /></label>
								</div>
								<div class="col s3 m2 flexCenterOnPage">
									<img src="${contextPath}/resources/img/user.png" class="circle z-depth-2 responsive-img" />
								</div>
								<div class="input-field col s9 m10">
									<textarea id="reviewMessage" class="reviewMessage materialize-textarea"></textarea>
									<label for="reviewMessage"><mytaglib:i18n key="woym" /></label>
								</div>
							</div>
							<div class="row" style="margin-bottom: 0.5vh; margin-top: 0.5vh">
								<div class="col s6">
									<div class="chip" style="margin-bottom: 0">
										<span><mytaglib:user-name></mytaglib:user-name></span>
									</div>
								</div>
								<div class="col s6 right-align">
									<button class="rateTour btn btn-small roundborder z-depth-0 waves-effect waves-light gradient-45deg-cyan-light-green" style="height: 32px">
										<mytaglib:i18n key="rate" /><i class="material-icons left">rate_review</i>
									</button>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</sec:authorize>
			<!-- END COMMENT ADD FIELD  -->
			
			<!-- START ALL COMMENTS -->
			<c:forEach var="review" items="${tourReviews}" varStatus="loopCounter">
				<div class="card hoverable review-${review.id} roundborder">
					<div class="card-content" style="padding-bottom: 0">
						<div class="row" style="margin-bottom: 0">
							<div class="col s3 m2">
								<img src="${contextPath}/resources/img/user.png" alt="" class="circle z-depth-2 responsive-img">
							</div>
							<div class="col s9 m10">
								<span class="pt14">${review.userFirstName}&nbsp;${review.userLastName}</span>
								<span class="chip right hide-on-small-only" style="margin: 0; padding: 0 0.7vh; height: 2.5vh; line-height: 2.5vh">
									<c:forEach var = "i" begin = "1" end = "${review.rating}" >
										<i class="material-icons pt11" style="line-height: inherit">star</i>
									</c:forEach>
								</span>
							</div>
							<div class="col s9 m10">
								<span class="pt11 grey-text text-darken-1">
									<fmt:formatDate pattern="dd MMMM y" value="${review.created}" />
								</span>
								<span class="pt11 grey-text text-darken-1 right hide-on-small-only">
									<fmt:formatDate pattern="dd.M.y" value="${review.tourDateStart}" /> &ndash; <fmt:formatDate pattern="dd.M.y" value="${review.tourDateEnd}" />
								</span>
							</div>
						</div>
						<div class="row">
							<div class="col s12">
								<div class="center hide-on-med-and-up">
									<div class="chip center" style="margin: 0; padding: 0 0.7vh; height: 2.5vh; line-height: 2.5vh">
										<c:forEach var = "i" begin = "1" end = "${review.rating}" >
											<i class="material-icons pt11" style="line-height: inherit">star</i>
										</c:forEach>
									</div>
									<div class="pt11 grey-text text-darken-1">
										<fmt:formatDate pattern="dd.M.y" value="${review.tourDateStart}" /> &ndash; <fmt:formatDate pattern="dd.M.y" value="${review.tourDateEnd}" />
									</div>
								</div>
								<blockquote style="border-left: 5px solid #d1c4e9">
									<pre class="inner-pre">${review.review}</pre>
								</blockquote>
							</div>
						</div>
					</div>
			<!-- END ALL COMMENTS -->
						
					<!-- START BUTTONS -->
					<sec:authorize access="hasAnyRole('admin','moderator')">
						<div class="fixed-action-btn" style="position: absolute">
							<a class="btn-floating btn-small waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteReview('${contextPath}', '${review.id}');"><i class="material-icons">delete</i></a>
						</div>
					</sec:authorize>
					<sec:authorize access="hasRole('user')">
						<c:if test="${review.customerId == customerAccount.id}">
							<div class="fixed-action-btn" style="position: absolute">
								<a class="btn-floating btn-small waves-effect waves-light red lighten-1 hvr-shrink" onclick="deleteReview('${contextPath}', '${review.id}');"><i class="material-icons">delete</i></a>
							</div>
						</c:if>
					</sec:authorize>
					<!-- END BUTTONS -->
						
				</div>
			</c:forEach>
		</div>
	</li>
</ul>
<!-- END ALL COMMENTS -->
