function initSelectElement(htmlElementId, jsonArray) {
	$('#' + htmlElementId).find('option').remove().end();

	$('#countryId').append($("<option></option>").attr({
		"value" : ''
	}).text('All countries'));
	
	$('#cityId').append($("<option></option>").attr({
		"value" : ''
	}).text('All cities'));
	
	$.each(jsonArray, function(key, value) {
		$('#' + htmlElementId).append($("<option></option>").attr("value", value.id).text(value.name));
	});
	
	$('#' + htmlElementId).formSelect();
}

function initComboboxes(contextUrl) {

	$.get(contextUrl + "/countries", function(countriesArray) {

		initSelectElement('countryId', countriesArray);

		$("#countryId").change(function() {
			var selectedId = $(this).val();
			$.get(contextUrl + "/cities?countryId=" + selectedId, function(citiesArray) {
				initSelectElement('cityId', citiesArray);
			})
		});
		
	});

}

function initCountry(contextUrl) {

	$("#countryId").change(function() {
		var selectedId = $(this).val();
		$.get(contextUrl + "/cities?countryId=" + selectedId, function(citiesArray) {
			initSelectElement('cityId', citiesArray);
		})
	});

}






/*----------------------------------------
		DATE AND PERSON COMBOBOX
------------------------------------------*/
function initSelect(htmlElementId, jsonArray) {
	
	$('#' + htmlElementId).find('option').remove().end();
	
	$('#tourDates').append($("<option></option>").attr({
		"selected" : '',
		"disabled" : '',
		"value" : ''
	}).text('Select date'));
	
	$('#personCount').append($("<option></option>").attr({
		"selected" : '',
		"disabled" : '',
		"value" : ''
	}).text('Person count'));
	
	$('.tourDateReview').append($("<option></option>").attr({
		"selected" : '',
		"disabled" : '',
		"value" : ''
	}).text('Select date'));
	
	$('.rating').append($("<option></option>").attr({
		"selected" : '',
		"disabled" : '',
		"value" : ''
	}).text('Rating'));
	
	if (htmlElementId === 'tourDates'){
		$.each(jsonArray, function(key, value) {
			$('#' + htmlElementId).append($("<option></option>").attr("value", value).text(key));
		});
	}
	var step;
	for (step = 1; step <= 5; step++) {
		$('.rating').append($("<option></option>").attr("value", step).text(step));
	}
	
	$.each(jsonArray, function(key, value) {
		$('.tourDateReview').append($("<option></option>").attr("value", value).text(key));
	});
	
	if (htmlElementId === 'personCount'){
		$.each(jsonArray, function(key, value) {
			$('#' + htmlElementId).append($("<option></option>").attr("value", key).text(value));
		});
	}
	
	$('#' + htmlElementId).formSelect();
	
}

function initTourDates(contextUrl, tourId) {

	$.get(contextUrl + "/tourdates?tourId=" + tourId, function(tourDatesArray) {

		initSelect('tourDates', tourDatesArray);

		$("#tourDates").change(function() {
			var selectedId = $(this).val();
			$.get(contextUrl + "/personcount?tourDateId=" + selectedId, function(personCountArray) {
				initSelect('personCount', personCountArray);
			})
		});
		
	});

}