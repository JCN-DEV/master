'use strict';

angular.module('stepApp')
    .factory('PfmsEmpMonthlyAdjSearch', function ($resource) {
        return $resource('api/_search/pfmsEmpMonthlyAdjs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
