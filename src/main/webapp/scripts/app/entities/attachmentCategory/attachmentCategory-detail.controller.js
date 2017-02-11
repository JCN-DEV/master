'use strict';

angular.module('stepApp')
    .controller('AttachmentCategoryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AttachmentCategory', 'Module',
    function ($scope, $rootScope, $stateParams, entity, AttachmentCategory, Module) {
        $scope.attachmentCategory = entity;
        $scope.load = function (id) {
            AttachmentCategory.get({id: id}, function(result) {
                $scope.attachmentCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:attachmentCategoryUpdate', function(event, result) {
            $scope.attachmentCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
