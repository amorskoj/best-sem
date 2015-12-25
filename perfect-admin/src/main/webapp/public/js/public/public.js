/**
 * Created by guochunyan on 2015/12/14.
 */
$(function () {
    //路由控制
    var href = window.location.href;
    href = '/' + href.split("/").slice(-1);
    if (href == "/") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(1) ").addClass("current");
        })
    } else if (href == "/roles") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(2) ").addClass("current");
        })
    }
    else if (href == "/system") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(3) ").addClass("current");
        })
    }
    else if (href == "/menus") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(4) ").addClass("current");
        })
    }
    else if (href == "/logs") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(5) ").addClass("current");
        })
    }
    //表格公用
    window.operateEvents = {
        'click .binding': function (e, value, row, index) {
            var bindingtext = $(this);
            if ($(this).html() == "绑定") {
                $('#modelbox').modal()
                $("#modelboxTitle").html("是否绑定？");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("取消绑定");
                })

            } else {
                $("#modelboxTitle").html("是否取消绑定？");
                $('#modelbox').modal()
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("绑定");
                })

            }
        },
        'click .disable': function (e, value, row, index) {
            var bindingtext = $(this);
            if ($(this).html() == "禁用") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("是否禁用？");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("启动");
                })

            } else {
                $("#modelboxTitle").html("是否启动？");
                $('#modelbox').modal()
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("禁用");
                })

            }
        },
        'click .editor': function (e, value, row, index) {
            var editorBottom = $(this);
            var that = $(this).parent().prevAll("td");
            var that_value = that.each(function (i) {
                var that_html = $(this).html();
                if (i == 1) {
                    return;
                } else if (i == 2) {
                    $(this).html("<a class='password_reset' href='javascript:void(0)' title='重置'>重置</a> ");
                } else if (i == 3) {
                    if (row.superAdmin) {
                        $(this).html("<select><option value='true'>超级管理员</option><option value='false'>管理员</option></select>");
                    } else {
                        $(this).html("<select><option value='false'>管理员</option><option value='true'>超级管理员</option></select>");
                    }
                } else if (i == 7) {
                    $(this).html(index + 1);
                } else if (i == 8) {
                    $(this).html("<input data-index='" + index + "' name='btSelectItem' type='checkbox'  />");
                } else {
                    $(this).html("<input type='text' class='form-control' value='" + that_html + "'> ");
                }
            });
            editorBottom.hide();
            editorBottom.next(".preserve").attr("style", "display:block");
            editorBottom.next().next(".cancel").attr("style", "display:block");
            editorBottom.next().next().next(".delete").attr("style", "display:none");
        },
        'click .preserve': function (e, value, row, index) {
            var preserveHtML = $(this);
            var preserveThat = $(this).parent().prevAll("td");
            var _newRow = {};
            if (confirm("是否需要修改?")) {
                preserveThat.each(function (i) {
                    var that_html = $(this).find("input").val();
                    _newRow["id"] = row.id;
                    switch (i) {
                        case 0:
                            _newRow["contact"] = that_html;
                            break;
                        case 1:
                            that_html = $(this).find("span").attr("ctime");
                            _newRow["ctime"] = that_html;
                            break;
                        case 3:
                            that_html = $(this).find("select :selected").val();
                            that_html = that_html == "true" ? true : false;
                            _newRow["superAdmin"] = that_html;
                            break;
                        case 4:
                            _newRow["loginName"] = that_html;
                            break;
                        case 5:
                            _newRow["title"] = that_html;
                            break;
                        case 6:
                            _newRow["name"] = that_html;
                            break;
                    }
                });
                $.ajax({
                    type: 'POST',
                    url: '/sysroles/' + row.id,
                    contentType: 'application/json',
                    data: JSON.stringify(_newRow),
                    success: function (result) {
                        if (result.code == 0) {
                            $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: _newRow});
                        } else {
                            $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: row});
                            alert(result.msg);
                        }
                    }
                });
            } else {
                $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: row});
            }
            preserveHtML.attr("style", "display:none");
            preserveHtML.next(".cancel").attr("style", "display:none");
            preserveHtML.next().next(".delete").attr("style", "display:block");
            preserveHtML.prevAll().show();
        },
        'click .cancel': function (e, value, row, index) {
            var cancelHtML = $(this);
            $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: row});
            cancelHtML.attr("style", "display:none");
            cancelHtML.next(".delete").attr("style", "display:block");
            cancelHtML.prev().prev(".editor").attr("style", "display:block");
            cancelHtML.prev(".preserve").attr("style", "display:none");
        },
        'click .delete': function (e, value, row, index) {
            if (row.id != -1) {
                $('#modelbox').modal();
                $("#modelboxTitle").html("是否删除？");
                $("#modelboxBottom").click(function () {
                    $.ajax({
                        url: '/sysroles/' + row.id,
                        type: "DELETE",
                        success: function (res) {
                            if (res.code == 0) {
                                $('#modelbox').modal('hide');
                                $("#roleAdmin").bootstrapTable("removeByUniqueId", row.id);
                                alert("删除成功!");
                            } else {
                                $('#modelbox').modal('hide');
                                if (res.msg) {
                                    alert(res.msg);
                                } else {
                                    alert("删除失败!")
                                }
                            }
                        }
                    })

                });
            } else {
                $("#roleAdmin").bootstrapTable("removeByUniqueId", row.id);
            }
        },
        'click .look': function (e, value, row, index) {
            $(".indexCret").css({'display': 'none'});
            $("#userLookUpWrap").css({"display": "block", "top": 221 + index * 45 + "px"});
            $(this).next().css("display", 'block');
            if (row != undefined && row != "") {
                var datas = [];
                row.systemUserModules.forEach(function (item, i) {
                    var data = {};
                    data.id = item.id;
                    data.userName = row.userName;
                    data.userId = row.id;
                    data.systemModal = item.moduleName;
                    data.userProperty = (item.enabled ? "已启用" : "已禁用");
                    data.openStates = (item.payed ? "已购买" : "未购买")
                    data.startDate = new Date(item.startTime).Format("yyyy年M月dd日");
                    data.endDate = new Date(item.endTime).Format("yyyy年M月dd日")
                    data.authorityAssignment = "设置";
                    var baiduName = [];
                    var baiduPwd = [];
                    var token = [];
                    var bestDomain = [];
                    var huiyanCode = [];

                    if (item.moduleName == "百思慧眼") {
                        $.ajax({
                            url: '/usersHuiYan',
                            type: 'get',
                            dataType: 'JSON',
                            async: false,
                            data: {
                                uid: row.id
                            },
                            success: function (user) {
                                if (user.data.length > 0) {
                                    user.data.forEach(function (huiyan, i) {
                                        baiduName.push(huiyan.bname == null ? "-" : huiyan.bname);
                                        baiduPwd.push(huiyan.bpasswd == null ? "-" : huiyan.bpasswd);
                                        token.push(huiyan.token == null ? "-" : huiyan.token);
                                        bestDomain.push(huiyan.site_url == null ? "-" : huiyan.site_url);
                                        var html = "<div id='base_code'>&lt;script&gt;<br>" +
                                            "var _pct= _pct|| [];<br> " +
                                            "(function() {<br>" +
                                            "var hm = document.createElement(\"script\");<br>" +
                                            "hm.src = \"//t.best-ad.cn/t.js?tid=" + huiyan.track_id + "\";<br>" +
                                            "var s = document.getElementsByTagName(\"script\")[0];<br>" +
                                            "s.parentNode.insertBefore(hm, s);<br> " +
                                            "})();<br>" +
                                            "&lt;/script&gt;</div>";
                                        huiyanCode.push(html)
                                    })
                                }
                            }
                        });
                    } else {
                        item.accounts.forEach(function (baidu, i) {
                            baiduName.push(baidu.baiduUserName == null ? "-" : baidu.baiduUserName);
                            baiduPwd.push(baidu.baiduPassword == null ? "-" : baidu.baiduPassword);
                            token.push(baidu.token == null ? "-" : baidu.token);
                            bestDomain.push(baidu.bestRegDomain == null ? "-" : baidu.bestRegDomain);
                        });
                    }
                    data.relatedAccount = baiduName;
                    data.relatedAccountPwd = baiduPwd;
                    data.APICode = token;
                    data.URLAddress = bestDomain;
                    data.statisticalCode = huiyanCode;
                    datas.push(data);
                });
                $("#userLookUpTable").bootstrapTable("removeAll")
                $("#userLookUpTable").bootstrapTable("append", datas);
                $('#userLookUpTable').bootstrapTable({
                    data: datas
                });
                $(".lookTableRow").parent().attr('style', 'padding: 0 !important');
            }
        },
        'click .password_reset': function (e, value, row, index) {
            $('#modelbox').modal();
            $("#modelboxTitle").html("重置管理员密码");
            $("#modelboxBottom").click(function () {
                if (confirm("是否重置用户:" + row.loginName + "的登录密码?")) {
                    var resetPwd = $("input[name='resetPwd']").val();
                    $.ajax({
                        url: '/sysroles/' + row.id + "/password",
                        type: 'POST',
                        data: {password: resetPwd},
                        success: function (res) {
                            if (res.code == 0) {
                                $('#modelbox').modal('hide');
                                alert("重置成功!");

                            } else {
                                if (res.msg) {
                                    alert(res.msg);
                                }
                            }
                        }
                    });
                }
            })
        },
        'click .addRole': function (e, value, row, index) {
            var readyAddData = {ctime: new Date().getTime()};
            var preserveThat = $(this).parent().prevAll("td");
            preserveThat.find("input")
                .each(function (i, o) {
                    var name = $(o).attr("name");
                    if (name) {
                        readyAddData[name] = $(o).val();
                    }
                });
            preserveThat.find("select").each(function (i, o) {
                var name = $(o).attr("name");
                if (name) {
                    readyAddData[name] = $(o).val() == "true" ? true : false;
                }
            });
            if (!readyAddData.name) {
                alert("请输入用户名!");
                return;
            }
            if (!readyAddData.title) {
                alert("请输入职务!");
                return;
            }
            if (!readyAddData.loginName) {
                alert("请输入登录名!");
                return;
            }
            if (!readyAddData.password) {
                alert("请输入密码!");
                return;
            }
            if (!readyAddData.contact) {
                alert("请输入联系方式!");
                return;
            }
            $.ajax({
                url: '/sysroles',
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify(readyAddData),
                success: function (res) {
                    if (res.code == 0) {
                        $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: readyAddData});
                        $("#roleAdmin").bootstrapTable("refresh");
                        alert("添加成功!");
                    } else {
                        alert(res.msg);
                    }
                }
            });
        }
    };
})
function firstAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        url: startId,
        platform: startId,
        time: startId
    });
    return rows;
}
function secondAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        wedName: startId,
        wedUrl: startId,
        wedCode: startId
    });
    return rows;
}
function queryParams() {
    return {
        type: 'owner',
        sort: 'updated',
        direction: 'desc',
        per_page: 100,
        page: 1
    };
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}