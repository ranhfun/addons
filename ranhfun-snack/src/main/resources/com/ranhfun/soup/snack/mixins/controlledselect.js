Ajax.ControlledSelectRefresh = Class.create({
	initialize: function(spec) {
		this.controller = $(spec.controllerId);
        this.controlled = $(spec.controlledId);
        this.controllerId = spec.controllerId;
        this.controlledId = spec.controlledId;
	    this.options = {};
	    this.options.paramName     = this.options.paramName || spec.paramName;
	    this.options.showBlankOption = this.options.showBlankOption || spec.showBlankOption;
	    this.options.blankLabel = this.options.blankLabel || spec.blankLabel;
	    this.options.asynchronous  = true;
	    this.options.onComplete    = this.onComplete.bind(this);
	    this.options.defaultParams = this.options.parameters || null;
	    this.url                   = spec.url;
	    Event.observe(this.controller, 'change', this.getUpdatedChoices.bindAsEventListener(this));
	    Event.observe(this.controller, 'ct:change', this.getUpdatedChoices.bindAsEventListener(this));
	},
    getUpdatedChoices: function() {
      var entry = encodeURIComponent(this.options.paramName) + '=' +
        encodeURIComponent(this.controller.value)+'&showBlankOption='+this.options.showBlankOption+'&blankLabel=' + this.options.blankLabel;
      this.options.parameters = this.options.callback ?
        this.options.callback(this.element, entry) : entry;

      if(this.options.defaultParams)
        this.options.parameters += '&' + this.options.defaultParams;
      new Ajax.Request(this.url, this.options);
    },
    onComplete: function(request) {
      this.controlled.update(request.responseText.sub('<select>','',1).sub('</select>','',1));
      this.controlled.fire('ct:change');
    }
});
Tapestry.Initializer.controlledSelectRefresh = function(spec)
{
	new Ajax.ControlledSelectRefresh(spec);
};