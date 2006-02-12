package com.javaguise.component;

import static com.garretwilson.lang.ClassUtilities.*;

import java.net.URI;
import java.util.MissingResourceException;

import com.garretwilson.lang.ObjectUtilities;
import com.javaguise.GuiseSession;
import com.javaguise.model.ActionValueModel;
import com.javaguise.model.DefaultActionValueModel;
import com.javaguise.model.ImageModel;

/**Image control that can be selected and generates actions.
@author Garret Wilson
*/
public class ImageSelectActionControl extends AbstractSelectActionControl<ImageSelectActionControl>
{

	/**The image bound property.*/
	public final static String IMAGE_PROPERTY=getPropertyName(ImageSelectActionControl.class, "image");
	/**The image resource key bound property.*/
	public final static String IMAGE_RESOURCE_KEY_PROPERTY=getPropertyName(ImageSelectActionControl.class, "imageResourceKey");
	/**The rollover image bound property.*/
	public final static String ROLLOVER_IMAGE_PROPERTY=getPropertyName(ImageSelectActionControl.class, "rolloverImage");
	/**The rollover image resource key bound property.*/
	public final static String ROLLOVER_IMAGE_RESOURCE_KEY_PROPERTY=getPropertyName(ImageSelectActionControl.class, "rolloverImageResourceKey");
	/**The selected image bound property.*/
	public final static String SELECTED_IMAGE_PROPERTY=getPropertyName(ImageSelectActionControl.class, "selectedImage");
	/**The selected image resource key bound property.*/
	public final static String SELECTED_IMAGE_RESOURCE_KEY_PROPERTY=getPropertyName(ImageSelectActionControl.class, "selectedImageResourceKey");

	/**The image URI, or <code>null</code> if there is no image URI.*/
	private URI image=null;

		/**Determines the URI of the image.
		If an image is specified, it will be used; otherwise, a value will be loaded from the resources if possible.
		@return The image URI, or <code>null</code> if there is no image URI.
		@exception MissingResourceException if there was an error loading the value from the resources.
		@see #getImageResourceKey()
		*/
		public URI getImage() throws MissingResourceException
		{
			return getSession().determineURI(image, getImageResourceKey());	//get the value or the resource, if available
		}

		/**Sets the URI of the image.
		This is a bound property of type <code>URI</code>.
		@param newImage The new URI of the image.
		@see ImageModel#IMAGE_PROPERTY
		*/
		public void setImage(final URI newImage)
		{
			if(!ObjectUtilities.equals(image, newImage))	//if the value is really changing
			{
				final URI oldImage=image;	//get the old value
				image=newImage;	//actually change the value
				firePropertyChange(IMAGE_PROPERTY, oldImage, newImage);	//indicate that the value changed
			}			
		}

	/**The image URI resource key, or <code>null</code> if there is no image URI resource specified.*/
	private String imageResourceKey=null;

		/**@return The image URI resource key, or <code>null</code> if there is no image URI resource specified.*/
		public String getImageResourceKey() {return imageResourceKey;}

		/**Sets the key identifying the URI of the image in the resources.
		This is a bound property.
		@param newImageResourceKey The new image URI resource key.
		@see #IMAGE_RESOURCE_KEY_PROPERTY
		*/
		public void setImageResourceKey(final String newImageResourceKey)
		{
			if(!ObjectUtilities.equals(imageResourceKey, newImageResourceKey))	//if the value is really changing
			{
				final String oldImageResourceKey=imageResourceKey;	//get the old value
				imageResourceKey=newImageResourceKey;	//actually change the value
				firePropertyChange(IMAGE_RESOURCE_KEY_PROPERTY, oldImageResourceKey, newImageResourceKey);	//indicate that the value changed
			}
		}

	/**The rollover image URI, or <code>null</code> if there is no rollover image URI.*/
	private URI rolloverImage=null;

		/**Determines the URI of the rollover image.
		If an image is specified, it will be used; otherwise, a value will be loaded from the resources if possible.
		@return The rollover image URI, or <code>null</code> if there is no rollover image URI.
		@exception MissingResourceException if there was an error loading the value from the resources.
		@see #getRolloverImageResourceKey()
		*/
		public URI getRolloverImage() throws MissingResourceException
		{
			return getSession().determineURI(rolloverImage, getRolloverImageResourceKey());	//get the value or the resource, if available
		}

		/**Sets the URI of the rollover image.
		This is a bound property of type <code>URI</code>.
		@param newRolloverImage The new URI of the rollover image.
		@see #ROLLOVER_IMAGE_PROPERTY
		*/
		public void setRolloverImage(final URI newRolloverImage)
		{
			if(!ObjectUtilities.equals(rolloverImage, newRolloverImage))	//if the value is really changing
			{
				final URI oldRolloverImage=image;	//get the old value
				rolloverImage=newRolloverImage;	//actually change the value
				firePropertyChange(ROLLOVER_IMAGE_PROPERTY, oldRolloverImage, newRolloverImage);	//indicate that the value changed
			}			
		}

	/**The rollover image URI resource key, or <code>null</code> if there is no rollover image URI resource specified.*/
	private String rolloverImageResourceKey=null;

		/**@return The rollover image URI resource key, or <code>null</code> if there is no rollover image URI resource specified.*/
		public String getRolloverImageResourceKey() {return rolloverImageResourceKey;}

		/**Sets the key identifying the URI of the rollover image in the resources.
		This is a bound property.
		@param newRolloverImageResourceKey The new rollover image URI resource key.
		@see #ROLLOVER_IMAGE_RESOURCE_KEY_PROPERTY
		*/
		public void setRolloverImageResourceKey(final String newRolloverImageResourceKey)
		{
			if(!ObjectUtilities.equals(rolloverImageResourceKey, newRolloverImageResourceKey))	//if the value is really changing
			{
				final String oldRolloverImageResourceKey=rolloverImageResourceKey;	//get the old value
				rolloverImageResourceKey=newRolloverImageResourceKey;	//actually change the value
				firePropertyChange(ROLLOVER_IMAGE_RESOURCE_KEY_PROPERTY, oldRolloverImageResourceKey, newRolloverImageResourceKey);	//indicate that the value changed
			}
		}

	/**The selected image URI, or <code>null</code> if there is no selected image URI.*/
	private URI selectedImage=null;

		/**Determines the URI of the selected image.
		If an image is specified, it will be used; otherwise, a value will be loaded from the resources if possible.
		@return The selected image URI, or <code>null</code> if there is no selected image URI.
		@exception MissingResourceException if there was an error loading the value from the resources.
		@see #getSelectedImageResourceKey()
		*/
		public URI getSelectedImage() throws MissingResourceException
		{
			return getSession().determineURI(selectedImage, getSelectedImageResourceKey());	//get the value or the resource, if available
		}

		/**Sets the URI of the selected image.
		This is a bound property of type <code>URI</code>.
		@param newSelectedImage The new URI of the selected image.
		@see #SELECTED_IMAGE_PROPERTY
		*/
		public void setSelectedImage(final URI newSelectedImage)
		{
			if(!ObjectUtilities.equals(selectedImage, newSelectedImage))	//if the value is really changing
			{
				final URI oldSelectedImage=image;	//get the old value
				selectedImage=newSelectedImage;	//actually change the value
				firePropertyChange(SELECTED_IMAGE_PROPERTY, oldSelectedImage, newSelectedImage);	//indicate that the value changed
			}			
		}

	/**The selected image URI resource key, or <code>null</code> if there is no selected image URI resource specified.*/
	private String selectedImageResourceKey=null;

		/**@return The selected image URI resource key, or <code>null</code> if there is no selected image URI resource specified.*/
		public String getSelectedImageResourceKey() {return selectedImageResourceKey;}

		/**Sets the key identifying the URI of the selected image in the resources.
		This is a bound property.
		@param newSelectedImageResourceKey The new selected image URI resource key.
		@see #SELECTED_IMAGE_RESOURCE_KEY_PROPERTY
		*/
		public void setSelectedImageResourceKey(final String newSelectedImageResourceKey)
		{
			if(!ObjectUtilities.equals(selectedImageResourceKey, newSelectedImageResourceKey))	//if the value is really changing
			{
				final String oldSelectedImageResourceKey=selectedImageResourceKey;	//get the old value
				selectedImageResourceKey=newSelectedImageResourceKey;	//actually change the value
				firePropertyChange(SELECTED_IMAGE_RESOURCE_KEY_PROPERTY, oldSelectedImageResourceKey, newSelectedImageResourceKey);	//indicate that the value changed
			}
		}

	/**Session constructor with a default data model.
	@param session The Guise session that owns this component.
	@exception NullPointerException if the given session is <code>null</code>.
	*/
	public ImageSelectActionControl(final GuiseSession session)
	{
		this(session, (String)null);	//construct the component, indicating that a default ID should be used
	}

	/**Session and ID constructor with a default data model.
	@param session The Guise session that owns this component.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	@exception NullPointerException if the given session is <code>null</code>.
	@exception IllegalArgumentException if the given identifier is not a valid component identifier.
	*/
	public ImageSelectActionControl(final GuiseSession session, final String id)
	{
		this(session, id, new DefaultActionValueModel<Boolean>(session, Boolean.class, Boolean.FALSE));	//construct the class with a default model
	}

	/**Session and model constructor.
	@param session The Guise session that owns this component.
	@param model The component data model.
	@exception NullPointerException if the given session and/or model is <code>null</code>.
	*/
	public ImageSelectActionControl(final GuiseSession session, final ActionValueModel<Boolean> model)
	{
		this(session, null, model);	//construct the component, indicating that a default ID should be used
	}

	/**Session, ID, and model constructor.
	@param session The Guise session that owns this component.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	@param model The component data model.
	@exception NullPointerException if the given session and/or model is <code>null</code>.
	@exception IllegalArgumentException if the given identifier is not a valid component identifier.
	*/
	public ImageSelectActionControl(final GuiseSession session, final String id, final ActionValueModel<Boolean> model)
	{
		super(session, id, model);	//construct the parent class
	}

}
