// Copyright 2009 Shing Hing Man
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

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * This component is a wrapper around javascript tabricator.js written by Bill
 * Brown. The javascript could be found at http://scripteka.com/
 * 
 * @author Shing Hing Man
 * 
 */
@SupportsInformalParameters
@Import(stylesheet="tabricator.css",library="tabricator.js")
public class Tab implements ClientElement {

	@Environmental
	private RenderSupport support;

	@Inject
	private ComponentResources resources;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	protected void beginRender(MarkupWriter writer) {

		clientId = support.allocateClientId(resources.getId());
	
		writer.element("dl",  "id", clientId);

		resources.renderInformalParameters(writer);
		
		 
		String var = String.format("new Tabricator('%s', 'DT');", clientId);
		support.addScript(var);
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end(); // <tag>


	}

}