package com.garretwilson.guise.test;

import java.net.URI;

import com.garretwilson.beans.AbstractPropertyValueChangeListener;
import com.garretwilson.beans.PropertyValueChangeEvent;
import com.garretwilson.guise.component.*;
import com.garretwilson.guise.component.layout.*;
import com.garretwilson.guise.event.ActionEvent;
import com.garretwilson.guise.event.ActionListener;
import com.garretwilson.guise.event.NavigateActionListener;
import com.garretwilson.guise.model.AbstractModelGroup;
import com.garretwilson.guise.model.ActionModel;
import com.garretwilson.guise.model.ModelGroup;
import com.garretwilson.guise.model.MutualExclusionModelGroup;
import com.garretwilson.guise.model.SingleSelectionStrategy;
import com.garretwilson.guise.model.ValueModel;
import com.garretwilson.guise.session.GuiseSession;
import com.garretwilson.guise.validator.RegularExpressionStringValidator;
import com.garretwilson.util.Debug;

/**Test frame for a home page.
@author Garret Wilson
*/
public class HomeFrame extends NavigationFrame
{

	/**Guise session constructor.
	@param session The Guise session that owns this frame.
	*/
	public HomeFrame(final GuiseSession<?> session)
	{
		this(session, null);	//construct the component, indicating that a default ID should be used
	}

	/**ID constructor.
	@param session The Guise session that owns this frame.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	*/
	public HomeFrame(final GuiseSession<?> session, final String id)
	{
		super(session, id, new FlowLayout(Axis.Y));	//construct the parent class, flowing verticallly
		getModel().setLabel("Home Frame Test");	//set the frame label
		
		final Label testLabel=new Label(session, "testLabel");
		testLabel.setStyleID("title");
		testLabel.getModel().setLabel("This is label text from the model.");
		add(testLabel);	//add a new label
		
		final Panel buttonPanel=new Panel(session, "testButtonPanel", new FlowLayout(Axis.X));	//create a panel flowing horizontally

		final Button testButton=new Button(session, "testButton");
		testButton.getModel().setLabel("Click here to go to the 'Hello World' demo.");
		testButton.getModel().addActionListener(new NavigateActionListener<ActionModel>("helloworld"));
		buttonPanel.add(testButton);	//add a new button
		final Button testButton2=new Button(session, "testButton2");
		testButton2.getModel().setLabel("Click this button to change the text.");
		testButton2.getModel().addActionListener(new ActionListener<ActionModel>()
				{
					public void actionPerformed(ActionEvent<ActionModel> actionEvent)
					{
						testLabel.getModel().setLabel("You pressed the button!");
					}
				});
		buttonPanel.add(testButton2);	//add a new button
		final Link testLink=new Link(session);
		testLink.getModel().setLabel("This is a link.");
		testLink.getModel().addActionListener(new ActionListener<ActionModel>()
				{
					public void actionPerformed(ActionEvent<ActionModel> actionEvent)
					{
						testLabel.getModel().setLabel("The link works.");
					}
				});
		buttonPanel.add(testLink);	//add a new button
		add(buttonPanel);	//add the button panel to the frame
		final TextControl<String> textInput=new TextControl<String>(session, "textInput", String.class);	//create a text input control
		textInput.getModel().setLabel("This is the text input label.");
		textInput.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractPropertyValueChangeListener<String>()
				{
					public void propertyValueChange(PropertyValueChangeEvent<String> propertyValueChangeEvent)
					{
						testLabel.getModel().setLabel(propertyValueChangeEvent.getNewValue());
					}
				});
//TODO del		textInput.getModel().setValidator(new RegularExpressionStringValidator("[a-z]*"));
		add(textInput);
	
	
	
		final Panel horizontalPanel=new Panel(session, new FlowLayout(Axis.X));	//create a panel flowing horizontally
	
	
		final Panel booleanPanel=new Panel(session, new FlowLayout(Axis.Y));	//create a panel flowing vertically
		booleanPanel.getModel().setLabel("Check one of these");
		final CheckControl check1=new CheckControl(session, "check1");
		check1.setCheckType(CheckControl.CheckType.ELLIPSE);
		check1.getModel().setLabel("First check");
		booleanPanel.add(check1);	
		final CheckControl check2=new CheckControl(session, "check2");	
		check2.setCheckType(CheckControl.CheckType.ELLIPSE);
		check2.getModel().setLabel("Second check");
		check2.getModel().setEnabled(false);	//TODO fix
		booleanPanel.add(check2);	
		final ModelGroup<ValueModel<Boolean>> booleanGroup=new MutualExclusionModelGroup();
		booleanGroup.add(check1.getModel());
		booleanGroup.add(check2.getModel());
	
		horizontalPanel.add(booleanPanel);

		final Button testButtona=new Button(session, "testButton");
		testButtona.getModel().setLabel("Nuther button.");
		horizontalPanel.add(testButtona);	//add a new button
/*TODO fix		
		final Panel booleanPanela=new Panel(session, new FlowLayout(Axis.Y));	//create a panel flowing vertically
		booleanPanela.getModel().setLabel("Check one of these");
		final CheckControl check1a=new CheckControl(session, "check1");
		check1a.setCheckType(CheckControl.CheckType.ELLIPSE);
		check1a.getModel().setLabel("First check");
		booleanPanela.add(check1a);	
		final CheckControl check2a=new CheckControl(session, "check2");	
		check2a.setCheckType(CheckControl.CheckType.ELLIPSE);
		check2a.getModel().setLabel("Second check");
		booleanPanela.add(check2a);	
		final ModelGroup<ValueModel<Boolean>> booleanGroupa=new MutualExclusionModelGroup();
		booleanGroupa.add(check1a.getModel());
		booleanGroupa.add(check2a.getModel());

		horizontalPanel.add(booleanPanela);
*/
		
		final Image image=new Image(session);
		image.getModel().setImage(URI.create("http://www.garretwilson.com/photos/2000/february/cowcalf.jpg"));
		image.getModel().setLabel("Cow and Calf");
		image.getModel().setMessage("A cow and her minutes-old calf.");
		horizontalPanel.add(image);
		
		
		add(horizontalPanel);
		
/*TODO del		
		final Heading resourceHeading=new Heading(session, 2);
		resourceHeading.getModel().setLabelResourceKey("test.resource");
		add(resourceHeading);
*/

		final Label afterImageLabel=new Label(session);
		afterImageLabel.getModel().setLabel("This is a lot of text. ;alsjfd ;lkjas ;ljag ;lkjas g;lkajg; laksgj akjlshf lkjashd flkjsdhlksahlsadkhj asldkhjf ;sgdh a;lgkh a;glkha s;dglh asgd;");
		add(afterImageLabel);

		final ListControl<String> listSelectControl=new ListControl<String>(session, String.class, new SingleSelectionStrategy<String>());
		listSelectControl.getModel().setLabel("Choose an option.");
		listSelectControl.getModel().add("The first option");
		listSelectControl.getModel().add(null);
		listSelectControl.getModel().add("The second option");
		listSelectControl.getModel().add("The third option");
		listSelectControl.getModel().add("The fourth option");
		add(listSelectControl);
	}

}
