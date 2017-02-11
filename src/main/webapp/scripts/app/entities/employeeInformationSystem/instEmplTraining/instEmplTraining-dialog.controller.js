'use strict';

angular.module('stepApp').controller('InstEmplTrainingDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'Principal', 'InstEmployeeCode', 'InstEmplTraining', 'InstEmployee','entity','DataUtils',
        function($scope, $rootScope, $stateParams, $state,Principal, InstEmployeeCode, InstEmplTraining, InstEmployee, entity,DataUtils) {
        /*$scope.instEmplTraining = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplTraining.get({id : id}, function(result) {
                $scope.instEmplTraining = result;
            });
        };*/

        /*Principal.identity().then(function (account) {
            $scope.account = account;
            InstEmployeeCode.get({'code':$scope.account.login},function(result){
                angular.forEach(result.instEmplTrainings,function(data){
                       if($stateParams.id == data.id){
                            console.log(data);
                            $scope.instEmplTraining =  data;
                            $scope.instEmplTraining.result = Number($scope.instEmplTraining.result);
                       }
                });
            });
        });*/

        /*if(entity.length < 1){
            $scope.instEmplTrainings = [{
                name: null,
                subjectsCoverd: null,
                location: null,
                startedDate: null,
                endedDate: null,
                result: null,
                id: null
            }];
        }
        else{
            $scope.instEmplTrainings = entity;
        }*/
            if($stateParams.instEmplTrainings == null){
                $state.go('employeeInfo.trainingInfo',{},{reload:true});
            }else{
                $scope.instEmplTrainings = $stateParams.instEmplTrainings;
            }
            console.log($stateParams);


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
            $scope.$emit('stepApp:instEmplTrainingUpdate', result);
            $scope.isSaving = false;
            $state.go('employeeInfo.trainingInfo',{},{reload: true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            angular.forEach($scope.instEmplTrainings, function(data){
                if (data.id != null) {
                    InstEmplTraining.update(data, onSaveSuccess, onSaveError);
                    $rootScope.setWarningMessage('stepApp.instEmplTraining.updated');
                } else {
                    if(data.name !=null && data.subjectsCoverd !=null&& data.location !=null){
                        InstEmplTraining.save(data, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.instEmplTraining.created');
                    }

                }
            });

        };

        $scope.AddMore = function(){
              $scope.instEmplTrainings.push({
                      name: null,
                      subjectsCoverd: null,
                      location: null,
                      startedDate: null,
                      endedDate: null,
                      result: null,
                      attachment: null,
                      attachmentContentType: null,
                      id: null
                  });
        }

        $scope.clear = function() {
            $state.go('employeeInfo.trainingInfo');
        };
        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setAttachment = function ($file, instEmplExperience) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmplExperience.attachment = base64Data;
                        instEmplExperience.attachmentContentType = $file.type;
                        instEmplExperience.attachmentName = $file.name;
                    });
                };
            }
        };
}]);
