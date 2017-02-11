'use strict';

angular.module('stepApp')
    .controller('AttachmentController',
    ['$scope', '$state', 'Attachment', 'AttachmentSearch', 'ParseLinks',
    function ($scope, $state, Attachment, AttachmentSearch, ParseLinks) {
        $scope.attachments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Attachment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.attachments = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Attachment.get({id: id}, function(result) {
                $scope.attachment = result;
                $('#deleteAttachmentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Attachment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAttachmentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AttachmentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.attachments = result;
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
            $scope.attachment = {
                name: null,
                fileName: null,
                file: null,
                fileContentType: null,
                remarks: null,
                id: null
            };
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

		//==================|
		// bulk actions		|
		//==================|
        $scope.areAllAttachmentsSelected = false;

        $scope.updateAttachmentsSelection = function (attachmentArray, selectionValue) {
            for (var i = 0; i < attachmentArray.length; i++)
            {
                attachmentArray[i].isSelected = selectionValue;
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.attachments.length; i++) {
                var attachment = $scope.attachments[i];
                console.info('TODO: export selected item with id: ' + attachment.id);
            }
        };

        $scope.import = function () {
            for (var i = 0; i < $scope.attachments.length; i++) {
                var attachment = $scope.attachments[i];
                console.info('TODO: import selected item with id: ' + attachment.id);
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.attachments.length; i++){
                var attachment = $scope.attachments[i];
                if(attachment.isSelected){
                    Attachment.delete(attachment);
                }
            }
            $state.go('attachment', null, {reload: true});
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.attachments.length; i++){
                var attachment = $scope.attachments[i];
                if(attachment.isSelected){
                    Attachment.update(attachment);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Attachment.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
				$scope.attachments = result;
                $scope.total = headers('x-total-count');
            });
        };


    }]);
