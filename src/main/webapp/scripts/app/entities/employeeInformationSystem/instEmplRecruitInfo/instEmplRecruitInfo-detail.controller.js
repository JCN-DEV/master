'use strict';

angular.module('stepApp')
    .controller('InstEmplRecruitInfoDetailController',
    ['$scope', 'Principal', 'InstEmployeeCode', '$rootScope', '$stateParams', 'InstEmplRecruitInfo', 'InstEmployee','InstEmplRecruitInfoCurrent','CurrentInstEmployee',
    function ($scope, Principal, InstEmployeeCode, $rootScope, $stateParams, InstEmplRecruitInfo, InstEmployee,InstEmplRecruitInfoCurrent,CurrentInstEmployee) {
        /*$scope.instEmplRecruitInfo = entity;
        $scope.load = function (id) {
            InstEmplRecruitInfo.get({id: id}, function(result) {
                $scope.instEmplRecruitInfo = result;
            });
        };*/

        $scope.hideEditButton = false;
        $scope.hideAddButton = true;
        InstEmplRecruitInfoCurrent.get({},function(result){
            $scope.instEmplRecruitInfo =  result;
            if(!result.instEmplRecruitInfo){
                $scope.hideAddButton = true;
                $scope.hideEditButton = false;
                CurrentInstEmployee.get({},function(result){
                    console.log('hideEditButton'+$scope.hideEditButton);
                    console.log('hideAddButton'+$scope.hideAddButton);
                    console.log(result);
                    if(result.status<2 ){
                        $scope.hideAddButton = true;
                        $scope.hideEditButton = false;
                    }else{
                        $scope.hideAddButton = false;
                        $scope.hideEditButton = true;
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
                console.log($scope.instEmplRecruitInfo);
                if(result.status<=2 ){
                    $scope.hideAddButton = true;
                    $scope.hideEditButton = false;
                }else{
                    $scope.hideEditButton = true;
                    $scope.hideAddButton = false;
                }
                if($scope.instEmplRecruitInfo.instEmployee.mpoAppStatus != undefined && $scope.instEmplRecruitInfo.instEmployee.mpoAppStatus>=3){
                    $scope.hideEditButton = false;
                    $scope.hideAddButton = false;
                    console.log('hideEditButton'+$scope.hideEditButton);
                    console.log('hideAddButton'+$scope.hideAddButton);
                }
            }
            console.log('hideEditButton'+$scope.hideEditButton);
            console.log('hideAddButton'+$scope.hideAddButton);

        });

        var unsubscribe = $rootScope.$on('stepApp:instEmplRecruitInfoUpdate', function(event, result) {
            $scope.instEmplRecruitInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.previewImage = function (content, contentType, name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            //$rootScope.viewerObject.pageTitle = "Birth Certificate  Image of "+name;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

    }]);
