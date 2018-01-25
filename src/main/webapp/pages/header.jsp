<%@ page import="by.epam.hr.model.Profile" %>
<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<% Profile profile = (Profile)session.getAttribute("profile"); %>
<header class="main-head">
    <div class="mobile-navbar">
        <div class="logo-img"></div>
        <ul class="account">
            <li>
                <a href="#" id="dropdown-toggle" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span id="navbar-profile-icon" ></span>
                </a>

                <ul class="account-menu">
                    <li>
                        <%--<% if( profile == null ){--%>
                            <%--out.write("<a href=#>Аккаунт</a>");--%>
                        <%--} else if(profile.getFirstName() == null || profile.getLastName() == null) {--%>
                            <%--out.write("<a href=#>" + profile.getEmail() + "</a>");--%>
                        <%--}--%>
                        <%--else {--%>
                            <%--out.write("<a href=#>"+ profile.getFirstName() + " " + profile.getLastName() + "</a>");--%>
                        <%--}%>--%>
                            <a href=#>
                        <c:choose>
                            <c:when test="${empty sessionScope.profile}">
                                <fmt:message key="account"/>
                            </c:when>
                            <c:when test="${empty sessionScope.profile.firstName && empty sessionScope.profile.lastName}">
                                <c:out value="${sessionScope.profile.email}" />
                            </c:when>
                            <c:otherwise>
                                <c:out value="${sessionScope.profile.firstName}" />
                                <c:out value="${sessionScope.profile.lastName}" />
                            </c:otherwise>
                        </c:choose>
                            </a>
                        <ul class="account-submenu drop-menu">
                            <li><a href=/controller?command=authPage><fmt:message key="login"/></a></li>
                            <li><a href=/controller?command=regPage><fmt:message key="registration"/></a></li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="main-menu-bar">
        <ul class="main-menu-list">
            <li><a class="main-menu" href="/"><fmt:message key="main"/></a></li>
            <li><a class="main-menu multi" href=#><fmt:message key="findJobs"/></a>
                <ul class="main-submenu drop-menu">
                    <li><a href=#>Advanced Search</a></li>
                    <li><a href=#>Most Popular Jobs</a></li>
                </ul>
            </li>
            <li><a class="main-menu" href=#>Post Resume</a></li>
        </ul>
    </div>
</header>
