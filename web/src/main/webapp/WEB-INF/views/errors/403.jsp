<%@ page isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<head>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
	<link rel="stylesheet" href="${contextPath}/resources/css/errorpage.css">
	
	<link rel="shortcut icon" type="image/png" href="${contextPath}/resources/img/logo-tittle.png" />
	<title><mytaglib:i18n key="403.forbidden" /></title>
</head>

<div class="row">
	<div class="col s12">
		<div class="browser-window" onclick="goBack()">
			<div class="top-bar">
				<div class="circles">
					<div id="close-circle" class="circle"></div>
					<div id="minimize-circle" class="circle"></div>
					<div id="maximize-circle" class="circle"></div>
				</div>
			</div>
			<div class="content">
				<div class="row">
					<div id="site-layout-example-top" class="col s12">
						<p class="flat-text-logo center white-text caption-uppercase"><mytaglib:i18n key="403.forbidden" /></p>
					</div>
					<div id="site-layout-example-right" class="col s12">
						<div class="row center">
							<h1 class="text-long-shadow col s12 mt-3">403</h1>
							<p class="center white-text"><mytaglib:i18n key="403.msg" /></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	function goBack() {
	    window.history.back();
	}
</script>