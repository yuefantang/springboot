
$(document).ready(function(){
  $("#login").click(function(){
	var  usernameValue= $("#txtuser").val();
	var  passWordValue= $("#password").val();
	var data={  //请求参数
        	userName : usernameValue,
        	password: passWordValue
        }
	
	if(usernameValue==''||passWordValue=='')
	{
	
				$('#ts').html("<b style='color :red'>密码或用户名不能为空</b>");
				return;
	}else{
		
		$('#ts').html("<b hidden='hidden'></b>");
	  $.ajax({
	        type : "POST",  //请求方式
	        url : "/login",  //请求路径
	        data: JSON.stringify(data),
            dataType:"json",
	        async:false,
	        contentType:"application/json", //contentType很重要
	        success:function(msg) {  //异步请求成功执行的回调函数
//	            alert("成功了: " + msg);
	            window.location.href= "/home.html";
	            window.event.returnValue=false; 
	        },      
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
					if(XMLHttpRequest.status!='200')
						{
						if(XMLHttpRequest.responseJSON==undefined)
						{
								$('#ts').html("<b style='color :red'>用户名或密码错误</b>");
						}else{
						$('#ts').html("<b style='color :red'>"+XMLHttpRequest.responseJSON.message+"</b>");
						}
						}else{
							
							
							$('#ts').html("<b style='color :red'>用户名或密码错误</b>");
						}
                 
                },
	      
	    });
	  
	}
  });
});