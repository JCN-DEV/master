'use strict';

angular.module('stepApp').controller('MpoVacancyRoleDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'MpoVacancyRole', 'CmsCurriculum', 'User', 'CmsTrade', 'InstEmplDesignation', 'CmsSubject', '$timeout', '$rootScope', 'MpoVacancyRoleTrade', 'MpoVacancyRoleDesgnations', '$state', 'MpoVacancyRoleDesgnationsByRole', 'MpoCmsTradeByCurriculumId', 'ActiveInstEmplDesignation', 'MpoVacancyRoleTradeByVacancyRole','FindActivecmsCurriculums','HrDesignationSetup',
        function ($scope, $stateParams, $q, entity, MpoVacancyRole, CmsCurriculum, User, CmsTrade, InstEmplDesignation, CmsSubject, $timeout, $rootScope, MpoVacancyRoleTrade, MpoVacancyRoleDesgnations, $state, MpoVacancyRoleDesgnationsByRole, MpoCmsTradeByCurriculumId, ActiveInstEmplDesignation, MpoVacancyRoleTradeByVacancyRole,FindActivecmsCurriculums,HrDesignationSetup) {

            $scope.mpoVacancyRole = {};

            $scope.mpoVacancyRoleTrade = {};
            $scope.mpoTrades = [];
            $scope.checkedCurriculums = [];
            $scope.checkedTrades = [];
            $scope.mpoVacancyRoleDesgnations = [];
            $scope.mpoVacancyRoleDesgnation = {};
            $scope.cmstrades = [];
            $scope.cmstrades2 = [];

            //this to edit mpo role
            MpoVacancyRole.get({id: $stateParams.id}, function (result) {
                $scope.mpoVacancyRole = result;

                //this to get all mpo trades of curriculum
                MpoCmsTradeByCurriculumId.query({id: result.cmsCurriculum.id}, function (trds) {
                    $scope.cmstrades = trds;
                    MpoVacancyRoleTradeByVacancyRole.query({id: $stateParams.id}, function (mpoTrds) {
                        $scope.mpoTrades = mpoTrds;
                        console.log('before inside for 1');

                        //to make checkbox checked which already added
                        for (var t = 0; t < $scope.cmstrades.length; t++) {
                            console.log('comes inside for 1');
                            for (var i = 0; i < $scope.mpoTrades.length; i++) {
                                console.log('comes inside for 2');
                                if ($scope.mpoTrades[i].cmsTrade.id == $scope.cmstrades[t].id) {
                                    console.log('trade found');
                                    $scope.cmstrades[t].isSelected = true;
                                }
                            }

                        };
                    });
                });


                console.log($scope.cmstrades);
            });

            HrDesignationSetup.query({},function(result){
                $scope.designationSetups=result;
            });

            $scope.cmscurriculums = FindActivecmsCurriculums.query({page: 0, size: 200});
            //$scope.instempldesignations = ActiveInstEmplDesignation.query({page: 0, size: 200});
            $scope.cmssubjects = CmsSubject.query({page: 0, size: 200});
            MpoVacancyRoleDesgnationsByRole.query({id: $stateParams.id}, function (result) {
                $scope.mpoVacancyRoleDesgnations = result;
            });
            /*MpoVacancyRoleTradeByVacancyRole.query({id :$stateParams.id}, function(result){
             $scope.mpoVacancyRoleDesgnations = result;
             });*/


            var onSaveSuccess = function (result) {

                //adding cmsTrades to mpoVacancyRoleTrade which is checked
                angular.forEach($scope.cmstrades, function (data) {
                    if (data.isSelected) {
                        $scope.mpoVacancyRoleTrade2 = {};
                        $scope.mpoVacancyRoleTrade2.mpoVacancyRole = result;
                        $scope.mpoVacancyRoleTrade2.cmsTrade = data;
                        console.log('cmstrade');
                        console.log(data);
                        MpoVacancyRoleTrade.save($scope.mpoVacancyRoleTrade2);
                    }
                });

                console.log("leeeeeeeeeenthg :" + $scope.mpoVacancyRoleDesgnations.length);
                console.log($scope.mpoVacancyRoleDesgnations);

                //adding designations of vacancy to mpoVacancyRoleDesignation
                angular.forEach($scope.mpoVacancyRoleDesgnations, function (data) {
                    data.mpoVacancyRole = result;
                    console.log('cmstrade');
                    console.log(data);
                    MpoVacancyRoleDesgnations.save(data);

                });
                $state.go('mpoVacancyRole', null, {reload: true});
                /* for (var i = 0; i < $scope.cmstrades.length; i++){
                 var cmstrade = $scope.cmstrades[i];

                 }*/
                $scope.$emit('stepApp:mpoVacancyRoleUpdate', result);
                //$modalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };
            //MpoCmsTradeByCurriculumId
            $scope.save = function () {
                //$scope.isSaving = true;


                /*for (var i = 0; i < $scope.cmscurriculums.length; i++){
                 var curriculum = $scope.cmscurriculums[i];
                 if(curriculum.isSelected){
                 console.log('curriculum');
                 console.log(curriculum);
                 }
                 }*/
                if ($scope.mpoVacancyRole.id != null) {
                    MpoVacancyRole.update($scope.mpoVacancyRole, onSaveSuccess, onSaveError);
                } else {
                    MpoVacancyRole.save($scope.mpoVacancyRole, onSaveSuccess, onSaveError);
                }
            };

            /*$scope.areAllCurriculumSelected = false;

             $scope.updateCurriculumSelection = function (curriculumArray, selectionValue) {
             for (var i = 0; i < curriculumArray.length; i++)
             {
             curriculumArray[i].isSelected = selectionValue;
             }
             console.log(curriculumArray)
             };
             */
            $scope.areAllTradesSelected = false;

            $scope.updateTradeSelection = function (tradeArray, selectionValue) {
                for (var i = 0; i < tradeArray.length; i++) {
                    tradeArray[i].isSelected = selectionValue;
                }
                console.log(tradeArray)
            };

            $scope.checkCurriculum = function (value, checked) {
                var idx = $scope.checkedCurriculums.indexOf(value);
                if (idx >= 0 && !checked) {
                    console.log('canceled');
                    $scope.checkedCurriculums.splice(idx, 1);
                }
                if (idx < 0 && checked) {
                    console.log('checked');
                    $scope.checkedCurriculums.push(value);
                }
            };

            $scope.AddMoreVacancyRoleDesgnations = function () {

                $scope.mpoVacancyRoleDesgnations.push(
                    {
                        status: null,
                        totalPost: null,
                        id: null
                    }
                );
                // Start Add this code for showing required * in add more fields
                $timeout(function () {
                    $rootScope.refreshRequiredFields();
                }, 100);
                // End Add this code for showing required * in add more fields

            };
            $scope.mpoVacancyRoleDesgnations.push(
                {
                    status: null,
                    totalPost: null,
                    id: null
                }
            );

            $scope.clear = function () {
                window.history.back();
                //$modalInstance.dismiss('cancel');
            };
            $scope.setTrades = function (curriculum) {
                MpoCmsTradeByCurriculumId.query({id: curriculum.id}, function (result) {
                    $scope.cmstrades = result;
                });
            };


        }]);
