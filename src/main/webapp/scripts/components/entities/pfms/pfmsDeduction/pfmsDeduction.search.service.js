'use strict';

angular.module('stepApp')
    .factory('PfmsDeductionSearch', function ($resource) {
        return $resource('api/_search/pfmsDeductions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
