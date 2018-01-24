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
                <form method="post" id="form-submit" action="/controller">
                    <input type="hidden" name="command" value="reg"/>
                    <h1 id="art-create-acc"><fmt:message key="createAccount"/> </h1>
                    <div class="email reg">
                        <label class="reg-label" for="email-address-id"><fmt:message key="email"/></label>
                        <input type="text"  class="email-input reg-input" name="email"  id="email-address-id"  placeholder="<fmt:message key="email"/>" maxlength="50" required>
                        <i class="login-icon icon-input"></i>
                        <span class="input-error" id="input-error-login"><fmt:message key="required"/></span>
                        <span class="input-error" id="input-error-login-match"><fmt:message key="validEmail"/> </span>
                    </div>
                    <div class="pass reg">
                        <label class="reg-label" for="pass-id"><fmt:message key="password"/> </label>
                        <input type="password" class="pass-input reg-input" name="password" id="pass-id" placeholder="<fmt:message key="password"/>" maxlength="30" required>
                        <i class="pass-icon icon-input"></i>
                        <span class="input-error" id="input-error-pass"><fmt:message key="required"/> </span>
                    </div>
                    <input type="radio" name="role" class="registration-radio" value="candidate" checked><fmt:message key="candidate"/> </input>
                    <input type="radio" name="role" class="registration-radio" value="employer"><fmt:message key="employer"/> </input>
                    <div id="submit-div">
                        <button type="submit" class="btn-reg" id="btn-reg-id"><fmt:message key="register"/> </button>
                    </div>
                </form>
            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>

</div>
    <script src="../js/registration.js"></script>
</body>
</html>