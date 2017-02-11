'use strict';

angular.module('stepApp')
    .controller('SmsServiceComplaintDetailController',
    ['$window', '$timeout', '$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'SmsServiceComplaint', 'SmsServiceName', 'SmsServiceType', 'SmsServiceDepartment',
    function ($window, $timeout, $scope, $rootScope, $stateParams, DataUtils, entity, SmsServiceComplaint, SmsServiceName, SmsServiceType, SmsServiceDepartment) {
        $scope.smsServiceComplaint = entity;

        $scope.load = function (id) {
            SmsServiceComplaint.get({id: id}, function(result) {
                $scope.smsServiceComplaint = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceComplaintUpdate', function(event, result) {
            $scope.smsServiceComplaint = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.displayAttachment= function(complainObj)
        {
            console.log("onDisply:"+complainObj);
            var blob = b64toBlob(complainObj.complaintDoc, complainObj.complaintDocContentType);
            $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
        }

        function b64toBlob(b64Data, contentType, sliceSize)
        {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;
            var byteCharacters = $window.atob(b64Data);
            var byteArrays = [];
            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize)
            {
                var slice = byteCharacters.slice(offset, offset + sliceSize);

                var byteNumbers = new Array(slice.length);
                for (var i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }
                var byteArray = new Uint8Array(byteNumbers);
                byteArrays.push(byteArray);
            }
            var blob = new Blob(byteArrays, {type: contentType});
            return blob;
        }

    }]);
