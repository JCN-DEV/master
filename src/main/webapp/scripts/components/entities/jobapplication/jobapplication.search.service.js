'use strict';

angular.module('stepApp')
    .factory('JobapplicationSearch', function ($resource) {
        return $resource('api/_search/jobapplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('JobapplicationJob', function ($resource) {
        return $resource('api/jobapplications/job/:id', {}, {responseType:'arraybuffer'}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('EmployeeJobApplication', function ($resource) {
        return $resource('api/employeeApplications/my', {}, {responseType:'arraybuffer'}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ViewedJobApplication', function ($resource) {
        return $resource('api/jobapplications/job/viewed/:id', {}, {responseType:'arraybuffer'}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('UniewedJobApplication', function ($resource) {
        return $resource('api/jobapplications/job/unviewed/:id', {}, {responseType:'arraybuffer'}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobApplicationsDto', function ($resource) {
        return $resource('api/jobapplicationsDto/job/:id', {}, {
            'query': { method: 'GET', isArray: false}
        });
    }).factory('JobApplicationsShortList', function ($resource) {
        return $resource('api/jobapplications/job/shortListed/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobApplicationsRejectedList', function ($resource) {
        return $resource('api/jobapplications/job/rejected/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobApplicationsSelectedList', function ($resource) {
        return $resource('api/jobapplications/job/finalSelected/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobApplicationsInterviewList', function ($resource) {
        return $resource('api/jobapplications/job/interviewed/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobApplicationsTotalExp', function ($resource) {
        return $resource('api/jobapplications/experience/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
