package com.guiseframework.platform.web.css;

import static com.garretwilson.lang.ObjectUtilities.*;

import static com.garretwilson.text.xml.stylesheets.css.XMLCSSConstants.*;

/**An ID simple selector.
@author Garret Wilson
*/
public class IDSelector implements SimpleSelector
{

	/**The ID to be selected.*/
	private final String id;

		/**@return The ID to be selected.*/
		public String getID() {return id;}

	/**ID constructor.
	@param id The ID to be selected.
	@exception NullPointerException if the given ID is <code>null</code>.
	*/
	public IDSelector(final String id)
	{
		this.id=checkInstance(id, "ID cannot be null.");
	}

	/**@return A hash code for this object.*/
	public int hashCode()
	{
		return getID().hashCode();
	}

	/**Determines whether this object is equivalent to another object.
	@param object The object to compare with this object.
	@return <code>true</code> if this object is equivalent to the given object.
	*/
	public boolean equals(final Object object)
	{
		return object instanceof IDSelector && getID().equals(((IDSelector)object).getID());
	}

	/**@return A string representation of this object.*/
	public String toString()
	{
		return new StringBuilder().append(ID_SELECTOR_DELIMITER).append(getID()).toString();
	}
}
