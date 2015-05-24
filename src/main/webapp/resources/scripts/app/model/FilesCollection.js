define(["backbone", "app/model/FileModel", "app/model/FolderModel"], function (Backbone, FileModel, FolderModel) {

    return Backbone.Collection.extend({

        initialize: function () {
            this.id = null;
            this.clientId = null;
            this.parents = [];
        },

        url: function () {
            var baseUrl = this.baseUrl();
            if (this.id) {
                return baseUrl + "/" + encodeURIComponent(this.id);
            } else {
                return baseUrl;
            }
        },

        baseUrl: function () {
            return "/storage/" + this.clientId;
        },

        model: function (attrs, options) {
            if (attrs.folder) {
                return new FolderModel(attrs, options);
            } else {
                return new FileModel(attrs, options);
            }
        },

        createNewFolder: function (name) {
            var putUrl = this.url() + "/" + name;
            var self = this;
            this.reset();
            $.ajax({
                url: putUrl,
                method: "PUT"
            }).success(function () {
                self.fetch();
            });
        },

        visitChild: function (childId) {
            this.parents.push(this.id);
            this.id = childId;
            this.resetAndFetch();
            return this;
        },

        visitMainFolder: function (clientId) {
            this.clientId = clientId;
            this.id = null;
            this.parents = [];
            this.resetAndFetch();
            return this;
        },

        visitParent: function () {
            this.id = this.parents.pop();
            this.resetAndFetch();
            return this;
        },

        resetAndFetch: function () {
            this.reset();
            this.fetch();
        },

        hasParent: function () {
            return this.parents.length;
        }
    })
});