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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.ksoap2.transport.ServiceConnection;

/**
 * Adds access to set(*)Timeout for HttpURLConnection.
 * 
 * This is a more-or-less verbatim copy of ServiceConnectionSE, which
 * unfortunately defines this.connection as private.
 * 
 */
public class AdvancedServiceConnection implements ServiceConnection {

	/**
	 * Set timeouts for this connection.
	 * 
	 * @param timeout
	 *            Connect and read timeout in miliseconds
	 */
	public void setTimeout(final int timeout) {
		this.connection.setConnectTimeout(timeout);
		this.connection.setReadTimeout(timeout);
	}

	protected HttpURLConnection connection;

	/**
	 * Constructor taking the url to the endpoint for this soap communication
	 * 
	 * @param url
	 *            the url to open the connection to.
	 */
	public AdvancedServiceConnection(final String url) throws IOException {
		this.connection = (HttpURLConnection) new URL(url).openConnection();
		this.connection.setUseCaches(false);
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
	}

	/**
	 * Constructor which sets timeouts.
	 * 
	 * @param url
	 *            Url to open the connection to
	 * @param timeout
	 *            Timeout in miliseconds
	 * @throws IOException
	 */
	public AdvancedServiceConnection(final String url, final int timeout)
			throws IOException {
		this(url);
		this.setTimeout(timeout);
	}

	public void connect() throws IOException {
		this.connection.connect();
	}

	public void disconnect() {
		this.connection.disconnect();
	}

	public void setRequestProperty(final String string, final String soapAction) {
		this.connection.setRequestProperty(string, soapAction);
	}

	public void setRequestMethod(final String requestMethod) throws IOException {
		this.connection.setRequestMethod(requestMethod);
	}

	public OutputStream openOutputStream() throws IOException {
		return this.connection.getOutputStream();
	}

	public InputStream openInputStream() throws IOException {
		return this.connection.getInputStream();
	}

	public InputStream getErrorStream() {
		return this.connection.getErrorStream();
	}

}
