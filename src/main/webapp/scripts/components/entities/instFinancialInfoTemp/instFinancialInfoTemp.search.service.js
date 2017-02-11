'use strict';

angular.module('stepApp')
    .factory('InstFinancialInfoTempSearch', function ($resource) {
        return $resource('api/_search/instFinancialInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
