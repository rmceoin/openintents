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

package org.openintents.historify.data.model.source;

import org.openintents.historify.data.loaders.SourceIconHelper.IconLoadingStrategy;

import android.net.Uri;

/**
 * 
 * Model class representing a Source of events.
 * 
 * @author berke.andras
 */
public abstract class AbstractSource {

	public enum SourceState {
		ENABLED, DISABLED, ERROR;

		public static SourceState parseString(String stateString) {
			for (SourceState s : values()) {
				if (s.toString().equals(stateString))
					return s;
			}
			return ERROR;
		}
	}

	//id
	private long mId;
	
	//displayed source name
	private String mName;

	//short description of the source
	private String mDescription;
	
	//icon displayed on the timeline
	private Uri mIconUri;
	
	//authority of the source content provider
	private String mAuthority;

	//Intent to be fired if user selects an event
	private String mEventIntent;
	
	//current state
	private SourceState mState;
	
	//source filter - if loaded
	private SourceFilter mSourceFilter;

	//flag for internal sources
	protected boolean mIsInternal = false;

	//Action of the intent to be fired for launching source configuration activity
	private String mConfigIntent;
	
	private IconLoadingStrategy mIconLoadingStrategy;
	
	
	protected AbstractSource(long id, String name, String description,
			String iconUri, IconLoadingStrategy iconLoadingStrategy, String authority, String eventIntent, String configIntent) {
		
		mId = id;
		mName = name;
		mDescription = description;
		mIconUri = iconUri==null ? null : Uri.parse(iconUri);
		mIconLoadingStrategy = iconLoadingStrategy==null ? IconLoadingStrategy.useSourceIcon : iconLoadingStrategy;
		mAuthority = authority;
		mEventIntent = eventIntent;
		mConfigIntent = configIntent;
		
		mState = SourceState.ENABLED;
	}

	public boolean isInternal() {
		return mIsInternal;
	}

	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getDescription() {
		return mDescription;
	}
	
	public String getAuthority() {
		return mAuthority;
	}
	
	public String getEventIntent() {
		return mEventIntent;
	}
	
	public String getConfigIntent() {
		return mConfigIntent;
	}
	
	public SourceState getState() {
		return mState;
	}

	public void setState(SourceState mState) {
		this.mState = mState;
	}

	public Uri getIcon() {
		return mIconUri;
	}
	
	public boolean isEnabled() {
		
		return (mSourceFilter==null && mState==SourceState.ENABLED) ||
			(mSourceFilter!=null && mSourceFilter.getFilteredState()==SourceState.ENABLED);
	}

	public void setEnabled(boolean checked) {
		SourceState newState = checked ? SourceState.ENABLED : SourceState.DISABLED;
		if(mSourceFilter==null) setState(newState);
		else mSourceFilter.setFilteredState(newState);
	}
	
	public void setSourceFilter(SourceFilter mSourceFilter) {
		this.mSourceFilter = mSourceFilter;
	}
	
	public SourceFilter getSourceFilter() {
		return mSourceFilter;
	}

	public IconLoadingStrategy getIconLoadingStrategy() {
		return mIconLoadingStrategy;
	}

	public static AbstractSource factoryMethod(
			boolean isInternal, 
			long id,
			String name, 
			String description,
			String iconUri,
			IconLoadingStrategy iconLoadingStrategy, 
			String authority,
			String eventIntent,
			String configIntent,
			String state) {
		
		AbstractSource retval = isInternal ? 
				new InternalSource(id, name, description, iconUri, iconLoadingStrategy, authority, eventIntent,configIntent):
				new ExternalSource(id, name, description, iconUri, iconLoadingStrategy, authority, eventIntent,configIntent);
		retval.setState(SourceState.parseString(state));

		return retval;
	}




}
