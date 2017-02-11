'use strict';
angular.module('stepApp').controller('InstGenInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'Division', '$filter', 'entity', '$q', 'District', 'InstGenInfo', 'Institute', 'InstGenInfosLocalitys', 'Upazila', 'InstGenInfoByStatus', 'CheckLogin', 'CheckEmail', 'InstCategory', 'InstLevel','InstCategoryActive','InstLevelActive',
        function ($scope, $rootScope, $stateParams, $state, Division, $filter, entity, $q, District, InstGenInfo, Institute, InstGenInfosLocalitys, Upazila, InstGenInfoByStatus, CheckLogin, CheckEmail, InstCategory, InstLevel, InstCategoryActive, InstLevelActive) {
            $scope.instGenInfo = InstGenInfo.get({id : $stateParams.id});
            $scope.divisions = Division.query();
            $scope.instCategories = InstCategoryActive.query();
            $scope.instLevels = InstLevelActive.query();
            $scope.mpoEnlistedDisable = true;
            $scope.mpoRelatedFieldDisable = true;
            $scope.mpoCodeError = false;
            $scope.allDistrict = [];
            $scope.allUpazila = [];
            $scope.checkActivity = false;
            /*$scope.email = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;*/

            if($stateParams.id != null){
                $scope.fullColumn = true;
                console.log($scope.fullColumn);
            }else {
                $scope.fullColumn = false;
                console.log($scope.fullColumn);
            }

            /*$scope.instructor = {
                isActive: true,
                course: 'chemistry'
            };*/

            if ($scope.allDistrict.length == 0) {
                District.query({page: $scope.page, size: 100}, function (result, headers) {
                    $scope.allDistrict = result;
                });
            }
            if ($scope.allUpazila.length == 0) {
                Upazila.query({page: $scope.page, size: 5000}, function (result, headers) {
                    $scope.allUpazila = result;
                });
            }
            $scope.updatedDistrict = function (select) {
                if ($scope.allDistrict.length == 0) {
                    District.query({page: $scope.page, size: 100}, function (result, headers) {
                        $scope.allDistrict = result;
                        console.log(select);
                        console.log($scope.allDistrict);

                        $scope.districts = [];
                        angular.forEach($scope.allDistrict, function (district) {
                            if (select != undefined && select.id == district.division.id) {
                                $scope.districts.push(district);
                            }
                        });
                    });
                } else {
                    console.log(select);
                    console.log($scope.allDistrict);

                    $scope.districts = [];
                    angular.forEach($scope.allDistrict, function (district) {
                        if (select != undefined && select.id == district.division.id) {
                            $scope.districts.push(district);
                        }
                    });
                }


            };
            $scope.updatedUpazila = function (select) {

                if ($scope.allUpazila.length == 0) {
                    Upazila.query({page: $scope.page, size: 5000}, function (result, headers) {
                        $scope.allUpazila = result;
                        $scope.upazilas = [];
                        angular.forEach($scope.allUpazila, function (upazila) {
                            if (select != undefined && select.id == upazila.district.id) {
                                $scope.upazilas.push(upazila);
                            }
                        });
                    });
                } else {
                    $scope.upazilas = [];
                    angular.forEach($scope.allUpazila, function (upazila) {
                        if (select != undefined && select.id == upazila.district.id) {
                            $scope.upazilas.push(upazila);
                        }
                    });
                }

            };
            $scope.retriveGenInfo = function () {

                if ($scope.instGenInfo == null) {
                    $scope.instGenInfo = entity;
                }
                else {
                    $scope.updatedDistrict($scope.instGenInfo.upazila.district.division);
                    $scope.updatedUpazila($scope.instGenInfo.upazila.district);

                    if ($scope.instGenInfo.type != "NonGovernment") {
                        console.log(' gov inst------- ');
                        $scope.mpoEnlistedDisable = true;
                        $scope.mpoRelatedFieldDisable = true;
                        //$scope.instGenInfo.mpoEnlisted = null;
                        $scope.instGenInfo.dateOfMpo = null;
                    }
                    else {
                        $scope.mpoEnlistedDisable = false;
                        $scope.mpoRelatedFieldDisable = true;
                        //$scope.instGenInfo.mpoEnlisted = true;
                    }
                }
            };
            $q.all([entity.$promise]).then(function () {

                $scope.instGenInfo = entity;
                //$scope.instGenInfo.mpoEnlisted = true;
                //$scope.instGenInfo.ownerType = true;
                //$scope.instGenInfo.locality = 1;
                $scope.retriveGenInfo();

                if (entity.upazila) {
                    if (entity.upazila.district) {
                        if (entity.upazila.district.division) {
                            $scope.instGenInfo.division = entity.upazila.district.division;
                            $scope.instGenInfo.district = entity.upazila.district;
                            $scope.instGenInfo.upazila = entity.upazila;
                        }
                        else {
                            $scope.division = "Select Division"
                        }
                    }
                    else {
                        $scope.district = "Select District";
                        $scope.division = "Select Division"
                    }
                }
                else {
                    $scope.division = "Select Division"
                    $scope.district = "Select District";
                    $scope.upazila = "Select Upazilla";
                }

            });

            $scope.click = function (data) {
                if (data) {
                    $scope.instGenInfo.lastExpireDateOfAffiliation = null;
                    $scope.instGenInfo.dateOfMpo = null;
                    $scope.mpoRelatedFieldDisable = false;
                    $scope.mpoCodeError = true;
                    $scope.validateMpoCode();

                    if ($('.req-start').html().indexOf('*') == -1)
                        $('.req-start').append('<strong style="color:red"> * </strong>');
                } else {
                    $scope.instGenInfo.lastExpireDateOfAffiliation = null;
                    $scope.instGenInfo.dateOfMpo = null;
                    $scope.mpoRelatedFieldDisable = true;
                    $scope.mpoCodeError = false;
                }
            }

            $scope.validateMpoCode = function () {
                if ($scope.instGenInfo.mpoCode && $scope.instGenInfo.mpoCode.length) {
                    $scope.mpoCodeError = false;
                }
                else {
                    $scope.mpoCodeError = true;
                }
            }

            InstGenInfosLocalitys.get({}, function (result) {

                $scope.instGenInfolocalitys = result;
                console.log(result);
                angular.forEach(result, function (data) {
                    console.log(data);
                });

            });


            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };

            $scope.deadlineValidation = function () {
                var d1 = Date.parse($scope.instGenInfo.firstAffiliationDate);
                var d2 = Date.parse($scope.instGenInfo.publicationDate);
                if (d1 <= d2) {
                    $scope.dateError = true;
                }else {
                    $scope.dateError = false;
                }
            };

            $scope.deadlineValidation1 = function () {
                var d1 = Date.parse($scope.instGenInfo.publicationDate);
                var d2 = Date.parse($scope.instGenInfo.firstAffiliationDate);
                var d3 = Date.parse($scope.instGenInfo.dateOfMpo);
                if ((d1 <= d2) || (d2 <= d3) || (d1 <= d3)) {
                    $scope.dateError1 = false;
                }else {
                    $scope.dateError1 = true;
                }
            };

            $scope.instGenInfoType = function (data) {
                if (data != "NonGovernment") {
                    $scope.mpoEnlistedDisable = true;
                    $scope.mpoRelatedFieldDisable = true;
                    if($scope.instGenInfo.id == null){
                        $scope.instGenInfo.mpoEnlisted = false;
                    }
                    $scope.instGenInfo.firstRecognitionDate = null;
                    $scope.instGenInfo.firstAffiliationDate = null;
                    $scope.instGenInfo.lastExpireDateOfAffiliation = null;
                    $scope.instGenInfo.dateOfMpo = null;
                }
                else {
                    $scope.mpoEnlistedDisable = false;
                    $scope.mpoRelatedFieldDisable = true;
                    $scope.instGenInfo.mpoEnlisted = false;
                    if ($scope.instGenInfo.mpoEnlisted == true) {
                        $scope.mpoCodeError = true;
                    }
                    else {
                        $scope.mpoCodeError = false;
                    }
                }
            }

            $scope.districts = District.query();
            $scope.upazilas = Upazila.query();

            var onSaveSuccess = function (result) {
                //$rootScope.setWarningMessage('Information Updated Successfully');
                $scope.$emit('stepApp:instGenInfoUpdate', result);
                console.log(result);
                $scope.isSaving = false;
                if ($scope.checkActivity)
                    $state.go('instituteInfo.generalInfo', {}, {reload: true});
                else {
                    $scope.instGenInfo = entity;
                    $state.go('instituteInfo.confirmationMsg', {}, {reload: true});
                }
            };

            var onSaveError = function (result) {
                $rootScope.setWarningMessage('There is an error in submitting application');
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.instGenInfo.id != null) {
                    console.log($scope.instGenInfo);
                    $scope.instGenInfo.status = 2;
                    $scope.checkActivity = true;
                    InstGenInfo.update($scope.instGenInfo, onSaveSuccess, onSaveError);
                } else {
                    $scope.instGenInfo.status = 0;
                    InstGenInfo.save($scope.instGenInfo, onSaveSuccess, onSaveError);
                }

            };
            $scope.clear = function () {
                $scope.instGenInfo = entity;
                localStorage.removeItem('instGenInfo');
            };
            $scope.doArchive = function () {
                console.log($scope.instGenInfo);
                localStorage.removeItem('instGenInfo');
                localStorage.setItem('instGenInfo', JSON.stringify($scope.instGenInfo));
                $state.go('instituteInfo.generalInfo.view');
            };
            $scope.previewInfo = false;
            $scope.showPreview = function (value) {
                $scope.previewInfo = value;
            }
            $scope.editForm = {};
        }]);
