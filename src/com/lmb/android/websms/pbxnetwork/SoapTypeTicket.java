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

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class SoapTypeTicket implements KvmSerializable {
	public String Id;

	public SoapTypeTicket() {
		this("");
	}

	public SoapTypeTicket(final String Id) {
		this.Id = Id;
	}

	@Override
	public Object getProperty(final int idx) {
		if (idx == 0) {
			return this.Id;
		}

		return null;
	}

	@Override
	public int getPropertyCount() {
		return 1;
	}

	@Override
	public void getPropertyInfo(final int idx, final Hashtable properties,
			final PropertyInfo info) {
		if (idx == 0) {
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "ticketId";
		}
	}

	@Override
	public void setProperty(final int idx, final Object value) {
		if (idx == 0) {
			this.Id = value.toString();
		}
	}
}
