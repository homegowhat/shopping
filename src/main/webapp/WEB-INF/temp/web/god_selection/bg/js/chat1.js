var url = "ws://" + window.location.host + "/wbs_eeeee";
var socket = null;
$(document).ready(function() {
    socket = new WebSocket(url);
   // alert(url);
    socket.onmessage = handleMsg
});
Number.prototype.padLeft = function(c, b) {
    var a = (String(c || 10).length - String(this).length) + 1;
    return a > 0 ? new Array(a).join(b || "0") + this : this
};

function xxx() {
    var a = document.getElementById("messages");
    a.scrollTop = a.scrollHeight;
    alert("f")
}

function handleMsg(c) {
    var g = JSON.parse(c.data);
    if (g.act == 9) {
        var a = $("#OrderTeamId").val();
        var h = $("#txtWbs").val();
        var e = new Msg(0, a, h, "");
        socket.send(JSON.stringify(e))
    } else {
        if (g.act == 0) {
            var k = $("#messages");
            k.html(k.html() + getSystemMsgDiv(g.userFullName + "進入房間..."));
            k.scrollTop(k[0].scrollHeight);
            var o = "/bootstrap/images/user/" + g.username;
            if (!IsFileExist(o)) {
                o = "/bootstrap/images/user/0"
            }
            var m = $("#members");
            m.html(m.html() + getMemberDiv(null, g.userFullName, o));
            m.scrollTop(k[0].scrollHeight)
        } else {
            if (g.act == 1 || g.act == 4) {
                var i = g.msg.split("_@_");
                if (i[0] == "3") {
                	alert(i[1]+"     "+i[2]);
                	gotoPage( i[2], i[1]);
                } else {
                    if (i[0] == "2") {
                        gotoSlidePage();
                    }
                }
                var k = $("#messages");
                var o = "/bootstrap/images/user/" + g.username;
                if (!IsFileExist(o)) {
                    o = "/bootstrap/images/user/0"
                }
                var l = new Date,
                    p = [l.getFullYear(), (l.getMonth() + 1).padLeft(), l.getDate().padLeft()].join("/") + " " + [l.getHours().padLeft(), l.getMinutes().padLeft(), l.getSeconds().padLeft()].join(":");
                var f = ($("#txtWbs").val() == g.num);
                var j = g.act == 1 ? getMsgDiv(g, o, p, f) : getImgDiv(g, o, p, f);
                var n = k[0].scrollHeight;
                k.html(k.html() + j);
                k.animate({
                    scrollTop: n
                }, 1)
            } else {
                if (g.act == 2) {
                    var k = $("#messages");
                    k.html(k.html() + getSystemMsgDiv(g.userFullName + "離開房間..."));
                    k.scrollTop(k[0].scrollHeight);
                    $("#" + g.username).remove()
                } else {
                    if (g.act == 3) {
                        var j = "";
                        for (var b in g.memberList) {
                            var o = "/bootstrap/images/user/" + g.memberList[b].username;
                            if (!IsFileExist(o)) {
                                o = "/bootstrap/images/user/0"
                            }
                            j += getMemberDiv(g.memberList[b].username, g.memberList[b].name, o)
                        }
                        var m = $("#members");
                        m.html(m.html() + j);
                        m.scrollTop(m[0].scrollHeight)
                    } else {
                        if (g.act == 5) {
                            var k = $("#messages");
                            k.html(k.html() + getSystemMsgDiv(g.msg));
                            k.scrollTop(k[0].scrollHeight)
                        } else {
                            if (g.act == 11) {
                            	gotoPage(g.msg)
                            } else {
                                if (g.act == 12) {
                                    gotoSlidePage()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

function playsound() {
    $("#msgVideo").trigger("load");
    $("#msgVideo")[0].currentTime = 0;
    $("#msgVideo")[0].play()
}

function getMsgDiv(d, c, b, a) {
    if (!a) {
        return "<div class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar'><img src='" + c + "'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p><span style='color:blue;'>" + d.userFullName + ":</span>" + d.msg + "</p><time >" + b + "</time></div></div></div>"
    } else {
        return "<div class='row msg_container base_sent'><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p><span style='color:blue;'>" + d.userFullName + ":</span>" + d.msg + "</p><time >" + b + "</time></div></div><div class='col-md-2 col-xs-2 avatar'><img src='" + c + "'  class=' img-responsive ' widthX='60' heightX='60' /></div></div>"
    }
}

function getImgDiv(d, c, b, a) {
    if (!a) {
        return "<div class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar' style='text-align:center'><img src='" + c + "'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p><img src='images/push/" + d.msg + ".jpg' width='100' height='100'    /></p><time >" + b + "</time></div></div></div>"
    } else {
        return "<div class='row msg_container base_sent'><div class='col-md-10 col-xs-10'><div class='messages msg_receive' style='text-align:center'><p><img src='images/push/" + d.msg + ".jpg' width='100' height='100'    /></p><time >" + b + "</time></div></div><div class='col-md-2 col-xs-2 avatar'><img src='" + c + "'  class=' img-responsive ' widthX='60' heightX='60' /></div></div>"
    }
}

function getSystemMsgDiv(a) {
    return "<div style='color:red;'>" + a + "</div>"
}

function getMemberDiv(c, a, b) {
    if (c != null) {
        c = "id='" + c + "'"
    }
    return "<div " + c + " class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar'><img src='" + b + "'  class=' img-responsive ' widthX='60' heightX='60' /></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p>" + a + "</p></div></div></div>"
}

function IsFileExist(a) {
    var b = false;
    $.ajax({
        url: a,
        type: "HEAD",
        async: false,
        success: function() {
            b = true
        },
        error: function() {
            b = false
        }
    });
    return b
}

function disconnect() {
    var b = $("#OrderTeamId").val();
    var a = $("#txtWbs").val();
    var c = new Msg(2, b, a, "");
    socket.send(JSON.stringify(c));
    socket.close()
}

function sendMessage() {
    var c = $("#OrderTeamId").val();
    var a = $("#txtWbs").val();
    var b = $("#btn-input").val();
    if (b != "") {
        var d = new Msg(1, c, a, b);
        socket.send(JSON.stringify(d));
        $("#btn-input").val("")
    }
}

function sendImage(a) {
    $("#faceul").hide();
    var c = $("#OrderTeamId").val();
    var b = $("#txtWbs").val();
    var d = new Msg(4, c, b, a);
    socket.send(JSON.stringify(d))
}

function closeImageDiv() {
    $("#faceul").hide()
}

function showImgDiv() {
    var a = $("#showImgBtn").offset();
    $("#faceul").css("left", a.left);
    $("#faceul").css("top", a.top + $("#faceul").offsetHeight);
    $("#faceul").show()
}
$(document).on("click", ".panel-heading span.icon_minim", function(c) {
    var b = $(this);
    if (!b.hasClass("panel-collapsed")) {
        b.parents(".panel").find(".panel-body").slideUp();
        b.addClass("panel-collapsed");
        b.removeClass("glyphicon-minus").addClass("glyphicon-plus");
        $("#char_bar").addClass("aaa");
        $("#msgVideo")[0].play();
        $("#msgVideo")[0].pause()
    } else {
        b.parents(".panel").find(".panel-body").slideDown();
        b.removeClass("panel-collapsed");
        b.removeClass("glyphicon-plus").addClass("glyphicon-minus");
        $("#char_bar").removeClass("aaa");
        $("#char_bar").css("background", "");
        $("#char_bar").css({
            "-webkit-animation": ""
        });
        var a = $("#messages");
        a.scrollTop(a[0].scrollHeight)
    }
});

function Msg(a, c, b, d) {
    this.act = a;
    this.rId = c;
    this.msg = d;
    this.num = b
};