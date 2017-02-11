'use strict';

angular.module('stepApp')
    .factory('FeePaymentCollectionSearch', function ($resource) {
        return $resource('api/_search/feePaymentCollections/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
