'use strict';

angular.module('stepApp')
    .factory('InstEmpAddressTempSearch', function ($resource) {
        return $resource('api/_search/instEmpAddressTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
