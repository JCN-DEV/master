'use strict';

angular.module('stepApp')
    .controller('PgmsPenGrSetupController',
    ['$scope', 'PgmsPenGrSetup','PgmsPenGrRate', 'PgmsPenGrRateList', 'PgmsPenGrSetupSearch', 'ParseLinks',
    function ($scope, PgmsPenGrSetup,PgmsPenGrRate, PgmsPenGrRateList, PgmsPenGrSetupSearch, ParseLinks) {
        $scope.pgmsPenGrSetups = [];
        $scope.pgmsPenGrRateListDel = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsPenGrSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsPenGrSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            $scope.pgmsPenGrRateListDel = PgmsPenGrRateList.get({penGrSetId : id});
            //console.log("POST "+JSON.stringify($scope.iisApplicantInfoListDel));
            angular.forEach($scope.pgmsPenGrRateListDel,function(ratDel)
            {
                PgmsPenGrRate.get({id: appDel.id});

            });
            PgmsPenGrSetup.get({id: id}, function(result) {
                $scope.pgmsPenGrSetup = result;
                $('#deletePgmsPenGrSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
           angular.forEach($scope.pgmsPenGrRateListDel,function(appDelId)
           {
               PgmsPenGrRate.delete({id: appDelId.id});
           });
            PgmsPenGrSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsPenGrSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsPenGrSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsPenGrSetups = result;
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
            $scope.pgmsPenGrSetup = {
                setupType: null,
                effectiveDate: null,
                setupVersion: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
