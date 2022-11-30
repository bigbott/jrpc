'use strict';
/* global main*/
main.ctrl = {
    bind: function () {
        $('#calculate').click(function () {
            let firstNumber = $('#firstNumber').val();
            let secondNumber = $('#secondNumber').val();
            main.model.addNumbers(firstNumber, secondNumber);

        });
        $('#submit').click(function () {
            let firstWord = $('#firstWord').val();
            let secondWord = $('#secondWord').val();
            let thirdWord = $('#thirdWord').val();
            main.model.submitWords(firstWord, secondWord, thirdWord);

        });

        $('#ageCheckbox').click(function () {
            if ($(this).is(":checked")) {
                let filter = {type: 'age', value: 18};
                main.model.filters.push(filter);
            } else {
                for (let i = 0; i < main.model.filters.length; i++) {
                    if (main.model.filters[i].type === 'age') {
                        main.model.filters.splice(i, 1);
                        i--;
                    }
                }
            }
            main.model.getUsersByFilters();
        });

        $('#maleCheckbox').click(function () {
            if ($(this).is(":checked")) {
                $("#femaleCheckbox").prop("checked", false);
                removeFilter('gender');
                let filter = {type: 'gender', value: 1};
                main.model.filters.push(filter);
            } else {
                removeFilter('gender');
            }
            main.model.getUsersByFilters();
        });
        $('#femaleCheckbox').click(function () {
            if ($(this).is(":checked")) {
                $("#maleCheckbox").prop("checked", false);
                removeFilter('gender');
                let filter = {type: 'gender', value: 0};
                main.model.filters.push(filter);
            } else {
                 removeFilter('gender');
            }
            main.model.getUsersByFilters();
        });

        function removeFilter(type) {
            for (let i = 0; i < main.model.filters.length; i++) {
                if (main.model.filters[i].type === type) {
                    main.model.filters.splice(i, 1);
                    i--;
                }
            }
        }
    }
};


