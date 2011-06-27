/* 
 * Copyright (C) 2011 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openintents.samples.historify.hellosharedsource;

import org.openintents.historify.services.bridge.HistorifyBridge;

import android.content.Context;

public class HelloReceiver extends HistorifyBridge.RequestReceiver {

	@Override
	protected void onRequestRegister(Context context) {

		new HistorifyBridge(R.drawable.icon).registerSource(context, "HelloSharedSource",
				HelloProvider.AUTHORITY,
				"Example app for registering a SharedSource", null);

	}

}