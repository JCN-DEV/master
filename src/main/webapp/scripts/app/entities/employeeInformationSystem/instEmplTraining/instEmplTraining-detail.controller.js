'use strict';

angular.module('stepApp')
    .controller('InstEmplTrainingDetailController',
    ['$scope', 'Principal', 'InstEmployeeCode', '$rootScope', '$stateParams', 'InstEmplTraining', 'InstEmployee','InstEmplTrainingCurrent','CurrentInstEmployee',
     function ($scope, Principal, InstEmployeeCode, $rootScope, $stateParams, InstEmplTraining, InstEmployee,InstEmplTrainingCurrent,CurrentInstEmployee) {


        /*Principal.identity().then(function (account) {
            $scope.account = account;
            InstEmployeeCode.get({'code':$scope.account.login},function(result){
                $scope.instEmplTrainings =  result.instEmplTrainings;
            });
        });*/
        $scope.instEmplTrainings=[];
        $scope.hideEditButton = false;
        $scope.hideAddButton = true;
        InstEmplTrainingCurrent.get({},function(result){
            $scope.instEmplTrainings =  result;
            console.log(result.length);
            console.log(result);
            if(result.length < 1){
                $scope.hideAddButton = true;
                $scope.hideEditButton = false;
                $scope.instEmplTrainings = [{
                    name: null,
                    subjectsCoverd: null,
                    location: null,
                    startedDate: null,
                    endedDate: null,
                    result: null,
                    id: null
                }];
                CurrentInstEmployee.get({},function(result){
                    console.log('hideEditButton'+$scope.hideEditButton);
                    console.log('hideAddButton'+$scope.hideAddButton);
                    console.log(result);
                    if(result.status<=2 ){
                        $scope.hideEditbutton=false;
                        $scope.hideAddButton = true;
                    }else{
                        $scope.hideAddButton = false;
                        $scope.hideEditButton = false;
                    }
                    if(result.mpoAppStatus>=3){
                        $scope.hideEditButton = false;
                        $scope.hideAddButton = false;
                        console.log('hideEditButton'+$scope.hideEditButton);
                        console.log('hideAddButton'+$scope.hideAddButton);
                    }
                });
            }
            else{
                $scope.hideAddButton = false;
                $scope.hideEditButton = true;
                if($scope.instEmplTrainings[0].instEmployee.status<2 ){
                    $scope.hideEditbutton=true;
                }else{
                    $scope.hideEditbutton=false;
                }
                if($scope.instEmplTrainings[0].instEmployee.mpoAppStatus>=3){
                    $scope.hideEditButton = false;
                    $scope.hideAddButton = false;
                    console.log('hideEditButton'+$scope.hideEditButton);
                    console.log('hideAddButton'+$scope.hideAddButton);
                }
            }

        });

       /* CurrentInstEmployee.get({},function(result){
            console.log('hideEditButton'+$scope.hideEditButton);
            console.log('hideAddButton'+$scope.hideAddButton);
            console.log(result);
            if(!result.mpoAppStatus<3){
                $scope.hideEditButton = false;
                $scope.hideAddButton = false;
                console.log('hideEditButton'+$scope.hideEditButton);
                console.log('hideAddButton'+$scope.hideAddButton);
            }
        });*/

        var unsubscribe = $rootScope.$on('stepApp:instEmplTrainingUpdate', function(event, result) {
            $scope.instEmplTraining = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.previewCertificate = function (content, contentType,name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = "National Id Image of "+name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

    }]);
