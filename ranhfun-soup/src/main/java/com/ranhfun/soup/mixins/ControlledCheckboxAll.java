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


package com.ranhfun.soup.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;

import com.ranhfun.soup.components.ControlCheckAllLink;

/**
 * 
 * A mixin to turn a standard checkbox into a controlled checkbox.
 * Clicking the control checkbox will set the state 
 * of the controlled checkbox to that of the control checkbox.
 * The Control checkbox is ControlCB.
 *
 */
public class ControlledCheckboxAll {
	
	@Parameter(name = "controllerall", required = true, defaultPrefix = BindingConstants.COMPONENT)
	private ControlCheckAllLink controller;

	
	@InjectContainer
	private Field checkbox;
	
	
	void afterRender(MarkupWriter write) {
           
		controller.registerControlledCheckbox(checkbox.getClientId());
		
	}

}
