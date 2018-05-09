
$(document).ready(function(){
	
	
  $("#addEmployee").click(function(){
	var  employeeMember= $("#member").val();
	var  employeeName= $("#name").val();
	var  employeeGender= $("#gender").val();
	var  employeeBirthday= $("#birthTime").val();
	var  employeeBirthPlace= $("#birthPlace").val();
	var  employeeJob= $("#job").val();
	var  employeeEducation= $("#education").val();
	var  employeeEntryDate= $("#entryTime").val();
	var  employeeIdcard= $("#idcard").val();
	var  employeeAddress= $("#address").val();
	var  employeeContact= $("#contact").val();
	var  employeeDepartment= $("#department").val();
	var  employeePost= $("#post").val();
	var  employeeRemark= $("#remark").val();

	var data={  //请求参数
			employeeMember : employeeMember,
			employeeName: employeeName,
			employeeGender: employeeGender,
			employeeBirthday:employeeBirthday,
			employeeBirthPlace: employeeBirthPlace,
	        employeeJob: employeeJob,
	        employeeEducation:employeeEducation,
	        employeeEntryDate:employeeEntryDate,
	        employeeIdcard:employeeIdcard,
	        employeeAddress:employeeAddress,
	        employeeContact:employeeContact,
	        employeeDepartment:employeeDepartment,
	        employeePost:employeePost,
	        employeeRemark:employeeRemark
        }
	
	  $.ajax({
	        type : "POST",  //请求方式
	        url : "/m/employee",  //请求路径
	        data: JSON.stringify(data),
            dataType:"json",
	        async:false,
	        contentType:"application/json", //contentType很重要
	        success:function(msg) {  //异步请求成功执行的回调函数
           alert("保存成功了");
          $('#myModal').modal('hide');
//	            window.location.href= "/home.html";
//	            window.event.returnValue=false; 
	        },      
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
				
	        }
	    });
	  
  });
  
  function createURL(url, param) {
		var myURL = {
			link: ''
		};
		for(var key in param) {
			var link = '&' + key + "=" + param[key];
			myURL.link += link;
		}
		myURL.link = url + "?" + myURL.link.substr(1);
		return myURL.link.replace(' ', '');
	}
  
  function query(array) {
		var queryMap = {};
		if(array[0]!=null&&array[0]!=""){
			queryMap.s_job=array[0];
		}
			queryMap.s_member=array[1];
		
			queryMap.s_idcard=array[2];
		
		return queryMap;
  }
  
  
  $("#export").click(function(){
	    var  job= $("#searchjob").val();
		var  member= $("#searchmember").val();
		var idcard= $("#searchidcard").val();  
		
		var searchArray=new Array();
		searchArray[0]=job;
		searchArray[1]=member;
		searchArray[2]=idcard;
		
		var url = "/m/employee/excel/download.do";
		
	    var mapquery=query(searchArray);
	    
		var link=createURL(url, mapquery);
		
		  window.location.href= link;
	  
  });
  
$("#search").click(function(){
	  
	  
  });
  
  
});