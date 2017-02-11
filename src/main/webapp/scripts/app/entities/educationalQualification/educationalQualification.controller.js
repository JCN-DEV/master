'use strict';

angular.module('stepApp')
    .controller('EducationalQualificationController',
    ['$scope', '$state', '$modal', 'EducationalQualification', 'EducationalQualificationSearch', 'ParseLinks',
    function ($scope, $state, $modal, EducationalQualification, EducationalQualificationSearch, ParseLinks) {

        $scope.educationalQualifications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EducationalQualification.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.educationalQualifications = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            EducationalQualificationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.educationalQualifications = result;
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
            $scope.educationalQualification = {
                level: null,
                degree: null,
                major: null,
                result: null,
                passingYear: null,
                duration: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllEducationalQualificationsSelected = false;

        $scope.updateEducationalQualificationsSelection = function (educationalQualificationArray, selectionValue) {
            for (var i = 0; i < educationalQualificationArray.length; i++)
            {
            educationalQualificationArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.educationalQualifications.length; i++){
                var educationalQualification = $scope.educationalQualifications[i];
                if(educationalQualification.isSelected){
                    //EducationalQualification.update(educationalQualification);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.educationalQualifications.length; i++){
                var educationalQualification = $scope.educationalQualifications[i];
                if(educationalQualification.isSelected){
                    //EducationalQualification.update(educationalQualification);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.educationalQualifications.length; i++){
                var educationalQualification = $scope.educationalQualifications[i];
                if(educationalQualification.isSelected){
                    EducationalQualification.delete(educationalQualification);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.educationalQualifications.length; i++){
                var educationalQualification = $scope.educationalQualifications[i];
                if(educationalQualification.isSelected){
                    EducationalQualification.update(educationalQualification);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            EducationalQualification.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.educationalQualifications = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
