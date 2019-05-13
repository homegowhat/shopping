var url = "ws://"+window.location.host+"/wbs_eeeee";
    var socket = null;
    $(document).ready(function() {
        socket = new WebSocket(url);
        socket.onmessage = handleMsg;
        
//        window.setInterval(function() {
//      	  var elem = document.getElementById('messages');
//      	  elem.scrollTop = elem.scrollHeight;
//      	}, 50);
    });
    
    Number.prototype.padLeft = function(base,chr){
   		var  len = (String(base || 10).length - String(this).length)+1;
   		return len > 0? new Array(len).join(chr || '0')+this : this;
	}
    
    function xxx(){
    	var elem = document.getElementById('messages');
    	  elem.scrollTop = elem.scrollHeight;
    	  alert("f");
    }

    function handleMsg(event) {
    	var obj = JSON.parse(event.data);
    	//alert(obj.act);
    	if(obj.act == 9){
    		
        	var rId = $('#OrderTeamId').val();
    		var num = $('#txtWbs').val();
        	var msg = new Msg(0, rId,num, ""); 
        	socket.send(JSON.stringify(msg));
    	}else if(obj.act == 0){
    		var msgJObj = $('#messages');
    		 msgJObj.html(msgJObj.html() +getSystemMsgDiv(obj.userFullName+"進入房間..."));
        	 msgJObj.scrollTop(msgJObj[0].scrollHeight);
        	 var imgSrc = "/bootstrap/images/user/"+obj.username;
        	if(!IsFileExist(imgSrc)){
        		imgSrc='/bootstrap/images/user/0';
        	}
    		var membersObj = $('#members');
    		 membersObj.html(membersObj.html() +getMemberDiv(null, obj.userFullName, imgSrc));
    		 membersObj.scrollTop(msgJObj[0].scrollHeight);
    	}else if(obj.act == 1 || obj.act == 4){
    		//alert(obj.msg);
    		var arq = obj.msg.split("_");
    		//alert(arq);
    		if(arq[0]=="1"){
    			gotoWeightList(arq[1]);
    		}else if(arq[0]=="2"){
    			gotoSlidePage() ;
    		}
        	var msgJObj = $('#messages');
       		var imgSrc = "/bootstrap/images/user/"+obj.username;
        	if(!IsFileExist(imgSrc)){
        		imgSrc='/bootstrap/images/user/0';
        	}
       		var d = new Date,
        	dformat = [ d.getFullYear(),(d.getMonth()+1).padLeft(),d.getDate().padLeft()].join('/')+' '+[ d.getHours().padLeft(),d.getMinutes().padLeft(),d.getSeconds().padLeft()].join(':');
            var isMe = ($('#txtWbs').val()==obj.num);
            var content = obj.act==1?getMsgDiv(obj,imgSrc,dformat, isMe):getImgDiv(obj,imgSrc,dformat, isMe)
            var top =msgJObj[0].scrollHeight;
//            alert(top);
           msgJObj.html(msgJObj.html() +content);
//         	msgJObj.scrollTop(msgJObj[0].scrollHeight);
//           alert(top);
           
           msgJObj.animate({
               scrollTop: top
           }, 1);
           
           
          
           
//         	if ($("#char_bar").hasClass('aaa')){
//         		$("#char_bar").css("background","#FFAA33");
//         		$("#char_bar").css({"-webkit-animation":"twinkling 1s infinite ease-in-out"});
         
         	//$('#msgVideo').get(0).play();
         	 // document.getElementById("msgVideo").play();
         	 // playsound();
         	 
//         	}
         }else if(obj.act == 2){
    		//某人離房
    		var msgJObj = $('#messages');
    		 msgJObj.html(msgJObj.html() +getSystemMsgDiv(obj.userFullName+"離開房間..."));
        	 msgJObj.scrollTop(msgJObj[0].scrollHeight);
        	 
        	 $( "#"+obj.username ).remove();
        	 //$( "div" ).remove( "#"+ obj.username);
    	}else if(obj.act == 3){
    		var content = "";
    		for(var name in obj.memberList){
    			var imgSrc = "/bootstrap/images/user/"+obj.memberList[name].username;
        		if(!IsFileExist(imgSrc)){
        			imgSrc='/bootstrap/images/user/0';
        		}
        		content+=getMemberDiv(obj.memberList[name].username, obj.memberList[name].name, imgSrc);
        		//content+="<div id='"+obj.memberList[name].username+"' class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar'><img src='"+imgSrc+"'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p>"+ obj.memberList[name].name+"</p></div></div></div>";
    		}
    		
    		var membersObj = $('#members');
    		 membersObj.html(membersObj.html() +content);
    		 membersObj.scrollTop(membersObj[0].scrollHeight);
    	}else if(obj.act == 5){
    		var msgJObj = $('#messages');
    		 msgJObj.html(msgJObj.html() +getSystemMsgDiv(obj.msg));
        	 msgJObj.scrollTop(msgJObj[0].scrollHeight);
    	}else if(obj.act == 11){
    		gotoWeightList(obj.msg);
    		
    	}else if(obj.act == 12){
    		gotoSlidePage() ;
    	
    	}
    	
    }
    
            
    function playsound()
            {
            
            $('#msgVideo').trigger('load');
                //$("#msgVideo")[0].play();
                //$("#msgVideo")[0].pause();
 
                //setTimeout(function(){
                    $("#msgVideo")[0].currentTime = 0;
                    $("#msgVideo")[0].play();
               // }, 100);
            }
    
    function getMsgDiv(obj,imgSrc,dformat, isMe){
    	if(!isMe){
    		return "<div class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar'><img src='"+imgSrc+"'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p><span style='color:blue;'>"+ obj.userFullName+":</span>"+obj.msg +"</p><time >"+dformat+"</time></div></div></div>";
    	}else{
    		return "<div class='row msg_container base_sent'><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p><span style='color:blue;'>"+ obj.userFullName+":</span>"+obj.msg +"</p><time >"+dformat+"</time></div></div><div class='col-md-2 col-xs-2 avatar'><img src='"+imgSrc+"'  class=' img-responsive ' widthX='60' heightX='60' /></div></div>";
    	}
    }
    
   function getImgDiv(obj,imgSrc,dformat, isMe){
    	if(!isMe){
    		return "<div class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar' style='text-align:center'><img src='"+imgSrc+"'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p><img src='images/push/"+obj.msg+".jpg' width='100' height='100'    /></p><time >"+dformat+"</time></div></div></div>";
    	}else{
    		return "<div class='row msg_container base_sent'><div class='col-md-10 col-xs-10'><div class='messages msg_receive' style='text-align:center'><p><img src='images/push/"+obj.msg+".jpg' width='100' height='100'    /></p><time >"+dformat+"</time></div></div><div class='col-md-2 col-xs-2 avatar'><img src='"+imgSrc+"'  class=' img-responsive ' widthX='60' heightX='60' /></div></div>";
    	}
    }
    
    function getSystemMsgDiv(msg){
    	return "<div style='color:red;'>"+msg+"</div>";
    }
    
    function getMemberDiv(id, userFullName, imgSrc){
    	if(id != null)id = "id='"+id+"'"
    	return "<div "+id+" class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar'><img src='"+imgSrc+"'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p>"+ userFullName+"</p></div></div></div>";
    }
    
    function IsFileExist(filePath){
	var bo = false;
		$.ajax({
    		url:filePath,
    		type:'HEAD',
    		async:false,
    		success:function(){
        	bo = true;
    	},
   		 error:function(){
        		bo = false;
   			 }
		});
		return bo;
	}
   
    function disconnect() {
    	var rId = $('#OrderTeamId').val();
    	var num = $('#txtWbs').val();
        var msg = new Msg( 2, rId,num, ""); 
        socket.send(JSON.stringify(msg));
        socket.close();
    }

    function sendMessage() {
    	var rId = $('#OrderTeamId').val();
    	var num = $('#txtWbs').val();
        var message = $('#btn-input').val();
        if(message != ""){
        var msg = new Msg( 1, rId,num, message); 
        socket.send(JSON.stringify(msg));
        $('#btn-input').val('');
        }
    }
    
     function sendImage(img) {
    	 $("#faceul").hide();
    	var rId = $('#OrderTeamId').val();
    	var num = $('#txtWbs').val();
        var msg = new Msg( 4, rId,num, img); 
        socket.send(JSON.stringify(msg));
    }
    
    function closeImageDiv(){
    	$("#faceul").hide();
    }
    
    
    function showImgDiv(){
    	var offset = $("#showImgBtn").offset();
		$("#faceul").css("left", offset.left);
		$("#faceul").css("top",offset.top+ $("#faceul").offsetHeight);
    	$("#faceul").show();
    }

    $(document).on('click', '.panel-heading span.icon_minim', function (e) {
        var $this = $(this);
        if (!$this.hasClass('panel-collapsed')) {
            $this.parents('.panel').find('.panel-body').slideUp();
            $this.addClass('panel-collapsed');
            $this.removeClass('glyphicon-minus').addClass('glyphicon-plus');
            $("#char_bar").addClass('aaa');
            $("#msgVideo")[0].play();
            $("#msgVideo")[0].pause();
        } else {
            $this.parents('.panel').find('.panel-body').slideDown();
            $this.removeClass('panel-collapsed');
            $this.removeClass('glyphicon-plus').addClass('glyphicon-minus');
             $("#char_bar").removeClass('aaa');
              $("#char_bar").css("background","");
              	$("#char_bar").css({"-webkit-animation":""});
              	var msgJObj = $('#messages');
              	msgJObj.scrollTop(msgJObj[0].scrollHeight);
        }
    });
    
    function Msg( act,rId,num, msg) {
    	this.act = act;
        this.rId = rId; 
        this.msg = msg; 
        this.num = num;
	}
  