define(["marionette", "jquery"], function (Marionette, $) {

    var ajaxOperation = function (url, data) {
        this.trigger("file:req:start");
        var self = this;
        $.ajax({
            url: url,
            method: "PUT",
            date: data
        }).success(function () {
            this.trigger("file:req:finish");
        }).fail(function () {
            console.log("FAIL");
            self.trigger("file:req:finish");
        });
    };

    var FileService = Marionette.Object.extend({
        copy: function (baseUrl, fileId, destination) {
            console.log("copy " + baseUrl + " " + fileId + " " + destination);
            var copyUrl = baseUrl + "/" + encodeURIComponent(fileId) + "/copy";
            ajaxOperation.call(this, copyUrl, destination)
        },

        move: function (baseUrl, fileId, destination) {
            console.log("move " + baseUrl + " " + fileId + " " + destination);
            var copyUrl = baseUrl + "/" + encodeURIComponent(fileId) + "/move";
            ajaxOperation.call(this, copyUrl, destination)
        },

        remove: function (baseUrl, fileId) {
            console.log("remove " + baseUrl + " " + fileId);
            var copyUrl = baseUrl + "/" + encodeURIComponent(fileId) + "/remove";
            ajaxOperation.call(this, copyUrl, {})
        }
    });

    var serviceInstance = new FileService();

    return {
        instance: function () {
            return serviceInstance;
        }
    }
});