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
    <%@include file="header.jsp"%>
    <main>
        <section class="interview-section">
            <div class="wrapper-interview">
                <div class="interview-input-data">
                    <h1>СОздать интеврью</h1>
                    <form class="interview-form" method="post">
                        <div class="left-block-interview">
                            <select class = "list-profiles"></select>
                            <input type="datetime-local"/>
                            <select class= "list-type-interview">
                                <option>Собеседование</option>
                                <option>Тех. собеседование</option>
                            </select>
                            <textarea class="interview-describe"></textarea>
                            <input type="submit"  value="СОздать">
                        </div>
                        <div class="right-block-interview"></div>
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