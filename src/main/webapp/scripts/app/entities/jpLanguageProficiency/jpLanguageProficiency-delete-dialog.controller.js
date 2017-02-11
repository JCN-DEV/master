'use strict';

angular.module('stepApp')
    .controller('JpLanguageProficiencyDeleteController',
     ['$scope', '$modalInstance', 'entity', 'JpLanguageProficiency',
     function ($scope, $modalInstance, entity, JpLanguageProficiency) {

        $scope.lang = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            console.log("id :"+id);
            JpLanguageProficiency.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
