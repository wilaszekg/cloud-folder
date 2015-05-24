define(["marionette",
    "app/lib/Hbs",
    "app/view/FileView",
    "app/view/FolderView"], function (Marionette, hbs, FileView, FolderView) {

    return Marionette.CompositeView.extend({

        initialize: function (options) {
            this.spinner = options.spinner;
            this.fileService = options.fileService;
            this.listenTo(this.fileService, "file:req:finish", this.refreshCollection);
            this.listenTo(this.fileService, "file:req:start", this.showSpinner);
        },

        template: hbs("#folder-header-template"),

        collectionEvents: {
            sync: "showFiles",
            reset: "showSpinner"
        },

        childEvents: {
            "folder:open": "openFolder",
            "file:selected": "fileSelected"
        },

        events: {
            "click .back-to-accounts": "backToAccounts",
            "click .back-to-parent": "backToParent",
            "click .folder-add": "addNewFolder",
            "click .copy": "copy",
            "click .move": "move",
            "click .remove": "remove"
        },

        showSpinner: function () {
            if (this.collection.clientId) {
                this.hide();
                this.spinner.show();
                this.render();
            }
        },

        showFiles: function () {
            this.trigger("folder:current:changed", {
                clientId: this.collection.clientId,
                directoryId: this.collection.id
            });
            this.show();
            this.spinner.hide();
            this.model.set("selectedFileId", null);
            this.collection.forEach(function (item) {
                item.set("selected", false);
            });
            this.render();
        },

        addNewFolder: function () {
            var name = this.$el.find(".folder-add-input").val().trim();
            if (name.length) {
                this.collection.createNewFolder(name);
            }
        },

        refreshCollection: function () {
            if (this.collection.clientId) {
                this.collection.fetch();
            }
        },

        destinationChanged: function (destination) {
            this.model.set("destinationDirectory", destination);
            if (this.collection.clientId) {
                this.render();
            }
        },

        copy: function () {
            this.fileService.copy(this.collection.baseUrl(), this.model.get("selectedFileId"), this.model.get("destinationDirectory"));
        },

        move: function () {
            this.fileService.move(this.collection.baseUrl(), this.model.get("selectedFileId"), this.model.get("destinationDirectory"));
        },

        remove: function () {
            this.fileService.remove(this.collection.baseUrl(), this.model.get("selectedFileId"));
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

        fileSelected: function (file) {
            var selectedId = file.model.get('id');
            this.model.set("selectedFileId", selectedId);
            this.collection.forEach(function (item) {
                if (item.get("id") === selectedId) {
                    item.set("selected", true);
                } else {
                    item.set("selected", false);
                }
            });
            this.render();
        },

        backToAccounts: function () {
            this.hide();
            this.trigger("folder:current:changed", null);
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