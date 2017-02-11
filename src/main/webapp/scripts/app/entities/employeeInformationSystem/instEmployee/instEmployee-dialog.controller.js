'use strict';

angular.module('stepApp').controller('InstEmployeeDialogController',
    ['$scope','BankSetup','$rootScope', '$stateParams', '$q', '$state', 'DataUtils', 'CourseSub','Division','District', 'Upazila', 'Principal', 'InstEmployeeCode', 'entity', 'InstEmployee', 'Institute',  'Religion',  'CourseTech', 'InstEmpAddress', 'InstEmpEduQuali','InstEmplDesignation','InstEmplBankInfo','AllInstEmplDesignationsList','CmsSubAssignByTrade','InstDesignationByInstLevel','IisTradesOfCurrentInst','InstLevel','IisTradesByInstitute','InstFinancialInfoByInstitute','BankBranchsOfADistrictByBank',
        function($scope,BankSetup,$rootScope, $stateParams, $q, $state, DataUtils, CourseSub, Division, District, Upazila, Principal, InstEmployeeCode, entity, InstEmployee, Institute,  Religion,  CourseTech,  InstEmpAddress, InstEmpEduQuali,InstEmplDesignation,InstEmplBankInfo,AllInstEmplDesignationsList,CmsSubAssignByTrade,InstDesignationByInstLevel, IisTradesOfCurrentInst, InstLevel, IisTradesByInstitute, InstFinancialInfoByInstitute, BankBranchsOfADistrictByBank) {

            $scope.message = '';
            $scope.content = '';
            $scope.bankSetups = BankSetup.query();
            $scope.instEmpAddress={};
            $scope.upazila={};
            $scope.bankBranches=[];
            $scope.instEmpAddress.upazila ==null ;
            $scope.instEmpAddress.roadBlockSector == null ;
            $scope.instEmpAddress.villageOrHouse == null;
            $scope.hideRegistration=false;
            $scope.registrationField=false;
            console.log($stateParams);

            $scope.cmsSubAssigns= [];

            //instEmplBankInfo.bankSetup




            /*Principal.identity().then(function (account) {
               $scope.account = account;
                console.log('loded for test');
               InstEmployeeCode.get({'code':$scope.account.login},function(result){
                   $scope.instEmployee = result.instEmployee;
                   $scope.instEmpAddress = result.instEmpAddress;
                   $scope.instEmplBankInfo = result.instEmplBankInfo;
                   console.log(result);
                   if($scope.instEmpAddress != null){
                       $scope.parmDivsion = $scope.instEmpAddress.upazila.district.division;
                       $scope.instEmpAddress.district = $scope.instEmpAddress.upazila.district;
                       $scope.instEmpAddress.prevDistrict = $scope.instEmpAddress.prevUpazila.district;
                       $scope.instEmpAddress.prevDivision = $scope.instEmpAddress.prevUpazila.district.division;
                   }
                   $scope.instEmployee.courseTech = $scope.instEmployee.courseSub.courseTech;

               });

            });*/
            if(!$stateParams.allInfo){
                $state.go('employeeInfo.personalInfo',{},{reload:true});
            }else{
                var result =  $stateParams.allInfo;
                $scope.instEmployee = result.instEmployee;
                $scope.institute = result.instEmployee.institute;
                $scope.instEmployee.nationality = 'Bangladeshi';
                if($scope.instEmployee.instCategory=='Teacher'){
                    $scope.hideRegistration=true;
                    $scope.registrationField=true;
                    if($scope.instEmployee.registeredCertificateSubject!=undefined && $scope.instEmployee.registrationExamYear !=undefined && $scope.instEmployee.registeredCertificateNo != undefined){
                        $scope.registrationField=false;
                    }
                }
                $scope.instEmpAddress = result.instEmpAddress;
                $scope.instEmplBankInfo = result.instEmplBankInfo;
                if($scope.instEmplBankInfo== null){
                    $scope.instEmplBankInfo = {};
                    InstFinancialInfoByInstitute.query({instituteId: result.instEmployee.institute.id}, function(infos){
                        console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        console.log(infos[0]);
                        $scope.instEmplBankInfo.bankSetup = infos[0].bankSetup;
                        $scope.bankName = infos[0].bankSetup.name;
                        $scope.bankId = infos[0].bankSetup.id;
                        console.log("bank id");

                        console.log($scope.bankId);
                        console.log("District id");
                        console.log($scope.institute.upazila.district.id);



                        BankBranchsOfADistrictByBank.query({districtId: $scope.institute.upazila.district.id, bankSetupId: $scope.bankId}, function(branches){
                            $scope.bankBranchesNew = branches;
                            console.log("bank Branches in.......");
                            console.log($scope.bankBranchesNew);
                        });
                    });

                }else{
                    if($scope.instEmplBankInfo.bankSetup == null){
                        InstFinancialInfoByInstitute.query({instituteId: result.instEmployee.institute.id}, function(infos){
                            $scope.instEmplBankInfo.bankSetup = infos[0].bankSetup;
                            $scope.bankName = infos[0].bankSetup.name;
                        });
                    }
                    $scope.bankName =$scope.instEmplBankInfo.bankSetup.name;
                    BankBranchsOfADistrictByBank.query({districtId: $scope.institute.upazila.district.id, bankSetupId: $scope.instEmplBankInfo.bankSetup.id}, function(branches){
                        $scope.bankBranches = branches;
                        console.log("bank Branches else");
                        console.log($scope.bankBranches);
                    });
                }

                if($scope.instEmployee.image !=null){
                    var blob = b64toBlob($scope.instEmployee.image, $scope.instEmployee.imageContentType);
                    $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                }
                console.log(result);
                if($scope.instEmpAddress != null){
                    $scope.parmDivsion = $scope.instEmpAddress.upazila.district.division;
                    $scope.instEmpAddress.district = $scope.instEmpAddress.upazila.district;
                    $scope.instEmpAddress.prevDistrict = $scope.instEmpAddress.prevUpazila.district;
                    $scope.instEmpAddress.prevDivision = $scope.instEmpAddress.prevUpazila.district.division;
                }
                if($scope.instEmployee.courseSub!=null){
                $scope.instEmployee.courseTech = $scope.instEmployee.courseSub.courseTech;
                }

                $scope.logInInstitute = result.instEmployee.institute;
                $scope.levelId =  result.instEmployee.institute.instLevel.id;
                $scope.levelName = '';
                IisTradesByInstitute.query({id: result.instEmployee.institute.id}, function(result){
                    $scope.cmsTrades = result;
                });
                $scope.instLevels = [];
                InstLevel.query(function(allInst){
                    angular.forEach(allInst, function(value, key) {
                        if(parseInt($scope.levelId) == parseInt(value.id))
                        {
                            $scope.levelName = value.name;
                        }
                    });

                    var splitNames = $scope.levelName.split('&');

                    angular.forEach(allInst, function(value, key) {
                        angular.forEach(splitNames, function(spname, spkey) {
                            if(spname.trim() == value.name.trim())
                            {
                                $scope.instLevels.push(value);
                            }
                        });

                    });

                });
            }




        $scope.courseSubsGlobal = CourseSub.query();
        //$scope.instEmplDesignation = InstEmplDesignation.query();
        $scope.instEmplDesignation = AllInstEmplDesignationsList.get({});
        $scope.coursetechs = CourseTech.query();

        $scope.coursesubs = $scope.courseSubsGlobal;
        $scope.CourseTechChange = function(data){
              $scope.Selected = "Select a subject";
              $scope.coursesubs = [];
              console.log(data);
              angular.forEach($scope.courseSubsGlobal,function(subjectData){
                   if(data.id == subjectData.courseTech.id){
                         $scope.coursesubs.push(subjectData);
                   }
              });
        }
        $scope.religions = Religion.query();

        /*$scope.instEmployee = entity;
        $scope.institutes = Institute.query();
        $scope.instEmplDesignation = InstEmplDesignation.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.instEmplDesignation.$promise]).then(function() {
            if (!$scope.instEmployee.instEmplDesignation.id) {
                return $q.reject();
            }
            return InstEmplDesignation.get({id : $scope.instEmployee.instEmplDesignation.id}).$promise;
        }).then(function(instEmplDesignation) {
            $scope.InstEmplDesignation.push(instEmplDesignation);
        });
        $scope.religions = Religion.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.religions.$promise]).then(function() {
            if (!$scope.instEmployee.religion.id) {
                return $q.reject();
            }
            return Religion.get({id : $scope.instEmployee.religion.id}).$promise;
        }).then(function(religion) {
            $scope.religions.push(religion);
        });
        *//*$scope.quotas = Quota.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.quotas.$promise]).then(function() {
            if (!$scope.instEmployee.quota.id) {
                return $q.reject();
            }
            return Quota.get({id : $scope.instEmployee.quota.id}).$promise;
        }).then(function(quota) {
            $scope.quotas.push(quota);
        });*//*
        $scope.coursetechs = CourseTech.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.coursetechs.$promise]).then(function() {
            if (!$scope.instEmployee.courseTech.id) {
                return $q.reject();
            }
            return CourseTech.get({id : $scope.instEmployee.courseTech.id}).$promise;
        }).then(function(courseTech) {
            $scope.coursetechs.push(courseTech);
        });
        $scope.gradesetups = GradeSetup.query({filter: 'instemployee-is-null'});
        $q.all([$scope.instEmployee.$promise, $scope.gradesetups.$promise]).then(function() {
            if (!$scope.instEmployee.gradeSetup.id) {
                return $q.reject();
            }
            return GradeSetup.get({id : $scope.instEmployee.gradeSetup.id}).$promise;
        }).then(function(gradeSetup) {
            $scope.gradesetups.push(gradeSetup);
        });
        $scope.instempaddresss = InstEmpAddress.query();
        $scope.instempeduqualis = InstEmpEduQuali.query();
        $scope.instemplhists = InstEmplHist.query();
        */
        Division.query({page: $scope.page, size: 10},function(result){
            $scope.divisions = result;
            $scope.prevdivisions = result;
            console.log($scope.prevdivisions);
        });
        District.query({page: $scope.page, size: 80},function(result){
            $scope.GlobalDistricts = result;
            $scope.districts = result;
            $scope.prevdistricts = result;

        });
        Upazila.query({page: $scope.page, size: 1000},function(result){
            $scope.GlobalUpazilas = result;
            $scope.upazilas = result;
            $scope.prevupazilas = result;
        });

        /*$scope.PreUpZillaChange = function(data) {
                $scope.prevdistricts = [];
                $scope.prevdivisions = [];
                $scope.prevdistricts.push(data.district);
                $scope.prevdivisions.push(data.district.division);

        }*/

        $scope.PreDistrictChange = function(data){
            $scope.prevupazilas = [];
            angular.forEach($scope.GlobalUpazilas, function (gUpzilla) {
                if ( data.id == gUpzilla.district.id) {
                    $scope.prevupazilas.push(gUpzilla);
                }
            })
            $scope.upazilaSelect = "Select a upzilla";
        }
        $scope.registrationFieldErrorFunction = function(data){
            console.log(" chack true--------");
            if($scope.instEmployee.registeredCertificateSubject!=undefined && $scope.instEmployee.registrationExamYear !=undefined && $scope.instEmployee.registeredCertificateNo != undefined){
                $scope.registrationField=false;
            }

            console.log($scope.instEmployee.registeredCertificateSubject);
            console.log($scope.instEmployee.registrationExamYear);
            console.log($scope.instEmployee.registeredCertificateNo);
        };
        $scope.PreDivisionChange = function(data){
            $scope.prevdistricts = [];
            angular.forEach($scope.GlobalDistricts,function(gDistrict){
               if(data.id == gDistrict.division.id){
                   $scope.prevdistricts.push(gDistrict);
               }
            });
            $scope.districtSelect = "Select a district";
            console.log(data);

            /* Reset pram check */

            $scope.instEmpAddress.sameAddress = false;
        };

        $scope.divisionChange = function(data){
            $scope.districts = [];
            angular.forEach($scope.GlobalDistricts,function(gDistrict){
                if(data.id == gDistrict.division.id){
                    $scope.districts.push(gDistrict);
                }
            });
            $scope.PreDistrictSelect = "Select a district";

            /* clear fields */
       $scope.instEmpAddress.prevVillageOrHouse= null;
       $scope.instEmpAddress.prevRoadBlockSector=null;
       $scope.instEmpAddress.prevUpazila=null;
       $scope.instEmpAddress.prevDistrict=null;
       $scope.instEmpAddress.prevDivision=null;
       $scope.instEmpAddress.sameAddress = false;
        }

        $scope.districtChange = function(data){
            $scope.upazilas = [];
            angular.forEach($scope.GlobalUpazilas, function(Districtdata){
                  if(data.id == Districtdata.district.id){
                      $scope.upazilas.push(Districtdata);
                  }
            });
            $scope.PreUpzilaSelect = "Select a Upazila";

             /* clear fields */
       $scope.instEmpAddress.prevVillageOrHouse= null;
       $scope.instEmpAddress.prevRoadBlockSector=null;
       $scope.instEmpAddress.prevUpazila=null;
       $scope.instEmpAddress.prevDistrict=null;
       $scope.instEmpAddress.prevDivision=null;
       $scope.instEmpAddress.sameAddress = false;
        }

        $scope.upazilaChange = function(){

                     /* clear fields */
       $scope.instEmpAddress.prevVillageOrHouse= null;
       $scope.instEmpAddress.prevRoadBlockSector=null;
       $scope.instEmpAddress.prevUpazila=null;
       $scope.instEmpAddress.prevDistrict=null;
       $scope.instEmpAddress.prevDivision=null;
       $scope.instEmpAddress.sameAddress = false;
                }

        $scope.load = function(id) {
            InstEmployee.get({id : id}, function(result) {
                $scope.instEmployee = result;
            });
        };

        $scope.bankInfoSave = function(){
             $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
              if($scope.instEmplBankInfo== null){
                  InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                  $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                       console.log(result);
                  },function(result){
                      console.log(result);
                  })
              }else{
                  InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                  $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                      console.log(result);
                  },function(result){
                      console.log(result);
                  })
              }
            /*$q.all(requests).then(function () {
                $state.go('employeeInfo.personalInfo',{},{reload:true});
            });*/
        }

        var onSaveSuccess = function (result) {

            $scope.$emit('stepApp:instEmployeeUpdate', result);
            $scope.isSaving = false;
            $scope.instEmpAddress.instEmployee = $scope.instEmployee;
            if($scope.instEmpAddress == null){
                InstEmpAddress.save($scope.instEmpAddress,function(result){
                    /*$scope.bankInfoSave();*/
                    $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
                    if($scope.instEmplBankInfo== null){
                        InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                        $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                            console.log(result);
                        },function(result){
                            console.log(result);
                        })
                    }else{
                        InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                        $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                            console.log(result);
                        },function(result){
                            console.log(result);
                        })
                    }
                    console.log(result);
                },function(result){
                    console.log(result);
                });
            }
            else{
                InstEmpAddress.update($scope.instEmpAddress,function(result){
                    /*$scope.bankInfoSave();*/
                    $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
                    if($scope.instEmplBankInfo== null){
                        InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                        $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                            console.log(result);
                        },function(result){
                            console.log(result);
                        })
                    }else{
                        InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                        $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                            console.log(result);
                        },function(result){
                            console.log(result);
                        })
                    }
                    console.log(result);
                },function(result){
                    console.log(result);
                });
            }
           /* $state.go('employeeInfo.personalInfo');*/
           /* $q.all(requests).then(function () {
                $state.go('employeeInfo.personalInfo',{},{reload:true});
            });*/
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if($scope.instEmployee.status=-1){
                $scope.instEmployee.status=0;
            }
            if($scope.instEmployee != null){
                console.log();
                //InstEmployee.update($scope.instEmployee, onSaveSuccess, onSaveError);
                InstEmployee.update($scope.instEmployee, function(result){
                    $scope.instEmpAddress.instEmployee = result;
                    if($scope.instEmpAddress == null){
                        InstEmpAddress.save($scope.instEmpAddress,function(result){
                            /*$scope.bankInfoSave();*/
                            $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
                            if($scope.instEmplBankInfo== null){
                                InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }else{
                                InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }
                            console.log(result);
                        },function(result){
                            console.log(result);
                        });
                    }
                    else{
                        InstEmpAddress.update($scope.instEmpAddress,function(result){
                           /* $scope.bankInfoSave();*/
                            $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
                            if($scope.instEmplBankInfo== null){
                                InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }else{
                                InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }
                            console.log(result);
                        },function(result){
                            console.log(result);
                        });
                    }
                });
            }
            else{
               // InstEmployee.save($scope.instEmployee, onSaveSuccess, onSaveError);
                InstEmployee.save($scope.instEmployee,function(result){
                    $scope.instEmpAddress.instEmployee = result;
                    if($scope.instEmpAddress == null){
                        InstEmpAddress.save($scope.instEmpAddress,function(result){
                           /* $scope.bankInfoSave();*/
                            $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
                            if($scope.instEmplBankInfo== null){
                                InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }else{
                                InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});

                                })
                            }
                            console.log(result);
                        },function(result){
                            console.log(result);
                        });
                    }
                    else{
                        InstEmpAddress.update($scope.instEmpAddress,function(result){
                           /* $scope.bankInfoSave();*/
                            $scope.instEmplBankInfo.instEmployee = $scope.instEmployee;
                            if($scope.instEmplBankInfo== null){
                                InstEmplBankInfo.save($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }else{
                                InstEmplBankInfo.update($scope.instEmplBankInfo,function(result){
                                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                },function(result){
                                    console.log(result);
                                    $state.go('employeeInfo.personalInfo',{},{reload:true});
                                })
                            }
                            console.log(result);
                        },function(result){
                            console.log(result);
                        });
                    }
                });
            }

            /*$q.all(requests).then(function () {
                $state.go('employeeInfo.personalInfo',{},{reload:true});
            });*/
            /*$state.go('employeeInfo.personalInfo',{},{reload:true});*/
        };

        $scope.clear = function() {
              $state.go('employeeInfo.personalInfo');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
/*
            $scope.$apply(function() {
                srmsRegInfo.studentImage = base64Data;
                srmsRegInfo.studentImageContentType = $file.type;
                var blob = b64toBlob(srmsRegInfo.studentImage, srmsRegInfo.studentImageContentType);
                $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                console.log($scope.url);
            });*/

        $scope.setImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.image = base64Data;
                        instEmployee.imageContentType = $file.type;
                        instEmployee.imageName = $file.name;
                        var blob = b64toBlob(instEmployee.image, instEmployee.imageContentType);
                        $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                    });
                };
            }
        };
        $scope.changeSameAddress = function (data) {
            console.log('checked -----clicked'+data);
            if (data) {

                $scope.instEmpAddress.prevVillageOrHouse= $scope.instEmpAddress.villageOrHouse;
                $scope.instEmpAddress.prevRoadBlockSector=$scope.instEmpAddress.roadBlockSector;
                $scope.instEmpAddress.prevUpazila=$scope.instEmpAddress.upazila;
                $scope.instEmpAddress.prevDistrict=$scope.instEmpAddress.district;
                $scope.instEmpAddress.prevDivision=$scope.instEmpAddress.district.division;
            }else{
                $scope.instEmpAddress.prevVillageOrHouse= null;
                $scope.instEmpAddress.prevRoadBlockSector=null;
                $scope.instEmpAddress.prevUpazila=null;
                $scope.instEmpAddress.prevDistrict=null;
                $scope.instEmpAddress.prevDivision=null;
            }
        };

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

        $scope.setNidImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.nidImage = base64Data;
                        instEmployee.nidImageContentType = $file.type;
                        instEmployee.nidImageName = $file.name;
                    });
                };
            }
        };

        $scope.setBirthCertImage = function ($file, instEmployee) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmployee.birthCertImage = base64Data;
                        instEmployee.birthCertImageContentType = $file.type;
                        instEmployee.birthCertNoName = $file.name;
                    });
                };
            }
        };

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


            $scope.courseSubsGlobal = CourseSub.query();
            /*$scope.instEmplDesignation = InstEmplDesignation.query();*/
            /*$scope.instEmplDesignation = ActiveInstEmplDesignation.query();*/
            $scope.coursetechs = CourseTech.query();
            $scope.coursesubs = [];
            $scope.selectedTech = "Select a Technology";
            $scope.SelectedSubs = "Select a Subject";

            $scope.CourseTechChange = function(data){
                $scope.coursesubs = [];
                angular.forEach($scope.courseSubsGlobal,function(subjectData){
                    if(data.id == subjectData.courseTech.id){
                        $scope.coursesubs.push(subjectData);
                    }
                });
            };

            $scope.CourseTradeChange = function(data){
                console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>');
                console.log(data);
                console.log(data.id);
                CmsSubAssignByTrade.query({id: data.id}, function(result){
                    $scope.cmsSubAssigns = result;
                });
            };

            $scope.setDesignation = function(instLevel) {
                console.log(instLevel);
                $scope.instLevel = instLevel;
                if($scope.instEmployee.category == 'Teacher') {
                    $scope.instEmplDesignation = InstDesignationByInstLevel.query({id: instLevel.id});
                }
            };
}]);
