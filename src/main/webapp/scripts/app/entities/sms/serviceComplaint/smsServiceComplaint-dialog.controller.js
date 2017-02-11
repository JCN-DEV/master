'use strict';

angular.module('stepApp').controller('SmsServiceComplaintDialogController',
    ['$scope', '$stateParams', '$state', 'DataUtils', 'entity', 'SmsServiceComplaint', 'SmsServiceName', 'SmsServiceTypeByStatus', 'SmsServiceDepartment','DateUtils',
        function($scope, $stateParams, $state, DataUtils, entity, SmsServiceComplaint, SmsServiceName, SmsServiceTypeByStatus, SmsServiceDepartment,DateUtils)
        {

        $scope.smsServiceComplaint = entity;
        $scope.smsservicenames = SmsServiceName.query();
        $scope.smsservicetypes = SmsServiceTypeByStatus.query({stat:true});
        $scope.smsservicedepartments = SmsServiceDepartment.query();

        $scope.filteredNameList = $scope.smsservicenames;
        $scope.SelectedName = "Select a Name";

        $scope.resultValue = 0;
        $scope.captchaHints = "";
        $scope.mathCaptchText = "";
        $scope.numberBounds = {lower: 5,upper: 50};

        $scope.load = function(id)
        {
            SmsServiceComplaint.get({id : id}, function(result) {
                $scope.smsServiceComplaint = result;
            });
        };

        $scope.filterNumericValue = function($event){
            if(isNaN(String.fromCharCode($event.keyCode))){
                $event.preventDefault();
            }
        };

        $scope.loadServiceNameByTypes = function(data){
            $scope.filteredNameList = [];
            angular.forEach($scope.smsservicenames,function(nameData){
                if(data.id == nameData.smsServiceType.id){
                    $scope.filteredNameList.push(nameData);
                }
            });
        };
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:smsServiceComplaintUpdate', result);
           $scope.isSaving = false;
            $scope.responseMessage = "Successfully created!!!";
            //$state.go('smsServiceComplaint');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.$on('$viewContentLoaded', function() {
            $scope.generateMathCaptcha();
        });

        $scope.generateMathCaptcha = function()
        {
            var firstNumber     = Math.floor(Math.random() * $scope.numberBounds.lower) + 1;
            var secondNumber    = Math.floor(Math.random() * $scope.numberBounds.upper) + 1;
            $scope.resultValue      = firstNumber + secondNumber;
            $scope.mathCaptchText   = firstNumber+'+'+secondNumber;
            $scope.captchaHints     = "";//$scope.resultValue;
        };

        $scope.save = function ()
        {
            if ($scope.captchaValue == $scope.resultValue)
            {
                $scope.smsServiceComplaint.createDate = DateUtils.convertLocaleDateToServer(new Date());
                $scope.smsServiceComplaint.activeStatus = true;
                $scope.isSaving = true;
                if ($scope.smsServiceComplaint.id != null) {
                    SmsServiceComplaint.update($scope.smsServiceComplaint, onSaveSuccess, onSaveError);
                } else {
                    SmsServiceComplaint.save($scope.smsServiceComplaint, onSaveSuccess, onSaveError);
                }
            }
            else
            {
                $scope.captchaHints = "Please enter the correct value.";
            }
        };

        $scope.clear = function() {
            $scope.responseMessage = "";
            $scope.smsServiceComplaint = {
                previousCode: null,
                priority: null,
                complaintName: null,
                fullName: null,
                emailAddress: null,
                contactNumber: null,
                description: null,
                complaintDoc: null,
                complaintDocContentType: null,
                complaintDocFileName: null,
                activeStatus: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setComplaintDoc = function ($file, smsServiceComplaint) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        smsServiceComplaint.complaintDoc = base64Data;
                        smsServiceComplaint.complaintDocContentType = $file.type;
                        smsServiceComplaint.complaintDocFileName = $file.name;
                    });
                };
            }
        };
}]);
