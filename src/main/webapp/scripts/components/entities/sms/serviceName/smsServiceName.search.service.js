'use strict';

angular.module('stepApp')
    .factory('SmsServiceNameSearch', function ($resource) {
        return $resource('api/_search/smsServiceNames/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
