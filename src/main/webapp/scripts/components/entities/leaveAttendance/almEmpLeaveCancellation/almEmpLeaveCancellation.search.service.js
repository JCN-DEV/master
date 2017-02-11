'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveCancellationSearch', function ($resource) {
        return $resource('api/_search/almEmpLeaveCancellations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
