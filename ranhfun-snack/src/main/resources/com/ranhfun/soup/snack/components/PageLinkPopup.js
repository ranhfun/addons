	
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
	
	// A object to create a  popup window when the underlying component is clicked.
	var pageLinkPopup = Class.create();

	//Defining the rest of the class implementation
	pageLinkPopup.prototype = {

	   initialize: function(component, url, windowName, features) {	        
			this.component = $(component);
			this.url =url;
			this.windowName=windowName;
			this.features=features;
			
			this.component.onclick = this.openPopup.bindAsEventListener(this);
			
	   },
   
	   openPopup: function() {	   	          
	      var newWindow = window.open(this.url, this.name, this.features);
          newWindow.focus();		 
	   }
	};