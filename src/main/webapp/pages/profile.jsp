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
    <%@include file="header.jsp"%>
    </header>
    <main class="main-profile">
        <section class="profile-section">
            <div class="wrapper-profile">
                <h1>Профиль</h1>
                <form class="profile-form" method="post" action="/controller" >
                    <div class="profile-div-image">
                        <img class="profile-img-photo" src="https://dayonline.ru/public/wysiwyg/images/11(6).jpg" alt="">
                        <button type="submit" class="btn-profile-update-photo">Загрузить фото</button>
                    </div>
                    <div class="profile-input-data">
                        <div class="profile-div-firstName profile-div">
                            <label for="profile-input-firstName">Имя</label>
                            <input id="profile-input-firstName" type="text" name="firstName"/>
                        </div>
                        <div class="profile-div-firstName profile-div">
                            <label for="profile-input-lastName">Фамилия </label>
                            <input id="profile-input-lastName" type="text" name="lastName"/>
                        </div>
                        <div class="profile-div-phone profile-div">
                            <label for="profile-input-phone">Телефон</label>
                            <input id="profile-input-phone" type="text" name="phone"/>
                        </div>
                        <div class="profile-div-age profile-div">
                            <label for="profile-input-age">Возраст </label>
                            <input id="profile-input-age" type="number" name="age"/>
                        </div>
                        <div class="profile-div-sex profile-div">
                            <label>Пол </label>
                            <label for="profile-label-male">Мужской</label>
                            <input id="profile-label-male" class="profile-input-age" type="radio" name="sex"/>
                            <label for="profile-label-female">Женский</label>
                            <input id="profile-label-female" class="profile-input-age" type="radio" name="sex"/>
                        </div>
                        <div class="profile-div-position profile-div">
                            <label for="profile-input-position">Текущая позиция</label>
                            <input id="profile-input-position" type="text" name="position"/>
                        </div>
                        <div class="profile-div-company profile-div">
                            <label for="profile-input-company">Компания</label>
                            <input id="profile-input-company" type="text" name="company"/>
                        </div>
                    </div>
                    <div class="profile-div-describe profile-div">
                        <label for="profile-input-describe">О себе</label>
                        <textarea id="profile-input-describe" name="describe"></textarea>
                    </div>
                    <button type="submit" class="btn-profile-update">Обновить профиль</button>

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