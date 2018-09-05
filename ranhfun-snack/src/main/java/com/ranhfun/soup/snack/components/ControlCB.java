// Copyright 2008 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.



package com.ranhfun.soup.snack.components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Heartbeat;

/**
 * A control check box to control the standard Checkbox with mixin 
 * ControlledCheckbox.
 * 
 */
@Import(library="ControlCB.js")
public class ControlCB extends Checkbox{
    
	/**
     * If true (the default), then any notification sent by the component will be deferred until the end of the form
     * submission (this is usually desirable).
     */
    @Parameter
	private boolean defer = true;
	
	@Environmental
	private Heartbeat heartbeat;
	
	@Environmental
	private FormSupport formSupport;


	@Inject
	private RenderSupport renderSupport;
     	
	/**
	 * A collection of the controlled check box css ids.
	 */
	private  Collection<String> checkboxIds;

	public void afterRender(MarkupWriter writer) {


		Runnable command = new Runnable() {
			public void run() {
				StringBuffer controlledCheckboxesSB = new StringBuffer("[");
				Iterator<String> it = getCheckboxIds().iterator();
				while (it.hasNext()) {
					if (!controlledCheckboxesSB.toString().equals("[")) {
						controlledCheckboxesSB.append(",");
					}
					String checkboxId = it.next();
					controlledCheckboxesSB.append("'" + checkboxId + "'");

				}
				controlledCheckboxesSB.append("]");
				
				// add line to instance a CheckboxGroup 
				renderSupport.addScript(String.format(
						"new CheckboxGroup('%s', %s);", 
								getClientId(), controlledCheckboxesSB
								.toString()));
			}
		};

		// The control check box might be places after the controlled checked box.
		// So need to wait until all controlled checkbox are registered before
		// rendering the javscript.
        if (defer) formSupport.defer(command);
        else heartbeat.defer(command);

	}

    public Collection<String> getCheckboxIds()
    {
    	  if (checkboxIds ==null)
          {
          	checkboxIds = new HashSet<String>();
          }
  	      return checkboxIds;
    }
	
	public void registerControlledCheckbox(String checkboxId) {
		getCheckboxIds().add(checkboxId);
	}

}
