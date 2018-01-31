(function () {
    'use strict';
    var elements = document.querySelectorAll('.dialog-field');
    var historyChat = document.querySelector('.history-chat');
    var send = document.getElementById('send-message');
    var text = document.getElementById('output-text');

    for (var i = 0; i < elements.length; i++) {
        elements[i].addEventListener("click",function () {
            while (historyChat.firstChild) {
                historyChat.removeChild(historyChat.firstChild);
            }
            $('.dialog-chat-wrapper').css({"opacity":"1"});
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

                            historyChat.appendChild( myMessage(text,new Date(time),status));
                        }
                        send.setAttribute('value',receiver);
                        historyChat.scrollTop = historyChat.scrollHeight;
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
        var min= time.getMinutes() <10?'0'+ time.getMinutes():time.getMinutes();
        tDivChatTime.innerHTML = time.getHours() + ':' + min;
        tDivChatContent.className = "chat-message-content";
        tDivChatContent.innerHTML = text;
        tDivChatMessage.appendChild(tDivChatTime);
        tDivChatMessage.appendChild(tDivChatContent);
        tDivChatItem.appendChild(tDivChatMessage);
        return tDivChatItem;
    }

    send.addEventListener("click",function () {
        var receiver = this.getAttribute('value');
        if(receiver !== null && text !== null){
            $.ajax({
                type: 'POST',
                data: {command: "sendMessage", receiver: receiver, text: text.value},
                url: '/controller',
                success: function (data) {

                    if(data === "success"){
                        historyChat.appendChild( myMessage(text.value, new Date(), true));
                        historyChat.scrollTop = historyChat.scrollHeight;

                    }
                    text.value='';
                }
            })
        }

    });


})();
