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
    <%@include file="header.jsp"%>
    <main id="vacancy-main">
        <c:set value="${vacancy}" var="vac" scope="request"/>
        <section class="vacancy-result section-vacancy">
            <div class="field-vacancy">
                <h1><fmt:message key="aboutTheJob"/></h1>
                <h2><fmt:message key="positionDescription"/>${vac.vacancyTitle} <fmt:message key="at"/> ${vac.company}</h2>
                <p>${vac.vacancyDescription}</p>
                <form action="/controller" method="post">
                    <input type="text" name="controller" value="vacancyRespond" hidden>
                    <button type="submit" class="btn" name="idVac" value="${vac.vacancyID}"><fmt:message key="respond"/></button>
                </form>

            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>
</body>
</html>