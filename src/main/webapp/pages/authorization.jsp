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
</head>
<body>
<div class="wrapper">
    <%@include file="header.jsp"%>
    </header>
    <main class="main-registration">
        <section class="registration-section">
            <div class="form-reg">
                <form class="auth-form" method="post" action="/controller" >
                    <input id="command-reg" type="hidden" name="command" value="auth"/>
                    <h1 id="art-create-acc"><fmt:message key="signInAccount"/> </h1>
                    <div class="email reg">
                        <label class="reg-label" for="email-address-id"><fmt:message key="email"/></label>
                        <input type="text"  class="email-input reg-input" name="email"  id="email-address-id"  placeholder="<fmt:message key="email"/>" maxlength="50" required>
                        <i class="login-icon icon-input"></i>
                    </div>
                    <div class="pass reg">
                        <label class="reg-label" for="pass-id"><fmt:message key="password"/> </label>
                        <input type="password" class="pass-input reg-input" name="password" id="pass-id" placeholder="<fmt:message key="password"/>" maxlength="30" required>
                        <i class="pass-icon icon-input"></i>
                        <span class="input-error" id="span-incorrect-login-pass"><fmt:message key="required"/> </span>
                    </div>
                    <div id="submit-div">
                        <button type="submit" class="btn-reg"><fmt:message key="signIn"/> </button>
                    </div>
                    <c:if test="${not empty message}">
                        <h1 id="auth-error-message"><fmt:message key="incorrectLoginOrPassword"/></h1>
                    </c:if>
                    <h2><fmt:message key="signUp"/> </h2>
                </form>
            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>

</div>
    <%--<script src="../js/jquery-1.9.0.js"></script>--%>
    <%--<script src="../js/registration.js"></script>--%>
</body>
</html>