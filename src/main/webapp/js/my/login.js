$(document).ready(function () {
    /*web存储*/
    /*页面打开，判断是否存储过用户名---*/
    var loginName = localStorage.getItem("loginName");
     if(loginName != null) {//说明之前存储过值，说明用户希望记住用户名
         //将值赋给用户名输入框
         $("#login-name").val(localStorage.getItem("loginName"));
     } else {
         document.getElementById('login-name').focus();
     }
});

function btn_login() {
    //referrer属性简单来说就是上一个页面的URL
    var prevLink = document.referrer;
    /*判断用户是否选择记住用户名*/
    var rememberme=document.getElementById('rememberme');
    $('#btn-login').button('loading');
    var name = $("#login-name").val();
    var pwd = $("#login-pwd").val();
    if (name == "" || pwd == "") {
        showMsg("请输入完整信息！", "info", 2000);
        $('#btn-login').button('reset');
    } else {
        $.ajax({
            type: 'POST',
            url: '/admin/getLogin',
            async: false,
            dataType: "json",
            data: {
                'username': name,
                'password': pwd
            },
            success: function (data) {
                if(rememberme.checked==true){
                    /*存储数据到localstorage*/
                    localStorage.setItem('loginName', $("#login-name").val());
                }
                if (data.code == 1) {
                    $.toast({
                        text: data.msg,
                        heading: '成功',
                        icon: 'success',
                        showHideTransition: 'fade',
                        allowToastClose: true,
                        hideAfter: 1500,
                        stack: 1,
                        position: 'top-center',
                        textAlign: 'left',
                        loader: true,
                        loaderBg: '#ffffff',
                        afterHidden: function () {
                            if ($.trim(prevLink) == '') {
                                window.location.href = '/admin';
                            } else {
                                window.location.href = prevLink;
                            }
                        }
                    });
                } else {
                    $('.login-body').addClass('animate shake');
                    $.toast({
                        text: data.msg,
                        heading: '错误',
                        icon: 'error',
                        showHideTransition: 'fade',
                        allowToastClose: true,
                        hideAfter: 2000,
                        stack: 1,
                        position: 'top-center',
                        textAlign: 'left',
                        loader: true,
                        loaderBg: '#ffffff',
                        afterHidden: function () {
                            $('.login-body').removeClass('animate shake');
                        }
                    });
                    $('#btn-login').button('reset');
                }
            }
        });
    }
}


function btn_register() {
    $('#btn-register').button('loading');
    var userName = $("#userName").val();
    var userPass = $("#userPass").val();
    var userEmail = $("#userEmail").val();
    if (userName == "" || userPass == "" || userEmail == "") {
        showMsg("请输入完整信息！", "info", 2000);
        $('#btn-register').button('reset');
    } else {
        $.ajax({
            type: 'POST',
            url: '/admin/getRegister',
            async: false,
            data: {
                'userName': userName,
                'userPass': userPass,
                'userEmail': userEmail
            },
            success: function (data) {
                if (data.code == 1) {
                    showMsgAndRedirect(data.msg, "success", 2000, "/admin/login");
                } else {
                    showMsg(data.msg, "error", 2000);
                    $('#btn-register').button('reset');
                    localStorage.setItem('loginName', userName);
                }
            }
        });
    }
}


function btn_forget() {
    $('#btn-forget').button('loading');
    var userName = $("#userName").val();
    var userEmail = $("#userEmail").val();
    if (userName == "" || userEmail == "") {
        showMsg("请输入完整信息！", "info", 2000);
        $('#btn-forget').button('reset');
    } else {
        $.ajax({
            type: 'POST',
            url: '/admin/getForget',
            async: false,
            data: {
                'userName': userName,
                'userEmail': userEmail
            },
            success: function (data) {
                if (data.code == 1) {
                    showMsgAndRedirect(data.msg, "success", 2000, "/admin/login");
                } else {
                    showMsg(data.msg, "error", 2000);
                    $('#btn-forget').button('reset');
                }
            }
        });
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        btn_login();
    }
});

//login
$('.toggle').click(function(){
    $(this).children('i').toggleClass('icon-user-lock');
    $('.form').animate({
        height: "toggle",
        'padding-top': 'toggle',
        'padding-bottom': 'toggle',
        opacity: "toggle"
    }, "slow");
});
