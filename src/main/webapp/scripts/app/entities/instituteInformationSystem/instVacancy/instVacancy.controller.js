'use strict';

angular.module('stepApp')
    .controller('InstVacancyController', function ($scope, $state, $modal, InstVacancy, InstVacancySearch, ParseLinks,InstVacancysCurrentInst, $stateParams, InstVacancysByInstitute, Principal) {

        $scope.instVacancys = [];
        $scope.page = 0;
        if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
            InstVacancysByInstitute.query({instituteId: $stateParams.instituteId}, function(result){
                $scope.instVacancys = result;
            });
        }else{
            InstVacancysCurrentInst.query({}, function(result) {
                //$scope.links = ParseLinks.parse(headers('link'));
                $scope.instVacancys = result;
            });
        }




        $scope.search = function () {
            InstVacancySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instVacancys = result;
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
            $scope.instVacancy = {
                dateCreated: null,
                dateModified: null,
                status: null,
                empType: null,
                totalVacancy: null,
                filledUpVacancy: null,
                id: null
            };
        };
    });
