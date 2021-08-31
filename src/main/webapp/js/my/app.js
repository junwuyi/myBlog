//批量删除文章
function batchDeletes() {
    //判断至少选择了一项
    var checkedNum = $("input[name='ids']:checked").length;
    if (checkedNum == 0) {
        showMsg("请至少选择一项", "info", 2000);
        return false;
    } else {
        $.alert({
            title: '确认？',
            type: 'dark',
            autoClose: 'cancel|5000',
            content: '<h4>确定<font color="red">删除</font>所选项目?</h4>',
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function () {
                        var checkedList = new Array();
                        $("input[name='ids']:checked").each(function () {
                            checkedList.push($(this).val());
                        });
                        $.ajax({
                            type: "POST",
                            url: '/admin/article/deleteBatch',
                            data: {
                                "delitems": checkedList.toString()
                            },
                            success: function (data) {
                                if (data.code == 1) {
                                    showMsgAndRedirect(data.msg, "success", 1000, "/admin/article/page/1")
                                } else {
                                    showMsg(data.msg, "error", 1000);
                                }
                            }
                        })
                    }
                },
                cancel: {
                    text: '取消',
                    action: function () {
                        //$.alert('你点击了取消!');
                    }
                }
            }
        });
    }
}


/**
 * 提示框
 * @param text
 * @param icon
 * @param hideAfter
 */
function showMsg(text, icon, hideAfter) {
    if (heading == undefined) {
        var heading = "提示";
    }
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff'
    });
}

function showMsgAndReload(text, icon, hideAfter) {
    if (heading == undefined) {
        var heading = "提示";
    }
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff',
        afterHidden: function () {
            window.location.reload();
        }
    });
}

function showMsgAndRedirect(text, icon, hideAfter, redirectUrl) {
    if (heading == undefined) {
        var heading = "提示";
    }
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff',
        afterHidden: function () {
            window.location.href = redirectUrl;
        }
    });
}
