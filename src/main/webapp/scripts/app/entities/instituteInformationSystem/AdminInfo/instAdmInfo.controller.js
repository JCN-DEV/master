'use strict';

angular.module('stepApp')
    .controller('InstAdmInfoController',
    ['$scope', '$state', '$modal', 'InstAdmInfo', 'InstAdmInfoSearch', 'ParseLinks','Principal',
    function ($scope, $state, $modal, InstAdmInfo, InstAdmInfoSearch, ParseLinks,Principal) {

        $scope.instAdmInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstAdmInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                angular.forEach(result, function(adminInfo) {
                                    if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
                                        console.log("role admin true");
                                        adminInfo.edit=false;
                                        adminInfo.approve=true;
                                        adminInfo.reject=true;
                                        console.log(adminInfo);
                                        if(adminInfo.status!=1){
                                    $scope.instAdmInfos.push(adminInfo);
                                        }
                                    }

                                    else if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
                                        console.log("role user true");
                                        adminInfo.edit=true;
                                        adminInfo.approve=false;
                                        adminInfo.reject=false;
                                        console.log(adminInfo);
                                        $scope.instAdmInfos.push(adminInfo);
                                    }else{
                                        console.log("role another true");
                                        adminInfo.edit=false;
                                        adminInfo.approve=false;
                                        adminInfo.reject=false;
                                        console.log(adminInfo);
                                        $scope.instAdmInfos.push(adminInfo);
                                    }

                                });
                if($scope.instAdmInfos.length==0){
                    $state.go('instGenInfo.instAdmInfo.new');
                }

            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstAdmInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instAdmInfos = result;
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
            $scope.instAdmInfo = {
                adminCounselorName: null,
                counselorMobileNo: null,
                insHeadName: null,
                insHeadMobileNo: null,
                deoName: null,
                deoMobileNo: null,
                status: null,
                id: null
            };
        };
    }]);
