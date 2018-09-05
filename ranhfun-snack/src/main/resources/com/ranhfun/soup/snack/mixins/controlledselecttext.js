Ajax.SelectAutocompleterText = Class.create({
	initialize: function(element, controlElementId, url, options) {
	    element = $(element);
	    this.element = element;
	    this.controlElementId = $(controlElementId);
	    this.options = options || {};
	    this.options.paramName     = this.options.paramName || this.element.name;
	    this.options.asynchronous  = true;
	    this.options.onComplete    = this.onComplete.bind(this);
	    this.options.defaultParams = this.options.parameters || null;
	    this.url                   = url;
	    //this.controlElementId.onchange = this.getUpdatedText.bindAsEventListener(this);
	    Event.observe(controlElementId, "change", this.getUpdatedText.bindAsEventListener(this));
	    Event.observe(controlElementId, "ct:change", this.getUpdatedText.bindAsEventListener(this)); 
	},
	getUpdatedText: function() {
      var entry = encodeURIComponent(this.options.paramName) + '=' +
        encodeURIComponent(this.controlElementId.value);
      this.options.parameters = this.options.callback ?
        this.options.callback(this.element, entry) : entry;

      if(this.options.defaultParams)
        this.options.parameters += '&' + this.options.defaultParams;
      new Ajax.Request(this.url, this.options);
    },

    onComplete: function(request) {
      this.element.update(request.responseText);
    }
});
Tapestry.Initializer.selectautocompletertext = function(elementId, controlElementId, url, config)
{
    $T(elementId).selectautocompletertext = new Ajax.SelectAutocompleterText(elementId, controlElementId, url, config);
};