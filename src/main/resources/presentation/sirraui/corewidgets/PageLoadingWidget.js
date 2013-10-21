function PageLoadingWidget() {
	this.lw = new LoadingWidget({pageMode: true});
}

PageLoadingWidget.prototype.remove = function() {
	this.lw.remove();
};