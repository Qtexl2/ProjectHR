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
    <link rel="stylesheet" href="../../css/base.css">
    <link rel="stylesheet" href="../../css/vacancy.css">
</head>
<body>
<div class="wrapper">
    <%@include file="employerHeader.jsp" %>
    <main>
        <section class="vacancy-result">
            <c:forEach var="vacancy" items="${vacancies}">
                <div class="vacancy-result-div">
                    <a class="vacancy-title"  target="_blank" href="/controller?command=vacancyEdit&vacancyId=${vacancy.vacancyId}">${vacancy.vacancyTitle}</a>
                    <a class="delete-vacancy" href="/controller?command=deleteVacancy&vacancyId=${vacancy.vacancyId}"></a>
                    <div>
                        <p>${vacancy.company}</p>
                        <i class="location-icon"></i>
                        <span class="vacancy-location">${vacancy.location}</span>
                    </div>
                </div>
            </c:forEach>
        </section>
    </main>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>