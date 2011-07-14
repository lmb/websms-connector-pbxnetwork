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

import java.io.IOException;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

/**
 * Implements timeout support for kSOAP2 calls.
 */
public class AdvancedHttpTransport extends HttpTransportSE {

	/**
	 * Default timeout (30s).
	 */
	public final static int DEFAULT_TIMEOUT = 30 * 1000;

	/**
	 * Current timeout (in miliseconds).
	 */
	protected int mTimeout;

	public AdvancedHttpTransport(final String url) {
		super(url);
		this.setTimeout(DEFAULT_TIMEOUT);
	}

	/**
	 * Set timeout for future connections.
	 * 
	 * @param timeout
	 *            Timeout in miliseconds
	 * @return Old timeout value
	 */
	public int setTimeout(final int timeout) {
		if (timeout < 1000) {
			// We probably were passed seconds instead of ms
			throw new RuntimeException(
					"Timeout value is too low (<1000). Please specify in miliseconds.");
		}

		int old_timeout = this.mTimeout;
		this.mTimeout = timeout;

		return old_timeout;
	}

	@Override
	protected ServiceConnection getServiceConnection() throws IOException {
		return new AdvancedServiceConnection(this.url, this.mTimeout);
	}
}
