'use strict';

angular.module('stepApp')
    .factory('SmsServiceDepartmentSearch', function ($resource) {
        return $resource('api/_search/smsServiceDepartments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('SmsServiceComplaintDepartmentSearch', function ($resource) {
        return $resource('api/smsServiceComplaints/department/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
