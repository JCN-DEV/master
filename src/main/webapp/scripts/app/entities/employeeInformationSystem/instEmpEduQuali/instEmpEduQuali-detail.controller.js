'use strict';

angular.module('stepApp')
    .controller('InstEmpEduQualiDetailController',
    ['$scope','Principal', 'InstEmployeeCode', '$rootScope', '$stateParams', 'DataUtils', 'InstEmpEduQuali', 'InstEmployee','InstEmpEduQualisCurrent','CurrentInstEmployee',
    function ($scope,Principal, InstEmployeeCode, $rootScope, $stateParams, DataUtils, InstEmpEduQuali, InstEmployee,InstEmpEduQualisCurrent,CurrentInstEmployee) {

        /*Principal.identity().then(function (account) {
           $scope.account = account;
           InstEmployeeCode.get({'code':$scope.account.login},function(result){
               console.log(result);
               $scope.EducationalQualifications = result.instEmpEduQualis;
           });
        });*/
     /*   InstEmpEduQualisCurrent.get({},function(result){
            $scope.EducationalQualifications = result;
            console.log(result);
        },function(result){
            console.log(result);
        });
        var unsubscribe = $rootScope.$on('stepApp:instEmpEduQualiUpdate', function(event, result) {
      */

        $scope.EducationalQualifications = [];
        $scope.hideEditButton = false;
        $scope.hideAddButton = true;
        console.log($scope.hideEditButton);
        console.log($scope.hideAddButton);
        InstEmpEduQualisCurrent.get({},function(result){
            console.log(result);
            if(result){
                console.log($scope.hideAddButton);
                $scope.EducationalQualifications = result;
                if($scope.EducationalQualifications.length>0){
                    console.log($scope.hideAddButton);
                    $scope.hideAddButton = false;
                    $scope.hideEditButton = true;
                    console.log($scope.hideEditButton);
                    console.log($scope.hideAddButton);
                    if($scope.EducationalQualifications[0].instEmployee.status<2 ){
                        $scope.hideEditbutton=true;
                    }else{
                        $scope.hideEditbutton=false;
                    }
                    if($scope.EducationalQualifications[0].instEmployee.mpoAppStatus>=3){
                        $scope.hideEditButton = false;
                        $scope.hideAddButton = false;
                    }
                }else{
                    $scope.hideAddButton = true;
                    $scope.hideEditButton = false;
                    console.log($scope.hideEditButton);
                    console.log($scope.hideAddButton);
                    $scope.EducationalQualifications.push(
                        {
                            certificateName: null,
                            board: null,
                            session: null,
                            semester: null,
                            rollNo: null,
                            passingYear: null,
                            cgpa: null,
                            certificateCopy: null,
                            certificateCopyContentType: null,
                            status: null,
                            id: null,
                            instEmployee: null
                        }
                    );
                    CurrentInstEmployee.get({},function(result){
                        console.log(result);
                        if(result.status <= 2 ){
                            $scope.hideEditbutton=false;
                            $scope.hideAddButton = true;
                        }else{
                            $scope.hideAddButton = false;
                            $scope.hideEditButton = false;
                        }
                        if(result.mpoAppStatus >= 3){
                            $scope.hideEditButton = false;
                            $scope.hideAddButton = false;
                        }
                    });

                }
                /*CurrentInstEmployee.get({},function(result){
                 console.log(result);
                 if(!result.mpoAppStatus>=3){
                 $scope.hideEditButton = false;
                 $scope.hideAddButton = false;
                 }
                 });*/
            }else {
                console.log("comes to if");
                $scope.hideEditbutton=false;
                $scope.hideAddButton = true;
            };


        });



        $scope.displayAttachment= function(attachment,fileContentType) {
            var blob = $rootScope.b64toBlob(attachment, fileContentType);
            $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
        }

        /*var unsubscribe = $rootScope.$on('stepApp:instEmpEduQualiUpdate', function(event, result) {
            $scope.instEmpEduQuali = result;
        });*/
       // $scope.$on('$destroy', unsubscribe);

        //$scope.byteSize = DataUtils.byteSize;
        $scope.previewCertificate = function (content, contentType,name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };
    }]);
