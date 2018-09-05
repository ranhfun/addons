// Copyright 2009 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.

//
// This component is a wrapper around javascript tabricator.js written by Bill
// Brown. The javascript could be found at http://scripteka.com/
//

var Tabricator = Class.create();
Tabricator.prototype = {
  root : null,
  trigtype : null,
  targtype : null,
  initialize : function (lmnt,trig,deep) {
    var root = this.root = $(lmnt).addClassName('tabricator').cleanWhitespace();
    this.trigtype = trig;
    this.targtype = this.root.select(trig).first().next().nodeName;
    var trigs = this.root.select(this.trigtype).each(function(trig){
      if (trig.up() === root) trig.addClassName('trig');
    });
    var targs = this.root.select(this.targtype).each(function(targ){
      if (targ.up() === root) {
        targ.addClassName('targ').hide();
        root.insert(targ);
      }
    });
    trigs[0].addClassName('open');
    targs[0].show();
    this.root.observe('click',this.swap.bindAsEventListener(this));
  },
  swap : function (event) {
    var trig = Event.element(event);
    if (trig.nodeName !== this.trigtype || trig.up() !== this.root) return;
    var trigs = this.root.select(this.trigtype).invoke('removeClassName','open');
    var targs = this.root.select(this.targtype).invoke('hide');
    var i = trigs.length;
    while (i--) {
      if (trigs[i] === trig) {
        trigs[i].addClassName('open');
        targs[i].show();
      }
    }
  }
};
