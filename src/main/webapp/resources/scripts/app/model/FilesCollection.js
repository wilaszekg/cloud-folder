define(["backbone", "app/model/FileModel", "app/model/FolderModel"], function (Backbone, FileModel, FolderModel) {

    return Backbone.Collection.extend({

        initialize: function () {
            this.id = null;
            this.clientId = null;
        },

        url: function () {
            var baseUrl = "/storage/" + this.clientId;
            if (this.id) {
                return baseUrl + "/" + this.id;
            } else {
                return baseUrl;
            }
        },

        model: function (attrs, options) {
            if (attrs.folder) {
                return new FolderModel(attrs, options);
            } else {
                return new FileModel(attrs, options);
            }
        },

        setId: function (id) {
            this.id = id;
            return this;
        },

        setClientId: function (clientId) {
            //this.clientId = clientId;
            this.clientId = "wilaszekg";
            return this;
        }
    })
});