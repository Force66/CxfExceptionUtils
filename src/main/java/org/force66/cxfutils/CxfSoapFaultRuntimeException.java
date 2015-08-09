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

import org.apache.commons.lang3.exception.ContextedRuntimeException;

/**
 * Exception meant to be extended by Apache CXF when generating exceptions for
 * SOAP Faults. This exception will insure that embedded information CSF places
 * for exceptions will be logged.
 *
 * @author D. Ashmore
 *
 */
abstract public class CxfSoapFaultRuntimeException extends
		ContextedRuntimeException {

	private static final long serialVersionUID = -3907214365624308261L;

	private boolean contextAdded = false;

	public CxfSoapFaultRuntimeException() {
		super();
	}

	public CxfSoapFaultRuntimeException(String message) {
		super(message);
	}

	public CxfSoapFaultRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CxfSoapFaultRuntimeException(Throwable cause) {
		super(cause);
	}

	protected void checkExceptionContextInfo() {
		if (!contextAdded) {

			ExceptionContextUtils.fillInContextInfo(this);
			contextAdded = true;
		}

	}

	@Override
	public String getMessage() {
		checkExceptionContextInfo();
		return super.getMessage();
	}

	@Override
	public String getRawMessage() {
		checkExceptionContextInfo();
		return super.getRawMessage();
	}

}
