define(["backbone"], function (Backbone) {
    return Backbone.Model.extend({
        defaults: {
            id: "",
            clientId: "",
            name: "",
            selected: false
        }
    })
});