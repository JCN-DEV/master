'use strict';

angular.module('stepApp')
    .controller('CountryDeleteController',
    ['$scope', '$rootScope', '$modalInstance', 'entity', 'Country',
    function ($scope, $rootScope, $modalInstance, entity, Country) {

        $scope.country = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Country.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.country.deleted');
                });
        };

    }]);
