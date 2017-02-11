'use strict';

angular.module('stepApp')
    .factory('SmsServiceType', function ($resource, DateUtils) {
        return $resource('api/smsServiceTypes/:id', {}, {
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
    .factory('SmsServiceTypeByStatus', function ($resource) {
        return $resource('api/smsServiceTypesByStat/:stat', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
