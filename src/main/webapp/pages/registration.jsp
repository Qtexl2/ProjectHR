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
    </header>
    <main class="main-registration">
        <section class="registration-section">
            <div class="form-reg">
                    <input id="command-reg" type="hidden" name="command" value="reg"/>
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
            </div>
        </section>
    </main>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>
</div>

</div>
    <script src="../js/jquery-1.9.0.js"></script>
    <script src="../js/registration.js"></script>
</body>
</html>