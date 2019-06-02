<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="${contextPath}/resources/css/phoca-flags/style.css">
<link rel="stylesheet" href="${contextPath}/resources/css/phoca-flags/phoca-flags.css">

<c:set var="baseUrl" value="${pageContext.request.contextPath}" scope="request" />

<!-- START HEADER -->
<header>
	<div class="nav-wrapper" style="z-index: 9999">
		<nav class="gradient-45deg-light-blue-cyan" style="overflow: inherit">
			<div class="container">
				<div class="navbar-fixed nav-wrapper">

					<a href="${baseUrl}/" class="brand-logo"><img class="hvr-forward" vspace=15% height=55% src="${contextPath}/resources/img/logo-1.png" /></a>
					<a class="sidenav-trigger" data-target="mobile-demo"><i class="material-icons">menu</i></a>

					<ul class="right hide-on-med-and-down">
						<li class="waves-effect waves-light"><a href="${baseUrl}/"><mytaglib:i18n key="menu.home" /></a></li>
						<li class="waves-effect waves-light"><a href="${baseUrl}/news"><mytaglib:i18n key="menu.news" /></a></li>
						<li class="waves-effect waves-light"><a href="${baseUrl}/tours"><mytaglib:i18n key="menu.tours" /></a></li>
						<li class="waves-effect waves-light"><a href="${baseUrl}/description"><mytaglib:i18n key="menu.description" /></a></li>
						<li class="waves-effect waves-light tooltipped hvr-icon-grow" data-position="bottom" data-tooltip="<mytaglib:i18n key="menu.fullscreen" />" onclick="toggleFullScreen()"><a><i class="material-icons hvr-icon">settings_overscan</i></a></li>

						<sec:authorize access="isAnonymous()">
							<li class="waves-effect waves-light tooltipped" data-position="bottom" data-tooltip="<mytaglib:i18n key="menu.logreg" />"><a href="${baseUrl}/login"><i class="material-icons" style="font-size: 250%">account_circle</i></a></li>
							<li class="waves-effect waves-light dropdown" data-target="lng"><a><i class="material-icons">language</i></a></li>
							<ul id="lng" class="dropdown-content roundborder center-align z-depth-1">
								<li style="display: grid"><a class="waves-effect" href="${baseUrl}?lang=ru" style="text-align: center">
									<span class="phoca-flag ru" style="background-size: 110%; background-repeat: no-repeat; background-position: center"></span>
								</a></li>
								<li style="display: grid"><a class="waves-effect" href="${baseUrl}?lang=en" style="text-align: center">
									<span class="phoca-flag us" style="background-size: 110%; background-repeat: no-repeat; background-position: center"></span>
								</a></li>
							</ul>
						</sec:authorize>

						<sec:authorize access="!isAnonymous()">
							<li><a class="sidenav-trigger show-on-large tooltipped" data-position="bottom" data-tooltip="<sec:authentication property="role" /> <mytaglib:i18n key="menu.profile" />" data-target="mobile-demo" style="margin:0"><i class="material-icons" style="font-size: 250%">account_circle</i></a></li>
						</sec:authorize>
					</ul>

				</div>
			</div>
		</nav>
	</div>
</header>
<!-- END HEADER -->

<!-- SLIDER -->
<jspFragments:mobileSlider />

<script>
$(document).ready(function() {
	$(".dropdown").dropdown({
		coverTrigger : false,
		constrainWidth : false
	});
});
</script>
