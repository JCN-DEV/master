'use strict';

angular.module('stepApp')
    .controller('IisCurriculumInfoController',
    ['$scope', '$state', '$modal', 'IisCurriculumInfo', 'IisCurriculumInfoSearch', 'ParseLinks','FindCurriculumByInstId','CurriculumsOfCurrentInst',
    function ($scope, $state, $modal, IisCurriculumInfo, IisCurriculumInfoSearch, ParseLinks,FindCurriculumByInstId, CurriculumsOfCurrentInst) {

       $scope.FindCurriculumByInstId ={};

        //$scope.iisCurriculumInfos = [];
        $scope.page = 0;
        /*$scope.loadAll = function() {
            IisCurriculumInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCurriculumInfos = result;
                console.log("===================================");
                console.log($scope.iisCurriculumInfos);
                if($scope.iisCurriculumInfos.length < 1){
                    $state.go('instituteInfo.iisCurriculumInfo.new',{},{reload:true});
                }
            });
        };*/
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            IisCurriculumInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.iisCurriculumInfos = result;
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
        CurriculumsOfCurrentInst.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.findCurriculumByInstIds = result;
                console.log($scope.findCurriculumByInstIds);

            });

        $scope.clear = function () {
            $scope.iisCurriculumInfo = {
                firstDate: null,
                lastDate: null,
                mpoEnlisted: null,
                recNo: null,
                mpoDate: null,
                id: null
            };
        };
    }]);
