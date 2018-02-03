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
    <%@include file="adminHeader.jsp" %>
    <main>
        <section class="interview-result">
            <table class="user-table-wrapper">
                <tr class="user-table">
                    <th class="user-icon-check"><span class="user-icon-check-i user-icons" ></span></th>
                    <th class="user-email">email</th>
                    <th class="user-password">password</th>
                    <th class="user-role" >role</th>
                    <th class="user-profile" ><fmt:message key="profile"/></th>
                    <th class="user-action" >Action</th>
                </tr>
                <tr class="user-menu">
                    <td colspan="6">
                        <span class="user-icon-i-delete user-icons"></span>
                        <span class="user-icon-i-add user-icons"></span>
                    </td>
                </tr>
                <c:forEach var="profile" items="${profiles}">
                    <tr class="user-item">
                        <td><input type="checkbox"></td>
                        <td class="user-td-email">${profile.email}</td>
                        <td class="user-td-password"><input type="password" value="**********"></td>
                        <td class="user-td-role">
                            <c:choose>
                                <c:when test="${profile.role eq 'ADMIN'}">
                                    <select name="type" class= "user-type-role">
                                        <option value="candidate"><fmt:message key="candidate"/></option>
                                        <option value="employer"><fmt:message key="employer"/></option>
                                        <option value="admin" selected><fmt:message key="admin"/></option>
                                    </select>
                                </c:when>
                                <c:when test="${profile.role eq 'EMPLOYER'}">
                                    <select name="type" class= "user-type-role">
                                        <option value="candidate"><fmt:message key="candidate"/></option>
                                        <option value="employer" selected><fmt:message key="employer"/></option>
                                        <option value="admin"><fmt:message key="admin"/></option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select name="type" class= "user-type-role">
                                        <option value="candidate" selected><fmt:message key="candidate"/></option>
                                        <option value="employer"><fmt:message key="employer"/></option>
                                        <option value="admin"><fmt:message key="admin"/></option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="user-td-profile"><a class="user-icon-profile user-icons" href="#"></a></td>
                        <td class="user-id-action"><a class="user-icon-action user-icons" href="#"></a></td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>
    <script src="../../js/jquery-1.9.0.js"></script>
    <script src="../../js/user.js"></script>
</body>
</html>