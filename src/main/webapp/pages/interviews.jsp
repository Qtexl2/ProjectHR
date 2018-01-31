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
    <link rel="stylesheet" href="../css/vacancy.css">
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
    <main>
        <section class="vacancy-result">
            <c:forEach var="vacancy" items="${vacancies}">
                <div class="vacancy-result-div">
                    <a class="vacancy-title"  target="_blank" href="/controller?command=vacancy&vacancyId=${vacancy.vacancyID}">${vacancy.vacancyTitle}</a>
                    <div>
                        <p>${vacancy.company}</p>
                        <i class="location-icon"></i>
                        <span class="vacancy-location">${vacancy.location}</span>
                    </div>
                </div>
            </c:forEach>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>
</body>
</html>