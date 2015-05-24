define(["backbone"], function (Backbone) {
    return Backbone.Model.extend({
        defaults: {
            id: "",
            clientId: "",
            name: "",
            folder: true,
            selected: false
        }
    })
});