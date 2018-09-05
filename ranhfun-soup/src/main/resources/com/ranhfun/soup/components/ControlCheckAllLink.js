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

var CheckboxGroupAllLink = function(controlCheckboxLink, controlledCheckboxes){
	this.initialize(controlCheckboxLink, controlledCheckboxes);
};

//defining the rest of the class implementation
CheckboxGroupAllLink.prototype = {

   initialize: function(controlCheckboxLink, controlledCheckboxes) {	        
	   this.controlCheckboxLink = $("#" + controlCheckboxLink);
	   this.controlledCheckboxes = controlledCheckboxes;
	   var that = this;
	   this.controlCheckboxLink.bind("click", {that:that}, this.selectAllCheckboxes);
		
   },

   selectAllCheckboxes: function(e) {
	  var that = e.data.that;
	  $.each(that.controlledCheckboxes, function(n, value) { 
			$('#' + value).attr("checked","checked"); 
      }); 
   }
};