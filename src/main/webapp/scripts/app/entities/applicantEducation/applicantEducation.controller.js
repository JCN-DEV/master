'use strict';

angular.module('stepApp')
    .controller('ApplicantEducationController',
    ['$scope', '$state', '$modal', 'ApplicantEducation', 'ApplicantEducationSearch', 'ParseLinks',
    function ($scope, $state, $modal, ApplicantEducation, ApplicantEducationSearch, ParseLinks) {

        $scope.applicantEducations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ApplicantEducation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.applicantEducations = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            ApplicantEducationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.applicantEducations = result;
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
            $scope.applicantEducation = {
                name: null,
                applicantGroup: null,
                gpa: null,
                examYear: null,
                examResultDate: null,
                remarks: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllApplicantEducationsSelected = false;

        $scope.updateApplicantEducationsSelection = function (applicantEducationArray, selectionValue) {
            for (var i = 0; i < applicantEducationArray.length; i++)
            {
            applicantEducationArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.applicantEducations.length; i++){
                var applicantEducation = $scope.applicantEducations[i];
                if(applicantEducation.isSelected){
                    //ApplicantEducation.update(applicantEducation);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.applicantEducations.length; i++){
                var applicantEducation = $scope.applicantEducations[i];
                if(applicantEducation.isSelected){
                    //ApplicantEducation.update(applicantEducation);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.applicantEducations.length; i++){
                var applicantEducation = $scope.applicantEducations[i];
                if(applicantEducation.isSelected){
                    ApplicantEducation.delete(applicantEducation);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.applicantEducations.length; i++){
                var applicantEducation = $scope.applicantEducations[i];
                if(applicantEducation.isSelected){
                    ApplicantEducation.update(applicantEducation);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            ApplicantEducation.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.applicantEducations = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
