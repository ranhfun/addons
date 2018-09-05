Ajax.ControllLinkRefresh = Class.create({
	initialize: function(element, url) {
	    this.url   	 = url;
	    this.options = {};
	    this.options.asynchronous  = true;
	    this.options.onComplete    = this.onComplete.bind(this);
		Event.observe($(element), 'click', this.getRefreshText.bindAsEventListener(this));
	},
	getRefreshText: function() {
      new Ajax.Request(this.url, this.options);
    },

    onComplete: function(request) {
    	$A(request.responseText.evalJSON()).each(function(spec) {
    		$(spec.id).update(spec.value);
    	 });
    }
});
Tapestry.Initializer.controllLinkRefresh = function(elementId, url)
{
    $T(elementId).controllLinkRefresh = new Ajax.ControllLinkRefresh(elementId, url);
};