package com.ranhfun.soup.components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Heartbeat;

/**
 * A control link to control the standard Checkbox with mixin 
 * ControlledCheckbox.
 * 
 */
@Import(library="ControlCheckAllLink.js")
public class ControlCheckAllLink implements ClientElement {

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
	
	@Inject
	private ComponentResources resources;

	private String clientId;
	
	public void beginRender(MarkupWriter writer) {
		clientId = renderSupport.allocateClientId(resources.getId());
		writer.element("a", "href" , "javascript:void(0)",  "id", clientId);
		resources.renderInformalParameters(writer);
	}
	
	public void afterRender(MarkupWriter writer) {
		
		writer.end(); // <a>
		
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
						"new CheckboxGroupAllLink('%s', %s);", 
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

	public String getClientId() {
		return clientId;
	}
	
}
