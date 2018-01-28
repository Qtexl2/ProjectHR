(function () {
    'use strict';

    var input = document.querySelector('.btn-profile-update-photo');
    input.addEventListener("change",function (ev) {

        var files = ev.target.files;
        var file = files[0];
        var formData = new FormData;
        console.log(file);
        formData.append('img', file);

        $.ajax({
            url: '/controller?command=imgUpd',
            type: 'POST',
            data: formData,
            cache: false,
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function(data){
                if( data === null){
                    location.reload();
                }
            },
            error: function () {
                $("#profile-h2-upload").css({"opacity":"1"});
            }
        });

    });

})();
