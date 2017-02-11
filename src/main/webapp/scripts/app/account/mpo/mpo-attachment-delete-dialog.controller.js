'use strict';

angular.module('stepApp')
	.controller('MpoAttachmentDeleteController',
	['$scope', '$modalInstance', 'entity', 'Attachment',
	function($scope, $modalInstance, entity, Attachment) {

        $scope.attachment = entity;

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.confirmDelete = function (attachmentId) {
            Attachment.delete({id: attachmentId},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
