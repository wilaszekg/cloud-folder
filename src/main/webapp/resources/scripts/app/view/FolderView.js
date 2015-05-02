define(["marionette", "app/lib/Hbs"], function (Marionette, hbs) {
    return Marionette.ItemView.extend({
        tagName: "li",
        template: hbs("#folder-template"),

        events: {
            click: "openFolder"
        },

        openFolder: function () {
            this.trigger("folder:open", this.model.get("clientId"), this.model.get("id"));
        }
    })
});