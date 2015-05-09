define(["marionette", "jquery",
    "app/view/AccountsView",
    "app/model/AccountsCollection",
    "app/view/FilesView",
    "app/model/FilesCollection",
    "app/view/Spinner"], function (Marionette, $, AccountsView, AccountsCollection, FilesView, FilesCollection, Spinner) {

    function createFilesBox(side, accountsCollection) {
        var accountsView = new AccountsView({
            collection: accountsCollection,
            el: "#accounts-list-" + side
        });
        accountsView.render();

        var spinner = new Spinner({el: "#spinner-" + side});
        var filesCollection = new FilesCollection();
        var filesView = new FilesView({
            collection: filesCollection,
            el: "#files-list-" + side,
            spinner: spinner
        });

        filesView.listenTo(accountsView, "childview:account:open:main", filesView.openMainFolder);
        accountsView.listenTo(filesView, "folder:show:accounts", accountsView.show);
    }

    var clientsJson = JSON.parse($("#clients-json").html());
    var accountsCollection = new AccountsCollection(clientsJson);

    createFilesBox("left", accountsCollection);
    createFilesBox("right", accountsCollection);


});