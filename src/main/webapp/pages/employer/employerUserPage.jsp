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
    <link rel="stylesheet" href="../../css/profile.css">
</head>
<body>
<div class="wrapper">
    <%@include file="../header.jsp"%>
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
                         <c:when test="${empty user.photo}">src="../../images/defaultAva.svg" </c:when>
                    <c:otherwise> src="/images?id=${user.profileID}" </c:otherwise>
                    </c:choose> alt="">
                </div>
                <form class="profile-form" method="post" action="/controller" >
                    <input name="command" value="userUpdate" hidden>
                    <div class="profile-input-data">
                        <div class="profile-div-firstName profile-div">
                            <label for="profile-input-firstName"><fmt:message key="firstName"/> </label>
                            <input id="profile-input-firstName" class="block" pattern="^[a-zA-Zа-яёА-ЯЁ\s-]{1,45}$" type="text" name="firstName"
                                    <c:if test="${not empty user.firstName}">
                                        value="${user.firstName}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-firstName profile-div">
                            <label for="profile-input-lastName"><fmt:message key="lastName"/></label>
                            <input id="profile-input-lastName" class="block" type="text" pattern="^[a-zA-Zа-яёА-ЯЁ\s-]{1,45}$" name="lastName"
                                    <c:if test="${not empty user.lastName}">
                                        value="${user.lastName}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-phone profile-div">
                            <label for="profile-input-phone"><fmt:message key="phone"/> </label>
                            <input id="profile-input-phone" type="text" class="block" pattern="^\+?[\d -\.]{7,18}$" name="phone"
                                    <c:if test="${not empty user.phone}">
                                        value="${user.phone}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-age profile-div">
                            <label for="profile-input-age"><fmt:message key="age"/> </label>
                            <input id="profile-input-age" type="number" class="block" pattern="\d{1,3}" name="age"
                                    <c:if test="${not empty user.age}">
                                        value="${user.age}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-sex profile-div">
                            <label><fmt:message key="gender"/></label>
                            <c:if test="${not empty user.role}">
                                <c:choose>
                                    <c:when test="${user.gender == 'FEMALE'}">
                                        <label for="profile-label-male"><fmt:message key="male"/> </label>
                                        <input id="profile-label-male"  class="profile-input-gender block" value="male" type="radio" name="sex"/>
                                        <label for="profile-label-female"><fmt:message key="female"/> </label>
                                        <input id="profile-label-female" class="profile-input-gender block" type="radio" value="female" name="sex" checked/>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="profile-label-male"><fmt:message key="male"/> </label>
                                        <input id="profile-label-male" class="profile-input-age block" type="radio" value="male" name="sex" checked/>
                                        <label for="profile-label-female"><fmt:message key="female"/> </label>
                                        <input id="profile-label-female" class="profile-input-age block" type="radio" value="female" name="sex"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                        </div>
                        <div class="profile-div-position profile-div">
                            <label for="profile-input-position"><fmt:message key="currentPosition"/> </label>
                            <input id="profile-input-position"  class="block" type="text" pattern="^[\w\dа-яёА-ЯЁ\s-]{1,99}$" name="position"
                                    <c:if test="${not empty user.currentPosition}">
                                        value="${user.currentPosition}"
                                    </c:if>
                            />
                        </div>
                        <div class="profile-div-company profile-div">
                            <label for="profile-input-company"><fmt:message key="currentCompany"/> </label>
                            <input id="profile-input-company" class="block" type="text" pattern="^[\w\dа-яёА-ЯЁ\s-]{1,99}$" name="company"
                            <c:if test="${not empty user.company}">
                                   value="${user.company}"
                            </c:if>
                        </div>
                    </div>
                    <div class="profile-div-describe profile-div">
                        <label for="profile-input-describe"><fmt:message key="aboutMyself"/></label>
                        <c:choose>
                            <c:when test="${not empty user.describe}">
                                <textarea id="profile-input-describe" class="block profile-input" name="describe">${user.describe}</textarea>
                            </c:when>
                            <c:otherwise>
                                <textarea id="profile-input-describe" class="block profile-input" name="describe"></textarea>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label for="profile-input-level"><fmt:message key="level"/>
                    <input id="profile-input-level" type="text" pattern="^(A1|A2|B1|B2|C1|C2)$" value="${user.englishLevel}" name="level">

                    </label>
                    <div class="profile-div-preInterview profile-div">
                        <label for="profile-input-preInterview"><fmt:message key="resultPre"/></label>
                        <textarea id="profile-input-preInterview" class="profile-input" name="preInterview">${user.preInterview}</textarea>
                    </div>

                    <div class="profile-div-technicalInterview profile-div">
                        <label for="profile-input-technicalInterview"><fmt:message key="resultTech"/></label>
                        <textarea id="profile-input-technicalInterview" class="profile-input" name="technicalInterview">${user.technicalInterview}</textarea>
                    </div>
                    <a id="btn-interview-page" href="/controller?command=interview&id=${user.profileID}"><fmt:message key="setUpInterview"/> </a>
                    <button type="submit" id="btn-profile-update-id" class="btn-profile-update" name="id" value="${user.profileID}"><fmt:message key="update"/> </button>
                </form>
            </div>
        </section>
    </main>
    <footer class="footer-content">
        <div class="bottom-logo"></div>
    </footer>
</div>

</div>
</body>
</html>