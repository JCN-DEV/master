'use strict';

angular.module('stepApp')
    .factory('SmsServiceDepartment', function ($resource, DateUtils) {
        return $resource('api/smsServiceDepartments/:id', {}, {
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
    });
