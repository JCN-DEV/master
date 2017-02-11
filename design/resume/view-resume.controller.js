'use strict';

angular.module('stepApp')
    .controller('ViewResumeController', function ($scope, Sessions, Jobapplication, Principal, ParseLinks, EducationalQualification) {
        Principal.identity().then(function (account) {
            $scope.account = account;
        });

        $scope.profile = function () {
            Principal.identity(true).then(function (account) {
                $scope.settingsAccount = account;
            });
        };
        $scope.qualifications = function () {


        };

        $scope.educations = function () {
            EducationalQualification.query({page: $scope.page, size: 10}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.educationalQualifications = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.experiences = function () {

        };

        $scope.others = function () {

        };


    });
