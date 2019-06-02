<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${contextPath}/news" scope="request"/>

<br/>
<h4 class="light"><mytaglib:i18n key="editNews" /></h4>

<div class="row">
	<div class="card roundborder center-div" style="height: auto; width: 50%; overflow: hidden">
		<div id="previewImage" class="card-image waves-effect waves-light roundborder" style="padding: 1vh">
			<c:if test="${not empty formModel.image}">
				<img id="img" src="${formModel.image}" style="border-radius: 1.2vh"> 
			</c:if>
		</div>
		<div class="card-content center-align" style="padding: 1vh; padding-top: 0">
			<span id="headline" class="card-title" style="font-weight: 300; font-size: 1.2rem">${formModel.name}</span>
		</div>
	</div>
</div>

<div class="card roundborder hoverable">
	<div class="card-content roundborder">
		<div class="row">
			<form:form method="POST" action="${baseUrl}" modelAttribute="formModel" style="margin: 0">
			
				<form:input path="id" type="hidden" />
				<form:input path="version" type="hidden" />
		
				<div class="row">
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">landscape</i>
						<form:input path="name" type="text" data-length="100" />
						<form:errors path="name" cssClass="red-text" />
						<label for="name"><mytaglib:i18n key="name" /></label>
					</div>
					
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">image</i>
						<form:input path="image" type="text" />
						<form:errors path="image" cssClass="red-text" />
						<label for="image"><mytaglib:i18n key="image" /></label>
					</div>
				</div>
		
				<div class="row">
					<div class="input-field col s12">
						<i class="material-icons prefix">insert_comment</i>
						<form:textarea class="materialize-textarea" path="text" type="text" />
						<form:errors path="text" cssClass="red-text" />
						<label for="text"><mytaglib:i18n key="description" /></label>
					</div>
				</div>
		
				<!--  Save/Back buttons -->
				<jspFragments:saveback />
		
			</form:form>
		</div>
	</div>
</div>

<script>
	$('#name').on('keyup', function() {
		name = $(this).val();
		$("#headline").html(name);
	});
	function addImg(image){
		var elem = document.createElement("img");
		document.getElementById("previewImage").appendChild(elem);
		elem.id = 'img';
		elem.src = image;
		elem.style = 'border-radius: 1.2vh';
	}
	$("#image").change(function() {
		if (('${formModel.image}' == null || '${formModel.image}' === "") && document.getElementById("img") == null) {
			newImage = $(this).val();
			addImg(newImage);
		} else {
			img = $(this).val();
			if (img === ""){
				$("#img").remove();
			} else {
				if (document.getElementById("img") == null){
					addImg(img);
				}
				$("#img").attr("src", img);
			}
		}
	});
</script>
