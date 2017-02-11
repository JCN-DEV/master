'use strict';

angular.module('stepApp')
    .factory('SmsServiceForwardSearch', function ($resource) {
        return $resource('api/_search/smsServiceForwards/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('SmsServiceComplaintForwardSearch', function ($resource) {
        return $resource('api/smsServiceForwards/complaint/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
