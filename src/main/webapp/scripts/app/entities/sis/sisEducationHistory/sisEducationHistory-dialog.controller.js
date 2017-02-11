'use strict';

angular.module('stepApp').controller('SisEducationHistoryDialogController',
    ['$scope', '$rootScope','$stateParams', 'entity', 'Principal','SisEducationHistory', '$state', 'User','DateUtils','EduLevel', 'EduBoard',
        function($scope, $rootScope, $stateParams,  entity, Principal, SisEducationHistory, $state, User, DateUtils, EduLevel, EduBoard) {

            $scope.sisEducationHistory = entity;
            $scope.edulevels = EduLevel.query();
            $scope.eduboards = EduBoard.query();

            $scope.load = function(id) {
                SisEducationHistory.get({id : id}, function(result) {
                    $scope.sisEducationHistory = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:sisEducationHistoryUpdate', result);
                $state.go('sisEducationHistory');
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:sisEducationHistoryUpdate', result);
                $scope.isSaving = false;
                $state.go('sisEducationHistory');
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function ()
            {
                if ($scope.sisEducationHistory.id != null) {
                    SisEducationHistory.update($scope.sisEducationHistory, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.sisEducationHistory.updated');
                } else {
                    console.log($scope.sisEducationHistory);
                    SisEducationHistory.save($scope.sisEducationHistory, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.sisEducationHistory.created');
                }


                /*Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result)
                    {
                        $scope.isSaving = true;
                        //$scope.sisEducationHistory.updateBy = result.id;
                        //$scope.sisEducationHistory.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if ($scope.sisEducationHistory.id != null)
                        {
                            SisEducationHistory.update($scope.sisEducationHistory, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.sisEducationHistory.updated');
                        }
                        else
                        {
                            //$scope.sisEducationHistory.createBy = result.id;
                            //$scope.sisEducationHistory.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            SisEducationHistory.save($scope.sisEducationHistory, onSaveSuccess, onSaveError);
                            $rootScope.setSuccessMessage('stepApp.sisEducationHistory.created');

                        }
                    });
                });*/
            };


            $scope.clear = function() {
                $modalInstance.dismiss('cancel');
            };

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
        }]);

