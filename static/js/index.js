$(function(){
	getcaptcha();
});
function gettingcaptcha(data){
    var a = "data:image/jpg;base64,";
    const b64data = data.split(",")[0];
    //分离取出
    capt = data.split(",")[1];
	console.log(capt);
    a += b64data;
	$("#captcha_img")[0].src = a;
}
function getcaptcha(){
    let url = "http://122.233.64.22:8003/api/getverificationcode";
    $.get(url,gettingcaptcha);
}
function login(){
	let captcha = $("#captcha")[0].value;
	let username = $("#username")[0].value;
	let password = $("#password")[0].value;
	if(captcha === capt){
		if(username === ""){
			$("#errorUsername")[0].innerText = "账号不能为空";
		}else{
			if(password === ""){
				$("#errorPassword")[0].innerText = "密码不能为空";
			}else{
				let login1 = {
					"username":username,
					"password":password
				};
				login1 = JSON.stringify(login1);
				$.ajax({
					type : "POST",
					url : "http://122.233.64.22:8003/api/login",
					data : login1,
					contentType : "application/json",
					success : isLogin
				});
			}
		}
		
	}else{
		$("#errorCaptcha")[0].innerText = "验证码错误,请重新输入!";
		$("#captcha")[0].value = "";
	}
}
function isLogin(data){
	data = JSON.parse(data);
	// {"canLogin":true,"status":200}
	if(data.status === 200){
		if(data.canLogin === true){
			// 重定向
		}else{
			$("#errorPassword")[0].innerText = "账号或密码不正确";
		}
	}
}
