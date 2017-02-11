'use strict';

angular.module('stepApp').controller('PgmsElpcDialogController',
    ['$scope', '$stateParams', '$rootScope', '$state', 'entity', 'PgmsElpc', 'HrEmployeeInfo', 'PgmsElpcBankInfo', 'User', 'Principal', 'DateUtils',
        function($scope, $stateParams, $rootScope, $state, entity, PgmsElpc, HrEmployeeInfo,PgmsElpcBankInfo, User, Principal, DateUtils) {

        $scope.pgmsElpc = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        var number = 123456;
        var demoDate = "2016-04-20";
        var demoString = "Demo Data";

        $scope.users = User.query({filter: 'pgmsElpc-is-null'});

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
            PgmsElpc.get({id : id}, function(result) {
                $scope.pgmsElpc = result;
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

           $scope.pgmsElpc.empName = empId.fullName;
           $scope.pgmsElpc.empCode = empId.id;
           $scope.pgmsElpc.instCode = empId.departmentInfo.departmentInfo.departmentCode;
           $scope.pgmsElpc.instName = empId.departmentInfo.departmentInfo.departmentName;
           $scope.pgmsElpc.desigId = empId.designationInfo.designationInfo.id;
           $scope.pgmsElpc.designation = empId.designationInfo.designationInfo.designationName;
           $scope.pgmsElpc.dateOfBirth = empId.birthDate;
           $scope.pgmsElpc.joinDate = empId.dateOfJoining;
           $scope.pgmsElpc.beginDateOfRetiremnt = demoDate;
           $scope.pgmsElpc.retirementDate = empId.retirementDate;
           $scope.pgmsElpc.lastRcvPayscale = number;
           $scope.pgmsElpc.incrsDtOfYrlyPayment = number;
           $scope.pgmsElpc.gainingLeave = number;
           $scope.pgmsElpc.leaveType = demoString;
           $scope.pgmsElpc.leaveTotal = number;
           $scope.pgmsElpc.mainPayment = number;
           $scope.pgmsElpc.incrMonRateLeaving = number;
           $scope.pgmsElpc.specialPayment = number;
           $scope.pgmsElpc.specialAllowance = number;
           $scope.pgmsElpc.houserentAl = number;
           $scope.pgmsElpc.treatmentAl = number;
           $scope.pgmsElpc.dearnessAl = number;
           $scope.pgmsElpc.travellingAl = number;
           $scope.pgmsElpc.laundryAl = number;
           $scope.pgmsElpc.personalAl = number;
           $scope.pgmsElpc.technicalAl = number;
           $scope.pgmsElpc.hospitalityAl = number;
           $scope.pgmsElpc.tiffinAl = number;
           $scope.pgmsElpc.advOfMakingHouse = number;
           $scope.pgmsElpc.vechileStatus = number;
           $scope.pgmsElpc.advTravAl = number;
           $scope.pgmsElpc.advSalary = number;
           $scope.pgmsElpc.houseRent = number;
           $scope.pgmsElpc.carRent = number;
           $scope.pgmsElpc.gasBill = number;
           $scope.pgmsElpc.santryWaterTax = demoString;

           PgmsElpcBankInfo.get({empInfoId:empId.id}, function(result) {

               $scope.pgmsElpc.bankAcc = result.accountNumber;
           });

           $scope.pgmsElpc.accBookNo = demoString;
           $scope.pgmsElpc.bookPageNo = number;
           $scope.pgmsElpc.bankInterest = number;
           $scope.pgmsElpc.monlyDepRateFrSalary = number;
           $scope.pgmsElpc.expectedDeposition = number;
           $scope.pgmsElpc.expectedDeposition = number;
           $scope.pgmsElpc.accDate = demoDate;
           $scope.pgmsElpc.appNo = demoString;

           var dataJsn = {employeeId:empId};
           console.log("dataJsn:"+JSON.stringify(dataJsn));
          // PgmsGrObtainSpecEmpInfo.get(dataJsn, function(result) {

              //$scope.gratuityRateList = empId;
             // console.log("Employee Details Information:"+JSON.stringify($scope.gratuityRateList));
           //});
        }

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsElpcUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsElpc");
        };
        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.pgmsElpc.updateBy = $scope.loggedInUser.id;
            $scope.pgmsElpc.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsElpc.id != null) {
                PgmsElpc.update($scope.pgmsElpc, onSaveFinished,onSaveError);
                $rootScope.setWarningMessage('stepApp.pgmsElpc.updated');
            } else {
                $scope.pgmsElpc.createBy = $scope.loggedInUser.id;
                $scope.pgmsElpc.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsElpc.save($scope.pgmsElpc, onSaveFinished,onSaveError);
                $rootScope.setSuccessMessage('stepApp.pgmsElpc.created');
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
