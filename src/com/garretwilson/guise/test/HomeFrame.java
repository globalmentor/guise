package com.garretwilson.guise.test;

import com.garretwilson.beans.AbstractPropertyValueChangeListener;
import com.garretwilson.beans.PropertyValueChangeEvent;
import com.garretwilson.guise.component.*;
import com.garretwilson.guise.component.layout.*;
import com.garretwilson.guise.event.ActionEvent;
import com.garretwilson.guise.event.ActionListener;
import com.garretwilson.guise.model.ActionModel;
import com.garretwilson.guise.model.ValueModel;

/**Test frame for a home page.
@author Garret Wilson
*/
public class HomeFrame extends DefaultFrame
{

	/**ID constructor.
	@param id The component identifier.
	@exception NullPointerException if the given identifier is <code>null</code>.
	*/
	public HomeFrame(final String id)
	{
		super(id, new FlowLayout(Axis.Y));	//construct the parent class, flowing vertical
		setTitle("Home Frame Test");	//set the frame label
		
		final Label testLabel=new Label("testLabel");
		testLabel.setStyleID("title");
		testLabel.getModel().setText("This is label text from the model.");
		add(testLabel);	//add a new label
		
		final Panel buttonPanel=new Panel("testButtonPanel", new FlowLayout(Axis.X));	//create a panel flowing horizontally

		final ActionControl testButton=new ActionControl("testButton");
		testButton.getModel().setText("First Test Button");
		buttonPanel.add(testButton);	//add a new button
		final ActionControl testButton2=new ActionControl("testButton2");
		testButton2.getModel().setText("Click this button to change the text.");
		testButton2.getModel().addActionListener(new ActionListener<ActionModel>()
				{
					public void onAction(ActionEvent<ActionModel> actionEvent)
					{
						testLabel.getModel().setText("You pressed the button!");
					}
				});
		buttonPanel.add(testButton2);	//add a new button
		add(buttonPanel);	//add the button panel to the frame
		final ValueControl<String> textInput=new ValueControl<String>("textInput", String.class);	//create a text input control
		textInput.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractPropertyValueChangeListener<String>()
				{
					public void propertyValueChange(PropertyValueChangeEvent<String> propertyValueChangeEvent)
					{
						testLabel.getModel().setText(propertyValueChangeEvent.getNewValue());
					}
				});
		add(textInput);
	}

}
