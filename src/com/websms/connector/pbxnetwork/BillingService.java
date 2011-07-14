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

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import de.ub0r.android.websms.connector.common.WebSMSException;

public class BillingService extends SoapService {
	private static final String URL = "https://secure.pbx-network.de/pbx-server/customer/services/BillingService";
	private static final String NAMESPACE = "https://secure.pbx-network.de/pbx-server/customer/services/BillingService";

	private static final String METHOD_GET_CREDIT = "getCredit";

	public BillingService() {
		super(URL, NAMESPACE);
	}

	/**
	 * Returns current account balance.
	 * 
	 * @param ticket
	 * @return
	 * @throws SocketTimeoutException
	 */
	public final Double getCredit(final SoapTypeTicket ticket)
			throws SocketTimeoutException {
		SoapObject req = this.newRequest(METHOD_GET_CREDIT);
		req.addProperty("t", ticket);

		Double credit;
		SoapPrimitive res;
		try {
			res = (SoapPrimitive) this.callMethod(req);
			credit = Double.valueOf(res.toString());
		} catch (NumberFormatException e) {
			throw new WebSMSException(
					"Could not retrieve account balance: malformed data");
		}

		return credit;
	}
}
