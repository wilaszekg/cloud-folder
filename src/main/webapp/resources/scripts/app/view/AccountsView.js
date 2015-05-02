define(["marionette", "app/view/AccountView"], function (Marionette, AccountView) {
    return Marionette.CollectionView.extend({
        childView: AccountView,

        childEvents: {
            "account:open:main": "hide"
        },

        hide: function () {
            this.$el.hide();
        },

        show: function () {
            this.$el.show();
        }
    });
});