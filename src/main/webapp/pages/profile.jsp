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
    <link rel="stylesheet" href="../css/profile.css">
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
    <main class="main-profile">
        <section class="profile-section">
            <div class="wrapper-profile">
                <h1><fmt:message key="profile"/></h1>
                    <c:choose>
                        <c:when test="${param.messageStatus == 'true'}">
                            <c:if test="${not empty param.message}">
                                <h2 id="profile-input-error">(${param.message})</h2>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${not empty param.message}">
                                <h2 id="profile-input-success">(${param.message})</h2>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile-div-image">
                        <img class="profile-img-photo"
                            <c:choose>
                                <c:when test="${empty sessionScope.profile.photo}">src="../images/defaultAva.svg" </c:when>
                                <c:otherwise> src="/images?id=${sessionScope.profile.profileID}" </c:otherwise>
                            </c:choose> alt="">
                        <div id="profile-input-wrapper">
                            <input type="file" class="btn-profile-update-photo" value="<fmt:message key="uploadPhoto"/> "/>
                        </div>
                        <h2 id="profile-h2-upload"><fmt:message key="fileToolarge"/></h2>
                    </div>
                <form class="profile-form" method="post" action="/controller" >
                    <input name="command" value="profileUpdate" hidden>
                    <div class="profile-input-data">
                        <div class="profile-div-firstName profile-div">
                            <label for="profile-input-firstName"><fmt:message key="firstName"/> </label>
                            <input id="profile-input-firstName" pattern="^[a-zA-Zа-яёА-ЯЁ\s-]{1,45}$" type="text" name="firstName"
                                <c:if test="${not empty sessionScope.profile.firstName}">
                                       value="${sessionScope.profile.firstName}"
                                </c:if>
                            />
                        </div>
                        <div class="profile-div-firstName profile-div">
                            <label for="profile-input-lastName"><fmt:message key="lastName"/></label>
                            <input id="profile-input-lastName" type="text" pattern="^[a-zA-Zа-яёА-ЯЁ\s-]{1,45}$" name="lastName"
                                    <c:if test="${not empty sessionScope.profile.lastName}">
                                        value="${sessionScope.profile.lastName}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-phone profile-div">
                            <label for="profile-input-phone"><fmt:message key="phone"/> </label>
                            <input id="profile-input-phone" type="text" pattern="^\+?[\d -\.]{7,18}$" name="phone"
                                    <c:if test="${not empty sessionScope.profile.phone}">
                                        value="${sessionScope.profile.phone}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-age profile-div">
                            <label for="profile-input-age"><fmt:message key="age"/> </label>
                            <input id="profile-input-age" type="number" pattern="\d{1,3}" name="age"
                                    <c:if test="${not empty sessionScope.profile.age}">
                                        value="${sessionScope.profile.age}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-sex profile-div">
                            <label><fmt:message key="gender"/></label>
                            <c:if test="${not empty sessionScope.profile.role}">
                                <c:choose>
                                    <c:when test="${sessionScope.profile.gender == 'FEMALE'}">
                                        <label for="profile-label-male"><fmt:message key="male"/> </label>
                                        <input id="profile-label-male" class="profile-input-gender" value="male" type="radio" name="sex"/>
                                        <label for="profile-label-female"><fmt:message key="female"/> </label>
                                        <input id="profile-label-female" class="profile-input-gender" type="radio" value="female" name="sex" checked/>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="profile-label-male"><fmt:message key="male"/> </label>
                                        <input id="profile-label-male" class="profile-input-age" type="radio" value="male" name="sex" checked/>
                                        <label for="profile-label-female"><fmt:message key="female"/> </label>
                                        <input id="profile-label-female" class="profile-input-age" type="radio" value="female" name="sex"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                        </div>
                        <div class="profile-div-position profile-div">
                            <label for="profile-input-position"><fmt:message key="currentPosition"/> </label>
                            <input id="profile-input-position" type="text" pattern="^[\w\dа-яёА-ЯЁ\s-]{1,99}$" name="position"
                                    <c:if test="${not empty sessionScope.profile.currentPosition}">
                                        value="${sessionScope.profile.currentPosition}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-company profile-div">
                            <label for="profile-input-company"><fmt:message key="currentCompany"/> </label>
                            <input id="profile-input-company" type="text" pattern="^[\w\dа-яёА-ЯЁ\s-]{1,99}$" name="company"
                                    <c:if test="${not empty sessionScope.profile.company}">
                                           value="${sessionScope.profile.company}"
                                    </c:if>
                        </div>
                    </div>
                    <div class="profile-div-describe profile-div">
                        <label for="profile-input-describe"><fmt:message key="aboutMyself"/></label>

                        <c:choose>
                            <c:when test="${not empty sessionScope.profile.describe}">
                                <textarea id="profile-input-describe" class="profile-input-describe" name="describe">${sessionScope.profile.describe}</textarea>
                            </c:when>
                            <c:otherwise>
                                <textarea id="profile-input-describe" class="profile-input-describe" name="describe"></textarea>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <button type="submit" id="btn-profile-update-id" class="btn-profile-update"><fmt:message key="update"/> </button>
                </form>
            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>

</div>
<script src="../js/jquery-1.9.0.js"></script>
<script src="../js/profile.js"></script>
</body>
</html>