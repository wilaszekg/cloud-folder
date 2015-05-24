define(["backbone"], function (Backbone) {
    return Backbone.Model.extend({
        defaults: {
            selectedFileId: null,
            destinationDirectory: null
        }
    })
});