'use strict';

angular.module('stepApp')
	.controller('DlContCatSetDeleteController',
	['$scope','$state', '$rootScope','$modalInstance', 'entity', 'DlContCatSet',
	function($scope,$state, $rootScope, $modalInstance, entity, DlContCatSet) {

        $scope.dlContCatSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlContCatSet',{},{reload:true});
            DlContCatSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlContCatSet.deleted');

                });


        };

    }]);
