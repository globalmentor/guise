package com.garretwilson.guise.controller.text.xml;

import static com.garretwilson.io.FileConstants.*;
import static com.garretwilson.io.FileUtilities.*;

import java.net.URI;

/**Constants to be used with the Guise stylesheet.
@author Garret Wilson
*/
public class CSSStyleConstants
{

	/**The URI of the Guise XHTML CSS stylesheet.*/
	public final static URI GUISE_XHTML_CSS_STYLESHEET_URI=URI.create(addExtension("guise", CSS_EXTENSION));

	/**The CSS class for a check control label.*/
	public final static String CHECK_CONTROL_LABEL_CLASS="check-control-label";
	/**The CSS class for the check of a check control.*/
	public final static String CHECK_CONTROL_COMPONENT_CLASS="check-control-component";
	/**The CSS class for the label part of a component.*/
	public final static String COMPONENT_LABEL_CLASS="component-label";
	/**The CSS class for the component part of a component.*/
	public final static String COMPONENT_COMPONENT_CLASS="component-component";
	/**The CSS class for the error part of a component.*/
	public final static String COMPONENT_ERROR_CLASS="component-error";
	
	//layout
	
	/**The CSS class for horizontal flow layout children.*/
	public final static String LAYOUT_FLOW_X_CHILD_CLASS="layout-flow-x-child";

	/**The CSS class for vertical flow layout children.*/
	public final static String LAYOUT_FLOW_Y_CHILD_CLASS="layout-flow-y-child";
}
