'use strict';

angular.module('stepApp')
    .controller('InstGenInfoController',
    ['$scope','$state','User','$modal','InstGenInfo','InstGenInfoSearch','Principal','ParseLinks',
    function ($scope, $state, User, $modal, InstGenInfo, InstGenInfoSearch, Principal, ParseLinks) {

        Principal.identity().then(function (account) {
           $scope.account = account;
        });

        $scope.instGenInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstGenInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                console.log('Institute for single user ')
                console.log(result)
                $scope.links = ParseLinks.parse(headers('link'));

                angular.forEach(result, function(genInfo) {
                    if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
                        genInfo.edit=false;
                        genInfo.approved=true;
                        genInfo.reject=true;
                        if(genInfo.status!=1){
                            $scope.instGenInfos.push(genInfo);
                        }
                    }else if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
                        genInfo.edit=true;
                        genInfo.approved=false;
                        genInfo.reject=false;
                        $scope.instGenInfos.push(genInfo);
                    }else{
                        genInfo.edit=false;
                        genInfo.approved=false;
                        genInfo.reject=false;
                        $scope.instGenInfos.push(genInfo);
                    }

                });
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstGenInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instGenInfos = result;
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
            $scope.instGenInfo = {
                code: null,
                name: null,
                publicationDate: null,
                type: null,
                village: null,
                postOffice: null,
                postCode: null,
                landPhone: null,
                mobileNo: null,
                email: null,
                consArea: null,
                status: null,
                id: null
            };
        };
    }]);
