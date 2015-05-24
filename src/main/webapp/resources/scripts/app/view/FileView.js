define(["marionette", "app/lib/Hbs"], function (Marionette, hbs) {
    return Marionette.ItemView.extend({
        tagName: "li",
        template: hbs("#file-template"),

        triggers: {
            click: "file:selected"
        }
    })
});