'use strict';

angular.module('stepApp')
    .factory('PfmsDeductionFinalizeSearch', function ($resource) {
        return $resource('api/_search/pfmsDeductionFinalizes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
