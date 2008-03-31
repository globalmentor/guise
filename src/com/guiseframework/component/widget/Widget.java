package com.guiseframework.component.widget;

import java.util.Set;

import javax.mail.internet.ContentType;

import static com.globalmentor.io.ContentTypes.*;

import com.guiseframework.component.Component;

/**A component that can be embedded in certain content and can be edited within a widget editing environment.
Widgets must provide a default constructor.
@author Garret Wilson
*/
public interface Widget extends Component
{

	/**The MIME type of a Guise widget.*/
	public final static ContentType WIDGET_CONTENT_TYPE=new ContentType(APPLICATION_PRIMARY_TYPE, SUBTYPE_EXTENSION_PREFIX+"guise-widget", null);

	/**Retrieves that names of parameters that are allowed to be accessed in a widget context.
	@return The set of names of widget parameters.
	*/
	public Set<String> getParameterNames();
}