'use strict';

angular.module('stepApp')
    .controller('HrEntertainmentBenefitDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrEntertainmentBenefit', 'HrEmployeeInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $stateParams, entity, HrEntertainmentBenefit, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEntertainmentBenefit = entity;
        $scope.load = function (id) {
            HrEntertainmentBenefit.get({id: id}, function(result) {
                $scope.hrEntertainmentBenefit = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEntertainmentBenefitUpdate', function(event, result) {
            $scope.hrEntertainmentBenefit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
