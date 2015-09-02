var browser = function() {
	var ie = document.all ? true : false;
	return {
		getElementX : function (elem) {
			var curleft = 0;
			if (elem.offsetParent) 
			{
				while (elem.offsetParent) 
				{
					curleft += elem.offsetLeft;
					elem = elem.offsetParent;
				}
			}
			else if (elem.x)
				curleft += elem.x;
			return curleft;
		},
		getElementY : function (elem) {
			var curtop = 0;
			if (elem.offsetParent) 
			{
				while (elem.offsetParent) 
				{
					curtop += elem.offsetTop
					elem = elem.offsetParent;
				}
			}
			else if (elem.y)
				curtop += elem.y;
			return curtop;
		},
		getElementWidth : function (elem) {
	        return elem.offsetWidth;
		},
		getElementHeight : function (elem) {
			return elem.offsetHeight;
		},
		getScreenWidth : function () {
			 if (typeof window.innerWidth != 'undefined') {
			      return window.innerWidth;
			 }
			 
			// IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
			 else if (typeof document.documentElement != 'undefined'
			     && typeof document.documentElement.clientWidth !=
			     'undefined' && document.documentElement.clientWidth != 0) {
			       return document.documentElement.clientWidth;
			 }
			 
			 // older versions of IE
		 
			 else {
			       return document.getElementsByTagName('body')[0].clientWidth;
			 }
		},
		getScreenHeight : function () {
			 if (typeof window.innerWidth != 'undefined') {
			      return window.innerHeight;
			 }
			 
			// IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
			 else if (typeof document.documentElement != 'undefined'
			     && typeof document.documentElement.clientWidth !=
			     'undefined' && document.documentElement.clientWidth != 0) {
			       return document.documentElement.clientHeight;
			 }
			 
			 // older versions of IE
			 else
			 {
				 return  document.getElementsByTagName('body')[0].clientHeight;
			 }
		},
		getScreenScrollY : function () {
			var scrollY = document.body.scrollTop;
			if (scrollY == 0)
			{
			    if (window.pageYOffset)
			        scrollY = window.pageYOffset;
			    else
			        scrollY = (document.body.parentElement) ? document.body.parentElement.scrollTop : 0;
			}
			return scrollY;
		},
		getScreenScrollX : function () {
			var scrollX = document.body.scrollLeft;
			if (scrollX == 0)
			{
			    if (window.pageXOffset)
			        scrollX = window.pageXOffset;
			    else
			        scrollX = (document.body.parentElement) ? document.body.parentElement.scrollLeft : 0;
			}
			return scrollX;
		}
	};
}();

var logger = function() {
	var level = 0;	// values 1 to 5 where 1 = debug, 2 = info, 3 = warn, 4 = error, 5 = severe
	return {
		setLevel : function(l) {
			level = l;
		},
		debug : function(msg) {
			if (level <= 1 && window['console']) {
				console.log(msg);
			}
		},
		info : function(msg) {
			if (this.level <= 2 && window['console']) {
				console.log(msg);
			}
		},
		warn : function(msg) {
			if (this.level <= 3 && window['console']) {
				console.log(msg);
			}
		},
		error : function(msg) {
			if (this.level <= 4 && window['console']) {
				console.log(msg);
			}
		},
		severe : function(msg) {
			if (this.level <= 5 && window['console']) {
				console.log(msg);
			}
		}
	};
}();

function HashMap() {
	this.keys = new Array();
	this.values = new Array();
	this.put = function(key, value) {
		var index = this.getIndex(key);
		if (index>-1) {
			this.values[index] = value;
		} else {
			this.keys.push(key);
			this.values.push(value);
		}
	};
	this.get = function(key) {
		var index = this.getIndex(key);
		if (index>-1) {
			return this.values[index];
		}
		return null;
	};    
	this.getKeyByIndex = function(index) {
		if (index>-1 && index < this.size()) {
			return this.keys[index];
		}
		return null;
	};    
	this.getValueByIndex = function(index) {
		if (index>-1 && index < this.size()) {
			return this.values[index];
		}
		return null;
	};    
	this.getIndex = function(key) {
		for( var index = 0; index < this.keys.length; index++ ){
			if( this.keys[ index ] == key ) {
				return index;
			}
		}
		return -1;
	};
	this.size = function(){
		return (this.keys.length);  
	};
	this.clear = function() {
		while (this.keys.length > 0) { 
			this.keys.pop();
			this.values.pop(); 
		} 
	};
	this.remove = function ( key ){
		var index = this.getIndex(key);
		if (index>-1) {
			this.keys = this.removeAt(this.keys, index);
			this.values = this.removeAt(this.values, index);
		}
	};
	this.removeAt = function ( array, index ){
		var part1 = array.slice( 0, index);
		var part2 = array.slice( index+1 );
		return( part1.concat( part2 ) );
	};
}

/*
* This function will replace all markers in a string that have a key in the map, json array or json object.
* ${replace.me} is how a marker is described in the page content
* "replace.me" is marker key in the map
*/
function MarkerReplacement() {
	this.replace = function(data, content) {
		if (data.hasOwnProperty('size')) {
			for (var index = 0 ; index < data.size() ; index++){
				var key = data.getKeyByIndex(index);
				var value = data.getValueByIndex(index);
				content = content.replace(new RegExp('\\${' + key + '}', 'gi'),value);
			}
		}
		else if (data.hasOwnProperty('length')) {
			// its an array
			for (var i = 0; i < data.length; i++){
				for(var key in data[i]){
					var value = data[i][key];
					content = content.replace(new RegExp('\\${' + key + '}', 'gi'),value);
				}
			}
		} else {
			for(var key in data){
				var value = data[key];
				content = content.replace(new RegExp('\\${' + key + '}', 'gi'),value);
			}
		}
		return content;
	}
}
var tooltip = function() {
	var id = 'tooltip';
	var maxw = 300;
	var speed = 10;
	var timer = 20;
	var endalpha = 100;	// original was 75
	var alpha = 0;
	var tt = null;
	var zIndex = '100';
	
	var ie = document.all ? true : false;
	return {
		createTT : function () {
			if (tt == undefined) {
				tt = document.createElement('div');
				document.body.appendChild(tt);
				tt.style.position = 'absolute';
				tt.style.zIndex = zIndex;
				console.debug("tooltip.createTT(zIndex:" + zIndex + ");")
			}
		},
		show : function(elem, content, width, height) {
			tooltip.createTT();
			if (tt.timer) {
				clearInterval(tt.timer);
			}
			tt.timer = setInterval(function() {tooltip.showOnTimer(elem, content, width, height)}, 1000);
		},
		showID : function(elem, id, width, height) {
			tooltip.createTT();
			if (tt.timer) {
				clearInterval(tt.timer);
			}
			var s = document.getElementById(id).innerHTML;
			tt.timer = setInterval(function() {tooltip.showOnTimer(elem, s, width, height)}, 1000);
		},
		showOnTimer : function(elem, content, width, height) {
			tt.style.display = 'block';
			tt.innerHTML = '<div class="tooltip">' + content + '</div>';
			this.setPos(tt, elem, width, height);
			clearInterval(tt.timer);
		},
		setPos : function(tooltip, elem, w, h) {
			if (h == undefined) {
				h = browser.getElementHeight(tt);
			} else {
				tooltip.style.height = h;
			}
			if (w == undefined){
				w = browser.getElementWidth(tt);
			} else {
				tooltip.style.width = w;
			}

			tooltip.style.top = tt.mouseY + 10 + 'px';
			tooltip.style.left = tt.mouseX + 'px';
		},
		fade : function(d) {
			var a = alpha;
			if ((a != endalpha && d == 1) || (a != 0 && d == -1)) {
				var i = speed;
				if (endalpha - a < speed && d == 1) {
					i = endalpha - a;
				} else if (alpha < speed && d == -1) {
					i = a;
				}
				alpha = a + (i * d);
			} else {
				clearInterval(tt.timer);
				if (d == -1) {
					tt.style.display = 'none'
				}
			}
		},
		hide : function() {
			tooltip.createTT();
			clearInterval(tt.timer);
			tt.timer = setInterval(function() {
				tooltip.fade(-1)
			}, timer);
		},
		onmousemove : function(evt) {
			tooltip.createTT();
			tt.mouseX = evt.pageX;
			tt.mouseY = evt.pageY;
		}
	};
}();
document.onmousemove = tooltip.onmousemove;

function popup() {
	var id = 'popup';
	var maxw = 300;
	var speed = 10;
	var timer = 20;
	var endalpha = 100;	// original was 75
	var alpha = 0;
	var tt = null;
	
	var ie = document.all ? true : false;
	return {
		createTT : function () {
			if (tt == undefined) {
				tt = document.createElement('div');
				document.body.appendChild(tt);
				tt.style.position = 'absolute';
			}
		},
		show : function(elem, content, width, height) {
			tooltip.createTT();
			if (tt.timer) {
				clearInterval(tt.timer);
			}
			tt.timer = setInterval(function() {tooltip.showOnTimer(elem, content, width, height)}, 1000);
		},
		showID : function(elem, id, width, height) {
			tooltip.createTT();
			if (tt.timer) {
				clearInterval(tt.timer);
			}
			var s = document.getElementById(id).innerHTML;
			tt.timer = setInterval(function() {tooltip.showOnTimer(elem, s, width, height)}, 1000);
		},
		showOnTimer : function(elem, content, width, height) {
			tt.style.display = 'block';
			tt.innerHTML = '<div class="tooltip">' + content + '</div>';
			this.setPos(tt, elem, width, height);
			clearInterval(tt.timer);
		},
		setPos : function(tooltip, elem, w, h) {
			if (h == undefined) {
				h = browser.getElementHeight(tt);
			} else {
				tooltip.style.height = h;
			}
			if (w == undefined){
				w = browser.getElementWidth(tt);
			} else {
				tooltip.style.width = w;
			}

			tooltip.style.top = tt.mouseY + 10 + 'px';
			tooltip.style.left = tt.mouseX + 'px';
		},
		fade : function(d) {
			var a = alpha;
			if ((a != endalpha && d == 1) || (a != 0 && d == -1)) {
				var i = speed;
				if (endalpha - a < speed && d == 1) {
					i = endalpha - a;
				} else if (alpha < speed && d == -1) {
					i = a;
				}
				alpha = a + (i * d);
			} else {
				clearInterval(tt.timer);
				if (d == -1) {
					tt.style.display = 'none'
				}
			}
		},
		hide : function() {
			tooltip.createTT();
			clearInterval(tt.timer);
			tt.timer = setInterval(function() {
				tooltip.fade(-1)
			}, timer);
		},
		onmousemove : function(evt) {
			tooltip.createTT();
			tt.mouseX = evt.pageX;
			tt.mouseY = evt.pageY;
		}
	};
};

