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

import java.net.SocketTimeoutException;

import org.ksoap2.serialization.SoapObject;

public class LoginService extends SoapService {
	private static final String URL = "https://secure.pbx-network.de/pbx-server/public/services/LoginService";
	private static final String NAMESPACE = "https://secure.pbx-network.de/pbx-server/public/services/LoginService";
	private static final String METHOD_LOGIN = "login";
	private static final String METHOD_IS_VALID = "isValid";
	private static final String TAG = "LoginService";

	public LoginService() throws RuntimeException {
		super(URL, NAMESPACE);
	}

	/**
	 * Retrieve a session ID from PBX-Network using username and password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SocketTimeoutException
	 */
	public SoapTypeTicket login(final String username, final String password)
			throws SocketTimeoutException {
		SoapObject req = this.newRequest(METHOD_LOGIN);
		req.addProperty("userId", username);
		req.addProperty("password", password);

		return (SoapTypeTicket) this.callMethod(req);
	}

	/**
	 * Check session ID for validity.
	 * 
	 * @param ticket
	 * @return
	 * @throws SocketTimeoutException
	 */
	public Boolean isValid(final SoapTypeTicket ticket)
			throws SocketTimeoutException {
		SoapObject req = this.newRequest(METHOD_IS_VALID);

		req.addProperty("t", ticket);

		return (Boolean) this.callMethod(req);
	}
}
