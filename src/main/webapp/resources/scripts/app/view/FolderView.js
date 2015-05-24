define(["marionette", "app/lib/Hbs"], function (Marionette, hbs) {
    return Marionette.ItemView.extend({
        tagName: "li",
        template: hbs("#folder-template"),

        events: {
            "click .folder-open": "openFolder"
        },

        triggers: {
            "click .folder-description": "file:selected"
        },

        openFolder: function () {
            this.trigger("folder:open",
                this.model.get("id"));
        }
    })
});