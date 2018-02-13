<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" errorPage="pages/error/404page.jsp" isELIgnored="false" contentType="text/html; UTF-8" %>
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
        <link rel="stylesheet" href="css/base.css">
    </head>
    <body>
        <label>${pageContext.getAttribute('lang')}</label>
        <label>${requestScope.lang}</label>

        <div class="wrapper">
        <c:choose>
            <c:when test="${sessionScope.profile.role eq 'EMPLOYER'}">
                <%@include file="pages/employer/employerHeader.jsp" %>
            </c:when>
            <c:when test="${sessionScope.profile.role eq 'ADMIN'}">
                <%@include file="pages/admin/adminHeader.jsp" %>
            </c:when>
            <c:otherwise>
                <%@include file="pages/header.jsp" %>
            </c:otherwise>
        </c:choose>
        <main class="main-page-picture">
            <section class="search-section">
                <h2 class="text-center"><fmt:message key="weCanHelp"/></h2>
                <div class="search-section-form-group">
                    <form class="form-inline" action="/controller" method="get">
                        <input type="hidden" name="command" value="searchJob" >
                        <div class="jobs-keyword keyword">
                            <div class="white-background">
                                <div class="jobs-keyword-icon"></div>
                            </div>
                            <input type="text" name="job" class="job-input input-style" id="q" placeholder="<fmt:message key="jobName"/>"  maxlength="250" spellcheck="false">
                            <div class="white-background wb-clear" id="wb-clear-icon-job">
                                <div class="clear-icon-job clearSwitchOff icon"></div>
                            </div>
                        </div>
                        <div class="where-keyword keyword">
                            <div class="white-background">
                                <div class="where-keyword-icon"></div>
                            </div>
                            <input type="text" name="location" class="where-input input-style" id="q2" placeholder="<fmt:message key="location"/>"  maxlength="250" spellcheck="false">
                            <div class="white-background wb-clear" id="wb-clear-icon-local">
                                <div class="clear-icon-where clearSwitchOff icon"></div>
                            </div>
                        </div>
                        <button type="submit" class="btn"><fmt:message key="search"/></button>
                    </form>
                </div>
            </section>
        </main>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
        </div>
        <script src="js/front.js"></script>
    </body>
</html>