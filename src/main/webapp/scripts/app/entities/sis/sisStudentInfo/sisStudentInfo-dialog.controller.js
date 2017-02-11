'use strict';

angular.module('stepApp').controller('SisStudentInfoDialogController',
    ['$scope', '$stateParams', 'entity', 'SisStudentInfo', 'Division', 'District', 'UpazilasByDistrict', 'CountrysByName', 'SisQuota', '$state', 'Institute', 'SisStudentReg', 'ParseLinks', 'SisStudentRegByAppId','DistrictsByDivision',
        function ($scope, $stateParams, entity, SisStudentInfo, Division, District, UpazilasByDistrict, CountrysByName, SisQuota, $state, Institute, SisStudentReg, ParseLinks, SisStudentRegByAppId,DistrictsByDivision) {

            $scope.sisStudentInfo = entity;
            $scope.divisions = Division.query();
            //$scope.districts = District.query();
            $scope.countrys = CountrysByName.query();
            $scope.sisquotas = SisQuota.query();
            $scope.sisInstitute = [];
            $scope.sisInstitutes = [];
            $scope.sisApplication = [];
            $scope.sisApplications = [];
            $scope.sisApplicationPreview = false;
            $scope.sisApplicationPreviewOff = true;
            $scope.previewAction = function () {
                //alert( $scope.sisApplicationPreview+' hi '+$scope.sisApplicationPreviewOff);
                if($scope.sisApplicationPreview == false && $scope.sisApplicationPreviewOff == true){
                    $scope.sisApplicationPreview = true;
                    $scope.sisApplicationPreviewOff = false;
                }else if($scope.sisApplicationPreview == true && $scope.sisApplicationPreviewOff == false){
                    $scope.sisApplicationPreview = false;
                    $scope.sisApplicationPreviewOff = true;
                }
            };


            console.log(JSON.stringify(entity));
            $scope.load = function (id) {
                SisStudentInfo.get({id: id}, function (result) {
                    $scope.sisStudentInfo = result;
                });
            };
            $scope.sisStudentInfo.nationality = 'Bangladeshi';
            Institute.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisInstitute = result;

                 $scope.sisInstitute.forEach(function(data)
                 {
                 //console.log(data.id);
                 $scope.sisInstitutes.push(data.code);

                 });
            });

            $scope.DistrictQuery = function(division){
                DistrictsByDivision.query({id:division.id},function(result){
                    $scope.districts=result;
                  });
            };

            $scope.DistrictPermanent = function(divisionId){
                DistrictsByDivision.query({id:divisionId.id},function(result){
                    $scope.districtions=result;
                });
            };

            $scope.LoadUpazila = function(district){
                console.log(district.id);
                UpazilasByDistrict.query({id:district.id},function(result){
                    $scope.Upazilas=result;
                    console.log($scope.Upazilas);
                });
            };

            $scope.LoadPerUpazila=function(dist){
                UpazilasByDistrict.query({id:dist.id},function(result){
                    $scope.UpazilaPer=result;
                    console.log($scope.UpazilaPer);
                });
            };

            SisStudentReg.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisApplication = result;
                //console.log("Application");
                $scope.sisApplication.forEach(function(data)
                {
                    //console.log("Application");
                    //console.log(data.applicationId);
                    $scope.sisApplications.push(data.applicationId);
                });
                //console.log($scope.sisApplications);
            });

            //36649151454 - Dhamrai Polytechnic Institute, 32791151454 - lalbag polytechnic institute, 31471151454 - khilgaon Polytechnic Institute
            //$scope.applicationId = ["Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Dakota", "North Carolina", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"];
            //$scope.applicationIds = [36649151454, 32791151454, 31471151454];
            //$scope.sisInstitutes = ["Dhamrai Polytechnic Institute", "lalbag polytechnic institute", "khilgaon Polytechnic Institute"];

            /*$scope.states = $scope.sisInstitute;*/


            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:sisStudentInfoUpdate', result);
                $state.go('sisStudentInfo');
            };


            $scope.searchApplicationId = function (searchdata) {
                if(searchdata != null){
                    alert(searchdata);

                    $scope.sisStudentReg = [];
                    SisStudentRegByAppId.get({applicationId : searchdata}, function(result) {
                        $scope.sisStudentReg = result;
                        //console.log($scope.sisStudentReg);
                        $scope.sisStudentInfo.name = $scope.sisStudentReg.studentName;
                        $scope.sisStudentInfo.instituteName = $scope.sisStudentReg.instituteName;
                        $scope.sisStudentInfo.fatherName = $scope.sisStudentReg.fatherName;
                        $scope.sisStudentInfo.motherName = $scope.sisStudentReg.motherName;
                        $scope.sisStudentInfo.dateOfBirth = $scope.sisStudentReg.dateOfBirth;
                        $scope.sisStudentInfo.gender = $scope.sisStudentReg.gender;
                        $scope.sisStudentInfo.religion = $scope.sisStudentReg.religion;
                        $scope.sisStudentInfo.bloodGroup = $scope.sisStudentReg.bloodGroup;
                        $scope.sisStudentInfo.quotaReg = $scope.sisStudentReg.quota;
                        $scope.sisStudentInfo.presentAddress = $scope.sisStudentReg.presentAddress;
                        $scope.sisStudentInfo.permanentAddress = $scope.sisStudentReg.permanentAddress;
                        $scope.sisStudentInfo.mobileNo = $scope.sisStudentReg.mobileNo;
                        $scope.sisStudentInfo.curriculum = $scope.sisStudentReg.curriculum;
                        alert($scope.sisStudentReg.tradeTechnology);
                        $scope.sisStudentInfo.TradeTechnology = $scope.sisStudentReg.tradeTechnology;
                        $scope.sisStudentInfo.shift = $scope.sisStudentReg.shift;
                        $scope.sisStudentInfo.emailAddress = $scope.sisStudentReg.emailAddress;
                        $scope.sisStudentInfo.maritalStatus = $scope.sisStudentReg.maritalStatus;
                    });
                    //$scope.sisStudentInfo
                }else{
                    alert("Please Enter Application ID");
                }

            };
            $scope.save = function () {
                if ($scope.sisStudentInfo.id != null) {
                    SisStudentInfo.update($scope.sisStudentInfo, onSaveFinished);
                } else {
                    //selectedInstitute
                    SisStudentInfo.save($scope.sisStudentInfo, onSaveFinished);
                }
            };

            $scope.clear = function () {
                //$modalInstance.dismiss('cancel');
                $state.go('sisStudentInfo');
            };

            $scope.abbreviate = function (text) {
                if (!angular.isString(text)) {
                    return '';
                }
                if (text.length < 30) {
                    return text;
                }
                return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
            };

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

            $scope.setStuPicture = function ($file, sisStudentInfo) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            sisStudentInfo.stuPicture = base64Data;
                            sisStudentInfo.stuPictureContentType = $file.type;
                        });
                    };
                }
            };
        }]);
