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
package org.force66.cxfutils.reflect;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * Reflection Utilities that don't throw checked exceptions
 *
 * @author D. Ashmore
 *
 */
public class ReflectUtils {

	protected static Object readField(final Field field, final Object target) {
		try {
			return FieldUtils.readField(field, target, true);
		} catch (Exception e) {
			throw new ContextedRuntimeException("Error reading field", e)
					.addContextValue("field", field).addContextValue("object",
							target);
		}
	}

	protected static void reflectionAppendContextValues(
			ExceptionContext context, Collection embeddedCollection,
			String label) {
		Validate.notNull(context, "Null context not allowed.");
		Validate.notNull(embeddedCollection,
				"Null embeddedCollection not allowed.");

		int offset = 1;
		for (Object collectionItem : embeddedCollection) {
			reflectionAppendContextValues(context, collectionItem, label + "."
					+ offset);
			offset++;
		}
	}

	public static void reflectionAppendContextValues(ExceptionContext context,
			Object embeddedInfo, String label) {
		Validate.notNull(context, "Null context not allowed.");
		Validate.notEmpty(label, "Null or blank label not allowed.");
		if (embeddedInfo == null) {
			context.addContextValue(label, embeddedInfo);
			return;
		}

		List<Field> fieldList = FieldUtils.getAllFieldsList(embeddedInfo
				.getClass());
		Object fieldValue;
		String fieldLabel;

		for (Field field : fieldList) {
			fieldLabel = label + "." + field.getName();
			fieldValue = readField(field, embeddedInfo);
			if (fieldValue instanceof Collection) {
				reflectionAppendContextValues(context, (Collection) fieldValue,
						fieldLabel);

			} else if (field.getType().isPrimitive()
					|| field.getType().getPackage().getName()
							.startsWith("java")) {
				context.addContextValue(fieldLabel, fieldValue);
			} else {
				reflectionAppendContextValues(context, fieldValue, fieldLabel);
			}
		}
	}

	public static Object safeInvokeExactMethod(final Object object,
			final String methodName, Object... args) {
		try {
			return MethodUtils.invokeExactMethod(object, methodName, args);
		} catch (Exception e) {
			ContextedRuntimeException ce = new ContextedRuntimeException(
					"Error invoking method", e).addContextValue("methodName",
							methodName).addContextValue("object", object);
			if (args != null) {
				for (int offset = 0; offset < args.length; offset++) {
					ce.addContextValue("arg " + offset, args[offset]);
				}
			}
			throw ce;
		}
	}
}
