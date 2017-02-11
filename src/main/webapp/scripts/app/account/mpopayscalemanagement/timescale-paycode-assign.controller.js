'use strict';

angular.module('stepApp')
    .controller('TimeScalePayCodeAssignController',
     ['$scope', 'entity', '$state','$stateParams', 'InstEmployee', 'MpoApplication', 'PayScale','TimeScaleApplication','InstEmplPayscaleHist',
     function ($scope, entity, $state,$stateParams, InstEmployee, MpoApplication, PayScale,TimeScaleApplication,InstEmplPayscaleHist) {

        $scope.mpoApplication = entity;
        $scope.instEmployee = {};
        $scope.payScaleDetails = '';
        $scope.payScaleID = '';
        $scope.mpoApplication.instEmployee = {};
         $scope.instEmplPayscaleHist = {};

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
            console.log($scope.payScaleIDins);
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
               $scope.instEmployee.timescaleAppStatus=6;

               InstEmployee.update($scope.instEmployee);

              $scope.instEmplPayscaleHist.activationDate = new Date();
              //$scope.instEmplPayscaleHist.endDate = {};
              $scope.instEmplPayscaleHist.instEmployee = $scope.instEmployee;
              $scope.instEmplPayscaleHist.payScale = result;
              InstEmplPayscaleHist.save($scope.instEmplPayscaleHist);

               $state.go('mpo.timeScalepayCodePendingList',{},{reload:true});
           });

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


    }]);
