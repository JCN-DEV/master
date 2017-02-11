'use strict';

angular.module('stepApp')
    .controller('SkillController',
    ['$scope', '$state', '$modal', 'Skill', 'SkillSearch', 'ParseLinks',
    function ($scope, $state, $modal, Skill, SkillSearch, ParseLinks) {

        $scope.skills = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Skill.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.skills = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            SkillSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.skills = result;
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
            $scope.skill = {
                name: null,
                level: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllSkillsSelected = false;

        $scope.updateSkillsSelection = function (skillArray, selectionValue) {
            for (var i = 0; i < skillArray.length; i++)
            {
            skillArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.skills.length; i++){
                var skill = $scope.skills[i];
                if(skill.isSelected){
                    //Skill.update(skill);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.skills.length; i++){
                var skill = $scope.skills[i];
                if(skill.isSelected){
                    //Skill.update(skill);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.skills.length; i++){
                var skill = $scope.skills[i];
                if(skill.isSelected){
                    Skill.delete(skill);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.skills.length; i++){
                var skill = $scope.skills[i];
                if(skill.isSelected){
                    Skill.update(skill);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Skill.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.skills = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
