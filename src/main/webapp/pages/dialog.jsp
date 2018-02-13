<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set  var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale.language}" scope="session" />
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="text"/>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="company"/></title>
    <link rel="stylesheet" href="../css/base.css">
    <link rel="stylesheet" href="../css/dialog.css">
</head>
<body>
<div class="wrapper">
    <c:choose>
        <c:when test="${sessionScope.profile.role eq 'EMPLOYER'}">
            <%@include file="employer/employerHeader.jsp" %>
        </c:when>
        <c:when test="${sessionScope.profile.role eq 'ADMIN'}">
            <%@include file="admin/adminHeader.jsp" %>
        </c:when>
        <c:otherwise>
            <%@include file="header.jsp" %>
        </c:otherwise>
    </c:choose>
    </header>
    <main class="main-dialog">
        <section class="dialog-section">
            <div class="wrapper-dialog">
                <div class="menu-dialog">
                    <c:forEach var="prof" items="${dialogs}">
                        <div class="dialog-field"  id = "dialog-profile-${prof.profileId}" about="${prof.profileId}">
                            <c:choose>
                                <c:when test="${not empty prof.photo}">
                                    <img class="navbar-profile-icon dialog-img" src="/images?id=${prof.profileId}"/>
                                </c:when>
                                <c:otherwise>
                                    <img class="navbar-profile-icon dialog-img" src="../images/avatar.svg" />
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
                        <textarea id="output-text"></textarea>
                        <button id="send-message" value=""><fmt:message key="send"/></button>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>
</div>

</div>
<script src="../js/jquery-1.9.0.js"></script>
<script src="../js/dialog.js"></script>
</body>
</html>