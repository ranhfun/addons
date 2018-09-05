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

   // Thanks to Kerekes Balázs for modifying the javascript to create the 
   //  tooltip lazily, instead of doing in the onLoad function. This solves
   // a problem with  IE, which does not like tooltip being created before the
   //  window is loaded.) SHMan 15th June, 06
 
   // 26 May 2008 : Modified scripts to work in Tapestry 5.0.12 snapshot. This
   //               scripts requires prototype,js. Todo: use more prototype.
  
 
   
   // id for the span element that holds the tooltip
   var tooltipID="manToolTip"
   var debug=false;

   // Absolute position of the mouse
   var man_tooltip_xM=0;
   var man_tooltip_yM=0;

   // The offsets of the position of the upper corner of the tooltip
   // relative to (man_tooltip_xM, man_tooltip_yM).
   // The upper corner of the tooltip will be at
   // (man_tooltip_xM + xoffset, man_tooltip_yM + yoffset);
   var man_tooltip_xoffset=0;
   var man_tooltip_yoffset=0;


   //static : indicate whether tooltip should move with mouse
   var man_tooltip_staticFlag=false;

   var man_tooltip_timeoutID;
   //var man_tooltip_timeout;

   
// A wrapper for tooltip attributes.
var man_tooltip = function(component, tooltipContent, xos, yos, staticFlag,timeout){
	this.initialize(component, tooltipContent, xos, yos, staticFlag,timeout);
};

// component is the component id.
//  tooltionContent is expected to be html code
man_tooltip.prototype = {

   initialize: function(component, tooltipContent, xos, yos, staticFlag,timeout) {	        
	   this.component = $("#" + component);
	   this.tooltipContent =  man_tooltip_unescape(tooltipContent);
       this.xoffset=xos;
       this.yoffset=yos;
       this.staticFlag=staticFlag;
       this.timeout=timeout;

       if (debug){
          alert("tootip =" + tooltipContent + "," + xos + "," + yos);
       }
       var that = this;
       this.component.bind("mouseover", {that:that}, this.gettip);
       this.component.bind("mouseout", this.reset);
   },

   // Set up new tooltip and display it.
   // This is called when the mouse is over an element with a tooltip.

   gettip : function (e) {
    // begin
        // @author kerekesb@infotec.hu
	   var that = e.data.that;
    if (!isToolTipInit) {
        man_tooltip_createTooltip();
        document.onmousemove=man_tooltip_track;
        isToolTipInit = true;
    }
    // end
        var tp= document.getElementById(tooltipID);
        tp.innerHTML=that.tooltipContent;
        tp.style.visibility="visible";

        var y = man_tooltip_yM + that.yoffset;
        var x = man_tooltip_xM + that.xoffset;
        if (debug){
          alert("0 get : y=" + y);
        }

        tp.style.top=y + "px";
        tp.style.left=x + "px";
         if(debug) {
            alert("1 get : tooptip top=" + tp.style.top +", tooltip left=" +
              tp.style.left + ",tt =" + that.tooltipContent + "," +
              that.xoffset + "," + that.yoffset);
        }


        man_tooltip_xoffset = that.xoffset;
        man_tooltip_yoffset = that.yoffset;
        man_tooltip_staticFlag = that.staticFlag;

         if(debug) {
            alert("2 get : tooptip top=" + tp.style.top +", tooltip left=" +
               tp.style.left + ",tt =" + that.tooltipContent + "," + that.xoffset
                + "," + tt.yoffset);
        }

        if ( that.timeout > 0){
           man_tooltip_timeoutID = setTimeout(that.reset, that.timeout);
        }
},



// Clear and hide the tooltip
reset : function() {
     var tp= document.getElementById(tooltipID);

     if(debug) {
        alert("reset : tooptip top=" + tp.style.top +", tooltip left=" + tp.style.left);
     }
     tp.innerHTML="";
     tp.style.visibility="hidden";

     clearTimeout(man_tooltip_timeoutID);

} // reset function ends


}; // end of man_tooltip



 // Track absolute position of the mouse.
 function man_tooltip_track(e){


    if (man_isTooltipVisible() &&  man_tooltip_staticFlag){
        return;
    }
    if (!e){
            e = window.event;
         }

         var xyScrollOffset =man_getScrollXY();

         // Update global position of the mouse

         man_tooltip_xM = e.clientX + xyScrollOffset[0];
         man_tooltip_yM = e.clientY +  xyScrollOffset[1];

    // update position of tooltip.
    var tp= document.getElementById(tooltipID);
    tp.style.top=(man_tooltip_yM + man_tooltip_yoffset + 5) + "px";
    tp.style.left=(man_tooltip_xM + man_tooltip_xoffset + 5) + "px";
    if (debug){
        window.status = man_tooltip_xM + "," +man_tooltip_yM + "," +
          man_tooltip_xoffset + "," + man_tooltip_yoffset;
   }
 }




// begin
// @author kerekesb@infotec.hu
var isToolTipInit = false;
// end

// To create a span element for holding/displaying the tooltip.
// This is called from body  onload
function man_tooltip_createTooltip(){
      if (debug){
         alert("create tooltip");
      }
     var elem = document.createElement("span");
         elem.setAttribute("id", tooltipID);
         elem.setAttribute("name", tooltipID);
     // begin
     // @author kerekesb@infotec.hu
     elem.style.visibility="hidden";
     elem.style.position="absolute";
     // end
         document.body.appendChild(elem);
         // begin
         // @author kerekesb@infotec.hu
         //document.getElementById(tooltipID).style.visibility="hidden";
         //document.getElementById(tooltipID).style.position="absolute";
         // end
}


 // A function to get the scroll offset
 function man_getScrollXY() {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    //Netscape compliant
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    //DOM compliant
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement &&
      ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  return [ scrOfX, scrOfY ];
}

function man_isTooltipVisible(){
        var tp= document.getElementById(tooltipID);
        var visible=false;
        if(tp.style.visibility =="visible"){
           visible = true;
        }
        return visible;
}

function man_tooltip_unescape(tooltipContent){
    var str = tooltipContent.replace(/&lt;/g,"<");
    str = str.replace(/&gt;/g,">");
       if (false){
          alert("man-tooltip_unescape: tootip =" + str);
       }
    return str;

}
  