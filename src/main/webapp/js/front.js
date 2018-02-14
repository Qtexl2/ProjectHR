(function () {
    'use strict';

    var inputJob = document.getElementById('q');
    var inputWhere = document.getElementById('q2');
    var clearIconJob = document.querySelector('.clear-icon-job');
    var clearIconWhere = document.querySelector('.clear-icon-where');

    inputJob.addEventListener('keydown', switchStatusJob);
    inputWhere.addEventListener('keydown', switchStatusWhere);
    clearIconJob.addEventListener('click', clearInputFirst);
    clearIconWhere.addEventListener('click', clearInputSecond);

    function switchStatusJob() {
        if(inputJob.value.length < 1){
            clearIconJob.classList.add("clearSwitchOff");
        } else{
            clearIconJob.classList.remove("clearSwitchOff");
        }
    }
    function switchStatusWhere() {
        if(inputWhere.value.length-1 < 1){
            clearIconWhere.classList.add("clearSwitchOff");
        } else{
            clearIconWhere.classList.remove("clearSwitchOff");
        }
    }

    function clearInputFirst() {
        inputJob.value = "";
        switchStatusJob();
    }

    function clearInputSecond() {
        inputWhere.value = "";
        switchStatusWhere();
    }

})();
