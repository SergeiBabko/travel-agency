<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${contextPath}/tours" />

<br />

<h4 class="light"><mytaglib:i18n key="editTour" /></h4>

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
			<form:form method="POST" action="${baseUrl}/add" modelAttribute="formModel" style="margin: 0">
				
				<div class="row">
					<form:input path="id" type="hidden" />
					<form:input path="version" type="hidden" />
					
					<div class="input-field col s6 l3">
						<i class="material-icons prefix">visibility</i>
						<form:select path="tourStatus">
							<option disabled selected><mytaglib:i18n key="selectStatus" /></option>
							<form:options items="${typeStatusChoice}" />
						</form:select>
						<form:errors path="tourStatus" cssClass="red-text" />
					</div>
					
					<div class="input-field col s6 l3">
						<i class="material-icons prefix">landscape</i>
						<form:select path="tourTypeId">
							<option disabled selected><mytaglib:i18n key="selectTourType" /></option>
							<form:options items="${tourTypeChoice}" />
						</form:select>
						<form:errors path="tourTypeId" cssClass="red-text" />
					</div>
					
					<div class="input-field col s6 l3">
						<i class="material-icons prefix">public</i>
						<form:select path="countryId">
							<option disabled selected><mytaglib:i18n key="selectCountry" /></option>
							<form:options items="${countryChoice}" />
						</form:select>
						<form:errors path="countryId" cssClass="red-text" />
					</div>
				
					<div class="input-field col s6 l3">
						<i class="material-icons prefix">location_city</i>
						<form:select path="cityId">
							<option disabled selected><mytaglib:i18n key="selectCity" /></option>
						<form:options items="${cityChoice}" />
						</form:select>
						<form:errors path="cityId" cssClass="red-text" />
					</div>
				
					<div class="input-field col s12 m8">
						<i class="material-icons prefix">location_on</i>
						<form:input path="address" type="text" />
						<form:errors path="address" cssClass="red-text" />
						<label for="address"><mytaglib:i18n key="address" /></label>
					</div>
					
					<div class="input-field col s6 m2">
						<i class="material-icons prefix">local_atm</i>
						<form:input path="price" type="text" />
						<form:errors path="price" cssClass="red-text" />
						<label for="price"><mytaglib:i18n key="price" /></label>
					</div>
		
					<div class="input-field col s6 m2">
						<i class="material-icons prefix">hotel</i>
						<form:input path="nights" type="text" />
						<form:errors path="nights" cssClass="red-text" />
						<label for="nights"><mytaglib:i18n key="nights" /></label>
					</div>
				
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">create</i>
						<form:input path="name" type="text" />
						<form:errors path="name" cssClass="red-text" />
						<label for="name"><mytaglib:i18n key="tour.name" /></label>
					</div>
		
					<div class="input-field col s12 m6">
						<i class="material-icons prefix">image</i>
						<form:input path="image" type="text" />
						<form:errors path="image" cssClass="red-text" />
						<label for="image"><mytaglib:i18n key="image" /></label>
					</div>
		
					<div class="input-field col s12">
						<i class="material-icons prefix">insert_comment</i>
						<form:textarea class="materialize-textarea" path="description" type="text"></form:textarea>
						<form:errors path="description" cssClass="red-text" />
						<label for="description"><mytaglib:i18n key="tour.description" /></label>
					</div>
				</div>
				<!--  Save/Back buttons -->
				<jspFragments:saveback />
				
			</form:form>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/init-combos.js"></script>
<script>
	initCountry('${baseUrl}');
	
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

