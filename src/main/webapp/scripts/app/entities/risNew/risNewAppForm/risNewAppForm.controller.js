'use strict';

angular.module('stepApp')
    .controller('RisNewAppFormController',
    ['$scope', '$state', '$rootScope', 'RisNewAppForm', 'RisNewAppFormSearch', 'ParseLinks', 'getEmployed', 'userregistration', 'jobPosting', 'TestHello',
        'getApplicantsByDesignation', 'HrDesignationSetup', 'sendingEmail', 'findById', 'smsWritten', 'seatwithcircular', 'gettingwithcircular', 'sendingEmailAppointmentLetter', 'getOneApplicantByStatus',
        'Selection', 'getApplicantsByStatus', 'Auth', 'User', 'getjobRequest', 'jobRequestUpdate', 'jobRequest', 'getCircularNumber', 'jobRequestUpdateByCircularNo', 'getJobCircular', 'getJobByCircular',
        'getJobByCircularStatus', 'getApplicantByRegNo', 'getapplicantbycircularandstatus', 'uniqueCircular', '$timeout', 'getalljobPosting', 'getjobPosting', 'risNewAppFormsbyuser', 'Principal', 'emailSend',
        function ($scope, $state, $rootScope, RisNewAppForm, RisNewAppFormSearch, ParseLinks, getEmployed, userregistration, jobPosting, TestHello,
                  getApplicantsByDesignation, HrDesignationSetup, sendingEmail, findById, smsWritten, seatwithcircular, gettingwithcircular, sendingEmailAppointmentLetter, getOneApplicantByStatus,
                  Selection, getApplicantsByStatus, Auth, User, getjobRequest, jobRequestUpdate, jobRequest, getCircularNumber, jobRequestUpdateByCircularNo, getJobCircular, getJobByCircular
            , getJobByCircularStatus, getApplicantByRegNo, getapplicantbycircularandstatus, uniqueCircular, $timeout, getalljobPosting, getjobPosting, risNewAppFormsbyuser, Principal, emailSend) {
            $scope.risNewAppForms = [];
            $scope.applicant = '';
            $scope.user = {};
            $scope.newJobPosts = [];
            $scope.jobpostingdto = {};
            $scope.circularUpdate = {};
            $scope.circularstatus = {};
            $scope.circularReject = {};
            $scope.seatdto = {};
            $scope.circulardto = {};
            $scope.usernametemp = "shafiqayon";
            $scope.pwtemp = "twinmos4";
            $scope.pwtemp = "twinmos4";
            $scope.firsttemp = "Shafiqur";
            $scope.secondtemp = "Rahman";
            $scope.emailtemp = "shafiqur.rahman.ayon@gmail.com";
            $scope.risNewAppForm = {};
            $scope.risNewAppForm.smsMessage = "";
            $scope.risNewAppForm.vivaTime = "";
            $scope.risNewAppForm.vivaVenueName = "";
            $scope.risNewAppForm.vivaDate = "";
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


            $scope.page = 0;
            $scope.i = 0;
            $scope.loadAll = function () {

                if (Principal.hasAnyAuthority(['ROLE_ADMIN'])) {

                    RisNewAppForm.query({page: $scope.page, size: 20}, function (result, headers) {
                        $scope.links = ParseLinks.parse(headers('link'));
                        $scope.risNewAppForms = result;
                    });
                }
                else if (Principal.hasAnyAuthority(['ROLE_APPLICANT'])) {
                    risNewAppFormsbyuser.query({page: $scope.page, size: 20}, function (result, headers) {
                        $scope.links = ParseLinks.parse(headers('link'));
                        $scope.risNewAppForms = result;
                    });
                }
            };
            $scope.dateFormatter = function (format) {
                console.log(format);
                var date = format.toString().substring(4, 15);
                return date;
            }
            $scope.time = function (format) {
                console.log(format);
                var date = format.toString().substring(16, 21);
                return date;
            }
            $scope.finalNumber = [];
            $scope.finalNumberInt = [];


            // to get the list of student inserting the circular no and seat
            $scope.sendSms = function (circularno, seat) {
                $scope.circularno = circularno;
                $scope.seatNo = seat;
                console.log($scope.seatNo);

                if ($scope.seatNo == 'undefined' || $scope.circularno == null) {
                    $scope.emptyError = 'Please Select Circular number and input Seat number';
                } else {
                    $scope.test = [];
                    $scope.circularno = $scope.circularno.CIRCULARNO;
                    $scope.emptyError = '';
                    console.log($scope.seatNo);
                    console.log("================");
                    console.log($scope.circularno);
                    $scope.seatdto.circularNo = $scope.circularno;
                    $scope.seatdto.seat = $scope.seatNo;
                    $scope.snedSmsResults = seatwithcircular.update($scope.seatdto);

                    console.log($scope.snedSmsResults);
                }
            }

            $scope.vivaSending = function () {
                $('#sendforviva').modal('show');
            }

            $scope.vivasms = [];
            $scope.vivasmsFinal = [];

            //fn for sending sms to selected candidate for viva sms sending
            $scope.confirmVivaSending = function () {
                $('.selectedviva:checked').each(function () {
                    $scope.vivasms.push($(this).val());
                    console.log("check val :" + $(this).val());
                    //will implement the sms sending here
                    console.log("length of array " + $scope.vivasms.length);
                });
                angular.forEach($scope.vivasms, function (value, key) {
                    $scope.vivasmsFinal[$scope.i] = parseInt($scope.vivasms[$scope.i]);
                    console.log($scope.vivasmsFinal[$scope.i]);
                    $scope.i++;
                })
                /*  $scope.risNewAppForm.formattedsms='Dear examinee your exam for is at ' + $rootScope.formatString($scope.risNewAppForm.vivaTime)  + 'on ' +$scope.risNewAppForm.vivaVenueName + 'at '+  $scope.risNewAppForm.vivaDate;*/
                $scope.message = $scope.risNewAppForm.smsMessage;
                $scope.subject = "Viva";
                $scope.venue = $scope.risNewAppForm.vivaVenueName;
                $scope.date = $scope.dateFormatter($scope.risNewAppForm.vivaDate);
                $scope.time =  $scope.time($scope.risNewAppForm.vivaTime);
                $scope.status = "7";
                console.log("check for sms send");
                console.log($scope.venue);
                console.log($scope.date);
                console.log($scope.time);
                console.log("check for sms send ends");
                console.log("sending to backend", $scope.vivasmsFinal);

                emailSend.get({
                    id: $scope.vivasmsFinal,
                    messagebody: $scope.message,
                    messageSubject: $scope.subject,
                    venue_name: $scope.venue,
                    exam_date: $scope.date,
                    exam_time: $scope.time,
                    status: $scope.status
                });

                /*$scope.smsandEmailSender($(this).val());*/
                $('.modal-open').css('overflow', 'scroll');
                $(".modal-backdrop").remove();
                $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                $state.go('risNewAppForm.vivaApplicantList', null, {reload: true});

            }

            $scope.sendWriten = function () {
                $('#sendWriten').modal('show');
            }

            //fn for sending sms and email to written examinees
            $scope.processSending = function () {
                $('.selectedNumbers:checked').each(function () {
                    $scope.finalNumber.push($(this).val());
                    console.log("check val :" + $(this).val());
                    /* $scope.smsandEmailSender($(this).val());*/
                });

                angular.forEach($scope.finalNumber, function (value, key) {
                    $scope.finalNumberInt[$scope.i] = parseInt($scope.finalNumber[$scope.i]);
                    console.log($scope.finalNumberInt[$scope.i]);
                    $scope.i++;
                })
                $scope.message = $scope.risNewAppForm.smsMessage;
                $scope.subject = "Written";
                $scope.venue = $scope.risNewAppForm.venueName;
                $scope.date = $scope.risNewAppForm.examDate;
                $scope.time = $scope.risNewAppForm.examTime;
                $scope.status = "7";

                console.log("check for sms send ");
                console.log($scope.venue);
                console.log($scope.date);
                console.log($scope.time);
                console.log("check for sms send ends ");

                console.log("sending to backend", $scope.finalNumberInt);
                sendingEmail.get({
                    id: $scope.finalNumberInt,
                    messagebody: $scope.message,
                    messageSubject: $scope.subject,
                    venue_name: $scope.venue,
                    exam_date: $scope.date,
                    exam_time: $scope.time,
                    status: $scope.status
                });
                $('.modal-open').css('overflow', 'scroll');
                $(".modal-backdrop").remove();
                $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                $state.go('risNewAppForm.WrittenExamSelection', null, {reload: true});
            };


            $scope.loadPage = function (page) {
                $scope.page = page;
                $scope.loadAll();
            };
            $scope.loadAll();


            $scope.getEmployedd = getEmployed.get();
            console.log($scope.getEmployedd);

            $scope.circulars = getCircularNumber.query();
            console.log($scope.circulars);

            $scope.jobCirculars = getJobCircular.query();
            console.log("jobCirculars" + $scope.jobCirculars);


            $scope.viewJobsBycircular = function (circularno) {
                console.log("This is the fire place");
                $scope.JobByCircular = [];
                $scope.circularno = circularno;
                console.log($scope.circularno);
                $scope.circulardto.circularNo = $scope.circularno;
                console.log($scope.circulardto.circularNo);
                $scope.JobByCircular = getJobByCircular.get($scope.circulardto);
                console.log($scope.JobByCircular);
                $('#viewJobsBycircular').modal('show');
            };


            $scope.viewRejectJobsBycircular = function (circularno) {
                console.log("This is the fire place");
                $scope.JobByCircular = [];
                $scope.circularno = circularno;
                console.log($scope.circularno);
                $scope.circulardto.circularNo = $scope.circularno;
                console.log($scope.circulardto.circularNo);
                $scope.JobByCircular = getJobByCircular.get($scope.circulardto);
                console.log($scope.JobByCircular);
                $('#rejectJobApprove').modal('show');
            }

            var onFoundJobsSuccess = function (result) {
                console.log("keno emon hoi man");
                console.log(result);
                $scope.forJobPosts = result;
            }

            $scope.newJobPost = function (circularno) {
                $scope.circularno = circularno;
                console.log("========== This is Here ============");
                $scope.forJobPosts = [];
                $scope.circulardto.circularNo = $scope.circularno;
                console.log($scope.circulardto.circularNo);
                getJobByCircular.get($scope.circulardto, onFoundJobsSuccess);
                console.log("=============== This is the perfect place ============");
            }


            $scope.back = function () {
                $scope.details = null;
                $scope.errorMessage = null;
                $scope.dataMessage = null;
            }

            var detailFound = function (result) {
                $scope.details = result;
                console.log("This is the fire place");
                console.log($scope.details);
                console.log($scope.details.length);

                if ($scope.details.length > 0) {
                    $scope.errorMessage = '';

                } else {
                    $scope.dataMessage = '';
                    $scope.errorMessage = 'No Data Found';
                }
            }

            $scope.detailsOfJobs = function (circularno) {
                $scope.circularno = circularno;
                $scope.circulardto.circularNo = $scope.circularno;
                console.log($scope.circulardto.circularNo);
                getjobPosting.post($scope.circulardto, detailFound);
            }


            $scope.opened = [];
            $scope.opened2 = [];
            $scope.open = function (index) {
                $timeout(function () {
                    $scope.opened[index] = true;
                });
            };
            $scope.open2 = function (index) {
                $timeout(function () {
                    $scope.opened2[index] = true;
                });
            };


            $scope.viewApprovedJobsBycircular = function (circularno) {
                console.log("This is the fire place");
                $scope.JobByCircular = [];
                $scope.circularno = circularno;
                console.log($scope.circularno);
                $scope.circulardto.circularNo = $scope.circularno;
                console.log($scope.circulardto.circularNo);
                $scope.JobByCircular = getJobByCircular.get($scope.circulardto);
                console.log($scope.JobByCircular);
                $('#viewApprovedJobsBycircular').modal('show');
            }

            $scope.approveJobsBycircular = function () {
                $scope.JobByCircular = [];
                $scope.circularUpdate.circularNo = $scope.circularno;
                $scope.circularUpdate.status = 2;
                console.log("job update by circular " + $scope.circularUpdate);
                $scope.JobByCircular = jobRequestUpdateByCircularNo.get($scope.circularUpdate);
                console.log($scope.JobByCircular);
                console.log("Enterring here");
                $('.modal-open').css('overflow', 'scroll');
                $('.modal-backdrop').css('display', 'none');
                $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                $state.go('risNewAppForm.Ministry', null, {reload: true});

            }

            $scope.rejectJobsBycircular = function () {
                $scope.rejectByCircular = [];
                $scope.circularReject.circularNo = $scope.circularno;
                $scope.circularReject.status = 9;
                console.log("job update by circular " + $scope.circularReject);
                $scope.rejectByCircular = jobRequestUpdateByCircularNo.get($scope.circularReject);
                console.log($scope.rejectByCircular);
                $('.modal-open').css('overflow', 'scroll');
                $('.modal-backdrop').css('display', 'none');
                $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                $('#viewApprovedJobsBycircular').hide('show');
                $state.go('risNewAppForm.Ministry', null, {reload: true});

            }

            //fulling objects with designation
            $scope.getAll = function (circularno) {
                $scope.test2 = [];
                $scope.circularno = circularno;
                console.log($scope.circularno);
                $scope.circulardto.circularNo = $scope.circularno;
                $scope.getByDesignations = gettingwithcircular.get($scope.circulardto);
                console.log("********getting Applicants with circular number*****");
                console.log($scope.getByDesignations);

                /*getApplicantsByDesignation.get({designation: $scope.desigPara}, function (result) {
                 $scope.getByDesignations = result;
                 if ($scope.getByDesignations.length > 0) {
                 $scope.showError = '';
                 $scope.showMessage = 'The following Application found';

                 } else {
                 $scope.showMessage = '';
                 $scope.showError = 'No Application found';
                 }
                 });*/
            }


            //select applicant for viva it will select status containing with id 7
            //written exam er jonne dakle 7 viva select er jonne 4 initial 1
            $scope.getAppForSelect = function (regiNo) {
                $scope.status = 7;
                $scope.id = regiNo;
                console.log($scope.id);
                if ($scope.id == null) {
                    $scope.showError = 'Please Enter correct Application Registration Number';
                    console.log($scope.showError);
                } else {

                    getApplicantByRegNo.get({regno: $scope.id, status: $scope.status}, function (result) {
                        $scope.getAppForSelects = result;
                        if ($scope.getAppForSelects.length > 0) {
                            $scope.getAppForSelects = $scope.getAppForSelects[0];
                            console.log("==========THis is ==========");
                            console.log($scope.getAppForSelects);
                            $scope.showError = '';
                            $scope.showMessage = 'The following Application found';
                            console.log($scope.showMessage);

                        } else {
                            $scope.showMessage = '';
                            $scope.showError = 'No Application found with the Registration number ' + $scope.id;
                            console.log($scope.showError);
                        }
                    });
                }
            }

            $scope.appointMentSMS = function (regiNo, messagebody) {


                console.log($scope.regiNo)


                $scope.messagebody = messagebody;
                $scope.status = 8;
                console.log($scope.regno + $scope.messagebody + $scope.status);
                sendingEmailAppointmentLetter.get({
                    regNo: $scope.regiNo,
                    messagebody: $scope.messagebody,
                    status: $scope.status
                })
            }

            $scope.appointMent = function (regiNo) {
                $scope.regiNo = regiNo;
                $scope.status = 4;
                if ($scope.regiNo == null) {
                    $scope.showError = 'Please Enter correct Registration Number';
                    console.log($scope.showError);
                    $scope.appointMents = null;
                } else {
                    getApplicantByRegNo.get({regno: $scope.regiNo, status: $scope.status}, function (result) {
                        $scope.appointMents = result;
                        if ($scope.appointMents.length > 0) {
                            $scope.appointMents = $scope.appointMents[0];
                            console.log("==========THis is ==========");
                            console.log($scope.appointMents);
                            $scope.showError = '';
                            $scope.showMessage = 'The following Application found';
                            console.log($scope.showMessage);
                        } else {
                            $scope.showMessage = '';
                            $scope.showError = 'No Application found with Registration Number ' + $scope.regiNo;
                            console.log($scope.showError);
                        }
                    });
                }
            }


            $scope.vivaApplicantList = function (circularNo) {
                $scope.circularNo = circularNo;
                if ($scope.circularNo == null) {
                    $scope.cirEmpty = 'Please select Circular number';
                    console.log($scope.cirEmpty);
                } else {
                    $scope.list = [];
                    $scope.cirEmpty = '';
                    $scope.status = 4;
                    $scope.circularstatus.circularNo = $scope.circularNo;
                    $scope.circularstatus.status = 4;
                    $scope.list = getapplicantbycircularandstatus.get($scope.circularstatus);
                    console.log($scope.list);
                }
            }


            var onFoundData = function (result) {
                console.log("result found");
                console.log(result);
                //$scope.circularList= result;
                // console.log($scope.circularList.length);
            }


            $scope.checkIfExistCircular = function (circularno) {
                $scope.circularno = circularno;
                console.log($scope.circularno);

                $scope.list = [];
                $scope.circulardto.circularNo = $scope.circularno;
                $scope.list = uniqueCircular.get($scope.circulardto);
                $scope.list.$promise.then(function (data) {
                    console.log(data);
                    console.log(data.length);
                    if (data.length > 0) {
                        $scope.dataFound = 'Circular Number Already exist, Please try another';
                        $scope.validCircular = '';
                    } else {
                        $scope.dataFound = '';
                        $scope.validCircular = 'Circular No is OK';
                    }
                });
            }


            $scope.confirmselectionForViva = function () {
                $scope.status = 4;
                Selection.get({regNo: $scope.regNoforViva, status: $scope.status}, function (result) {
                    $scope.hh = result;
                    console.log($scope.hh);
                });
                $('.modal-backdrop').css('display', 'none');
                $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                $state.go('risNewAppForm.VivaSelection', null, {reload: true});
            }

            $scope.selectionForViva = function (regNo) {
                $scope.regNoforViva = regNo;
                $('#selectionForViva').modal('show');
            }

            $scope.test = function () {
                $scope.test = [];
                $scope.test2 = [];
                console.log("test for seat with circular number  ");
                $scope.seatdto.circularNo = "May/01/02/2016/001";
                $scope.seatdto.seat = 2;
                $scope.test = seatwithcircular.update($scope.seatdto);
                console.log("******seatwithcircular*******");
                console.log($scope.test);
                $scope.circulardto.circularNo = "May/01/02/2016/001";
                $scope.test2 = gettingwithcircular.get($scope.circulardto);
                console.log("********getting with circular number*****");
                console.log($scope.test2);
            }


            // for pulling object with id
            $scope.getApplicantById = function (id) {
                $scope.registrationId = id;
                if ($scope.registrationId != null) {
                    findById.get({id: $scope.registrationId}, function (result) {
                        $scope.risNewAppForm = result[0];
                        if ($scope.risNewAppForm != null) {
                            console.log($scope.risNewAppForm);
                            console.log($scope.registrationId);
                            $scope.notFound = '';
                        } else {
                            $scope.notFound = 'No Information found by the given Registration No.' + $scope.registrationId;
                            console.log($scope.notFound);
                        }
                    });
                } else {
                    $scope.notFound = '';
                    $scope.errorMess = 'Please Enter Correct Registration Number'
                    console.log("===================");
                    console.log($scope.errorMess);
                }
            }

            $scope.postAlert = function () {
                $('#postAlert').modal('show');
            }


            $scope.newJobReqAll = function () {
                $('#newJobReqAll').modal('show');
            }

            $scope.jobReject = function (id, a, b) {
                $scope.jobsApprove = id;
                $scope.position = a;
                $scope.department = b;
                console.log($scope.jobsApprove);
                console.log($scope.position);
                console.log($scope.department);
                $('#jobReject').modal('show');
            }


            /*
             need to format as per this format
             $scope.smsformat= function()
             {
             */
            /* $scope.risNewAppForm.smsMessage='Dear examinee your exam for is at ' + $rootScope.formatString($scope.risNewAppForm.vivaTime)  + 'on ' +$scope.risNewAppForm.vivaVenueName + 'at '+  $scope.risNewAppForm.vivaDate;*/
            /*
             console.log($scope.formatString($scope.risNewAppForm.vivaTime));
             }

             $scope.formatString = function(format) {
             var year   = parseInt(format.substring(0,4));
             var month  = parseInt(format.substring(5,7));
             var day    = parseInt(format.substring(8,10));
             var date = new Date(year, month, day);
             return date;
             };*/


            $scope.confirmReject = function () {
                console.log($scope.jobsApprove);
                console.log($scope.position);
                console.log($scope.department);
                $scope.status = 9;
                console.log("This Approve page");
                jobRequestUpdate.get({
                    position: $scope.position,
                    department: $scope.department,
                    status: $scope.status
                }, function (result) {
                    $scope.JobRequest = result;
                    console.log($scope.JobRequest);
                    console.log("Enterring here");
                    $('.modal-open').css('overflow', 'scroll');
                    $('.modal-backdrop').css('display', 'none');
                    $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                    $state.go('risNewAppForm.Ministry', null, {reload: true});
                });
            };


            $scope.jobApprove = function (id, a, b) {
                $scope.jobsApprove = id;
                $scope.position = a;
                $scope.department = b;
                console.log($scope.jobsApprove);
                console.log($scope.position);
                console.log($scope.department);
                $('#jobApprove').modal('show');
            }

            $scope.newJobReq = function (desig, dept, alloc, assign, vacant) {
                $scope.jobRequestDto = {};
                $scope.desig = desig;
                $scope.dept = dept;
                $scope.alloc = alloc;
                $scope.assign = assign;
                $scope.vacant = vacant;
                $scope.jobStat = 1;
                $scope.circularno = "MAY/1/02/BMET/7";
                /* jobRequest/{position}/{department}/{allocated}/{currentEmployee}/{availableVacancy}/{stataus}*/
                /* jobRequest.get({position: $scope.desig, department: $scope.dept, allocated: $scope.alloc,currentEmployee:$scope.assign,availableVacancy:$scope.vacant,stataus:$scope.jobStat },function(result){

                 }
                 );*/
                $scope.jobRequestDto.position = $scope.desig;
                $scope.jobRequestDto.department = $scope.dept;
                $scope.jobRequestDto.allocated = $scope.alloc;
                $scope.jobRequestDto.currentEmployee = $scope.assign;
                $scope.jobRequestDto.availableVacancy = $scope.vacant;
                $scope.jobRequestDto.stataus = $scope.jobStat;
                $scope.jobRequestDto.circularno = $scope.circularno;
                $scope.jobRequestDto.job_id = $scope.id;


                jobRequest.update($scope.jobRequestDto);
            }


            $scope.confirmApprove = function () {
                console.log($scope.jobsApprove);
                console.log($scope.position);
                console.log($scope.department);
                $scope.status = 2;
                console.log("This Approve page");
                jobRequestUpdate.get({
                    position: $scope.position,
                    department: $scope.department,
                    status: $scope.status
                }, function (result) {
                    $scope.JobRequest = result;
                    console.log($scope.JobRequest);
                    console.log("Enterring here");
                    $('.modal-open').css('overflow', 'scroll');
                    $('.modal-backdrop').css('display', 'none');
                    $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                    $state.go('risNewAppForm.Ministry', null, {reload: true});
                });
            };


            getJobByCircularStatus.get({status: 1}, function (result) {
                $scope.getjobRequests = result;
                console.log("Get All jobs by status 1");
                console.log($scope.getjobRequests);
            });

            getJobByCircularStatus.get({status: 2}, function (result) {
                $scope.approvedLists = result;
                console.log("Get All jobs by status 2");
                console.log($scope.approvedLists);
            });

            getJobByCircularStatus.get({status: 9}, function (result) {
                $scope.rejectLists = result;
                console.log("Get All jobs by status 9");
                console.log($scope.rejectLists);
            });


            $scope.cutomDelete = function (id) {
                $scope.customDelete = id;
                console.log($scope.customDelete);
                $('#customDelete').modal('show');
            }

            $scope.confirmDelete = function () {
                $scope.id = $scope.customDelete;
                RisNewAppForm.delete({id: $scope.id},
                    function () {
                        $scope.clear();
                        $('#customDelete').modal('hide');
                        $('.modal-open').css('overflow', 'scroll');
                        $('.modal-backdrop').css('display', 'none');
                        $rootScope.setSuccessMessage('stepApp.risNewAppForm.deleted');
                        $state.go('risNewAppForm.appList', null, {reload: true});
                    });
            };


            $scope.delete = function (id) {
                $scope.customDelete = id;
                console.log($scope.customDelete);
                $('#customDelete').modal('show');
            };
            $scope.deleteConfirm = function () {
                $scope.id = $scope.customDelete;
                RisNewAppForm.delete({id: $scope.id},
                    function () {
                        $scope.clear();
                        $('#customDelete').modal('hide');
                        $('.modal-open').css('overflow', 'scroll');
                        $('.modal-backdrop').css('display', 'none');
                        $rootScope.setSuccessMessage('stepApp.risNewAppForm.deleted');
                        $state.go('risNewAppForm', null, {reload: true});
                    });
            };

            $scope.register = function () {
                $scope.regFirstName = risRegistration.firstName;
                $scope.regLastName = risRegistration.lastName;
                $scope.regLogin = risRegistration.login;
                $scope.regEmail = risRegistration.emailRegistration;
                $scope.regPassword = risRegistration.password;

                console.log($scope.regFirstName);
                console.log($scope.regLastName);
                console.log($scope.regLogin);
                console.log($scope.regEmail);
                console.log($scope.regPassword);


                userregistration.get(
                    {
                        username: $scope.regLogin,
                        password: $scope.regPassword,
                        firstname: $scope.regFirstName,
                        lastname: $scope.regLastName,
                        email: $scope.regEmail
                    },
                    function (result) {
                        $scope.temp = result;
                        console.log(result);

                    });


                /*$scope.user.langKey = "en";
                 $scope.user.authorities = ["ROLE_APPLICANT"];
                 Auth.createAccount($scope.user).then(function (res) {
                 console.log(res);
                 }).catch(function (response) {
                 $scope.success = null;
                 if (response.status === 400 && response.data === 'login already in use') {
                 $scope.errorUserExists = 'ERROR';
                 } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                 $scope.errorEmailExists = 'ERROR';
                 } else {
                 $scope.error = 'ERROR';
                 }
                 });
                 */

            }
            $scope.saveItems = function (circularno) {
                $scope.circularno = circularno;
                $scope.jobStat = 1;
                $scope.saveRequest2 = [];
                for (var i = 0; i < $scope.getEmployedd.length; i++) {
                    $scope.jobRequestDto = {};
                    var saveRequest = $scope.getEmployedd[i];
                    if (saveRequest.isSelected) {
                        $scope.saveRequest2[i] = saveRequest;
                        console.log("saveRequest");
                        console.log(saveRequest);
                        console.log(saveRequest.ID);
                        console.log("saveRequest ends")
                        console.log($scope.circularno);

                        $scope.jobRequestDto.position = saveRequest.DESIGNATION_NAME;
                        $scope.jobRequestDto.department = saveRequest.DEPARTMENT_NAME;
                        $scope.jobRequestDto.allocated = saveRequest.ALLOCATED;
                        $scope.jobRequestDto.currentEmployee = saveRequest.ASSIGNED;
                        $scope.jobRequestDto.availableVacancy = saveRequest.VACANT;
                        $scope.jobRequestDto.stataus = $scope.jobStat;
                        $scope.jobRequestDto.circularno = $scope.circularno;
                        $scope.jobRequestDto.job_id = saveRequest.ID;

                        console.log("updating request" + $scope.jobRequestDto);
                        jobRequest.update($scope.jobRequestDto, onSave);
                    }
                }
                $('#newJobReqAll').modal('hide');
                $('.modal-open').css('overflow', 'scroll');
                $('.modal-backdrop').css('display', 'none');
                $rootScope.setSuccessMessage('stepApp.risNewAppForm.updated');
                $state.go('risNewAppForm.viewandapprove', null, {reload: true});
            }

            var onSave = function () {
                console.log("keno emon hoi man");
            }

            var savesuccess = function () {
                $state.go('risNewAppForm.getJobList', null, {reload: true});
                $rootScope.setSuccessMessage('Job Circular Has Been Saved');

            }


            $scope.areAllSelected = false;
            $scope.requestSelection = function (getEmployedd, selectionValue) {
                for (var i = 0; i < getEmployedd.length; i++) {
                    getEmployedd[i].isSelected = selectionValue;
                }
            };


            $scope.search = function () {
                RisNewAppFormSearch.query({query: $scope.searchQuery}, function (result) {
                    $scope.risNewAppForms = result;
                }, function (response) {
                    if (response.status === 404) {
                        $scope.loadAll();
                    }
                });
            };

            $scope.refresh = function () {
                $scope.loadAll();
                $scope.clear();
            };
            $scope.setAttachment = function ($file, forJobPost) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            forJobPost.attachment = base64Data;
                            forJobPost.attachmentImgName = $file.name;
                            forJobPost.attachmentContentType = $file.type;
                        });
                    };
                }
            };

            $scope.List = getalljobPosting.post();
            $scope.List.$promise.then(function (data) {
                if (data.length > 0) {
                    $scope.JobLists = data;
                    console.log($scope.JobLists);
                } else {
                    console.log('Data not Found');
                }
            });

            $scope.saveJobItems = function () {
                console.log("This is for save");
                var d = new Date();

                angular.forEach($scope.forJobPosts, function (forJobPost) {
                    console.log(forJobPost);
                    console.log("hellow");
                    /*$scope.jobpostingdto.Circularno = forJobPost.CIRCULARNO;
                     $scope.jobpostingdto.EDUCATIONAL_QUALIFICATION = forJobPost.educationalQualification;
                     $scope.jobpostingdto.OTHER_QUALIFICATION = forJobPost.otherQualification;
                     $scope.jobpostingdto.REMARKS = forJobPost.remarks;*/
                    $scope.jobpostingdto.circularNo = forJobPost.CIRCULARNO;
                    $scope.jobpostingdto.educationalQualification = forJobPost.educationalQualification;
                    $scope.jobpostingdto.otherQualification = forJobPost.otherQualification;
                    $scope.jobpostingdto.remarks = forJobPost.remarks;
                    $scope.jobpostingdto.publishDate = forJobPost.publishDate;
                    $scope.jobpostingdto.applicationDate = forJobPost.applicationDate;
                    $scope.jobpostingdto.attachmentContentType = forJobPost.attachmentContentType;
                    $scope.jobpostingdto.createdDate = d;
                    $scope.jobpostingdto.updatedDate = d;
                    $scope.jobpostingdto.status = "1";
                    $scope.jobpostingdto.attachment = forJobPost.attachment;
                    $scope.jobpostingdto.attachmentImageName = forJobPost.attachmentImgName;
                    $scope.jobpostingdto.positionName = forJobPost.POSITION;
                    $scope.jobpostingdto.vacantPosition = forJobPost.AVAILABLE_POSTINGS;
                    $scope.jobpostingdto.department = forJobPost.DEPARTMENT;
                    console.log("whats posting" + $scope.jobpostingdto);
                    console.log("whats posting POSITION_NAME " + $scope.jobpostingdto.POSITION_NAME);
                    console.log("whats posting DEPARTMENT" + $scope.jobpostingdto.DEPARTMENT);
                    /*$scope.ii = jobPosting.get($scope.jobpostingdto);*/
                    jobPosting.get($scope.jobpostingdto, savesuccess);
                    /* console.log("return >>> " + $scope.ii);*/
                });
            }

            $scope.clear = function () {
                $scope.risNewAppForm = {
                    designation: null,
                    circularNo: null,
                    applicationDate: null,
                    applicantsNameBn: null,
                    applicantsNameEn: null,
                    nationalId: null,
                    birthCertificateNo: null,
                    birthDate: null,
                    age: null,
                    fathersName: null,
                    mothersName: null,
                    holdingNameBnPresent: null,
                    villageBnPresent: null,
                    unionBnPresent: null,
                    poBnPresent: null,
                    poCodeBnPresent: null,
                    holdingNameBnPermanent: null,
                    villageBnPermanent: null,
                    unionBnPermanent: null,
                    poBnPermanent: null,
                    poCodeBnPermanent: null,
                    contactPhone: null,
                    email: null,
                    nationality: null,
                    profession: null,
                    religion: null,
                    holdingNameEnPresent: null,
                    villageEnPresent: null,
                    unionEnPresent: null,
                    poEnPresent: null,
                    poCodeEnPresent: null,
                    holdingNameEnPermanent: null,
                    villageEnPermanent: null,
                    unionEnPermanent: null,
                    poEnPermanent: null,
                    written_exam: null,
                    viva_exam: null,
                    poCodeEnPermanent: null,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    id: null
                };
            };
        }]);
