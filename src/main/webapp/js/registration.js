(function () {
    'use strict';

    var inputLogin = document.querySelector('.email-input');
    var errorMessageLogin = document.getElementById('input-error-login');
    var inputPassword = document.querySelector('.pass-input');
    var errorMessagePassword = document.getElementById('input-error-pass');
    var submit = document.getElementById('btn-reg-id');
    var form = document.getElementById('form-submit');


    inputLogin.addEventListener("focusout", checkLogin);
    inputPassword.addEventListener("focusout", checkPassword);
    form.addEventListener("submit",validSubmit);

    function checkLogin(){
        var status = true;
        var pattern = ".+@.+\\..+";
        if(!inputLogin.value.length){
            inputLogin.classList.add("input-alarm-border");
            errorMessageLogin.classList.add("input-span-alarm");
            errorMessageLogin.innerHTML = "This field is required";
            status = false;
        }
        else if(inputLogin.value.search(pattern) < 0){
            inputLogin.classList.add("input-alarm-border");
            errorMessageLogin.classList.add("input-span-alarm");
            errorMessageLogin.innerHTML = "The email address you entered does not represent a valid email address.";
            status = false;
        }
        else{
            inputLogin.classList.remove("input-alarm-border");
            errorMessageLogin.classList.remove("input-span-alarm");
        }
        return status;
    }

    function checkPassword() {
        var status = true;
        var patternPass = '(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}';
        if (!inputPassword.value.length) {
            inputPassword.classList.add("input-alarm-border");
            errorMessagePassword.classList.add("input-span-alarm");
            errorMessagePassword.innerHTML = "This field is required";
            status = false;

        }
        else if(inputPassword.value.search(patternPass) < 0){
            inputPassword.classList.add("input-alarm-border");
            errorMessagePassword.classList.add("input-span-alarm");
            errorMessagePassword.innerHTML = "Password not strong enough.";
            status = false;
        }
        else {
            inputPassword.classList.remove("input-alarm-border");
            errorMessagePassword.classList.remove("input-span-alarm");
        }
        return status;
    }

    function validSubmit() {
        if(checkPassword() && checkLogin()){
            return true;
        }
        else {
            event.preventDefault()
        }
    }
})();
