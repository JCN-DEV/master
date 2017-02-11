'use strict';

angular.module('stepApp')
    .factory('FeePaymentTypeSetupSearch', function ($resource) {
        return $resource('api/_search/feePaymentTypeSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
