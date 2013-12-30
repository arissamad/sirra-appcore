function ThumbWidget(thumbUrl, settings) {
	if(thumbUrl == null || thumbUrl == "") return;
	
	ClassUtil.mixin(ThumbWidget, this, Widget);
	Widget.call(this, "ThumbWidget", true, settings);
	
	this.widget.find("img").attr("src", thumbUrl);
}
