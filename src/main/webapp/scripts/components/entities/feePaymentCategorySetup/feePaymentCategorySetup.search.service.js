'use strict';

angular.module('stepApp')
    .factory('FeePaymentCategorySetupSearch', function ($resource) {
        return $resource('api/_search/feePaymentCategorySetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
