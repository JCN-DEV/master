'use strict';

angular.module('stepApp')
    .factory('ApplicantEducationSearch', function ($resource) {
        return $resource('api/_search/applicantEducations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('ApplicantEducationEmployee', function ($resource) {
        return $resource('api/applicantEducations/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
