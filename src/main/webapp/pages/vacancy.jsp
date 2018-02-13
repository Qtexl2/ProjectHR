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
            <main id="vacancy-main">
                <c:set value="${vacancy}" var="vac" scope="request"/>
                <section class="vacancy-result section-vacancy">
                    <div class="field-vacancy">
                        <h1><fmt:message key="aboutTheJob"/></h1>
                        <h2><fmt:message key="positionDescription"/>${vac.vacancyTitle} <fmt:message key="at"/> ${vac.company}</h2>
                        <p>${vac.vacancyDescription}</p>
                        <button type="submit" class="btn btn-respond" name="idVac" value="${vac.vacancyId}"><fmt:message key="respond"/></button>
                    </div>
                </section>
            </main>
            <%@include file="/WEB-INF/jspf/footer.jspf" %>
        </div>
    <script src="../js/jquery-1.9.0.js"></script>
    <script src="../js/vacancy.js"></script>
    </body>
</html>