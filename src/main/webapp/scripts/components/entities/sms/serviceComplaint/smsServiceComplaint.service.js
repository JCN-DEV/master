'use strict';

angular.module('stepApp')
    .factory('SmsServiceComplaint', function ($resource, DateUtils) {
        return $resource('api/smsServiceComplaints/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('SmsServiceComplaintsByEmployee', function ($resource) {
        return $resource('api/userServiceComplaints', {
            'query': { method: 'GET', isArray: true}
        });
    });
