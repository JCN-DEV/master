'use strict';

angular.module('stepApp')
    .factory('SmsGatewaySearch', function ($resource) {
        return $resource('api/_search/smsGateways/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
