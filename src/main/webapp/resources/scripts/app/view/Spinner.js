define(["marionette", "app/lib/Hbs"], function (Marionette, hbs) {

    return Marionette.ItemView.extend({
        template: hbs("#spinner-template"),

        show: function () {
            this.$el.show();
            this.render();
        },

        hide: function () {
            this.$el.hide();
        }
    });
});