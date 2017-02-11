'use strict';

angular.module('stepApp')
    .controller('PgmsPenGrSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsPenGrSetup', 'PgmsPenGrRateList',
    function ($scope, $rootScope, $stateParams, entity, PgmsPenGrSetup,PgmsPenGrRateList) {
        $scope.pgmsPenGrSetup = entity;
        $scope.pgmsPenGrRateListView = [];

        $scope.loadAllDataView = function ()
        {
            PgmsPenGrRateList.get({penGrSetId : $stateParams.id}, function(result) {
                $scope.pgmsPenGrRateListView = result;
               // console.log("Rate info:"+JSON.stringify($scope.pgmsPenGrRatelist));
            });

        };
        $scope.loadAllDataView();

        $scope.load = function (id) {
            PgmsPenGrSetup.get({id: id}, function(result) {
                $scope.pgmsPenGrSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsPenGrSetupUpdate', function(event, result) {
            $scope.pgmsPenGrSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
