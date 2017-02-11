'use strict';

angular.module('stepApp')
    .controller('InstEmplExperienceDetailController',
    ['$scope', 'Principal', 'InstEmployeeCode', '$rootScope', '$stateParams', 'DataUtils', 'InstEmplExperience', 'InstEmployee','InstEmplExperienceCurrent','CurrentInstEmployee',
    function ($scope, Principal, InstEmployeeCode, $rootScope, $stateParams, DataUtils, InstEmplExperience, InstEmployee,InstEmplExperienceCurrent,CurrentInstEmployee) {
       /* $scope.instEmplExperience = entity;
        $scope.load = function (id) {
            InstEmplExperience.get({id: id}, function(result) {
                $scope.instEmplExperience = result;
            });
        };*/
        /*Principal.identity().then(function (account) {
            $scope.account = account;
            InstEmployeeCode.get({'code':$scope.account.login},function(result){
                console.log(result);
                $scope.instEmplExperiences =  result.instEmplExperiences;
            });
        });*/
        $scope.hideEditButton = false;
        $scope.hideAddButton = true;
        InstEmplExperienceCurrent.get({},function(result){
            $scope.instEmplExperiences = result;
            if(result.length< 1){
                $scope.hideAddButton = true;
                $scope.hideEditButton = false;
                $scope.instEmplExperiences = [{
                    instituteName: null,
                    indexNo: null,
                    address: null,
                    designation: null,
                    subject: null,
                    salaryCode: null,
                    joiningDate: null,
                    mpoEnlistingDate: null,
                    resignDate: null,
                    dateOfPaymentReceived: null,
                    startDate: null,
                    endDate: null,
                    vacationFrom: null,
                    vacationTo: null,
                    totalExpFrom: null,
                    totalExpTo: null,
                    current: null,
                    attachment: null,
                    attachmentContentType: null,
                    id: null
                }];
            }
            else{
                $scope.hideAddButton = false;
                $scope.hideEditButton = true;
            }
            console.log('hideEditButton'+$scope.hideEditButton);
            console.log('hideAddButton'+$scope.hideAddButton);

        },function(result){

        });
        CurrentInstEmployee.get({},function(result){
            console.log(result);
            if(!result.mpoAppStatus>=3){
                $scope.hideEditButton = false;
                $scope.hideAddButton = false;
                console.log('hideEditButton'+$scope.hideEditButton);
                console.log('hideAddButton'+$scope.hideAddButton);
            }
            console.log('hideEditButton'+$scope.hideEditButton);
            console.log('hideAddButton'+$scope.hideAddButton);
        });
        var unsubscribe = $rootScope.$on('stepApp:instEmplExperienceUpdate', function(event, result) {
            $scope.instEmplExperience = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
