'use strict';

angular.module('stepApp')
    .controller('PfmsLoanAppRequestLController',
    ['$scope', '$state', '$rootScope', '$timeout', 'DataUtils', 'PfmsLoanApplication', 'PfmsLoanApplicationByEmployee', 'HrEmployeeInfo', 'Principal',  'DateUtils',

    function($scope, $state, $rootScope, $timeout, DataUtils, PfmsLoanApplication, PfmsLoanApplicationByEmployee,
                                                             HrEmployeeInfo, Principal, DateUtils)
    {

        $scope.newRequestList = [];
        $scope.approvedList = [];
        $scope.rejectedList = [];
        $scope.loadingInProgress = true;
        $scope.requestEntityCounter = 0;

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

        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 1;
            $scope.newRequestList = [];
            PfmsLoanApplication.query(function(result)
            {
                console.log(JSON.stringify(result));
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    console.log(JSON.stringify(dtoInfo));
                    if(dtoInfo.approvalStatus == "Pending"){
                        $scope.newRequestList.push(dtoInfo);
                    }
                });
            });
            $scope.newRequestList.sort($scope.sortById);
            $scope.loadApprovedRejectList();

        };

        $scope.sortById = function(a,b){
            return b.id - a.id;
        };

        $scope.approvalViewAction = function ()
        {
            if($scope.approvalObj.isApproved)
                $scope.approvalObj.approvalStatus='Approved';
            else $scope.approvalObj.approvalStatus='Reject';
            $scope.approvalAction($scope.approvalObj);
        };

        $scope.approvalActionDirect = function ()
        {

            if($scope.approvalObj.actionType == 'accept') {
                $scope.approvalObj.approvalStatus = 'Approved';
            }
            else {
                $scope.approvalObj.approvalStatus='Reject';
            }
            $scope.approvalAction($scope.approvalObj);
        };

        $scope.approvalAction = function (requestObj){

            PfmsLoanApplication.update(requestObj, function(result)
            {
                $('#approveRejectConfirmation').modal('hide');
                $('#approveViewDetailForm').modal('hide');
                //$scope.updateLeaveBalance(requestObj);
                $scope.loadAll();
            });
        };

        /*$scope.updateLeaveBalance = function (requestObj) {
            $scope.isSaving = false;
            AlmEmpLeaveBalance.query(function(result){
                angular.forEach(result,function(dtoInfo) {
                    if(dtoInfo.employeeInfo.id == requestObj.employeeInfo.id
                        && dtoInfo.almLeaveType.id == requestObj.almEmpLeaveApplication.almLeaveType.id){
                        $scope.almEmpLeaveBalance = dtoInfo;
                        if(requestObj.approvalStatus == 'Approved'){
                            dtoInfo.leaveBalance = dtoInfo.leaveBalance + requestObj.almEmpLeaveApplication.leaveDays;
                            dtoInfo.leaveOnApply = dtoInfo.leaveOnApply - requestObj.almEmpLeaveApplication.leaveDays;
                        }
                        dtoInfo.updateBy = $scope.loggedInUser.id;
                        dtoInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        AlmEmpLeaveBalance.update(dtoInfo);
                    }

                });
            });

        };*/

        $scope.loadApprovedRejectList = function ()
        {
            $scope.approvedList = [];
            $scope.rejectedList = [];
            PfmsLoanApplication.query({}, function(result)
            {
                angular.forEach(result,function(requestObj)
                {
                    if(requestObj.approvalStatus=="Approved")
                    {
                        $scope.approvedList.push(requestObj);
                    }
                    if(requestObj.approvalStatus=="Reject")
                    {
                        $scope.rejectedList.push(requestObj);
                    }
                });
            },function(response)
            {
                console.log("data from view load failed");
            });
        };

        $scope.searchText = "";
        $scope.updateSearchText = "";

        $scope.clearSearchText = function (source)
        {
            if(source=='request')
            {
                $timeout(function(){
                    $('#searchText').val('');
                    angular.element('#searchText').triggerHandler('change');
                });
            }
        };

        $scope.searchTextApp = "";

        $scope.clearSearchTextApp = function (source)
        {
            if(source=='approved')
            {
                $timeout(function(){
                    $('#searchTextApp').val('');
                    angular.element('#searchTextApp').triggerHandler('change');
                });
            }
        };

        $scope.searchTextRej = "";

        $scope.clearSearchTextRej = function (source)
        {
            if(source=='approved')
            {
                $timeout(function(){
                    $('#searchTextRej').val('');
                    angular.element('#searchTextRej').triggerHandler('change');
                });
            }
        };


        $scope.approvalViewDetail = function (dataObj)
        {
            $scope.approvalObj = dataObj;
            $('#approveViewDetailForm').modal('show');
        };

        $scope.approvalConfirmation = function (dataObj, actionType){
            $scope.approvalObj = dataObj;
            $scope.approvalObj.actionType = actionType;
            $('#approveRejectConfirmation').modal('show');
        };

        $scope.clear = function () {
            $scope.approvalObj = {
                entityId: null,
                employeeId:null,
                entityName:null,
                requestFrom:null,
                requestSummary: null,
                requestDate:null,
                approveState: null,
                logStatus:null,
                logComments:null,
                actionType:'',
                entityObject: null
            };
        };

        $rootScope.$on('onEntityApprovalProcessCompleted', function(event, data)
        {
            $scope.loadAll();
        });


        $scope.loadEmployee = function () {
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;

            }, function (response) {
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
            })
        };

        $scope.sort = function(keyname, source){
            if(source=='request')
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }

            else if(source=='approved')
            {
                $scope.sortKey3 = keyname;
                $scope.reverse3 = !$scope.reverse3;
            }
            else if(source=='rejected')
            {
                $scope.sortKey4 = keyname;
                $scope.reverse4 = !$scope.reverse4;
            }

        };

        $scope.loadAll();

    }])
;

