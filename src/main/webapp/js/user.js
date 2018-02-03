(function () {
    'use strict';
    var str =
        '<tr class="user-item">' +
        '<td></td>'+
        '<td class="user-td-email"><input type="text"></td>'+
        '<td class="user-td-password"><input type="password" value="**********"></td>'+
        '<td class="user-td-role">'+
        '<select name="type" class= "user-type-role">'+
        '<option value="candidate" selected>кандидат</option>'+
        '<option value="employer">работодатель</option>'+
        '<option value="admin">Администратор</option>'+
        '</select>'+
        '</td>'+
        '<td class="user-td-profile" colspan="2"><a class="user-icon-create user-icons" href="#"></a></td>'+
        '</tr>';


    $('.user-icon-i-add').bind('click', function () {
        $('.user-menu:first').after(str);

    })
})();
