'use strict';

angular.module('stepApp')
    .factory('FeeInvoiceSearch', function ($resource) {
        return $resource('api/_search/feeInvoices/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
