define(["backbone"], function (Backbone) {
    return Backbone.Model.extend({
        defaults: {
            enabled: false,
            uploadAction: ""
        }
    })
});