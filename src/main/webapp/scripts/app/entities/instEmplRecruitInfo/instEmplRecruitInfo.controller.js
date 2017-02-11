'use strict';

angular.module('stepApp')
    .controller('InstEmplRecruitInfoController',
    ['$scope','$state','$modal','InstEmplRecruitInfo','InstEmplRecruitInfoSearch','ParseLinks',
     function ($scope, $state, $modal, InstEmplRecruitInfo, InstEmplRecruitInfoSearch, ParseLinks) {

        $scope.instEmplRecruitInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplRecruitInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplRecruitInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplRecruitInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplRecruitInfos = result;
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
            $scope.instEmplRecruitInfo = {
                salaryScale: null,
                salaryCode: null,
                monthlySalaryGovtProvided: null,
                monthlySalaryInstituteProvided: null,
                gbResolutionReceiveDate: null,
                gbResolutionAgendaNo: null,
                circularPaperName: null,
                circularPublishedDate: null,
                recruitExamReceiveDate: null,
                dgRepresentativeName: null,
                dgRepresentativeDesignation: null,
                dgRepresentativeAddress: null,
                boardRepresentativeName: null,
                boardRepresentativeDesignation: null,
                boardRepresentativeAddress: null,
                recruitApproveGBResolutionDate: null,
                recruitPermitAgendaNo: null,
                recruitmentDate: null,
                presentInstituteJoinDate: null,
                presentPostJoinDate: null,
                dgRepresentativeRecordNo: null,
                boardRepresentativeRecordNo: null,
                department: null,
                id: null
            };
        };
    }]);
