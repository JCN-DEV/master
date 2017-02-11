'use strict';

angular.module('stepApp').controller('MpoTradeDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'MpoTrade', 'CmsTrade','$state','$timeout','$rootScope','CmsCurriculum','CmsTradesByCurriculum','MpoTradeByTradeId','FindActivecmsCurriculums',
        function($scope, $stateParams, $q, entity, MpoTrade, CmsTrade, $state, $timeout, $rootScope, CmsCurriculum, CmsTradesByCurriculum, MpoTradeByTradeId,FindActivecmsCurriculums) {

        $scope.mpoTrade = {};
        $scope.initialCurriculum = null;
        $scope.initialTrade = null;
        $scope.mpoTrades = [];

        MpoTrade.get({id:$stateParams.id}, function(result){
            $scope.mpoTrades =[];
            $scope.mpoTrades.push(
                {
                    id: result.id
                }
            );
            $scope.mpoTrade = result;
            $scope.initialCurriculum = result.cmsTrade.cmsCurriculum.name;
            $scope.initialTrade = result.cmsTrade.name;
            //$scope.mpoTrade2 = result;
            CmsTradesByCurriculum.query({id:result.cmsTrade.cmsCurriculum.id}, function(result){
                $scope.cmstrades = result;
            });
        });


        //$scope.cmstrades = CmsTrade.query();
        $scope.cmsCurriculums = FindActivecmsCurriculums.query();
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoTradeUpdate', result);
           // $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('mpoTrade', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            //if($scope.mpoTrade.id == null){
                angular.forEach($scope.mpoTrades, function(data){
                    console.log(data);

                    if (data.id != null) {
                        MpoTrade.update(data, onSaveSuccess, onSaveError);
                    } else {
                        MpoTradeByTradeId.get({id: data.cmsTrade.id}, function(result){
                            console.log('already Exists!');
                        }, function(response) {
                            if(response.status === 404) {
                                console.log('not found');
                                data.status = 1;
                                MpoTrade.save(data, onSaveSuccess, onSaveError);
                            }
                        });

                    }

                });
            /*}else{
                MpoTrade.update($scope.mpoTrade, onSaveSuccess, onSaveError);
            }*/


        };

        $scope.AddMoreMpoTrade = function(){

            $scope.mpoTrades.push(
                {
                    status: null,
                    id: null
                }
            );
            // Start Add this code for showing required * in add more fields
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);
            // End Add this code for showing required * in add more fields

        };


            $scope.mpoTrades.push(
                {
                    status: null,
                    id: null
                }
            );


        $scope.setTrades = function() {
            $scope.initialCurriculum = null;
            $scope.initialTrade = null;
            CmsTradesByCurriculum.query({id:$scope.cmsCurriculum.id}, function(result){
                $scope.cmstrades = result;
            });
        };

        $scope.chageTrades = function() {
            $scope.initialTrade = null;
        };
        $scope.clear = function() {
            window.history.back();
           // $modalInstance.dismiss('cancel');
        };
}]);
