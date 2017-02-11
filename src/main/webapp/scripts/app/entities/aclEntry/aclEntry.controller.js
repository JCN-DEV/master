'use strict';

angular.module('stepApp')
    .controller('AclEntryController',
     ['$scope', '$state', '$modal', 'AclEntry', 'AclEntrySearch', 'ParseLinks',
    function ($scope, $state, $modal, AclEntry, AclEntrySearch, ParseLinks) {

        $scope.aclEntrys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AclEntry.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclEntrys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            AclEntrySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aclEntrys = result;
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
            $scope.aclEntry = {
                aceOrder: null,
                mask: null,
                granting: false,
                auditSuccess: false,
                auditFailure: false,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllAclEntrysSelected = false;

        $scope.updateAclEntrysSelection = function (aclEntryArray, selectionValue) {
            for (var i = 0; i < aclEntryArray.length; i++)
            {
            aclEntryArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.aclEntrys.length; i++){
                var aclEntry = $scope.aclEntrys[i];
                if(aclEntry.isSelected){
                    //AclEntry.update(aclEntry);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.aclEntrys.length; i++){
                var aclEntry = $scope.aclEntrys[i];
                if(aclEntry.isSelected){
                    //AclEntry.update(aclEntry);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.aclEntrys.length; i++){
                var aclEntry = $scope.aclEntrys[i];
                if(aclEntry.isSelected){
                    AclEntry.delete(aclEntry);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.aclEntrys.length; i++){
                var aclEntry = $scope.aclEntrys[i];
                if(aclEntry.isSelected){
                    AclEntry.update(aclEntry);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            AclEntry.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aclEntrys = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
