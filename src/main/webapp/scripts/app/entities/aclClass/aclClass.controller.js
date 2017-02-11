'use strict';

angular.module('stepApp')
    .controller('AclClassController',
    ['$scope', '$state', '$modal', 'AclClass', 'AclClassSearch', 'ParseLinks',
     function ($scope, $state, $modal, AclClass, AclClassSearch, ParseLinks) {

        $scope.aclClasss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AclClass.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclClasss = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            AclClassSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aclClasss = result;
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
            $scope.aclClass = {
                aclClass: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllAclClasssSelected = false;

        $scope.updateAclClasssSelection = function (aclClassArray, selectionValue) {
            for (var i = 0; i < aclClassArray.length; i++)
            {
            aclClassArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.aclClasss.length; i++){
                var aclClass = $scope.aclClasss[i];
                if(aclClass.isSelected){
                    //AclClass.update(aclClass);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.aclClasss.length; i++){
                var aclClass = $scope.aclClasss[i];
                if(aclClass.isSelected){
                    //AclClass.update(aclClass);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.aclClasss.length; i++){
                var aclClass = $scope.aclClasss[i];
                if(aclClass.isSelected){
                    AclClass.delete(aclClass);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.aclClasss.length; i++){
                var aclClass = $scope.aclClasss[i];
                if(aclClass.isSelected){
                    AclClass.update(aclClass);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            AclClass.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclClasss = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
