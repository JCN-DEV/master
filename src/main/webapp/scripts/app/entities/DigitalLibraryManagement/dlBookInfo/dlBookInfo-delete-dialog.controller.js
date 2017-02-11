'use strict';

angular.module('stepApp')
	.controller('DlBookInfoDeleteController',
	['$scope','$rootScope','$state', '$modalInstance', 'entity', 'DlBookInfo',
	function($scope,$rootScope,$state, $modalInstance, entity, DlBookInfo) {

        $scope.dlBookInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.libraryBookInfos',{},{reload:true});
            DlBookInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlBookInfo.deleted');



                });

        };

    }]);
