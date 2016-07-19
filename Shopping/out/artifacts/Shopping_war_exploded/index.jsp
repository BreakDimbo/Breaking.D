<%@ page import="com.limbo.shopping.model.Category" %>
<%@ page import="com.limbo.shopping.model.Product" %>
<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.service.CategoryMgr" %>
<%@ page import="com.limbo.shopping.service.ProductMgr" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    List<Category> categories = CategoryMgr.getInstance().getCategories();
    List<Category> topCategories = CategoryMgr.getInstance().getTopCategories(categories);
    List<Product> products = ProductMgr.getInstance().getAllProducts();
    User u = (User) session.getAttribute("user");
    boolean login = false;
    if (u != null) {
        login = true;
    }
%>

<!DOCTYPE html>
<!-- saved from url=(0026)https://market.douban.com/ -->
<html lang="zh-CN" class="ua-mac ua-webkit    ">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Start Visual Website Optimizer Asynchronous Code -->
    <script async="" src="./index/gtm.js"></script>
    <script async="" src="./index/analytics.js"></script>
    <script type="text/javascript" defer="" async="" src="./index/piwik.js"></script>
    <script type="text/javascript">
        var _vwo_code = (function () {
            var account_id = 228272,
                    settings_tolerance = 2000,
                    library_tolerance = 2500,
                    use_existing_jquery = false,
// DO NOT EDIT BELOW THIS LINE
                    f = false, d = document;
            return {
                use_existing_jquery: function () {
                    return use_existing_jquery;
                }, library_tolerance: function () {
                    return library_tolerance;
                }, finish: function () {
                    if (!f) {
                        f = true;
                        var a = d.getElementById('_vis_opt_path_hides');
                        if (a)a.parentNode.removeChild(a);
                    }
                }, finished: function () {
                    return f;
                }, load: function (a) {
                    var b = d.createElement('script');
                    b.src = a;
                    b.type = 'text/javascript';
                    b.innerText;
                    b.onerror = function () {
                        _vwo_code.finish();
                    };
                    d.getElementsByTagName('head')[0].appendChild(b);
                }, init: function () {
                    settings_timer = setTimeout('_vwo_code.finish()', settings_tolerance);
                    var a = d.createElement('style'), b = 'body{opacity:0 !important;filter:alpha(opacity=0) !important;background:none !important;}', h = d.getElementsByTagName('head')[0];
                    a.setAttribute('id', '_vis_opt_path_hides');
                    a.setAttribute('type', 'text/css');
                    if (a.styleSheet)a.styleSheet.cssText = b; else a.appendChild(d.createTextNode(b));
                    h.appendChild(a);
                    this.load('//dev.visualwebsiteoptimizer.com/j.php?a=' + account_id + '&u=' + encodeURIComponent(d.URL) + '&r=' + Math.random());
                    return settings_timer;
                }
            };
        }());
        _vwo_settings_timer = _vwo_code.init();
    </script>

    <!-- End Visual Website Optimizer Asynchronous Code -->

    <title>市集</title>


    <link rel="stylesheet" href="./index/font-awesome.min.css">
    <link rel="stylesheet" href="./index/common.css">
    <script src="./index/head.js"></script>
    <script>var _USER_ID = "30959598"</script>


    <link rel="stylesheet" href="./index/base.css">

    <link rel="stylesheet" href="./index/3f6496a7b9dfa367.css">
</head>
<body class="">

<header id="market-header" class="market-global-header">
    <div class="inner">


        <ul class="categories">

            <%
                for (int i = 0; i < topCategories.size(); i++) {
                    Category c = topCategories.get(i);

            %>
            <!-- 一级分类 -->
            <li class="item" level="1">

                <a class="nav" href=# title=<%=c.getName()%>>
                    <%=c.getName()%>
                </a>

                <!-- 二级分类 -->

                <div class="sub-category">
                    <div class="sub-wrap">
                        <div class="sub-box" style="width: 1209px;">
                            <%
                                List<Category> children2 = CategoryMgr.getInstance().getChildren(categories, c.getId(), 2);
                                for (Category c2 : children2) {

                            %>
                            <dl class="sub">
                                <dt class="title">
                                    <a href=# title=<%=c2.getName()%>>
                                        <%=c2.getName()%>
                                    </a>
                                </dt>

                                <%
                                    List<Category> children3 = CategoryMgr.getInstance().getChildren(categories, c2.getId(), 3);
                                    for (Category c3 : children3) {

                                %>
                                <dd>
                                    <!-- 三级分类 -->
                                    <div class="split">

                                        <a href="#" title=<%=c3.getName()%>>
                                            <%=c3.getName()%>
                                        </a>

                                    </div>
                                </dd>
                                <%
                                    }
                                %>
                            </dl>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
            </li>
            <%
                }
            %>

        </ul>


        <div class="market-user">
            <%
                if (login) {
            %>
            <a class="user-pic" id="header-user-pic" href="selfservice.jsp">
                <img src="./index/u30959598-9.jpg"><span>我的账户</span>
            </a>
            <%
            } else {
            %>
            <a class="user-pic" id="header-user-pic" href="login.jsp">
                <img src="./index/u30959598-9.jpg"><span>登录</span>
            </a>

            <%
                }
            %>
            <a class="user-cart" id="header-user-cart" href="buy.jsp">
                <i class="mf cart"></i>
                <b class="cart-count"></b><span>购物车</span>
            </a>
        </div>


        <!--搜索-->
        <div class="search-bar">
            <form id="searchForm" action="admin/SearchResult.jsp" method="get">
                <input value="POLO衫" type="text" name="keyword" class="text" placeholder="搜一下试试看" maxlength="30">
                <button type="submit" class="submit"><i class="mf search"></i></button>
            </form>
        </div>
    </div>
</header>


<div class="market-wrapper" id="homepage">


    <div class="slide-box" id="slide-top">
        <div class="slide" style="height: 280px; position: relative; z-index: 1;">
            <div class="slide-inner" style="position: absolute; z-index: 1; width: 4750px; top: 0px; left: 0px;">
                <div class="slide-item">
                    <a href="#">
                        <img src="./index/file-1467963350-0.jpg" alt="">
                    </a>
                </div>
                <div class="slide-item">
                    <a href="#"><img src="./index/file-1467943529-0.jpg" alt="">
                    </a>
                </div>
                <div class="slide-item">
                    <a href="#">
                        <img src="./index/file-1467266371-0.jpg" alt="">
                    </a>
                </div>
                <div class="slide-item">
                    <a href="#">
                        <img src="./index/file-1467364961-0.jpg" alt="">
                    </a>
                </div>
                <div class="slide-item">
                    <a href="#">
                        <img src="./index/file-1467885911-0.jpg" alt="">
                    </a>
                </div>
            </div>
            <a class="slide-button-next"></a><a class="slide-button-prev"></a>
            <ul class="slide-paginator">
                <li class="slide-paginator-item active" data-index="0">●</li>
                <li class="slide-paginator-item " data-index="1">●</li>
                <li class="slide-paginator-item " data-index="2">●</li>
                <li class="slide-paginator-item " data-index="3">●</li>
                <li class="slide-paginator-item " data-index="4">●</li>
            </ul>
        </div>
    </div>


    <!--商品展示开始-->

    <div class="homepage-hot-items grid" id="homePageWidget-2">

        <%
            for (Product p : products) {

        %>

        <div class="grid-item">
            <div class="product-item">
                <div class="p-img">
                    <a href="buy.jsp?action=add&id=<%=p.getId()%> "
                       target="_blank"
                       title=<%=p.getName()%>>

                        <img src="images/product/<%=p.getId()%>.jpg"/>
                    </a>
                </div>
                <div class="p-title">
                    <a href="register.jsp"
                       target="_blank">
                        <%=p.getName()%>
                    </a>
                </div>
                <div class="p-price">
                    <i class="price"><%=p.getNormalPrice()%>
                    </i>
                </div>
            </div>
        </div>
        <%
            }
        %>
        <!--tuijian kaishi -->
    </div>


    <!--[if gte IE 9]><!-->
    <script src="./index/modern.js"></script>
    <!--<![endif]-->

    <script type="text/javascript" src="./index/1c7e29b07f138b4f.js"></script>
    <script>var _SPLITTEST = ""</script>


</body>
</html>