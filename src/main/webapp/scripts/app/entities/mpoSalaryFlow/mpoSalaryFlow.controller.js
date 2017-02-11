'use strict';

angular.module('stepApp')
    .controller('MpoSalaryFlowController',
    ['$rootScope','$scope','MpoSalaryFlow','MpoSalaryFlowSearch','ParseLinks','$sce', 'NotificationStep',
    function ($rootScope, $scope, MpoSalaryFlow, MpoSalaryFlowSearch, ParseLinks, $sce, NotificationStep) {
        $scope.url = "";
        $scope.reportId = 0;
        $scope.reportApprovedReject = 0;
        $scope.reportComments = "";
        $scope.mpoSalaryFlows = [];
        $scope.mpoSalaryFlow = {};
        $scope.page = 0;


        $scope.loadAll = function() {
            MpoSalaryFlow.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoSalaryFlows = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MpoSalaryFlow.get({id: id}, function(result) {
                $scope.mpoSalaryFlow = result;
                $('#deleteMpoSalaryFlowConfirmation').modal('show');
            });
        };
        $scope.showReport = function (urlss, id) {
            $scope.reportComments = "";
            $scope.url = $sce.trustAsResourceUrl(urlss);
            $scope.reportId = id;
            $('#deleteMpoSalaryFlowConfirmation').modal('show');
        };

        $scope.reportApprove = function (reportComments) {
        console.log($scope.reportId);
            MpoSalaryFlow.get({id: $scope.reportId}, function(result) {
                $scope.mpoSalaryFlow = result;
                $scope.reportApprovedReject = "Approved";
                console.log($scope.mpoSalaryFlow);
                if($rootScope.userRoles == "ROLE_MINISTRY"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Approve By MINISTRY";
                    $scope.mpoSalaryFlow.levels = 2;
                    $scope.mpoSalaryFlow.ministryStatus = "Approved";
                    $scope.mpoSalaryFlow.ministryId = $rootScope.accountId;
                    $scope.mpoSalaryFlow.ministryComments = reportComments;
                    $scope.mpoSalaryFlow.comments = reportComments;
                    $scope.mpoSalaryFlow.ministryApproveDate = new Date();
                } else if($rootScope.userRoles == "ROLE_MPOADMIN" && $scope.mpoSalaryFlow.approveStatus == "Approve By MINISTRY"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_AG";
                    $scope.mpoSalaryFlow.approveStatus = "Forward To AG By DTE";
                    $scope.mpoSalaryFlow.levels = 3;
                    $scope.mpoSalaryFlow.comments = reportComments;
                } else if($rootScope.userRoles == "ROLE_AG" && $scope.mpoSalaryFlow.approveStatus == "Forward To AG By DTE"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Approve By AG";
                    $scope.mpoSalaryFlow.levels = 4;
                    $scope.mpoSalaryFlow.agStatus = "Approved";
                    $scope.mpoSalaryFlow.agId = $rootScope.accountId;
                    $scope.mpoSalaryFlow.agComments = reportComments;
                    $scope.mpoSalaryFlow.comments = reportComments;
                    $scope.mpoSalaryFlow.agApproveDate = new Date();
                } else if($rootScope.userRoles == "ROLE_MPOADMIN" && $scope.mpoSalaryFlow.approveStatus == "Approve By AG"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_BANK";
                    $scope.mpoSalaryFlow.approveStatus = "Forward To BANK By DTE";
                    $scope.mpoSalaryFlow.levels = 5;
                    $scope.mpoSalaryFlow.comments = reportComments;
                } else if($rootScope.userRoles == "ROLE_BANK" && $scope.mpoSalaryFlow.approveStatus == "Forward To BANK By DTE"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Checked and Forwarded to branch By BANK";
                    $scope.mpoSalaryFlow.levels = 7;
                    $scope.mpoSalaryFlow.comments = reportComments;
                    console.log($scope.mpoSalaryFlow.levels);
                } else if($rootScope.userRoles == "ROLE_INSTITUTE"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Checked By INSTITUTE";
                    $scope.mpoSalaryFlow.levels = 6;
                    $scope.mpoSalaryFlow.comments = reportComments;
                }

                //console.log($scope.mpoSalaryFlow);
                if ($scope.mpoSalaryFlow.id != null) {
                    MpoSalaryFlow.update($scope.mpoSalaryFlow, onSaveFinished);
                }
                //MpoSalaryFlow.save($scope.mpoSalaryFlow, onSaveFinished);
                $scope.loadAll();
                $('#deleteMpoSalaryFlowConfirmation').modal('hide');

            });
        };

        $scope.reportReject = function (reportComments) {
            MpoSalaryFlow.get({id: $scope.reportId}, function(result) {
                $scope.mpoSalaryFlow = result;
                $scope.reportApprovedReject = "Rejected";
                if($rootScope.userRoles == "ROLE_MINISTRY"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Reject By MINISTRY";
                    $scope.mpoSalaryFlow.levels = 2;
                    $scope.mpoSalaryFlow.ministryStatus = "Rejected";
                    $scope.mpoSalaryFlow.ministryId = $rootScope.accountId;
                    $scope.mpoSalaryFlow.ministryComments = reportComments;
                    $scope.mpoSalaryFlow.comments = reportComments;
                } else if($rootScope.userRoles == "ROLE_AG" && $scope.mpoSalaryFlow.approveStatus == "Forward To AG By DTE"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Reject By AG";
                    $scope.mpoSalaryFlow.levels = 4;
                    $scope.mpoSalaryFlow.agStatus = "Rejected";
                    $scope.mpoSalaryFlow.agId = $rootScope.accountId;
                    $scope.mpoSalaryFlow.agComments = reportComments;
                    $scope.mpoSalaryFlow.comments = reportComments;
                } else if($rootScope.userRoles == "ROLE_BANK" && $scope.mpoSalaryFlow.approveStatus == "Approve By AG"){
                    $scope.mpoSalaryFlow.forwardToRole = "ROLE_MPOADMIN";
                    $scope.mpoSalaryFlow.approveStatus = "Reject By BANK";
                    $scope.mpoSalaryFlow.levels = 5;
                    $scope.mpoSalaryFlow.comments = reportComments;
                }

                //console.log($scope.mpoSalaryFlow);
                if ($scope.mpoSalaryFlow.id != null) {
                    MpoSalaryFlow.update($scope.mpoSalaryFlow, onSaveFinished);
                }
                //MpoSalaryFlow.save($scope.mpoSalaryFlow, onSaveFinished);
                $scope.loadAll();
                $('#deleteMpoSalaryFlowConfirmation').modal('hide');

            });
        };

        var onSaveFinished = function (result) {
            //$scope.loadAll();
            $('#mpoSalaryFlowConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            MpoSalaryFlow.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMpoSalaryFlowConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            MpoSalaryFlowSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoSalaryFlows = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.mpoSalaryFlow = {
                reportName: null,
                urls: null,
                forwardTo: null,
                forwardFrom: null,
                forwardToRole: null,
                status: null,
                approveStatus: null,
                userLock: null,
                levels: null,
                dteStatus: null,
                dteId: null,
                dteApproveDate: null,
                ministryStatus: null,
                ministryId: null,
                ministryApproveDate: null,
                agStatus: null,
                agId: null,
                agApproveDate: null,
                dteComments: null,
                ministryComments: null,
                agComments: null,
                comments: null,
                id: null
            };
        };
    }]);
