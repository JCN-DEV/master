/**
 * developed by BeLancer
 */
(function (ng) {
    var treeServiceApp = ng.module('tree.factory', []);
    treeServiceApp.factory("URLConfig", [function () {
        return {
            tree: "scripts/components/util/tree/tree.json"
        }
    }]);
})(angular);
