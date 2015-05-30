define(["marionette", "app/lib/Hbs"], function (Marionette, hbs) {
    return Marionette.ItemView.extend({
        template: hbs("#file-upload-template"),


        folderChanged: function (folder) {
            if (folder) {
                var path = "/storage/" + folder.clientId;
                if (folder.directoryId) {
                    path += "/" + folder.directoryId;
                }
                path += "/upload"

                this.model.set({
                    uploadAction: path,
                    enabled: true
                });
            } else {
                this.model.set("enabled", false);
            }
            this.render();
        }
    })
});
