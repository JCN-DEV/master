'use strict';

angular.module('stepApp')
    .controller('InstEmpSpouseInfoDetailController',
    ['$scope', '$rootScope', 'InstEmpSpouseInfoForCurrent', '$stateParams', 'InstEmpSpouseInfo', 'InstEmployee', 'InstEmpAddress','CurrentInstEmployee',
     function ($scope, $rootScope, InstEmpSpouseInfoForCurrent, $stateParams, InstEmpSpouseInfo, InstEmployee, InstEmpAddress,CurrentInstEmployee) {


        /*$scope.load = function (id) {
        InstEmpSpouseInfoForCurrent.get({}, function(result) {
                $scope.instEmpSpouseInfo = result;
            });
        };*/
        $scope.hideEditButton = false;
        $scope.hideAddButton = true;
        InstEmpSpouseInfoForCurrent.get({},function(result){
            $scope.instEmpSpouseInfo = result;
            if(result.length<1){
                $scope.hideAddButton = true;
                $scope.hideEditButton = false;
                $scope.instEmpSpouseInfo ={
                    name: null,
                    dob: null,
                    isNominee: false,
                    gender: null,
                    relation: null,
                    nomineePercentage: null,
                    occupation: null,
                    tin: null,
                    nid: null,
                    designation: null,
                    govJobId: null,
                    mobile: null,
                    officeContact: null,
                    id: null
                };
            }
            else{
                $scope.hideAddButton = false;
                $scope.hideEditButton = true;
            }
        });
        CurrentInstEmployee.get({},function(result){
            console.log('hideEditButton'+$scope.hideEditButton);
            console.log('hideAddButton'+$scope.hideAddButton);
            console.log(result);
            if(!result.mpoAppStatus>=3){
                $scope.hideEditButton = false;
                $scope.hideAddButton = false;
                console.log('hideEditButton'+$scope.hideEditButton);
                console.log('hideAddButton'+$scope.hideAddButton);
            }
        });

        var unsubscribe = $rootScope.$on('stepApp:instEmpSpouseInfoUpdate', function(event, result) {
            $scope.instEmpSpouseInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
