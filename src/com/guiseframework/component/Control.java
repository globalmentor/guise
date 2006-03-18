package com.guiseframework.component;

import static com.garretwilson.lang.ClassUtilities.*;

import com.guiseframework.model.Enableable;

/**A component that accepts user interaction to manipulate a data model.
@author Garret Wilson
*/
public interface Control<C extends Control<C>> extends Component<C>, Enableable
{

	/**The editable bound property.*/
	public final static String EDITABLE_PROPERTY=getPropertyName(Control.class, "editable");	//TODO decide if this should be moved down to ValueControl
	/**The valid bound property.*/
	public final static String VALID_PROPERTY=getPropertyName(Control.class, "valid");

	/**@return Whether the text literal value represents a valid value for the model.*/
	public boolean isValid();

	/**Sets whether the text literal value represents a valid value for the value model.
	This is a bound property of type <code>Boolean</code>.
	@param newValid <code>true</code> if the text literal and model value should be considered valid.
	@see Control#VALID_PROPERTY
	*/
//TODO del	public void setValid(final boolean newValid);

}