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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.force66.cxfutils.reflect.ReflectUtils;

public class ExceptionContextUtils {

	public static void fillInContextInfo(ExceptionContext context) {
		Validate.notNull(context, "Null ExceptionContext not allowed.");
		Object embeddedInfo = ReflectUtils.safeInvokeExactMethod(context,
				"getFaultInfo");
		if (embeddedInfo == null) {
			return;
		}

		ReflectUtils.reflectionAppendContextValues(context, embeddedInfo,
				"cxfclient");
	}

}
