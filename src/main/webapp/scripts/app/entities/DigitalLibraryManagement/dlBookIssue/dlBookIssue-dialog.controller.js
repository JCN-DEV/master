'use strict';

angular.module('stepApp').controller('DlBookIssueDialogController',
    ['$scope', '$state', 'Principal', '$stateParams', 'entity', 'DlBookIssue', 'InstEmployee', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet', 'DlBookReturn', 'getEmplInfoByCode', 'DlBookInfo', 'isbnBookInfo', 'instituteStudentInfo', 'SisStudentInfo', 'getAllAuthorByBookTitle', 'getAllEditionById', 'DlBookEdition', 'sisStudentInfoInstituteWise', 'getAllBookInfoByScatAndInstitute', 'findAllBookIssue', 'findBookIssueForStudentRole', 'FindActivcategory', 'FindActiveSubcategory',
        function ($scope, $state, Principal, $stateParams, entity, DlBookIssue, InstEmployee, DlContTypeSet, DlContCatSet, DlContSCatSet, DlBookReturn, getEmplInfoByCode, DlBookInfo, isbnBookInfo, instituteStudentInfo, SisStudentInfo, getAllAuthorByBookTitle, getAllEditionById, DlBookEdition, sisStudentInfoInstituteWise, getAllBookInfoByScatAndInstitute, findAllBookIssue, findBookIssueForStudentRole, FindActivcategory, FindActiveSubcategory) {

            $scope.dlBookIssue = entity;
            $scope.isbnBookInfo = {};
            $scope.tt = {};
            $scope.dlBookInfo = {};
            $scope.instemployees = InstEmployee.query();
            $scope.dlconttypesets = DlContTypeSet.query();
            $scope.dlBookEditions = DlBookEdition.query();
            $scope.dlcontcatsets = DlContCatSet.query();
            $scope.dlcontscatsets = DlContSCatSet.query();
            $scope.dlbookreturns = DlBookReturn.query();
            $scope.dlbooinfos = DlBookInfo.query();
            $scope.issueSave = false;
            $scope.takenCheck = true;
            $scope.stdtakenCheck = true;

            /*

             $scope.load = function(id) {
             DlBookIssue.get({id : id}, function(result) {
             $scope.dlBookIssue = result;
             });
             };
             */

            //Find Employee Information By code
            $scope.getEmployeeInfo = function (code) {
                $scope.code = code;
                if ($scope.code && $scope.code.trim() != '') {
                    $scope.getEmplInfoByCodes = getEmplInfoByCode.get({code: $scope.code}, function (result) {
                        $scope.getEmplInfoByCode = result;
                        $scope.EmpInfoError = '';
                        $scope.notFound = '';
                    }, function (response) {
                        if (response.status === 404) {
                            $scope.isSaving = true;
                            $scope.EmpInfoError = '';
                            $scope.notFound = 'No Employee Found with the employee code ' + $scope.code;
                            $scope.getEmplInfoByCode = null;
                        }
                    });
                } else {
                    $scope.notFound = '';
                    $scope.EmpInfoError = 'Please enter correct Employee Code';
                    $scope.getEmplInfoByCode = null;
                }
            }


            //Find Book Information By ISBN Number
            $scope.isbnBookInfos = function (bookId) {
                $scope.bookId = bookId;
                console.log($scope.bookId);
                if ($scope.bookId && $scope.bookId.trim() != '') {
                    $scope.isbnBookInfo = isbnBookInfo.get({bookId: $scope.bookId}, function (result) {
                        $scope.isbnBookInfo = result;
                        console.log($scope.isbnBookInfo);
                        $scope.bookIdWise = $scope.isbnBookInfo.id;

                        getAllEditionById.query({id: $scope.bookIdWise}, function (result) {
                            $scope.editionWiseBook = result;
                            console.log("welcome to edition");
                            console.log($scope.editionWiseBook);
                        });

                        /*
                         $scope.dlBookIssue.dlbookinfoId=$scope.isbnBookInfo.id;
                         */
                        $scope.dlBookIssue.dlBookInfo = $scope.isbnBookInfo;
                        $scope.isbnInfoError = '';
                        $scope.notIsbnFound = '';
                    }, function (response) {
                        if (response.status === 404) {
                            $scope.isSaving = true;
                            $scope.isbnInfoError = '';
                            $scope.notIsbnFound = 'No Book Information Found with the Book-ID   ' + $scope.bookId + ' Please Enter Correct Book-ID';
                            $scope.isbnBookInfo = null;
                        }
                    });
                } else {
                    $scope.notIsbnFound = '';
                    $scope.isbnInfoError = 'Please enter correct Book-ID ';
                    $scope.isbnBookInfo = null;
                }
            };


            $scope.getEditionInfo = function (data, titleId, editionId) {
                $scope.answer = data;
                console.log($scope.answer);
                console.log("edition list");
                //checking Book Availavle or not in Liberian And Student those role
                if ($scope.answer.totalCopies > 1) {
                    $scope.issueSave = false;
                    console.log("Book available");
                    $scope.availableError = '';
                } else {
                    $scope.issueSave = true;
                    console.log("Book is not available");
                    $scope.availableError = 'Your requested Book is not Available';
                }
                //End checking Book Availavle or not in Liberian And Student those role

                $scope.titleidget = titleId[0].bookId;
                $scope.editionidget = editionId.id;
                console.log($scope.titleidget, $scope.editionidget);

                findBookIssueForStudentRole.get({
                        bookId: $scope.titleidget,
                        BookEditionId: $scope.editionidget
                    }, function (result) {

                        $scope.GetDuplicateIssuByStudentRole = result;
                        console.log($scope.GetDuplicateIssuByStudentRole);

                        if ($scope.GetDuplicateIssuByStudentRole != null) {
                            console.log("You Are Not Allowed To Take This Book Again !");
                            $scope.StdTakeError = 'You Are Not Allowed To Take This Book Again !';

                            $scope.stdtakenCheck = true;


                        }

                    }, function (respons) {

                        if (respons.status === 404) {
                            console.log("You Are Allowed To Take This Book");
                            $scope.StdTakeError = '';

                            $scope.stdtakenCheck = false;


                        }
                    }
                );//End checking Allowed to take this book


            };


            //Find Student Information By ID
            $scope.instituteStudentInfos = function (stdId) {
                console.log(stdId);
                if (stdId > 0) {
                    sisStudentInfoInstituteWise.get({id: stdId}, function (result) {
                        console.log(result);
                        $scope.dlBookIssue.sisStudentInfo = result;
                        $scope.studentInfoError = '';
                        $scope.notStudentFound = '';
                    }, function (response) {
                        if (response.status === 404) {
                            $scope.isSaving = true;
                            $scope.studentInfoError = '';
                            $scope.notIsbnFound = 'No Book Information Found with the ISBN number  ' + $scope.stdId + ' Please Enter Correct ISBN Number';
                            $scope.instituteStudentInfo = null;
                        }
                    });
                } else {
                    $scope.notStudentFound = '';
                    $scope.studentInfoError = 'Please enter correct ISBN Number';
                    $scope.instituteStudentInfo = null;
                }
            };


            DlBookIssue.query({id: $stateParams.id}, function (result, headers) {
                $scope.dlBookIssues = result;
            });


//        $scope.checkAvailable = function (noOfCopies) {
//        if($scope.dlBookIssue.noOfCopies >= $scope.answer.totalCopies-2 || $scope.dlBookIssue.noOfCopies>2){
//
//        $scope.availableError = 'Your requested number of copies exceed the the limit of availability';
//        $scope.dlBookIssue.noOfCopies=null;
//                  }
//
//        }


            var onSaveSuccess = function (result) {
                console.log(result);
                /*    DlBookInfo.get({id: $scope.isbnBookInfo.id}, function(result3){
                 $scope.dlBookInfo =  result3;
                 $scope.dlBookInfo.totalCopies = $scope.dlBookInfo.totalCopies-$scope.dlBookIssue.noOfCopies;
                 DlBookInfo.update($scope.dlBookInfo);
                 });*/
                if (Principal.isAuthenticated() && Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {

                    DlBookEdition.get({id: $scope.answer.id}, function (result3) {
                        $scope.dlBookEdition = result3;
                        $scope.dlBookEdition.totalCopies = parseInt($scope.dlBookEdition.totalCopies - 1);
                        DlBookEdition.update($scope.dlBookEdition)

                    });

                }


                DlBookIssue.query({id: $stateParams.id}, function (result, headers) {
                    $scope.dlBookIssues = result;
                });


                $scope.$emit('stepApp:dlBookIssueUpdate', result);
                $scope.isSaving = false;


                if (Principal.isAuthenticated() && Principal.hasAnyAuthority(['ROLE_GOVT_STUDENT'])) {

                    $state.go('libraryInfo.dlBookIssue.detail', {id: result.id});
                }
                else if (Principal.isAuthenticated() && Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {

                    $state.go('libraryInfo.dlBookIssue', {}, {reload: true});
                }
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;


            };
            console.log($scope.isbnBookInfo.total_copies);
            console.log($scope.isbnBookInfo.id);


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
            var allBooksTitle = DlBookInfo.query({page: $scope.page, size: 500000}, function (result, headers) {
                return result;
            });

            $scope.updatedDlBooksTitle = function (ansss) {
                console.log("selected sub category .............");
                $scope.getId = ansss.id;
                console.log($scope.getId);
                getAllBookInfoByScatAndInstitute.get({id: $scope.getId}, function (result) {
                    $scope.BookTitle = result;
                    console.log("welcome to edition");
                    console.log($scope.BookTitle);
                });


//                                                $scope.dlBookInfos=[];
//                                                angular.forEach(allBooksTitle, function(dlBookInfo) {
//                                                    if(select.id==dlBookInfo.dlContSCatSet.id){
//                                                        $scope.dlBookInfos.push(dlBookInfo);
//
//                                                    }
//                                                });

            };

            $scope.updatedDlContSCatSet = function (select) {
                /* console.log("selected district .............");
                 console.log(select);*/
                $scope.dlContSCatSets = [];
                angular.forEach(allDlContSCatSet, function (dlContSCatSet) {
                    if (select.id == dlContSCatSet.dlContCatSet.id) {
                        $scope.dlContSCatSets.push(dlContSCatSet);
                    }
                });

            };

            $scope.dlContCatSets = DlContCatSet.query();
            $scope.dlContSCatSets = DlContSCatSet.query();

            $scope.updatedDlContCatSet = function (select) {
                $scope.dlContCatSets = [];
                angular.forEach(allDlContCatSet, function (dlContCatSet) {

                    if ((dlContCatSet.dlContTypeSet && select) && (select.id != dlContCatSet.dlContTypeSet.id)) {
                        console.log("There is error");
                    } else {
                        console.log("There is the fire place");
                        $scope.dlContCatSets.push(dlContCatSet);
                    }
                });
            };

            $scope.loadAuthorByBookTitle = function (select) {
                $scope.title = select;
                console.log($scope.title);
                getAllAuthorByBookTitle.query({title: $scope.title}, function (result) {
                    $scope.tt = result;
                    console.log("welcome to tt");
                    console.log($scope.tt);
                    console.log($scope.tt.id);

                });


            };

            $scope.getAllEditionByAuthorName = function (select) {
                $scope.data = select.id;
                console.log("Edition" + $scope.data);
                getAllEditionById.query({id: $scope.data}, function (result) {
                    $scope.bookEdition = result;
                    console.log("welcome to edition");
                    console.log($scope.bookEdition);
                });

            };


            $scope.getAllParamiter = function (studentid, BookId, Edition) {
                $scope.studentsid = studentid;
                $scope.BooksId = BookId;

                $scope.Editions = Edition;

                $scope.Editionss = Edition.id;

                console.log("all paramiter" + $scope.studentsid, $scope.BooksId, $scope.Editions);
                findAllBookIssue.get({
                        sisId: $scope.studentsid,
                        BookInfoId: $scope.BooksId,
                        BookEdiId: $scope.Editionss
                    }, function (result) {

                        $scope.AnsOfAllDuplicateIssue = result;
                        console.log($scope.AnsOfAllDuplicateIssue);

                        if ($scope.AnsOfAllDuplicateIssue != null) {
                            console.log("Student Already Taken This Book");
                            $scope.takenCheck = true;
                            $scope.takeError = 'This Book is already Issued this Student';


                        }

                    }, function (respons) {

                        if (respons.status === 404) {
                            console.log("you are  allowed to take this book")

                            $scope.takenCheck = false;
                            $scope.takeError = '';


                        }
                    }
                );
            };


            //Start Past Date Disabled function
            $scope.today = function () {
                $scope.dlBookIssue.returnDate = new Date();
                $scope.dlBookIssue.expecRecvDate = new Date();
            };
            $scope.dateformat = "MM/dd/yyyy";
            $scope.today();
            $scope.showcalendar = function ($event) {
                $scope.showdp = true;
            };
            $scope.showdp = false;
            $scope.dtmax = new Date();
            // End Past Date Disabled function


            $scope.save = function () {
                //console.log($scope.isbnBookInfo.id);
                console.log($scope.dlBookIssue);
                $scope.isSaving = true;
                if ($scope.dlBookIssue.id != null) {
                    console.log("comes to if");
                    DlBookIssue.update($scope.dlBookIssue, onSaveSuccess, onSaveError);
                } else {
                    if (Principal.isAuthenticated() && Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
                        /*
                         $scope.dlBookIssue.dlBookInfo=$scope.dlBookInfo;
                         */
                        $scope.dlBookIssue.issueDate = new Date();

                        $scope.dlBookIssue.status = 1;
                        $scope.dlBookIssue.noOfCopies = 1;


                    }
                    else if (Principal.isAuthenticated() && Principal.hasAnyAuthority(['ROLE_GOVT_STUDENT'])) {
                        $scope.dlBookIssue.status = 2;
                        /*
                         $scope.dlBookIssue.isbnNo= $scope.bookEdition[0].isbnNo;
                         */
                        $scope.dlBookIssue.requisitionDate = new Date();

                    }
                    console.log("come to else");

                    DlBookIssue.save($scope.dlBookIssue, onSaveSuccess, onSaveError);
                }
            };


        }]);
