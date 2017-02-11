'use strict';

angular.module('stepApp')
    .controller('AuthorDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Author', 'User',
    function ($scope, $rootScope, $stateParams, DataUtils, entity, Author, User) {
        $scope.author = entity;
        $scope.load = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:authorUpdate', function(event, result) {
            $scope.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
