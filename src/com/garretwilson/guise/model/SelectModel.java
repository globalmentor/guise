package com.garretwilson.guise.model;

import java.util.List;

import com.garretwilson.guise.event.ListListener;

/**A model for selecting one or more values from a collection.
The model must be thread-safe, synchronized on itself. Any iteration over values should include synchronization on the instance of this interface.
@param <V> The type of values contained in the model.
@author Garret Wilson
*/
public interface SelectModel<V> extends ControlModel, List<V>
{

	/**@return The selection strategy for this model.*/
	public SelectionStrategy<V> getSelectionStrategy();

	/**Determines the selected indices.
	This method delegates to the selection strategy.
	@return The indices currently selected.
	@see #getSelectedValues()
	*/
	public int[] getSelectedIndices();

	/**Determines the selected values.
	This method delegates to the selection strategy.
	@return The values currently selected.
	@see #getSelectedIndices()
	*/
	public V[] getSelectedValues();

	/**Sets the selected indices.
	Invalid and duplicate indices will be ignored.
	This method delegates to the selection strategy.
	@param indices The indices to select.
	@see #setSelectedValues(V[])
	@see #addSelectedIndex(int)
	*/
	public void setSelectedIndices(final int... indices);

	/**Sets the selected values.
	If a value occurs more than one time in the model, the first occurrence of the value will be selected.
	Values that do not occur in the select model will be ignored.
	This method delegates to the selection strategy.
	@param values The values to select.
	@see #setSelectedIndices(int[])
	*/
	public void setSelectedValues(final V... values);

	/**Adds a selection at the given index.
	An invalid index will be ignored.
	This method delegates to the selection strategy.
	@param index The index to add as a selection.
	@see #setSelectedIndices(int[])
	*/
	public void addSelectedIndex(final int index);

	/**Removes a selection at the given index.
	An invalid index will be ignored.
	This method delegates to the selection strategy.
	@param index The index to remove as a selection.
	@see #setSelectedIndices(int[])
	*/
	public void removeSelectedIndex(final int index);
	
	/**@return The class representing the type of value this model can hold.*/
	public Class<V> getValueClass();

	/**Adds a list listener.
	@param listListener The list listener to add.
	*/
	public void addListListener(final ListListener<SelectModel<V>, V> listListener);

	/**Removes a list listener.
	@param listListener The list listener to remove.
	*/
	public void removeListListener(final ListListener<SelectModel<V>, V> listListener);

}
