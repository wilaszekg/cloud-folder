define(["marionette", "app/view/FileView", "app/view/FolderView"], function (Marionette, FileView, FolderView) {
    return Marionette.CollectionView.extend({

        collectionEvents: {
            sync: "render"
        },

        childEvents: {
            "folder:open": "openFolder"
        },

        getChildView: function (model) {
            if (model.get("folder")) {
                return FolderView;
            } else {
                return FileView;
            }
        },

        openMainFolder: function (caller, clientId) {
            this.collection.setClientId(clientId)
                .setId(null)
                .fetch();
        },

        openFolder: function (caller, clientId, id) {
            this.collection.setClientId(clientId)
                .setId(id)
                .fetch();
        }
    })
});