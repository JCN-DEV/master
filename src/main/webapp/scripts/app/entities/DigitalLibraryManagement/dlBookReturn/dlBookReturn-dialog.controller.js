'use strict';

angular.module('stepApp').controller('DlBookReturnDialogController',
    ['$scope', '$rootScope', '$state', '$stateParams', 'entity', '$q', 'DlBookReturn', 'DlBookInfo', 'DlBookIssue', 'InstEmployee', 'IssueInfoByid', 'DlIssueByStudentId', 'DlIssueByIssueId', 'DlFineInfoBySCatId', 'DlBookEdition',
        function ($scope, $rootScope, $state, $stateParams, entity, $q, DlBookReturn, DlBookInfo, DlBookIssue, InstEmployee, IssueInfoByid, DlIssueByStudentId, DlIssueByIssueId, DlFineInfoBySCatId, DlBookEdition) {

            $scope.dlBookReturn = entity;
            $scope.shuvo = {};
            $scope.dlbooinfos = DlBookInfo.query();
            $scope.dateError = '';
            $scope.dlBookReturn.actualReturnDate = new Date();

            $scope.dlBookIssue = {};
            $scope.dlbookissues = DlBookIssue.query();
            $scope.instemployees = InstEmployee.query();

            $scope.dlBookReturn.cfFineStatus = 'false';
            $scope.dlBookReturn.cfCompensationStatus = 'false';
            $scope.load = function (id) {
                DlBookReturn.get({id: id}, function (result) {
                    $scope.dlBookReturn = result;
                });
            };


//        $scope.showDetail= function(issueId){
//        console.log("id requestiong"+issueId);
//
//            $scope.issueId = issueId;
//            console.log($scope.issueId);
//             DlBookIssue.get({id: $scope.issueId}, function(result) {
//                            $scope.dlBookIssue = result;
//                            console.log("at dl book issue");
//                            console.log($scope.dlBookIssue);
//                            console.log(result);
//                        });
//
//
//        if($scope.issueId != null){
//                IssueInfoByid.get({id: $scope.issueId}, function(result){
//                    $scope.shuvo = result;
//                    console.log("shuvo print");
//                    console.log($scope.shuvo);
//
//                            $q.all([$scope.shuvo.$promise]).then(function() {
//
//                                 $scope.dlBookReturn.createdDate = new Date();
//                                 var d1 = Date.parse($scope.shuvo.RETURN_DATE);
//
//                                 var d3 = Date.parse($scope.shuvo.ISSUE_DATE);
//                                 var d2 = Date.parse($scope.dlBookReturn.createdDate);
//                                 var ONE_DAY = 1000 * 60 * 60 * 24
//
//
//
//                                      $scope.diffOfIssuSubmit = Math.abs(d2 - d3);
//                                      console.log($scope.diffOfIssuSubmit);
//
//                                      $scope.DiffAsDay=Math.round($scope.diffOfIssuSubmit/ONE_DAY);
//                                      console.log("Difference Between Issue And Return="+$scope.DiffAsDay);
//
//                                      $scope.allocatedDay=$scope.shuvo.TIME_LIMIT;
//                                      console.log("Allocation day="+$scope.allocatedDay);


            //                         if (d1 <= d2) {
            //                             $scope.error = '';
            //                             $scope.notIssueFound = '';
            //                             $scope.dateError = 'Your Date is expired!';
            //                             console.log($scope.dateError);
            //
            //                         }else {
            //                             $scope.dateError = '';
            //                             console.log("================");
            //                             console.log($scope.dateError);
            //                         }


//                        if($scope.DiffAsDay > $scope.allocatedDay ){
//                         $scope.error = '';
//                         $scope.notIssueFound = '';
//                         $scope.dateError = 'Your Date is expired!';
//                         console.log($scope.dateError);
//                                console.log("your are late");
//
//                                $scope.fineDay=Math.abs($scope.DiffAsDay-$scope.allocatedDay);
//                                console.log("Fine Date="+$scope.fineDay);
//
//                                $scope.countingTotalFine=Math.abs($scope.fineDay * $scope.shuvo.FINE);
//                                console.log("Total fine="+$scope.countingTotalFine);
//
//                                $scope.dlBookReturn.totalFine=$scope.countingTotalFine;
//
//                        }else{
//                                console.log("Every thing is okey");
//                        }


//                      $scope.fineChecking=function(obj){
//                                console.log(obj);
//                                    if($scope.dlBookReturn.totalFine != obj){
//                                      console.log("Fine is not equal")
//                                      console.log($scope.dlBookReturn.remissionStatus);
//                                    }else{
//                                        console.log($scope.dlBookReturn.remissionStatus);
//                                        console.log("Fine is equal");
//                                    }
//                      }

//                      $scope.compensation=function(obj){
//                        console.log(obj);
//                             if($scope.dlBookReturn.compensation != obj){
//                                  console.log("Fine is not equal")
//                                  console.log($scope.dlBookReturn.remissionCompensationStatus);
//                             }else{
//                                 console.log($scope.dlBookReturn.remissionCompensationStatus);
//                                 console.log("Fine is equal");
//                             }
//
//                      }

//                            $scope.dlBookReturn.compensation=$scope.shuvo.COMPENSATION;
//                            $scope.COMPENSATION=parseInt($scope.shuvo.COMPENSATION);
//                            console.log("compensation"+$scope.COMPENSATION);
//                            $scope.TotalFineAndCompensation=$scope.countingTotalFine+$scope.COMPENSATION;
//                            console.log("TotalFineAndCompensation="+$scope.TotalFineAndCompensation);
//
//                            $scope.dlBookReturn.totalFineCompensation=$scope.TotalFineAndCompensation;
//
//
//
//                 });
//
//            },function(response) {
//                  if(response.status === 404) {
//                    $scope.shuvo = null;
//                    $scope.dateError = '';
//                    $scope.notIssueFound = 'No Book Information Found with the ISBN number  ' + $scope.issueId + ' Please Enter Correct ISBN Number';
//                    console.log($scope.notIssueFound);
//                  }
//            });
//        }else{
//            $scope.notIssueFound = '';
//            $scope.shuvo = null;
//            $scope.error = 'Please enter correct Issue ID';
//            console.log($scope.error);
//        }
//    }


//             $scope.fineRemission=function(object){
//                $scope.fineStatus=object;
//                console.log($scope.fineStatus);
//                if($scope.fineStatus=='true'){
//                    $scope.fineDecrease=$scope.dlBookReturn.totalFineCompensation-$scope.countingTotalFine;
//                    console.log("Decrease="+$scope.fineDecrease);
//                    $scope.dlBookReturn.totalFineCompensation=$scope.fineDecrease;
//                }else if($scope.fineStatus=='false'){
//                    console.log("else portion");
//                    $scope.fineIncrease=$scope.dlBookReturn.totalFineCompensation+$scope.countingTotalFine;
//                    console.log("Increase="+$scope.fineIncrease);
//                    $scope.dlBookReturn.totalFineCompensation=$scope.fineIncrease;
//                }
//
//             }
//
//
//             $scope.fineCompensation=function(object){
//                 $scope.fineCompensationStatus=object;
//                 console.log($scope.fineCompensationStatus);
//                if($scope.fineCompensationStatus=='true'){
//                    console.log($scope.dlBookReturn.totalFineCompensation);
//                    $scope.CompensationDecrease=$scope.dlBookReturn.totalFineCompensation-$scope.COMPENSATION;
//                    console.log("Decrease="+$scope.CompensationDecrease);
//                    $scope.dlBookReturn.totalFineCompensation=$scope.CompensationDecrease;
//                }else if($scope.fineCompensationStatus=='false'){
//                    console.log("else portion");
//                    $scope.CompensationIncrease=$scope.dlBookReturn.totalFineCompensation+$scope.COMPENSATION;
//                    console.log("Increase="+$scope.CompensationIncrease);
//                    $scope.dlBookReturn.totalFineCompensation=$scope.CompensationIncrease;
//                }
//
//             }

            var onUpdateSuccess = function (result) {
//                console.log("========= Fired man =========");
//                console.log($scope.dlBookIssue.ID);
//                $q.all([$scope.dlBookIssue.$promise]).then(function() {
//                     console.log($scope.dlBookIssue.id);
//                     DlBookInfo.get({id: $scope.dlBookIssue.dlBookInfo.id}, function(result3){
//                         $scope.dlBookInfo =  result3;
//                         console.log($scope.dlBookInfo);
//                        $scope.dlBookEdition.totalCopies =parseInt ($scope.dlBookInfo.dlBookEdition.totalCopies)+1;
//                        DlBookInfo.update($scope.dlBookInfo);
//                     });
//                });

                DlBookEdition.get({id: $scope.dlBookIssue.dlBookEdition.id}, function (result3) {
                    $scope.dlBookEdition = result3;
                    console.log("upper One");
                    console.log($scope.dlBookEdition.totalCopies);
                    $scope.dlBookEdition.totalCopies = parseInt($scope.dlBookEdition.totalCopies) + 1;
                    console.log("lower one");
                    console.log($scope.dlBookEdition.totalCopies);
                    console.log("ans update hoise ");
                    DlBookEdition.update($scope.dlBookEdition)

                });


            }

            var onSaveSuccess = function (result) {
                DlBookIssue.get({id: result.dlBookIssue.id}, function (result2) {
                    $scope.dlBookIssue = result2;
                    $scope.dlBookIssue.status = 0;
                    DlBookIssue.update($scope.dlBookIssue, onUpdateSuccess);
                });
                $scope.$emit('stepApps:dlBookReturnUpdate', result);
                $scope.isSaving = false;
                $state.go('libraryInfo.dlBookReturn.new', {}, {reload: true});
            };


            $scope.showIssueDetail = function () {
                DlIssueByStudentId.get({id: $scope.stuId}, function (result) {
                    $scope.dlBookIssueList = result;
                    console.log("Issue list");
                    console.log($scope.dlBookIssueList);
                });
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };


            $scope.showFormForReturnBook = function (data) {
                $scope.ShoWholeForm = true;
                $scope.getId = data;
                console.log("amar data");
                console.log($scope.getId);
                console.log("amar data");

                DlIssueByIssueId.get({id: $scope.getId}, function (result) {
                    $scope.holeDataOfBookIssue = result;
                    console.log("shuvo print");
                    console.log($scope.holeDataOfBookIssue);
                    $scope.subCategoryIdForParameter = $scope.holeDataOfBookIssue.dlBookInfo.dlContSCatSet.id;
                    console.log($scope.subCategoryIdForParameter);
                    $scope.dlBookReturn.title = $scope.holeDataOfBookIssue.dlBookInfo.title;//Book Title

                    $scope.dlBookReturn.authorName = $scope.holeDataOfBookIssue.dlBookInfo.authorName;//authorName

                    $scope.dlBookReturn.edition = $scope.holeDataOfBookIssue.dlBookEdition.edition;//edition

                    $scope.dlBookReturn.issueDate = $scope.holeDataOfBookIssue.issueDate;//issueDate

                    $scope.dlBookReturn.returnDate = $scope.holeDataOfBookIssue.returnDate;//returnDate


                    DlFineInfoBySCatId.get({id: $scope.subCategoryIdForParameter}, function (result) {
                        $scope.FineResult = result;
                        console.log($scope.FineResult);

                        var d1 = Date.parse($scope.holeDataOfBookIssue.returnDate);
                        var d3 = Date.parse($scope.holeDataOfBookIssue.issueDate);
                        var d2 = Date.parse($scope.dlBookReturn.actualReturnDate);
                        var ONE_DAY = 1000 * 60 * 60 * 24

                        $scope.diffOfIssueSubmit = Math.abs(d2 - d3);
                        console.log($scope.diffOfIssueSubmit);

                        $scope.DiffAsDay = Math.round($scope.diffOfIssueSubmit / ONE_DAY);
                        console.log("Difference Between Issue And Return=" + $scope.DiffAsDay);

                        $scope.allocatedDay = $scope.FineResult.timeLimit;
                        console.log("Allocation day=" + $scope.allocatedDay);

                        if ($scope.DiffAsDay > $scope.allocatedDay) {
                            $scope.error = '';
                            $scope.notIssueFound = '';
                            $scope.dateError = 'Your Date is expired!';
                            console.log($scope.dateError);
                            console.log("your are late");

                            $scope.fineDay = Math.abs($scope.DiffAsDay - $scope.allocatedDay);
                            console.log("Fine Date=" + $scope.fineDay);

                            $scope.countingTotalFine = Math.abs($scope.fineDay * $scope.FineResult.fine);
                            console.log("Total fine=" + $scope.countingTotalFine);

                            $scope.dlBookReturn.totalFine = $scope.countingTotalFine;
                        } else {
                            console.log("Every thing is okey");
                            $scope.dlBookReturn.totalFine = 0;
                        }


                        $scope.fineChecking = function (obj) {
                            console.log(obj);
                            if ($scope.dlBookReturn.totalFine != obj) {
                                console.log("Fine is not equal")
                            } else {
                                console.log("Fine is equal");
                            }
                        }
                        $scope.dlBookReturn.compensation = $scope.holeDataOfBookIssue.dlBookEdition.compensation;
                        $scope.compensation = function (obj) {
                            console.log(obj);
                            if ($scope.dlBookReturn.compensation != obj) {
                                console.log("Fine is not equal")
                            } else {
                                console.log("Fine is equal");
                            }

                        }
                        $scope.COMPENSATION = parseInt($scope.holeDataOfBookIssue.dlBookEdition.compensation);
                        console.log("compensation" + $scope.COMPENSATION);

                        $scope.FineValue = $scope.dlBookReturn.totalFine;
                        console.log($scope.FineValue);

                        $scope.TotalFineAndCompensation = Math.abs($scope.FineValue + $scope.COMPENSATION);
                        console.log("TotalFineAndCompensation=" + $scope.TotalFineAndCompensation);

                        $scope.dlBookReturn.totalFineCompensation = $scope.TotalFineAndCompensation;


                        $scope.fineRemission = function (object) {
                            $scope.fineStatus = object;
                            console.log($scope.fineStatus);
                            if ($scope.fineStatus == 'true') {
                                $scope.fineDecrease = $scope.dlBookReturn.totalFineCompensation - $scope.FineValue;
                                console.log("Decrease=" + $scope.fineDecrease);
                                $scope.dlBookReturn.totalFineCompensation = $scope.fineDecrease;
                            } else if ($scope.fineStatus == 'false') {
                                console.log("else portion");
                                $scope.fineIncrease = $scope.dlBookReturn.totalFineCompensation + $scope.FineValue;
                                console.log("Increase=" + $scope.fineIncrease);
                                $scope.dlBookReturn.totalFineCompensation = $scope.fineIncrease;
                            }

                        }

                        $scope.fineCompensation = function (object) {
                            $scope.fineCompensationStatus = object;
                            console.log($scope.fineCompensationStatus);
                            $scope.totalFineCompensationValue = $scope.dlBookReturn.totalFineCompensation;
                            if ($scope.fineCompensationStatus == 'true') {

                                $scope.CompensationDecrease = $scope.totalFineCompensationValue - $scope.COMPENSATION;
                                console.log("Decrease=" + $scope.CompensationDecrease);
                                $scope.dlBookReturn.totalFineCompensation = $scope.CompensationDecrease;

                            } else if ($scope.fineCompensationStatus == 'false') {
                                console.log("else portion");
                                $scope.CompensationIncrease = $scope.totalFineCompensationValue + $scope.COMPENSATION;
                                console.log("Increase=" + $scope.CompensationIncrease);
                                $scope.dlBookReturn.totalFineCompensation = $scope.CompensationIncrease;
                            }

                        }

                    });
                });
            }


            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.dlBookReturn.id != null) {
                    DlBookReturn.update($scope.dlBookReturn, onSaveSuccess, onSaveError);
                } else {
                    $scope.dlBookReturn.dlBookIssue = $scope.holeDataOfBookIssue;
                    console.log("at save");
                    console.log($scope.dlBookReturn.dlBookIssue);
                    DlBookReturn.save($scope.dlBookReturn, onSaveSuccess, onSaveError);
                }
            };


        }]);
