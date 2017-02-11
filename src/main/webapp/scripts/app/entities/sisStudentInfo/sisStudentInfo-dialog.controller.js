'use strict';

angular.module('stepApp').controller('SisStudentInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SisStudentInfo', 'Division', 'District', 'Country', 'SisQuota',
        function($scope, $stateParams, $modalInstance, entity, SisStudentInfo, Division, District, Country, SisQuota) {

        $scope.sisStudentInfo = entity;
        $scope.sisStudentInfo = {};
        $scope.divisions = Division.query();
        $scope.districts = District.query();
        $scope.countrys = Country.query();
        $scope.sisquotas = SisQuota.query();
        $scope.load = function(id) {
            SisStudentInfo.get({id : id}, function(result) {
                $scope.sisStudentInfo = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:sisStudentInfoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.sisStudentInfo.id != null) {
                SisStudentInfo.update($scope.sisStudentInfo, onSaveFinished);
            } else {
                SisStudentInfo.save($scope.sisStudentInfo, onSaveFinished);
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

        /*$scope.setStuPicture = function ($file, sisStudentInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        sisStudentInfo.stuPicture = base64Data;
                        sisStudentInfo.stuPictureContentType = $file.type;
                    });
                };
            }
        };*/

        $scope.setStuPicture = function ($file, sisStudentInfo) {
                    if ($file && $file.$error == 'pattern') {
                        return;
                    }
                    if ($file) {
                        if($file.size/1024 > 100){
                            alert("File size should be maximum 100KB!");
                        }else{
                            var fileReader = new FileReader();
                            fileReader.readAsDataURL($file);
                            fileReader.onload = function (e) {
                                var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                                $scope.$apply(function() {
                                    sisStudentInfo.stuPicture = base64Data;
                                    sisStudentInfo.stuPictureContentType = $file.type;
                                    sisStudentInfo.stuPictureName = $file.name;
                                });
                            };
                        }
                    }
                };






}]);
