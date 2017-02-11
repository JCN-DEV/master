'use strict';

angular.module('stepApp')
    .controller('PgmsAppRetirmntPenController', function ($scope, PgmsAppRetirmntPen,PgmsAppRetirmntNmine,DeleteRetirementNomineeInfosByPenId,DeleteRetirementAttachInfosByPenId,PgmsAppRetirmntPenSearch, ParseLinks) {
        $scope.pgmsAppRetirmntPens = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsAppRetirmntPen.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsAppRetirmntPens = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsAppRetirmntPen.get({id: id}, function(result) {
                $scope.pgmsAppRetirmntPen = result;
                $('#deletePgmsAppRetirmntPenConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DeleteRetirementNomineeInfosByPenId.get({penId:id});
            DeleteRetirementAttachInfosByPenId.get({penId:id});
            PgmsAppRetirmntPen.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsAppRetirmntPenConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsAppRetirmntPenSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsAppRetirmntPens = result;
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
            $scope.pgmsAppRetirmntPen = {
                withdrawnType: null,
                applicationType: null,
                rcvGrStatus: false,
                workDuration: null,
                emergencyContact: null,
                bankAccStatus: false,
                bankName: null,
                bankAcc: null,
                bankBranch: null,
                appDate: null,
                appNo: null,
                aprvStatus: 'Pending',
                aprvDate: null,
                aprvComment: null,
                aprvBy: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateBy: null,
                updateDate: null,
                id: null
            };
        };
    });
