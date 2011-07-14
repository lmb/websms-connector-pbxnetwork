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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import de.ub0r.android.websms.connector.common.WebSMSException;

public class Ticket {
	private final static String TAG = "Ticket";

	protected SoapTypeTicket mTicket;
	protected static LoginService LOGIN_SERVICE = new LoginService();

	protected String mUsername;
	protected String mPassword;

	public final SoapTypeTicket get(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final String p_username = p.getString(Preferences.PREFS_USER, "");
		final String p_password = p.getString(Preferences.PREFS_PASSWORD, "");

		if (!p_username.equals(this.mUsername)
				|| !p_password.equals(this.mPassword)) {
			// Login credentials were updated
			this.mTicket = null;
			this.mUsername = p_username;
			this.mPassword = p_password;
		}

		if (this.mUsername.length() == 0 || this.mPassword.length() == 0) {
			throw new WebSMSException("No username or password specified.");
		}

		try {
			if (this.mTicket == null) {
				// Perform login
				this.mTicket = LOGIN_SERVICE.login(this.mUsername,
						this.mPassword);

				return this.mTicket;
			}

			if (!LOGIN_SERVICE.isValid(this.mTicket)) {
				Log.d(TAG, "Token is invalid, logging in");
				this.mTicket = LOGIN_SERVICE.login(this.mUsername,
						this.mPassword);
			}
		} catch (SocketTimeoutException e) {
			throw new TimeoutException(context, R.string.err_login);
		}

		return this.mTicket;
	}
}
