
// adding an image via url box
function addImage(e) {
	var imgUrl = $("#imgUrl").val();
	if (imgUrl.length) {
		$("#imageContainer img").attr("src", imgUrl);
	}
	e.preventDefault();
}

//on pressing return, addImage() will be called
$("#urlBox").submit(addImage);


// editing image via css properties
function editImage() {
	var gs = $("#gs").val(); // grayscale
	var br = $("#br").val(); // brightness
	var ct = $("#ct").val(); // contrast
	var invert = $("#invert").val(); //invert

	$("#imageContainer img").css("filter", 'grayscale(' + gs+
													 '%) brightness(' + br +
													 '%) contrast(' + ct +
													 '%) invert(' + invert +'%)');

	$("#imageContainer img").css("-webkit-filter", 'grayscale(' + gs+
													 '%) brightness(' + br +
													 '%) contrast(' + ct +
													 '%) invert(' + invert + '%)'); 

}

//When sliders change image will be updated via editImage() function
$("input[type=range]").change(editImage).mousemove(editImage);

// Reset sliders back to their original values on press of 'reset'
$('#imageEditor').on('reset', function () {
	setTimeout(function() {
		editImage();
	},0);
});