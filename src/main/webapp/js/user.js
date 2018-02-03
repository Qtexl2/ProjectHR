(function () {
    'use strict';

    function userReg() {
           var id = $('.interview-result').attr('about');
           var email = $('.email-'+id);
           var password = $('.password-'+id);
           var role = $('.role-'+ id +' option:selected');
           $.ajax({
               type: 'POST',
               data: {email: email.val(), password: password.val(), role: role.val()},
               url: '/controller?command=createUser',
               success: function (data) {
                   data = JSON.parse(data);
                   var message  = data[0];
                   if (data[1] === "true") {
                        location.reload()
                   }
                   else {
                       document.querySelector(".status-"+id).innerHTML = message;
                   }
               }
           })
       }
    $('.user-icon-i-add').bind('click', function () {
        var id = $('.interview-result').attr('about');
        var email = "email-"+id;
        var password = "password-"+id;
        var role = "role-"+id;
        var str =
            '<tr class="user-item">' +
            '<td></td>'+
            '<td class="user-td-email"><input  required class=' + email +' type="text"></td>'+
            '<td class="user-td-password"><input pattern="(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}" class=' + password +' type="password" value=""></td>'+
            '<td class="user-td-role">'+
            '<select name="type" class= "user-type-role '+ role +'">'+
            '<option value="candidate">кандидат</option>'+
            '<option value="employer">работодатель</option>'+
            '<option value="admin">Администратор</option>'+
            '</select>'+
            '</td>'+
            '<td class="user-td-profile" colspan="2"><span class="user-icon-create user-create-submit-b user-icons"></span></td>' +
            '<td class="status-message-user status-'+ id +'" ></td>'+
            '</tr>';
        $('.user-menu:first').after(str);
        var input = document.querySelector('.user-create-submit-b');
        input.addEventListener("click", userReg);

    })
})();
