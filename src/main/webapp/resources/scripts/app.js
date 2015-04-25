// For any third party dependencies, like jQuery, place them in the vendor folder.

// Configure loading modules from the vendor directory,
// except for 'app' ones, which are in a sibling
// directory.
requirejs.config({
    baseUrl: '/resources/scripts/vendor',
    paths: {
        app: '../app',
        jquery: 'jquery',
        underscore: "underscore",
        json2: "json2",
        backbone: 'backbone',
        marionette: 'backbone.marionette'
    },
    shim: {
        underscore: {
            exports: "_"
        },
        backbone: {
            deps: ["jquery", "underscore", "json2"],
            exports: "Backbone"
        },
        marionette: {
            deps: ["backbone"],
            exports: "Marionette"
        }
    }
});

// Start loading the main app file. Put all of
// your application logic in there.
requirejs(['app/main']);