'use strict';

angular.module('stepApp')
    .controller('AclSidController',
    ['$scope', '$state', '$modal', 'AclSid', 'AclSidSearch', 'ParseLinks',
    function ($scope, $state, $modal, AclSid, AclSidSearch, ParseLinks) {

        $scope.aclSids = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AclSid.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclSids = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            AclSidSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aclSids = result;
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
            $scope.aclSid = {
                principal: false,
                sid: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllAclSidsSelected = false;

        $scope.updateAclSidsSelection = function (aclSidArray, selectionValue) {
            for (var i = 0; i < aclSidArray.length; i++)
            {
            aclSidArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.aclSids.length; i++){
                var aclSid = $scope.aclSids[i];
                if(aclSid.isSelected){
                    //AclSid.update(aclSid);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.aclSids.length; i++){
                var aclSid = $scope.aclSids[i];
                if(aclSid.isSelected){
                    //AclSid.update(aclSid);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.aclSids.length; i++){
                var aclSid = $scope.aclSids[i];
                if(aclSid.isSelected){
                    AclSid.delete(aclSid);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.aclSids.length; i++){
                var aclSid = $scope.aclSids[i];
                if(aclSid.isSelected){
                    AclSid.update(aclSid);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            AclSid.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclSids = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
