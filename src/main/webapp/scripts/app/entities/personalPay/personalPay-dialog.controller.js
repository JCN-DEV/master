'use strict';

angular.module('stepApp').controller('PersonalPayDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'PersonalPay', 'PayScale', 'User','$state','$timeout','$rootScope',
        function($scope, $stateParams, $q, entity, PersonalPay, PayScale, User,$state,$timeout,$rootScope) {

        $scope.personalPay = PersonalPay.get({id: $stateParams.id});
        $scope.personalPays = entity;
        $scope.payscales = PayScale.query({filter: 'personalpay-is-null'});
        /*$q.all([$scope.personalPay.$promise, $scope.payscales.$promise]).then(function() {
            if (!$scope.personalPay.payScale.id) {
                return $q.reject();
            }
            return PayScale.get({id : $scope.personalPay.payScale.id}).$promise;
        }).then(function(payScale) {
            $scope.payscales.push(payScale);
        });*/
        $scope.load = function(id) {
            $scope.personalPays.push(
                {
                    payScale: null,
                    amount: null
                }
            );
            /*PersonalPay.get({id : id}, function(result) {
                $scope.personalPay = result;
            });*/
        };

            $scope.AddMore = function(){
                $scope.personalPays.push(
                    {
                        payScale: null,
                        amount: null
                    }
                );
                // Start Add this code for showing required * in add more fields
                $timeout(function() {
                    $rootScope.refreshRequiredFields();
                }, 100);
                // End Add this code for showing required * in add more fields

            };

        var onSaveSuccess = function (result) {
            $state.go('personalPay',{},{reload:true});
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;

                 if ($scope.personalPay.id != null) {
                 PersonalPay.update($scope.personalPay, onSaveSuccess, onSaveError);
            }else{
                     angular.forEach($scope.personalPays, function(data){
                         if(data.id != null){
                             PersonalPay.update(data, onSaveSuccess, onSaveError);
                             $rootScope.setWarningMessage('stepApp.personalPay.updated');
                         }
                         else{
                             if(data.payScale != null || data.amount != null ){
                                 data.effectiveDate=$scope.personalPay.effectiveDate;
                                 data.status=$scope.personalPay.status;
                                 PersonalPay.save(data, onSaveSuccess, onSaveError);
                                 $rootScope.setSuccessMessage('stepApp.personalPay.created');
                             }

                         }
                     });
                 }

            $scope.removeItem = function (item) {

                var index = $scope.personalPays.indexOf(item);
                $scope.personalPays.splice(index, 1);
            }
           /* $q.all(requests).then(function () {
                $state.go('personalPay',{},{reload:true});
            });*/
        };
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);

        $scope.clear = function() {

        };
}]);
