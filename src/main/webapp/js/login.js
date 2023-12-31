function GetLogupFromValue(e) {
	var get = document.getElementsByClassName("login");
	//console.log(get);
	//console.log((get[0])[0].value);
	//var LocalStorage_loginName = localStorage.getItem("name");
	//console.log(LocalStorage_loginName);
	//var LocalStorage_loginPassword = localStorage.getItem("password");
	//console.log(LocalStorage_loginPassword);
	if ((get[0])[0].value == "") { //用户名为空
		document.getElementsByClassName("LogupName_error")[0].style.display = "block";
	}
	if ((get[0])[0].value != "") { //用户名为空取消
		document.getElementsByClassName("LogupName_error")[0].style.display = "none";
	}
	if ((get[0])[1].value == "") { //密码为空
		document.getElementsByClassName("LogupPassword_error")[0].style.display = "block";
	}
	if ((get[0])[1].value != "") { //密码为空取消
		document.getElementsByClassName("LogupPassword_error")[0].style.display = "none";
	}
	if ((get[0])[0].value == LocalStorage_loginName && (get[0])[1].value != LocalStorage_loginPassword) { //密码错误
		document.getElementsByClassName("LogupPassword_error")[1].style.display = "block";
	}
	if ((get[0])[0].value != LocalStorage_loginName) {
		document.getElementsByClassName("LogupName_error")[2].style.display = "block";
	}
	if ((get[0])[0].value != LocalStorage_loginName && (get[0])[1].value != LocalStorage_loginPassword) { //未注册
		document.getElementsByClassName("LogupName_error")[1].style.display = "block";
	}
	if ((get[0])[0].value == LocalStorage_loginName && (get[0])[1].value == LocalStorage_loginPassword) { //验证成功
		//window.location.href = "../index.html?id=" + LocalStorage_loginName + "&password=" + LocalStorage_loginPassword;
	}
}

function SetLoginFromValue() {
	var set = document.getElementsByClassName("sign");
	if ((set[0])[1].value != (set[0])[2].value) { //密码不一致
		document.getElementsByClassName("confirm_info")[0].style.display = "block";
	}
	if ((set[0])[1].value == (set[0])[2].value) { //密码不一致取消
		document.getElementsByClassName("confirm_info")[0].style.display = "none";
	}
	if ((set[0])[0].value == "") { //用户名为空
		document.getElementsByClassName("loginName_error")[0].style.display = "block";
	}
	if ((set[0])[0].value != "") { //用户名不一致取消
		document.getElementsByClassName("loginName_error")[0].style.display = "none";
	}
	if ((set[0])[1].value == "") { //密码为空
		document.getElementsByClassName("loginPassword_error")[0].style.display = "block";
	}
	if ((set[0])[1].value != "") { //密码为空取消
		document.getElementsByClassName("loginPassword_error")[0].style.display = "none";
	}
	if ((set[0])[2].value == "") { //确认密码为空
		document.getElementsByClassName("loginPasswordConfirm_error")[0].style.display = "block";
	}
	if ((set[0])[2].value != "") { //确认密码为空取消
		document.getElementsByClassName("loginPasswordConfirm_error")[0].style.display = "none";
	}
	if ((set[0])[1].value == (set[0])[2].value && (set[0])[1].value != "" && (set[0])[2].value != "") {
		document.getElementsByClassName("location_choice")[0].style.visibility = "visible";
		document.getElementsByClassName("loginPasswordConfirm_error")[0].style.display = "none";
		document.getElementsByClassName("loginPassword_error")[0].style.display = "none";
		document.getElementsByClassName("loginName_error")[0].style.display = "none";
		document.getElementsByClassName("confirm_info")[0].style.display = "none";
		if (document.getElementById("show_location").value != "") {
			/*localStorage.setItem("name", (set[0])[0].value);
			localStorage.setItem("password", (set[0])[1].value);*/
			//location.href = "login.html";
		}

	}
}
