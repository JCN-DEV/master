'use strict';

angular.module('stepApp').controller('ResumeAttachmentController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'Principal', 'User', 'Employee', '$state', 'JpEmployee', '$sce',
    function ($scope, $rootScope, $stateParams, DataUtils, Principal, User, Employee, $state, JpEmployee, $sce) {

        $scope.content = '';
        $scope.preview = false;

        $scope.showCv = false;

        Principal.identity().then(function (account) {
            $scope.account = account;
        });


        JpEmployee.get({id: 'my'}, function (result) {
            $scope.jpEmployee = result;
            $scope.showAlert = false;
            if ($scope.jpEmployee.cv) {
                var aType = $scope.jpEmployee.cvContentType;
                $scope.preview = false;
                if(aType.indexOf("image") >= 0 || aType.indexOf("pdf") >= 0) {
                    $scope.preview = true;
                }
                var blob = $rootScope.b64toBlob($scope.jpEmployee.cv, aType);
                $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);
            }
        }, function(res){
            if(res.status == 404)
            {
                $scope.showAlert = true;
            }
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:employee', result);
            $scope.isSaving = false;
            $state.go('resume');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.jpEmployee.id != null) {
                JpEmployee.update($scope.jpEmployee, onSaveSuccess, onSaveError);
            }
        };

        $scope.showMyCv = function () {
            $scope.showCv = !$scope.showCv;
        };

            $scope.setCv = function ($file, jpEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        jpEmployee.cv = base64Data;
                        jpEmployee.cvContentType = $file.type;
                        jpEmployee.cvName = $file.name;

                        $scope.fileAdded = "File added";
                        //$scope.fileName = $file.name;

                        var aType = jpEmployee.cvContentType;
                        $scope.preview = false;
                        if(aType.indexOf("image") >= 0 || aType.indexOf("pdf") >= 0) {
                            $scope.preview = true;
                        }

                        var blob = $rootScope.b64toBlob($scope.jpEmployee.cv, aType);
                        $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);
                    });
                };
            }
        };
    }]);
