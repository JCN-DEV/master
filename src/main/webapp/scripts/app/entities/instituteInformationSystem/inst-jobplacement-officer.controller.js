'use strict';

angular.module('stepApp')
    .controller('InstituteInfoJobPlacementOfficerController',
    ['$scope', '$state', '$modal', 'InsAcademicInfo', 'InsAcademicInfoSearch','Principal', 'ParseLinks','JpAdminsByInstituteId','InstituteByLogin',
    function ($scope, $state, $modal, InsAcademicInfo, InsAcademicInfoSearch,Principal, ParseLinks, JpAdminsByInstituteId, InstituteByLogin) {

       /* Principal.identity().then(function (account) {
            $scope.account = account;
        });*/

        $scope.instEmployees = [];
        $scope.page = 0;
        InstituteByLogin.query({}, function(result){
            JpAdminsByInstituteId.query({id: result.id}, function(result2){
                $scope.instEmployees =result2;
            });
        });

        $scope.search = function () {
            InsAcademicInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.insAcademicInfos = result;
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
            $scope.insAcademicInfo = {
                counselorName: null,
                curriculum: null,
                totalTechTradeNo: null,
                tradeTechDetails: null,
                status: null,
                id: null
            };
        };
    }]);
