<!DOCTYPE html>

<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<html lang="${language}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="company"/></title>
        <link rel="stylesheet" href="css/base.css">
    </head>
    <body>
        <div class="wrapper">
            <%@include file="pages/header.jsp" %>
        <%--<header class="main-head">--%>
            <%--<div class="mobile-navbar">--%>
                <%--<div class="logo-img"></div>--%>
                <%--<ul class="account">--%>
                    <%--<li>--%>
                        <%--<a href="#" id="dropdown-toggle" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
                            <%--<span id="navbar-profile-icon" ></span>--%>
                        <%--</a>--%>
                        <%--<ul class="account-menu">--%>
                            <%--<li><a href=#><fmt:message key="account"/></a>--%>
                                <%--<ul class="account-submenu drop-menu">--%>
                                    <%--<li><a href=#><fmt:message key="login"/></a></li>--%>
                                    <%--<li><a href=pages/registration.jsp><fmt:message key="registration"/></a></li>--%>
                                <%--</ul>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</div>--%>
            <%--<div class="main-menu-bar">--%>
                <%--<ul class="main-menu-list">--%>
                    <%--<li><a class="main-menu" href=#><fmt:message key="main"/></a></li>--%>
                    <%--<li><a class="main-menu multi" href=#><fmt:message key="findJobs"/></a>--%>
                        <%--<ul class="main-submenu drop-menu">--%>
                            <%--<li><a href=#>Advanced Search</a></li>--%>
                            <%--<li><a href=#>Most Popular Jobs</a></li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                    <%--<li><a class="main-menu" href=#>Post Resume</a></li>--%>

                <%--</ul>--%>
            <%--</div>--%>
        <%--</header>--%>
        <main class="main-page-picture">
            <section class="search-section">
                <h2 class="text-center"><fmt:message key="weCanHelp"/></h2>
                <div class="search-section-form-group">
                    <form class="form-inline" action="/controller" method="get">
                        <input type="hidden" name="command" value="searchJob" >
                        <div class="jobs-keyword keyword">
                            <div class="white-background">
                                <div class="jobs-keyword-icon"></div>
                            </div>
                            <input type="text" name="job" class="job-input input-style" id="q" placeholder="<fmt:message key="jobName"/>"  maxlength="250" spellcheck="false">
                            <div class="white-background wb-clear" id="wb-clear-icon-job">
                                <div class="clear-icon-job clearSwitchOff icon"></div>
                            </div>
                        </div>
                        <div class="where-keyword keyword">
                            <div class="white-background">
                                <div class="where-keyword-icon"></div>
                            </div>
                            <input type="text" name="location" class="where-input input-style" id="q2" placeholder="<fmt:message key="location"/>"  maxlength="250" spellcheck="false">
                            <div class="white-background wb-clear" id="wb-clear-icon-local">
                                <div class="clear-icon-where clearSwitchOff icon"></div>
                            </div>
                        </div>
                        <button type="submit" class="btn"><fmt:message key="search"/></button>
                    </form>
                </div>
            </section>
        </main>
        <footer class="footer-content">
            <div class="bottom-logo"></div>
        </footer>
        </div>
        <script src="js/front.js"></script>
    </body>
</html>