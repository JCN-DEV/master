'use strict';

angular.module('stepApp')
    .factory('SmsServiceTypeSearch', function ($resource) {
        return $resource('api/_search/smsServiceTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
