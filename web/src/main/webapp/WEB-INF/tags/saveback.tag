<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<div class="row center-align" style="margin: 0">
	<button class="btn waves-effect waves-light gradient-45deg-cyan-light-green roundborder" type="submit">
		<mytaglib:i18n key="save" /><i class="material-icons left" style="font-size: 150%">save</i>
	</button>
	<a class="btn waves-effect waves-light gradient-45deg-red-red roundborder" href="${baseUrl}"><mytaglib:i18n key="back" /><i class="material-icons left" style="font-size: 150%">arrow_back</i></a>
</div>