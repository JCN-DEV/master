'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanApprovePanelController',
        ['$scope','$rootScope', '$stateParams','entity','EmployeeLoanRequisitionForm', 'EmployeeLoanTypeSetup', 'EmployeeLoanRulesSetup',
            'LoanAttachmentByEmployeeAndAppNameAndRequisitionId','GetApproveDataByRequisitionId',
        function ($scope,$rootScope, $stateParams,entity,EmployeeLoanRequisitionForm, EmployeeLoanTypeSetup, EmployeeLoanRulesSetup,
                  LoanAttachmentByEmployeeAndAppNameAndRequisitionId,GetApproveDataByRequisitionId) {

            $scope.employeeLoanRequisitionForm = entity;

                EmployeeLoanRequisitionForm.get({id: $stateParams.id}, function(result) {
                      $scope.employeeLoanRequisitionForm = result;
                    LoanAttachmentByEmployeeAndAppNameAndRequisitionId.query({employeeInfoId:result.employeeInfo.id,
                                                                              applicationName:"Employee-Loan-Application",
                                                                              requisitionId:result.id }, function(res){
                        console.log('------------------------------');
                        $scope.attachments = res;
                    });
                    GetApproveDataByRequisitionId.query({loanReqId:result.id},function(data){
                        $scope.employeeLoanApprovedAndForwards = data;
                    });
                });


            $scope.previewImage = function (content, contentType,name) {
                var blob = $rootScope.b64toBlob(content, contentType);
                $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
                $rootScope.viewerObject.contentType = contentType;
                $rootScope.viewerObject.pageTitle = name;
                // call the modal
                $rootScope.showFilePreviewModal();
            };

            var unsubscribe = $rootScope.$on('stepApp:employeeLoanApproveUpdate', function(event, result) {
                  $scope.employeeLoanRequisitionForm = result;
            });
            $scope.$on('$destroy', unsubscribe);
    }]);
