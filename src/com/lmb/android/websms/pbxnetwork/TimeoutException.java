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

import android.content.Context;
import de.ub0r.android.websms.connector.common.WebSMSException;

/**
 * Displays a "%s: connection timed out." message to the user.
 * 
 * @author lmb
 */
public class TimeoutException extends WebSMSException {

	private static final long serialVersionUID = -4315729019236883487L;

	public TimeoutException(final Context c, final int rid) {
		super(c.getString(rid) + c.getString(R.string.err_timeout));
	}

}
