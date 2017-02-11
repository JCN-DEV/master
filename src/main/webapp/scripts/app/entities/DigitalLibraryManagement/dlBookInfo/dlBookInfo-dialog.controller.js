'use strict';

angular.module('stepApp').controller('DlBookInfoDialogController',

    ['$scope', '$state', '$q', '$stateParams', 'entity', '$timeout', '$rootScope', 'DlBookInfo', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet', 'validationforisbn', 'validationForBookId', 'DlSourceSetUp', 'DlBookEdition', 'getAllEditionById', 'FindActivcategory', 'FindActiveTypes', 'FindActiveSubcategory', 'GetAllBookDepositByUserRequisition','DlBookRequisition','FindActiveSourceSetup',
        function ($scope, $state, $q, $stateParams, entity, $timeout, $rootScope, DlBookInfo, DlContTypeSet, DlContCatSet, DlContSCatSet, validationforisbn, validationForBookId, DlSourceSetUp, DlBookEdition, getAllEditionById, FindActivcategory, FindActiveTypes, FindActiveSubcategory,GetAllBookDepositByUserRequisition,DlBookRequisition,FindActiveSourceSetup) {

            $scope.dlBookInfo = {};
            // $scope.dlBookRequisitions = [];
            // $scope.dlBookRequisitionsssssssssss = [];
            $scope.dlBookInfo.dlBookRequisitionIdOne=null;

            $scope.dlContTypeSets = FindActiveTypes.query();
            $scope.dlBookEditions = DlBookEdition.query();
            $scope.addMoreEdition = [];
            $scope.dlcontcatsets = DlContCatSet.query();
            $scope.dlcontscatsets = DlContSCatSet.query();
            $scope.dlSourceSetUps = FindActiveSourceSetup.query();
            $scope.dlBookInfo.pStatus = true;
            $scope.newBooksjh = {};
            $scope.gettypeCode = null;
            $scope.getCategoryCode = null;
            $scope.subCatCode = null;


            GetAllBookDepositByUserRequisition.query({page: $scope.page, size: 20}, function(result) {
                $scope.dlBookRequisitions = result;
                console.log($scope.dlBookRequisitionsssssssssss);
            });

//edit data of this entity
            DlBookInfo.get({id: $stateParams.id}, function (result) {
                $scope.dlBookInfo = result;
                console.log($scope.dlBookInfo);
            });


            validationforisbn.get({isbnNo: $scope.dlBookInfo.isbnNo}, function (dlBookInfo) {

                $scope.message = "The  File Type is already exist.";

            });

            $q.all([entity.$promise]).then(function () {
                if (entity.dlContSCatSet) {
                    $scope.dlContSCatSet = entity.dlContSCatSet.name;
                    if (entity.dlContSCatSet.dlContCatSet) {
                        $scope.dlContCatSet = entity.dlContSCatSet.dlContCatSet.name;
                        if (entity.dlContSCatSet.dlContCatSet.dlContTypeSet) {
                            $scope.dlContTypeSet = entity.dlContSCatSet.dlContCatSet.dlContTypeSet.name;
                        }
                        else {
                            $scope.dlContTypeSet = "Select Content Type"
                        }
                    }
                    else {
                        $scope.dlContCatSet = "Select Category";
                        $scope.dlContTypeSet = "Select Content Type"
                    }
                }
                else {
                    $scope.dlContTypeSet = "Select Content Type";
                    $scope.dlContCatSet = "Select Category";
                    $scope.dlContSCatSet = "Select Sub Category";
                }
            });

            $scope.dlContTypeSets = DlContTypeSet.query();
            var allDlContCatSet = FindActivcategory.query({page: $scope.page, size: 65}, function (result, headers) {
                return result;
            });
            var allDlContSCatSet = FindActiveSubcategory.query({
                page: $scope.page,
                size: 500
            }, function (result, headers) {
                return result;
            });

            $scope.updatedDlContSCatSet = function (select) {
                $scope.categoryCode = select.code;
                $scope.getCategoryCode = $scope.categoryCode;
                /* console.log("selected district .............");
                 console.log(select);*/
                $scope.dlContSCatSets = [];
                angular.forEach(allDlContSCatSet, function (dlContSCatSet) {
                    if (select.id == dlContSCatSet.dlContCatSet.id) {
                        $scope.dlContSCatSets.push(dlContSCatSet);
                    }
                });
                $scope.CategoryCode();
            };

            $scope.dlContCatSets = DlContCatSet.query();
            $scope.dlContSCatSets = DlContSCatSet.query();
            $scope.updatedDlContCatSet = function (select) {
                $scope.typeCode = select.code;
                $scope.gettypeCode = $scope.typeCode;
                $scope.dlContCatSets = [];
                angular.forEach(allDlContCatSet, function (dlContCatSet) {

                    if ((dlContCatSet.dlContTypeSet && select) && (select.id != dlContCatSet.dlContTypeSet.id)) {
                        console.log("There is error");
                    } else {
                        console.log("There is the fire place");
                        $scope.dlContCatSets.push(dlContCatSet);
                    }
                });
                $scope.typecode();
            };
            $scope.getSubCategoryCode = function (ans) {
                $scope.subcategoryCode = ans.code;
                $scope.subCatCode = $scope.subcategoryCode;
                $scope.subCatCodes();
            };

           /* $scope.approve = function (ans) {
                $scope.dlBookInfo.dlBookRequisitionId = ans;
                console.log($scope.dlBookInfo.dlBookRequisitionId);

            };*/
            $scope.typecode = function () {
                console.log($scope.gettypeCode);
                $scope.CategoryCode = function () {
                    console.log($scope.getCategoryCode);
                    $scope.subCatCodes = function () {
                        console.log($scope.subCatCode);
                        $scope.getallCode = $scope.gettypeCode + $scope.getCategoryCode + $scope.subCatCode;
                        $scope.dlBookInfo.bookId = $scope.getallCode;
                        console.log($scope.getallCode);
                    };
                };
            };


            var onSaveSuccess = function (result) {
if(result.dlBookRequisition!=null){
    $scope.dlBookRequisition = result.dlBookRequisition;
    $scope.dlBookRequisition.status = false;
    DlBookRequisition.update($scope.dlBookRequisition);

}


                angular.forEach($scope.addMoreEdition, function (data) {
                    console.log(data);
                    if (data.id != null) {
                        //data.totalBookCopies = data.totalCopies;
                        console.log("comes to update projection more");
                        if (data.newBook) {
                            $scope.totalBcopies = parseInt(data.totalBookCopies);
                            $scope.newBData = parseInt(data.newBook);
                            data.totalBookCopies = $scope.totalBcopies + $scope.newBData;
                            $scope.totalCopies = parseInt(data.totalCopies);
                            $scope.newCData = parseInt(data.newBook);
                            data.totalCopies = $scope.totalCopies + $scope.newCData;

                        }
                        ;

                        DlBookEdition.update(data);
//                             $rootScope.setWarningMessage('stepApp.fmsBudgetAllocationConfig.updated');
//                             $state.go('dlBookInfos');
                    } else {
                        console.log("comes to save projection more");
                        data.totalBookCopies = data.totalCopies;
                        data.dlBookInfo = result;
                        DlBookEdition.save(data);
//                             $rootScope.setSuccessMessage('stepApp.fmsBudgetAllocationConfig.created');
//                             $state.go('fmsBudgetAllocationConfig');
                    }
                });

                $scope.$emit('stepApp:dlBookInfoUpdate', result);
                $scope.isSaving = false;
                $state.go('libraryInfo.libraryBookInfos', {}, {reload: true});
            };

            //$scope.newBooks=function(kk){
            //    $scope.newBooksjh=kk;
            //    console.log($scope.newBooksjh);
            //    $scope.parseIntval=parseInt($scope.newBooksjh);
            //    console.log($scope.parseIntval);
            //
            //};
            var onUpdateFinished = function (result) {
                angular.forEach($scope.dlBookEditionsss, function (data) {
                    //data.totalBookCopies=data.totalCopies;
                    if (data.id != null) {
                        console.log("comes to update projection more pre");
                        console.log(data.id);
                        console.log("comes to update projection more");

                        DlBookEdition.update(data);
                    } else {
                        console.log("comes to save projection more");
                        data.dlBookInfo = result;
                        DlBookEdition.save(data);
                    }
                });
                console.log("enter success");
                $scope.$emit('stepApp:dlBookInfoUpdate', result);
                $state.go('libraryInfo.libraryBookInfos');
            };


            var onSaveError = function (result) {
                $scope.isSaving = false;
            };


            $scope.setBookImg = function ($file, dlBookInfo) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            dlBookInfo.bookImg = base64Data;
                            dlBookInfo.bookImgContentType = $file.type;
                            dlBookInfo.bookImgName = $file.name;

                        });
                    };
                }

            };

            $scope.approve = function (ans) {
                $scope.dlBookInfo.dlBookRequisitionIdOne = ans;

            };

            $scope.save = function () {
                //$scope.isSaving = true;
                //if ($scope.dlBookInfo.id != null) {
                //    DlBookInfo.update($scope.dlBookInfo, onSaveSuccess, onSaveError);
                //} else {
                //    DlBookInfo.save($scope.dlBookInfo, onSaveSuccess, onSaveError);
                //}
                if ($scope.dlBookInfo.id != null) {
                    console.log("comes to update projection");
                    console.log($scope.dlBookInfo);
                    DlBookInfo.update($scope.dlBookInfo, onSaveSuccess);
                    $rootScope.setWarningMessage('stepApp.dlBookInfo.updated');
                } else {
                    console.log("comes to save projection");
                    if($scope.dlBookInfo.dlBookRequisitionIdOne!=null){
                        console.log("comes to save requisition");

                        $scope.dlBookInfo.dlBookRequisition=$scope.dlBookInfo.dlBookRequisitionIdOne;

                    }
                    DlBookInfo.save($scope.dlBookInfo, onSaveSuccess);
                    $rootScope.setSuccessMessage('stepApp.dlBookInfo.created');
                }

            };


            $scope.addMore = function () {
                $scope.addMoreEdition.push(
                    {
                        edition: null,
                        totalCopies: null,
                        totalBookCopies: null,
                        compensation: null,
                        id: null
                    }
                );
                $timeout(function () {
                    $rootScope.refreshRequiredFields();
                }, 100);
            };

            if (!$stateParams.id) {
                $scope.addMoreEdition.push(
                    {
                        edition: null,
                        totalCopies: null,
                        totalBookCopies: null,
                        compensation: null,
                        id: null
                    }
                );
                $timeout(function () {
                    $rootScope.refreshRequiredFields();
                }, 100);

            }


            getAllEditionById.query({id: $stateParams.id}, function (result) {
                console.log($stateParams.id);
                //$scope.dlBookEditionsss = result;
                $scope.addMoreEdition = result;
                console.log($scope.dlBookEditionsss);
            });


        }]);
