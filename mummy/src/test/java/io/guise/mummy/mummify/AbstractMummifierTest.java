/*
 * Copyright © 2020 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

package io.guise.mummy.mummify;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.net.URI;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

import io.urf.URF;

/**
 * Tests of {@link AbstractMummifier}.
 * @author Garret Wilson
 */
public class AbstractMummifierTest {

	/**
	 * @implNote Because the matched name is assumed to be a valid URF name, no checks are performed for strings such as <code>published-On</code>, which would
	 *           match the pattern but would never appear as an URF name.
	 * @see AbstractMummifier#AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN
	 */
	@Test
	public void testAdHocPropertyLocalDateNamePattern() {
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("x").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("n").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("fooBar").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("on").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("On").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("xon").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("xOn").matches(), is(true));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("publishedon").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("publishedOn").matches(), is(true));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("publishedOnx").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("publishedOnFooBar").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("fromoAtoZon").matches(), is(false));
		assertThat(AbstractMummifier.AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN.matcher("fromoAtoZOn").matches(), is(true));
	}

	/** @see AbstractMummifier#parseMetadataPropertyValue(URI, CharSequence) */
	@Test
	public void testParseMetadataPropertyValueReturnsStringByDefault() {
		assertThat(AbstractMummifier.parseMetadataPropertyValue(URF.Handle.toTag("foo"), "bar"), is("bar"));
		assertThat(AbstractMummifier.parseMetadataPropertyValue(URF.Handle.toTag("foo"), new StringBuilder("bar")), is("bar"));
	}

	/**
	 * @see AbstractMummifier#parseMetadataPropertyValue(URI, CharSequence)
	 * @see AbstractMummifier#AD_HOC_PROPERTY_LOCAL_DATE_NAME_PATTERN
	 */
	@Test
	public void testParseMetadataPropertyValueAdHocLocalDate() {
		assertThat(AbstractMummifier.parseMetadataPropertyValue(URF.Handle.toTag("publishedOn"), "2001-02-03"), is(LocalDate.of(2001, 2, 3)));
	}

}
