'use strict';

angular.module('stepApp')
    .controller('AclObjectIdentityController',
     ['$scope', '$state', '$modal', 'AclObjectIdentity', 'AclObjectIdentitySearch', 'ParseLinks',
    function ($scope, $state, $modal, AclObjectIdentity, AclObjectIdentitySearch, ParseLinks) {

        $scope.aclObjectIdentitys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AclObjectIdentity.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclObjectIdentitys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            AclObjectIdentitySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aclObjectIdentitys = result;
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
            $scope.aclObjectIdentity = {
                entriesInheriting: false,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllAclObjectIdentitysSelected = false;

        $scope.updateAclObjectIdentitysSelection = function (aclObjectIdentityArray, selectionValue) {
            for (var i = 0; i < aclObjectIdentityArray.length; i++)
            {
            aclObjectIdentityArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.aclObjectIdentitys.length; i++){
                var aclObjectIdentity = $scope.aclObjectIdentitys[i];
                if(aclObjectIdentity.isSelected){
                    //AclObjectIdentity.update(aclObjectIdentity);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.aclObjectIdentitys.length; i++){
                var aclObjectIdentity = $scope.aclObjectIdentitys[i];
                if(aclObjectIdentity.isSelected){
                    //AclObjectIdentity.update(aclObjectIdentity);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.aclObjectIdentitys.length; i++){
                var aclObjectIdentity = $scope.aclObjectIdentitys[i];
                if(aclObjectIdentity.isSelected){
                    AclObjectIdentity.delete(aclObjectIdentity);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.aclObjectIdentitys.length; i++){
                var aclObjectIdentity = $scope.aclObjectIdentitys[i];
                if(aclObjectIdentity.isSelected){
                    AclObjectIdentity.update(aclObjectIdentity);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            AclObjectIdentity.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclObjectIdentitys = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
