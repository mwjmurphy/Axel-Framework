function changeContent(newContentUrl, anchor) {
	var width = $('#slide_content').width();
	width += width/2;
	console.log("animate left width:" + width);
	$('#slide_content').animate({right: '+=' + width + 'px'}, 300, function() {
		var ao = new AjaxObject(newContentUrl);
		ao.makePostAjaxASyncCall(function(responseText) {
			console.log("animated left:" + responseText);
			$('#slide_content').html(responseText);
			$('#slide_content').animate({right: '-=' + width + 'px'}, 300);
			scrollToTop(anchor);
		})
    });
}

// dont forget the # anchor marker
function scrollToTop(anchor) {
	if (anchor) {
		//goto that anchor by setting the body scroll top to anchor top
		$('html, body').animate({scrollTop:0}, 500, 'easeInSine');
	}
}