<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 14-8-20
  Time: 上午11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/assistantStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui-dialog.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/plugs/jQuery-smartMenu/smartMenu.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/table/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/table/bootstrap-responsive.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/grid/ui.jqgrid.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>
    <style rel="stylesheet" type="text/css">
        #sDiv .span-error {
            color: red;
        }

        #sDiv .span-ok {
            color: #9f9f9f;
        }
    </style>
    <script>
        String.prototype.trims = function () {
            return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
        };

    </script>
</head>
<body>
<%--<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>--%>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<jsp:include page="../promotionAssistant/alert/adgroupAlert.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>
<div class="mid over">
<div class="title_box">
    <div class="on_title over">
        <a href="#">  推广助手</a>
    </div>
</div>
<div id="tab">
<div class=" jiangjia_concent over">
<div class="jingjia_left fl over" id="left">
    <div class="j_l_top over">
        <span class="fl"><h3>账户目录</h3></span>
        <a href="javascript:void(0)" onclick="javascript:window.location.reload(true);" class="fr">刷新</a>
    </div>
    <div class="j_l_top2 over">
        <span class="fl">查找计划单元</span>
        <input class="fr" type="image" src="../public/img/search.png">
    </div>
    <div class="j_list01 over">
        <div id="loading">
        </div>
        <ul id="zTree" class="ztree over">
        </ul>
    </div>
    <div class="j_l_under over">
        <a href="javascript:void(0)" class="jiangkong">监控文件夹</a>
        <div class="j_list02 hides" style="height:100px; background:#fff;overflow:auto;">
            <div id="jiangkong_box">监控文件夹</div>
            <ul id="zTree2" class="ztree over" style="height:100px;">
            </ul>
        </div>
    </div>
</div>
<div class="jingjia_right fr over" id="right">
<div ID="testIframe" Name="testIframe" width="100%" onLoad="iFrameHeight()">
<div class="content_wraps over">
<div class="zhushou over wd ">
<div class="zhushou_menu wd">
    <ul class="zs_nav">
        <li class="showbox"><a id="downloadAccountData"><span><img  src="../public/img/dowland.png"></span><span>下载账户</span></a></li>
        <li><a><span><img src="../public/img/update.png"></span><span>上传更新</span></a></li>
        <li class="current"><a><span><img src="../public/img/Advanced_search.png"></span><span>高级搜索</span></a></li>
        <li class="nav_menu"><a><span><img src="../public/img/Repeat_keyword.png"></span><span>重复关键词</span></a>
            <ul>
                <li class="showbox3 current">重复关键词</li>
                <li>设置</li>
            </ul>
        </li>
        <li class="showbox4"><a> <span><img src="../public/img/Estimate.png"></span><span>估算工具</span></a></li>
    </ul>
</div>
<div class="zhushou_concent over" id="jiangkong_box2">
<div class="zs_concent_top over">
    <ul class="zh_menu2 fl" id="tabMenu">
        <li class="current" cname="table1">关键词</li>
        <li cname="table2">普通创意</li>
        <li cname="table3">附加创意</li>
        <li cname="table4">推广单元</li>
        <li cname="table5">推广计划</li>
        <li>账户</li>
    </ul>
</div>
<div class="zs_line"></div>
<div class="zs_box over">
<!--关键词-->
<div class="containers  over">
    <div class="zs_function">
        <ul class="fl">
            <li><a id="addKeyword" href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="javascript:deleteKwd()"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
            <li><a id="addOrUpdateKwd" href="#"><span class="zs_top"><img src="../public/img/zs_function3.png"></span><b>批量添加/更新&nbsp;</b></a><input type="image"id="deletekeywords"
                                                                                                                                                     src="../public/img/zs_input.png">
                <ul id="deletekeywordes" class="hides">
                    <li id="batchDelKwd"><a href="#"><span class="zs_top"><img src="../public/img/zs_function3.png"></span><b>批量删除</b></a></li>
                </ul>
            </li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a id="search_keyword" href="#"><span class="zs_top"><img
                    src="../public/img/zs_function5.png"></span><b>搜索</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function6.png"></span><b>分析</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function7.png"></span><b>估算</b></a></li>
            <li><a href="javascript:void(0)" onclick="MonitorDialog()"><span class="zs_top"><img src="../public/img/zs_function8.png"></span><b>监控</b></a></li>
            <li><a href="#" id="reduction"><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原</b></a><%--z_function_hover--%> </li>
            <li><a href="#" class="searchwordReport"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
            <li><a href="#"><span class="z_function_hover"><img  src="../public/img/zs_function11.png"></span><b>激活</b></a></li>
        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4">
        <div class="container">
            <table border="0" cellspacing="0" width="100%" class="table1 table-bordered"
                   data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th>&nbsp;关键词名称</th>
                    <th>&nbsp;关键词状态</th>
                    <th>&nbsp;启动/暂停</th>
                    <th>&nbsp;出价</th>
                    <th>&nbsp;计算机质量度</th>
                    <th>&nbsp;移动质量度</th>
                    <th>&nbsp;匹配模式</th>
                    <th>&nbsp;访问URL</th>
                    <th>&nbsp;移动访问URL</th>
                    <th class="username-column" data-noresize>&nbsp;推广计划名称</th>
                    <th class="username-column" data-noresize>&nbsp;
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick">
                </tbody>
            </table>
        </div>
    </div>
    <div class="more_list over" style="display:none;">
        <ul>
            <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加关键词</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除关键词</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证关键词</b></li>
            <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原关键词</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
        </ul>
    </div>
    <div class="zhanghu_input"></div>
    <div id="pagination_keywordPage" class="pagination"></div>
    <div class="zs_bottom over">
        <input type="hidden" id="hiddenkwid_1"/>
        <div class="zs_bottom1 over fl ">
            <ul>
                <li>
                    <div class="t_list01 fl over">关键词名称：</div>
                    <div class="t_list02 fl over"><input type="text" disabled="disabled" class="zs_input1 keyword_1"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">出价：</div>
                    <div class="t_list02 fl over"><input type="text" onblur="whenBlurEditKeyword(2,this.value)" onkeydown="missBlur(event,this)" class="zs_input1 price_1">
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">访问URL:</div>
                    <div class="t_list02 fl over"><input type="text" onblur="whenBlurEditKeyword(3,this.value)" onkeydown="missBlur(event,this)"class="zs_input1 pcurl_1"><span
                            class="pcurlSize_1">0/1024</span></div>
                </li>
                <li>
                    <div class="t_list01 fl over">移动访问URL：</div>
                    <div class="t_list02 fl over"><input type="text" onblur="whenBlurEditKeyword(4,this.value)"  onkeydown="missBlur(event,this)"class="zs_input1 mourl_1"><span
                            class="mourlSize_1">0/1024</span></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr">
            <ul>
                <li>
                    <div class="t_list01 fl over">所属监控文件夹：</div>
                    <div class="w_list02 fl over"><em id="genusFolderCount">0个</em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">匹配模式：</div>
                    <div class="w_list02 fl over"><em class="matchModel_1"></em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b class="status_1"></b></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select class="pause_1" onchange="whenBlurEditKeyword(7,this.value)">
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--普通创意-->
<div class="containers  over hides">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="javascript:void(0)" onclick="addCreative();"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="javascript:void(0)"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
            <li><a href="#"><span class="zs_top"> <img src="../public/img/zs_function3.png"></span><b onclick="creativeMulti();">批量添加/更新</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a href="#"><span class="z_function_hover" id="reBak" onclick="reBakClick();"><img src="../public/img/zs_function9.png"></span><b onclick="reBakClick();">还原</b></a>  </li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
            <li><a href="#"><span class="z_function_hover"><img src="../public/img/zs_function11.png"></span><b>激活</b></a>  </li>
        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4" style="height:480px;">
        <div class="container">
            <table border="0" cellspacing="0" width="100%" id="createTable" class="table2 table-bordered" data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th>&nbsp;操作</th>
                    <th>&nbsp;创意标题</th>
                    <th>&nbsp;创意描述1</th>
                    <th>&nbsp;创意描述2</th>
                    <th>&nbsp;默认访问URL</th>
                    <th>&nbsp;默认显示URL</th>
                    <th>&nbsp;移动访问URL</th>
                    <th>&nbsp;移动显示URL</th>
                    <th>&nbsp;启用/暂停</th>
                    <th>&nbsp;创意状态</th>
                    <th class="username-column" data-noresize>
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick2">
                </tbody>
            </table>
        </div>
        <div class="more_list over" style="display:none;">
            <ul>
                <li class="current" onclick="alert(123);"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加创意</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除创意</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证创意</b></li>
                <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原创意</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
            </ul>
        </div>
    </div>
    <div class="zhanghu_input"></div>
    <div id="creativePager" class="pagination"></div>
    <div class="zs_bottom over">
        <div class="zs_bottom1 over fl " id="sDiv">
            <ul>
                <li>
                    <div class="t_list01 fl over">创意标题：</div>
                    <div class="t_list03 fl over">
                        <input type="text" class="zs_input1" id="sTitle"><span>49/50</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意描述1：</div>
                    <div class="t_list03 fl over"><input type="text" class="zs_input1" id="sDes1"><span>79/80</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意描述2：</div>
                    <div class="t_list03 fl over"><input type="text" class="zs_input1" id="sDes2"><span>79/80</span>
                    </div>
                </li>
                <li>
                    <div class="t_list04 fl over">
                        <div class="t_list01 fl over">默认访问URL：</div>
                        <div class="t_list05 fl over"><input type="text" class="zs_input3" id="sPc"/><span>0/1024</span>
                        </div>
                    </div>
                    <div class="t_list04 fr over">
                        <div class="t_list01 fl over">默认显示URL：</div>
                        <div class="t_list05 fl over"><input type="text" class="zs_input3" id="sPcs"/><span>49/50</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="t_list04 fl over">
                        <div class="t_list01 fl over">移动访问URL：</div>
                        <div class="t_list05 fl over"><input type="text" class="zs_input3"
                                                             id="sMib"><span>49/1024</span>
                        </div>
                    </div>
                    <div class="t_list04 fr over">
                        <div class="t_list01 fl over">移动显示URL：</div>
                        <div class="t_list05 fl over"><input type="text" class="zs_input3" id="sMibs"><span>35/36</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意预览：</div>
                    <div class="w_list01 fl over" id="sPreview"
                         style="width:74%;height: 98px;border:1px solid #CCC; padding:1%;"></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr" style="border:none;">
            <ul>
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b id="sStatus">有效</b></div>
                </li>
                <li>
                    <div class="t_list01 fl over">设备偏好：</div>
                    <div class="w_list02 fl over">
                        <select>
                            <option>全部</option>
                        </select></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over" id="sPause">
                        <select>
                            <option value="true">启用</option>
                            <option value="false">暂停</option>
                        </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--附加创意-->
<div class="containers  over hides">
<div class="cy_menu over">
    <ul>
        <li class="current">蹊径子链</li>
        <li>推广电话</li>
        <li>商桥移动质询</li>
    </ul>
</div>
<div class="zs_function over">
    <ul class="fl">
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
        <li><a href="#"><span class="zs_top"> <img src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a></li>
        <li><a href="#"><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原</b></a>
        </li>
    </ul>
    <span class="fr">1/10</span>
</div>
<div class="list4" style="height:368px;">
<div class="container">
<table border="0" cellspacing="0" width="100%" class="table3 table-bordered" data-resizable-columns-id="demo-table">
<thead>
<tr class="list02_top">
    <th>&nbsp;子链一名称</th>
    <th>&nbsp;子链一URL</th>
    <th>&nbsp;子链二名称</th>
    <th>&nbsp;子链二URL</th>
    <th>&nbsp;子链三名称</th>
    <th>&nbsp;子链三URL</th>
    <th>&nbsp;子链四名称</th>
    <th>&nbsp;子链四URL</th>
    <th>&nbsp;子链五名称</th>
    <th class="username-column" data-noresize>&nbsp;子链五URL
        <div class="set fr"></div>
    </th>
</tr>
</thead>
<tbody id="tbodyClick4">
<tr class="list2_box3">
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tbody>
</table>
</div>
</div>
<div class="more_list over" style="display:none;">
    <ul>
        <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加推广计划</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广计划</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广计划</b></li>
        <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原推广计划</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
    </ul>
</div>
<div class="zhanghu_input"></div>
<div class="zs_bottom3 over">
    <div class="zs_bottom1 over fl " style="width:100%; border:none;">
        <ul>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称</span>
                    <input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称</span>
                    <input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称</span>
                    <input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称</span>
                    <input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称</span>
                    <input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
        </ul>
    </div>
</div>
<div class="zs_bottom over">
    <div class="zs_bottom1 over fl ">
        <div class="w_list01 fl over">关键词名称：</div>
        <div class="w_list04 fl over">
            <ul class="w_list05 over">
                <li class="current">推广位 <span>|</span><span class="list06_point"></span></li>
                <li>推广链接位</li>
            </ul>
            <div class="w_list06 over">
                <div class="list06_concent">
                    <textarea class="list06_textarea">
                    </textarea>
                </div>
                <div class="list06_concent hides">
                    <textarea class="list06_textarea">
                    </textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="zs_bottom2 over fr">
        <ul>
            <li>
                <div class="t_list01 fl over"><span>状态:</span></div>
                <div class="w_list02 fl over"><b></b></div>
            </li>
            <li>
                <div class="t_list01 fl over"><span>设备偏好：</span></div>
                <div class="w_list02 fl over"><select>
                    <option>全部</option>
                </select></div>
            </li>
            <li>
                <div class="t_list01 fl over"><span>启用/暂停：</span></div>
                <div class="w_list02 fl over"><select>
                    <option>启用</option>
                </select></div>
            </li>
        </ul>
    </div>
</div>
</div>
<!--推广单元-->
<div class="containers  over hides">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="javascript:void(0)" onclick="addAdgroup()"><span class="zs_top"><img  src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function3.png"></span><b  onclick="adgroupMutli();">批量添加/更新</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a href="#"><span class="z_function_hover" id="agReback" onclick="agreBakClick()"><img src="../public/img/zs_function9.png"></span><b onclick="agreBakClick()">还原</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4">
        <div class="container">
            <table border="0" cellspacing="0" width="100%" id="adGroupTable" class="table4 table-bordered" data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th>&nbsp;操作</th>
                    <th>&nbsp;推广单元名称</th>
                    <th>&nbsp;推广单元状态</th>
                    <th>&nbsp;启动/暂停</th>
                    <th>&nbsp;出价</th>
                    <th>&nbsp;否定关键词</th>
                    <th>&nbsp;移动出价比例</th>
                    <th>&nbsp;推广计划名称</th>
                    <th class="username-column" data-noresize>
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick_campaign">
                <%--<tr class="list2_box3">--%>
                <%--<td>&nbsp;北京{婚博会}<span class="editor"></span></td>--%>
                <%--<td>&nbsp;有效<span class="editor"></span></td>--%>
                <%--<td>&nbsp;启用<span class="editor"></span></td>--%>
                <%--<td>&nbsp;一星<span class="editor"></span></td>--%>
                <%--<td>&nbsp;一星<span class="editor"></span></td>--%>
                <%--<td>&nbsp;短语-核心包含<span class="editor"></span></td>--%>
                <%--<td>&nbsp;<a href="#">http://tthunbohui.com </a><span class="editor"></span></td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>
    </div>
    <div class="more_list over" style="display:none;">
        <ul>
            <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加推广单元</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广单元</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广单元</b></li>
            <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原推广单元</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
        </ul>
    </div>
    <div class="zhanghu_input"></div>
    <div id="adgroupPager" class="pagination"></div>
    <div class="zs_bottom over" id="aDiv">
        <div class="zs_bottom1 over fl ">
            <ul>
                <li>
                    <div class="t_list01 fl over">名称：</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">出价：</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">移动出价比例：</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr">
            <ul>
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b id="apStatus">有效</b></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select id="apPause" onchange="adgroupdSelectChange(this);">
                        <option value="true">启用</option>
                        <option value="false">暂停</option>
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--推广计划-->
<div class="containers  over hides">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="#" id="addCampaign"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="#" id="quickAddplan"><span class="zs_top"><img src="../public/img/zs_function17.png"></span><b>快速新建计划</b></a></li>
            <li><a href="javascript:deleteCampaign();"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a href="#" id="reduction_caipamgin"><span class="zs_top"><img src="../public/img/zs_function9.png"></span><b>还原</b></a></li>
            <li><a href="#" class="searchwordReport"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4">
        <div class="container">
            <table border="0" cellspacing="0" width="100%" class="table5 table-bordered" data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th>&nbsp;推广计划名称</th>
                    <th>&nbsp;推广计划状态</th>
                    <th>&nbsp;启用/暂停</th>
                    <th>&nbsp;每日预算</th>
                    <th>&nbsp;创意展现方式</th>
                    <th>&nbsp;动态创意状态</th>
                    <th>&nbsp;推广时段</th>
                    <th>&nbsp;推广地域</th>
                    <th>&nbsp;否定关键词</th>
                    <th>&nbsp;IP排除</th>
                    <th>&nbsp;到预算下线时间</th>
                    <th class="username-column" data-noresize style="text-align:left; width:150px;"><span class="fl"> </span>
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick5">
                </tbody>
            </table>
        </div>
    </div>
    <div class="more_list over" style="display:none;">
        <ul>
            <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加推广计划</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广计划</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广计划</b></li>
            <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原推广计划</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
        </ul>
    </div>
    <div class="zhanghu_input"></div>
    <div id="pagination_campaignPage" class="pagination"></div>
    <div class="zs_bottom over">
        <input type="hidden" id="hiddenCampaignId"/>
        <div class="zs_bottom2 over fl " style="border-right: 1px solid #e7e7e7;">
            <ul>
                <li>
                    <div class="t_list01 fl over">名称：</div>
                    <div class="t_list06 fl over"><input type="text" onblur="whenBlurEditCampaign(1,this.value);"onkeydown="missBlur(event,this);" class="zs_input3 campaignName_5"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">每日预算：</div>
                    <div class="t_list06 fl over"><input type="text" onblur="whenBlurEditCampaign(2,this.value);"onkeydown="missBlur(event,this);" class="zs_input3 budget_5">
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">移动出价比例：</div>
                    <div class="t_list06 fl over"><input type="text" onblur="whenBlurEditCampaign(3,this.value);" onkeydown="missBlur(event,this);"class="zs_input3 priceRatio_5"><span>0/1024</span></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom1 over fr" style="border-right:none;">
            <ul class="z_bottom3 fl">
                <li>
                    <div class="t_list01 fl over">推广时段：</div>
                    <div class="w_list02 fl over"><em class="schedule_5">全部</em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">推广地域：</div>
                    <div class="w_list02 fl over"><em class="regionTarget_5">使用账户推广地域</em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">动态创意状态：</div>
                    <div class="w_list02 fl over"><b class="isDynamicCreative_5"></b></div>
                </li>
            </ul>
            <ul class="z_bottom3 fl">
                <li>
                    <div class="t_list01 fl over">否定关键词：</div>
                    <div class="w_list02 fl over "><em class="negativeWords_5"></em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">IP排除：</div>
                    <div class="w_list02 fl over"><em class="excluedIp_5"></em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意展现方式：</div>
                    <div class="w_list02 fl over">
                        <select class="selectShowProb_5" onchange="whenBlurEditCampaign(10,this.value);"> </select>
                    </div>
                </li>
            </ul>
            <ul class="z_bottom3 fl">
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b class="status_5"></b></div>
                </li>
                <li>
                    <%--<div class="w_list01 fl over">IP排除：</div>
                    <div class="w_list02 fl over"><span> 0次</span></div>--%>
                    <div class="t_list01 fl over">达到预算下线：</div>
                    <div class="w_list02 fl over"><span class="budgetOfflineTime_5"></span></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over">
                        <select class="selectPause_5"  onchange="whenBlurEditCampaign(11,this.value);">
                        </select>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--账户-->
<div class="containers  over hides">
    <div class="zh_top over">
        <div class="fl">
            <span><b>baidu-bjtthunbohui2134115</b></span>
            <%--            <span><a href="#">还原帐户</a><input type="checkbox" checked="checkde"/>验证帐户</span>--%>
        </div>
        <div class="fr"><input type="image" src="../public/img/shuaxin.png"></div>
    </div>
    <div class="zh_list02 over">
        <ul>
            <li>
                <span>帐户余额：<b id="balance" class="blue">￥38678.1</b></span>
                <span>昨日消费：<b id="cost" class="blue">暂无数据</b></span>
                <span>消费升降：<b id="costStatus" class="blue">暂无数据</b></span>
                <span>动态创意:<a href="#" class="showbox7">开启</a></span>
            </li>
            <li>
                <span>帐户预算：<b id="accountBudget">不限定</b><a href="#" class="showbox5">修改</a></span>
                <span>到达预算：<b id="reachBudget" class="blue">-</b></span>
                <span>IP排除：<a href="#" class="showbox6">设置</a></span>
            </li>
        </ul>
    </div>
    <div class="zh_list over">
        <div id="containerLegend" class="zs_function over">
        </div>
        <div id="container" style="width: 60%; height: 40%"></div>
    </div>
</div>
</div>
</div>
<div class="zhushou_concent over hides" id="jiangkong_box3">
    <div class="zs_concent_top over">
        <ul class="zh_menu2 fl">
            <li class="current" cname="table1">已监控关键词</li>
            <li cname="table2" id="jkwjj">监控文件夹</li>
        </ul>
    </div>
    <div class="zs_line"></div>

    <!--已监控关键词-->
    <%--<div class="containers  over">
        <div class="zs_function">
            <ul class="fl">
                <li><a  href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>停止</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function11.png"></span><b>激活&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a> </li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function5.png"></span><b>搜索</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function6.png"></span><b>分析</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function7.png"></span><b>估算</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function18.png"></span><b>下载同步</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function19.png"></span><b>上传更新</b></a></li>
            </ul>
        </div>
        <div class="zs_line"></div>--%>
    <div class="zs_box over">
        <!--已监控关键词-->
        <div class="containers  over">
            <div class="zs_function">
                <ul class="fl">
                    <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b
                            id="removeMonitor">停止监控</b></a>
                    </li>
                    <li><a href="javascript:void(0);" onclick="searchword();"><span class="zs_top"><img
                            src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function11.png"></span><b id="activate">激活</b></a>
                    </li>
                    <li><a href="javascript:void(0);" onclick="showSearchWord();"><span class="zs_top"><img src="../public/img/zs_function5.png"></span><b>搜索</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function6.png"></span><b>分析</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function7.png"></span><b>估算</b></a>
                    </li>
                </ul>
            </div>
            <div class="list4">
                <div class="container">
                    <table border="0" cellspacing="0" width="100%" class="table1 table-bordered"
                           data-resizable-columns-id="demo-table">
                        <thead>
                        <tr class="list02_top">
                            <th>&nbsp;关键词名称</th>
                            <th>&nbsp;推广计划名称</th>
                            <th>&nbsp;推广单元名称</th>
                            <th>&nbsp;关键词状态</th>
                            <th>&nbsp;启动/暂停</th>
                            <th>&nbsp;出价</th>
                            <th>&nbsp;计算机质量度</th>
                            <th>&nbsp;移动质量度</th>
                            <th>&nbsp;匹配模式</th>
                            <th>&nbsp;访问URL</th>
                            <th>&nbsp;移动访问URL</th>
                            <th class="username-column" data-noresize>&nbsp;监控文件夹
                                <div class="set fr"></div>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="monitorFolder">
                        </tbody>
                    </table>
                    <input type="hidden" id="remoneMonitor">
                </div>
            </div>
            <div class="more_list over" style="display:none;">
                <ul>
                    <li class="current"><span class="zs_top"><img
                            src="../public/img/zs_function1.png"></span><b>添加关键词</b></li>
                    <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除关键词</b></li>
                    <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证关键词</b></li>
                    <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原关键词</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
                    <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
                    <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
                    <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
                </ul>
            </div>
            <div class="zhanghu_input"></div>

            <div class="page kwdPage over">
                <ul>
                    <div>每页显示条数<select style="width:60px;" onchange="getKwdList(1)">
                        <option value="20">20</option>
                        <option value="40">40</option>
                        <option value="60">60</option>
                    </select></div>
                    <li><a href="#">首页</a></li>
                    <li><a href="#">上一页</a></li>
                    <li><a href="#">下一页</a></li>
                    <li><a href="#">尾页</a></li>
                    <li>当前页:1/0</li>
                    <li>共0条</li>
                    <li><input type="text" maxlength="10" class="inputNo kwdPageNo"/>&nbsp;<input type="button"value="GO"/></li>
                </ul>
            </div>
        </div>
        <!--监控文件夹-->
        <div class="containers  over hides">
            <div class="zs_function over">
                <ul class="fl">
                    <li><a href="javascript:void(0)" onclick="folderDialog();"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
                    <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b id="removeFolder">删除</b></a></li>
                    <li><a href="javascript:void(0);" onclick="searchword();"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
                    <li id="downSync"><a href="javascript:void(0)"><span class="zs_top"><img src="../public/img/zs_function18.png"></span><b>下载同步</b></a></li>
                    <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function19.png"></span><b>上传更新</b></a></li>
                </ul>
            </div>
            <div class="list4" style="height:480px;">
                <div class="container">
                    <table border="0" cellspacing="0" width="100%" class="table2 table-bordered"
                           data-resizable-columns-id="demo-table">
                        <thead>
                        <tr class="list02_top">
                            <th>&nbsp;监控文件夹</th>
                            <th>&nbsp;监控文件夹内</th>
                            <th class="username-column" data-noresize>
                                <div class="set fr"></div>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="MonitorTbody">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="zhanghu_input"></div>
            <div class="zs_bottom over">
                <div class="zs_bottom1 over" style="width:100%;">
                    <ul>
                        <li>
                            <div class="t_list01 fl over" style="width:100px;">监控关键词数量：</div>
                            <div class="t_list02 fl over"><input type="text" class="zs_input1" id="count" readonly>
                            </div>
                        </li>
                        <li>
                            <div class="t_list01 fl over" style="width:100px;">监控文件夹名称：</div>
                            <div class="t_list02 fl over"><input type="text" class="zs_input1" id="folder" readonly>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
<jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
<!---------下载账户----------->
<div class="TB_overlayBG"></div>
<div class="box" style="display:none;*width:400px;" id="download">
    <h2 id="box2"><span class="fl">账户下载</span><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        您希望下载账户的哪个部分？
        <ul class="zs_set">
            <li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>
            <li><input type="radio" name="no1">&nbsp; 已下载的推广计划</li>
            <li><input type="radio" name="no1">&nbsp; 从最新的推广计划列表中选择</li>
        </ul>
        <div class="zs_sets over">
            <div id="allCampaign" class="zs_ses1" style="overflow: auto">
                <ul>
                </ul>
            </div>
            <div id="existsCampaign" class="zs_ses1 hides" style="overflow: auto">
                <ul>
                </ul>
            </div>
            <div id="newCampaign" class="zs_ses1 hides" style="overflow: auto">
                <ul>
                </ul>
            </div>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="downloadAccount" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------查找重复关键词----------->
<div class="box3" style="display:none;*width:400px;">
    <h2 id="box3">
        <span class="fl">查找重复关键词</span><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <span>请指定重复关键词的标准，已删除的关键词不会被当作重复关键词。</span>
        <ul class="zs_set">
            <li><h3>匹配模式</h3></li>
            <li><input type="radio" checked="checked" name="ms" class="ms">&nbsp; 重复的关键词须为相同的匹配模式
                <div class="zs_sets2 over hides">
                    <input type="checkbox" name="ms2">&nbsp; 所有短语模式视为湘潭
                </div>
            </li>
            <li></li>
            <li><input type="radio" checked="checked" name="ms">&nbsp; 重复的关键词可为不同的匹配模式</li>
        </ul>
        <ul class="zs_set">
            <li><h3>重复关键词位置</h3></li>
            <li><input type="radio" checked="checked" name="ms3">&nbsp; 在同一推广单元内</li>
            <li><input type="radio" name="ms3">&nbsp; 在同一推广计划内（所有推广单元）</li>
            <li><input type="radio" name="ms3">&nbsp; 整个账户内（所有推广计划）</li>
        </ul>
        <ul class="zs_set">
            <li><h3>不显示以下关键词</h3></li>
            <li><input type="checkbox" checked="checked" name="ms4">&nbsp; 已删除的推广计划和推广单元内的重复关键词</li>
            <li><input type="checkbox" name="ms4">&nbsp; 已暂停推广的推广计划和推广单元内的重复关键词</li>

        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------估算工具----------->
<div class="box4" style="display:none;*width:520px;">
    <h2 id="box4">
        <span class="fl">估算工具</span><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <span>关键词：<em>（每行一个，最多100个）</em></span>

        <div class="over wd">
            <div class="gusuan_bottom1 over fl ">
                <p>
                    <span class="fl"><em>匹配模式：广泛=关键词 短语=”关键词” 精准=[关键词]</em></span>
                    <span class="fr"><em>1/100</em></span>
                </p>
                <textarea class="zs_input5"></textarea>
            </div>
            <div class="gusuan_bottom2 fr over">
                <ul>
                    <li>每次点击最高出价：</li>
                    <li><select>
                        <option>5.00</option>
                    </select></li>
                    <li>推广地域：<em>全部地域</em></li>
                    <li><h3>注意：</h3>重复的关键词按第一次出现时使用的匹配模式进行估算。</li>
                </ul>
            </div>
        </div>
        <div class="gusuan_under over">
            <div class="gusuan_under1 over wd">
                <input type="button" value="估算" class="zs_input2 fl"><span class="gs_remind fl">将联网进行估算，请您保持网络畅通。</span>
            </div>
            <div class="gusuan_under2 over wd">
                <span class="gusuan_under3"></span>
                <table border="0" cellspacing="0" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <td>&nbsp;关键词名称</td>
                        <td>&nbsp;匹配模式</td>
                        <td>&nbsp;估算状态</td>
                        <td>&nbsp;估算排名区间</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>&nbsp;二手车买卖</td>
                        <td>&nbsp;广泛</td>
                        <td>&nbsp;有效</td>
                        <td>&nbsp;1-3</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <p><em>估算排名是关键词在所有可展现的推广中的排名（实际展现位置受质量度的影响）。</em></p>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------修改账户预算----------->
<div class="box5" style="display:none; width: 230px">
    <h2 id="box5">
        <span class="fl">修改账户预算</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul class="zs_set">
            <li><input id="budget_text" type="text" style="width: 180px"/></li>
        </ul>
    </div>
    <div class="main_bottom" style="margin-left: 20%">
        <div class="w_list03">
            <ul>
                <li id="modifyAccountBudget_ok" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------IP排除----------->
<div class="box6" style="display:none;width: 300px">
    <h2 id="box6">
        <span class="fl">IP排除</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul class="zs_set">
            <li><textarea id="excludeIP_ta" style="width: 250px; overflow:auto; resize: none"></textarea></li>
        </ul>
    </div>
    <div class="main_bottom" style="margin-left: 12%">
        <div class="w_list03">
            <ul>
                <li id="excludeIP_ok" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------修改动态创意状态----------->
<div class="box7" style="display:none; width: 230px">
    <h2 id="box7"><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li id="dynamicCreative" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<div id="reachBudget1" class="box" style="display:none; width: 600px">
    <h2 id="reachBudget_head">
        <span class="fl">账户预算</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <div class="tu_top over">
            <ul id="budgetList" class="zs_set">
            </ul>
        </div>
    </div>
</div>
<%--创意添加选择计划，单元弹出窗口--%>
<div class="box" style="display:none" id="jcAdd">
    <h2 id="dAdd">
        <span class="fl">添加创意</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        选择要添加到的计划或者单元!
        <ul class="zs_set" id="jcUl">
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
        </ul>
        <input type="hidden" id="doMain">
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="planUnit()">确认</li>
                <li onclick="closeAlertCreative();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--推广计划设置否定关键词窗口--%>
<div class="box" style="display:none;" id="setNegtiveWord">
    <h2 id="setFdKeywordDiv">
        <span class="fl">否定关键词设置</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <p>以下设置仅对"广泛","短语"匹配的关键词生效，每行一词，没词20汉字以内，最多200项。</p>

        <div class="inputKwdDiv fl">
            <div><p>否定关键词<span id="ntwCount">(0/200)</span></p></div>
            <textarea id="ntwTextarea" onkeydown="ntwTextareaCount();" style="height: 300px;width: 350px;"></textarea>
        </div>

        <div class="inputKwdDiv fr">
            <div><p>精确否定关键词<span id="entwCount">(0/200)</span></p></div>
            <textarea id="entwTextarea" onkeydown="entwTextareaCount();" style="height: 300px;width: 350px;"></textarea>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current ntwOk">确认</li>
                <li onclick="closeSetNegtiveWord()">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--创意修改弹出窗口--%>
<div class="box" style="display:none;width:500px;" id="jcUpdate">
    <h2 id="dUpdate">
        <span class="fl">修改创意</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <form id="cUpdateForm">
            <ul class="chuangyi">
                <li><label style="display: none;">---:<span>4/50</span></label><input name="oid" type="hidden"/></li>
                <li><label>创意标题:<span>49/50</span></label><input name="title" class="chuangyi_input" maxlength="50"/>
                    <input type="button" class="chuangyi_fu" value="插入{}符" onclick="addTb()"/></li>
                <li><label>创意描述1:<span>49/80</span></label><input name="description1" class="chuangyi_input"  maxlength="80"/><input type="button" class="chuangyi_fu" value="插入{}符"
                                                                                                                                     onclick="addTbDes1()"/></li>
                <li><label>创意描述2:<span>49/80</span></label><input name="description2" class="chuangyi_input"   maxlength="80"/><input type="button"class="chuangyi_fu" value="插入{}符"
                                                                                                                                      onclick="addTbDes2()"/></li>
                <li><label>默认访问URL:<span>49/1024</span></label><input name="pcDestinationUrl" maxlength="1024"/></li>
                <li><label>默认显示URL:<span>49/50</span></label><input name="pcDisplayUrl" maxlength="50"/></li>
                <li><label>移动访问URL:<span>49/1024</span></label><input name="mobileDestinationUrl" maxlength="1024"/></li>
                <li><label>移动显示URL:<span>35/36</span></label><input name="mobileDisplayUrl" maxlength="36"/></li>
                <li> <label class="fl"><span>创意状态:</span></label><label id="cuStatus" class="fl">暂无</label><input type="hidden" name="status"></li>
                <li><label class="fl"><span>是否启用:</span></label><select name="pause"><option value="true">启用</option><option value="false">暂停</option></select></li>
            </ul>
        </form>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current" onclick="updateOk();">确认</li>
                <li onclick="closeAlertCreative();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--推广计划设置IP排除窗口--%>
<div class="box" style="display:none;" id="setExcludeIp">
    <h2 id="setExcludeIpDiv">
        <span class="fl">IP排除列表</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li>你可将IP最后一段设为*，以屏蔽一段地址内的创意展现。</li>
            <li>每个IP地址占一行。IP排除的数量不能超过20</li>
        </ul>
        <span id="IpListCount">IP排除(0/20)</span>

        <div class="inputIpDiv" style="margin-left:0px;">
            <textarea id="IpListTextarea" onkeydown="IpListTextareaCount();"
                      style="width:365px;height: 350px;"></textarea>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current excludeIpOk">确认</li>
                <li onclick="closeSetExcludeIp()">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--推广计划设置推广时段窗口--%>
<div class="box" style="display:none;" id="setExtension">
    <h2 id="setExtensionDiv">
        <span class="fl">推广时段管理</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="chooseTime">
        <b class="fl">请选择时段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(按住Ctrl键并且鼠标经过可多选时间段)</b>
        <ul class="fr">
            <li class="tfsjd"><span></span>&nbsp;&nbsp;&nbsp;投放时间段</li>
            <li class="ztsjd"><span></span>&nbsp;&nbsp;&nbsp;暂停时间段</li>
        </ul>
    </div>
    <br/>
    <div class="hours">
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current scheduleOk">确认</li>
                <li onclick="closeSetExtension()">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--添加监控文件夹弹出窗口--%>
<div class="box" style="display:none" id="addFolderDiv">
    <h2 id="folderTUO">
        <span class="fl">添加监控文件夹</span>
        <a href="#" class="close">关闭</a></h2>
    <div class="mainlist">
        <label id="dialogMsg">请输入要创建的监控文件夹名称!</label>
        <ul class="zs_set" id="adfd">
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="addFolderQR">确认</li>
                <li onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--添加监控对象弹出窗口--%>
<div class="box" style="display:none" id="addMonitorDiv">
    <h2 id="MonitorTUO">
        <span class="fl">添加监控对象</span>
        <a href="#" class="close">关闭</a></h2>
    <div class="mainlist">
        <ul class="zs_set" id="admon">
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="addMonitorQR">确认</li>
                <li onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--添加推广计划弹出窗口--%>
<jsp:include page="../promotionAssistant/alert/addCampaign.jsp"/>
<!-- javascript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/table/store.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/grid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/table/jquery.resizableColumns.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/plugs/jQuery-smartMenu/jquery-smartMenu-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/dialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/dialog-plus.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/html.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/creative.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/assistantKeyword.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/global.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/assistantCampaign.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/assistantAccount.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/adgroup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/updateAccountData.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/addKeyword.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/Monitoring.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.pin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        window.dialog = dialog;
    });
    $(function () {
        setTimeout("resizable('table1')", 300);
    });
    var resizable = function (cla) {
        $("." + cla).resizableColumns({
            store: store
        });
    };
    //loading
    var ajaxbg = $("#background,#progressBar");
    ajaxbg.hide();
    $(document).ajaxStart(function () {
        ajaxbg.show();
    })
    $(document).ajaxStop(function () {
        ajaxbg.fadeOut(1000);
    });

</script>
</body>
</html>