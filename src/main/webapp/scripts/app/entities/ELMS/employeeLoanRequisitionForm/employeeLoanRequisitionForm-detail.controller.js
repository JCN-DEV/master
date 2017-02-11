'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanRequisitionFormDetailController',
        ['$scope','$rootScope' , '$stateParams','entity','EmployeeLoanRequisitionForm', 'EmployeeLoanTypeSetup', 'EmployeeLoanRulesSetup','LoanAttachmentByEmployeeAndAppName',
        function ($scope, $rootScope, $stateParams, entity, EmployeeLoanRequisitionForm, EmployeeLoanTypeSetup, EmployeeLoanRulesSetup,LoanAttachmentByEmployeeAndAppName) {
        $scope.employeeLoanRequisitionForm = entity;
        $scope.attachments = [];

        //$scope.load = function (id) {
            EmployeeLoanRequisitionForm.get({id: $stateParams.id}, function(result) {
                $scope.employeeLoanRequisitionForm = result;

                LoanAttachmentByEmployeeAndAppName.query({id:result.employeeInfo.id, applicationName:"Employee-Loan-Application"}, function(data){
                    $scope.attachments = data;

                });


            });
        //};



        $scope.previewImage = function (content, contentType,name) {
            console.log('click-----');
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

        var unsubscribe = $rootScope.$on('stepApp:employeeLoanRequisitionFormUpdate', function(event, result) {
            $scope.employeeLoanRequisitionForm = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
