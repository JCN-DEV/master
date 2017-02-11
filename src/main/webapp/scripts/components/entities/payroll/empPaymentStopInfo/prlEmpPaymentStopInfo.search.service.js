'use strict';

angular.module('stepApp')
    .factory('PrlEmpPaymentStopInfoSearch', function ($resource) {
        return $resource('api/_search/prlEmpPaymentStopInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
