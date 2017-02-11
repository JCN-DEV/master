'use strict';

angular.module('stepApp')
    .controller('PrlPayscaleInfoDetailController', function ($scope, $rootScope, $stateParams, $timeout, entity, PrlPayscaleInfo, PrlPayscaleAllowanceInfoByPsGrd, PrlPayscaleBasicInfoByPayscale) {
        $scope.prlPayscaleInfo = entity;
        $scope.load = function (id) {
            PrlPayscaleInfo.get({id: id}, function(result) {
                $scope.prlPayscaleInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlPayscaleInfoUpdate', function(event, result) {
            $scope.prlPayscaleInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.loadAllowanceList = function()
        {
            //console.log("loadAllowanceList PSID: "+$scope.prlPayscaleInfo.id);
            if($scope.prlPayscaleInfo.id != null)
            {
                //console.log("loadAllowanceList payscaleid: "+$scope.prlPayscaleInfo.id+", gradeId: "+$scope.prlPayscaleInfo.gradeInfo.id);
                PrlPayscaleAllowanceInfoByPsGrd.query({psid: $scope.prlPayscaleInfo.id,grdid:$scope.prlPayscaleInfo.gradeInfo.id}, function (result)
                {
                    //console.log("PayScaleAllowance: "+JSON.stringify(result));
                    $scope.prlPayscaleAllowanceList = result;

                }, function (response)
                {
                    console.log("error: "+response);
                })
            }
        };

        $scope.loadBasicSalaryList = function()
        {
            //console.log("loadBasicSalaryList PSID: "+$scope.prlPayscaleInfo.id);
            if($scope.prlPayscaleInfo.id != null)
            {
                PrlPayscaleBasicInfoByPayscale.query({psid: $scope.prlPayscaleInfo.id}, function (result)
                {
                    $scope.prlBasicPayscaleList = result;
                }, function (response)
                {
                    console.log("error: "+response);
                })
            }
        };

        $timeout(function()
        {
            //console.log("Loading payscale basic list: ");
            $scope.loadBasicSalaryList();

            //console.log("Loading payscale allowance list: ");
            $scope.loadAllowanceList();
        }, 800);

    });
