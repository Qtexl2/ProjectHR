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
    <link rel="stylesheet" href="../../css/interview.css">
</head>
<body>
<div class="wrapper">
    <%@include file="employerHeader.jsp" %>
    <main>
        <section class="interview-section">
            <div class="wrapper-interview">
                <div class="interview-input-data">
                    <h1 class="title-interview"><fmt:message key="createInterview"/> </h1>
                    <c:if test="${not empty param.message}">
                        <label class="error-label-interview">${param.message}</label>
                    </c:if>
                    <form class="interview-form" method="post" action="/controller?command=createInterview">
                        <input name="id" value="${param.id}" hidden>
                        <div class="left-block-interview">
                            <div class="wrapper-input-interview">
                                <label class="interview-label" ><fmt:message key="date"/> </label>
                                <input  name="date" type="datetime-local"/>
                            </div>
                            <div class="wrapper-input-interview">
                                <label class="interview-label"><fmt:message key="type"/> </label>
                                <select name="type" class= "list-type-interview">
                                    <option value="common"><fmt:message key="interview"/> </option>
                                    <option value="technical"><fmt:message key="techInterview"/> </option>
                                </select>
                            </div>
                            <div class="wrapper-input-interview wrapper-textarea-interview">
                                <label class="interview-label"><fmt:message key="descriptionInterview"/> </label>
                                <textarea name="description" class="interview-describe"></textarea>
                            </div>
                            <input type="submit" class="btn-interview"  value="<fmt:message key="create"/>">
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