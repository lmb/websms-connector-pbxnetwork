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

public class SMSService extends SoapService {
	private final static String URL = "https://secure.pbx-network.de/pbx-server/customer/services/SMSService";
	private final static String NAMESPACE = "https://secure.pbx-network.de/pbx-server/customer/services/SMSService";

	private final static String METHOD_SEND_SMS = "sendSMS";
	private final static String METHOD_SEND_LOW_COST_SMS = "sendLowCostSMS";

	public SMSService() {
		super(URL, NAMESPACE);
	}

	/**
	 * Send a regular SMS, which supports setting sender information.
	 * 
	 * @param from
	 * @param recipient
	 * @param message
	 * @param ticket
	 * @return True on success.
	 * @throws SocketTimeoutException
	 */
	public Boolean sendSMS(final String from, final String recipient,
			final String message, final SoapTypeTicket ticket)
			throws SocketTimeoutException {
		SoapObject req = this.newRequest(METHOD_SEND_SMS);

		req.addProperty("t", ticket);
		req.addProperty("sender", from);
		req.addProperty("reciever", recipient);
		req.addProperty("msg", message);

		return (Boolean) this.callMethod(req);
	}

	/**
	 * Send a low-cost SMS, which does not allow setting sender information.
	 * 
	 * @param recipient
	 * @param message
	 * @param ticket
	 * @return True on success.
	 * @throws SocketTimeoutException
	 */
	public Boolean sendLowCostSMS(final String recipient, final String message,
			final SoapTypeTicket ticket) throws SocketTimeoutException {
		SoapObject req = this.newRequest(METHOD_SEND_LOW_COST_SMS);

		req.addProperty("t", ticket);
		req.addProperty("reciever", recipient);
		req.addProperty("msg", message);

		return (Boolean) this.callMethod(req);
	}
}
