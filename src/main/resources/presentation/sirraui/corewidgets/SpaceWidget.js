function SpaceWidget(numBlocks) {
	ClassUtil.mixin(SpaceWidget, this, Widget);
	Widget.call(this, "SpaceWidget");
	
	if(numBlocks == null) numBlocks = 1;
	this.widget.css("width", numBlocks * 10);
}