<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<style>
	table {
		border-spacing: 1vh;
		border-collapse: separate;
	}
	
	td {
		-webkit-box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
		box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12), 0 1px 5px 0 rgba(0, 0, 0, 0.2);
	}
	
	.image {
		background-size: cover !important;
		background-position: center !important; 
		background-repeat : no-repeat !important;
		border-radius: 2vh;
		border-style: solid;
		border-width: 1vh;
		border-color: white;
		padding: 0;
	}
	
	tr {
		border: 0;
	}
	
	th, td {
		padding: 15px;
		vertical-align: bottom;
	}
	
	th, td, tr {
		cursor: pointer;
	}
</style>

<br />

<div class="row" style="margin-bottom: 34px">
	<h4 class="light">
		<span class="col s6"><mytaglib:i18n key="index.latestnews" /></span>
		<span class="col s6 right-align">
			<a class="btn-small waves-effect waves-light cyan lighten-1 roundborder hvr-icon-forward" data-target="sort" href="${contextPath}/news"><mytaglib:i18n key="index.allnews" /><i class="material-icons right hvr-icon" style="margin-left: 1vh">arrow_right</i></a>
		</span>
	</h4>
</div>

<!-- START NEWS CARDS FOR LARGE SCREEN -->
<div class="row hide-on-small-only">
	<table style="width: 100%; height: 500px">
		<tr>
			<c:forEach var="news" items="${news}" begin="0" end ="0">
				<td class="hoverable image modal-trigger" href="#${news.id}" rowspan="2" style="background: url(${news.image}); width: 65%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
					    border-style: solid;
					    border-width: 0 1vh 1vh 1vh;
					    border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${news.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 2vh 0.5vh 2vh">
							<span class="card-title">${news.name}</span>
						</div>
					</div>
				</td>
			</c:forEach>
			<c:forEach var="news" items="${news}" begin="1" end ="1">
				<td class="hoverable image modal-trigger" href="#${news.id}" style="background: url(${news.image}); width: 35%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
					    border-style: solid;
					    border-width: 0 1vh 1vh 1vh;
					    border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${news.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -1vh -0.5vh -0.5vh -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 1vh 1vh 1vh">
							<span class="pt12 light">${news.name}</span>
						</div>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="news" items="${news}" begin="2" end ="2">
				<td class="hoverable image modal-trigger" href="#${news.id}" style="background: url(${news.image}); width: 35%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
					    border-style: solid;
					    border-width: 0 1vh 1vh 1vh;
					    border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${news.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -1vh -0.5vh -0.5vh -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 1vh 1vh 1vh">
							<span class="pt12 light">${news.name}</span>
						</div>
					</div>
				</td>
			</c:forEach>
		</tr>
	</table>
</div>
<!-- END NEWS CARDS FOR LARGE SCREEN -->

<!-- START NEWS CARDS FOR SMALL/MIDDLE SCREEN -->
<div class="row">
	<c:forEach var="news" items="${news}" varStatus="loopCounter">
		<!-- START NEWS CARDS -->
		<div class="col s12 m4">
			<div class="card hoverable roundborder hide-on-med-and-up">

				<div class="card-image waves-effect waves-light roundtopborder" style="padding: 1vh">
					<a class="modal-trigger" href="#${news.id}"> 
						<img src="${news.image}" style="border-radius: 1.2vh"> 
						<span class="card-title" style="padding: 1vh; bottom: 1vh"> 
							<span class="new badge  roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${news.created}" />
							</span>
						</span>
					</a>
				</div>

				<div class="card-content center" style="padding: 0 2vh 1vh 2vh">
					<a class="modal-trigger" href="#${news.id}" style="color: black">
						<span class="light pt14">${news.name}</span>
					</a>
				</div>

			</div>
		</div>
		<!-- END NEWS CARDS -->
		
		<!-- START NEWS VIEW MODAL -->
		<div id="${news.id}" class="modal modal-big modal-fixed-footer roundborder">
			<div class="modal-content">
	
				<c:if test="${not empty news.image}">
					<div class="card z-depth-0 roundborder">
						<div class="card-image roundborder">
							<img class="materialboxed roundborder" src="${news.image}">	
							<span class="card-title center roundbottomborder" style="background-color: #26c6daf0; width: 100%; padding: 1vh 2vh">
								<span class="light" style="font-size: 2.5vh">${news.name}</span>
							</span>
						</div>
					</div>
				</c:if>
					
				<c:if test="${empty news.image}">
					<p class="center-align light" style="font-size: 3.5vh">${news.name}</p>
				</c:if>
					
				<pre><span class="inner-pre light">${news.text}</span></pre>
					
			</div>
			<div class="modal-footer">
				<p class="btn-flat light disabled left pt10" data-badge-caption=""><fmt:formatDate pattern="dd MMMM y, hh:mm a" value="${news.created}" /></p>
				<p class="modal-close waves-effect waves-red btn-flat roundborder"><i class="material-icons">close</i></p>
			</div>
		</div>
		<!-- END NEWS VIEW MODAL -->
			
	</c:forEach>
</div>
<!-- END NEWS CARDS FOR SMALL/MIDDLE SCREEN -->


<div class="row" style="margin-bottom: 34px">
	<h4 class="light">
		<span class="col s6 light"><mytaglib:i18n key="index.latesttours" /></span>
		<span class="col s6 right-align">
			<a class="btn-small waves-effect waves-light cyan lighten-1 roundborder hvr-icon-forward" data-target="sort" href="${contextPath}/tours"><mytaglib:i18n key="index.alltours" /><i class="material-icons right hvr-icon" style="margin-left: 1vh">arrow_right</i></a>
		</span>
	</h4>
</div>

<!-- START TOURS CARDS FOR LARGE SCREEN -->
<div class="row hide-on-small-only">
	<table style="width: 100%; height: 700px">
		<tr>
			<c:forEach var="tour" items="${tours}" begin="0" end ="0">
				<td class="hoverable image" onclick="location.href='${contextPath}/tours/${tour.id}'" style="background: url(${tour.image}); width: 35%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
						border-style: solid;
						border-width: 0 1vh 1vh 1vh;
						border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${tour.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -1vh -0.5vh -0.5vh -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 1vh 1vh 1vh">
							<span class="pt12 light">${tour.name}</span>
						</div>
						<div class="card-content pt11 flexCenterOnPage" style="padding: 0 0.5rem; padding-bottom: 1vh">
							<span class="light valign-wrapper-center center-div" style="justify-content: center; flex-flow: wrap">
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 0">local_atm</i>
								<c:if test="${customerBonus == 0}">
									${tour.price}
								</c:if>
								<c:if test="${customerBonus > 0}">
									<strike><span style="color: #ff5722">${tour.price}</span></strike>
									&nbsp;
									<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
								</c:if></p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">landscape</i>${tour.tourTypeName}</p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">hotel</i>${tour.nights}</p>
							</span>		
						</div>
					</div>
				</td>
			</c:forEach>
			<c:forEach var="tour" items="${tours}" begin="01" end ="1">
				<td colspan="2" rowspan="2" class="hoverable image" onclick="location.href='${contextPath}/tours/${tour.id}'" style="background: url(${tour.image})">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
						border-style: solid;
						border-width: 0 1vh 1vh 1vh;
						border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${tour.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 2vh 0.5vh 2vh">
							<span class="card-title" style="margin-bottom: 0">${tour.name}</span>
						</div>
						<div class="card-content pt14 flexCenterOnPage" style="padding: 0 0.5rem; padding-bottom: 1vh">
							<span class="light valign-wrapper-center center-div" style="justify-content: center; flex-flow: wrap">
								<p class="valign-wrapper-center"><i class="material-icons pt18" style="margin: 0 0.5rem 0 0">local_atm</i>
								<c:if test="${customerBonus == 0}">
									${tour.price}
								</c:if>
								<c:if test="${customerBonus > 0}">
									<strike><span style="color: #ff5722">${tour.price}</span></strike>
									&nbsp;
									<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
								</c:if></p>
								<p class="valign-wrapper-center"><i class="material-icons pt18" style="margin: 0 0.5rem 0 1rem">landscape</i>${tour.tourTypeName}</p>
								<p class="valign-wrapper-center"><i class="material-icons pt18" style="margin: 0 0.5rem 0 1rem">hotel</i>${tour.nights}</p>
							</span>								
						</div>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="tour" items="${tours}" begin="2" end ="2">
				<td rowspan="2" class="hoverable image" onclick="location.href='${contextPath}/tours/${tour.id}'" style="background: url(${tour.image}); width: 35%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
						border-style: solid;
						border-width: 0 1vh 1vh 1vh;
						border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${tour.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -1vh -0.5vh -0.5vh -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 1vh 1vh 1vh">
							<span class="pt12 light">${tour.name}</span>
						</div>
						<div class="card-content pt11 flexCenterOnPage" style="padding: 0 0.5rem; padding-bottom: 1vh">
							<span class="light valign-wrapper-center center-div" style="justify-content: center; flex-flow: wrap">
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 0">local_atm</i>
								<c:if test="${customerBonus == 0}">
									${tour.price}
								</c:if>
								<c:if test="${customerBonus > 0}">
									<strike><span style="color: #ff5722">${tour.price}</span></strike>
									&nbsp;
									<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
								</c:if></p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">landscape</i>${tour.tourTypeName}</p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">hotel</i>${tour.nights}</p>
							</span>										
						</div>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="tour" items="${tours}" begin="3" end ="3">
				<td class="hoverable image" onclick="location.href='${contextPath}/tours/${tour.id}'" style="background: url(${tour.image}); width: 32.5%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
						border-style: solid;
						border-width: 0 1vh 1vh 1vh;
						border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${tour.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -1vh -0.5vh -0.5vh -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 1vh 1vh 1vh">
							<span class="pt12 light">${tour.name}</span>
						</div>
						<div class="card-content pt11 flexCenterOnPage" style="padding: 0 0.5rem; padding-bottom: 1vh">
							<span class="light valign-wrapper-center center-div" style="justify-content: center; flex-flow: wrap">
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 0">local_atm</i>
								<c:if test="${customerBonus == 0}">
									${tour.price}
								</c:if>
								<c:if test="${customerBonus > 0}">
									<strike><span style="color: #ff5722">${tour.price}</span></strike>
									&nbsp;
									<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
								</c:if></p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">landscape</i>${tour.tourTypeName}</p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">hotel</i>${tour.nights}</p>
							</span>												
						</div>
					</div>
				</td>
			</c:forEach>
			<c:forEach var="tour" items="${tours}" begin="4" end ="4">
				<td class="hoverable image" onclick="location.href='${contextPath}/tours/${tour.id}'" style="background: url(${tour.image}); width: 32.5%">
					<div class="card z-depth-0" style="
						margin: -1vh -1vh -0.5vh -1vh;
						padding: 2vh;
						border-radius: 0 0 2vh 2vh;
						border-style: solid;
						border-width: 0 1vh 1vh 1vh;
						border-color: white;
						background-color: transparent;
						">
						<span class="card-content">
							<span class="new badge roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${tour.created}" />
							</span>
						</span>
					</div>
					<div class="card z-depth-0 roundbottomborder" style="margin: -1vh -0.5vh -0.5vh -0.5vh; border-radius: 0">
						<div class="card-content center-align" style="padding: 1vh 1vh 1vh 1vh">
							<span class="pt12 light">${tour.name}</span>
						</div>
						<div class="card-content pt11 flexCenterOnPage" style="padding: 0 0.5rem; padding-bottom: 1vh">
							<span class="light valign-wrapper-center center-div" style="justify-content: center; flex-flow: wrap">
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 0">local_atm</i>
								<c:if test="${customerBonus == 0}">
									${tour.price}
								</c:if>
								<c:if test="${customerBonus > 0}">
									<strike><span style="color: #ff5722">${tour.price}</span></strike>
									&nbsp;
									<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
								</c:if></p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">landscape</i>${tour.tourTypeName}</p>
								<p class="valign-wrapper-center"><i class="material-icons pt14" style="margin: 0 0.5rem 0 1rem">hotel</i>${tour.nights}</p>
							</span>								
						</div>
					</div>
				</td>
			</c:forEach>
		</tr>
	</table>
</div>
<!-- END TOURS CARDS FOR LARGE SCREEN -->

<!-- START TOURS CARDS FOR SMALL/MIDDLE SCREEN -->
<div class="row">
	<c:forEach var="tour" items="${tours}" varStatus="loopCounter">
		<div class="col s12 m4">
			<div class="card hoverable roundborder hide-on-med-and-up">

				<div class="card-image waves-effect waves-light roundtopborder" style="padding: 1vh">
					<a href="${contextPath}/tours/${tour.id}"> 
						<img src="${tour.image}" style="border-radius: 1.2vh"> 
						<span class="card-title" style="padding: 1vh; bottom: 1vh"> 
							<span class="new badge  roundborder left z-depth-1" data-badge-caption="" style="background-color: #e16151 !important">
								<fmt:formatDate pattern="dd MMMM" value="${tour.created}" />
							</span>
						</span>
					</a>
				</div>

				<div class="card-content center" style="padding: 0 2vh 1vh 2vh">
					<a href="${contextPath}/tours/${tour.id}" style="color: black">
						<span class="light pt14">${tour.name}</span>
					</a>
				</div>
				
				<div class="card-content flexCenterOnPage" style="padding: 0; padding-bottom: 2vh">
					<span class="light pt12 valign-wrapper-center center-div"><i class="material-icons pt14" style="margin-right: 1vh">local_atm</i>
						<c:if test="${customerBonus == 0}">
							${tour.price}
						</c:if>
						<c:if test="${customerBonus > 0}">
							<strike><span style="color: #ff5722">${tour.price}</span></strike>
							&nbsp;
							<span style="color: #4caf50">${tour.price - tour.price*customerBonus/100}</span>
						</c:if>
					<i class="material-icons pt14" style="margin-left: 2.5vh; margin-right: 1vh">landscape</i>${tour.tourTypeName}	
					<i class="material-icons pt14" style="margin-left: 2.5vh; margin-right: 1vh">hotel</i>${tour.nights}</span>								
				</div>

			</div>
		</div>
	</c:forEach>
</div>
<!-- END TOURS CARDS FOR SMALL/MIDDLE SCREEN -->

<br/>
