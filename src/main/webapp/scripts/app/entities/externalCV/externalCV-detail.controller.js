'use strict';

angular.module('stepApp')
    .controller('ExternalCVDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'ExternalCV',
     function ($scope, $rootScope, $stateParams, entity, ExternalCV) {
        $scope.externalCV = entity;
        $scope.load = function (id) {
            ExternalCV.get({id: id}, function(result) {
                $scope.externalCV = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:externalCVUpdate', function(event, result) {
            $scope.externalCV = result;
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
