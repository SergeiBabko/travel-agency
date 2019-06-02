<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${contextPath}/description" />

<!-- START DESCRIPTION FLOATING ADD BUTTON -->
<c:if test="${empty entity}">
	<sec:authorize access="hasAnyRole('admin','moderator')">
		<a class="btn-floating btn-large waves-effect waves-light cyan accent-4 pulse centerOnPage hvr-icon-pulse-grow" href="${baseUrl}/add"><i class="material-icons hvr-icon">add</i></a>
	</sec:authorize>
</c:if>
<!-- END DESCRIPTION FLOATING ADD BUTTON -->

<!-- START DESCRIPTION SLIDERS -->
<div class="slider fullscreen">
	<c:forEach var="description" items="${entity}" varStatus="loopCounter">
	
		<ul class="slides">

			<li>
				<div class="darken mask">
					<img src="${description.image1}">
				</div>
				<div class="caption center-align">
					<div class="row">
						<div class="col s0 m1"></div>
						<div class="col s12 m10">
							<h4><mytaglib:i18n key="description.about" /></h4>
							<pre><span class="inner-pre light white-text text-lighten-3" style="font-size: 2.1vh">${description.description}</span></pre>
						</div>
						<div class="col s0 m1"></div>
					</div>
				</div>
			</li>

			<li>
				<div class="darken mask">
					<img class="mask" src="${description.image2}">
				</div>
				<div class="caption left-align">
					<h4><mytaglib:i18n key="description.contacts" /></h4>
					<pre><span class="inner-pre"><h6 class="light white-text text-lighten-3" style="font-size: 2.1vh">${description.contacts}</h6></span></pre>
					<div class="right-align">
						<h4><mytaglib:i18n key="description.address" /></h4>
						<pre><span class="inner-pre"><h6 class="light white-text text-lighten-3" style="font-size: 2.1vh">${description.address}</h6></span></pre>
					</div>
				</div>
				<div class="center-align" style="position: absolute; top: 90%; left: 50%; transform: translate(-50%, -50%);">
					<a class="waves-effect modal-trigger btn-flat btn-floating tooltipped hvr-bob" data-position="top" data-tooltip="<mytaglib:i18n key="showOnMap" />" href="#${description.id}" style="color: white"><i class="material-icons" style="font-size: 250%">place</i></a>
				</div>
			</li>

		</ul>
		
		<!-- START DESCRIPTION FLOATING EDIT BUTTON -->
		<sec:authorize access="hasAnyRole('admin','moderator')">
			<div class="fixed-action-btn">
				<a class="btn-floating btn-large waves-effect waves-light cyan accent-4"><i class="material-icons">menu</i></a>
				<ul style="display: table; align-items: center; right: 6vh">
					<li style="margin: 0 4% 0 0"><a class="btn-floating waves-effect waves-light teal lighten-3 hvr-icon-rotate" href="${baseUrl}/edit/${description.id}"><i class="material-icons hvr-icon">edit</i></a></li>
					<li style="margin: 0 4% 0 0"><a class="btn-floating waves-effect waves-light red lighten-1 hvr-icon-shrink" href="${baseUrl}/delete/${description.id}"><i class="material-icons hvr-icon">delete</i></a></li>
				</ul>
			</div>
		</sec:authorize>
		<!-- END DESCRIPTION FLOATING EDIT BUTTON -->

		<!-- START MODAL WITH MAP -->
		<div id="${description.id}" class="modal bottom-sheet map roundtopborder" style="background: linear-gradient(45deg, #039be5 0%, #26c6da 100%)">
			<div class="modal-footer" style="background: transparent">
				<p class="btn-flat disabled left" style="font-size: 10pt; color: #fff !important">${description.address}</p>
				<p class="modal-close roundborder waves-effect waves-red btn-flat"><i class="material-icons">close</i></p>
			</div>
			 <div class="modal-content" style="padding: 0">
				<script>
					ymaps.ready(initMap.bind(null, '${description.address}', '17'));
				</script>
				<div class="card z-depth-0" style="height: 100%; margin: 0">
					<div class="card-content" style="padding: 0">
						<div id="map" class="dmap"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- END MODAL WITH MAP -->

	</c:forEach>
	
	
	
</div>
<!-- END DESCRIPTION SLIDERS -->