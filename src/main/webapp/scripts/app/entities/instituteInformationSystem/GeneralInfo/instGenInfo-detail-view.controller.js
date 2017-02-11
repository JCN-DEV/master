'use strict';

angular.module('stepApp')
    .controller('InstGenInfoDetailViewController',
    ['$scope', '$rootScope', '$stateParams', 'InstGenInfo', 'Institute', 'Upazila', '$state',
    function ($scope, $rootScope, $stateParams, InstGenInfo, Institute, Upazila,$state) {

        /*$scope.load = function (id) {
            InstGenInfo.get({id: id}, function(result) {
                $scope.instGenInfo = result;
            });
        };*/
        var unsubscribe = $rootScope.$on('stepApp:instGenInfoUpdate', function(event, result) {
            $scope.instGenInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.retriveGenInfo = function() {
            return JSON.parse(localStorage.getItem('instGenInfo'));
        };
         $scope.backButton = function() {
             console.log('back clicked');
             window.history.back();
        };
        $scope.removeGenInfo = function() {

            $scope.instGenInfo= localStorage.getItem('instGenInfo');
            JSON.parse($scope.instGenInfo);
            if($scope.instGenInfo == null){
                $scope.instGenInfo=entity;
            }
            else{
                $scope.instGenInfo = JSON.parse($scope.instGenInfo);
                localStorage.removeItem('instGenInfo');
            }
        };
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instGenInfoUpdate', result);
            console.log(result);
            $scope.isSaving = false;
            $scope.removeGenInfo();
            if($scope.checkActivity)
                $state.go('instituteInfo.generalInfo',{},{reload:true});
            else{
                $rootScope.setSuccessMessage("Institute Registration Complete with code: '"+result.code+"'. Please wait for admin approve")
                //$rootScope.setErrorMessage("Institute Registration Complete with code: "+result.code+" Please wait for admin approve")
                $state.go('instituteInfo');
            }
        };
        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
                console.log('-----save');
                console.log($scope.instGenInfo);
                InstGenInfo.save($scope.instGenInfo, onSaveSuccess, onSaveError);

        };


        $scope.instGenInfo = $scope.retriveGenInfo();

    }]);
