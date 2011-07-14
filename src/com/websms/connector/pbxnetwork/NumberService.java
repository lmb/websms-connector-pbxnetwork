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
package com.websms.connector.pbxnetwork;

import java.net.SocketTimeoutException;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class NumberService extends SoapService {
	private final static String URL = "https://secure.pbx-network.de/pbx-server/customer/services/NumberService";
	private final static String NAMESPACE = "https://secure.pbx-network.de/pbx-server/customer/services/NumberService";

	private final static String METHOD_GET_USER_EXTENSIONS = "getUserExtensions";

	public NumberService() {
		super(URL, NAMESPACE);
	}

	public final Vector<String> getUserExtensions(final SoapTypeTicket ticket)
			throws SocketTimeoutException {
		SoapObject req = this.newRequest(METHOD_GET_USER_EXTENSIONS);
		req.addProperty("t", ticket);

		Vector<SoapPrimitive> res = (Vector<SoapPrimitive>) this
				.callMethod(req);
		Vector<String> extensions = new Vector<String>(res.size());

		for (SoapPrimitive extension : res) {
			extensions.add("+" + extension.toString());
		}

		return extensions;
	}
}
