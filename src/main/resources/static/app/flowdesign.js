(function(w){
	
	Raphael.fn.connection = function (obj1, obj2, line, bg) {
	    if (obj1.line && obj1.from && obj1.to) {
	        line = obj1;
	        obj1 = line.from;
	        obj2 = line.to;
	    }
	    var bb1 = obj1.getBBox(),
	        bb2 = obj2.getBBox(),
	        p = [{x: bb1.x + bb1.width / 2, y: bb1.y - 1},
	        {x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1},
	        {x: bb1.x - 1, y: bb1.y + bb1.height / 2},
	        {x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2},
	        {x: bb2.x + bb2.width / 2, y: bb2.y - 1},
	        {x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1},
	        {x: bb2.x - 1, y: bb2.y + bb2.height / 2},
	        {x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2}],
	        d = {}, dis = [];
	    for (var i = 0; i < 4; i++) {
	        for (var j = 4; j < 8; j++) {
	            var dx = Math.abs(p[i].x - p[j].x),
	                dy = Math.abs(p[i].y - p[j].y);
	            if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
	                dis.push(dx + dy);
	                d[dis[dis.length - 1]] = [i, j];
	            }
	        }
	    }
	    if (dis.length == 0) {
	        var res = [0, 4];
	    } else {
	        res = d[Math.min.apply(Math, dis)];
	    }
	    var x1 = p[res[0]].x,
	        y1 = p[res[0]].y,
	        x4 = p[res[1]].x,
	        y4 = p[res[1]].y;
	    dx = Math.max(Math.abs(x1 - x4) / 2, 10);
	    dy = Math.max(Math.abs(y1 - y4) / 2, 10);
	    var x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
	        y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
	        x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
	        y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
	    var path = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
	    if (line && line.line) {
	        line.bg && line.bg.attr({path: path});
	        line.line.attr({path: path});
	    } else {
	        var color = typeof line == "string" ? line : "#000";
	        var l = this.path(path).attr({stroke: color, "arrow-end":'classic-wide-long',"stroke-width":2});
	        l.hover(function(){
	        	this.attr({"opacity":0.6})
	        },function(){
	        	this.attr({"opacity":1})
	        })
	        return {
	            bg: bg && bg.split && this.path(path).attr({stroke: bg.split("|")[0], fill: "none", "stroke-width": bg.split("|")[1] || 3}),
	            line: l,
	            from: obj1,
	            to: obj2,
	            toString:function(){
	            	return {
	            		from:this.from.id,
	            		to:this.to.id
	            	}
	            }
	        };
	    }
	};
	
	/*
	 * eles:{parent:xxx,children:[]}
	 */
	function Combox(eles,type,contexts){
		this.type = type;
		this.eles = eles;
		this.contexts = contexts;
		this.eles.children = [eles.title]
		if(this.eles.approver){
			this.eles.children.push(this.eles.approver);
		}
		if(this.eles.edit){
			this.eles.children.push(this.eles.edit)
		}
		
		var dragger = function () {
			this.ox = (this.type == "rect" || this.type == "text" || this.type == 'image') ? this.attr("x") : this.attr("cx");
			this.oy = (this.type == "rect" || this.type == "text" || this.type == 'image') ? this.attr("y") : this.attr("cy");
			var childrenStart = [];
			if(eles.children){
				for(var i=0; i<eles.children.length; i++){
					var c = eles.children[i];
					childrenStart[i] = (c.type == 'rect'||c.type == 'text' || c.type == 'image')?{x:c.attr("x"),y:c.attr("y")}:{x:c.attr("cx"),y:c.attr("cy")}
				}
				this.data("initPosition",childrenStart);
			}
		},move = function (dx, dy) {
			var att = (this.type == "rect" || this.type == "text" || this.type == 'image') ? {x: this.ox + dx, y: this.oy + dy} : {cx: this.ox + dx, cy: this.oy + dy};
			this.attr(att);
			if(contexts && contexts.lines && contexts.lines.length>0){
				for (var i = contexts.lines.length; i--;) {
					contexts.paper.connection(contexts.lines[i]);
				}
			}
			//子节点跟随移动
			if(eles.children){
				var childrenStart = this.data("initPosition");
				for(var i=0; i<eles.children.length; i++){
					var c = eles.children[i];
					var start = childrenStart[i];
					var position = (c.type == 'rect'||c.type=='text' || c.type == 'image')?{x:start.x+dx,y:start.y+dy}:{cx:start.x+dx,cy:start.y+dy}
					c.attr(position);
				}
			}
		},up = function () {
		};
		//移动父节点
		this.eles.parent.drag(move,dragger,up)
	}
	Combox.prototype.toString = function(){
		if(this.type == 'task'){
			var info = this.eles.edit.data("textinfo");
			info = info?info:{};
			info.id = this.eles.parent.id;
			info.type = "task";
			info.position = {
					x:this.eles.parent.attr("x"),
					y:this.eles.parent.attr("y")
			}
			return info;
		}else{
			return {
				type:this.type,
				id:this.eles.parent.id,
				position:{
					x:this.eles.parent.attr("x"),
					y:this.eles.parent.attr("y")
				}
			}
		}
	}
	
	var baseDraw = {
			init:function(id){
				this.paper  = Raphael("flowconfig");
			},
			getById:function(id){
				return this.paper.getById(id);
			},
			remove:function(){
				this.paper.remove();
			},
			circle:function(x,y,r){
				var c =  this.paper.circle(x,y,r);
				this.wrapper(c);
				return c;
			},
			rect:function(x,y,w,h,r){
				var rect  = this.paper.rect(x,y,w,h,r);
				this.wrapper(rect);
				return rect;
			},
			text:function(x,y,content){
				var t = this.paper.text(x,y,content);
				this.wrapper(t);
				return t;
			},
			wrapper:function(ele){
				ele.attr({
					"stroke":"#222D32",
					"font-size":10,
					"font-weight":"200",
					"cursor":"move",
					"fill":"#fff"
				})
				if(ele.type == 'rect'){
					ele.attr({
						"fill-opacity":0,
						"stroke-width":2
					})
				}
				ele.hover(function(){
					this.attr({
						"opacity":0.6
					})
				},function(){
					this.attr({
						"opacity":1
					})
				})
			},
			uuid:function(){
				var s = [];
			    var hexDigits = "0123456789abcdef";
			    for (var i = 0; i < 36; i++) {
			        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
			    }
			    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
			    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
			    s[8] = s[13] = s[18] = s[23] = "-";
			 
			    var uuid = s.join("");
			    return uuid;
			}
	}
	var flow = {
			comboxs:[],
			lines:[],
			drawBegin:function(x,y){
				for(var i=0; i<this.comboxs.length; i++){
					if(this.comboxs[i].type == 'begin'){
						return;
					}
				}
				var beginRect = this.rect(x,y,80,40,10);
				beginRect.attr({
//					stroke:"#1CB841",
					title:"开始"
				});
				var beginText = this.text(x+40,y+20,"开始");
				beginText.attr({
//					stroke:"#1CB841",
					"stroke-width":1,
					"stroke-width":0.5
				})
				var r = new Combox({parent:beginRect,title:beginText},"begin",this);
				this.comboxs.push(r);
				return r;
			},
			drawEnd:function(x,y){
				for(var i=0; i<this.comboxs.length; i++){
					if(this.comboxs[i].type == 'end'){
						return;
					}
				}
				var endRect = this.rect(x,y,80,40,10);
				endRect.attr({"title":"结束"});
				var endText = this.text(x+40,y+20,"结束");
				endText.attr({"stroke-width":0.5})
				var r = new Combox({parent:endRect,title:endText},"end",this);
				this.comboxs.push(r);
				return r;
			},
			drawTask:function(x,y,name,approver){
				var taskRect = this.rect(x,y,120,50,5);
				
				var title = this.text(x+60,y+12,name);
				title.attr({"stroke":"#222D32","stroke-width":0.5,"font-size":12}).toBack();
				
				var approver = this.text(x+60,y+38,"审批人:"+(approver?approver:"---"))
				approver.attr({"stroke-width":0.5,"font-size":10,"stroke":"#222D32"}).toBack();
				
				var edit = this.paper.image("/app/imgs/flow-edit.png",x+100,y+2,15,15).toFront(0).attr({"cursor":"pointer","title":"编辑"});
				edit.click(function(){
					var textinfo = this.data("textinfo");
					$("#editNode").form("load",{
						nodeid:this.id,
						taskName:textinfo?textinfo.taskname:"",
						approverId:textinfo?textinfo.approverid:""
					})
					$("#saveNode").dialog("open")
				});
				
				var r = new Combox({parent:taskRect,title:title,approver:approver,edit:edit},"task",this);
				this.comboxs.push(r);
				return r;
			},
			line:function(from,to){
				if(from.id == to.id){
					return;
				}
				for(var i=0; i<this.lines.length; i++){
					if(this.lines[i].from.id == from.id || this.lines[i].to.id == to.id){
						return;
					}
				}
				var l = this.paper.connection(from,to,"#3498DB")
				this.lines.push(l);
				return l;
			},
			update:function(editid){
				var p = this.getById(editid);
				var textinfo = p.data("textinfo");
				for(var i=0; i<this.comboxs.length; i++){
					if(this.comboxs[i].type == 'task' && this.comboxs[i].eles.edit.id == editid){
						this.comboxs[i].eles.title.attr({text:textinfo.taskname});
						this.comboxs[i].eles.approver.attr({text:"审批人:"+textinfo.approvername});
						return;
					}
				}
			},
			toString:function(){
				var nodes = [];
				var lines = [];
				for(var i=0; i<this.comboxs.length; i++){
					nodes.push(this.comboxs[i].toString());
				}
				for(var i=0; i<this.lines.length; i++){
					lines.push(this.lines[i].toString());
				}
				return {
					nodes:nodes,
					lines:lines
				}
			},
			load:function(data){
				if(data){
					var ls = data.lines;
					var ns = data.nodes;
					var map = {};
					for(var i=0; i<ns.length; i++){
						if(!ns[i].position){
							ns[i].position = {x:100,y:100}
						}
						if(ns[i].type == 'begin'){
							map[ns[i].id] = this.drawBegin(ns[i].position.x,ns[i].position.y)
						}else if(ns[i].type == 'end'){
							map[ns[i].id] = this.drawEnd(ns[i].position.x,ns[i].position.y)
						}else if(ns[i].type == 'task'){
							var r = this.drawTask(ns[i].position.x,ns[i].position.y,ns[i].taskname,ns[i].approvername);
							map[ns[i].id] = r;
							r.eles.edit.data("textinfo",ns[i]);
						}
					}
					for(var i=0; i<ls.length; i++){
						var f = map[ls[i].from];
						var t = map[ls[i].to];
						this.line(f.eles.parent,t.eles.parent)
					}
				}
			}
	};
	$.extend(flow,baseDraw);
	w.flow = flow;
})(window);

