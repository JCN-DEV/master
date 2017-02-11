'use strict';

angular.module('stepApp')
    .controller('InsAcademicInfoController',
    ['$scope','$state','$modal','InsAcademicInfo','InsAcademicInfoSearch','Principal','ParseLinks',
     function ($scope, $state, $modal, InsAcademicInfo, InsAcademicInfoSearch,Principal, ParseLinks) {

       /* Principal.identity().then(function (account) {
            $scope.account = account;
        });*/

        $scope.insAcademicInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InsAcademicInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
               // $scope.insAcademicInfos = result;
                    angular.forEach(result, function(academicInfo) {
                    if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
                        console.log("role admin true");
                        academicInfo.edit=false;
                        academicInfo.approve=true;
                        academicInfo.reject=true;
                        console.log(academicInfo);
                        if(academicInfo.status!=1){
                            $scope.insAcademicInfos.push(academicInfo);
                        }
                    }else if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
                        console.log("role user true");
                        academicInfo.edit=true;
                        academicInfo.approve=false;
                        academicInfo.reject=false;
                        console.log(academicInfo);
                        $scope.insAcademicInfos.push(academicInfo);
                    }else{
                        console.log("role another true");
                        academicInfo.edit=false;
                        academicInfo.approve=false;
                        academicInfo.reject=false;
                        console.log(academicInfo);
                        $scope.insAcademicInfos.push(academicInfo);
                    }
                    //console.log(genInfo);

                });
                if($scope.insAcademicInfos.length==0){
                    $state.go('instGenInfo.insAcademicInfo.new');
                }
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


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
