define(["backbone", "app/model/FileModel", "app/model/FolderModel"], function (Backbone, FileModel, FolderModel) {

    return Backbone.Collection.extend({

        initialize: function () {
            this.id = null;
            this.clientId = null;
            this.parents = [];
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

        visitChild: function (childId) {
            this.parents.push(this.id);
            this.id = childId;
            return this;
        },

        visitMainFolder: function (clientId) {
            this.clientId = clientId;
            this.id = null;
            this.parents = [];
            return this;
        },

        visitParent: function () {
            this.id = this.parents.pop();
            return this;
        },

        hasParent: function () {
            return this.parents.length;
        }
    })
});