'use strict';

angular.module('stepApp')
    .controller('InstEmployeeDetailController',
    ['$scope', '$rootScope', '$stateParams', 'Principal', 'DataUtils',  'entity', 'InstEmployee', 'Institute',  'Religion',  'CourseTech',  'InstEmpAddress', 'InstEmpEduQuali','InstEmplDesignation', 'InstEmployeeCode',
     function ($scope, $rootScope, $stateParams, Principal, DataUtils,  entity, InstEmployee, Institute,  Religion,  CourseTech,  InstEmpAddress, InstEmpEduQuali,InstEmplDesignation, InstEmployeeCode) {

        $scope.hideEditButton = false;
        $scope.hideAddButton = false;
        $scope.allInfo = {};
        $scope.hideRegistration=false;

        Principal.identity().then(function (account) {
            $scope.account = account;
            InstEmployeeCode.get({'code':$scope.account.login},function(result){
                $scope.allInfo=result;
                $scope.instEmployee = result.instEmployee;
                if($scope.instEmployee.category=='Teacher'){
                    $scope.hideRegistration=true;
                }
                if($scope.instEmployee.image !=null){
                    var blob = b64toBlob($scope.instEmployee.image, $scope.instEmployee.imageContentType);
                    $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                   /* $scope.instEmployee.image=null;*/
                }
                if($scope.instEmployee.nidImage !=null){

                    var blob = b64toBlob($scope.instEmployee.nidImage, $scope.instEmployee.nidImageContentType);
                    $scope.nidImageNameurl = (window.URL || window.webkitURL).createObjectURL( blob );
                   /* $scope.instEmployee.image=null;*/
                }

               /* if ($scope.instEmployee.nidImage !=null) {
                   // console.log($scope.cmsSyllabus);
                    var aType = $scope.instEmployee.imageContentType;
                    $scope.preview = false;
                    if(aType.indexOf("image") >= 0 || aType.indexOf("pdf") >= 0) {
                        $scope.preview = true;
                    }
                    console.log($scope.preview);
                    var blob = $rootScope.b64toBlob($scope.instEmployee.nidImage, 'image*,application/pdf');
                    $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);
 }*/

                console.log(' $scope.instEmployee.status------'+ $scope.instEmployee.status);

                $scope.instEmpAddress = result.instEmpAddress;
                $scope.instEmplBankInfo = result.instEmplBankInfo;
                //if( $scope.instEmployee.mpoAppStatus<=3 && $scope.instEmployee.status!=-1){
                if($scope.instEmployee.mpoAppStatus<3 ){
                    if( $scope.instEmployee.status==-1){
                        $scope.hideAddButton=true;
                    }else{
                        $scope.hideEditbutton=true;
                    }
                }else{
                    $scope.hideEditbutton=false;
                }
                /*if( $scope.instEmployee.status==-1){
                    $scope.hideAddButton=true;
                }*/
                console.log(result);
            });
        });


        $scope.load = function (id) {
            InstEmployee.get({id: id}, function(result) {
                $scope.instEmployee = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmployeeUpdate', function(event, result) {
            $scope.instEmployee = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;




        function b64toBlob(b64Data, contentType, sliceSize) {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;

            var byteCharacters = atob(b64Data);
            var byteArrays = [];

            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
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

        $scope.previewNidImage = function (content, contentType,name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = "National Id Image of "+name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };
        $scope.previewBirthImage = function (content, contentType, name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = "Birth Certificate  Image of "+name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };


    }]);
