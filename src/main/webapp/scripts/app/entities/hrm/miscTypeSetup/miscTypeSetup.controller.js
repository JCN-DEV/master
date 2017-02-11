'use strict';

angular.module('stepApp')
    .controller('MiscTypeSetupController',
     ['$rootScope', '$scope', '$state', 'MiscTypeSetup', 'MiscTypeSetupSearch', 'MiscTypeSetupByCategory', 'ParseLinks',
     function ($rootScope, $scope, $state, MiscTypeSetup, MiscTypeSetupSearch, MiscTypeSetupByCategory, ParseLinks) {

        $scope.miscTypeSetups = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.categoryStat = 'all';
        $scope.typeCategory = '';

        $scope.loadAll = function() {
            MiscTypeSetup.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.miscTypeSetups = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $rootScope.$on('miscTypeSetupUpdateByCategory', function(event, data)
        {
            console.log("EmitAndOn-ON"+data.typeCategory);
            //console.log(JSON.stringify(data));
            $scope.typeCategory = data.typeCategory;
            $scope.loadMiscTypes();
        });
        //$scope.loadAll();

        $scope.loadMiscTypes = function ()
        {
            console.log("category: "+$scope.typeCategory+", stat: "+$scope.categoryStat);

            if($scope.typeCategory != null)
            {
                if($scope.categoryStat !='all')
                {
                    MiscTypeSetupByCategory.get({cat: $scope.typeCategory, stat:$scope.categoryStat}, function (result) {
                        $scope.miscTypeSetups = result;
                    }, function (response) {
                        if (response.status === 404) {
                            $scope.loadAll();
                        }
                    });
                }
                else
                {
                    MiscTypeSetupByCategory.get({cat: $scope.typeCategory}, function (result) {
                        $scope.miscTypeSetups = result;
                    }, function (response) {
                        if (response.status === 404) {
                            $scope.loadAll();
                        }
                    });
                }
            }
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.miscTypeSetup = {
                typeCategory: null,
                typeName: null,
                typeTitle: null,
                typeTitleBn: null,
                typeDesc: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
