define(["marionette", "app/lib/Hbs"], function (Marionette, hbs) {
    return Marionette.ItemView.extend({
        tagName: "li",
        template: hbs("#account-template"),

        events: {
            click: "openMainFolder"
        },

        openMainFolder: function () {
            this.trigger("account:open:main", this.model.get("id"));
        }
    })
});