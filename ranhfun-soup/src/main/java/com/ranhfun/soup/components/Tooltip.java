// Copyright 2007 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.

package com.ranhfun.soup.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

@SupportsInformalParameters
@Import(library="tooltip.js")
public class Tooltip implements ClientElement {

	/**
	 * tooltipContent is expected to be a piece of static html code.
	 * Consequently, the tooltip could be formatted using CSS given by an id or
	 * class selector. Also, the tooltip could be a list or image.
	 */
	@Parameter(required = true)
	private String tooltipContent;
	
	@Parameter(value="tooltip", defaultPrefix = BindingConstants.LITERAL)
	private String wrapperClass;

	/**
	 * If the mouse is at (x,y), then the top left hand corner of the tooltip
	 * will be at (x+xoffset, y+yoffset).
	 */
	@Parameter(required = true)
	private int xoffset;

	/**
	 * 
	 * Please see the discription for xoffset.
	 */
	@Parameter(required = true)
	private int yoffset;

	/**
	 * If given (eg &lt;tr &gt;), then the body of this component will be
	 * rendered inside the tag, specified by element. Also, informal parameters
	 * are included.
	 */
	@Parameter(required = false)
	private String element;

	@Parameter(required = false)
	private boolean disabled;

	/**
	 * When set to true, no tooltip is displayed when the mouse is over the
	 * element.
	 * 
	 */
	@Parameter(required = false)
	private Boolean staticFlag;

	/**
	 * 
	 * When set to true, the tooltip will move with the mouse. Otherwise, the
	 * tooltip will stay at the same opposition until the mouse is out of the
	 * associated object or being timed out (if timeout is set), which ever
	 * happens sooner.
	 */
	@Parameter(required = false)
	private int timeout;

	@Environmental
	private RenderSupport support;

	@Inject
	private ComponentResources resources;
	
	
	private String clientId;

	public String getClientId() {
		return clientId;
	}

	Boolean defaultStaticFlag() {
		return new Boolean(false);
	}

	protected void beginRender(MarkupWriter writer) {

		clientId = support.allocateClientId(resources.getId());
		String tag = (element == null ? "span" : element);

		if (disabled) {
			writer.element(tag);
			resources.renderInformalParameters(writer);

		} else {
			writer.element(tag, "id", clientId, "class", wrapperClass);

			resources.renderInformalParameters(writer);

			String content = getContent();
	
			support.addScript(content);
			
		}
	}

	protected void afterRender(MarkupWriter writer) {
		writer.end(); // <tag>
	}

	protected String getContent() {

		Object[] objects = new Object[] { clientId, tooltipContent,
				xoffset + "", yoffset + "", staticFlag.toString(), timeout + "" };

		String var = resources.getMessages().format("jsVar", objects);

		return var;
	}

	public int getXoffset() {
		return xoffset;
	}

	public void setXoffset(int xoffset) {
		this.xoffset = xoffset;
	}

}
