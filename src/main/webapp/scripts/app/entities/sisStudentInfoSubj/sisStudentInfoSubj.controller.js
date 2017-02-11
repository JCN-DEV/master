'use strict';

angular.module('stepApp')
    .controller('SisStudentInfoSubjController', function ($scope, SisStudentInfoSubj, SisStudentInfoSubjSearch, ParseLinks) {
        $scope.sisStudentInfoSubjs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SisStudentInfoSubj.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisStudentInfoSubjs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SisStudentInfoSubj.get({id: id}, function(result) {
                $scope.sisStudentInfoSubj = result;
                $('#deleteSisStudentInfoSubjConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SisStudentInfoSubj.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSisStudentInfoSubjConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SisStudentInfoSubjSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.sisStudentInfoSubjs = result;
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
            $scope.sisStudentInfoSubj = {
                subject: null,
                subjectType: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
