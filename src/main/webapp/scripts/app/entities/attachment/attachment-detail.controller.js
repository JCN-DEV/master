'use strict';

angular.module('stepApp')
    .controller('AttachmentDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Attachment', 'InstEmployee', 'AttachmentCategory',
    function ($scope, $rootScope, $stateParams, entity, Attachment, InstEmployee, AttachmentCategory) {
        $scope.attachment = entity;
        $scope.load = function (id) {
            Attachment.get({id: id}, function(result) {
                $scope.attachment = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:attachmentUpdate', function(event, result) {
            $scope.attachment = result;
        });
        $scope.$on('$destroy', unsubscribe);


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
    }]);
