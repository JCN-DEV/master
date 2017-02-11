'use strict';

angular.module('stepApp')
    .controller('SisStudentInfoController', function ($scope, SisStudentInfo, SisStudentInfoSearch, ParseLinks) {
        $scope.sisStudentInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SisStudentInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisStudentInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SisStudentInfo.get({id: id}, function(result) {
                $scope.sisStudentInfo = result;
                $('#deleteSisStudentInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SisStudentInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSisStudentInfoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SisStudentInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.sisStudentInfos = result;
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
            $scope.sisStudentInfo = {
                name: null,
                stuPicture: null,
                stuPictureContentType: null,
                instituteName: null,
                TradeTechnology: null,
                studentName: null,
                fatherName: null,
                motherName: null,
                dateOfBirth: null,
                presentAddress: null,
                permanentAddress: null,
                nationality: null,
                nationalIdNo: null,
                birthCertificateNo: null,
                mobileNo: null,
                contactNoHome: null,
                emailAddress: null,
                gender: null,
                maritalStatus: null,
                bloodGroup: null,
                religion: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                curriculum: null,
                applicationId: null,
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
    });
