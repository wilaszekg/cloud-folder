define(["marionette", "jquery",
        "app/view/AccountsView",
        "app/model/AccountsCollection",
        "app/view/FilesView",
        "app/model/FilesCollection",
        "app/view/Spinner",
        "app/model/FilesHeaderModel",
        "app/service/FileService",
        "app/model/FileUploadModel",
        "app/view/FileUploadView"],
    function (Marionette, $, AccountsView, AccountsCollection, FilesView,
              FilesCollection, Spinner, FilesHeaderModel, FileService,
              FileUploadModel, FileUploadView) {

        function createFilesBox(side, accountsCollection) {
            var accountsView = new AccountsView({
                collection: accountsCollection,
                el: "#accounts-list-" + side
            });
            accountsView.render();

            var spinner = new Spinner({el: "#spinner-" + side});
            var filesCollection = new FilesCollection();
            var filesView = new FilesView({
                model: new FilesHeaderModel(),
                collection: filesCollection,
                el: "#files-list-" + side,
                spinner: spinner,
                fileService: FileService.instance()
            });

            var fileUploadView = new FileUploadView({
                model: new FileUploadModel(),
                el: "#" + side + "-upload"
            });

            filesView.listenTo(accountsView, "childview:account:open:main", filesView.openMainFolder);
            accountsView.listenTo(filesView, "folder:show:accounts", accountsView.show);
            fileUploadView.listenTo(filesView, "folder:current:changed", fileUploadView.folderChanged);

            return filesView;
        }

        var clientsJson = JSON.parse($("#clients-json").html());
        var accountsCollection = new AccountsCollection(clientsJson);

        var leftFiles = createFilesBox("left", accountsCollection);
        var rightFiles = createFilesBox("right", accountsCollection);

        leftFiles.listenTo(rightFiles, "folder:current:changed", leftFiles.destinationChanged);
        rightFiles.listenTo(leftFiles, "folder:current:changed", rightFiles.destinationChanged);
    });