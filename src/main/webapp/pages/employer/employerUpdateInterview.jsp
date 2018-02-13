<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>
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
                    <h1 class="title-interview"><fmt:message key="updateInterviewTitle"/> </h1>
                    <c:if test="${not empty param.message}">
                        <label class="error-label-interview">${param.message}</label>
                    </c:if>
                    <form class="interview-form" method="post" action="/controller?command=updateInterview">
                        <input name="id" value="${interview.interviewId}" hidden>
                        <div class="left-block-interview">
                            <div class="wrapper-input-interview">
                                <label class="interview-label" ><fmt:message key="date"/> </label>
                                <input  name="date" type="datetime-local" value="${fn:replace(interview.interviewTime,' ' ,'T') }"/>
                            </div>
                            <div class="wrapper-input-interview">
                                <label class="interview-label"><fmt:message key="type"/></label>
                                <c:choose>
                                    <c:when test="${interview.interviewType eq 'TECHNICAL'}">
                                        <select name="type" class= "list-type-interview">
                                            <option value="common"><fmt:message key="interview"/> </option>
                                            <option value="technical" selected><fmt:message key="techInterview"/> </option>
                                        </select>
                                    </c:when>
                                    <c:otherwise>
                                        <select name="type" class= "list-type-interview">
                                            <option value="common" selected><fmt:message key="interview"/> </option>
                                            <option value="technical"><fmt:message key="techInterview"/> </option>
                                        </select>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="wrapper-input-interview wrapper-textarea-interview">
                                <label class="interview-label"><fmt:message key="descriptionInterview"/> </label>
                                <textarea name="description" class="interview-describe">${interview.interviewDescription}</textarea>
                            </div>
                            <input type="submit" class="btn-interview"  value="<fmt:message key="updateInterview"/>">
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </main>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>