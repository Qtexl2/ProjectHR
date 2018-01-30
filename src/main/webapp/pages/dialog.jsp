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
    <link rel="stylesheet" href="../css/base.css">
    <link rel="stylesheet" href="../css/dialog.css">
</head>
<body>
<div class="wrapper">
    <%@include file="header.jsp"%>
    </header>
    <main class="main-dialog">
        <section class="dialog-section">
            <div class="wrapper-dialog">
                <div class="menu-dialog">
                    <c:forEach var="prof" items="${dialogs}">
                        <div class="dialog-field"  id = "dialog-profile-${prof.profileID}" about="${prof.profileID}">
                            <c:choose>
                                <c:when test="${not empty prof.photo}">
                                    <img class="navbar-profile-icon dialog-img" src="/images?id=${prof.profileID}"/>
                                </c:when>
                                <c:otherwise>
                                    <img class="navbar-profile-icon dialog-img" src="../images/avatar.svg"/>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${not empty prof.firstName and not empty prof.lastName}">
                                    <label>${prof.firstName} ${prof.lastName}</label>
                                </c:when>
                                <c:otherwise>
                                    <label>${prof.email}</label>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
                <div class="dialog-chat-wrapper">
                    <div class="div-chat">
                        <div class="history-chat">

                        </div>
                        <textarea></textarea>
                        <button>Отправить</button>

                    </div>
                </div>
            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>

</div>
<script src="../js/jquery-1.9.0.js"></script>
<script src="../js/dialog.js"></script>
</body>
</html>