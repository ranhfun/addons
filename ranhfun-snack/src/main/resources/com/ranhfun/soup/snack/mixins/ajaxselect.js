Ajax.SelectAjaxcompleter = Class.create({
	initialize: function(element, url, options) {
	    element = $(element);
	    this.element = element;
	    this.options = options || {};
	    this.options.paramName     = this.options.paramName || this.element.name;
	    this.options.asynchronous  = true;
	    this.options.defaultParams = this.options.parameters || null;
	    this.url                   = url;
	    this.element.onchange = this.getUpdatedText.bindAsEventListener(this);
	},
	getUpdatedText: function() {
      var entry = encodeURIComponent(this.options.paramName) + '=' +
        encodeURIComponent(this.element.value);
      this.options.parameters = this.options.callback ?
    	        this.options.callback(this.element, entry) : entry;
      if(this.options.defaultParams)
        this.options.parameters += '&' + this.options.defaultParams;
      new Ajax.Request(this.url, this.options);
    }
});
Tapestry.Initializer.selectajaxcompleter = function(elementId, url, config)
{
    $T(elementId).selectajaxcompleter = new Ajax.SelectAjaxcompleter(elementId, url, config);
};