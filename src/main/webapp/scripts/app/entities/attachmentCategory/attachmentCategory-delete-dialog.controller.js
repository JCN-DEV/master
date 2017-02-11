'use strict';

angular.module('stepApp')
    .controller('attachmentCategoryDeleteController',
    ['$scope', '$modalInstance', 'entity', 'AttachmentCategory',
     function ($scope, $modalInstance, entity, AttachmentCategory) {

        $scope.country = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AttachmentCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
