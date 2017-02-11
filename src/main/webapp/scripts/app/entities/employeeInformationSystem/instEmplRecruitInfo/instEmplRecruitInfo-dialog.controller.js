'use strict';

angular.module('stepApp').controller('InstEmplRecruitInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$q', '$state', 'entity', 'InstEmplRecruitInfo', 'InstEmployee', 'DataUtils','PayScalesOfActiveGazette',
        function ($scope,$rootScope, $stateParams, $q, $state, entity, InstEmplRecruitInfo, InstEmployee, DataUtils, PayScalesOfActiveGazette) {
            /*$scope.instEmplRecruitInfo = entity;
             $scope.instemployees = InstEmployee.query();
             $scope.load = function(id) {
             InstEmplRecruitInfo.get({id : id}, function(result) {
             $scope.instEmplRecruitInfo = result;
             });
             };*/

            $scope.instEmplRecruitInfo = entity;
            $scope.payScales = [];
            $q.all([$scope.instEmplRecruitInfo.$promise]).then(function () {
                return $scope.instEmplRecruitInfo.$promise;
            });

            PayScalesOfActiveGazette.query({}, function(result){
                $scope.payScales = result;
            });

            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };
            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:instEmplRecruitInfoUpdate', result);
                $scope.isSaving = false;
                $state.go("employeeInfo.recruitmentInfo", {}, {reload: true});
            };
            var onSaveError = function (result) {
                $scope.isSaving = false;
            };
            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.instEmplRecruitInfo.id != null) {
                    InstEmplRecruitInfo.update($scope.instEmplRecruitInfo, onSaveSuccess, onSaveError);
                    $rootScope.setWarningMessage('stepApp.instEmplRecruitInfo.updated');
                } else {
                    InstEmplRecruitInfo.save($scope.instEmplRecruitInfo, onSaveSuccess, onSaveError);
                    $rootScope.setSuccessMessage('stepApp.instEmplRecruitInfo.created');
                }
            };
            $scope.clear = function () {
                $scope.instEmplRecruitInfo = null;
            };
            $scope.abbreviate = DataUtils.abbreviate;
            $scope.byteSize = DataUtils.byteSize;
            $scope.setAttachment = function ($file, instEmplRecruitInfo, attachment, attachmentContentType, attachmentName) {
                if ($file) {
                    console.log('attachment set started');
                    var fileReader = new FileReader();
                    var strin = 'attachment';
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            instEmplRecruitInfo[attachment] = base64Data;
                            instEmplRecruitInfo[attachmentContentType] = $file.type;
                            instEmplRecruitInfo[attachmentName] = $file.name;
                        });
                    };
                }
            };
    }]);
