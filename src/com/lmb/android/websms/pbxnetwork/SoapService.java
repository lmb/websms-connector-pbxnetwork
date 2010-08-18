/*
 * Copyright (C) 2010 Lorenz Bauer
 * 
 * This file is part of WebSMS.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */
package com.lmb.android.websms.pbxnetwork;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import de.ub0r.android.websms.connector.common.WebSMSException;

/**
 * A Class used to interact with the PBX-Network APIs.
 * 
 * @author lmb
 */
public abstract class SoapService {
	private static final String TAG = "SoapService";

	private String mURL;
	private String mNamespace;

	private AdvancedHttpTransport mTransport;

	/**
	 * Serialization envelope, can be used to register new type mappings.
	 */
	protected SoapSerializationEnvelope mEnvelope;

	/**
	 * Creates a new SoapService.
	 * 
	 * @param URL
	 *            URL of the SOAP service.
	 * @param namespace
	 *            Namespace used by the SOAP service.
	 * @throws RuntimeException
	 */
	protected SoapService(final String URL, final String namespace)
			throws RuntimeException {
		if (URL.length() == 0 || namespace.length() == 0) {
			throw new RuntimeException("Invalid URL or Namespace");
		}

		// FIXME: Workaround for
		// http://code.google.com/p/android/issues/detail?id=7786
		System.setProperty("http.keepAlive", "false");

		this.mURL = URL;
		this.mNamespace = namespace;

		this.mTransport = new AdvancedHttpTransport(this.mURL);
		this.mTransport.debug = true;

		this.mEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		this.mEnvelope.addMapping("pbx", "Ticket", SoapTypeTicket.class);
	}

	/**
	 * Creates a request object with the default namespace.
	 * 
	 * @param method
	 *            Remote procedure you would like to call.
	 * @return
	 */
	protected SoapObject newRequest(final String method) {
		return this.newRequest(this.mNamespace, method);
	}

	/**
	 * Creates a request from namespace and method.
	 * 
	 * @param namespace
	 *            Target namespace.
	 * @param method
	 *            Remote procedure you would like to call.
	 * @return
	 */
	protected SoapObject newRequest(final String namespace, final String method) {
		return new SoapObject(namespace, method);
	}

	/**
	 * @param request
	 *            Request to perfom.
	 * @return Return value of the RPC.
	 * @throws SocketTimeoutException
	 *             Remote call timed out.
	 */
	protected Object callMethod(final SoapObject request)
			throws SocketTimeoutException {
		this.mEnvelope.setOutputSoapObject(request);

		Object res = null;
		try {
			this.mTransport.call(null, this.mEnvelope);

			res = this.mEnvelope.getResponse();
		} catch (SoapFault e) {
			throw new WebSMSException(e.faultstring);
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (IOException e) {
			throw new WebSMSException(e);
		} catch (XmlPullParserException e) {
			throw new WebSMSException(e);
		} /*- finally {
			String req_dump = (this.mTransport.requestDump != null) ? this.mTransport.requestDump
					: "N/A";
			String res_dump = (this.mTransport.responseDump != null) ? this.mTransport.responseDump
					: "N/A";
			Log.d(TAG, req_dump);
			Log.d(TAG, res_dump);
			}*/

		return res;
	}

	/**
	 * Proxy for {@see AdvancedHttpTransport.setTimeout}
	 */
	public final int setTimeout(final int timeout) {
		return this.mTransport.setTimeout(timeout);
	}
}
