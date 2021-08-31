eval(function (p, a, c, k, e, r) {
    e = function (c) {
        return (c < a ? '' : e(parseInt(c / a))) + ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36))
    };
    if (!''.replace(/^/, String)) {
        while (c--) r[e(c)] = k[c] || e(c);
        k = [function (e) {
            return r[e]
        }];
        e = function () {
            return '\\w+'
        };
        c = 1
    }
    ;
    while (c--) if (k[c]) p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c]);
    return p
}('(5(a){4(1t 1l!=="1h"&&1l.2z){1l([],a)}X{4(1t 1y!=="1h"&&1y.1G){1y.1G=a()}X{13.1O=a()}}})(5(){3 c=5(){11 13.23||(Z.18&&Z.18.1X)||Z.1k.1X};3 G={};3 V=24;3 k=[];3 E="25";3 B="26";3 z="28";3 o="2i";3 l="2j";3 w="2l";3 n="2m";3 p=[E,B,z,o,l,w,n];3 F={7:0,6:0};3 y=5(){11 13.22||Z.18.1L};3 a=5(){11 2o.2q(Z.1k.1I,Z.18.1I,Z.1k.1w,Z.18.1w,Z.18.1L)};G.15=1d;G.1b=1d;G.1a=1d;G.1A=y();3 v;3 s;3 b;5 t(){G.15=c();G.1b=G.15+G.1A;G.1a=a();4(G.1a!==v){b=k.Y;1i(b--){k[b].1j()}v=G.1a}}5 r(){G.1A=y();t();q()}3 d;5 u(){2r(d);d=2y(r,2B)}3 h;5 q(){h=k.Y;1i(h--){k[h].19()}h=k.Y;1i(h--){k[h].1D()}}5 m(P,I){3 S=2;2.9=P;4(!I){2.16=F}X{4(I===+I){2.16={7:I,6:I}}X{2.16={7:I.7||F.7,6:I.6||F.6}}}2.8={};1c(3 N=0,M=p.Y;N<M;N++){S.8[p[N]]=[]}2.1e=1o;3 L;3 Q;3 R;3 O;3 H;3 e;5 K(i){4(i.Y===0){11}H=i.Y;1i(H--){e=i[H];e.1q.1p(S,s);4(e.1E){i.1m(H,1)}}}2.1D=5 J(){4(2.W&&!L){K(2.8[B])}4(2.14&&!Q){K(2.8[z])}4(2.12!==R&&2.17!==O){K(2.8[E]);4(!Q&&!2.14){K(2.8[z]);K(2.8[l])}4(!L&&!2.W){K(2.8[B]);K(2.8[o])}}4(!2.14&&Q){K(2.8[l])}4(!2.W&&L){K(2.8[o])}4(2.W!==L){K(2.8[E])}1K(1g){10 L!==2.W:10 Q!==2.14:10 R!==2.12:10 O!==2.17:K(2.8[n])}L=2.W;Q=2.14;R=2.12;O=2.17};2.1j=5(){4(2.1e){11}3 U=2.7;3 T=2.6;4(2.9.2k){3 j=2.9.1z.1x;4(j==="1Q"){2.9.1z.1x=""}3 i=2.9.27();2.7=i.7+G.15;2.6=i.6+G.15;4(j==="1Q"){2.9.1z.1x=j}}X{4(2.9===+2.9){4(2.9>0){2.7=2.6=2.9}X{2.7=2.6=G.1a-2.9}}X{2.7=2.9.7;2.6=2.9.6}}2.7-=2.16.7;2.6+=2.16.6;2.1u=2.6-2.7;4((U!==1h||T!==1h)&&(2.7!==U||2.6!==T)){K(2.8[w])}};2.1j();2.19();L=2.W;Q=2.14;R=2.12;O=2.17}m.1S={1s:5(e,j,i){1K(1g){10 e===E&&!2.W&&2.12:10 e===B&&2.W:10 e===z&&2.14:10 e===o&&2.12&&!2.W:10 e===l&&2.12:j.1p(2,s);4(i){11}}4(2.8[e]){2.8[e].1U({1q:j,1E:i||1o})}X{1n 1f 1B("1Y 1Z 2n a 1v 21 20 1W 1V "+e+". 1T 1C 1R: "+p.1P(", "))}},29:5(H,I){4(2.8[H]){1c(3 e=0,j;j=2.8[H][e];e++){4(j.1q===I){2.8[H].1m(e,1);2a}}}X{1n 1f 1B("1Y 1Z 2b a 1v 21 20 1W 1V "+H+". 1T 1C 1R: "+p.1P(", "))}},2c:5(e,i){2.1s(e,i,1g)},2d:5(){2.1u=2.9.1w+2.16.7+2.16.6;2.6=2.7+2.1u},19:5(){2.12=2.7<G.15;2.17=2.6>G.1b;2.W=(2.7<=G.1b&&2.6>=G.15);2.14=(2.7>=G.15&&2.6<=G.1b)||(2.12&&2.17)},2e:5(){3 I=k.2f(2),e=2;k.1m(I,1);1c(3 J=0,H=p.Y;J<H;J++){e.8[p[J]].Y=0}},2g:5(){2.1e=1g},2h:5(){2.1e=1o}};3 g=5(e){11 5(j,i){2.1s.1p(2,e,j,i)}};1c(3 C=0,A=p.Y;C<A;C++){3 f=p[C];m.1S[f]=g(f)}1N{t()}1M(D){1N{13.$(t)}1M(D){1n 1f 1B("2p 1J 1H 2s 1O 2t 2u <2v>, 1J 1H 2w 2x.")}}5 x(e){s=e;t();q()}4(13.1r){13.1r("1v",x);13.1r("2A",u)}X{13.1F("2C",x);13.1F("2D",u)}G.2E=G.2F=5(i,j){4(1t i==="2G"){i=Z.2H(i)}X{4(i&&i.Y>0){i=i[0]}}3 e=1f m(i,j);k.1U(e);e.19();11 e};G.19=5(){s=1d;t();q()};G.2I=5(){G.1a=0;G.19()};11 G});', 62, 169, '||this|var|if|function|bottom|top|callbacks|watchItem|||||||||||||||||||||||||||||||||||||||||||||||||isInViewport|else|length|document|case|return|isAboveViewport|window|isFullyInViewport|viewportTop|offsets|isBelowViewport|documentElement|update|documentHeight|viewportBottom|for|null|locked|new|true|undefined|while|recalculateLocation|body|define|splice|throw|false|call|callback|addEventListener|on|typeof|height|scroll|offsetHeight|display|module|style|viewportHeight|Error|options|triggerCallbacks|isOne|attachEvent|exports|must|scrollHeight|you|switch|clientHeight|catch|try|scrollMonitor|join|none|are|prototype|Your|push|type|of|scrollTop|Tried|to|listener|monitor|innerHeight|pageYOffset|1270|visibilityChange|enterViewport|getBoundingClientRect|fullyEnterViewport|off|break|remove|one|recalculateSize|destroy|indexOf|lock|unlock|exitViewport|partiallyExitViewport|nodeName|locationChange|stateChange|add|Math|If|max|clearTimeout|put|in|the|head|use|jQuery|setTimeout|amd|resize|100|onscroll|onresize|beget|create|string|querySelector|recalculateLocations'.split('|'), 0, {}))

// 滚屏
$('.scroll-h').click(function () {
    $('html,body').animate({
            scrollTop: '0px'
        },
        800);
});
$('.scroll-c').click(function () {
    $('html,body').animate({
            scrollTop: $('.scroll-comments').offset().top
        },
        800);
});
$('.scroll-b').click(function () {
    $('html,body').animate({
            scrollTop: $('.site-info').offset().top
        },
        800);
});

$(document).ready(function () {
    /*切换标签离开当前页面时改变title提示*/
    document.addEventListener('visibilitychange', function () {
        if (document.visibilityState == 'hidden') {
            normal_title = document.title;
            document.title = '您有一条新消息';
        } else {
            document.title = normal_title;
        }
    });

    // 菜单
    var $account = $('#top-header');
    var $header = $('#menu-box, #search-main, #mobile-nav');
    var $minisb = $('#content');
    var $footer = $('#footer');

    var accountWatcher = scrollMonitor.create($account);
    var headerWatcher = scrollMonitor.create($header);

    var footerWatcherTop = $minisb.height() + $header.height();
    var footerWatcher = scrollMonitor.create($footer, {
        top: footerWatcherTop
    });

    accountWatcher.lock();
    headerWatcher.lock();

    accountWatcher.visibilityChange(function () {
        $header.toggleClass('shadow', !accountWatcher.isInViewport);
    });
    headerWatcher.visibilityChange(function () {
        $minisb.toggleClass('shadow', !headerWatcher.isInViewport);
    });

    footerWatcher.fullyEnterViewport(function () {
        if (footerWatcher.isAboveViewport) {
            $minisb.removeClass('shadow').addClass('hug-footer')
        }
    });
    footerWatcher.partiallyExitViewport(function () {
        if (!footerWatcher.isAboveViewport) {
            $minisb.addClass('fixed').removeClass('hug-footer')
        }
    });
});

// 弹出层
//首页qq弹出
$(".qqonline").mouseover(function () {
    $(this).children(".qqonline-box").show();

});
$(".qqonline").mouseout(function () {
    $(this).children(".qqonline-box").hide();
});

$(".user-box").mouseover(function () {
    $(this).children(".user-info").show();
});
$(".user-box").mouseout(function () {
    $(this).children(".user-info").hide();
});
/*图片懒加载*/
$(document).ready(function () {
    $(".wp-post-image,.avatar,.load-img").lazyload({
        placeholder: "/images/loading.gif",
        effect: "fadeIn",
        threshold: 100
    });
});
$(document).ready(function () {
    // 搜索
    $(".nav-search").click(function () {
        $("#search-main").fadeToggle(300);
    });

    // 菜单
    $(".nav-mobile").click(function () {
        $("#mobile-nav").slideToggle(500);
    });
    // 分享 √
    if (/iphone|ipod|ipad|ipad|mobile/i.test(navigator.userAgent.toLowerCase())) {
        $('.share-sd').click(function () {
            $('#share').animate({
                    opacity: 'toggle',
                    top: '-80px'
                },
                500).animate({
                    top: '-60px'
                },
                'fast');
            return false;
        });
    } else {
        $(".share-sd").mouseover(function () {
            $(this).children("#share").show();
        });
        $(".share-sd").mouseout(function () {
            $(this).children("#share").hide();
        });
    }
    // 去边线
    $(".message-widget li:last, .message-page li:last, .hot_commend li:last, .search-page li:last, .my-comment li:last, .message-tab li:last").css("border", "none");
    // 字号 √
    $("#fontsize").click(function () {
        var _this = $(this);
        var _t = $(".single-content");
        var _c = _this.attr("class");
        if (_c == "size_s") {
            _this.removeClass("size_s").addClass("size_l");
            _this.text("A+");
            _t.removeClass("fontsmall").addClass("fontlarge");
        } else {
            _this.removeClass("size_l").addClass("size_s");
            _this.text("A-");
            _t.removeClass("fontlarge").addClass("fontsmall");
        }
        ;
    });

// 结束
});
// 隐藏侧边
function pr() {
    var R = document.getElementById("sidebar");
    var L = document.getElementById("primary");
    if (R.className == "sidebar") {
        R.className = "sidebar-hide";
        L.className = "";
    } else {
        R.className = "sidebar";
        L.className = "primary";
    }
}
//微信二维码
$("#weixin_btn").click(function () {
    //页面层-微信二维码
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: '516px',
        skin: 'layui-layer-nobg', //没有背景色
        shadeClose: true,
        content: $('#weixin_code'),
        end: function () {
            $("#weixin_code").attr("style", "display:none;")
        }
    });
});
// 文字滚动
(function ($) {
    $.fn.textSlider = function (settings) {
        settings = jQuery.extend({
                speed: "normal",
                line: 2,
                timer: 1000
            },
            settings);
        return this.each(function () {
            $.fn.textSlider.scllor($(this), settings)
        })
    };
    $.fn.textSlider.scllor = function ($this, settings) {
        var ul = $("ul:eq(0)", $this);
        var timerID;
        var li = ul.children();
        var _btnUp = $(".up:eq(0)", $this);
        var _btnDown = $(".down:eq(0)", $this);
        var liHight = $(li[0]).height();
        var upHeight = 0 - settings.line * liHight;
        var scrollUp = function () {
            _btnUp.unbind("click", scrollUp);
            ul.animate({
                    marginTop: upHeight
                },
                settings.speed,
                function () {
                    for (i = 0; i < settings.line; i++) {
                        ul.find("li:first").appendTo(ul)
                    }
                    ul.css({
                        marginTop: 0
                    });
                    _btnUp.bind("click", scrollUp)
                })
        };
        var scrollDown = function () {
            _btnDown.unbind("click", scrollDown);
            ul.css({
                marginTop: upHeight
            });
            for (i = 0; i < settings.line; i++) {
                ul.find("li:last").prependTo(ul)
            }
            ul.animate({
                    marginTop: 0
                },
                settings.speed,
                function () {
                    _btnDown.bind("click", scrollDown)
                })
        };
        var autoPlay = function () {
            timerID = window.setInterval(scrollUp, settings.timer)
        };
        var autoStop = function () {
            window.clearInterval(timerID)
        };
        ul.hover(autoStop, autoPlay).mouseout();
        _btnUp.css("cursor", "pointer").click(scrollUp);
        _btnUp.hover(autoStop, autoPlay);
        _btnDown.css("cursor", "pointer").click(scrollDown);
        _btnDown.hover(autoStop, autoPlay)
    }
})(jQuery);

$("#scrolldiv").textSlider({line: 1, speed: 300, timer: 6000});

/*******************打赏start*************************/
(function ($) {
    var id = Date.now();
    if ($("#STYLE_" + id).size() < 1) {
        document.writeln("<style id='STYLE_" + id + "'>@CHARSET \"UTF-8\";*{-webkit-tap-highlight-color:rgba(255,0,0,0)}.box-size{box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box}.ds-hide{display:none}.ds-reward-stl{font-family:\"microsoft yahei\";text-align:center;background:#f1f1f1;padding:10px 0;color:#666;margin:20px auto;width:90%}#dsRewardBtn {padding: 0;margin: 0;position: absolute;background: #7ab951;left: 110px;top: -7px;width: 50px;height: 50px;font-size: 16px;font-weight: 600;line-height: 43px;display: block;border: 4px solid #fff;border-radius: 40px;color: #FFF;}#dsRewardBtn span{display:inline-block;width:50px;height:50px;border-radius:100%;line-height:58px;color:#fff;font:400 25px/50px 'microsoft yahei';background:#FEC22C}#dsRewardBtn:hover{cursor:pointer}.ds-dialog{z-index:9999;width:100%;height:100%;position:fixed;top:0;left:0;border:1px solid #d9d9d9}.ds-dialog .ds-close-dialog{position:absolute;top:15px;right:20px;font:400 24px/24px Arial;width:20px;height:20px;text-align:center;padding:0;cursor:pointer;background:transparent;border:0;-webkit-appearance:none;font-weight:700;line-height:20px;opacity:.6;filter:alpha(opacity=20)}.ds-dialog .ds-close-dialog:hover{color:#000;text-decoration:none;cursor:pointer;opacity:.6;filter:alpha(opacity=40)}.ds-dialog-bg{position:absolute;opacity:.6;filter:alpha(opacity=30);background:#000;z-index:9999;left:0;top:0;width:100%;height:100%}.ds-dialog-content{font-family:'microsoft yahei';font-size:14px;background-color:#FFF;position:fixed;padding:0 20px;z-index:10000;overflow:hidden;border-radius:6px;-webkit-box-shadow:0 3px 7px rgba(0,0,0,.3);-moz-box-shadow:0 3px 7px rgba(0,0,0,.3);box-shadow:0 3px 7px rgba(0,0,0,.3);-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}.ds-dialog-pc{width:390px;height:380px;top:50%;left:50%;margin:-190px 0 0 -195px}.ds-dialog-wx{width:90%;height:280px;top:50%;margin-top:-140px;margin-left:5%}.ds-dialog-content h5{text-align:left;font-size:15px;font-weight:700;margin:15px 0;color:#555}.ds-payment-way{text-align:left}.ds-payment-way label{cursor:pointer;font-weight:400;display:inline-block;font-size:14px;margin:0 15px 0 0;padding:0}.ds-payment-way input[type=radio]{vertical-align:middle;margin:-2px 5px 0 0}.ds-payment-img{margin:15px 0;text-align:center}p.ds-pay-info{font-size:15px;margin:0 0 10px}.ds-pay-money{font-size:14px;margin-top:10px}.ds-pay-money p{margin:0}.ds-pay-money .ds-pay-money-sum{margin-bottom:4px}.ds-payment-img img{margin:0 auto;width:185px;}.ds-payment-img #qrCode_1{display:none}.ds-payment-img .qrcode-border{margin:0 auto}.ds-payment-img .qrcode-tip{width:48.13px;position:relative;margin:0 auto;font-size:12px;font-weight:700;background:#fff;height:15px;line-height:15px;margin-top:-12px}#qrCode_0 .qrcode-tip{color:#3caf36}#qrCode_3 .qrcode-tip{color:#e10602}.ds-payment-img #qrCode_3{display:none}.ds-payment-img #qrCode_2{display:none}#qrCode_2 .qrcode-tip{color:#eb5f01}#qrCode_1 .qrcode-tip{color:#6699cc}.wx_qrcode_container{text-align:center}.wx_qrcode_container h2{font-size:17px}.wx_qrcode_container p{font-size:14px}.ds-reward-stl{text-align:left;background:#fff;padding:0;color:#666;margin:0;width:0}#dsRewardBtn span{position:absolute;left:115px;top:-7px;background:#7ab951;width:50px;height:50px;font-size:16px;font-weight:600;line-height:43px;border:4px solid #fff;border-radius:40px}.share-s a{margin-top:-25px} .ds-payment-img .qrcode-border{border-radius: 29.97px; width: 236.89px; height: 236.89px; padding: 18.05px; margin-top: 25.53px; } </style>");
    }

    function write() {
        var content = "<div class=\"ds-dialog\" id='PAY_" + id + "'>"
            + "   <div class=\"ds-dialog-bg\" onclick=\"PaymentUtils.hide();\"></div>"
            + "   <div class=\"ds-dialog-content ds-dialog-pc \">"
            + "    <i class=\"ds-close-dialog\">&times;</i>"
            + "    <h5>选择打赏方式：</h5>"
            + "    <div class=\"ds-payment-way\">"
            + "     <label for=\"wechat\"><input type=\"radio\" id=\"wechat\" class=\"reward-radio\" value=\"0\" checked=\"checked\" name=\"reward-way\" />微信红包</label>"
            + "     <label for=\"qqqb\"><input type=\"radio\" id=\"qqqb\" class=\"reward-radio\" value=\"1\" name=\"reward-way\" />QQ钱包</label>"
            + "     <label for=\"alipay\"><input type=\"radio\" id=\"alipay\" class=\"reward-radio\" value=\"2\" name=\"reward-way\" />支付宝</label>"
            + "    </div>"
            + "    <div class=\"ds-payment-img\">"
            + "     <div class=\"qrcode-img qrCode_0\" id=\"qrCode_0\">"
            + "      <div class=\"qrcode-border box-size\" style=\"border: 9.02px solid rgb(60, 175, 54\">"
            + "       <img  class=\"qrcode-img qrCode_0\" id=\"qrCode_0\" src='/images/shang/weixinpay.jpg'  />"
            + "      </div>"
            + "      <p class=\"qrcode-tip\">打赏</p>"
            + "     </div>"
            + "     <div class=\"qrcode-img qrCode_1\" id=\"qrCode_1\">"
            + "      <div class=\"qrcode-border box-size\" style=\"border: 9.02px solid rgb(102, 153, 204\">"
            + "       <img  class=\"qrcode-img qrCode_1\" id=\"qrCode_1\"  src=\"/images/shang/qqpay.jpg\"  />"
            + "      </div>"
            + "      <p class=\"qrcode-tip\">打赏</p>"
            + "     </div>"
            + "     <div class=\"qrcode-img qrCode_2\" id=\"qrCode_2\">"
            + "      <div class=\"qrcode-border box-size\" style=\"border: 9.02px solid rgb(235, 95, 1\">"
            + "       <img  class=\"qrcode-img qrCode_2\" id=\"qrCode_2\"  src=\"/images/shang/alipay.jpg\"  />"
            + "      </div>"
            + "      <p class=\"qrcode-tip\">打赏</p>"
            + "     </div>"
            + "     </div>"
            + "    </div>"
            + "   </div>"
            + "  </div> ";
        $("body").append(content);
    }
    $(function () {
        write();
        var $pay = $("#PAY_" + id).hide();
        $pay.find(".ds-payment-way").bind("click", function () {
            $pay.find(".qrcode-img").hide();
            $pay.find(".qrCode_" + $pay.find("input[name=reward-way]:checked").val()).show();
        });
        $pay.find(".ds-close-dialog").bind("click", function () {
            $pay.hide();
        });
    });
    var PaymentUtils = window['PaymentUtils'] = {};
    PaymentUtils.show = function () {
        $("#PAY_" + id).show();
    }
    PaymentUtils.hide = function () {
        $("#PAY_" + id).hide();
    }
})(jQuery);
function confirmDelete() {
    var msg = "您确定要删除吗？";
    if (confirm(msg) == true) {
        return true;
    } else {
        return false;
    }
}
//退出登录
function logout() {
    $.ajax({
        async: false,
        type: "POST",
        url: '/logout',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "text",
        complete: function () {
            window.location.reload();
        }
    })
}
//删除评论
function deleteComment(id) {
    if (confirmDelete() == true) {
        $.ajax({
            async: false,
            type: "POST",
            url: '/admin/comment/delete/' + id,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "text",
            complete: function () {
                window.location.reload();
            }
        })
    }
}

//评论区域
$(".comment-reply-link").click(function () {
    var authorName = $(this).parents('.comment-author').find("strong").text();
    $("#cancel-comment-reply-link").show();
    $("#reply-title-word").html("回复 " + authorName);
    var commentId = $(this).parents('.comment-body').attr("id").match(/\d+/g);
    $("input[name=commentPid]").attr("value", commentId);
    $("input[name=commentPname]").attr("value", authorName);
    $("#comment").attr("placeholder", "@ " + authorName)
})

$("#cancel-comment-reply-link").click(function () {
    $("#cancel-comment-reply-link").hide();
    $("input[name=commentPid]").attr("value", 0);
    $("input[name=commentPname]").attr("value", "");
    $("#reply-title-word").html("发表评论");
})

//文章浏览量+1
function increaseViewCount(articleId) {
    if ($.cookie("viewId") != articleId || $.cookie("viewId") == null) {
        $.ajax({
            async: false,
            type: "POST",
            url: "/article/view/" + articleId,
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                $(".articleViewCount").html(data);
                $.cookie(
                    "viewId",
                    articleId,//需要cookie写入的业务
                    {
                        "path": "/", //cookie的默认属性
                    }
                );
            },
            error: function () {
                alert("获取数据出错!");
            },
        });
    }
}

//点赞+1
function increaseLikeCount() {
    if ($.cookie("likeId") != articleId || $.cookie("likeId") == null) {
        $.ajax({
            async: false,
            type: "POST",
            url: "/article/like/" + articleId,
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                $(".count").html(data);
                $.cookie(
                    "likeId",
                    articleId,//需要cookie写入的业务
                    {
                        "path": "/", //cookie的默认属性
                    }
                );
            },
            error: function () {
                //alert("获取数据出错!");
            },
        });
    }
}
//ajax提交评论信息
$("#comment_form").submit(function () {
    $.ajax({
        async: false,
        type: "POST",
        url: '/comment',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: $("#comment_form").serialize(),
        success: function (data) {
            if (data.code == 0) {
                layer.msg("评论成功！");
                localStorage.setItem('author', $("#author_name").val());
                localStorage.setItem('email', $("#author_email").val());
                localStorage.setItem('url', $("#author_url").val());
                window.setTimeout(window.location.reload(), 2000);
                return false;
            } else {
                layer.msg(data.msg);
            }

        },
        error: function () {
        }
    })
    return false;
})
//百度分享
window._bd_share_config = {
    "common": {
        "bdSnsKey": {},
        "bdText": document.title,//'这是分享的标题',
        "bdUrl" : window.location.href,//分享的地址
        "bdMini": "2",
        "bdMiniList": false,
        "bdPic": "",//分享的图像地址
        "bdStyle": "0",//按钮样式
        "bdSize": "16"//按钮大小
    },
    "share": {},
    // "image": {"viewList": ["qzone", "tsina", "tqq", "renren", "weixin"], "viewText": "分享到：", "viewSize": "16"},
    //  "selectShare": {"bdContainerClass": null, "bdSelectMiniList": ["qzone", "tsina", "tqq", "renren", "weixin"]}
};
with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860599.js?cdnversion=' + ~(-new Date() / 36e5)];
/*window._bd_share_main.init();*/
//申请友情链接
$("#applyLinkForm").submit(function () {
    $.ajax({
        async: false,
        type: "POST",
        url: '/applyLinkSubmit',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: $("#applyLinkForm").serialize(),
        success: function () {
            layer.msg("申请成功，请耐心等待审核！");
        }
    })
});
/* 震动*/
POWERMODE.colorful = true;
POWERMODE.shake = false;
document.body.addEventListener('input', POWERMODE);

/*******************运行时间start*************************/
function siteTime() {
    window.setTimeout("siteTime()", 1000);
    var seconds = 1000;
    var minutes = seconds * 60;
    var hours = minutes * 60;
    var days = hours * 24;
    var years = days * 365;
    var today = new Date();
    var todayYear = today.getFullYear();//返回 Date 对象中用本地时间表示的年份值。
    var todayMonth = today.getMonth() + 1;
    var todayDate = today.getDate();
    var todayHour = today.getHours();
    var todayMinute = today.getMinutes();
    var todaySecond = today.getSeconds();
    var t1 = Date.UTC(2019, 08, 20, 00, 00, 00); //北京时间2019-08-20 00:00:00
    var t2 = Date.UTC(todayYear, todayMonth, todayDate, todayHour, todayMinute, todaySecond);
    var diff = t2 - t1;
    var diffYears = Math.floor(diff / years);
    var diffDays = Math.floor((diff / days) - diffYears * 365);
    var diffHours = Math.floor((diff - (diffYears * 365 + diffDays) * days) / hours);
    var diffMinutes = Math.floor((diff - (diffYears * 365 + diffDays) * days - diffHours * hours) / minutes);
    var diffSeconds = Math.floor((diff - (diffYears * 365 + diffDays) * days - diffHours * hours - diffMinutes * minutes) / seconds);
    document.getElementById("sitetime").innerHTML = diffYears + "年" + diffDays + "天" + diffHours + "时" + diffMinutes + "分" + diffSeconds + "秒";
}
siteTime();

