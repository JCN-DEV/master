'use strict';

angular.module('stepApp')
    .controller('PrincipalRequirementController',
    ['$scope', '$state', '$modal', 'PrincipalRequirement', 'PrincipalRequirementSearch', 'ParseLinks',
    function ($scope, $state, $modal, PrincipalRequirement, PrincipalRequirementSearch, ParseLinks) {

        $scope.principalRequirements = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PrincipalRequirement.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.principalRequirements = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            PrincipalRequirementSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.principalRequirements = result;
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
            $scope.principalRequirement = {
                firstJoiningDateAsLecturer: null,
                firstMPOEnlistingDateAsLecturer: null,
                firstJoiningDateAsAsstProf: null,
                firstMPOEnlistingDateAsstProf: null,
                firstJoiningDateAsVP: null,
                firstMPOEnlistingDateAsVP: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllPrincipalRequirementsSelected = false;

        $scope.updatePrincipalRequirementsSelection = function (principalRequirementArray, selectionValue) {
            for (var i = 0; i < principalRequirementArray.length; i++)
            {
            principalRequirementArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.principalRequirements.length; i++){
                var principalRequirement = $scope.principalRequirements[i];
                if(principalRequirement.isSelected){
                    //PrincipalRequirement.update(principalRequirement);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.principalRequirements.length; i++){
                var principalRequirement = $scope.principalRequirements[i];
                if(principalRequirement.isSelected){
                    //PrincipalRequirement.update(principalRequirement);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.principalRequirements.length; i++){
                var principalRequirement = $scope.principalRequirements[i];
                if(principalRequirement.isSelected){
                    PrincipalRequirement.delete(principalRequirement);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.principalRequirements.length; i++){
                var principalRequirement = $scope.principalRequirements[i];
                if(principalRequirement.isSelected){
                    PrincipalRequirement.update(principalRequirement);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            PrincipalRequirement.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.principalRequirements = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
