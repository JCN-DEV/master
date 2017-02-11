'use strict';

angular.module('stepApp')
    .factory('PfmsEmpAdjustmentSearch', function ($resource) {
        return $resource('api/_search/pfmsEmpAdjustments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
