'use strict';

angular.module('stepApp')
    .controller('JobapplicationController',
     ['$scope', 'Jobapplication', 'JobapplicationSearch', 'ParseLinks', 'EmployeeJobApplication',
     function ($scope, Jobapplication, JobapplicationSearch, ParseLinks, EmployeeJobApplication) {
        $scope.jobapplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Jobapplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobapplications = result;
            });
        };

        EmployeeJobApplication.query( function(result) {
            $scope.jobapplications = result;
            console.log("********");
            console.log($scope.jobapplications.length);
        });

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Jobapplication.get({id: id}, function(result) {
                $scope.jobapplication = result;
                $('#deleteJobapplicationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Jobapplication.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobapplicationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JobapplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jobapplications = result;
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
            $scope.jobapplication = {
                note: null,
                cv: null,
                cvContentType: null,
                expectedSalary: null,
                applicantStatus: null,
                cvType: null,
                appliedDate: null,
                id: null
            };
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.filterActiveApplications = function(){
            return function(item){
                if(item.applicantStatus === "rejected"){
                    return false;
                }
                return true;
            };
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };
    }]);
