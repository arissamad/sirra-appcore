/**
 * Supports many styles.
 * 
 *  - info-box
 *  
 * 
 */
function DivWidget(cssClass, settings) {
	ClassUtil.mixin(DivWidget, this, Widget);
	Widget.call(this, "DivWidget", true, settings);
	
	if(cssClass != null) this.widget.addClass(cssClass);
	
	current = this.widget;
}
