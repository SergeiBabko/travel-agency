<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="${contextPath}/resources/css/phoca-flags/style.css">
<link rel="stylesheet" href="${contextPath}/resources/css/phoca-flags/phoca-flags.css">

<ul id="mobile-demo" class="sidenav right" style="z-index: 10000; overflow: visible">

	<li>
		<div class="user-view">
			<sec:authorize access="isAnonymous()">
				<div class="background">
					<img align=middle width=100% src="${contextPath}/resources/img/travel.png">
				</div>
				<br /> 
				<br /> 
				<br /> 
				<div class="row" style="padding: 0">
					<div class="col s2 offset-s10" style="bottom: 0px; right: 0.5rem; position: absolute; padding-bottom: 14px">
						<span class="white-text dropdown-trigger" data-target="lang"><i class="material-icons">language</i></span>
					</div>
				</div>
			</sec:authorize>
			
			<sec:authorize access="!isAnonymous()">
				<div class="background darkblur">
					<img align=middle src="${contextPath}/resources/img/travel.png">
				</div>
				<br />
				<div class="row" style="padding: 0">
					<div class="col s10" style="padding: 0">
						<sec:authorize access="hasAnyRole('admin','moderator')">
							<a href="${baseUrl}/manager">
								<img class="usericon" src="${contextPath}/resources/img/user.png">
								<span class="white-text name pt12"><mytaglib:user-name></mytaglib:user-name></span>
								<span class="white-text email pt10"><sec:authentication property="principal" /></span>
							</a>
						</sec:authorize>
						<sec:authorize access="hasRole('user')">
							<a href="${baseUrl}/customer">
								<img class="usericon" src="${contextPath}/resources/img/user.png">
								<span class="white-text name pt12"><mytaglib:user-name></mytaglib:user-name></span>
								<span class="white-text email pt10"><sec:authentication property="principal" /></span>
							</a>
						</sec:authorize>
					</div>
					<div class="col s2" style="bottom: 0px; right: 0.5rem; position: absolute; padding-bottom: 14px">
						<span class="white-text dropdown-trigger" data-target="lang"><i class="material-icons">language</i></span>
					</div>
				</div>
			</sec:authorize>
			<ul id="lang" class="dropdown-content roundborder center-align z-depth-1">
				<li><a class="waves-effect" href="${baseUrl}?lang=ru" style="text-align: center">
					<span class="phoca-flag ru" style="background-size: 110%; background-repeat: no-repeat; background-position: center"></span>
				</a></li>
				<li><a class="waves-effect" href="${baseUrl}?lang=en" style="text-align: center">
					<span class="phoca-flag us" style="background-size: 110%; background-repeat: no-repeat; background-position: center"></span>
				</a></li>
			</ul>
		</div>
	</li>

	<li><a class="waves-effect" href="${baseUrl}/"><i class="material-icons" style="margin: 0 24px 0 0">home</i><mytaglib:i18n key="menu.home" /></a></li>
	<li><a class="waves-effect" href="${baseUrl}/news"><i class="material-icons" style="margin: 0 24px 0 0">dashboard</i><mytaglib:i18n key="menu.news" /></a></li>
	<li><a class="waves-effect" href="${baseUrl}/tours"><i class="material-icons" style="margin: 0 24px 0 0">map</i><mytaglib:i18n key="menu.tours" /></a></li>
	<li><a class="waves-effect" href="${baseUrl}/description"><i class="material-icons" style="margin: 0 24px 0 0">chrome_reader_mode</i><mytaglib:i18n key="menu.description" /></a></li>
	
	<li>&nbsp;</li>
	
	<li>
		<sec:authorize access="isAnonymous()">
			<a class="waves-effect" href="${baseUrl}/login"><i class="material-icons" style="margin: 0 24px 0 0">account_circle</i><mytaglib:i18n key="menu.logreg" /></a>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('admin','moderator')">
			<a class="waves-effect" href="${baseUrl}/manager"><i class="material-icons" style="margin: 0 24px 0 0">account_circle</i><sec:authentication property="role" /> <mytaglib:i18n key="menu.profile" /></a>
		</sec:authorize>
		<sec:authorize access="hasRole('user')">
			<a class="waves-effect" href="${baseUrl}/customer"><i class="material-icons" style="margin: 0 24px 0 0">account_circle</i><sec:authentication property="role" /> <mytaglib:i18n key="menu.profile" /></a>
		</sec:authorize>
	</li>
	
	<sec:authorize access="!isAnonymous()">
		<li><a class="waves-effect" href="${baseUrl}/execute_logout" title="logout"><i class="material-icons left" style="margin: 0 24px 0 0">exit_to_app</i><mytaglib:i18n key="menu.logout" /></a></li>
	</sec:authorize>
	
</ul>