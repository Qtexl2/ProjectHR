(function () {
    'use strict';
    var elements = document.querySelectorAll('.dialog-field');
    var historyChat = document.querySelector('.history-chat');
    for (var i = 0; i < elements.length; i++) {
        elements[i].addEventListener("click",function () {
            while (historyChat.firstChild) {
                historyChat.removeChild(historyChat.firstChild);
            }
            var receiver = this.getAttribute('about');
            $.ajax({
                type: 'POST',
                data: {command: "chat", sender: receiver},
                url: '/controller',
                success: function (data) {
                    if(data !== "null"){
                        data = JSON.parse(data);
                        for( var j=0; j < data.length; j++){
                            var text= data[j].messageText;
                            var time= data[j].messageTime;
                            var reception= data[j].profileReceptionID;
                            var status = reception == receiver;

                            console.log(status);
                            console.log(reception);
                            console.log(receiver);
                            historyChat.appendChild( myMessage(text,time,status));
                        }
                    }
                }
            })
        });
    }

    function myMessage (text, time, my) {
        var tDivChatItem = document.createElement('div');
        var tDivChatMessage = document.createElement('div');
        var tDivChatTime = document.createElement('div');
        var tDivChatContent = document.createElement('div');

        if(!my) {
            tDivChatItem.className = "chat-item chat-item-responder";
        }
        else{
            tDivChatItem.className = "chat-item";
        }
        tDivChatMessage.className = "chat-message";
        tDivChatTime.className = "chat-message-time";
        var date = new Date(time);
        var min= date.getMinutes() <10?'0'+ date.getMinutes():date.getMinutes();
        tDivChatTime.innerHTML = date.getHours() + ':' + min;
        tDivChatContent.className = "chat-message-content";
        tDivChatContent.innerHTML = text;
        tDivChatMessage.appendChild(tDivChatTime);
        tDivChatMessage.appendChild(tDivChatContent);
        tDivChatItem.appendChild(tDivChatMessage);
        return tDivChatItem;
    }



})();
