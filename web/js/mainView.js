'use strict';
/* global main*/

main.view = {
    show: function(){
        $('pre.code').highlight();
    },
    showAddNumbersResult: function(result){
        $('#result').val(result);
    }, 
    showSentence: function (wordsArray){
        let sentence = wordsArray[0] + ' ' + wordsArray[1] + ' ' + wordsArray[2];
        $('#sentence').text(sentence);
    }, 
    showUsers: function (usersArray){
        let rows = '';
        for (let i = 0; i < usersArray.length; i++){
            let user = usersArray[i];
            rows += '<tr>';
            rows += '<td>' + user.name +'</td>';
            rows += '<td>' + user.age +'</td>';
            rows += '<td>' + (user.isMale ? 'male' : 'female') +'</td>';
            rows += '</tr>';
        }
        $('#tbody').empty();
        $('#tbody').append(rows);
    }
};


