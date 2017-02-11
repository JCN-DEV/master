'use strict';

angular.module('stepApp').controller('HrEmployeeInfoProfileController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', '$q', 'DataUtils', 'HrEmployeeInfo', 'HrDepartmentSetup', 'HrDesignationSetup', 'User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($rootScope, $sce, $scope, $stateParams, $state, $q, DataUtils, HrEmployeeInfo, HrDepartmentSetup, HrDesignationSetup, User, Principal,DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmployeeInfo = {};

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };

        $scope.getLoggedInUser();

        $scope.viewMode = true;
        $scope.addMode = false;
        $scope.noEmployeeFound = false;

        $scope.loadProfile = function ()
        {
            console.log("loadProfile");
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;
                $scope.hrEmployeeInfo.viewMode = true;
                $scope.hrEmployeeInfo.viewModeText = "Edit";
                $scope.hrEmployeeInfo.isLocked = false;
                if($scope.hrEmployeeInfo.logStatus==0 || $scope.hrEmployeeInfo.logStatus==5)
                {
                    $scope.hrEmployeeInfo.isLocked = true;
                }

            }, function (response) {
                $scope.hasProfile = false;
                $scope.clearForAdd();
                $scope.noEmployeeFound = true;
                $scope.addMode = true;
                $scope.hrEmployeeInfo.viewMode = true;
                $scope.hrEmployeeInfo.viewModeText = "Add";
            })
        };

        $scope.loadProfile();

        $scope.changeProfileMode = function (modelInfo)
        {
            if(modelInfo.viewMode)
            {
                modelInfo.viewMode = false;
                modelInfo.viewModeText = "Cancel";
            }
            else
            {
                modelInfo.viewMode = true;
                if(modelInfo.id==null)
                    modelInfo.viewModeText = "Add";
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.changeProfileToAdd = function ()
        {
            $scope.viewMode = false;
            $scope.clearForAdd();
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        //$scope.hrdepartmentsetups = HrDepartmentSetup.query({id:'bystat'});
        //$scope.hrdesignationsetups = HrDesignationSetup.query({id:'bystat'});
        //$scope.workAreaList = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.users = User.query({filter: 'hremployeeinfo-is-null'});
        $q.all([$scope.hrEmployeeInfo.$promise, $scope.users.$promise]).then(function()
        {
            if (!$scope.hrEmployeeInfo.user || !$scope.hrEmployeeInfo.user.id) {
                return $q.reject();
            }
            return User.get({id : $scope.hrEmployeeInfo.user.id}).$promise;
        }).then(function(user) {
            $scope.users.push(user);
        });
        $scope.load = function(id) {
            HrEmployeeInfo.get({id : id}, function(result) {
                $scope.hrEmployeeInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmployeeInfoUpdate', result);
            $scope.isSaving = false;
            $scope.viewMode = true;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.nationalitySelection = "Bangladesh";

        $scope.updateProfile = function (modelInfo)
        {
            console.log("update employee profile");
            $scope.isSaving = true;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            if($scope.nationalitySelection == 'Bangladesh')
                modelInfo.nationality = $scope.nationalitySelection;

            if (modelInfo.id != null)
            {
                modelInfo.logStatus = 0;
                HrEmployeeInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
            else
            {
                modelInfo.logStatus = 0;
                modelInfo.user = $scope.loggedInUser;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());

                HrEmployeeInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {
        };

        $scope.clearForAdd = function () {
            $scope.hrEmployeeInfo = {
                fullName: null,
                fullNameBn: null,
                fatherName: null,
                fatherNameBn: null,
                motherName: null,
                motherNameBn: null,
                birthDate: null,
                apointmentGoDate: null,
                lastWorkingDay:null,
                presentId: null,
                nationalId: null,
                emailAddress: null,
                mobileNumber: null,
                gender: null,
                birthPlace: null,
                anyDisease: null,
                officerStuff: null,
                tinNumber: null,
                maritalStatus: null,
                bloodGroup: null,
                nationality: null,
                quota: null,
                birthCertificateNo: null,
                religion: null,
                empPhoto: null,
                empPhotoContentType: null,
                imageName: null,
                quotaCert: null,
                quotaCertContentType: null,
                quotaCertName: null,
                employeeId:null,
                logId:null,
                logStatus:null,
                logComments:null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                activeAccount:true,
                id: null
            };
        };

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewEmpPhoto = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.empPhoto, modelInfo.empPhotoContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.empPhotoContentType;
            $rootScope.viewerObject.pageTitle = "Employee Photo : "+modelInfo.fullName;

            $rootScope.showPreviewModal();
        };

        $scope.previewQuotaCertDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.quotaCert, modelInfo.quotaCertContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.quotaCertContentType;
            $rootScope.viewerObject.pageTitle = "Quota Certificate : "+modelInfo.fullName;

            $rootScope.showPreviewModal();
        };

        $scope.setEmpPhoto = function ($file, modelInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        modelInfo.empPhoto = base64Data;
                        modelInfo.empPhotoContentType = $file.type;
                        if (modelInfo.imageName == null)
                        {
                            modelInfo.imageName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.setQuotaCertDoc = function ($file, modelInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        modelInfo.quotaCert = base64Data;
                        modelInfo.quotaCertContentType = $file.type;
                        if (modelInfo.quotaCertName == null)
                        {
                            modelInfo.quotaCertName = $file.name;
                        }
                    });
                };
            }
        };
}]);
