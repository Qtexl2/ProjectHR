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
                    <%@include file="employerHeader.jsp" %>
                </c:when>
                <c:when test="${sessionScope.profile.role eq 'ADMIN'}">
                    <%@include file="../admin/adminHeader.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@include file="../header.jsp" %>
                </c:otherwise>
            </c:choose>
            <main id="vacancy-main">
                <section class="vacancy-result section-vacancy">
                    <form method="post" action="/controller">
                        <input name="command" value="createVacancy" hidden>
                        <div class="field-vacancy">
                            <h1><fmt:message key="createNewVacancy"/></h1>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="position"/> </label>
                                <input class="vacancy-input" name="position" type="text">
                            </div>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="city"/> </label>
                                <input class="vacancy-input" name="city" type="text">
                            </div>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="companyName"/> </label>
                                <input class="vacancy-input" name="companyName" type="text">
                            </div>
                            <div class="wrap-input">
                                <label id="label-actual-vac" class="vacancy-label"><fmt:message key="actualVac"/> </label>
                                <input id="checkbox-actual-vac" name="actualVac" class="vacancy-input"  type="checkbox">
                            </div>
                            <div class="wrap-textarea">
                                <label class="vacancy-label" ><fmt:message key="positionDescription"/> </label>
                                <textarea class="vacancy-textarea" name="positionDescription"></textarea>
                            </div>
                            <button type="submit" class="btn btn-respond"><fmt:message key="create"/></button>
                        </div>
                    </form>
                </section>
            </main>
            <footer class="footer-content">
                <div class="bottom-logo"></div>
            </footer>
        </div>
    <script src="../js/jquery-1.9.0.js"></script>
    <%--<script src="../js/vacancy.js"></script>--%>
    </body>
</html>