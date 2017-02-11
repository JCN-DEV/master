'use strict';

angular.module('stepApp')
    .controller('JpEmployeeController',
    ['$scope', 'JpEmployee', 'JpEmployeeSearch', 'ParseLinks',
    function ($scope, JpEmployee, JpEmployeeSearch, ParseLinks) {
        $scope.jpEmployees = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpEmployee.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpEmployees = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpEmployee.get({id: id}, function(result) {
                $scope.jpEmployee = result;
                $('#deleteJpEmployeeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpEmployee.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpEmployeeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpEmployeeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpEmployees = result;
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
            $scope.jpEmployee = {
                name: null,
                fatherName: null,
                motherName: null,
                presentAddress: null,
                permanentAddress: null,
                gender: null,
                dob: null,
                maritialStatus: null,
                nidNo: null,
                nationality: null,
                currentLocation: null,
                mailingAddress: null,
                homePhone: null,
                mobileNo: null,
                officePhone: null,
                email: null,
                alternativeMail: null,
                cv: null,
                cvContentType: null,
                objective: null,
                presentSalary: null,
                expectedSalary: null,
                availibilityType: null,
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
