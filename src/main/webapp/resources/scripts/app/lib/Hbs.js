define(["handlebars", "jquery"], function (Handlebars, $) {
    return function (selector) {
        var source = $(selector).html();
        return Handlebars.compile(source);
    };
});