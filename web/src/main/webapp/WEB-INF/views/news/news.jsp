<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="baseUrl" value="${contextPath}/news" scope="request"/>

<br />

<!-- START PAGE NAME AND SORTING -->
<div class="row" style="margin-bottom: 32px">
	<h4>
		<span class="col s6 light"><mytaglib:i18n key="news" /></span>
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
</ul>
<!-- END PAGE NAME AND SORTING -->

<!-- START NEWS FLOATING ADD BUTTON -->
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
<!-- END NEWS FLOATING ADD BUTTON -->

<!-- START NEWS PAGE CONTENT -->
<div id="item-posts" class="row">
	<div class="item-sizer"></div>
	<c:forEach var="news" items="${gridItems}" varStatus="loopCounter">

		<!-- START NEWS CARDS -->
		<div class="item news-${news.id}">
			<div class="card roundborder hoverable hvr-grow-my">

				<c:if test="${not empty news.image}">
					<div class="card-image waves-effect waves-light roundborder" style="padding: 1vh">
						<a class="modal-trigger" href="#${news.id}"> 
							<img src="${news.image}" style="border-radius: 1.2vh"> 
							<span class="card-title" style="padding: 1vh; bottom: 1vh"> 
								<span class="new badge roundborder cyan lighten-1 left z-depth-1" data-badge-caption=""><fmt:formatDate pattern="dd MMMM" value="${news.updated}" /></span>
							</span>
						</a>
					</div>
				</c:if>

				<sec:authorize access="hasAnyRole('admin','moderator')">
					<div class="card-action" style="padding: 0; bottom: 1vh">
						<div class="fixed-action-btn" style="position: relative; float: right; z-index: 1">
							<a class="btn-floating waves-effect waves-light cyan lighten-1"><i class="material-icons">menu</i></a>
							<ul>
								<li><a class="btn-floating btn-small waves-effect waves-light teal lighten-3 hvr-icon-rotate" href="${baseUrl}/edit/${news.id}"><i class="material-icons hvr-icon">edit</i></a></li>
								<li><a class="btn-floating btn-small waves-effect waves-light red lighten-1 hvr-icon-shrink" onclick="deleteNews('${contextPath}', '${news.id}');"><i class="material-icons hvr-icon">delete</i></a></li>
							</ul>
						</div>
					</div>
				</sec:authorize>

				<div class="card-content center-align" style="padding-top: 0">
					<a class="modal-trigger" href="#${news.id}" style="color: black">
						<span class="card-title" style="font-weight: 300">${news.name}</span>
					</a>
					<p style="font-weight: 300">${news.shortText}</p>
				</div>

			</div>
		</div>
		<!-- END NEWS CARDS -->

		<!-- START NEWS VIEW MODAL -->
		<div id="${news.id}" class="modal modal-big modal-fixed-footer roundborder news-${news.id}">
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
				<p class="btn-flat light disabled left pt10" data-badge-caption=""><fmt:formatDate pattern="dd MMMM y, hh:mm a" value="${news.updated}" /></p>
				<p class="modal-close waves-effect waves-red btn-flat roundborder"><i class="material-icons">close</i></p>
			</div>
		</div>
		<!-- END NEWS VIEW MODAL -->

	</c:forEach>
</div>
<!-- END NEWS PAGE CONTENT -->

<!-- EXPORT PAGE PAGING -->
<jspFragments:paging />
<br/>
