package com.guiseframework.model;

import java.util.Iterator;

import com.guiseframework.event.*;

/**A model for a potential action.
@author Garret Wilson
*/
public interface ActionModel extends Model
{

	/**Adds an action listener.
	@param actionListener The action listener to add.
	*/
	public void addActionListener(final ActionListener actionListener);

	/**Removes an action listener.
	@param actionListener The action listener to remove.
	*/
	public void removeActionListener(final ActionListener actionListener);

	/**@return all registered action listeners.*/
	public Iterator<ActionListener> getActionListeners();

	/**Fires an action to all registered action listeners.
	@see ActionListener
	@see ActionEvent
	*/
	public void fireAction();

}
