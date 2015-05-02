define(["marionette", "jquery",
    "app/view/AccountsView",
    "app/model/AccountsCollection",
    "app/view/FilesView",
    "app/model/FilesCollection"], function (Marionette, $, AccountsView, AccountsCollection, FilesView, FilesCollection) {

    var clientsJson = JSON.parse($("#clients-json").html());
    var accountsCollection = new AccountsCollection(clientsJson);
    var accountsView = new AccountsView({
        collection: accountsCollection,
        el: "#accounts-list"
    });
    accountsView.render();

    var filesCollection = new FilesCollection();
    var filesView = new FilesView({
        collection: filesCollection,
        el: "#files-list"
    });

    filesView.listenTo(accountsView, "childview:account:open:main", filesView.openMainFolder);


});