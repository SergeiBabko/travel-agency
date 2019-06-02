<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request" />

<html xmlns="http://www.w3.org/1999/xhtml">
<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>

<head>

	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"></link>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"></link>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
	<script src="https://api-maps.yandex.ru/2.1/?apikey=488bc54a-5e18-41a2-a43b-1955df405d9e&lang=en_US" type="text/javascript"></script>
	<script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.js"></script>
	<script src="https://unpkg.com/imagesloaded@4/imagesloaded.pkgd.min.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
	
	<link rel="shortcut icon" type="image/png" href="${contextPath}/resources/img/logo-tittle.png" /></link>
	<link rel="stylesheet" href="${contextPath}/resources/css/custom.css"></link>
	<link rel="stylesheet" href="${contextPath}/resources/css/grid.css"></link>
	<link rel="stylesheet" href="${contextPath}/resources/css/pre-loader.css"></link>
	<link rel="stylesheet" href="${contextPath}/resources/css/hoover.css"></link>
	
	
	<script src="${contextPath}/resources/js/init-materialize.js"></script>
	<script src="${contextPath}/resources/js/init-maps.js"></script>
	<script src="${contextPath}/resources/js/init-fullscreen.js"></script>
	<script src="${contextPath}/resources/js/init-login.js"></script>

	
	<title><tiles:insertAttribute name="title" /></title>

</head>

<body>

	<!-- START PAGE LOADER -->
	<div id="loader-wrapper">
		<div id="loader"></div>
		<div class="loader-section section-left"></div>
		<div class="loader-section section-right"></div>
	</div>
	<!-- END PAGE LOADER -->

	<tiles:insertAttribute name="header" />
	
	<main>
		<div class="container">
			<tiles:insertAttribute name="body" />
		</div>
	</main>
	
	<tiles:insertAttribute name="footer" />
	
</body>

</html>