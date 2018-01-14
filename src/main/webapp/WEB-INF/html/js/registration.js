(function () {
    'use strict';

    var inputLogin = document.querySelector('.email-input');
    var errorMessageLogin = document.getElementById('input-error-login');
    // var errorMessageLoginMatch = document.getElementById('input-error-login-match');
    var inputPassword = document.querySelector('.pass-input');
    var errorMessagePassword = document.getElementById('input-error-pass');

    inputLogin.addEventListener("focusout", checkLogin);
    inputPassword.addEventListener("focusout", checkPassword);


    function checkLogin(){
        var pattern = ".+@.+\\..+";
        if(!inputLogin.value.length){
            inputLogin.classList.add("input-alarm-border");
            errorMessageLogin.classList.add("input-span-alarm");
            errorMessageLogin.innerHTML = "This field is required"
        }
        else if(inputLogin.value.search(pattern) < 0){
            inputLogin.classList.add("input-alarm-border");
            errorMessageLogin.classList.add("input-span-alarm");
            errorMessageLogin.innerHTML = "The email address you entered does not represent a valid email address."
        }
        else{
            inputLogin.classList.remove("input-alarm-border");
            errorMessageLogin.classList.remove("input-span-alarm");
        }
    }

    function checkPassword() {
        var patternPass = '(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}';
        if (!inputPassword.value.length) {
            inputPassword.classList.add("input-alarm-border");
            errorMessagePassword.classList.add("input-span-alarm");
            errorMessagePassword.innerHTML = "This field is required";
        }
        else if(inputPassword.value.search(patternPass) < 0){
            inputPassword.classList.add("input-alarm-border");
            errorMessagePassword.classList.add("input-span-alarm");
            errorMessagePassword.innerHTML = "Password not strong enough."
        }


        else {
            inputPassword.classList.remove("input-alarm-border");
            errorMessagePassword.classList.remove("input-span-alarm");

        }
    }
})();
