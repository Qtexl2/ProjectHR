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
    <link rel="stylesheet" href="../../css/interview.css">
</head>
<body>
<div class="wrapper">
    <%@include file="employerHeader.jsp" %>
    <main>
        <section class="interview-result">
            <table>
                <tr class="header-field">
                    <th class="field-time"><fmt:message key="time"/></th>
                    <th class="field-type-interview"><fmt:message key="type"/></th>
                    <th class="field-description" colspan="2"><fmt:message key="descriptionInterview"/></th>
                </tr>
            <c:forEach var="interview" items="${interviews}">
                <tr class="item-filed-interview check-item">
                    <td class="field-center">${interview.interviewTime}</td>
                    <c:choose>
                        <c:when test="${interview.interviewType eq 'COMMON'}">
                            <td class="field-center"><fmt:message key="interview"/> </td>
                        </c:when>
                        <c:otherwise>
                            <td class="field-center"><fmt:message key="techInterview"/> </td>
                        </c:otherwise>
                    </c:choose>
                    <td>${interview.interviewDescription}</td>
                    <td class="close-edit">
                        <a href="/controller?command=deleteInterview&id=${interview.interviewID}" class="close-icon-interview"></a>
                        <a href="/controller?command=updateInterviewPage&id=${interview.interviewID}" class="edit-icon-interview"></a>
                    </td>
                </tr>
            </c:forEach>
            </table>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>
</body>
</html>