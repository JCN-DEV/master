'use strict';

angular.module('stepApp').controller('PgmsGrObtainSpecEmpDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PgmsGrObtainSpecEmp', 'HrEmployeeInfo', 'PgmsGrObtainSpecEmpInfo', 'User', 'Principal', 'DateUtils',
        function($scope, $stateParams, $state, entity, PgmsGrObtainSpecEmp, HrEmployeeInfo,PgmsGrObtainSpecEmpInfo, User, Principal, DateUtils) {

        $scope.pgmsGrObtainSpecEmp = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.users = User.query({filter: 'pgmsGrObtainSpecEmp-is-null'});
        $scope.gratuityRateList = [];

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        $scope.load = function(id) {
            PgmsGrObtainSpecEmp.get({id : id}, function(result) {
                $scope.pgmsGrObtainSpecEmp = result;
            });
        };
        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.employeeInfo = function(empId){

           $scope.pgmsGrObtainSpecEmp.empName = empId.fullName;
           $scope.pgmsGrObtainSpecEmp.empDesignation = empId.designationInfo.designationInfo.designationName;
           $scope.pgmsGrObtainSpecEmp.empDepartment = empId.departmentInfo.departmentInfo.departmentName;
           $scope.pgmsGrObtainSpecEmp.empEndDate = empId.retirementDate;
           //var bDate = empId.birthDate;
           //var d = new Date(2011, 31)
           //d.setMonth( d.getMonth() + 1 )

          // $scope.pgmsGrObtainSpecEmp.empEndDate = empId.birthDate;
           //console.log("After Date Add:"+JSON.stringify($scope.pgmsGrObtainSpecEmp.empEndDate));

           //$scope.pgmsGrObtainSpecEmp.empEndDate = bDate.addYears(59);
           //$scope.pgmsGrObtainSpecEmp.empEndDate = empId.birthDate;
           $scope.pgmsGrObtainSpecEmp.empStatus = empId.activeStatus;
          // $scope.pgmsGrObtainSpecEmp.empWrkingYear = 20;

           var dataJsn = {employeeId:empId};
           console.log("dataJsn:"+JSON.stringify(dataJsn));
          // PgmsGrObtainSpecEmpInfo.get(dataJsn, function(result) {

              //$scope.gratuityRateList = empId;
             // console.log("Employee Details Information:"+JSON.stringify($scope.gratuityRateList));
           //});
        }

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsGrObtainSpecEmpUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsGrObtainSpecEmp");
        };

        $scope.save = function () {
            $scope.pgmsGrObtainSpecEmp.updateBy = $scope.loggedInUser.id;
            $scope.pgmsGrObtainSpecEmp.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsGrObtainSpecEmp.id != null) {
                PgmsGrObtainSpecEmp.update($scope.pgmsGrObtainSpecEmp, onSaveFinished);
            } else {
                $scope.pgmsGrObtainSpecEmp.createBy = $scope.loggedInUser.id;
                $scope.pgmsGrObtainSpecEmp.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsGrObtainSpecEmp.save($scope.pgmsGrObtainSpecEmp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
