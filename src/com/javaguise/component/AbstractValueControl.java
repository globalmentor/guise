package com.javaguise.component;

import com.javaguise.model.*;
import com.javaguise.session.GuiseSession;

/**Abstract implementation of a control to accept input from the user.
@param <V> The type of value to represent.
@author Garret Wilson
*/
public abstract class AbstractValueControl<V, C extends ValueControl<V, C>> extends AbstractControl<ValueModel<V>, C> implements ValueControl<V, C>
{

	/**Session constructor with a default data model to represent a given type.
	@param session The Guise session that owns this component.
	@param valueClass The class indicating the type of value held in the model.
	@exception NullPointerException if the given session and/or value class is <code>null</code>.
	*/
	public AbstractValueControl(final GuiseSession<?> session, final Class<V> valueClass)
	{
		this(session, null, valueClass);	//construct the component, indicating that a default ID should be used
	}

	/**Session and ID constructor with a default data model to represent a given type.
	@param session The Guise session that owns this component.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	@param valueClass The class indicating the type of value held in the model.
	@exception NullPointerException if the given session and/or value class is <code>null</code>.
	@exception IllegalArgumentException if the given identifier is not a valid component identifier.
	*/
	public AbstractValueControl(final GuiseSession<?> session, final String id, final Class<V> valueClass)
	{
		this(session, id, new DefaultValueModel<V>(session, valueClass));	//construct the class with a default model
	}

	/**Session, ID, and model constructor.
	@param session The Guise session that owns this component.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	@param model The component data model.
	@exception NullPointerException if the given session and/or model is <code>null</code>.
	@exception IllegalArgumentException if the given identifier is not a valid component identifier.
	*/
	public AbstractValueControl(final GuiseSession<?> session, final String id, final ValueModel<V> model)
	{
		super(session, id, model);	//construct the parent class
	}

}