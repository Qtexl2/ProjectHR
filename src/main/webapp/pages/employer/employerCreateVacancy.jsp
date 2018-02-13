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
            <%@include file="employerHeader.jsp" %>
            <main id="vacancy-main">
                <section class="vacancy-result section-vacancy">
                    <form method="post" action="/controller">
                        <input name="command" value="createVacancy" hidden>
                        <div class="field-vacancy">
                            <h1 class="h1-create-vac"><fmt:message key="createNewVacancy"/></h1>
                            <label class="error-label-vac">${param.message}</label>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="position"/> </label>
                                <input class="vacancy-input" required pattern="^[\wа-яёА-ЯЁ\s-]{1,140}$" name="position" type="text">
                            </div>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="city"/> </label>
                                <input class="vacancy-input" required pattern="^[\wа-яёА-ЯЁ\s-]{1,40}$" name="city" type="text">
                            </div>
                            <div class="wrap-input">
                                <label class="vacancy-label"><fmt:message key="companyName"/> </label>
                                <input class="vacancy-input" required pattern="^[\wа-яёА-ЯЁ\s-]{1,95}$" name="companyName" type="text">
                            </div>
                            <div class="wrap-input">
                                <label id="label-actual-vac" class="vacancy-label"><fmt:message key="actualVac"/> </label>
                                <input id="checkbox-actual-vac" name="actualVac" class="vacancy-input"  type="checkbox">
                            </div>
                            <div class="wrap-textarea">
                                <label class="vacancy-label" ><fmt:message key="positionDesc"/> </label>
                                <textarea class="vacancy-textarea" required name="positionDescription"></textarea>
                            </div>
                            <button type="submit" class="btn btn-respond"><fmt:message key="create"/></button>
                        </div>
                    </form>
                </section>
            </main>
            <%@include file="/WEB-INF/jspf/footer.jspf" %>
        </div>
    </body>
</html>