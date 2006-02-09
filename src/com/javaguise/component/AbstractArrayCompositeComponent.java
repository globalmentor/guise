package com.javaguise.component;

import java.util.*;
import static java.util.Arrays.*;

import com.javaguise.GuiseSession;
import com.javaguise.model.Model;

/**Abstract implementation of a composite component that keeps track of its child components at specific indices in an array.
Child components should not directly call {@link #addComponent(Component)} and {@link #removeComponent(Component)}.
Each index in the array can be <code>null</code>.
Iterating over child components is thread safe.
@author Garret Wilson
*/
public abstract class AbstractArrayCompositeComponent<C extends CompositeComponent<C>> extends AbstractMultipleCompositeComponent<C>
{

	/**The array of child components.*/ 
	private final Component<?>[] componentArray;

		/**@return The array of child components.*/ 
//TODO del		protected final Component<?>[] getComponentArray() {return componentArray;}

  /**Returns the component at the specified index in the array.
  @param index The index of the component to return.
	@return The component at the specified position in this array.
	@exception IndexOutOfBoundsException if the index is out of range.
	*/
	protected Component<?> getComponent(final int index) {return componentArray[index];}
		
  /**Sets the component at the given index.
  If the new component is the same as the old, no action is taken.
  This implementation calls {@link #addComponent(Component)} and {@link #removeComponent(Component)} as necessary.
  @param index The index of the component to set.
  @param newComponent The component to set at the given index.
  @return The component previously at the given index.
	@exception IndexOutOfBoundsException if the index is out of range.
	@see #addComponent(Component)
	@see #removeComponent(Component)
	*/
	protected Component<?> setComponent(final int index, final Component<?> newComponent)
	{		
		final Component<?> oldComponent=componentArray[index];	//get the old value
		if(oldComponent!=newComponent)	//if the value is really changing
		{
			componentArray[index]=newComponent;	//actually change the value
			if(oldComponent!=null)	//if there was an old component
			{
				removeComponent(oldComponent);	//remove the old component
			}
			if(newComponent!=null)	//if there is a new component
			{
				addComponent(newComponent);	//add the component					
			}
		}
		return oldComponent;	//return the component previously at the given index
	}
		
	/**@return An iterator to contained components.*/
	public Iterator<Component<?>> iterator()
	{
		final List<Component<?>> componentList=new ArrayList<Component<?>>(componentArray.length);	//create a list of components that is large enough to hold all components if we need to
		for(int i=componentArray.length-1; i>=0; --i)	//for each component
		{
			final Component<?> component=componentArray[i];	//get a reference to this component
			if(component!=null)	//if we found another component
			{
				componentList.add(component);	//add the component to our list
			}
		}
		return componentList.iterator();	//return an iterator to our list of components
	}

	/**@return Whether this component has children.*/
	public boolean hasChildren()
	{
		for(int i=componentArray.length-1; i>=0; --i)	//for each component
		{
			if(componentArray[i]!=null)	//if there is a component at this index
			{
				return true;	//we found a child component
			}
		}
		return false;	//all components in the array are null; we have no child components
	}

	/**Session, ID, model, and maximum component count constructor.
	@param session The Guise session that owns this component.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	@param model The component data model.
	@param maxComponentCount The maximum number of child components to support.
	@exception NullPointerException if the given session, layout, and/or model is <code>null</code>.
	@exception IllegalArgumentException if the given identifier is not a valid component identifier.
	*/
	public AbstractArrayCompositeComponent(final GuiseSession session, final String id, final Model model, final int maxComponentCount)
	{
		super(session, id, model);	//construct the parent class
		componentArray=new Component[maxComponentCount];	//create an array of components of the appropriate length
		fill(componentArray, null);	//fill the array with nulls
	}

}
