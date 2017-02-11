'use strict';

angular.module('stepApp')
    .controller('InstFinancialInfoController',
    ['$scope','$state','$modal','InstFinancialInfo','InstFinancialInfoSearch','Principal','ParseLinks',
    function ($scope, $state, $modal, InstFinancialInfo, InstFinancialInfoSearch,Principal, ParseLinks) {

        $scope.instFinancialInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstFinancialInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                angular.forEach(result, function(financialInfo) {
                    if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
                        console.log("role admin true");
                        financialInfo.edit=false;
                        financialInfo.approve=true;
                        financialInfo.reject=true;
                        console.log(financialInfo);
                        if(financialInfo.status!=1){
                            $scope.instFinancialInfos.push(financialInfo);
                        }
                    }else if(Principal.hasAnyAuthority(['ROLE_USER'])){
                        console.log("role user true");
                        financialInfo.edit=true;
                        financialInfo.approve=false;
                        financialInfo.reject=false;
                        console.log(financialInfo);
                        $scope.instFinancialInfos.push(financialInfo);
                    }else{
                        console.log("role another true");
                        financialInfo.edit=false;
                        financialInfo.approve=false;
                        financialInfo.reject=false;
                        console.log(financialInfo);
                        $scope.instFinancialInfos.push(financialInfo);
                    }
                    //console.log(genInfo);

                });
                if($scope.instFinancialInfos.length==0){
                    $state.go('instGenInfo.instFinancialInfo.new');
                }
              //  $scope.instFinancialInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstFinancialInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instFinancialInfos = result;
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
            $scope.instFinancialInfo = {
                bankName: null,
                branchName: null,
                accountType: null,
                accountNo: null,
                issueDate: null,
                expireDate: null,
                amount: null,
                status: null,
                id: null
            };
        };
    }]);

