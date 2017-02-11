'use strict';

angular.module('stepApp')
    .controller('DlBookRequisitionController', function ($scope, DlBookRequisition, DlBookRequisitionSearch, ParseLinks) {
        $scope.dlBookRequisitions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlBookRequisition.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlBookRequisitions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DlBookRequisition.get({id: id}, function(result) {
                $scope.dlBookRequisition = result;
                $('#deleteDlBookRequisitionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DlBookRequisition.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDlBookRequisitionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DlBookRequisitionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlBookRequisitions = result;
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
            $scope.dlBookRequisition = {
                title: null,
                edition: null,
                authorName: null,
                createDate: null,
                updateDate: null,
                createBy: null,
                updateBy: null,
                status: null,
                id: null
            };
        };
    });
