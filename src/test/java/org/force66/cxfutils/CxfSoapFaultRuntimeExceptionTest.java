/*
 * This software is licensed under the Apache License, Version 2.0
 * (the "License") agreement; you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.force66.cxfutils;

import org.junit.Assert;
import org.junit.Test;

public class CxfSoapFaultRuntimeExceptionTest {

	@Test
	public void testCollection() throws Exception {
		TestInvalidInput input = new TestInvalidInput();
		input.setEmbeddedStuff("embedded cxf info");
		input.getInvalidInputList()
		.add(new KeyValue("field1", "can't be null"));
		input.getInvalidInputList().add(
				new KeyValue("field2", "must be larger than 0"));

		TestFaultInvalidInputsException exception = new TestFaultInvalidInputsException(
				"exception message", input);
		exception.printStackTrace();
		Assert.assertTrue(exception.getMessage().contains("field1"));
		Assert.assertTrue(exception.getMessage().contains("field2"));
		Assert.assertTrue(exception.getMessage().contains("can't be null"));
		Assert.assertTrue(exception.getMessage().contains(
				"must be larger than 0"));
	}

}
