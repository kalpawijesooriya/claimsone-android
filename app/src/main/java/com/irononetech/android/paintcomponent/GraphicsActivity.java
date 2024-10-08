/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.irononetech.android.paintcomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GraphicsActivity extends Activity {
	Logger LOG = LoggerFactory.getLogger(GraphicsActivity.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LOG.debug("ENTRY onCreate");
		super.onCreate(savedInstanceState);
		
		LOG.debug("SUCCESS onCreate");
	}

	@Override
	public void setContentView(View view) {
		/*if (false) { // set to true to test Picture
			ViewGroup vg = new PictureLayout(this);
			vg.addView(view);
			view = vg;
		}*/

		super.setContentView(view);
	}
}
