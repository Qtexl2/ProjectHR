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
    <link rel="stylesheet" href="../../css/base.css">
    <link rel="stylesheet" href="../../css/vacancy.css">
</head>
    <body>
        <div class="wrapper">
            <%@include file="employerHeader.jsp" %>
            <main id="vacancy-main">
                <section class="vacancy-result section-vacancy">
                    <form method="post" action="/controller">
                        <input name="command" value="editVacancy" hidden>
                        <div class="field-vacancy">
                            <h1 class="h1-create-vac"><fmt:message key="updateVacancy"/></h1>
                            <label class="error-label-vac">${param.message}</label>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="position"/> </label>
                                <input class="vacancy-input"  required pattern="^[\wа-яёА-ЯЁ\s-]{1,140}$" name="position" type="text" value="${vacancy.vacancyTitle}">
                            </div>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="city"/> </label>
                                <input class="vacancy-input" required pattern="^[\wа-яёА-ЯЁ\s-]{1,40}$" name="city" type="text" value="${vacancy.location}">
                            </div>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="companyName"/> </label>
                                <input class="vacancy-input" required pattern="^[\wа-яёА-ЯЁ\s-]{1,95}$" name="companyName" type="text" value="${vacancy.company}">
                            </div>
                            <div class="wrap-input">
                                <label id="label-actual-vac" class="vacancy-label"><fmt:message key="actualVac"/> </label>
                                <c:choose>
                                    <c:when test="${vacancy.vacancyStatus eq 'true'}">
                                        <input id="checkbox-actual-vac" name="actualVac" class="vacancy-input" checked type="checkbox">
                                    </c:when>
                                    <c:otherwise>
                                        <input id="checkbox-actual-vac" name="actualVac" class="vacancy-input" type="checkbox">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="wrap-textarea">
                                <label class="vacancy-label" ><fmt:message key="positionDescription"/> </label>
                                <textarea class="vacancy-textarea" required name="positionDescription">${vacancy.vacancyDescription}</textarea>
                            </div>
                            <button type="submit" class="btn btn-respond" name="id" value="${vacancy.vacancyID}"><fmt:message key="updateVacancySubmit"/></button>
                        </div>
                    </form>
                </section>
            </main>
            <footer class="footer-content">
                <div class="bottom-logo"></div>
            </footer>
        </div>
    </body>
</html>