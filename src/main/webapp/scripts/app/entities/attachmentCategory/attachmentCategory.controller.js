'use strict';

angular.module('stepApp')
    .controller('AttachmentCategoryController',
    ['$scope', '$state', 'AttachmentCategory', 'AttachmentCategorySearch', 'ParseLinks',
    function ($scope, $state, AttachmentCategory, AttachmentCategorySearch, ParseLinks) {
        $scope.attachmentCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AttachmentCategory.query({page: $scope.page, size: 2000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.attachmentCategorys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AttachmentCategory.get({id: id}, function(result) {
                $scope.attachmentCategory = result;
                $('#deleteAttachmentCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AttachmentCategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAttachmentCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AttachmentCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.attachmentCategorys = result;
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
            $scope.attachmentCategory = {
                applicationName: null,
                attachmentName: null,
                date: null,
                id: null
            };
        };

		//==================|
		// bulk actions		|
		//==================|
        $scope.areAllAttachmentCategorysSelected = false;

        $scope.updateAttachmentCategorysSelection = function (attachmentCategoryArray, selectionValue) {
            for (var i = 0; i < attachmentCategoryArray.length; i++)
            {
                attachmentCategoryArray[i].isSelected = selectionValue;
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.attachmentCategorys.length; i++) {
                var attachmentCategory = $scope.attachmentCategorys[i];
                console.info('TODO: export selected item with id: ' + attachmentCategory.id);
            }
        };

        $scope.import = function () {
            for (var i = 0; i < $scope.attachmentCategorys.length; i++) {
                var attachmentCategory = $scope.attachmentCategorys[i];
                console.info('TODO: import selected item with id: ' + attachmentCategory.id);
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.attachmentCategorys.length; i++){
                var attachmentCategory = $scope.attachmentCategorys[i];
                if(attachmentCategory.isSelected){
                    AttachmentCategory.delete(attachmentCategory);
                }
            }
            $state.go('attachmentCategory', null, {reload: true});
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.attachmentCategorys.length; i++){
                var attachmentCategory = $scope.attachmentCategorys[i];
                if(attachmentCategory.isSelected){
                    AttachmentCategory.update(attachmentCategory);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            AttachmentCategory.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
				$scope.attachmentCategorys = result;
                $scope.total = headers('x-total-count');
            });
        };


    }]);
