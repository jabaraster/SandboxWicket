function initializeFileUploadPanel(pSubmitUrl) {
	console.log(pSubmitUrl);
	var doc = $(document);
	var root = 'div.FileUploadPanel';
	doc.on('click', root + 'button.uploader', function() {
		var file = $(this).parent()/*li*/.prev()/*li*/.child()[0]/*div.fileOverlay*/.child()[0];
		alert(file.val());
		return false;
	});
}

/*
(function() {
    var vars = {
        submitUrl: '${submitUrl}',
        dummy: null
    };

    $(function() {
        initialize('${submitUrl}');
    });


})();
*/