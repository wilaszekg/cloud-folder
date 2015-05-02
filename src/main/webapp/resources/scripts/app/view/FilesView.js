define(["marionette",
    "app/lib/Hbs",
    "app/view/FileView",
    "app/view/FolderView"], function (Marionette, hbs, FileView, FolderView) {
    return Marionette.CompositeView.extend({

        template: hbs("#folder-header-template"),

        collectionEvents: {
            sync: "render"
        },

        childEvents: {
            "folder:open": "openFolder"
        },

        events: {
            "click .back-to-accounts": "backToAccounts",
            "click .back-to-parent": "backToParent"
        },

        getChildView: function (model) {
            if (model.get("folder")) {
                return FolderView;
            } else {
                return FileView;
            }
        },

        openMainFolder: function (caller, clientId) {
            this.show();
            this.collection.visitMainFolder(clientId)
                .fetch();
        },

        openFolder: function (caller, id) {
            this.collection.visitChild(id)
                .fetch();
        },

        backToAccounts: function () {
            this.hide();
            this.trigger("folder:show:accounts");
        },

        backToParent: function () {
            if (!this.collection.hasParent()) {
                return this.backToAccounts();
            }
            this.collection.visitParent().fetch();
        },

        show: function () {
            this.$el.show();
        },

        hide: function () {
            this.$el.hide();
        }
    })
});