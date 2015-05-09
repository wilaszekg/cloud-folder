define(["marionette",
    "app/lib/Hbs",
    "app/view/FileView",
    "app/view/FolderView"], function (Marionette, hbs, FileView, FolderView) {

    return Marionette.CompositeView.extend({

        initialize: function (options) {
            this.spinner = options.spinner;
        },

        template: hbs("#folder-header-template"),

        collectionEvents: {
            sync: "showFiles",
            reset: "showSpinner"
        },

        childEvents: {
            "folder:open": "openFolder"
        },

        events: {
            "click .back-to-accounts": "backToAccounts",
            "click .back-to-parent": "backToParent",
            "click .folder-add": "addNewFolder"
        },

        showSpinner: function () {
            this.hide();
            this.spinner.show();
            this.render();
        },

        showFiles: function () {
            this.show();
            this.spinner.hide();
            this.render();
        },

        addNewFolder: function () {
            var name = $(".folder-add-input").val().trim();
            if (name.length) {
                this.collection.createNewFolder(name);
            }
        },

        getChildView: function (model) {
            if (model.get("folder")) {
                return FolderView;
            } else {
                return FileView;
            }
        },

        openMainFolder: function (caller, clientId) {
            this.collection.visitMainFolder(clientId);
        },

        openFolder: function (caller, id) {
            this.collection.visitChild(id);
        },

        backToAccounts: function () {
            this.hide();
            this.trigger("folder:show:accounts");
        },

        backToParent: function () {
            if (!this.collection.hasParent()) {
                return this.backToAccounts();
            }
            this.collection.visitParent();
        },

        show: function () {
            this.$el.show();
        },

        hide: function () {
            this.$el.hide();
        }
    });
});