Tapestry.Initializer.inplaceeditelement = function(elementId, url, config)
{
    $T(elementId).inplaceeditelement = new Ajax.InPlaceEditor(elementId, url, config);
};