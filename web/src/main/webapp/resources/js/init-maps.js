function initMap(location, zoom) {
	
	var myMap = new ymaps.Map("map", {
		center : [ 53.678122, 23.829807 ],
		zoom : zoom,
		controls: ['largeMapDefaultSet']
	});
	
    ymaps.geocode(location, {
        results: 1
    }).then(function (res) {
            var firstGeoObject = res.geoObjects.get(0),
                coords = firstGeoObject.geometry.getCoordinates(),
                bounds = firstGeoObject.properties.get('boundedBy');
            firstGeoObject.options.set('preset', 'islands#darkBlueDotIconWithCaption');
            firstGeoObject.properties.set('iconCaption', firstGeoObject.getAddressLine());

            myMap.geoObjects.add(firstGeoObject);
            
            myMap.setBounds(myMap.geoObjects.getBounds(), {
            	checkZoomRange:true
            }).then(function(){ 
            	myMap.setZoom(zoom);
            });
        });
        
}