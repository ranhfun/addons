Ajax.ControllInputRefresh = Class.create({
	initialize: function(params) {
		this.controller = $(params.controllerId);
		this.customer = $(params.customerId);
		this.editable = params.editable;
		this.splitId = "";
		if(params.controllerId.split('_').length>1) {
		  splitId = "_" + params.controllerId.split('_')[1];
		}	
		this.options = {};
	    this.options.asynchronous  = true;
	    this.options.onComplete    = this.onComplete.bind(this);
	    this.options.defaultParams = this.options.parameters || null;
	    this.url                   = params.url;		
	    Event.observe(params.controllerId, "change", this.getUpdatedText.bindAsEventListener(this)); 
	    Event.observe(params.controllerId, "ct:change", this.getUpdatedText.bindAsEventListener(this)) 
	},
	getUpdatedText: function() {
      var entry = encodeURIComponent("controllerValue") + '=' +
        encodeURIComponent(this.customer.value);
      this.options.parameters = this.options.callback ?
        this.options.callback(this.element, entry) : entry;

      if(this.options.defaultParams)
        this.options.parameters += '&' + this.options.defaultParams;
      new Ajax.Request(this.url, this.options);
    },

    onComplete: function(request) {
    	splitId = this.splitId;
    	$A(request.responseText.evalJSON()).each(function(item) {
    		Try.these(
    			function() { 
			      	if(item.value.indexOf('<select>')>-1) {
			      		$(item.id + splitId).update(item.value.sub('<select>','',1).sub('</select>','',1));
			      	} else {
			      		$(item.id + splitId).setValue(item.value);
			      	}    				
    			},
		      	function() { 
			      	$(item.id + splitId).update(item.value);
		      	}
    		)     		
    	 });
    }
});
Tapestry.Initializer.controllInputRefresh = function(params)
{
    $T(params.controllerId).controllInputRefresh = new Ajax.ControllInputRefresh(params);
};