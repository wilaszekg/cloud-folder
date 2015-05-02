define(["backbone", "app/model/AccountModel"], function (Backbone, AccountModel) {
    return Backbone.Collection.extend({
        model: AccountModel
    })
});