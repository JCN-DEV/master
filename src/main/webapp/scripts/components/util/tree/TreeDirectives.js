/**
 * developed by BeLancer
 */
(function (ng) {
    var app = ng.module('tree.directives', []);
    app.directive('nodeTree', function () {
        return {
            template: '<node ng-repeat="node in tree"></node>',
            replace: true,
            restrict: 'E',
            scope: {
                tree: '=children'
            }
        };
    });
    app.directive('node', function ($compile) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: 'scripts/components/util/tree/node.html', // HTML for a single node.
            link: function (scope, element) {
                /*
                 * Here we are checking that if current node has children then compiling/rendering children.
                 * */
                if (scope.node && scope.node.children && scope.node.children.length > 0) {
                    scope.node.childrenVisibility = true;
                    var childNode = $compile('<ul class="tree" ng-if="!node.childrenVisibility"><node-tree children="node.children"></node-tree></ul>')(scope);
                    element.append(childNode);
                } else {
                    scope.node.childrenVisibility = false;
                }
            },
            controller: ["$scope", function ($scope) {
                // This function is for just toggle the visibility of children
                $scope.toggleVisibility = function (node) {
                    if (node.children) {
                        node.childrenVisibility = !node.childrenVisibility;
                    }
                };
                $scope.getCheckedItems=[];
                // Here We are marking check/un-check all the nodes.
                $scope.checkNode = function (node) {
                    node.checked = !node.checked;
                    $scope.getCheckedItems.push('1');
                    console.log($scope.getCheckedItems);
                    function checkChildren(c) {
                        angular.forEach(c.children, function (c) {
                            c.checked = node.checked;
                            checkChildren(c);
                        });
                    }
                    checkChildren(node);
                };


            }]
        };
    });
})(angular);
