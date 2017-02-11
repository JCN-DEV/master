'use strict';

angular.module('stepApp')
    .controller('BeadPayCodeAssignController',
    ['$scope', 'entity', '$state','$stateParams', 'InstEmployee', 'MpoApplication', 'PayScale','TimeScaleApplication',
    function ($scope, entity, $state,$stateParams, InstEmployee, MpoApplication, PayScale,TimeScaleApplication) {

        $scope.mpoApplication = entity;
        $scope.instEmployee = {};
        $scope.payScaleDetails = '';
        $scope.payScaleID = '';
        $scope.mpoApplication.instEmployee = {};
        $scope.mpoApplication.instEmployee.payScale = {};
        console.log($scope.mpoApplication);


        TimeScaleApplication.get({id : $stateParams.id},function(result){
            $scope.mpoApplication=result;
            if($scope.mpoApplication.instEmployee.payScale !=null){
                $scope.payScaleDetails = $scope.mpoApplication.instEmployee.payScale;
                $scope.payScaleID = $scope.mpoApplication.instEmployee.payScale.id;
                console.log($scope.payScaleDetails);
            }
        });


        $scope.payScales = [];

        $scope.loadAll = function() {
            PayScale.query({page: $scope.page, size: 500}, function(result) {
                $scope.payScales = result;
                console.log($scope.payScales);
            });
        };

        $scope.loadAll();

        $scope.showPayScaleDetails = function(){
            console.log($scope.payScaleID);
            PayScale.get({id: $scope.payScaleID}, function(result) {
               $scope.payScaleDetails = result;
               console.log($scope.payScaleDetails);
           });
        };

        $scope.save = function () {

            if($scope.payScaleID == '') {
                console.log('Please Select a PayScale');
                return false;
            }

           $scope.isSaving = true;

           $scope.instEmployee = $scope.mpoApplication.instEmployee;

           PayScale.get({id: $scope.payScaleID}, function(result) {
               $scope.instEmployee.payScale = result;
               console.log($scope.instEmployee);
               InstEmployee.update($scope.instEmployee);
               $state.go('mpo.bedpayCodePendingList',{},{reload:true});
           });

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


    }]);
