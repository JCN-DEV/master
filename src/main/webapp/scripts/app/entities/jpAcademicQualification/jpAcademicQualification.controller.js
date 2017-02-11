'use strict';

angular.module('stepApp')
    .controller('JpAcademicQualificationController',
    ['$scope', 'JpAcademicQualification', 'JpAcademicQualificationSearch', 'ParseLinks',
    function ($scope, JpAcademicQualification, JpAcademicQualificationSearch, ParseLinks) {
        $scope.jpAcademicQualifications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpAcademicQualification.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpAcademicQualifications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpAcademicQualification.get({id: id}, function(result) {
                $scope.jpAcademicQualification = result;
                $('#deleteJpAcademicQualificationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpAcademicQualification.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpAcademicQualificationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpAcademicQualificationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpAcademicQualifications = result;
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
            $scope.jpAcademicQualification = {
                educationLevel: null,
                degreeTitle: null,
                major: null,
                institute: null,
                resulttype: null,
                result: null,
                passingyear: null,
                duration: null,
                achivement: null,
                id: null
            };
        };
    }]);
