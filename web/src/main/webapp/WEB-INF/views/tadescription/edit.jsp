<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${contextPath}/description" />

<br/>
<h4 class="light"><mytaglib:i18n key="description.edit" /></h4>

<div class="row">
	<div class="col s6">
		<div class="card roundborder center-div" style="height: auto; width: 100%; overflow: hidden">
			<div id="previewImage1" class="card-image waves-effect waves-light roundborder" style="padding: 1vh">
				<c:if test="${not empty formModel.image1}">
					<img id="img1" src="${formModel.image1}" style="border-radius: 1.2vh"> 
				</c:if>
			</div>
		</div>
	</div>
	<div class="col s6">
		<div class="card roundborder center-div" style="height: auto; width: 100%; overflow: hidden">
			<div id="previewImage2" class="card-image waves-effect waves-light roundborder" style="padding: 1vh">
				<c:if test="${not empty formModel.image2}">
					<img id="img2" src="${formModel.image2}" style="border-radius: 1.2vh"> 
				</c:if>
			</div>
		</div>
	</div>
</div>

<div class="card roundborder hoverable">
	<div class="card-content roundborder">
		<div class="row">
			<form:form method="POST" action="${baseUrl}/add" modelAttribute="formModel" style="margin: 0">

				<form:input path="id" type="hidden" />
				<div class="row">
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">image</i>
						<form:textarea class="materialize-textarea" path="image1" type="text" />
						<form:errors path="image1" cssClass="red-text" />
						<label for="image1"><mytaglib:i18n key="image" /></label>
					</div>
		
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">image</i>
						<form:textarea class="materialize-textarea" path="image2" type="text" />
						<form:errors path="image2" cssClass="red-text" />
						<label for="image2"><mytaglib:i18n key="image" /></label>
					</div>
					
					<form:input path="image3" type="hidden" />
			
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">contacts</i>
						<form:textarea class="materialize-textarea" path="contacts" type="text" />
						<form:errors path="contacts" cssClass="red-text" />
						<label for="contacts"><mytaglib:i18n key="description.contacts" /></label>
					</div>
		
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">location_on</i>
						<form:textarea class="materialize-textarea" path="address" type="text" />
						<form:errors path="address" cssClass="red-text" />
						<label for="address"><mytaglib:i18n key="description.address" /></label>
					</div>
					
					<div class="input-field col s12">
						<i class="material-icons prefix">insert_comment</i>
						<form:textarea class="materialize-textarea" path="description" type="text" />
						<form:errors path="description" cssClass="red-text" />
						<label for="description"><mytaglib:i18n key="description.about" /></label>
					</div>
			
					<%-- div class="input-field col s12">
						<i class="material-icons prefix">image</i>
						<form:textarea class="materialize-textarea" path="image3" type="text" />
						<form:errors path="image3" cssClass="red-text" />
						<label for="image3">Image3</label>
					</div> --%>
				</div>
				<!--  Save/Back buttons -->
				<jspFragments:saveback />
	
			</form:form>
		</div>
	</div>
</div>

<script>
	function addImgOne(image){
		var elem = document.createElement("img");
		document.getElementById("previewImage1").appendChild(elem);
		elem.id = 'img1';
		elem.src = image;
		elem.style = 'border-radius: 1.2vh';
	}
	$("#image1").change(function() {
		if (('${formModel.image1}' == null || '${formModel.image1}' === "") && document.getElementById("img1") == null) {
			img = $(this).val();
			addImgOne(img);
		} else {
			img = $(this).val();
			if (img === ""){
				$("#img1").remove();
			} else {
				if (document.getElementById("img1") == null){
					addImgOne(img);
				}
				$("#img1").attr("src", img);
			}
		}
	});
	
	function addImgTwo(image){
		var elem = document.createElement("img");
		document.getElementById("previewImage2").appendChild(elem);
		elem.id = 'img2';
		elem.src = image;
		elem.style = 'border-radius: 1.2vh';
	}
	$("#image2").change(function() {
		if (('${formModel.image2}' == null || '${formModel.image2}' === "") && document.getElementById("img2") == null) {
			img = $(this).val();
			addImgTwo(img);
		} else {
			img = $(this).val();
			if (img === ""){
				$("#img2").remove();
			} else {
				if (document.getElementById("img2") == null){
					addImgTwo(img);
				}
				$("#img2").attr("src", img);
			}
		}
	});
</script>
