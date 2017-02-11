'use strict';

angular.module('stepApp')
	.controller('InstEmplWithhelHistDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstEmplWithhelHist',
    function($scope, $modalInstance, entity, InstEmplWithhelHist) {

        $scope.instEmplWithhelHist = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplWithhelHist.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
