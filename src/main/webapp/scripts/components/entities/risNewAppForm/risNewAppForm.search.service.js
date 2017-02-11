'use strict';

angular.module('stepApp')
    .factory('RisNewAppFormSearch', function ($resource) {
        return $resource('api/_search/risNewAppForms/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })

    .factory('risNewAppFormsbyuser', function ($resource) {
        return $resource('api/risNewAppFormsbyuser', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('getEmployed', function ($resource) {
        return $resource('api/_search/risNewAppForms/getEmployed', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('getApplicantsByDesignation', function ($resource) {
        return $resource('api/risNewAppForms/designation/:designation', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })
    .factory('writtenmarksentry', function ($resource) {
        return $resource('api/risNewAppForms/writtenmarksentry/:registration/:marks', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })
    .factory('vivamarksentry', function ($resource) {
        return $resource('api/risNewAppForms/vivamarksentry/:registration/:marks', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })
    .factory('applicantStatus', function ($resource) {
        return $resource('api/risNewAppForms/applicantStatus', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })
    .factory('smsWritten', function ($resource) {
        return $resource('api/risNewAppForms/smsWritten/:seat', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('findById', function ($resource) {
        return $resource('api/risNewAppForms/findById/:id', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('risAppDetailById', function ($resource) {
        return $resource('api/risNewAppForms/findRisAppDetailByID/:id', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    /*.factory('sendingEmail', function ($resource) {
     return $resource('api/risNewAppForms/sendingEmail/:email/:name/:phone/:subject/:body', {}, {
     'get': { method: 'GET', isArray: false}
     });
     })*/


    .factory('sendingEmail', function ($resource) {
        return $resource('api/risNewAppForms/sendingEmail/:id/:messagebody/:messageSubject/:venue_name/:exam_date/:exam_time/:status', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })

    .factory('emailSend', function ($resource) {
        return $resource('api/risNewAppForms/emailSend/:id/:messagebody/:messageSubject/:venue_name/:exam_date/:exam_time/:status', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })

    .factory('sendingEmailAppointmentLetter', function ($resource) {
        return $resource('api/risNewAppForms/sendingEmailAppointmentLetter/:regNo/:messagebody/:status', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })

    .factory('getApplicantsByStatus', function ($resource) {
        return $resource('api/risNewAppForms/getApplicantsByStatus/:status', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })
    .factory('getApplicantsByStatusWithNumber', function ($resource) {
        return $resource('api/risNewAppForms/getApplicantsByStatus/:status/:number', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('Selection', function ($resource) {
        return $resource('api/risNewAppForms/selection/:regNo/:status', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })


    .factory('getOneApplicantByStatus', function ($resource) {
        return $resource('api/risNewAppForms/getOneApplicantsByStatusid/:status/:id', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('userregistration', function ($resource) {
        return $resource('api/risNewAppForms/userregistration/:username/:password/:firstname/:lastname/:email', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })

    .factory('getjobRequest', function ($resource) {
        return $resource('api/risNewAppForms/getjobRequest/:status', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('getJobByCircularStatus', function ($resource) {
        return $resource('api/risNewAppForms/getJobByCircularStatus/:status', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('getapplicantbycircularandstatus', function ($resource) {
        return $resource('api/risNewAppForms/getapplicantbycircularandstatus', {}, {
            'get': {method: 'POST', isArray: true}
        });
    })

    .factory('getApplicantByRegNo', function ($resource) {
        return $resource('api/risNewAppForms/getApplicantByRegNo/:regno/:status', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })
    /*.factory('jobRequest', function ($resource) {

     return $resource('api/risNewAppForms/jobRequest/:position/:department/:allocated/:currentEmployee/:availableVacancy/:stataus/:circularno', {}, {
     'get': { method: 'GET', isArray: false}
     });
     })*/
    .factory('jobRequest', function ($resource) {
        return $resource('api/risNewAppForms/jobRequest', {}, {
            'update': {
                method: 'POST',
                transformRequest: function (data) {
                    // data.date = DateUtils.convertLocaleDateToServer(data.date);
                    if (data) {
                        return angular.toJson(data);
                    }

                }
            }

        });
    })
    .factory('jobRequestUpdate', function ($resource) {
        return $resource('api/risNewAppForms/jobRequestUpdate/:position/:department/:status/:circularno', {}, {
            'get': {method: 'GET', isArray: false}
        });
    })

    .factory('getDesignation', function ($resource) {
        return $resource('api/risNewAppForms/getDesignation', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('getCircularNumber', function ($resource) {
        return $resource('api/risNewAppForms/getCircularNumber', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    /*.factory('seatwithcircular', function ($resource) {
     return $resource('api/risNewAppForms/seatwithcircular', {}, {
     'update': {
     method: 'POST',
     transformRequest: function (data) {
     // data.date = DateUtils.convertLocaleDateToServer(data.date);
     if(data){
     return angular.toJson(data);
     }

     }
     }

     });
     })*/
    .factory('seatwithcircular', function ($resource) {
        return $resource('api/risNewAppForms/seatwithcircular', {}, {
            'update': {method: 'POST', isArray: true}
        });
    })

    .factory('gettingwithcircular', function ($resource) {
        return $resource('api/risNewAppForms/gettingwithcircular', {}, {
            'get': {method: 'POST', isArray: true}
        });
    })

    .factory('positionbycircular', function ($resource) {
        return $resource('api/risNewAppForms/positionbycircular', {}, {
            'get': {method: 'POST', isArray: true}
        });
    })
    .factory('uniqueCircular', function ($resource) {
        return $resource('api/risNewAppForms/uniqueCircular', {}, {
            'get': {method: 'POST', isArray: true}
        });
    })

    .factory('getJobCircular', function ($resource) {
        return $resource('api/risNewAppForms/getJobCircular', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })
    .factory('jobRequestUpdateByCircularNo', function ($resource) {
        return $resource('api/risNewAppForms/jobRequestUpdateByCircularNo', {}, {
            'get': {method: 'POST', isArray: true}
        });
    })

    .factory('jobPosting', function ($resource) {
        return $resource('api/risNewAppForms/jobPosting', {}, {
            'get': {method: 'PUT', isArray: false}
        });
    })

    .factory('TestHello', function ($resource) {
        return $resource('api/risNewAppForms/hello', {}, {
            'create': {method: 'POST', isArray: false}
        });
    })

    .factory('getjobPosting', function ($resource) {
        return $resource('api/risNewAppForms/getjobPosting', {}, {
            'post': {method: 'POST', isArray: true}
        });
    })

    .factory('getalljobPosting', function ($resource) {
        return $resource('api/risNewAppForms/getalljobPosting', {}, {
            'post': {method: 'POST', isArray: true}
        });
    })
    .factory('getJobByCircular', function ($resource) {
        return $resource('api/risNewAppForms/getJobByCircular', {}, {
            'get': {method: 'POST', isArray: true}
        });
    })
;
