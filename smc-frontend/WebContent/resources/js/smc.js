function handleSubmit(xhr, status, args, dialog) {
	var jqDialog = jQuery('#' + dialog.id);
	if (args.validationFailed) {
		jqDialog.effect('shake', {
			times : 3
		}, 100);
	} else {
		dialog.hide();
	}
}

(function() {
	$(".exp-wrapper")
			.each(
					function(index, value) {
						var $elem = $(value), $more, $less, $text, maxHeight, minHeight, animDuration = 200;

						$less = $elem.find(".exp-less");
						$more = $elem.find(".exp-more");
						$text = $elem.find('.exp-text');
						minHeight = $text.height() + "px";

						$more.click(function() {
							maxHeight = $text.get(0).scrollHeight;
							$(this).animate({
								'opacity' : '0'
							}, animDuration);
							$less.animate({
								'opacity' : '1',
								'z-index' : '1'
							}, animDuration);
							$text.animate({
								'height' : maxHeight
							}, animDuration);
						});
						$less.click(function() {
							$(this).animate({
								'opacity' : '0',
								'z-index' : '-1'
							}, animDuration);
							$more.animate({
								'opacity' : '1'
							}, animDuration);
							$text.animate({
								'height' : minHeight
							}, animDuration);
						});
					})
})();
