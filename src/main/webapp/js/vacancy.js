(function () {
    'use strict';
    var submit = document.querySelector('.btn-respond');
    submit.addEventListener("click",function () {
            var idVac = submit.getAttribute('value');
            $.ajax({
                type: 'POST',
                data: {command: "vacancyRespond", idVac: idVac},
                url: '/controller',
                success: function (data) {

                    if(data === "null"){
                        $('.btn-respond').css({"background": "#e54747","color":"wight","border":"1px #e54747 solid"});
                    } else{
                        try{
                            data = JSON.parse(data);
                            if(data[0] === null){
                                $('.btn-respond').css({"background": "#e54747","color":"wight","border":"1px #e54747 solid"});
                            }
                            else {
                                $('.btn-respond').css({"background": "#7bcf7b","color":"wight","border":"1px #7bcf7b solid"});
                            }
                        }
                        catch(err) {
                            window.location.href = "/controller?command=regPage";
                        }
                    }
                },
                error: function () {
                    $('.btn-respond').css({"background": "red","color":"wight"});
                }
            })
    });

})();
