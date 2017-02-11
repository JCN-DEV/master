'use strict';

angular.module('stepApp')
    .controller('InstEmplExperienceController',
    ['$scope','$state','$modal','DataUtils','InstEmplExperience','InstEmplExperienceSearch','ParseLinks',
     function ($scope, $state, $modal, DataUtils, InstEmplExperience, InstEmplExperienceSearch, ParseLinks) {

        $scope.instEmplExperiences = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplExperience.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplExperiences = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplExperienceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplExperiences = result;
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
            $scope.instEmplExperience = {
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
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
