'use strict';

angular.module('stepApp').controller('JobapplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Jobapplication', 'User', 'Job',
        function($scope, $stateParams, $modalInstance, entity, Jobapplication, User, Job) {

        $scope.jobapplication = entity;
        $scope.users = User.query();
        $scope.jobs = Job.query();
        $scope.load = function(id) {
            Jobapplication.get({id : id}, function(result) {
                $scope.jobapplication = result;
                console.log($scope.jobapplication);
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jobapplicationUpdate', result);
            $modalInstance.close(result);
        };



        $scope.save = function () {
            if ($scope.jobapplication.id != null) {
                Jobapplication.update($scope.jobapplication, onSaveFinished);
            } else {
                Jobapplication.save($scope.jobapplication, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
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



        $scope.setCv = function ($file, jobapplication) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        jobapplication.cv = base64Data;
                        jobapplication.cvContentType = $file.type;
                    });
                };
            }
        };
}]);
