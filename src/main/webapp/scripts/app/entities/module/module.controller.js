'use strict';

angular.module('stepApp')
    .controller('ModuleController',
    ['$scope','$state','Module','ModuleSearch','ParseLinks',
    function ($scope, $state, Module, ModuleSearch, ParseLinks) {
        $scope.modules = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Module.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.modules = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Module.get({id: id}, function(result) {
                $scope.module = result;
                $('#deleteModuleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Module.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteModuleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ModuleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.modules = result;
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
            $scope.module = {
                name: null,
                description: null,
                moduleCode: null,
                status: null,
                id: null
            };
        };

		//==================|
		// bulk actions		|
		//==================|
        $scope.areAllModulesSelected = false;

        $scope.updateModulesSelection = function (moduleArray, selectionValue) {
            for (var i = 0; i < moduleArray.length; i++)
            {
                moduleArray[i].isSelected = selectionValue;
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.modules.length; i++) {
                var module = $scope.modules[i];
                console.info('TODO: export selected item with id: ' + module.id);
            }
        };

        $scope.import = function () {
            for (var i = 0; i < $scope.modules.length; i++) {
                var module = $scope.modules[i];
                console.info('TODO: import selected item with id: ' + module.id);
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.modules.length; i++){
                var module = $scope.modules[i];
                if(module.isSelected){
                    Module.delete(module);
                }
            }
            $state.go('module', null, {reload: true});
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.modules.length; i++){
                var module = $scope.modules[i];
                if(module.isSelected){
                    Module.update(module);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Module.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
				$scope.modules = result;
                $scope.total = headers('x-total-count');
            });
        };


    }]);
