/**
 * Supports many styles.
 * 
 *  - info-box
 *  
 * 
 */
function DivWidget(settings) {
	ClassUtil.mixin(DivWidget, this, Widget);
	Widget.call(this, "DivWidget", true, settings);
	
	current = this.widget;
}
