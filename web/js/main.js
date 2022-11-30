
var main = {
    init: function () {
        main.model.getUsersByFilters();
        main.view.show();
        main.ctrl.bind();

    }
};


