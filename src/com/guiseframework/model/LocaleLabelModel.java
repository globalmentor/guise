package com.guiseframework.model;

import java.util.Locale;

import com.guiseframework.Guise;
import com.guiseframework.GuiseSession;

import static com.globalmentor.java.Objects.*;

/**A label model that provides a localized version of a locale name.
@author Garret Wilson
*/
public class LocaleLabelModel extends DefaultLabelModel
{

	/**The Guise session that owns this object.*/
	private final GuiseSession session;

		/**@return The Guise session that owns this object.*/
		public GuiseSession getSession() {return session;}

	/**The locale the label should represent.*/
	private final Locale locale;

		/**@return The locale the label should represent.*/
		public Locale getLocale() {return locale;}

	/**Constructs a label model indicating the locale to represent.
	@exception NullPointerException if the given locale is <code>null</code>.
	*/
	public LocaleLabelModel(final Locale locale)
	{
		this.session=Guise.getInstance().getGuiseSession();	//store a reference to the current Guise session
		this.locale=checkInstance(locale, "Locale cannot be null");	//save the locale
	}

	/**Determines the text of the label.
	This version returns the localized version of the locale if the value has not been explicitly set.
	@return The label text, or <code>null</code> if there is no label text.
	*/
	public String getLabel()
	{
		final String label=super.getLabel();	//get the default label
		return label!=null ? label : getLocale().getDisplayName(getSession().getLocale());	//return the localized name of the locale based upon the current session locale, if no locale was explicitly set 
	}

}
