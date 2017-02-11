'use strict';

angular.module('stepApp')
    .factory('SmsServiceAssignSearch', function ($resource) {
        return $resource('api/_search/smsServiceAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
