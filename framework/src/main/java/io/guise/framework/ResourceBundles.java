/*
 * Copyright © 1996-2008 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.guise.framework;

import java.io.*;
import java.net.*;
import java.util.*;

import static java.util.Objects.*;

import com.globalmentor.io.IO;
import com.globalmentor.model.Locales;
import com.globalmentor.rdf.*;
import com.globalmentor.util.HashMapResourceBundle;

import static com.globalmentor.io.Filenames.*;
import static com.globalmentor.java.Java.*;
import static com.globalmentor.model.Locales.*;
import static com.globalmentor.net.URIs.*;
import static com.globalmentor.rdf.RDFResources.*;
import static com.globalmentor.util.PropertiesUtilities.*;
import static com.globalmentor.w3c.spec.XML.*;

/**
 * Utilities for working with resource bundles.
 * @author Garret Wilson
 * @deprecated Switch to using Rincl.
 */
@Deprecated
public class ResourceBundles //TODO moved out of globalmentor-core to allow org.urframework project extraction; refactor to allow flexibility and storage format pluggability
{

	/** The map of resource bundles softly keyed to resource bundle base paths. This serves as a soft cache to resource bundles. */
	//TODO fix caching; the problem is currently how to verify the same loader and resource path while keeping a weak reference to the loader private static final Map<String, ResourceBundle> resourceBundleMap=synchronizedMap(new SoftValueHashMap<String, ResourceBundle>());

	/**
	 * The format in which a resource bundle is serialized, in order of preference.
	 * @author Garret Wilson
	 */
	private enum ResourceBundleFormat {
		/** The resource bundle is serialized in a TURF file. */
		TURF(org.urframework.TURF.NAME_EXTENSION),

		/** The resource bundle is serialized in an RDF+XML file. */
		RDFXML(com.globalmentor.w3c.spec.RDF.XML.NAME_EXTENSION),

		/** The resource bundle is serialized in an XML file. */
		XML(XML_NAME_EXTENSION),

		/** The resource bundle is serialized in a properties file. */
		PROPERTIES(PROPERTIES_NAME_EXTENSION);

		/** The extension for this resource bundle file type. */
		private final String extension;

		/** @return The extension for this resource bundle file type. */
		public String getExtension() {
			return extension;
		}

		/**
		 * Extension constructor.
		 * @param extension The extension for this resource bundle file type.
		 * @throws NullPointerException if the given extension is <code>null</code>.
		 */
		private ResourceBundleFormat(final String extension) {
			this.extension = requireNonNull(extension, "Extension cannot be null.");
		}
	}

	/**
	 * Loads a resource bundle for a given base name and locale. This implementation recognizes properties stored in <code>.turf</code>, <code>.rdf</code>,
	 * <code>.xml</code>, and <code>.properties</code> files, searching in that order. TURF property files are only used if a given TURF resource I/O instance is
	 * provided for reading from the file. RDF property files are only used if a given RDF resource I/O instance is provided for reading from the file.
	 * @param baseName The base name of the resource bundle, which is a fully qualified class name, such as "myProperties".
	 * @param locale The locale for which a resource bundle is desired.
	 * @param loader The class loader from which to load the resource bundle.
	 * @param parent The parent resource bundle, or <code>null</code> if there should be no parent for resolving resources.
	 * @param turfResourceIO The I/O support for loading resources from a TURF representation, or <code>null</code> if TURF resource bundles are not supported.
	 * @param rdfResourceIO The I/O support for loading resources from an RDF+XML serialization, or <code>null</code> if RDF resource bundles are not supported.
	 * @param rdfPropertyNamespaceURI The namespace of the properties to gather, using the property local name as the map entry key, or <code>null</code> if RDF
	 *          resource bundles are not supported.
	 * @return A resource bundle for the given base name and locale.
	 * @throws MissingResourceException if no resource bundle for the specified base name can be found, or if there is an error loading the resource bundle.
	 */
	public static ResourceBundle getResourceBundle(final String baseName, final Locale locale, final ClassLoader loader, final ResourceBundle parent,
			final IO<Map<Object, Object>> turfResourceIO, final IO<? extends RDFResource> rdfResourceIO, final URI rdfPropertyNamespaceURI)
			throws MissingResourceException {
		final String basePath = baseName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR); //create a base path from base name
		final ResourceBundleFormat[] resourceBundleFormats = ResourceBundleFormat.values(); //get the available resource bundle formats
		final int resourceBundleFormatCount = resourceBundleFormats.length; //see how many resource bundle formats there are
		final String[] paths = new String[resourceBundleFormatCount]; //create an array of paths to try
		for(int resourceBundleFormatIndex = 0; resourceBundleFormatIndex < resourceBundleFormatCount; ++resourceBundleFormatIndex) { //for each resource bundle format
			paths[resourceBundleFormatIndex] = addExtension(basePath, resourceBundleFormats[resourceBundleFormatIndex].getExtension()); //create a path to test for this resource bundle format
		}
		for(int depth = 3; depth >= 0; --depth) { //try different locales, starting with the most specific, until we find an input stream
			for(int resourceBundleFormatIndex = 0; resourceBundleFormatIndex < resourceBundleFormatCount; ++resourceBundleFormatIndex) { //for each resource bundle format
				final ResourceBundleFormat resourceBundleFormat = resourceBundleFormats[resourceBundleFormatIndex]; //get this resource bundle format
				if(resourceBundleFormat == ResourceBundleFormat.TURF && turfResourceIO == null) { //if this is a TURF file, only use it if we have I/O for the file
					continue; //don't check for TURF, because we don't have the means to read it
				}
				if(resourceBundleFormat == ResourceBundleFormat.RDFXML && (rdfResourceIO == null || rdfPropertyNamespaceURI == null)) { //if this is an RDF+XML file, only use it if we have I/O for the file
					continue; //don't check for RDF+XML, because we don't have the means to read it
				}
				final String resourcePath = getLocaleCandidatePath(paths[resourceBundleFormatIndex], locale, depth); //get a candidate path for the resource bundle at this locale depth, using the path for this resource bundle type
				if(resourcePath != null) { //if we can generate a candidate path for the locale at this depth
					/*TODO fix
									final ResourceBundle cachedResourceBundle=resourceBundleMap.get(resourcePath);	//see if we have a cached XML resource bundle
									if(cachedResourceBundle!=null) {	//if there is a cached XML resource bundle
										return cachedResourceBundle;	//return the cached bundle
									}
									else	//if there is no cached bundle, try to load one
					*/
					{
						final URL resourceURL = loader.getResource(resourcePath); //see if this resource bundle exists
						if(resourceURL != null) { //if we found an existing resource bundle
							try {
								final InputStream inputStream = resourceURL.openConnection().getInputStream(); //open an input stream to the resource URL
								try {
									switch(resourceBundleFormat) { //see which type of resource bundle we're loading
										case TURF: {
											final Map<Object, Object> resourceMap = turfResourceIO.read(inputStream, resourceURL.toURI()); //try to read the resource
											return new HashMapResourceBundle(resourceMap, parent); //create a new hash map resource bundle with resources and the given parent and return it
										}
										case RDFXML: {
											final RDFResource rdfResource = rdfResourceIO.read(inputStream, resourceURL.toURI()); //try to read the resource
											return toResourceBundle(rdfResource, rdfPropertyNamespaceURI, parent); //create and return a resource bundle from the RDF resource
										}
										case XML: {
											final Properties properties = new Properties(); //we'll load a properties file
											properties.loadFromXML(inputStream); //load the properties file from the XML
											return new HashMapResourceBundle(properties, parent); //create and return a resource bundle with the given parent
										}
										case PROPERTIES: {
											final Properties properties = new Properties(); //we'll load a properties file
											properties.load(inputStream); //load the traditional properties file
											return new HashMapResourceBundle(properties, parent); //create and return a resource bundle with the given parent
										}
										default:
											throw new AssertionError("Unrecognized resource bundle format: " + resourceBundleFormat);
									}
								} finally {
									inputStream.close(); //always close the input stream
								}
							} catch(final URISyntaxException uriSyntaxException) { //if the resource URL wasn't strictly in compliance with URI syntax								
								throw (MissingResourceException)new MissingResourceException(uriSyntaxException.getMessage(), baseName + Locales.LOCALE_SEPARATOR + locale, "")
										.initCause(uriSyntaxException);
							} catch(final IOException ioException) { //if there is an error loading the resource
								throw (MissingResourceException)new MissingResourceException("I/O error in " + resourceURL + ": " + ioException.getMessage(),
										baseName + Locales.LOCALE_SEPARATOR + locale, "").initCause(ioException);
							}
						}
					}
				}
			}
		}
		throw new MissingResourceException("Can't find resource bundle for base name " + baseName + ", locale " + locale,
				baseName + Locales.LOCALE_SEPARATOR + locale, "");
	}

	/**
	 * Creates a resource bundle with contents reflecting the properties of a given RDF resource.
	 * @param rdfResource The RDF resource the properties of which should be turned into a map.
	 * @param rdfPropertyNamespaceURI The namespace of the properties to gather, using the property local name as the map entry key.
	 * @param parent The parent resource bundle, or <code>null</code> if there should be no parent for resolving resources.
	 * @return A resource bundle with contents reflecting the property/value pairs, resolving to the given parent.
	 */
	public static ResourceBundle toResourceBundle(final RDFResource rdfResource, final URI rdfPropertyNamespaceURI, final ResourceBundle parent) {
		return new HashMapResourceBundle(toMap(rdfResource, rdfPropertyNamespaceURI), parent); //create a new hash map resource bundle with the given parent and return it		
	}

	/**
	 * Creates a map with contents reflecting the properties of a given RDF resource. Resource values are determined according to the algorithm used by
	 * {@link #getResourceValue(RDFObject)}.
	 * @param rdfResource The RDF resource the properties of which should be turned into a map.
	 * @param rdfPropertyNamespaceURI The namespace of the properties to gather, using the property local name as the map entry key.
	 * @return A map with contents reflecting the property/value pairs of the given resource.
	 */
	public static Map<String, Object> toMap(final RDFResource rdfResource, final URI rdfPropertyNamespaceURI) {
		final HashMap<String, Object> resourceHashMap = new HashMap<String, Object>(rdfResource.getPropertyCount()); //create a new hash map with enough initial room for all properties
		for(final RDFPropertyValuePair propertyValuePair : rdfResource.getProperties()) { //for each resource property/value pair
			final RDFResource property = propertyValuePair.getProperty(); //get the property
			final URI propertyURI = property.getURI(); //get the property URI
			if(propertyURI != null && rdfPropertyNamespaceURI.equals(getNamespaceURI(propertyURI))) { //if this property is in the resourceKey namespace
				final String resourceKey = getLocalName(propertyURI); //use the local name as the resource key
				final Object resourceValue = getResourceValue(propertyValuePair.getValue()); //determine the resource value from the RDF property value
				if(resourceValue != null) { //if we found a resource value
					resourceHashMap.put(resourceKey, resourceValue); //store the resource key/value pair in the map
				}
			}
		}
		return resourceHashMap; //return the map		
	}

	/**
	 * Determines the resource value from an RDF property value. Resource values are determined as follows:
	 * <ol>
	 * <li>If the property value is a typed literal, the typed literal {@link Object} data is used.</li>
	 * <li>If the property value is any other literal, the lexical form {@link String} value is used.</li>
	 * <li>If the property value is a list, a {@link List} is returned filled with values obtained by using this same algorithm; <code>null</code> values are
	 * accepted.</li>
	 * <li>If the value is any other resource with a reference URI, that {@link URI} is used.</li>
	 * <li>If the value is any other resource with no reference URI but that has a single <code>rdf:value</code>, the value of that value resource is obtained by
	 * using this same algorithm.</li>
	 * </ol>
	 * @param rdfPropertyValue The RDF property value object to be converted to a resource value.
	 * @return A {@link String}, {@link URI}, or a {@link List} representing the resource value for the RDF property value, or <code>null</code> if no value could
	 *         be determined.
	 */
	public static Object getResourceValue(final RDFObject rdfPropertyValue) {
		if(rdfPropertyValue instanceof RDFLiteral) { //if the property value is a literal
			return rdfPropertyValue instanceof RDFTypedLiteral ? ((RDFTypedLiteral<?>)rdfPropertyValue).getValue() : ((RDFLiteral)rdfPropertyValue).getLexicalForm(); //get the typed literal object if this is a typed literal
		} else if(rdfPropertyValue instanceof RDFListResource) { //if the property value is a list
			final RDFListResource<?> listResource = (RDFListResource<?>)rdfPropertyValue; //get the property value as a list
			final List<Object> list = new ArrayList<Object>(listResource.size()); //create a new list
			for(final RDFObject elementResource : listResource) { //for each element in the list resource
				list.add(getResourceValue(elementResource)); //convert the element resource to a resource value and add it to the list
			}
			return list; //return the list
		} else if(rdfPropertyValue instanceof RDFResource) { //if the property value is any other resource
			final RDFResource resource = (RDFResource)rdfPropertyValue; //get the property value as a resource
			final URI referenceURI = resource.getURI(); //get the reference URI
			if(referenceURI != null) { //if there is a reference URI
				return referenceURI; //return the reference URI
			} else { //if there is no reference URI, see if there is an rdf:value specified
				final RDFObject rdfValue = getValue(resource); //get the rdf:value specified, if any
				if(rdfValue != null) { //if there is an rdf:value specified
					return getResourceValue(rdfValue); //convert the rdf:value to a resource value and return it
				}
			}
		}
		return null; //indicate that no value could be determined
	}

}
