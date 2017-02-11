'use strict';

angular.module('stepApp')
    .controller('TrainingRequisitionFormDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity','TrainingRequisitionForm', 'TrainingCategorySetup', 'TrainingHeadSetup',
            'Country', 'HrEmployeeInfo','TraineeListByRequisition','Principal',
        function ($scope, $rootScope, $stateParams, entity, TrainingRequisitionForm, TrainingCategorySetup, TrainingHeadSetup,
                  Country, HrEmployeeInfo,TraineeListByRequisition,Principal) {
        $scope.trainingRequisitionForm = entity;
        $scope.traineeInfoList = [];

        //if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
        //    $scope.applyTypeInstitute = true;
        //}else{
        //    $scope.applyTypeDTE = true;
        //}

        TrainingRequisitionForm.get({id: $stateParams.id}, function(result) {
            $scope.trainingRequisitionForm = result;
            TraineeListByRequisition.query({pTrainingReqId:result.id},function(data){
                $scope.traineeInfoList = data;
            });
            if(result.institute !=null){
                console.log("77777777777777777");
                $scope.applyTypeInstitute = true;
            }else{
                console.log("88888888888888888");
                $scope.applyTypeDTE = true;
            }
        });

        var unsubscribe = $rootScope.$on('stepApp:trainingRequisitionFormUpdate', function(event, result) {
            console.log('-------------------------------------');
            $scope.trainingRequisitionForm = result;
        });
        $scope.$on('$destroy', unsubscribe);


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

        $scope.previewImage = function (content, contentType,name) {
            console.log('click-----');
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

    }]);
