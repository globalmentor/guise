package com.garretwilson.guise.component;

import com.garretwilson.guise.component.layout.Layout;

/**An abstract base class for boxes.
@author Garret Wilson
*/
public class AbstractBox extends AbstractContainer implements Box
{

	/**ID constructor.
	@param id The component identifier.
	@param layout The layout definition for the container.
	@exception NullPointerException if the given identifier or layout is <code>null</code>.
	*/
	public AbstractBox(final String id, final Layout layout)
	{
		super(id, layout);	//construct the parent class
	}

}
