package com.guiseframework;

import static com.garretwilson.lang.ObjectUtilities.*;

/**Abstract implementation of a destination referencing another destination.
@author Garret Wilson
*/
public abstract class AbstractReferenceDestination extends AbstractDestination implements ReferenceDestination
{

	/**The referenced destination.*/
	private final Destination destination;

		/**@return The referenced destination.*/
		public Destination getDestination() {return destination;}

	/**Path and referenced destination constructor.
	@param path The appplication context-relative path within the Guise container context, which does not begin with '/'.
	@param destination The referenced destination.
	@exception NullPointerException if the path and/or destination is <code>null</code>.
	@exception IllegalArgumentException if the provided path is absolute.
	*/
	public AbstractReferenceDestination(final String path, final Destination destination)
	{
		super(path);	//construct the parent class
		this.destination=checkInstance(destination, "Destination cannot be null.");
	}
}