'use strict';

angular.module('stepApp')
    .controller('SisStudentRegController', function ($scope, SisStudentReg, SisStudentRegSearch, ParseLinks) {
        $scope.sisStudentRegs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SisStudentReg.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisStudentRegs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SisStudentReg.get({id: id}, function(result) {
                $scope.sisStudentReg = result;
                $('#deleteSisStudentRegConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SisStudentReg.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSisStudentRegConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SisStudentRegSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.sisStudentRegs = result;
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
            $scope.sisStudentReg = {
                applicationId: null,
                instCategory: null,
                instituteName: null,
                curriculum: null,
                TradeTechnology: null,
                subject1: null,
                subject2: null,
                subject3: null,
                subject4: null,
                subject5: null,
                optional: null,
                shift: null,
                semester: null,
                studentName: null,
                fatherName: null,
                motherName: null,
                dateOfBirth: null,
                gender: null,
                religion: null,
                bloodGroup: null,
                quota: null,
                nationality: null,
                mobileNo: null,
                contactNoHome: null,
                emailAddress: null,
                presentAddress: null,
                permanentAddress: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                maritalStatus: null,
                id: null
            };
        };
    });
