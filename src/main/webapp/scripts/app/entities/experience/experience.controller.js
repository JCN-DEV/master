'use strict';

angular.module('stepApp')
    .controller('ExperienceController',
    ['$scope', '$state', '$modal', 'DataUtils', 'Experience', 'ExperienceSearch', 'ParseLinks',
    function ($scope, $state, $modal, DataUtils, Experience, ExperienceSearch, ParseLinks) {

        $scope.experiences = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Experience.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.experiences = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            ExperienceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.experiences = result;
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
            $scope.experience = {
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

        // bulk operations start
        $scope.areAllExperiencesSelected = false;

        $scope.updateExperiencesSelection = function (experienceArray, selectionValue) {
            for (var i = 0; i < experienceArray.length; i++)
            {
            experienceArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.experiences.length; i++){
                var experience = $scope.experiences[i];
                if(experience.isSelected){
                    //Experience.update(experience);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.experiences.length; i++){
                var experience = $scope.experiences[i];
                if(experience.isSelected){
                    //Experience.update(experience);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.experiences.length; i++){
                var experience = $scope.experiences[i];
                if(experience.isSelected){
                    Experience.delete(experience);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.experiences.length; i++){
                var experience = $scope.experiences[i];
                if(experience.isSelected){
                    Experience.update(experience);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Experience.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.experiences = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
