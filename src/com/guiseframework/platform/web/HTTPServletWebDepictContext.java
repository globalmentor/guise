package com.guiseframework.platform.web;

import java.io.*;
import java.lang.ref.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.Collections.*;

import javax.mail.internet.ContentType;
import javax.servlet.http.HttpServletResponse;

import static com.garretwilson.io.ContentTypeConstants.*;
import static com.garretwilson.io.ContentTypes.*;

import com.garretwilson.io.ParseReader;
import com.garretwilson.servlet.http.HttpServletUtilities;
import com.garretwilson.text.CharacterEncoding;
import static com.garretwilson.text.FormatUtilities.*;
import com.garretwilson.text.xml.QualifiedName;
import static com.garretwilson.text.CharacterEncoding.*;
import static com.garretwilson.text.xml.xhtml.XHTMLConstants.*;
import com.garretwilson.util.*;
import static com.garretwilson.util.ArrayUtilities.*;

import com.guiseframework.Destination;
import com.guiseframework.GuiseSession;

import static com.globalmentor.java.Objects.*;
import static com.guiseframework.platform.web.WebPlatform.*;
import com.guiseframework.platform.web.css.*;
import com.guiseframework.platform.web.css.GuiseCSSProcessor.IE6FixClass;

/**A web depict context of an HTTP servlet.
The output stream defaults to <code>text/plain</code> encoded in <code>UTF-8</code>.
Quirks mode is only used with IE6 in some configurations.
@author Garret Wilson
*/
public class HTTPServletWebDepictContext extends AbstractWebDepictContext
{

	/**The Guise HTTP request.*/
	private final HTTPServletGuiseRequest guiseRequest;

		/**@return The Guise HTTP request.*/
		protected HTTPServletGuiseRequest getGuiseRequest() {return guiseRequest;}

	/**The HTTP servlet response.*/
	private final HttpServletResponse response;

		/**@return The HTTP servlet response.*/
		protected HttpServletResponse getResponse() {return response;}

		/**The current full absolute URI for this depiction, including any query.*/
		private final URI depictURI;

		/**@return The current full absolute URI for this depiction, including any query.*/
		public URI getDepictionURI() {return depictURI;}

	/**Whether quirks mode is being used.*/
	private final boolean isQuirksMode;

		/**@return Whether quirks mode is being used.*/
		public boolean isQuirksMode() {return isQuirksMode;}

	/**The current content type of the output.*/
	private ContentType outputContentType=createContentType(TEXT, PLAIN_SUBTYPE);	//default to text/plain

	/**The qualified name to use for the attribute hash attribute.*/
	private final String attributeHashAttributeQualifiedName=getQualifiedName(GUISE_ML_NAMESPACE_URI, ATTRIBUTE_ATTRIBUTE_HASH);

		/**@return The qualified name to use for the attribute hash attribute.*/
		protected String getAttributeHashAttributeQualifiedName() {return attributeHashAttributeQualifiedName;}

	/**The qualified name to use for the content hash attribute.*/
	private final String contentHashAttributeQualifiedName=getQualifiedName(GUISE_ML_NAMESPACE_URI, ATTRIBUTE_CONTENT_HASH);

		/**@return The qualified name to use for the content hash attribute.*/
		protected String getContentHashAttributeQualifiedName() {return contentHashAttributeQualifiedName;}

	/**Constructor.
	@param guiseRequest Guise request information.
	@param response The HTTP servlet response. 
	@param session The Guise user session of which this context is a part.
	@param destination The destination with which this context is associated.
	@exception NullPointerException if the given Guise request, session, and/or destination is <code>null</code>.
	@exception IOException If there was an I/O error loading a needed resource.
	*/
	public HTTPServletWebDepictContext(final HTTPServletGuiseRequest guiseRequest, final HttpServletResponse response, final GuiseSession session, final Destination destination) throws IOException
	{
		super(session, destination);	//construct the parent class
		this.guiseRequest=checkInstance(guiseRequest, "Guise request cannot be null.");
		this.response=checkInstance(response, "Response cannot be null.");
//TODO decide if we want this to include parameters or not		this.navigationURI=URI.create(request.getRequestURL().toString());	//create the absolute navigation URI from the HTTP requested URL
		this.depictURI=HttpServletUtilities.getRequestURI(guiseRequest.getHTTPServletRequest());	//get the depiction URI, containing any query
/*TODO del if not needed
		final String referrer=getReferer(request);	//get the request referrer, if any
		referrerURI=referrer!=null ? getPlainURI(URI.create(referrer)) : null;	//get a plain URI version of the referrer, if there is a referrer
*/
/*TODO del
		final String contentTypeString=request.getContentType();	//get the request content type
		final ContentType contentType=contentTypeString!=null ? createContentType(contentTypeString) : null;	//create a content type object from the request content type, if there is one
*/
		setHashAttributesGenerated(true);	//always generate hash attributes
		final ContentType defaultContentType=createContentType(outputContentType.getPrimaryType(), outputContentType.getSubType(), new NameValuePair<String, String>(CHARSET_PARAMETER, UTF_8));	//default to text/plain encoded in UTF-8
		response.setContentType(defaultContentType.toString());	//initialize the default content type and encoding
		HttpServletUtilities.setContentLanguage(response, session.getLocale());	//set the response content language
		final WebUserAgentProduct webUserAgent=getPlatform().getClientProduct();	//get the web user agent
		this.isQuirksMode=webUserAgent.getBrand()==WebUserAgentProduct.Brand.INTERNET_EXPLORER && webUserAgent.getVersionNumber()<7 && !session.getApplication().isThemed();	//only use quirks mode for certain legacy (non-themed) applications using IE6
	}

	/**@return The character encoding currently used for the text output.*/
	public CharacterEncoding getOutputCharacterEncoding()
	{
		return new CharacterEncoding(getResponse().getCharacterEncoding(), NO_BOM);	//return the current output character encoding
	}

	/**@return The current content type of the text output.*/
	public ContentType getOutputContentType() {return outputContentType;}

	/**Sets the content type of the text output.
	This implementation removes all parameters and adds a character set parameter of the current encoding.
	@param contentType The content type of the text output.
	*/
	public void setOutputContentType(final ContentType contentType)
	{
			//default to text/plain encoded in UTF-8 replace the charset parameter with the currently set character set TODO change to really just replace one parameter, instead of removing all others
		this.outputContentType=createContentType(contentType.getPrimaryType(), contentType.getSubType(), new NameValuePair<String, String>(CHARSET_PARAMETER, getOutputCharacterEncoding().toString()));
		getResponse().setContentType(this.outputContentType.toString());	//set the content type of the response, including the current character set
	}

	/**The map of soft references to IE6 fix class lists, keyed to style URIs.*/
	private final static Map<URI, Reference<List<IE6FixClass>>> styleIE6FixClassesReferenceMap=new ConcurrentHashMap<URI, Reference<List<IE6FixClass>>>();

	/**Returns a list of IE6 fix classes for the given stylesheet URI.
	If the IE6 classes have not yet been determined for this stylesheet URI, the stylesheet will be loaded and parsed, and the IE6 fix classes cached.
	@param styleURI The URI of the stylesheet for which IE6 fix classes should be returned.
	@return The list of IE6 fix classes for the given stylesheet, which may be empty if the stylesheet could not be loaded.
	*/
	protected List<IE6FixClass> getIE6FixClasses(final URI styleURI)
	{
		final Reference<List<IE6FixClass>> ie6FixClassesReference=styleIE6FixClassesReferenceMap.get(styleURI);	//get a reference to a list of IE6 fix classes
		List<IE6FixClass> ie6FixClasses=ie6FixClassesReference!=null ? ie6FixClassesReference.get() : null;	//dereference the list if we got a reference
		if(ie6FixClasses==null)	//if no IE6 fix classes are available for this stylesheet, load the stylesheet and cache the results (the race condition here is unlikely and benign, as in the worse case we'll load the stylesheet an extra time initially)
		{
			ie6FixClasses=new ArrayList<IE6FixClass>();	//create a new array of IE6 fix classes
			try
			{
				final InputStream inputStream=getSession().getApplication().getInputStream(styleURI);	//get an input stream to the stylesheet
				if(inputStream!=null)	//if this stylesheet exists, load it; otherwise, just stick with the empty list of IE6 fix classes so that we won't try to load the stylesheet again 
				{
	//TODO del Debug.trace("got input stream to:", styleURI);
					final ParseReader cssReader=new ParseReader(new InputStreamReader(inputStream, UTF_8));
					try
					{
						final GuiseCSSProcessor cssProcessor=new GuiseCSSProcessor();	//TODO comment
						final CSSStylesheet cssStylesheet=cssProcessor.process(cssReader);	//parse the stylesheet
						cssProcessor.fixIE6Stylesheet(cssStylesheet);	//fix this stylesheet for IE6 so that we can have the fixed classes
	/*TODO del
	for(final GuiseCSSProcessor.IE6FixClass ie6FixClass:cssProcessor.getIE6FixClasses())	//TODO del; testing
	{
		Debug.trace("for stylesheet", styleURI, "got IE6 fix class", ie6FixClass.getFixClass());
	}
	*/
						ie6FixClasses.addAll(cssProcessor.getIE6FixClasses());	//add all these IE6 fix classes to our list
					}
					finally
					{
						cssReader.close();	//always close the input stream
					}
				}
			}
			catch(final IOException ioException)	//if there is an error loading the stylesheet, stick with an empty list of IE6 fix classes
			{
				Debug.warn(ioException);	//TODO use a Guise warn method eventually
			}
			styleIE6FixClassesReferenceMap.put(styleURI, new SoftReference<List<IE6FixClass>>(ie6FixClasses));	//cache a soft reference to this list for next time
		}
		return ie6FixClasses;	//return the IE6 fix classes
	}

	/**Retrieves the value of a given attribute.
	This version modifed any class attributes if there are IE6 fix classes and this is not an AJAX call.
	@param elementQualifedName The qualified name of the element.
	@param attributeQualifiedName The qualified name of the attribute.
	@param attributeValue The default value of the attribute.
	@return The value of the attribute.
	@see #getIE6FixClasses()
	@see #isAJAX()
	*/
	protected String getAttributeValue(final QualifiedName elementQualifiedName, final QualifiedName attributeQualifiedName, String attributeValue)
	{
		final WebUserAgentProduct webUserAgent=getPlatform().getClientProduct();	//get the web user agent
		if(webUserAgent.getBrand()==WebUserAgentProduct.Brand.INTERNET_EXPLORER && webUserAgent.getVersionNumber()<7)	//if the user agent is IE6, touch up the class name
		{
			if(XHTML_NAMESPACE_URI.toString().equals(elementQualifiedName.getNamespaceURI()) && attributeQualifiedName.getNamespaceURI()==null && ATTRIBUTE_CLASS.equals(attributeQualifiedName.getLocalName()))	//if this is an XHTML class attribute
			{
				//TODO del Debug.trace("ready to fix classes:", attributeValue);
				final Set<String> newClasses=new HashSet<String>();	//we'll collect new classes to add; use a set to keep from getting duplicates of fixed class names (multiple selectors may match the same class that needs fixed)
				final String[] classNames=attributeValue.split("\\s");	//split out the class names
				for(final URI styleURI:getStyles())	//for each stylesheet
				{
					final List<IE6FixClass> ie6FixClasses=getIE6FixClasses(styleURI);	//get the fix classes for this stylesheet
					for(final IE6FixClass ie6FixClass:ie6FixClasses)	//look at each IE6 fix classes
					{
						final List<SimpleSelector> simpleSelectorSequence=ie6FixClass.getSimpleSelectorSequence();	//get this simple selector sequence
//					TODO del Debug.trace("looking at IE6 fix class", ie6FixClass.getFixClass(), "with simple selector count", simpleSelectorSequence.size());
						final TypeSelector typeSelector=simpleSelectorSequence.size()>0 ? asInstance(simpleSelectorSequence.get(0), TypeSelector.class) : null;	//get the first simple selector if it is a type selector
						if(typeSelector==null || typeSelector.getTypeName().equals(elementQualifiedName.getLocalName()))	//make sure the type selector matches the element name
						{
							boolean classesMatch=true;	//see if this element matches all the classes (we don't have to make sure there are multiple classes; if not, the IE6 fix object wouldn't be included in the list
							for(final SimpleSelector simpleSelector:simpleSelectorSequence)	//for each simple selector
							{
								if(simpleSelector!=typeSelector)	//if this isn't the type selector
								{
									if(simpleSelector instanceof ClassSelector)	//if this is a class selector
									{
//									TODO del Debug.trace("looking at class selector:", ((ClassSelector)simpleSelector).getClassName());
										if(!contains(classNames, ((ClassSelector)simpleSelector).getClassName()))	//if this class name is not contained in the list TODO switch to a hash set to make this more efficient
										{
											classesMatch=false;	//all the classes don't match; don't bother fixing things up
											break;	//stop looking at the classes
										}
									}
								}
							}
							if(classesMatch)	//if all the classes selectors match
							{
//							TODO del Debug.trace("adding fix class:", ie6FixClass.getFixClass());
								newClasses.add(ie6FixClass.getFixClass());	//add the fix class to the class names
							}
						}
					}
				}
				if(!newClasses.isEmpty())	//if there are new classes
				{
					addAll(newClasses, classNames);	//add the normal class names to our new classes list
					attributeValue=formatList(new StringBuilder(), ' ', newClasses).toString();	//combine the classes back into one class string
//TODO del Debug.trace("new attribute value:", attributeValue);
				}
			}
		}
		return attributeValue;	//return the attribute value with no modifications
	}

}
