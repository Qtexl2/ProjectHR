(function () {
    'use strict';
    //
    // var firstName = document.getElementById('profile-input-firstName');
    // var lastName = document.getElementById('profile-input-lastName');
    // var phone = document.getElementById('profile-input-phone');
    // var age = document.getElementById('profile-input-age');
    // var position = document.getElementById('profile-input-position');
    // var company = document.getElementById('profile-input-company');
    // var gender = document.getElementById('.profile-input-gender');
    // var description = document.getElementById('profile-input-describe');
    //
    submit.addEventListener("click",function () {
        alert(sessionStorage.getItem('profile'));
        alert(sessionStorage.getItem('profile.profileID'));
            // var firstName = $('#profile-input-firstName');
            // var lastName = $('#profile-input-lastName');
            // var phone = $('#profile-input-phone');
            // var age = $('#profile-input-age');
            // var position = $('#profile-input-position');
            // var company = $('#profile-input-company');
            // var gender = $('.profile-input-gender');
            // var description = $('#profile-input-describe');
            // var id = $.session
            //
            // $.ajax({
            //     type: 'POST',
            //     data: {command: "profileUpdate", firstName: firstName.val(), lastName: lastName.val(), phone: phone.val(),
            //             age: age, position: position, company: company, gender: gender, description: description},
            //     url: '/controller',
            //     success: function (data) {
            //         alert("Пришел ответ");
            //         // data = JSON.parse(data);
            //         // message.innerHTML = data[0];
            //         // password.val('');
            //         // email.val('');
            //         // if (data[1] === "true") {
            //         //     message.classList.add("input-span-success");
            //         //     message.classList.remove("input-span-alarm");
            //         // }
            //         // else {
            //         //     message.classList.remove("input-span-success");
            //         //     message.classList.add("input-span-alarm");
            //         // }
            //     }
            // })

    });
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
            return false;
        }
    }

})();
