package com.guiseframework.prototype;

import static com.garretwilson.lang.ObjectUtilities.*;
import static com.garretwilson.text.TextUtilities.*;

import java.net.URI;

import javax.mail.internet.ContentType;

import com.garretwilson.lang.ObjectUtilities;
import com.guiseframework.model.*;

/**Contains prototype information for a value control.
@param <V> The type of value contained in the model.
@author Garret Wilson
*/
public class ValuePrototype<V> extends DefaultValueModel<V> implements Prototype, LabelModel, Enableable
{

	/**Whether the control is enabled and can receive user input.*/
	private boolean enabled=true;

		/**@return Whether the control is enabled and can receive user input.*/
		public boolean isEnabled() {return enabled;}

		/**Sets whether the control is enabled and and can receive user input.
		This is a bound property of type <code>Boolean</code>.
		@param newEnabled <code>true</code> if the control should indicate and accept user input.
		@see #ENABLED_PROPERTY
		*/
		public void setEnabled(final boolean newEnabled)
		{
			if(enabled!=newEnabled)	//if the value is really changing
			{
				final boolean oldEnabled=enabled;	//get the old value
				enabled=newEnabled;	//actually change the value
				firePropertyChange(ENABLED_PROPERTY, Boolean.valueOf(oldEnabled), Boolean.valueOf(newEnabled));	//indicate that the value changed
			}
		}

	/**The icon URI, which may be a resource URI, or <code>null</code> if there is no icon URI.*/
	private URI icon=null;

		/**@return The icon URI, which may be a resource URI, or <code>null</code> if there is no icon URI.*/
		public URI getIcon() {return icon;}

		/**Sets the URI of the icon.
		This is a bound property of type <code>URI</code>.
		@param newIcon The new URI of the icon, which may be a resource URI.
		@see #ICON_PROPERTY
		*/
		public void setIcon(final URI newIcon)
		{
			if(!ObjectUtilities.equals(icon, newIcon))	//if the value is really changing
			{
				final URI oldIcon=icon;	//get the old value
				icon=newIcon;	//actually change the value
				firePropertyChange(ICON_PROPERTY, oldIcon, newIcon);	//indicate that the value changed
			}			
		}

	/**The label text, which may include a resource reference, or <code>null</code> if there is no label text.*/
	private String label=null;

		/**@return The label text, which may include a resource reference, or <code>null</code> if there is no label text.*/
		public String getLabel() {return label;}

		/**Sets the text of the label.
		This is a bound property.
		@param newLabelText The new text of the label, which may include a resource reference.
		@see #LABEL_PROPERTY
		*/
		public void setLabel(final String newLabelText)
		{
			if(!ObjectUtilities.equals(label, newLabelText))	//if the value is really changing
			{
				final String oldLabel=label;	//get the old value
				label=newLabelText;	//actually change the value
				firePropertyChange(LABEL_PROPERTY, oldLabel, newLabelText);	//indicate that the value changed
			}			
		}

	/**The content type of the label text.*/
	private ContentType labelContentType=PLAIN_TEXT_CONTENT_TYPE;

		/**@return The content type of the label text.*/
		public ContentType getLabelContentType() {return labelContentType;}

		/**Sets the content type of the label text.
		This is a bound property.
		@param newLabelTextContentType The new label text content type.
		@exception NullPointerException if the given content type is <code>null</code>.
		@exception IllegalArgumentException if the given content type is not a text content type.
		@see #LABEL_CONTENT_TYPE_PROPERTY
		*/
		public void setLabelContentType(final ContentType newLabelTextContentType)
		{
			checkInstance(newLabelTextContentType, "Content type cannot be null.");
			if(labelContentType!=newLabelTextContentType)	//if the value is really changing
			{
				final ContentType oldLabelTextContentType=labelContentType;	//get the old value
				if(!isText(newLabelTextContentType))	//if the new content type is not a text content type
				{
					throw new IllegalArgumentException("Content type "+newLabelTextContentType+" is not a text content type.");
				}
				labelContentType=newLabelTextContentType;	//actually change the value
				firePropertyChange(LABEL_CONTENT_TYPE_PROPERTY, oldLabelTextContentType, newLabelTextContentType);	//indicate that the value changed
			}			
		}

	/**Constructs a value prototype indicating the type of value it can hold.
	The default value is set to <code>null</code>.
	@param valueClass The class indicating the type of value held in the model.
	@exception NullPointerException if the given value class is <code>null</code>.
	*/
	public ValuePrototype(final Class<V> valueClass)
	{
		this(valueClass, null);	//construct the class with a null default value
	}

	/**Constructs a value prototype indicating the type of value it can hold, along with an initial value.
	@param valueClass The class indicating the type of value held in the model.
	@param defaultValue The default value, which will not be validated.
	@exception NullPointerException if the given value class is <code>null</code>.
	*/
	public ValuePrototype(final Class<V> valueClass, final V defaultValue)
	{
		super(valueClass, defaultValue);	//construct the value model parent class
	}
}