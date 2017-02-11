'use strict';

angular.module('stepApp')
    .factory('PgmsPenGrCalculationSearch', function ($resource) {
        return $resource('api/_search/pgmsPenGrCalculations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
