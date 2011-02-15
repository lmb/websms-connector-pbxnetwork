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
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.Toast;
import de.ub0r.android.websms.connector.common.WebSMSException;

/**
 * Custom preference for managing data retrieval from {@see NumberService}.
 * 
 * @author lmb
 */
public class ExtensionsPreference extends ListPreference {

	private final static String TAG = "ExtensionsPreference";

	public ExtensionsPreference(final Context context) {
		super(context);
	}

	public ExtensionsPreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	private class FetchExtensions extends AsyncTask<Void, Void, String[]> {
		protected Context mContext;
		protected SoapTypeTicket mTicket;
		protected ProgressDialog mDialog;

		public FetchExtensions(final Context context,
				final SoapTypeTicket ticket) {
			this.mContext = context;
			this.mTicket = ticket;
		}

		@Override
		protected void onPreExecute() {
			this.mDialog = ProgressDialog.show(this.mContext, "",
					this.mContext.getString(R.string.loading_extensions), true,
					false);
		}

		@Override
		protected String[] doInBackground(final Void... args) {
			final NumberService ns = ConnectorPbxnetwork.NUMBER_SERVICE;
			int old_timeout = ns.setTimeout(10 * 1000); // Short 10s timeout

			Vector<String> extensions;
			try {
				extensions = ns.getUserExtensions(this.mTicket);
			} catch (SocketTimeoutException e) {
				return null;
			} finally {
				ns.setTimeout(old_timeout);
			}

			String[] entries = new String[extensions.size()];
			extensions.copyInto(entries);

			return entries;
		}

		@Override
		protected void onPostExecute(final String[] extensions) {
			this.mDialog.dismiss();

			if (extensions == null) {
				String msg = this.mContext.getString(R.string.err_extensions)
						+ this.mContext.getString(R.string.err_timeout);
				Toast error = Toast.makeText(this.mContext, msg,
						Toast.LENGTH_LONG);
				error.show();
			} else {
				ExtensionsPreference.this.setEntries(extensions);
				ExtensionsPreference.this.setEntryValues(extensions);
				ExtensionsPreference.this.showDialog(null);
			}
		}
	}

	@Override
	protected void onClick() {
		SoapTypeTicket ticket = null;
		try {
			ticket = ConnectorPbxnetwork.TICKET.get(this.getContext());
		} catch (WebSMSException e) {
			// Password / username not set
			String msg = this.getContext().getString(
					R.string.err_enter_user_and_pass);
			Toast error = Toast.makeText(this.getContext(), msg,
					Toast.LENGTH_LONG);
			error.show();

			return;
		}

		new FetchExtensions(this.getContext(), ticket).execute();
	}
}
