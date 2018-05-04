
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
           alert("成功了: " + msg);
          $('#myModal').modal('hide');
//	            window.location.href= "/home.html";
//	            window.event.returnValue=false; 
	        },      
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
				
	        }
	    });
	  
  });
});