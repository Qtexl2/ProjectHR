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
    <link rel="stylesheet" href="../css/interview.css">
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
        <section class="interview-section">
            <div class="wrapper-interview">
                <div class="interview-input-data">
                    <h1 class="title-interview">СОздать интеврью</h1>
                    <%--<label class="error-label-interview"> Error</label>--%>
                    <form class="interview-form" method="post">
                        <div class="left-block-interview">
                            <div class="wrapper-input-interview">
                                <label class="interview-label" > Дата</label>
                                <input  name="date" type="datetime-local"/>
                            </div>
                            <div class="wrapper-input-interview">
                                <label class="interview-label"> Тип</label>
                                <select name="type" class= "list-type-interview">
                                    <option value="1">Собеседование</option>
                                    <option value="2">Тех. собеседование</option>
                                </select>
                            </div>
                            <div class="wrapper-input-interview wrapper-textarea-interview">
                                <label class="interview-label">Описание</label>
                                <textarea name="describe" class="interview-describe"></textarea>
                            </div>
                                <input type="submit" class="btn-interview" value="СОздать">
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>
</body>
</html>