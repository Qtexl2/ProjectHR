<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" isErrorPage="true"%>
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
            <%@include file="../employer/employerHeader.jsp" %>
        </c:when>
        <c:when test="${sessionScope.profile.role eq 'ADMIN'}">
            <%@include file="../admin/adminHeader.jsp" %>
        </c:when>
        <c:otherwise>
            <%@include file="../header.jsp" %>
        </c:otherwise>
    </c:choose>
    <main id="error-page-main">
        <section>
            <div id="error-page-div">
                <h1><fmt:message key="notFound"/> </h1>
                <h2><fmt:message key="youCanFind"/> </h2>
                <h2><fmt:message key="returnMain"/> </h2>
            </div>
        </section>
    </main>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>